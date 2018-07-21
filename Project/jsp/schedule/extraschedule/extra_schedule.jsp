
<%@page import="com.dimata.harisma.entity.attendance.EmpScheduleReport"%>
<%@page import="com.dimata.harisma.entity.attendance.PstEmpSchedule"%>
<%
            /*
             * Page Name  		:  extra_schedule.jsp
             * Created on 		:  [date] [time] AM/PM
             *
             * @author  		: Satrya Ramayu
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
<%@ page import = "java.text.*" %>

<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>

<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<%@ page import = "com.dimata.qdep.entity.*" %>

<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.overtime.*" %>
<%@ page import = "com.dimata.harisma.form.overtime.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.I_Leave" %>
<%@ include file = "../../main/javainit.jsp" %>

<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_EXTRA_SCHEDULE, AppObjInfo.OBJ_EXTRA_SCHEDULE_FORM);%>
<%@ include file = "../../main/checkuser.jsp" %>
<%
    //cek tipe browser, browser detection
    //String userAgent = request.getHeader("User-Agent"); 
    //boolean isMSIE = false;//(userAgent!=null && userAgent.indexOf("MSIE") !=-1); 
%>
<%!
 long hr_department = 0;
 int finalApprovalMinLevel = PstPosition.LEVEL_DIRECTOR;
 int finalApprovalMaxLevel = PstPosition.LEVEL_GENERAL_MANAGER;
 
 
 public void init(){
            try{ hr_department = Long.parseLong(PstSystemProperty.getValueByName("OID_HRD_DEPARTMENT")); } catch(Exception exc){}                        
}
 
I_Leave leaveConfig = null; 
public void jspInit(){
    try{
    leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
    }catch (Exception e){
    System.out.println("Exception : " + e.getMessage());
    }
    }

 
%>

<!-- Jsp Block -->
<%!    public String drawList(int iCommand, Overtime overtime, Vector objectClass, FrmOvertimeDetail frmObject, OvertimeDetail objEntity, long overtimeDetailId, boolean loginByHRD,Hashtable hashCekOverlap) {

        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("Nr.", "3%");
        ctrlist.addHeader("Payroll", "10%");
        ctrlist.addHeader("Name", "15%");
        ctrlist.addHeader("Start Date", "15%");
        ctrlist.addHeader("Start Time", "7%");
        ctrlist.addHeader("End Date", "15%");
        ctrlist.addHeader("End Time", "7%");
        ctrlist.addHeader("Rest(h)", "7%");
        ctrlist.addHeader("Job Desc.", "20%");
        ctrlist.addHeader("Paid w/", "10%");
        ctrlist.addHeader("Allowance", "10%");
        ctrlist.addHeader("Status ", "10%");
        ctrlist.setLinkRow(1);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEditOv('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        Vector rowx = new Vector();
        int index = -1;
        int recordNo = 1;

        //untuk mengambil Status
        Vector obj_status = new Vector(1, 1);
        Vector val_status = new Vector(1, 1);
        Vector key_status = new Vector(1, 1);

        val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_DRAFT));
        key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_DRAFT]);

        val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED));
        key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED]);

        val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_FINAL));
        key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_FINAL]);

        val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_CANCELLED));
        key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_CANCELLED]);

        System.out.println("objectClass" + objectClass.size());

        if (objectClass != null && objectClass.size() > 0) {
            Employee employee = new Employee();
            if(overtime==null){overtime = new Overtime();};

            for (int i = 0; i < objectClass.size(); i++) {
                OvertimeDetail overtimeDetail = (OvertimeDetail) objectClass.get(i);
                
                /*try {
                    overtime = PstOvertime.fetchExc(overtimeDetail.getOvertimeId());
                } catch (Exception e) {
                }*/

                String errorOverlap="";
                if(overtimeDetail.getEmployeeId()!=0 && hashCekOverlap!=null && hashCekOverlap.size()>0 && hashCekOverlap.containsKey(overtimeDetail.getEmployeeId())){
                    ScheduleSymbol scheduleSymbol = (ScheduleSymbol)hashCekOverlap.get(overtimeDetail.getEmployeeId());
                    errorOverlap =   CtrlOvertimeDetail.resultText[CtrlOvertimeDetail.LANGUAGE_FOREIGN][CtrlOvertimeDetail.RSLT_OVERTIME_OVERLAP_THIS_SCHEDULE] +" "+ (scheduleSymbol.getSymbol()!=null ? scheduleSymbol.getSymbol() :"") + " = (" + (scheduleSymbol.getTimeIn()!=null? Formater.formatDate(scheduleSymbol.getTimeIn(), " HH:mm "):"")+" : "+(scheduleSymbol.getTimeOut()!=null ? Formater.formatDate(scheduleSymbol.getTimeOut(), " HH:mm "):"")+")";
                }
                if (overtimeDetailId == overtimeDetail.getOID()) {
                    index = i;
                }

                rowx = new Vector();
                 
                    String startTime = Formater.formatDate(overtimeDetail.getDateFrom(), "HH:mm");
                    String endTime = Formater.formatDate(overtimeDetail.getDateTo(), "HH:mm");
                    if(overtime.getStatusDoc()==I_DocStatus.DOCUMENT_STATUS_DRAFT || ( overtime.getStatusDoc()==I_DocStatus.DOCUMENT_STATUS_PROCEED && loginByHRD )  ){
                        rowx.add("<a href=\"javascript:cmdEditOv('" + String.valueOf(overtimeDetail.getOID()) + "')\">" + String.valueOf(recordNo++) + "</a>");
                       if(errorOverlap!=null && errorOverlap.length()>0){
                        rowx.add("<a title=\""+errorOverlap+"\"  href=\"javascript:cmdEditOv('" + String.valueOf(overtimeDetail.getOID()) + "')\">" + overtimeDetail.getPayroll() +" <font color=\"#FF0000\"> (Overlap!) </font> "+ "</a>");
                       }else{
                           rowx.add("<a href=\"javascript:cmdEditOv('" + String.valueOf(overtimeDetail.getOID()) + "')\">" + overtimeDetail.getPayroll()+ "</a>"); 
                       }
                    } else {
                        rowx.add(""+String.valueOf(recordNo++));
                        if(errorOverlap!=null && errorOverlap.length()>0){
                        rowx.add(""+"<p title=\""+errorOverlap+"\">"+overtimeDetail.getPayroll() +" <font color=\"#FF0000\"> (Overlap!) </font> "+"</p>");                        
                        }else{
                            rowx.add(""+overtimeDetail.getPayroll());                        
                        }
                    }
                    rowx.add(String.valueOf(overtimeDetail.getName()));
                    rowx.add(Formater.formatDate(overtimeDetail.getDateFrom(), "dd-MM-yyyy"));
                    rowx.add(startTime);
                    rowx.add(Formater.formatDate(overtimeDetail.getDateTo(), "dd-MM-yyyy"));
                    rowx.add(endTime);
                    String restStart = overtimeDetail.getRestStart()!=null ? Formater.formatDate(overtimeDetail.getRestStart(), "HH:mm") :""; 
                    String restEnd = overtimeDetail.getRestEnd()!=null ? Formater.formatDate(overtimeDetail.getRestEnd(), "HH:mm") :""; 
                    rowx.add( (overtimeDetail.getRestTimeinHr()<0.001 || overtimeDetail.getRestStart()==null) ? Formater.formatNumber(overtimeDetail.getRestTimeinHr(),"###.###") : ( restStart+"~"+restEnd) );
                    rowx.add(String.valueOf(overtimeDetail.getJobDesk()));
                    rowx.add(String.valueOf(OvertimeDetail.paidByKey[overtimeDetail.getPaidBy()]));
                    rowx.add(Overtime.allowanceType[overtimeDetail.getAllowance()]);
                    rowx.add(I_DocStatus.fieldDocumentStatus[overtimeDetail.getStatus()]);                    
                
                lstData.add(rowx);
          }
        }            
        return ctrlist.draw(index);
    }

