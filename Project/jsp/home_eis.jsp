
<%@page import="com.dimata.harisma.entity.search.SrcEmpReprimand"%>
<%@page import="com.dimata.harisma.form.employee.FrmEmployee"%>
<%@page import="com.dimata.harisma.form.search.FrmSrcSpecialEmployeeQuery"%>
<%@page import="com.dimata.harisma.form.search.FrmSrcSpecialEmployee"%>
<%@page import="com.dimata.harisma.entity.masterdata.leaveconfiguration.PstLeaveConfigurationMain"%>
<%@page import="javax.swing.text.Style"%>
<%@ page language="java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.locker.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>
<%@ page import = "com.dimata.qdep.system.*" %>
<%@ page import = "com.dimata.harisma.session.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.session.leave.*"%>
<%@ page import = "com.dimata.harisma.entity.leave.*" %>
<%@ page import = "com.dimata.harisma.form.leave.*" %>
<%@ page import = "com.dimata.harisma.form.admin.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.session.leave.dp.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.dp.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.ll.*" %>
<%@ page import = "com.dimata.harisma.session.leave.ll.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file = "main/javainit.jsp" %>
<%  int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_LOGIN, AppObjInfo.G2_LOGIN, AppObjInfo.OBJ_LOGIN_LOGIN); %>
<%@ include file = "main/checkuser.jsp" %>
<%
            int appObjCodeMainMenu = AppObjInfo.composeObjCode(AppObjInfo.G1_LOGIN, AppObjInfo.G2_MENU, AppObjInfo.OBJ_MENU);
            int appObjCodeMenuEmployee = AppObjInfo.composeObjCode(AppObjInfo.G1_LOGIN, AppObjInfo.G2_MENU, AppObjInfo.OBJ_MENU_EMPLOYEE);
            
            int objCodeEmployeeSchedule = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_ATTENDANCE, AppObjInfo.OBJ_WORKING_SCHEDULE);

            boolean privViewMainMenu = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeMainMenu, AppObjInfo.COMMAND_VIEW));
            boolean privViewMenuEmployee = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeMenuEmployee, AppObjInfo.COMMAND_VIEW));
            boolean privViewChangePinNPasword =  userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeMenuEmployee, AppObjInfo.COMMAND_VIEW));          
            if(privViewMainMenu == false && privViewMenuEmployee == false){
                privViewMainMenu = true;
                privViewMenuEmployee = false;
            }
            
            boolean privAddSchedule = userSession.checkPrivilege(AppObjInfo.composeCode(objCodeEmployeeSchedule, AppObjInfo.COMMAND_ADD));
            boolean isSecretaryLogin = (positionType >= PstPosition.LEVEL_SECRETARY) ? true : false;             
            long hrdDepartmentOid = 0;
            try{
                hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT))); 
            }catch(Exception exc){
                out.println("System Property : OID_HRD_DEPARTMENT is not set, please go to menu system property and set");
            }
            boolean isHRDLogin = hrdDepartmentOid == departmentOid ? true : false;
            
            long edpSectionOid = 0;
            try{
                edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION))); 
            }catch(Exception exc){
                out.println("System Property : OID_EDP_SECTION is not set, please go to menu system property and set");
            }
            
            //update by satrya 2013-12-23
            boolean viewChangePassword=false;
            try{
                viewChangePassword = Boolean.parseBoolean(String.valueOf(PstSystemProperty.getValueByName("VIEW_CHANGE_PASSWORD")));  
            }catch(Exception exc){
                out.println("System Property : VIEW_CHANGE_PASSWORD is not set, please go to menu system property and set");
            }
            
            boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
            boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
            
            response.setHeader("Expires", "Mon, 06 Jan 1990 00:00:01 GMT"); 
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "nocache");

            int TYPE_SAVE = 1;            
           
%>
<!-- Jsp Block -->
<%! 



//update by priska 2014-11-06
SearchSpecialQuery searchSpecialQuery = new SearchSpecialQuery();

FrmSrcSpecialEmployeeQuery frmSrcSpecialEmployeeQuery = new FrmSrcSpecialEmployeeQuery();

%>

