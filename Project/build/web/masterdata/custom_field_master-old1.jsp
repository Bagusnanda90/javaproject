<%-- 
    Document   : custom_field_master
    Created on : Jun 10, 2015, 3:45:26 PM
    Author     : Hendra McHen
--%>

<%@page import="com.dimata.harisma.entity.masterdata.PstCustomFieldMaster"%>
<%@page import="com.dimata.harisma.entity.masterdata.CustomFieldMaster"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmCustomFieldMaster"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlCustomFieldMaster"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstPosition"%>
<%@page import="com.dimata.system.entity.system.PstSystemProperty"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@page import="com.dimata.harisma.entity.payroll.PstCustomRptMain"%>
<%@page import="com.dimata.harisma.entity.payroll.CustomRptMain"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.payroll.CtrlCustomRptMain"%>
<%@page import="com.dimata.harisma.form.payroll.FrmCustomRptMain"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@ include file = "../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_PRESENCE_REPORT, AppObjInfo.OBJ_PRESENCE_REPORT);
    int appObjCodePresenceEdit = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_ATTENDANCE, AppObjInfo.OBJ_PRESENCE);
    boolean privUpdatePresence = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodePresenceEdit, AppObjInfo.COMMAND_UPDATE));
%>
<%@ include file = "../main/checkuser.jsp" %>
<%
    long hrdDepOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    boolean isHRDLogin = hrdDepOid == departmentOid ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
%>
<%!

   public String drawList(Vector objectClass) {

        ControlList ctrlist = new ControlList(); //membuat new class ControlList
        // membuat tampilan dengan controllist

        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("tblStyle");
        ctrlist.setTitleStyle("title_tbl");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("title_tbl");
        ctrlist.setCellSpacing("0");
        ctrlist.addHeader("No", "");
        ctrlist.addHeader("Field Name","");
        ctrlist.addHeader("Field Type", "");
        ctrlist.addHeader("Required", "");
        ctrlist.addHeader("Data List", "");
        ctrlist.addHeader("Input Type","");
        ctrlist.addHeader("Show Field","");
        ctrlist.addHeader("Note","");

        ctrlist.setLinkRow(1); // untuk menge-sett link di kolom pertama atau dikolom yg lain
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");

        ctrlist.reset(); //berfungsi untuk menginisialisasi list menjadi kosong

        int no = 0;
        // objectClass mempunyai tipe data Vector
        // objectClass.size(); mendapatkan banyak record
        for (int i = 0; i < objectClass.size(); i++) {
            CustomFieldMaster custom = (CustomFieldMaster)objectClass.get(i);
            // rowx will be created secara berkesinambungan base on i
            Vector rowx = new Vector();
            
            no = no + 1;
            rowx.add("" + no);
            rowx.add(custom.getFieldName());
            String strType = "";
            switch(custom.getFieldType()){
                case 0: strType = "Text"; break;
                case 1: strType = "Decimal"; break;
                case 2: strType = "Integer"; break;
                case 3: strType = "Date"; break;
                case 4: strType = "Date time"; break;
            }
            rowx.add(strType);
            if (custom.getRequired()== 1){rowx.add("Required");}else{rowx.add("Not-Required");}
            rowx.add(custom.getDataList());
            String strInput = "";
            switch(custom.getInputType()){
                case 0: strInput = "Text Field"; break;
                case 1: strInput = "Text Area"; break;
                case 2: strInput = "Combo Box"; break;
                case 3: strInput = "Multiple Combo Box"; break;
                case 4: strInput = "Datepicker"; break;
                case 5: strInput = "Datepicker and Time"; break;
                case 6: strInput = "Check Box"; break;
                case 7: strInput = "Radio button"; break;
            }
            rowx.add(strInput);
            String show = "";
            for (String retval : custom.getShowField().split(",")) {
                switch(Integer.valueOf(retval)){
                    case 1:show += "<div>Search</div>"; break;
                    case 2:show += "<div>List</div>"; break;
                    case 3:show += "<div>Print</div>"; break;
                }
            }
            rowx.add(show);
            rowx.add(custom.getNote());
            
            lstData.add(rowx);
            lstLinkData.add(String.valueOf(custom.getOID()));
        }

        return ctrlist.draw(); // mengembalikan data-data control list
        
    }
   
   public String getDataList(int inputType){
       String data = "";
       Vector listDataList = PstCustomFieldDataList.list(0, 0, "", "");
       if (listDataList != null && listDataList.size() > 0){
           switch(inputType){
                case 2:
                    data = "<select name=\"input2\">";
                    for(int i=0; i<listDataList.size(); i++){
                        CustomFieldDataList dataList = (CustomFieldDataList)listDataList.get(i);
                        data += "<option value=\""+dataList.getDataListValue()+"\">"+dataList.getDataListCaption()+"</option>";
                    }
                    data += "</select>";
                    break;
                case 3: 
                    data = "<select name=\"input3\" multiple=\"multiple\">";
                    for(int i=0; i<listDataList.size(); i++){
                        CustomFieldDataList dataList = (CustomFieldDataList)listDataList.get(i);
                        data += "<option value=\""+dataList.getDataListValue()+"\">"+dataList.getDataListCaption()+"</option>";
                    }
                    data += "</select>";
                    break;
                case 6:
                    for(int i=0; i<listDataList.size(); i++){
                        CustomFieldDataList dataList = (CustomFieldDataList)listDataList.get(i);
                        data += "<input type=\"checkbox\" name=\"input6\" value=\""+dataList.getDataListValue()+"\" /> "+dataList.getDataListCaption();
                    }
                    break;
                case 7: 
                    for(int i=0; i<listDataList.size(); i++){
                        CustomFieldDataList dataList = (CustomFieldDataList)listDataList.get(i);
                        data += "<input type=\"radio\" name=\"input7\" value=\""+dataList.getDataListValue()+"\" /> "+dataList.getDataListCaption();
                    }
                    break;
           }
       }
       return data;
   }
   
   public void deleteTempDataList(){
       Vector listDataList = PstCustomFieldDataList.list(0, 0, "", "");
        if (listDataList != null && listDataList.size()>0){
            for(int i=0; i<listDataList.size(); i++){
                CustomFieldDataList data = (CustomFieldDataList)listDataList.get(i);
                try {
                  PstCustomFieldDataList.deleteExc(data.getOID());
                } catch(Exception ex){
                    System.out.println("deleteTempDataList => "+ex.toString());
                }
            }
        }
   }
