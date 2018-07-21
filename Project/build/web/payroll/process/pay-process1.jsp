
<%@page import="com.dimata.harisma.session.payroll.PayProcessManager"%>
<%@ page language="java" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>

<%@ page import = "java.util.Date" %>

<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>


<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_PROCESS, AppObjInfo.OBJ_PAYROLL_PROCESS_PROCESS);%>
<%@ include file = "../../main/checkuser.jsp" %>

<%
            CtrlPaySlipComp ctrlPaySlipComp = new CtrlPaySlipComp(request);
            int prevCommand = FRMQueryString.requestInt(request, "prev_command");
            int iCommand = FRMQueryString.requestCommand(request);
            int start = FRMQueryString.requestInt(request, "start");
            String levelCode = FRMQueryString.requestString(request, "level");
            levelCode = levelCode!=null && levelCode.length()>0 ? levelCode: "ALL";
            int aksiCommand = FRMQueryString.requestInt(request, "aksiCommand");
            long periodId = FRMQueryString.requestLong(request, "periodId");
            long oidDepartment = FRMQueryString.requestLong(request,"department");
            
            if (periodId != 0){
                try {
                    PayPeriod payPeriod123 = PstPayPeriod.fetchExc(periodId);
                } catch (Exception e) {
                    
                }
                
            }
            
            // 2014-11-27 update by Hendra McHen
            String[] SelectedValues = FRMQueryString.requestStringValues(request, "POSITION_LEVEL");
            
            boolean isSecretaryLogin = (positionType >= PstPosition.LEVEL_SECRETARY) ? true : false;
            long hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
            boolean isHRDLogin = hrdDepartmentOid == departmentOid ? true : false;
            long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
            boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
            boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
            
%>
<%
            /*mengambil nama period saat ini
            Updated By Yunny*/
            /*Date now = new Date();
            long periodId = PstPeriod.getPeriodIdBySelectedDate(now);*/
//out.println("PeriodId:::::"+periodId);
%>


<%
            System.out.println("iCommand::::" + iCommand);
            int iErrCode = FRMMessage.ERR_NONE;
            String msgString = "";
            String msgStr = "";
            int recordToGet = 1000;
            int vectSize = 0;
            String orderClause = "";
            String whereClause = "";
            ControlLine ctrLine = new ControlLine();

// action on object agama defend on command entered
            FrmPaySlipComp frmPaySlipComp = ctrlPaySlipComp.getForm();
            PaySlipComp paySlipComp = ctrlPaySlipComp.getPaySlipComp();
            msgString = ctrlPaySlipComp.getMessage();

%>
<%

            String[] levelId = null;
            levelId = new String[PstPosition.strPositionLevelInt.length];
            Vector levelSel= new Vector(); 
            int max1 = 0;
            
            for(int j = 0 ; j < PstPosition.strPositionLevelInt.length ; j++){                
                String name = "LEVL_"+PstPosition.strPositionLevelInt[j];
                String val = FRMQueryString.requestString(request,name);
                levelId[j] = val;
                if(val!=null && val.equals("1")){ 
                   levelSel.add(""+PstPosition.strPositionLevelInt[j]); 
                }
                max1++;
            }
            // 2014-11-27 update by Hendra McHen
            Vector checkValues = new Vector(1,1);
            if(SelectedValues != null && SelectedValues.length > 0){
                for (int i = 0; i < SelectedValues.length; ++i){
                    checkValues.add(SelectedValues[i]);
                }
            }

            Vector listEmpLevel = new Vector(1, 1);
            if (iCommand == Command.LIST || iCommand == Command.SAVE || iCommand == Command.ADD) {
                if (periodId != 0){
                    listEmpLevel = PstPayEmpLevel.listEmpLevelwithDateResign(levelCode, checkValues, oidDepartment, null , PstEmployee.NO_RESIGN, payPeriod123.getStartDate(), payPeriod123.getEndDate() );
                } else {
                    listEmpLevel = PstPayEmpLevel.listEmpLevel(levelCode, checkValues, oidDepartment );
                }
            }

%>		

