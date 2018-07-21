<%@page contentType="text/html"%>
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
<%@ page import = "com.dimata.harisma.form.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% 
    int appObjCodeGen = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_TRAINING, AppObjInfo.OBJ_GENERAL_TRAINING); 
    int appObjCodeDept = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_TRAINING, AppObjInfo.OBJ_DEPARTMENTAL_TRAINING); 
    int appObjCode = 0; 
    
    // check training privilege (0 = none, 1 = general, 2 = departmental)
    int trainType = checkTrainingType(appObjCodeGen, appObjCodeDept, userSession);
    
    if(trainType == PRIV_GENERAL) {    
        appObjCode = appObjCodeGen;
    }
    else if(trainType == PRIV_DEPT) {  
        appObjCode = appObjCodeDept;
    }

    boolean privView = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
    boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
    boolean privUpdate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
    boolean privDelete = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
    boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>
<%@ include file = "../../main/checktraining.jsp" %>

<%!
	public String drawList(Vector objectClass, String emp_period, long  oidTraining, long oidEmployee){
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("Employee Number","8%");
		ctrlist.addHeader("Full Name","15%");
		ctrlist.addHeader("Department","10%");
			
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.reset();
                Hashtable hDept = new Hashtable();
                Vector listDept = PstDepartment.listAll();
                hDept.put("0", "-");
                for (int ls = 0; ls < listDept.size(); ls++) {
                    Department dept = (Department) listDept.get(ls);
                    hDept.put(String.valueOf(dept.getOID()), dept.getDepartment());
                }

		for (int i = 0; i < objectClass.size(); i++) {
                   
			Employee employee = (Employee) objectClass.get(i);
			Vector rowx = new Vector();
                           rowx.add(employee.getEmployeeNum());
                            rowx.add(employee.getFullName());
                            rowx.add(hDept.get(String.valueOf(employee.getDepartmentId())));
                            lstData.add(rowx);
                            lstLinkData.add(String.valueOf(employee.getOID()) + "','" + employee.getEmployeeNum() + "','" + employee.getFullName() + "','" + employee.getDepartmentId()); 
		}

		ctrlist.setLinkSufix("')");
		return ctrlist.draw();
	}
%>

<% 
	CtrlTrainingHistory ctrlTrainingHistory = new CtrlTrainingHistory(request);
	FrmSrcEmployee frmSrcEmployee = new FrmSrcEmployee();
	int iCommand = FRMQueryString.requestCommand(request);
        String oidDepartment = request.getParameter("oidDepartment");
	String trainer = request.getParameter("trainer");
	String dateHistory = request.getParameter("dateHistory");
	long oidTrainingAktivityActual = FRMQueryString.requestLong(request, "oidTrainingAktivityActual");
	long oidTrainingHistory = FRMQueryString.requestLong(request, "hidden_training_history_id");
	long oidEmployee =  FRMQueryString.requestLong(request, "oidEmployee");
	long oidTraining =  FRMQueryString.requestLong(request, "oidTraining");
        long lOidDepartment = FRMQueryString.requestLong(request, "oidDepartment");
        long oidTrainingPlan = FRMQueryString.requestLong(request, "oidTrainingPlan");        
              
	String whereClause = "";        
        if(oidDepartment!=null && oidDepartment.length()>0  && lOidDepartment!=0 ){
            whereClause = PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + "=" + oidDepartment + " AND  ";
        }
	//whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 0 ";
        whereClause += PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 0 ";

        int start = 0;
        int recordToGet = 0;
        String orderClause = "";
	Date dateStart = new Date();
	Date startTime = new Date();
	Date endTime = new Date();
        Vector listEmp = new Vector(1,1);
    
        if(iCommand==Command.LIST){
            // added by Bayu
            whereClause = whereClause + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                        " NOT IN (SELECT " + PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID] +
                        " FROM " + PstTrainingHistory.TBL_HR_TRAINING_HISTORY +
                        " WHERE " + PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ACTIVITY_PLAN_ID] +
                        " = " + oidTrainingPlan + ")";
            // -------------
            listEmp = PstEmployee.list(start, recordToGet, whereClause, orderClause);
        }
 	//TrainingActivityActual trH = new TrainingActivityActual ();
 	if(listEmp != null && listEmp.size()>0){
            try{
                 TrainingActivityActual trH = PstTrainingActivityActual.fetchExc(oidTrainingAktivityActual);
            }
            catch(Exception e){}			
	}
	
        if((iCommand==Command.SAVE))		 
             ctrlTrainingHistory.actionActual(iCommand , oidTrainingHistory, oidTraining, oidEmployee, oidTrainingAktivityActual, request);
		