%>
<%
boolean ignoreOverlap=false;
         try{
           String sIgnoreOverlap = String.valueOf(PstSystemProperty.getValueByName("OVERTIME_IGNORE_OVERLAP"));
           if(sIgnoreOverlap!=null && sIgnoreOverlap.length()>0){
            ignoreOverlap = Boolean.parseBoolean(sIgnoreOverlap);
           }
        }catch(Exception ex){
            System.out.println("Execption REASON_DUTTY_NO: " + ex);
}
String[][] message = {
        {//"Status Awal adalah <strong>Draft</strong>",
         "Karena kemungkinan masih ada employee yang <strong>overlapping dengan schedule </strong>",
         "Karena kemungkinan ada karyawan yang statusnya lebih tinggi <strong>levelnya dari yang login</strong>, silahkan cek kembali",
         "Karena <strong>Request By </strong> masih kosong",
         "Karena <strong>Approved by </strong> masih kosong",
         "Karena  belum berhak untuk approval overtime , silahkan cek kembali"
         },
         //english
        {//"Fist Status is <strong>Draft</strong>",
         "Because there is still has the possibility that employees overtime's  <strong>overlapping</strong> with <strong>schedule</strong>",
         "Because there is still has <strong>the possibility that overtime from higher level employee requested by lower level employee</strong>,please check again",
         "Because <strong>request by combobox is still Empty</strong>",
         "Because <strong>approved by combobox is still Empty</strong>",
         "Because the employee <strong>doesn't reserve to approve overtime</strong>, please check again"                 
         }
    };
int language=CtrlOvertimeDetail.LANGUAGE_FOREIGN;
//int question1=0;
int question2=0;
int question3=1;
int question4=2;
int question5=3;
int question6=4;
//int question7=6;
%>
<%
            boolean loginByHRD= false;
            if(departmentOid ==hr_department){
                loginByHRD = true;
            }
            String sources = FRMQueryString.requestString(request, "sources");
            int iCommand = FRMQueryString.requestCommand(request);
            int startOt = FRMQueryString.requestInt(request, "startOt");
            int start = FRMQueryString.requestInt(request, "start");
            int prevCommand = FRMQueryString.requestInt(request, "prev_command");
            long oidOvertime = FRMQueryString.requestLong(request, "overtime_oid");
            long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");
            long oidOvt_Employee = FRMQueryString.requestLong(request, "ovtEmployee_oid");
            

            long oidCompany = FRMQueryString.requestLong(request, FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_COMPANY_ID]);
            long oidDivision = FRMQueryString.requestLong(request, FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_DIVISION_ID]);
            long oidDepartment = FRMQueryString.requestLong(request, FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_DEPARTMENT_ID]);
            long oidSection = FRMQueryString.requestLong(request, FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_SECTION_ID]);

            int recordToGet = 600;
            String msgString = "";
            int iErrCode = FRMMessage.NONE;
            String whereClauseOvDtl = PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVERTIME_ID] + " = " + oidOvertime;
            String orderClauseOvDtl = PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM];
            
            String whereClause = "";
            String orderClause = "";

            CtrlOvertime ctrlOvertime = new CtrlOvertime(request);
            ControlLine ctrLine = new ControlLine();
            Vector listOvertimeDetail = new Vector(1, 1);
            Vector listDepartment = PstDepartment.list(0, 0, "", "DEPARTMENT");
            Vector listCompany = PstCompany.list(0, 0, "", "COMPANY");
            int defaultStatusDoc = 0;
            //Vector listSection = new Vector(1, 1);
            int vectSize = PstOvertime.getCount(whereClause);
            
            /*switch statement */
           
            iErrCode = ctrlOvertime.action(iCommand, oidOvertime, oidEmployee, request);
            /* end switch*/
            FrmOvertime frmOvertime = ctrlOvertime.getForm();

            Overtime overtime = ctrlOvertime.getOvertime();
            
            if(overtime==null)
                overtime = new Overtime();
            oidOvertime = overtime.getOID();
            //update by satrya 2013-06-13
            //untuk mencari yg overlaping
            long oidDayOff = 0;         
        try{
            oidDayOff = Long.parseLong(PstSystemProperty.getValueByName("OID_DAY_OFF"));
        }catch(Exception ex){
            System.out.println("Execption OID_DAY_OFF: " + ex.toString());
        }
            Hashtable hashCekOverlap = new Hashtable();
            Vector listOvertimeDetailOverlap = PstOvertimeDetail.list(0, 0, PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVERTIME_ID]+"="+overtime.getOID(),""); 
            if(listOvertimeDetailOverlap!=null && listOvertimeDetailOverlap.size()>0){
                for(int idx=0;idx<listOvertimeDetailOverlap.size();idx++){
                    OvertimeDetail ovd = (OvertimeDetail)listOvertimeDetailOverlap.get(idx);
                 if(ovd!=null && ovd.getDateFrom()!=null && ovd.getDateTo()!=null){
                      //update by satrya 2013-06-12
                    //cek jika ada schedulenya dia overlap dengan plan OT
                    Date fromDate = null;
                    if(ovd.getDateFrom()!=null){
                        fromDate = new Date(ovd.getDateFrom().getTime() - 24*60*60*1000);  
                    }
                    Date toDate = null;
                    if(ovd.getDateTo()!=null){ 
                        toDate = new Date(ovd.getDateTo().getTime() + 24*60*60*1000);  
                    }
                    
                   Vector vListOverlapSchedule = PstEmpSchedule.getListSchedule(fromDate, toDate, ovd.getEmployeeId());
                   EmpScheduleReport empScheduleReport = new EmpScheduleReport();
                  if(vListOverlapSchedule!=null && vListOverlapSchedule.size()>0){
                      empScheduleReport =(EmpScheduleReport)vListOverlapSchedule.get(0);
                  }
                   Hashtable hasOverlapOT = PstOvertime.isScheduleOverlapOvertime(ovd.getEmployeeId(), vListOverlapSchedule, oidDayOff, ovd.getDateFrom(),  ovd.getDateTo(), empScheduleReport); 
                   if(hasOverlapOT!=null && hasOverlapOT.get("true")!=null){ 
                       String sOidSch = (String)hasOverlapOT.get("true");
                      String sOidSchx = sOidSch!=null?sOidSch.split("_")[0]:"0";  
                      
                       long oidSch= Long.parseLong(sOidSchx);
                       ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
                     if(oidSch!=0){
                         scheduleSymbol = PstScheduleSymbol.fetchExc(oidSch); 
                     }
                       
                    String sOidEmp = (String)hasOverlapOT.get("true");
                    String sOidEmpx = sOidEmp!=null?sOidEmp.split("_")[1]:"0";   
                    long oidEmp= Long.parseLong(sOidEmpx);
                    if(oidEmp!=0 && oidSch!=0){ 
                      hashCekOverlap.put(oidEmp, scheduleSymbol); 
                    }
                   
                   }
                 }   
                }
            }
            // Hashtable listOverlap =  PstOvertimeDetail.checkOvertimeDetailVsSchedule(overtime.getOID());
            if(oidCompany!=0){ 
              overtime.setCompanyId(oidCompany);  
            } else {
                oidCompany = overtime.getCompanyId();
            }
            if(oidDivision!=0){ 
              overtime.setDivisionId(oidDivision);
            } else{
                oidDivision = overtime.getDivisionId();
            }
            if(oidDepartment!=0){ 
              overtime.setDepartmentId(oidDepartment);
            } else{
                oidDepartment=overtime.getDepartmentId();
            }
            if(oidSection!=0){ 
              overtime.setSectionId(oidSection);
            } else{
                oidSection = overtime.getSectionId();
            }
            
            msgString = ctrlOvertime.getMessage();

            /*untuk overtime detail*/

            CtrlEmployee ctrlEmployee = new CtrlEmployee(request);
            //iErrCode = ctrlEmployee.action(iCommand, oidEmployee, request);
            //msgString = ctrlEmployee.getMessage();
            FrmEmployee frmEmployee = ctrlEmployee.getForm();

            Employee employee = new Employee();
            try {
                employee = PstEmployee.fetchExc(oidEmployee);
            } catch (Exception exc) {
                employee = new Employee();
            }


            /*switch list Overtime*/
            if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidOvertime == 0)) {
                startOt = PstOvertimeDetail.findLimitStart(overtime.getOID(), recordToGet, whereClauseOvDtl, orderClauseOvDtl);
            }

            /*count list All Overtime*/
            //int vectSize = PstOvertime.getCount(whereClause);

            if ((iCommand == Command.FIRST || iCommand == Command.PREV)
                    || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                startOt = ctrlOvertime.actionList(iCommand, startOt, vectSize, recordToGet);
            }
            /* end switch list*/

            /* get record to display */
            listOvertimeDetail = PstOvertimeDetail.listWithEmployee(0, recordToGet, whereClauseOvDtl, orderClauseOvDtl);


            /*handle condition if size of record to display = 0 and start > 0 	after delete*/
            
            if (listOvertimeDetail.size() < 1 && startOt > 0) {
                if (vectSize - recordToGet > recordToGet) {
                    startOt = startOt - recordToGet;   //go to Command.PREV
                } else {
                    startOt = 0;
                    iCommand = Command.FIRST;
                    prevCommand = Command.FIRST; //go to Command.FIRST
                }
                listOvertimeDetail = PstOvertimeDetail.listWithEmployee(startOt, recordToGet, whereClauseOvDtl, orderClauseOvDtl);
            }

            if (iCommand == Command.GOTO) {
                frmOvertime = new FrmOvertime(request, overtime);
                frmOvertime.requestEntityObject(overtime);
                }
            
            if(overtime.getStatusDoc()!=I_DocStatus.DOCUMENT_STATUS_DRAFT && overtime.getStatusDoc()!=I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED  ){
                privDelete=false;
            }
            
