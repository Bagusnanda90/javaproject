<%-- 
    Document   : srcemployee-new
    Created on : May 10, 2016, 2:48:46 PM
    Author     : Dimata 007
--%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.harisma.session.employee.SessEmployee"%>
<%@page import="com.dimata.harisma.form.search.FrmSrcSpecialEmployeeQuery"%>
<%@page import="com.dimata.harisma.session.employee.SearchSpecialQuery"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    long hrdDepOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    long sdmDivisionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_SDM_DIVISION)));
    boolean isHRDLogin = (hrdDepOid == departmentOid || sdmDivisionOid == divisionOid) ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
%>

<%
    int iCommand = FRMQueryString.requestCommand(request);
        
    SearchSpecialQuery searchSpecialQuery = new SearchSpecialQuery();
    FrmSrcSpecialEmployeeQuery frmSrcSpecialEmployeeQuery = new FrmSrcSpecialEmployeeQuery();//new FrmSrcSpecialEmployeeQuery(request, searchSpecialQuery);
        
        
    if (iCommand == Command.GOTO) {
        
        frmSrcSpecialEmployeeQuery = new FrmSrcSpecialEmployeeQuery(request, searchSpecialQuery);
        frmSrcSpecialEmployeeQuery.requestEntityObject(searchSpecialQuery);
    }
        
    if (iCommand == Command.BACK) {
        
        frmSrcSpecialEmployeeQuery = new FrmSrcSpecialEmployeeQuery(request, searchSpecialQuery);
        try {
            searchSpecialQuery = (SearchSpecialQuery) session.getValue(SessEmployee.SESS_SRC_EMPLOYEE);
            if (searchSpecialQuery == null) {
                searchSpecialQuery = new SearchSpecialQuery();
            }
        } catch (Exception e) {
            searchSpecialQuery = new SearchSpecialQuery();
        }
    }
    I_Dictionary dictionaryD = userSession.getUserDictionary();
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HAIRISMA - Search <%=dictionaryD.getWord(I_Dictionary.EMPLOYEE)%></title>
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <style type="text/css">
            .tblStyle {border-collapse: collapse;}
            .tblStyle td {padding: 5px 7px; border: 1px solid #CCC; font-size: 12px;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            .title_page {color:#0db2e1; font-weight: bold; font-size: 14px; background-color: #EEE; border-left: 1px solid #0099FF; padding: 12px 18px;}

            body {color:#373737;}
            #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3;}
            #menu_title {color:#0099FF; font-size: 14px;}
            #menu_teks {color:#CCC;}
            
            .title_part {color:#FF7E00; background-color: #F7F7F7; border-left: 1px solid #0099FF; padding: 9px 11px;}
            
            body {background-color: #EEE;}
            .header {
                
            }
            .content-main {
                padding: 5px 7px 25px 7px;
                margin: 0px 21px 59px 21px;
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
                margin:8px; 
                padding: 9px 12px;
                color: #32819C;
                background-color: #BEE1ED;
                border-radius: 3px;
            }
            .box-info-1 {
                margin-top: 5px;
                padding: 9px 12px;
                color: #575757;
                font-weight: bold;
                background-color: #DDD;
                border-radius: 3px;
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
            
            #photo {
                padding: 7px; 
                background-color: #FFF; 
                border: 1px solid #DDD;
            }
            .btn {
                background-color: #00a1ec;
                border-radius: 3px;
                font-family: Arial;
                border-radius: 3px;
                color: #EEE;
                font-size: 12px;
                padding: 7px 17px 7px 17px;
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
            #inp {
                color: #575757;
                padding: 7px 7px;
                font-weight: bold;
            }
            select {
                padding: 5px 7px;
            }
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
            
            
            .form-style {
                font-size: 12px;
                color: #575757;
                border: 1px solid #DDD;
                border-radius: 5px;
            }
            .form-title {
                padding: 11px 21px;
                margin-bottom: 2px;
                border-bottom: 1px solid #DDD;
                background-color: #EEE;
                border-top-left-radius: 5px;
                border-top-right-radius: 5px;
                font-weight: bold;
            }
            .form-content {
                padding: 21px;
            }
            .form-footer {
                border-top: 1px solid #DDD;
                padding: 11px 21px;
                margin-top: 2px;
                background-color: #EEE;
                border-bottom-left-radius: 5px;
                border-bottom-right-radius: 5px;
            }
            #confirm {
                padding: 18px 21px;
                background-color: #FF6666;
                color: #FFF;
                border: 1px solid #CF5353;
            }
            #btn-confirm {
                padding: 3px; border: 1px solid #CF5353; 
                background-color: #CF5353; color: #FFF; 
                font-size: 11px; cursor: pointer;
            }
            .footer-page {
                font-size: 12px;
            }
            td { font-size: 12px; color: #474747; }
            .search-title {
                color:#575757;
                padding: 3px 11px;
                font-size: 18px;
                font-weight: bold;
                text-align: center;
            }
            .search-input {
                padding: 7px 11px;
                text-align: center;
            }
            .caption {
                font-size: 13px;
                color: #575757;
                font-weight: bold;
                padding: 9px 13px 9px 13px;
                border-bottom: 1px solid #DDD;
            }
            .box {
                margin: 5px;
                padding: 19px 21px;
                background-color: #FFF;
                border-radius: 3px;
                border: 1px solid #DDD;
                color:#575757;
            }

            .box-item {
                color: #575757;
                padding: 7px 13px;
                font-size: 11px;
                font-weight: bold;
                background-color: #FFF;
                margin: 1px 0px;
                cursor: pointer;
            }
            .box-item:hover {
                color: #373737;
                background-color: #EEE;
            }
            .box-item-active {
                color: #F5F5F5;
                padding: 9px 13px;
                font-size: 11px;
                background-color: #007fba;
                margin: 1px 0px;
                cursor: pointer;
            }
            .emp-hover {
                background-color: #FFF;
            }
            .emp-hover:hover {
                background-color: #EEE;
            }

        </style>
        <script type="text/javascript">
            function loadCompany(oid) {
                if (oid.length == 0) { 
                    document.getElementById("div-struct").innerHTML = "";
                    return;
                } else {
                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange = function() {
                        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                            document.getElementById("div-struct").innerHTML = xmlhttp.responseText;
                        }
                    };
                    xmlhttp.open("GET", "struct_search_emp.jsp?career_path_oid=" + oid, true);
                    xmlhttp.send();
                }
            }

            function loadDivision(str) {
                if (str.length == 0) { 
                    document.getElementById("div-struct").innerHTML = "";
                    return;
                } else {
                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange = function() {
                        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                            document.getElementById("div-struct").innerHTML = xmlhttp.responseText;
                        }
                    };
                    xmlhttp.open("GET", "struct_search_emp.jsp?company_id=" + str, true);
                    xmlhttp.send();
                }
            }

            function loadDepartment(comp_id, divisi_id) {
                if ((comp_id.length == 0)&&(divisi_id.length == 0)) { 
                    document.getElementById("div-struct").innerHTML = "";
                    return;
                } else {
                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange = function() {
                        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                            document.getElementById("div-struct").innerHTML = xmlhttp.responseText;
                        }
                    };
                    xmlhttp.open("GET", "struct_search_emp.jsp?company_id="+comp_id+"&division_id=" + divisi_id, true);
                    xmlhttp.send();
                }
            }

            function loadSection(comp_id, divisi_id, depart_id) {
                if ((comp_id.length == 0)&&(divisi_id.length == 0)&&(depart_id.length == 0)) { 
                    document.getElementById("div-struct").innerHTML = "";
                    return;
                } else {
                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange = function() {
                        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                            document.getElementById("div-struct").innerHTML = xmlhttp.responseText;
                        }
                    };
                    xmlhttp.open("GET", "struct_search_emp.jsp?company_id="+comp_id+"&division_id=" + divisi_id +"&department_id="+depart_id, true);
                    xmlhttp.send();
                }
            }
        </script>
    </head>
    <body>
        <div class="header">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../../styletemplate/template_header.jsp" %>
            <%} else {%>
            <tr> 
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
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
            <span id="menu_title">Pencarian / <%=dictionaryD.getWord(I_Dictionary.EMPLOYEE)%></span>
        </div>
        <div class="content-main">
            <form name="frmsrcemployee" method="post" action="">
                <input type="hidden" name="command" value="<%=iCommand%>">
                <table>
                    <tr>
                        <td valign="top">
                            <div class="box">
                                <div id="caption">Payroll Number</div>
                                <div id="divinput">
                                    <input style="padding: 6px 9px;" type="text" name="<%=frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_EMPLOYEENUM] %>"  value="<%= searchSpecialQuery.getEmpnumber() %>" class="elemenForm" onkeydown="javascript:fnTrapKD()">
                                </div>
                                <div id="caption"><%=dictionaryD.getWord(I_Dictionary.EMPLOYEE)%>  <%=dictionaryD.getWord("NAME")%></div>
                                <div id="divinput">
                                    <input style="padding: 6px 9px;" type="text" name="<%=frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_EMP_FULLNAME] %>"  value="<%= searchSpecialQuery.getFullNameEmp() %>" class="elemenForm" size="50" onkeydown="javascript:fnTrapKD()">
                                </div>
                                <div id="caption"><%=dictionaryD.getWord(I_Dictionary.ADDRESS)%></div>
                                <div id="divinput">
                                    <input style="padding: 6px 9px;" type="text" name="<%=frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_EMP_ADDRESS] %>"  value="<%= searchSpecialQuery.getAddrsEmployee() %>" class="elemenForm" size="50" onkeydown="javascript:fnTrapKD()"> 
                                </div>
                            </div>
                        </td>
                        <td valign="top">
                            <div class="box">
                                
                                <div id="caption"><%=dictionaryD.getWord(I_Dictionary.COMPANY)%></div>
                                <div id="divinput">
                                    <%
                                        long depOid = departmentOfLoginUser.getOID();
                                        int SetHRD = 0;
                                        try {
                                            SetHRD = Integer.valueOf(PstSystemProperty.getValueByName("OID_HRD_DEPARTMENT"));
                                        } catch (Exception e) {
                                        }
                                    %>
                                    <%
                                        Vector comp_value = new Vector(1, 1);
                                        Vector comp_key = new Vector(1, 1);
                                        comp_value.add("0");
                                        comp_key.add("select ...");
                                        Vector listComp = PstCompany.list(0, 0, "", " COMPANY ");
                                        long companyDefault = 0;
                                        for (int i = 0; i < listComp.size(); i++) {
                                            Company comp = (Company) listComp.get(i);
                                            comp_key.add(comp.getCompany());
                                            comp_value.add(String.valueOf(comp.getOID()));
                                            companyDefault = comp.getOID();
                                        }
                                        String companyId[] = searchSpecialQuery.getArrCompany(0);
                                        long lCompanyId = companyId != null && companyId[0] != null && companyId[0].length() > 0 ? Long.parseLong(companyId[0]) : 0;
                                                                  
                                    %> <%= ControlCombo.draw(frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_COMPANY_ID], "formElemen", null, "" + (lCompanyId), comp_value, comp_key, "onChange=\"javascript:cmdUpdateDiv()\"")%> 
                                </div>
                                <div id="div-struct"></div>
                                <div id="caption"><%=dictionaryD.getWord("CATEGORY")%></div>
                                <div id="divinput">
                                    <%
                                        Vector cat_value = new Vector(1, 1);
                                        Vector cat_key = new Vector(1, 1);
                                        cat_value.add("0");
                                        cat_key.add("select ...");
                                        Vector listCat = PstEmpCategory.list(0, 0, "", " EMP_CATEGORY ");
                                        for (int i = 0; i < listCat.size(); i++) {
                                            EmpCategory cat = (EmpCategory) listCat.get(i);
                                            cat_key.add(cat.getEmpCategory());
                                            cat_value.add(String.valueOf(cat.getOID()));
                                        }
                                        String empCatId[] = searchSpecialQuery.getArrCompany(0);
                                        long lEmpCatId = empCatId != null && empCatId[0] != null && empCatId[0].length() > 0 ? Long.parseLong(empCatId[0]) : 0;
                                    %> <%= ControlCombo.draw(frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_EMP_CATEGORY_ID], "formElemen", null, "" + lEmpCatId, cat_value, cat_key, " onkeydown=\"javascript:fnTrapKD()\"")%>
                                </div>
                            </div>
                        </td>
                    </tr>
                </table>
                
                
                
            </form>
        </div>
        <div class="footer-page">
            <table>
                <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
                <tr>
                    <td valign="bottom"><%@include file="../../footer.jsp" %></td>
                </tr>
                <%} else {%>
                <tr> 
                    <td colspan="2" height="20" ><%@ include file = "../../main/footer.jsp" %></td>
                </tr>
                <%}%>
            </table>
        </div>
    </body>
</html>
