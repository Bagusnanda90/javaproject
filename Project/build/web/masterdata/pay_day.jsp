<%-- 
    Document   : pay_day.jsp
    Created on : Jun 11, 2014, 4:07:51 PM
    Author     : Satrya Ramayu
--%>

<%@page import="com.dimata.harisma.entity.masterdata.payday.PstPayDay"%>
<%@page import="com.dimata.harisma.form.masterdata.payday.CtrlPayDay"%>
<%@page import="com.dimata.harisma.form.masterdata.payday.FrmPayDay"%>
<%@page import="com.dimata.harisma.entity.masterdata.payday.PayDay"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@ include file = "../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_LEAVE_CONFIGURATION);%>
<%@ include file = "../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%!    public String drawList(int iCommand, int start, Vector objectClass,Vector vPosition,Vector vCategory,long oidSelect,FrmPayDay frmPayDay,long oidEmpCategoryDw,PayDay objPayDay) {
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("No", "1%");
        ctrlist.addHeader("Employee Category", "20%");
        ctrlist.addHeader("Position", "20%");
        ctrlist.addHeader("Value", "50%");


        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.reset();
        int index = -1;
        int noCounter = start + 1;
        Vector rowx = new Vector();
       
        String attTag =  "data-placeholder=\"Choose a Employee Category...\" onChange=\"javascript:cmdEmpCat()\"";
        Vector vKeyEmpCat = new Vector();
        Vector vValueEmpCat= new Vector();
        if(vCategory!=null && vCategory.size()>0){
            for(int i=0;i<vCategory.size();i++){
                EmpCategory empCategory = (EmpCategory)vCategory.get(i);
                vKeyEmpCat.add(empCategory.getEmpCategory());
                vValueEmpCat.add(""+empCategory.getOID());
            }
        }
        
        Vector vKeyPos = new Vector();
        Vector vValuePos= new Vector();
        if(vPosition!=null && vPosition.size()>0){
            for(int i=0;i<vPosition.size();i++){
                Position position = (Position)vPosition.get(i);
                vKeyPos.add(position.getPosition());
                vValuePos.add(""+position.getOID());
            }
        }
        Hashtable hashValueNamePosition = PstPosition.listt(0, 0, "", ""); 
    Hashtable hashValueCategory = PstEmpCategory.listt(0, 0, "", ""); 
         boolean adaKlikEdit = true;
        if (objectClass != null && objectClass.size() > 0) {
            for (int i = 0; i < objectClass.size(); i++) {
                PayDay payDay = (PayDay)objectClass.get(i);
                rowx = new Vector();
                rowx.add("" + noCounter);
                noCounter++;

                if (oidSelect == payDay.getOID()) {
                    index = i;
                }
                if (index == i && (iCommand == Command.GOTO || iCommand == Command.EDIT || iCommand == Command.ASK)) {
                    //jika dia ada Edit
                    adaKlikEdit = false;
                    if(iCommand != Command.GOTO){
                         if(oidEmpCategoryDw==payDay.getEmpCategoryId()){
                                rowx.add(ControlCombo.draw(FrmPayDay.fieldNames[FrmPayDay.FRM_EMP_CATEGORY_ID], "chosen-select", "-Selected-", ""+payDay.getEmpCategoryId(), vKeyEmpCat,vValueEmpCat,null, attTag));
                         rowx.add("-");
                         rowx.add("<input type=\"text\" name=\""+FrmPayDay.fieldNames[FrmPayDay.FRM_VALUE_PAY_DAY]+"\" value=\"" + payDay.getValuePayDay() + "\" size=\"20\" class=\"elemenForm\">");
                         }else{
                             rowx.add(ControlCombo.draw(FrmPayDay.fieldNames[FrmPayDay.FRM_EMP_CATEGORY_ID], "chosen-select", "-Selected-", ""+payDay.getEmpCategoryId(), vKeyEmpCat,vValueEmpCat,null, attTag));
                         rowx.add(ControlCombo.draw(FrmPayDay.fieldNames[FrmPayDay.FRM_POSITION_ID], "chosen-select", "-Selected-", ""+payDay.getPositionId(), vKeyPos,vValuePos,null,null));
                         rowx.add("<input type=\"text\" name=\""+FrmPayDay.fieldNames[FrmPayDay.FRM_VALUE_PAY_DAY]+"\" value=\"" + payDay.getValuePayDay() + "\" size=\"20\" class=\"elemenForm\">");
                         }
                         
                    }else{
                        if(objPayDay!=null && oidEmpCategoryDw==objPayDay.getEmpCategoryId()){
                         rowx.add(ControlCombo.draw(FrmPayDay.fieldNames[FrmPayDay.FRM_EMP_CATEGORY_ID], "chosen-select", "-Selected-", ""+objPayDay.getEmpCategoryId(), vKeyEmpCat,vValueEmpCat,null, attTag));
                         rowx.add("-");
                         rowx.add("<input type=\"text\" name=\""+FrmPayDay.fieldNames[FrmPayDay.FRM_VALUE_PAY_DAY]+"\" value=\"" + objPayDay.getValuePayDay() + "\" size=\"20\" class=\"elemenForm\">");
                        }else{
                         rowx.add(ControlCombo.draw(FrmPayDay.fieldNames[FrmPayDay.FRM_EMP_CATEGORY_ID], "chosen-select", "-Selected-", ""+objPayDay.getEmpCategoryId(), vKeyEmpCat,vValueEmpCat,null, attTag));
                         rowx.add(ControlCombo.draw(FrmPayDay.fieldNames[FrmPayDay.FRM_POSITION_ID], "chosen-select", "-Selected-", ""+objPayDay.getPositionId(), vKeyPos,vValuePos,null,null));
                         rowx.add("<input type=\"text\" name=\""+FrmPayDay.fieldNames[FrmPayDay.FRM_VALUE_PAY_DAY]+"\" value=\"" + objPayDay.getValuePayDay() + "\" size=\"20\" class=\"elemenForm\">");
                        }
                        
                    }
                    lstLinkData.add(String.valueOf(payDay.getOID()));  

                } else {
                    //jika sdh selesai save maka akan kesini
                    
                    rowx.add(""+(hashValueCategory!=null && hashValueCategory.size()>0 && hashValueCategory.containsKey(""+payDay.getEmpCategoryId())?(String)hashValueCategory.get(""+payDay.getEmpCategoryId()):"-"));
                    rowx.add(""+(hashValueNamePosition!=null && hashValueNamePosition.size()>0 && hashValueNamePosition.containsKey(""+payDay.getPositionId())?(String)hashValueNamePosition.get(""+payDay.getPositionId()):"-"));
                    rowx.add(""+Formater.formatNumber(payDay.getValuePayDay(), "#.##"));
                    lstLinkData.add(String.valueOf(payDay.getOID()));
                }
                lstData.add(rowx);
                //noCounter = (noCounter+1); 
            }
        }
        rowx = new Vector();

        if (adaKlikEdit &&   (iCommand == Command.GOTO ||   iCommand == Command.ADD || (iCommand == Command.SAVE && frmPayDay.errorSize() > 0) || (objectClass.size() < 1))) {
            rowx.add("" + noCounter);
            noCounter++;

            if(objPayDay.getEmpCategoryId()==oidEmpCategoryDw){
                rowx.add(ControlCombo.draw(FrmPayDay.fieldNames[FrmPayDay.FRM_EMP_CATEGORY_ID], "chosen-select", "-Selected-", ""+objPayDay.getEmpCategoryId(), vKeyEmpCat,vValueEmpCat,null, attTag));
                rowx.add("-"); 
                rowx.add("<input type=\"text\" name=\""+FrmPayDay.fieldNames[FrmPayDay.FRM_VALUE_PAY_DAY]+"\" value=\"" + objPayDay.getValuePayDay() + "\" size=\"20\" class=\"elemenForm\">");
            
            }else{
                rowx.add(ControlCombo.draw(FrmPayDay.fieldNames[FrmPayDay.FRM_EMP_CATEGORY_ID], "chosen-select", "-Selected-", ""+objPayDay.getEmpCategoryId(), vKeyEmpCat,vValueEmpCat,null, attTag));
                rowx.add(ControlCombo.draw(FrmPayDay.fieldNames[FrmPayDay.FRM_POSITION_ID], "chosen-select", "-Selected-", ""+objPayDay.getPositionId(), vKeyPos,vValuePos,null,null));
                rowx.add("<input type=\"text\" name=\""+FrmPayDay.fieldNames[FrmPayDay.FRM_VALUE_PAY_DAY]+"\" value=\"" + objPayDay.getValuePayDay() + "\" size=\"20\" class=\"elemenForm\">");
            }
            
            lstLinkData.add(String.valueOf(0));
            noCounter = (noCounter + 1);
        }
        lstData.add(rowx);
        return ctrlist.draw();
    }

