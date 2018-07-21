<%-- 
    Document   : structure_view
    Created on : Aug 27, 2015, 11:20:21 AM
    Author     : Dimata 007
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.dimata.harisma.session.employee.SessEmployeePicture"%>
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

<!DOCTYPE html>
<%  
    int iCommand = FRMQueryString.requestCommand(request);
    int structureOp = FRMQueryString.requestInt(request, "structure_op");
    int rbTime = FRMQueryString.requestInt(request, "rb_time");
    int structureSelect = FRMQueryString.requestInt(request, "structure_select");
    String selectedDate = FRMQueryString.requestString(request, "selected_date");
    int viewMode = FRMQueryString.requestInt(request, "view_mode");
    /* Update by Hendra Putu | 20150217 */
    long companyId = FRMQueryString.requestLong(request, "company_id");
    long divisionId = FRMQueryString.requestLong(request, "division_id");
    long departmentId = FRMQueryString.requestLong(request, "department_id");
    long sectionId = FRMQueryString.requestLong(request, "section_id");
    
    int chkPhoto = FRMQueryString.requestInt(request, "chk_photo");
    int chkGap = FRMQueryString.requestInt(request, "chk_gap");
    int levelRank = FRMQueryString.requestInt(request, "level_rank");
    /* Structure Module adalah kumpulan fungsi utk proses view struktur */
    StructureModule structureModule = new StructureModule();
    Vector listPositionByBrunch = new Vector();
    long parentMain = 0; /* menampung position id utama */
    String whereEmployee = "";
    
    /*
    * Ambil date (current || history)
    * Date : 2015-09-16 
    */
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date now = new Date();
    String dateSelected = "";
    if (rbTime == 1){ /* current */
        dateSelected = dateFormat.format(now);
    } else { /* history */
        dateSelected = selectedDate;
    }
    
    /*
    * Get Data Structure Template
    * Description :
    * Input : dateSelected
    * Output : long[] arrTemp (array template) 
    * Proses : sumber data diambil dari PstStructureTemplate.list();
    * data di-fetch, kemudian di-filter sesuai dengan dateSelected.
    * if (dateSelected == dateTemplate) maka tampung OID template ke array Template
    */
    Vector listTemplate = PstStructureTemplate.list(0, 0, "", "");
    long[] arrTemp = new long[listTemplate.size()];
    int[] arrTotalTemp = new int[listTemplate.size()];
    long templateID = 0;
    if (listTemplate != null && listTemplate.size()>0){
        for(int t=0; t<listTemplate.size(); t++){
            StructureTemplate temp = (StructureTemplate)listTemplate.get(t);
            Date startDateTemp = temp.getStartDate();
            Date endDateTemp = temp.getEndDate();
            Vector rangeDate = structureModule.getRangeOfDate(dateFormat.format(startDateTemp), dateFormat.format(endDateTemp));
            if (rangeDate != null && rangeDate.size()>0){
                for(int r=0; r<rangeDate.size(); r++){
                    String tanggal = (String)rangeDate.get(r);
                    if (tanggal.equals(dateSelected)){
                        arrTemp[t] = temp.getOID();
                    }
                }
            }
        }
    }

