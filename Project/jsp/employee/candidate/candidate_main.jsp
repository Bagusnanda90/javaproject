<%-- 
    Document   : candidate_main
    Created on : Sep 6, 2015, 11:56:24 PM
    Author     : Hendra Putu
--%>
<%@page import="com.dimata.qdep.entity.I_DocStatus"%>
<%@page import="com.dimata.harisma.form.employee.FrmCandidateMain"%>
<%@page import="com.dimata.harisma.form.employee.CtrlCandidateMain"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK);%>
<% boolean privGenerate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_GENERATE_SALARY_LEVEL));%>
<%@ include file = "../../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%!
    public String getEmployeeName(long empId){
        String str = "";
        Employee emp = new Employee();
        try {
            emp = PstEmployee.fetchExc(empId);
            str = emp.getFullName();
        } catch(Exception e){
            System.out.println("Employee Name=>"+e.toString());
        }
        return str;
    }
%>
<%
int iCommand = FRMQueryString.requestCommand(request);
long oidCandidateMain = FRMQueryString.requestLong(request, "oid_candidate_main");
long candidateMainId = FRMQueryString.requestLong(request, "candidate_main_id");
long approve1 = FRMQueryString.requestLong(request, "approve_1");
long requestby = FRMQueryString.requestLong(request, "requestby");
int typeMenu = FRMQueryString.requestInt(request, "type_menu");

String titleMenu = "";
if (typeMenu == 0){
    titleMenu = "Candidate";
} else {
    titleMenu = "Talent";
}

