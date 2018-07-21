<%-- 
    Document   : structure_org
    Created on : Jul 31, 2015, 9:46:50 AM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.harisma.form.search.FrmSrcStructure"%>
<%@page import="com.dimata.harisma.entity.search.SrcStructure"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
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

    public String getPositionName(long posId){
        String position = "";
        Position pos = new Position();
        try {
            pos = PstPosition.fetchExc(posId);
        } catch(Exception ex){
            System.out.println("getPositionName ==> "+ex.toString());
        }
        position = pos.getPosition();
        return position;
    }
    
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
    
    public String getStructureOption(int structureSelect, int structureOp){
        String str = "";
        String check1 = "";
        String check2 = "";
        if (structureOp == 1){
            check1= "checked=\"checked\"";
            check2 = "";
        } else {
            check1 = "";
            check2= "checked=\"checked\"";
        }
        str += "<div><input type=\"radio\" name=\"structure_op\" onclick=\"cmdResetOption('1')\" "+check1+" value=\"1\" />Pusat</div>";
        str += "<div id=\"geser\">Tampilkan per level</div>";
        str += "<div id=\"geser\"><select name=\"structure_select\" onchange=\"javascript:structChange(this.value)\">";
            String [] arrStructure = {"-select-","Company","Division","Department","Section"};
            for (int i=0; i<arrStructure.length; i++){
                if (i == structureSelect){           
                    str += "<option value=\""+i+"\" selected=\"selected\">"+arrStructure[i]+"</option>";            
                } else {             
                    str += "<option value=\""+i+"\">"+arrStructure[i]+"</option>";         
                }
            }
        str += "</select></div>";
        str += "<div><input type=\"radio\" name=\"structure_op\" onclick=\"cmdResetOption('2')\" "+check2+" value=\"2\" />Cabang</div>";
        return str;
    }
   
%>
<!DOCTYPE html>
<%  
    

    int iCommand = FRMQueryString.requestCommand(request);
    int commandOther = FRMQueryString.requestInt(request, "command_other");
    int rbTime = FRMQueryString.requestInt(request, "rb_time");
    int structureOp = FRMQueryString.requestInt(request, "structure_op");
    int structureSelect = FRMQueryString.requestInt(request, "structure_select");
    int levelRank = FRMQueryString.requestInt(request, "level_rank");
    int chkPhoto = FRMQueryString.requestInt(request, "chk_photo");
    int chkGap = FRMQueryString.requestInt(request, "chk_gap");
    int viewMode = FRMQueryString.requestInt(request, "view_mode");
    
    /* Update by Hendra Putu | 20150217 */
    long companyId = FRMQueryString.requestLong(request, "company_id");
    long divisionId = FRMQueryString.requestLong(request, "division_id");
    long departmentId = FRMQueryString.requestLong(request, "department_id");