<%
            int iCommand = FRMQueryString.requestCommand(request);
            
            int viewListEndV1= FRMQueryString.requestInt(request,"viewListEndV1");
            
            Vector listEmpBirthday = new Vector(1, 1);
            //priska 20141106
            Vector listEmpEndedContract = new Vector(1, 1);
            Vector listEmpEndedContractDua = new Vector(1, 1);
            
            Vector listEmpEndedContractTiga = new Vector(1, 1);
            //add opie-eyek 20141106
            int sizeEndContractToday=0;
            int sizeEndContractthismonth=0;
            int sizeEndContractfreevalue=0;
            boolean aksesApproval = false;

            Employee objEmployee = new Employee();
            Position position = new Position();
            Section section = new Section();

            I_Atendance attendanceConfig = null;
            try {
                attendanceConfig = (I_Atendance) (Class.forName(PstSystemProperty.getValueByName("ATTENDANCE_CONFIG")).newInstance());
            }
            catch(Exception e) {
                System.out.println("Exception : " + e.getMessage());
            }
            try {
               if(emplx.getOID()!=0){
                objEmployee = PstEmployee.fetchExc(emplx.getOID());
               }
            } catch (Exception e) {
                System.out.println("EXCEPTION " + e.toString());
            }

            try {
              if(objEmployee.getPositionId()!=0){
                position = PstPosition.fetchExc(objEmployee.getPositionId());
              }
            } catch (Exception e) {
                System.out.println("EXCEPTION " + e.toString());
            }
            
            try {
              if(objEmployee.getSectionId()!=0){
                section = PstSection.fetchExc(objEmployee.getSectionId());
              }
            } catch (Exception e) {
                System.out.println("EXCEPTION " + e.toString());
            }


            if (position.getDisabledAppDeptScope() == PstPosition.DISABLED_APP_DEPT_SCOPE_FALSE || position.getDisabledAppUnderSupervisor() == PstPosition.DISABLED_APP_UNDER_SUPERVISOR_FALSE || 
                    position.getDisabedAppDivisionScope() == PstPosition.DISABLED_APP_DIV_SCOPE_FALSE){

                aksesApproval = true;

            }

            if(viewChangePassword){
                privViewChangePinNPasword = viewChangePassword;
                //update by satrya 2014-01-15 privViewMenuEmployee = aksesApproval;
                
            }
            if (!NonViewBirthday) {
                //Update By Agus 14-02-2014
                SessEmployee sessEmployee = new SessEmployee();
                if(attendanceConfig.getConfigurationBirthDay()== I_Atendance.CONFIGURASI_I_VIEW_BIRTHDAY_A_WEEK){ 
                listEmpBirthday = sessEmployee.getBirthdayReminder();
                }else{
                    
                    if(attendanceConfig!=null && attendanceConfig.getConfigurationBirthDay()== I_Atendance.CONFIGURASI_III_VIEW_BIRTHDAY_A_MONTH_NOT_SHOW_ANNYVERSARY_DATE_HAS_PASSED){   
                        listEmpBirthday = sessEmployee.getBirthdayReminderMonth();
                    }else{
                        listEmpBirthday = sessEmployee.getBirthdayReminderMonth();
                    }
                }            
            }            
