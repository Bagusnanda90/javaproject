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
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.utility.service.tma.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_BARCODE, AppObjInfo.OBJ_UPLOAD_PER_EMPLOYEE); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privStart=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_START));
%>
<%!
    public String drawList(Vector objectClass, int st){
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("No.","2%");
        ctrlist.addHeader("Payroll","8%");
        ctrlist.addHeader("Full Name","15%");
        ctrlist.addHeader("Position","10%");
        ctrlist.addHeader("Barcode Number","10%");

        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.reset();

        Hashtable position = new Hashtable();
        Vector listPosition = PstPosition.listAll();
        position.put("0", "-");
        for (int ls = 0; ls < listPosition.size(); ls++) {
            Position pos = (Position) listPosition.get(ls);
            position.put(String.valueOf(pos.getOID()), pos.getPosition());
        }
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
            rowx.add(String.valueOf(st + 1 + i));
            rowx.add(employee.getEmployeeNum());
            rowx.add(employee.getFullName());
            rowx.add(position.get(String.valueOf(employee.getPositionId())));
            String barcode = "";
            try {
                barcode = employee.getBarcodeNumber();
            }
            catch(Exception e) {
            }

            if ((barcode != null) && (barcode.length() > 0)) {
                rowx.add("<input type=\"hidden\" name=\"employee_id\" value=\"" + employee.getOID() + "\">" + 
                         "<input type=\"hidden\" class=\"elementForm\" name=\"old_barcode_number\" value=\"" + employee.getBarcodeNumber() + "\"><input type=\"textbox\" class=\"elementForm\" name=\"barcode_number\" value=\"" + employee.getBarcodeNumber() + "\" disabled>");
            }
            else {
                rowx.add("<input type=\"hidden\" name=\"employee_id\" value=\"" + employee.getOID() + "\">" + 
                         "<input type=\"hidden\" class=\"elementForm\" name=\"old_barcode_number\" value=\"\"><input type=\"textbox\" class=\"elementForm\" name=\"barcode_number\" value=\"\" disabled>");
            }
            lstData.add(rowx);
            //lstLinkData.add();
        }

        ctrlist.setLinkSufix("')");
        return ctrlist.draw();
    }
