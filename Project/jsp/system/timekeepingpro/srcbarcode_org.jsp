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
        ctrlist.addHeader("Payroll","10%");
        ctrlist.addHeader("Full Name","35%");
        ctrlist.addHeader("Position","35%");
        ctrlist.addHeader("Barcode","10%");
        //ctrlist.addHeader("PIN","10%");


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
        for (int i = 0; i < objectClass.size(); i++) 
		{
            Employee employee = (Employee) objectClass.get(i);
            Vector rowx = new Vector();
            rowx.add(employee.getEmployeeNum());
            rowx.add(employee.getFullName());
            rowx.add(position.get(String.valueOf(employee.getPositionId())));
            String barcode = "";
            String empNickName = "";
            String empFingerId = "";
           
            try 
			{
                barcode = employee.getBarcodeNumber();
           //     if(employee.getFullName().length()>10){
           //         empNickName = employee.getFullName().substring(0, 10);
           //     }else{
           //         empNickName = employee.getFullName(); 
           //     }
			//	empFingerId = employee.getEmpPin();
            }
            catch(Exception e){
		System.out.println("exc when fetch barcode : " + e.toString());
            }

            if ((barcode != null) && (barcode.length() > 0)) 
			{
                rowx.add("<input type=\"hidden\" name=\"employee_id\" value=\"" + employee.getOID() + "\">"+
			"<input type=\"hidden\" class=\"elementForm\" name=\"old_barcode_number\" value=\"" + barcode + "\">"+
			"<input type=\"hidden\" size=\"10\" class=\"elementForm\" name=\"barcode_number\" value=\"" + barcode + "\" >"+
                        "<input type=\"textbox\" size=\"10\" class=\"elementForm\" name=\"barcode_number\" value=\"" + barcode + "\" disabled=\"disabled\">");
	/*			<input type=\"hidden\" name=\"employee_finger_id\" value=\"" + empFingerId + "\">
				<input type=\"hidden\" name=\"employee_nick_name\" value=\"" + empNickName + "\">");
				*/
            }
            else 
			{
                rowx.add("<input type=\"hidden\" name=\"employee_id\" value=\"" + employee.getOID() + "\">"+
			"<input type=\"hidden\" size=\"10\" class=\"elementForm\" name=\"old_barcode_number\" value=\"\">"+
			"<input type=\"textbox\" size=\"10\" class=\"elementForm\" name=\"barcode_number\" value=\"\">");
	/*			<input type=\"hidden\" name=\"employee_finger_id\" value=\"" + empFingerId + "\">
				<input type=\"hidden\" name=\"employee_nick_name\" value=\"" + empNickName + "\">"); */
            }
			//tidak dibutuhkan No PIN (khusus Intimas)
			//By Yunny
            /*if ((empPin != null) && (empPin.length() > 0)) 
			{
                rowx.add("<input type=\"textbox\" size=\"10\" class=\"elementForm\" name=\"emp_pin\" value=\"" + empPin + "\">");
            }
            else 
			{
                rowx.add("<input type=\"textbox\" size=\"10\" class=\"elementForm\" name=\"emp_pin\" value=\"\">");
            }*/

            lstData.add(rowx);            
        }

        ctrlist.setLinkSufix("')");
        return ctrlist.draw();
    }
