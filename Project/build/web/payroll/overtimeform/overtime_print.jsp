
<%
            /*
             * Page Name  		:  overtime.jsp
             * Created on 		:  [date] [time] AM/PM
             *
             * @author  		: wiweka
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

<%@ include file = "../../main/javainit.jsp" %>

<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_OVERTIME, AppObjInfo.OBJ_PAYROLL_OVERTIME_FORM);%>
<%@ include file = "../../main/checkuser.jsp" %>
<%
            /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>

<!-- Jsp Block -->
<%!    public String drawList(int iCommand, Overtime overtime, Vector objectClass, FrmOvertimeDetail frmObject, OvertimeDetail objEntity, long overtimeDetailId) {

        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("No.", "3%");
        ctrlist.addHeader("Payroll", "10%");
        ctrlist.addHeader("Name", "15%");
        ctrlist.addHeader("Start Date", "15%");
        ctrlist.addHeader("Start Time", "7%");
        ctrlist.addHeader("End Date", "15%");
        ctrlist.addHeader("End Time", "7%");
        ctrlist.addHeader("Rest(h)", "7%");
        ctrlist.addHeader("JobDesk", "20%");        
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
        /*Vector obj_status = new Vector(1, 1);
        Vector val_status = new Vector(1, 1);
        Vector key_status = new Vector(1, 1);

        val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_DRAFT));
        key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_DRAFT]);

        val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED));
        key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED]);

        val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_FINAL));
        key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_FINAL]);

        val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_CANCELLED));
        key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_CANCELLED]);*/

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


                if (overtimeDetailId == overtimeDetail.getOID()) {
                    index = i;
                }

                rowx = new Vector();
                 {
                    String startTime = Formater.formatDate(overtimeDetail.getDateFrom(), "HH:mm");
                    String endTime = Formater.formatDate(overtimeDetail.getDateTo(), "HH:mm");

                    rowx.add(""+String.valueOf(recordNo++));
                    rowx.add(""+ overtimeDetail.getPayroll());
                    rowx.add(String.valueOf(overtimeDetail.getName()));
                    rowx.add(Formater.formatDate(overtimeDetail.getDateFrom(), "dd-MM-yyyy"));
                    rowx.add(startTime);
                    rowx.add(Formater.formatDate(overtimeDetail.getDateTo(), "dd-MM-yyyy"));
                    rowx.add(endTime);
                    rowx.add(Formater.formatNumber(overtimeDetail.getRestTimeinHr(),"##.##"));
                    rowx.add(String.valueOf(overtimeDetail.getJobDesk()));
                    rowx.add(String.valueOf(OvertimeDetail.paidByKey[overtimeDetail.getPaidBy()]));
                    rowx.add(Overtime.allowanceType[overtimeDetail.getAllowance()]);
                    String select_status = "" + overtimeDetail.getStatus();
                    if (overtimeDetail.getStatus() == I_DocStatus.DOCUMENT_STATUS_DRAFT) {
                        rowx.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_DRAFT]);
                    } else if (overtimeDetail.getStatus() == I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED) {
                        rowx.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED]);
                    } else if (overtimeDetail.getStatus() == I_DocStatus.DOCUMENT_STATUS_FINAL) {
                        rowx.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_FINAL]);
                    }else if (overtimeDetail.getStatus() == I_DocStatus.DOCUMENT_STATUS_PROCEED) {
                        rowx.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_PROCEED]);
                    }else {
                        rowx.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_CANCELLED]);
                    }

                }
                lstData.add(rowx);
            }
            

        } 
        return ctrlist.draw(index);
    }

%>

