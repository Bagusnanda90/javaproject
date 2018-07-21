<%-- 
    Document   : history_position_period
    Created on : Sep 14, 2015, 2:48:33 PM
    Author     : Dimata 007
--%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK);%>
<% boolean privGenerate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_GENERATE_SALARY_LEVEL));%>
<%@ include file = "../../main/checkuser.jsp" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%!
    public String getSectionLink(String sectionId){
        String str = "";
        try{
            Section section = PstSection.fetchExc(Long.valueOf(sectionId));
            str = section.getSection();
            return str;
        } catch(Exception e){
            System.out.println(e);
        }
        return str;
    }
%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    long companyId = FRMQueryString.requestLong(request, "company_id");
    long divisionId = FRMQueryString.requestLong(request, "division_id");
    long departmentId = FRMQueryString.requestLong(request, "department_id");
    
    String orderBy = "";
    /*
    * Description : get value Company, Division, Department, and Section
    * Date : 2015-02-17
    * Author : Hendra Putu
    */
// List Company
    Vector comp_value = new Vector(1, 1);
    Vector comp_key = new Vector(1, 1);
    comp_value.add("0");
    comp_key.add("-select-");
    String comp_selected = "";
// List Division
    Vector div_value = new Vector(1, 1);
    Vector div_key = new Vector(1, 1);
    String whereDivision = "COMPANY_ID = "+companyId;
    div_value.add("0");
    div_key.add("-select-");
    String div_selected = "";
// List Department
    Vector depart_value = new Vector(1,1);
    Vector depart_key = new Vector(1,1);
    String whereComp = ""+companyId;
    String whereDiv = "" +divisionId;
    depart_value.add("0");
    depart_key.add("-select-");
    String depart_selected = "";

