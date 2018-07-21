<%-- 
    Document   : search_amount_emp
    Created on : Jan 9, 2016, 9:22:31 AM
    Author     : Dimata 007
--%>
<%@page import="com.dimata.harisma.entity.payroll.PayComponent"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayComponent"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.math.RoundingMode"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.dimata.harisma.entity.payroll.Value_Mapping"%>
<%@page import="com.dimata.harisma.entity.payroll.PstValue_Mapping"%>
<%@page import="com.dimata.harisma.report.EmployeeAmountXLS"%>
<%@ include file = "../main/javainit.jsp" %> 
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_DEPARTMENT); %>
<%@ include file = "../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%!
    public String getDivisionName(long divisionId){
        String name = "-";
        if (divisionId != 0) {
            try {
                Division division = PstDivision.fetchExc(divisionId);
                name = division.getDivision();
            } catch (Exception e) {
                System.out.println("Division Name =>" + e.toString());
            }
        }
        return name;
    }
    
    public double convertDouble(String val){
        BigDecimal bDecimal = new BigDecimal(Double.valueOf(val));
        bDecimal = bDecimal.setScale(0, RoundingMode.HALF_UP);
        return bDecimal.doubleValue();
    }
    /* Convert int */
    public int convertInteger(int scale, double val){
        BigDecimal bDecimal = new BigDecimal(val);
        bDecimal = bDecimal.setScale(scale, RoundingMode.HALF_UP);
        return bDecimal.intValue();
    }
    
    public String getNextDate(Date dateInp){
        String nextDate = "-";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); /*new SimpleDateFormat("dd MMMM yyyy");*/
            Calendar c = Calendar.getInstance();
            c.setTime(dateInp);
            c.add(Calendar.DATE, 1);  // number of days to add
            nextDate = sdf.format(c.getTime());  // dt is now the new date
            //String[] arrEndDate = nextDate.split("-");
            //intWorkFrom = Integer.valueOf(arrEndDate[0] + arrEndDate[1] + arrEndDate[2]);
        } catch(Exception e){
            System.out.println("Date=>"+e.toString());
        }
        return nextDate;
    }
    
    public double customFormat(String pattern, double value ) {
        String out = "";
        double outDouble = 0;
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        String output = myFormatter.format(value);
        outDouble = Double.valueOf(output);
        out = value + "  " + pattern + "  " + output;
        return outDouble;
   }

