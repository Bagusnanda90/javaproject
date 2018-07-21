<%@page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.session.employee.SessEmployee" %>
<%@ page import = "com.dimata.harisma.utility.service.tma.*" %>
<%@ page import = "com.dimata.harisma.utility.machine.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_BARCODE, AppObjInfo.OBJ_INSERT_AND_UPLOAD_BARCODE); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privStart=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_START));
%>
<%!
    public String drawList(Vector objectClass){
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("Payroll","7%");
        ctrlist.addHeader("Full Name","23%");
        ctrlist.addHeader("Position","25%");
        ctrlist.addHeader("Message","35%");
        ctrlist.addHeader("Start Date","10%"); 
        ctrlist.addHeader("End Date","10%"); 
        ctrlist.addHeader("Select","15%"); 

        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.reset();
		
	//int iAddFingerId = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("FINGER_PRINT_NUMBER")));
	
        Hashtable position = new Hashtable();
        Vector listPosition = PstPosition.listAll();
        position.put("0", "-");
        for (int ls = 0; ls < listPosition.size(); ls++) {
            Position pos = (Position) listPosition.get(ls);
            position.put(String.valueOf(pos.getOID()), pos.getPosition());
        }
		Vector vTemp = new Vector();
        for (int i = 0; i < objectClass.size(); i++){
            vTemp = (Vector)objectClass.get(i);
			
            Employee employee = (Employee) vTemp.get(0);
            EmpMessage empMessage = (EmpMessage) vTemp.get(1);
			
            Vector rowx = new Vector();
            rowx.add(employee.getEmployeeNum());
            rowx.add(employee.getFullName());
            rowx.add(position.get(String.valueOf(employee.getPositionId())));
            String barcode = "";
           
            try 
			{
                barcode = employee.getBarcodeNumber();
            }
            catch(Exception e){
		System.out.println("exc when fetch barcode : " + e.toString());
            }

            if ((barcode != null) && (barcode.length() > 0)){
                rowx.add("<input type=\"hidden\" name=\"employee_id\" value=\"" + employee.getOID() + "\">"
                        +"<input type=\"hidden\" name=\"barcode_number\" value=\"" + barcode + "\">"
                        +"<input type=\"hidden\" name=\"message_id\" value=\"" + empMessage.getOID() + "\">"
                        +"<input type=\"text\" name=\"message_text\" size=\"32\" maxlength=\"32\" value=\""
                        +(empMessage.getOID()>0?empMessage.getMessage():"")
                        +"\">");
                Date startDate = empMessage.getStartDate() == null? new Date() : empMessage.getStartDate();
                rowx.add(ControlDatePopup.writeDate("start_date",startDate,1));
                Date thisEndDate = new Date();
                Date endDate = empMessage.getEndDate() == null? new Date(thisEndDate.getYear(),thisEndDate.getMonth(),thisEndDate.getDate()+1) : empMessage.getEndDate();
                rowx.add(ControlDatePopup.writeDate("end_date",endDate,2));
                	
                rowx.add("<center><input type=\"checkbox\" name=\"data_is_process"+i+"\" value=\"1\" checked=\"true\" ></center>");
                lstData.add(rowx);
				
            }    
        }

        ctrlist.setLinkSufix("')");
        return ctrlist.draw();
    }
