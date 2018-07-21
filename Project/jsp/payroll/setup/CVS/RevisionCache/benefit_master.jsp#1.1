<%-- 
    Document   : benefit_master
    Created on : Feb 10, 2015, 1:33:32 PM
    Author     : Dimata 007
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
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_SETUP, AppObjInfo.OBJ_PAYROLL_SETUP_COMPONENT);%>
<%@ include file = "../../main/checkuser.jsp" %>

<%!
    public String drawList(Vector objectClass, long oidBenefitMaster) {

        ControlList ctrlist = new ControlList(); //membuat new class ControlList
        // membuat tampilan dengan controllist

        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.setAreaStyle("customTable");
        ctrlist.addHeader("No", "7%");
        ctrlist.addHeader("Period From", "20%");
        ctrlist.addHeader("Period To", "20%");
        ctrlist.addHeader("Code", "20%");
        ctrlist.addHeader("Title", "32%");

        ctrlist.setLinkRow(1); // untuk menge-sett link di kolom pertama atau dikolom yg lain

        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");

        ctrlist.reset(); //berfungsi untuk menginisialisasi list menjadi kosong

        int no = 0;
        for (int i = 0; i < objectClass.size(); i++) {     
            BenefitMaster benefitMaster= (BenefitMaster) objectClass.get(i);
            // rowx will be created secara berkesinambungan base on i
            Vector rowx = new Vector();

            no = no + 1;
            rowx.add("" + no);
            rowx.add(""+benefitMaster.getPeriodFrom());
            rowx.add(""+benefitMaster.getPeriodTo());
            rowx.add(benefitMaster.getCode());
            rowx.add(benefitMaster.getTitle());

            lstData.add(rowx);
            lstLinkData.add(String.valueOf(benefitMaster.getOID()));

        }

        return ctrlist.draw(); // mengembalikan data-data control list

    }
%>
<%  
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidBenefitMaster = FRMQueryString.requestLong(request, "benefit_master_id");
    Date selectedDateFrom = FRMQueryString.requestDate(request, "check_date_start");
    Date selectedDateTo = FRMQueryString.requestDate(request, "check_date_finish");
    /*variable declaration*/
    int recordToGet = 10;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    String whereClause = "";
    String orderClause = "";//

    
    CtrlBenefitMaster ctrBenefitMaster = new CtrlBenefitMaster(request);
    ControlLine ctrLine = new ControlLine();
    Vector listBenefitMaster = new Vector(1, 1);

    /*switch statement */
    iErrCode = ctrBenefitMaster.action(iCommand, oidBenefitMaster);
    /* end switch*/
    FrmBenefitMaster frmBenefitMaster = ctrBenefitMaster.getForm();

    /*count list All Position*/
    int vectSize = PstBenefitMaster.getCount(whereClause); //PstWarningReprimandAyat.getCount(whereClause);
    BenefitMaster benefitMaster = ctrBenefitMaster.getBenefitMaster();
    msgString = ctrBenefitMaster.getMessage();
    
    if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
        start = PstBenefitMaster.findLimitStart(benefitMaster.getOID(), recordToGet, whereClause, orderClause);
        oidBenefitMaster = benefitMaster.getOID();
    }

    if ((iCommand == Command.FIRST || iCommand == Command.PREV)
            || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
        start = ctrBenefitMaster.actionList(iCommand, start, vectSize, recordToGet);
    }
    /* end switch list*/

    /* get record to display */
    listBenefitMaster = PstBenefitMaster.list(start, recordToGet, whereClause, orderClause);

    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listBenefitMaster.size() < 1 && start > 0) {
        if (vectSize - recordToGet > recordToGet) {
            start = start - recordToGet;   //go to Command.PREV
        } else {
            start = 0;
            iCommand = Command.FIRST;
            prevCommand = Command.FIRST; //go to Command.FIRST
        }
        listBenefitMaster = PstBenefitMaster.list(start, recordToGet, whereClause, orderClause);
    }