%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    String[] divisionSelect = FRMQueryString.requestStringValues(request, "division_select");
    String[] levelSelect = FRMQueryString.requestStringValues(request, "level_select");
    String[] categorySelect = FRMQueryString.requestStringValues(request, "category_select");
    String[] positionSelect = FRMQueryString.requestStringValues(request, "position_select");
    int chooseBy = FRMQueryString.requestInt(request, "choose_by");
    
    String dateFrom = FRMQueryString.requestString(request, "datefrom");
    String dateTo = FRMQueryString.requestString(request, "dateto");
    
    EmployeeAmountXLS empAmount = new EmployeeAmountXLS();
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Amount of Employee</title>
        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <style type="text/css">
            .tblStyle {border-collapse: collapse; font-size: 12px; }
            .tblStyle td {padding: 5px 7px; border: 1px solid #CCC; font-size: 12px; background-color: #FFF;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            .title_page {color:#0db2e1; font-weight: bold; font-size: 14px; background-color: #EEE; border-left: 1px solid #0099FF; padding: 12px 18px;}

            body {color:#373737;}
            #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3;}
            #menu_title {color:#0099FF; font-size: 14px;}
            #menu_teks {color:#CCC;}
            
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
            .title_part {color:#FF7E00; background-color: #F7F7F7; border-left: 1px solid #0099FF; padding: 9px 11px;}
            
            body {background-color: #EEE;}
            .header {
                
            }
            .content-main {
                padding: 21px 32px;
                margin: 0px 23px 59px 23px;
            }
            .content-info {
                padding: 21px;
                border-bottom: 1px solid #E5E5E5;
            }
            .content-title {
                padding: 21px;
                border-bottom: 1px solid #E5E5E5;
                margin-bottom: 5px;
            }
            #title-large {
                color: #575757;
                font-size: 16px;
                font-weight: bold;
            }
            #title-small {
                color:#797979;
                font-size: 11px;
            }
            .content {
                padding: 21px;
            }
            .box {
                margin: 17px 7px;
                background-color: #FFF;
                color:#575757;
            }
            #box_title {
                padding:21px; 
                font-size: 14px; 
                color: #007fba;
                border-top: 1px solid #28A7D1;
                border-bottom: 1px solid #EEE;
            }
            #box_content {
                padding:21px; 
                font-size: 12px;
                color: #575757;
            }
            .box-info {
                padding:21px 43px; 
                background-color: #F7F7F7;
                border-bottom: 1px solid #CCC;
                -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, 0.065);
                 -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, 0.065);
                      box-shadow: 0 1px 4px rgba(0, 0, 0, 0.065);
            }
            #title-info-name {
                padding: 11px 15px;
                font-size: 35px;
                color: #535353;
            }
            #title-info-desc {
                padding: 7px 15px;
                font-size: 21px;
                color: #676767;
            }
            .btn {
                background-color: #00a1ec;
                border-radius: 3px;
                font-family: Arial;
                color: #EEE;
                font-size: 12px;
                padding: 7px 11px 7px 11px;
                text-decoration: none;
            }

            .btn:hover {
                color: #FFF;
                background-color: #007fba;
                text-decoration: none;
            }
            
            .btn-small {
                font-family: sans-serif;
                text-decoration: none;
                padding: 5px 7px; 
                background-color: #676767; color: #F5F5F5; 
                font-size: 11px; cursor: pointer;
                border-radius: 3px;
            }
            .btn-small:hover { background-color: #474747; color: #FFF;}
            
            .btn-small-1 {
                font-family: sans-serif;
                text-decoration: none;
                padding: 5px 7px; 
                background-color: #EEE; color: #575757; 
                font-size: 11px; cursor: pointer;
                border-radius: 3px;
            }
            .btn-small-1:hover { background-color: #DDD; color: #474747;}
            
            .tbl-main {border-collapse: collapse; font-size: 11px; background-color: #FFF; margin: 0px;}
            .tbl-main td {padding: 4px 7px; border: 1px solid #DDD; }
            #tbl-title {font-weight: bold; background-color: #F5F5F5; color: #575757;}
            
            .tbl-small {border-collapse: collapse; font-size: 11px; background-color: #FFF;}
            .tbl-small td {padding: 2px 3px; border: 1px solid #DDD; }
            
            #caption {
                font-size: 12px;
                color: #474747;
                font-weight: bold;
                padding: 2px 0px 3px 0px;
            }
            #divinput {
                margin-bottom: 5px;
                padding-bottom: 5px;
            }
            
            #level_select {
                margin-bottom: 5px;
                padding-bottom: 5px;
                visibility: hidden;
            }
            
            #category_select {
                margin-bottom: 5px;
                padding-bottom: 5px;
                visibility: hidden;
            }
            
            #position_select {
                margin-bottom: 5px;
                padding-bottom: 5px;
                visibility: hidden;
            }
            
            #div_item_sch {
                background-color: #EEE;
                color: #575757;
                padding: 5px 7px;
            }
            
            #record_count{
                font-size: 12px;
                font-weight: bold;
                padding-bottom: 9px;
            }
            #box-form {
                background-color: #EEE; 
                border-radius: 5px;
            }
            .form-style {
                font-size: 12px;
                color: #575757;
                border: 1px solid #DDD;
                border-radius: 5px;
            }
            .form-title {
                padding: 11px 21px;
                text-align: left;
                border-bottom: 1px solid #DDD;
                background-color: #FFF;
                border-top-left-radius: 5px;
                border-top-right-radius: 5px;
                font-weight: bold;
            }
            .form-content {
                text-align: left;
                padding: 21px;
                background-color: #DDD;
            }
            .form-footer {
                border-top: 1px solid #DDD;
                padding: 11px 21px;
                background-color: #FFF;
                border-bottom-left-radius: 5px;
                border-bottom-right-radius: 5px;
            }
            #confirm {
                padding: 13px 17px;
                background-color: #FF6666;
                color: #FFF;
                border-radius: 3px;
                font-size: 12px;
                font-weight: bold;
                visibility: hidden;
            }
            #btn-confirm {
                padding: 4px 9px; border-radius: 3px;
                background-color: #CF5353; color: #FFF; 
                font-size: 11px; cursor: pointer;
            }
            .footer-page {
                font-size: 12px; 
            }
            
        </style>
        <script type="text/javascript">
            function getExportToExcel(){
                document.frm.action="<%=printroot%>.report.EmployeeAmountXLS"; 
                document.frm.target = "ReportExcel";
                document.frm.submit();
            }
            
            function getAmount(){
                document.frm.command.value="<%= Command.LIST %>";
                document.frm.action="search_amount_emp.jsp";
                document.frm.submit();
            }
            
            function cmdCheck(nilai){
                switch(nilai){
                    case 0:
                        document.getElementById("level_select").style.visibility="visible";
                        document.getElementById("category_select").style.visibility="hidden";
                        document.getElementById("position_select").style.visibility="hidden";
                        break;
                    case 1:
                        document.getElementById("level_select").style.visibility="hidden";
                        document.getElementById("category_select").style.visibility="visible";
                        document.getElementById("position_select").style.visibility="hidden";
                        break;
                    case 2:
                        document.getElementById("level_select").style.visibility="hidden";
                        document.getElementById("category_select").style.visibility="hidden";
                        document.getElementById("position_select").style.visibility="visible";
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
        function pageLoad(){ $(".mydate").datepicker({ dateFormat: "yy-mm-dd" }); } 
	</script>
    </head>
    <body onload="pageLoad()">
        <div class="header">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
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
            </table>
        </div>
        <div id="menu_utama">
            <span id="menu_title">Masterdata <strong style="color:#333;"> / </strong>Pencarian Jumlah Karyawan</span>
        </div>
        <div class="content-main">
            <form name="frm" method="post" action="">
                <input type="hidden" name="command" value="<%=iCommand%>">
                <table class="tblStyle">
                    <tr><td colspan="4" style="color: #474747; font-weight: bold;">Date From <input type="text" name="datefrom" class="mydate" /> to <input type="text" name="dateto" class="mydate" /></td></tr>
                    <tr><td colspan="4" style="color: #474747; font-weight: bold;">If you want to select multiple data, click [Ctrl] and then select items</td></tr>
                    <tr>
                        <td valign="top"><div id="caption"><%=dictionaryD.getWord(I_Dictionary.DIVISION)%></div></td>
                        <td valign="top"><div id="caption"><input type="radio" name="choose_by" id="choose_by" onclick="cmdCheck(0)" value="0" /> <%=dictionaryD.getWord(I_Dictionary.LEVEL)%></div></td>
                        <td valign="top"><div id="caption"><input type="radio" name="choose_by" id="choose_by" onclick="javascript:cmdCheck(1)" value="1" /> <%= dictionaryD.getWord(I_Dictionary.CATEGORY)%>&nbsp;<%= dictionaryD.getWord(I_Dictionary.EMPLOYEE)%></div></td>
                        <td valign="top"><div id="caption"><input type="radio" name="choose_by" id="choose_by" onclick="javascript:cmdCheck(2)" value="2" /> <%=dictionaryD.getWord(I_Dictionary.POSITION)%></div></td>
                    </tr>
                    <tr>
                        <td valign="top">
                            <div id="divinput">
                                <select name="division_select" multiple="multiple" size="20">
                                    <%
                                    Vector listDivision = PstDivision.list(0, 0, "", PstDivision.fieldNames[PstDivision.FLD_DIVISION]);
                                    if (listDivision != null && listDivision.size()>0){
                                        for(int i=0; i<listDivision.size(); i++){
                                            Division division = (Division)listDivision.get(i);
                                            if (division.getValidStatus() !=0){
                                            %>
                                            <option value="<%=division.getOID()%>"><%=division.getDivision()%></option>
                                            <%
                                            }
                                        }
                                    }
                                    %>
                                </select>
                            </div>
                        </td>
                        <td valign="top">
                            <div id="level_select">
                                <select name="level_select" multiple="multiple" size="20">
                                    <%
                                    Vector listLevel = PstLevel.list(0, 0, "", PstLevel.fieldNames[PstLevel.FLD_LEVEL_RANK]+" DESC");
                                    if (listLevel != null && listLevel.size()>0){
                                        for(int i=0; i<listLevel.size(); i++){
                                            Level level = (Level)listLevel.get(i);                        
                                        %>
                                        <option value="<%=level.getOID()%>"><%=level.getLevel()%></option>
                                        <%
                                        }
                                    }
                                    %>
                                </select>
                            </div>
                        </td>
                        <td valign="top">
                            <div id="category_select">
                                <select name="category_select" multiple="multiple" size="20">
                                    <%
                                    Vector listEmpCategory = PstEmpCategory.list(0, 0, "", PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]);
                                    if (listEmpCategory != null && listEmpCategory.size()>0){
                                        for(int i=0; i<listEmpCategory.size(); i++){
                                            EmpCategory empCategory = (EmpCategory)listEmpCategory.get(i);
                                            %>
                                            <option value="<%=empCategory.getOID()%>"><%=empCategory.getEmpCategory()%></option>
                                            <%
                                        }
                                    }
                                    %>
                                </select>
                            </div>
                        </td>
                        <td valign="top">
                            <div id="position_select">
                                <select name="position_select" multiple="multiple" size="20">
                                    <%
                                    String wherePosition = PstPosition.fieldNames[PstPosition.FLD_VALID_STATUS]+"="+PstPosition.VALID_ACTIVE;
                                    Vector listPosition = PstPosition.list(0, 0, wherePosition, PstPosition.fieldNames[PstPosition.FLD_POSITION]);
                                    if (listPosition != null && listPosition.size()>0){
                                        for(int i=0; i<listPosition.size(); i++){
                                            Position pos = (Position)listPosition.get(i);
                                            %>
                                            <option value="<%=pos.getOID()%>"><%=pos.getPosition()%></option>
                                            <%
                                        }
                                    }
                                    %>
                                </select>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="4" style="padding: 17px 7px;">
                            <a style="color:#FFF" class="btn" href="javascript:getAmount()">Get amount</a>
                            <a style="color:#FFF" class="btn" href="javascript:getExportToExcel()">Export to Excel</a>
                        </td>
                    </tr>
                </table>
                <div>&nbsp;</div>               
                <%
                if (iCommand == Command.LIST){
                    String amountTitle = "";
                    switch(chooseBy){
                        case 0: amountTitle = "Level";
                            break;
                        case 1: amountTitle = "Kategori Karyawan";
                            break;
                        case 2: amountTitle = "Jabatan";
                            break;
                    }
                    %>
                    <div id="menu_utama">
                        <strong>Period :</strong> <span style="font-size: 12px;"><%= dateFrom +" To "+ dateTo %></span>
                        &nbsp;|&nbsp;
                        <strong>By :</strong> <span style="font-size: 12px;"><%=amountTitle%></span>
                    </div>
                    <div>&nbsp;</div>
                    <%= empAmount.getDrawListAmount(chooseBy, dateFrom, dateTo, divisionSelect, levelSelect, categorySelect, positionSelect) %>
                    <%
                }
                %>
                
            </form>
                   
        </div>
        <div class="footer-page">
            <table>
                <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
                <tr>
                    <td valign="bottom"><%@include file="../footer.jsp" %></td>
                </tr>
                <%} else {%>
                <tr> 
                    <td colspan="2" height="20" ><%@ include file = "../main/footer.jsp" %></td>
                </tr>
                <%}%>
            </table>
        </div>
    </body>
</html>