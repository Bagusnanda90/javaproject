<%@ page language="java" %>
<!-- package java -->
<%@ page import = "java.util.*"%>
<!-- package qdep -->
<%@ page import = "com.dimata.util.*"%>
<%@ page import = "com.dimata.qdep.form.*"%>
<%@ page import = "com.dimata.gui.jsp.*"%>
<!-- package prochain -->
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.clinic.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.session.clinic.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_CLINIC, AppObjInfo.G2_MEDICAL, AppObjInfo.OBJ_MEDICAL_RECORD); %>
<%//@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));

%>
<%
long hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
boolean isHRDLogin = hrdDepartmentOid == departmentOid ? true : false;

if(isHRDLogin){

int iCommand = FRMQueryString.requestCommand(request);
long deptId = FRMQueryString.requestLong(request, FrmSrcMedicalRecord.fieldNames[FrmSrcMedicalRecord.FRM_FIELD_DEPARTMENT_ID]);
long empId = FRMQueryString.requestLong(request,"emp_id");

Vector vectDiseaseType = PstDiseaseType.listAll();
Vector vectMedicalType = PstMedicalType.listAll();

SrcMedicalRecord srcMedicalRecord = new SrcMedicalRecord();
if(iCommand==Command.NONE){
	if(session.getValue("SESS_MEDICAL_RECORD")!=null){
		srcMedicalRecord = (SrcMedicalRecord)session.getValue("SESS_MEDICAL_RECORD");
                deptId = srcMedicalRecord.getDepartmentId();
	}
}

if(iCommand==Command.GOTO){
	FrmSrcMedicalRecord frmSrcMedicalRecord = new FrmSrcMedicalRecord(request, srcMedicalRecord);
	frmSrcMedicalRecord.requestEntityObject(srcMedicalRecord);
	srcMedicalRecord = frmSrcMedicalRecord.getEntityObject();
}

%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Search Medical Record</title>
<script language="JavaScript">
function cmdAdd(){
		document.frsearch.command.value="<%=Command.ADD%>";
		document.frsearch.action="medicalrecord.jsp";
		document.frsearch.submit();
}

function cmdSearch(){
		document.frsearch.command.value="<%=Command.LIST%>";
		document.frsearch.action="medicalrecord.jsp";
		document.frsearch.submit();
}

function fnTrapKD(){
   if (event.keyCode == 13) {
		document.all.aSearch.focus();
		cmdSearch();
   }
}
function deptChange() {
	document.frsearch.command.value = "<%=Command.GOTO%>";
	document.frsearch.dept_id.value = document.frsearch.<%=FrmSrcMedicalRecord.fieldNames[FrmSrcMedicalRecord.FRM_FIELD_DEPARTMENT_ID]%>.value;
	document.frsearch.emp_id.value = "0";
	document.frsearch.action = "scrmedicalrecord.jsp";
	document.frsearch.submit();
}
</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<!-- #EndEditable --> 
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
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
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr> 
          <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Clinic 
                  &gt; Medical Record &gt; Search<!-- #EndEditable --> </strong></font> 
                </td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td class="tablecolor"> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr> 
                            <td valign="top"> 
                              <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                <tr> 
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                      <tr> 
                                        <td valign="top" align="left"> 
                                          <form name="frsearch" method="post" action="prd_list.jsp">
                                            <input type="hidden" name="command" value="<%=iCommand%>">
                                            <input type="hidden" name="dept_id" value="<%=deptId%>">
                                            <input type="hidden" name="emp_id" value="<%=empId%>">
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td> 
                                                  <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                    <tr> 
                                                      <td colspan="3" height="18"> 
                                                        <div align="left"><b>Please 
                                                          enter the search parameter 
                                                          for Medical Record</b></div>
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="10%" align="right"> 
                                                        <div align="left"></div>
                                                      </td>
                                                      <td width="2%">&nbsp;</td>
                                                      <td width="88%">&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="10%"> 
                                                        <div align="left">Record 
                                                          Date</div>
                                                      </td>
                                                      <td width="2%">:</td>
                                                      <td width="88%"> <%=ControlDate.drawDateWithStyle(FrmSrcMedicalRecord.fieldNames[FrmSrcMedicalRecord.FRM_FIELD_START_DATE], srcMedicalRecord.getStartDate(), 0,-5, "formElemen", "")%> to 
													  <%=ControlDate.drawDateWithStyle(FrmSrcMedicalRecord.fieldNames[FrmSrcMedicalRecord.FRM_FIELD_END_DATE], srcMedicalRecord.getEndDate(), 0,-5, "formElemen", "")%></td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="10%" align="right"> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>
                                                      </td>
                                                      <td width="2%">:</td>
                                                      <td width="88%"> 
                                                        <%
                                                        Vector dept_value = new Vector(1,1);
                                                        Vector dept_key = new Vector(1,1);
                                                        Vector listDept = PstDepartment.list(0,0,""," DEPARTMENT ");
                                                        dept_key.add("select...");
                                                        dept_value.add("0");
                                                        String selectDept = ""+srcMedicalRecord.getDepartmentId();
                                                        for (int i = 0; i < listDept.size(); i++) {
                                                            Department dept = (Department) listDept.get(i);
                                                            dept_key.add(dept.getDepartment());
                                                            dept_value.add(String.valueOf(dept.getOID()));
                                                        }
                                                        %>
                                                        <%= ControlCombo.draw(FrmSrcMedicalRecord.fieldNames[FrmSrcMedicalRecord.FRM_FIELD_DEPARTMENT_ID],"formElemen",null, selectDept, dept_value, dept_key, "onchange=\"javascript:deptChange();\"") %></td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="10%" align="right"> 
                                                        <div align="left">Employee</div>
                                                      </td>
                                                      <td width="2%">:</td>
                                                      <td width="88%"> 
                                                        <%
                                                            String whereEmp = " DEPARTMENT_ID = " + deptId;
                                                            String orderEmp = " FULL_NAME ";
                                                            Vector emp_value = new Vector(1,1);
                                                            Vector emp_key = new Vector(1,1);
                                                            emp_value.add("0");
                                                            emp_key.add("select...");
                                                            Vector listEmployee = PstEmployee.list(0, 0, whereEmp, orderEmp);
                                                            String selectValueEmp = ""+srcMedicalRecord.getEmployeeId();//String.valueOf(empId);
                                                            for (int i = 0; i < listEmployee.size(); i++) {
                                                                            Employee emp = (Employee) listEmployee.get(i);
                                                                            emp_key.add(emp.getFullName());
                                                                            emp_value.add(String.valueOf(emp.getOID()));
                                                            }
                                                            %>
                                                        <%= ControlCombo.draw(FrmSrcMedicalRecord.fieldNames[FrmSrcMedicalRecord.FRM_FIELD_EMPLOYEE_ID],"elementForm", null, selectValueEmp, emp_value, emp_key) %></td>
                                                    </tr>
                                                    <tr> 
                                                      <%
                                                        Vector medCaseValues = new Vector(1,1);
                                                        Vector medCaseKeys = new Vector(1,1);
                                                        medCaseValues.add("");
                                                        medCaseKeys.add("select...");														
                                                           String orderBy = " "+PstMedicalCase.fieldNames[PstMedicalCase.FLD_SORT_NUMBER];                                                       
                                                           Vector  medCaseList = PstMedicalCase.list(0,200, "", orderBy);                                                       
                                                                                                                
                                                        if(medCaseList!=null && medCaseList.size()>0){
                                                                String sLastMedCaseGroup="";
                                                                for(int i=0; i<medCaseList.size(); i++){
                                                                        MedicalCase medCase = (MedicalCase)medCaseList.get(i);
                                                                  if(sLastMedCaseGroup.compareTo(""+medCase.getCaseGroup())!=0){
                                                                          sLastMedCaseGroup=medCase.getCaseGroup();
                                                                          //medCaseValues.add(""+medCase.getOID());
                                                                          //medCaseKeys.add(medCase.getCaseName());
                                                                          medCaseValues.add(""+medCase.getOID());
                                                                          medCaseKeys.add(medCase.getCaseName());
                                                                        } else{
                                                                        
                                                                            medCaseValues.add(""+medCase.getOID());
                                                                            medCaseKeys.add(medCase.getCaseName());
                                                                        }
                                                                }
                                                        }
                                                        %>
                                                    
                                                      <td width="10%"> 
                                                        <div align="left">Case/Treatment</div>
                                                      </td>
                                                      <td width="2%">:</td>
                                                      <td width="88%"> <%=ControlCombo.draw(FrmSrcMedicalRecord.fieldNames[FrmSrcMedicalRecord.FRM_FIELD_MEDICAL_CASE_ID],null, ""+srcMedicalRecord.getMedicalCaseId(), medCaseValues , medCaseKeys, "formElemen", "")%> </td>
                                                    </tr>                                                    
                                                    <tr> 
                                                      <%
														Vector diseasetypeid_value = new Vector(1,1);
														Vector diseasetypeid_key = new Vector(1,1);
														diseasetypeid_value.add("");
														diseasetypeid_key.add("select...");														
														if(vectDiseaseType!=null && vectDiseaseType.size()>0){
															for(int i=0; i<vectDiseaseType.size(); i++){
																DiseaseType diseaseType = (DiseaseType)vectDiseaseType.get(i);
																diseasetypeid_value.add(""+diseaseType.getOID());
																diseasetypeid_key.add(diseaseType.getDiseaseType());
															}
														}
														%>
                                                      <td width="10%"> 
                                                        <div align="left">Disease 
                                                          Type</div>
                                                      </td>
                                                      <td width="2%">:</td>
                                                      <td width="88%"> <%=ControlCombo.draw(FrmSrcMedicalRecord.fieldNames[FrmSrcMedicalRecord.FRM_FIELD_DESEASE_TYPE_ID],null, ""+srcMedicalRecord.getDiseaseTypeId(), diseasetypeid_value , diseasetypeid_key, "formElemen", "")%> </td>
                                                    </tr>
                                                    <tr> 
                                                      <%
														Vector medicaltypeid_value = new Vector(1,1);
														Vector medicaltypeid_key = new Vector(1,1);
														medicaltypeid_value.add("");
														medicaltypeid_key.add("select..."); 														
														if(vectMedicalType!=null && vectMedicalType.size()>0){
															for(int i=0; i<vectMedicalType.size(); i++){
																MedicalType medicalType = (MedicalType)vectMedicalType.get(i);
																medicaltypeid_value.add(""+medicalType.getOID());
																medicaltypeid_key.add(medicalType.getTypeName()); 
															}
														}													
														%>
                                                      <td width="10%"> 
                                                        <div align="left">Medical 
                                                          Type</div>
                                                      </td>
                                                      <td width="2%">:</td>
                                                      <td width="88%"> <%=ControlCombo.draw(FrmSrcMedicalRecord.fieldNames[FrmSrcMedicalRecord.FRM_FIELD_MEDICAL_TYPE_ID],null, ""+srcMedicalRecord.getMedicalTypeId(), medicaltypeid_value , medicaltypeid_key, "formElemen", "")%> </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="10%" height="27" align="right"> 
                                                        <div align="left">Sort 
                                                          List by</div>
                                                      </td>
                                                      <td width="2%" height="27">:</td>
                                                      <td width="88%" height="27"><%=ControlCombo.draw(FrmSrcMedicalRecord.fieldNames[FrmSrcMedicalRecord.FRM_FIELD_ORDER_BY],"formElemen",null,srcMedicalRecord.getOrderBy()+"",SessMedicalRecord.getKeySortMedicalBy(),SessMedicalRecord.getValSortMedicalBy()) %> </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="10%" height="27"> 
                                                        <div align="left"></div>
                                                      </td>
                                                      <td width="2%" height="27">&nbsp;</td>
                                                      <td width="88%" height="27">&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td colspan="3"> 
                                                        <div align="left"> 
                                                          <table width="41%" border="0" cellspacing="1" cellpadding="1">
                                                            <tr> 
                                                              <td width="7%"><a href="javascript:cmdSearch()"><img src="../../images/BtnSearch.jpg" width="24" height="24" border="0"></a></td>
                                                              <td width="38%"><b><a href="javascript:cmdSearch()" class="buttonlink">Search 
                                                                Medical Record</a></b></td>
                                                              <td width="7%"><a href="javascript:cmdAdd()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0"></a></td>
                                                              <td width="48%"><b><a href="javascript:cmdAdd()" class="buttonlink">Add 
                                                                New Medical Record</a></b></td>
                                                            </tr>
                                                          </table>
                                                        </div>
                                                      </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                            </table>
                                          </form>
                                        </td>
                                      </tr>
                                    </table>
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
  <tr> 
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" --> {script} 
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
<%
}else{
%>    
    <script language="javascript">
              window.location="<%=approot%>/inform.jsp?ic=<%= I_SystemInfo.HAVE_NOPRIV%>";
    </script>             
<%
}
%>

