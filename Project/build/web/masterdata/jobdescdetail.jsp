<%-- 
    Document   : jobdescdetail
    Created on : Nov 12, 2015, 1:46:50 PM
    Author     : khirayinnura
--%>

<%@page import="com.dimata.gui.jsp.ControlDate"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.harisma.entity.masterdata.jobdesc.JobCat"%>
<%@page import="com.dimata.harisma.entity.masterdata.jobdesc.PstJobCat"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.entity.masterdata.jobdesc.JobDesc"%>
<%@page import="com.dimata.harisma.entity.masterdata.jobdesc.JobDesc"%>
<%@page import="com.dimata.harisma.entity.masterdata.jobdesc.PstJobDesc"%>
<%@page import="com.dimata.harisma.form.masterdata.jobdesc.FrmJobDesc"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.masterdata.jobdesc.CtrlJobDesc"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>

<%@ include file = "../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_DIVISION);%>
<%@ include file = "../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%!    public String drawList(Vector objectClass, long jobDescId) {
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("No.", "10%");
        ctrlist.addHeader("Job Title", "50%");
        ctrlist.addHeader("Job Category", "50%");
        ctrlist.addHeader("Job Desc", "50%");
        ctrlist.addHeader("Job Input", "50%");
        ctrlist.addHeader("Job Output ", "50%");
        ctrlist.addHeader("Schedule Type ", "50%");
        ctrlist.addHeader("Repeat Type ", "50%");
        ctrlist.addHeader("Start Date", "50%");
        ctrlist.addHeader("End Date ", "50%");
        ctrlist.addHeader("Repeat Every ", "50%");
        ctrlist.addHeader("Repeat Until Date ", "50%");
        ctrlist.addHeader("Repeat Until Qty ", "50%");
        ctrlist.addHeader("Repeat Week Days ", "50%");
        ctrlist.addHeader("Repeat Month Date ", "50%");
        ctrlist.addHeader("Repeat Month Day ", "50%");
        ctrlist.addHeader("Reminder Minutes Before ", "50%");
        ctrlist.addHeader("Notification ", "50%");
        ctrlist.addHeader("Checker Position", "50%");
        ctrlist.addHeader("Approver Position", "50%");
        
        ctrlist.setLinkRow(1);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        int index = -1;
                
        for (int i = 0; i < objectClass.size(); i++) {
            JobDesc jobDesc = (JobDesc) objectClass.get(i);
            Vector rowx = new Vector();
            if (jobDescId == jobDesc.getOID()) {
                index = i;
            }
                        
            int no = i+1;
            
            rowx.add(""+no);
            rowx.add(jobDesc.getJobTitle());
            String jobCatName = PstJobCat.getJobDescName(jobDesc.getJobCategoryId());
            rowx.add(jobCatName);
            rowx.add(jobDesc.getJobDesc());
            rowx.add(jobDesc.getJobInput());
            rowx.add(jobDesc.getJobOutput());
            rowx.add(""+jobDesc.getScheduleType());
            rowx.add(""+jobDesc.getRepeatType());
            rowx.add(""+jobDesc.getStartDatetime());
            rowx.add(""+jobDesc.getEndDatetime());
            rowx.add(""+jobDesc.getRepeatEvery());
            rowx.add(""+jobDesc.getRepeatUntilDate());
            rowx.add(""+jobDesc.getRepeatUntilQty());
            rowx.add(""+jobDesc.getRepeatWeekDays());
            rowx.add(""+jobDesc.getRepeatMonthDate());
            rowx.add(""+jobDesc.getRepeatMonthDay());
            rowx.add(""+jobDesc.getReminderMinutesBefore());
            rowx.add(""+jobDesc.getNotification());
            String checkerPos = PstPosition.getPositionName(String.valueOf(jobDesc.getCheckerPositionId()));
            rowx.add(checkerPos);
            String approvePos = PstPosition.getPositionName(String.valueOf(jobDesc.getApproverPositionId()));
            rowx.add(approvePos);
             
           
            lstData.add(rowx);
            lstLinkData.add(String.valueOf(jobDesc.getOID()));
        }
        return ctrlist.draw(index);
    }

%>