//priska 2014-11-06
            
                //Update By Agus priska 06-11-2014
            // emngambil nilai dari system properties
           
               SearchSpecialQuery searchSpecialQuery = new SearchSpecialQuery();
               searchSpecialQuery.setRadioendcontract(1);
               Date now = new Date();
               searchSpecialQuery.addEndcontractfrom(now);
               searchSpecialQuery.addEndcontractto(now);
               searchSpecialQuery.setiSex(2);
               sizeEndContractToday = SessSpecialEmployee.countSearchSpecialEmployee(searchSpecialQuery, 0, 0);
               
               if(viewListEndV1==1){
                    listEmpEndedContract=SessSpecialEmployee.searchSpecialEmployee(searchSpecialQuery, 0, 0);
                    session.putValue(SessEmployee.SESS_SRC_EMPLOYEE, searchSpecialQuery);
               }
              
               Date dt = new Date();
               dt.setDate(1);
               
               Date dt2 = new Date();
               dt2.setDate(30);
               SearchSpecialQuery searchSpecialQueryDua = new SearchSpecialQuery();
               searchSpecialQueryDua.setRadioendcontract(1);
               searchSpecialQueryDua.addEndcontractfrom(dt);
               searchSpecialQueryDua.addEndcontractto(dt2);
               searchSpecialQueryDua.setiSex(2);
               sizeEndContractthismonth = SessSpecialEmployee.countSearchSpecialEmployee(searchSpecialQueryDua, 0, 0);
               Vector  listEm = new Vector(1,1);
               try{
               listEm = SessSpecialEmployee.searchSpecialEmployee(searchSpecialQueryDua,0,500);     
               } catch (Exception ex){
                   
               }
               
               if(viewListEndV1==2){
                    listEmpEndedContractDua=SessSpecialEmployee.searchSpecialEmployee(searchSpecialQueryDua, 0, 0);
                    session.putValue(SessEmployee.SESS_SRC_EMPLOYEE, searchSpecialQueryDua);
                }
               
               Date dt3 = new Date();  
            int   lengthContractEnd = Integer.valueOf(PstSystemProperty.getValueByName("SET_LENGTH_END_CONTRACT")); 
               dt3.setDate(dt3.getDate()+lengthContractEnd);
               SearchSpecialQuery searchSpecialQueryTiga = new SearchSpecialQuery();
               searchSpecialQueryTiga.setRadioendcontract(1);
               searchSpecialQueryTiga.addEndcontractfrom(now);
               searchSpecialQueryTiga.addEndcontractto(dt3);
               searchSpecialQueryTiga.setiSex(2);
               sizeEndContractfreevalue = SessSpecialEmployee.countSearchSpecialEmployee(searchSpecialQueryTiga, 0, 0);
               
               if(viewListEndV1==3){
                    listEmpEndedContractTiga=SessSpecialEmployee.searchSpecialEmployee(searchSpecialQueryTiga, 0, 0);
                    session.putValue(SessEmployee.SESS_SRC_EMPLOYEE, searchSpecialQueryTiga);
               }
            
               if(iCommand == Command.RETRY){
                
                String[] size_emp_approval      = null;	
                String[] emp_need_approval      = null;          
                String[] emp_approve            = null;
                
                Vector listEmpApproval = new Vector();
                
                try{
                    
                    size_emp_approval = request.getParameterValues("size");
                    emp_need_approval =  new String[size_emp_approval.length];
                    emp_approve =  new String[size_emp_approval.length];
                    
                    Vector listApproval = new Vector(); 
                    long employeeId = 0;                  
                    
                    for(int i = 0 ; i < size_emp_approval.length ; i++){
                        
                        emp_need_approval[i] = FRMQueryString.requestString(request,"leave_app_"+i);                        
                        emp_approve[i] = FRMQueryString.requestString(request,"employee_approval_"+i);   
                          
                        int ix = FRMQueryString.requestInt(request, "data_app_process"+i);
                        
                        if(ix == 1){
                            
                            long appOid = 0;
                            long empOid = 0;
                            
                            try{
                                
                                appOid  = Long.parseLong(emp_need_approval[i]);       
                                employeeId  = Long.parseLong(emp_approve[i]);                               
                                        
                            }catch(Exception E){
                                System.out.println("Exception "+E.toString());
                            }
                            
                            LeaveApplication objleaveApplication = new LeaveApplication();
                            
                            objleaveApplication.setOID(appOid);                            
                            
                            listApproval.add(objleaveApplication);
                            
                        }
                        
                    }
                   
                    SessLeaveApplication.approvalCheckBox(employeeId,listApproval);
                    
                }catch(Exception E){
                    System.out.println();
                }
            }
            
            if(iCommand ==Command.LOCK){
                
                String[] size_emp_sch   = null;	
                String[] emp_sch_id     = null;             	                
                
                Vector listEmpSchedule = new Vector();
                
                try{
                    
                    size_emp_sch = request.getParameterValues("size");
                    emp_sch_id =  new String[size_emp_sch.length];
                    
                    for(int i = 0 ; i < size_emp_sch.length ; i++){
                        
                        emp_sch_id[i] = FRMQueryString.requestString(request,"emp_schedule_id_"+i);
                        EmpSchedule empSchedule = new EmpSchedule();
                        int ix = FRMQueryString.requestInt(request, "data_is_process"+i);                       
                        if(ix==1){            
                                long schOid = 0;
                                
                                try{
                                    schOid = Long.parseLong(emp_sch_id[i]);
                                }catch(Exception E){
                                    System.out.println("Exception "+E.toString());
                                }
                                
                                empSchedule.setOID(schOid);
                            
                        }
                        
                        listEmpSchedule.add(empSchedule);
                    }
                    
                    SessEmpSchedule.updateScheduleToBeCheck(listEmpSchedule);
                    
                }catch(Exception E){
                    System.out.println("Exception "+E.toString());
                }
            }

            //Update By Agus 14-02-2014
            if (iCommand == Command.SUBMIT) {
                SessEmployee sessEmployee = new SessEmployee();
                if(attendanceConfig!=null && attendanceConfig.getConfigurationBirthDay()==I_Atendance.CONFIGURASI_I_VIEW_BIRTHDAY_A_WEEK){ 
                listEmpBirthday = sessEmployee.getBirthdayReminder();
                }else{
                    if(attendanceConfig!=null && attendanceConfig.getConfigurationBirthDay()== I_Atendance.CONFIGURASI_III_VIEW_BIRTHDAY_A_MONTH_NOT_SHOW_ANNYVERSARY_DATE_HAS_PASSED){   
                        listEmpBirthday = sessEmployee.getBirthdayReminderMonthNotShowDatePassed();
                    }else{
                        listEmpBirthday = sessEmployee.getBirthdayReminderMonth();
                    }
            }
            }

            
            Vector listNeedApprove = new Vector();

            if (iCommand == Command.ACTIVATE && aksesApproval == true){
                
                long LevelExcomLocal = 0;
                long LevelExcomExpatriat = 0;
                
                try{
                    LevelExcomLocal = Long.parseLong(PstSystemProperty.getValueByName("OID_LEVEL_EXCOM_LOCAL"));
                }catch(Exception E){
                    System.out.println("Exception "+E.toString());
                }
                try{
                    LevelExcomExpatriat = Long.parseLong(PstSystemProperty.getValueByName("OID_LEVEL_EXCOM_EXPATRIAT"));
                }catch(Exception E){
                    System.out.println("Exception "+E.toString());
                }
                
                boolean approveConfiguration=false;
                try{
                    approveConfiguration = Boolean.parseBoolean(PstSystemProperty.getValueByName("LEAVE_CONFIGURATION_APPROVAL_HOME"));
                }catch(Exception E){
                    System.out.println("Exception "+E.toString());
                }
                
                boolean levelExpat = false;
                
                if(objEmployee.getLevelId() == LevelExcomLocal || objEmployee.getLevelId() == LevelExcomExpatriat){
                    levelExpat = true;
                }
                if(approveConfiguration){
                     if(position.getPositionLevel() == PstPosition.LEVEL_MANAGER){
                         if(position.getDisabledAppDeptScope() == PstPosition.DISABLED_APP_DEPT_SCOPE_FALSE){
                            listNeedApprove = SessLeaveApplication.getListApprovalDepartment(objEmployee.getOID(),objEmployee.getDepartmentId(),PstPosition.LEVEL_GENERAL,PstPosition.LEVEL_MANAGER);
                        }
                     }else if(position.getPositionLevel() == PstPosition.LEVEL_ASST_DIRECTOR){
                         if(position.getDisabledAppDeptScope() == PstPosition.DISABLED_APP_DEPT_SCOPE_FALSE){
                            listNeedApprove = SessLeaveApplication.getListApprovalDepartment(objEmployee.getOID(),objEmployee.getDepartmentId(),PstPosition.LEVEL_GENERAL,PstPosition.LEVEL_ASST_DIRECTOR);
                        }
                     }else if(position.getPositionLevel() == PstPosition.LEVEL_DIRECTOR){
                         if(position.getDisabledAppDeptScope() == PstPosition.DISABLED_APP_DEPT_SCOPE_FALSE){
                            listNeedApprove = SessLeaveApplication.getListApprovalDepartment(objEmployee.getOID(),objEmployee.getDepartmentId(),PstPosition.LEVEL_GENERAL,PstPosition.LEVEL_DIRECTOR);
                        }
                     }else if(position.getPositionLevel() == PstPosition.LEVEL_GENERAL_MANAGER){
                         if(position.getDisabledAppDeptScope() == PstPosition.DISABLED_APP_DEPT_SCOPE_FALSE){
                            listNeedApprove = SessLeaveApplication.getListApprovalDepartment(objEmployee.getOID(),objEmployee.getDepartmentId(),PstPosition.LEVEL_GENERAL,PstPosition.LEVEL_GENERAL_MANAGER);
                        }
                     }
                }else{
                   String employeeIds = PstLeaveConfigurationMain.listJoinDetail(emplx.getOID()); 
                    if(employeeIds!=null && employeeIds.length()>0){
                        listNeedApprove = SessLeaveApplication.getListApprovallConfig(employeeIds,emplx.getOID());
                    }else{
                        if(position.getPositionLevel() == PstPosition.LEVEL_GENERAL_MANAGER){
                    
                    if(position.getDisabledAppUnderSupervisor() == PstPosition.DISABLED_APP_UNDER_SUPERVISOR_FALSE){
                        
                        listNeedApprove = SessLeaveApplication.getListGmExcomAndExpat(objEmployee.getSectionId(), SessLeaveApplication.GM_SECTION_SCOPE);
                        
                    }else if(position.getDisabledAppDeptScope() == PstPosition.DISABLED_APP_DEPT_SCOPE_FALSE){
                        
                        listNeedApprove = SessLeaveApplication.getListGmExcomAndExpat(objEmployee.getDepartmentId(), SessLeaveApplication.GM_DEPARTMENT_SCOPE);
                        
                    }else if(position.getDisabedAppDivisionScope() == PstPosition.DISABLED_APP_DIV_SCOPE_FALSE){
                        
                        listNeedApprove = SessLeaveApplication.getListGmExcomAndExpat(objEmployee.getDivisionId(), SessLeaveApplication.GM_DIVISION_SCOPE);
                        
                    }    
                    //listNeedApprove = SessLeaveApplication.getListApprovalGM();  
                    
                }else if(position.getAllDepartment() == PstPosition.ALL_DEPARTMENT_TRUE){
                    
                    if(position.getDisabledAppUnderSupervisor() == PstPosition.DISABLED_APP_UNDER_SUPERVISOR_FALSE){
                        
                        listNeedApprove = SessLeaveApplication.getListApprovalSection(objEmployee.getSectionId(), 0 /*objEmployee.getDepartmentId()*/ ,objEmployee.getOID());                        
                        
                    }else if(position.getDisabledAppDeptScope() == PstPosition.DISABLED_APP_DEPT_SCOPE_FALSE || position.getDisabedAppDivisionScope() == PstPosition.DISABLED_APP_DIV_SCOPE_FALSE){
                        
                        listNeedApprove = SessLeaveApplication.getListApprovalAllDepartment(objEmployee.getOID(),levelExpat);
                        
                    }
                    
                }else{
                    if(position.getDisabedAppDivisionScope() == PstPosition.DISABLED_APP_DIV_SCOPE_FALSE){                        
                        listNeedApprove = SessLeaveApplication.getListApprovalDivision(objEmployee.getOID(), objEmployee.getDivisionId());                                                
                    } else {                    
                        if(position.getDisabledAppDeptScope() == PstPosition.DISABLED_APP_DEPT_SCOPE_FALSE){
                            listNeedApprove = SessLeaveApplication.getListApprovalDepartment(objEmployee.getOID(),objEmployee.getDepartmentId());
                        }else{                   
                            if(position.getDisabledAppUnderSupervisor() == PstPosition.DISABLED_APP_UNDER_SUPERVISOR_FALSE){                    

                                listNeedApprove = SessLeaveApplication.getListApprovalSection(objEmployee.getSectionId(), objEmployee.getDepartmentId() ,objEmployee.getOID());  

                            }
                       }
                    }
                    
                    
                }
                    }
              }//end else
            }

            if (iCommand == Command.LOAD) { 

                listNeedApprove = SessLeaveApplication.getListEmployeeLeaveApplication(emplx.getOID());

            }
            if(iCommand == Command.GET){
                
                SessEmployee.insertToAccess();
                
            }
            
            if(iCommand == Command.RESET){
                
                SessEmployee.setBarcodeNumber();
                
            }

            Employee appUser = new Employee();

            AppUser objAppUser = new AppUser();
            String msg = "";
            int status_update = 0;
            if (iCommand == Command.UPDATE){

              
                status_update = FRMQueryString.requestInt(request, "status");

                if (status_update == 1) {

                    String employeeNum = FRMQueryString.requestString(request, "FRM_EMP_NUM");
                    String empPaswd = FRMQueryString.requestString(request, "FRM_EMP_PIN");
                    String empConfirmPaswd = FRMQueryString.requestString(request, "FRM_EMP_CINFIRM_PIN");

                    if (empPaswd.equals(empConfirmPaswd)) {

                        SessEmployee.updateEmpNum_Pin(emplx.getOID(), empPaswd);

                        msg = "Update Success";

                    } else {

                        msg = "Confirm password wrong";

                    }

                    try {
                        appUser = PstEmployee.fetchExc(emplx.getOID());
                    } catch (Exception e) {
                        System.out.println("EXCEPTION " + e.toString());
                    }
                
                }//update by satrya 2013-12-23
                else if (status_update == 2) {

                    String employeeNum = FRMQueryString.requestString(request, "FRM_USER_NAME");
                    String empPaswd = FRMQueryString.requestString(request, "FRM_EMP_PASSWORD");
                    String empConfirmPaswd = FRMQueryString.requestString(request, "FRM_EMP_CINFIRM_PASSWORD");

                    if (empPaswd.equals(empConfirmPaswd)) {

                        SessEmployee.updateEmpPassword(emplx.getOID(), empPaswd);

                        msg = "Update Success";
                        
                    } else {

                        msg = "Confirm password wrong";

                    }
                    try {
                        objAppUser = PstAppUser.getByEmployeeId(""+emplx.getOID());
                    } catch (Exception e) {
                        System.out.println("EXCEPTION " + e.toString());
                    }
                    status_update = 3;
                    
                }else if(status_update == 3){
                    try {
                        objAppUser = PstAppUser.getByEmployeeId(""+emplx.getOID());
                    } catch (Exception e) {
                        System.out.println("EXCEPTION " + e.toString());
                    }
                }
                //end update by satrya 
                else {

                    try {
                        appUser = PstEmployee.fetchExc(emplx.getOID());
                    } catch (Exception e) {
                        System.out.println("EXCEPTION " + e.toString());
                    }
                }
            }

            if (iCommand == Command.YES) {

                SessEmployee.setEmployeePin();
            }
            
            if (iCommand == Command.START) {

                SessEmployee.setEmployeePin();
            }
            
            if (iCommand == Command.CONFIRM) {

                SessEmployee.UpdateSts();
            }
            
            if (iCommand == Command.UNLOCK) {

                SessEmployee.updateBarcodeNumberEmployee();
                
            }

            if (iCommand == Command.REFRESH){

                SessEmployee.delCheckInOut();
                
            }
            
            if (iCommand == Command.STOP){

                SessEmployee.UpdateStsUncheck();
                
            }


            if (iCommand == Command.SAVE){

                String employeeNum = FRMQueryString.requestString(request, "FRM_EMP_NUM");
                String empPaswd = FRMQueryString.requestString(request, "FRM_EMP_PIN");
                String empConfirmPaswd = FRMQueryString.requestString(request, "FRM_EMP_CINFIRM_PIN");

                if (empPaswd.equals(empConfirmPaswd)) {

                    SessEmployee.updateEmpNum_Pin(emplx.getOID(), empPaswd);
                    msg = "Update Success";

                } else {

                    try {
                        appUser = PstEmployee.fetchExc(emplx.getOID());
                    } catch (Exception e) {
                        System.out.println("EXCEPTION " + e.toString());
                    }

                    msg = "Confirm password wrong";

                }
            }
        
       Vector listScheduleCheck = new Vector();     
       
       if (iCommand == Command.ASSIGN  || iCommand == Command.LOCK) {

            Vector dept_value = new Vector(1, 1);
            Vector dept_key = new Vector(1, 1);
            Vector listDept = new Vector(1, 1);
            if (processDependOnUserDept) {
                if (emplx.getOID() > 0) {
                    if (isHRDLogin || isEdpLogin || isGeneralManager) {
                        dept_value.add("0");
                        dept_key.add("select ...");
                        listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
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
                                      grpIdx = countIdx;   
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
            
            listScheduleCheck = SessEmpSchedule.searchEmpScheduleToBeCheck(listDept);            
                        
        }

%>

<%
  /*
   * Update by Hendra McHen
   * Date : 2014-11-21
   * Description : get list reprimand by Today, and This month
   */
    Vector listReprimand = new Vector();
    Date dateT = new Date();
    Calendar cal = Calendar.getInstance();
    cal.setTime(dateT);
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH) + 1; // tambah 1 karena month dimulai dari 0
    int day = cal.get(Calendar.DAY_OF_MONTH);
    String dateNow = year+"-"+month+"-"+day;
    String whereClause = "";
    String orderClause = "";
    int vectSize = 0;
  if (viewListEndV1 == 4){
      whereClause = PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_REP_DATE] + " LIKE " + "'"+dateNow+"'";  
  }
  
  if (viewListEndV1 == 5){
      whereClause = PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_REP_DATE] + " LIKE " + "'"+year+"-"+month+"%'";     
  }
  orderClause = PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_REP_DATE];
  vectSize = PstEmpReprimand.getCount(whereClause);
  listReprimand = PstEmpReprimand.list(0, vectSize, whereClause, orderClause);