//update by satrya 2013-08-06
boolean cekSudahAdaDetail = overtime!=null?PstOvertimeDetail.checkOIDDetailByOvertrimeId(overtime.getOID()):false;            
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">

<html><!-- #BeginTemplate "/Templates/maintab.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" -->
        <title>Overtime </title>
        
        <script language="JavaScript">
            
            function changeRequester(){
              <% if((emplx.getOID()!=overtime.getRequestId() && overtime.getRequestId()!=0)){ %>
                var reqID= document.frmovertime.<%=((emplx.getOID()!=overtime.getRequestId() && overtime.getRequestId()!=0)
                                             ?"req_id_x":frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_REQ_ID]) %>.value;                                                                                                                                                                            
                document.frmovertime.<%=frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_REQ_ID]%>.value=reqID;
                //update by satrya 2013-04-30
                
               <% }%>
                getRequestTimeOt();
                document.frmovertime.command.value="<%=Command.GOTO%>";
                document.frmovertime.action="<%=(approot+"/payroll/overtimeform/overtime.jsp")%>";
                document.frmovertime.submit();
            }

            function changeApproval(){
              <% if((emplx.getOID()!=overtime.getApprovalId() && overtime.getApprovalId()!=0)){ %>
                var appID= document.frmovertime.<%=((emplx.getOID()!=overtime.getApprovalId() && overtime.getApprovalId()!=0)
                                             ?"approval_id_x":frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_APPROVAL_ID]) %>.value;                                                                                                                                                                            
                document.frmovertime.<%=frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_APPROVAL_ID]%>.value=appID; 
                 
               <% }%>
                   getRequestTimeOt();
                getApproveTimeOt();
                   document.frmovertime.command.value="<%=Command.GOTO%>";
                document.frmovertime.action="<%=(approot+"/payroll/overtimeform/overtime.jsp")%>";
                document.frmovertime.submit();
            }

            function changeAcknowlege(){
              <% if((emplx.getOID()!=overtime.getAckId() && overtime.getAckId()!=0)){ %>
                var ackID= document.frmovertime.<%=((emplx.getOID()!=overtime.getAckId() && overtime.getAckId()!=0)
                                             ?"acknowlege_id_x":frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_ACK_ID]) %>.value;                                                                                                                                                                            
                document.frmovertime.<%=frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_ACK_ID]%>.value=ackID; 
                 
               <% }%>
                   getRequestTimeOt();
                getApproveTimeOt();
                getAckTimeOt();
                   document.frmovertime.command.value="<%=Command.GOTO%>";
                document.frmovertime.action="<%=(approot+"/payroll/overtimeform/overtime.jsp")%>";
                document.frmovertime.submit();
            }
            
            
            
            function cmdUpdateDiv(){
                document.frmovertime.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmovertime.action="<%=(approot+"/payroll/overtimeform/overtime.jsp")%>";
                document.frmovertime.submit();
            }

            function cmdLink(){
                document.frmovertime.overtime_oid.value="0";
                document.frmovertime.command.value="<%=Command.ADD%>";
                document.frmovertime.prev_command.value="<%=Command.ADD%>";
                document.frmovertime.action="<%=(approot+"/payroll/overtimeform/overtime.jsp")%>";
                document.frmovertime.submit();
            }
            
            function checkValueCostDepartment(){
                var valcost = document.frmovertime.<%=FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_COST_DEP_ID ] %>.value;
                if(valcost==-1){
                    alert("Please select department, you have selected company name");
                }
                if(valcost==-2){
                    alert("Please select department, you have selected division name");
                }
            }
            
            function cmdAdd(){
                document.frmovertime.overtime_oid.value="0";
                document.frmovertime.command.value="<%=Command.ADD%>";
                document.frmovertime.prev_command.value="<%=Command.ADD%>";
                document.frmovertime.action="<%=(approot+"/payroll/overtimeform/overtime.jsp")%>";
                document.frmovertime.submit();
            }

            function cmdAsk(oidOvertime){
                document.frmovertime.overtime_oid.value=oidOvertime;
                document.frmovertime.command.value="<%=Command.ASK%>";
                document.frmovertime.prev_command.value="<%=prevCommand%>";
                document.frmovertime.action="<%=(approot+"/payroll/overtimeform/overtime.jsp")%>";
                document.frmovertime.submit();
            }

            function cmdConfirmDelete(oidOvertime){
                document.frmovertime.overtime_oid.value=oidOvertime;
                document.frmovertime.command.value="<%=Command.DELETE%>";
                document.frmovertime.prev_command.value="<%=prevCommand%>";
                document.frmovertime.action="<%=(approot+"/payroll/overtimeform/overtime.jsp")%>";
                document.frmovertime.submit();
            }
            function cmdSave(){
                getRequestTimeOt();
                getApproveTimeOt();
                getAckTimeOt()
                document.frmovertime.command.value="<%=Command.SAVE%>";
                //document.frmovertime.prev_command.value="<--%=prevCommand--%>";
                document.frmovertime.action="<%=(approot+"/payroll/overtimeform/overtime.jsp")%>";
                document.frmovertime.submit();
            }

            function cmdEdit(oidOvertime){
                document.frmovertime.overtime_oid.value=oidOvertime;
                document.frmovertime.command.value="<%=Command.EDIT%>";
                document.frmovertime.prev_command.value="<%=Command.EDIT%>";
                document.frmovertime.action="<%=(approot+"/payroll/overtimeform/overtime.jsp")%>";
                document.frmovertime.submit();
            }

            function cmdPrint(oidOvertime){
                window.open("<%=(approot+"/payroll/overtimeform/overtime_print.jsp")%>?overtime_oid=<%=overtime.getOID()%>&command="+<%=Command.EDIT%>+
                "&prev_command=<%=Command.EDIT%>", "PrintOvertime", "")
            }

            function cmdCancel(oidOvertime){
                document.frmovertime.overtime_oid.value=oidOvertime;
                document.frmovertime.command.value="<%=Command.EDIT%>";
                document.frmovertime.prev_command.value="<%=prevCommand%>";
                document.frmovertime.action="<%=(approot+"/payroll/overtimeform/overtime.jsp")%>";
                document.frmovertime.submit();
            }

            function cmdBack(){
                document.frmovertime.command.value="<%=Command.BACK%>";
                document.frmovertime.action="<%=(approot+"/payroll/overtimeform/overtime_list.jsp")%>";
                //document.frmovertime.start.value="<%//=start%>";
                document.frmovertime.submit();
            }
            
            function cmdBackSearch(){
		document.frmovertime.command.value="<%=Command.BACK%>";
		document.frmovertime.action="<%=(approot+"/payroll/overtimeform/src_overtime.jsp")%>";
		document.frmovertime.submit();
            }

            function cmdUpdateSection(){
                document.frmovertime.command.value="<%= Command.GOTO%>";
                document.frmovertime.prev_command.value="<%= prevCommand%>";
                document.frmovertime.action="<%=(approot+"/payroll/overtimeform/overtime.jsp")%>";
                document.frmovertime.submit();
            }

            function cmdBackEmp(empOID){
                document.frmovertime.employee_oid.value=empOID;
                document.frmovertime.command.value="<%=Command.EDIT%>";
                document.frmovertime.action="<%=(approot+"/payroll/overtimeform/overtime.jsp")%>";
                document.frmovertime.submit();
            }


            function cmdListFirst(){
                document.frmovertime.command.value="<%=Command.FIRST%>";
                document.frmovertime.prev_command.value="<%=Command.FIRST%>";
                document.frmovertime.action="<%=(approot+"/payroll/overtimeform/overtime.jsp")%>";
                document.frmovertime.submit();
            }

            function cmdListPrev(){
                document.frmovertime.command.value="<%=Command.PREV%>";
                document.frmovertime.prev_command.value="<%=Command.PREV%>";
                document.frmovertime.action="<%=(approot+"/payroll/overtimeform/overtime.jsp")%>";
                document.frmovertime.submit();
            }

            function cmdListNext(){
                document.frmovertime.command.value="<%=Command.NEXT%>";
                document.frmovertime.prev_command.value="<%=Command.NEXT%>";
                document.frmovertime.action="<%=(approot+"/payroll/overtimeform/overtime.jsp")%>";
                document.frmovertime.submit();
            }

            function cmdListLast(){
                document.frmovertime.command.value="<%=Command.LAST%>";
                document.frmovertime.prev_command.value="<%=Command.LAST%>";
                document.frmovertime.action="<%=(approot+"/payroll/overtimeform/overtime.jsp")%>";
                document.frmovertime.submit();
            }
            //Function Untuk Overtime Detail
            function cmdAddOv(){
                document.frmovertime.command.value="<%=Command.ADD%>";
                document.frmovertime.action="<%=(approot+"/payroll/overtimeform/ovdetail.jsp")%>";
                document.frmovertime.submit();
            }

            function fnTrapKD(){
                //alert(event.keyCode);
                switch(event.keyCode)         {
                    case <%=LIST_PREV%>:
                            cmdListPrev();
                        break        ;
                    case <%=LIST_NEXT%>:
                            cmdListNext();
                        break        ;
                    case <%=LIST_FIRST%>:
                            cmdListFirst();
                        break        ;
                    case <%=LIST_LAST%>:
                            cmdListLast();
                        break;
                    default:
                        break;
                    }
                }
                
            function cmdEditOv(oidOvertimeDetail){
                document.frmovertime.hidden_overtime_detail_id.value=oidOvertimeDetail;
                document.frmovertime.command.value="<%=Command.EDIT%>";
                document.frmovertime.prev_command.value="<%=prevCommand%>";
                document.frmovertime.action="<%=(approot+"/payroll/overtimeform/ovdetail.jsp")%>";
                document.frmovertime.submit();
            }
            
