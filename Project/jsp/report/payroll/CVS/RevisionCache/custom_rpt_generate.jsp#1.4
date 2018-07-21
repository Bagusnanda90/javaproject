<%-- 
    Document    : custom_rpt_generate
    Created on  : Apr 3, 2015, 11:37:15 AM
    Author      : Hendra Putu
    Description : custom rpt generate berguna untuk menampilkan hasil report 
    yang telah disusun di manage data.

================================================================================
    Custom Report adalah suatu module atau fitur yang dapat memudahkan 
    pengguna membuat atau meng-customize report yang diinginkan.
    ada 3 tahapan dalam pembuatan custom report.
    1. Buat terlebih dahulu nama report dan juga pembuatnya.
    2. Manage data atau susun data-data yang ingin ditampilkan.
    3. Generate report yang telah disusun.
    Dari sisi pengguna ini akan terasa mudah, dan sangat menguntungkan, 
    sebab mereka tidak perlu report" lagi menentukan list request yang 
    akan diberikan ke developer app.
    Dari sisi developer, efisien dan efektif dalam resorce dan kecepatan.
================================================================================
--%>

<%@page import="java.math.RoundingMode"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.dimata.harisma.entity.payroll.PaySlipComp"%>
<%@page import="com.dimata.harisma.entity.payroll.PaySlip"%>
<%@page import="com.dimata.harisma.entity.payroll.CustomRptDynamic"%>
<%@page import="com.dimata.harisma.entity.payroll.CustomRptConfig"%>
<%@page import="com.dimata.harisma.entity.payroll.PstCustomRptConfig"%>
<%@page import="com.dimata.harisma.entity.payroll.PstCustomRptMain"%>
<%@page import="com.dimata.harisma.entity.payroll.CustomRptMain"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.payroll.CtrlCustomRptMain"%>
<%@page import="com.dimata.harisma.form.payroll.FrmCustomRptMain"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_PRESENCE_REPORT, AppObjInfo.OBJ_PRESENCE_REPORT);
    int appObjCodePresenceEdit = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_ATTENDANCE, AppObjInfo.OBJ_PRESENCE);
    boolean privUpdatePresence = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodePresenceEdit, AppObjInfo.COMMAND_UPDATE));
%>
<%@ include file = "../../main/checkuser.jsp" %>
<%
    long hrdDepOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    boolean isHRDLogin = hrdDepOid == departmentOid ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