<%
            long hr_department = 0;
            try{ hr_department = Long.parseLong(PstSystemProperty.getValueByName("OID_HRD_DEPARTMENT")); } catch(Exception exc){}            
            boolean loginByHRD= false;
            if(departmentOid ==hr_department){
                loginByHRD = true;
            }

            int iCommand = FRMQueryString.requestCommand(request);
            int start = FRMQueryString.requestInt(request, "start");
            int prevCommand = FRMQueryString.requestInt(request, "prev_command");
            long oidOvertime = FRMQueryString.requestLong(request, "overtime_oid");
            long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");
            long oidOvt_Employee = FRMQueryString.requestLong(request, "ovtEmployee_oid");


            long oidCompany = FRMQueryString.requestLong(request, FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_COMPANY_ID]);
            long oidDivision = FRMQueryString.requestLong(request, FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_DIVISION_ID]);
            long oidDepartment = FRMQueryString.requestLong(request, FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_DEPARTMENT_ID]);
            long oidSection = FRMQueryString.requestLong(request, FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_SECTION_ID]);

            int recordToGet = 10000;
            String msgString = "";
            int iErrCode = FRMMessage.NONE;
            String whereClauseOv = PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVERTIME_ID] + " = " + oidOvertime;
            //String orderClauseOv = PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_] + " = " + oidOvertime;;
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
            oidOvertime = overtime.getOID();
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
                start = PstOvertime.findLimitStart(overtime.getOID(), recordToGet, whereClause, orderClause);
            }

            /*count list All Overtime*/
            //int vectSize = PstOvertime.getCount(whereClause);

            if ((iCommand == Command.FIRST || iCommand == Command.PREV)
                    || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                start = ctrlOvertime.actionList(iCommand, start, vectSize, recordToGet);

            }
            /* end switch list*/

            /* get record to display */
            listOvertimeDetail = PstOvertimeDetail.listWithEmployee(start, recordToGet, whereClauseOv, orderClause);


            /*handle condition if size of record to display = 0 and start > 0 	after delete*/
            if (listOvertimeDetail.size() < 1 && start > 0) {
                if (vectSize - recordToGet > recordToGet) {
                    start = start - recordToGet;   //go to Command.PREV
                } else {
                    start = 0;
                    iCommand = Command.FIRST;
                    prevCommand = Command.FIRST; //go to Command.FIRST
                }
                listOvertimeDetail = PstOvertimeDetail.listWithEmployee(start, recordToGet, whereClauseOv, orderClause);
            }

            if (iCommand == Command.GOTO) {
                frmOvertime = new FrmOvertime(request, overtime);
                frmOvertime.requestEntityObject(overtime);
                }
