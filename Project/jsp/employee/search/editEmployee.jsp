
<%
/*
 * Page Name  		:  employee_edit.jsp
 * Created on 		:  [date] [time] AM/PM
 *
 * @author  		: lkarunia
 * @version  		: 01
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
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.form.locker.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.locker.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
%>

<%
	CtrlEmployee ctrlEmployee = new CtrlEmployee(request);
	long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");
	int prevCommand = FRMQueryString.requestInt(request, "prev_command");
	int hidden_command = FRMQueryString.requestInt(request, "hidden_flag_cmd");
	
	int iErrCode = FRMMessage.ERR_NONE;
	String errMsg = "";
	String whereClause = "";
	String orderClause = "";
	int iCommand = FRMQueryString.requestCommand(request);
	int start = FRMQueryString.requestInt(request,"start");
        
        Employee employee = new Employee();

	//out.println("iCommand : "+iCommand);
	ControlLine ctrLine = new ControlLine();
	iErrCode = ctrlEmployee.action(iCommand , oidEmployee);
	errMsg = ctrlEmployee.getMessage();
        FrmEmployee frmEmployee = ctrlEmployee.getForm();
        if(iCommand==Command.SAVE || iCommand==Command.EDIT){
            employee = ctrlEmployee.getEmployee();
            oidEmployee =  employee.getOID();
        }
	//Employee employee = ctrlEmployee.getEmployee();
	//oidEmployee = employee.getOID();
	//--------------------------------------
	// sehubungan dengan picture
	/*   try{
			employee = PstEmployee.fetchExc(oidEmployee);
	   }catch(Exception exc){
			employee = new Employee();
	   }
	  */ 
	//----------------------------------------
	//locker;
       //    System.out.println("EMPLOYEE NAME :::::::::::::: "+employee.getFullName());
	FrmLocker frmLocker = ctrlEmployee.getFormLocker();
	Locker locker = ctrlEmployee.getLocker();



          if((iCommand==Command.SAVE)&&(iErrCode == 0)){
              
            iCommand = Command.EDIT;
          }
        %>

<!-- End of Jsp Block -->
<html>
<!-- #BeginTemplate "/Templates/maintab.dwt" -->
<head>
<!-- #BeginEditable "doctitle" -->
<title>HARISMA - Employee</title>
<script language="JavaScript">
<!--

	function cmdSave(){
            document.frm_employee.command.value="<%=String.valueOf(Command.SAVE)%>";
            document.frm_employee.action="editEmployee.jsp";
            document.frm_employee.submit();    
	}
        
        function cmdNew(){
            document.frm_employee.command.value="<%=String.valueOf(Command.RESET)%>";
            document.frm_employee.action="editEmployee.jsp";
            document.frm_employee.submit();    
	}
         
        function cmdFinish(){
           self.opener.document.searchEmp.<%=FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_NAME]%>.value = 
           document.frm_employee.<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_FULL_NAME]%>.value;
           self.opener.document.searchEmp.<%=FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_EMPNUMBER]%>.value =  
           document.frm_employee.<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_EMPLOYEE_NUM]%>.value;
           self.opener.document.searchEmp.<%=FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_EMP_CATEGORY]%>.value =  
           document.frm_employee.<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_EMP_CATEGORY_ID]%>.value;
           self.opener.document.searchEmp.<%=FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_DEPARTMENT]%>.value =  
           document.frm_employee.<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_DEPARTMENT_ID]%>.value;
           self.opener.document.searchEmp.<%=FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_SECTION]%>.value =  
           document.frm_employee.<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_SECTION_ID]%>.value;
           self.opener.document.searchEmp.<%=FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_POSITION]%>.value =  
           document.frm_employee.<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_POSITION_ID]%>.value;
           self.close();
        }