%>

<%
    String url= request.getParameter("menu");
   
%>
<!-- JSP Block -->
<!-- End of JSP Block -->

<html><!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" --> 
        <title>Harisma - Index Page</title>
        <!-- #EndEditable --> 
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
        <!-- #BeginEditable "styles" --> 
        <link rel="stylesheet" href="styles/main.css" type="text/css">
       
        <link rel="stylesheet" type="text/css" href="stylesheets/general_home_style.css" />
        <!-- #EndEditable -->
        <!-- #BeginEditable "stylestab" --> 
        <link rel="stylesheet" href="<%=approot%>/styles/tab.css" type="text/css">
        <link rel="stylesheet" href="<%=approot%>/styles/form.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "headerscript" --> 
        <SCRIPT language=JavaScript>
            
            function cmdEdit(oidLeave, oidEmployee)
            {
                document.frm.command.value="<%=Command.EDIT%>";
                document.frm.<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_LEAVE_APPLICATION_ID]%>.value = oidLeave;
                document.frm.oid_employee.value = oidEmployee;
                document.frm.action="employee/leave/leave_app_edit.jsp";
                document.frm.submit();
            }    
           // from priska 07-11-2014
            function cmdEditEmp(oidEmployee){
		document.frm.employee_oid.value=oidEmployee;
		document.frm.command.value="<%=Command.EDIT %>";
		document.frm.action="employee/databank/employee_edit.jsp";
		document.frm.submit();
            }
            
            function cmdEditSchedule(oid){
                document.frm.command.value="<%=String.valueOf(Command.EDIT)%>";                
                document.frm.hidden_emp_schedule_id.value = oid;
		document.frm.action="employee/attendance/empschedule_edit.jsp";
		document.frm.submit();
            }
            
            function cmdEditMyLeave(oidLeave, oidEmployee)
            {
                document.frm.command.value="<%=Command.EDIT%>";
                document.frm.<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_LEAVE_APPLICATION_ID]%>.value = oidLeave;
                document.frm.oid_employee.value = oidEmployee;
                document.frm.action="employee/leave/leave_app_edit.jsp";
                document.frm.submit();
            }    
            
            function cmdSetPin(){
                document.frm.command.value="<%=Command.YES %>";        
                document.frm.action="home.jsp";
                document.frm.submit();
            }
            
            function cmdView(){
                document.frm.command.value="<%=Command.SUBMIT%>";
                document.frm.action="home.jsp";	
                document.frm.submit();
            }
            
            function checkview(idxview){
                document.frm.command.value="<%=Command.SUBMITENDEDCONTRACT%>";
                 document.frm.viewListEndV1.value=idxview;
                document.frm.action="home.jsp";	
                document.frm.submit(); 
            }
            
            function cmdViewEC(){
                document.frm.command.value="<%=Command.SUBMITENDEDCONTRACT%>";
                document.frm.action="home.jsp";	
                document.frm.submit();
            }
            
            function cmdViewApprovalLeave(){
                document.frm.command.value="<%=Command.ACTIVATE %>";        
                document.frm.action="home.jsp";	
                document.frm.submit();
            }
            
            function cmdViewApplyLeave(){
                document.frm.command.value="<%=Command.EDIT%>";        
                document.frm.oid_employee.value = "<%=emplx.getOID()%>";
                document.frm.action="employee/leave/leave_app_edit.jsp";
                document.frm.submit();
            }
            
            function cmdViewMyLeave(){
                document.frm.command.value="<%=Command.LOAD %>";
                document.frm.oid_employee.value = "<%=emplx.getOID()%>";
                document.frm.action="home.jsp";	
                document.frm.submit();
            }
            
            function cmdChangePassword(){
                document.frm.command.value="<%=Command.UPDATE %>";
                document.frm.oid_employee.value = "<%=emplx.getOID()%>";
                document.frm.action="home.jsp";	
                document.frm.submit();
            }
            
            function cmdChangePasswordNew(){
                document.frm.command.value="<%=Command.UPDATE %>";
                 document.frm.status.value="<%=3 %>";
                document.frm.oid_employee.value = "<%=emplx.getOID()%>";
                document.frm.action="home.jsp";	
                document.frm.submit();
            }
            
            function cmdProccessSchedule(){
                document.frm.command.value="<%=Command.LOCK %>";
                document.frm.oid_employee.value = "<%=emplx.getOID()%>";
                document.frm.action="home.jsp";	
                document.frm.submit();
            }
            
            function cmdProccessApproval(){
                document.frm.command.value="<%=Command.RETRY %>";
                document.frm.oid_employee.value = "<%=emplx.getOID()%>";
                document.frm.action="home.jsp";	
                document.frm.submit();
            }
            
            function cmdSavePassword(){
                document.frm.command.value="<%=Command.UPDATE %>";
                document.frm.status.value="<%=1 %>";
                document.frm.oid_employee.value ="<%=emplx.getOID()%>";
                document.frm.action="home.jsp";	
                document.frm.submit();
            }
            
             function cmdSavePasswordNew(){
                document.frm.command.value="<%=Command.UPDATE %>";
                document.frm.status.value="<%=2 %>";
                document.frm.oid_employee.value ="<%=emplx.getOID()%>";
                document.frm.action="home.jsp";	
                document.frm.submit();
            }
            
            function cmdCancelSavePassword(){
                document.frm.command.value="<%=Command.NONE %>";
                document.frm.oid_employee.value = "<%=emplx.getOID()%>";
                document.frm.action="home.jsp";	
                document.frm.submit();
            }
            
            function cmdScheduleCheck(){
                document.frm.command.value="<%=Command.ASSIGN %>";
                document.frm.oid_employee.value = "<%=emplx.getOID()%>";
                document.frm.action="home.jsp";	
                document.frm.submit();
            }
            
            function InsertDatabase(){
                document.frm.command.value="<%=Command.GET %>";
                document.frm.oid_employee.value = "<%=emplx.getOID()%>";
                document.frm.action="home.jsp";	
                document.frm.submit();
            } 
            
            function updateBarcodeNumber(){
                document.frm.command.value="<%=Command.RESET %>";
                document.frm.oid_employee.value = "<%=emplx.getOID()%>";
                document.frm.action="home.jsp";	
                document.frm.submit();
            } 
            
            function UpdateDatabase(){
                document.frm.command.value="<%=Command.DETAIL %>";
                document.frm.oid_employee.value = "<%=emplx.getOID()%>";
                document.frm.action="home.jsp";	
                document.frm.submit();
            } 
            
            function ChangeBarcode(){
                document.frm.command.value="<%=Command.UNLOCK %>";
                document.frm.oid_employee.value = "<%=emplx.getOID()%>";
                document.frm.action="home.jsp";	
                document.frm.submit();
            }
            
            function delCheckInOut(){
                document.frm.command.value="<%=Command.REFRESH %>";
                document.frm.oid_employee.value = "<%=emplx.getOID()%>";
                document.frm.action="home.jsp";	
                document.frm.submit();
            }

            function SetProces(){
                document.frm.command.value="<%=Command.STOP %>";
                document.frm.oid_employee.value = "<%=emplx.getOID()%>";
                document.frm.action="home.jsp";
                document.frm.submit();
            }

           function cmdPrintXLS(){
		window.open("<%=approot%>/servlet/com.dimata.harisma.report.EmployeeListXLS");
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
            
        </SCRIPT>
        <!-- #EndEditable -->
        
        <style type="text/css">
.inputReadOnly {         
    background-color  : #C0C0C0;
}
</style>
    </head> 
    
    <body bgcolor="<%=bgColorBody%>" <%=verTemplate.equalsIgnoreCase("0")%> leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">      
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" <%=headerStyle?"":"bgcolor=\"#F9FCFF\""%> >
           <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="styletemplate/template_header.jsp" %>
            <%}else{%>
           <tr> 
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
                    <!-- #BeginEditable "header" --> 
      <%@ include file = "main/header.jsp" %>
                    <!-- #EndEditable --> 
                </td>
            </tr> 
            <tr> 
                <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
      <%--@ include file = "main/mnmain_hardrock_par_01.jsp" --%>        
    
      <%if (privViewMainMenu) {%>          
        <%@ include file = "main/mnmain_full.jsp" %>
      <%
      } else if (privViewMenuEmployee) {
      %>        
         <%@ include file = "main/mnmain_logout.jsp" %>
      <%} else {
      %>        
         <%@ include file = "main/mnmain_logout.jsp" %>
      <%}%>  
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
    <td width="100%" valign="top" align="left"> 
<table width="100%" border="0" cellspacing="3" cellpadding="2"> 
    <tr> 
<td width="100%">
        <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
            <tr> 
                <td height="20"><font color="#FF6600" face="Arial"><strong>
                            <!-- #BeginEditable "contenttitle" -->
                            <div align="center"><big><b>Executive Information Dashboard</b></big></div>
                            <!-- #EndEditable --> 
                    </strong></font> <% %>
                </td>
            </tr>
             <%if(url!=null && url.length()>0 && !url.equalsIgnoreCase("home.jsp")){%>
                <jsp:forward page="styletemplate/slide_template.jsp"/> 

                 <%}else{%>
            <tr>
                <td>
                    <table  width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr> 
                            <td > 
                                <table width="100%" border="0" cellspacing="1" cellpadding="1" style="background-color:<%=garisContent%>">
                                    <tr> 
                                        <td valign="top"> 
                                            <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbggHome" bgcolor="<%=bgColorContent%>">
                                                <tr> 
<td valign="top">
    <!-- #BeginEditable "content" -->
    <form name="frm" method="post" action=""> 
        <input type="hidden" name="command">
        <input type="hidden" name="<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_LEAVE_APPLICATION_ID]%>"  value="">
        <input type="hidden" name="oid_employee"  value="<%=emplx.getOID()%>">
        <input type="hidden" name="employee_oid"  value="">
        <input type="hidden" name="status">
        <input type="hidden" name="hidden_emp_schedule_id" value="0">
        <input type="hidden" name="viewListEndV1" value="<%=viewListEndV1%>">
        <%if (privViewMainMenu) { %>
         <table width="100%" border="0" cellspacing="0" cellpadding="0">
             <tr>
                 <td><img src="servlet/com.dimata.harisma.report.eis.KpiStatistic?type=<%=com.dimata.harisma.report.eis.KpiStatistic.REPORT_TYPE_KPI_SUMMARY %>&typediagram=<%=com.dimata.common.jfreechart.I_JFreeConstanta.DIAGRAM_TYPE_BAR %>" /></td>
                 <td><img src="servlet/com.dimata.harisma.report.eis.EmployeeStatistic?type=<%=com.dimata.harisma.report.eis.EmployeeStatistic.REPORT_TYPE_EDUCATION_SUMMARY %>&typediagram=<%=com.dimata.common.jfreechart.I_JFreeConstanta.DIAGRAM_TYPE_PIE %>" /></td>
                 <td><img src="servlet/com.dimata.harisma.report.eis.EmployeeStatistic?type=<%=com.dimata.harisma.report.eis.EmployeeStatistic.REPORT_TYPE_AGE_SUMMARY %>&typediagram=<%=com.dimata.common.jfreechart.I_JFreeConstanta.DIAGRAM_TYPE_BAR %>" /></td>
                 <td></td>
             </tr>


         </table>
         <%}%>
    </form>
    <!-- #EndEditable -->
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
             <%}%>
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
                                <%@include file="footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
        </table>
    </body>
    <!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>

