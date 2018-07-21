<%-- 
    Document   : leave_setting.jsp
    Created on : Jun 11, 2014, 4:07:51 PM
    Author     : Satrya Ramayu
--%>

<%@page import="com.dimata.harisma.entity.masterdata.leaveconfiguration.PstLeaveConfigurationMainRequestOnly"%>
<%@page import="com.dimata.harisma.form.masterdata.leaveconfiguration.CtrlLeaveConfigurationMainRequestOnly"%>
<%@page import="com.dimata.harisma.form.masterdata.leaveconfiguration.FrmLeaveConfigurationDepartmentRequestOnly"%>
<%@page import="com.dimata.harisma.form.masterdata.leaveconfiguration.FrmLeaveConfigurationMainRequestOnly"%>
<%@page import="com.dimata.harisma.entity.masterdata.leaveconfiguration.PstLeaveConfigurationDetailPositionRequestOnly"%>
<%@page import="com.dimata.harisma.entity.masterdata.leaveconfiguration.LeaveConfigurationDetailPositionRequestOnly"%>
<%@page import="com.dimata.harisma.entity.masterdata.leaveconfiguration.LeaveConfigurationMainRequestLeaveOnly"%>
<%@page import="com.dimata.harisma.entity.masterdata.leaveconfiguration.PstLeaveConfigurationDetailDepartementRequestOnly"%>
<%@page import="com.dimata.harisma.entity.masterdata.leaveconfiguration.LeaveConfigurationDetailDepartementRequestOnly"%>
<%@page import="com.dimata.harisma.form.masterdata.leaveconfiguration.FrmLeaveConfigurationDepartment"%>
<%@page import="com.dimata.harisma.entity.masterdata.leaveconfiguration.PstLeaveConfigurationDetailPosition"%>
<%@page import="com.dimata.harisma.entity.masterdata.leaveconfiguration.PstLeaveConfigurationDetailDepartement"%>
<%@page import="com.dimata.harisma.form.masterdata.leaveconfiguration.CtrlLeaveConfigurationDetailDepartment"%>
<%@page import="com.dimata.harisma.entity.masterdata.leaveconfiguration.LeaveConfigurationDetailPosition"%>
<%@page import="com.dimata.harisma.entity.masterdata.leaveconfiguration.LeaveConfigurationDetailDepartement"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.harisma.entity.masterdata.leaveconfiguration.PstLeaveConfigurationMain"%>
<%@page import="com.dimata.harisma.entity.masterdata.leaveconfiguration.LeaveConfigurationMain"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.masterdata.leaveconfiguration.FrmLeaveConfigurationMain"%>
<%@page import="com.dimata.harisma.form.masterdata.leaveconfiguration.CtrlLeaveConfigurationMain"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@ include file = "../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_LEAVE_CONFIGURATION);%>
<%@ include file = "../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%!    public String drawList(int iCommand, int start, Vector objectClass, boolean processDependOnUserDept,boolean isGeneralManager,long oidSelectEmpId,long oidSelectLeaveConfigMain,long oidDepartment,Vector listPosition,LeaveConfigurationMain objLeaveConfigurationMain,FrmLeaveConfigurationMain frmLeaveConfigurationMain,LeaveConfigurationDetailDepartement objLeaveConfigurationDetailDepartement,LeaveConfigurationDetailPosition objLeaveConfigurationDetailPosition,String source) {
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("No", "1%");
        ctrlist.addHeader("Who Approvall", "10%");
        ctrlist.addHeader("Konfiguration Setting", "89%");


        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        //Vector rowx = new Vector(1, 1);
        ctrlist.reset();
        int index = -1;
        int noCounter = start + 1;
        Vector rowx = new Vector();
        Vector listEmployee = PstEmployee.list(0, 0, "RESIGNED = 0", "EMPLOYEE_NUM");
        Vector empKey = new Vector(1,1);
        Vector empValue = new Vector(1,1);

        for(int i =0;i <listEmployee.size();i++){ 
              Employee employee = (Employee)listEmployee.get(i);
                  Department dept =  new Department();
                  try{
                      dept = PstDepartment.fetchExc(employee.getDepartmentId());
                  }
                  catch(Exception e){
                          System.out.println("--- useredit.jsp >>>> ### :: "+employee.getFullName()+", "+e.toString());
                  }
              empKey.add(employee.getEmployeeNum() + " - " + employee.getFullName() + " - (" + dept.getDepartment() + ")");
              empValue.add(""+employee.getOID());

        }
        String attTag =  "data-placeholder=\"Choose a Employee...\" onChange=\"javascript:cmdUpdateDiv(source)\"";
         Vector listDepartment = PstDepartment.listDepartment(oidSelectEmpId, processDependOnUserDept,isGeneralManager); 
         Vector listSection = PstSection.listSection(oidDepartment);
         boolean adaKlikEdit = true;
        if (objectClass != null && objectClass.size() > 0) {
            for (int i = 0; i < objectClass.size(); i++) {
//PublicLeave publicLeave = (PublicLeave) objectClass.get(i);
                LeaveConfigurationMain leaveConfigurationMain = (LeaveConfigurationMain)objectClass.get(i);
                rowx = new Vector();
                rowx.add("" + noCounter);
                noCounter++;

                if (oidSelectLeaveConfigMain == leaveConfigurationMain.getOID()) {
                    index = i;
                }
                LeaveConfigurationDetailDepartement leaveConfigurationDetailDepartement = PstLeaveConfigurationDetailDepartement.listJoin(0, 0, "md."+PstLeaveConfigurationDetailDepartement.fieldNames[PstLeaveConfigurationDetailDepartement.FLD_LEAVE_CONFIGURATION_MAIN_ID]+"="+leaveConfigurationMain.getOID(), "");
                LeaveConfigurationDetailPosition leaveConfigurationDetailPosition = PstLeaveConfigurationDetailPosition.getPositionName(0, 0, PstLeaveConfigurationDetailDepartement.fieldNames[PstLeaveConfigurationDetailDepartement.FLD_LEAVE_CONFIGURATION_MAIN_ID]+"="+leaveConfigurationMain.getOID(), "");
                if (index == i && (iCommand == Command.GOTO || iCommand == Command.EDIT || iCommand == Command.ASK)) {
                    //jika dia ada Edit
                    adaKlikEdit = false;
                    if(iCommand != Command.GOTO){
                         listDepartment = PstDepartment.listDepartment(leaveConfigurationMain.getEmployeeId(), processDependOnUserDept,isGeneralManager);
                         long deptId= leaveConfigurationDetailDepartement.getDepartementIds()==null || leaveConfigurationDetailDepartement.getDepartementIds().length>1?0:Long.parseLong(leaveConfigurationDetailDepartement.getDepartementIds()[0]); 
                         listSection = listSection = PstSection.listSection(deptId);
                         rowx.add(ControlCombo.draw(FrmLeaveConfigurationMain.fieldNames[FrmLeaveConfigurationMain.FRM_FIELD_EMPLOYEE_ID], "chosen-select", "-Selected-", ""+leaveConfigurationMain.getEmployeeId(), empKey,empValue,null, attTag)+"<input type=\"text\" name=\""+FrmLeaveConfigurationMain.fieldNames[FrmLeaveConfigurationMain.FRM_FIELD_PLUS_NOTE_APPROVALL]+"\" value=\"" + leaveConfigurationMain.getPlusNoteApprovall() + "\" size=\"20\" class=\"elemenForm\">");
                         rowx.add(objLeaveConfigurationMain.getStrTable(leaveConfigurationMain.getEmployeeId(),listDepartment,listSection,leaveConfigurationDetailDepartement,leaveConfigurationDetailPosition,listPosition,source));
                    }else{
                        leaveConfigurationMain.setEmployeeId(oidSelectEmpId);
                        long deptId= leaveConfigurationDetailDepartement.getDepartementIds()==null || leaveConfigurationDetailDepartement.getDepartementIds().length>1?0:Long.parseLong(leaveConfigurationDetailDepartement.getDepartementIds()[0]); 
                         listSection = listSection = PstSection.listSection(deptId);
                        rowx.add(ControlCombo.draw(FrmLeaveConfigurationMain.fieldNames[FrmLeaveConfigurationMain.FRM_FIELD_EMPLOYEE_ID], "chosen-select", "-Selected-", ""+leaveConfigurationMain.getEmployeeId(), empKey,empValue,null, attTag)+"<input type=\"text\" name=\""+FrmLeaveConfigurationMain.fieldNames[FrmLeaveConfigurationMain.FRM_FIELD_PLUS_NOTE_APPROVALL]+"\" value=\"" + leaveConfigurationMain.getPlusNoteApprovall() + "\" size=\"20\" class=\"elemenForm\">");
                         rowx.add(objLeaveConfigurationMain.getStrTable(oidSelectEmpId,listDepartment,listSection,leaveConfigurationDetailDepartement,leaveConfigurationDetailPosition,listPosition,source));
                    }
                    

                    lstLinkData.add(String.valueOf(leaveConfigurationMain.getOID()));  

                } else {
                    //jika sdh selesai save maka akan kesini
                    rowx.add(leaveConfigurationMain.getNamaEmployee() + " <B>" + leaveConfigurationMain.getPlusNoteApprovall()+"</B>");
                    rowx.add(leaveConfigurationMain.getStrTableAfterSave(leaveConfigurationDetailDepartement,leaveConfigurationDetailPosition));

                    lstLinkData.add(String.valueOf(leaveConfigurationMain.getOID()));
                }
                lstData.add(rowx);
                //noCounter = (noCounter+1); 
            }
        }
        rowx = new Vector();

        if (adaKlikEdit &&   (iCommand == Command.GOTO ||   iCommand == Command.ADD || (iCommand == Command.SAVE && frmLeaveConfigurationMain.errorSize() > 0) || (objectClass.size() < 1))) {
            rowx.add("" + noCounter);
            objLeaveConfigurationMain.setEmployeeId(oidSelectEmpId); 
            //LeaveConfigurationDetailDepartement objLeaveConfigurationDetailDepartement = new LeaveConfigurationDetailDepartement();
            //LeaveConfigurationDetailPosition objLeaveConfigurationDetailPosition = new LeaveConfigurationDetailPosition();
            noCounter++;

            rowx.add(ControlCombo.draw(FrmLeaveConfigurationMain.fieldNames[FrmLeaveConfigurationMain.FRM_FIELD_EMPLOYEE_ID], "chosen-select", "-Selected-", ""+objLeaveConfigurationMain.getEmployeeId(), empKey,empValue,null, attTag)+"<input type=\"text\" name=\""+FrmLeaveConfigurationMain.fieldNames[FrmLeaveConfigurationMain.FRM_FIELD_PLUS_NOTE_APPROVALL]+"\" value=\"" + objLeaveConfigurationMain.getPlusNoteApprovall() + "\" size=\"20\" class=\"elemenForm\">");
            rowx.add(objLeaveConfigurationMain.getStrTable(objLeaveConfigurationMain.getEmployeeId(),listDepartment,listSection,objLeaveConfigurationDetailDepartement,objLeaveConfigurationDetailPosition,listPosition,source));
            lstLinkData.add(String.valueOf(objLeaveConfigurationMain.getOID()));
            noCounter = (noCounter + 1);
        }
        lstData.add(rowx);
        return ctrlist.draw();
    }

