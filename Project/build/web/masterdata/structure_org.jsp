<%-- 
    Document   : structure_org
    Created on : Jul 31, 2015, 9:46:50 AM
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
    
    if (filterRd == 1){
        chxDir = 1;
        chxDiv = 1;
        chxDep = 1;
    }

    /*Cari id bod*/
    String whereDT = PstDivisionType.fieldNames[PstDivisionType.FLD_GROUP_TYPE]+"="+PstDivisionType.TYPE_BOD;
    long oidBODType = 0;
    Vector listDivisionType = PstDivisionType.list(0, 0, whereDT, "");
    if (listDivisionType != null && listDivisionType.size()>0){
        for(int ldt=0; ldt<listDivisionType.size(); ldt++){
            DivisionType dt = (DivisionType)listDivisionType.get(ldt);
            oidBODType = dt.getOID();
        }
    }
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
    Vector section_v = new Vector();
    Vector section_k = new Vector();
    String whereSection = "DEPARTMENT_ID = "+departmentId;
    section_value.add("0");
    section_key.add("-select-");
    section_v.add("0");
    section_k.add("-select-");


    Vector listComp = PstCompany.list(0, 0, "", " COMPANY ");
    for (int i = 0; i < listComp.size(); i++) {
    Company comp = (Company) listComp.get(i);
        if (comp.getOID() == companyId){
          comp_selected = String.valueOf(companyId);
        }
        comp_key.add(comp.getCompany());
        comp_value.add(String.valueOf(comp.getOID()));
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
            .tblStyle td {font-size: 11px;}
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
        </style>
        <script type="text/javascript">
            function cmdSubmit(){
                document.frm.command.value="<%=Command.VIEW%>";
                document.frm.action="structure_org.jsp";
                document.frm.submit();
            }
            
            function cmdShowOptionDetail(posId){
                newWindow=window.open("structure_option.jsp?pos_id="+posId, "OptionDetail", "height=500,width=500, status=yes, toolbar=no, menubar=no, location=center, scrollbars=yes");
                newWindow.focus();
            }
            
            function cmdEmpDetail(posId){
                newWindow=window.open("struct_emp_detail.jsp?pos_id="+posId, "EmpDetail", "height=500,width=500, status=yes, toolbar=no, menubar=no, location=center, scrollbars=yes");
                newWindow.focus();
            }
            
            function cmdPosDetail(posId){
                newWindow=window.open("struct_pos_detail.jsp?pos_id="+posId, "EmpDetail", "height=500,width=500, status=yes, toolbar=no, menubar=no, location=center, scrollbars=yes");
                newWindow.focus();
            }
            
            function compChange(val) 
            {
                document.frm.command.value = "<%=Command.GOTO%>";
                document.frm.company_id.value = val;
                document.frm.division_id.value = "0";
                document.frm.department_id.value = "0";
                document.frm.action = "structure_org.jsp";
                document.frm.submit();
            }
            function divisiChange(val) 
            {
                document.frm.command.value = "<%=Command.GOTO%>";
                document.frm.division_id.value = val;
                document.frm.action = "structure_org.jsp";
                document.frm.submit();
            }
            function deptChange(val) 
            {
                document.frm.command.value = "<%=Command.GOTO%>";	
                document.frm.department_id.value = val;
                document.frm.action = "structure_org.jsp";
                document.frm.submit();
            }
            
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
        
/* Search Divisi BOD */
// get data that have BOD type
String whereBOD = PstDivision.fieldNames[PstDivision.FLD_DIVISION_TYPE_ID]+"="+oidBODType;
Vector listBOD = PstDivision.list(0, 0, whereBOD, "");
long oidBOD = 0;
if (listBOD != null && listBOD.size()>0){
    for(int b=0; b<listBOD.size(); b++){
        Division div = (Division)listBOD.get(b);
        oidBOD = div.getOID(); /* get BOD ID*/
    }
}
/* mendapatkan list yang bertipe BOD */
String wherePosBOD = PstPositionDivision.fieldNames[PstPositionDivision.FLD_DIVISION_ID]+"="+oidBOD;
Vector listPosBOD = PstPositionDivision.list(0, 0, wherePosBOD, "");
String whereManagePos = "";
long direkturTop = 0;
Vector listDownDir = new Vector(1,1);
if (listPosBOD != null && listPosBOD.size()>0){
    for(int db=0; db<listPosBOD.size(); db++){
        PositionDivision posDiv = (PositionDivision)listPosBOD.get(db);
        /* Cek pada hr_managing_position */
        whereManagePos = PstTopPosition.fieldNames[PstTopPosition.FLD_POSITION_ID]+"="+posDiv.getPositionId();
        listDownDir = PstTopPosition.list(0, 0, whereManagePos, "");
        if (listDownDir != null && listDownDir.size()>0){
            TopPosition tp = (TopPosition)listDownDir.get(0);
            direkturTop = tp.getPositionToplink(); /* mendapatkan ID Top Link */
        }
    }
}
%>
<table class="tblStyle">
    <tr>
        <td valign="middle">
            <div class="shareholder">
                <table class="tblStyle">
                    <tr>
                        <td>Rapat Umum Pemegang Saham</td>
                    </tr>
                    <tr>
                        <td>
                            <div class="comp_media">
                                <table class="tblStyle">
                                    <tr>
                                        <td>Dewan Komisaris</td>
                                    </tr>
                                    <tr>
                                        <td style="background-color: #FF6600">Komite : 1.Udit, 2.Pemantau Risiko, 3.Remunerasi dan Nominasi</td>
                                    </tr>
                                </table>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
        </td>
    </tr>
</table>
<table class="tblStyle">
    <tr>
        <td valign="middle">
            <i style="font-size: 11px">Board Of Director</i>
            <%
            /* mencari bawahannya direktur */
            String whereDownlinkDir = PstTopPosition.fieldNames[PstTopPosition.FLD_POSITION_TOPLINK]+"="+direkturTop;
            Vector listDownlinkDir = PstTopPosition.list(0, 0, whereDownlinkDir, "");
            %>
            <table class="tblStyle">
                <%if(direkturTop > 0 && chxDir != 0){%>
                <tr>
                    <td align="center" colspan="<%=listDownlinkDir.size()%>">
                        <div class="dir" style="text-align: center">
                            <img width="64" src="<%=approot%>/images/photocv.jpg" style="padding:3px; background-color: #EEE;" /><br />
                            <div class="detail_pos" onclick="cmdShowOptionDetail('<%=direkturTop%>')"><%=getPositionName(direkturTop)%></div>
                        </div>
                    </td>
                </tr>
                <%}%>
                <tr>
                    <%
                    if (listDownlinkDir != null && listDownlinkDir.size()>0){
                        for(int dld=0; dld<listDownlinkDir.size(); dld++){
                            TopPosition dwn = (TopPosition)listDownlinkDir.get(dld);//
                            String whereDivi = PstTopPosition.fieldNames[PstTopPosition.FLD_POSITION_TOPLINK]+"="+dwn.getPositionId();
                            Vector listDivision = PstTopPosition.list(0, 0, whereDivi, "");
                            %>
                            <td valign="top">
                                <div class="dir" style="text-align: center">
                                <% if (chxDir != 0){%>
                                <img width="64" src="<%=approot%>/images/photocv.jpg" style="padding:3px; background-color: #EEE;" /><br />
                                <% } %>
                                <table class="tblStyle">
                                    <% if (chxDir != 0){%>
                                    <tr>
                                        <td valign="top" colspan="<%=listDivision.size()%>"><div class="detail_pos" onclick="cmdShowOptionDetail('<%=dwn.getPositionId()%>')"><%=getPositionName(dwn.getPositionId())%></div></td>
                                    </tr>
                                    <% } %>
                                    <tr>
                                        <%
                                        if (listDivision != null && listDivision.size()>0){
                                            for(int ldiv=0; ldiv<listDivision.size(); ldiv++){
                                                TopPosition dvp = (TopPosition)listDivision.get(ldiv);
                                                String whDep = PstTopPosition.fieldNames[PstTopPosition.FLD_POSITION_TOPLINK]+"="+dvp.getPositionId();
                                                Vector listDep = PstTopPosition.list(0, 0, whDep, "");
                                                %>
                                                <td valign="top">
                                                    <% if (chxDiv != 0){ %>
                                                    <div class="detail" onclick="cmdEmpDetail('<%=dvp.getPositionId()%>')">DETAIL</div>
                                                    <% } %>
                                                    <div class="div">
                                                    <table class="tblStyle">
                                                        <% if (chxDiv != 0){ %>
                                                        <tr>
                                                            <td colspan="<%=listDep.size()%>">
                                                                <div class="detail_pos" onclick="cmdShowOptionDetail('<%=dvp.getPositionId()%>')">
                                                                    <%=getPositionName(dvp.getPositionId())%>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                        <% } %>
                                                        <tr>
                                                            <td valign="top">
                                                            <%
                                                            if (listDep != null && listDep.size()>0){
                                                                for(int ldp=0; ldp<listDep.size(); ldp++){
                                                                    TopPosition dp = (TopPosition)listDep.get(ldp);
                                                                    if (chxDep != 0){
                                                                    %>
                                                                    
                                                                    <div class="dep">
                                                                        <div class="detail1" onclick="cmdEmpDetail('<%=dp.getPositionId()%>')">DETAIL</div>
                                                                        <%=getPositionName(dp.getPositionId())%>
                                                                    </div>
                                                                    
                                                                    <%
                                                                    }
                                                                }
                                                            }
                                                            %>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                    </div>
                                                </td>
                                                <%
                                            }
                                        }
                                        %>
                                        
                                    </tr>
                                </table>
                                </div>
                            </td>
                            <%
                        }
                    }
                    %>
                </tr>
            </table>
        </td>
    </tr>
</table>
<%
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