<%-- 
    Document   : candidate_search
    Created on : Feb 12, 2015, 10:16:13 AM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.harisma.form.employee.FrmPositionCandidate"%>
<%@page import="com.dimata.harisma.form.employee.CtrlPositionCandidate"%>
<%@ page language="java" %>

<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.form.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
    long hrdDepOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    boolean isHRDLogin = hrdDepOid == departmentOid ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
%>
<%!
    public String drawList(Vector objectClass) {

        ControlList ctrlist = new ControlList(); //membuat new class ControlList
        // membuat tampilan dengan controllist

        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.setAreaStyle("customTable");
        ctrlist.addHeader("No", "7%");
        ctrlist.addHeader("Payroll", "20%");
        ctrlist.addHeader("Employee Name", "20%");
        //ctrlist.addHeader("Position", "20%");
        ctrlist.addHeader("Position", "20%");
        ctrlist.addHeader("Score", "32%");

        ctrlist.setLinkRow(1); // untuk menge-sett link di kolom pertama atau dikolom yg lain

        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");

        ctrlist.reset(); //berfungsi untuk menginisialisasi list menjadi kosong

        int no = 0;

        // objectClass mempunyai tipe data Vector
        // objectClass.size(); mendapatkan banyak record
        for (int i = 0; i < objectClass.size(); i++) {
            // membuat object WarningReprimandAyat berdasarkan objectClass ke-i
            PositionCandidateResultSet hasil = (PositionCandidateResultSet)objectClass.get(i);
            // rowx will be created secara berkesinambungan base on i
            Vector rowx = new Vector();

            no = no + 1;
            rowx.add("" + no);
            rowx.add(hasil.getEmployeeNum());
            rowx.add(hasil.getEmpFullName());
            //rowx.add(position.getPosition());
            rowx.add(hasil.getPositionName());
            rowx.add(""+hasil.getScore());
            //rowx.add(""+empComp.getScoreValue());

            lstData.add(rowx);
            // menambah ID ke list LinkData
            lstLinkData.add(String.valueOf(hasil.getEmployeeID()));

        }

        return ctrlist.draw(); // mengembalikan data-data control list

    }
%>
<%  
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidCandidate = FRMQueryString.requestLong(request, "candidate_id");
    long oidPosition = FRMQueryString.requestLong(request, "position_id");
    String[] divisions = FRMQueryString.requestStringValues(request, "divisions");
    
    
    
        
     
    
    
    Date dueDate = FRMQueryString.requestDate(request, "due_date");
    Date selectedDateTo = FRMQueryString.requestDate(request, "check_date_finish");
    /*variable declaration*/
    int recordToGet = 10;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    String whereClause = "";
    String orderClause = "";//

    
    CtrlPositionCandidate ctrPositionCandidate = new CtrlPositionCandidate(request);
    ControlLine ctrLine = new ControlLine();
    Vector listPositionCandidate = new Vector(1, 1);
    Vector listCompetency = new Vector(1,1);

    /*switch statement */
    iErrCode = ctrPositionCandidate.action(iCommand, oidCandidate);
    /* end switch*/
    FrmPositionCandidate frmPositionCandidate = ctrPositionCandidate.getForm();

    /*count list All Position*/
    int vectSize = PstPositionCandidate.getCount(whereClause); //PstWarningReprimandAyat.getCount(whereClause);
    PositionCandidate positionCandidate = ctrPositionCandidate.getPositionCandidate();
    msgString = ctrPositionCandidate.getMessage();
    
    if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
        start = PstPositionCandidate.findLimitStart(positionCandidate.getOID(), recordToGet, whereClause, orderClause);
        oidCandidate = positionCandidate.getOID();
    }

    if ((iCommand == Command.FIRST || iCommand == Command.PREV)
            || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
        start = ctrPositionCandidate.actionList(iCommand, start, vectSize, recordToGet);
    }
    /* end switch list*/

    /* get record to display */
    if (iCommand == Command.LIST){
        String competency = "";
        listCompetency = PstPositionCandidate.listGetCompetency(""+oidPosition);
        for(int j=0; j<listCompetency.size(); j++){
            PositionCompetencyRequired comp = (PositionCompetencyRequired)listCompetency.get(j);
            if(j < listCompetency.size()-1){
                competency += comp.getCompetencyId()+",";//
            } else {
                competency += comp.getCompetencyId();
            }
        }
        
            String checkValues = "";
            for (int i = 0; i < divisions.length; ++i) {
                if (i != divisions.length - 1) {
                    checkValues = checkValues + divisions[i] + ",";
                } else {
                    checkValues = checkValues + divisions[i];
                }
            }
        
        listPositionCandidate = PstPositionCandidate.listEmployeeRelation(checkValues,competency);
    }
    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listPositionCandidate.size() < 1 && start > 0) {
        if (vectSize - recordToGet > recordToGet) {
            start = start - recordToGet;   //go to Command.PREV
        } else {
            start = 0;
            iCommand = Command.FIRST;
            prevCommand = Command.FIRST; //go to Command.FIRST
        }
        listPositionCandidate = PstPositionCandidate.list(start, recordToGet, whereClause, orderClause);
    }