<%
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidJobDesc = FRMQueryString.requestLong(request, "hidden_jobdesc_id");
    int statPos = FRMQueryString.requestInt(request, "statPos");
    long oidPosition = FRMQueryString.requestLong(request, "hidden_position_id");
    long oidCheckPos = FRMQueryString.requestLong(request, "checker_pos_id");
    long oidAppPos = FRMQueryString.requestLong(request, "approve_pos_id");

    /*variable declaration*/
    int recordToGet = 50;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    String whereClause = "POSITION_ID="+oidPosition;
    String orderClause = "";

    CtrlJobDesc ctrlJobDesc = new CtrlJobDesc(request);
    ControlLine ctrLine = new ControlLine();
    Vector listJobDesc = new Vector(1, 1);

    /*switch statement */
    iErrCode = ctrlJobDesc.action(iCommand, oidJobDesc);
    /* end switch*/
    FrmJobDesc frmJobDesc = ctrlJobDesc.getForm();

    /*count list All Position*/
    int vectSize = PstJobDesc.getCount(whereClause);

    JobDesc jobDesc = ctrlJobDesc.getJobDesc();
    msgString = ctrlJobDesc.getMessage();

    if ((iCommand == Command.FIRST || iCommand == Command.PREV)
            || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
        start = ctrlJobDesc.actionList(iCommand, start, vectSize, recordToGet);
    }
    /* end switch list*/

    /* get record to display */
    listJobDesc = PstJobDesc.list(start, recordToGet, whereClause, orderClause);

    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listJobDesc.size() < 1 && start > 0) {
        if (vectSize - recordToGet > recordToGet) {
            start = start - recordToGet;   //go to Command.PREV
        } else {
            start = 0;
            iCommand = Command.FIRST;
            prevCommand = Command.FIRST; //go to Command.FIRST
        }
        listJobDesc = PstJobDesc.list(start, recordToGet, whereClause, orderClause);
    }
