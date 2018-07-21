<%-- 
    Document   : list_train_type
    Created on : Jan 14, 2009, 11:20:10 AM
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

<%@ include file = "../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_TRAINING, AppObjInfo.G2_TRAINING_TYPE, AppObjInfo.OBJ_MENU_TRAINING_TYPE);%>
<%@ include file = "../main/checkuser.jsp" %>

<%-- JSP Block --%>
<%!
    public String drawList(Vector objectClass, long trainTypeId, int number, boolean privUpdate) {        
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("tblStyle");
        ctrlist.setTitleStyle("title_tbl");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("title_tbl");

        ctrlist.addHeader("No","");
        ctrlist.addHeader("Training Type","");
        ctrlist.addHeader("Description","");
        ctrlist.addHeader("Code","");

        Vector lstData = ctrlist.getData();
        ctrlist.reset();

        int highlight = -1;
        
        for(int i = 0; i < objectClass.size(); i++) {
            Vector rowx = new Vector();
            TrainType trainType = (TrainType)objectClass.get(i);

            if(trainTypeId == trainType.getOID())
                 highlight = i;

            rowx.add(String.valueOf(number++));
            if (privUpdate){
                rowx.add("<a href=\"javascript:cmdEdit('"+trainType.getOID()+"')\">"+trainType.getTypeName()+"</a>");
            } else {
                rowx.add(trainType.getTypeName());
            }
            
            rowx.add(trainType.getTypeDesc());
            rowx.add(trainType.getTypeCode());
            
            lstData.add(rowx);
        }

        return ctrlist.draw(highlight);
    } 
%>