// List Section
    Vector section_value = new Vector(1,1);
    Vector section_key = new Vector(1,1);
    String whereSection = "DEPARTMENT_ID = "+departmentId;
    section_value.add("0");
    section_key.add("-select-");


    Vector listComp = PstCompany.list(0, 0, "", " COMPANY ");
    for (int i = 0; i < listComp.size(); i++) {
    Company comp = (Company) listComp.get(i);
        if (comp.getOID() == companyId){
          comp_selected = String.valueOf(companyId);
        }
        comp_key.add(comp.getCompany());
        comp_value.add(String.valueOf(comp.getOID()));
    }

    String whrDivType = PstDivisionType.fieldNames[PstDivisionType.FLD_GROUP_TYPE]+"="+PstDivisionType.TYPE_BRANCH_OF_COMPANY;
    Vector listDivType = PstDivisionType.list(0, 0, whrDivType, "");
    long oidDivType = 0;
    if (listDivType != null && listDivType.size()>0){
        for(int i=0; i<listDivType.size(); i++){
            DivisionType divType = (DivisionType)listDivType.get(i);
            oidDivType = divType.getOID();
        }
    }
    
    Vector listDiv = PstDivision.list(0, 0, whereDivision, " DIVISION ");
    if (listDiv != null && listDiv.size() > 0) {
        for (int i = 0; i < listDiv.size(); i++) {
            Division division = (Division) listDiv.get(i);
            if (division.getOID() == divisionId){
                div_selected = String.valueOf(divisionId);
            }
            div_key.add(division.getDivision());
            div_value.add(String.valueOf(division.getOID()));
        }
    }

    Vector listDepart = PstDepartment.listDepartmentVer1(0, 0, whereComp, whereDiv);
    if (listDepart != null && listDepart.size() > 0) {
        for (int i = 0; i < listDepart.size(); i++) {
            Department depart = (Department) listDepart.get(i);
            if (depart.getOID()==departmentId){
                depart_selected = String.valueOf(departmentId);
            }
            depart_key.add(depart.getDepartment());
            depart_value.add(String.valueOf(depart.getOID()));
        }
    }

    Vector listSection = PstSection.list(0, 0, whereSection, "");
    if (listSection != null && listSection.size() > 0) {
        for (int i = 0; i < listSection.size(); i++) {
            Section section = (Section) listSection.get(i);
            section_key.add(section.getSection());
            section_value.add(String.valueOf(section.getOID()));
            String sectionLink = section.getSectionLinkTo();
            if ((sectionLink != null) && sectionLink.length()>0){

                for (String retval : sectionLink.split(",")) {
                    section_value.add(retval);
                    section_key.add(getSectionLink(retval));
                }
            }
        }
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sejarah Jabatan</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
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
              background-color: #DDD;
              border: 1px solid #CCC;
              border-radius: 3px;
              color: #575757;
              font-size: 11px;
              padding: 2px 5px;
              cursor: pointer;
            }
            #btn1:hover {
              color: #373737;
              background-color: #c5c5c5;
              border: 1px solid #999;
            }
            
            #btn2 {
              background-color: #00ccff;
              border: 1px solid #00aeda;
              border-radius: 3px;
              color: #FFF;
              font-size: 14px;
              padding: 9px 17px;
              cursor: pointer;
            }
            #btn2:hover {
              color: #FFF;
              background-color: #0096bb;
              border: 1px solid #007592;
            }
            
            #btn_disable {
              background-color: #EEE;
              border: 1px solid #D5D5D5;
              border-radius: 3px;
              color: #CCC;
              font-size: 14px;
              padding: 9px 17px;
            }
            
            #nav-next {
              background-color: #00ccff;
              color: #FFF;
              font-size: 14px;
              padding: 11px 17px 10px 17px;
              cursor: pointer;
            }
            #nav-next-disable {
              background-color: #d9d9d9;
              color: #FFF;
              font-size: 14px;
              padding: 11px 17px 10px 17px;
              cursor: pointer;
            }
            .tblStyle {border-collapse: collapse; font-size: 9px;}
            .tblStyle td {font-weight: bold; padding: 3px 7px; color: #575757;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757; }
            .title_part {background-color: #FFF; border-left: 1px solid #3cb0fd; padding:5px 15px;  color: #575757; margin: 1px 0px;}
            
        </style>

<link rel="stylesheet" href="<%=approot%>/javascripts/datepicker/themes/jquery.ui.all.css">
<script src="<%=approot%>/javascripts/jquery.js"></script>
<script src="<%=approot%>/javascripts/datepicker/jquery.ui.core.js"></script>
<script src="<%=approot%>/javascripts/datepicker/jquery.ui.widget.js"></script>
<script src="<%=approot%>/javascripts/datepicker/jquery.ui.datepicker.js"></script>

<script type="text/javascript">
    function getCmd(){
        document.frm.action = "candidate_main.jsp";
        document.frm.submit();
    }
    function cmdSave(){
        document.frm.command.value = "<%=Command.SAVE%>";
        getCmd();
    }

    function compChange(val) 
    {
        document.frm.command.value = "<%=Command.GOTO%>";
        document.frm.company_id.value = val;
        document.frm.action = "history_position_period.jsp";
        document.frm.submit();
    }
    function divisiChange(val) 
    {
        document.frm.command.value = "<%=Command.GOTO%>";
        document.frm.division_id.value = val;
        document.frm.action = "history_position_period.jsp";
        document.frm.submit();
    }
    function deptChange(val) 
    {
        document.frm.command.value = "<%=Command.GOTO%>";	
        document.frm.department_id.value = val;
        document.frm.action = "history_position_period.jsp";
        document.frm.submit();
    }
</script>
<script>
$(function() {
    $( "#datepicker1" ).datepicker({ dateFormat: "yy-mm-dd" });
    $( "#datepicker2" ).datepicker({ dateFormat: "yy-mm-dd" });
});
</script>

    </head>
    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../../styletemplate/template_header.jsp" %>
            <%} else {%>
            <tr> 
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" height="54"> 
                    <!-- #BeginEditable "header" --> 
                    <%@ include file = "../../main/header.jsp" %>
                    <!-- #EndEditable --> 
                </td>
            </tr>
            <tr> 
                <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
                    <%@ include file = "../../main/mnmain.jsp" %>
                    <!-- #EndEditable --> </td>
            </tr>
            <tr> 
                <td  bgcolor="#9BC1FF" height="10" valign="middle"> 
                    <table border="0" cellspacing="0" cellpadding="0">
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
                <td valign="top" align="left" width="100%"> 
                    <table border="0" cellspacing="3" cellpadding="2" id="tbl0" width="100%">
                        <tr> 
                            <td  colspan="3" valign="top" style="padding: 12px"> 
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr> 
                                        <td height="20"> <div id="menu_utama">Sejarah Jabatan</div> </td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td style="background-color:#EEE;" valign="top" width="100%">

                                            <table width="100%" style="padding:9px; border:1px solid <%=garisContent%>;"  border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                <tr>
                                                    <td valign="top">
                                                        <form name="frm" method="post" action="">
                                                            <input type="hidden" name="command" value="<%=iCommand%>">
                                                            <table>
                                                                <tr>
                                                                    <td valign="middle">Period</td>
                                                                    <td valign="middle">
                                                                        <input type="text" name="start_date" id="datepicker1" value="" /> To <input type="text" name="end_date" id="datepicker2" value="" />
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td valign="middle">Company</td>
                                                                    <td valign="middle">
                                                                        <%= ControlCombo.draw("company_id", "formElemen", null, comp_selected, comp_value, comp_key, " onChange=\"javascript:compChange(this.value)\"") %>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td valign="middle">Division</td>
                                                                    <td valign="middle">
                                                                        <%= ControlCombo.draw("division_id", "formElemen", null, div_selected, div_value, div_key, " onChange=\"javascript:divisiChange(this.value)\"") %>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td valign="middle">Department</td>
                                                                    <td valign="middle">
                                                                        <%= ControlCombo.draw("department_id", "formElemen", null, depart_selected, depart_value, depart_key, " onChange=\"javascript:deptChange(this.value)\"") %>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td valign="middle">Section</td>
                                                                    <td valign="middle">
                                                                        <%= ControlCombo.draw("section_id", "formElemen", null, "0", section_value, section_key, "") %> 
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td valign="middle">Jabatan</td>
                                                                    <td valign="middle">
                                                                        <select name="">
                                                                            <option value="0">-SELECT-</option>
                                                                        <%
                                                                        orderBy = PstPosition.fieldNames[PstPosition.FLD_POSITION]+" ASC";
                                                                        Vector listPosition = PstPosition.list(0, 0, "", orderBy);
                                                                        if (listPosition != null && listPosition.size()>0){
                                                                            for(int i=0; i<listPosition.size(); i++){
                                                                                Position position = (Position)listPosition.get(i);
                                                                                %>
                                                                                <option value="<%=position.getOID()%>"><%=position.getPosition()%></option>
                                                                                <%
                                                                            }
                                                                        }
                                                                        %>
                                                                        </select>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td valign="middle">Level</td>
                                                                    <td valign="middle">
                                                                        <select name="">
                                                                            <option value="0">-SELECT-</option>
                                                                        <%
                                                                        orderBy = PstLevel.fieldNames[PstLevel.FLD_LEVEL_RANK]+" ASC";
                                                                        Vector listLevel = PstLevel.list(0, 0, "", orderBy);
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
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td valign="middle" colspan="">
                                                                        <button id="btn" onclick="cmdCari()">Cari Riwayat</button>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                            
                                                        </form>
                                                    </td>
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