%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    String gotoEmpName = FRMQueryString.requestString(request, "hidden_goto_empname");
    String gotoEmpPayroll = FRMQueryString.requestString(request, "hidden_goto_emppayroll");
    String gotoEmpBarcode = FRMQueryString.requestString(request, "hidden_goto_barcode");	
    long gotoEmpCat = FRMQueryString.requestLong(request, "hidden_goto_empcat");
    long gotoDept = FRMQueryString.requestLong(request, "hidden_goto_dept");
    long gotoEmpSec = FRMQueryString.requestLong(request, "hidden_goto_empsec");					
    long gotoEmpPos = FRMQueryString.requestLong(request, "hidden_goto_emppos");   
    String s_barcode_number = null;
    String s_old_barcode_number = null;
    String s_emp_pin = null;
    String machineNumber = "01";
    String[] machineNumbers;
    Vector vMsg = new Vector(1,1);
  //  SessEmployee.setAllEmpFingerPrintNumber();
    int iUseFingerPrint = 0;
	machineNumber = String.valueOf(PstSystemProperty.getValueByName("ABSEN_TMA_NO"));
	iUseFingerPrint = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("FINGER_PRINT_NUMBER")));
	StringTokenizer strTokenizer = new StringTokenizer(machineNumber,",");
	machineNumbers = new String[strTokenizer.countTokens()];
	
	System.out.println(" CEK ABSEN MACHINE :::::::::: "+machineNumbers.length);
	// Proses jika command adalah ASVE	
    if (iCommand == Command.SAVE) {
	int count = 0;
        while(strTokenizer.hasMoreTokens()){
            machineNumbers[count] = strTokenizer.nextToken();
            System.out.println("ABSEN MACHINE :::::::::: "+machineNumbers[count]);
            count ++;
        }
	// Inisialisasi variable yang meng-handle nilai2 berikut
        String[] employee_id = null;		
        String[] barcode_number = null;
        String[] old_barcode_number = null;
   //     String[] nick_name = null;
   //     String[] finger_id = null;				

	// Mengambil array nilai2 berikut
        try {
            employee_id = request.getParameterValues("employee_id");
            barcode_number = request.getParameterValues("barcode_number");
            old_barcode_number = request.getParameterValues("old_barcode_number");
	//		nick_name = request.getParameterValues("employee_nick_name");
	//		finger_id = request.getParameterValues("employee_finger_id");
            //tidak dibutuhkan No PIN (khusus Intimas)
            //By Yunny
            //emp_pin = request.getParameterValues("emp_pin");
        }catch (Exception e){
            System.out.println("Err : "+e.toString());
        }

		// Jika array barcode_number tidak kosong (ada/minimal satu barcode employee diisi)	
        
        if ((barcode_number != null) && (barcode_number.length > 0)){
            for (int i = 0; i < barcode_number.length; i++){
                EmployeeUp employeeUpOld = new EmployeeUp();
                EmployeeUp employeeUp = new EmployeeUp();
                Employee employee = new Employee();
				// new barcode number	
                try{
                   long oidEmp = Long.parseLong(employee_id[i]);
                   employee = PstEmployee.fetchExc(oidEmp);
                } catch (Exception e){} 
                StringTokenizer strToken = new StringTokenizer(employee.getFullName()," ");
                String strName = "";
                while(strToken.hasMoreTokens()){
					strName = strToken.nextToken();
					/*if(!(
					(strName.toLowerCase().equals("i"))||
					(strName.toLowerCase().equals("ni"))||
					(strName.toLowerCase().equals("made"))||
					(strName.toLowerCase().equals(""))||
					(strName.toLowerCase().equals(""))
					)){
					}*/
                }
                if ((barcode_number[i].length() > 0)){
                    try{
                       s_barcode_number = String.valueOf(barcode_number[i]);
                       employeeUpOld.setBarcode(barcode_number[i]);
                       employeeUpOld.setName(strName);
                       employeeUp.setBarcode(barcode_number[i]);
                    } catch (Exception e){}
                }else {
                    s_barcode_number = "";
                }

				// old barcode number
                if ((old_barcode_number[i].length() > 0)){
                    try {
                        s_old_barcode_number = String.valueOf(old_barcode_number[i]);
                        employeeUp.setBarcode(barcode_number[i]);
                    } catch (Exception e){}
                }else {
                    s_old_barcode_number = "";
                }
                
                if ((!s_barcode_number.equals("")) && (s_barcode_number.length() > 0)) {
                    if ((s_old_barcode_number.equals("")) || (s_old_barcode_number.length() <= 0)) {
                        String strMax = employee.getEmpPin();
                        System.out.println("___________________________________");
                        int iMax = 0;
                        if(strMax.equals("")||strMax.length()<=0){
                            iMax = SessEmployee.getMaxFingerPrintNumber();
                            if(iMax>-1){iMax+=iUseFingerPrint;}else{iMax = iUseFingerPrint;}
                            //iMax+=iUseFingerPrint;
                            strMax = String.valueOf(iMax);
                        }
                    //    System.out.println("MAX ::::  "+strMax);
                        employeeUp.setUserHexId(Integer.parseInt(strMax));
                  //      System.out.println("HEX :::: "+Integer.toHexString(Integer.parseInt(strMax)));
                        employeeUp.setName(strName);
                        for(int icount=0;icount<machineNumbers.length;icount++){
                            //iUseFingerPrint untuk menghitung banyak sidik jari yang dipergunakan
                            I_Machine i_Machine = MachineBroker.getMachineByNumber(machineNumbers[icount]);
                            boolean status = i_Machine.processUpload(employeeUp);
                            if(!status && (icount == 0)){
                                String strMsg = strName+" ["+employee.getBarcodeNumber()+"]";
                                vMsg.add(strMsg);;
                            }
                        }
                        PstEmployee.updateBarcodeAndPin(Long.parseLong(employee_id[i]), s_barcode_number,strMax);
                        strMax = "";
                    }
                }
                else{
                    if ((!s_old_barcode_number.equals("")) && (s_old_barcode_number.length() > 0)) {
                        System.out.println("====== CLEAR DATA");
                        PstEmployee.updateBarcode(Long.parseLong(employee_id[i]), null);
                    }
                }
            }
        }
    }

    int start = 0;
    int recordToGet = 0;
	
	/*
    String whereClause = PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + String.valueOf(gotoDept);
        whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 0 ";
    String orderClause = "";]
	*/
	
    String whereClause = "";
	if(gotoEmpName!=null && gotoEmpName.length()>0){
		   whereClause = PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " LIKE '%" + gotoEmpName.trim() + "%'";
	}		   

	if(gotoEmpPayroll!=null && gotoEmpPayroll.length()>0){	
		if(whereClause!=null && whereClause.length()>0){	   	
		   whereClause += " AND "  + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " LIKE '%" + gotoEmpPayroll.trim() + "%'";
		}else{
		   whereClause += PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " LIKE '%" + gotoEmpPayroll.trim() + "%'";		
		}	 
	}  

	if(gotoEmpBarcode!=null && gotoEmpBarcode.length()>0){	
		if(whereClause!=null && whereClause.length()>0){	   	
		   whereClause += " AND "  + PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER] + " LIKE '%" + gotoEmpBarcode.trim() + "%'";
		}else{
		   whereClause += PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER] + " LIKE '%" + gotoEmpBarcode.trim() + "%'";		
		}	 
	}  
	
	if(gotoEmpCat>0){
		if(whereClause!=null && whereClause.length()>0){	   				
	       whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] + " = " + String.valueOf(gotoEmpCat);
		}else{
	       whereClause += PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] + " = " + String.valueOf(gotoEmpCat);		
		}   
	}
		   
	if(gotoEmpSec>0){
		if(whereClause!=null && whereClause.length()>0){	   					
	       whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " = " + String.valueOf(gotoEmpSec);
		}else{
	       whereClause += PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " = " + String.valueOf(gotoEmpSec);		
		}   
	}	   
	
	if(gotoEmpPos>0){
		if(whereClause!=null && whereClause.length()>0){	   					
	       whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + " = " + String.valueOf(gotoEmpPos);		   		   		   		   
		}else{
	       whereClause += PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + " = " + String.valueOf(gotoEmpPos);		   		   		   		   		
		}
	}

	if(gotoDept>0){
		if(whereClause!=null && whereClause.length()>0){	   					
	       whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + String.valueOf(gotoDept);		   		   		   		   
		}else{
	       whereClause += PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + String.valueOf(gotoDept);		   		   		   		   		
		}
	}
		
	if(whereClause!=null && whereClause.length()>0){	   							
           whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 0 ";
	}else{
           whereClause += PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 0 ";	
	}	   

	/*
	if(whereClause!=null && whereClause.length()>0){	   							
	       whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + String.valueOf(gotoDept);		   
           whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 0 ";
	}else{
	       whereClause += PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + String.valueOf(gotoDept);		   
           whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 0 ";	
	}	   
    */	

	String orderClause = "" + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
		
    Vector listEmp = new Vector(1,1);
	if(iCommand == Command.GOTO)
	{
    	listEmp = PstEmployee.list(start, recordToGet, whereClause, orderClause);
	}
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Barcode Management</title>
<script language="JavaScript">
function cmdSave() {
	document.frmbarcode.command.value = "<%=String.valueOf(Command.SAVE)%>";
	document.frmbarcode.hidden_goto_dept.value = document.frmbarcode.DEPARTMENT_ID.value;
	document.frmbarcode.action = "srcbarcode.jsp";
	document.frmbarcode.submit();
}