%>

<%!    public String drawListRequestOnly(int iCommand, int start, Vector objectClass, boolean processDependOnUserDept,boolean isGeneralManager,long oidSelectEmpId,long oidSelectLeaveConfigMain,long oidDepartment,Vector listPosition,LeaveConfigurationMainRequestLeaveOnly objLeaveConfigurationMainRequestLeaveOnly,FrmLeaveConfigurationMainRequestOnly frmLeaveConfigurationMainRequestOnly,LeaveConfigurationDetailDepartementRequestOnly objLeaveConfigurationDetailDepartementRequestOnly,LeaveConfigurationDetailPositionRequestOnly objLeaveConfigurationDetailPositionRequestOnly,String source) {
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("No", "1%");
        ctrlist.addHeader("Who Approvall", "10%");
        ctrlist.addHeader("Konfiguration Setting", "89%");


        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        ctrlist.setLinkPrefix("javascript:cmdEditLeaveRequestOnly('");
        ctrlist.setLinkSufix("')");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        //Vector rowx = new Vector(1, 1);
        ctrlist.reset();
        int index = -1;
        int noCounter = start + 1;
        Vector rowx = new Vector();
        Vector listEmployee = PstEmployee.list(0, 0, "RESIGNED = 0", "EMPLOYEE_NUM");
        Vector empKey = new Vector(1,1);
        Vector empValue = new Vector(1,1);

        for(int i =0;i <listEmployee.size();i++){ 
              Employee employee = (Employee)listEmployee.get(i);
                  Department dept =  new Department();
                  try{
                      dept = PstDepartment.fetchExc(employee.getDepartmentId());
                  }
                  catch(Exception e){
                          System.out.println("--- useredit.jsp >>>> ### :: "+employee.getFullName()+", "+e.toString());
                  }
              empKey.add(employee.getEmployeeNum() + " - " + employee.getFullName() + " - (" + dept.getDepartment() + ")");
              empValue.add(""+employee.getOID());

        }
        String attTag =  "data-placeholder=\"Choose a Employee...\" onChange=\"javascript:cmdUpdateDivLeaveRequestOnly(source)\"";
         Vector listDepartment = PstDepartment.listDepartment(oidSelectEmpId, processDependOnUserDept,isGeneralManager);  
         Vector listSection = PstSection.listSection(oidDepartment);
         boolean adaKlikEdit = true;
        if (objectClass != null && objectClass.size() > 0) {
            for (int i = 0; i < objectClass.size(); i++) {
//PublicLeave publicLeave = (PublicLeave) objectClass.get(i);
                LeaveConfigurationMainRequestLeaveOnly leaveConfigurationMainRequestLeaveOnly = (LeaveConfigurationMainRequestLeaveOnly)objectClass.get(i);
                rowx = new Vector();
                rowx.add("" + noCounter);
                noCounter++;

                if (oidSelectLeaveConfigMain == leaveConfigurationMainRequestLeaveOnly.getOID()) {
                    index = i;
                }
                LeaveConfigurationDetailDepartementRequestOnly leaveConfigurationDetailDepartementRequestOnly = PstLeaveConfigurationDetailDepartementRequestOnly.listJoin(0, 0, "md."+PstLeaveConfigurationDetailDepartementRequestOnly.fieldNames[PstLeaveConfigurationDetailDepartementRequestOnly.FLD_LEAVE_CONFIGURATION_MAIN_ID_REQUEST_ONLY]+"="+leaveConfigurationMainRequestLeaveOnly.getOID(), "");
                LeaveConfigurationDetailPositionRequestOnly leaveConfigurationDetailPositionRequestOnly = PstLeaveConfigurationDetailPositionRequestOnly.getPositionName(0, 0, PstLeaveConfigurationDetailDepartementRequestOnly.fieldNames[PstLeaveConfigurationDetailDepartementRequestOnly.FLD_LEAVE_CONFIGURATION_MAIN_ID_REQUEST_ONLY]+"="+leaveConfigurationMainRequestLeaveOnly.getOID(), ""); 
                if (index == i && (iCommand == Command.GOTO || iCommand == Command.EDIT || iCommand == Command.ASK)) {
                    //jika dia ada Edit
                    adaKlikEdit = false;
                    if(iCommand != Command.GOTO){
                         listDepartment = PstDepartment.listDepartment(leaveConfigurationMainRequestLeaveOnly.getEmployeeId(), processDependOnUserDept,isGeneralManager);
                         long deptId= leaveConfigurationDetailDepartementRequestOnly.getDepartementIds()==null || leaveConfigurationDetailDepartementRequestOnly.getDepartementIds().length>1?0:Long.parseLong(leaveConfigurationDetailDepartementRequestOnly.getDepartementIds()[0]);  
                         listSection = listSection = PstSection.listSection(deptId);
                         rowx.add(ControlCombo.draw(FrmLeaveConfigurationMainRequestOnly.fieldNames[FrmLeaveConfigurationMainRequestOnly.FRM_FIELD_EMPLOYEE_ID], "chosen-select", "-Selected-", ""+leaveConfigurationMainRequestLeaveOnly.getEmployeeId(), empKey,empValue,null, attTag)+"<input type=\"text\" name=\""+FrmLeaveConfigurationMainRequestOnly.fieldNames[FrmLeaveConfigurationMainRequestOnly.FRM_FIELD_PLUS_NOTE_APPROVALL]+"\" value=\"" + leaveConfigurationMainRequestLeaveOnly.getPlusNoteApprovall() + "\" size=\"20\" class=\"elemenForm\">");
                         rowx.add(objLeaveConfigurationMainRequestLeaveOnly.getStrTable(leaveConfigurationMainRequestLeaveOnly.getEmployeeId(),listDepartment,listSection,leaveConfigurationDetailDepartementRequestOnly,leaveConfigurationDetailPositionRequestOnly,listPosition,source));  
                    }else{
                        leaveConfigurationMainRequestLeaveOnly.setEmployeeId(oidSelectEmpId); 
                        long deptId= leaveConfigurationDetailDepartementRequestOnly.getDepartementIds()==null || leaveConfigurationDetailDepartementRequestOnly.getDepartementIds().length>1?0:Long.parseLong(leaveConfigurationDetailDepartementRequestOnly.getDepartementIds()[0]); 
                         listSection = listSection = PstSection.listSection(deptId);
                        rowx.add(ControlCombo.draw(FrmLeaveConfigurationMainRequestOnly.fieldNames[FrmLeaveConfigurationMainRequestOnly.FRM_FIELD_EMPLOYEE_ID], "chosen-select", "-Selected-", ""+leaveConfigurationMainRequestLeaveOnly.getEmployeeId(), empKey,empValue,null, attTag)+"<input type=\"text\" name=\""+FrmLeaveConfigurationMainRequestOnly.fieldNames[FrmLeaveConfigurationMainRequestOnly.FRM_FIELD_PLUS_NOTE_APPROVALL]+"\" value=\"" + leaveConfigurationMainRequestLeaveOnly.getPlusNoteApprovall() + "\" size=\"20\" class=\"elemenForm\">");
                         rowx.add(objLeaveConfigurationMainRequestLeaveOnly.getStrTable(oidSelectEmpId,listDepartment,listSection,leaveConfigurationDetailDepartementRequestOnly,leaveConfigurationDetailPositionRequestOnly,listPosition,source));
                    }
                    

                    lstLinkData.add(String.valueOf(leaveConfigurationMainRequestLeaveOnly.getOID()));  

                } else {
                    //jika sdh selesai save maka akan kesini
                    rowx.add(leaveConfigurationMainRequestLeaveOnly.getNamaEmployee() + " <B>" + leaveConfigurationMainRequestLeaveOnly.getPlusNoteApprovall()+"</B>");
                    rowx.add(leaveConfigurationMainRequestLeaveOnly.getStrTableAfterSave(leaveConfigurationDetailDepartementRequestOnly,leaveConfigurationDetailPositionRequestOnly)); 
 
                    lstLinkData.add(String.valueOf(leaveConfigurationMainRequestLeaveOnly.getOID()));
                }
                lstData.add(rowx);
                //noCounter = (noCounter+1); 
            }
        }
        rowx = new Vector();

        if (adaKlikEdit &&   (iCommand == Command.GOTO ||   iCommand == Command.ADD || (iCommand == Command.SAVE && frmLeaveConfigurationMainRequestOnly.errorSize() > 0) || (objectClass.size() < 1))) {
            rowx.add("" + noCounter);
            objLeaveConfigurationMainRequestLeaveOnly.setEmployeeId(oidSelectEmpId); 
            //LeaveConfigurationDetailDepartement objLeaveConfigurationDetailDepartement = new LeaveConfigurationDetailDepartement();
            //LeaveConfigurationDetailPosition objLeaveConfigurationDetailPosition = new LeaveConfigurationDetailPosition();
            noCounter++;

            rowx.add(ControlCombo.draw(FrmLeaveConfigurationMainRequestOnly.fieldNames[FrmLeaveConfigurationMainRequestOnly.FRM_FIELD_EMPLOYEE_ID], "chosen-select", "-Selected-", ""+objLeaveConfigurationMainRequestLeaveOnly.getEmployeeId(), empKey,empValue,null, attTag)+"<input type=\"text\" name=\""+FrmLeaveConfigurationMainRequestOnly.fieldNames[FrmLeaveConfigurationMainRequestOnly.FRM_FIELD_PLUS_NOTE_APPROVALL]+"\" value=\"" + objLeaveConfigurationMainRequestLeaveOnly.getPlusNoteApprovall() + "\" size=\"20\" class=\"elemenForm\">");
            rowx.add(objLeaveConfigurationMainRequestLeaveOnly.getStrTable(objLeaveConfigurationMainRequestLeaveOnly.getEmployeeId(),listDepartment,listSection,objLeaveConfigurationDetailDepartementRequestOnly,objLeaveConfigurationDetailPositionRequestOnly,listPosition,source));
            lstLinkData.add(String.valueOf(objLeaveConfigurationMainRequestLeaveOnly.getOID()));
            noCounter = (noCounter + 1);
        }
        lstData.add(rowx);
        return ctrlist.draw();
    }