function getRequestTimeOt(){
    <%=ControlDatePopup.writeDateCaller("frmovertime",FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_TIME_REQUEST_OT])%>
}

function getApproveTimeOt(){
    <%=ControlDatePopup.writeDateCaller("frmovertime",FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_TIME_APPROVAL_OT])%>
}

function getAckTimeOt(){
    <%=ControlDatePopup.writeDateCaller("frmovertime",FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_TIME_ACK_OT])%>
}

function hideObjectForDate(index){
}

function showObjectForDate(){
}
                
        </script>
        <!-- #EndEditable -->
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <!-- #BeginEditable "styles" -->
        <link rel="stylesheet" href="<%=(approot+"/styles/main.css")%>" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "stylestab" -->
        <link rel="stylesheet" href="<%=(approot+"/styles/tab.css")%>" type="text/css">
        <link rel="stylesheet" href="<%=approot%>/styles/calendar.css" type="text/css">
            <!-- untuk menampilkan tooltips -->
	<link type="text/css" rel="stylesheet" href="../../stylesheets/jquery.dropdown.css" />
	<script type="text/javascript" src="../../javascripts/jquery.min.js"></script>
	<script type="text/javascript" src="../../javascripts/jquery.dropdown.js"></script>
      
        <!-- #EndEditable -->
        <!-- #BeginEditable "headerscript" -->
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
        <style type="text/css">
        #content {
 height: auto;
 margin-top: 10px;
 min-height:540px;
}
BODY,TD
{
    font-family: ·L³n¥¿¶ÂÅé, Tahoma, Arial, Verdana;
    font-weight: normal;
    font-size: 12px;
    color: #333333;
}
    </style>
