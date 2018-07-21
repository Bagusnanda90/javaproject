<%-- 
    Document   : structure_org_ver1
    Created on : Aug 18, 2015, 4:56:28 PM
    Author     : Dimata 007
--%>

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
    
    public String getDrawDownPosition(long oidPosition){
        String str = "";
        String whereClause = PstMappingPosition.fieldNames[PstMappingPosition.FLD_UP_POSITION_ID]+"="+oidPosition;
        whereClause += " AND "+PstMappingPosition.fieldNames[PstMappingPosition.FLD_TEMPLATE_ID]+"=504404598653755546";
        Vector listDown = PstMappingPosition.list(0, 0, whereClause, "");
        if (listDown != null && listDown.size()>0){
            str = "<table class=\"tblStyle\"><tr>";
            for(int i=0; i<listDown.size(); i++){
                MappingPosition pos = (MappingPosition)listDown.get(i);
                str += "<td>"+getPositionName(pos.getDownPositionId())+"<br />"+getDrawDownPosition(pos.getDownPositionId())+"</td>";
            }
            str += "</tr></table>";
        }
        
        return str;
    }
    
    public String getDrawVertical(long oidPosition){
        String str = "";
        String whereClause = PstMappingPosition.fieldNames[PstMappingPosition.FLD_UP_POSITION_ID]+"="+oidPosition;
        whereClause += " AND "+PstMappingPosition.fieldNames[PstMappingPosition.FLD_TEMPLATE_ID]+"=504404598653861995";
        Vector listDown = PstMappingPosition.list(0, 0, whereClause, "");
        if (listDown != null && listDown.size()>0){
            str = "<div class=\"box\">";
            for(int i=0; i<listDown.size(); i++){
                MappingPosition pos = (MappingPosition)listDown.get(i);
                str += "<button class=\"part\">"+getPositionName(pos.getDownPositionId())+"</button>"+getDrawVertical(pos.getDownPositionId());
            }
            str += "</div>";
        }
        
        return str;
    }
   
%>
<!DOCTYPE html>
<%  
    int iCommand = FRMQueryString.requestCommand(request);
    int commandOther = FRMQueryString.requestInt(request, "command_other");
    int chxDir = FRMQueryString.requestInt(request, "chx_dir");
    int chxDiv = FRMQueryString.requestInt(request, "chx_div");
    int chxDep = FRMQueryString.requestInt(request, "chx_dep");
    int chxSec = FRMQueryString.requestInt(request, "chx_sec");
    int filterRd = FRMQueryString.requestInt(request, "filter_rd");
    
    /* Update by Hendra Putu | 20150217 */
    long companyId = FRMQueryString.requestLong(request, "company_id");
    long divisionId = FRMQueryString.requestLong(request, "division_id");
    long departmentId = FRMQueryString.requestLong(request, "department_id");
    
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Struktur Organisasi</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <link rel="stylesheet" href="../styles/tab.css" type="text/css">
        <link href="<%=approot%>/stylesheets/superTables.css" rel="Stylesheet" type="text/css" /> 
        <style type="text/css">
            #mn_utama {color: #FF6600; padding: 5px 14px; border-left: 1px solid #999; font-size: 12px; font-weight: bold; background-color: #F5F5F5;}        
            #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3; color:#0099FF; font-size: 14px; font-weight: bold;}   
            .btn {
              background: #C7C7C7;
              border: 1px solid #BBBBBB;
              border-radius: 3px;
              font-family: Arial;
              color: #474747;
              font-size: 11px;
              padding: 3px 7px;
              cursor: pointer;
            }

            .btn:hover {
              color: #FFF;
              background: #B3B3B3;
              border: 1px solid #979797;
            }
            
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
            .tblStyle td {font-size: 11px; color:#575757; text-align: center; border: 1px solid #999; padding: 3px 5px;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757; }
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
            .part {
                background-color: #bce7e9;
                border:1px solid #64adb0;
                padding: 3px;
                margin: 1px 1px 0px 2px;
                color:#64adb0;
                font-size: 11px;
            }
            .box {
                padding: 0px 5px 0px 7px;
            }
        </style>
        <script type="text/javascript">
            function cmdBack() {
                document.frm.command.value="<%=Command.BACK%>";               
                document.frm.action="structure_src.jsp";
                document.frm.submit();
            }
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
                                        <td height="20"> 
                                            <div id="menu_utama"> 
                                                <button class="btn" onclick="cmdBack()">Back to search</button> &nbsp; Struktur Organisasi
                                            </div> 
                                        </td>
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
                                                        
                                                        </form>
    
                                                        
<%
if (iCommand == Command.VIEW){
    String whereClause = PstMappingPosition.fieldNames[PstMappingPosition.FLD_TEMPLATE_ID]+"=504404598653755546";
    Vector listMapping = PstMappingPosition.list(0, 0, whereClause, "");
    int checkUp = 0;
    long topMain = 0;
    if (listMapping != null && listMapping.size()>0){
        long[] arrUp = new long[listMapping.size()];
        long[] arrDown = new long[listMapping.size()];
        for(int i=0; i<listMapping.size(); i++){
            MappingPosition map = (MappingPosition)listMapping.get(i);
            arrUp[i] = map.getUpPositionId();
            arrDown[i] = map.getDownPositionId();
        }
        for(int j=0; j<arrUp.length; j++){
            for(int k=0; k<arrDown.length; k++){
                if (arrUp[j] == arrDown[k]){
                    checkUp++;
                }
            }
            if (checkUp == 0){
                topMain = arrUp[j];
            }
            checkUp = 0;
        }
        
        if (topMain > 0){
            %>
            <table class="tblStyle">
                <tr>
                    <td>
                        <%=getPositionName(topMain)%>
                        <%=getDrawDownPosition(topMain)%>
                    </td>
                </tr>
            </table>
            <%
        }
    }
}
%>
                                                        

                                                        
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
