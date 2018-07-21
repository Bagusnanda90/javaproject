<%-- 
    Document   : candidate_location
    Created on : Sep 8, 2015, 10:53:03 AM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.harisma.form.employee.FrmCandidateLocation"%>
<%@page import="com.dimata.harisma.form.employee.CtrlCandidateLocation"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK);%>
<% boolean privGenerate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_GENERATE_SALARY_LEVEL));%>
<%@ include file = "../../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
int iCommand = FRMQueryString.requestCommand(request);
long candidateMainId = FRMQueryString.requestLong(request, "candidate_main_id");
long candidateLocId = FRMQueryString.requestLong(request, "candidate_location_id");

String[] chxCompany = FRMQueryString.requestStringValues(request, "chx_company");
String[] chxDivision = FRMQueryString.requestStringValues(request, "chx_division");
String[] chxDepartment = FRMQueryString.requestStringValues(request, "chx_department");
String[] chxSection = FRMQueryString.requestStringValues(request, "chx_section");
int typeMenu = FRMQueryString.requestInt(request, "type_menu");

String titleMenu = "";
if (typeMenu == 0){
    titleMenu = "Candidate";
} else {
    titleMenu = "Talent";
}
String whereClause = "";
String orderBy = "";

CandidateLocation dataLocation = new CandidateLocation();

if (iCommand == Command.SAVE && candidateMainId > 0){
    /* check box company */
    if (chxCompany != null && chxCompany.length > 0){
        dataLocation = new CandidateLocation();
        long oidLocation = 0;
        for(int i=0; i < chxCompany.length; i++){
            dataLocation.setOID(0);
            dataLocation.setCandidateMainId(candidateMainId);
            dataLocation.setGenId(Long.valueOf(chxCompany[i]));
            dataLocation.setDivisionId(0);
            dataLocation.setDepartmentId(0);
            dataLocation.setSectionId(0);
            try {
                oidLocation = PstCandidateLocation.insertExc(dataLocation);
            } catch(Exception ex){
                System.out.print("Insert data Candidate Location by Company"+ex.toString());
            }
        }
        candidateLocId = oidLocation;
    }
    /* check box division */
    if (chxDivision != null && chxDivision.length > 0){
        dataLocation = new CandidateLocation();
        for (int i = 0; i < chxDivision.length; i++) {
            String[] arr = chxDivision[i].split("-");
            dataLocation.setOID(0);
            dataLocation.setCandidateMainId(candidateMainId);
            dataLocation.setGenId(Long.valueOf(arr[0]));
            dataLocation.setDivisionId(Long.valueOf(arr[1]));
            dataLocation.setDepartmentId(0);
            dataLocation.setSectionId(0);
            try {
                PstCandidateLocation.insertExc(dataLocation);
            } catch(Exception ex){
                System.out.print("Insert data Candidate Location by Division"+ex.toString());
            }
        }
    }
    /* check box department */
    if (chxDepartment != null && chxDepartment.length > 0){
        dataLocation = new CandidateLocation();
        for (int i = 0; i < chxDepartment.length; i++) {
            String[] arr = chxDepartment[i].split("-");
            dataLocation.setOID(0);
            dataLocation.setCandidateMainId(candidateMainId);
            dataLocation.setGenId(Long.valueOf(arr[0]));
            dataLocation.setDivisionId(Long.valueOf(arr[1]));
            dataLocation.setDepartmentId(Long.valueOf(arr[2]));
            dataLocation.setSectionId(0);
            try {
                PstCandidateLocation.insertExc(dataLocation);
            } catch(Exception ex){
                System.out.print("Insert data Candidate Location by Department"+ex.toString());
            }
        }
    }
    /* check box section */
    if (chxSection != null && chxSection.length > 0){
        dataLocation = new CandidateLocation();
        for (int i = 0; i < chxSection.length; i++) {
            String[] arr = chxSection[i].split("-");
            dataLocation.setOID(0);
            dataLocation.setCandidateMainId(candidateMainId);
            dataLocation.setGenId(Long.valueOf(arr[0]));
            dataLocation.setDivisionId(Long.valueOf(arr[1]));
            dataLocation.setDepartmentId(Long.valueOf(arr[2]));
            dataLocation.setSectionId(Long.valueOf(arr[3]));
            try {
                PstCandidateLocation.insertExc(dataLocation);
            } catch(Exception ex){
                System.out.print("Insert data Candidate Location by Section"+ex.toString());
            }
        }
    }
    /*
    String redirectURL = approot+"/employee/candidate/candidate_position.jsp?oid="+candidateMainId;
    response.sendRedirect(redirectURL);
    */
}




