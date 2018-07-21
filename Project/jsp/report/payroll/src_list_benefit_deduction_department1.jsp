
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
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
<%@ page import = "com.dimata.printman.*" %>
<%@ page import = "com.dimata.harisma.printout.*" %>
<%@ page import = "com.dimata.harisma.printout.PayPrintText" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_PROCESS, AppObjInfo.OBJ_PAYROLL_PROCESS_PRINT);%>
<%@ include file = "../../main/checkuser.jsp" %>

<%
    CtrlPaySlipComp ctrlPaySlipComp = new CtrlPaySlipComp(request);
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int inclResign = FRMQueryString.requestInt(request, "INCLUDE_RESIGN");
    boolean bIncResign = (inclResign == 1 ? true : false);

    long oidCompany = FRMQueryString.requestLong(request, "oidCompany");
    long oidDivision = FRMQueryString.requestLong(request, "division");
    long oidDepartment = FRMQueryString.requestLong(request, "department");
    long oidSection = FRMQueryString.requestLong(request, "section");
    long oidPaySlipComp = FRMQueryString.requestLong(request, "section");
    String searchNrFrom = FRMQueryString.requestString(request, "searchNrFrom");
    String searchNrTo = FRMQueryString.requestString(request, "searchNrTo");
    String searchName = FRMQueryString.requestString(request, "searchName");
    int dataStatus = FRMQueryString.requestInt(request, "dataStatus");
    String codeComponenGeneral = FRMQueryString.requestString(request, "compCode");
    String compName = FRMQueryString.requestString(request, "compName");
    int aksiCommand = FRMQueryString.requestInt(request, "aksiCommand");
    //update by satrya 2014-03-26
    int value_search = FRMQueryString.requestInt(request, "value_search");

    long periodeId = FRMQueryString.requestLong(request, "periodId");
    int numKolom = FRMQueryString.requestInt(request, "numKolom");
    int statusSave = FRMQueryString.requestInt(request, "statusSave");
    int keyPeriod = FRMQueryString.requestInt(request, "paySlipPeriod");
    //update by satrya 2013-01-24
    long payGroupId = FRMQueryString.requestLong(request, "payGroupId");
    String keyPeriodStr = request.getParameter("paySlipPeriod");

    boolean printZeroValue = true;
    String sprintZeroValue = PstSystemProperty.getValueByName("PAYROLL_PRINT_ZERO_VALUE");
    if (sprintZeroValue == null || sprintZeroValue.length() == 0 || sprintZeroValue.equalsIgnoreCase("YES")
            || sprintZeroValue.equalsIgnoreCase("1") || sprintZeroValue.equalsIgnoreCase("Not initialized")) {
        printZeroValue = true;
    } else {
        printZeroValue = false;
    }

    String sTemp = PstSystemProperty.getValueByName("PAYROLL_PRINT_pageLength");
    int iTemp = 0;
    try {
        iTemp = Integer.parseInt(sTemp);
    } catch (Exception exc) {
        System.out.println("No setting for PAYROLL_PRINT_pageLength ");
    }
    int pageLength = iTemp != 0 ? iTemp : 29; // maximum row per slip

    sTemp = PstSystemProperty.getValueByName("PAYROLL_PRINT_pageWidth");
    try {
        iTemp = Integer.parseInt(sTemp);
    } catch (Exception exc) {
        System.out.println("No setting for PAYROLL_PRINT_pageWidth ");
    }
    int pageWidth = iTemp != 0 ? iTemp : 80; // maximum lebar per slip

    sTemp = PstSystemProperty.getValueByName("PAYROLL_PRINT_leftMargin");
    try {
        iTemp = Integer.parseInt(sTemp);
    } catch (Exception exc) {
        System.out.println("No setting for PAYROLL_PRINT_leftMargin ");
    }
    int leftMargin = iTemp != 0 ? iTemp : 2; // margin di kiri

    sTemp = PstSystemProperty.getValueByName("PAYROLL_PRINT_maxLeftgSiteLength");
    try {
        iTemp = Integer.parseInt(sTemp);
    } catch (Exception exc) {
        System.out.println("No setting for PAYROLL_PRINT_maxLeftgSiteLength ");
    }
    int maxLeftgSiteLength = iTemp != 0 ? iTemp : 48; // maximum lebar bagian kiri slip spt untuk company dan data karyawan

    sTemp = PstSystemProperty.getValueByName("PAYROLL_PRINT_startCompany");
    try {
        iTemp = Integer.parseInt(sTemp);
    } catch (Exception exc) {
        System.out.println("No setting for PAYROLL_PRINT_startCompany ");
    }
    int startCompany = iTemp != 0 ? iTemp : 1; // row mulai print company

    sTemp = PstSystemProperty.getValueByName("PAYROLL_PRINT_startColHeaderValue");
    try {
        iTemp = Integer.parseInt(sTemp);
    } catch (Exception exc) {
        System.out.println("No setting for PAYROLL_PRINT_startColHeaderValue ");
    }
    int startColHeaderValue = iTemp != 0 ? iTemp : 14;

    sTemp = PstSystemProperty.getValueByName("PAYROLL_PRINT_startRowSlipComp");
    try {
        iTemp = Integer.parseInt(sTemp);
    } catch (Exception exc) {
        System.out.println("No setting for PAYROLL_PRINT_startRowSlipComp ");
    }
    int startRowSlipComp = iTemp != 0 ? iTemp : 1; // start row slip component

    PayPrintText.setPageLength(pageLength);
    PayPrintText.setPageWidth(pageWidth);
    PayPrintText.setLeftMargin(leftMargin);
    PayPrintText.setMaxLeftgSiteLength(maxLeftgSiteLength);
    PayPrintText.setStartCompany(startCompany);
    PayPrintText.setStartColHeaderValue(startColHeaderValue);
    PayPrintText.setStartRowSlipComp(startRowSlipComp);


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
    iErrCode = ctrlPaySlipComp.action(iCommand, oidPaySlipComp);
    FrmPaySlipComp frmPaySlipComp = ctrlPaySlipComp.getForm();
    PaySlipComp paySlipComp = ctrlPaySlipComp.getPaySlipComp();
    msgString = ctrlPaySlipComp.getMessage();

    /*if(iCommand == Command.SAVE && prevCommand == Command.ADD)
     {
     start = PstPaySlip.findLimitStart(oidEmployee,recordToGet, whereClause,orderClause);
     vectSize = PstEmployee.getCount(whereClause);
     }
     else
     {
     vectSize = sessEmployee.countEmployee(srcEmployee);
     }
     if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)||
     (iCommand==Command.LAST)||(iCommand==Command.LIST))
     start = ctrlPaySlip.actionList(iCommand, start, vectSize, recordToGet);*/
