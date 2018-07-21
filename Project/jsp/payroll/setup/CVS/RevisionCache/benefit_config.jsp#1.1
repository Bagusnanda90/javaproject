<%-- 
    Document   : benefit_config
    Created on : Feb 21, 2015, 1:19:04 PM
    Author     : Hendra Putu
--%>

<%@ page language="java" %>

<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.form.payroll.*" %>


<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_SETUP, AppObjInfo.OBJ_PAYROLL_SETUP_COMPONENT);%>
<%@ include file = "../../main/checkuser.jsp" %>

<%!
    public String drawList(Vector objectClass, long oidBenefitConfig) {

        ControlList ctrlist = new ControlList();

        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("tblStyle");
        ctrlist.setTitleStyle("title_tbl");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("title_tbl");
        ctrlist.setCellSpacing("0");
        ctrlist.addHeader("No", "");
        ctrlist.addHeader("Period From", "");
        ctrlist.addHeader("Period To", "");
        ctrlist.addHeader("Code", "");
        ctrlist.addHeader("Title", "");
        ctrlist.addHeader("Delete", "");

        ctrlist.setLinkRow(1); // untuk menge-sett link di kolom pertama atau dikolom yg lain

        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");

        ctrlist.reset(); //berfungsi untuk menginisialisasi list menjadi kosong

        int no = 0;
        for (int i = 0; i < objectClass.size(); i++) {     
            BenefitConfig benefitConfig = (BenefitConfig) objectClass.get(i);
            // rowx will be created secara berkesinambungan base on i
            Vector rowx = new Vector();

            no = no + 1;
            rowx.add("" + no);
            rowx.add(""+benefitConfig.getPeriodFrom());
            rowx.add(""+benefitConfig.getPeriodTo());
            rowx.add(benefitConfig.getCode());
            rowx.add(benefitConfig.getTitle());
            rowx.add("<a href=\"javascript:cmdAsk('"+benefitConfig.getOID()+"')\">&times;&nbsp;Delete</a>");
            lstData.add(rowx);
            lstLinkData.add(String.valueOf(benefitConfig.getOID()));

        }

        return ctrlist.draw(); // mengembalikan data-data control list

    }

    public String drawListDeduction(Vector objectClass) {

        ControlList ctrlist = new ControlList(); //membuat new class ControlList
        // membuat tampilan dengan controllist

        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("tblStyle");
        ctrlist.setTitleStyle("title_tbl");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("title_tbl");
        ctrlist.setCellSpacing("0");
        ctrlist.addHeader("No", "");
        ctrlist.addHeader("Percent", "");
        ctrlist.addHeader("Description", "");
        ctrlist.addHeader("Reference", "");

        ctrlist.setLinkRow(1); // untuk menge-sett link di kolom pertama atau dikolom yg lain

        Vector lstData = ctrlist.getData();

        ctrlist.reset(); //berfungsi untuk menginisialisasi list menjadi kosong

        int no = 0;
        for (int i = 0; i < objectClass.size(); i++) {    
            BenefitConfigDeduction deduction = (BenefitConfigDeduction) objectClass.get(i);
            
            // rowx will be created secara berkesinambungan base on i
            String strReference = "";
            Vector rowx = new Vector();
            if (deduction.getDeductionReference()==0){
                strReference = "Total Revenue";
            } else {
                BenefitConfigDeduction ded = new BenefitConfigDeduction();
                try{
                    ded = PstBenefitConfigDeduction.fetchExc(deduction.getDeductionReference());
                } catch(Exception e){
                    System.out.println(e);
                }
                strReference = "Reference to deduction-"+ded.getDeductionIndex();
            }
            no = no + 1;
            rowx.add("" + no);
            rowx.add(""+deduction.getDeductionPercent()+" %");
            rowx.add("<a href=\"javascript:cmdEditDeduction('"+deduction.getOID()+"','"+deduction.getBenefitConfigId()+"')\">"+deduction.getDeductionDescription()+"</a>");
            rowx.add(strReference);
            lstData.add(rowx);
        }
        return ctrlist.draw(); // mengembalikan data-data control list
    }
%>
<%  
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidBenefitConfig = FRMQueryString.requestLong(request, "benefit_config_id");
    long tempBenefitConfig = FRMQueryString.requestLong(request, "temp_benefit_config_id");
    String positionInput = FRMQueryString.requestString(request, "position_input");
    String payrollInput = FRMQueryString.requestString(request, "payroll_input");
    String divisionInput = FRMQueryString.requestString(request, "division_input");