<style type="text/css">
      
            .bdr{border-bottom:2px dotted #0099FF;}
		
		.highlight {
			color: #090;
		}
		
		.example {
			color: #08C;
			cursor: pointer;
			padding: 4px;
			border-radius: 4px;
		}
		
		.example:after {
			font-family: Consolas, Courier New, Arial, sans-serif;
			content: '?';
			margin-left: 6px;
			color: #08C;
		}
		
		.example:hover {
			background: #F2F2F2;
		}
		
		.example.dropdown-open {
			background: #888;
			color: #FFF;
		}
		
		.example.dropdown-open:after {
			color: #FFF;
		}
		
	</style>
    </head>

    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
        <!-- Untuk Calender-->
<%=(ControlDatePopup.writeTable(approot))%>
<!-- End Calender-->
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF">
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
                            <td align="left"><img src="<%=approot%>/images/harismaMenuLine1.jpg" width="100%" height="7"/></td>
                            
                        </tr>
                    </table>
                </td>
            </tr>
                            <%}%>
            <tr>
                <td width="88%" valign="top" align="left">
                    <div id="content">
                    <table width="100%" border="0" cellspacing="3" cellpadding="2">
                        <tr>
                            <td width="100%">
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Employee
                                                    &gt; Overtime<!-- #EndEditable --> </strong></font> </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td valign="top">
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1">
                                                            <tr>
                                                                <td valign="top"> <!-- #BeginEditable "content" -->
                                                                    <form name="frmovertime" method ="post" action="overtime.jsp">
                                                                        <input type="hidden" name="command" value="<%=iCommand%>">
                                                                        <input type="hidden" name="sources" value=""/>
                                                                            
                                                                        <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                                                        <input type="hidden" name="startOt" value="<%=startOt%>">
                                                                        <!-- update by satrya 2013-06-19 -->
                                                                        <input type="hidden" name="start" value="<%=start%>">
                                                                        <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                                        <input type="hidden" name="overtime_oid" value="<%=oidOvertime%>">
                                                                        <input type="hidden" name="department_oid" value="<%=oidDepartment%>">
                                                                        <input type="hidden" name="employee_oid" value="<%=oidEmployee%>">
                                                                        <input type="hidden" name="hidden_overtime_detail_id" value="0">
                                                                        

                                                                        <input type="hidden" name="ovtEmployee_oid" value="<%=oidOvt_Employee%>">


                                                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                            <tr>
                                                                                <td  style="background-color:<%=bgColorContent%>; ">
                                                                                    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                                                        <tr>
                                                                                            <td valign="top">
		<table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="0" cellpadding="0" class="tabbg">
			<tr align="left" valign="top">
				<td height="8" valign="middle" colspan="3">

					<table width="100%" border="0" cellspacing="2" cellpadding="2">
						<tr>
							<td colspan="2"><b class="listtitle">Overtime Editor</b></td>
						</tr>
						<tr>
							<td width="100%" colspan="2">
            <table width="100%" cellspacing="1" cellpadding="1">
                    <tr>
                            <td width="100" height="20">Req. Date</td>
                            <td width="400" height="20"> <%--=ControlDate.drawDateWithStyle(frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_REQ_DATE], new Date(), 5, -35, "formElemen")--%>
                                    <%=ControlDate.drawDate(frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_REQ_DATE], overtime.getRequestDate() != null ? overtime.getRequestDate() : new Date(), "formElemen", 1, -5)%>
                                    <%=ControlDate.drawTime(frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_REQ_DATE], overtime.getRequestDate() != null ? overtime.getRequestDate() : new Date(), "formElemen",24, 1, 0)%>
                                    * <%=frmOvertime.getErrorMsg(FrmOvertime.FRM_FIELD_REQ_DATE)%>
                            <td width="100" height="20">Number </td>
                            <td width="400" height="20"> 
                                <%=overtime.getOvertimeNum() %>
                                <input type="hidden" value ="<%=(overtime.getOvertimeNum()) %>" name="<%=frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_OV_NUMBER]%>" /></td>
                    </tr>
                    <tr>
                            <td width="100" height="20">Company</td>
                            <td width="400" height="20"><%
                                                    Vector comp_value = new Vector(1, 1);
                                                    Vector comp_key = new Vector(1, 1);
                                                    comp_value.add("0");
                                                    comp_key.add("select ...");
                                                    Vector listComp = PstCompany.list(0, 0, "", " COMPANY ");
                                                    for (int i = 0; i < listComp.size(); i++) {
                                                            Company comp = (Company) listComp.get(i);
                                                            comp_key.add(comp.getCompany());
                                                            comp_value.add(String.valueOf(comp.getOID()));
                                                    }
                                    %> <%= ControlCombo.draw(frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_COMPANY_ID], "formElemen", null, "" + overtime.getCompanyId(), comp_value, comp_key, "onChange=\"javascript:cmdUpdateDiv()\"")%>*
                                    <%=frmOvertime.getErrorMsg(FrmOvertime.FRM_FIELD_COMPANY_ID)%>
                            </td>
                            <td width="100" height="20">Status Doc.</td>
                            <td width="400" height="20"> 
                                    <%
                                                            Vector obj_status = new Vector(1, 1);
                                                            Vector val_status = new Vector(1, 1);
                                                            Vector key_status = new Vector(1, 1);
                                                            
                if (overtime.getRequestId() != 0) {
                    overtime.setStatusDoc(I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED);
                    val_status.clear();
                    key_status.clear();                    
                    val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED));
                    key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED]);                    
                    if(overtime.getApprovalId()!=0){
                        overtime.setStatusDoc(I_DocStatus.DOCUMENT_STATUS_FINAL);
                        val_status.clear();
                        key_status.clear();                    
                        val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_FINAL));
                        key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_FINAL]);
                        
                        if(overtime.getAckId()!=0){                            
                            overtime.setStatusDoc(I_DocStatus.DOCUMENT_STATUS_PROCEED);
                            val_status.clear();
                            key_status.clear();                    
                            val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_PROCEED));
                            key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_PROCEED]);                                                                                        
                            val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_CLOSED));
                            key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_CLOSED]);                                                                                        
                        } 
                    }
                } else {
                    // jika belum di sign oleh requester maka semua yg proses approval setelah itu akan di set 0 ( di reset )
                    overtime.setApprovalId(0);
                    overtime.setAckId(0);
                    if(overtime.getStatusDoc()!=I_DocStatus.DOCUMENT_STATUS_CANCELLED){
                        overtime.setStatusDoc(I_DocStatus.DOCUMENT_STATUS_DRAFT);
                    }
                    val_status.clear();
                    key_status.clear();
                    val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_DRAFT));                    
                    key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_DRAFT]);

                    val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_CANCELLED));
                    key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_CANCELLED]);                                                                                    
                }                                                            
                                                                                                                                                                                   

                out.println(ControlCombo.draw(FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_STATUS_DOC], null, ""+overtime.getStatusDoc(), val_status, key_status, "tabindex=\"4\"", "formElemen"));
                                    %></td>
                    </tr>
                    <tr>
                            <td width="100" height="20"><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></td>
                            <td width="400" height="20"> <%
                                                    Vector div_value = new Vector(1, 1);
                                                    Vector div_key = new Vector(1, 1);
                                                    div_value.add("0");
                                                    div_key.add("select ...");
                                                    //Vector listDiv = PstDivision.list(0, 0, "", " DIVISION ");
                                                    String strWhere = PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID] + "=" + ( oidCompany==0 ? overtime.getCompanyId() : oidCompany) ;
                                                    Vector listDiv = PstDivision.list(0, 0, strWhere, PstDivision.fieldNames[PstDivision.FLD_DIVISION]);
                                                    for (int i = 0; i < listDiv.size(); i++) {
                                                            Division div = (Division) listDiv.get(i);
                                                            div_key.add(div.getDivision());
                                                            div_value.add(String.valueOf(div.getOID()));
                                                    }
                                    %> <%= ControlCombo.draw(frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_DIVISION_ID], "formElemen", null, "" + overtime.getDivisionId(), div_value, div_key, "onChange=\"javascript:cmdUpdateDiv()\"")%>*
                                   <%=frmOvertime.getErrorMsg(FrmOvertime.FRM_FIELD_DIVISION_ID)%> 
                            </td>
                            <td width="100" height="20"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></td>
                            <td width="400" height="20"><%
                                                    Vector sec_value = new Vector(1, 1);
                                                    Vector sec_key = new Vector(1, 1);
                                                    sec_value.add("0");
                                                    sec_key.add("select ...");
                                                    //Vector listSec = PstSection.list(0, 0, "", " SECTION ");
                                                    String strWhereSec = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + "=" + ( oidDepartment==0? overtime.getDepartmentId(): oidDepartment);
                                                    Vector listSec = PstSection.list(0, 0, strWhereSec, PstSection.fieldNames[PstSection.FLD_SECTION]);
                                                    for (int i = 0; i < listSec.size(); i++) {
                                                            Section sec = (Section) listSec.get(i);
                                                            sec_key.add(sec.getSection());
                                                            sec_value.add(String.valueOf(sec.getOID()));
                                                    }
                                    %> <%= ControlCombo.draw(frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_SECTION_ID], "formElemen", null, "" + overtime.getSectionId(), sec_value, sec_key, " onkeydown=\"javascript:fnTrapKD()\"")%>
                            </td>
                    </tr>
                    <tr>
                            <td width="100" height="20"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                            <td width="400" height="20"><%
                                                    Vector dept_value = new Vector(1, 1);
                                                    Vector dept_key = new Vector(1, 1);
                                                    dept_value.add("0");
                                                    dept_key.add("select ...");
                                                    String strWhereDept = PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + ( oidDivision==0 ? overtime.getDivisionId() :oidDivision);
                                                    Vector listDept = PstDepartment.list(0, 0, strWhereDept, PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);
                                                    //Vector listDept = PstDepartment.list(0, 0, "", " DEPARTMENT ");
                                                    for (int i = 0; i < listDept.size(); i++) {
                                                            Department dept = (Department) listDept.get(i);
                                                            dept_key.add(dept.getDepartment());
                                                            dept_value.add(String.valueOf(dept.getOID()));
                                                    }
                                    %> <%= ControlCombo.draw(frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_DEPARTMENT_ID], "formElemen", null, "" + overtime.getDepartmentId(), dept_value, dept_key, "onChange=\"javascript:cmdUpdateDiv()\"")%>*
                                <%=frmOvertime.getErrorMsg(FrmOvertime.FRM_FIELD_DEPARTMENT_ID)%>     
                            </td>
                            <td width="100" height="20">Cost Center</td>
                            <td width="400" height="20">
                                <%
                                                    Vector cost_value = new Vector(1, 1);
                                                    Vector cost_key = new Vector(1, 1);
                                                    cost_value.add("0");
                                                    cost_key.add("select ...");
                                                    strWhereDept = "";//PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + ( oidDivision==0 ? overtime.getDivisionId() :oidDivision) ;
                                                    Vector listCostDept = PstDepartment.listWithCompanyDiv(0, 0, strWhereDept);
                                                    //Vector listDept = PstDepartment.list(0, 0, "", " DEPARTMENT ");
                                                    String prevCompany="";
                                                    String prevDivision="";
                                                    for (int i = 0; i < listCostDept.size(); i++) {
                                                        Department dept = (Department) listCostDept.get(i);
                                                        if(prevCompany.equals(dept.getCompany())){
                                                           if(prevDivision.equals(dept.getDivision())){
                                                               cost_key.add("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+dept.getDepartment());                                                               
                                                               cost_value.add(String.valueOf(dept.getOID()));
                                                           } else{
                                                               cost_key.add("&nbsp;-"+dept.getDivision()+"-");                                                               
                                                               cost_value.add("-2");
                                                               cost_key.add("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+dept.getDepartment());                                                               
                                                               cost_value.add(String.valueOf(dept.getOID()));
                                                               prevDivision=dept.getDivision();
                                                           }
                                                        } else {
                                                               cost_key.add("-"+dept.getCompany()+"-") ;                                                               
                                                               cost_value.add("-1");                                                                                                                                                                                     
                                                               cost_key.add("&nbsp;-"+dept.getDivision()+"-");                                                                                                                              cost_value.add("-2");                                                                                                                                                                                     
                                                               cost_key.add("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+dept.getDepartment());                                                               
                                                               cost_value.add(String.valueOf(dept.getOID()));
                                                               prevCompany =dept.getCompany();
                                                               prevDivision=dept.getDivision();                                                            
                                                        }
                                                   }                                                  
                                
                            
                                    %> <%= ControlCombo.draw(frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_COST_DEP_ID ], "formElemen", null, "" + overtime.getCostDepartmentId(), cost_value, cost_key, "onChange=\"javascript:checkValueCostDepartment()\"")%>
                            </td>
                    </tr>
                    <tr>
                        <td width="100" valign="top" height="20" rowspan="2">Ov. Objective</td>
                            <td width="400" height="20" colspan="1" rowspan="2"><textarea name="<%=frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_OBJECTIVE]%>" class="elemenForm" cols="35" rows="2"><%= overtime.getObjective()%></textarea> *
                                <%=frmOvertime.getErrorMsg(FrmOvertime.FRM_FIELD_OBJECTIVE)%>     
                            </td>
                            <td width="100" valign="top" height="20" >
                                <% if(loginByHRD &&  cekSudahAdaDetail){ %>
                                 <input type="checkbox" name="<%=frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_ALLOWANCE_DO]%>" value="1"  <%=(overtime.getDoUpdateAllowence()==1?"checked":"")%> />                                
                                 Set allowance
                                <%}%>                                                                
                            </td>
                            <td valign="top" align="left"  >
                            <% if(loginByHRD &&  cekSudahAdaDetail){
                                                    Vector allw_value = new Vector(1, 1);
                                                    Vector allw_key = new Vector(1, 1);
                                                    for (int i = 0; i < Overtime.allowanceType.length ; i++) {                                                           
                                                            allw_value.add(""+ Overtime.allowanceValue[i]);
                                                            allw_key.add(Overtime.allowanceType[i] );
                                                    }
                                    %> <%= ControlCombo.draw(frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_ALLOWANCE], "formElemen", null, "" + overtime.getAllowence(), allw_value, allw_key, "")%>                                    
                                <%} else {%>
                                <input type="hidden" name="<%=frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_ALLOWANCE]%>" value="<%=overtime.getAllowence()%>" />
                                <%}%>                             
                            </td>
                    </tr>                                                                                                                

                     <tr>
                                                    
                            <td width="100" valign="top" height="20"> 
                                <% if(loginByHRD &&  cekSudahAdaDetail){ 
                                    OvertimeDetail aDetailOT = null; 
                                  if(listOvertimeDetail!=null && listOvertimeDetail.size()>0 ) {
                                      aDetailOT=(OvertimeDetail) listOvertimeDetail.get(0);
                                      overtime.setRestTimeStart(aDetailOT.getRestStart());
                                      overtime.setRestTimeHR((float)aDetailOT.getRestTimeinHr());
                                  }
                                  %>                                  
                                 <input type="checkbox" name="<%=frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_REST_TIME_START_DO]%>" value="1" <%=(overtime.getDoRestTimeStart()==1?"checked":"")%> />                                
                                 Set rest time 
                                <%}%>
                            </td>
                            <td valign="top" align="left"  >
                            <% if(loginByHRD &&  cekSudahAdaDetail){ %>
                                    <br> Start 
                                    <%=ControlDate.drawDate(frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_REST_TIME_START], overtime.getRestTimeStart() != null ? overtime.getRestTimeStart() : new Date(), "formElemen", 1, -5)%>
                                    <%=ControlDate.drawTime(frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_REST_TIME_START], overtime.getRestTimeStart() != null ? overtime.getRestTimeStart() : new Date(), "formElemen",24, 1, 0)%>
                                    <%=frmOvertime.getErrorMsg(FrmOvertime.FRM_FIELD_REQ_DATE)%>
                                    duration <input type="text" size="6" maxlength="6" name="<%=frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_REST_TIME_HR]%>"  value="<%=overtime.getRestTimeHR()%>" /> hours
                                <%}else if(!loginByHRD){ 
                                    Date restDt = new Date();//jika overtime.getRestTimeStart() nilainya null
                                    restDt.setHours(0);
                                    restDt.setMinutes(0);
                                    restDt.setSeconds(0);
                                     out.println(ControlDatePopup.writeDateTimeDisabled(frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_REST_TIME_START],(overtime.getRestTimeStart() != null ? overtime.getRestTimeStart() : restDt)));
                                %>                                                                
                                <%}%>
                            </td>
                    </tr>   
                    
                    <tr>
                         <td colspan="4"><hr /></td>
                    </tr>
