<%-- 
    Document   : list_train_venue
    Created on : Jan 14, 2009, 11:30:08 AM
    Author     : bayu
--%>


<%@ page language = "java" %>

<%-- package java --%>
<%@ page import = "java.util.*" %>

<%-- package dimata --%>
<%@ page import = "com.dimata.util.*" %>
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>

<%-- package harisma --%>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>

<%-- PRIV NOT SET --%>
<%--@ include file = "../main/javainit.jsp" %>
<%  int  appObjCode = 0; %>
<%@ include file = "../main/checkuser.jsp" %>
<%
    privAdd = true;
    privUpdate = true;
    privDelete = true;
--%>

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

<%-- JSP Block --%>
<%!
    public String drawList(Vector objectClass, long trainVenueId, int number) {        
        ControlList ctrlist = new ControlList();

        ctrlist.setAreaWidth("70%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");

        ctrlist.addHeader("No","5%");
        ctrlist.addHeader("Venue Name","20%");
        ctrlist.addHeader("Description","45%");

        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();

        ctrlist.setLinkRow(1);
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();

        int highlight = -1;
        
        for(int i = 0; i < objectClass.size(); i++) {
            Vector rowx = new Vector();
            TrainVenue trainVenue = (TrainVenue)objectClass.get(i);

            if(trainVenueId == trainVenue.getOID())
                 highlight = i;

            rowx.add(String.valueOf(number++));
            rowx.add(trainVenue.getVenueName());
            rowx.add(trainVenue.getVenueDesc());

            lstData.add(rowx);
            lstLinkData.add(String.valueOf(trainVenue.getOID()));
        }

        return ctrlist.draw(highlight);
    } 
%>

