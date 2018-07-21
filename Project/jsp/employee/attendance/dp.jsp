
<%@ page language="java" %>
<!-- package java -->
<%@ page import ="java.util.*,                  
                  com.dimata.util.Command,
                  com.dimata.util.Formater,				  
                  com.dimata.gui.jsp.ControlDate,
                  com.dimata.gui.jsp.ControlCombo,
                  com.dimata.qdep.form.FRMQueryString,
                  com.dimata.gui.jsp.ControlList,				  
		  com.dimata.gui.jsp.ControlLine ,
                  com.dimata.harisma.entity.employee.Employee,
                  com.dimata.harisma.entity.masterdata.LeavePeriod,				  
                  com.dimata.harisma.entity.masterdata.PstLeavePeriod,
		  com.dimata.harisma.entity.masterdata.PstPeriod,
                  com.dimata.harisma.entity.masterdata.PstDepartment,
                  com.dimata.harisma.entity.masterdata.Department,
                  com.dimata.harisma.entity.employee.PstEmployee,
                  com.dimata.harisma.entity.attendance.DpStockManagement,
                  com.dimata.harisma.entity.attendance.PstDpStockManagement,				  
                  com.dimata.harisma.form.attendance.FrmDpStockManagement,
                  com.dimata.harisma.form.attendance.CtrlDpStockManagement,
                  com.dimata.harisma.entity.search.SrcLeaveManagement, 
                  com.dimata.harisma.form.search.FrmSrcLeaveManagement,
                  com.dimata.harisma.session.attendance.SessLeaveManagement"%>    
<!-- package qdep -->
<%@ include file = "../../main/javainit.jsp" %>    
<!-- JSP Block -->
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_LEAVE_MANAGEMENT, AppObjInfo.OBJ_LEAVE_DP_MANAGEMENT); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/    
//boolean privDelete = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
//boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>

<%!
/**
 @Description Untuk menampilkan daftar employee yang memiliki Dp dalam periode terpilih
 @Created by Gadnyana
 @Edited by Edhy & Roy Andika
*/
public String drawListSummary(int offset, Vector listal,SrcLeaveManagement srcLeaveManagement) 
{
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.addHeader("No","5%", "0", "0");
	ctrlist.addHeader("Payroll","20%", "0", "0");
	ctrlist.addHeader("Employee","35%", "0", "0");
	ctrlist.addHeader("Quantity","10%", "0", "0");
        
	ctrlist.addHeader("Taken","10%", "0", "0");
        ctrlist.addHeader("Expired","10%", "0", "0");
	ctrlist.addHeader("Balance","10%", "0", "0");			
	ctrlist.setLinkRow(1);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdChange('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();

	for (int i = 0; i < listal.size(); i++) {
            
            Vector rowx = new Vector(1,1);
            Vector ls = (Vector)listal.get(i);
            DpStockManagement dpStockManagement = (DpStockManagement)ls.get(0);
            Employee emp = (Employee)ls.get(1);

            rowx.add(""+(offset+i+1));
            rowx.add(emp.getEmployeeNum());
            rowx.add(emp.getFullName());
            
            //int expiredQTY = SessLeaveManagement.getTotalDpExpired(dpStockManagement.getEmployeeId(),srcLeaveManagement);
            float expiredQTY = SessLeaveManagement.getDpExpired(dpStockManagement.getEmployeeId(),null);
            float residu = dpStockManagement.getiDpQty() - dpStockManagement.getQtyUsed() - expiredQTY;
            
            rowx.add(""+dpStockManagement.getiDpQty());
            rowx.add(""+dpStockManagement.getQtyUsed());
            rowx.add(""+expiredQTY);
            rowx.add(""+residu);
		
            lstData.add(rowx);
            lstLinkData.add(String.valueOf(dpStockManagement.getEmployeeId()));
        }
	return ctrlist.drawList();
}
%>


<%
    
    boolean isSecretaryLogin = (positionType >= PstPosition.LEVEL_SECRETARY) ? true : false;
    long hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    boolean isHRDLogin = hrdDepartmentOid == departmentOid ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;

    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    long empOid = FRMQueryString.requestLong(request,""+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EMPLOYEE_ID]+"");

    // konstanta untuk navigasi ke database
    int recordToGet = 15;
    int vectSize = 0;

    // process get list DP (summary or detail depend on Empid value)