/*************************
* How to get template id ?
* Input : structureOp (structure option) | 1 = Pusat, 2 = Cabang
* Output : long templateID atau long parentMain
* FAQ : apa bedanya output templateID dengan parentMain?
*     - Output templateID akan diproses sehingga menampilkan bagan secara KESELURUHAN sesuai TEMPLATE
*     - Output parentMain akan diproses sehingga menampilkan hanya SEBAGIAN bagan sesuai PARENT (Up Position Id)
*/
    if (structureOp == 1){
    /******* Structure Pusat *******
    * jika mode structureOp == 1 maka tampil structure utama dg proses
    * ambil data pada hr_division_type dengan kondisi GROUP TYPE = BOD, dan SHAREHOLDER
    * ambil data division dengan WHERE IN() division_type_id yg sudah didapat
    * ambil data pada hr_position_division dengan WHERE IN(data division id yg sudah didapat)
    * ambil data MappingPosition dengan data WHERE dari proses di atas
    * setelah data mapping position didapat maka cari dimana template id yg paling dominan
    * tentukan template id
    * proses-proses di atas hanya utk mencari template id yg sesuai
    */
        /* Kondisi utk view Structure Utama (Pusat) */
        if (companyId > 0){
            /* if view all */
            Vector listDivType = new Vector();
            Vector listDivisi = new Vector();
            Vector listPosDiv = new Vector();
            Vector listMapPos = new Vector();
            String whereDiv = "";
            String wherePosDiv = "";
            String whereMapPos = "";
            String inData = "";
            String inData2 = "";
            String inData3 = "";

            String whereDivType = PstDivisionType.fieldNames[PstDivisionType.FLD_GROUP_TYPE]+" IN(";
            whereDivType += PstDivisionType.TYPE_BOD+","+PstDivisionType.TYPE_SHAREHOLDER+")";
            listDivType = PstDivisionType.list(0, 0, whereDivType, "");
            if (listDivType != null && listDivType.size()>0){
                inData = "";
                for(int dt=0; dt<listDivType.size(); dt++){
                    DivisionType divType = (DivisionType)listDivType.get(dt);
                    inData += divType.getOID()+",";
                }
                whereDiv = PstDivision.fieldNames[PstDivision.FLD_DIVISION_TYPE_ID]+" IN("+inData+"0)";
                listDivisi = PstDivision.list(0, 0, whereDiv, "");
                if (listDivisi != null && listDivisi.size()>0){
                    inData2 = "";
                    for(int d=0; d<listDivisi.size(); d++){
                        Division divisi = (Division)listDivisi.get(d);
                        inData2 += divisi.getOID()+",";
                    }
                    wherePosDiv = PstPositionDivision.fieldNames[PstPositionDivision.FLD_DIVISION_ID]+" IN("+inData2+"0)";
                    listPosDiv = PstPositionDivision.list(0, 0, wherePosDiv, "");
                    if (listPosDiv != null && listPosDiv.size() > 0) {
                        for (int p = 0; p < listPosDiv.size(); p++) {
                            PositionDivision pos = (PositionDivision) listPosDiv.get(p);
                            inData3 += pos.getPositionId()+",";
                        }
                        whereMapPos = PstMappingPosition.fieldNames[PstMappingPosition.FLD_UP_POSITION_ID]+" IN("+inData3+"0)";
                        String orderMapPos = PstMappingPosition.fieldNames[PstMappingPosition.FLD_TEMPLATE_ID];
                        listMapPos = PstMappingPosition.list(0, 0, whereMapPos, orderMapPos);
                        if (listMapPos != null && listMapPos.size()>0){
                            int inc = 0;
                            int banding = -1;
                            for(int a=0; a<arrTemp.length; a++){
                                for(int p=0; p<listMapPos.size(); p++){
                                    MappingPosition pos = (MappingPosition)listMapPos.get(p);
                                    if (arrTemp[a]==pos.getTemplateId()){
                                        inc++;
                                    }
                                }
                                arrTotalTemp[a] = inc;
                                if (arrTotalTemp[a]>banding){
                                    banding = arrTotalTemp[a];
                                    templateID = arrTemp[a]; /* Hasil akhir | Output */
                                }
                                inc = 0;
                            }
                        }
                    }
                }
            }
        }
        /* Pusat: [ Company / division ] */
        if (companyId > 0 && divisionId > 0){
            /* ambil data dari hr_position_division */
            String wherePosDivision = PstPositionDivision.fieldNames[PstPositionDivision.FLD_DIVISION_ID]+"="+divisionId;
            Vector listPositionDivision = PstPositionDivision.list(0, 0, wherePosDivision, "");
            String inData = "";
            if (listPositionDivision != null && listPositionDivision.size()>0){
                for(int p=0; p<listPositionDivision.size(); p++){
                    PositionDivision posDiv = (PositionDivision)listPositionDivision.get(p);
                    inData += posDiv.getPositionId()+",";
                }
                /* Setelah kumpulan posisi dari hr_position_division didapat, 
                 * maka data dari proses diatas digunakan utk mencari top position */
                String whereMapPos = PstMappingPosition.fieldNames[PstMappingPosition.FLD_UP_POSITION_ID]+" IN("+inData+"0)";
                String orderMapPos = PstMappingPosition.fieldNames[PstMappingPosition.FLD_TEMPLATE_ID];
                Vector listMapPos = PstMappingPosition.list(0, 0, whereMapPos, orderMapPos);
                if (listMapPos != null && listMapPos.size()>0){
                    parentMain = structureModule.getTopPosition(listMapPos); /* Output */
                }
            }
        }
        /* Pusat: [ Company / Division / Department ] */
        /*
        if (companyId > 0 && divisionId > 0 && departmentId > 0){
            String wherePosDepartment = PstPositionDepartment.fieldNames[PstPositionDepartment.FLD_DEPARTMENT_ID]+"=";
        }
        */
    } else {
        /**
        * Structure Cabang
        * Input : structureOp = 2
        * Condition :
        *   1.(companyId > 0 && divisionId > 0 && departmentId == 0 && sectionId == 0)
        *   2.(companyId > 0 && divisionId > 0 && departmentId > 0 && sectionId == 0)
        *   3.(companyId > 0 && divisionId > 0 && departmentId > 0 && sectionId > 0)
        * Output : templateID | long
        */
        String inData = "";
        String whereClause = "";
        if (companyId > 0 && divisionId > 0 && departmentId == 0 && sectionId == 0){
            whereClause = PstPositionDivision.fieldNames[PstPositionDivision.FLD_DIVISION_ID]+"="+divisionId;
            listPositionByBrunch = PstPositionDivision.list(0, 0, whereClause, "");
        }
        
        if (companyId > 0 && divisionId > 0 && departmentId > 0 && sectionId == 0){
            whereClause = PstPositionDepartment.fieldNames[PstPositionDepartment.FLD_DEPARTMENT_ID]+"="+departmentId;
            listPositionByBrunch = PstPositionDepartment.list(0, 0, whereClause, "");
        }
        
        if (companyId > 0 && divisionId > 0 && departmentId > 0 && sectionId > 0){
            whereClause = PstPositionSection.fieldNames[PstPositionSection.FLD_SECTION_ID]+"="+sectionId;
            listPositionByBrunch = PstPositionSection.list(0, 0, whereClause, "");
        }
        
        if (listPositionByBrunch != null && listPositionByBrunch.size()>0){
            if (companyId > 0 && divisionId > 0 && departmentId == 0 && sectionId == 0){
                for(int p=0; p<listPositionByBrunch.size(); p++){
                    PositionDivision positionDivision = (PositionDivision)listPositionByBrunch.get(p);
                    inData += positionDivision.getPositionId()+",";
                }
            }
            if (companyId > 0 && divisionId > 0 && departmentId > 0 && sectionId == 0){
                for(int p=0; p<listPositionByBrunch.size(); p++){
                    PositionDepartment positionDepartment = (PositionDepartment)listPositionByBrunch.get(p);
                    inData += positionDepartment.getPositionId()+",";
                }
            }
            if (companyId > 0 && divisionId > 0 && departmentId > 0 && sectionId > 0){
                for(int p=0; p<listPositionByBrunch.size(); p++){
                    PositionSection positionSection = (PositionSection)listPositionByBrunch.get(p);
                    inData += positionSection.getPositionId()+",";
                }
            }
            /* Proses diatas menghasilkan inData yg akan digunakan utk filter position di MappingPosition */
            String whereMapPos = PstMappingPosition.fieldNames[PstMappingPosition.FLD_UP_POSITION_ID]+" IN("+inData+"0)";
            String orderMapPos = PstMappingPosition.fieldNames[PstMappingPosition.FLD_TEMPLATE_ID];
            Vector listMapPos = PstMappingPosition.list(0, 0, whereMapPos, orderMapPos);
            if (listMapPos != null && listMapPos.size()>0){
                int inc = 0;
                int banding = -1;
                for(int a=0; a<arrTemp.length; a++){
                    for(int p=0; p<listMapPos.size(); p++){
                        MappingPosition pos = (MappingPosition)listMapPos.get(p);
                        if (arrTemp[a]==pos.getTemplateId()){
                            inc++;
                        }
                    }
                    arrTotalTemp[a] = inc;
                    if (arrTotalTemp[a]>banding){
                        banding = arrTotalTemp[a];
                        templateID = arrTemp[a]; /* Output */
                    }
                    inc = 0;
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
            .tblStyle {border-collapse: collapse; background-color: #FFF;}
            .tblStyle td {font-size: 11px; color:#05a5c7; text-align: center; border: 1px solid #C7C7C7; padding: 3px 5px;}
            .tblStyle1 {border-collapse: collapse; background-color: #FFF;}
            .tblStyle1 td {font-size: 11px; color:#575757; border: 1px solid #C7C7C7; padding: 3px 5px;}
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
                color: #0099FF;
                background-color: #FFF;
                padding: 3px 9px;
                margin: 1px 2px;
            }
            .box1 {
                color: #EEE;
                border: 1px solid #999;
                background-color: #CCC;
                padding: 3px 5px;
                margin: 3px 3px 0px 0px
            }
            #space {margin: 3px;}
            .position {
                background-color: #FFF;
                color:#373737;
                padding: 17px 21px;
                margin: 1px 0px;
            }
            #linkStyle {
                text-decoration: none;
                color:#474747;
            }
            #linkStyle:hover {
                color:#ff0000;
            }
            .content {
                background-color: #FFF;
                color:#575757;
                font-size: 11px;
                padding: 17px;
                margin: 3px 0px;
                border-radius: 3px;
            }
        </style>
        <script type="text/javascript">
            function cmdBack() {
                document.frm.command.value="<%=Command.BACK%>";               
                document.frm.action="structure_src.jsp";
                document.frm.submit();
            }
            function cmdViewEmployee(oid){
		document.frm.employee_oid.value=oid;
		document.frm.command.value="<%=Command.EDIT%>";
		document.frm.prev_command.value="<%=Command.EDIT%>";
                document.frm.target="_blank";
		document.frm.action="../employee/databank/employee_edit.jsp";
		document.frm.submit();
            }
            function cmdCetakList(){
                document.frm.action="<%=printroot%>.report.StructureListXLS"; 
                document.frm.target = "ReportExcel";
                document.frm.submit();
            }
            function cmdCetakStruktur(){
                document.frm.target="_blank";
                document.frm.action="structure_preview.jsp";
                document.frm.submit();
            }
            function cmdViewGap(oid){
                newWindow=window.open("view_gap.jsp?oid="+oid,"ViewGap", "height=600,width=500,status=yes,toolbar=no,menubar=no,location=center,scrollbars=yes");
                newWindow.focus();
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
                                                        <!--- fitur filter  --->
                                                        <form name="frm" method="post" action="" target="">
                                                            <input type="hidden" name="command" value="<%=iCommand%>">
                                                            <input type="hidden" name="prev_command" value="<%=iCommand%>">
                                                            <input type="hidden" name="employee_oid" value="">
                                                            <input type="hidden" name="approot" value="<%=approot%>" />
                                                            <input type="hidden" name="rb_time" value="<%=rbTime%>" />
                                                            <input type="hidden" name="structure_op" value="<%=structureOp%>" />
                                                            <input type="hidden" name="company_id" value="<%=companyId%>" />
                                                            <input type="hidden" name="division_id" value="<%=divisionId%>" />
                                                            <input type="hidden" name="department_id" value="<%=departmentId%>" />
                                                            <input type="hidden" name="section_id" value="<%=sectionId%>" />
                                                            <input type="hidden" name="chk_photo" value="<%=chkPhoto%>" />
                                                            <input type="hidden" name="level_rank" value="<%=levelRank%>" />
                                                            <div id="mn_utama">
                                                                Ringkasan Pencarian
                                                            </div>
                                                            <div class="content">
                                                                <table>
                                                                    <tr>
                                                                        <th style="padding-right: 5px">Valid Status</th>
                                                                        <td>
                                                                            <%
                                                                            if(rbTime== 1){
                                                                                %>
                                                                                Current
                                                                                <%
                                                                            } else {
                                                                                %>
                                                                                History
                                                                                <%
                                                                            }
                                                                            %>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <th style="padding-right: 5px">Bagian Struktur</th>
                                                                        <td>
                                                                            <%
                                                                            if (structureOp==1){
                                                                                %>
                                                                                Pusat
                                                                                <%
                                                                            } else {
                                                                                %>
                                                                                Cabang
                                                                                <%
                                                                            }
                                                                            %>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <th style="padding-right: 5px">Sampai Level</th>
                                                                        <td><%=structureModule.getLevelName(levelRank)%></td>
                                                                    </tr>
                                                                    <tr>
                                                                        <th style="padding-right: 5px">Company</th>
                                                                        <td><%=structureModule.getCompanyName(companyId)%></td>
                                                                    </tr>
                                                                    <tr>
                                                                        <th style="padding-right: 5px">Division</th>
                                                                        <td><%=structureModule.getDivisionName(divisionId)%></td>
                                                                    </tr>
                                                                    <tr>
                                                                        <th style="padding-right: 5px">Department</th>
                                                                        <td><%=structureModule.getDepartmentName(departmentId)%></td>
                                                                    </tr>
                                                                    <tr>
                                                                        <th style="padding-right: 5px">Section</th>
                                                                        <td><%=structureModule.getSectionName(sectionId)%></td>
                                                                    </tr>
                                                                    <tr>
                                                                        <th style="padding-right: 5px">Tampilkan Foto</th>
                                                                        <td>
                                                                            <%
                                                                            if(chkPhoto == 1){
                                                                                %>
                                                                                On
                                                                                <%
                                                                            } else {
                                                                                %>
                                                                                Off
                                                                                <%
                                                                            }
                                                                            %>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <th style="padding-right: 5px">Tampilkan Gap</th>
                                                                        <td>
                                                                            <%
                                                                            if(chkGap == 1){
                                                                                %>
                                                                                On
                                                                                <%
                                                                            } else {
                                                                                %>
                                                                                Off
                                                                                <%
                                                                            }
                                                                            %>
                                                                        </td>
                                                                    </tr>
                                                                </table>
                                                            </div>
                                                        
                                                        <div id="mn_utama">
                                                            <%
                                                            if (iCommand == Command.VIEW && viewMode == 1){
                                                            %>
                                                            Organization Structure <button class="btn" onclick="cmdCetakStruktur()">Cetak Struktur</button>
                                                            <% } else if (iCommand == Command.VIEW && viewMode == 2){%>
                                                            List of Organization Structure &nbsp;&nbsp;<button class="btn" onclick="cmdCetakList()">Cetak Unit Kerja</button>
                                                            <% } else { %>
                                                            There is not view
                                                            <% } %>
                                                        </div>
                                                        <div>&nbsp;</div>
                                                        <%
                                                        if (iCommand == Command.VIEW){
                                                            if (structureOp == 1){
                                                                /* Pusat */
                                                                if (companyId > 0 && divisionId == 0){
                                                                    String whereClause = PstMappingPosition.fieldNames[PstMappingPosition.FLD_TEMPLATE_ID]+"="+templateID;
                                                                    Vector listMapping = PstMappingPosition.list(0, 0, whereClause, "");
                                                                    parentMain = structureModule.getTopPosition(listMapping);
                                                                }
                                                            } else { /* structureOp == 2 */
                                                                /* Cabang */
                                                                
                                                                String whereClause = PstMappingPosition.fieldNames[PstMappingPosition.FLD_TEMPLATE_ID]+"="+templateID;
                                                                Vector listMapping = PstMappingPosition.list(0, 0, whereClause, "");
                                                                parentMain = structureModule.getTopPosition(listMapping);
                                                                
                                                            }
                                                            /* mengisi nilai whereEmployee */
                                                            if (companyId > 0 && divisionId == 0 && departmentId == 0 && sectionId == 0){
                                                                whereEmployee += PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]+"="+companyId;
                                                            }
                                                            if (companyId > 0 && divisionId > 0 && departmentId == 0 && sectionId == 0){
                                                                whereEmployee += PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+"="+divisionId;
                                                            }
                                                            if (companyId > 0 && divisionId > 0 && departmentId > 0 && sectionId == 0){
                                                                whereEmployee += PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+"="+divisionId;
                                                                whereEmployee += " AND "+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+"="+departmentId;
                                                            }
                                                            if (companyId > 0 && divisionId > 0 && departmentId > 0 && sectionId > 0){
                                                                whereEmployee += PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+"="+divisionId;
                                                                whereEmployee += " AND "+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+"="+departmentId;
                                                                whereEmployee += " AND "+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+"="+sectionId;
                                                            }
                                                            structureModule.setWhereEmployee(whereEmployee);
                                                            /* jika parentMain sudah terisi nilai Position ID Top maka tampilkan struktur */
                                                            if (viewMode == 1){
                                                                if (parentMain > 0){
                                                                    structureModule.setupEmployee(" AND "+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+"="+parentMain);
                                                                    %>
                                                                    <table class="tblStyle">
                                                                        <tr>
                                                                            <td>
                                                                                <% if (chkPhoto == 1 && structureModule.getEmployeeResign() == 0){ %>
                                                                                <div><img width="64" src="<%=approot%>/imgcache/employee-sample.jpg" style="padding:3px; background-color: #373737;" /></div>
                                                                                <% } %>
                                                                                <div style="color: #373737">
                                                                                    <%
                                                                                    if (structureModule.getEmployeeResign() == 0){
                                                                                        if (structureModule.getEmployeeId() > 0){
                                                                                        %>
                                                                                        <a id="linkStyle" href="javascript:cmdViewEmployee('<%=structureModule.getEmployeeId()%>')">
                                                                                            <strong><%=structureModule.getEmployeeName()%></strong>
                                                                                        </a>
                                                                                        <%
                                                                                        } else {
                                                                                            %>
                                                                                            <strong>-Kosong-</strong>
                                                                                            <%
                                                                                        }
                                                                                    } else {
                                                                                        %>
                                                                                        <strong>-Kosong-</strong>
                                                                                        <%
                                                                                    }
                                                                                    %>
                                                                                </div>
                                                                                <div><%=structureModule.getPositionName(parentMain)%></div>
                                                                                <%=structureModule.getDrawDownPosition(parentMain, templateID, whereEmployee, approot, chkPhoto, levelRank)%>
                                                                            </td>
                                                                        </tr>
                                                                    </table>
                                                                    <%
                                                                }
                                                            }
                                                            if (viewMode == 2){
                                                                if (parentMain > 0){
                                                                    structureModule.setupEmployee(" AND "+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+"="+parentMain);
                                                                    SessEmployeePicture sessEmployeePicture = new SessEmployeePicture();
                                                                    String pictPath = sessEmployeePicture.fetchImageEmployee(structureModule.getEmployeeId());
                                                                %>
                                                                <div>
                                                                    <table>
                                                                        <tr>
                                                                            <td>
                                                                                <table class="tblStyle1">
                                                                                    <tr>
                                                                                        <td>
                                                                                            <div class="box1">1</div>
                                                                                        </td>
                                                                                        <td>
                                                                                            <div class="box">
                                                                                                <%
                                                                                                if (chkPhoto == 1 && structureModule.getEmployeeResign() == 0){
                                                                                                    %>
                                                                                                    <img width="64" src="<%=approot + "/" + pictPath%>" style="padding:3px; background-color: #373737;" />
                                                                                                    <%
                                                                                                }
                                                                                                %>
                                                                                                <div style="color: #373737">
                                                                                                <%
                                                                                                if (structureModule.getEmployeeResign() == 0){
                                                                                                %>
                                                                                                    <a id="linkStyle" href="javascript:cmdViewEmployee('<%=structureModule.getEmployeeId()%>')">
                                                                                                    <strong><%=structureModule.getEmployeeName()%> | </strong>
                                                                                                    <strong><%=structureModule.getEmployeePayroll()%></strong>
                                                                                                    </a>
                                                                                                <% } else { %>
                                                                                                    <strong><%=structureModule.getEmployeeName()%> | </strong>
                                                                                                    <strong><%=structureModule.getEmployeePayroll()%></strong>
                                                                                                <% } %>
                                                                                                </div>
                                                                                                <%=structureModule.getPositionName(parentMain)%>
                                                                                                &nbsp;|&nbsp;<a href="javascript:cmdViewGap('<%=structureModule.getEmployeeId()%>')">VIEW GAP</a>
                                                                                            </div>
                                                                                        </td>
                                                                                        <%
                                                                                        if (chkGap > 0){
                                                                                        %>
                                                                                        <td valign="top">
                                                                                            <strong>Kompetensi yang dibutuhkan</strong>
                                                                                            <%=structureModule.getCompetencyPosition(parentMain)%>
                                                                                        </td>
                                                                                        <%
                                                                                        if (structureModule.getEmployeeResign() == 0){
                                                                                        %>
                                                                                        <td valign="top">
                                                                                            <strong>Kompetensi yang dimiliki</strong>
                                                                                            <%=structureModule.getCompetencyEmployee(parentMain, structureModule.getEmployeeId())%>
                                                                                        </td>
                                                                                        <td valign="top">
                                                                                            <strong>Gap Kompetensi</strong>
                                                                                            <%=structureModule.getCompetencyGap(parentMain, structureModule.getEmployeeId())%>
                                                                                        </td>
                                                                                        <%
                                                                                        }
                                                                                        %>
                                                                                        <td valign="top">
                                                                                            <strong>Pendidikan yang dibutuhkan</strong>
                                                                                            <%=structureModule.getEducationPosition(parentMain)%>
                                                                                        </td>
                                                                                        <td valign="top">
                                                                                            <strong>Pendidikan yang dimiliki</strong>
                                                                                            data
                                                                                        </td>
                                                                                        <td valign="top">
                                                                                            <strong>Gap Pendidikan</strong>
                                                                                            data
                                                                                        </td>
                                                                                        <td valign="top">
                                                                                            <strong>Pelatihan yang dibutuhkan</strong>
                                                                                            <%=structureModule.getTrainingPosition(parentMain)%>
                                                                                        </td>
                                                                                        <td valign="top">
                                                                                            <strong>Pelatihan yang dimiliki</strong>
                                                                                            data
                                                                                        </td>
                                                                                        <td valign="top">
                                                                                            <strong>Gap Pelatihan</strong>
                                                                                            data
                                                                                        </td>
                                                                                        <td valign="top">
                                                                                            <strong>KPI yang dibutuhkan</strong>
                                                                                            data
                                                                                        </td>
                                                                                        <td valign="top">
                                                                                            <strong>KPI yang dimiliki</strong>
                                                                                            data
                                                                                        </td>
                                                                                        <td valign="top">
                                                                                            <strong>Gap KPI</strong>
                                                                                            data
                                                                                        </td>
                                                                                        <% } %>
                                                                                    </tr>
                                                                                </table>
                                                                            </td>
                                                                        </tr>
                                                                    </table>
                                                                    <%=structureModule.getViewList(parentMain, templateID, whereEmployee, 0, approot, chkPhoto, chkGap)%>
                                                                </div>
                                                                <hr>
                                                                <table class="tblStyle">
                                                                    <tr>
                                                                        <td class="title_tbl">Atasan</td>
                                                                        <td class="title_tbl">Position</td>
                                                                        <td class="title_tbl">Payroll</td>
                                                                        <td class="title_tbl">Full Name</td>
                                                                    </tr>
                                                                    <%=structureModule.getViewPrint(parentMain, templateID, whereEmployee)%>
                                                                </table>
                                                                
                                                                <%
                                                                }
                                                            }
                                                        }
                                                        %>
                                                        <div>
                                                            <input type="hidden" name="parent_main" value="<%=parentMain%>" />
                                                            <input type="hidden" name="template_id" value="<%=templateID%>" />
                                                        </div>
                                                        <div id="mn_utama">Position Available : <%=listPositionByBrunch.size()%></div>
                                                        <div>&nbsp;</div>
                                                        <%
                                                            if (structureOp == 2){
                                                                if (companyId > 0 && divisionId > 0 && departmentId == 0 && sectionId == 0){
                                                                    for(int p=0; p<listPositionByBrunch.size(); p++){
                                                                        PositionDivision positionDivision = (PositionDivision)listPositionByBrunch.get(p);
                                                                        String whereEmp = "";
                                                                        whereEmp  = PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]+"="+companyId;
                                                                        whereEmp += " AND "+PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+"="+divisionId;
                                                                        whereEmp += " AND "+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+"="+positionDivision.getPositionId();
                                                                        %>
                                                                        <div class="position">
                                                                            <p style="font-size: 15px; color: #0599ab; padding-bottom: 3px; margin-bottom: 0px;"><%=structureModule.getPositionName(positionDivision.getPositionId())%></p>
                                                                            <%=structureModule.getListEmployeeByPosition(whereEmp)%>
                                                                        </div>
                                                                        <%
                                                                    }
                                                                }
                                                                if (companyId > 0 && divisionId > 0 && departmentId > 0 && sectionId == 0){
                                                                    for(int p=0; p<listPositionByBrunch.size(); p++){
                                                                        PositionDepartment positionDepartment = (PositionDepartment)listPositionByBrunch.get(p);
                                                                        String whereEmp = "";
                                                                        whereEmp  = PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]+"="+companyId;
                                                                        whereEmp += " AND "+PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+"="+divisionId;
                                                                        whereEmp += " AND "+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+"="+departmentId;
                                                                        whereEmp += " AND "+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+"="+positionDepartment.getPositionId();
                                                                        %>
                                                                        <div class="position">
                                                                            <p style="font-size: 15px; color: #0599ab; padding-bottom: 3px; margin-bottom: 0px;"><%=structureModule.getPositionName(positionDepartment.getPositionId())%></p>
                                                                            <%=structureModule.getListEmployeeByPosition(whereEmp)%>
                                                                        </div>
                                                                        <%
                                                                    }
                                                                }
                                                                if (companyId > 0 && divisionId > 0 && departmentId > 0 && sectionId > 0){
                                                                    for(int p=0; p<listPositionByBrunch.size(); p++){
                                                                        PositionSection positionSection = (PositionSection)listPositionByBrunch.get(p);
                                                                        String whereEmp = "";
                                                                        whereEmp  = PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]+"="+companyId;
                                                                        whereEmp += " AND "+PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+"="+divisionId;
                                                                        whereEmp += " AND "+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+"="+departmentId;
                                                                        whereEmp += " AND "+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+"="+sectionId;
                                                                        whereEmp += " AND "+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+"="+positionSection.getPositionId();
                                                                        %>
                                                                        <div class="position">
                                                                            <p style="font-size: 15px; color: #0599ab; padding-bottom: 3px; margin-bottom: 0px;"><%=structureModule.getPositionName(positionSection.getPositionId())%></p>
                                                                            <%=structureModule.getListEmployeeByPosition(whereEmp)%>
                                                                        </div>
                                                                        <%
                                                                    }
                                                                }
                                                            }
                                                        %>
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