%>
<%
    CtrlLeaveConfigurationMain ctrlLeaveConfigurationMain = new CtrlLeaveConfigurationMain(request);
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    int iCommand = FRMQueryString.requestCommand(request);
    long oidLeaveConfigMain = FRMQueryString.requestLong(request, FrmLeaveConfigurationMain.fieldNames[FrmLeaveConfigurationMain.FRM_FIELD_LEAVE_CONFIGURATION_MAIN_ID]);
    long oidEmployee = FRMQueryString.requestLong(request, FrmLeaveConfigurationMain.fieldNames[FrmLeaveConfigurationMain.FRM_FIELD_EMPLOYEE_ID]);
    long oidDepartment = FRMQueryString.requestLong(request, FrmLeaveConfigurationDepartment.fieldNames[FrmLeaveConfigurationDepartment.FRM_FIELD_DEPARTEMENT_ID]);
    int start = FRMQueryString.requestInt(request, "start");
    String source = FRMQueryString.requestString(request, "source");
    
    //untuk yg leave request only
    CtrlLeaveConfigurationMainRequestOnly ctrlLeaveConfigurationMainRequestOnly = new CtrlLeaveConfigurationMainRequestOnly(request);
    int prevCommandRequestOnly = FRMQueryString.requestInt(request, "prev_command_req_only");
    int iCommandReqOnly = FRMQueryString.requestInt(request, "command_req_only"); 
    long oidLeaveConfigMainRequestOnly = FRMQueryString.requestLong(request, FrmLeaveConfigurationMainRequestOnly.fieldNames[FrmLeaveConfigurationMainRequestOnly.FRM_FIELD_LEAVE_CONFIGURATION_MAIN_ID_REQUEST_ONLY]);
    //long oidEmployeeRequestOnly = FRMQueryString.requestLong(request, FrmLeaveConfigurationMainRequestOnly.fieldNames[FrmLeaveConfigurationMainRequestOnly.FRM_FIELD_EMPLOYEE_ID]);
    long oidDepartmentRequestOnly = FRMQueryString.requestLong(request, FrmLeaveConfigurationDepartmentRequestOnly.fieldNames[FrmLeaveConfigurationDepartmentRequestOnly.FRM_FIELD_DEPARTEMENT_ID]);
    String source_req_only = FRMQueryString.requestString(request, "source_req_only");
    int start_req_only = FRMQueryString.requestInt(request, "start_req_only");
    
    int posType =0;
    if(oidEmployee!=0){
        try{
         Employee employee =PstEmployee.fetchExc(oidEmployee);
         Position position = PstPosition.fetchExc(employee.getPositionId());
         posType = position.getPositionLevel();
         //position.getPosition();
        }catch(Exception exc){
            System.out.println(exc);
        }
    }
    // update by satrya 2014-07-06 boolean isDirectorAll = posType == PstPosition.LEVEL_DIRECTOR ? true : false; 
    boolean isDirectorAll=true;
    int iErrCode = FRMMessage.ERR_NONE;
    String msgString = "";
    ControlLine ctrLine = new ControlLine();
    iErrCode = ctrlLeaveConfigurationMain.action(iCommand, oidLeaveConfigMain);
    msgString = ctrlLeaveConfigurationMain.getMessage();
    FrmLeaveConfigurationMain frmLeaveConfigurationMain = ctrlLeaveConfigurationMain.getForm();
    LeaveConfigurationMain leaveConfigurationMain = ctrlLeaveConfigurationMain.getLeaveConfigurationMain();
    
    LeaveConfigurationDetailDepartement leaveConfigurationDetailDepartement = ctrlLeaveConfigurationMain.getLeaveConfigurationDetailDepartment();
    LeaveConfigurationDetailPosition leaveConfigurationDetailPosition = ctrlLeaveConfigurationMain.getLeaveConfigurationDetailPosition();

    /*variable declaration*/
    int recordToGet = 5;
    String whereClause = "";

    String orderClause = PstLeaveConfigurationMain.fieldNames[PstLeaveConfigurationMain.FLD_EMPLOYEE_ID];
    Vector listLeaveConfigMain = new Vector(1, 1);


    /* end switch*/

    /*count list All Position*/
    int vectSize = PstLeaveConfigurationMain.getCount(whereClause);

    if ((iCommand == Command.FIRST || iCommand == Command.PREV)
            || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
        start = ctrlLeaveConfigurationMain.actionList(iCommand, start, vectSize, recordToGet);
    }
    /* end switch list*/

    /* get record to display */
    listLeaveConfigMain = PstLeaveConfigurationMain.listJoinEmployee(start, recordToGet, whereClause, orderClause);

    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listLeaveConfigMain.size() < 1 && start > 0) {
        if (vectSize - recordToGet > recordToGet) {
            start = start - recordToGet;   //go to Command.PREV
        } else {
            start = 0;
            iCommand = Command.FIRST;
            prevCommand = Command.FIRST; //go to Command.FIRST
        }
        listLeaveConfigMain = PstLeaveConfigurationMain.listJoinEmployee(start, recordToGet, whereClause, orderClause);
    }
    
    
    ////////////////////////////////////
    //untuk request only
   
    boolean isOpenAllDepartement = true; 
    
    int iErrCodeRequestOnly = FRMMessage.ERR_NONE;
    String msgStringRequestOnly = "";
    ControlLine ctrLineRequestOnly = new ControlLine();
    iErrCodeRequestOnly = ctrlLeaveConfigurationMainRequestOnly.action(iCommandReqOnly, oidLeaveConfigMainRequestOnly);
    msgStringRequestOnly = ctrlLeaveConfigurationMainRequestOnly.getMessage();
    FrmLeaveConfigurationMainRequestOnly frmLeaveConfigurationMainRequestOnly = ctrlLeaveConfigurationMainRequestOnly.getForm();
    LeaveConfigurationMainRequestLeaveOnly leaveConfigurationMainRequestOnly = ctrlLeaveConfigurationMainRequestOnly.getLeaveConfigurationMain();
    
    LeaveConfigurationDetailDepartementRequestOnly leaveConfigurationDetailDepartementRequestLeaveOnly = ctrlLeaveConfigurationMainRequestOnly.getLeaveConfigurationDetailDepartment();
    LeaveConfigurationDetailPositionRequestOnly leaveConfigurationDetailPositionRequestOnly = ctrlLeaveConfigurationMainRequestOnly.getLeaveConfigurationDetailPosition();

  
    String whereClauseRequestOnly = "";

    String orderClauseRequestOnly = PstLeaveConfigurationMainRequestOnly.fieldNames[PstLeaveConfigurationMainRequestOnly.FLD_EMPLOYEE_ID];
    Vector listLeaveConfigMainRequestOnly = new Vector(1, 1);


    /* end switch*/

    /*count list All Position*/
    int vectSizeRequestOnly = PstLeaveConfigurationMainRequestOnly.getCount(whereClauseRequestOnly);

    if ((iCommandReqOnly == Command.FIRST || iCommandReqOnly == Command.PREV)
            || (iCommandReqOnly == Command.NEXT || iCommandReqOnly == Command.LAST)) {
        start_req_only = ctrlLeaveConfigurationMainRequestOnly.actionList(iCommandReqOnly, start_req_only, vectSizeRequestOnly, recordToGet);
    }
    /* end switch list*/

    /* get record to display */
    listLeaveConfigMainRequestOnly = PstLeaveConfigurationMainRequestOnly.listJoinEmployee(start_req_only, recordToGet, whereClauseRequestOnly, orderClauseRequestOnly);

    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listLeaveConfigMainRequestOnly.size() < 1 && start_req_only > 0) {
        if (vectSizeRequestOnly - recordToGet > recordToGet) {
            start_req_only = start_req_only - recordToGet;   //go to Command.PREV
        } else {
            start_req_only = 0;
            iCommandReqOnly = Command.FIRST;
            prevCommandRequestOnly = Command.FIRST; //go to Command.FIRST
        }
        listLeaveConfigMainRequestOnly = PstLeaveConfigurationMainRequestOnly.listJoinEmployee(start_req_only, recordToGet, whereClauseRequestOnly, orderClauseRequestOnly);
    }