%>
<!-- JSP Block -->
<!-- End of JSP Block -->
<html>
    <!-- #BeginTemplate "/Templates/main.dwt" --> 
    <head>
        <!-- #BeginEditable "doctitle" --> 
        <title>HARISMA - Benefit Master</title>
        <!-- #EndEditable --> 
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <!-- #BeginEditable "styles" --> 
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
        
        <style type="text/css">
            #mn_utama {color: #FF6600; padding: 5px 14px; border-left: 1px solid #999; font-size: 14px; background-color: #F5F5F5;}
            #sub {border-left: 1px solid #333; background-color: #EEE; color: #333; padding: 3px 5px; margin: 5px 0px 3px 0px;}
            #sub1 {padding-left: 14px;}
            #sub_part {padding-left: 14px; display: none;}
            #sub2 {padding-left: 21px; margin-top: 3px;}
            #exception1, #exception2, #exception3, #exception4 {padding-left: 21px; margin-top: 3px; display: none;}
            #tbl0 {padding: 7px;}
            #tbl1 {border-collapse: collapse;}
            #td1 {
                border-collapse: collapse;
                border: 1px solid #CCC;
                background-color: #DDD;
                font-weight: bold;
                padding: 3px;
            }
            #td2 {
                border-collapse: collapse;
                border: 1px solid #CCC;
                padding: 3px;
            }
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
            #titleTd {background-color: #3cb0fd; color: #FFF; padding: 3px 5px; border-left: 1px solid #0066CC;}
        </style>
        <script type="text/javascript">
            function getCmd(){
                document.<%=FrmBenefitMaster.FRM_NAME_BENEFIT_MASTER%>.action = "benefit_master.jsp";
                document.<%=FrmBenefitMaster.FRM_NAME_BENEFIT_MASTER%>.submit();
            }
            function cmdAdd() {              
                document.<%=FrmBenefitMaster.FRM_NAME_BENEFIT_MASTER%>.command.value="<%=Command.ADD%>";               
                document.<%=FrmBenefitMaster.FRM_NAME_BENEFIT_MASTER%>.benefit_master_id.value = "0";
                getCmd();
            }
            function cmdBack() {
                document.<%=FrmBenefitMaster.FRM_NAME_BENEFIT_MASTER%>.command.value="<%=Command.BACK%>";               
                getCmd();
            }

            function cmdEdit(oid) {
                document.<%=FrmBenefitMaster.FRM_NAME_BENEFIT_MASTER%>.command.value = "<%=Command.EDIT%>";
                document.<%=FrmBenefitMaster.FRM_NAME_BENEFIT_MASTER%>.benefit_master_id.value = oid;
                getCmd();
            }

            function cmdSave() {
                document.<%=FrmBenefitMaster.FRM_NAME_BENEFIT_MASTER%>.command.value = "<%=Command.SAVE%>";
                getCmd();
            }
            
            function cmdListFirst(){
                    document.<%=FrmBenefitMaster.FRM_NAME_BENEFIT_MASTER%>.command.value="<%=Command.FIRST%>";
                    document.<%=FrmBenefitMaster.FRM_NAME_BENEFIT_MASTER%>.prev_command.value="<%=Command.FIRST%>";
                    getCmd();
            }

            function cmdListPrev(){
                    document.<%=FrmBenefitMaster.FRM_NAME_BENEFIT_MASTER%>.command.value="<%=Command.PREV%>";
                    document.<%=FrmBenefitMaster.FRM_NAME_BENEFIT_MASTER%>.prev_command.value="<%=Command.PREV%>";
                    getCmd();
                    }

            function cmdListNext(){
                    document.<%=FrmBenefitMaster.FRM_NAME_BENEFIT_MASTER%>.command.value="<%=Command.NEXT%>";
                    document.<%=FrmBenefitMaster.FRM_NAME_BENEFIT_MASTER%>.prev_command.value="<%=Command.NEXT%>";
                    getCmd();
            }

            function cmdListLast(){
                    document.<%=FrmBenefitMaster.FRM_NAME_BENEFIT_MASTER%>.command.value="<%=Command.LAST%>";
                    document.<%=FrmBenefitMaster.FRM_NAME_BENEFIT_MASTER%>.prev_command.value="<%=Command.LAST%>";
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
                                        <td height="20"> <div id="mn_utama"> <!-- #BeginEditable "contenttitle" -->Benefit Master Configuration<!-- #EndEditable --> </div> </td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td style="background-color:#EEE;">
                                        
                                            <table style="padding:9px; border:1px solid #00CCFF;" <%=garisContent%> width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                <tr>
                                                    <td>
                                                        <form name="<%=FrmBenefitMaster.FRM_NAME_BENEFIT_MASTER%>" method="POST" action="">
                                                        <input type="hidden" name="start" value="<%=start%>">
                                                        <input type="hidden" name="command" value="<%=iCommand%>">
                                                        <input type="hidden" name="benefit_master_id" value="<%=oidBenefitMaster%>">
                                                        <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                                        <input type="hidden" name="start" value="<%=start%>">
                                                        <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                        <table style="color:#373737;">
                                                            <tr>
                                                                <td>
                                                                    <table>
                                                                        <tr>
                                                                            <td>Period From</td>
                                                                            <td>
                                                                                <%=ControlDate.drawDateWithStyle(FrmBenefitMaster.fieldNames[FrmBenefitMaster.FRM_FIELD_PERIOD_FROM], selectedDateFrom != null ? selectedDateFrom : new Date(), 2, -5, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%> 
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td>Period To</td>
                                                                            <td>
                                                                                <%=ControlDate.drawDateWithStyle(FrmBenefitMaster.fieldNames[FrmBenefitMaster.FRM_FIELD_PERIOD_TO], selectedDateTo != null ? selectedDateTo : new Date(), 2, -5, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%>
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td>Code</td>
                                                                            <td><input type="text"  size="50" name="<%=FrmBenefitMaster.fieldNames[FrmBenefitMaster.FRM_FIELD_CODE]%>" value="" /></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td>Title</td>
                                                                            <td><input type="text"  name="<%=FrmBenefitMaster.fieldNames[FrmBenefitMaster.FRM_FIELD_TITLE]%>" value="" /></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td>Description</td>
                                                                            <td><textarea name="<%=FrmBenefitMaster.fieldNames[FrmBenefitMaster.FRM_FIELD_DESCRIPTION]%>"></textarea></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td>company structure</td>
                                                                            <td>
                                                                                <%
                                                                                    for (int c = 0; c < PstBenefitMaster.companyStructurValue.length; c++) {
                                                                                %>
                                                                                <input type="checkbox" name="<%=FrmBenefitMaster.fieldNames[FrmBenefitMaster.FRM_FIELD_COMPANY_STRUCTURE]%>" value="<%=PstBenefitMaster.companyStructurValue[c]%>" />
                                                                                <%=PstBenefitMaster.companyStructurKey[c]%>
                                                                                <%
                                                                                    }
                                                                                %>
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td>Prorate employee presence</td>
                                                                            <td>
                                                                                <%
                                                                                    for (int p = 0; p < PstBenefitMaster.prorateValue.length; p++) {
                                                                                %>
                                                                                <input type="radio" name="<%=FrmBenefitMaster.fieldNames[FrmBenefitMaster.FRM_FIELD_PRORATE_EMPLOYEE_PRESENCE]%>" value="<%=PstBenefitMaster.prorateValue[p]%>" />
                                                                                <%=PstBenefitMaster.prorateKey[p]%>
                                                                                <%
                                                                                    }
                                                                                %>
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td>Employee level point</td>
                                                                            <td>
                                                                                <%
                                                                                    for (int l = 0; l < PstBenefitMaster.levelPointValue.length; l++) {
                                                                                %>
                                                                                <input type="radio" name="<%=FrmBenefitMaster.fieldNames[FrmBenefitMaster.FRM_FIELD_EMPLOYEE_LEVEL_POINT]%>" value="<%=PstBenefitMaster.levelPointValue[l]%>" />
                                                                                <%=PstBenefitMaster.levelPointKey[l]%>
                                                                                    
                                                                                <% }%>
                                                                            </td>
                                                                        </tr>
                                                                        
                                                                        <tr>
                                                                            <td>employee by entitle</td>
                                                                            <td><input type="text" name="<%=FrmBenefitMaster.fieldNames[FrmBenefitMaster.FRM_FIELD_EMPLOYEE_BY_ENTITLE]%>" value="" /></td>
                                                                        </tr>
                                                                            
                                                                        
                                                                    </table>
                                                                </td>
                                                                <td valign="top">
                                                                    <table>
                                                                        <tr>
                                                                            <td colspan="3"><div id="titleTd">Distribution Part</div></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td>
                                                                                <table style="background-color: #DDD; border-radius: 3px; padding: 3px">
                                                                                    <tr>
                                                                                        <td>Part 1</td>
                                                                                        <td><input type="text" name="<%=FrmBenefitMaster.fieldNames[FrmBenefitMaster.FRM_FIELD_DISTRIBUTION_PART_1]%>" value="" /></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td>Code 1</td>
                                                                                        <td><input type="text" name="<%=FrmBenefitMaster.fieldNames[FrmBenefitMaster.FRM_FIELD_DISTRIBUTION_CODE_1]%>" value="" /></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td>Description 1</td>
                                                                                        <td><input type="text" name="<%=FrmBenefitMaster.fieldNames[FrmBenefitMaster.FRM_FIELD_DISTRIBUTION_DESCRIPTION_1]%>" value="" /></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td>Total 1</td>
                                                                                        <td>
                                                                                            <select name="<%=FrmBenefitMaster.fieldNames[FrmBenefitMaster.FRM_FIELD_DISTRIBUTION_TOTAL_1]%>">
                                                                                                <%
                                                                                                    for (int d = 0; d < PstBenefitMaster.distributionTotalValue.length; d++) {
                                                                                                %>
                                                                                                <option value="<%=PstBenefitMaster.distributionTotalValue[d]%>"><%=PstBenefitMaster.distributionTotalKey[d]%></option>
                                                                                                <%
                                                                                                    }
                                                                                                %>
                                                                                            </select>
                                                                                        </td>
                                                                                    </tr>
                                                                                </table>
                                                                            </td>
                                                                            <td>
                                                                                <table style="background-color: #DDD; border-radius: 3px; padding: 3px">
                                                                                    <tr>
                                                                                        <td>Part 2</td>
                                                                                        <td><input type="text" name="<%=FrmBenefitMaster.fieldNames[FrmBenefitMaster.FRM_FIELD_DISTRIBUTION_PART_2]%>" value="" /></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td>Code 2</td>
                                                                                        <td><input type="text" name="<%=FrmBenefitMaster.fieldNames[FrmBenefitMaster.FRM_FIELD_DISTRIBUTION_CODE_2]%>" value="" /></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td>Description 2</td>
                                                                                        <td><input type="text" name="<%=FrmBenefitMaster.fieldNames[FrmBenefitMaster.FRM_FIELD_DISTRIBUTION_DESCRIPTION_2]%>" value="" /></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td>Total 2</td>
                                                                                        <td>
                                                                                            <select name="<%=FrmBenefitMaster.fieldNames[FrmBenefitMaster.FRM_FIELD_DISTRIBUTION_TOTAL_2]%>">
                                                                                                <%
                                                                                                    for (int d = 0; d < PstBenefitMaster.distributionTotalValue.length; d++) {
                                                                                                %>
                                                                                                <option value="<%=PstBenefitMaster.distributionTotalValue[d]%>"><%=PstBenefitMaster.distributionTotalKey[d]%></option>
                                                                                                <%
                                                                                                    }
                                                                                                %>
                                                                                            </select>
                                                                                        </td>
                                                                                    </tr>
                                                                                </table>
                                                                            </td>
                                                                            <td>
                                                                                <table style="background-color: #DDD; border-radius: 3px; padding: 3px">
                                                                                    <tr>
                                                                                        <td>Part 3</td>
                                                                                        <td><input type="text" name="<%=FrmBenefitMaster.fieldNames[FrmBenefitMaster.FRM_FIELD_DISTRIBUTION_PART_3]%>" value="" /></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td>Code 3</td>
                                                                                        <td><input type="text" name="<%=FrmBenefitMaster.fieldNames[FrmBenefitMaster.FRM_FIELD_DISTRIBUTION_CODE_3]%>" value="" /></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td>Description 3</td>
                                                                                        <td><input type="text" name="<%=FrmBenefitMaster.fieldNames[FrmBenefitMaster.FRM_FIELD_DISTRIBUTION_DESCRIPTION_3]%>" value="" /></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td>Total 3</td>
                                                                                        <td>
                                                                                            <select name="<%=FrmBenefitMaster.fieldNames[FrmBenefitMaster.FRM_FIELD_DISTRIBUTION_TOTAL_3]%>">
                                                                                                <%
                                                                                                    for (int d = 0; d < PstBenefitMaster.distributionTotalValue.length; d++) {
                                                                                                %>
                                                                                                <option value="<%=PstBenefitMaster.distributionTotalValue[d]%>"><%=PstBenefitMaster.distributionTotalKey[d]%></option>
                                                                                                <%
                                                                                                    }
                                                                                                %>
                                                                                            </select>
                                                                                        </td>
                                                                                    </tr>
                                                                                </table>
                                                                            </td>
                                                                        </tr>
                                                                    </table>
                                                                    <table>
                                                                        <tr>
                                                                            <td colspan="5"><div id="titleTd">Exception No</div></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td style="padding: 3px; background-color: #EEE; font-weight: bold;">Exception no by category</td>
                                                                            <td style="padding: 3px; background-color: #FFF; font-weight: bold;">Exception no by position</td>
                                                                            <td style="padding: 3px; background-color: #EEE; font-weight: bold;">Exception no by payroll</td>
                                                                            <td style="padding: 3px; background-color: #FFF; font-weight: bold;">Exception no by special leave</td>
                                                                        </tr>
                                                                        <tr>
                                                                            
                                                                            <td>
                                                                                
                                                                                <%
                                                                                    Vector category_value = new Vector(1, 1);
                                                                                    Vector category_key = new Vector(1, 1);
                                                                                    category_value.add("0");
                                                                                    category_key.add("select ...");
                                                                                    Vector listCategory = PstEmpCategory.list(0, 0, "", "");
                                                                                    if (listCategory != null && listCategory.size() > 0) {
                                                                                        for (int i = 0; i < listCategory.size(); i++) {
                                                                                            EmpCategory empCategory = (EmpCategory) listCategory.get(i);
                                                                                            category_key.add(empCategory.getEmpCategory());
                                                                                            category_value.add(String.valueOf(empCategory.getOID()));
                                                                                        }
                                                                                    }
                                                                                %>
                                                                                <%= ControlCombo.draw(FrmBenefitMaster.fieldNames[FrmBenefitMaster.FRM_FIELD_EXCEPTION_NO_BY_CATEGORY],"formElemen",null, "0", category_value, category_key, " size=8 multiple onkeydown=\"javascript:fnTrapKD()\"") %>
                                                                            </td>
                                                                                
                                                                            <td>
                                                                                <%
                                                                                Vector positionValue = new Vector();
                                                                                Vector positionKey = new Vector();
                                                                                positionValue.add("0");
                                                                                positionKey.add("-Select-");
                                                                                Vector listPosition = PstPosition.list(0, 0, "", "");
                                                                                if (listPosition != null && listPosition.size() > 0) {
                                                                                        for (int i = 0; i < listPosition.size(); i++) {
                                                                                            Position position = (Position) listPosition.get(i);
                                                                                            positionKey.add(position.getPosition());
                                                                                            positionValue.add(String.valueOf(position.getOID()));
                                                                                        }
                                                                                    }
                                                                                %>
                                                                                <%= ControlCombo.draw(FrmBenefitMaster.fieldNames[FrmBenefitMaster.FRM_FIELD_EXCEPTION_NO_BY_POSITION],"formElemen",null, "0", positionValue, positionKey, " size=8 multiple onkeydown=\"javascript:fnTrapKD()\"") %>
                                                                            </td>
                                                                                
                                                                            <td>
                                                                                <input type="text" name="<%=FrmBenefitMaster.fieldNames[FrmBenefitMaster.FRM_FIELD_EXCEPTION_NO_BY_PAYROLL]%>" value="" />
                                                                            </td>
                                                                                
                                                                            <td>
                                                                                <input type="text" name="<%=FrmBenefitMaster.fieldNames[FrmBenefitMaster.FRM_FIELD_EXCEPTION_NO_BY_SPECIAL_LEAVE]%>" value="" />
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
                                                        </form>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <%if (listBenefitMaster != null && listBenefitMaster.size() > 0) {%>
                                                    <td>
                                                        <%=drawList(listBenefitMaster, oidBenefitMaster)%>
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

