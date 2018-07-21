
<% 
/* 
 * Page Name  		:  recognition.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		:  [authorName] 
 * @version  		:  [version] 
 */

/*******************************************************************
 * Page Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 			: [output ...] 
 *******************************************************************/
%>
<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>

<%//@ include file = "../../main/javainit.jsp" %>
<%// int  appObjCode = 1;//AppObjInfo.composeObjCode(AppObjInfo.G1_SECRETARY , AppObjInfo.G2_SECRETARY_GENERAL   , AppObjInfo.OBJ_SG_RECOGNITION   	); %>
<%//@ include file = "../../main/checkuser.jsp" %>
<%// privStart = true;%>
<%
    /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//	boolean privStart=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
//    boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//    boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//    boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE , AppObjInfo.G2_RECOGNITION  , AppObjInfo.OBJ_ENTRY_PER_DEPT); %>
<%@ include file = "../../main/checkuser.jsp" %>
<% privStart = true;%>
<%
    /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
      privStart=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
      privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
      privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
      privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<%
        long hrdDepOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    boolean isHRDLogin = hrdDepOid == departmentOid ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
%>
<!-- Jsp Block -->
<%!
    public String drawList(Vector objectClass, Date gotoDate){
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("80%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("No.","2%");
        ctrlist.addHeader("Payroll","5%");
        ctrlist.addHeader("Full Name","15%");
        ctrlist.addHeader("Position","15%");
        ctrlist.addHeader("Points","5%");

        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.reset();

        Hashtable position = new Hashtable();
        Vector listPosition = PstPosition.listAll();
        position.put("0", "-");
        for (int ls = 0; ls < listPosition.size(); ls++) {
            Position pos = (Position) listPosition.get(ls);
            position.put(String.valueOf(pos.getOID()), pos.getPosition());
        }

        Hashtable hDept = new Hashtable();
        Vector listDept = PstDepartment.listAll();
        hDept.put("0", "-");
        for (int ls = 0; ls < listDept.size(); ls++) {
            Department dept = (Department) listDept.get(ls);
            hDept.put(String.valueOf(dept.getOID()), dept.getDepartment());
        }

        Vector vPoint = new Vector(1, 1);
        for (int i = 0; i < objectClass.size(); i++) {
            Employee employee = (Employee) objectClass.get(i);
            Vector rowx = new Vector();
            String where = "";

            rowx.add(String.valueOf(i+1));
            rowx.add(employee.getEmployeeNum());
            rowx.add(employee.getFullName());
            rowx.add(position.get(String.valueOf(employee.getPositionId())));

            where = " EMPLOYEE_ID = " + String.valueOf(employee.getOID()) +
                    " AND RECOG_DATE = '" + ""+(gotoDate.getYear()+1900)+"-"+""+(gotoDate.getMonth()+1)+"-"+""+gotoDate.getDate() + "' ";
            //update by satrya 2012-12-20
            //String order = PstRecognition.fieldNames[PstRecognition.FLD_POINT] + " DESC ";
            vPoint = PstRecognition.list(0, 0, where, "");
            if (vPoint.size() > 0) {
                Recognition rec = (Recognition) vPoint.get(0);
                rowx.add("<input type=\"hidden\" name=\"employee_id\" value=\"" + employee.getOID() + "\"><input type=\"textbox\" class=\"elementForm\" name=\"point\" value=\"" + String.valueOf(rec.getPoint()) + "\" size=\"5\">");
            }
            else {
                rowx.add("<input type=\"hidden\" name=\"employee_id\" value=\"" + employee.getOID() + "\"><input type=\"textbox\" class=\"elementForm\" name=\"point\" value=\"0\" size=\"5\">");
            }
            lstData.add(rowx);
            //lstLinkData.add();
        }

        ctrlist.setLinkSufix("')");
        return ctrlist.draw();
    }
%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    long gotoDept = FRMQueryString.requestLong(request, "hidden_goto_dept");
    Date gotoDate = FRMQueryString.requestDate(request, "RECOG_DATE");

    String[] point = null;
    String[] employee_id = null;
    Vector vList = new Vector(1, 1);
    String whereClauseRecognition = "";

    if (iCommand == Command.NONE) {
        gotoDate = new Date();
    }

    if (iCommand == Command.SAVE) {
        try {
            employee_id = request.getParameterValues("employee_id");
            point = request.getParameterValues("point");
        }
        catch (Exception e) {}

        for (int i = 0; i < point.length; i++) {
            whereClauseRecognition = " EMPLOYEE_ID = " + String.valueOf(employee_id[i]) + 
                                     " AND RECOG_DATE = '" + ""+(gotoDate.getYear()+1900)+"-"+""+(gotoDate.getMonth()+1)+"-"+""+gotoDate.getDate() + "' ";
            vList = PstRecognition.list(0, 0, whereClauseRecognition, "");
            if (vList.size() > 0) {
                if (Integer.parseInt(point[i]) > 0) {
                    Recognition recognition = (Recognition) vList.get(0);
                    recognition.setEmployeeId(Long.parseLong(employee_id[i]));
                    recognition.setRecogDate(gotoDate);
                    recognition.setPoint(Integer.parseInt(point[i]));
                    PstRecognition.updateExc(recognition);
                }
                else {
                    Recognition recognition = (Recognition) vList.get(0);
                    PstRecognition.deleteExc(recognition.getOID());
                }
            }
            else {
                if (Integer.parseInt(point[i]) > 0) {
                    Recognition recognition = new Recognition();
                    recognition.setEmployeeId(Long.parseLong(employee_id[i]));
                    recognition.setRecogDate(gotoDate);
                    recognition.setPoint(Integer.parseInt(point[i]));
                    PstRecognition.insertExc(recognition);
                }
            }
        }
    }

    int start = 0;
    int recordToGet = 0;
    String whereClause = PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + String.valueOf(gotoDept);
        whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 0 ";
   // String orderClause = "EMPLOYEE_NUM";
        //update by satrya 2012-12-20
        String orderClause = PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
    Vector listEmp = new Vector(1,1);
    listEmp = PstEmployee.list(start, recordToGet, whereClause, orderClause);
%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Employee</title>
<script language="JavaScript">

	

    function cmdSave() {
        document.frmbarcode.command.value = "<%=Command.SAVE%>";
        document.frmbarcode.hidden_goto_dept.value = document.frmbarcode.DEPARTMENT_ID.value;
        document.frmbarcode.action = "recognitiondep.jsp";
        document.frmbarcode.submit();
    }

    function cmdGetList() {
        document.frmbarcode.command.value = "<%=Command.GOTO%>";
        document.frmbarcode.hidden_goto_dept.value = document.frmbarcode.DEPARTMENT_ID.value;
        document.frmbarcode.action = "recognitiondep.jsp";
        document.frmbarcode.submit();
    }

//-------------- script control line -------------------
	function MM_swapImgRestore() { //v3.0
		var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
	}

function MM_preloadImages() { //v3.0
		var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
		var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
		if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
	}

function MM_findObj(n, d) { //v4.0
		var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
		d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
		if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
		for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
		if(!x && document.getElementById) x=document.getElementById(n); return x;
	}

function MM_swapImage() { //v3.0
		var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
		if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
	}

</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<SCRIPT language=JavaScript>
    function hideObjectForEmployee(){
        //document.frmsrcemployee.<%//=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_START_COMMENC] + "_mn"%>.style.visibility = 'hidden';
    } 
	 
    function hideObjectForLockers(){ 
    }
	
    function hideObjectForCanteen(){
    }
	
    function hideObjectForClinic(){
    }

    function hideObjectForMasterdata(){
    }
	
    function showObjectForMenu(){
        //document.all.<%//=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_START_COMMENC] + "_mn"%>.style.visibility = "";
    }
</SCRIPT>
<!-- #EndEditable --> 
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
   <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
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
                  Employee &gt; Recognition Data Entry per Department<!-- #EndEditable --> 
                  </strong></font> </td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td  style="background-color:<%=bgColorContent%>;"> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                          <tr> 
                            <td valign="top"> 
                              <table style="border:1px solid <%=garisContent%>"  width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                <tr> 
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                <% //if (privStart) { %>
                                    <form name="frmbarcode" method="post" action="">
                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                    <input type="hidden" name="hidden_goto_dept" value="<%=gotoDept%>">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                            <td>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td width="1%">&nbsp;</td>
                                                <td width="12%"> 
                                                  <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>
                                                </td>
                                                <td width="87%"> 
                                                  <% 
                                                       /* Vector dept_value = new Vector(1,1);
                                                        Vector dept_key = new Vector(1,1);
                                                        String where;
                                                        
                                                        long oidHRD = 0;
                                                                                        
                                                        try{
                                                            oidHRD = Long.parseLong(PstSystemProperty.getValueByName("OID_HRD_DEPARTMENT"));
                                                        }catch(Exception E){
                                                            System.out.println("[exception] Sys Prop OID_HRD_DEPARTMENT [not set] "+E.toString());
                                                        }
                                                        
                                                        if(departmentOid==oidHRD){
                                                            where="";
                                                        }else{
                                                            where = " DEPARTMENT_ID = "+departmentOid;
                                                        }
                                                        
                                                        Vector listDept = PstDepartment.list(0, 0, where, "DEPARTMENT");
                                                        
                                                        String selectDept = String.valueOf(gotoDept);
                                                        for (int i = 0; i < listDept.size(); i++) {
                                                            Department dept = (Department) listDept.get(i);
                                                            dept_key.add(dept.getDepartment());
                                                            dept_value.add(String.valueOf(dept.getOID()));
                                                        }*/
                                                    %>
                                                    <%

                                                                                                    Vector dept_value = new Vector(1, 1);
                                                                                                    Vector dept_key = new Vector(1, 1);
                                                                                                    //Vector listDept = new Vector(1, 1);
                                                                                                    DepartmentIDnNameList keyList = new DepartmentIDnNameList();

                                                                                                    if (processDependOnUserDept) {
                                                                                                        if (emplx.getOID() > 0) {
                                                                                                            if (isHRDLogin || isEdpLogin || isGeneralManager) {
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
                                                                                                            //dept_value.add("0");
                                                                                                            //dept_key.add("select ...");
                                                                                                            keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                                                                                                            //listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                                                                        }
                                                                                                    } else {
                                                                                                        //dept_value.add("0");
                                                                                                        //dept_key.add("select ...");
                                                                                                        keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                                                                                                        //listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                                                                    }
                                                                                                    dept_value = keyList.getDepIDs();
                                                                                                    dept_key = keyList.getDepNames();
                                                                                                    String selectValueDepartment =String.valueOf(gotoDept); 

                                                                                                %>
                                                  <%= ControlCombo.draw("DEPARTMENT_ID","formElemen",null, selectValueDepartment, dept_value, dept_key) %> 
                                                </td>
                                              </tr>
                                              <tr>
                                                <td width="1%">&nbsp;</td>
                                                <td width="12%">Recognition Date</td>
                                                <td width="87%"><%=ControlDate.drawDateWithStyle("RECOG_DATE" , (gotoDate == null) ? new Date() : gotoDate, 1,-5, "formElemen", "")%></td>
                                              </tr>
                                              <tr> 
                                                <td width="1%">&nbsp;</td>
                                                <td width="12%"></td>
                                                <td width="87%"> 
                                                  <table cellpadding="0" cellspacing="0" border="0">
                                                    <tr> 
                                                      <td width="8"><a href="javascript:cmdGetList()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Locker"></a></td>
                                                      <td width="4"><img src="<%=approot%>/images/spacer.gif" width="2" height="8"></td>
                                                      <td width="110" class="command" nowrap><a href="javascript:cmdGetList()">Get 
                                                        List </a> </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td colspan="3">
                                                  <hr>
                                                </td>
                                              </tr>
                                            </table>
                                        </td>
                                      </tr>
                                      <tr>
                                        <td>
                                          <% if (listEmp.size() > 0) { 
                                                Department dep = new Department();
                                                if (gotoDept > 0) {
                                                    dep = PstDepartment.fetchExc(gotoDept);
                                                }
                                          %>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <% if (gotoDept > 0) { %>
                                              <tr>
                                                <td>&nbsp;Data for <b><%=dep.getDepartment()%></b> on <b><%=Formater.formatDate(gotoDate, "dd MMMM yyyy")%></b>
                                                </td>
                                              </tr>
                                              <% } %>
                                              <tr>
                                                <td>
                                                    <%=drawList(listEmp, gotoDate)%>
                                                </td>
                                              </tr>
                                              <tr>
                                                <td>
												<%if(privStart){%>
                                                  <table border="0" cellpadding="0" cellspacing="0" width="201">
                                                    <tr> 
                                                      <td width="17"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                      <td width="28"><a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSaveOn.jpg',1)"><img name="Image10" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save"></a></td>
                                                      <td width="15"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                      <td width="141" class="command" nowrap><a href="javascript:cmdSave()">Save 
                                                        Point</a></td>
                                                    </tr> 
                                                  </table>
												  <%}%>
                                                </td>
                                              </tr>
                                            </table>
                                          <% } %>
                                          </td>
                                          </tr>
                                        </table>
                                    </form>
                                <%// } 
                                  // else
                                  // {
                                %>
                                <!--div align="center">You do not have sufficient privilege to access this page.</div-->
                                <%// } %>
                                    <!-- #EndEditable --> </td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td>&nbsp; </td>
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
   <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%>
            <tr>
                            <td valign="bottom">
                               <!-- untuk footer -->
                                <%@include file="../../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