/*
 * Date : 2015-03-12 | Hendra Putu
*/
    Date periodFrom = FRMQueryString.requestDate(request, FrmBenefitConfig.fieldNames[FrmBenefitConfig.FRM_FIELD_PERIOD_FROM]);
    Date periodTo = FRMQueryString.requestDate(request, FrmBenefitConfig.fieldNames[FrmBenefitConfig.FRM_FIELD_PERIOD_TO]);
    String codeInput = FRMQueryString.requestString(request, FrmBenefitConfig.fieldNames[FrmBenefitConfig.FRM_FIELD_CODE]);
    String titleInput = FRMQueryString.requestString(request, FrmBenefitConfig.fieldNames[FrmBenefitConfig.FRM_FIELD_TITLE]);
    String descriptionInput = FRMQueryString.requestString(request, FrmBenefitConfig.fieldNames[FrmBenefitConfig.FRM_FIELD_DESCRIPTION]);
    
    String[] empCategoryCheck = FRMQueryString.requestStringValues(request, "emp_category");
   
    /*variable declaration*/
    int recordToGet = 10;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    String whereClause = "";
    String orderClause = "";//

    if (iCommand == Command.EDIT || iCommand == Command.ADD){
        if (oidBenefitConfig != tempBenefitConfig){
            positionInput = "";
            payrollInput = "";
            divisionInput = "";
        }
    }

    CtrlBenefitConfig ctrBenefitConfig = new CtrlBenefitConfig(request);
    ControlLine ctrLine = new ControlLine();
    Vector listBenefitConfig = new Vector(1, 1);
    FrmBenefitConfig frmBenefitConfig = new FrmBenefitConfig();
    frmBenefitConfig = ctrBenefitConfig.getForm();
    BenefitConfig benefitConfig = new BenefitConfig();
    
    if (iCommand == Command.SAVE){
        frmBenefitConfig.setEmpCategory(empCategoryCheck);
        frmBenefitConfig.requestEntityObject(benefitConfig);
    }
    
    /* code process save */
    iErrCode = ctrBenefitConfig.action(iCommand, oidBenefitConfig);
    /* end switch*/
    

    /*count list All Position*/
    int vectSize = PstBenefitConfig.getCount(whereClause); //PstWarningReprimandAyat.getCount(whereClause);
    benefitConfig = ctrBenefitConfig.getBenefitConfig();
    msgString = ctrBenefitConfig.getMessage();
    tempBenefitConfig = benefitConfig.getOID();
    
    if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
        start = PstBenefitConfig.findLimitStart(benefitConfig.getOID(), recordToGet, whereClause, orderClause);
        oidBenefitConfig = benefitConfig.getOID();
    }

    if ((iCommand == Command.FIRST || iCommand == Command.PREV)
            || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
        start = ctrBenefitConfig.actionList(iCommand, start, vectSize, recordToGet);
    }
   
    
    /* get record to display */
    listBenefitConfig = PstBenefitConfig.list(start, recordToGet, whereClause, orderClause);
    
    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listBenefitConfig.size() < 1 && start > 0) {
        if (vectSize - recordToGet > recordToGet) {
            start = start - recordToGet;   //go to Command.PREV
        } else {
            start = 0;
            iCommand = Command.FIRST;
            prevCommand = Command.FIRST; //go to Command.FIRST
        }
        listBenefitConfig = PstBenefitConfig.list(start, recordToGet, whereClause, orderClause);
    }
/*
 * Check data
 */
    Date selectedDateFrom = null;
    Date selectedDateTo = null;
    String codeData = "";
    String titleData = "";
    String descriptionData = "";
    if (benefitConfig.getOID() > 0){
        selectedDateFrom = benefitConfig.getPeriodFrom();
        selectedDateTo = benefitConfig.getPeriodTo();
        codeData = benefitConfig.getCode();
        titleData = benefitConfig.getTitle();
        descriptionData = benefitConfig.getDescription();
    } else {
        selectedDateFrom = periodFrom;
        selectedDateTo = periodTo;
        codeData = codeInput;
        titleData = titleInput;
        descriptionData = descriptionInput;
    }