%>
<!DOCTYPE html>
<%  
    

    int iCommand = FRMQueryString.requestCommand(request);
    int commandOther = FRMQueryString.requestInt(request, "command_other");
    long oidCustom = FRMQueryString.requestLong(request, "oid_custom");
    long oidCustomField = FRMQueryString.requestLong(request, "oid_custom_field");
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    String[] showField = FRMQueryString.requestStringValues(request, "show_field");
    String dataListCaption = FRMQueryString.requestString(request, "data_list_caption");
    String dataListValue = FRMQueryString.requestString(request, "data_list_value");
    int radioType = FRMQueryString.requestInt(request, FrmCustomFieldMaster.fieldNames[FrmCustomFieldMaster.FRM_FIELD_INPUT_TYPE]);
    
    if (commandOther > 0){
        CustomFieldDataList dataList = new CustomFieldDataList();
        dataList.setCustomFieldId(oidCustomField);
        dataList.setDataListCaption(dataListCaption);
        dataList.setDataListValue(dataListValue);
        PstCustomFieldDataList.insertExc(dataList);
        dataListCaption = "";
        dataListValue = "";
        commandOther = 0;
    }
    
    /*variable declaration*/
    int recordToGet = 10;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    String whereClause = "";
    String orderClause = "";//

    CtrlCustomFieldMaster ctrlCustomField = new CtrlCustomFieldMaster(request);
    ControlLine ctrLine = new ControlLine();
    Vector listCustomField = new Vector(1,1);
    FrmCustomFieldMaster frmCustomField = new FrmCustomFieldMaster();
    frmCustomField = ctrlCustomField.getForm();
    CustomFieldMaster customField = new CustomFieldMaster();
    
    if (iCommand == Command.SAVE){
        String dataList = getDataList(radioType);
        frmCustomField.setDataList(dataList);
        frmCustomField.setShowFields(showField);
        frmCustomField.requestEntityObject(customField);
        /* proses delete temp data list */
        deleteTempDataList();
    }
    /* reset temp data list */
    if (iCommand == Command.RESET){
        deleteTempDataList();
    }
    /* code process save */
    iErrCode = ctrlCustomField.action(iCommand, oidCustomField);
    /* end switch*/
    

    /*count list All Position*/
    int vectSize = PstCustomFieldMaster.getCount(whereClause); //PstWarningReprimandAyat.getCount(whereClause);
    customField = ctrlCustomField.getCustomFieldMaster();
    msgString = ctrlCustomField.getMessage();
    
    if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
        start = PstCustomRptMain.findLimitStart(customField.getOID(), recordToGet, whereClause, orderClause);
        oidCustom = customField.getOID();
    }

    if ((iCommand == Command.FIRST || iCommand == Command.PREV)
            || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
        start = ctrlCustomField.actionList(iCommand, start, vectSize, recordToGet);
    }
   
    
    /* get record to display */
    listCustomField = PstCustomFieldMaster.list(start, recordToGet, whereClause, orderClause);
    
    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listCustomField.size() < 1 && start > 0) {
        if (vectSize - recordToGet > recordToGet) {
            start = start - recordToGet;   //go to Command.PREV
        } else {
            start = 0;
            iCommand = Command.FIRST;
            prevCommand = Command.FIRST; //go to Command.FIRST
        }
        listCustomField = PstCustomFieldMaster.list(start, recordToGet, whereClause, orderClause);
    }
    
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Custom Field Master</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <link rel="stylesheet" href="../styles/tab.css" type="text/css">
        <link href="<%=approot%>/stylesheets/superTables.css" rel="Stylesheet" type="text/css" /> 
        <style type="text/css">

            #mn_utama {color: #FF6600; padding: 5px 14px; border-left: 1px solid #999; font-size: 12px; font-weight: bold; background-color: #F5F5F5;}
            
            #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3; color:#0099FF; font-size: 14px; font-weight: bold;}
            
            #btn {
              background: #3498db;
              border: 1px solid #0066CC;
              border-radius: 3px;
              font-family: Arial;
              color: #ffffff;
              font-size: 12px;
              padding: 3px 9px 3px 9px;
            }

            #btn:hover {
              background: #3cb0fd;
              border: 1px solid #3498db;
            }
            #btn1 {
              background: #f27979;
              border: 1px solid #d74e4e;
              border-radius: 3px;
              font-family: Arial;
              color: #ffffff;
              font-size: 12px;
              padding: 3px 9px 3px 9px;
            }

            #btn1:hover {
              background: #d22a2a;
              border: 1px solid #c31b1b;
            }
            #tdForm {
                padding: 5px;
            }
            .tblStyle {border-collapse: collapse;font-size: 11px;}
            .tblStyle td {padding: 3px 5px; border: 1px solid #CCC;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            #confirm {background-color: #fad9d9;border: 1px solid #da8383; color: #bf3c3c; padding: 14px 21px;border-radius: 5px;}
            #desc_field_type{padding:7px 12px; background-color: #F3F3F3; border:1px solid #FFF; margin:3px 0px;}
            #text_desc {background-color: #FFF;color:#575757; padding:3px; font-size: 9px;}
            #data_list{padding:3px 5px; color:#FFF; background-color: #79bbff; margin:2px 1px 2px 0px; border-radius: 3px;}
            #data_list_close {padding:3px 5px; color:#FFF; background-color: #79bbff; margin:2px 1px 2px 0px; border-radius: 3px; cursor: pointer;}
            #data_list_close:hover {padding:3px 5px; color:#FFF; background-color: #0099FF; margin:2px 1px 2px 0px; border-radius: 3px;}
        </style>
        <script type="text/javascript">
            function getCmd(){
                document.<%=FrmCustomFieldMaster.FRM_NAME_CUSTOM_FIELD_MASTER%>.action = "custom_field_master.jsp";
                document.<%=FrmCustomFieldMaster.FRM_NAME_CUSTOM_FIELD_MASTER%>.submit();
            }
            function cmdAdd() {              
                document.<%=FrmCustomFieldMaster.FRM_NAME_CUSTOM_FIELD_MASTER%>.command.value="<%=Command.ADD%>";               
                document.<%=FrmCustomFieldMaster.FRM_NAME_CUSTOM_FIELD_MASTER%>.oid_custom_field.value = "0";
                getCmd();
            }
            function cmdBack() {
                document.<%=FrmCustomFieldMaster.FRM_NAME_CUSTOM_FIELD_MASTER%>.command.value="<%=Command.BACK%>";               
                getCmd();
            }

            function cmdEdit(oid) {
                document.<%=FrmCustomFieldMaster.FRM_NAME_CUSTOM_FIELD_MASTER%>.command.value = "<%=Command.EDIT%>";
                document.<%=FrmCustomFieldMaster.FRM_NAME_CUSTOM_FIELD_MASTER%>.oid_custom_field.value = oid;
                getCmd();
            }

            function cmdSave() {
                document.<%=FrmCustomFieldMaster.FRM_NAME_CUSTOM_FIELD_MASTER%>.command.value = "<%=Command.SAVE%>";
                getCmd();
            }
            
            function cmdSaveDataList(oid){
                document.<%=FrmCustomFieldMaster.FRM_NAME_CUSTOM_FIELD_MASTER%>.command.value="<%=Command.ADD%>"; 
                document.<%=FrmCustomFieldMaster.FRM_NAME_CUSTOM_FIELD_MASTER%>.command_other.value="1";
                document.<%=FrmCustomFieldMaster.FRM_NAME_CUSTOM_FIELD_MASTER%>.oid_custom_field.value = oid;
                getCmd();
            }
            function cmdAsk(oid){
                document.<%=FrmCustomFieldMaster.FRM_NAME_CUSTOM_FIELD_MASTER%>.command.value="<%=Command.ASK%>";
                document.<%=FrmCustomFieldMaster.FRM_NAME_CUSTOM_FIELD_MASTER%>.oid_custom_field.value = oid;
                getCmd();
            }
            function cmdDelete(oid){
                document.<%=FrmCustomFieldMaster.FRM_NAME_CUSTOM_FIELD_MASTER%>.command.value = "<%=Command.DELETE%>";
                document.<%=FrmCustomFieldMaster.FRM_NAME_CUSTOM_FIELD_MASTER%>.oid_custom_field.value = oid;
                getCmd();
            }
            
            function cmdClear(){
                document.<%=FrmCustomFieldMaster.FRM_NAME_CUSTOM_FIELD_MASTER%>.command.value = "<%=Command.RESET%>";
                getCmd();
            }
            
            function cmdListFirst(){
                document.<%=FrmCustomFieldMaster.FRM_NAME_CUSTOM_FIELD_MASTER%>.command.value="<%=Command.FIRST%>";
                document.<%=FrmCustomFieldMaster.FRM_NAME_CUSTOM_FIELD_MASTER%>.prev_command.value="<%=Command.FIRST%>";
                getCmd();
            }

            function cmdListPrev(){
                document.<%=FrmCustomFieldMaster.FRM_NAME_CUSTOM_FIELD_MASTER%>.command.value="<%=Command.PREV%>";
                document.<%=FrmCustomFieldMaster.FRM_NAME_CUSTOM_FIELD_MASTER%>.prev_command.value="<%=Command.PREV%>";
                getCmd();
            }

            function cmdListNext(){
                document.<%=FrmCustomFieldMaster.FRM_NAME_CUSTOM_FIELD_MASTER%>.command.value="<%=Command.NEXT%>";
                document.<%=FrmCustomFieldMaster.FRM_NAME_CUSTOM_FIELD_MASTER%>.prev_command.value="<%=Command.NEXT%>";
                getCmd();
            }

            function cmdListLast(){
                document.<%=FrmCustomFieldMaster.FRM_NAME_CUSTOM_FIELD_MASTER%>.command.value="<%=Command.LAST%>";
                document.<%=FrmCustomFieldMaster.FRM_NAME_CUSTOM_FIELD_MASTER%>.prev_command.value="<%=Command.LAST%>";
                getCmd();
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
        </script>
        <link rel="stylesheet" href="<%=approot%>/javascripts/datepicker/themes/jquery.ui.all.css">
        <script src="<%=approot%>/javascripts/jquery.js"></script>
        <script src="<%=approot%>/javascripts/datepicker/jquery.ui.core.js"></script>
	<script src="<%=approot%>/javascripts/datepicker/jquery.ui.widget.js"></script>
	<script src="<%=approot%>/javascripts/datepicker/jquery.ui.datepicker.js"></script>
        <script>
	$(function() {
            $( "#datepicker" ).datepicker({ dateFormat: "yy-mm-dd" });
	});
	</script>
    </head>
    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
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
                <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
                    <%@ include file = "../main/mnmain.jsp" %>
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
                    <table width="100%" border="0" cellspacing="3" cellpadding="2" id="tbl0">
                        <tr> 
                            <td width="100%" colspan="3" valign="top" style="padding: 12px"> 
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr> 
                                        <td height="20"> <div id="menu_utama"> <!-- #BeginEditable "contenttitle" -->Custom Field Master<!-- #EndEditable --> </div> </td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td style="background-color:#EEE;" valign="top">
                                        
                                            <table style="padding:9px; border:1px solid #00CCFF;" <%=garisContent%> width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                <tr>
                                                    <td valign="top">
                                                        
                                                        
                                                        <form name="<%=FrmCustomFieldMaster.FRM_NAME_CUSTOM_FIELD_MASTER%>" method="POST" action="">
                                                            <input type="hidden" name="command" value="<%=iCommand%>" />
                                                            <input type="hidden" name="command_other" value="<%=commandOther%>" />
                                                            <input type="hidden" name="oid_custom_field" value="<%=oidCustomField%>" />
                                                            <input type="hidden" name="start" value="<%=start%>" />
                                                            <input type="hidden" name="prev_command" value="<%=prevCommand%>" />
                                                            <%
                                                            if(iCommand == Command.EDIT || iCommand == Command.ADD){
                                                            %>
                                                            <div id="mn_utama">Form Custom Field</div>
                                                            <div>&nbsp;</div>
                                                            <table style="color:#474747; background-color: #E3E3E3; padding:12px" cellspacing="0" cellpadding="0">
                                                                <tr>
                                                                    <!-- Left Side -->
                                                                    <td valign="top" style="padding-right: 18px; border-right: 1px solid #FFF;">
                                                                    <table>
                                                                        <tr>
                                                                            <th valign="middle">Field Name</th>
                                                                            <td>&nbsp;</td>
                                                                            <td valign="top"><input type="text" name="<%=FrmCustomFieldMaster.fieldNames[FrmCustomFieldMaster.FRM_FIELD_FIELD_NAME]%>" value="<%=customField.getFieldName()%>" size="42" /></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <th valign="top">Field Type</th>
                                                                            <td>&nbsp;</td>
                                                                            <td valign="top">
                                                                                <div id="desc_field_type">    
                                                                                    <input type="radio" name="<%=FrmCustomFieldMaster.fieldNames[FrmCustomFieldMaster.FRM_FIELD_FIELD_TYPE]%>" value="0" /><strong>Text</strong>
                                                                                    <div id="text_desc">text text text ...</div>
                                                                                </div>
                                                                                <div id="desc_field_type">
                                                                                    <input type="radio" name="<%=FrmCustomFieldMaster.fieldNames[FrmCustomFieldMaster.FRM_FIELD_FIELD_TYPE]%>" value="1" /><strong>Decimal</strong>
                                                                                    <div>2.00, 1000.00 ...</div>
                                                                                </div>
                                                                                <div id="desc_field_type">
                                                                                    <input type="radio" name="<%=FrmCustomFieldMaster.fieldNames[FrmCustomFieldMaster.FRM_FIELD_FIELD_TYPE]%>" value="2" /><strong>Integer</strong>
                                                                                    <div>1, 2, 100, ...</div>
                                                                                </div>
                                                                                <div id="desc_field_type" style="background-color: #dff092">
                                                                                    <input type="radio" name="<%=FrmCustomFieldMaster.fieldNames[FrmCustomFieldMaster.FRM_FIELD_FIELD_TYPE]%>" value="3" /><strong>Date</strong>
                                                                                    <div>2015-01-01</div>
                                                                                </div>
                                                                                <div id="desc_field_type" style="background-color: #c9c9c9">
                                                                                    <input type="radio" name="<%=FrmCustomFieldMaster.fieldNames[FrmCustomFieldMaster.FRM_FIELD_FIELD_TYPE]%>" value="4" /><strong>Date time</strong>
                                                                                    <div>2015-01-01 00:00</div>
                                                                                </div>
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <th valign="middle">Required</th>
                                                                            <td>&nbsp;</td>
                                                                            <td valign="top">
                                                                                <input type="radio" name="<%=FrmCustomFieldMaster.fieldNames[FrmCustomFieldMaster.FRM_FIELD_REQUIRED]%>" value="0" />Not Required &nbsp;
                                                                                <input type="radio" name="<%=FrmCustomFieldMaster.fieldNames[FrmCustomFieldMaster.FRM_FIELD_REQUIRED]%>" value="1" />Required
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <th valign="top">Data List</th>
                                                                            <td>&nbsp;</td>
                                                                            <td valign="top">
                                                                                <div style="padding:2px 3px;"><strong>Caption</strong></div>
                                                                                <div><input type="text" name="data_list_caption" value="" size="42" /></div>
                                                                                <div style="padding:2px 3px;"><strong>Value</strong></div>
                                                                                <div><input type="text" name="data_list_value" value="" size="42" /></div>
                                                                                <div style="padding:2px 0px;"><button id="btn" onclick="cmdSaveDataList('<%=oidCustomField%>')">Add to list</button></div>
                                                                                <div style="padding:5px 0px;">
                                                                                    <table class="tblStyle">
                                                                                        <tr>
                                                                                            <th style="padding: 3px 5px; border: 1px solid #CCC; background-color: #D3D3D3;">Caption</th>
                                                                                            <th style="padding: 3px 5px; border: 1px solid #CCC; background-color: #D3D3D3;">Value</th>
                                                                                        </tr>
                                                                                        <%
                                                                                        Vector listDataList = PstCustomFieldDataList.list(0, 0, "", "");
                                                                                        if (listDataList != null && listDataList.size()>0){
                                                                                            for(int dL=0; dL<listDataList.size(); dL++){
                                                                                                CustomFieldDataList cfDataList = (CustomFieldDataList)listDataList.get(dL);
                                                                                                %>
                                                                                                <tr>
                                                                                                    <td><%=cfDataList.getDataListCaption()%></td>
                                                                                                    <td><%=cfDataList.getDataListValue()%></td>
                                                                                                </tr>
                                                                                                <%
                                                                                            }
                                                                                        }
                                                                                        %>
                                                                                        
                                                                                    </table>
                                                                                </div>
                                                                            </td>
                                                                        </tr>
                                                                        
                                                                    </table>        
                                                                    </td>
                                                                    <!-- Right Side -->
                                                                    <td valign="top" style="border-left: 1px solid #C3C3C3; padding: 0px 15px 0px 21px">
                                                                    <table>
                                                                    <tr>
                                                                            <th valign="top">Type of Input</th>
                                                                            <td>&nbsp;</td>
                                                                            <td valign="top">
                                                                                <table>
                                                                                    <tr>
                                                                                        <td valign="top">
                                                                                        <div id="desc_field_type">
                                                                                            <input type="radio" name="<%=FrmCustomFieldMaster.fieldNames[FrmCustomFieldMaster.FRM_FIELD_INPUT_TYPE]%>" value="0" /><strong>Text Field</strong>
                                                                                            <div><i>example of text field</i><br><input type="text" name="ex_text" /></div>
                                                                                        </div>
                                                                                        <div id="desc_field_type">
                                                                                            <input type="radio" name="<%=FrmCustomFieldMaster.fieldNames[FrmCustomFieldMaster.FRM_FIELD_INPUT_TYPE]%>" value="1" /><strong>Text Area</strong>
                                                                                            <div><i>example of textarea</i><br><textarea name="txtarea"></textarea></div>
                                                                                        </div>
                                                                                        <div id="desc_field_type">
                                                                                            <input type="radio" name="<%=FrmCustomFieldMaster.fieldNames[FrmCustomFieldMaster.FRM_FIELD_INPUT_TYPE]%>" value="2" /><strong>Combo Box</strong>
                                                                                            <div><i>example of combo box</i><br><select><option>option 1</option><option>option 2</option></select></div>
                                                                                        </div>
                                                                                        <div id="desc_field_type">
                                                                                            <input type="radio" name="<%=FrmCustomFieldMaster.fieldNames[FrmCustomFieldMaster.FRM_FIELD_INPUT_TYPE]%>" value="3" /><strong>Multiple Combo box</strong>
                                                                                            <div><i>example of multiple combo box</i><br><select multiple="multiple"><option>value 1</option><option>value 2</option><option>value 3</option></select></div> 
                                                                                            <i>*)you can choose multiple option</i>
                                                                                        </div>
                                                                                        <div id="desc_field_type" style="background-color: #dff092">
                                                                                            <input type="radio" name="<%=FrmCustomFieldMaster.fieldNames[FrmCustomFieldMaster.FRM_FIELD_INPUT_TYPE]%>" value="4" /><strong>Datepicker</strong>
                                                                                            <div>
                                                                                                <i>example of datepicker</i><br>
                                                                                                <img src="../images/datepicker.jpg" />
                                                                                            </div>
                                                                                        </div>
                                                                                        </td>
                                                                                        <td valign="top">
                                                                                        <div id="desc_field_type" style="background-color: #c9c9c9">
                                                                                            <input type="radio" name="<%=FrmCustomFieldMaster.fieldNames[FrmCustomFieldMaster.FRM_FIELD_INPUT_TYPE]%>" value="5" /><strong>Datepicker and Time</strong>
                                                                                            <div>
                                                                                                <i>example of datepicker and time</i><br>
                                                                                                <img src="../images/datepicker.jpg" />
                                                                                            </div>
                                                                                            <div>
                                                                                                <select><option>00</option></select>:<select><option>00</option></select>
                                                                                            </div>
                                                                                        </div>
                                                                                        <div id="desc_field_type">
                                                                                            <input type="radio" name="<%=FrmCustomFieldMaster.fieldNames[FrmCustomFieldMaster.FRM_FIELD_INPUT_TYPE]%>" value="6" /><strong>Check Box</strong>
                                                                                            <div><i>example of check box</i><br><input type="checkbox" name="ex_chk" />Caption 1&nbsp;<input type="checkbox" name="ex_chk" />Caption 2</div>
                                                                                            <i>*)you can checked multiple option</i>
                                                                                        </div>
                                                                                        <div id="desc_field_type">
                                                                                            <input type="radio" name="<%=FrmCustomFieldMaster.fieldNames[FrmCustomFieldMaster.FRM_FIELD_INPUT_TYPE]%>" value="7" /><strong>Radio button</strong>
                                                                                            <div><i>example of radio button</i><br><input type="radio" name="ex_radio" />Caption 1&nbsp;<input type="radio" name="ex_radio" />Caption 2</div>
                                                                                            <i>*)you can only choose one option</i>
                                                                                        </div>        
                                                                                        </td>
                                                                                    </tr>
                                                                                </table>
                                                                                
                                                                                
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <th valign="middle">Show field</th>
                                                                            <td>&nbsp;</td>
                                                                            <td valign="middle">
                                                                                <input type="checkbox" name="show_field" value="1" /><label> Search</label>
                                                                                &nbsp;<input type="checkbox" name="show_field" value="2" /><label> List</label>
                                                                                &nbsp;<input type="checkbox" name="show_field" value="3" /><label> Print</label>
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <th valign="middle">Note</th>
                                                                            <td>&nbsp;</td>
                                                                            <td valign="middle">
                                                                                <textarea name="<%=FrmCustomFieldMaster.fieldNames[FrmCustomFieldMaster.FRM_FIELD_NOTE]%>"><%=customField.getNote()%></textarea>
                                                                            </td>
                                                                        </tr>
                                                                    </table>        
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td>
                                                                        <button id="btn" onclick="cmdSave()">Save</button>&nbsp;
                                                                        <button id="btn" onclick="cmdClear()">Clear Form</button>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                            
                                                            <%
                                                            }
                                                            %>
                                                        </form>
                                                        
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>&nbsp;</td>
                                                </tr>
                                                <%
                                                if (iCommand == Command.ASK){
                                                %>
                                                <tr>
                                                    <td>
                                                        <span id="confirm">
                                                            <strong>Are you sure to delete item ?</strong> &nbsp;
                                                            <button id="btn1" onclick="javascript:cmdDelete('<%=oidCustom%>')">Yes</button>
                                                            &nbsp;<button id="btn1" onclick="javascript:cmdBack()">No</button>
                                                        </span>
                                                    </td>
                                                </tr>
                                                <%
                                                }
                                                %>
                                                <tr>
                                                    <td>&nbsp;</td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <button id="btn" onclick="cmdAdd()">Add New Custom Field</button>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <%
                                                    if (listCustomField != null && listCustomField.size()>0){
                                                    %>
                                                    <td>
                                                        <div id="mn_utama">List Custom Field</div>
                                                        <%=drawList(listCustomField)%>
                                                    </td>
                                                    <div> 
                                                    <%
                                                        int cmd = 0;
                                                        if ((iCommand == Command.FIRST || iCommand == Command.PREV)
                                                                || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                                                            cmd = iCommand;
                                                        } else {
                                                            if (iCommand == Command.NONE || prevCommand == Command.NONE) {
                                                                cmd = Command.FIRST;
                                                            } else {
                                                                if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
                                                                    cmd = PstCustomRptMain.findLimitCommand(start, recordToGet, vectSize);
                                                                } else {
                                                                    cmd = prevCommand;
                                                                }
                                                            }
                                                        }

                                                    %>
                                                    <% ctrLine.setLocationImg(approot + "/images");
                                                        ctrLine.initDefault();
                                                    %>
                                                    <%=ctrLine.drawImageListLimit(cmd, vectSize, start, recordToGet)%> 
                                                </div>
                                                    <%
                                                    } else {
                                                    %>
                                                    <td>
                                                        <div id="mn_utama">List Custom Report</div>
                                                        record not found
                                                    </td>
                                                    <%    
                                                    }
                                                    %>
                                                </tr>
                                                <tr>
                                                    <td>&nbsp;</td>
                                                </tr>
                                            </table>
                                        
                                        </td>
                                    </tr>
                                </table><!---End Tble--->
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <table>
            <tr>
                <td>&nbsp;</td>
            </tr>
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
            <tr>
                <td valign="bottom">
                    <!-- untuk footer -->
                    <%@include file="../footer.jsp" %>
                </td>
                            
            </tr>
            <%} else {%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
                    <%@ include file = "../main/footer.jsp" %>
                    <!-- #EndEditable --> </td>
            </tr>
            <%}%>
        </table>
    </body>
    <!-- #BeginEditable "script" --> <script language="JavaScript">
                var oBody = document.body;
                var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
    </script>
                
    <!-- #EndEditable --> <!-- #EndTemplate -->
</html>