%>
<%


//get the kode component name by componentId
/*PayComponent payComponent = new PayComponent();
     String codeComponenGeneral ="";
     try{
     payComponent = PstPayComponent.fetchExc(componentId);
     codeComponenGeneral = payComponent.getCompCode();
     }
     catch(Exception e){
     }*/


    Vector listEmpPaySlip = new Vector(1, 1);
    if (iCommand == Command.LIST || iCommand == Command.EDIT || iCommand == Command.SAVE || iCommand == Command.ADD || iCommand == Command.PRINT) {
        listEmpPaySlip = SessEmployee.listEmpPaySlip(oidDepartment, oidDivision, oidSection, searchNrFrom, searchNrTo, searchName, periodeId, -1, bIncResign, 0);
        /*if(listEmpPaySlip.size()==0){
         //listEmpPaySlip = SessEmployee.listEmpPaySlip(oidDepartment,oidDivision,oidSection,searchNrFrom,searchNrTo,searchName,0);			
         }*/
    }

%>

<!-- JSP Block -->
<%!    public String drawList(int iCommand, FrmPaySlipComp frmObject, PaySlipComp objEntity, Vector objectClass, long idPaySlipComp, String codeComponent, String componentName) {
        String result = "";
        Vector token = new Vector(1, 1);
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("90%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("No", "2%", "2", "0");
        ctrlist.addHeader("Employee Nr.", "5%", "2", "0");
        ctrlist.addHeader("Nama", "12%", "2", "0");
        ctrlist.addHeader("Position", "12%", "2", "0");
        ctrlist.addHeader("Commencing Date", "5%", "0", "0");
        ctrlist.addHeader("Salary Level", "5%", "0", "0");
        ctrlist.addHeader("Start Date", "5%", "0", "0");
        String checked = "";
        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        Vector rowx = new Vector(1, 1);
        int index = -1;
        String frmCurrency = "#,###";
        if (objectClass != null && objectClass.size() > 0) {
            for (int i = 0; i < objectClass.size(); i++) {
                int total = 0;
                Vector temp = (Vector) objectClass.get(i);
                Employee employee = (Employee) temp.get(0);
                PayEmpLevel payEmpLevel = (PayEmpLevel) temp.get(1);
                PaySlip paySlip = (PaySlip) temp.get(2);
                rowx = new Vector();
                rowx.add(String.valueOf(1 + i));
                rowx.add("<input type=\"hidden\" name=\"paySlipId\" value=\"" + paySlip.getOID() + "\" class=\"formElemen\" size=\"10\">" + employee.getEmployeeNum());
                rowx.add(employee.getFullName());
                //get the position
                Position pos = new Position();
                String position = "";
                try {
                    pos = PstPosition.fetchExc(employee.getPositionId());
                    position = pos.getPosition();
                } catch (Exception e) {
                    System.out.println("Exception" + e);
                }
                rowx.add("" + position);
                rowx.add("" + Formater.formatDate(employee.getCommencingDate(), "dd-MMM-yyyy"));
                rowx.add("<input type=\"hidden\" name=\"level_code\" value=\"" + payEmpLevel.getLevelCode() + "\" class=\"formElemen\" size=\"10\">" + payEmpLevel.getLevelCode());
                rowx.add("" + payEmpLevel.getStartDate());
                lstData.add(rowx);
            }
            result = ctrlist.drawList();
        } else {
            result = "<i>Belum ada data dalam sistem ...</i>";
        }
        return result;
    }
%>
<%
    Vector hostLst = null;
    Vector localPrinter = null;
    try {
        //System.out.println(" JSP 1 0");
        //hostLst = RemotePrintMan.getHostList();
        //System.out.println(" JSP 1 1");
        if (hostLst != null) {
            for (int h = 0; h < hostLst.size(); h++) {
                PrinterHost host = (PrinterHost) hostLst.get(h);
                //System.out.println(" JSP 1 2"+h);
                if (host != null) {
                    //out.println(""+h+")"+host.getHostName()+"<br>");
                }
            }
        } else {
            System.out.println("START LOCAL PRINTER SERVICE INDEX 0");
            if (DSJ_PrinterService.getPrinterDrv(0) == null) { // check if the first printer exist
                DSJ_PrinterService prnsvc = DSJ_PrinterService.getInstance();
                if (!prnsvc.running) {
                    prnsvc.running = true;
                    Thread thr = new Thread(prnsvc);
                    thr.setDaemon(false);
                    thr.start();
                }
            }
        }
    } catch (Exception exc) {
        System.out.println("HostLst:  " + exc);
    }
    //out.println("hostLst :::::::::::::"+hostLst);

%>
<%
    String s_employee_id = null;
    String s_payslip_id = null;
    String s_level_code = null;
    long oidEmployee = 0;
// Jika tekan command Save
    if (iCommand == Command.PRINT) {
        if (aksiCommand == 0) {
            System.out.println("print all");
            String[] employee_id = null;
            String[] paySlip_id = null;
            String[] level_code = null;
            String hostIpIdx = "";
            Vector listDfGjPrintBenefit = new Vector(1, 1);
            Vector listDfGjPrintDeduction = new Vector(1, 1);
            try {
                employee_id = request.getParameterValues("employeeId");
                paySlip_id = request.getParameterValues("paySlipId");
                level_code = request.getParameterValues("level_code");
                hostIpIdx = request.getParameter("printeridx");// ip server
                System.out.println("nilai hostIpIdx  " + hostIpIdx);
            } catch (Exception e) {
                System.out.println("Err : " + e.toString());
            }

            DSJ_PrintObj obj = null;
            Vector list = new Vector();
            if (employee_id != null && paySlip_id != null && level_code != null) {
                for (int i = 0; i < listEmpPaySlip.size(); i++) {
                    listDfGjPrintBenefit = new Vector();
                    try {
                        //oidEmployee = FRMQueryString.requestLong(request, "print"+i+""); // row yang dicheked
                        s_employee_id = String.valueOf(employee_id[i]);
                        s_payslip_id = String.valueOf(paySlip_id[i]);
                        s_level_code = String.valueOf(level_code[i]);
                    } catch (Exception e) {
                    }
                    //print semua yang ditmapilkan
                    // cari payslip dari slip yang akan dicetak
                    //listDfGjPrintBenefit = PstSalaryLevelDetail.listPaySlip(PstSalaryLevelDetail.YES_TAKE,s_level_code,PstPayComponent.TYPE_BENEFIT,Long.parseLong(paySlip_id[i]),keyPeriod, printZeroValue);
                    //System.out.println("PaySlipId yang diprint " + Long.parseLong(paySlip_id[i]));
                    listDfGjPrintBenefit = PstSalaryLevelDetail.listPaySlipGlobal(PstSalaryLevelDetail.YES_TAKE, s_level_code, Long.parseLong(paySlip_id[i]), keyPeriod);
                    list.add(listDfGjPrintBenefit);

                }
            }
            if ((hostIpIdx != null) && (hostLst != null) && (hostLst.size() > 0)) {
                System.out.println("PrintPaySlip.rowxz  " + listDfGjPrintBenefit.size());
                obj = PrintPaySlip.PrintForm(employee_id, periodeId, list, listEmpPaySlip, keyPeriod, payGroupId);
                PrinterHost prnHost = RemotePrintMan.getPrinterHost(hostIpIdx, ";");
                PrnConfig prn = RemotePrintMan.getPrinterConfig(hostIpIdx, ";");
                obj.setPrnIndex(prn.getPrnIndex());
                RemotePrintMan.printObj(prnHost, obj);
            } else {
                obj = PrintPaySlip.PrintForm(employee_id, periodeId, list, listEmpPaySlip, keyPeriod, payGroupId);
                try {
                    obj.setPrnIndex(Integer.parseInt(hostIpIdx));
                } catch (Exception exc) {
                    System.out.println(exc);
                };
                if (obj != null && (!DSJ_PrinterService.existPrnDriverLoader(obj.getPrnIndex()) || !DSJ_PrinterService.running)) {
                    DSJ_PrinterService prnSvc = DSJ_PrinterService.getInstance();
                    if (obj != null) {
                        PrinterDriverLoader prndLoader = new PrinterDriverLoader(obj);
                        prnSvc.addPrnDriverLoader(prndLoader);
                        Thread thr = new Thread(prnSvc);
                        thr.setDaemon(false);
                        thr.start();
                    }
                }
                if (obj != null && DSJ_PrinterService.existPrnDriverLoader(obj.getPrnIndex())) {
                    DSJ_PrinterService.print(obj);
                } else {
                    if (!DSJ_PrinterService.existPrnDriverLoader(obj.getPrnIndex())) {
                        msgString = "No local printer found";
                    }
                }
            }
            //update by satrya 2013-01-14
            listEmpPaySlip = SessEmployee.listEmpPaySlip(oidDepartment, oidDivision, oidSection, searchNrFrom, searchNrTo, searchName, periodeId, -1, bIncResign, 0);
        } else {
            System.out.println("print selected");
            String[] employee_id = null;
            String[] paySlip_id = null;
            String[] level_code = null;
            String hostIpIdx = "";
            Vector listDfGjPrintBenefit = new Vector(1, 1);
            try {
                employee_id = request.getParameterValues("employeeId");
                paySlip_id = request.getParameterValues("paySlipId");
                level_code = request.getParameterValues("level_code");
                hostIpIdx = request.getParameter("printeridx");// ip server
                System.out.println("nilai hostIpIdx  " + hostIpIdx);
            } catch (Exception e) {
                System.out.println("Err : " + e.toString());
            }
            DSJ_PrintObj obj = null;
            Vector list = new Vector();
            Vector listEmp = new Vector();
            Vector listEmpId = new Vector();
            for (int i = 0; i < listEmpPaySlip.size(); i++) {
                listDfGjPrintBenefit = new Vector();
                try {
                    oidEmployee = FRMQueryString.requestLong(request, "print" + i + ""); // row yang dicheked
                    s_employee_id = String.valueOf(employee_id[i]);
                    s_payslip_id = String.valueOf(paySlip_id[i]);
                    s_level_code = String.valueOf(level_code[i]);
                } catch (Exception e) {
                    System.out.println("Exception" + e);
                }
                //System.out.println("nilai statusSave"+statusSave);
                if (oidEmployee != 0) {
                    //System.out.println("PaySlipId yang diprint " + Long.parseLong(paySlip_id[i]));
                    //System.out.println("keyPeriod yang diprint  " + keyPeriod);
                    listDfGjPrintBenefit = PstSalaryLevelDetail.listPaySlipGlobal(PstSalaryLevelDetail.YES_TAKE, s_level_code, Long.parseLong(paySlip_id[i]), keyPeriod);
                    list.add(listDfGjPrintBenefit);
                    listEmp.add("" + oidEmployee);
                    listEmpId.add(oidEmployee);
                }

            }
            if (listEmpId != null && listEmpId.size() > 0) {
                listEmpPaySlip = SessEmployee.listEmpPaySlipByEmployeeId(listEmpId, periodeId, -1, bIncResign);
            }
            if ((hostIpIdx != null) && (hostLst != null) && (hostLst.size() > 0)) {
                System.out.println("PrintPaySlip.rowxz  " + listDfGjPrintBenefit.size());
                obj = PrintPaySlip.PrintForm(employee_id, periodeId, list, listEmp, keyPeriod, payGroupId);
                PrinterHost prnHost = RemotePrintMan.getPrinterHost(hostIpIdx, ";");
                PrnConfig prn = RemotePrintMan.getPrinterConfig(hostIpIdx, ";");
                obj.setPrnIndex(prn.getPrnIndex());
                RemotePrintMan.printObj(prnHost, obj);
            } else {
                obj = PrintPaySlip.PrintForm(employee_id, periodeId, list, listEmp, keyPeriod, payGroupId);
                try {
                    obj.setPrnIndex(Integer.parseInt(hostIpIdx));
                } catch (Exception exc) {
                    System.out.println(exc);
                };
                if (obj != null && (!DSJ_PrinterService.existPrnDriverLoader(obj.getPrnIndex()) || !DSJ_PrinterService.running)) {
                    DSJ_PrinterService prnSvc = DSJ_PrinterService.getInstance();
                    if (obj != null) {
                        PrinterDriverLoader prndLoader = new PrinterDriverLoader(obj);
                        prnSvc.addPrnDriverLoader(prndLoader);
                        Thread thr = new Thread(prnSvc);
                        thr.setDaemon(false);
                        thr.start();
                    }
                }
                if (obj != null && DSJ_PrinterService.existPrnDriverLoader(obj.getPrnIndex())) {
                    DSJ_PrinterService.print(obj);
                } else {
                    if (!DSJ_PrinterService.existPrnDriverLoader(obj.getPrnIndex())) {
                        msgString = "No local printer found";
                    }
                }
            }

        }
        //hidde by satrya 2013-01-14
        // listEmpPaySlip = SessEmployee.listEmpPaySlip(oidDepartment, oidDivision, oidSection, searchNrFrom, searchNrTo, searchName, periodeId, -1, bIncResign);
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

            function cmdUpdateDiv(){
            document.frm_printing.target="";    
                document.frm_printing.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frm_printing.action="src_list_benefit_deduction_department1.jsp";        
                document.frm_printing.submit();
            }

            function cmdUpdateDept(){
            document.frm_printing.target="";    
                document.frm_printing.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frm_printing.action="src_list_benefit_deduction_department1.jsp";        
                document.frm_printing.submit();
            }

            function cmdUpdateSec(){
            document.frm_printing.target="";    
                document.frm_printing.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frm_printing.action="src_list_benefit_deduction_department1.jsp";        
                document.frm_printing.submit();
            }



            function cmdSearch(){
                document.frm_printing.target="";
                document.frm_printing.command.value="<%=Command.LIST%>";
                document.frm_printing.value_search.value="1";
                document.frm_printing.action="src_list_benefit_deduction_department1.jsp";
                document.frm_printing.submit();
            }
        
            function cmdLoad(component_code,component_name){
                document.frm_printing.target="";
                document.frm_prepare_data.compCode.value=component_code;
                document.frm_prepare_data.compName.value=component_name;
                document.frm_prepare_data.command.value="<%=Command.LIST%>";
                document.frm_prepare_data.action="pay-pre-data.jsp";
                document.frm_prepare_data.submit();
                document.frm_prepare_data.refresh;
            }
        
            function cmdLevel(employeeId,salaryLevel,paySlipId,paySlipPeriod){
                document.frm_printing.target="";
                document.frm_prepare_data.action="pay-input-detail.jsp?employeeId=" + employeeId+ "&salaryLevel=" + salaryLevel+"&paySlipId=" + paySlipId +"&paySlipPeriod=" + paySlipPeriod ;
                document.frm_prepare_data.command.value="<%=Command.LIST%>";
                document.frm_prepare_data.submit();
            }
        
            function cmdSave(){
                document.frm_printing.target="";
                document.frm_prepare_data.command.value="<%=Command.SAVE%>";
                document.frm_prepare_data.aksiCommand.value="0";
                document.frm_prepare_data.statusSave.value="0";
                document.frm_prepare_data.action="pay-pre-data.jsp";
                document.frm_prepare_data.submit();
            }
            function cmdPrint(){
                document.frm_printing.target="";
                document.frm_printing.command.value="<%=Command.PRINT%>";
                document.frm_printing.aksiCommand.value="1";
                document.frm_printing.action="src_list_benefit_deduction_department1.jsp";
                document.frm_printing.target = "";
                document.frm_printing.submit();
            }
        
        
        
            function cmdPrintAll(){
                document.frm_printing.target="";
                document.frm_printing.command.value="<%=Command.PRINT%>";
                document.frm_printing.aksiCommand.value="0";
                document.frm_printing.action="src_list_benefit_deduction_department1.jsp";
                document.frm_printing.target = "";
                document.frm_printing.submit();
            }
 

            function cmdPrintText(){
                document.frm_printing.target="";
                document.frm_printing.command.value="<%=Command.PRINT%>";
                document.frm_printing.aksiCommand.value="1";
                document.frm_printing.action="<%=approot%>/servlet/com.dimata.harisma.printout.PayPrintText";
                document.frm_printing.target = "";
                document.frm_printing.submit();
            }

            function cmdPrintAllText(){
                document.frm_printing.target="";
                document.frm_printing.command.value="<%=Command.PRINT%>";
                document.frm_printing.aksiCommand.value="0";
                document.frm_printing.target="printpayroll";
                document.frm_printing.action="<%=approot%>/servlet/com.dimata.harisma.printout.PayPrintText";
                document.frm_printing.submit();
            }

            function cmdPrintCsv(){
                document.frm_printing.target="";
                document.frm_printing.command.value="<%=Command.PRINT%>";
                document.frm_printing.aksiCommand.value="1";
                document.frm_printing.target="summarypayroll";
                document.frm_printing.action="<%=approot%>/servlet/com.dimata.harisma.printout.PayrollSummaryXls";
                document.frm_printing.submit();
            }

            function cmdPrintAllCsvRekonsiliasi(){
                document.frm_printing.target="";
                document.frm_printing.command.value="<%=Command.PRINT%>";
                document.frm_printing.aksiCommand.value="0";
                document.frm_printing.target="summarypayroll";
                document.frm_printing.action="<%=approot%>/servlet/com.dimata.harisma.printout.PayrollSummaryXlsRekonsiliasiGaji";
                document.frm_printing.submit();
            }
        
            function cmdPrintAllCsvPerDepart(){
                document.frm_printing.target="";
                document.frm_printing.command.value="<%=Command.PRINT%>";
                document.frm_printing.aksiCommand.value="0";
                document.frm_printing.target="summarypayroll";
                document.frm_printing.action="<%=approot%>/servlet/com.dimata.harisma.printout.PayrollSummaryXls1PerDepart";
                document.frm_printing.submit();
            }
        
            function cmdPrintAllCsvDifferent(){
                document.frm_printing.target="";
                document.frm_printing.command.value="<%=Command.PRINT%>";
                document.frm_printing.aksiCommand.value="0";
                document.frm_printing.target="summarypayroll";
                document.frm_printing.action="<%=approot%>/servlet/com.dimata.harisma.printout.PayrollSummaryXlsDifferent";
                document.frm_printing.submit();
            }
        

            function cmdSaveAll(){
                document.frm_printing.target="";
                document.frm_prepare_data.command.value="<%=Command.SAVE%>";
                document.frm_prepare_data.aksiCommand.value="0";
                document.frm_prepare_data.statusSave.value="1";
                document.frm_prepare_data.action="pay-pre-data.jsp";
                document.frm_prepare_data.submit();
            }
        
            function cmdBack(){
                document.frm_printing.target="";
                document.frm_prepare_data.command.value="<%=Command.LIST%>";
                document.frm_prepare_data.action="pay-pre-data.jsp";
                document.frm_prepare_data.submit();
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
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../../styletemplate/template_header.jsp" %>
            <%} else {%> 
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
                                        <td height="20"> <font color="#FF6600" face="Arial"><strong> 
                                                <!-- #BeginEditable "contenttitle" -->Payroll Prosess &gt; Pay Slip Printing <!-- #EndEditable --> </strong></font> </td>
                                    </tr>
                                    <tr> 
                                        <td> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr> 
                                                    <td style="background-color:<%=bgColorContent%>; "> 
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                            <tr> 
                                                                <td valign="top"> 
                                                                    <table style="border:1px solid <%=garisContent%>" width="100%" cellspacing="1" cellpadding="1" class="tabbg" >
                                                                        <tr> 
                                                                            <td valign="top"> <!-- #BeginEditable "content" --> 
                                                                                <form name="frm_printing" method="post" action="" >
                                                                                    <input type="hidden" name="command" value="">
                                                                                    <input type="hidden" name="aksiCommand" value="<%=aksiCommand%>">
                                                                                    <input type="hidden" name="value_search" value="<%=value_search%>">
                                                                                    <input type="hidden" name="paySlipPeriod" value="1">
                                                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                        <tr> 
                                                                                            <td height="13" width="1%">&nbsp;</td>
                                                                                            <td height="13" width="33%" nowrap><b class="listtitle"><font size="3" color="#000000">Period</font></b> &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;:
                                                                                                    <%
                                                                                                        Vector periodValue = new Vector(1, 1);
                                                                                                        Vector periodKey = new Vector(1, 1);
                                                                                                        // salkey.add(" ALL DEPARTMET");
                                                                                                        //deptValue.add("0");
                                                                                                        Vector listPeriod = PstPayPeriod.list(0, 0, "", "START_DATE DESC");
                                                                                                        //   Vector listPeriod = PstPeriod.list(0, 0, "", "START_DATE DESC");
                                                                                                        for (int r = 0; r < listPeriod.size(); r++) {
                                                                                                            PayPeriod payPeriod = (PayPeriod) listPeriod.get(r);
                                                                                                            //  Period period = (Period) listPeriod.get(r);
                                                                                                            periodValue.add("" + payPeriod.getOID());
                                                                                                            periodKey.add(payPeriod.getPeriod());
                                                                                                        }
                                                                                                    %> <%=ControlCombo.draw("periodId", null, "" + periodeId, periodValue, periodKey, "")%>
                                                                                                </font></b></td>
                                                                                            <td height="13" width="30%">Company : 
                                                                                                <%
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
                                                                                                %> 
                                                                                                <%= ControlCombo.draw("oidCompany", "formElemen", null, "" + oidCompany, comp_value, comp_key, "onChange=\"javascript:cmdUpdateDiv()\"")%>
                                                                                            </td>
                                                                                            <td height="13" width="28%">&nbsp;</td>
                                                                                            <td height="13" width="8%">&nbsp;</td>
                                                                                        </tr>
                                                                                        <tr> 
                                                                                            <td height="30" width="1%">&nbsp;</td>

                                                                                            <td height="30" width="33%" nowrap >Division 
                                                                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: 
                                                                                                <%
                                                                                                    String whereDiv = "";
                                                                                                    if (oidCompany != 0) {
                                                                                                        whereDiv = PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID] + " = " + oidCompany;
                                                                                                    }
                                                                                                    Vector listDivision = PstDivision.list(0, 0, whereDiv, "DIVISION");
                                                                                                    Vector divValue = new Vector(1, 1);
                                                                                                    Vector divKey = new Vector(1, 1);
                                                                                                    divValue.add("0");
                                                                                                    divKey.add("select ...");
                                                                                                    for (int d = 0; d < listDivision.size(); d++) {
                                                                                                        Division division = (Division) listDivision.get(d);
                                                                                                        divValue.add("" + division.getOID());
                                                                                                        divKey.add(division.getDivision());
                                                                                                    }
                                                                                                    out.println(ControlCombo.draw("division", null, "" + oidDivision, divValue, divKey, "onChange=\"javascript:cmdUpdateDept()\""));
                                                                                                %>
                                                                                            </td>
                                                                                            <td height="30" width="40%">Dept 
                                                                                                : 
                                                                                                <%
                                                                                                    Vector dept_value = new Vector(1, 1);
                                                                                                    Vector dept_key = new Vector(1, 1);
                                                                                                    dept_value.add("0");
                                                                                                    dept_key.add("select ...");
                                                                                                    String whereDept = "" + (oidDivision != 0 ? PstDepartment.TBL_HR_DEPARTMENT + "." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivision : "");
                                                                                                    Vector listDept = PstDepartment.list(0, 0, whereDept, " DEPARTMENT ");

                                                                                                    for (int i = 0; i < listDept.size(); i++) {
                                                                                                        Department dept = (Department) listDept.get(i);
                                                                                                        dept_key.add(dept.getDepartment());
                                                                                                        dept_value.add(String.valueOf(dept.getOID()));
                                                                                                    }
                                                                                                    out.println(ControlCombo.draw("department", null, "" + oidDepartment, dept_value, dept_key, "onChange=\"javascript:cmdUpdateSec()\""));
                                                                                                %>
                                                                                            </td>
                                                                                            <td height="30" width="28%">Section 
                                                                                                <%
                                                                                                    Vector sec_value = new Vector(1, 1);
                                                                                                    Vector sec_key = new Vector(1, 1);
                                                                                                    sec_value.add("0");
                                                                                                    sec_key.add("select ...");
                                                                                                    String whereSec = "" + (oidDepartment == 0 ? "" : PstSection.TBL_HR_SECTION + "." + PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + "=" + oidDepartment);
                                                                                                    Vector listSec = PstSection.list(0, 0, whereSec, " SECTION ");
                                                                                                    for (int i = 0; i < listSec.size(); i++) {
                                                                                                        Section sec = (Section) listSec.get(i);
                                                                                                        sec_key.add(sec.getSection());
                                                                                                        sec_value.add(String.valueOf(sec.getOID()));
                                                                                                    }
                                                                                                    out.println(ControlCombo.draw("section", null, "" + oidSection, sec_value, sec_key));
                                                                                                %>
                                                                                            </td>
                                                                                            <td height="30" width="8%">&nbsp;</td>
                                                                                        </tr>
                                                                                        <tr> 
                                                                                            <td height="32" width="1%">&nbsp;</td>


                                                                                            <td height="32" width="8%">&nbsp;<input type="checkbox" name="INCLUDE_RESIGN" value="1" />&nbsp;Termasuk Karyawan Resign</td>
                                                                                        </tr>

                                                                                        <tr> 
                                                                                            <td>&nbsp;</td>
                                                                                            <td width="33%"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a>
                                                                                                <img src="<%=approot%>/images/spacer.gif" width="6" height="1">
                                                                                                <a href="javascript:cmdSearch()">Search 
                                                                                                    for Employee</a></td></tr>
                                                                                        <tr> 
                                                                                            <td height="13" width="1%">&nbsp;</td>
                                                                                            <td height="13" colspan="4">&nbsp;</td>
                                                                                        </tr>
                                                                                       <%
                                                                                            //System.out.println("listPreData  "+listPreData.size());
                                                                                            if ((listEmpPaySlip != null) && (listEmpPaySlip.size() > 0)) {
                                                                                        %>
                                                                                        <tr> 
                                                                                            <td colspan="6" height="8"><%=drawList(iCommand, frmPaySlipComp, paySlipComp, listEmpPaySlip, oidPaySlipComp, codeComponenGeneral, compName)%></td>
                                                                                        </tr>
                                                                                        <%} else {%>
                                                                                        <tr> 
                                                                                            <td>&nbsp; </td>
                                                                                            <td height="8" width="33%" class="comment" colspan="3"><span class="comment"><br>
                                                                                                    &nbsp;No Employee available</span> 
                                                                                            </td>
                                                                                        </tr>
                                                                                        <%}%>


                                                                                        <tr> <td colspan="5">
                                                                                                <table>
                                                                                                    <tr>            <%if (value_search > 0) {%>



                                                                                                        <td >
                                                                                                            <!-- Untuk selected printing -->
                                                                                                            <img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print All(Text)">
                                                                                                            <img src="<%=approot%>/images/spacer.gif" width="6" height="1">
                                                                                                            <a href="javascript:cmdPrintAllCsvPerDepart()" class="command">Export All Depart to Excel</a> &nbsp;&nbsp; &nbsp;&nbsp;
                                                                                                        </td>
                                                                                                        <td >
                                                                                                            <!-- Untuk selected printing -->
                                                                                                            <img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print All(Text)">
                                                                                                            <img src="<%=approot%>/images/spacer.gif" width="6" height="1">
                                                                                                            <a href="javascript:cmdPrintAllCsvDifferent()" class="command">Export Rincian to Excel</a> &nbsp;&nbsp; &nbsp;&nbsp;
                                                                                                        </td>
                                                                                                        <td >
                                                                                                            <!-- Untuk selected printing -->
                                                                                                            <img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print All(Text)">
                                                                                                            <img src="<%=approot%>/images/spacer.gif" width="6" height="1">
                                                                                                            <a href="javascript:cmdPrintAllCsvRekonsiliasi()" class="command">Export Rekonsiliasi to Excel</a> &nbsp;&nbsp; &nbsp;&nbsp;
                                                                                                        </td>
                                                                                                        <%}%>		
                                                                                                        <%//}%>
                                                                                                    </tr>                    
                                                                                                    <tr> 

                                                                                                    </tr> 
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>

                                                                                        <tr> 
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
                                                                                            <td colspan="4">
                                                                                                <table width="100%">
                                                                                                    <td width="3%"> <img src="<%=approot%>/images/attention-icon.png" width="25" height="25"></td>
                                                                                                    <td width="97%"><span class="fielderror"><font size="4">Attention, please set up printer in page set up with:</font> </span></td> 
                                                                                                </table>
                                                                                            </td>

                                                                                        </tr>
                                                                                        <tr> 
                                                                                            <td width="1%">&nbsp;</td>
                                                                                            <td colspan="4">
                                                                                                <table width="100%">
                                                                                                    <td width="3%">&nbsp;</td>
                                                                                                    <td colspan="4"><span class="bullettitle1">
                                                                                                            <ul>
                                                                                                                <li>paper size setting with size letter</li>
                                                                                                                <li>orientation paper setting with portait</li>
                                                                                                                <li>margin setting top: with 0.75, and etc set to zero(0)</li>
                                                                                                                <li>header and footer setting with none</li>
                                                                                                            </ul> </span>
                                                                                                    </td>
                                                                                                </table>
                                                                                            </td>

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
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
            <tr>
                <td valign="bottom">
                    <!-- untuk footer -->
                    <%@include file="../../footer.jsp" %>
                </td>

            </tr>
            <%} else {%>
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
    <!-- #EndEditable --> <!-- #EndTemplate -->
</html>