/*
    SrcStructure srcStructure = new SrcStructure();
    FrmSrcStructure frmSrcStructure = new FrmSrcStructure();
    
    if(iCommand==Command.BACK){
        frmSrcStructure  = new FrmSrcStructure(request, srcStructure);
        try
        {			
            srcStructure = (SrcStructure) session.getValue(PstStructureTemplate.SESS_STRUCTURE_ORG);			
            if(srcStructure== null)
            {
                srcStructure = new SrcStructure();
            }		
        }
        catch (Exception e)
        {
            srcStructure = new SrcStructure();
        }
    }
*/
%>
<%
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

    if (structureOp == 1){
        whereDivision += " AND "+PstDivision.fieldNames[PstDivision.FLD_DIVISION_TYPE_ID]+"!="+oidDivType; /* tidak sama dengan */
    }
    
    if (structureOp == 2){
        whereDivision += " AND "+PstDivision.fieldNames[PstDivision.FLD_DIVISION_TYPE_ID]+"="+oidDivType; /* sama dengan*/
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
        <title>Searching Structure</title>
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
            .detail {
                background-color: #b01818;
                color:#FFF;
                padding: 2px;
                font-size: 9px;
                cursor: pointer;
            }
            .detail1 {
                background-color: #ffe400;
                color:#d76f09;
                padding: 2px;
                font-size: 9px;
                cursor: pointer;
            }
            .detail_pos {
                cursor: pointer;
            }
            .tblStyle {border-collapse: collapse;font-size: 9px;}
            .tblStyle td {font-size: 11px;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757; }
            .title_part {background-color: #FFF; border-left: 1px solid #3cb0fd; padding:5px 15px;  color: #575757; margin: 1px 0px;}
            .shareholder {background-color: #999; color: #FFF; padding: 3px;}
            .comp_media {background-color: #CCC; color: #FFF; padding: 3px;}
            .dir {background-color: #0599ab; color: #FFF; padding: 3px;}
            .div {background-color: #dd4949; color: #FFF; padding: 3px;}
            .dep {background-color: #ff8a00; color: #FFF; padding: 3px; margin: 2px 0px;}
            .filter {
                background-color: #DDD;
                border: 1px solid #FFF;
                border-radius: 3px;
                padding: 5px;
            }
            #geser {
                margin: 5px 0px 5px 21px;
            }
            #level {
                padding: 3px 5px;
                background-color: #EEE;
            }
        </style>
        <script type="text/javascript">
            function cmdViewAll(){
                document.frm.command.value="<%=Command.VIEW%>";
                document.frm.action="structure_view.jsp";
                document.frm.submit();
            }
            function structChange(val){
                document.frm.command.value = "<%=Command.GOTO%>";
                document.frm.structure_select.value = val;
                document.frm.action = "structure_src.jsp";
                document.frm.submit();
            }
            
            function levelChange(val){
                document.frm.command.value = "<%=Command.GOTO%>";
                document.frm.level_rank.value = val;
                document.frm.action = "structure_src.jsp";
                document.frm.submit();
            }
            function compChange(val) 
            {
                document.frm.command.value = "<%=Command.GOTO%>";
                document.frm.company_id.value = val;
                document.frm.action = "structure_src.jsp";
                document.frm.submit();
            }
            function divisiChange(val) 
            {
                document.frm.command.value = "<%=Command.GOTO%>";
                document.frm.division_id.value = val;
                document.frm.action = "structure_src.jsp";
                document.frm.submit();
            }
            function deptChange(val) 
            {
                document.frm.command.value = "<%=Command.GOTO%>";	
                document.frm.department_id.value = val;
                document.frm.action = "structure_src.jsp";
                document.frm.submit();
            }
            
            function cmdViewByStructure(){
                document.frm.command.value="<%=Command.VIEW%>";
                document.frm.view_mode.value="1";
                document.frm.action="structure_view.jsp";
                document.frm.submit();
            }
            
            function cmdViewByList(){
                document.frm.command.value="<%=Command.VIEW%>";
                document.frm.view_mode.value="2";
                document.frm.action="structure_view.jsp";
                document.frm.submit();
            }
            
            function cmdResetOption(val){
                document.frm.structure_op.value=val;
                document.frm.action="structure_src.jsp";
                document.frm.submit();
            }
        </script>
<link rel="stylesheet" href="<%=approot%>/javascripts/datepicker/themes/jquery.ui.all.css">
<script src="<%=approot%>/javascripts/jquery.js"></script>
<script src="<%=approot%>/javascripts/datepicker/jquery.ui.core.js"></script>
<script src="<%=approot%>/javascripts/datepicker/jquery.ui.widget.js"></script>
<script src="<%=approot%>/javascripts/datepicker/jquery.ui.datepicker.js"></script>
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
            <%@include file="../styletemplate/template_header.jsp" %>
            <%} else {%>
            <tr> 
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" height="54"> 
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
                                        <td height="20"> <div id="menu_utama">Searching Structure</div> </td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td style="background-color:#EEE;" valign="top" width="100%">
                                        
                                            <table width="100%" style="padding:9px; border:1px solid <%=garisContent%>;"  border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                <tr>
                                                    <td valign="top">
                                                        <!--- fitur filter --->
                                                        <form name="frm" method="post" action="">
                                                            <input type="hidden" name="command" value="<%=iCommand%>">
                                                            <input type="hidden" name="view_mode" value="<%=viewMode%>" />
                                                        <div class="filter">
                                                            <div class="title_part">
                                                                <%
                                                                if (rbTime == 0){
                                                                    %>
                                                                    <input type="radio" name="rb_time" checked="checked" value="1" /><strong style="padding-right: 9px;">Current</strong>|
                                                                    <input type="radio" name="rb_time" value="2" /><strong>History</strong>
                                                                    <%
                                                                } else {
                                                                    if (rbTime == 1){
                                                                        %>
                                                                        <input type="radio" name="rb_time" checked="checked" value="1" /><strong style="padding-right: 9px;">Current</strong>|
                                                                        <input type="radio" name="rb_time" value="2" /><strong>History</strong>
                                                                        <%
                                                                    } else {
                                                                        %>
                                                                        <input type="radio" name="rb_time" value="1" /><strong style="padding-right: 9px;">Current</strong>|
                                                                        <input type="radio" name="rb_time" checked="checked" value="2" /><strong>History</strong>
                                                                        <%
                                                                    }
                                                                }
                                                                %>
                                                                
                                                                <input type="text" name="selected_date" id="datepicker1" value="" />
                                                            </div>
                                                                <!--
                                                            <div class="title_part">
                                                                <button id="btn" onclick="cmdViewAll()">Tampilkan semua</button>&nbsp;
                                                                <strong> Bagan struktur organisasi keseluruhan</strong>
                                                            </div>-->
                                                            <div class="title_part">
                                                                <%
                                                                if (structureOp == 0){
                                                                    int strOp = 1;
                                                                    %>
                                                                    <%=getStructureOption(structureSelect, strOp)%>
                                                                    <%
                                                                } else {
                                                                    if (structureOp == 1){
                                                                    %>
                                                                    <%=getStructureOption(structureSelect, structureOp)%>
                                                                    <%    
                                                                    } else {
                                                                    %>
                                                                    <%=getStructureOption(structureSelect, structureOp)%>
                                                                    <%     
                                                                    }
                                                                }
                                                                %>
                                                            </div>
                                                            <div class="title_part">
                                                                <div>Tampilkan Sampai</div>
                                                                <div>
                                                                    <select name="level_rank" onchange="levelChange(this.value)">
                                                                        <%
                                                                        String orderBy = PstLevel.fieldNames[PstLevel.FLD_LEVEL_RANK]+" ASC";
                                                                        Vector listLevel = PstLevel.list(0, 0, "", orderBy);
                                                                        if (listLevel != null && listLevel.size()>0){
                                                                            for(int l=0; l<listLevel.size(); l++){
                                                                                Level level = (Level)listLevel.get(l);
                                                                                if (level.getLevelRank()==levelRank){
                                                                                    %>
                                                                                    <option value="<%=level.getLevelRank()%>" selected="selected"><%=level.getLevel()%></option>
                                                                                    <%
                                                                                } else {
                                                                                    %>
                                                                                    <option value="<%=level.getLevelRank()%>"><%=level.getLevel()%></option>
                                                                                    <%
                                                                                }
                                                                                
                                                                            }
                                                                        }
                                                                        %>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                            <%
                                                            if (structureOp == 1){
                                                            %>
                                                            <div class="title_part">
                                                                <table>
                                                                <% if(structureSelect == 1 || structureSelect == 2 || structureSelect == 3 || structureSelect == 4){ %>
                                                                <tr>
                                                                    <td valign="top">Company</td>
                                                                    <td valign="top">
                                                                        <%= ControlCombo.draw("company_id", "formElemen", null, comp_selected, comp_value, comp_key, " onChange=\"javascript:compChange(this.value)\"") %>
                                                                    </td>
                                                                </tr>
                                                                <% } %>
                                                                <% if(structureSelect == 2 || structureSelect == 3 || structureSelect == 4){ %>
                                                                <tr>
                                                                    <td valign="top">Division</td>
                                                                    <td valign="top">
                                                                        <%= ControlCombo.draw("division_id", "formElemen", null, div_selected, div_value, div_key, " onChange=\"javascript:divisiChange(this.value)\"") %>
                                                                    </td>
                                                                </tr>
                                                                <% } %>
                                                                <% if(structureSelect == 3 || structureSelect == 4){ %>
                                                                <tr>
                                                                    <td valign="top">Department</td>
                                                                    <td valign="top">
                                                                        <%= ControlCombo.draw("department_id", "formElemen", null, depart_selected, depart_value, depart_key, " onChange=\"javascript:deptChange(this.value)\"") %>
                                                                    </td>
                                                                </tr>
                                                                <% } %>
                                                                <% if(structureSelect == 4){ %>
                                                                <tr>
                                                                    <td valign="top">Section</td>
                                                                    <td valign="top">
                                                                        <%= ControlCombo.draw("section_id", "formElemen", null, "0", section_value, section_key, "") %> 
                                                                    </td>
                                                                </tr>
                                                                <% } %>
                                                                <tr>
                                                                    <td colspan="2">&nbsp;</td>
                                                                </tr>
                                                                <tr>
                                                                    <td valign="top">
                                                                        <input type="checkbox" name="chk_photo" value="1" />Tampilkan Foto
                                                                    </td>
                                                                    <td valign="top">
                                                                        <input type="checkbox" name="chk_gap" value="1" />Tampilkan Gap
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td colspan="2">&nbsp;</td>
                                                                </tr>
                                                                <tr>
                                                                    <td colspan="2">
                                                                        <button id="btn" onclick="cmdViewByStructure()">Tampilkan Struktur</button>&nbsp;
                                                                        <button id="btn" onclick="cmdViewByList()">Tampilkan Daftar</button>
                                                                    </td>
                                                                </tr>
                                                                </table>
                                                            </div>
                                                            <% } %>
                                                            <%
                                                            if (structureOp == 2){
                                                            %>
                                                            <div class="title_part">
                                                                <table>
                                                                <tr>
                                                                    <td valign="top" colspan="2">
                                                                        <strong>Pencarian lebih spesifik ke masing-masing cabang.</strong>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td valign="top">Company</td>
                                                                    <td valign="top">
                                                                        <%= ControlCombo.draw("company_id", "formElemen", null, comp_selected, comp_value, comp_key, " onChange=\"javascript:compChange(this.value)\"") %>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td valign="top">Division</td>
                                                                    <td valign="top">
                                                                        <%= ControlCombo.draw("division_id", "formElemen", null, div_selected, div_value, div_key, " onChange=\"javascript:divisiChange(this.value)\"") %>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td valign="top">Department</td>
                                                                    <td valign="top">
                                                                        <%= ControlCombo.draw("department_id", "formElemen", null, depart_selected, depart_value, depart_key, " onChange=\"javascript:deptChange(this.value)\"") %>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td valign="top">Section</td>
                                                                    <td valign="top">
                                                                        <%= ControlCombo.draw("section_id", "formElemen", null, "0", section_value, section_key, "") %> 
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td colspan="2">&nbsp;</td>
                                                                </tr>
                                                                <tr>
                                                                    <td valign="top">
                                                                        <input type="checkbox" name="chk_photo" value="1" />Tampilkan Foto
                                                                    </td>
                                                                    <td valign="top">
                                                                        <input type="checkbox" name="chk_gap" value="1" />Tampilkan Gap
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td colspan="2">&nbsp;</td>
                                                                </tr>
                                                                <tr>
                                                                    <td colspan="2">
                                                                        <button id="btn" onclick="cmdViewByStructure()">Tampilkan Struktur</button>&nbsp;
                                                                        <button id="btn" onclick="cmdViewByList()">Tampilkan Daftar</button>
                                                                    </td>
                                                                </tr>
                                                                </table>
                                                            </div>
                                                            <% } %>
                                                        </div>
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