%>
<%
    int iCommand = FRMQueryString.requestCommand(request);    
    
    String s_barcode_number = null;
    String s_old_barcode_number = null;
    String s_emp_pin = null;
    String machineNumber = "01";
    String machineNumberCanteen = "01";
    String[] machineNumbers;
    String[] machineNumbersCanteen;
    Vector vMsg = new Vector(1,1);
    Vector vMsgCanteen = new Vector(1,1);
  //  SessEmployee.setAllEmpFingerPrintNumber();
    int iUseFingerPrint = 0;
	machineNumber = String.valueOf(PstSystemProperty.getValueByName("ABSEN_TMA_NO"));
	machineNumberCanteen = String.valueOf(PstSystemProperty.getValueByName("CANTEEN_TMA_NO"));
	iUseFingerPrint = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("FINGER_PRINT_NUMBER")));
	StringTokenizer strTokenizer = new StringTokenizer(machineNumber,",");
	StringTokenizer strTokenizerCanteen = new StringTokenizer(machineNumberCanteen,",");
	machineNumbers = new String[strTokenizer.countTokens()];
	machineNumbersCanteen = new String[strTokenizerCanteen.countTokens()];
	
	System.out.println(" CEK ABSEN MACHINE :::::::::: "+machineNumbers.length);
	System.out.println(" CEK CANTEEN MACHINE :::::::::: "+machineNumbersCanteen.length);
	// Proses jika command adalah ASVE	
    if (iCommand == Command.SAVE) {
	int count = 0;
	int countCanteen = 0;
        while(strTokenizer.hasMoreTokens()){
            machineNumbers[count] = strTokenizer.nextToken();
            System.out.println("ABSEN MACHINE :::::::::: "+machineNumbers[count]);
            count ++;
        }
        while(strTokenizerCanteen.hasMoreTokens()){
            machineNumbersCanteen[countCanteen] = strTokenizerCanteen.nextToken();
            System.out.println("CANTEEN MACHINE :::::::::: "+machineNumbersCanteen[countCanteen]);
            countCanteen ++;
        }
	// Inisialisasi variable yang meng-handle nilai2 berikut
        String[] employee_id = null;		
        String[] barcode_number = null;
        String[] messages = null;
        String[] message_id = null;
        String[] start_date = null;				
        String[] end_date = null;				
        boolean[]isProcess = null;
	// Mengambil array nilai2 berikut
        try {
            employee_id = request.getParameterValues("employee_id");
            barcode_number = request.getParameterValues("barcode_number");
            message_id = request.getParameterValues("message_id");
            messages = request.getParameterValues("message_text");
            start_date = request.getParameterValues("start_date");
            end_date = request.getParameterValues("end_date");
            isProcess = new boolean[barcode_number.length];
            for(int i=0;i<barcode_number.length;i++){
                int ix = FRMQueryString.requestInt(request, "data_is_process"+i);
                if(ix==1){
                    isProcess[i] = true;
                }else{
                    isProcess[i] = false;
                }
            }
            
        }catch (Exception e){
            System.out.println("Err : "+e.toString());
        }

		// Jika array barcode_number tidak kosong (ada/minimal satu barcode employee diisi)	
        
        if ((barcode_number != null) && (barcode_number.length > 0)){
            for (int i = 0; i < barcode_number.length; i++){
                if(isProcess[i]){
                    Date currDate = new Date();
                    Date bfDate = new Date();
                    Date afDate = new Date();
                    bfDate = SessEmpMessage.parseStringToDate(start_date[i], SessEmpMessage.DATE_FORMAT_OTHER);
                    afDate = SessEmpMessage.parseStringToDate(end_date[i], SessEmpMessage.DATE_FORMAT_OTHER);
                    //if(bfDate.getTime()<currDate.getTime() && afDate.getTime()>currDate.getTime()){
                        EmployeeUp employeeUp = new EmployeeUp();
                        //Employee employee = new Employee();
                        employeeUp.setBarcode(barcode_number[i]);
                        String strMsg = "";
                        strMsg = messages[i];
                        long msgId = 0;
                        long empId = 0;
                        try{
                            msgId = Long.parseLong(message_id[i]);
                        }catch(Exception ex){}
                        try{
                            empId = Long.parseLong(employee_id[i]);
                        }catch(Exception ex){}
                        EmpMessage empMessage = new EmpMessage();
                        empMessage.setOID(msgId);
                        empMessage.setEmployeeId(empId);
                        empMessage.setMessage(strMsg);
                        empMessage.setStartDate(bfDate);
                        empMessage.setEndDate(afDate);
                        boolean isAvailable = PstEmpMessage.checkOID(msgId);
                        if(isAvailable){
                            PstEmpMessage.updateExc(empMessage);
                        }else{
                            PstEmpMessage.insertExc(empMessage);
                        }
                        
                    //}///
                }
		
            }//
            //PROSES PENGIRIMAN PESAN
            
                System.out.println("-----------------------------------------------");
                System.out.println("----------- START SEND MESSAGE ----------------");
                System.out.println("-----------------------------------------------");
                //PESAN AKAN DIKIRIM PADA SATU MESIN TERLEBIH DAHULU
                Vector vList = new Vector(1,1);
                vList = SessEmpMessage.listDataEmpMessageReady(new Date());
                //KIRIMKAN PESAN KE MESIN ABSENSI
                for(int icount=0;icount<machineNumbers.length;icount++){
                    //iUseFingerPrint untuk menghitung banyak sidik jari yang dipergunakan
                    String isOn = FRMQueryString.requestString(request,"MACHINE"+icount);
                    if(isOn.equals("ON")){
                        //System.out.println("- PESAN PADA MESIN : "+icount);
                        I_Machine i_Machine = MachineBroker.getMachineByNumber(machineNumbers[icount]);
                        i_Machine.processClearMessage();
                        for(int j=0;j<vList.size();j++){
                            EmpMessage empMessage = new EmpMessage();
                            Employee emp = new Employee();
                            EmployeeUp empUp = new EmployeeUp();

                            Vector vTemp = (Vector) vList.get(j);
                            emp = (Employee)vTemp.get(0);
                            empMessage = (EmpMessage)vTemp.get(1);
                            empUp.setBarcode(emp.getBarcodeNumber());

                            //System.out.println("- PESAN : "+empMessage.getEmployeeId()+" = "+empMessage.getMessage());
                            boolean status = i_Machine.processSendMessage(empUp, empMessage.getMessage());
                        }
                    }
                }
                //KIRIMKAN PESAN KE MESIN CANTEEN
                for(int icount=0;icount<machineNumbersCanteen.length;icount++){
                    //iUseFingerPrint untuk menghitung banyak sidik jari yang dipergunakan
                    String isOn = FRMQueryString.requestString(request,"MACHINE_CANTEEN"+icount);
                    if(isOn.equals("ON")){
                        //System.out.println("- PESAN PADA MESIN CANTEEN : "+icount);
                        I_Machine i_Machine = MachineBroker.getMachineByNumber(machineNumbersCanteen[icount]);
                        i_Machine.processClearMessage();
                        for(int j=0;j<vList.size();j++){
                            EmpMessage empMessage = new EmpMessage();
                            Employee emp = new Employee();
                            EmployeeUp empUp = new EmployeeUp();

                            Vector vTemp = (Vector) vList.get(j);
                            emp = (Employee) vTemp.get(0);
                            empMessage = (EmpMessage) vTemp.get(1);
                            empUp.setBarcode(emp.getBarcodeNumber());
                            
                            System.out.println("- PESAN : "+emp.getFullName()+" = "+empMessage.getMessage());
                            boolean status = i_Machine.processSendMessage(empUp, empMessage.getMessage());
                        }
                    }
                }
        }
    }
        
    ControlLine ctrLine = new ControlLine();
    SrcEmployee srcEmployee = new SrcEmployee();
    CtrlEmployee ctrlEmployee = new CtrlEmployee(request);
    FrmSrcEmployee frmSrcEmployee = new FrmSrcEmployee(request, srcEmployee);
    frmSrcEmployee.requestEntityObject(srcEmployee);
    
    String gotoEmpName = srcEmployee.getName();
    String gotoEmpPayroll = srcEmployee.getEmpnumber();
    String gotoEmpBarcode = "";
    long gotoEmpCat = srcEmployee.getEmpCategory();;
    long gotoDept = srcEmployee.getDepartment();
    long gotoEmpSec = srcEmployee.getSection();
    long gotoEmpPos = srcEmployee.getPosition();
    
    int start = FRMQueryString.requestInt(request,"start");
    final int recordToGet = 10;
    int vectSize = 0;
    
    SessEmpMessage sessEmpEmployee = new SessEmpMessage();
   Vector listEmployee = new Vector(1,1);
   if(iCommand == Command.GOTO){
        vectSize = sessEmpEmployee.countDataEmpMessage(srcEmployee);
        if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)
          ||(iCommand==Command.LAST)||(iCommand==Command.LIST))
        {
            start = ctrlEmployee.actionList(iCommand, start, vectSize, recordToGet);
        }else{
            start = 0;
        }
       try{
            listEmployee = sessEmpEmployee.listDataEmpMessage(srcEmployee, start, recordToGet,PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]);
       }catch(Exception ex){

       }
   }
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Message Management</title>
<script language="JavaScript">
function cmdSave() {
	document.frmMessage.command.value = "<%=String.valueOf(Command.SAVE)%>";
	document.frmMessage.action = "message.jsp";
	document.frmMessage.submit();
}