<%
    int iCommand = FRMQueryString.requestCommand(request);
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    int start = FRMQueryString.requestInt(request, "start");
    long oidVenue = FRMQueryString.requestLong(request, "train_venue_id");

    String whereClause = "";
    String orderClause = PstTrainVenue.fieldNames[PstTrainVenue.FLD_TRAIN_VENUE_NAME];    
    int recordToGet = 10;  
    
    ControlLine ctrLine = new ControlLine();
    Vector listTrainVenue = new Vector(1,1);
    
    CtrlTrainVenue ctrlTrainVenue = new CtrlTrainVenue(request);
    int iErrCode = FRMMessage.NONE;
    String msgString = "";
        
    
    iErrCode = ctrlTrainVenue.action(iCommand, oidVenue);
    
    FrmTrainVenue frmTrainVenue = ctrlTrainVenue.getForm();
    TrainVenue trainVenue = ctrlTrainVenue.getVenue();
    msgString = ctrlTrainVenue.getMessage();

    int vectSize = PstTrainVenue.getCount(whereClause);    

    if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
        oidVenue = trainVenue.getOID();
        start = PstTrainVenue.findLimitStart(oidVenue, recordToGet, whereClause, orderClause);
    }

    if((iCommand == Command.FIRST || iCommand == Command.PREV )||
       (iCommand == Command.NEXT || iCommand == Command.LAST)) {
        start = ctrlTrainVenue.actionList(iCommand, start, vectSize, recordToGet);
    } 

    listTrainVenue = PstTrainVenue.list(start, recordToGet, whereClause, orderClause);

    /* handle condition if size of record to display = 0 and start > 0 (after delete) */
    if (listTrainVenue.size() == 0 && start > 0) {
       
         if(vectSize - recordToGet >= recordToGet) {
             start = start - recordToGet;   
             iCommand = Command.PREV;
             prevCommand = Command.PREV;   // go to prev
         }
         else {
             start = 0 ;
             iCommand = Command.FIRST;
             prevCommand = Command.FIRST;   // go to first
         }
       
         listTrainVenue = PstTrainVenue.list(start, recordToGet, whereClause , orderClause);
    }
   
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Master Data Training Venue</title>
<script language="JavaScript">

    function cmdAdd(){
        document.frmtrainvenue.train_venue_id.value="0";
        document.frmtrainvenue.command.value="<%= Command.ADD %>";
        document.frmtrainvenue.prev_command.value="<%= prevCommand %>";
        document.frmtrainvenue.action="list_train_venue.jsp";
        document.frmtrainvenue.submit();
    }

    function cmdAsk(oidVenue){
        document.frmtrainvenue.train_venue_id.value=oidVenue;
        document.frmtrainvenue.command.value="<%= Command.ASK %>";
        document.frmtrainvenue.prev_command.value="<%= prevCommand %>";
        document.frmtrainvenue.action="list_train_venue.jsp";
        document.frmtrainvenue.submit();
    }

    function cmdConfirmDelete(oidVenue){
        document.frmtrainvenue.train_venue_id.value=oidVenue;
        document.frmtrainvenue.command.value="<%= Command.DELETE %>";
        document.frmtrainvenue.prev_command.value="<%= prevCommand %>";
        document.frmtrainvenue.action="list_train_venue.jsp";
        document.frmtrainvenue.submit();
    }
    
    function cmdSave(){
        document.frmtrainvenue.command.value="<%= Command.SAVE %>";
        document.frmtrainvenue.prev_command.value="<%= prevCommand %>";
        document.frmtrainvenue.action="list_train_venue.jsp";
        document.frmtrainvenue.submit();
    }

    function cmdEdit(oidVenue){
        document.frmtrainvenue.train_venue_id.value=oidVenue;
        document.frmtrainvenue.command.value="<%= Command.EDIT %>";
        document.frmtrainvenue.prev_command.value="<%= prevCommand %>";
        document.frmtrainvenue.action="list_train_venue.jsp";
        document.frmtrainvenue.submit();
    }

    function cmdCancel(oidVenue){
        cmdEdit(oidVenue);
    }

    function cmdBack(){
        document.frmtrainvenue.command.value="<%= Command.BACK %>";
        document.frmtrainvenue.action="list_train_venue.jsp";
        document.frmtrainvenue.submit();
    }


    function cmdListFirst(){
	document.frmtrainvenue.command.value="<%= Command.FIRST %>";
	document.frmtrainvenue.prev_command.value="<%= Command.FIRST %>";
	document.frmtrainvenue.action="list_train_venue.jsp";
	document.frmtrainvenue.submit();
    }

    function cmdListPrev(){
	document.frmtrainvenue.command.value="<%= Command.PREV %>";
	document.frmtrainvenue.prev_command.value="<%= Command.PREV %>";
	document.frmtrainvenue.action="list_train_venue.jsp";
	document.frmtrainvenue.submit();
    }

    function cmdListNext(){
	document.frmtrainvenue.command.value="<%= Command.NEXT %>";
	document.frmtrainvenue.prev_command.value="<%= Command.NEXT %>";
	document.frmtrainvenue.action="list_train_venue.jsp";
	document.frmtrainvenue.submit();
    }

    function cmdListLast(){
	document.frmtrainvenue.command.value="<%= Command.LAST %>";
	document.frmtrainvenue.prev_command.value="<%= Command.LAST %>";
	document.frmtrainvenue.action="list_train_venue.jsp";
	document.frmtrainvenue.submit();
    }

    function fnTrapKD(){
	switch(event.keyCode) {
            case <%= LIST_PREV %>:
                cmdListPrev();
                break;
                
            case <%= LIST_NEXT %>:
                cmdListNext();
                break;
                
            case <%= LIST_FIRST %>:
                cmdListFirst();
                break;
                
            case <%= LIST_LAST %>:
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
<!-- #EndEditable -->
</head> 

<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
    <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
<tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
    <!-- #BeginEditable "header" --> 
    <%@ include file = "../main/header.jsp" %>
    <!-- #EndEditable --> 
    </td>
</tr> 
<tr> 
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> 
    <!-- #BeginEditable "menumain" --> 
    <%@ include file = "../main/mnmain.jsp" %>
    <!-- #EndEditable --> 
    </td> 
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
            <!-- #BeginEditable "contenttitle" --> 
            Master Data &gt; Training Venue
            <!-- #EndEditable --> 
            </strong></font>
            </td>
        </tr>
        <tr> 
            <td>
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
                        <form name="frmtrainvenue" method ="post" action="">
                            <input type="hidden" name="command" value="<%= iCommand %>">
                            <input type="hidden" name="prev_command" value="<%= prevCommand %>">
                            <input type="hidden" name="start" value="<%= start %>">                            
                            <input type="hidden" name="train_venue_id" value="<%= oidVenue %>">
                            
                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr align="left" valign="top"> 
                                <td height="8"  colspan="3"> 
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr align="left" valign="top"> 
                                    <td height="14" valign="middle" colspan="3" class="listtitle">
                                        &nbsp;Training Venue List 
                                    </td>
                                </tr>
                                <%
                                    if (listTrainVenue.size()>0) {
                                %>
                                    <tr align="left" valign="top"> 
                                        <td height="22" valign="middle" colspan="3"> 
                                            <%= drawList(listTrainVenue,oidVenue, start + 1)%> 
                                        </td>
                                    </tr>
                                <%   }  
                                     else {
                                %>
                                    <tr align="left" valign="top"> 
                                        <td height="22" valign="middle" colspan="3"> 
                                            <p>&nbsp;&nbsp; No training venue data found!</p> 
                                        </td>
                                    </tr>
                                <%   } %>
                                <tr align="left" valign="top"> 
                                    <td height="8" align="left" colspan="3" class="command"> 
                                    <span class="command"> 
                                    <% 
                                        int cmd = 0;
                                        
                                        if ((iCommand == Command.FIRST || iCommand == Command.PREV )|| 
                                            (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                                            
                                            cmd =iCommand; 
                                        }
                                        else {
                                            if(iCommand == Command.NONE || prevCommand == Command.NONE) {
                                                cmd = Command.FIRST;
                                            }
                                            else {
                                                if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE))
                                                    cmd = PstDepartment.findLimitCommand(start,recordToGet,vectSize);
                                                else									 
                                                    cmd =prevCommand;
                                            }  
                                        } 
                                      %>
                                      <% ctrLine.setLocationImg(approot+"/images");
                                         ctrLine.initDefault();
                                      %>
                                      <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> 
                                      </span>
                                      </td>
                                </tr>
                                <%        
                                    if((iCommand != Command.ADD && iCommand != Command.ASK && 
                                        iCommand != Command.EDIT)&& (frmTrainVenue.errorSize()<1)) {
                                        
                                        if(privAdd){ %>
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
                                                        <a href="javascript:cmdAdd()" class="command">Add New Venue</a> 
                                                    </td>
                                                </tr>
                                                </table>
                                                </td>
                                            </tr>
                                          <% } %>
                                  <%  } %>
                                </table>
                                </td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                            </tr>
                            <tr align="left" valign="top"> 
                                <td height="8" valign="middle" colspan="3"> 
                                <%
                                    if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&
                                       (frmTrainVenue.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){ %>
                                       
                                        <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                        <tr> 
                                            <td colspan="2" class="listtitle">
                                                <%=oidVenue==0 ? "Add" : "Edit" %> Training Venue
                                            </td>
                                        </tr>
                                        <tr> 
                                            <td height="100%" colspan="2" > 
                                            <table border="0" cellspacing="2" cellpadding="2" width="50%">
                                            <tr align="left" valign="top"> 
                                                <td valign="top" width="19%">&nbsp;</td>
                                                <td width="81%" class="comment">*)entry required </td>
                                            </tr>
                                            <tr align="left" valign="top"> 
                                                <td valign="top" width="19%">Training Venue</td>
                                                <td width="81%"> 
                                                    <input type="text" name="<%= frmTrainVenue.fieldNames[FrmTrainVenue.FRM_FIELD_TRAIN_VENUE_NAME] %>"  value="<%= trainVenue.getVenueName() %>" class="elemenForm" size="30">
                                                    * <%=frmTrainVenue.getErrorMsg(FrmTrainVenue.FRM_FIELD_TRAIN_VENUE_NAME)%>
                                                </td>
                                            </tr>
                                            <tr align="left" valign="top"> 
                                                <td valign="top" width="19%">Description</td>
                                                <td width="81%"> 
                                                    <textarea name="<%= frmTrainVenue.fieldNames[FrmTrainVenue.FRM_FIELD_TRAIN_VENUE_DESC] %>" class="elemenForm" cols="30" rows="3"><%= trainVenue.getVenueDesc() %></textarea>
                                                </td>
                                            </tr>
                                            </table>
                                            </td>
                                        </tr>
                                        <tr align="left" valign="top" > 
                                            <td colspan="2" class="command"> 
                                            <%
                                                ctrLine.setLocationImg(approot+"/images");
                                                ctrLine.initDefault();
                                                ctrLine.setTableWidth("80%");
                                                
                                                String scomDel = "javascript:cmdAsk('"+oidVenue+"')";
                                                String sconDelCom = "javascript:cmdConfirmDelete('"+oidVenue+"')";
                                                String scancel = "javascript:cmdEdit('"+oidVenue+"')";
                                                
                                                ctrLine.setBackCaption("Back to List Venue");
                                                ctrLine.setCommandStyle("buttonlink");
                                                ctrLine.setAddCaption("Add Venue");
                                                ctrLine.setSaveCaption("Save Venue");
                                                ctrLine.setDeleteCaption("Delete Venue");
                                                ctrLine.setConfirmDelCaption("Yes Delete Venue");

                                                if (privDelete){
                                                    ctrLine.setConfirmDelCommand(sconDelCom);
                                                    ctrLine.setDeleteCommand(scomDel);
                                                    ctrLine.setEditCommand(scancel);
                                                }
                                                else{ 
                                                    ctrLine.setConfirmDelCaption("");
                                                    ctrLine.setDeleteCaption("");
                                                    ctrLine.setEditCaption("");
                                                }

                                                if(privAdd == false  && privUpdate == false)
                                                    ctrLine.setSaveCaption("");
                                                

                                                if (privAdd == false)
                                                    ctrLine.setAddCaption("");                                                

                                                if(iCommand == Command.ASK)
                                                    ctrLine.setDeleteQuestion(msgString);

                                              %>
                                              <%= ctrLine.drawImage(iCommand, iErrCode, msgString) %> 
                                           </td>
                                        </tr>
                                        <tr> 
                                            <td width="39%">&nbsp;</td>
                                            <td width="61%">&nbsp;</td>
                                        </tr>
                                        <tr align="left" valign="top" > 
                                            <td colspan="3"><div align="left"></div></td>
                                        </tr>
                                        </table>
                                <%  } %>
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
<!-- #EndEditable -->
<!-- #EndTemplate -->
</html>