//-->
</script>
<!-- #EndEditable -->
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" -->
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" -->
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" -->
<SCRIPT language=JavaScript>
<!--
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
</SCRIPT>
<!-- #EndEditable -->
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF">
  
  <tr>
    <td width="88%" valign="top" align="left">
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr>
          <td width="100%">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->
                  Employee &gt; Employee Editor<!-- #EndEditable --> </strong></font>
                </td>
              </tr>
              <tr>
                <td>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td valign="top">
                        <table width="100%" border="0" cellspacing="1" cellpadding="1">
                          <tr>
                            <td valign="top"> <!-- #BeginEditable "content" -->
                              <form name="frm_employee" method="post" action="">
                                <input type="hidden" name="command" value="">
                                <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
                                <input type="hidden" name="employee_oid" value="<%=String.valueOf(oidEmployee)%>">
                                <input type="hidden" name="prev_command" value="<%=String.valueOf(prevCommand)%>">
                                <input type="hidden" name="hidden_flag_cmd" value="<%=String.valueOf(hidden_command)%>">
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
				
                                  <tr>
                                    <td class="tablecolor">
                                      <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                        <tr>
                                          <td valign="top">
                                            <table width="100%" height="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                              <tr>
                                                <td valign="top" width="50%">
                                                  <table width="100%" cellspacing="2" cellpadding="2" >
                                                    <tr>
                                                      <td width="0%">&nbsp;</td>
                                                      <td width="11%" class="txtheading1">&nbsp;</td>
                                                      <td colspan="2" class="comment">
                                                        <div align="left">*) entry
                                                          required</div></td>
                                                      <td width="46%" class="txtheading1">&nbsp;</td>
                                                    </tr>
                                                    <tr>
                                                      <td height="20">&nbsp;</td>
                                                      <td height="20" nowrap>Employee
                                                        Number </td>
                                                      <td height="20"><input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_EMPLOYEE_NUM]%>" value="<%=employee.getEmployeeNum()%>" class="formElemen">
                                                        * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_EMPLOYEE_NUM)%></td>
                                                      <td height="20"><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></td>
                                                      <td height="20"><%
                                                            Vector div_value = new Vector(1,1);
                                                            Vector div_key = new Vector(1,1);
                                                            Vector listDiv = PstDivision.list(0, 0, "", " DIVISION ");
                                                            for (int i = 0; i < listDiv.size(); i++) {
                                                                    Division div = (Division) listDiv.get(i);
                                                                    div_key.add(div.getDivision());
                                                                    div_value.add(String.valueOf(div.getOID()));
                                                            }
                                                        %> <%= ControlCombo.draw(FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_DIVISION_ID],"formElemen",null, ""+employee.getDivisionId(), div_value, div_key) %> * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_DIVISION_ID)%> &nbsp;</td>
                                                    </tr>
                                                    <tr>
                                                      <td width="0%" height="20">&nbsp;</td>
                                                      <td width="11%" height="20" nowrap>
                                                        <div align="left">Full
                                                          Name </div></td>
                                                      <td width="31%" height="20">
                                                        <input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_FULL_NAME]%>" value="<%=employee.getFullName()%>" class="formElemen" size="40">
                                                        * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_FULL_NAME)%>
                                                         </td>
                                                      <td width="12%" height="20">
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div></td>
                                                      <td width="46%" height="20">
                                                        <%
                                                            Vector dept_value = new Vector(1,1);
                                                            Vector dept_key = new Vector(1,1);
                                                            Vector listDept = PstDepartment.list(0, 0, "", " DEPARTMENT ");
                                                            for (int i = 0; i < listDept.size(); i++) {
                                                                    Department dept = (Department) listDept.get(i);
                                                                    dept_key.add(dept.getDepartment());
                                                                    dept_value.add(String.valueOf(dept.getOID()));
                                                            }
                                                        %> <%= ControlCombo.draw(FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_DEPARTMENT_ID],"formElemen",null, ""+employee.getDepartmentId(), dept_value, dept_key) %> * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_DEPARTMENT_ID)%> </td>
                                                    </tr>
                                                    <tr align="left">
                                                      <td width="0%"  valign="top"  >&nbsp;</td>
                                                      <td width="11%"  valign="top"  >
                                                        <div align="left">Address</div></td>
                                                      <td  width="31%"  valign="top">
                                                        <input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDRESS]%>" value="<%=employee.getAddress()%>" class="formElemen" size="45">
                                                        * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_ADDRESS)%> </td>
                                                      <td  width="12%"  valign="top" nowrap>
                                                        <div align="left">Employee
                                                          Category</div></td>
                                                      <td  width="46%"  valign="top">
                                                        <%
                                                            Vector ctg_value = new Vector(1,1);
                                                            Vector ctg_key = new Vector(1,1);
                                                            Vector listCtg = PstEmpCategory.listAll();
                                                            for (int i = 0; i < listCtg.size(); i++) {
                                                                    EmpCategory ctg = (EmpCategory) listCtg.get(i);
                                                                    ctg_key.add(ctg.getEmpCategory());
                                                                    ctg_value.add(String.valueOf(ctg.getOID()));
                                                            }
                                                        %> <%= ControlCombo.draw(FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_EMP_CATEGORY_ID],null, "" + employee.getEmpCategoryId(), ctg_value, ctg_key) %> * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_EMP_CATEGORY_ID)%> </td>
                                                    </tr>
                                                    <tr align="left">
                                                      <td width="0%"  valign="top"  >&nbsp;</td>
                                                      <td width="11%"  valign="top"  >
                                                        <div align="left">Postal
                                                          Code</div></td>
                                                      <td  width="31%"  valign="top"><input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_POSTAL_CODE]%>" value="<%=employee.getPostalCode()==0?"":""+employee.getPostalCode()%>" class="formElemen"></td>
                                                      <td  width="12%"  valign="top">
                                                        <div align="left">Level</div></td>
                                                      <td  width="46%"  valign="top">
                                                        <%
                                                            Vector lvl_value = new Vector(1,1);
                                                            Vector lvl_key = new Vector(1,1);
                                                            Vector listlvl = PstLevel.list(0, 0, "", " LEVEL ");
                                                            for (int i = 0; i < listlvl.size(); i++) {
                                                                    Level lvl = (Level) listlvl.get(i);
                                                                    lvl_key.add(lvl.getLevel());
                                                                    lvl_value.add(String.valueOf(lvl.getOID()));
                                                            }
                                                        %> <%= ControlCombo.draw(FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_LEVEL_ID],"formElemen",null, "" + employee.getLevelId(), lvl_value, lvl_key) %> * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_LEVEL_ID)%> </td>
                                                    </tr>
                                                    <tr align="left">
                                                      <td width="0%"  valign="top"  >&nbsp;</td>
                                                      <td width="11%"  valign="top"  >
                                                        <div align="left">Phone</div></td>
                                                      <td  width="31%"  valign="top"><input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_PHONE]%>" value="<%=employee.getPhone()%>" class="formElemen">

                                                      </td>
                                                      <td  width="12%"  valign="top">
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.POSITION) %></div></td>
                                                      <td  width="46%"  valign="top">
                                                        <%
                                                            Vector pos_value = new Vector(1,1);
                                                            Vector pos_key = new Vector(1,1);
                                                            Vector listPos = PstPosition.list(0, 0, "", " POSITION ");
                                                            for (int i = 0; i < listPos.size(); i++) {
                                                                    Position pos = (Position) listPos.get(i);
                                                                    pos_key.add(pos.getPosition());
                                                                    pos_value.add(String.valueOf(pos.getOID()));
                                                            }
                                                        %> <%= ControlCombo.draw(FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_POSITION_ID],"formElemen",null, "" + employee.getPositionId(), pos_value, pos_key) %> * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_POSITION_ID)%></td>
                                                    </tr>
                                                    <tr align="left">
                                                      <td width="0%"  valign="top"  >&nbsp;</td>
                                                      <td width="11%"  valign="top"  >
                                                        <div align="left">Handphone</div></td>
                                                      <td  width="31%"  valign="top"><input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_HANDPHONE]%>" value="<%=employee.getHandphone()%>" class="formElemen">

                                                      </td>
                                                      <td  width="12%"  valign="top">
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></div></td>
                                                      <td  width="46%"  valign="top">
                                                        <%
                                                            Vector sec_value = new Vector(1,1);
                                                            Vector sec_key = new Vector(1,1);
                                                            Vector listSec = PstSection.list(0, 0, "", "SECTION");
                                                            for (int i = 0; i < listSec.size(); i++) {
                                                                    Section sec = (Section) listSec.get(i);
                                                                    sec_key.add(sec.getSection());
                                                                    sec_value.add(String.valueOf(sec.getOID()));
                                                            }
                                                        %> <%= ControlCombo.draw(FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_SECTION_ID],"formElemen",null, "" + employee.getSectionId(), sec_value, sec_key) %> * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_SECTION_ID)%> </td>
                                                    </tr>
                                                    <tr align="left">
                                                      <td width="0%"  valign="top"  >&nbsp;</td>
                                                      <td width="11%"  valign="top"  >
                                                        <div align="left">Gender</div></td>
                                                      <td  width="31%"  valign="top"><% for(int i=0;i<PstEmployee.sexValue.length;i++){
                                                        String str = "";
                                                        if(employee.getSex()==PstEmployee.sexValue[i]){str = "checked";}
                                                        %> <input type="radio" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_SEX]%>" value="<%=String.valueOf(PstEmployee.sexValue[i])%>" <%=str%> style="border:'none'">
                                                        <%=PstEmployee.sexKey[i]%> <% }%>
                                                        * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_SEX)%>

                                                      </td>
                                                      <td  width="12%"  valign="top">
                                                        <div align="left">Commencing
                                                          Date</div></td>
                                                      <td  width="46%"  valign="top">
                                                        <%=ControlDate.drawDateWithStyle(frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_COMMENCING_DATE], employee.getCommencingDate()==null?new Date():employee.getCommencingDate(), 0, -35,"formElemen")%> * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_COMMENCING_DATE)%> </td>
                                                    </tr>
                                                    <tr align="left">
                                                      <td width="0%"  valign="top"  >&nbsp;</td>
                                                      <td width="11%"  valign="top"  >
                                                        <div align="left">Place
                                                          of Birth</div></td>
                                                      <td  width="31%"  valign="top"><input type="text" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_BIRTH_PLACE]%>" value="<%=employee.getBirthPlace()%>" class="formElemen">
                                                        * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_BIRTH_PLACE)%>
                                                        </td>
                                                      <td  width="12%"  valign="top">
                                                        <div align="left">Locker</div></td>
                                                      <td  width="46%"  valign="top">
                                                        <%

                                                            Vector locLocKey = new Vector(1,1);
                                                            Vector locLocValue = new Vector(1,1);
                                                            Vector listLockerLocation = PstLockerLocation.list(0, 0, "", "");
                                                            locLocKey.add("select...");
                                                            locLocValue.add("0");
                                                            for(int i=0; i<listLockerLocation.size();i++){
                                                                LockerLocation lockerlocation = (LockerLocation) listLockerLocation.get(i);
                                                                locLocKey.add(lockerlocation.getLocation());
                                                                locLocValue.add(""+lockerlocation.getOID());
                                                            }
                                                            //out.println(ControlCombo.draw(frmLocker.fieldNames[FrmLocker.FRM_FIELD_LOCATION_ID],"formElemen",null,locker.getLocationId() != 0 ? ""+locker.getLocationId() : "",locLocValue,locLocKey));
															out.println(ControlCombo.draw(FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_LOCKER_ID],"formElemen",null,employee.getLockerId() != 0 ? ""+employee.getLockerId() : "",locLocValue,locLocKey));
                                                        %>
                                                        <input type="text" name="<%=frmLocker.fieldNames[FrmLocker.FRM_FIELD_LOCKER_NUMBER]%>" value="<%=locker.getLockerNumber()%>" class="formElemen" size="10">
                                                        <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_LOCKER_ID]%>" value="<%=String.valueOf(employee.getLockerId())%>">
                                                      </td>
                                                    </tr>
                                                    <tr align="left">
                                                      <td width="0%"  valign="top"  >&nbsp;</td>
                                                      <td width="11%"  valign="top"  >
                                                        <div align="left">Date
                                                          of Birth</div></td>
                                                      <td  width="31%"  valign="top"><%=ControlDate.drawDateWithStyle(frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_BIRTH_DATE], employee.getBirthDate()==null?new Date():employee.getBirthDate(), 0, -75,"formElemen")%> * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_BIRTH_DATE)%>
                                                        </td>
                                                      <td  width="12%"  valign="top">
                                                        <div align="left">Jamsostek
                                                          Number</div></td>
                                                      <td  width="46%"  valign="top">
                                                        <input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ASTEK_NUM]%>" value="<%=employee.getAstekNum()%>" class="formElemen">
                                                      </td>
                                                    </tr>
                                                    <tr align="left">
                                                      <td width="0%"  valign="top" height="29"  >&nbsp;</td>
                                                      <td width="11%"  valign="top" height="29"  >
                                                        <div align="left">Religion</div></td>
                                                      <td  width="31%"  valign="top" height="29"><% Vector relKey = new Vector(1,1);
                                                             Vector relValue = new Vector(1,1);
                                                                 Vector listReligion = PstReligion.listAll();
                                                             for(int i=0; i<listReligion.size();i++){
                                                                Religion religion = (Religion)listReligion.get(i);
                                                                        relKey.add(religion.getReligion());
                                                                        relValue.add(""+religion.getOID());
                                                                }
                                                                out.println(ControlCombo.draw(frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_RELIGION_ID],"formElemen",null,""+employee.getReligionId(),relValue,relKey));
                                                          %>
                                                        * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_RELIGION_ID)%>
                                                        </td>
                                                      <td  width="12%"  valign="top" height="29">
                                                        <div align="left">Jamsostek
                                                          Date</div></td>
                                                      <td  width="46%"  valign="top" height="29">
                                                        <%=ControlDate.drawDateWithStyle(frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ASTEK_DATE], employee.getAstekDate()==null?new Date():employee.getAstekDate(), 1, -35,"formElemen")%> </td>
                                                    </tr>
                                                    <tr align="left">
                                                      <td width="0%"  valign="top"  >&nbsp;</td>
                                                      <td width="11%"  valign="top"  >
                                                        <div align="left">Marital
                                                          Status - Children</div></td>
                                                      <td  width="31%"  valign="top">
                                                        <% Vector marKey = new Vector(1,1);
                                                         Vector marValue = new Vector(1,1);
                                                             Vector listMarital = PstMarital.list(0, 0, "", " MARITAL_STATUS ");
                                                         for(int i=0; i<listMarital.size();i++){
                                                            Marital marital = (Marital)listMarital.get(i);
                                                                    marKey.add(marital.getMaritalStatus()+ " - "+marital.getNumOfChildren());
                                                                    marValue.add(""+marital.getOID());
                                                             }
                                                             out.println(ControlCombo.draw(frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_MARITAL_ID],"formElemen",null,""+employee.getMaritalId(),marValue,marKey));
                                                      %>
                                                        * <%=frmEmployee.getErrorMsg(FrmEmployee.FRM_FIELD_MARITAL_ID)%>
                                                        </td>
                                                      <td  width="12%"  valign="top">
                                                        <div align="left">Resigned</div></td>
                                                      <td  width="46%"  valign="top">
                                                        <% for(int i=0;i<PstEmployee.resignValue.length;i++){
                                                        String strRes = "";
                                                        if(employee.getResigned()==PstEmployee.resignValue[i]){
                                                                strRes = "checked";
                                                                }
                                                        %> <input type="radio" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_RESIGNED]%>" value="<%=String.valueOf(PstEmployee.resignValue[i])%>" <%=strRes%> style="border:'none'">
                                                        <%=PstEmployee.resignKey[i]%> <%}%> </td>
                                                    </tr>
                                                    <tr align="left">
                                                      <td width="0%"  valign="top"  >&nbsp;</td>
                                                      <td width="11%"  valign="top" nowrap  >
                                                        <div align="left">Blood
                                                          Type </div></td>
                                                      <td  width="31%"  valign="top"><%
                                                             out.println(ControlCombo.draw(frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_BLOOD_TYPE],"formElemen",null,employee.getBloodType(),PstEmployee.getBlood(),PstEmployee.getBlood()));
                                                      %> </td>
                                                      <td  width="12%"  valign="top">
                                                        <div align="left">Resigned
                                                          Date</div></td>
                                                      <td  width="46%"  valign="top">
                                                        <%//=ControlDate.drawDateWithStyle(frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_RESIGNED_DATE], employee.getResignedDate()==null?new Date():employee.getResignedDate(), +5, -10,"formElemen")%> <%=ControlDate.drawDateWithStyle(frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_RESIGNED_DATE], employee.getResignedDate(), +5, -10,"formElemen")%> </td>
                                                    </tr>
                                                    <tr align="left">
                                                      <td width="0%"  valign="top"  >&nbsp;</td>
                                                      <td width="11%"  valign="top"  >
                                                        <div align="left"></div></td>
                                                      <td  width="31%"  valign="top">
                                                        </td>
                                                      <td  width="12%"  valign="top" nowrap>
                                                        <div align="left">Resigned
                                                          Reason </div></td>
                                                      <td  width="46%"  valign="top">
                                                        <% Vector resKey = new Vector(1,1);
                                                         Vector resValue = new Vector(1,1);
                                                             Vector listRes = PstResignedReason.list(0, 0, "", "RESIGNED_REASON");
                                                             resKey.add("select...");
                                                             resValue.add("0");
                                                         for(int i=0; i<listRes.size();i++){
                                                            ResignedReason resignedReason = (ResignedReason) listRes.get(i);
                                                                    resKey.add(resignedReason.getResignedReason());
                                                                    resValue.add(""+resignedReason.getOID());
                                                            }
                                                            out.println(ControlCombo.draw(frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_RESIGNED_REASON_ID],"formElemen",null,""+employee.getResignedReasonId(),resValue,resKey));
                                                      %> </td>
                                                    </tr>
                                                    <tr align="left">
                                                      <td width="0%"  valign="top"  >&nbsp;</td>
                                                      <td width="11%"  valign="top"  >&nbsp;</td>
                                                      <td  width="31%"  valign="top">&nbsp;
                                                      </td>
                                                      <td  width="12%"  valign="top" nowrap>Resigned
                                                        Description </td>
                                                      <td  width="46%"  valign="top">
                                                        <textarea name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_RESIGNED_DESC]%>" class="formElemen" rows="2" cols="20"><%=employee.getResignedDesc()%></textarea>
                                                      </td>
                                                    </tr>
                                                    <tr align="left">
                                                      <td width="0%"  valign="top"  >&nbsp;</td>
                                                      <td colspan="4"  valign="top"  >
                                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                          <tr>
                                                            <td>
                                                                <%
                                                                if(privAdd){
                                                                    %>
                                                                   <input type="submit" name="Submit" value="Save Employee" onClick="javascript:cmdSave()">
                                                                    <%
                                                                }
                                                                %>
                                                                  <input type="submit" name="Submit" value="New Employee" onClick="javascript:cmdNew()">
                                                                  <input type="submit" name="Submit" value="Finish" onClick="javascript:cmdFinish()">
                                                            </td>
                                                          </tr>
                                                          
                                                        </table></td>
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
                                </table>
                              </form>
                              <!-- #EndEditable --> </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr>
                      <td >&nbsp;</td>
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
  
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