<tr>
    <td colspan="4">
        <table width="100%">
    <tr>
        <td>
                   
<img src="<%=approot%>/images/attention-icon_x.png"/><span class="example" data-dropdown="#dropdown-1">Why Disable Combobox Request By,Approved By, And Final Approve By</span>
<div id="dropdown-1" class="dropdown dropdown-tip has-icons">
		<div class="dropdown-panel">
			<table  border="0" class="tablecolor">
            <tr>
                <td class="tabbg">
                    <table>
                        <tr>
                            <td>
                                <table width="475px" >
                                   <!-- <tr>
                                        <td width="14" class="bdr" >1)</td>
                                        <td colspan="2" class="bdr"><//%=message[language][question1]%></td>
                                    </tr>-->
                                    <tr >
                                        <td class="bdr">1)</td>
                                        <td width="236" class="bdr"><%=message[language][question2]%> </td>
                                        <td width="343" class="bdr"><img src="../../images/tooltips/Overlap.jpg" width="206" height="54"/></td>
                                    </tr>
                                    <tr>
                                        <td class="bdr">2)</td>
                                        <td colspan="2" class="bdr"><%=message[language][question3]%> </td>

                                    </tr>
                                     <tr >
                                        <td class="bdr">3)</td>
                                        <td width="236" class="bdr"><%=message[language][question4]%> </td>
                                        <td width="343" class="bdr"><img src="../../images/tooltips/request.png" width="206" height="54"/></td>
                                    </tr>
                                    <tr >
                                        <td class="bdr">4)</td>
                                        <td width="236" class="bdr"><%=message[language][question5]%> </td>
                                        <td width="343" class="bdr"><img src="../../images/tooltips/ApproveBy.jpg" width="206" height="54"/></td>

                                    </tr>
                                    <tr>
                                        <td >5)</td>
                                        <td colspan="2" ><%=message[language][question6]%> </td>

                                    </tr>
                              </table>
                            </td>
                        </tr>

                    </table>
                </td>
            </tr>
        </table>
		</div>
	</div>  

        </td>
    </tr>