%>
<html>
<head><title>Search Employee</title>
<script language="javascript">
	<%if(iCommand == Command.SAVE){// && iErrCode == 0){%>
		self.opener.document.fractual.command.value="<%=Command.EDIT%>";
		self.opener.document.fractual.hidden_training_activity_id.value="<%=oidTrainingAktivityActual%>";
		self.opener.document.fractual.submit();
                self.close();	
	<%}%>

        function cmdEdit(oid, number, fullname, department) {
           //alert("oidEmployee" +oid);
                    document.frm_empschedule.<%=FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_EMPLOYEE_ID]%>.value=oid;
                    document.frm_empschedule.oidEmployee.value=oid;
                    oidTrainingAktivityActual =  document.frm_empschedule.oidTrainingAktivityActual.value; 
                    document.frm_empschedule.command.value="<%=Command.SAVE%>"; 
                    //document.frm_empschedule.action="training_act_actual_edit.jsp?oidEmployee=" + oid;// + "&oidTrainingHistory=" + oidTrainingHistory + "&oidTraining=" + oidTraining + "&oidTrainingAktivityActual=" + oidTrainingAktivityActual  ;//?oidEmployee=" + oid;
                    document.frm_empschedule.submit();
            //self.close();
        }
    
    
    function cmdChangeDept(){
         document.frm_empschedule.command.value="<%=Command.LIST%>"; 
         document.frm_empschedule.submit();
        }
</script>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form name="frm_empschedule" method="get" action="">
<input type="hidden" name="command" value="<%=iCommand%>">
<input  type="hidden" name="oidTraining" value="<%=oidTraining%>" size="50">
<input type="hidden" name="<%=FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_TRAINING_PROGRAM]%>" value="<%=oidTraining%>">
<input  type="hidden" name="oidEmployee" value="<%=oidEmployee%>" size="50">
<input type="hidden" name="<%=FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_EMPLOYEE_ID]%>" value="<%=oidEmployee%>">
<input  type="hidden" name="oidTrainingAktivityActual" value="<%=oidTrainingAktivityActual%>" size="50">


<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#FCFDEC" >
  <%-- <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <%@ include file = "../../main/header.jsp" %>
  </tr> --%>
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr> 
          <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
			<input type="hidden" name="hidden_training_history_id" value="<%=oidTrainingHistory%>">
                        <%-- added by bayu --%>
                        <input type="hidden" name="oidTrainingPlan" value="<%=oidTrainingPlan%>">
              <tr> 
                  <td height="20"> <font color="#FF6600" face="Arial"><strong>Select Employee</strong></font> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Department : 
                        <%                                                  
                        String where = "";
                        
                        if(trainType == PRIV_DEPT)
                            where = PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + "=" + departmentOid;
                           
                        Vector listDepartment = PstDepartment.list(0, 0, where, " DEPARTMENT ");
                        Vector deptValue = new Vector(1,1); //hidden values that will be deliver on request (oids) 
                        Vector deptKey = new Vector(1,1); //texts that displayed on combo box

                        for(int d=0;d<listDepartment.size();d++){
                                Department department = (Department)listDepartment.get(d);
                                deptValue.add(""+department.getOID());
                                deptKey.add(department.getDepartment());
                        }											
                        String select_departmentid = ""+oidDepartment; //selected on combo box
                        %>
                        <%=ControlCombo.draw("oidDepartment", null, select_departmentid, deptValue, deptKey, "onChange=\"javascript:cmdChangeDept()\"", "formElemen")%>    
                            &nbsp;&nbsp;
                        <input type="button" onclick="javascript:cmdChangeDept()" caption="List employee" value="List">                                            
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
                                  <td valign="top">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle"> 
                                                <%=drawList(listEmp, oidDepartment,oidTraining,oidEmployee)%>
                                                </td>
                                              </tr>
                                              <%--
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle"> 
                                                &nbsp;&nbsp;&nbsp;<b>Note</b> : <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;* - this employee already had a schedule
                                                </td>
                                              </tr>
                                              --%>
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
                      </td>
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
    <td colspan="2" height="20" bgcolor="#15A9F5">
      <%@ include file = "../../main/footer.jsp" %>
    </td>
  </tr>
</table>
</form>
</body>
</html>