<%
    int iCommand = FRMQueryString.requestCommand(request);
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    int start = FRMQueryString.requestInt(request, "start");
    long oidTrainType = FRMQueryString.requestLong(request, "train_type_id");

    String whereClause = "";
    String orderClause = PstTrainType.fieldNames[PstTrainType.FLD_TRAIN_TYPE_NAME];    
    int recordToGet = 10;  
    
    ControlLine ctrLine = new ControlLine();
    Vector listTrainType = new Vector(1,1);
    
    CtrlTrainType ctrlTrainType = new CtrlTrainType(request);
    int iErrCode = FRMMessage.NONE;
    String msgString = "";
        
    
    iErrCode = ctrlTrainType.action(iCommand, oidTrainType);
    
    FrmTrainType frmTrainType = ctrlTrainType.getForm();
    TrainType train = ctrlTrainType.getTrainType();
    msgString = ctrlTrainType.getMessage();

    int vectSize = PstTrainType.getCount(whereClause);    

    if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
        oidTrainType = train.getOID();
        start = PstTrainType.findLimitStart(oidTrainType, recordToGet, whereClause, orderClause);
    }

    if((iCommand == Command.FIRST || iCommand == Command.PREV )||
       (iCommand == Command.NEXT || iCommand == Command.LAST)) {
        start = ctrlTrainType.actionList(iCommand, start, vectSize, recordToGet);
    } 

    listTrainType = PstTrainType.list(start, recordToGet, whereClause, orderClause);

    /* handle condition if size of record to display = 0 and start > 0 (after delete) */
    if (listTrainType.size() == 0 && start > 0) {
       
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
       
         listTrainType = PstTrainType.list(start, recordToGet, whereClause , orderClause);
    }
   
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Master Data Training Type</title>
<script language="JavaScript">

    function cmdAdd(){
        document.frmtraintype.train_type_id.value="0";
        document.frmtraintype.command.value="<%= Command.ADD %>";
        document.frmtraintype.prev_command.value="<%= prevCommand %>";
        document.frmtraintype.action="list_train_type.jsp";
        document.frmtraintype.submit();
    }

    function cmdAsk(oidTrainType){
        document.frmtraintype.train_type_id.value=oidTrainType;
        document.frmtraintype.command.value="<%= Command.ASK %>";
        document.frmtraintype.prev_command.value="<%= prevCommand %>";
        document.frmtraintype.action="list_train_type.jsp";
        document.frmtraintype.submit();
    }

    function cmdConfirmDelete(oidTrainType){
        document.frmtraintype.train_type_id.value=oidTrainType;
        document.frmtraintype.command.value="<%= Command.DELETE %>";
        document.frmtraintype.prev_command.value="<%= prevCommand %>";
        document.frmtraintype.action="list_train_type.jsp";
        document.frmtraintype.submit();
    }
    
    function cmdSave(){
        document.frmtraintype.command.value="<%= Command.SAVE %>";
        document.frmtraintype.prev_command.value="<%= prevCommand %>";
        document.frmtraintype.action="list_train_type.jsp";
        document.frmtraintype.submit();
    }

    function cmdEdit(oidTrainType){
        document.frmtraintype.train_type_id.value=oidTrainType;
        document.frmtraintype.command.value="<%= Command.EDIT %>";
        document.frmtraintype.prev_command.value="<%= prevCommand %>";
        document.frmtraintype.action="list_train_type.jsp";
        document.frmtraintype.submit();
    }

    function cmdCancel(oidTrainType){
        cmdEdit(oidTrainType);
    }

    function cmdBack(){
        document.frmtraintype.command.value="<%= Command.BACK %>";
        document.frmtraintype.action="list_train_type.jsp";
        document.frmtraintype.submit();
    }


    function cmdListFirst(){
	document.frmtraintype.command.value="<%= Command.FIRST %>";
	document.frmtraintype.prev_command.value="<%= Command.FIRST %>";
	document.frmtraintype.action="list_train_type.jsp";
	document.frmtraintype.submit();
    }

    function cmdListPrev(){
	document.frmtraintype.command.value="<%= Command.PREV %>";
	document.frmtraintype.prev_command.value="<%= Command.PREV %>";
	document.frmtraintype.action="list_train_type.jsp";
	document.frmtraintype.submit();
    }

    function cmdListNext(){
	document.frmtraintype.command.value="<%= Command.NEXT %>";
	document.frmtraintype.prev_command.value="<%= Command.NEXT %>";
	document.frmtraintype.action="list_train_type.jsp";
	document.frmtraintype.submit();
    }

    function cmdListLast(){
	document.frmtraintype.command.value="<%= Command.LAST %>";
	document.frmtraintype.prev_command.value="<%= Command.LAST %>";
	document.frmtraintype.action="list_train_type.jsp";
	document.frmtraintype.submit();
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
<style type="text/css">
    .tblStyle {border-collapse: collapse;}
    .tblStyle td {padding: 3px 5px; border: 1px solid #CCC; font-size: 12px;}
    .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
</style>
<!-- #EndEditable -->
<!-- #BeginEditable "headerscript" --> 
<!-- #EndEditable -->
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
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
            Master Data &gt; Training Type
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
                        <form name="frmtraintype" method ="post" action="">
                            <input type="hidden" name="command" value="<%= iCommand %>">
                            <input type="hidden" name="prev_command" value="<%= prevCommand %>">
                            <input type="hidden" name="start" value="<%= start %>">                            
                            <input type="hidden" name="train_type_id" value="<%= oidTrainType %>">
                            
                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr align="left" valign="top"> 
                                <td height="8"  colspan="3"> 
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr align="left" valign="top"> 
                                    <td height="14" valign="middle" colspan="3" class="listtitle">
                                        &nbsp;Training Type List 
                                    </td>
                                </tr>
                                <%
                                    if (listTrainType.size()>0) {
                                %>
                                    <tr align="left" valign="top"> 
                                        <td height="22" valign="middle" colspan="3"> 
                                            <%= drawList(listTrainType,oidTrainType, start + 1, privUpdate)%> 
                                        </td>
                                    </tr>
                                <%   }  
                                     else {
                                %>
                                    <tr align="left" valign="top"> 
                                        <td height="22" valign="middle" colspan="3"> 
                                            <p>&nbsp;&nbsp; No training type data found!</p> 
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
                                        iCommand != Command.EDIT)&& (frmTrainType.errorSize()<1)) {
                                        
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
                                                        <a href="javascript:cmdAdd()" class="command">Add New Type</a> 
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
                                       (frmTrainType.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){ %>
                                       
                                        <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                        <tr> 
                                            <td colspan="2" class="listtitle">
                                                <%=oidTrainType==0 ? "Add" : "Edit" %> Training Type
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
                                                <td valign="top" width="19%">Training Type</td>
                                                <td width="81%"> 
                                                    <input type="text" name="<%= frmTrainType.fieldNames[FrmTrainType.FRM_FIELD_TRAIN_TYPE_NAME] %>"  value="<%= train.getTypeName() %>" class="elemenForm" size="30">
                                                    * <%=frmTrainType.getErrorMsg(FrmTrainType.FRM_FIELD_TRAIN_TYPE_NAME)%>
                                                </td>
                                            </tr>
                                            <tr align="left" valign="top"> 
                                                <td valign="top" width="19%">Description</td>
                                                <td width="81%"> 
                                                    <textarea name="<%= frmTrainType.fieldNames[FrmTrainType.FRM_FIELD_TRAIN_TYPE_DESC] %>" class="elemenForm" cols="30" rows="3"><%= train.getTypeDesc() %></textarea>
                                                </td>
                                            </tr>
                                            <tr align="left" valign="top"> 
                                                <td valign="top" width="19%">Code</td>
                                                <td width="81%"> 
                                                    <input type="text" name="<%= frmTrainType.fieldNames[FrmTrainType.FRM_FIELD_TRAIN_TYPE_CODE] %>"  value="<%= train.getTypeCode() %>" class="elemenForm" size="30">
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
                                                
                                                String scomDel = "javascript:cmdAsk('"+oidTrainType+"')";
                                                String sconDelCom = "javascript:cmdConfirmDelete('"+oidTrainType+"')";
                                                String scancel = "javascript:cmdEdit('"+oidTrainType+"')";
                                                
                                                ctrLine.setBackCaption("Back to List Type");
                                                ctrLine.setCommandStyle("buttonlink");
                                                ctrLine.setAddCaption("Add Type");
                                                ctrLine.setSaveCaption("Save Type");
                                                ctrLine.setDeleteCaption("Delete Type");
                                                ctrLine.setConfirmDelCaption("Yes Delete Type");

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