</table>
    </td>
</tr>

                    <tr>
                            <td colspan="4">
                                <%=drawList(Command.LIST, overtime, listOvertimeDetail, new FrmOvertimeDetail(), new OvertimeDetail(), 0, loginByHRD,hashCekOverlap)%>
                            </td>
                    </tr>
            </table>
							</td>
						</tr>
<tr>
    <td>
<img src="<%=approot%>/images/attention-icon_x.png"/><span class="example" data-dropdown="#dropdown-2">Why Disable Combobox Request By,Approved By, And Final Approve By</span>
<table>
    <tr>
        <td>
                   

<div id="dropdown-2" class="dropdown dropdown-tip has-icons">
		<div class="dropdown-panel">
			<table  border="0" class="tablecolor">
            <tr>
                <td class="tabbg">
                    <table>
                        <tr>
                            <td>
                                <table width="475px" >
                                    <tr>
                                        <td colspan="3" class="bdr" >Rule :</td>
                                        
                                    </tr>
                                    <!--<tr>
                                        <td width="14" class="bdr" >1)</td>
                                        <td colspan="2" class="bdr"><///%=message[language][question1]%></td>
                                    </tr>-->
                                    <tr >
                                        <td class="bdr">1)</td>
                                        <td width="236" class="bdr"><%=message[language][question2]%> </td>
                                        <td width="343" class="bdr"><img src="../../images/tooltips/Overlap.jpg" width="206" height="54"/></td>
                                    </tr>
                                    <tr>
                                        <td class="bdr">2)</td>
                                        <td colspan="2" class="bdr"><%=message[language][question3]%> </td>

                                    </tr>
                                     <tr >
                                        <td class="bdr">3)</td>
                                        <td width="236" class="bdr"><%=message[language][question4]%> </td>
                                        <td width="343" class="bdr"><img src="../../images/tooltips/request.png" width="206" height="54"/></td>
                                    </tr>
                                    <tr >
                                        <td class="bdr">4)</td>
                                        <td width="236" class="bdr"><%=message[language][question5]%> </td>
                                        <td width="343" class="bdr"><img src="../../images/tooltips/ApproveBy.jpg" width="206" height="54"/></td>

                                    </tr>
                                    <tr>
                                        <td class="bdr">5)</td>
                                        <td colspan="2" class="bdr"><%=message[language][question6]%> </td>

                                    </tr>
                              </table>
                            </td>
                        </tr>

                    </table>
                </td>
            </tr>
        </table>
		</div>
	</div>  

        </td>
    </tr>
</table>
    </td>
