<%-- 
    Document   : candidate_matrix
    Created on : Sep 10, 2015, 9:27:20 PM
    Author     : Dimata 007
--%>

<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dimata.harisma.form.employee.FrmCandidateLocation"%>
<%@page import="com.dimata.harisma.form.employee.CtrlCandidateLocation"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK);%>
<% boolean privGenerate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_GENERATE_SALARY_LEVEL));%>
<%@ include file = "../../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%!
    public String getCompanyName(long oidCompany){
        String str = "";
        Company company = new Company();
        try {
            company = PstCompany.fetchExc(oidCompany);
            str = company.getCompany();
        } catch(Exception e){
            System.out.println("getCompanyName=>"+e.toString());
        }
        return str;
    }
    
    public String getDivisionName(long oidDivision){
        String str = "";
        Division division = new Division();
        try {
            division = PstDivision.fetchExc(oidDivision);
            str = division.getDivision();
        } catch(Exception e){
            System.out.println("getDivisionName=>"+e.toString());
        }
        return str;
    }
    
    public String getDepartmentName(long oidDepartment){
        String str = "";
        Department department = new Department();
        try {
            department = PstDepartment.fetchExc(oidDepartment);
            str = department.getDepartment();
        } catch(Exception e){
            System.out.println("getDepartmentName=>"+e.toString());
        }
        return str;
    }
    
    public String getSectionName(long oidSection){
        String str = "";
        Section section = new Section();
        try {
            section = PstSection.fetchExc(oidSection);
            str = section.getSection();
        } catch(Exception e){
            System.out.println("getSectionName=>"+e.toString());
        }
        return str;
    }
    
    public String getLocationName(long locationId){
        String str = "";
        String whereClause = PstCandidateLocation.fieldNames[PstCandidateLocation.FLD_CANDIDATE_LOC_ID]+"="+locationId;
        Vector listCanLoc = PstCandidateLocation.list(0, 0, whereClause, "");
        if (listCanLoc != null && listCanLoc.size()>0){
            for(int i=0; i<listCanLoc.size(); i++){
                CandidateLocation canLoc = (CandidateLocation)listCanLoc.get(i);
                if (canLoc.getGenId() > 0){
                    str += getCompanyName(canLoc.getGenId())+" / ";
                }
                if (canLoc.getDivisionId() > 0){
                    str += getDivisionName(canLoc.getDivisionId())+" / ";
                }
                if (canLoc.getDepartmentId() > 0){
                    str += getDepartmentName(canLoc.getDepartmentId())+" / ";
                }
                if (canLoc.getSectionId() > 0){
                    str += getSectionName(canLoc.getSectionId());
                }
            }
        }
        return str;
    }
%>
<!DOCTYPE html>
<%
int iCommand = FRMQueryString.requestCommand(request);
long candidateMainId = FRMQueryString.requestLong(request, "candidate_main_id");
String[] locationId = FRMQueryString.requestStringValues(request, "location_id");
String[] positionId = FRMQueryString.requestStringValues(request, "position_id");
String[] number = FRMQueryString.requestStringValues(request, "number");
String[] dueDate = FRMQueryString.requestStringValues(request, "due_date");
String[] candidateType = FRMQueryString.requestStringValues(request, "candidate_type");
String[] objectives = FRMQueryString.requestStringValues(request, "objectives");
int typeMenu = FRMQueryString.requestInt(request, "type_menu");

String titleMenu = "";
if (typeMenu == 0){
    titleMenu = "Candidate";
} else {
    titleMenu = "Talent";
}
DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

String whereClause = "";
String orderBy = "";