%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Candidate Location</title>
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
            .tblStyle {border-collapse: collapse; font-size: 9px;}
            .tblStyle td {font-weight: bold; padding: 3px 7px;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757; }
            .title_part {background-color: #FFF; border-left: 1px solid #3cb0fd; padding:5px 15px;  color: #575757; margin: 1px 0px;}
            .div_company {
                padding: 3px;
                background-color: #FFF;
            }
            .div_division {
                padding: 3px;
                background-color: #EEE;
                margin: 1px 21px;
            }
            .div_department {
                padding: 3px;
                background-color: #DDD;
                margin: 1px 23px;
            }
            .div_section {
                padding: 3px;
                background-color: #CCC;
                margin: 1px 25px;
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
            .pesan {
                padding: 21px;
                background-color: #c9ff6a;
                color: #689f09;
                border-radius: 5px;
            }
        </style>
        <script type="text/javascript">
            function cmdNext(){
                document.frm.command.value="<%=Command.SAVE%>";
                document.frm.action = "candidate_location.jsp";
                document.frm.submit();
            }
            function cmdGoTo(val, oid){
                var link = "";
                switch(val){
                    case "1":
                        link = "candidate_main.jsp";
                        break;
                    case "2":
                        link = "candidate_location.jsp";
                        break;
                    case "3":
                        link = "candidate_position.jsp";
                        break;
                    case "4":
                        link = "candidate_matrix.jsp";
                        break;
                    case "5":
                        link = "candidate_selection.jsp";
                        break;
                    case "6":
                        link = "candidate_complete.jsp";
                        break;
                }
                document.frm.command.value="0";
                document.frm.candidate_main_id.value=oid;
                document.frm.action = link;
                document.frm.submit();
            }
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
                                        <td height="20"> <div id="menu_utama">Candidate Location</div> </td>
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
                                                            <input type="hidden" name="candidate_main_id" value="<%=candidateMainId%>" />
                                                            <input type="hidden" name="candidate_location_id" value="<%=candidateLocId%>" />
                                                            <table>
                                                                <tr>
                                                                    <td colspan="2" style="border-bottom:1px solid #CCC; padding:15px 9px;">
                                                                        <table cellspacing="0" cellpadding="0" border="0">
                                                                            <tr>
                                                                                <td valign="top"><div id="nav-next" onclick="cmdGoTo('1','<%=candidateMainId%>')">1) <%=titleMenu%> Main</div></td>
                                                                                <td valign="middle"><img src="<%=approot%>/images/nav-next-enable.png" /></td>
                                                                                <% if (typeMenu == 0){ %>
                                                                                <td valign="top"><div id="nav-next" onclick="cmdGoTo('2','<%=candidateMainId%>')">2) <%=titleMenu%> Location</div></td>
                                                                                <% } %>
                                                                                <td valign="middle"><img src="<%=approot%>/images/nav-next.png" /></td>
                                                                                <td valign="top"><div id="nav-next-disable" onclick="cmdGoTo('3','<%=candidateMainId%>')">3) <%=titleMenu%> Position</div></td>
                                                                                <td valign="middle"><img src="<%=approot%>/images/nav-next-disable.png" /></td>
                                                                                <td valign="top"><div id="nav-next-disable" onclick="cmdGoTo('4','<%=candidateMainId%>')">4) <%=titleMenu%> Detail</div></td>
                                                                                <td valign="middle"><img src="<%=approot%>/images/nav-next-disable.png" /></td>
                                                                                <td valign="top"><div id="nav-next-disable" onclick="cmdGoTo('5','<%=candidateMainId%>')">5) <%=titleMenu%> Selection</div></td>
                                                                                <td valign="middle"><img src="<%=approot%>/images/nav-next-disable.png" /></td>
                                                                                <td valign="top"><div id="nav-next-disable" onclick="cmdGoTo('6','<%=candidateMainId%>')">6) <%=titleMenu%> Summary</div></td>                                                                                
                                                                            </tr>
                                                                        </table>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td>&nbsp;</td>
                                                                </tr>
                                                                <tr>
                                                                    <td>
                                                                        <%
                                                                        if (iCommand == Command.SAVE){
                                                                            iCommand = Command.NONE;
                                                                            %>
                                                                            <div class="pesan">Data berhasil disimpan!</div>
                                                                            <%
                                                                        }
                                                                        %>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td colspan="2" style="border-top:1px solid #DDD; padding-top: 12px">
                                                                        <button id="btn2" onclick="cmdNext()">Save</button>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td valign="top">
                                                                        <table>
                                                                            <tr>
                                                                                <td valign="top">
                                                                                    <h4>Planning Target Location</h4>
                                                                                    
                                                                                        <%
                                                                                        Vector listCompany = PstCompany.list(0, 0, "", "");
                                                                                        if (listCompany != null && listCompany.size()>0){
                                                                                            for (int c=0; c<listCompany.size(); c++){
                                                                                                Company company = (Company)listCompany.get(c);
                                                                                                whereClause = PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID]+"="+company.getOID();
                                                                                                orderBy = PstDivision.fieldNames[PstDivision.FLD_DIVISION];
                                                                                                Vector listDivision = PstDivision.list(0, 0, whereClause, orderBy);
                                                                                                %>
                                                                                                <div class="div_company">
                                                                                                    <input type="checkbox" name="chx_company" value="<%=company.getOID()%>" /><%=company.getCompany()%>
                                                                                                <%
                                                                                                if (listDivision != null && listDivision.size()>0){
                                                                                                    for (int d=0; d<listDivision.size(); d++){
                                                                                                        Division division = (Division)listDivision.get(d);
                                                                                                        Vector listDepartment = PstDepartment.listDepartmentVer1(0, 0, ""+company.getOID(), ""+division.getOID());
                                                                                                        %>
                                                                                                        <div class="div_division">
                                                                                                            <input type="checkbox" name="chx_division" value="<%=company.getOID()%>-<%=division.getOID()%>" /><%=division.getDivision()%>
                                                                                                        <%
                                                                                                        if (listDepartment != null && listDepartment.size()>0){
                                                                                                            for(int dp=0; dp<listDepartment.size(); dp++){
                                                                                                                Department department = (Department)listDepartment.get(dp);
                                                                                                                whereClause = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+"="+department.getOID();
                                                                                                                Vector listSection = PstSection.list(0, 0, whereClause, "");
                                                                                                                %>
                                                                                                                <div class="div_department">
                                                                                                                    <input type="checkbox" name="chx_department" value="<%=company.getOID()%>-<%=division.getOID()%>-<%=department.getOID()%>" /><%=department.getDepartment()%>
                                                                                                                    <%
                                                                                                                    if (listSection != null && listSection.size()>0){
                                                                                                                        for (int s=0; s<listSection.size(); s++){
                                                                                                                            Section section = (Section)listSection.get(s);
                                                                                                                            %>
                                                                                                                            <div class="div_section">
                                                                                                                                <input type="checkbox" name="chx_section" value="<%=company.getOID()%>-<%=division.getOID()%>-<%=department.getOID()%>-<%=section.getOID()%>" /><%=section.getSection()%>
                                                                                                                            </div>
                                                                                                                            <%
                                                                                                                        }
                                                                                                                    }
                                                                                                                    %>
                                                                                                                </div>
                                                                                                                <%
                                                                                                            }
                                                                                                        }
                                                                                                        %>
                                                                                                        </div>
                                                                                                        <%
                                                                                                    }
                                                                                                }
                                                                                                %>
                                                                                                </div>
                                                                                                <%
                                                                                            }
                                                                                        }

                                                                                        %>
                                                                                </td>
                                                                            </tr>
                                                                        </table>                                                                        
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td colspan="2" style="border-top:1px solid #DDD; padding-top: 12px">
                                                                        <button id="btn2" onclick="cmdNext()">Save</button>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                            
                                                        </form>
                                                    </td>
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