%>
<html>
    <head>
        <!-- #BeginEditable "doctitle" -->
        <title>HARISMA - Master Data Company</title>
        <script language="JavaScript">


            function cmdAdd(){
                document.frmjobdesc.hidden_jobdesc_id.value="0";
                document.frmjobdesc.command.value="<%=Command.ADD%>";
                document.frmjobdesc.prev_command.value="<%=prevCommand%>";
                document.frmjobdesc.action="jobdescdetail.jsp";
                document.frmjobdesc.submit();
            }

            function cmdAsk(oidCompany){
                document.frmjobdesc.hidden_jobdesc_id.value=oidCompany;
                document.frmjobdesc.command.value="<%=Command.ASK%>";
                document.frmjobdesc.prev_command.value="<%=prevCommand%>";
                document.frmjobdesc.action="jobdescdetail.jsp";
                document.frmjobdesc.submit();
            }

            function cmdConfirmDelete(oidCompany){
                document.frmjobdesc.hidden_jobdesc_id.value=oidCompany;
                document.frmjobdesc.command.value="<%=Command.DELETE%>";
                document.frmjobdesc.prev_command.value="<%=prevCommand%>";
                document.frmjobdesc.action="jobdescdetail.jsp";
                document.frmjobdesc.submit();
            }
            function cmdSave(){
                document.frmjobdesc.command.value="<%=Command.SAVE%>";
                document.frmjobdesc.prev_command.value="<%=prevCommand%>";
                document.frmjobdesc.action="jobdescdetail.jsp";
                document.frmjobdesc.submit();
            }

            function cmdEdit(oidCompany){
                document.frmjobdesc.hidden_jobdesc_id.value=oidCompany;
                document.frmjobdesc.command.value="<%=Command.EDIT%>";
                document.frmjobdesc.prev_command.value="<%=prevCommand%>";
                document.frmjobdesc.action="jobdescdetail.jsp";
                document.frmjobdesc.submit();
            }

            function cmdCancel(oidCompany){
                document.frmjobdesc.hidden_jobdesc_id.value=oidCompany;
                document.frmjobdesc.command.value="<%=Command.EDIT%>";
                document.frmjobdesc.prev_command.value="<%=prevCommand%>";
                document.frmjobdesc.action="jobdescdetail.jsp";
                document.frmjobdesc.submit();
            }

            function cmdBack(){
                document.frmjobdesc.command.value="<%=Command.BACK%>";
                document.frmjobdesc.action="jobdescdetail.jsp";
                document.frmjobdesc.submit();
            }

            function cmdListFirst(){
                document.frmjobdesc.command.value="<%=Command.FIRST%>";
                document.frmjobdesc.prev_command.value="<%=Command.FIRST%>";
                document.frmjobdesc.action="jobdescdetail.jsp";
                document.frmjobdesc.submit();
            }

            function cmdListPrev(){
                document.frmjobdesc.command.value="<%=Command.PREV%>";
                document.frmjobdesc.prev_command.value="<%=Command.PREV%>";
                document.frmjobdesc.action="jobdescdetail.jsp";
                document.frmjobdesc.submit();
            }

            function cmdListNext(){
                document.frmjobdesc.command.value="<%=Command.NEXT%>";
                document.frmjobdesc.prev_command.value="<%=Command.NEXT%>";
                document.frmjobdesc.action="jobdescdetail.jsp";
                document.frmjobdesc.submit();
            }

            function cmdListLast(){
                document.frmjobdesc.command.value="<%=Command.LAST%>";
                document.frmjobdesc.prev_command.value="<%=Command.LAST%>";
                document.frmjobdesc.action="jobdescdetail.jsp";
                document.frmjobdesc.submit();
            }
            
            function srcChecker(){
                var comm = document.frmjobdesc.command.value;
                newWindow=window.open("srcchecker.jsp?comm="+comm+"&statPos=<%=statPos%>","SearchChecker", "height=600,width=427,status=yes,toolbar=no,menubar=no,location=right,scrollbars=yes");
                newWindow.focus();
            }
            
            function srcApproved(){
                var comm = document.frmjobdesc.command.value;
                newWindow=window.open("srcapprover.jsp?comm="+comm,"SearchApp", "height=600,width=427,status=yes,toolbar=no,menubar=no,location=right,scrollbars=yes");
                newWindow.focus();
            }

            function fnTrapKD(){
                //alert(event.keyCode);
                switch(event.keyCode) {
                    case <%=LIST_PREV%>:
                            cmdListPrev();
                        break;
                    case <%=LIST_NEXT%>:
                            cmdListNext();
                        break;
                    case <%=LIST_FIRST%>:
                            cmdListFirst();
                        break;
                    case <%=LIST_LAST%>:
                            cmdListLast();
                        break;
                    default:
                        break;
                    }
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
        <!-- #EndEditable -->
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <!-- #BeginEditable "styles" -->
        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "stylestab" -->
        <link rel="stylesheet" href="../styles/tab.css" type="text/css">
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

        </SCRIPT>
        <style type="text/css">
            #btn {
                padding: 3px; border: 1px solid #CCC; 
                background-color: #EEE; color: #777777; 
                font-size: 11px; cursor: pointer;
            }
            #btn:hover {border: 1px solid #999; background-color: #CCC; color: #FFF;}
        </style>
        <!-- #EndEditable -->
    </head>
    <body>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td  style="background-color:<%=bgColorContent%>; "> 
                    <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                        <tr>
                            <td valign="top">
                                <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                    <tr>
                                        <td valign="top">
                                            <!-- #BeginEditable "content" -->
                                            <form name="frmjobdesc" method ="post" action="">
                                                <input type="hidden" name="command" value="<%=iCommand%>">
                                                <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                                <input type="hidden" name="start" value="<%=start%>">
                                                <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                <input type="hidden" name="hidden_jobdesc_id" value="<%=oidJobDesc%>">
                                                <input type="hidden" name="statPos" value="<%=statPos%>">
                                                <input type="hidden" name="hidden_position_id" value="<%=oidPosition%>">
                                                <input type="hidden" name="checker_pos_id" value="<%=oidCheckPos%>">
                                                <input type="hidden" name="approve_pos_id" value="<%=oidAppPos%>">
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr align="left" valign="top">
                                                        <td height="8"  colspan="3">
                                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                <tr align="left" valign="top">
                                                                    <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;Job Category List </td>
                                                                </tr>
                                                                <%
                                                                            try {
                                                                                if (listJobDesc.size() > 0) {
                                                                %>
                                                                <tr align="left" valign="top">
                                                                    <td height="22" valign="middle" colspan="3">
                                                                        <%= drawList(listJobDesc, oidJobDesc)%>
                                                                    </td>
                                                                </tr>
                                                                <%  }
                                                                    } catch (Exception exc) {
                                                                    }%>
                                                                <tr align="left" valign="top">
                                                                    <td height="8" align="left" colspan="3" class="command">
                                                                        <span class="command">
                                                                            <%
                                                                                        int cmd = 0;
                                                                                        if ((iCommand == Command.FIRST || iCommand == Command.PREV)
                                                                                                || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
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
                                                                            <%=ctrLine.drawImageListLimit(cmd, vectSize, start, recordToGet)%>
                                                                        </span> </td>
                                                                </tr>

                                                                   <%//if((iCommand == Command.NONE || iCommand == Command.DELETE || iCommand == Command.BACK || iCommand ==Command.SAVE)&& (frmCompany.errorSize()<1)){
                                                                if ((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT) && (frmJobDesc.errorSize() < 1)) {
                                                                    if (privAdd) {%>
                                                                <tr align="left" valign="top">
                                                                    <td>
                                                                        <table cellpadding="0" cellspacing="0" border="0">
                                                                            <tr>
                                                                                <td>&nbsp;</td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                                                <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                <td height="22" valign="middle" colspan="3" width="951">
                                                                                    <a href="javascript:cmdAdd()" class="command">Add
                                                                                        New </a> </td>
                                                                            </tr>
                                                                        </table>
                                                                    </td>
                                                                </tr>
                                                                <%}
                                                                  }%>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>&nbsp;
                                                        </td>
                                                    </tr>
                                                    <tr align="left" valign="top">
                                                        <td height="8" valign="middle" colspan="3">
                                                            <%if ((iCommand == Command.ADD) || (iCommand == Command.SAVE) && (frmJobDesc.errorSize() > 0) || (iCommand == Command.EDIT) || (iCommand == Command.ASK)) {%>
                                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                <tr>
                                                                    <td class="listtitle"><%=oidJobDesc == 0 ? "Add" : "Edit"%> Job Category</td>
                                                                </tr>
                                                                <tr>
                                                                    <td height="100%">
                                                                        <table border="0" cellspacing="2" cellpadding="2" width="50%">
                                                                            <tr align="left" valign="top">
                                                                                <td valign="top" width="17%">&nbsp;</td>
                                                                                <td width="83%" class="comment">*)entry required </td>
                                                                            </tr>
                                                                            <tr align="left" valign="top">
                                                                                <td valign="top" width="17%">
                                                                                    Job Title</td>
                                                                                <td width="83%">
                                                                                    <input type="text" name="<%=frmJobDesc.fieldNames[FrmJobDesc.FRM_FIELD_JOB_TITLE]%>"  value="<%= jobDesc.getJobTitle()%>" class="elemenForm" size="30">
                                                                                    *<%=frmJobDesc.getErrorMsg(FrmJobDesc.FRM_FIELD_JOB_TITLE)%>
                                                                                    <input type="hidden" name="<%=frmJobDesc.fieldNames[FrmJobDesc.FRM_FIELD_POSITION_ID]%>"  value="<%= oidPosition%>" class="elemenForm" size="30">
                                                                                </td>
                                                                            </tr>
                                                                            <tr align="left" valign="top">
                                                                                <td valign="top" width="17%">
                                                                                    Job Category</td>
                                                                                <td width="83%">
                                                                                    <%
                                                                                        Vector jobcat_value = new Vector(1, 1);
                                                                                        Vector jobcat_key = new Vector(1, 1);
                                                                                        Vector listJobCat = PstJobCat.list(0, 0, "", "");
                                                                                        jobcat_value.add(""+0);
                                                                                        jobcat_key.add("select");
                                                                                        for (int i = 0; i < listJobCat.size(); i++) {
                                                                                            JobCat jobCat = (JobCat) listJobCat.get(i);
                                                                                            jobcat_key.add(jobCat.getCategoryTitle());
                                                                                            jobcat_value.add(String.valueOf(jobCat.getOID()));
                                                                                        }

                                                                                    %>
                                                                                    <%= ControlCombo.draw(frmJobDesc.fieldNames[FrmJobDesc.FRM_FIELD_JOB_CATEGORY_ID], "formElemen", null, ""+jobDesc.getJobCategoryId(), jobcat_value, jobcat_key,"")%>
                                                                                </td>
                                                                            </tr>
                                                                            
                                                                            <tr align="left" valign="top">
                                                                                <td valign="top" width="17%">
                                                                                    Job Desc</td>
                                                                                <td width="83%">
                                                                                    <textarea name="<%=frmJobDesc.fieldNames[FrmJobDesc.FRM_FIELD_JOB_DESC]%>" class="elemenForm" cols="30" rows="3"><%= jobDesc.getJobDesc()%></textarea>
                                                                                </td>
                                                                            </tr>
                                                                            <tr align="left" valign="top">
                                                                                <td valign="top" width="17%">
                                                                                    Job Input</td>
                                                                                <td width="83%">
                                                                                    <input type="text" name="<%=frmJobDesc.fieldNames[FrmJobDesc.FRM_FIELD_JOB_INPUT]%>"  value="<%= jobDesc.getJobInput()%>" class="elemenForm" size="30">
                                                                                    <!--<textarea name="<!--%=frmJobDesc.fieldNames[FrmJobDesc.FRM_FIELD_JOB_INPUT]%>" class="elemenForm" cols="30" rows="3"><!--%= jobDesc.getJobInput()%></textarea>-->
                                                                                </td>
                                                                            </tr>
                                                                            <tr align="left" valign="top">
                                                                                <td valign="top" width="17%">
                                                                                    Job Output</td>
                                                                                <td width="83%">
                                                                                    <input type="text" name="<%=frmJobDesc.fieldNames[FrmJobDesc.FRM_FIELD_JOB_OUTPUT]%>"  value="<%= jobDesc.getJobOutput()%>" class="elemenForm" size="30">
                                                                                    <!--<textarea name="<!--%=frmJobDesc.fieldNames[FrmJobDesc.FRM_FIELD_JOB_OUTPUT]%>" class="elemenForm" cols="30" rows="3"><!--%= jobDesc.getJobOutput()%></textarea>-->
                                                                                </td>
                                                                            </tr>
                                                                            <tr align="left" valign="top">
                                                                                <td valign="top" width="17%">
                                                                                    Schedule Type</td>
                                                                                <td width="83%">
                                                                                    <%
                                                                                    Vector sche_value = new Vector(1, 1);
                                                                                    Vector sche_key = new Vector(1, 1);
                                                                                    sche_value.add(""+0);
                                                                                    sche_key.add("select");
                                                                                    for(int i=0;i<PstJobDesc.scheduleTypeKey.length;i++){
                                                                                        sche_key.add(""+PstJobDesc.scheduleTypeKey[i]);
                                                                                        sche_value.add(""+PstJobDesc.scheduleTypeValue[i]);
                                                                                    }
                                                                                    
                                                                                    %>
                                                                                    <%= ControlCombo.draw(frmJobDesc.fieldNames[FrmJobDesc.FRM_FIELD_SCHEDULE_TYPE], "formElemen", null, ""+jobDesc.getScheduleType(), sche_value, sche_key,"")%>
                                                                                </td>
                                                                            </tr>
                                                                            <tr align="left" valign="top">
                                                                                <td valign="top" width="17%">
                                                                                    Repeat Type</td>
                                                                                <td width="83%">
                                                                                    <%
                                                                                    Vector rep_value = new Vector(1, 1);
                                                                                    Vector rep_key = new Vector(1, 1);
                                                                                    rep_value.add(""+0);
                                                                                    rep_key.add("select");
                                                                                    for(int i=0;i<PstJobDesc.repeatTypeKey.length;i++){
                                                                                        rep_key.add(""+PstJobDesc.repeatTypeKey[i]);
                                                                                        rep_value.add(""+PstJobDesc.repeatTypeValue[i]);
                                                                                    }
                                                                                    
                                                                                    %>
                                                                                    <%= ControlCombo.draw(frmJobDesc.fieldNames[FrmJobDesc.FRM_FIELD_REPEAT_TYPE], "formElemen", null, ""+jobDesc.getRepeatEvery(), rep_value, rep_key,"")%>
                                                                                </td>
                                                                            </tr>
                                                                            <tr align="left" valign="top">
                                                                                <td valign="top" width="17%">
                                                                                    Start Date</td>
                                                                                <td width="83%">
                                                                                    <% 
                                                                                            //String selectDateStart = "" + selectedDateFrom; 
                                                                                       //String selectDateFinish 
                                                                                    Date st = new Date();
                                                                                    st.setHours(0);
                                                                                    st.setMinutes(0);
                                                                                    st.setSeconds(0);
                                                                                    Date end = new Date();
                                                                                    end.setHours(23);
                                                                                    end.setMinutes(59);
                                                                                    end.setSeconds(59);
                                                                                     String ctrTimeStart = ControlDate.drawTime(frmJobDesc.fieldNames[FrmJobDesc.FRM_FIELD_START_DATETIME],  jobDesc.getStartDatetime() != null ? jobDesc.getStartDatetime() : st, "elemenForm", 24,0, 0); 
                                                                                     String ctrTimeEnd = ControlDate.drawTime(frmJobDesc.fieldNames[FrmJobDesc.FRM_FIELD_END_DATETIME],  jobDesc.getEndDatetime() != null ?  iCommand==Command.NONE ? end : jobDesc.getEndDatetime() : end, "elemenForm", 24,0, 0); 
                                                                                    %>
                                                                                    <%=ControlDate.drawDateWithStyle(frmJobDesc.fieldNames[FrmJobDesc.FRM_FIELD_START_DATETIME], jobDesc.getStartDatetime() != null ? jobDesc.getStartDatetime() : new Date(), 2, -5, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"") + ctrTimeStart%>
                                                                                    <!--%=ControlDate.drawDateWithStyle(frmJobDesc.fieldNames[FrmJobDesc.FRM_FIELD_START_DATETIME], jobDesc.getStartDatetime() == null ? new Date() : jobDesc.getStartDatetime(), 0, -150, "formElemen")%-->
                                                                                </td>
                                                                            </tr>
                                                                            <tr align="left" valign="top">
                                                                                <td valign="top" width="17%">
                                                                                    End Date</td>
                                                                                <td width="83%">
                                                                                    <%=ControlDate.drawDateWithStyle(frmJobDesc.fieldNames[FrmJobDesc.FRM_FIELD_END_DATETIME], jobDesc.getEndDatetime() != null ? jobDesc.getEndDatetime() : new Date(), 2, -5, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"") + ctrTimeStart%>
                                                                                   <!--%=ControlDate.drawDateWithStyle(frmJobDesc.fieldNames[FrmJobDesc.FRM_FIELD_END_DATETIME], jobDesc.getEndDatetime() == null ? new Date() : jobDesc.getEndDatetime(), 0, -150, "formElemen")%-->
                                                                                </td>
                                                                            </tr>
                                                                            <tr align="left" valign="top">
                                                                                <td valign="top" width="17%">
                                                                                    Repeat Every</td>
                                                                                <td width="83%">
                                                                                    <input type="text" name="<%=frmJobDesc.fieldNames[FrmJobDesc.FRM_FIELD_REPEAT_EVERY]%>"  value="<%= jobDesc.getRepeatEvery()%>" class="elemenForm" size="30">
                                                                                </td>
                                                                            </tr>
                                                                            <tr align="left" valign="top">
                                                                                <td valign="top" width="17%">
                                                                                    Repeat Until Date</td>
                                                                                <td width="83%">
                                                                                    <%=ControlDate.drawDateWithStyle(frmJobDesc.fieldNames[FrmJobDesc.FRM_FIELD_REPEAT_UNTIL_DATE], jobDesc.getRepeatUntilDate() == null ? new Date() : jobDesc.getRepeatUntilDate(), 0, -150, "formElemen")%>
                                                                                </td>
                                                                            </tr>
                                                                            <tr align="left" valign="top">
                                                                                <td valign="top" width="17%">
                                                                                    Repeat Until Qty</td>
                                                                                <td width="83%">
                                                                                    <input type="text" name="<%=frmJobDesc.fieldNames[FrmJobDesc.FRM_FIELD_REPEAT_UNTIL_QTY]%>"  value="<%= jobDesc.getRepeatUntilQty()%>" class="elemenForm" size="30">
                                                                                </td>
                                                                            </tr>
                                                                            <tr align="left" valign="top">
                                                                                <td valign="top" width="17%">
                                                                                    Repeat Week Days</td>
                                                                                <td width="83%">
                                                                                    <%
                                                                                    Vector repweek_value = new Vector(1, 1);
                                                                                    Vector repweek_key = new Vector(1, 1);
                                                                                    repweek_value.add(""+0);
                                                                                    repweek_key.add("select");
                                                                                    for(int i=0;i<PstJobDesc.repeatWeekDayKey.length;i++){
                                                                                        repweek_key.add(""+PstJobDesc.repeatWeekDayKey[i]);
                                                                                        repweek_value.add(""+PstJobDesc.repeatWeekDayValue[i]);
                                                                                    }
                                                                                    
                                                                                    %>
                                                                                    <%= ControlCombo.draw(frmJobDesc.fieldNames[FrmJobDesc.FRM_FIELD_REPEAT_WEEK_DAYS], "formElemen", null, ""+jobDesc.getRepeatWeekDays(), repweek_value, repweek_key,"")%>
                                                                                </td>
                                                                            </tr>
                                                                            <tr align="left" valign="top">
                                                                                <td valign="top" width="17%">
                                                                                    Repeat Month Date</td>
                                                                                <td width="83%">
                                                                                    <input type="text" name="<%=frmJobDesc.fieldNames[FrmJobDesc.FRM_FIELD_REPEAT_MONTH_DATE]%>"  value="<%= jobDesc.getRepeatMonthDate()%>" class="elemenForm" size="30">
                                                                                </td>
                                                                            </tr>
                                                                            <tr align="left" valign="top">
                                                                                <td valign="top" width="17%">
                                                                                    Repeat Month Day</td>
                                                                                <td width="83%">
                                                                                    <input type="text" name="<%=frmJobDesc.fieldNames[FrmJobDesc.FRM_FIELD_REPEAT_MONTH_DAY]%>"  value="<%= jobDesc.getRepeatMonthDay()%>" class="elemenForm" size="30">
                                                                                </td>
                                                                            </tr>
                                                                            <tr align="left" valign="top">
                                                                                <td valign="top" width="17%">
                                                                                    Reminder Minutes Before</td>
                                                                                <td width="83%">
                                                                                    <input type="text" name="<%=frmJobDesc.fieldNames[FrmJobDesc.FRM_FIELD_REMINDER_MINUTES_BEFORE]%>"  value="<%= jobDesc.getReminderMinutesBefore()%>" class="elemenForm" size="30">
                                                                                </td>
                                                                            </tr>
                                                                            <tr align="left" valign="top">
                                                                                <td valign="top" width="17%">
                                                                                    Reminder Minutes Before</td>
                                                                                <td width="83%">
                                                                                    <input type="text" name="<%=frmJobDesc.fieldNames[FrmJobDesc.FRM_FIELD_REMINDER_MINUTES_BEFORE]%>"  value="<%= jobDesc.getReminderMinutesBefore()%>" class="elemenForm" size="30">
                                                                                </td>
                                                                            </tr>
                                                                            <tr align="left" valign="top">
                                                                                <td valign="top" width="17%">
                                                                                    Notification</td>
                                                                                <td width="83%">
                                                                                    <%
                                                                                    Vector notif_value = new Vector(1, 1);
                                                                                    Vector notif_key = new Vector(1, 1);
                                                                                    for(int i=0;i<PstJobDesc.notifKey.length;i++){
                                                                                        notif_key.add(""+PstJobDesc.notifKey[i]);
                                                                                        notif_value.add(""+PstJobDesc.notifValue[i]);
                                                                                    }
                                                                                    %>
                                                                                    <%= ControlCombo.draw(frmJobDesc.fieldNames[FrmJobDesc.FRM_FIELD_NOTIFICATION], "formElemen", null, ""+jobDesc.getNotification(), notif_value, notif_key,"")%>
                                                                                </td>
                                                                            </tr>
                                                                            <tr align="left" valign="top">
                                                                                <td valign="top" width="17%">
                                                                                    Checker Position</td>
                                                                                <td width="83%">
                                                                                    <%
                                                                                    Vector listPosition = new Vector(1, 1);
                                                                                    if(oidCheckPos != 0 && oidPosition != 0){                                                                                    
                                                                                                                                                                        
                                                                                    listPosition = PstPosition.list(start, recordToGet, "POSITION_ID="+oidCheckPos, orderClause);
                                                                                    for(int cp = 0; cp < listPosition.size();cp++){
                                                                                        Position check_pos = (Position)listPosition.get(cp);
                                                                                    %>
                                                                                    <input type="hidden" name="<%=frmJobDesc.fieldNames[FrmJobDesc.FRM_FIELD_CHECKER_POSITION_ID]%>"  value="<%= oidCheckPos%>" class="elemenForm" size="30">
                                                                                    <input type="text" name="checker_name"  value="<%= check_pos.getPosition()%>" class="elemenForm" size="30">
                                                                                    &nbsp;<button id="btn" onclick="srcChecker()">cari</button>
                                                                                    <%}
                                                                                    } else {%>
                                                                                    <input type="text" name="checker_name"  value="" class="elemenForm" size="30">
                                                                                    &nbsp;<button id="btn" onclick="srcChecker()">cari</button>
                                                                                    <%}%>
                                                                                </td>
                                                                            </tr>
                                                                            <tr align="left" valign="top">
                                                                                <td valign="top" width="17%">
                                                                                    Approver Position</td>
                                                                                <td width="83%">
                                                                                    <%
                                                                                    Vector listPositionAp = new Vector(1, 1);
                                                                                    if(oidAppPos != 0 && oidPosition != 0){                                                                                    
                                                                                                                                                                        
                                                                                    listPositionAp = PstPosition.list(start, recordToGet, "POSITION_ID="+oidAppPos, orderClause);
                                                                                    for(int ap = 0; ap < listPositionAp.size();ap++){
                                                                                        Position app_pos = (Position)listPositionAp.get(ap);
                                                                                    %>
                                                                                    <input type="hidden" name="<%=frmJobDesc.fieldNames[FrmJobDesc.FRM_FIELD_APPROVER_POSITION_ID]%>"  value="<%= oidAppPos%>" class="elemenForm" size="30">
                                                                                    <input type="text" name="app_name"  value="<%= app_pos.getPosition()%>" class="elemenForm" size="30">
                                                                                    &nbsp;<button id="btn" onclick="srcApproved()">cari</button>
                                                                                    <%}
                                                                                    } else {%>
                                                                                    <input type="text" name="app_name"  value="" class="elemenForm" size="30">
                                                                                    &nbsp;<button id="btn" onclick="srcApproved()">cari</button>
                                                                                    <%}%>
                                                                                    
                                                                                </td>
                                                                            </tr>
                                                                        </table >
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td colspan="2">
                                                                        <%
                                                                            ctrLine.setLocationImg(approot + "/images");
                                                                            ctrLine.initDefault();
                                                                            ctrLine.setTableWidth("80%");
                                                                            String scomDel = "javascript:cmdAsk('" + oidJobDesc + "')";
                                                                            String sconDelCom = "javascript:cmdConfirmDelete('" + oidJobDesc + "')";
                                                                            String scancel = "javascript:cmdEdit('" + oidJobDesc + "')";
                                                                            ctrLine.setBackCaption("Back to List");
                                                                            ctrLine.setCommandStyle("buttonlink");
                                                                            ctrLine.setBackCaption("Back to List");
                                                                            ctrLine.setSaveCaption("Save");
                                                                            ctrLine.setConfirmDelCaption("Yes Delete");
                                                                            ctrLine.setDeleteCaption("Delete");

                                                                            if (privDelete) {
                                                                                ctrLine.setConfirmDelCommand(sconDelCom);
                                                                                ctrLine.setDeleteCommand(scomDel);
                                                                                ctrLine.setEditCommand(scancel);
                                                                            } else {
                                                                                ctrLine.setConfirmDelCaption("");
                                                                                ctrLine.setDeleteCaption("");
                                                                                ctrLine.setEditCaption("");
                                                                            }

                                                                            if (privAdd == false && privUpdate == false) {
                                                                                ctrLine.setSaveCaption("");
                                                                            }

                                                                            if (privAdd == false) {
                                                                                ctrLine.setAddCaption("");
                                                                            }

                                                                            if (iCommand == Command.ASK) {
                                                                                ctrLine.setDeleteQuestion(msgString);
                                                                            }
                                                                        %>
                                                                        <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                            <%}%>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </form>
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
    </body>
</html>