%>

<%
    CtrlPayDay ctrlPayDay = new CtrlPayDay(request);
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    long oidPayDay = FRMQueryString.requestLong(request, FrmPayDay.fieldNames[FrmPayDay.FRM_PAY_DAY_ID]);
    int iErrCode = FRMMessage.ERR_NONE;
    String msgString = "";
    ControlLine ctrLine = new ControlLine();
    iErrCode = ctrlPayDay.action(iCommand,oidPayDay);
    msgString = ctrlPayDay.getMessage();
    FrmPayDay frmPayDay = ctrlPayDay.getForm();
    PayDay objPayDay = ctrlPayDay.getPayDay();
    
    
    long oidEmployeeDw = 11002;
    /*variable declaration*/
    int recordToGet = 5;
    String whereClause = "";

    String orderClause = "";
    Vector listPayDay = new Vector(1, 1);
    Vector vPosition = PstPosition.list(0, 0, "", "");
    Vector vEmpCategory = PstEmpCategory.list(0, 0, "", "");

    /* end switch*/

    /*count list All Position*/
    int vectSize = PstPayDay.getCount(whereClause);

    if ((iCommand == Command.FIRST || iCommand == Command.PREV)
            || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
        start = ctrlPayDay.actionList(iCommand, start, vectSize, recordToGet);
    }
    /* end switch list*/

    /* get record to display */
    listPayDay = PstPayDay.list(start, recordToGet, whereClause, orderClause);

    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listPayDay.size() < 1 && start > 0) {
        if (vectSize - recordToGet > recordToGet) {
            start = start - recordToGet;   //go to Command.PREV
        } else {
            start = 0;
            iCommand = Command.FIRST;
            prevCommand = Command.FIRST; //go to Command.FIRST
        }
        listPayDay = PstPayDay.list(start, recordToGet, whereClause, orderClause); 
    }
    
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
    <head>
        <script src="../javascripts/jquery.min-1.6.2.js" type="text/javascript"></script>
