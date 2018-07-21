<%-- 
    Document   : riwayat_jabatan_result
    Created on : Sep 14, 2015, 5:12:03 PM
    Author     : Dimata 007
--%>

<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK);%>
<% boolean privGenerate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_GENERATE_SALARY_LEVEL));%>
<%@ include file = "../../main/checkuser.jsp" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%!
    public String getEmployeeName(long employeeId){
        String str = "";
        try {
            Employee emp = PstEmployee.fetchExc(employeeId);
            str = emp.getFullName();
        } catch(Exception e){
            System.out.println("Employee Name =>"+e.toString());
        }
        return str;
    }

    public String drawList(Vector objectClass, Vector rangeDate) {
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("tblStyle");
        ctrlist.setTitleStyle("title_tbl");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("title_tbl");
        ctrlist.setCellSpacing("0");
        ctrlist.addHeader("Perusahaan", "");
        ctrlist.addHeader("Satuan Kerja", "");
        ctrlist.addHeader("Unit", "");
        ctrlist.addHeader("Sub Unit", "");
        ctrlist.addHeader("Jabatan", "");
        ctrlist.addHeader("Nama Karyawan","");
        ctrlist.addHeader("Level", "");
        ctrlist.addHeader("Kerja dari", "");
        ctrlist.addHeader("Sampai", "");
        ctrlist.addHeader("Deskripsi", "");
        ctrlist.setLinkRow(1);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        StructureModule structureModule = new StructureModule();
        if (objectClass != null && objectClass.size()>0){
            for(int i=0; i<objectClass.size(); i++){
                CareerPath careerPath = (CareerPath) objectClass.get(i);
                Vector rowx = new Vector();
                
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String workFrom = dateFormat.format(careerPath.getWorkFrom());
                String workTo = dateFormat.format(careerPath.getWorkTo());
                
                Vector listRange = structureModule.getRangeOfDate(workFrom, workTo);
                int inc = 0;
                if (rangeDate != null && rangeDate.size()>0){
                    for(int r1=0; r1<listRange.size(); r1++){
                        String range1 = (String)listRange.get(r1);
                        for(int r2=0; r2<rangeDate.size(); r2++){
                            String range2 = (String)rangeDate.get(r2);
                            if (range1.equals(range2)){
                               inc++; 
                            }
                        }
                    }
                    if (inc > 0){
                        rowx.add(careerPath.getCompany());
                        rowx.add(careerPath.getDivision());
                        rowx.add(careerPath.getDepartment());
                        rowx.add(careerPath.getSection());
                        rowx.add(careerPath.getPosition());
                        rowx.add(getEmployeeName(careerPath.getEmployeeId()));
                        rowx.add(careerPath.getLevel());
                        rowx.add(""+careerPath.getWorkFrom());
                        rowx.add(""+careerPath.getWorkTo());
                        rowx.add(careerPath.getDescription());
                    }
                } else {
                    rowx.add(careerPath.getCompany());
                    rowx.add(careerPath.getDivision());
                    rowx.add(careerPath.getDepartment());
                    rowx.add(careerPath.getSection());
                    rowx.add(careerPath.getPosition());
                    rowx.add(getEmployeeName(careerPath.getEmployeeId()));
                    rowx.add(careerPath.getLevel());
                    rowx.add(""+careerPath.getWorkFrom());
                    rowx.add(""+careerPath.getWorkTo());
                    rowx.add(careerPath.getDescription());
                }
                
                
                lstData.add(rowx);
                lstLinkData.add(String.valueOf(careerPath.getOID()));
            }
        }
        return ctrlist.draw();
    }
%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    long companyId = FRMQueryString.requestLong(request, "company_id");
    long divisionId = FRMQueryString.requestLong(request, "division_id");
    long departmentId = FRMQueryString.requestLong(request, "department_id");
    long sectionId = FRMQueryString.requestLong(request, "section_id");
    long positionId = FRMQueryString.requestLong(request, "position_id");
    long levelId = FRMQueryString.requestLong(request, "level_id");
    String startDate = FRMQueryString.requestString(request, "start_date");
    String endDate = FRMQueryString.requestString(request, "end_date");
    
    StructureModule structureModule = new StructureModule();
    Vector rangeOfDate = new Vector();
    if (startDate.length()>0){
        rangeOfDate = structureModule.getRangeOfDate(startDate, endDate);
    }
    
    String orderBy = "";
    String whereClause = "";
    Vector listCareerPath = new Vector();
    if (iCommand == Command.VIEW){
        whereClause = PstCareerPath.fieldNames[PstCareerPath.FLD_COMPANY_ID]+"="+companyId;
        if (divisionId > 0){
            whereClause += " AND ";
            whereClause += PstCareerPath.fieldNames[PstCareerPath.FLD_DIVISION_ID]+"="+divisionId;
        }
        if (departmentId > 0){
            whereClause += " AND ";
            whereClause += PstCareerPath.fieldNames[PstCareerPath.FLD_DEPARTMENT_ID]+"="+departmentId;
        }
        if (sectionId > 0){
            whereClause += " AND ";
            whereClause += PstCareerPath.fieldNames[PstCareerPath.FLD_SECTION_ID]+"="+sectionId;
        }
        if (positionId > 0){
            whereClause += " AND ";
            whereClause += PstCareerPath.fieldNames[PstCareerPath.FLD_POSITION_ID]+"="+positionId;
        }
        if (levelId > 0){
            whereClause += " AND ";
            whereClause += PstCareerPath.fieldNames[PstCareerPath.FLD_LEVEL_ID]+"="+levelId;
        }

        listCareerPath = PstCareerPath.list(0, 0, whereClause, "");
    }
    
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hasil Riwayat Jabatan</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
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
            .tblStyle td {padding: 3px 7px; color: #575757; border: 1px solid #CCC;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757; }
            .title_part {background-color: #FFF; border-left: 1px solid #3cb0fd; padding:5px 15px;  color: #575757; margin: 1px 0px;}
            
        </style>


<script type="text/javascript">
    function cmdBack() {
        document.frm.command.value="<%=Command.BACK%>";               
        document.frm.action="riwayat_jabatan_search.jsp";
        document.frm.submit();
    }
    function getCmd(){
        document.frm.action = "candidate_main.jsp";
        document.frm.submit();
    }
    function cmdSave(){
        document.frm.command.value = "<%=Command.SAVE%>";
        getCmd();
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
                                        <td height="20"> <div id="menu_utama"><button class="btn" onclick="cmdBack()">Back to search</button> Hasil Riwayat Jabatan</div> </td>
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
                                                                    <td>
                                                                        <%
                                                                        if (iCommand == Command.VIEW){
                                                                            %>
                                                                            <%=drawList(listCareerPath, rangeOfDate)%>
                                                                            <%
                                                                        }
                                                                        %>
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