SrcLeaveManagement srcLeaveManagement = new SrcLeaveManagement();
Vector vectListDP = new Vector(0,0);
if(iCommand != Command.NONE)
{
	// mencari parameter pencarian yang disimpan di session atau form	
	FrmSrcLeaveManagement frmSrcLeaveManagement = new FrmSrcLeaveManagement(request, srcLeaveManagement);
	frmSrcLeaveManagement.requestEntityObject(srcLeaveManagement);	
	
	if(iCommand==Command.FIRST || iCommand==Command.NEXT || iCommand==Command.PREV || iCommand==Command.LAST || iCommand==Command.BACK)
	{
		 try
		 {		  
		 	if(session.getValue(SessLeaveManagement.SESS_MANAGEMENT_LEAVE_DP) != null)
			{
				srcLeaveManagement = (SrcLeaveManagement)session.getValue(SessLeaveManagement.SESS_MANAGEMENT_LEAVE_DP); 			
			}
		 }
		 catch(Exception e)
		 { 
			srcLeaveManagement = new SrcLeaveManagement();
		 }
		 
		 // jika command BACK (kembali dari form detail, maka ubah status command menjadi LIST
		 // supaya menampilkan list record employee - AL yang benar  
		 if(iCommand == Command.BACK)
		 {
			iCommand = Command.LIST;		 	
		 }
	}	
	session.putValue(SessLeaveManagement.SESS_MANAGEMENT_LEAVE_DP, srcLeaveManagement);		
	
	// mencari nilai limitStart
	vectSize = SessLeaveManagement.countSummaryDpStock(srcLeaveManagement);	
	if(iCommand==Command.FIRST || iCommand==Command.NEXT || iCommand==Command.PREV || iCommand==Command.LAST || iCommand==Command.LIST)
	{
		CtrlDpStockManagement ctrlDpStockManagement = new CtrlDpStockManagement(request);	
		start = ctrlDpStockManagement.actionList(iCommand, start, vectSize, recordToGet);
	}	
	
	// list record yang sesuai 	
	//vectListDP = SessLeaveManagement.listSummaryDpStock(srcLeaveManagement, start, recordToGet);  listSummaryDpManagement
        vectListDP = SessLeaveManagement.listSummaryDpManagement(srcLeaveManagement, start, recordToGet);  
}
%>
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - DP Management</title>
<script language="JavaScript">
<!--
function cmdView(){
	document.frdp.command.value="<%=Command.LIST%>";
	document.frdp.start.value="0";
	document.frdp.action="dp.jsp";
	document.frdp.submit();
}

function cmdAdd(){
	document.frdp.command.value="<%=Command.ADD%>";
	document.frdp.start.value="0";
	document.frdp.action="dp_add.jsp";
	document.frdp.submit();
}
function cmdUpdateDep(){
                document.frpresence.command.value="<%=String.valueOf(Command.GOTO)%>";
                 document.frpresence.action="dp_add.jsp";
                 document.frpresence.target = "";
                 document.frpresence.submit();
            }
function cmdChange(oid)
{
	document.frdp.command.value="<%=Command.LIST%>";
	document.frdp.start_summary.value="<%=start%>";
	document.frdp.start.value="0";		
        document.frdp.<%=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EMPLOYEE_ID]%>.value=oid;
	document.frdp.action="dp_detail.jsp";
	document.frdp.submit();
}

function cmdListFirst(){
	document.frdp.command.value="<%=Command.FIRST%>";
	document.frdp.action="dp.jsp";
	document.frdp.submit();
}

function cmdListPrev(){
	document.frdp.command.value="<%=Command.PREV%>";
	document.frdp.action="dp.jsp";
	document.frdp.submit();
}

function cmdListNext(){
	document.frdp.command.value="<%=Command.NEXT%>";
	document.frdp.action="dp.jsp";
	document.frdp.submit();
}

function cmdListLast(){
	document.frdp.command.value="<%=Command.LAST%>";
	document.frdp.action="dp.jsp";
	document.frdp.submit();
}