%>
<!-- JSP Block -->
<!-- End of JSP Block -->
<html>
    <!-- #BeginTemplate "/Templates/main.dwt" --> 
    <head>
        <!-- #BeginEditable "doctitle" --> 
        <title>HARISMA - Benefit Config</title>
        <!-- #EndEditable --> 
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <!-- #BeginEditable "styles" --> 
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
        
        <style type="text/css">
            #menu_utama {color: #0066CC; font-weight: bold; padding: 5px 14px; border-left: 1px solid #0066CC; font-size: 14px; background-color: #F7F7F7;}
            #mn_utama {color: #FF6600; padding: 5px 14px; border-left: 1px solid #999; font-size: 14px; background-color: #F5F5F5;}
    
            #tbl0 {padding: 7px;}
            
            #input {padding: 3px; border: 1px solid #CCC; margin: 0px;}
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
            .tblStyle {border-collapse: collapse;font-size: 11px;}
            .tblStyle td {padding: 3px 5px; border: 1px solid #CCC;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            a {text-decoration: none; font-weight: bold; color: #0066CC;}
            a:hover {color: red;}
            
            #titleTd {background-color: #a0d5fb; color: #FFF; padding: 3px 5px; border-left: 1px solid #0066CC;}
            #listPos {background-color: #FFF; border: 1px solid #CCC; padding: 3px;cursor: pointer;margin: 3px 0px;} 
            #position_input { margin: 3px; padding: 5px 7px; color: #FF6600;}
            #payroll_input { margin: 3px; padding: 5px 7px; color: #FF6600;}
            #division_input { margin: 3px; padding: 5px 7px; color: #FF6600;}
            #confirm {background-color: #fad9d9;border: 1px solid #da8383; color: #bf3c3c; padding: 14px 21px;border-radius: 5px;}
        </style>
        <script type="text/javascript">

            function getCmd(){
                document.<%=FrmBenefitConfig.FRM_NAME_BENEFIT_CONFIG%>.action = "benefit_config.jsp";
                document.<%=FrmBenefitConfig.FRM_NAME_BENEFIT_CONFIG%>.submit();
            }
            function cmdAdd() {              
                document.<%=FrmBenefitConfig.FRM_NAME_BENEFIT_CONFIG%>.command.value="<%=Command.ADD%>";               
                document.<%=FrmBenefitConfig.FRM_NAME_BENEFIT_CONFIG%>.benefit_config_id.value = "0";
                getCmd();
            }
            function cmdBack() {
                document.<%=FrmBenefitConfig.FRM_NAME_BENEFIT_CONFIG%>.command.value="<%=Command.BACK%>";               
                getCmd();
            }

            function cmdEdit(oid) {
                document.<%=FrmBenefitConfig.FRM_NAME_BENEFIT_CONFIG%>.command.value = "<%=Command.EDIT%>";
                document.<%=FrmBenefitConfig.FRM_NAME_BENEFIT_CONFIG%>.benefit_config_id.value = oid;
                getCmd();
            }

            function cmdSave() {
                document.<%=FrmBenefitConfig.FRM_NAME_BENEFIT_CONFIG%>.command.value = "<%=Command.SAVE%>";
                getCmd();
            }
            
            function cmdListFirst(){
                document.<%=FrmBenefitConfig.FRM_NAME_BENEFIT_CONFIG%>.command.value="<%=Command.FIRST%>";
                document.<%=FrmBenefitConfig.FRM_NAME_BENEFIT_CONFIG%>.prev_command.value="<%=Command.FIRST%>";
                getCmd();
            }

            function cmdListPrev(){
                document.<%=FrmBenefitConfig.FRM_NAME_BENEFIT_CONFIG%>.command.value="<%=Command.PREV%>";
                document.<%=FrmBenefitConfig.FRM_NAME_BENEFIT_CONFIG%>.prev_command.value="<%=Command.PREV%>";
                getCmd();
            }

            function cmdListNext(){
                document.<%=FrmBenefitConfig.FRM_NAME_BENEFIT_CONFIG%>.command.value="<%=Command.NEXT%>";
                document.<%=FrmBenefitConfig.FRM_NAME_BENEFIT_CONFIG%>.prev_command.value="<%=Command.NEXT%>";
                getCmd();
            }

            function cmdListLast(){
                document.<%=FrmBenefitConfig.FRM_NAME_BENEFIT_CONFIG%>.command.value="<%=Command.LAST%>";
                document.<%=FrmBenefitConfig.FRM_NAME_BENEFIT_CONFIG%>.prev_command.value="<%=Command.LAST%>";
                getCmd();
            } 
            function cmdAddDeduction(benefitId){
                newWindow=window.open("deduction_form.jsp?benefit_id="+benefitId,"Deduction", "height=400, width=500, status=yes, toolbar=no, menubar=no, location=center, scrollbars=yes");
                newWindow.focus();
            }
            function cmdEditDeduction(deductionId, benefitId){
                newWindow=window.open("deduction_form.jsp?deduction_id="+deductionId+"&benefit_id="+benefitId, "Deduction", "height=400, width=500, status=yes, toolbar=no, menubar=no, location=center, scrollbars=yes");
                newWindow.focus();
            }
            function cmdAddPosition(){
                var data = document.getElementById("select_position").value;
                var result = document.getElementById("position_input").value;
                if(result!=""){
                    result = result +","+ data;
                } else {
                    result = result + data;
                }
                document.getElementById("position_input").value = result;
            }
            
            function cmdAddPayroll(){
                var data = document.getElementById("select_payroll").value;
                var result = document.getElementById("payroll_input").value;
                if(result!=""){
                    result = result +","+ data;
                } else {
                    result = result + data;
                }
                document.getElementById("payroll_input").value = result;
            }
            function cmdAddDivision(){
                var data = document.getElementById("select_division").value;
                var result = document.getElementById("division_input").value;
                if(result!=""){
                    result = result +","+ data;
                } else {
                    result = result + data;
                }
                document.getElementById("division_input").value = result;
            }
            function cmdAsk(oid){
                document.<%=FrmBenefitConfig.FRM_NAME_BENEFIT_CONFIG%>.command.value="<%=Command.ASK%>";
                document.<%=FrmBenefitConfig.FRM_NAME_BENEFIT_CONFIG%>.benefit_config_id.value = oid;
                getCmd();
            }
            function cmdDelete(oid){
                document.<%=FrmBenefitConfig.FRM_NAME_BENEFIT_CONFIG%>.command.value = "<%=Command.DELETE%>";
                document.<%=FrmBenefitConfig.FRM_NAME_BENEFIT_CONFIG%>.benefit_config_id.value = oid;
                getCmd();
            }
        </script>
        <!-- #EndEditable --> 
    </head>
    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../../styletemplate/template_header.jsp" %>
            <%} else {%>
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
                    <table width="100%" border="0" cellspacing="3" cellpadding="2" id="tbl0">
                        <tr> 
                            <td width="100%" colspan="3" valign="top"> 
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr> 
                                        <td height="20"> <div id="menu_utama"> <!-- #BeginEditable "contenttitle" -->Benefit Configuration<!-- #EndEditable --> </div> </td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td style="background-color:#EEE;">
                                        
                                            <table style="padding:9px; border:1px solid #00CCFF;" <%=garisContent%> width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                <tr>
                                                    <td>
                                                        <form name="<%=FrmBenefitConfig.FRM_NAME_BENEFIT_CONFIG%>" method="POST" action="">
                                                        <input type="hidden" name="start" value="<%=start%>">
                                                        <input type="hidden" name="command" value="<%=iCommand%>">
                                                        <input type="hidden" name="benefit_config_id" value="<%=oidBenefitConfig%>">
                                                        <input type="hidden" name="temp_benefit_config_id" value="<%=benefitConfig.getOID()%>" />
                                                        <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                                        <input type="hidden" name="start" value="<%=start%>">
                                                        <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                        <% 
                                                        if ((iCommand == Command.ADD)||(iCommand == Command.EDIT)){
                                                            if (iCommand == Command.ADD){
                                                                %>
                                                                <div id="mn_utama">Form Add Benefit Configuration </div>
                                                                <%
                                                            } else {
                                                                %>
                                                                <div id="mn_utama">Form Edit Benefit Configuration </div>
                                                                <%
                                                            }
                                                        %>
                                                        
                                                        
                                                        <table style="color:#373737;">
                                                            <tr>
                                                                <td valign="top">
                                                                    <table>
                                                                        <tr>
                                                                            <td colspan="2"><div id="titleTd">Basic Configuration</div></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td>Period From</td>
                                                                            <td>
                                                                                <%=ControlDate.drawDateWithStyle(FrmBenefitConfig.fieldNames[FrmBenefitConfig.FRM_FIELD_PERIOD_FROM], selectedDateFrom != null ? selectedDateFrom : new Date(), 2, -5, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%> 
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td>Period To</td>
                                                                            <td>
                                                                                <%=ControlDate.drawDateWithStyle(FrmBenefitConfig.fieldNames[FrmBenefitConfig.FRM_FIELD_PERIOD_TO], selectedDateTo != null ? selectedDateTo : new Date(), 2, -5, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%>
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td>Code</td>
                                                                            <td><input type="text"  size="30" name="<%=FrmBenefitConfig.fieldNames[FrmBenefitConfig.FRM_FIELD_CODE]%>" value="<%=codeData%>" /></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td>Title</td>
                                                                            <td><input type="text" size="49"  name="<%=FrmBenefitConfig.fieldNames[FrmBenefitConfig.FRM_FIELD_TITLE]%>" value="<%=titleData%>" /></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td>Description</td>
                                                                            <td><textarea name="<%=FrmBenefitConfig.fieldNames[FrmBenefitConfig.FRM_FIELD_DESCRIPTION]%>"><%=descriptionData%></textarea></td>
                                                                        </tr>
                                                                        
                                                                        <tr>
                                                                            <td colspan="2"><div id="titleTd">Exception no service charge by employee category</div></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td colspan="2">
                                                                                
                                                                                <%
                                                                                
                                                                                    Vector listCategory = new Vector(1,1);
                                                                                    listCategory = PstEmpCategory.list(0, 0, "", "");
                                                                                    String[] checkeds = new String[listCategory.size()];
                                                                                    // Inisialisasi checkeds
                                                                                    for(int a=0; a<listCategory.size();a++){
                                                                                        checkeds[a]="0";
                                                                                    }
                                                                                    int n = 0;                                                                         
                                                                                    String empCategoryData = benefitConfig.getExceptionNoByCategory();
                                                                                    if ((empCategoryData != null)||(!empCategoryData.equals(""))||(empCategoryData.length() > 0)){
                                                                                        for (String retval : empCategoryData.split(",")) {
                                                                                            checkeds[n] = retval;
                                                                                            n++;
                                                                                        }
                                                                                    }
                                                                                    String checked = " checked=\"checked\" ";
                                                                                    String strEmpCat = "";
                                                                                    int inc = 0;
                                                                                    for(int c=0; c<listCategory.size(); c++){
                                                                                        EmpCategory empCategory = (EmpCategory)listCategory.get(c);
                                                                                        strEmpCat = String.valueOf(empCategory.getOID());
                                                                                        for(int e=0; e<listCategory.size(); e++){
                                                                                            if(strEmpCat.equals(checkeds[e])){
                                                                                                checked = " checked=\"checked\" ";
                                                                                                break;
                                                                                            } else {
                                                                                                checked = "";
                                                                                            }
                                                                                        }
                                                                                        if (inc < 3){
                                                                                            inc++;
                                                                                            
                                                                                        %>
                                                                                        <input name="emp_category" type="checkbox" <%=checked%>  value="<%=empCategory.getOID()%>" > <%=empCategory.getEmpCategory()%> &nbsp;&nbsp;
                                                                                        <%
                                                                                        } else {
                                                                                            inc = 0;
                                                                                        %>
                                                                                           <input name="emp_category" type="checkbox" <%=checked%> value="<%=empCategory.getOID()%>" > <%=empCategory.getEmpCategory()%> &nbsp;&nbsp;<br /> 
                                                                                        <%
                                                                                        }
                                                                                    }
                                                                                %>
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td colspan="2"><div id="titleTd">Exception No Benefit By Division</div></td>
                                                                        </tr>
                                                                        
                                                                        
                                                                    </table>
                                                                    <table>
                                                                        <tr>
                                                                            <td>
                                                                                <button id="btn" onclick="javascript:cmdAddDivision()">Add Division</button>
                                                                                <select id="select_division">
                                                                                    <option value="0">-select-</option>
                                                                                <%
                                                                                Vector listDivision = new Vector();
                                                                                listDivision = PstDivision.list(0, 0, "", "");
                                                                                if (listDivision != null && listDivision.size() > 0){
                                                                                    for(int i=0; i<listDivision.size(); i++){
                                                                                        Division division= (Division)listDivision.get(i);
                                                                                        %>
                                                                                        <option value="<%=""+division.getOID()%>"><%="["+division.getOID()+"] "+division.getDivision()%></option>
                                                                                        <%
                                                                                    }
                                                                                }
                                                                                String divisionData = "";
                                                                                
                                                                                if (benefitConfig.getExceptionNoByDivision().length() > 0){
                                                                                    if (divisionInput.equals("")){
                                                                                       divisionData = benefitConfig.getExceptionNoByDivision(); 
                                                                                    } else {
                                                                                       divisionData = divisionInput;
                                                                                    }
                                                                                } else {
                                                                                    divisionData = divisionInput;
                                                                                }
                                                                                %>
                                                                                </select><br />
                                                                                <input type="text" id="division_input" name="division_input" value="<%=divisionData%>" size="57" />
                                                                                <input type="hidden" name="<%=FrmBenefitConfig.fieldNames[FrmBenefitConfig.FRM_FIELD_EXCEPTION_NO_BY_DIVISION]%>" value="<%=divisionData%>" size="47" />
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td valign="top" colspan="2"><div id="titleTd">Document Approved Configuration</div></td>
                                                                        </tr>
                                                                     </table>
                                                                        <table>
                                                                            
                                                                            <tr>
                                                                                <td valign="top">Approved 1 by</td>
                                                                                <td valign="top">
                                                                                    <select name="<%=FrmBenefitConfig.fieldNames[FrmBenefitConfig.FRM_FIELD_APPROVE_1_EMP_ID]%>">
                                                                                        <option>-select-</option>
                                                                                        <%
                                                                                        String selectedApprove1 = "";
                                                                                        Vector listEmpApp1 = PstEmployee.list(0, 0, "", "FULL_NAME"); 
                                                                                        if (listEmpApp1 != null && listEmpApp1.size() > 0){
                                                                                            for(int i=0; i<listEmpApp1.size(); i++){
                                                                                                Employee employies = (Employee)listEmpApp1.get(i);
                                                                                                if (benefitConfig.getApprove1EmpId() == employies.getOID()){
                                                                                                    selectedApprove1 = " selected=\"selected\"";
                                                                                                } else {
                                                                                                    selectedApprove1 = " ";
                                                                                                }
                                                                                                %>
                                                                                                <option value="<%=employies.getOID()%>" <%=selectedApprove1%>><%=employies.getFullName()%></option>
                                                                                                <%
                                                                                            }
                                                                                        }
                                                                                        %>
                                                                                    </select>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td valign="top">Approved 2 by</td>
                                                                                <td valign="top">
                                                                                    <select name="<%=FrmBenefitConfig.fieldNames[FrmBenefitConfig.FRM_FIELD_APPROVE_2_EMP_ID]%>">
                                                                                        <option>-select-</option>
                                                                                        <%
                                                                                        String selectedApprove2 = "";
                                                                                        Vector listEmpApp2 = PstEmployee.list(0, 0, "", "FULL_NAME"); 
                                                                                        if (listEmpApp1 != null && listEmpApp2.size() > 0){
                                                                                            for(int i=0; i<listEmpApp2.size(); i++){
                                                                                                Employee employies = (Employee)listEmpApp2.get(i);
                                                                                                if (benefitConfig.getApprove2EmpId() == employies.getOID()){
                                                                                                    selectedApprove2 = " selected=\"selected\"";
                                                                                                } else {
                                                                                                    selectedApprove2 = " ";
                                                                                                }
                                                                                                %>
                                                                                                <option value="<%=employies.getOID()%>" <%=selectedApprove2%>><%=employies.getFullName()%></option>
                                                                                                <%
                                                                                            }
                                                                                        }
                                                                                        %>
                                                                                    </select>
                                                                                </td>
                                                                            </tr>
                                                                        </table>
                                                                </td>
                                                                <td valign="top">
                                                                    <table width="100%">
                                                                        <tr>
                                                                            <td colspan="2"><div id="titleTd">Deductions Configuration</div></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <% if (iCommand == Command.EDIT){ %>
                                                                            <td>
                                                                                <button id="btn" onclick="javascript:cmdAddDeduction('<%=benefitConfig.getOID()%>')">Add Deduction</button>
                                                                                <div>
                                                                                <%
                                                                                Vector listDeduction = new Vector();
                                                                                String whereDeduction  = "BENEFIT_CONFIG_ID="+benefitConfig.getOID();
                                                                                listDeduction = PstBenefitConfigDeduction.list(0, 0, whereDeduction, "");
                                                                                if(listDeduction.size() > 0){
                                                                                    %>
                                                                                    <%=drawListDeduction(listDeduction)%>
                                                                                    <%
                                                                                } else {
                                                                                    %>
                                                                                    No data record
                                                                                    <%
                                                                                }
                                                                                %>
                                                                                </div>
                                                                            </td>
                                                                            
                                                                            <% } else { %>
                                                                            <td>Deduction can be added after save basic configuration !</td>
                                                                            <% } %>
                                                                        </tr>
                                                                        
                                                                    </table>
                                                                    <table width="100%">
                                                                        <tr>
                                                                            <td colspan="2"><div id="titleTd">Distribution Configuration</div></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td>
                                                                                <table style="border-left: 1px solid #CCC; padding: 3px">
                                                                                    <tr>
                                                                                        <td>Part 1</td>
                                                                                        <td><input type="text" name="<%=FrmBenefitConfig.fieldNames[FrmBenefitConfig.FRM_FIELD_DISTRIBUTION_PART_1]%>" value="<%=benefitConfig.getDistributionPart1()%>" size="45" /></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td>Percent</td>
                                                                                        <td><input type="text" name="<%=FrmBenefitConfig.fieldNames[FrmBenefitConfig.FRM_FIELD_DISTRIBUTION_PERCENT_1]%>" value="<%=benefitConfig.getDistributionPercent1()%>" size="5" />%</td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td>Description</td>
                                                                                        <td><input type="text" name="<%=FrmBenefitConfig.fieldNames[FrmBenefitConfig.FRM_FIELD_DISTRIBUTION_DESCRIPTION_1]%>" value="<%=benefitConfig.getDistributionDescription1()%>" size="45" /></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td>Total</td>
                                                                                        <td>
                                                                                            <select name="<%=FrmBenefitConfig.fieldNames[FrmBenefitConfig.FRM_FIELD_DISTRIBUTION_TOTAL_1]%>">
                                                                                                <%
                                                                                                    /*String selected = "";
                                                                                                    for (int d = 0; d < PstBenefitConfig.distributionTotalValue.length; d++) {
                                                                                                        if (PstBenefitConfig.distributionTotalValue[d] == benefitConfig.getDistributionTotal1()){
                                                                                                            selected = " selected=\"selected\" ";
                                                                                                        } else {
                                                                                                            selected = "";
                                                                                                        }*/
                                                                                                %>
                                                                                                <option value="<%=PstBenefitConfig.distributionTotalValue[0]%>" disabled="disabled" selected="selected"><%=PstBenefitConfig.distributionTotalKey[0]%></option>
                                                                                                <%
                                                                                                    /*}*/
                                                                                                %>
                                                                                            </select>
                                                                                        </td>
                                                                                    </tr>
                                                                                </table>
                                                                            </td>
                                                                            <td>
                                                                                <table style="border-left: 1px solid #CCC; padding: 3px">
                                                                                    <tr>
                                                                                        <td>Part 2</td>
                                                                                        <td><input type="text" name="<%=FrmBenefitConfig.fieldNames[FrmBenefitConfig.FRM_FIELD_DISTRIBUTION_PART_2]%>" value="<%=benefitConfig.getDistributionPart2()%>" size="45" /></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td>Percent</td>
                                                                                        <td><input type="text" name="<%=FrmBenefitConfig.fieldNames[FrmBenefitConfig.FRM_FIELD_DISTRIBUTION_PERCENT_2]%>" value="<%=benefitConfig.getDistributionPercent2()%>" size="5" />%</td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td>Description</td>
                                                                                        <td><input type="text" name="<%=FrmBenefitConfig.fieldNames[FrmBenefitConfig.FRM_FIELD_DISTRIBUTION_DESCRIPTION_2]%>" value="<%=benefitConfig.getDistributionDescription2()%>" size="45" /></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td>Total</td>
                                                                                        <td>
                                                                                            <select name="<%=FrmBenefitConfig.fieldNames[FrmBenefitConfig.FRM_FIELD_DISTRIBUTION_TOTAL_2]%>">
                                                                                                <%/*
                                                                                                    String selected1 = "";
                                                                                                    for (int d = 0; d < PstBenefitConfig.distributionTotalValue.length; d++) {
                                                                                                        if (PstBenefitConfig.distributionTotalValue[d] == benefitConfig.getDistributionTotal2()){
                                                                                                            selected1 = " selected=\"selected\" ";
                                                                                                        } else {
                                                                                                            selected1 = "";
                                                                                                        }
 *                                                                                                 */
                                                                                                %>
                                                                                                <option value="<%=PstBenefitConfig.distributionTotalValue[1]%>" disabled="disabled" selected="selected"><%=PstBenefitConfig.distributionTotalKey[1]%></option>
                                                                                                <%
                                                                                                    /*}*/
                                                                                                %>
                                                                                            </select>
                                                                                        </td>
                                                                                    </tr>
                                                                                </table>
                                                                            </td>
                                                                        </tr>
                                                                    </table>
                                                                    <table width="100%">
                                                                        <tr>
                                                                            <td colspan="5"><div id="titleTd">Exception no service charge by position</div></td>                                                                            
                                                                        </tr>
                                                                        
                                                                        <tr> 
                                                                            <td>
                                                                                <button id="btn" onclick="javascript:cmdAddPosition()">Add Position</button>
                                                                                <select id="select_position">
                                                                                    <option value="0">-select-</option>
                                                                                <%
                                                                                Vector listPosition = new Vector();
                                                                                listPosition = PstPosition.list(0, 0, "", "");
                                                                                if (listPosition != null && listPosition.size() > 0){
                                                                                    for(int i=0; i<listPosition.size(); i++){
                                                                                        Position position = (Position)listPosition.get(i);
                                                                                        %>
                                                                                        <option value="<%=""+position.getOID()%>"><%="["+position.getOID()+"] "+position.getPosition()%></option>
                                                                                        <%
                                                                                    }
                                                                                }
                                                                                String positionData = "";
                                                                                if (!benefitConfig.getExceptionNoByPosition().equals(null) && !benefitConfig.getExceptionNoByPosition().equals("")){
                                                                                    if (positionInput.equals("")){
                                                                                       positionData = benefitConfig.getExceptionNoByPosition(); 
                                                                                    } else {
                                                                                       positionData = positionInput;
                                                                                    }
                                                                                } else {
                                                                                    positionData = positionInput;
                                                                                }
                                                                                %>
                                                                                </select><br />
                                                                                <input type="text" id="position_input" name="position_input" value="<%=positionData%>" size="70" />
                                                                                <input type="hidden" size="49"  name="<%=FrmBenefitConfig.fieldNames[FrmBenefitConfig.FRM_FIELD_EXCEPTION_NO_BY_POSITION]%>" value="<%=positionData%>" />
                                                                            </td>
                                                                            
                                                                        </tr>
                                                                            
                                                                    </table>
                                                                    <table width="100%">
                                                                        <tr>
                                                                            <td colspan="5"><div id="titleTd">Exception no service charge by payroll</div></td>
                                                                        </tr>
                                                                        
                                                                        <tr>
                                                                            <td>
                                                                                <button id="btn" onclick="javascript:cmdAddPayroll()">Add Payroll</button>
                                                                                <select id="select_payroll">
                                                                                    <option value="0">-select-</option>
                                                                                <%
                                                                                Vector listPayroll = PstEmployee.list(0, 0, "", "");
                                                                                if (listPayroll != null && listPayroll.size() > 0){
                                                                                    for(int i=0; i<listPayroll.size(); i++){
                                                                                        Employee emp = (Employee)listPayroll.get(i);
                                                                                        %>
                                                                                        <option value="<%=""+emp.getEmployeeNum()%>"><%=emp.getEmployeeNum()+" | "+emp.getFullName()%></option>
                                                                                        <%
                                                                                    }
                                                                                }
                                                                                String payrollData = "";
                                                                                if (!benefitConfig.getExceptionNoByPayroll().equals(null) && !benefitConfig.getExceptionNoByPayroll().equals("")){
                                                                                    if (payrollInput.equals("")){
                                                                                       payrollData = benefitConfig.getExceptionNoByPayroll(); 
                                                                                    } else {
                                                                                       payrollData = payrollInput;
                                                                                    }
                                                                                } else {
                                                                                    payrollData = payrollInput;
                                                                                }
                                                                                %>
                                                                                </select><br />
                                                                                <input type="text" id="payroll_input" name="payroll_input" value="<%=payrollData%>" size="70" />
                                                                                <input type="hidden" size="49"  name="<%=FrmBenefitConfig.fieldNames[FrmBenefitConfig.FRM_FIELD_EXCEPTION_NO_BY_PAYROLL]%>" value="<%=payrollData%>" />
                                                                            </td>
                                                                        </tr>
                                                                    </table>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                
                                                                <td colspan="2">
                                                                    <button id="btn" onclick="cmdSave()">Save</button>
                                                                    <button id="btn" onclick="cmdBack()">Back</button>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <%}%><!-- end if add or edit form -->
                                                        </form>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <div id="mn_utama"> List Benefit Configuration </div>
                                                        <button style="margin: 5px 0px" id="btn" onclick="cmdAdd()">Add Benefit Configuration</button>
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
                                                            <button id="btn1" onclick="javascript:cmdDelete('<%=oidBenefitConfig%>')">Yes</button>
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
                                                    <%if (listBenefitConfig != null && listBenefitConfig.size() > 0) {%>
                                                    <td>
                                                        <%=drawList(listBenefitConfig, oidBenefitConfig)%>
                                                    </td>

                                                    <%} else {%>
                                                    <td>
                                                        record not found
                                                    </td>
                                                    <%}%>
                                                </tr>
                                                <tr>
                                                    <td>&nbsp;</td>
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
                    <%@include file="../../footer.jsp" %>
                </td>
                            
            </tr>
            <%} else {%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
                    <%@ include file = "../../main/footer.jsp" %>
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