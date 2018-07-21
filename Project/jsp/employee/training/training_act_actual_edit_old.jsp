<%
            /* 
             * Page Name  		:  training_act_actual_edit.jsp
             * Created on 		:  [date] [time] AM/PM 
             * 
             * @author  		:  [authorName] 
             * @version  		:  [version] 
             */

            /*******************************************************************
             * Page Description 	: [project description ... ] 
             * Imput Parameters 	: [input parameter ...] 
             * Output 			: [output ...] 
             *******************************************************************/
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
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>

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

<!-- Jsp Block -->
<%
    CtrlTrainingActivityActual ctrlTrainingActivityActual = new CtrlTrainingActivityActual(request);
    CtrlTrainingHistory ctrlTrainingHistory = new CtrlTrainingHistory(request);

    long oidTrainingActivityActual = FRMQueryString.requestLong(request, "hidden_training_activity_id");
    long oidDepartment = FRMQueryString.requestLong(request, "oidDepartment");
    long oidTraining = FRMQueryString.requestLong(request, "oidTraining");
    long oidEmployee = FRMQueryString.requestLong(request, "oidEmployee");
    long oidTrainingHistory = FRMQueryString.requestLong(request, "oidTrainingHistory");
    long oidTrainingAktivityActual = FRMQueryString.requestLong(request, "oidTrainingAktivityActual");
    long date = FRMQueryString.requestLong(request, "date");

    String trainer = request.getParameter("trainer");
    long oidTrainingPlan = FRMQueryString.requestLong(request, "oidTrainingPlan");
    System.out.println("oidTrainingPlan.... " + oidTrainingPlan);

    int iErrCode = FRMMessage.ERR_NONE;
    int start = 0;
    String errMsg = "";
    String whereClause = "";
    String orderClause = "";
    int iCommand = FRMQueryString.requestCommand(request);

    ControlLine ctrLine = new ControlLine();

    iErrCode = ctrlTrainingActivityActual.action(iCommand, oidTrainingActivityActual, oidDepartment, request);

    errMsg = ctrlTrainingActivityActual.getMessage();
    FrmTrainingActivityActual frmTrainingActivityActual = ctrlTrainingActivityActual.getForm();
    TrainingActivityActual trainingActivityActual = ctrlTrainingActivityActual.getTrainingActivityActual();

    oidTrainingActivityActual = trainingActivityActual.getOID();
    Vector vctEmployeeTraining = new Vector(1, 1);
    vctEmployeeTraining = PstTrainingHistory.listEmployeeTraining(oidTrainingPlan);