%>
<!-- JSP Block -->
<!-- End of JSP Block -->
<html>
    <!-- #BeginTemplate "/Templates/main.dwt" --> 
    <head>
        <!-- #BeginEditable "doctitle" --> 
        <title>HARISMA - Candidate Search</title>
        <!-- #EndEditable --> 
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <!-- #BeginEditable "styles" --> 
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
        
        <style type="text/css">
            #mn_utama {color: #333; padding: 2px 21px 2px 7px; margin: 5px; border-left: 1px solid #333; font-size: 16px; font-weight: bold; background-color: #d8e8f9;}
            #td1 {padding: 3px 7px 3px 3px; background-color: #F7F7F7; border-collapse: collapse;}
            #td2 {padding: 3px 7px 3px 3px; background-color: #EEE; border-collapse: collapse;}
            #input {padding: 3px; border: 1px solid #CCC; margin: 0px;}
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

            #titleTd {font-weight: bold; padding: 3px 5px; border-bottom: 1px solid #CCC; margin: 3px;}
            #divTd {margin: 5px; padding: 5px; border: 1px solid #DDD;}
            #valTd {padding: 3px 0px 3px 3px;}
            #xTd {padding: 3px; border-left: 1px solid #DDD;}

        </style>
        <script type="text/javascript">
            function getCmd(){
                document.<%=FrmPositionCandidate.FRM_NAME_POSITION_CANDIDATE%>.action = "candidate_search.jsp";
                document.<%=FrmPositionCandidate.FRM_NAME_POSITION_CANDIDATE%>.submit();
            }
            function cmdAdd() {              
                document.<%=FrmPositionCandidate.FRM_NAME_POSITION_CANDIDATE%>.command.value="<%=Command.ADD%>";               
                document.<%=FrmPositionCandidate.FRM_NAME_POSITION_CANDIDATE%>.benefit_master_id.value = "0";
                getCmd();
            }
            function cmdBack() {
                document.<%=FrmPositionCandidate.FRM_NAME_POSITION_CANDIDATE%>.command.value="<%=Command.BACK%>";               
                getCmd();
            }

            function cmdEdit(oid) {
                document.<%=FrmPositionCandidate.FRM_NAME_POSITION_CANDIDATE%>.command.value = "<%=Command.EDIT%>";
                document.<%=FrmPositionCandidate.FRM_NAME_POSITION_CANDIDATE%>.benefit_master_id.value = oid;
                getCmd();
            }

            function cmdSave() {
                document.<%=FrmPositionCandidate.FRM_NAME_POSITION_CANDIDATE%>.command.value = "<%=Command.SAVE%>";
                getCmd();
            }
            function cmdSearch(){
                document.<%=FrmPositionCandidate.FRM_NAME_POSITION_CANDIDATE%>.command.value = "<%=Command.LIST%>";
                getCmd();
            }
            function cmdListFirst(){
                    document.<%=FrmPositionCandidate.FRM_NAME_POSITION_CANDIDATE%>.command.value="<%=Command.FIRST%>";
                    document.<%=FrmPositionCandidate.FRM_NAME_POSITION_CANDIDATE%>.prev_command.value="<%=Command.FIRST%>";
                    getCmd();
            }

            function cmdListPrev(){
                    document.<%=FrmPositionCandidate.FRM_NAME_POSITION_CANDIDATE%>.command.value="<%=Command.PREV%>";
                    document.<%=FrmPositionCandidate.FRM_NAME_POSITION_CANDIDATE%>.prev_command.value="<%=Command.PREV%>";
                    getCmd();
                    }

            function cmdListNext(){
                    document.<%=FrmPositionCandidate.FRM_NAME_POSITION_CANDIDATE%>.command.value="<%=Command.NEXT%>";
                    document.<%=FrmPositionCandidate.FRM_NAME_POSITION_CANDIDATE%>.prev_command.value="<%=Command.NEXT%>";
                    getCmd();
            }

            function cmdListLast(){
                    document.<%=FrmPositionCandidate.FRM_NAME_POSITION_CANDIDATE%>.command.value="<%=Command.LAST%>";
                    document.<%=FrmPositionCandidate.FRM_NAME_POSITION_CANDIDATE%>.prev_command.value="<%=Command.LAST%>";
                    getCmd();
            }               
        </script>
        <!-- #EndEditable --> 
    </head>
    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../../styletemplate/template_header.jsp" %>
            <%} else {%>
            <tr> 
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
                    <!-- #BeginEditable "header" --> 
                    <%@ include file = "../../main/header.jsp" %>
                    <!-- #EndEditable --> </td>
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
            <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2"> 
        <tr> 
          <td width="100%">
      <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
            <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> 
              Employee &gt; Candidate &gt; Candidate Search<!-- #EndEditable --> 
              </strong></font> </td>
        </tr>
        <tr> 
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td class="tablecolor"  style="background-color:<%=bgColorContent%>; ">
                                        
                                            <table border:1px solid <%=garisContent%> width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                <tr>
                                                    <td>
                                                        <form name="<%=FrmPositionCandidate.FRM_NAME_POSITION_CANDIDATE%>" method="POST" action="">
                                                        <input type="hidden" name="start" value="<%=start%>">
                                                        <input type="hidden" name="command" value="<%=iCommand%>">
                                                        <input type="hidden" name="benefit_master_id" value="<%=oidCandidate%>">
                                                        <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                                        <input type="hidden" name="start" value="<%=start%>">
                                                        <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                        
                                                            <table cellspacing="0" cellpadding="0" style="margin: 5px;">
                                                                <tr>
                                                                    <%
                                                                    String CtrOrderClause = PstPosition.fieldNames[PstPosition.FLD_POSITION_ID];
                                                                    Vector vectListPosition = PstPosition.list(0, 500, "", CtrOrderClause);
                                                                    Vector valPosition = new Vector(1, 1); //hidden values that will be deliver on request (oids) 
                                                                    Vector keyPosition = new Vector(1, 1); //texts that displayed on combo box
                                                                    valPosition.add("0");
                                                                    keyPosition.add("All Group");
                                                                    for (int c = 0; c < vectListPosition.size(); c++) {
                                                                        Position pos = (Position)vectListPosition.get(c);
                                                                        valPosition.add("" + pos.getOID());
                                                                        keyPosition.add(pos.getPosition());
                                                                    }
                                                                    %>
                                                                    <td colspan="4" style="font-weight: bold; background-color: #FFF; padding: 5px; border-bottom: 1px solid #DDD;">
                                                                        Position Target &nbsp;
                                                                        <%=ControlCombo.draw("position_id", null, "0", valPosition, keyPosition, "", "input")%>
                                                                    </td>
                                                                </tr>
                                                                
                                                                <tr>
                                                                    <td colspan="4" style="font-weight: bold; background-color: #FFF; padding: 5px; border-bottom: 1px solid #DDD;">Select Company Structure</td>
                                                                </tr>
                                                                <tr>
                                                                    <td valign="top" id="td1">
                                                                        <div id="titleTd">Company</div>
                                                                    </td>

                                                                    <td valign="top" id="td2">
                                                                        <div id="titleTd"><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></div>
                                                                    </td>

                                                                    <td valign="top" id="td1">
                                                                        <div id="titleTd"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>
                                                                    </td>

                                                                    <td valign="top" id="td2">
                                                                        <div id="titleTd"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></div>
                                                                    </td>

                                                                </tr>
                                                                <tr>
                                                                    <td valign="top" id="td1">
                                                                        <%
                                                            Vector comp_value = new Vector(1,1);
                                                            Vector comp_key = new Vector(1,1);
                                                            comp_value.add("0");
                                                            comp_key.add("select ...");
                                                            Vector listComp = PstCompany.list(0, 0, "", " COMPANY ");
                                                            for (int i = 0; i < listComp.size(); i++) {
                                                                    Company comp = (Company) listComp.get(i);
                                                                    comp_key.add(comp.getCompany());
                                                                    comp_value.add(String.valueOf(comp.getOID()));
                                                            }
                                                        %>
                                                              <%= ControlCombo.draw(frmPositionCandidate.fieldNames[FrmPositionCandidate.FRM_FIELD_COMPANY],"formElemen",null, "0", comp_value, comp_key, " size=8 multiple onkeydown=\"javascript:fnTrapKD()\"") %>
                                                        
                                                                    </td>

                                                                    <td valign="top" id="td2">
                                                                        <%
                                                            Vector div_value = new Vector(1,1);
                                                            Vector div_key = new Vector(1,1);
                                                            div_value.add("0");
                                                            div_key.add("select ...");
                                                            Vector listDiv = PstDivision.list(0, 0, "", " DIVISION ");
                                                          if(listDiv!=null && listDiv.size()>0){ 
                                                            for (int i = 0; i < listDiv.size(); i++) {
                                                                    Division division = (Division) listDiv.get(i);
                                                                    div_key.add(division.getDivision());
                                                                    div_value.add(String.valueOf(division.getOID()));
                                                           }
                                                        }
                                                        %>
                                                              <%= ControlCombo.draw("divisions","formElemen",null, "0", div_value, div_key, " size=8 multiple onkeydown=\"javascript:fnTrapKD()\"") %>
                                                              
                                                                    </td>

                                                                    <td valign="top" id="td1">
                                                                        <% 
                                                              
                                                                
                                                                Vector dept_value = new Vector(1, 1);
                                                                Vector dept_key = new Vector(1, 1);
                                                                //Vector listDept = new Vector(1, 1);
                                                                DepartmentIDnNameList keyList = new DepartmentIDnNameList();

                                                                if (processDependOnUserDept) {
                                                                    if (emplx.getOID() > 0) {
                                                                        if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                                                                            keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                                                                            //listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                                        } else {
                                                                            Position position = null;
                                                                            try {
                                                                                position = PstPosition.fetchExc(emplx.getPositionId());
                                                                            } catch (Exception exc) {
                                                                            }
                                                                            if (position != null & position.getDisabedAppDivisionScope() == 0 & position.getPositionLevel() >= PstPosition.LEVEL_MANAGER) {
                                                                                String whereDiv = " d." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + emplx.getDivisionId() + "";
                                                                                keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, whereDiv, true);
                                                                            } else {

                                                                                String whereClsDep = "(" + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + " = " + departmentOid
                                                                                        + ") OR (" + PstDepartment.fieldNames[PstDepartment.FLD_JOIN_TO_DEPARTMENT_ID] + " = " + departmentOid + ") ";
                                                                                try {
                                                                                    String joinDept = PstSystemProperty.getValueByName("JOIN_DEPARMENT");
                                                                                    Vector depGroup = com.dimata.util.StringParser.parseGroup(joinDept);

                                                                                    int grpIdx = -1;
                                                                                    int maxGrp = depGroup == null ? 0 : depGroup.size();
                                                                                    int countIdx = 0;
                                                                                    int MAX_LOOP = 10;
                                                                                    int curr_loop = 0;
                                                                                    do { // find group department belonging to curretn user base in departmentOid
                                                                                        curr_loop++;
                                                                                        String[] grp = (String[]) depGroup.get(countIdx);
                                                                                        for (int g = 0; g < grp.length; g++) {
                                                                                            String comp = grp[g];
                                                                                            if (comp.trim().compareToIgnoreCase("" + departmentOid) == 0) {
                                                                                                grpIdx = countIdx;   // A ha .. found here 
                                                                                            }
                                                                                        }
                                                                                        countIdx++;
                                                                                    } while ((grpIdx < 0) && (countIdx < maxGrp) && (curr_loop < MAX_LOOP)); // if found then exit

                                                                                    // compose where clause
                                                                                    if (grpIdx >= 0) {
                                                                                        String[] grp = (String[]) depGroup.get(grpIdx);
                                                                                        for (int g = 0; g < grp.length; g++) {
                                                                                            String comp = grp[g];
                                                                                            whereClsDep = whereClsDep + " OR (DEPARTMENT_ID = " + comp + ")";
                                                                                        }
                                                                                    }
                                                                                                                    } catch (Exception exc) {
                                                                                                                            System.out.println(" Parsing Join Dept" + exc);
                                                                                                                         
                                                                                                                    }
                                                                                                                    keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, whereClsDep, false);
                                                                                                                    //listDept = PstDepartment.list(0, 0,whereClsDep, "");
                                                                                                                }
                                                                                                            }
                                                                                                        } else {
                                                                                                            keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                                                                                                        }
                                                                                                    } else {
                                                                                                        keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                                                                                                    }
                                                                                                    dept_value = keyList.getDepIDs();
                                                                                                    dept_key = keyList.getDepNames();

                                                                                                    String selectValueDepartment = "0";//+objSrcLeaveApp.getDepartmentId(); 
                                                            %>
                                                              <%//= ControlCombo.draw(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_DEPARTMENT],"formElemen",null, ""+srcSpecialEmployee.getDepartment(), dept_value, dept_key, " size=8 multiple onkeydown=\"javascript:fnTrapKD()\"") %>
                                                               <%=ControlCombo.draw(frmPositionCandidate.fieldNames[FrmPositionCandidate.FRM_FIELD_DEPARTMENT], "formElemen", null, selectValueDepartment, dept_value, dept_key, " size=8 multiple onkeydown=\"javascript:fnTrapKD()\"")%>
                                                                    </td>

                                                                    <td valign="top" id="td2">
                                                                        <% 
                                                                Vector sec_value = new Vector(1,1);
                                                                Vector sec_key = new Vector(1,1); 
                                                                sec_value.add("0");
                                                                sec_key.add("...ALL...");
                                                                Vector listSec = PstSection.list(0, 0, "", " DEPARTMENT_ID, SECTION ");
                                                                for (int i = 0; i < listSec.size(); i++) {
                                                                        Section sec = (Section) listSec.get(i);
                                                                        sec_key.add(sec.getSection());
                                                                        sec_value.add(String.valueOf(sec.getOID()));
                                                                }
                                                            %>
                                                              <%= ControlCombo.draw(frmPositionCandidate.fieldNames[FrmPositionCandidate.FRM_FIELD_SECTION],"formElemen",null, "0", sec_value, sec_key, " size=8 multiple onkeydown=\"javascript:fnTrapKD()\"") %>
                                                                    </td>

                                                                </tr>
                                                                <tr>
                                                                    <td colspan="4"><button id="btn" onclick="cmdSearch()">Search</button></td>
                                                                </tr>
                                                            </table>
                                                               
                                                        </form>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <!--Draw List-->
                                                    
                                                    <%
                                                    if(listPositionCandidate.size() > 0){
                                                    %>
                                                    <%=drawList(listPositionCandidate)%>
                                                    <%
                                                    } else {
                                                    %>
                                                        <p>no record</p>
                                                    <%          
                                                    }
                                                    %>
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
                                </table>
                            </td>
                        </tr>
                    </table>
                </td>
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
    </td></tr></table>
    </body>
    <!-- #BeginEditable "script" --> <script language="JavaScript">
                var oBody = document.body;
                var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
    </script>
                
    <!-- #EndEditable --> <!-- #EndTemplate -->
</html>