<!-- JSP Block -->
<%!
    public String drawList(JspWriter outJsp,  Vector objectClass) {
        String result = "";
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("80%");
       /* ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");*/
                //update by satrya 2013-01-2013
        		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
                ctrlist.setCellStyles("listgensellstyles");
                ctrlist.setRowSelectedStyles("rowselectedstyles");
                
        ctrlist.addHeader("No", "5%","1","1");
        ctrlist.addHeader("Payroll Nr.", "10%", "1","1");
        ctrlist.addHeader("Full Name", "20%","1","1");
        ctrlist.addHeader("Commencing Date", "10%", "1","1");
        ctrlist.addHeader("Salary Level", "20%", "1","1");
        ctrlist.addHeader("Start Date", "20%", "1","1");

        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        Vector rowx = new Vector(1, 1);
        int index = -1;

        if (objectClass != null && objectClass.size() > 0) {
            ctrlist.drawListHeader(outJsp);
            for (int i = 0; i < objectClass.size(); i++) {
                Vector temp = (Vector) objectClass.get(i);
                Employee employee = (Employee) temp.get(0);
                PayEmpLevel payEmpLevel = (PayEmpLevel) temp.get(1);
                SalaryLevel salary = (SalaryLevel) temp.get(2);

                rowx = new Vector();
                rowx.add(String.valueOf(1 + i));
                rowx.add(String.valueOf(employee.getEmployeeNum()));
                rowx.add(String.valueOf(employee.getFullName()));
                rowx.add("" + Formater.formatTimeLocale(employee.getCommencingDate(), "dd-MMM-yyyy"));
                rowx.add(String.valueOf(salary.getLevelName()));
                rowx.add("" + Formater.formatTimeLocale(payEmpLevel.getStartDate(), "dd-MMM-yyyy"));
                ctrlist.drawListRow(outJsp,0,rowx, i);
                //lstData.add(rowx);
            }
            result = "";
            ctrlist.drawListEndTable(outJsp); //ctrlist.draw();
        } else {
            result = "<i>Belum ada data dalam sistem ...</i>";
        }
        return result;
    }
%>


<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
    <!-- #BeginEditable "doctitle" --> 
    <title>HARISMA - </title>
    <!-- #EndEditable --> 
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <!-- #BeginEditable "styles" --> 
    <link rel="stylesheet" href="../../styles/main.css" type="text/css">
    <!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
    <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
    <!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
    <SCRIPT language=JavaScript>
        
        function fnTrapKD(){
            if (event.keyCode == 13) {
                document.all.aSearch.focus();
                cmdSearch();
            }
        }
        function SetAllCheckBoxes(FormName, FieldName, CheckValue)
{
	if(!document.forms[FormName])
		return;
	var objCheckBoxes = document.forms[FormName].elements[FieldName];
	if(!objCheckBoxes)
		return;
	var countCheckBoxes = objCheckBoxes.length;
	if(!countCheckBoxes)
		objCheckBoxes.checked = CheckValue;
	else
		// set the check value for all check boxes
		for(var i = 0; i < countCheckBoxes; i++)
			objCheckBoxes[i].checked = CheckValue;
}
        // 2014-11-27 update by Hendra McHen
        function SetAllCheckBoxesLevel(n,c){
            for(var i=0; i < n; i++)//
            document.getElementById("cek"+i).checked=c;
        }    
    

        function cmdSearch(){
            document.frm_prepare_data.command.value="<%=Command.LIST%>";
            document.frm_prepare_data.action="pay-process.jsp";
            document.frm_prepare_data.submit();
        }
        
        function cmdStartCalculate(){
            document.frm_prepare_data.command.value="<%=Command.START %>";
            document.frm_prepare_data.action="run-pay-process.jsp";
            document.frm_prepare_data.submit();
        }

        function cmdStopCalculate(){
            document.frm_prepare_data.command.value="<%=Command.STOP %>";
            document.frm_prepare_data.action="run-pay-process.jsp";
            document.frm_prepare_data.submit();
        }

        
        function cmdLevel(employeeId,salaryLevel,paySlipId){
            document.frm_prepare_data.action="pay-input-detail.jsp?employeeId=" + employeeId+ "&salaryLevel=" + salaryLevel+"&paySlipId=" + paySlipId;
            document.frm_prepare_data.command.value="<%=Command.LIST%>";
            document.frm_prepare_data.submit();
        }
        
        function cmdSave(){
            document.frm_prepare_data.command.value="<%=Command.SAVE%>";
            document.frm_prepare_data.aksiCommand.value="0";
            document.frm_prepare_data.statusSave.value="0";
            document.frm_prepare_data.action="pay-pre-data.jsp";
            document.frm_prepare_data.submit();
        }
        function cmdSaveAll(){
            document.frm_prepare_data.command.value="<%=Command.SAVE%>";
            document.frm_prepare_data.aksiCommand.value="0";
            document.frm_prepare_data.statusSave.value="1";
            document.frm_prepare_data.action="pay-pre-data.jsp";
            document.frm_prepare_data.submit();
        }
        
        function cmdBack(){
            document.frm_prepare_data.command.value="<%=Command.LIST%>";
            document.frm_prepare_data.action="pay-pre-data.jsp";
            document.frm_prepare_data.submit();
        }
        
        function openLevel(){
            var strUrl ="sel_salary-level.jsp?frmname=frm_prepare_data";
            var levelWindow = window.open(strUrl);
            levelWindow.focus();
        }

        function clearLevelCode(){
            document.frm_prepare_data.level.value="ALL";
        }
        
        
        function hideObjectForEmployee(){
            
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
            
        }
    </SCRIPT>
    <!-- #EndEditable --> 