%>
<%!
    /*
    * method name : apostrophe(String value, String opr)
    * Description : memanipulasi value dengan menampahkan 'kutip satu
    */
    public String apostrophe(String value, String opr){
        String str = "";
        if (opr.equals("=")){
            str = "'"+value+"'";
        } else if (opr.equals("BETWEEN")){
            String[] data = new String[5];
            int inc = 0;
            for (String retval : value.split(" ")) {
                data[inc]= retval;
                inc++;
            }
            str = "'"+data[0]+"' AND '"+data[1]+"'";
        } else if (opr.equals("LIKE")){
            str = "'"+value+"'";
        } else if (opr.equals("IN")){
            String stIn = "";
            for (String retval : value.split(",")) {
                stIn += " '"+ retval +"', ";
            }
            stIn += "'0'";
            str += "("+stIn+")";
        } else if (opr.equals("!=")) {
            str = "'"+value+"'";
        } else {
            str = value;
        }
        return str;
    }
    /* Convert double */
    public double convertDouble(String val){
        BigDecimal bDecimal = new BigDecimal(Double.valueOf(val));
        bDecimal = bDecimal.setScale(0, RoundingMode.HALF_DOWN);
        return bDecimal.doubleValue();
    }
    /* Convert int */
    public int convertInteger(double val){
        BigDecimal bDecimal = new BigDecimal(val);
        bDecimal = bDecimal.setScale(0, RoundingMode.HALF_DOWN);
        return bDecimal.intValue();
    }
    /* drawList Table*/
    public String drawList(Vector listField, Vector listRecord, Vector listComp, Vector listComb, long oidCustom, int showValue) {
        /* === get list of SUB TOTAL === */
        String whereSubTotal = " RPT_CONFIG_DATA_TYPE = 3 AND RPT_CONFIG_DATA_GROUP = 0 AND RPT_MAIN_ID ="+oidCustom;
        Vector listSubTotal = PstCustomRptConfig.list(0, 0, whereSubTotal, "");
        String[] countName = new String[listField.size()];
        int[] countIdx = new int[listField.size()];
        /* get name of count by SECTION or DEPARTMENT or else */
        if (listSubTotal != null && listSubTotal.size()>0){
            for(int st=0; st<listSubTotal.size(); st++){
                CustomRptConfig sbt = (CustomRptConfig)listSubTotal.get(st);
                if ("COUNT".equals(sbt.getRptConfigTableGroup())){
                    countName[st] = sbt.getRptConfigTableName();
                }
            }
        }
        /* Some of array variable */
        String[][] arrField = new String[listRecord.size()+1][listField.size()]; /* for dinamic fields */
        double[][] totalComp = new double[listRecord.size()][listComp.size()]; /* for total salary component */
        double[] totalCombine = new double[listRecord.size()]; /* for total combine */
        /* update by Hendra Putu | 2015-11-05 */
        double countValueComp = 0;
        boolean[] showRecord = new boolean[listRecord.size()];
       
        /* mengambil field dinamis */
        /* Check count and get index */
        int idx = 0;
        for(int i=0;i<listField.size(); i++){
            CustomRptConfig fields = (CustomRptConfig)listField.get(i);              
            for(int cn=0; cn<countName.length; cn++){
                if (fields.getRptConfigFieldName().equals(countName[cn])){
                    countIdx[idx] = i;
                    idx++;
                }
            }
        }
        
        /* 
        ===============================================================================
        * listRecord berisi baris data hasil query dan akan ditampung ke variabel array 
        ===============================================================================
        */
        if (listRecord != null && listRecord.size()>0){
            for(int y=0; y<listRecord.size(); y++){
                CustomRptDynamic dyc = (CustomRptDynamic)listRecord.get(y);
                /* Get dinamis field */
                if (listField != null && listField.size() > 0){
                    for(int i=0;i<listField.size(); i++){
                        CustomRptConfig rpt = (CustomRptConfig)listField.get(i);
                        /* jika field name != PAY_SLIP_ID */
                        if(!rpt.getRptConfigFieldName().equals("PAY_SLIP_ID")){       
                            arrField[y][i] = dyc.getField(rpt.getRptConfigFieldName());
                        }  
                    }
                }
                /* get value field */
                for(int k=0; k<listComp.size(); k++){
                    CustomRptConfig rptComp = (CustomRptConfig)listComp.get(k);                    
                    totalComp[y][k] = convertDouble(dyc.getField(rptComp.getRptConfigFieldName()));
                    countValueComp += totalComp[y][k];
                }
                /* get value field combine */
                for(int l=0; l<listComb.size(); l++){
                    CustomRptConfig rptComb = (CustomRptConfig)listComb.get(l);
                    totalCombine[y] = convertDouble(dyc.getField(rptComb.getRptConfigFieldName()));
                }
                if (showValue == 0){
                   showRecord[y] = true;
                } else {
                    if (countValueComp == 0){
                        showRecord[y] = false;
                    } else {
                        showRecord[y] = true;
                    }
                }
                countValueComp = 0;
            }
        }
        /* END of List Record */
        int[] countTotal = new int[idx];
        boolean[] countFind = new boolean[idx];
        /* inisialisasi */
        for(int d=0; d<idx; d++){
            countTotal[d] = 1;
            countFind[d] = false;
        }
        int nomor = 0;
        int yStart = 0;
        int yEnd = 0;
        double total = 0;
        boolean ketemu = false;
        String table = "<table class='tblStyle'>";
        table +="<tr>";
        table +="<td class='title_tbl'>No</td>";
        /* mengambil field dinamis */
        for(int i=0;i<listField.size(); i++){
            CustomRptConfig fields = (CustomRptConfig)listField.get(i);    
            if(!fields.getRptConfigFieldName().equals("PAY_SLIP_ID")){
                table +="<td class='title_tbl'>"+fields.getRptConfigFieldHeader()+"</td>";
            }
        }
        /* mengambil field salary component */
        for(int j=0; j<listComp.size(); j++){
            CustomRptConfig comp = (CustomRptConfig)listComp.get(j);
            table +="<td class='title_tbl'>"+comp.getRptConfigFieldHeader()+"</td>";
        }
        /* mengambil field combine */
        for(int k=0; k<listComb.size(); k++){
            CustomRptConfig comb = (CustomRptConfig)listComb.get(k);
            table +="<td class='title_tbl'>"+comb.getRptConfigFieldHeader()+"</td>";
        }
        table +="</tr>";
        /* ============== record result ==============*/
        if (listRecord != null && listRecord.size()>0){
            for(int y=0; y<listRecord.size(); y++){
                if (showRecord[y] == true){
                    nomor++;
                    table +="<tr>";
                    table +="<td>"+nomor+"</td>";
                    /* Fields dinamic */
                    if (listField != null && listField.size() > 0){
                        for(int i=0;i<listField.size(); i++){
                            if (arrField[y][i] != null){
                                table +="<td>"+arrField[y][i]+"</td>";
                            }
                        }
                        /* count record by ... */
                        for(int sb=0; sb<idx; sb++){
                            if (arrField[y][countIdx[sb]].equals(arrField[y+1][countIdx[sb]])){
                                countTotal[sb] = countTotal[sb] + 1;
                            } else {
                                countFind[sb] = true;
                                ketemu = true;
                                yEnd = y+1;
                            }
                        }
                    }

                    for(int k=0; k<listComp.size(); k++){
                        table +="<td>"+convertInteger(totalComp[y][k])+"</td>";
                    }
                    for(int l=0; l<listComb.size(); l++){
                        table +="<td>"+convertInteger(totalCombine[y])+"</td>";
                    }
                    table +="</tr>";
                    table +="<tr>";

                    boolean addColoum = false;
                    if (ketemu == true){
                        for(int f=0; f<idx; f++){
                            if (countFind[f] == true){
                                if (addColoum == false){
                                    for(int a=0; a<countIdx[f]+1; a++){
                                        table +="<td id='tdTotal'>&nbsp;</td>";
                                    }
                                    addColoum = true;
                                }
                                table +="<td id='tdTotal'>"+countTotal[f]+"</td>";

                                countFind[f] = false;
                                countTotal[f] = 1;
                            }
                        }
                        addColoum = false;
                        for(int b=0; b<listField.size()-(countIdx[idx-1]+2); b++){
                            table +="<td id='tdTotal'>&nbsp;</td>";
                        }
                        for(int kolom=0; kolom<listComp.size(); kolom++){
                            for(int x=yStart; x<yEnd; x++){
                                total += totalComp[x][kolom];
                            }
                            table +="<td id='tdTotal'>"+convertInteger(total)+"</td>";
                            total = 0;
                        }
                        for(int x=yStart; x<yEnd; x++){
                            total += totalCombine[x];
                        }
                        table +="<td id='tdTotal'>"+convertInteger(total)+"</td>";
                        total = 0;
                        ketemu = false;
                    }
                    table +="</tr>";
                    yStart = yEnd;
                }
            }
            table += "<tr>";
            table += "<td class='td_grand_total' colspan=\""+(listField.size())+"\">Grand Total</td>";
            /* mengambil field salary component */
            double grandTotalComp = 0;
            for(int j=0; j<listComp.size(); j++){
                for(int y=0; y<listRecord.size(); y++){
                    grandTotalComp = grandTotalComp + totalComp[y][j];
                }
                table +="<td class='td_grand_total'>"+convertInteger(grandTotalComp)+"</td>";
                grandTotalComp = 0;
            }
            /* mengambil field combine */
            double grandTotalComb = 0;
            
            for(int y=0; y<listRecord.size(); y++){
                grandTotalComb = grandTotalComb +  totalCombine[y];
            }
            if (grandTotalComb != 0){
                table +="<td class='td_grand_total'>"+convertInteger(grandTotalComb)+"</td>";
            }
            
            table += "</tr>";
        }
        table +="</table>";
        
        return table;

    }