function cmdUpdateDep(){
    document.frdp.command.value="<%=String.valueOf(Command.GOTO)%>";
    document.frdp.action="dp.jsp";
    document.frdp.submit();
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
//-->
</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> <!-- #EndEditable --> 
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg','/harisma_proj/images/BtnNewOn.jpg')">
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Employee 
                  &gt; Leave Application &gt; DP Management<!-- #EndEditable --> 
                  </strong></font> </td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td  style="background-color:<%=bgColorContent%>; "> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                          <tr> 
                            <td valign="top"> 
                              <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                <tr> 
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="frdp" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="start_summary" value="<%=start%>">
                                      <input type="hidden" name="<%=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EMPLOYEE_ID]%>" value="<%=empOid%>">
                                      <input type="hidden" name="<%=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_LEAVE_PERIODE_ID]%>" value="0">
                                      <table width="60%" border="0" cellspacing="2" cellpadding="2">
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td nowrap width="18%">Name</td>
                                          <td width="3%">:</td>
                                          <td width="78%"> 
                                            <input type="text" name="<%=FrmSrcLeaveManagement.fieldNames[FrmSrcLeaveManagement.FRM_FIELD_FULL_NAME]%>"  value="<%=srcLeaveManagement.getEmpName()%>" class="elemenForm" size="40">
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td nowrap width="18%">Payroll Number</td>
                                          <td width="3%">:</td>
                                          <td width="78%"> 
                                            <input type="text" name="<%=FrmSrcLeaveManagement.fieldNames[FrmSrcLeaveManagement.FRM_FIELD_EMP_NUMBER]%>"  value="<%=srcLeaveManagement.getEmpNum()%>" class="elemenForm">
                                          </td>
                                        </tr>
                                        <tr>
                                          <td width="1%">&nbsp;</td>
                                          <td nowrap width="18%"><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></td>
                                          <td width="3%">:</td>
                                          <td width="78%"> 
                                              <%														
                                            Vector div_value = new Vector(1,1);
                                            Vector div_key = new Vector(1,1);
                                            div_value.add("0");
                                            div_key.add("select ...");                                                              
                                            Vector listDiv = PstDivision.list(0, 0, "", "");                                                          
                                            String selectValueDiv = ""+srcLeaveManagement.getDivisionId();
                                            for (int i = 0; i < listDiv.size(); i++) 
                                            {
                                                    Division div = (Division) listDiv.get(i);
                                                    div_key.add(div.getDivision());
                                                    div_value.add(String.valueOf(div.getOID()));
                                            }															
                                            %>
                                            <%=ControlCombo.draw(FrmSrcLeaveManagement.fieldNames[FrmSrcLeaveManagement.FRM_FIELD_DIVISION],"elementForm", null, selectValueDiv, div_value, div_key,"onChange=\"javascript:cmdUpdateDep()\"") %>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td nowrap width="18%"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                          <td width="3%">:</td>
                                          <td width="78%"> 
                                            <%
                                                Vector dept_value = new Vector(1, 1);
                                                Vector dept_key = new Vector(1, 1);
                                                Vector listDept = new Vector(1, 1);
                                                if (processDependOnUserDept) {
                                                    if (emplx.getOID() > 0) {
                                                        if (isHRDLogin || isEdpLogin || isGeneralManager) {
                                                            dept_value.add("0");
                                                            dept_key.add("select ...");
                                                            if(srcLeaveManagement.getDivisionId() != 0){
                                                                listDept = PstDepartment.list(0, 0, "hr_department.DIVISION_ID="+srcLeaveManagement.getDivisionId(), " DEPARTMENT ");                                                        
                                                            }
                                                          //  listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                        } else {
                                                            String whereClsDep="(DEPARTMENT_ID = " + departmentOid+")";
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
                                                                        if(comp.trim().compareToIgnoreCase(""+departmentOid)==0){
                                                                          grpIdx = countIdx;   // A ha .. found here 
                                                                        }
                                                                    }
                                                                    countIdx++;
                                                                } while ((grpIdx < 0) && (countIdx < maxGrp) && (curr_loop<MAX_LOOP)); // if found then exit

                                                                // compose where clause
                                                                if(grpIdx>=0){
                                                                    String[] grp = (String[]) depGroup.get(grpIdx);
                                                                    for (int g = 0; g < grp.length; g++) {
                                                                        String comp = grp[g];
                                                                        whereClsDep=whereClsDep+ " OR (DEPARTMENT_ID = " + comp+")"; 
                                                                    }         
                                                                   }                                                  
                                                            } catch (Exception exc) {
                                                                System.out.println(" Parsing Join Dept" + exc);
                                                            }

                                                            listDept = PstDepartment.list(0, 0,whereClsDep, "");
                                                            //System.out.println("\t WHERE ::::::::::::; "+whereClsDep);
                                                        }
                                                    } else {
                                                        dept_value.add("0");
                                                        dept_key.add("select ...");
                                                        listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                    }
                                                } else {
                                                    dept_value.add("0");
                                                    dept_key.add("select ...");
                                                    listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                }

                                                for (int i = 0; i < listDept.size(); i++) {
                                                    Department dept = (Department) listDept.get(i);
                                                    dept_key.add(dept.getDepartment());
                                                    dept_value.add(String.valueOf(dept.getOID()));
                                                }
                                                /*Vector dept_value = new Vector(1, 1);
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
*/
                                                                                                    /*for (int i = 0; i < listDept.size(); i++) {
                                                                                                    Department dept = (Department) listDept.get(i);
                                                                                                    dept_key.add(dept.getDepartment());
                                                                                                    dept_value.add(String.valueOf(dept.getOID()));
                                                                                                    } */


                                                                                                    String selectValueDepartment = "" + srcLeaveManagement.getEmpDeptId();//+objSrcLeaveApp.getDepartmentId();

                                            %>
                                            <%=ControlCombo.draw(FrmSrcLeaveManagement.fieldNames[FrmSrcLeaveManagement.FRM_FIELD_DEPARTMENT], "elementForm", null, selectValueDepartment, dept_value, dept_key, " onChange=\"javascript:cmdUpdateDep()\"")%>   
                                          <%//= ControlCombo.draw(FrmSrcLeaveManagement.fieldNames[FrmSrcLeaveManagement.FRM_FIELD_DEPARTMENT],"formElemen",null,String.valueOf(srcLeaveManagement.getEmpDeptId()), dept_value, dept_key, "") %>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td nowrap width="18%">Period</td>
                                          <td width="3%">:</td>
                                          <td width="78%"> 
                                            <input type="checkbox" name="<%=FrmSrcLeaveManagement.fieldNames[FrmSrcLeaveManagement.FRM_FIELD_PERIOD_CHECKED]%>" <%=(srcLeaveManagement.isPeriodChecked() ? "checked" : "")%> value="1">
                                            <i><font color="#FF0000">Select all 
                                            period</font></i></td>
                                        </tr>
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td nowrap width="18%">&nbsp;</td>
                                          <td width="3%">&nbsp;</td>
                                          <td width="78%">&nbsp;<%=ControlDate.drawDateMY(FrmSrcLeaveManagement.fieldNames[FrmSrcLeaveManagement.FRM_FIELD_PERIOD], srcLeaveManagement.getLeavePeriod()==null ? new Date() : srcLeaveManagement.getLeavePeriod(), "MMM yyyy", "formElemen", 0, installInterval)%></td>
                                        </tr>
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td width="18%" nowrap> 
                                            <div align="left"></div>
                                          </td>
                                          <td width="3%">&nbsp;</td>
                                          <td width="78%"> 
                                            <table border="0" cellspacing="0" cellpadding="0" width="200
											">
                                              <tr> 
                                                <td width="12"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image101','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image101" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Get DP"></a></td>
                                                <td width="5"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                <td width="63" class="command" nowrap><a href="javascript:cmdView()">Get 
                                                  DP</a></td>
												<%if(privAdd){%>
                                                <td width="12"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnNewOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New DP"></a></td>
                                                <td width="5"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                <td width="103" class="command" nowrap><a href="javascript:cmdAdd()">Add 
                                                  New DP</a></td>
												  <%}%>												  
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                      </table>  
                                      <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                        <%
										//--- start untuk menampilkan garis pembatas antara 'search' dengan 'list result'
										if(vectListDP.size()>0)
										{
										%>
                                        <tr> 
                                          <td> 
                                            <hr>
                                          </td>
                                        </tr>
                                        <%
										}
										//--- end untuk menampilkan garis pembatas antara 'search' dengan 'list result'
										%>
                                        <%
										//--- start untuk menampilkan list data result, baik summary maupun detail per employee 
										String drawList = "";
										if(vectListDP != null && vectListDP.size()>0)
										{
											drawList = drawListSummary(start, vectListDP, srcLeaveManagement);		
										}
																				
										String strDpData = drawList.trim();
										if(strDpData!=null && strDpData.length()>0)
										{
										%>
                                        <tr> 
                                          <td><%=strDpData%></td>
                                        </tr>
                                        <%
										}
										else if(iCommand == Command.LIST)
										{
										%>
                                        <tr> 
                                          <td><%="<div class=\"msginfo\">&nbsp;&nbsp;No day off payment data found ...</div>"%></td>
                                        </tr>
                                        <%	
										}
										//--- end untuk menampilkan list data result, baik summary maupun detail per employee 
										%>
                                        <tr> 
                                          <td> 
                                            <% 
										    ControlLine ctrLine = new ControlLine();												
											ctrLine.setLocationImg(approot+"/images");
											ctrLine.setLanguage(SESS_LANGUAGE);
											ctrLine.initDefault();		
											int listCommand = iCommand;											
											if(iCommand==Command.EDIT && empOid!=0)
											{
												listCommand = Command.LIST;
											}
											out.println(ctrLine.drawImageListLimit(listCommand, vectSize, start, recordToGet));
											%>
                                          </td>
                                        </tr>
                                      </table>
                                    </form>
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