%>
<%
    Vector levelKey = new Vector(1,1);
    Vector levelValue = new Vector(1,1);
    for(int idx=0; idx < PstPosition.strPositionLevelNames.length;idx++){																							
         levelKey.add(PstPosition.strPositionLevelNames[idx]);
        levelValue.add(PstPosition.strPositionLevelValue[idx]);														
    }
    Vector listPosition = new Vector();
    listPosition.add(levelValue);
    listPosition.add(levelKey);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
    <head>
        <script src="../javascripts/jquery.min-1.6.2.js" type="text/javascript"></script>
<script src="../javascripts/chosen.jquery.js" type="text/javascript"></script>
        <title>Harisma - Konfigurasi Leave</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
<link rel="stylesheet" href="../styles/main.css" type="text/css">
    <link rel="stylesheet" href="../styles/tab.css" type="text/css">
 <link rel="stylesheet" href="../stylesheets/chosen.css" >
        <SCRIPT language=JavaScript>
            
            function cmdEdit(oidLeaveMain){
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.<%=FrmLeaveConfigurationMain.fieldNames[FrmLeaveConfigurationMain.FRM_FIELD_LEAVE_CONFIGURATION_MAIN_ID]%>.value=oidLeaveMain;
                
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.command.value="<%=Command.EDIT%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.prev_command.value="<%=prevCommand%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.action="leave_setting.jsp";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.target = "";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.submit();
            }
            function cmdEditLeaveRequestOnly(oidLeaveMain){
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.<%=FrmLeaveConfigurationMainRequestOnly.fieldNames[FrmLeaveConfigurationMainRequestOnly.FRM_FIELD_LEAVE_CONFIGURATION_MAIN_ID_REQUEST_ONLY]%>.value=oidLeaveMain;
                
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.command_req_only.value="<%=Command.EDIT%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.prev_command_req_only.value="<%=prevCommand%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.action="leave_setting.jsp";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.target = "";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.submit();
            }
            
            function cmdAdd(){
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.command.value="<%=Command.ADD%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.<%=FrmLeaveConfigurationMain.fieldNames[FrmLeaveConfigurationMain.FRM_FIELD_LEAVE_CONFIGURATION_MAIN_ID]%>.value=0;
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.prev_command.value="<%=prevCommand%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.action="leave_setting.jsp";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.target = "";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.submit();
            }
            
            function cmdAddLeaveRequestOnly(){
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.command_req_only.value="<%=Command.ADD%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.<%=FrmLeaveConfigurationMainRequestOnly.fieldNames[FrmLeaveConfigurationMainRequestOnly.FRM_FIELD_LEAVE_CONFIGURATION_MAIN_ID_REQUEST_ONLY]%>.value=0;
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.prev_command_req_only.value="<%=prevCommandRequestOnly%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.action="leave_setting.jsp";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.target = "";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.submit();
            }
            
            function cmdSave(){
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.command.value="<%=Command.SAVE%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.prev_command.value="<%=prevCommand%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.action="leave_setting.jsp";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.target = "";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.submit();
            }
            
            function cmdSaveLeaveRequestOnly(){
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.command_req_only.value="<%=Command.SAVE%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.prev_command_req_only.value="<%=prevCommandRequestOnly%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.action="leave_setting.jsp";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.target = "";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.submit();
            }
            
            function cmdBack(){
               
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.command.value="<%=Command.BACK%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.action="leave_setting.jsp";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.submit();
            }
            
            function cmdBackLeaveRequestOnly(){
               
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.command_req_only.value="<%=Command.BACK%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.action="leave_setting.jsp";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.submit();
            }
            
            function cmdUpdateDiv(source){
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.source.value=source;
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.action="leave_setting.jsp";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.target = "";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.submit();
            }
            
            function cmdUpdateDivLeaveRequestOnly(source_req_only){
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.command_req_only.value="<%=String.valueOf(Command.GOTO)%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.source_req_only.value=source_req_only;
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.action="leave_setting.jsp";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.target = "";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.submit();
            }
            
             function cmdUpdateDep(){
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.command.value="<%=String.valueOf(Command.GOTO)%>";
                
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.action="leave_setting.jsp";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.target = "";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.submit();
            }
          // function cmdUpdatePosLeaveRequestOnly(oid){
          //      document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.command_req_only.value="<%=String.valueOf(Command.GOTO)%>";
                
          //      document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.action="leave_setting.jsp";
          //      document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.target = "";
          //      document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.submit();
               
           //}
            
            function cmdUpdateDepLeaveRequestOnly(){
               
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.command_req_only.value="<%=String.valueOf(Command.GOTO)%>";
                
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.action="leave_setting.jsp";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.target = "";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.submit();
            }
            
            function cmdListFirst(){
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.command.value="<%=Command.FIRST%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.prev_command.value="<%=Command.FIRST%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.action="leave_setting.jsp";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.target = "";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.submit();
            }
            
            function cmdListFirstLeaveRequestOnly(){
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.command_req_only.value="<%=Command.FIRST%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.prev_command_req_only.value="<%=Command.FIRST%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.action="leave_setting.jsp";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.target = "";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.submit();
            }
            
            function cmdListPrev(){
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.command.value="<%=Command.PREV%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.prev_command.value="<%=Command.PREV%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.action="leave_setting.jsp";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.target = "";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.submit();
            }
            
             function cmdListPrevLeaveRequestOnly(){
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.command_req_only.value="<%=Command.PREV%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.prev_command_req_only.value="<%=Command.PREV%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.action="leave_setting.jsp";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.target = "";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.submit();
            }
            function cmdListNext(){
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.command.value="<%=Command.NEXT%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.prev_command.value="<%=Command.NEXT%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.action="leave_setting.jsp";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.target = "";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.submit();
            }
            
            function cmdListNextLeaveRequestOnly(){
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.command_req_only.value="<%=Command.NEXT%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.prev_command_req_only.value="<%=Command.NEXT%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.action="leave_setting.jsp";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.target = "";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.submit();
            }
            
            function cmdListLast(){
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.command.value="<%=Command.LAST%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.prev_command.value="<%=Command.LAST%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.action="leave_setting.jsp";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.target = "";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.submit();
            }
            
            function cmdListLastLeaveRequestOnly(){
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.command_req_only.value="<%=Command.LAST%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.prev_command_req_only.value="<%=Command.LAST%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.action="leave_setting.jsp";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.target = "";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.submit();
            }
            function cmdAsk(oid){
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.command.value="<%=String.valueOf(Command.ASK)%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.<%=FrmLeaveConfigurationMain.fieldNames[FrmLeaveConfigurationMain.FRM_FIELD_LEAVE_CONFIGURATION_MAIN_ID]%>.value=oid;
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.prev_command.value="<%=String.valueOf(prevCommand)%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.action="leave_setting.jsp";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.target = "";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.submit();
            }
            
            function cmdAskLeaveRequestOnly(oid){
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.command_req_only.value="<%=String.valueOf(Command.ASK)%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.<%=FrmLeaveConfigurationMainRequestOnly.fieldNames[FrmLeaveConfigurationMainRequestOnly.FRM_FIELD_LEAVE_CONFIGURATION_MAIN_ID_REQUEST_ONLY]%>.value=oid;
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.prev_command_req_only.value="<%=String.valueOf(prevCommand)%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.action="leave_setting.jsp";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.target = "";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.submit();
            }
            function cmdConfirmDelete(oid){
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.<%=FrmLeaveConfigurationMain.fieldNames[FrmLeaveConfigurationMain.FRM_FIELD_LEAVE_CONFIGURATION_MAIN_ID]%>.value=oid;
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.command.value="<%=String.valueOf(Command.DELETE)%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.prev_command.value="<%=String.valueOf(prevCommand)%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.action="leave_setting.jsp";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.target = "";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.submit();
            }
            
            function cmdConfirmDeleteLeaveRequestOnly(oid){
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.<%=FrmLeaveConfigurationMainRequestOnly.fieldNames[FrmLeaveConfigurationMainRequestOnly.FRM_FIELD_LEAVE_CONFIGURATION_MAIN_ID_REQUEST_ONLY]%>.value=oid;
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.command_req_only.value="<%=String.valueOf(Command.DELETE)%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.prev_command_req_only.value="<%=String.valueOf(prevCommand)%>";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.action="leave_setting.jsp";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.target = "";
                document.<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>.submit();
            }
           
            
               
                function fnTrapKD(){
                    //alert(event.keyCode);
                    switch(event.keyCode) {
                        case <%=String.valueOf(LIST_PREV)%>:
                                cmdListPrev();
                            break;
                        case <%=String.valueOf(LIST_NEXT)%>:
                                cmdListNext();
                            break;
                        case <%=String.valueOf(LIST_FIRST)%>:
                                cmdListFirst();
                            break;
                        case <%=String.valueOf(LIST_LAST)%>:
                                cmdListLast();
                            break;
                        default:
                            break;
                        }
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
        </SCRIPT>
    </head> 

    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../styletemplate/template_header.jsp" %>
            <%} else {%>
            <tr> 
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
                    <!-- #BeginEditable "header" --> 
                    <%@ include file = "../main/header.jsp" %>
                    <!-- #EndEditable --> 
                </td>
            </tr> 
            <tr> 
                <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --><!-- #EndEditable -->  <%@ include file = "../main/mnmain.jsp" %> </td> 
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
                                        <td height="20">
                                            <font color="#FF6600" face="Arial"><strong>
                                                <!-- #BeginEditable "contenttitle" --> 
                                                Master Data &gt; Leave Configuration<!-- #EndEditable -->
                                            </strong></font>
                                        </td>
                                    </tr>
                                    <tr> 
                                        <td>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr> 
                                                    <td  style="background-color:<%=bgColorContent%>; "> 
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                            <tr> 
                                                                <td valign="top"> 
                                                                    <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                                                        <tr> 
                                                                            <td valign="top">
                                                                                <!-- #BeginEditable "content" -->
                                                                                <!--- FORM PERTAMA-->

                                                                                <!--- FORM KEDUA-->
                                                                                <form name="<%=FrmLeaveConfigurationMain.FRM_NAME_LEAVE_CONFIGURATION_MAIN_NAME%>" method="post" action="">
                                                                                    <input type="hidden" name="command" value="">
                                                                                     <input type="hidden" name="prev_command" value="">
                                                                                    <input type="hidden" name="start" value="<%=start%>">
                                                                                    <input type="hidden" name="source" value="">
                                                                                    <input type="hidden" name="command_req_only" value="">
                                                                                    <input type="hidden" name="start_req_only" value="<%=start_req_only%>">
                                                                                    <input type="hidden" name="source_req_only" value="">
                                                                                    <input type="hidden" name="prev_command_req_only" value="">
                                                                                    

                                                                                        
                                                                                    <%if(iCommand==Command.GOTO){%>
                                                                                        <input type="hidden" name="<%=FrmLeaveConfigurationMain.fieldNames[FrmLeaveConfigurationMain.FRM_FIELD_LEAVE_CONFIGURATION_MAIN_ID]%>" value="<%=oidLeaveConfigMain%>"/>
                                                                                    <%}else{%>
                                                                                        <input type="hidden" name="<%=FrmLeaveConfigurationMain.fieldNames[FrmLeaveConfigurationMain.FRM_FIELD_LEAVE_CONFIGURATION_MAIN_ID]%>" value="<%=oidLeaveConfigMain%>">
                                                                                    <%}%>
                                                                                    <%if(iCommandReqOnly==Command.GOTO){%>
                                                                                        <input type="hidden" name="<%=FrmLeaveConfigurationMainRequestOnly.fieldNames[FrmLeaveConfigurationMainRequestOnly.FRM_FIELD_LEAVE_CONFIGURATION_MAIN_ID_REQUEST_ONLY]%>" value="<%=oidLeaveConfigMainRequestOnly%>"/>
                                                                                    <%}else{%>
                                                                                        <input type="hidden" name="<%=FrmLeaveConfigurationMainRequestOnly.fieldNames[FrmLeaveConfigurationMainRequestOnly.FRM_FIELD_LEAVE_CONFIGURATION_MAIN_ID_REQUEST_ONLY]%>" value="<%=oidLeaveConfigMainRequestOnly%>">
                                                                                    <%}%>
                                                                                    <input type="hidden" name="oidDepartment" value="<%=oidDepartment%>">
                                                                                    
                                                                                    <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                        <tr align="left" valign="top"> 
                                                                                            <td height="8"  colspan="3"> 
                                                                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                                    <tr>
                                                                                                        <td width="5">&nbsp;</td>
                                                                                                        <td>&nbsp;</td>
                                                                                                    </tr>
                                                                                                    <tr>
                                                                                                        <td width="100%">
                                                                                                            Leave List Approvall
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                    
                                                                                                   
                                                                                                    <%
                                                                                                        try {
                                                                                                            //if (listPublicLeave!=null && listPublicLeave.size()>0){
%>
                                                                                                    <tr align="left" valign="top"> 
                                                                                                        <td height="22" valign="middle" colspan="3"> 
                                                                                                            <%=drawList(iCommand,start, listLeaveConfigMain,processDependOnUserDept,isDirectorAll,oidEmployee,leaveConfigurationMain.getOID(),oidDepartment,listPosition,leaveConfigurationMain,frmLeaveConfigurationMain,leaveConfigurationDetailDepartement,leaveConfigurationDetailPosition,source)%></td> 
                                                                                                    </tr>
                                                                                                    <% // } 
                                                                                                        } catch (Exception exc) {
                                                                                                            System.out.println(exc);
                                                                                                    }%>
                                                                                                    <tr align="left" valign="top"> 
                                                                                                        <td height="8" align="left" colspan="3" class="command"> 
                                                                                                            <span class="command"> 
                                                                                                                <%
                                                                                                                    int cmd = 0;
                                                                                                                    if ((iCommand == Command.FIRST || iCommand == Command.PREV)
                                                                                                                            || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                                                                                                                        cmd = iCommand;
                                                                                                                    } else {
                                                                                                                        if (iCommand == Command.NONE || prevCommand == Command.NONE) {
                                                                                                                            cmd = Command.FIRST;
                                                                                                                        } else {
                                                                                                                            cmd = prevCommand;
                                                                                                                        }
                                                                                                                    }
                                                                                                                %>
                                                                                                                <% ctrLine.setLocationImg(approot + "/images");
                                                                                                                    ctrLine.initDefault();
                                                                                                                %>
                                                                                                                <%=ctrLine.drawImageListLimit(cmd, vectSize, start, recordToGet)%>                                                  </span> </td>
                                                                                                    </tr>
                                                                                                </table>
                                                                                            </td>
                                                                                            </>
                                                                                        <tr align="left" valign="top"> 
                                                                                            <td height="8" valign="middle" colspan="3"> 
                                                                                                <%if ((iCommand == Command.ADD) || (iCommand == Command.SAVE) && (msgString != null && msgString.length() > 0) || (iCommand == Command.EDIT) || (iCommand == Command.ASK)) {%>

                                                                                                <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                                                    <tr> 
                                                                                                        <td colspan="2"> 
                                                                                                            <%
                                                                                                                ctrLine.setLocationImg(approot + "/images");
                                                                                                                ctrLine.initDefault();
                                                                                                                ctrLine.setTableWidth("80%");
                                                                                                                String scomDel = "javascript:cmdAsk('" + oidLeaveConfigMain + "')";
                                                                                                                String sconDelCom = "javascript:cmdConfirmDelete('" + oidLeaveConfigMain + "')";
                                                                                                                String scancel = "javascript:cmdEdit('" + oidLeaveConfigMain + "')";
                                                                                                                ctrLine.setBackCaption("Back to List Employee Approvall");
                                                                                                                ctrLine.setCommandStyle("buttonlink");
                                                                                                                ctrLine.setBackCaption("Back to List Employee Approvall ");
                                                                                                                ctrLine.setSaveCaption("Save New Employee Approvall");
                                                                                                                ctrLine.setConfirmDelCaption("Yes Delete Employee Approvall");
                                                                                                                ctrLine.setDeleteCaption("Delete Employee Approvall ");
                                                                                                                ctrLine.setAddCaption("Add New Employee Approvall");
                                                                                                                if (privDelete) {
                                                                                                                    ctrLine.setConfirmDelCommand(sconDelCom);
                                                                                                                    ctrLine.setDeleteCommand(scomDel);
                                                                                                                    ctrLine.setEditCommand(scancel);
                                                                                                                } else {
                                                                                                                    ctrLine.setConfirmDelCaption("");
                                                                                                                    ctrLine.setDeleteCaption("");
                                                                                                                    ctrLine.setEditCaption("");
                                                                                                                }

                                                                                                                if (privAdd == false && privUpdate == false) {
                                                                                                                    ctrLine.setSaveCaption("");
                                                                                                                }

                                                                                                                if (privAdd == false) {
                                                                                                                    ctrLine.setAddCaption("");
                                                                                                                }

                                                                                                                if (iCommand == Command.ASK) {
                                                                                                                    ctrLine.setDeleteQuestion(msgString);
                                                                                                                }
                                                                                                                if (iCommand == Command.SAVE) {
                                                                                                                    ctrLine.setSaveCaption("");
                                                                                                                    ctrLine.setDeleteCaption("");
                                                                                                                    ctrLine.setBackCaption("");
                                                                                                                }
                                                                                                            %>
                                                                                                            <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%> 
                                                                                                        </td>

                                                                                                    </tr>
                                                                                                </table>
                                                                                                <%}%>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <%if (iCommand == Command.BACK || (iCommand == Command.FIRST || iCommand == Command.PREV)
                                                    || (iCommand == Command.NEXT || iCommand == Command.LAST) || iCommand == Command.DELETE) {%>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <table width="100%">

                                                                                                    <tr>
                                                                                                        <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                        <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                                                                        <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                        <td height="22"> 
                                                                                                            <a href="javascript:cmdAdd()" class="command">Add New Employee Approvall</a> 
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                </table>
                                                                                            </td>

                                                                                        </tr>
                                                                                        <%}%>
                                                                                         <%if (iCommand == Command.NONE || iCommand == Command.GOTO) {%>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <%if(iCommand == Command.GOTO || listLeaveConfigMain!=null && listLeaveConfigMain.size()==0){%>
                                                                                                <table width="100%">
                                                                                                    <tr>
                                                                                                      <td width="3"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                      <td width="24"><a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Save new data"></a></td>
                                                                                                      <td width="5"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                      <td width="150" nowrap="nowrap"><a href="javascript:cmdSave()" class="command">Save New Employee Approvall</a> </td>
                                                                                                      <td width="24"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back List"></a></td>
                                                                                                      <td width="10"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                      <td width="910" height="22"><a href="javascript:cmdBack()" class="command">Back to List Employee Approvall</a> </td>
                                                                                                    </tr>
                                                                                                  </table>
                                                                                                 <%}else{%>
                                                                                                 <table width="100%">

                                                                                                    <tr>
                                                                                                        <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                        <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                                                                        <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                        <td height="22"> 
                                                                                                            <a href="javascript:cmdAdd()" class="command">Add New Employee Approvall</a> 
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                </table>
                                                                                                 <%}%>
                                                                                            </td>

                                                                                        </tr>
                                                                                        <%}%>
                                                                                    </table>
                                                                                    <!-- end list approvall -->
                                                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                        <tr align="left" valign="top"> 
                                                                                            <td height="8"  colspan="3"> 
                                                                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                                    <tr>
                                                                                                        <td width="5">&nbsp;</td>
                                                                                                        <td>&nbsp;</td>
                                                                                                    </tr>
                                                                                                    <tr>
                                                                                                        <td width="100%">
                                                                                                            Leave List Request Only
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                    
                                                                                                   
                                                                                                    <%
                                                                                                        try {
                                                                                                            //if (listPublicLeave!=null && listPublicLeave.size()>0){
%>
                                                                                                    <tr align="left" valign="top"> 
                                                                                                        <td height="22" valign="middle" colspan="3"> 
                                                                                                            <%=drawListRequestOnly(iCommandReqOnly,start_req_only, listLeaveConfigMainRequestOnly,processDependOnUserDept,isOpenAllDepartement,oidEmployee,leaveConfigurationMainRequestOnly.getOID(),oidDepartmentRequestOnly,listPosition,leaveConfigurationMainRequestOnly,frmLeaveConfigurationMainRequestOnly,leaveConfigurationDetailDepartementRequestLeaveOnly,leaveConfigurationDetailPositionRequestOnly,source_req_only)%></td> 
                                                                                                    </tr>
                                                                                                    <% // } 
                                                                                                        } catch (Exception exc) {
                                                                                                            System.out.println(exc);
                                                                                                    }%>
                                                                                                    <tr align="left" valign="top"> 
                                                                                                        <td height="8" align="left" colspan="3" class="command"> 
                                                                                                            <span class="command"> 
                                                                                                                <%
                                                                                                                     cmd = 0;
                                                                                                                    if ((iCommandReqOnly == Command.FIRST || iCommandReqOnly == Command.PREV)
                                                                                                                            || (iCommandReqOnly == Command.NEXT || iCommandReqOnly == Command.LAST)) {
                                                                                                                        cmd = iCommandReqOnly;
                                                                                                                    } else {
                                                                                                                        if (iCommandReqOnly == Command.NONE || prevCommandRequestOnly == Command.NONE) {
                                                                                                                            cmd = Command.FIRST;
                                                                                                                        } else {
                                                                                                                            cmd = prevCommandRequestOnly;
                                                                                                                        }
                                                                                                                    }
                                                                                                                %>
                                                                                                                <% ctrLineRequestOnly.setLocationImg(approot + "/images");
                                                                                                                    ctrLineRequestOnly.initDefault();
                                                                                                                    ctrLineRequestOnly.setListPrevCommand("javascript:cmdListPrevLeaveRequestOnly()");
                                                                                                                    ctrLineRequestOnly.setListNextCommand("javascript:cmdListNextLeaveRequestOnly()");
                                                                                                                    ctrLineRequestOnly.setListFirstCommand("javascript:cmdListFirstLeaveRequestOnly()");
                                                                                                                    ctrLineRequestOnly.setListLastCommand("javascript:cmdListLastLeaveRequestOnly()");
                                                                                                                    
                                                                                                                %>
                                                                                                                <%=ctrLineRequestOnly.drawImageListLimit(cmd, vectSizeRequestOnly, start_req_only, recordToGet)%></span> </td>
                                                                                                    </tr>
                                                                                                </table>
                                                                                            </td>
                                                                                            </>
                                                                                        <tr align="left" valign="top"> 
                                                                                            <td height="8" valign="middle" colspan="3"> 
                                                                                                <%if ((iCommandReqOnly == Command.ADD) || (iCommandReqOnly == Command.SAVE) && (msgStringRequestOnly != null && msgStringRequestOnly.length() > 0) || (iCommandReqOnly == Command.EDIT) || (iCommandReqOnly == Command.ASK)) {%>

                                                                                                <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                                                    <tr> 
                                                                                                        <td colspan="2"> 
                                                                                                            <%
                                                                                                                ctrLineRequestOnly.setLocationImg(approot + "/images");
                                                                                                                ctrLineRequestOnly.initDefault();
                                                                                                                ctrLineRequestOnly.setTableWidth("80%");
                                                                                                                String scomDel = "javascript:cmdAskLeaveRequestOnly('" + oidLeaveConfigMainRequestOnly + "')";
                                                                                                                String sconDelCom = "javascript:cmdConfirmDeleteLeaveRequestOnly('" + oidLeaveConfigMainRequestOnly + "')";
                                                                                                                String scancel = "javascript:cmdEditLeaveRequestOnly('" + oidLeaveConfigMainRequestOnly + "')";
                                                                                                                String sSave = "javascript:cmdSaveLeaveRequestOnly()";
                                                                                                                String sBack ="javascript:cmdBackLeaveRequestOnly()";
                                                                                                                
                                                                                                                ctrLineRequestOnly.setSaveCommand(sSave);
                                                                                                                ctrLineRequestOnly.setBackCommand(sBack);
                                                                                                                 ctrLineRequestOnly.setAddCommand("javascript:cmdAddLeaveRequestOnly()");
                                                                                                                ctrLineRequestOnly.setBackCaption("Back to List Employee Request");
                                                                                                                ctrLineRequestOnly.setCommandStyle("buttonlink");
                                                                                                                ctrLineRequestOnly.setBackCaption("Back to List Employee Request ");
                                                                                                                ctrLineRequestOnly.setSaveCaption("Save New Employee Request");
                                                                                                                ctrLineRequestOnly.setConfirmDelCaption("Yes Delete Employee Request");
                                                                                                                ctrLineRequestOnly.setDeleteCaption("Delete Employee Request ");
                                                                                                                ctrLineRequestOnly.setAddCaption("Add New Employee Request");
                                                                                                                if (privDelete) {
                                                                                                                    ctrLineRequestOnly.setConfirmDelCommand(sconDelCom);
                                                                                                                    ctrLineRequestOnly.setDeleteCommand(scomDel);
                                                                                                                    ctrLineRequestOnly.setEditCommand(scancel);
                                                                                                                } else {
                                                                                                                    ctrLineRequestOnly.setConfirmDelCaption("");
                                                                                                                    ctrLineRequestOnly.setDeleteCaption("");
                                                                                                                    ctrLineRequestOnly.setEditCaption("");
                                                                                                                }

                                                                                                                if (privAdd == false && privUpdate == false) {
                                                                                                                    ctrLineRequestOnly.setSaveCaption("");
                                                                                                                }

                                                                                                                if (privAdd == false) {
                                                                                                                    ctrLineRequestOnly.setAddCaption("");
                                                                                                                }

                                                                                                                if (iCommandReqOnly == Command.ASK) {
                                                                                                                    ctrLineRequestOnly.setDeleteQuestion(msgStringRequestOnly);
                                                                                                                }
                                                                                                                if (iCommandReqOnly == Command.SAVE) {
                                                                                                                    ctrLineRequestOnly.setSaveCaption("");
                                                                                                                    ctrLineRequestOnly.setDeleteCaption("");
                                                                                                                    ctrLineRequestOnly.setBackCaption("");
                                                                                                                }
                                                                                                            %>
                                                                                                            <%= ctrLineRequestOnly.drawImage(iCommandReqOnly, iErrCodeRequestOnly, msgStringRequestOnly)%> 
                                                                                                        </td>

                                                                                                    </tr>
                                                                                                </table>
                                                                                                <%}%>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <%if (iCommandReqOnly == Command.BACK || (iCommandReqOnly == Command.FIRST || iCommandReqOnly == Command.PREV)
                                                    || (iCommandReqOnly == Command.NEXT || iCommandReqOnly == Command.LAST) || iCommandReqOnly == Command.DELETE) {%>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <table width="100%">

                                                                                                    <tr>
                                                                                                        <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                        <td width="24"><a href="javascript:cmdAddLeaveRequestOnly()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                                                                        <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                        <td height="22"> 
                                                                                                            <a href="javascript:cmdAddLeaveRequestOnly()" class="command">Add New Employee Request Only</a> 
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                </table>
                                                                                            </td>

                                                                                        </tr>
                                                                                        <%}%>
                                                                                         <%if (iCommandReqOnly == Command.NONE || iCommandReqOnly == Command.GOTO) {%>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <%if(iCommandReqOnly == Command.GOTO || listLeaveConfigMainRequestOnly!=null && listLeaveConfigMainRequestOnly.size()==0){%>
                                                                                                <table width="100%">
                                                                                                    <tr>
                                                                                                      <td width="3"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                      <td width="24"><a href="javascript:cmdSaveLeaveRequestOnly()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Save new data"></a></td>
                                                                                                      <td width="5"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                      <td width="150" nowrap="nowrap"><a href="javascript:cmdSaveLeaveRequestOnly()" class="command">Save New Employee Request Only</a> </td>
                                                                                                      <td width="24"><a href="javascript:cmdBackLeaveRequestOnly()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back List"></a></td>
                                                                                                      <td width="10"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                      <td width="910" height="22"><a href="javascript:cmdBackLeaveRequestOnly()" class="command">Back to List Employee Request Only</a> </td>
                                                                                                    </tr>
                                                                                                  </table>
                                                                                                 <%}else{%>
                                                                                                 <table width="100%">

                                                                                                    <tr>
                                                                                                        <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                        <td width="24"><a href="javascript:cmdAddLeaveRequestOnly()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                                                                        <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                        <td height="22"> 
                                                                                                            <a href="javascript:cmdAddLeaveRequestOnly()" class="command">Add New Employee Request Only</a> 
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                </table>
                                                                                                 <%}%>
                                                                                            </td>

                                                                                        </tr>
                                                                                        <%}%>
                                                                                    </table>    
                                                                            </td>
                                                                        </tr>
                                                                    </table>
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
        <%//@include file="../footer.jsp" %>
    </td>

</tr>
<%} else {%>
<tr> 
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
        <%//@ include file = "../main/footer.jsp" %>
        <!-- #EndEditable --> </td>
</tr>
<%}%>
 <script type="text/javascript">
        var config = {
            '.chosen-select'           : {},
            '.chosen-select-deselect'  : {allow_single_deselect:true},
            '.chosen-select-no-single' : {disable_search_threshold:10},
            '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
            '.chosen-select-width'     : {width:"95%"}
        }
        for (var selector in config) {
            $(selector).chosen(config[selector]);
        }
</script>
<script type="text/javascript">
        var configs = {
            '.chosen-selects'           : {},
            '.chosen-select-deselect'  : {allow_single_deselect:true},
            '.chosen-selectno-single' : {disable_search_threshold:10},
            '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
            '.chosen-select-width'     : {width:"95%"}
        }
        for (var selectors in configs) {
            $(selectors).chosen(configs[selectors]);
        }
</script>
</table>
</body>
<!-- #BeginEditable "script" -->
<script language="JavaScript">
            //var oBody = document.body;
            //var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
</script>
<!-- #EndEditable -->
<!-- #EndTemplate --></html>