%>
<html><!-- #BeginTemplate "/Templates/maintab.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" -->
        <title>Overtime </title>
        <script language="JavaScript">
            function cmdUpdateDiv(){
                document.frmovertime.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmovertime.action="overtime.jsp";
                document.frmovertime.submit();
            }

            function cmdLink(){
                document.frmovertime.overtime_oid.value="0";
                document.frmovertime.command.value="<%=Command.ADD%>";
                document.frmovertime.prev_command.value="<%=Command.ADD%>";
                document.frmovertime.action="overtime.jsp";
                document.frmovertime.submit();
            }
            
            function cmdAdd(){
                document.frmovertime.overtime_oid.value="0";
                document.frmovertime.command.value="<%=Command.ADD%>";
                document.frmovertime.prev_command.value="<%=Command.ADD%>";
                document.frmovertime.action="overtime.jsp";
                document.frmovertime.submit();
            }

            function cmdAsk(oidOvertime){
                document.frmovertime.overtime_oid.value=oidOvertime;
                document.frmovertime.command.value="<%=Command.ASK%>";
                document.frmovertime.prev_command.value="<%=prevCommand%>";
                document.frmovertime.action="overtime.jsp";
                document.frmovertime.submit();
            }

            function cmdConfirmDelete(oidOvertime){
                document.frmovertime.overtime_oid.value=oidOvertime;
                document.frmovertime.command.value="<%=Command.DELETE%>";
                document.frmovertime.prev_command.value="<%=prevCommand%>";
                document.frmovertime.action="overtime.jsp";
                document.frmovertime.submit();
            }
            function cmdSave(){
                document.frmovertime.command.value="<%=Command.SAVE%>";
                //document.frmovertime.prev_command.value="<--%=prevCommand--%>";
                document.frmovertime.action="overtime.jsp";
                document.frmovertime.submit();
            }

            function cmdEdit(oidOvertime){
                document.frmovertime.overtime_oid.value=oidOvertime;
                document.frmovertime.command.value="<%=Command.EDIT%>";
                document.frmovertime.prev_command.value="<%=Command.EDIT%>";
                document.frmovertime.action="overtime.jsp";
                document.frmovertime.submit();
            }

            function cmdCancel(oidOvertime){
                document.frmovertime.overtime_oid.value=oidOvertime;
                document.frmovertime.command.value="<%=Command.EDIT%>";
                document.frmovertime.prev_command.value="<%=prevCommand%>";
                document.frmovertime.action="overtime.jsp";
                document.frmovertime.submit();
            }

            function cmdBack(){
                document.frmovertime.command.value="<%=Command.BACK%>";
                document.frmovertime.action="overtime_list.jsp";
                document.frmovertime.submit();
            }
            
            function cmdBackSearch(){
		document.frmovertime.command.value="<%=Command.BACK%>";
		document.frmovertime.action="src_overtime.jsp";
		document.frmovertime.submit();
            }

            function cmdUpdateSection(){
                document.frmovertime.command.value="<%= Command.GOTO%>";
                document.frmovertime.prev_command.value="<%= prevCommand%>";
                document.frmovertime.action="overtime.jsp";
                document.frmovertime.submit();
            }

            function cmdBackEmp(empOID){
                document.frmovertime.employee_oid.value=empOID;
                document.frmovertime.command.value="<%=Command.EDIT%>";
                document.frmovertime.action="overtime.jsp";
                document.frmovertime.submit();
            }


            function cmdListFirst(){
                document.frmovertime.command.value="<%=Command.FIRST%>";
                document.frmovertime.prev_command.value="<%=Command.FIRST%>";
                document.frmovertime.action="overtime.jsp";
                document.frmovertime.submit();
            }

            function cmdListPrev(){
                document.frmovertime.command.value="<%=Command.PREV%>";
                document.frmovertime.prev_command.value="<%=Command.PREV%>";
                document.frmovertime.action="overtime.jsp";
                document.frmovertime.submit();
            }

            function cmdListNext(){
                document.frmovertime.command.value="<%=Command.NEXT%>";
                document.frmovertime.prev_command.value="<%=Command.NEXT%>";
                document.frmovertime.action="overtime.jsp";
                document.frmovertime.submit();
            }

            function cmdListLast(){
                document.frmovertime.command.value="<%=Command.LAST%>";
                document.frmovertime.prev_command.value="<%=Command.LAST%>";
                document.frmovertime.action="overtime.jsp";
                document.frmovertime.submit();
            }
            //Function Untuk Overtime Detail
            function cmdAddOv(){
                document.frmovertime.command.value="<%=Command.ADD%>";
                document.frmovertime.action="ovdetail.jsp";
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
                document.frmovertime.action="ovdetail.jsp";
                document.frmovertime.submit();
            }
                
        </script>
        <!-- #EndEditable -->
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <!-- #BeginEditable "styles" -->
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "stylestab" -->
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
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
                                        <td>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td valign="top">
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1">
                                                            <tr>
                                                                <td valign="top"> <!-- #BeginEditable "content" -->
                                                                    <form name="frmovertime" method ="post" action="overtime.jsp">
                                                                        <input type="hidden" name="command" value="<%=iCommand%>">
                                                                        <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                                                        <input type="hidden" name="start" value="<%=start%>">
                                                                        <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                                        <input type="hidden" name="overtime_oid" value="<%=oidOvertime%>">
                                                                        <input type="hidden" name="department_oid" value="<%=oidDepartment%>">
                                                                        <input type="hidden" name="employee_oid" value="<%=oidEmployee%>">
                                                                        <input type="hidden" name="hidden_overtime_detail_id" value="0">
                                                                        

                                                                        <input type="hidden" name="ovtEmployee_oid" value="<%=oidOvt_Employee%>">


                                                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                            <tr>
                                                                                <td class="tablecolor">
                                                                                    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                                                        <tr>
                                                                                            <td valign="top">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tabbg">
			<tr align="left" valign="top">
				<td height="8" valign="middle" colspan="3">

					<table width="100%" border="0" cellspacing="2" cellpadding="2">
						<tr>
							<td colspan="2"><b class="listtitle">Overtime Form</b></td>
						</tr>
						<tr>
							<td width="100%" colspan="2">
            <table width="100%" cellspacing="1" cellpadding="1">
                    <tr>
                            <td width="100" height="20">Req. Date</td>
                            <td width="400" height="20"> 
                                <%=Formater.formatDate(overtime.getRequestDate(), "dd MMMM yyyy") %>                                
                            <td width="100" height="20">No.</td>
                            <td width="400" height="20"> <%=overtime.getOvertimeNum() %></td>
                    </tr>
                    <tr>
                            <td width="100" height="20">Company</td>
                            <td width="400" height="20"><%
                             try{
                                Company company = PstCompany.fetchExc(overtime.getCompanyId());
                                out.println(company.getCompany());
                              }catch(Exception exc){
                                  
                              }
                                %>
                            <td width="100" height="20">Status Doc.</td>
                            <td width="400" height="20"> 
                                    <%=I_DocStatus.fieldDocumentStatus[overtime.getStatusDoc()] %> </td>
                    </tr>
                    <tr>
                            <td width="100" height="20"><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></td>
                            <td width="400" height="20"> <%
                             try{
                                    Division division = PstDivision.fetchExc(overtime.getDivisionId());
                                    out.println(division.getDivision());
                                 } catch(Exception exc){
                                     
                                 }
                             
                             %> </td>
                            <td width="100" height="20"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></td>
                            <td width="400" height="20"><%
                            if(overtime.getSectionId()!=0){
                                try{
                                   Section section = PstSection.fetchExc(overtime.getSectionId()) ;
                                   out.println(section.getSection() );
                                } catch(Exception exc){                                    
                                }
                            } %>
                                </td>
                    </tr>
                    <tr>
                            <td width="100" height="20"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                            <td width="400" height="20"><%
                            if(overtime.getDepartmentId()!=0){
                                try{
                                Department department = PstDepartment.fetchExc(overtime.getDepartmentId());
                                out.println(department.getDepartment());
                                }catch(Exception exc){}
                            } %>
                            </td>