</head>
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
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
<tr > 
<td width="100%" valign="top" align="left"> 
    <table width="100%" border="0" cellspacing="3" cellpadding="2">
    <tr> 
        <td width="100%"> 
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> 

                        <!-- #BeginEditable "contenttitle" -->Payroll Prosess &gt; Pay Prosess <!-- #EndEditable --> </strong></font> </td>
            </tr>
       
            <tr>
                <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                <td  style="background-color:<%=bgColorContent%>; "> 
                <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                <tr> 
                <td valign="top"> 
                <table style="border:1px solid <%=garisContent%>" width="100%" cellspacing="1" cellpadding="1"  >
                <tr> 
                <td valign="top"> <!-- #BeginEditable "content" --> 
                <form name="frm_prepare_data" method="post" action="">
                    <input type="hidden" name="command" value="">
                    <input type="hidden" name="aksiCommand" value="<%=aksiCommand%>">
                    <input type="hidden" name="start" value="<%=start%>">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr> 
                            <td height="13" width="1%">&nbsp;</td>
                            <td height="13" width="33%" nowrap><b class="listtitle"><font size="3" color="#000000">Period 
                                        Period &nbsp; &nbsp;
                                        
                                        <%
            Vector perValue = new Vector(1, 1);
            Vector perKey = new Vector(1, 1);
            // salkey.add(" ALL DEPARTMET");
            //deptValue.add("0");
            Vector listPeriod = PstPayPeriod.list(0, 0, "", "START_DATE DESC");
            // Vector listPeriod = PstPeriod.list(0, 0, "", "START_DATE DESC");
            for (int r = 0; r < listPeriod.size(); r++) {
                PayPeriod payPeriod = (PayPeriod) listPeriod.get(r);
                // Period period = (Period) listPeriod.get(r);
                perValue.add("" + payPeriod.getOID());
                perKey.add(payPeriod.getPeriod());
            }
                                        %> <%=ControlCombo.draw("periodId", null, "" + periodId, perValue, perKey, "")%>
                            </font></b></td>
                            <td height="13" width="30%">&nbsp;</td>
                            <td height="13" width="28%">&nbsp;</td>
                            <td height="13" width="8%">&nbsp;</td>
                        </tr> 
                        <tr> 
                            <td height="13" width="1%">&nbsp;</td>
                            <td height="13" width="33%" nowrap><b class="listtitle"><font size="3" color="#000000">                        
                        Department 
                                            
                                            <% 
                                                          /*  Vector dept_value = new Vector(1,1);
                                                            Vector dept_key = new Vector(1,1);        
															dept_value.add("");
                                                            dept_key.add("select ...");                                                          
                                                            Vector listDept = PstDepartment.list(0, 0, "", " DEPARTMENT ");                                                        
                                                            for (int i = 0; i < listDept.size(); i++) {
                                                                    Department dept = (Department) listDept.get(i);
                                                                    dept_key.add(String.valueOf(dept.getDepartment()));
                                                                    dept_value.add(""+dept.getOID());
                                                            }*/
                                            
                                             Vector dept_value = new Vector(1, 1);
            Vector dept_key = new Vector(1, 1);
            //Vector listDept = new Vector(1, 1);
            DepartmentIDnNameList keyList= new DepartmentIDnNameList ();            
            if (processDependOnUserDept) {
                if (emplx.getOID() > 0) {
                    if (isHRDLogin || isEdpLogin || isGeneralManager){
                        
                        dept_value.add("0");
                        dept_key.add("select ...");                        
                        //listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                        keyList= PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                        
                    } else {
                        //String whereClsDep="(DEPARTMENT_ID = " + departmentOid+")";
                        String whereClsDep="(DEPARTMENT_ID = " + departmentOid+" OR JOIN_TO_DEPARTMENT_ID='"+departmentOid+"')";
                        
                        Vector SectionList = new Vector();
                        try {
                            String joinDept = PstSystemProperty.getValueByName("JOIN_DEPARMENT");
                            Vector depGroup = com.dimata.util.StringParser.parseGroup(joinDept);
                            
                            String joinDeptSection = PstSystemProperty.getValueByName("JOIN_DEPARTMENT_SECTION");
                            Vector depSecGroup = com.dimata.util.StringParser.parseGroup(joinDeptSection);

                            int grpIdx = -1;
                            int maxGrp = depGroup == null ? 0 : depGroup.size();
                            
                            int grpSecIdx = -1;
                            int maxGrpSec = depSecGroup == null ? 0 : depSecGroup.size();
                                                        
                            int countIdx = 0;
                            int MAX_LOOP = 10;
                            int curr_loop = 0;
                            
                            int countIdxSec = 0;
                            int MAX_LOOPSec = 10;
                            int curr_loopSec = 0;
                            
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
                            
                            Vector idxSecGroup = new Vector();
                            
                            for(int x = 0; x < maxGrpSec ; x++){
                                
                                String[] grp = (String[]) depSecGroup.get(x);
                                for(int j = 0 ; j < 1 ; j++){
                                    
                                    String comp = grp[j];
                                    if(comp.trim().compareToIgnoreCase(""+departmentOid)==0){
                                        Counter counter = new Counter();
                                        counter.setCounter(x);
                                        idxSecGroup.add(counter);
                                    }                                    
                                }
                            }
                            
                            for(int s = 0 ; s < idxSecGroup.size() ; s++ ){
                                
                                Counter counter = (Counter)idxSecGroup.get(s);
                                
                                String[] grp = (String[]) depSecGroup.get(counter.getCounter());
                                
                                Section sec = new Section();
                                sec.setDepartmentId(Long.parseLong(grp[0]));
                                sec.setOID(Long.parseLong(grp[2]));
                                SectionList.add(sec);
                                
                            }
                            
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

                        //dept_value.add("0");
                        //dept_key.add("select ...");
                        //listDept = PstDepartment.list(0, 0,whereClsDep, "");
                        keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, whereClsDep, true);
                        
                        for(int idx = 0 ; idx < SectionList.size() ; idx++){    
                            
                            Section sect = (Section)SectionList.get(idx);
                            
                            long sectionOid = 0;
                            
                            /** for(int z = 0 ; z < listDept.size() ; z++){
                            
                                Department dep = new Department();
                                
                                dep = (Department)listDept.get(z);
                                
                                if(sect.getDepartmentId() == dep.getOID()){
                                    
                                    sectionOid = sect.getOID();
                                    
                                }                                
                            }
                            
                            if(sectionOid != 0){
                                
                                Section lstSection = new Section();
                                Department lstDepartment = new Department();
                                
                                try{
                                    lstSection = PstSection.fetchExc(sectionOid);
                                }catch(Exception e){
                                    System.out.println("Exception "+e.toString());
                                }
                                
                                try{
                                    lstDepartment = PstDepartment.fetchExc(lstSection.getDepartmentId());
                                }catch(Exception e){
                                    System.out.println("Exception "+e.toString());
                                }
                                
                                listDept.add(lstDepartment);
                                
                            } **/
                        }                        
                    }
                } else {
                    dept_value.add("0");
                    dept_key.add("select ...");
                    //listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                    keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                }
            } else {
                dept_value.add("0");
                dept_key.add("select ...");
                //listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
            }
            /*
            for (int i = 0; i < listDept.size(); i++) {
                Department dept = (Department) listDept.get(i);
                dept_key.add(dept.getDepartment());
                dept_value.add(String.valueOf(dept.getOID()));
            }*/
            dept_value = keyList.getDepIDs();
            dept_key = keyList.getDepNames();                                      
            //String sSelectedDepartment = iCommand==Command.GOTO ? ""+lDepartmentOid : srcEmpSchedule.getDepartment();
                                            
														out.println(ControlCombo.draw("department",null,""+oidDepartment,dept_value,dept_key)); 
												%>
                        
                        </font></b></td>
                            <td height="13" width="30%">&nbsp;</td>
                            <td height="13" width="28%">&nbsp;</td>
                            <td height="13" width="8%">&nbsp;</td>
                        </tr> 
						<tr>
						  <td>&nbsp;</td>
						  <td height="30" width="80%"> Position :
                                                      <%
                                                         /*
                                                          * Date:  2014-11-27 
                                                          * update by Hendra McHen
                                                          */
 
                                                          AppUser appUser;
                                                          String positionLevel = "";
                                                          String banyakCheck = "";
                                                         try {
                                                             appUser = PstAppUser.fetch(appUserIdSess);
                                                         } catch (Exception e) {
                                                             appUser = null;
                                                         }         
                                                         
                                                         if (appUser != null){
                                                            positionLevel = appUser.getPositionLevelId();
                                                            String[] checkeds = new String[20];
                                                            int i = 0;
                                                            if(positionLevel!=null){
                                                                for (String retval : positionLevel.split("-")) {
                                                                    checkeds[i] = retval;
                                                                    i++;
                                                                }
                                                            }else{
                                                                out.println("No position is set for the user");
                                                            }
                                                            
                                                            
                                                            int checkValue;
                                                            
                                                            int iLevel = 0;
                                                            for (iLevel = 0; iLevel < i; iLevel++) {
                                                                checkValue = Integer.valueOf(checkeds[iLevel]);     
                                                                
                                                            %>
                                                                <input name="POSITION_LEVEL" id="cek<%=iLevel%>" type="checkbox" checked="" value="<%= PstPosition.strPositionLevelValue[checkValue]%>" > <%= PstPosition.strPositionLevelNames[checkValue]%> &nbsp;&nbsp;
                                                            <%           
                                                                
                                                            }
                                                            banyakCheck = Integer.toString(iLevel);//
                                                         } else {
                                                            String nameInp = "";
                                                            Vector x = new Vector(1, 1);
                                                            for (int iLevel = 0; iLevel < PstPosition.strPositionLevelInt.length; iLevel++) {
                                                                nameInp = "LEVL_" + PstPosition.strPositionLevelInt[iLevel];

                                                                if (levelId[iLevel].equals("1")) {

                                                                  %>
                                                                  <input name=<%=nameInp%> type="checkbox" checked value=1 > <%= PstPosition.strPositionLevelNames[iLevel]%> &nbsp;&nbsp;
                                                                  <%
                                                                  } else {
                                                                  %>
                                                                  <input name=<%=nameInp%> type="checkbox" value=1 > <%= PstPosition.strPositionLevelNames[iLevel]%> &nbsp;&nbsp;
                                                                  <%
                                                                  }
                                                               
                                                            }
                                                         }
                                                      %>
                         
                        <!--2014-11-27 update by Hendra McHen -->
			<a href="Javascript:SetAllCheckBoxesLevel('<%=banyakCheck%>','checked')">Select All</a> | <a href="Javascript:SetAllCheckBoxesLevel('<%=banyakCheck%>','')">Deselect All</a></td>
						  <td>&nbsp;</td>
						</tr>
<tr> 
                            <td height="30" width="1%">&nbsp;</td>
                            
                            <td height="30" width="80%" nowrap >Salary Level  
                                : <input name="level" type="text" readonly="true" value="<%=levelCode%>"> <a href="javascript:openLevel();">Select</a>
                                              | <a href="javascript:clearLevelCode()">Clear</a>
                                <% /*
            Vector listSalLevel = PstSalaryLevel.list(0, 0, "", "LEVEL_NAME");
            Vector salValue = new Vector(1, 1);
            Vector salKey = new Vector(1, 1);
            salValue.add("ALL");
            salKey.add("All Level");
            
            for (int d = 0; d < listSalLevel.size(); d++) {
                SalaryLevel salaryLevel = (SalaryLevel) listSalLevel.get(d);
                salValue.add("" + salaryLevel.getLevelCode());
                salKey.add(salaryLevel.getLevelName());
            }
            out.println(ControlCombo.draw("salaryLevel", null, "" + levelCode, salValue, salKey));
                                */ %>
                            </td>
                            <td height="30" width="8%">&nbsp;</td>
                        </tr>						
						
                        <tr> 
                            <td>&nbsp;</td>
                            <td width="80%"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a>
                                <img src="<%=approot%>/images/spacer.gif" width="6" height="1">
                                <a href="javascript:cmdSearch()">Search 
                        for Employee</a></td></tr>
                        <tr> 
                            <td height="13" width="1%">&nbsp;</td>
                            <td height="13" colspan="4">&nbsp;</td>
                        </tr>
                        <tr> 
                            <%
            if (listEmpLevel != null && listEmpLevel.size() > 0) {
                            %>
                            <td colspan="6" height="8"><%=drawList(out, listEmpLevel)%></td>
                            <%
                            } else {
                            %>
                        </tr>
                        <tr> 
                        <td>&nbsp;  </td>
                        <td height="8" width="33%" class="comment"><span class="comment"><br>
                            &nbsp;No Employee available</span> 
                        </td>
                        </tr>
                        <%
            }
                        %>
                        <tr> 
                            <td>&nbsp;</td>
                            <td colspan="4"> 
                                <div align="left">
                                    <%
            if (listEmpLevel != null && listEmpLevel.size() > 0 && iCommand == Command.LIST) {
                                    %>
                                    <b><%//=strStatusSvrmgrTMA%></b>.<br>                                    
                                    <input type="button" name="calculate" value="<%=(PayProcessManager.isRunning()? "Stop Process" : "Run Process") %>" onClick="javascript:cmdStartCalculate('<%//=strButtonStatusTMA%>');"> 
                                </div>
                            </td>                            
                            <%
                               } 
                            %> 
                    </tr> 
                    <td class="listtitle" width="1%">&nbsp;</td>
                    <td class="listtitle" colspan="4">&nbsp;</td>
                </tr>
                <tr> 
                    <td width="1%">
                    </td>
                    <td colspan="4">&nbsp; </td>
                </tr>
                <tr> 
                    <td width="1%">&nbsp;</td>
                    <td colspan="4">&nbsp;</td>
                </tr>
                <tr> 
                    <td width="1%">&nbsp;</td>
                    <td colspan="4">&nbsp;</td>
                </tr>
                <tr> 
                    <td width="1%">&nbsp;</td>
                    <td colspan="4">&nbsp;</td>
                </tr>
                <tr> 
                    <td width="1%">&nbsp;</td>
                    <td colspan="4">&nbsp;</td>
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
        <%if(false){%>
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
        <%}%>
            <%if(PayProcessManager.isRunning()){%>
            <script language="JavaScript">

//enter refresh time in "minutes:seconds" Minutes should range from 0 to inifinity. Seconds should range from 0 to 59
var limit="0:08"
if (document.images){
var parselimit=limit.split(":")
parselimit=parselimit[0]*60+parselimit[1]*1
}
function beginrefresh(){
if (!document.images)
return

if (parselimit==1)
window.location = window.location.href //agar tidak memunculkan pesan confirmasi
else{
parselimit-=1
//curmin=Math.floor(parselimit/60)
//cursec=parselimit%60
//if (curmin!=0)
//curtime=curmin+" minutes and "+cursec+" seconds left until page refresh!"

setTimeout("beginrefresh()",100)
}
}

window.onload=beginrefresh
//-->
</script>
            <%}%>
</table>
</body>
<!-- #BeginEditable "script" --> 
<%if(true){%>
<script language="JavaScript">
    var oBody = document.body;
    var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
    </script>
<%}%>
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