String whereClause = "";
int iErrCode = FRMMessage.NONE;
CtrlCandidateMain ctrlCandidateMain = new CtrlCandidateMain(request);
iErrCode = ctrlCandidateMain.action(iCommand, oidCandidateMain);
FrmCandidateMain frmCandidateMain = ctrlCandidateMain.getForm();
CandidateMain candidateMain = ctrlCandidateMain.getCandidateMain();
/////////////////////////////////
if (iCommand == Command.SAVE){
    candidateMainId = candidateMain.getOID(); 
}
if(iCommand == Command.VIEW){
    candidateMain = PstCandidateMain.fetchExc(candidateMainId);
}
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%=titleMenu%> Main</title>
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
            .pesan {
                padding: 21px;
                background-color: #c9ff6a;
                color: #689f09;
                border-radius: 5px;
            }
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

    function cmdAdd(){
        document.frm.oid_candidate_main.value = "0";
        document.frm.command.value="<%=Command.ADD%>";
        getCmd();
    }

    function cmdEdit(oid) {
        document.frm.command.value = "<%=Command.EDIT%>";
        document.frm.oid_candidate_main.value = oid;
        getCmd();
    }
    function cmdBack() {
        document.frm.command.value="<%=Command.BACK%>";               
        getCmd();
    }
    function cmdAsk(oid){
        document.frm.command.value="<%=Command.ASK%>";
        document.frm.oid_candidate_main.value = oid;
        getCmd();
    }
    function cmdDelete(oid){
        document.frm.command.value = "<%=Command.DELETE%>";
        document.frm.oid_candidate_main.value = oid;
        getCmd();
    }
    
    function cmdRequestBy(){
        newWindow=window.open("source_employee.jsp","RequestBy", "height=600,width=500,status=yes,toolbar=no,menubar=no,location=center,scrollbars=yes");
        newWindow.focus();
    }
    
    function cmdApproveBy(val){
        newWindow=window.open("candidate_approve_source.jsp?id="+val,"ApproveSource", "height=600,width=500,status=yes,toolbar=no,menubar=no,location=center,scrollbars=yes");
        newWindow.focus();
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
<script>
$(function() {
    $( "#datepicker1" ).datepicker({ dateFormat: "yy-mm-dd" });
    $( "#datepicker2" ).datepicker({ dateFormat: "yy-mm-dd" });
    
    $( "#datepicker_appr1" ).datepicker({ dateFormat: "yy-mm-dd" });
    $( "#datepicker_appr2" ).datepicker({ dateFormat: "yy-mm-dd" });
    $( "#datepicker_appr3" ).datepicker({ dateFormat: "yy-mm-dd" });
    $( "#datepicker_appr4" ).datepicker({ dateFormat: "yy-mm-dd" });
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
                                        <td height="20"> <div id="menu_utama"><%=titleMenu%> Main</div> </td>
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
                                                            <input type="hidden" name="oid_candidate_main" value="<%=oidCandidateMain%>" />
                                                            <input type="hidden" name="candidate_main_id" value="<%=candidateMainId%>" />
                                                            <table>
                                                                <tr>
                                                                    <td colspan="2" style="border-bottom:1px solid #CCC; padding:15px 9px;">
                                                                        <table cellspacing="0" cellpadding="0" border="0">
                                                                            <tr>
                                                                                <td valign="top"><div id="nav-next" onclick="cmdSaveCandidateMain()">1) <%=titleMenu%> Main</div></td>
                                                                                <td valign="middle"><img src="<%=approot%>/images/nav-next.png" /></td>
                                                                                <% if (typeMenu == 0){ %>
                                                                                <td valign="top"><div id="nav-next-disable" onclick="cmdGoTo('2','<%=candidateMainId%>')">2) <%=titleMenu%> Location</div></td>
                                                                                <td valign="middle"><img src="<%=approot%>/images/nav-next-disable.png" /></td>
                                                                                <% } %>
                                                                                
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
                                                                        <table class="tblStyle">
                                                                            <tr>
                                                                                <td valign="top">Request by</td>
                                                                                <input type="hidden" name="<%=frmCandidateMain.fieldNames[FrmCandidateMain.FRM_FIELD_REQUESTED_BY]%>"  value="<%=requestby%>" />
                                                                                <td valign="top"><input type="text" name="requestview" size="50" value="<%=getEmployeeName(requestby)%>" /><input type="hidden" name="requestby" size="40" value="<%=requestby%>" />&nbsp;<a id="btn1" href="javascript:cmdRequestBy()">Browse</a></td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td valign="top">Candidate Type</td>
                                                                                <td valign="top">
                                                                                    <input type="radio" name="<%=frmCandidateMain.fieldNames[FrmCandidateMain.FRM_FIELD_CANDIDATE_TYPE]%>" value="0" /> Free Position
                                                                                    <input type="radio" name="<%=frmCandidateMain.fieldNames[FrmCandidateMain.FRM_FIELD_CANDIDATE_TYPE]%>" value="1" /> Replacement
                                                                                    <input type="radio" name="<%=frmCandidateMain.fieldNames[FrmCandidateMain.FRM_FIELD_CANDIDATE_TYPE]%>" value="2" /> Rotation
                                                                                    <input type="radio" name="<%=frmCandidateMain.fieldNames[FrmCandidateMain.FRM_FIELD_CANDIDATE_TYPE]%>" value="3" /> Talent Pool
                                                                                    <input type="radio" name="<%=frmCandidateMain.fieldNames[FrmCandidateMain.FRM_FIELD_CANDIDATE_TYPE]%>" value="4" /> Mix
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td valign="top">Title</td>
                                                                                <td valign="top"><input type="text" name="<%=frmCandidateMain.fieldNames[FrmCandidateMain.FRM_FIELD_TITLE]%>" size="70" value="<%=candidateMain.getTitle()%>" /></td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td valign="top">Objective</td>
                                                                                <td valign="top">
                                                                                    <textarea name="<%=frmCandidateMain.fieldNames[FrmCandidateMain.FRM_FIELD_OBJECTIVE]%>" cols="45"><%= candidateMain.getObjective() %></textarea>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td valign="top">Due date</td>
                                                                                <td valign="top"><input type="text" name="<%=frmCandidateMain.fieldNames[FrmCandidateMain.FRM_FIELD_DUE_DATE]%>" id="datepicker1" value="<%= candidateMain.getDueDate() %>" /></td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td valign="top">Date of Request</td>
                                                                                <td valign="top"><input type="text" name="<%=frmCandidateMain.fieldNames[FrmCandidateMain.FRM_FIELD_DATE_OF_REQUEST]%>" id="datepicker2" value="<%= candidateMain.getDateOfRequest() %>" /></td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td colspan="2">
                                                                                    <input type="hidden" name="<%=frmCandidateMain.fieldNames[FrmCandidateMain.FRM_FIELD_CREATED_BY]%>" value="1" />
                                                                                    <input type="hidden" name="<%=frmCandidateMain.fieldNames[FrmCandidateMain.FRM_FIELD_CREATED_DATE]%>" value="2015-09-07" />
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td valign="top">Score Tolerance</td>
                                                                                <td valign="top"><input type="text" name="<%=frmCandidateMain.fieldNames[FrmCandidateMain.FRM_FIELD_SCORE_TOLERANCE]%>" value="<%= candidateMain.getScoreTolerance() %>" /></td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td valign="top">Status Document</td>
                                                                                <td valign="top">
                                                                                    <select name="<%=frmCandidateMain.fieldNames[FrmCandidateMain.FRM_FIELD_STATUS_DOC]%>">
                                                                                        <%
                                                                                        for(int d=0; d<I_DocStatus.fieldDocumentStatus.length; d++){
                                                                                        %>
                                                                                            <option value="<%=d%>"><%=I_DocStatus.fieldDocumentStatus[d]%></option>
                                                                                        <%
                                                                                        }
                                                                                        %>
                                                                                    </select>
                                                                                </td>
                                                                            </tr>
                                                                        </table>
                                                                    </td>
                                                                    
                                                                <input type="hidden" name="<%=frmCandidateMain.fieldNames[FrmCandidateMain.FRM_FIELD_APPROVE_BY_ID_1]%>" value="1" size="45" />
                                                                <input type="hidden" name="<%=frmCandidateMain.fieldNames[FrmCandidateMain.FRM_FIELD_APPROVE_DATE_1]%>" value="2000-01-01" /></td>
                                                                <input type="hidden" name="<%=frmCandidateMain.fieldNames[FrmCandidateMain.FRM_FIELD_APPROVE_BY_ID_2]%>" value="2" />
                                                                <input type="hidden" name="<%=frmCandidateMain.fieldNames[FrmCandidateMain.FRM_FIELD_APPROVE_DATE_2]%>" value="2000-01-01" /></td>
                                                                <input type="hidden" name="<%=frmCandidateMain.fieldNames[FrmCandidateMain.FRM_FIELD_APPROVE_BY_ID_3]%>" value="3" />
                                                                <input type="hidden" name="<%=frmCandidateMain.fieldNames[FrmCandidateMain.FRM_FIELD_APPROVE_DATE_3]%>" value="2000-01-01" /></td>
                                                                <input type="hidden" name="<%=frmCandidateMain.fieldNames[FrmCandidateMain.FRM_FIELD_APPROVE_BY_ID_4]%>" value="4" />
                                                                <input type="hidden" name="<%=frmCandidateMain.fieldNames[FrmCandidateMain.FRM_FIELD_APPROVE_DATE_4]%>" value="2000-01-01" /></td>

                                                                </tr>
                                                                <tr>
                                                                    <td colspan="2" style="border-top:1px solid #DDD; padding-top: 12px">
                                                                        <button id="btn2" onclick="cmdSave()">Save</button>
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