%>
<!DOCTYPE html>
<%  

    /* Update by Hendra Putu | 20150403 */
    int iCommand = FRMQueryString.requestCommand(request);
    long oidCustom = FRMQueryString.requestLong(request, "oid_custom");
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    int generate = FRMQueryString.requestInt(request, "generate");
    int selectionChoose = FRMQueryString.requestInt(request, "selection_choose");
    String[] whereField = FRMQueryString.requestStringValues(request, "where_field");
    String[] whereValue = FRMQueryString.requestStringValues(request, "where_value");
    String[] whereType = FRMQueryString.requestStringValues(request, "where_type");
    String[] operator = FRMQueryString.requestStringValues(request, "operator");
    String whereCustom = FRMQueryString.requestString(request, "where_custom");
    /* Update by Hendra Putu | 2015-11-05 */
    int showValue = FRMQueryString.requestInt(request, "show_value");
    /* get Custom Report Title */
    CustomRptMain customRptMain = new CustomRptMain();
    if (oidCustom > 0){
        customRptMain = PstCustomRptMain.fetchExc(oidCustom);
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Custom Report Generate</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <link href="<%=approot%>/stylesheets/superTables.css" rel="Stylesheet" type="text/css" /> 
        <style type="text/css">
            #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3;}
            #menu_title {color:#0099FF; font-size: 14px; font-weight: bold;}
            #menu_teks {color:#CCC;}
            #mn_utama {color: #FF6600; padding: 5px 14px; border-left: 1px solid #999; font-size: 14px; background-color: #F5F5F5;}
            #btn {
              background: #C7C7C7;
              border: 1px solid #BBBBBB;
              border-radius: 3px;
              font-family: Arial;
              color: #474747;
              font-size: 11px;
              padding: 3px 7px;
              cursor: pointer;
            }

            #btn:hover {
              color: #FFF;
              background: #B3B3B3;
              border: 1px solid #979797;
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
            .td_grand_total {font-weight: bold; background-color: #0096bb; color: #F5F5F5;}
            #confirm {background-color: #fad9d9;border: 1px solid #da8383; color: #bf3c3c; padding: 14px 21px;border-radius: 5px;}
            #td1 {border: 1px solid #CCC; background-color: #DDD;}
            #td2 {border: 1px solid #CCC;}
            #tdTotal {background-color: #fad9d9;}
            #query {
                padding: 7px 9px; color: #f7f7f7; background-color: #797979; 
                border:1px solid #575757; border-radius: 5px; 
                margin-bottom: 5px; font-size: 12px;
                font-family: Courier New,Courier,Lucida Sans Typewriter,Lucida Typewriter,monospace;
            }
            #info {
                width: 500px;
                padding: 21px; color: #797979; background-color: #F7F7F7;
                border:1px solid #DDD;
                font-size: 12px;
            }
            
            .LockOff {
                display: none;
                visibility: hidden;
            }

            .LockOn {
                display: block;
                visibility: visible;
                position: absolute;
                z-index: 999;
                top: 0px;
                left: 0px;
                width: 105%;
                height: 105%;
                background-color: #ccc;
                text-align: center;
                padding-top: 20%;
                filter: alpha(opacity=75);
                opacity: 0.75;
                font-size: 250%;
            }
        </style>
        <script type="text/javascript">
            function getCmd(){
                document.form_generate.action = "custom_rpt_generate.jsp";
                document.form_generate.submit();
            }
            function cmdGenerate(val){             
                document.form_generate.command.value="<%=Command.ADD%>"; 
                document.form_generate.selection_choose.value=val;
                document.form_generate.generate.value="1";
                getCmd();
                lockScreen('We are processing generate report, please wait...');
            }
            function cmdBack() {
                document.form_generate.command.value="<%=Command.BACK%>";               
                document.form_generate.action="custom_rpt_main.jsp";
                document.form_generate.submit();
            }
            function cmdClear() {
                document.form_generate.command.value="<%=Command.CANCEL%>";
                document.form_generate.generate.value="0";
                document.form_generate.action="custom_rpt_generate.jsp";
                document.form_generate.submit();
            }
            function cmdShowQuery(data){
                document.form_generate.query_result.value=data;

                document.form_generate.action = "custom_rpt_query.jsp","ShowQuery", "height=457,width=457,status=yes,toolbar=no,menubar=no,location=center,scrollbars=yes";
                document.form_generate.submit();
            }
            function cmdExportToExcel(){
                document.form_generate.action="<%=printroot%>.report.payroll.CustomRptXLS"; 
                document.form_generate.target = "ReportExcel";
                document.form_generate.submit();
            }
            function cmdSendExcel(){
                document.form_generate.action="<%=printroot%>.report.payroll.CustomRptXLSEmail"; 
                document.form_generate.target = "ReportExcel";
                document.form_generate.submit();
            }
            function cmdChooseSelection(val){
                document.form_generate.generate.value="0";
                document.form_generate.selection_choose.value=val;
                document.form_generate.action="custom_rpt_generate.jsp";
                document.form_generate.submit();
            }
            function cmdPosted()
            {
              document.form_generate.command.value="<%=Command.SAVE%>";
              document.form_generate.action="custom_rpt_generate.jsp";
              document.form_generate.submit();
              lockScreen('We are posting the journals, please wait...');
            }

            function lockScreen(str)
            {
               var lock = document.getElementById('theLockPane');
               if (lock)
                  lock.className = 'LockOn';

               lock.innerHTML = str;
            }
        </script>
    </head>
    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
        <div id="theLockPane" class="LockOff"></div> 
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
                            <td width="100%" colspan="3" valign="top" style="padding: 12px"> 
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td height="20"> <div id="menu_utama"><button id="btn" onclick="cmdBack()">Back</button>&nbsp; <span id="menu_title"><%=customRptMain.getRptMainTitle()%></span> - <span id="menu_teks">Custom Report Generate</span> <!-- #EndEditable --> </div> </td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td style="background-color:#EEE;" valign="top">
                                        
                                            <table style="padding:9px; border:1px solid #00CCFF;" <%=garisContent%> width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                
                                                <tr>
                                                    <td valign="top">
                                                        <form name="form_generate" method="POST" action="">
                                                            <input type="hidden" name="command" value="<%=iCommand%>" />
                                                            <input type="hidden" name="oid_custom" value="<%=oidCustom%>" />
                                                            <input type="hidden" name="start" value="<%=start%>" />
                                                            <input type="hidden" name="prev_command" value="<%=prevCommand%>" />
                                                            <input type="hidden" name="generate" value="<%=generate%>" />
                                                            <input type="hidden" name="query_result" value="" />
                                                            <input type="hidden" name="selection_choose" value="" />
                                                            
                                                           
                                                            <table>
                                                                
                                                                <tr>
                                                                    <td colspan="2">
                                                                        <div id="info">
                                                                            <h2>Operator Description</h2>
                                                                            <p><strong>=, !=, >, <, >=, <=</strong><br />
                                                                                This operator is used for only one of data, either number or letters. 
                                                                                Example : name = 'John' or Salary = 1000. But in this field, just type value of data.
                                                                                Example : John or 219.
                                                                            </p>
                                                                            <p><strong>IN</strong><br />
                                                                                It is used for some of data which it have same type. Example: IN(123, 456, 78) or IN('abc', 'def'). Just type test, text, etc.
                                                                            </p>
                                                                            <p><strong>BETWEEN</strong><br />
                                                                                The BETWEEN operator is used when you want to get data by range. E.g: 2014-12-01 To 2015-01-01.
                                                                                So you just type 2014-12-01 2015-01-01. ([date_from][space][date_to]) Note: the between operator is used if the value is date.
                                                                            </p>
                                                                            <p><strong>LIKE</strong><br />
                                                                                You can type not complete value. E.g: You want to find some of data that has letter 'a', so you must type: %a%</p>                                                                         
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td>
                                                                        <%
                                                                        if (selectionChoose == 0){
                                                                            %>
                                                                            <input type="radio" name="choose" value="0" onclick="cmdChooseSelection(this.value)" checked="checked" />Default
                                                                            &nbsp;
                                                                            <input type="radio" name="choose" value="1" onclick="cmdChooseSelection(this.value)" />Custom
                                                                            <%
                                                                        } else {
                                                                            %>
                                                                            <input type="radio" name="choose" value="0" onclick="cmdChooseSelection(this.value)" />Default
                                                                            &nbsp;
                                                                            <input type="radio" name="choose" value="1" onclick="cmdChooseSelection(this.value)" checked="checked" />Custom
                                                                            <%
                                                                        }
                                                                        %>
                                                                        
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td>
                                                                        Show 0 value : <input type="radio" name="show_value" checked="checked" value="0" />Yes | 
                                                                        <input type="radio" name="show_value" value="1" />No
                                                                    </td>
                                                                </tr>
                                                                <tr><td>&nbsp;</td></tr>
                                                                <%
                                                                if (selectionChoose == 0){
                                                                %>
                                                                <tr>
                                                                    <td colspan="2">
                                                                        <h2>Selection default</h2>
                                                                        <table>
                                                                        <%
                                                                        if (oidCustom != 0){
                                                                            String whereList = " RPT_CONFIG_DATA_TYPE = 0 AND RPT_CONFIG_DATA_GROUP = 1 AND RPT_MAIN_ID ="+oidCustom;
                                                                            Vector listWhere = PstCustomRptConfig.list(0, 0, whereList, "");
                                                                            if (listWhere != null && listWhere.size()>0){
                                                                                for(int i=0; i<listWhere.size(); i++){
                                                                                    CustomRptConfig selection = (CustomRptConfig)listWhere.get(i);

                                                                                    %>
                                                                                    <tr>
                                                                                        <td>
                                                                                            <%=selection.getRptConfigFieldHeader()%>
                                                                                        </td>
                                                                                        <td>
                                                                                            <input type="hidden" name="where_field" value="<%=selection.getRptConfigTableName()+"."+selection.getRptConfigFieldName()%>" />
                                                                                            <select name="operator">
                                                                                                <option value="=">=</option>
                                                                                                <option value="!=">!=</option>
                                                                                                <option value=">">></option>
                                                                                                <option value="<"><</option>
                                                                                                <option value=">=">>=</option>
                                                                                                <option value="<="><=</option>
                                                                                                <option value="BETWEEN">BETWEEN</option>
                                                                                                <option value="LIKE">LIKE</option>
                                                                                                <option value="IN">IN</option>
                                                                                            </select>
                                                                                            <input type="text" name="where_value" value="" size="70" />
                                                                                            <input type="hidden" name="where_type" value="<%=selection.getRptConfigFieldType()%>" />
                                                                                        </td>
                                                                                    </tr>
                                                                                    <%
                                                                                }
                                                                            }
                                                                        }
                                                                        %>
                                                                        </table>
                                                                    </td>
                                                                </tr>
                                                                <% } else { %>
                                                                <tr>
                                                                    <td colspan="2">
                                                                        <h2>Selection Custom</h2>
                                                                        <textarea name="where_custom" rows="5" cols="52">
                                                                            
                                                                        </textarea> 
                                                                    </td>
                                                                </tr>
                                                                <% } %>
                                                                
                                                                <tr>
                                                                    <td colspan="2"><button id="btn" onclick="cmdGenerate(<%=selectionChoose%>)">Generate</button>&nbsp;<button id="btn" onclick="cmdExportToExcel()">Export to Excel</button>&nbsp;<button id="btn" onclick="cmdClear()">Clear</button>&nbsp;<button id="btn" onclick="cmdSendExcel()">Send Excel</button>&nbsp;  Email : <input type="text" name="emailAddress" placeholder="alamat email" value="" /></td>
                                                                </tr>
                                                                
                                                            </table>
                                                        </form>
                                                    </td>
                                                </tr>
                                                <%
                                                if (generate == 1){
                                                %>
                                                <tr>
                                                    <td>
                                                        <%
                                                        /*
                                                        * Description : Code to find WHERE DATA 
                                                        */
                                                        String whereClause = "";
                                                        if (selectionChoose == 0){
                                                            String valueInp = "";
                                                            String[] dataWhere = new String[whereValue.length];
                                                            int a = 0;
                                                            /* Jika input selection tidak kosong */
                                                            if (whereValue != null && whereValue.length > 0){
                                                                whereClause = " WHERE ";
                                                                /* Ulang sebanyak jumlah field */
                                                                for(int w=0; w<whereField.length; w++){
                                                                    if (whereValue[w].length() > 0){
                                                                        /* Jika tipe data field = String maka..*/
                                                                        if (whereType[w].equals("1")){
                                                                            /* manipulasi data dengan menambahkan apostrophe (') */
                                                                            valueInp = apostrophe(whereValue[w], operator[w]);
                                                                        } else {
                                                                            valueInp = whereValue[w];
                                                                        }
                                                                        dataWhere[a] = whereField[w] +" "+ operator[w] +" "+ valueInp + " ";
                                                                        a++;
                                                                    }
                                                                }
                                                                for(int x=0; x < a; x++){
                                                                    whereClause += dataWhere[x];
                                                                    if (x == a-1){
                                                                        whereClause +=" ";
                                                                    } else {
                                                                        whereClause += " AND ";
                                                                    }
                                                                }
                                                            }
                                                        } else {
                                                            whereCustom = whereCustom.replace("#", "'");
                                                            whereClause = " "+ whereCustom + " ";
                                                        }
                                                        /*
                                                        * Description : Get SELECT DATA and JOIN DATA
                                                        * Date : 2015-04-08 
                                                        */
                                                        String[] dataJoin = new String[5];
                                                        
                                                        String strSelect = "SELECT ";
                                                        String strJoin = "";
                                                        String strOrderBy = "";
                                                        int inc = 0;
                                                        /* WHERE CLAUSE WITH RPT_CONFIG_DATA_TYPE = 0 */
                                                        String where = " RPT_CONFIG_DATA_TYPE = 0 AND RPT_CONFIG_DATA_GROUP = 0 AND RPT_MAIN_ID ="+oidCustom;
                                                        Vector listData = PstCustomRptConfig.list(0, 0, where, "");
                                                        /* WHERE CLAUSE WITH RPT_CONFIG_DATA_TYPE = 2 */
                                                        String whereComp = " RPT_CONFIG_DATA_TYPE = 2 AND RPT_CONFIG_DATA_GROUP = 0 AND RPT_MAIN_ID ="+oidCustom;
                                                        Vector listSalaryComp = PstCustomRptConfig.list(0, 0, whereComp, "");
                                                        /* WHERE CLAUSE WITH RPT_CONFIG_DATA_TYPE = 1 */
                                                        String whereComb = " RPT_CONFIG_DATA_TYPE = 1 AND RPT_CONFIG_DATA_GROUP = 0 AND RPT_MAIN_ID ="+oidCustom;
                                                        Vector listComb = PstCustomRptConfig.list(0, 0, whereComb, "");
                                                        /* Array join */
                                                        int[] joinCollection = new int[PstCustomRptConfig.joinDataPriority.length];
                                                        /* inisialisasi joinCollection */
                                                        for(int d=0; d<PstCustomRptConfig.joinDataPriority.length; d++){
                                                            joinCollection[d] = -1;
                                                        }
                                                        boolean found = false;
                                                        if (listData != null && listData.size() > 0){
                                                            int incC = 0;
                                                            for(int i=0;i<listData.size(); i++){
                                                                CustomRptConfig rpt = (CustomRptConfig)listData.get(i);
                                                                /* mendapatkan join data */
                                                                for(int k=0; k<PstCustomRptConfig.joinData.length; k++){
                                                                    for (String retval : PstCustomRptConfig.joinData[k].split(" ")) {
                                                                        dataJoin[inc] = retval;
                                                                        inc++;
                                                                    }
                                                                    inc = 0;
                                                                    /* bandingkan nilai table dengan data join */
                                                                    if (rpt.getRptConfigTableName().equals(dataJoin[2])){
                                                                        /* cek data join pada array joinCollection */
                                                                        for(int c=0; c<joinCollection.length; c++){
                                                                            if (PstCustomRptConfig.joinDataPriority[k] == joinCollection[c]){
                                                                                found = true; /* jika found == true, maka data sudah ada di joinCollection */
                                                                            }
                                                                        }
                                                                        if (found == false){
                                                                            joinCollection[incC] = PstCustomRptConfig.joinDataPriority[k];
                                                                        }
                                                                        found = false;
                                                                    }
                                                                }
                                                                incC++;
                                                                /* mendapatkan data select */
                                                                strSelect += rpt.getRptConfigTableName()+"."+rpt.getRptConfigFieldName();
                                                                if (i == listData.size()-1){
                                                                    strSelect +=" ";
                                                                } else {
                                                                    strSelect += ", ";
                                                                }
                                                            }
                                                            /* join Collection */
                                                            Arrays.sort(joinCollection); /* sorting array */
                                                            for(int m=0; m<joinCollection.length; m++){
                                                                if (joinCollection[m] != -1){
                                                                    strJoin += PstCustomRptConfig.joinData[joinCollection[m]] + " ";
                                                                }
                                                            }
                                                        }
                                                        /* ORDER BY */
                                                        String whereOrder = " RPT_CONFIG_DATA_TYPE = 0 AND RPT_CONFIG_DATA_GROUP = 2 AND RPT_MAIN_ID ="+oidCustom;
                                                        Vector listOrder = PstCustomRptConfig.list(0, 0, whereOrder, "");
                                                        if (listOrder != null && listOrder.size() > 0){
                                                            strOrderBy = " ORDER BY ";
                                                            for(int ord=0;ord<listOrder.size(); ord++){
                                                                CustomRptConfig rptOrder = (CustomRptConfig)listOrder.get(ord);
                                                                strOrderBy += rptOrder.getRptConfigTableName() + "." + rptOrder.getRptConfigFieldName();
                                                                if (ord == listOrder.size()-1){
                                                                    strOrderBy +=" ";
                                                                } else {
                                                                    strOrderBy +=", ";
                                                                }
                                                                
                                                            }
                                                        }
                                                        %>
                                                        <div id="query"><%=strSelect+" FROM hr_employee "+strJoin+" "+whereClause+ strOrderBy%></div>
                                                        <%
                                                        Vector listResult = PstCustomRptConfig.listData(strSelect+" FROM hr_employee "+strJoin+" "+whereClause + strOrderBy, listData);
                                                        
                                                        %>
                                                        <div><%=drawList(listData, listResult, listSalaryComp, listComb, oidCustom, showValue)%></div>
                                                        
                                                    </td>
                                                </tr>
                                                <% } %>
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
        <div id="theLockPane" class="LockOff"></div> 
    </body>
    <!-- #BeginEditable "script" --> <script language="JavaScript">
                var oBody = document.body;
                var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
    </script>
                
    <!-- #EndEditable --> <!-- #EndTemplate -->
</html>
<!--


-->