<script src="../javascripts/chosen.jquery.js" type="text/javascript"></script>
        <title>Harisma - Konfigurasi Leave</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
<link rel="stylesheet" href="../styles/main.css" type="text/css">
    <link rel="stylesheet" href="../styles/tab.css" type="text/css">
 <link rel="stylesheet" href="../stylesheets/chosen.css" >
        <SCRIPT language=JavaScript>
            
            function cmdEdit(oidPayDay){
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.<%=FrmPayDay.fieldNames[FrmPayDay.FRM_PAY_DAY_ID]%>.value=oidPayDay;
                
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.command.value="<%=Command.EDIT%>";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.prev_command.value="<%=prevCommand%>";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.action="pay_day.jsp";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.target = "";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.submit();
            }
            
            function cmdAdd(){
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.command.value="<%=Command.ADD%>";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.<%=FrmPayDay.fieldNames[FrmPayDay.FRM_PAY_DAY_ID]%>.value=0;
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.prev_command.value="<%=prevCommand%>";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.action="pay_day.jsp";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.target = "";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.submit();
            }
            
            
            function cmdSave(){
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.command.value="<%=Command.SAVE%>";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.prev_command.value="<%=prevCommand%>";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.action="pay_day.jsp";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.target = "";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.submit();
            }
            
            function cmdBack(){
               
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.command.value="<%=Command.BACK%>";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.action="pay_day.jsp";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.submit();
            }
            
            function cmdEmpCat(){
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.action="pay_day.jsp";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.target = "";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.submit();
            }
            
            
            function cmdListFirst(){
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.command.value="<%=Command.FIRST%>";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.prev_command.value="<%=Command.FIRST%>";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.action="pay_day.jsp";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.target = "";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.submit();
            }
            
            function cmdListFirstLeaveRequestOnly(){
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.command_req_only.value="<%=Command.FIRST%>";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.prev_command_req_only.value="<%=Command.FIRST%>";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.action="pay_day.jsp";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.target = "";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.submit();
            }
            
            function cmdListPrev(){
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.command.value="<%=Command.PREV%>";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.prev_command.value="<%=Command.PREV%>";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.action="pay_day.jsp";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.target = "";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.submit();
            }
            
            function cmdListNext(){
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.command.value="<%=Command.NEXT%>";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.prev_command.value="<%=Command.NEXT%>";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.action="pay_day.jsp";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.target = "";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.submit();
            }
            
            function cmdListLast(){
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.command.value="<%=Command.LAST%>";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.prev_command.value="<%=Command.LAST%>";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.action="pay_day.jsp";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.target = "";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.submit();
            }
            
            function cmdAsk(oid){
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.command.value="<%=String.valueOf(Command.ASK)%>";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.<%=FrmPayDay.fieldNames[FrmPayDay.FRM_PAY_DAY_ID]%>.value=oid;
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.prev_command.value="<%=String.valueOf(prevCommand)%>";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.action="pay_day.jsp";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.target = "";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.submit();
            }
            
            function cmdConfirmDelete(oid){
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.<%=FrmPayDay.fieldNames[FrmPayDay.FRM_PAY_DAY_ID]%>.value=oid;
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.command.value="<%=String.valueOf(Command.DELETE)%>";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.prev_command.value="<%=String.valueOf(prevCommand)%>";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.action="pay_day.jsp";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.target = "";
                document.<%=FrmPayDay.FRM_NAME_PAY_DAY%>.submit();
            }
            
               
                function fnTrapKD(){
                    //alert(event.keyCode);
                    switch(event.keyCode) {
                        case <%=String.valueOf(LIST_PREV)%>:
                                cmdListPrev();
                            break;
                        case <%=String.valueOf(LIST_NEXT)%>:
                                cmdListNext();
                            break;
                        case <%=String.valueOf(LIST_FIRST)%>:
                                cmdListFirst();
                            break;
                        case <%=String.valueOf(LIST_LAST)%>:
                                cmdListLast();
                            break;
                        default:
                            break;
                        }
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
    </head> 

    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../styletemplate/template_header.jsp" %>
            <%} else {%>
            <tr> 
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
                    <!-- #BeginEditable "header" --> 
                    <%@ include file = "../main/header.jsp" %>
                    <!-- #EndEditable --> 
                </td>
            </tr> 
            <tr> 
                <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --><!-- #EndEditable -->  <%@ include file = "../main/mnmain.jsp" %> </td> 
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
                                                Master Data &gt; Leave Configuration<!-- #EndEditable -->
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
                                                                                <!--- FORM PERTAMA-->

                                                                                <!--- FORM KEDUA-->
                                                                                <form name="<%=FrmPayDay.FRM_NAME_PAY_DAY%>" method="post" action="">
                                                                                    <input type="hidden" name="command" value="">
                                                                                     <input type="hidden" name="prev_command" value="">
                                                                                    <input type="hidden" name="start" value="<%=start%>">
                                                                                        <input type="hidden" name="<%=FrmPayDay.fieldNames[FrmPayDay.FRM_PAY_DAY_ID]%>" value="<%=objPayDay.getOID()%>">

                                                                                    <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                        <tr align="left" valign="top"> 
                                                                                            <td height="8"  colspan="3"> 
                                                                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                                    <tr>
                                                                                                        <td width="5">&nbsp;</td>
                                                                                                        <td>&nbsp;</td>
                                                                                                    </tr>
                                                                                                    <tr>
                                                                                                        <td width="100%">
                                                                                                            PayDay List
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                    
                                                                                                   
                                                                                                    <%
                                                                                                        try {
                                                                                                            //if (listPublicLeave!=null && listPublicLeave.size()>0){
%>
                                                                                                    <tr align="left" valign="top"> 
                                                                                                        <td height="22" valign="middle" colspan="3"> 
                                                                                                            <%=drawList(iCommand,start,listPayDay,vPosition,vEmpCategory,oidPayDay,frmPayDay,oidEmployeeDw,objPayDay)%></td> 
                                                                                                    </tr>
                                                                                                    <% // } 
                                                                                                        } catch (Exception exc) {
                                                                                                            System.out.println(exc);
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
                                                                                                                <%=ctrLine.drawImageListLimit(cmd, vectSize, start, recordToGet)%>                                                  </span> </td>
                                                                                                    </tr>
                                                                                                </table>
                                                                                            </td>
                                                                                            </>
                                                                                        <tr align="left" valign="top"> 
                                                                                            <td height="8" valign="middle" colspan="3"> 
                                                                                                <%if ((iCommand == Command.ADD) || (iCommand == Command.SAVE) && (msgString != null && msgString.length() > 0) || (iCommand == Command.EDIT) || (iCommand == Command.ASK)) {%>

                                                                                                <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                                                    <tr> 
                                                                                                        <td colspan="2"> 
                                                                                                            <%
                                                                                                                ctrLine.setLocationImg(approot + "/images");
                                                                                                                ctrLine.initDefault();
                                                                                                                ctrLine.setTableWidth("80%");
                                                                                                                String scomDel = "javascript:cmdAsk('" + oidPayDay + "')";
                                                                                                                String sconDelCom = "javascript:cmdConfirmDelete('" + oidPayDay + "')";
                                                                                                                String scancel = "javascript:cmdEdit('" + oidPayDay + "')";
                                                                                                                ctrLine.setBackCaption("Back to List Employee Approvall");
                                                                                                                ctrLine.setCommandStyle("buttonlink");
                                                                                                                ctrLine.setBackCaption("Back to List Pay Day ");
                                                                                                                ctrLine.setSaveCaption("Save New Pay Day");
                                                                                                                ctrLine.setConfirmDelCaption("Yes Delete Pay Day");
                                                                                                                ctrLine.setDeleteCaption("Delete Pay Day ");
                                                                                                                ctrLine.setAddCaption("Add New Pay Day");
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
                                                                                                                if (iCommand == Command.SAVE) {
                                                                                                                    ctrLine.setSaveCaption("");
                                                                                                                    ctrLine.setDeleteCaption("");
                                                                                                                    //ctrLine.setBackCaption("");
                                                                                                                }
                                                                                                            %>
                                                                                                            <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%> 
                                                                                                        </td>

                                                                                                    </tr>
                                                                                                </table>
                                                                                                <%}%>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <%if (iCommand == Command.BACK || (iCommand == Command.FIRST || iCommand == Command.PREV)
                                                    || (iCommand == Command.NEXT || iCommand == Command.LAST) || iCommand == Command.DELETE) {%>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <table width="100%">

                                                                                                    <tr>
                                                                                                        <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                        <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                                                                        <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                        <td height="22"> 
                                                                                                            <a href="javascript:cmdAdd()" class="command">Add New Employee Approvall</a> 
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                </table>
                                                                                            </td>

                                                                                        </tr>
                                                                                        <%}%>
                                                                                         <%if (iCommand == Command.NONE || iCommand == Command.GOTO) {%>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <%if(iCommand == Command.GOTO || listPayDay!=null && listPayDay.size()==0){%>
                                                                                                <table width="100%">
                                                                                                    <tr>
                                                                                                      <td width="3"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                      <td width="24"><a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Save new data"></a></td>
                                                                                                      <td width="5"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                      <td width="150" nowrap="nowrap"><a href="javascript:cmdSave()" class="command">Save New Pay Day</a> </td>
                                                                                                      <td width="24"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back List"></a></td>
                                                                                                      <td width="10"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                      <td width="910" height="22"><a href="javascript:cmdBack()" class="command">Back to List Pay Day</a> </td>
                                                                                                    </tr>
                                                                                                  </table>
                                                                                                 <%}else{%>
                                                                                                 <table width="100%">

                                                                                                    <tr>
                                                                                                        <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                        <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                                                                        <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                        <td height="22"> 
                                                                                                            <a href="javascript:cmdAdd()" class="command">Add New Pay Day</a> 
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                </table>
                                                                                                 <%}%>
                                                                                            </td>

                                                                                        </tr>
                                                                                        <%}%>
                                                                                    </table>
                                                                                    <!-- end list approvall -->
                                                                                     
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
                                </table>
                            </td> 
                        </tr>
                    </table>
                </td> 
            </tr>
        </table>
    </td> 
</tr>
<%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
<tr>
    <td valign="bottom">
        <!-- untuk footer -->
        <%//@include file="../footer.jsp" %>
    </td>

</tr>
<%} else {%>
<tr> 
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
        <%//@ include file = "../main/footer.jsp" %>
        <!-- #EndEditable --> </td>
</tr>
<%}%>
 <script type="text/javascript">
        var config = {
            '.chosen-select'           : {},
            '.chosen-select-deselect'  : {allow_single_deselect:true},
            '.chosen-select-no-single' : {disable_search_threshold:10},
            '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
            '.chosen-select-width'     : {width:"95%"}
        }
        for (var selector in config) {
            $(selector).chosen(config[selector]);
        }
</script>
<script type="text/javascript">
        var configs = {
            '.chosen-selects'           : {},
            '.chosen-select-deselect'  : {allow_single_deselect:true},
            '.chosen-selectno-single' : {disable_search_threshold:10},
            '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
            '.chosen-select-width'     : {width:"95%"}
        }
        for (var selectors in configs) {
            $(selectors).chosen(configs[selectors]);
        }
</script>
</table>
</body>
<!-- #BeginEditable "script" -->
<script language="JavaScript">
            //var oBody = document.body;
            //var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
</script>
<!-- #EndEditable -->
<!-- #EndTemplate --></html>