function deptChange() {
	document.frmbarcode.command.value = "<%=String.valueOf(Command.GOTO)%>";
	document.frmbarcode.hidden_goto_dept.value = document.frmbarcode.DEPARTMENT_ID.value;
	document.frmbarcode.action = "srcbarcode.jsp";
	document.frmbarcode.submit();
}

function cmdSearch() {
	document.frmbarcode.command.value = "<%=String.valueOf(Command.GOTO)%>";
	document.frmbarcode.hidden_goto_empname.value = document.frmbarcode.EMP_NAME.value;
	document.frmbarcode.hidden_goto_emppayroll.value = document.frmbarcode.EMP_PAYROLL_NUM.value;
	document.frmbarcode.hidden_goto_barcode.value = document.frmbarcode.EMP_BARCODE.value;		
	document.frmbarcode.hidden_goto_empcat.value = document.frmbarcode.CATEGORY_ID.value;
	document.frmbarcode.hidden_goto_dept.value = document.frmbarcode.DEPARTMENT_ID.value;
	document.frmbarcode.hidden_goto_empsec.value = document.frmbarcode.SECTION_ID.value;
	document.frmbarcode.hidden_goto_emppos.value = document.frmbarcode.POSITION_ID.value;										
	document.frmbarcode.action = "srcbarcode.jsp";
	document.frmbarcode.submit();
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
                  &gt; Timekeeping &gt; Insert &amp; Upload Barcode<!-- #EndEditable --> 
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
                                    <form name="frmbarcode" method="post" action="">
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
                                                <td colspan="3"><b><u><font color="#FF0000">ATTENTION</font></u></b>: 
                                                  <br>
                                                  Use this form to <b>INSERT</b> 
                                                  barcode to database and <b>UPLOAD</b> 
                                                  barcode to timekeeping machine 
                                                  simultaneously. <br>
                                                  Please make sure that all of 
                                                  timekeeping machines are switched 
                                                  on.<br>                                                  
                                                  <hr>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="11%">Upload to Machine</td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                            <% 
                                                    for(int i=0;i<machineNumbers.length;i++){
                                                            %>                                                            <input type="checkbox" name="MACHINE<%=String.valueOf(i) %>" value="ON" checked="checked" /> M-<%=String.valueOf(i)%>&nbsp;&nbsp;						

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
                                                  <input type="text" name="EMP_NAME"  value="<%=gotoEmpName%>" class="elemenForm" size="40">
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="11%">Payroll Number</td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                                  <input type="text" name="EMP_PAYROLL_NUM"  value="<%=gotoEmpPayroll%>" class="elemenForm">
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="11%">Barcode</td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                                  <input type="text" name="EMP_BARCODE"  value="<%=gotoEmpBarcode%>" class="elemenForm">
                                                </td>
                                              </tr>
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
                                                  <%= ControlCombo.draw("CATEGORY_ID","formElemen",null, ""+ gotoEmpCat, cat_value, cat_key, "") %> </td>
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
                                                  <%= ControlCombo.draw("DEPARTMENT_ID","formElemen",null, selectDept, dept_value, dept_key, "") %> </td>
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
                                                  <%= ControlCombo.draw("SECTION_ID","formElemen",null, ""+ selectSect, sec_value, sec_key, "") %> </td>
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
                                                  <%= ControlCombo.draw("POSITION_ID","formElemen",null, String.valueOf(gotoEmpPos), pos_value, pos_key, "") %> </td>
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
                                          <% if (listEmp.size() > 0) { %>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr>
                                                <td><%=drawList(listEmp)%></td>
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
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>