%>
<!-- End of Jsp Block -->
<html>
    
    <head>
        <title>HARISMA - Training Activity Actual</title>
        <script language="JavaScript">
            
            function cmdCancel(){
                document.fractual.command.value="<%=Command.CANCEL%>";
                document.fractual.action="training_act_actual_edit.jsp";
                document.fractual.submit();
            } 
            
            function cmdEdit(oid){ 
                document.fractual.command.value="<%=Command.EDIT%>";
                document.fractual.action="training_act_actual_edit.jsp";
                document.fractual.submit(); 
            } 
            
            function cmdSave(){
                document.fractual.command.value="<%=Command.SAVE%>"; 
                document.fractual.action="training_act_actual_edit.jsp";
                document.fractual.submit();
            }
            
            function cmdAdd(){
                document.fractual.command.value="<%=Command.ADD%>"; 
                document.fractual.action="training_act_actual_edit.jsp";
                document.fractual.submit();
            } 
            
            function cmdAsk(oid){
                document.fractual.command.value="<%=Command.ASK%>"; 
                document.fractual.action="training_act_actual_edit.jsp";
                document.fractual.submit();
            } 
            
            function cmdConfirmDelete(oid){
                document.fractual.command.value="<%=Command.DELETE%>";
                document.fractual.action="training_act_actual_edit.jsp"; 
                document.fractual.submit();
            }  
            
            function cmdBack(){
                document.fractual.command.value="<%=Command.BACK%>"; 
                document.fractual.action="training_act_actual_list.jsp";
                document.fractual.submit();
            }
            
            function cmdChg(){
                window.open("search_plan.jsp", null, "height=600,width=800, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");
            }
            /*function cmdSearch(){
                
                oidDepartment = document.fractual.oidDepartment.value;
                oidTraining = document.fractual.oidTraining.value;
                oidTrainingAktivityActual = document.fractual.hidden_training_activity_id.value;
                oidTrainingPlan=document.fractual.oidTrainingPlan.value;
                
                //alert("OidDepartment=" + oidDepartment + ", OidTraining=" + oidTraining + ", oidTrainingActual=" + oidTrainingAktivityActual);
                //trainer =  document.fractual.trainer.value
                //alert("search_employee.jsp?oidDepartment=" + oidDepartment + ",oidTraining=" + oidTraining + ",oidTrainingAktivityActual=" + oidTrainingAktivityActual + ",oidTrainingPlan=" + oidTrainingPlan);
                //window.open("search_employee.jsp?oidDepartment=" + oidDepartment + "&oidTraining=" + 
                             oidTraining + "&oidTrainingAktivityActual=" + oidTrainingAktivityActual + "&oidTrainingPlan=" + oidTrainingPlan, 
                             null, "height=600,width=800, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");
                
            }*/
            
            function cmdSearch() {
                
                oidDepartment = document.fractual.oidDepartment.value;
                oidTraining = document.fractual.oidTraining.value;
                oidTrainingAktivityActual = document.fractual.hidden_training_activity_id.value;
                oidTrainingPlan=document.fractual.oidTrainingPlan.value;
                
                window.open("search_employee.jsp?oidDepartment=" + oidDepartment + "&oidTraining=" + oidTraining + "&oidTrainingAktivityActual=" + oidTrainingAktivityActual + "&oidTrainingPlan=" + oidTrainingPlan,
                            null, "height=600,width=800, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");
            }
            
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
            
            function showObjectForMenu(){
            }
        </SCRIPT>
    </head>
    <body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
            <tr> 
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
                <%@ include file = "../../main/header.jsp" %> </td>
            </tr>
            <tr> 
                <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <%@ include file = "../../main/mnmain.jsp" %> </td>
            </tr>
            <tr> 
                <td  bgcolor="#9BC1FF" height="10" valign="middle"> <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr> 
                            <td align="left"><img src="<%=approot%>/images/harismaMenuLeft1.jpg" width="8" height="8"></td>
                            <td align="center" background="<%=approot%>/images/harismaMenuLine1.jpg" width="100%"><img src="<%=approot%>/images/harismaMenuLine1.jpg" width="8" height="8"></td>
                            <td align="right"><img src="<%=approot%>/images/harismaMenuRight1.jpg" width="8" height="8"></td>
                        </tr>
                </table></td>
            </tr>
            <tr> 
                <td width="88%" valign="top" align="left"> <table width="100%" border="0" cellspacing="3" cellpadding="2">
                        <tr> 
                            <td width="100%"> <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr> 
                                        <td height="20"> <font color="#FF6600" face="Arial"><strong> Training 
                                        &gt; Training Activity </strong></font> </td>
                                    </tr>
                                    <tr> 
                                        <td> <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr> 
                                                    <td class="tablecolor"> <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                            <tr> 
                                                                <td valign="top"> <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                                                        <tr> 
                                                                            <td valign="top"> <form name="fractual" method="post" action="">
                                                                                    <input type="hidden" name="command" value="">
                                                                                    <input type="hidden" name="start" value="<%=start%>">
                                                                                    <input type="hidden" name="hidden_training_activity_id" value="<%=oidTrainingActivityActual%>">
                                                                                    <input type="hidden" name="<%=FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_TRAINING_ACTIVITY_PLAN_ID]%>" value="<%=trainingActivityActual.getTrainingActivityPlanId()%>">
                                                                                    <input  type="hidden" name="oidDepartment" value="<%=oidDepartment%>" size="50">
                                                                                    <input  type="hidden" name="oidTraining" value="<%=oidTraining%>" size="50">
                                                                                    <input  type="hidden" name="oidTrainingPlan" value="<%=oidTrainingPlan%>" size="50">
                                                                                    
                                                                                    <table width="100%" cellspacing="1" cellpadding="1" >
                                                                                        <tr> 
                                                                                            <td colspan="3">&nbsp;</td>
                                                                                        </tr>
                                                                                        <tr> 
                                                                                            <td width="0%">&nbsp;</td>
                                                                                            <td class="txtheading1" align="center" width="12%" nowrap>&nbsp;</td>
                                                                                            <td class="comment" width="85%">*) entry 
                                                                                            required</td>
                                                                                        </tr>
                                                                                        <%
            whereClause = PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_TRAINING_ACTIVITY_PLAN_ID] + " = " + trainingActivityActual.getTrainingActivityPlanId();
            Vector plans = PstTrainingActivityPlan.list(0, 0, whereClause, "");
            String topic = "";
            trainer = "";
            //long oidDepartment = 0;
            //System.out.println("vect plans" +plans);
            if (plans != null && plans.size() > 0) {
                TrainingActivityPlan trnActPlan = (TrainingActivityPlan) plans.get(0);
                try {
                    Training tr = PstTraining.fetchExc(trnActPlan.getTrainingId());
                    topic = tr.getName();
                //TrainingActivityPlan trA = PstTrainingActivityPlan.fetchExc(trainingActivityActual.getTrainingActivityPlanId());
                //System.out.println("trnActPlan.getTrainingId() "+trnActPlan.getTrainingId()) ;

                } catch (Exception e) {
                }
            // topic = trnActPlan.getProgram();
            }
                                                                                        %>
                                                                                        <tr> 
                                                                                            <td width="0%"  valign="top" align="left"  >&nbsp;</td>
                                                                                            <td width="12%"  valign="top" align="left" nowrap  >Training 
                                                                                            Program</td>
                                                                                            <td  width="85%"  valign="top" align="left"> 
                                                                                                <input type="text" name="title" value="<%=topic%>" size="50" readonly>
                                                                                                * <%=frmTrainingActivityActual.getErrorMsg(FrmTrainingActivityActual.FRM_FIELD_TRAINING_ACTIVITY_PLAN_ID)%> <a href="javascript:cmdChg()">search 
                                                                                            for existing plan</a></td>
                                                                                        </tr>
                                                                                        <tr> 
                                                                                            <td width="0%"  valign="top" align="left"  >&nbsp;</td>
                                                                                            <td width="12%"  valign="top" align="left" nowrap  >Date</td>
                                                                                            <td  width="85%"  valign="top" align="left"> 
                                                                                        <%=ControlDate.drawDateWithStyle(FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_DATE], (trainingActivityActual.getDate() == null) ? new Date() : trainingActivityActual.getDate(), 1, -5, "formElemen", "")%> * <%=frmTrainingActivityActual.getErrorMsg(FrmTrainingActivityActual.FRM_FIELD_DATE)%> </tr>
                                                                                        <tr> 
                                                                                            <td width="0%"  valign="top" align="left"  >&nbsp;</td>
                                                                                            <td width="12%"  valign="top" align="left" nowrap  > 
                                                                                            Time</td>
                                                                                            <td  width="85%"  valign="top" align="left"> 
                                                                                            <%=ControlDate.drawTime(FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_START_TIME], (trainingActivityActual.getStartTime() == null) ? new Date() : trainingActivityActual.getStartTime(), "formElemen")%> <%=frmTrainingActivityActual.getErrorMsg(FrmTrainingActivityActual.FRM_FIELD_START_TIME)%> <%="to"%> <%=ControlDate.drawTime(FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_END_TIME], (trainingActivityActual.getEndTime() == null) ? new Date() : trainingActivityActual.getEndTime(), "formElemen")%> * <%=frmTrainingActivityActual.getErrorMsg(FrmTrainingActivityActual.FRM_FIELD_END_TIME)%> </td>
                                                                                        </tr>
                                                                                        <tr> 
                                                                                            <td width="0%"  valign="top" align="left"  >&nbsp;</td>
                                                                                            <td width="12%"  valign="top" align="left" nowrap  >Atendees</td>
                                                                                            <td  width="85%"  valign="top" align="left"> 
                                                                                                <input type="text" name="<%=FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_ATENDEES]%>" value="<%if (trainingActivityActual.getAtendees() > 0) {%><%=trainingActivityActual.getAtendees()%><%}%>" class="formElemen" size="10"> 
                                                                                            <%=frmTrainingActivityActual.getErrorMsg(FrmTrainingActivityActual.FRM_FIELD_ATENDEES)%></td>
                                                                                        </tr>
                                                                                        <tr> 
                                                                                            <td width="0%"  valign="top" align="left"  >&nbsp;</td>
                                                                                            <td width="12%"  valign="top" align="left" nowrap  >Venue</td>
                                                                                            <td  width="85%"  valign="top" align="left"> 
                                                                                                <input type="text" name="<%=FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_VENUE]%>" value="<%=trainingActivityActual.getVenue()%>" class="formElemen" size="30">
                                                                                            * <%=frmTrainingActivityActual.getErrorMsg(FrmTrainingActivityActual.FRM_FIELD_VENUE)%></td>
                                                                                        </tr>
                                                                                        <tr> 
                                                                                            <td  valign="top" align="left"  >&nbsp;</td>
                                                                                            <td  valign="top" align="left" nowrap  >Trainner</td>
                                                                                            <td  valign="top" align="left">
                                                                                                <input type="hidden" name="trainer" value="<% if (trainingActivityActual.getTrainner().equals("")) {%><%=trainer%><%} else {%><%=trainingActivityActual.getTrainner()%><%}%>" size="50">
                                                                                            <input  name="<%=FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_TRAINNER]%>" value="<% if (trainingActivityActual.getTrainner().equals("")) {%><%=trainer%><%} else {%><%=trainingActivityActual.getTrainner()%><%}%>" type="text" size="30" class="formElement" ></td>
                                                                                        </tr>
                                                                                        <tr> 
                                                                                            <td width="0%"  valign="top" align="left"  >&nbsp;</td>
                                                                                            <td width="12%"  valign="top" align="left" nowrap  >Remark</td>
                                                                                            <td  width="85%"  valign="top" align="left"> 
                                                                                                <textarea name="<%=FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_REMARK]%>" class="formElemen" cols="30" wrap="VIRTUAL" rows="3"><%=trainingActivityActual.getRemark()%></textarea> 
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr> 
                                                                                            <td width="0%"  valign="top" align="left"  >&nbsp;</td>
                                                                                            <td width="12%"  valign="top" align="left" nowrap ></td>
                                                                                            <td  width="85%"  valign="top" align="left">&nbsp;</td>
                                                                                        </tr>
                                                                                        <tr> 
                                                                                            <td  valign="top" align="left"  >&nbsp;</td>
                                                                                            <td  valign="top" align="left" nowrap  > </td>
                                                                                            <td width="85%"></td>
                                                                                            <td width="3%"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                                                        </tr>
                                                                                        
                                                                                        <tr> 
                                                                                            <td colspan="3"> <%
            ctrLine.setLocationImg(approot + "/images");
            ctrLine.initDefault();
            ctrLine.setTableWidth("85%");
            String scomDel = "javascript:cmdAsk('" + oidTrainingActivityActual + "')";
            String sconDelCom = "javascript:cmdConfirmDelete('" + oidTrainingActivityActual + "')";
            String scancel = "javascript:cmdEdit('" + oidTrainingActivityActual + "')";
            ctrLine.setBackCaption("Back to Search Training Activity");
            ctrLine.setDeleteCaption("Delete Training Activity");
            ctrLine.setSaveCaption("Save Training Activity");
            ctrLine.setAddCaption("Add Training Activity");
            ctrLine.setCommandStyle("buttonlink");

            if (privDelete) {
                ctrLine.setConfirmDelCommand(sconDelCom);
                ctrLine.setDeleteCommand(scomDel);
                ctrLine.setEditCommand(scancel);
            } else {
                ctrLine.setConfirmDelCaption("");
                ctrLine.setDeleteCaption("");
                ctrLine.setEditCaption("");
                out.println("setelah save");
            }

            if (privAdd == false && privUpdate == false) {
                ctrLine.setSaveCaption("");
            }

            if (privAdd == false) {
                ctrLine.setAddCaption("");


            }

            if (iCommand == Command.SAVE || iCommand == Command.EDIT) {%>
                                                                                                <!--masukkan detail employee yang ikut trainning-->
                                                                                                <table width="100%" border="0">
                                                                                                    <tr>
                                                                                                        <td width="13%">Detail Training</td>
                                                                                                        <td width="87%"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a> 
                                                                                                            <a href="javascript:cmdSearch()">Edit Attendances</a></td>
                                                                                                    </tr>
                                                                                                    <tr> 
                                                                                                        
                                                                                                        <td colspan="2" align="left"  valign="top" nowrap  ><table   width="100%" border="0">
                                                                                                                <tr> 
                                                                                                                    <td  class="listgentitle" width="5%">No</td>
                                                                                                                    <td  class="listgentitle" width="21%">Payroll 
                                                                                                                    Number</td>
                                                                                                                    <td  class="listgentitle" width="24%">Name</td>
                                                                                                                    <td  class="listgentitle" width="18%"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                                                                                                    <td  class="listgentitle" width="18%">Commencing 
                                                                                                                    Date</td>
                                                                                                                </tr>
                                                                                                                <tr > 
                                                                                                                    
                                                                                                                    <%
                                                                                                                                             if (vctEmployeeTraining != null && vctEmployeeTraining.size() > 0) {
                                                                                                                                                 for (int i = 0; i < vctEmployeeTraining.size(); i++) {
                                                                                                                                                     TrainingHistory trHis = (TrainingHistory) vctEmployeeTraining.get(i);


                                                                                                                    %>
                                                                                                                    <td class="listgensell"><%=start + (i + 1)%></td>
                                                                                                                    
                                                                                                                    <td class="listgensell"><%
                                                                                                                                Employee emp = new Employee();
                                                                                                                                try {
                                                                                                                                    emp = PstEmployee.fetchExc(trHis.getEmployeeId());
                                                                                                                                } catch (Exception e) {
                                                                                                                                    emp = new Employee();
                                                                                                                                }

                                                                                                                                Department dept = new Department();
                                                                                                                                try {
                                                                                                                                    dept = PstDepartment.fetchExc(oidDepartment);
                                                                                                                                } catch (Exception e) {
                                                                                                                                    dept = new Department();
                                                                                                                                }
                                                                                                                        %> 
                                                                                                                    <%=emp.getEmployeeNum()%></td>
                                                                                                                    <td class="listgensell"><%=emp.getFullName()%></td>
                                                                                                                    
                                                                                                                    <td class="listgensell"><%=dept.getDepartment() %></td>
                                                                                                                    <td class="listgensell"><%= (emp.getCommencingDate() == null) ? "-" : Formater.formatDate(emp.getCommencingDate(), "dd MMMM yyyy") %> </td>
                                                                                                                </tr>
                                                                                                                <% }
                                                                                                                                             }%>
                                                                                                                <tr class="listgensell"> 
                                                                                                                    <td colspan="6"><% if (vctEmployeeTraining.size() == 0) {%>
                                                                                                                        no employee available 
                                                                                                                        .... 
                                                                                                                    <% }%></td>
                                                                                                                </tr>
                                                                                                        </table></td>
                                                                                                    </tr>
                                                                                                </table>
                                                                                                <% }%>
                                                                                            <%= ctrLine.drawImage(iCommand, iErrCode, errMsg)%> </td>
                                                                                        </tr>
                                                                                    </table>
                                                                            </form></td>
                                                                        </tr>
                                                                </table></td>
                                                            </tr>
                                                    </table></td>
                                                </tr>
                                        </table></td>
                                    </tr>
                            </table></td>
                        </tr>
                </table></td>
            </tr>
            <tr> 
                <td colspan="2" height="20" <%=bgFooterLama%>> <%@ include file = "../../main/footer.jsp" %> </td>
            </tr>
        </table>
    </body>
</html>