Vector listDistinct = new Vector();
if (candidateMainId > 0){
    String distinct = PstCandidatePosition.fieldNames[PstCandidatePosition.FLD_CANDIDATE_LOC_ID];
    whereClause = PstCandidatePosition.fieldNames[PstCandidatePosition.FLD_CANDIDATE_MAIN_ID]+"="+candidateMainId;
    listDistinct = PstCandidatePosition.listDistinct(distinct, whereClause);
}
if (iCommand == Command.SAVE && candidateMainId > 0){
    whereClause = PstCandidatePosition.fieldNames[PstCandidatePosition.FLD_CANDIDATE_MAIN_ID]+"="+candidateMainId;
    Vector listPosition = PstCandidatePosition.list(0, 0, whereClause, "");
    CandidatePosition updateCanPos = new CandidatePosition();
    if (listPosition != null && listPosition.size()>0){
        for(int i=0; i<listPosition.size(); i++){
            CandidatePosition canPosition = (CandidatePosition)listPosition.get(i);
            if (canPosition.getCandidateLocId() == Long.valueOf(locationId[i])){
                updateCanPos.setOID(canPosition.getOID());
                updateCanPos.setCandidateMainId(canPosition.getCandidateMainId());
                updateCanPos.setCandidateLocId(Long.valueOf(locationId[i]));
                updateCanPos.setPositionId(Long.valueOf(positionId[i]));
                updateCanPos.setNumberOfCandidate(Integer.valueOf(number[i]));
                updateCanPos.setDueDate(dateFormat.parse(dueDate[i]));
                updateCanPos.setCandidateType(Integer.valueOf(candidateType[i]));
                updateCanPos.setObjectives(objectives[i]);
                try {
                    PstCandidatePosition.updateExc(updateCanPos);
                } catch(Exception e){
                    System.out.println("update candidate position =>"+e.toString());
                }
            }
        }
    }
    /*
    String redirectURL = approot+"/employee/candidate/candidate_selection.jsp?oid="+candidateMainId;
    response.sendRedirect(redirectURL);
    */
}
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Candidate Matrix</title>
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
            .tblStyle {border-collapse: collapse;font-size: 11px;}
            .tblStyle td {padding: 3px 5px; border: 1px solid #CCC;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
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
            .item_title {
                color:#0599ab;
                padding: 7px 21px;
                background-color: #FFF;
                border-left: 1px solid #0099FF;
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
                document.frm.action = "candidate_matrix.jsp";
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
                                        <td height="20"> <div id="menu_utama">Candidate Matrix</div> </td>
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
                                                            <table>
                                                                <tr>
                                                                    <td colspan="2" style="border-bottom:1px solid #CCC; padding:15px 9px;">
                                                                        <table cellspacing="0" cellpadding="0" border="0">
                                                                            <tr>
                                                                                <td valign="top"><div id="nav-next" onclick="cmdGoTo('1','<%=candidateMainId%>')">1) <%=titleMenu%> Main</div></td>
                                                                                <td valign="middle"><img src="<%=approot%>/images/nav-next-enable.png" /></td>
                                                                                <td valign="top"><div id="nav-next" onclick="cmdGoTo('2','<%=candidateMainId%>')">2) <%=titleMenu%> Location</div></td>
                                                                                <td valign="middle"><img src="<%=approot%>/images/nav-next-enable.png" /></td>
                                                                                <td valign="top"><div id="nav-next" onclick="cmdGoTo('3','<%=candidateMainId%>')">3) <%=titleMenu%> Position</div></td>
                                                                                <td valign="middle"><img src="<%=approot%>/images/nav-next-enable.png" /></td>
                                                                                <td valign="top"><div id="nav-next" onclick="cmdGoTo('4','<%=candidateMainId%>')">4) <%=titleMenu%> Detail</div></td>
                                                                                <td valign="middle"><img src="<%=approot%>/images/nav-next.png" /></td>
                                                                                <td valign="top"><div id="nav-next-disable" onclick="cmdGoTo('5','<%=candidateMainId%>')">5) <%=titleMenu%> Selection</div></td>
                                                                                <td valign="middle"><img src="<%=approot%>/images/nav-next-disable.png" /></td>
                                                                                <td valign="top"><div id="nav-next-disable" onclick="cmdGoTo('6','<%=candidateMainId%>')">6) <%=titleMenu%> Summary</div></td>                                                                                
                                                                            </tr>
                                                                        </table>
                                                                    </td>
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
                                                                    <td valign="top">
                                                                        <table>
                                                                            <tr>
                                                                                <td valign="top">
                                                                                    <h4>Position Detail</h4>
                                                                                    <%
                                                                                    if (listDistinct != null && listDistinct.size()>0){
                                                                                        for (int d=0; d<listDistinct.size(); d++){
                                                                                            Long canLocId = (Long)listDistinct.get(d);
                                                                                            whereClause = PstCandidatePosition.fieldNames[PstCandidatePosition.FLD_CANDIDATE_MAIN_ID]+"="+candidateMainId;
                                                                                            whereClause += " AND "+PstCandidatePosition.fieldNames[PstCandidatePosition.FLD_CANDIDATE_LOC_ID]+"="+canLocId;
                                                                                            Vector listCandidatePos = PstCandidatePosition.list(0, 0, whereClause, "");
                                                                                            if (listCandidatePos != null && listCandidatePos.size()>0){
                                                                                                %>
                                                                                                <p class="item_title"><%=getLocationName(canLocId)%></p>
                                                                                                <table border="0" cellspacing="0" cellpadding="0" class="tblStyle">
                                                                                                    <tr>
                                                                                                        <td class="title_tbl">Position</td>
                                                                                                        <td class="title_tbl">Number of Candidate</td>
                                                                                                        <td class="title_tbl">Due date</td>
                                                                                                        <td class="title_tbl">Candidate Type</td>
                                                                                                        <td class="title_tbl">Objectives</td>
                                                                                                    </tr>
                                                                                                <%
                                                                                                for(int i=0; i<listCandidatePos.size(); i++){
                                                                                                    CandidatePosition canPos = (CandidatePosition)listCandidatePos.get(i);
                                                                                                    %>
                                                                                                    <tr>
                                                                                                    <input type="hidden" name="location_id" value="<%=canLocId%>" />
                                                                                                    <input type="hidden" name="position_id" value="<%=canPos.getPositionId()%>" />
                                                                                                        <td><%=PstPosition.getPositionName(String.valueOf(canPos.getPositionId()))%></td>
                                                                                                        <td><input type="text" name="number" value="<%=canPos.getNumberOfCandidate()%>" /></td>
                                                                                                        <td><input type="text" id="datepicker1" name="due_date" value="<%=canPos.getDueDate()%>" /></td>
                                                                                                        <td>
                                                                                                            <select name="candidate_type">
                                                                                                                <%
                                                                                                                for(int t=0; t<PstCandidatePosition.candidateTypeNames.length; t++){
                                                                                                                    %>
                                                                                                                    <option value="<%=t%>"><%=PstCandidatePosition.candidateTypeNames[t]%></option>
                                                                                                                    <%
                                                                                                                }
                                                                                                                %>
                                                                                                            </select>
                                                                                                        </td>
                                                                                                        <td><textarea name="objectives"><%=canPos.getObjectives()%></textarea></td>
                                                                                                    </tr>
                                                                                                    <%
                                                                                                }
                                                                                                %>
                                                                                                </table>
                                                                                                <%
                                                                                            }
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