%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    long gotoDept = FRMQueryString.requestLong(request, "hidden_goto_dept");
	long gotoEmp = FRMQueryString.requestLong(request, "hidden_goto_emp");
    int start = FRMQueryString.requestInt(request, "start");

    String s_barcode_number = null;
    String s_old_barcode_number = null;
    String sOwnError = "";
    ControlLine ctrLine = new ControlLine();
    CtrlEmployee ctrlEmployee = new CtrlEmployee(request);

    String machineNumber = "01";
    String[] machineNumbers;
    
    int vectSize = 0;

    //out.print(iCommand);

    if (iCommand == Command.GOTO) {
        iCommand = Command.LIST;
    }
    //out.print(iCommand);

    if (iCommand == Command.SAVE) {
        //System.out.println("iCommand == Command.SAVE");
        String[] barcode_number = null;
        String[] employee_id = null;
        String[] old_barcode_number = null;

        try {
            employee_id = request.getParameterValues("employee_id");
            barcode_number = request.getParameterValues("barcode_number");
            old_barcode_number = request.getParameterValues("old_barcode_number");
        }
        catch (Exception e) {
            System.out.println("Exception on getParameterValues(): " + e);
        }
		
		System.out.println("employee_id : "+employee_id);
		System.out.println("<br>");
		System.out.println("barcode_number : "+barcode_number);
		System.out.println("<br>");
		System.out.println("old_barcode_number : "+old_barcode_number);
		
        machineNumber = String.valueOf(PstSystemProperty.getValueByName("ABSEN_TMA_NO"));
        StringTokenizer strTokenizer = new StringTokenizer(machineNumber,",");
        machineNumbers = new String[strTokenizer.countTokens()];
        int count = 0;
        while(strTokenizer.hasMoreTokens()){
            machineNumbers[count] = strTokenizer.nextToken();
            System.out.println("ABSEN MACHINE :::::::::: "+machineNumbers[count]);
            count ++;
        }
        if ((old_barcode_number != null) && (old_barcode_number.length > 0)) {
            //System.out.println("inside if... ");
            try {
                AccessTMA svc = new AccessTMA();
                int numUpload = 0;
                for (int i = 0; i < old_barcode_number.length; i++) {

                    if ((old_barcode_number[i].length() > 0)) {
                        try {
                            s_old_barcode_number = String.valueOf(old_barcode_number[i]);
                        } catch (Exception e) {}
                    }
                    else {
                        s_old_barcode_number = null;
                    }
                    //System.out.println(String.valueOf(employee_id[i]) + " - " + s_barcode_number);
                    //... updating HR_EMPLOYEE ...
                    /* 
                        PstEmployee.updateBarcode(Long.parseLong(employee_id[i]), s_barcode_number);
                    */
                    //System.out.println("old_barcode_number : " + s_old_barcode_number);
                    //... upload data to Tidex ...

                    //AccessTMA svc = new AccessTMA();
                    if ((s_old_barcode_number != null) && (s_old_barcode_number.length() > 0)) {
                        
                        System.out.println("   Processing barcode : " + s_old_barcode_number);
                        //svc.executeCommand(svc.DELETE, "01", s_old_barcode_number);
                        if(machineNumbers.length>0 && !(machineNumbers[0].equals("")) && machineNumbers[0].length()>0){
                            svc.executeCommand(svc.UPLOAD, machineNumbers[0], s_old_barcode_number + "\\000000\\T@@");
                        }
                        //svc.executeCommand(svc.DELETE, "02", s_old_barcode_number);
                        if(machineNumbers.length>1 && !(machineNumbers[1].equals("")) && machineNumbers[1].length()>0){
                            svc.executeCommand(svc.UPLOAD, machineNumbers[1], s_old_barcode_number + "\\000000\\T@@");
                        }
                    //}
                    //if ((s_old_barcode_number != null) && (s_old_barcode_number.length() > 0)) {
                        numUpload++;
                        //System.out.println("   Uploading barcode: " + s_old_barcode_number);
                        //svc.executeCommand(svc.UPLOAD, "01", s_old_barcode_number + "\\000000\\T@@");
                        //svc.executeCommand(svc.UPLOAD, "02", s_old_barcode_number + "\\000000\\T@@");
                    }
                }
                System.out.println("  Total uploaded barcode = " + numUpload);
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
        iCommand = Command.LIST;
    }

    //int start = 0;
    int recordToGet = 50;
    String whereClause = PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + String.valueOf(gotoDept);
        whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 0 "+
		" AND "+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+"="+gotoEmp;
    String orderClause = "";
    vectSize = PstEmployee.getCount(whereClause);

    if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)||
	(iCommand==Command.LAST)||(iCommand==Command.LIST))
            start = ctrlEmployee.actionList(iCommand, start, vectSize, recordToGet);
    //out.println(" start = " + start);
    Vector listEmp = new Vector(1,1);
    listEmp = PstEmployee.list(start, recordToGet, whereClause, orderClause);
    
    //out.println("listEmp.size() = " + listEmp.size());
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Barcode Management</title>
<script language="JavaScript">
	function cmdListFirst(){
		document.frmbarcode.command.value="<%=Command.FIRST%>";
		document.frmbarcode.action="upload2tma4emp.jsp";
		document.frmbarcode.submit();
	}

	function cmdListPrev(){
		document.frmbarcode.command.value="<%=Command.PREV%>";
		document.frmbarcode.action="upload2tma4emp.jsp";
		document.frmbarcode.submit();
	}

	function cmdListNext(){
		document.frmbarcode.command.value="<%=Command.NEXT%>";
		document.frmbarcode.action="upload2tma4emp.jsp";
		document.frmbarcode.submit();
	}

	function cmdListLast(){
		document.frmbarcode.command.value="<%=Command.LAST%>";
		document.frmbarcode.action="upload2tma4emp.jsp";
		document.frmbarcode.submit();
	}

    function cmdSave() {
        document.frmbarcode.command.value = "<%=Command.SAVE%>";
        document.frmbarcode.hidden_goto_dept.value = document.frmbarcode.DEPARTMENT_ID.value;
        var oBarcode = document.all.item("barcode_number");
        var oEmployee = document.all.item("employee_id");
        var oOldBarcode = document.all.item("old_barcode_number");
        for(i = 0; i < oBarcode.length; i++) {
            //alert(oBarcode[i].defaultValue + " - " + oBarcode[i].value);
            /*if (oBarcode[i].defaultValue != oBarcode[i].value) { */
                oBarcode[i].name = "barcode_number_changed";
                oEmployee[i].name = "employee_id_changed";
                oOldBarcode[i].name = "old_barcode_number_changed";
            /*}*/
        }
        document.frmbarcode.action = "upload2tma4emp.jsp";
        document.frmbarcode.submit();
    }

    function empChange() {
        document.frmbarcode.command.value = "<%=Command.GOTO%>";
        document.frmbarcode.hidden_goto_dept.value = document.frmbarcode.DEPARTMENT_ID.value;
		document.frmbarcode.hidden_goto_emp.value = document.frmbarcode.EMPLOYEE_ID.value;
        document.frmbarcode.action = "upload2tma4emp.jsp";
        document.frmbarcode.submit();
    }
	
	function deptChange() {
        document.frmbarcode.command.value = "<%=Command.SUBMIT%>";
        document.frmbarcode.hidden_goto_dept.value = document.frmbarcode.DEPARTMENT_ID.value;
        document.frmbarcode.action = "upload2tma4emp.jsp";
        document.frmbarcode.submit();
    }

    //-------------- script control line -------------------
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
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../../main/header.jsp" %>
      <!-- #EndEditable --> 
    </td>
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
                  &gt; Timekeeping &gt; Upload Barcode to Timekeeping Machine<!-- #EndEditable --> 
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
                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                    <input type="hidden" name="start" value="<%=start%>">
                                    <input type="hidden" name="hidden_goto_dept" value="<%=gotoDept%>">
									<input type="hidden" name="hidden_goto_emp" value="<%=gotoEmp%>">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                            <td>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td colspan="2"><b><u><font color="#FF0000">ATTENTION</font></u></b>: 
                                                  <br>
                                                  Use this form to <b>UPLOAD</b> 
                                                  barcode to timekeeping machine 
                                                  only. <br>
                                                  <br>
                                                  If you want to INSERT barcode 
                                                  to database and UPLOAD it to 
                                                  timekeeping machine instead, 
                                                  please use System &gt; Barcode 
                                                  &gt; Insert &amp; Upload Barcode<br>
                                                  If you want to INSERT barcode 
                                                  to database only and not to 
                                                  UPLOAD it to timekeeping machine 
                                                  instead, please use System &gt; 
                                                  Barcode &gt; Insert Barcode 
                                                  to Database 
                                                  <hr>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="9%"> 
                                                  <div align="right">Department 
                                                    : </div>
                                                </td>
                                                <td width="91%"> 
                                                  <% 
                                                        Vector dept_value = new Vector(1,1);
                                                        Vector dept_key = new Vector(1,1);
                                                        Vector listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                        dept_key.add("select...");
                                                        dept_value.add("0");
                                                        String selectDept = String.valueOf(gotoDept);
                                                        for (int i = 0; i < listDept.size(); i++) {
                                                            Department dept = (Department) listDept.get(i);
                                                            dept_key.add(dept.getDepartment());
                                                            dept_value.add(String.valueOf(dept.getOID()));
                                                        }
                                                    %>
                                                  <%= ControlCombo.draw("DEPARTMENT_ID","formElemen",null, selectDept, dept_value, dept_key, "onchange=\"javascript:deptChange();\"") %> </td>
                                              </tr>
                                              <tr>
                                                <td width="9%">
                                                  <div align="right">Employee 
                                                    : </div>
                                                </td>
                                                <td width="91%"> 
                                                  <%
													String whereEmp = " DEPARTMENT_ID = " + gotoDept+" AND RESIGNED = 0";
													String orderEmp = " EMPLOYEE_NUM ";
													Vector emp_value = new Vector(1,1);
													Vector emp_key = new Vector(1,1);
													emp_value.add("0");
													emp_key.add("select...");
													Vector listEmployee = PstEmployee.list(0, 0, whereEmp, orderEmp);
													String selectValueEmp = String.valueOf(gotoEmp);
													long oidEmp = 0;
													try{
														oidEmp = Long.parseLong(selectValueEmp);
													}
													catch(Exception e){
														oidEmp = 0;
													}
													String strName = "";
													for (int i = 0; i < listEmployee.size(); i++) {
															Employee emp = (Employee) listEmployee.get(i);
															if(emp.getOID()==oidEmp){
																strName = emp.getFullName();
															}
															emp_key.add(emp.getEmployeeNum()+" - "+emp.getFullName());
															emp_value.add(String.valueOf(emp.getOID()));
													}
                                                    %>
                                                  <%= ControlCombo.draw("EMPLOYEE_ID","elementForm", null, selectValueEmp, emp_value, emp_key, "onchange=\"javascript:empChange();\"") %> </td>
                                              </tr>
                                            </table>
                                        </td>
                                      </tr>
                                      <tr>
                                        <td>
                                          <% if (listEmp.size() > 0) { %>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr>
                                                <td>
                                                    <%=drawList(listEmp, start)%>
                                                </td>
                                              </tr>
                                              <% if (sOwnError.length() > 0) { %>
                                              <tr>
                                                    <td><br>Duplicate barcode found : <br><%=sOwnError%><br></td>
                                              </tr>
                                              <% } %>
                                                <tr> 
                                                  <td> 
                                                    <% ctrLine.setLocationImg(approot+"/images");
                                                        ctrLine.initDefault();						
                                                        %>
                                                    <%=ctrLine.drawImageListLimit(iCommand,vectSize,start,recordToGet)%> 
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
                                          <% } %>
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