</tr>
						<tr>
							<td width="100%" height="20">
								<table width="100%" cellspacing="1" cellpadding="1">
									<tr>
										<td width="5%" height="20" ></td>
										<td width="30%" height="20" align="center">Requested by</td>
										<td width="30%" height="20" align="center">Approved by</td>
										<td width="30%" height="20" align="center">Final Approved by</td>
										<td width="5%" height="20" ></td>
									</tr>
									<tr>
										<td width="5%" height="20" ></td>
										<td width="30%" height="20" align="center">
											<%
														Vector req_value = new Vector(1, 1);
														Vector req_key = new Vector(1, 1);
														req_value.add("0");
														req_key.add("select ...");														
                Boolean changeOfReqList = true;//String strWhereReq = PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID ] + "=" + oidDepartment ;
                Vector listReq = leaveConfig.overtimeRequester(overtime.getOID());//oidDepartment!=0 ? PstEmployee.listEmployeeSupervisi( oidDepartment, 0L, PstPosition.LEVEL_SUPERVISOR, PstPosition.LEVEL_MANAGER) : new Vector();                                                                                                                
                //update by satrya 2013-06-11, jika belum ada detail maka tidak muncul yg request
                
                for (int i = 0; i < listReq.size(); i++) {
                        Employee req = (Employee) listReq.get(i);
                       if(( req.getOID()==overtime.getRequestId())){
                          changeOfReqList=false; 
                       }
                        if(req.getOID()==emplx.getOID() || ( req.getOID()==overtime.getRequestId() ) ){
                            req_key.add(req.getFullName());
                            req_value.add(String.valueOf(req.getOID()));
                        }
                }
											%> 
                                                                                        <%= ControlCombo.draw( ((emplx.getOID()!=overtime.getRequestId() && overtime.getRequestId()!=0)
                                             ?"req_id_x":frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_REQ_ID]),
                                        "formElemen", null, "" + overtime.getRequestId(), req_value, req_key, 
                        ((emplx.getOID()!=overtime.getRequestId() &&  !cekSudahAdaDetail || (hashCekOverlap!=null&&hashCekOverlap.size()>0  && ignoreOverlap))?"disabled":"onChange=\"javascript:changeRequester()\"") )%>
                        <!-- ((emplx.getOID()!=overtime.getRequestId() && overtime.getRequestId()!=0 && !changeOfReqList )?"disabled":"onChange=\"javascript:changeRequester()\"") )%>-->
                        <%if(((emplx.getOID()!=overtime.getRequestId() && overtime.getRequestId()!=0 || changeOfReqList))){%>
                          
                        <%
                            String dtFormat = overtime.getTimeReqOt()!=null?Formater.formatDate(overtime.getTimeReqOt(), "MMMM dd, yyyy"):"-";
                            out.println(overtime.getTimeReqOt()==null?"-":" &nbsp; <B>"+dtFormat+"</B> &nbsp; ");
                            out.println(ControlDatePopup.writeDateDisabled(FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_TIME_REQUEST_OT],overtime.getTimeReqOt()));
                        }else{%>  
                           ON  <%=ControlDatePopup.writeDate(frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_TIME_REQUEST_OT],(overtime.getTimeReqOt()==null ? new Date() : overtime.getTimeReqOt()),"getRequestTimeOt()")%>
                        <%}%>
     
                    
                                                                                        <% if((emplx.getOID()!=overtime.getRequestId() && overtime.getRequestId()!=0)){ %>
                                                                                          <input type="hidden" name ="<%=frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_REQ_ID]%>" value="<%=overtime.getRequestId()%>">
                                                                                          
                                                                                        <%}%>
										</td>
										<td width="30%" height="20" align="center">
											<%                      changeOfReqList = true;
														Vector app_value = new Vector(1, 1);
														Vector app_key = new Vector(1, 1);
														app_value.add("0");
														app_key.add("select ...");														                                                                                                                        
														Vector listApp =leaveConfig.overtimeApprover(overtime.getOID());
														for (int i = 0; i < listApp.size(); i++) {
															Employee app = (Employee) listApp.get(i);
                                                                                                                        if(( app.getOID()==overtime.getApprovalId() )){
                                                                                                                           changeOfReqList=false; 
                                                                                                                       }
                                                                                                                        if(app.getOID()==emplx.getOID() || ( app.getOID()==overtime.getApprovalId() ) ){
                                                                                                                            app_key.add(app.getFullName());
                                                                                                                            app_value.add(String.valueOf(app.getOID()));
                                                                                                                        }
														}
											%> <%=ControlCombo.draw( (emplx.getOID()!=overtime.getApprovalId() && overtime.getApprovalId()!=0)?"approval_id_x": frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_APPROVAL_ID], "formElemen", null, "" + overtime.getApprovalId(), app_value, app_key,
                                                                                        ((emplx.getOID()!=overtime.getApprovalId() && overtime.getRequestId()==0  || (hashCekOverlap!=null&&hashCekOverlap.size()>0  && ignoreOverlap))?"disabled":" onChange=\"javascript:changeApproval()\" "))%>
                                                                                        <!-- update by satrya 2013-12-04 ((emplx.getOID()!=overtime.getApprovalId() && overtime.getRequestId()==0  )?"disabled":" onChange=\"javascript:changeApproval()\" "))%>-->
                                                                                        <!-- ((emplx.getOID()!=overtime.getApprovalId() && overtime.getApprovalId()!=0 && !changeOfReqList)?"disabled":" onChange=\"javascript:changeApproval()\" "))%>-->
                                                                                        
                                                                                        
                                                                                                                                                                                     
                        <%if((emplx.getOID()!=overtime.getApprovalId() && overtime.getApprovalId()!=0 || changeOfReqList)){%>
                          
                        <%
                            String dtFormat = overtime.getTimeApproveOt()!=null ?Formater.formatDate(overtime.getTimeApproveOt(), "MMMM dd, yyyy"):"-"; 
                            out.println(overtime.getTimeApproveOt()==null?"-":" &nbsp; <B>"+dtFormat+"</B> &nbsp; ");  
                            out.println(ControlDatePopup.writeDateDisabled(FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_TIME_APPROVAL_OT],overtime.getTimeApproveOt()));
                        }else{ %>
                                ON <%=ControlDatePopup.writeDate(FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_TIME_APPROVAL_OT],(overtime.getTimeApproveOt()==null ? new Date() : overtime.getTimeApproveOt()), "getApproveTimeOt()")%>
                            <%}%>
                         
                         
                            
                                                                                        <% if((emplx.getOID()!=overtime.getApprovalId() && overtime.getApprovalId()!=0)){ %>
                                                                                          <input type="hidden" name ="<%=frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_APPROVAL_ID]%>" value="<%=overtime.getApprovalId()%>">
                                                                                          
                                                                                        <%}%>
										</td>
										<td width="30%" height="20" align="center">
											<%
                                                                                                    Vector ack_value = new Vector(1, 1);
                                                                                                    Vector ack_key = new Vector(1, 1);
                                                                                                    ack_value.add("0");
                                                                                                    ack_key.add("select ...");														                                                                                                                
                                                                                                    changeOfReqList = true;
                                                                                                    Vector listAck =leaveConfig.overtimeFinalApprover(overtime.getOID()) ;// oidDepartment!=0 ? PstEmployee.listEmployeeSupervisi(0, 0, finalApprovalMinLevel, finalApprovalMaxLevel) : new Vector();
                                                                                                    for (int i = 0; i < listAck.size(); i++) {
                                                                                                            Employee ack = (Employee) listAck.get(i);
                                                                                                            if(( ack.getOID()==overtime.getAckId() )){
                                                                                                                  changeOfReqList=false; 
                                                                                                             }
                                                                                                            if(ack.getOID()==emplx.getOID() || ( ack.getOID()==overtime.getAckId() ) ){
                                                                                                                ack_key.add(ack.getFullName());
                                                                                                                ack_value.add(String.valueOf(ack.getOID()));
                                                                                                            }
                                                                                                    }
											%> <%= ControlCombo.draw( (emplx.getOID()!=overtime.getAckId() && overtime.getAckId()!=0)?"acknowlege_id_x": 
                                                                                            frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_ACK_ID], "formElemen", null, "" + overtime.getAckId(), ack_value, ack_key,  
    ((emplx.getOID()!=overtime.getAckId() && overtime.getApprovalId()==0)?"disabled":" onChange=\"javascript:changeAcknowlege()\" "))%>
    <!-- ((emplx.getOID()!=overtime.getAckId() && overtime.getAckId()!=0)?"disabled":" onChange=\"javascript:changeAcknowlege()\" "))%>-->
                                                                                             
                        <%if(((emplx.getOID()!=overtime.getAckId() && overtime.getAckId()!=0 || changeOfReqList ))){%>
                          
                        <%
                            String dtFormat = overtime.getTimeAckOt()!=null ?Formater.formatDate(overtime.getTimeAckOt(), "MMMM dd, yyyy"):"-"; 
                            out.println(overtime.getTimeAckOt()==null?"-":" &nbsp; <B>"+dtFormat+"</B> &nbsp; ");
                             out.println(ControlDatePopup.writeDateDisabled(FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_TIME_ACK_OT],overtime.getTimeAckOt()));
                        }else{%> 
                            ON <%=ControlDatePopup.writeDate(frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_TIME_ACK_OT],(overtime.getTimeAckOt()==null ? null : overtime.getTimeAckOt()), "getAckTimeOt()")%>
                        <%}%>
                        

                       
                         
                                                                                           <% if((emplx.getOID()!=overtime.getAckId() && overtime.getAckId()!=0)){ %>
                                                                                                <input type="hidden" name ="<%=frmOvertime.fieldNames[FrmOvertime.FRM_FIELD_ACK_ID]%>" value="<%=overtime.getAckId()%>">
                                                                                                
                                                                                            <%}%>                                                                                        
										</td>
										<td width="5%" height="20" ></td>
									</tr>
									<tr>
										<td height="20" align="center" valign="middle"></td>
									</tr>
								</table>

							</td>
						</tr>
                                                <%if(iErrCode!=FRMMessage.NONE) {%>
                                                <tr>
                                                    <td style="background-color:#FFFF00 " >
                                                        <img src="<%=approot%>/images/info3.png"><b><%=ctrlOvertime.getMessage() %></b> 
                                                    </td>
                                                </tr>
                                                <% } %>
						<tr align="left" valign="top" >
							<td width="100" height="20">
								<table width="50%" cellspacing="" cellpadding="">
									<tr>
										<td colspan="7">
											<% if ( (iCommand == Command.SAVE || iCommand == Command.EDIT) && (overtime.getOID()!=0) && (overtime.getStatusDoc()==I_DocStatus.DOCUMENT_STATUS_DRAFT ) ) {%>
											<table>
												<tr>
													<td width="10" height="25"><a href="javascript:cmdAddOv()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
													<td width="5" height="25"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
													<td height="30" valign="middle" colspan="3" width="1000"><a href="javascript:cmdAddOv()" class="command">Add Overtime Detail</a></td>
												</tr>
											</table>
											<% }%>
										</td>
									</tr>
									<tr>
        <td width="5" height="25"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
        <td width="10" height="25"><a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
        <td width="5" height="25"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
        <td height="30" valign="middle" colspan="3" width="1000"> 
            <% if( overtime.getStatusDoc()!=I_DocStatus.DOCUMENT_STATUS_CLOSED ) { %>
               <a href="javascript:cmdSave()" class="command">Save</a> 
            <% }  else { %> 
               Status is Closed update not available
            <% } %> </td>
        <td width="5" height="25"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back to List Overtime"></a></td>
        <td width="5" height="25"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
        <td height="30" valign="middle" colspan="3" width="1000"><a href="javascript:cmdBack()" class="command">Back to List Overtime</a></td>
        <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
        <td width="24"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image300','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image300" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back To List"></a></td>
        <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
        <td nowrap="true" nowrap> <a href="javascript:cmdBackSearch()" class="command" >Back To SearchOvertime</a></td>
        <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
        <td width="24"><a href="javascript:cmdPrint()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image300','','<%=approot%>/images/icon/print.jpg',1)"><img name="Image300" border="0" src="<%=approot%>/images/icon/print.jpg" width="24" height="24" alt="Print"></a></td>
        <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
        <td nowrap="true" nowrap> <a href="javascript:cmdPrint()" class="command" >Print Overtime</a></td>
        
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
                                                                                <td>&nbsp; </td>
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
                </div>
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
    <script language="JavaScript">
                var oBody = document.body;
                var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
    </script>
    <!-- #EndEditable -->
    <!-- #EndTemplate --></html>