function deptChange() {
	document.frmMessage.command.value = "<%=String.valueOf(Command.GOTO)%>";
	document.frmMessage.hidden_goto_dept.value = document.frmMessage.DEPARTMENT_ID.value;
	document.frmMessage.action = "message.jsp";
	document.frmMessage.submit();
}

function cmdSearch() {
	document.frmMessage.command.value = "<%=String.valueOf(Command.GOTO)%>";
	document.frmMessage.hidden_goto_empname.value = document.frmMessage.EMP_NAME.value;
	document.frmMessage.hidden_goto_emppayroll.value = document.frmMessage.EMP_PAYROLL_NUM.value;
	document.frmMessage.hidden_goto_barcode.value = document.frmMessage.EMP_BARCODE.value;		
	document.frmMessage.hidden_goto_empcat.value = document.frmMessage.CATEGORY_ID.value;
	document.frmMessage.hidden_goto_dept.value = document.frmMessage.DEPARTMENT_ID.value;
	document.frmMessage.hidden_goto_empsec.value = document.frmMessage.SECTION_ID.value;
	document.frmMessage.hidden_goto_emppos.value = document.frmMessage.POSITION_ID.value;										
	document.frmMessage.action = "message.jsp";
	document.frmMessage.submit();
}

//-------------- script control line -------------------
function MM_swapImgRestore() 
{ //v3.0
	var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() 
{ //v3.0
	var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
	var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
	if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) 
{ //v4.0
	var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
	d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
	if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
	for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
	if(!x && document.getElementById) x=document.getElementById(n); return x;
}

function MM_swapImage() 
{ //v3.0
	var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
	if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

function getThn(){
            <%=ControlDatePopup.writeDateCaller("frmMessage","start_date")%>
            <%=ControlDatePopup.writeDateCaller("frmMessage","end_date")%>
            
    }


    function hideObjectForDate(index){
        if(index==1){
            <%//=ControlDatePopup.writeDateHideObj("frdp", FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_DP_STATUS])%>
        }
    }

    function showObjectForDate(){
        <%//=ControlDatePopup.writeDateShowObj("frdp", FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_DP_STATUS])%>
    }

</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
    
<link rel="stylesheet" href="<%=approot%>/styles/calendar.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "headerscript" --> 
<SCRIPT language=JavaScript>
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
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSaveOn.jpg')">
<!-- Untuk Calender-->
<%=(ControlDatePopup.writeTable(approot))%>
<!-- End Calender-->
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --><!-- #EndEditable -->    </td>
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->System 
                  &gt; Timekeeping &gt; Message Management<!-- #EndEditable --> 
                  </strong></font> </td>
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
		    				  <!-- #BeginEditable "content" -->
                                <% if (privStart) { %>
                                    <form name="frmMessage" method="post" action="">
                                    <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
                                    <input type="hidden" name="hidden_goto_empname" value="<%=gotoEmpName%>">
                                    <input type="hidden" name="hidden_goto_emppayroll" value="<%=gotoEmpPayroll%>">
                                    <input type="hidden" name="hidden_goto_barcode" value="<%=gotoEmpBarcode%>">																		
                                    <input type="hidden" name="hidden_goto_empcat" value="<%=String.valueOf(gotoEmpCat)%>">
                                    <input type="hidden" name="hidden_goto_dept" value="<%=String.valueOf(gotoDept)%>">
                                    <input type="hidden" name="hidden_goto_empsec" value="<%=String.valueOf(gotoEmpSec)%>">
                                    <input type="hidden" name="hidden_goto_emppos" value="<%=String.valueOf(gotoEmpPos)%>">																																													

                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                            <td>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td colspan="3"><b><u><font color="#FF0000">INFORMATION</font></u></b>: 
                                                  <br>
                                                  Use this form to <b>INSERT</b> 
                                                  message to database and <b>UPLOAD</b> 
                                                  message to timekeeping machine 
                                                  simultaneously. <br>
                                                  Please make sure that all of 
                                                  timekeeping machines are switched 
                                                  on.
                                                  
                                                  <br>                                                  
                                                  <hr>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="11%">Upload to Machine</td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                            <% 
                                                    for(int i=0;i<machineNumbers.length;i++){
                                            %>                                                            
                                            <input type="checkbox" name="MACHINE<%=String.valueOf(i) %>" value="ON" checked="checked" /> M-<%=String.valueOf(i)%>&nbsp;&nbsp;						
                                           <%}
                                            %>
                                            <% 
                                                    for(int i=0;i<machineNumbersCanteen.length;i++){
                                            %>                                                            
                                            <input type="checkbox" name="MACHINE_CANTEEN<%=String.valueOf(i) %>" value="ON" checked="checked" /> C-<%=String.valueOf(i)%>&nbsp;&nbsp;						
                                           <%}
                                            %>
                                       <!--         <input type="checkbox" name="MACHINE01" value="ON" checked="checked" /> M-1&nbsp;&nbsp;
                                                <input type="checkbox" name="MACHINE02" value="ON" checked="checked" /> M-2&nbsp;&nbsp;
                                                <input type="checkbox" name="MACHINE03" value="ON" checked="checked" /> M-3&nbsp;&nbsp;
                                                <input type="checkbox" name="MACHINE04" value="ON" checked="checked" /> M-4&nbsp;&nbsp; -->
                                                </td>
                                              </tr>

                                              <tr> 
                                                <td width="11%">Name</td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                                  <input type="text" name="<%=FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_NAME]%>"  value="<%=gotoEmpName%>" class="elemenForm" size="40">
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="11%">Payroll Number</td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                                  <input type="text" name="<%=FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_EMPNUMBER]%>"  value="<%=gotoEmpPayroll%>" class="elemenForm">
                                                </td>
                                              </tr>
                                 <!--             <tr> 
                                                <td width="11%">Barcode</td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                                  <input type="text" name="EMP_BARCODE"  value="<%//=gotoEmpBarcode%>" class="elemenForm">
                                                </td>
                                              </tr> -->
                                              <tr> 
                                                <td width="11%">Category</td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                                  <% 
                                                        Vector cat_value = new Vector(1,1);
                                                        Vector cat_key = new Vector(1,1);        
                                                        cat_value.add("0");
                                                        cat_key.add("all category ...");                                                          
                                                        Vector listCat = PstEmpCategory.list(0, 0, "", " EMP_CATEGORY ");                                                        
                                                        for (int i = 0; i < listCat.size(); i++) {
                                                                        EmpCategory cat = (EmpCategory) listCat.get(i);
                                                                        cat_key.add(cat.getEmpCategory());
                                                                        cat_value.add(String.valueOf(cat.getOID()));
                                                        }
                                                %>
                                                  <%= ControlCombo.draw(FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_EMP_CATEGORY],"formElemen",null, ""+ gotoEmpCat, cat_value, cat_key, "") %> </td>
                                              </tr>
                                              <tr> 
                                                <td width="11%"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                                  <% 
                                                        Vector dept_value = new Vector(1,1);
                                                        Vector dept_key = new Vector(1,1);
                                                        Vector listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                        dept_key.add("all department...");
                                                        dept_value.add("0");
                                                        String selectDept = String.valueOf(gotoDept);
                                                        for (int i = 0; i < listDept.size(); i++) {
                                                            Department dept = (Department) listDept.get(i);
                                                            dept_key.add(dept.getDepartment());
                                                            dept_value.add(String.valueOf(dept.getOID()));
                                                        }
                                                    %>
                                                  <%//= ControlCombo.draw("DEPARTMENT_ID","formElemen",null, selectDept, dept_value, dept_key, "onchange=\"javascript:deptChange();\"") %>
                                                  <%= ControlCombo.draw(FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_DEPARTMENT],"formElemen",null, selectDept, dept_value, dept_key, "") %> </td>
                                              </tr>
                                              <tr> 
                                                <td width="11%"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                                  <% 
                                                        Vector sec_value = new Vector(1,1);
                                                        Vector sec_key = new Vector(1,1); 
                                                        sec_value.add("0");
                                                        sec_key.add("all section ...");
                                                        Vector listSec = PstSection.list(0, 0, "", " DEPARTMENT_ID, SECTION ");
                                                        String selectSect = String.valueOf(gotoEmpSec);
                                                        for (int i = 0; i < listSec.size(); i++) {
                                                                        Section sec = (Section) listSec.get(i);
                                                                        sec_key.add(sec.getSection());
                                                                        sec_value.add(String.valueOf(sec.getOID()));
                                                        }
                                                %>
                                                  <%= ControlCombo.draw(FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_SECTION],"formElemen",null, ""+ selectSect, sec_value, sec_key, "") %> </td>
                                              </tr>
                                              <tr> 
                                                <td width="11%">Position</td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                                  <% 
														Vector pos_value = new Vector(1,1);
														Vector pos_key = new Vector(1,1); 
														pos_value.add("0");
														pos_key.add("all position ...");                                                       
														Vector listPos = PstPosition.list(0, 0, "", " POSITION ");                                                            
														for (int i = 0; i < listPos.size(); i++) {
																Position pos = (Position) listPos.get(i);
																pos_key.add(pos.getPosition());
																pos_value.add(String.valueOf(pos.getOID()));
														}
													%>
                                                  <%= ControlCombo.draw(FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_POSITION],"formElemen",null, String.valueOf(gotoEmpPos), pos_value, pos_key, "") %> </td>
                                              </tr>
                                              <tr> 
                                                <td width="11%">&nbsp;</td>
                                                <td width="1%">&nbsp;</td>
                                                <td width="88%"> 
                                                  <input type="submit" name="Submit" value="Search Employee" onClick="javascript:cmdSearch()">
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="11%">&nbsp; </td>
                                                <td width="1%">&nbsp;</td>
                                                <td width="88%">&nbsp; </td>
                                              </tr>
                                            </table>
                                        </td>
                                      </tr>
                                      <tr>
                                        <td>
                                          <% if (listEmployee.size() > 0) { %>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr>
                                                <td><%=drawList(listEmployee)%></td>
                                              </tr>
                                              <tr>
                                                <td><%
						ctrLine.setLocationImg(approot+"/images");
                                                ctrLine.initDefault();
                                                %><%=ctrLine.drawImageListLimit(iCommand,vectSize,start,recordToGet)%>
                                                </td>
                                              </tr>
                                              <tr>
                                                <td>
                                                  <table border="0" cellpadding="0" cellspacing="0" width="100">
                                                    <tr> 
                                                      <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                      <td width="24"><a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSaveOn.jpg',1)"><img name="Image10" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save"></a></td>
                                                      <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                      <td width="50" class="command" nowrap><a href="javascript:cmdSave()">Save</a></td>
                                                    </tr> 
                                                  </table>
                                                </td>
                                              </tr>
                                            </table>
                                          <% } else{
                                                if(vMsg.size()>0){
                                                    %>
                                                    <h2><font color="blue">UPLOAD DATA IS FINISH!</font></h2>
                                                    <br><font color="red">but some data is failed to upload :</font>
                                                    <%
                                                    for(int i=0;i<vMsg.size();i++){
                                                        String strWrite = "<br>"+(i+1)+") "+(String)vMsg.get(i);
                                                        out.println(strWrite);
                                                    }
                                                }else{
                                                    if(iCommand == Command.SAVE){
                                                    %>
                                                    <h2><font color="blue">UPLOAD DATA SUCCESS!</font></h2>
                                                    <%}
                                                }
                                          
                                          }%>
                                          </td>
                                          </tr>
                                        </table>
                                    </form>
                                <% } 
                                   else
                                   {
                                %>
                                <div align="center">You do not have sufficient privilege to access this page.</div>
                                <% } %>
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
  <tr> 
    <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
<!-- #EndEditable --> 
      </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>