<td width="100" height="20">Cost Center</td>
                <td width="400" height="20">                                                                                                                        
                     <% 
                      if(overtime!=null && overtime.getCostDepartmentId()!=0){
                          try{
                            Department costCenter = PstDepartment.fetchExc(overtime.getCostDepartmentId());
                            out.println(costCenter.getDepartment());
                          } catch(Exception exc){
                              
                          }
                       }                     
                                                                                                                         
                      if (1==0) {%>
                        <!--a href="javascript:cmdLink()" class="command">Link</a-->
                        <table width="100%" cellspacing="1" cellpadding="1">

                                <tr>
                                        <td width="10%" height="20">Customer Task </td>
                                        <td width="15%" height="20"><label>
                                                        <input type="text" name="textfield" id="textfield" disabled="true" />
                                                </label></td>
                                </tr>
                                <tr>
                                        <td width="10%" height="20">Logbook </td>
                                        <td width="15%" height="20"><label>
                                                        <input type="text" name="textfield2" id="textfield2" disabled="true" />
                                                </label></td>
                                </tr>

                        </table><% }%>
                        </td>
                    </tr>
                    <tr>
                            <td width="100" valign="top" height="20">Ov. Objective</td>
                            <td width="400" height="20" ><%= overtime.getObjective()%></td>
                             <td ><%=(loginByHRD?"Allowance ":"&nbsp;")%><br><br>
                                                                                                                    </td>                                                                                                                                                                                                                                
                                      <td> <% if(loginByHRD){
                                                    
                                    %> <%=Overtime.allowanceType[overtime.getAllowence()]%>                                                                
                                    <%}%>
                                 <input type="hidden" name="<%=FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_ALLOWANCE]%>  value="<%=overtime.getAllowence()%>" />

                    </tr>                                                                                                                

                    <tr>
                            <td colspan="4"></td>
                    </tr>
                    <tr>
                            <td colspan="4"><hr /></td>
                    </tr>
                    <tr>
                            <td colspan="4">
                                <%=drawList(Command.LIST, overtime, listOvertimeDetail, new FrmOvertimeDetail(), new OvertimeDetail(), 0)%>
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
										<td width="30%" height="20" align="center">Request by,<br><%=overtime.getTimeReqOt()!=null?Formater.formatDate(overtime.getTimeReqOt(), "MMMM dd, yyyy"):"-"%></td>
										<td width="30%" height="20" align="center">Approved by,<br><%=overtime.getTimeApproveOt()!=null?Formater.formatDate(overtime.getTimeApproveOt(), "MMMM dd, yyyy"):"-"%></td>
										<td width="30%" height="20" align="center">Final Approved by,<br><%=overtime.getTimeAckOt()!=null?Formater.formatDate(overtime.getTimeAckOt(), "MMMM dd, yyyy"):"-"%></td>
										<td width="5%" height="20" ></td>
									</tr>
									<tr>
										<td width="5%" height="20" ></td>
										<td width="30%" height="20" align="center">&nbsp;</td>
										<td width="30%" height="20" align="center">&nbsp;</td>
										<td width="30%" height="20" align="center">&nbsp;</td>
										<td width="5%" height="20" ></td>
									</tr>
									<tr>
										<td width="5%" height="20" ></td>
										<td width="30%" height="20" align="center">&nbsp;</td>
										<td width="30%" height="20" align="center">&nbsp;</td>
										<td width="30%" height="20" align="center">&nbsp;</td>
										<td width="5%" height="20" ></td>
									</tr>
									<tr>
										<td width="5%" height="20" ></td>
										<td width="30%" height="20" align="center">&nbsp;</td>
										<td width="30%" height="20" align="center">&nbsp;</td>
										<td width="30%" height="20" align="center">&nbsp;</td>
										<td width="5%" height="20" ></td>
									</tr>
									<tr>
										<td width="5%" height="20" ></td>
										<td width="30%" height="20" align="center">
											<%
                                                                                          if( overtime!=null && overtime.getRequestId()!=0) {
                                                                                              Employee req = PstEmployee.fetchExc(overtime.getRequestId());
                                                                                              out.println(req.getFullName());
                                                                                              
                                                                                          } else {
                                                                                              out.println("not yet approved");              
                                                                                          }
                                                                                        /*
														Vector req_value = new Vector(1, 1);
														Vector req_key = new Vector(1, 1);
														req_value.add("0");
														req_key.add("select ...");														
														//String strWhereReq = PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID ] + "=" + oidDepartment ;
														Vector listReq = oidDepartment!=0 ? PstEmployee.listEmployeeSupervisi( oidDepartment, 0L, PstPosition.LEVEL_SUPERVISOR, PstPosition.LEVEL_MANAGER) : new Vector();
														for (int i = 0; i < listReq.size(); i++) {
															Employee req = (Employee) listReq.get(i);
                                                                                                                        if(overtime.getRequestId()==req.getOID()){
                                                                                                                            out.println(req.getFullName());
                                                                                                                            break;
                                                                                                                        }															
														}
                                                                                         * */
											%> 
										</td>
										<td width="30%" height="20" align="center">
											<%
                                                                                          if( overtime!=null && overtime.getApprovalId()!=0) {
                                                                                              Employee aprv = PstEmployee.fetchExc(overtime.getApprovalId());
                                                                                              out.println(aprv.getFullName());
                                                                                              
                                                                                          } else {
                                                                                              out.println("not yet approved");              
                                                                                          }
                                                                                        
														/* Vector app_value = new Vector(1, 1);
														Vector app_key = new Vector(1, 1);
														app_value.add("0");
														app_key.add("select ...");														                                                                                                                        
														Vector listApp = oidDepartment!=0 ?  PstEmployee.listEmployeeSupervisi(oidDepartment, 0L, PstPosition.LEVEL_ASST_MANAGER, PstPosition.LEVEL_MANAGER) : new Vector();
														for (int i = 0; i < listApp.size(); i++) {
															Employee app = (Employee) listApp.get(i);
                                                                                                                        if(overtime.getApprovalId()==app.getOID()){
                                                                                                                            out.println(app.getFullName());
                                                                                                                            break;
                                                                                                                        }															
														}*/
											%> 
										</td>
										<td width="30%" height="20" align="center">
											<%
                                                                                          if( overtime!=null && overtime.getAckId()!=0) {
                                                                                              Employee aprv = PstEmployee.fetchExc(overtime.getAckId());
                                                                                              out.println(aprv.getFullName());
                                                                                             
                                                                                          } else {
                                                                                              out.println("not yet approved");              
                                                                                          }
                                                                                        
														/* Vector ack_value = new Vector(1, 1);
														Vector ack_key = new Vector(1, 1);
														ack_value.add("0");
														ack_key.add("select ...");														
                                                                                                                if(hr_department!=0){
														Vector listAck = oidDepartment!=0 ? PstEmployee.listEmployeeSupervisi(hr_department, 0, PstPosition.LEVEL_SUPERVISOR, PstPosition.LEVEL_MANAGER) : new Vector();
														for (int i = 0; i < listAck.size(); i++) {
															Employee ack = (Employee) listAck.get(i);
                                                                                                                        if(overtime.getAckId()==ack.getOID()){
                                                                                                                            out.println(ack.getFullName());
                                                                                                                        }															
														} */
											%>                                                                                         
										</td>
										<td width="5%" height="20" ></td>
									</tr>
									<tr>
										<td height="20" align="center" valign="middle"></td>
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
                </td>
            </tr>
            <tr>
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" -->
                    <%@ include file = "../../main/footer.jsp" %>
                    <!-- #EndEditable --> </td>
            </tr>
        </table>
    </body>
    <!-- #BeginEditable "script" -->
    <script language="JavaScript">
                var oBody = document.body;
                var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
    </script>
    <!-- #EndEditable -->
    <!-- #EndTemplate --></html>
