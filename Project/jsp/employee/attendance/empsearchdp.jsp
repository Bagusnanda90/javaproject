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

<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_ATTENDANCE, AppObjInfo.OBJ_WORKING_SCHEDULE); %>
<%@ include file = "../../main/javainit.jsp" %>

<%!
public String drawList(Vector objectClass)
{
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

	for (int i = 0; i < objectClass.size(); i++) 
	{
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
String emp_number = request.getParameter("emp_number");
String emp_fullname = request.getParameter("emp_fullname");
String emp_department = request.getParameter("emp_department");

String whereClause = "";
whereClause = FRMQueryString.requestString(request, "whereClause");
String whereClause1 = "";
String whereClause2 = "";
String whereClause3 = "";

int iCommand = FRMQueryString.requestCommand(request);

if(!(iCommand==Command.FIRST || iCommand==Command.NEXT || iCommand==Command.PREV || iCommand==Command.LAST || iCommand==Command.LIST)){
    if (emp_number.compareToIgnoreCase("") != 0) {
            whereClause1 = PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " LIKE '%" + emp_number + "%' ";
    }
    if (emp_fullname.compareToIgnoreCase("") != 0) {
            whereClause2 = PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " LIKE '%" + emp_fullname + "%' ";
    }
    if (emp_department.compareToIgnoreCase("0") != 0) {
            whereClause3 = PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + emp_department + " ";
    }

    if (whereClause1.length() > 0) {
            whereClause = whereClause1;
    }
    if (whereClause2.length() > 0) {
            if (whereClause.length() > 0) {
                    whereClause += " AND " + whereClause2;
            }
            else {
                    whereClause = whereClause2;
            }
    }
    if (whereClause3.length() > 0) {
            if (whereClause.length() > 0) {
                    whereClause += " AND " + whereClause3;
            }
            else {
                    whereClause = whereClause3;
            }
    }
    if(whereClause.length()>0){
        whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 0 ";
    }else{
        whereClause += " "+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 0 ";
    }
}

int start = FRMQueryString.requestInt(request, "start");
int recordToGet = 20;
String orderClause = "";

CtrlDpStockManagement ctrlDpStockManagement = new CtrlDpStockManagement(request);

int vectSize = PstEmployee.getCount(whereClause);	
if(iCommand==Command.FIRST || iCommand==Command.NEXT || iCommand==Command.PREV || iCommand==Command.LAST || iCommand==Command.LIST)
{
	start = ctrlDpStockManagement.actionList(iCommand, start, vectSize, recordToGet);
}else{
    iCommand=Command.LIST;
}

Vector listEmp = new Vector(1,1);
listEmp = PstEmployee.list(start, recordToGet, whereClause, orderClause);
%>
<html>
<head><title>Search Employee</title>
<script language="javascript">
    function cmdEdit(oid, number, fullname, department) {
        self.opener.document.frdp.<%=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EMPLOYEE_ID]%>.value = oid;
        self.opener.document.frdp.EMP_NUMBER.value = number;
        //self.opener.document.frdp.EMP_NUMBER_XX.value = number;
        self.opener.document.frdp.EMP_FULLNAME.value = fullname;
        self.opener.document.frdp.EMP_DEPARTMENT.value = department;
        self.close();
    }
    
    //------------------------------------------------------
    function cmdListFirst(){
	document.frmsrcdp.command.value="<%=String.valueOf(Command.FIRST)%>";
	document.frmsrcdp.action="empsearchdp.jsp";
	document.frmsrcdp.submit();
    }

    function cmdListPrev(){
        document.frmsrcdp.command.value="<%=String.valueOf(Command.PREV)%>";
        document.frmsrcdp.action="empsearchdp.jsp";
        document.frmsrcdp.submit();
    }

    function cmdListNext(){
        document.frmsrcdp.command.value="<%=String.valueOf(Command.NEXT)%>";
        document.frmsrcdp.action="empsearchdp.jsp";
        document.frmsrcdp.submit();
    }

    function cmdListLast(){
        document.frmsrcdp.command.value="<%=String.valueOf(Command.LAST)%>";
        document.frmsrcdp.action="empsearchdp.jsp";
        document.frmsrcdp.submit();
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
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr> 
  <td class="tablecolor"> 
        <form name="frmsrcdp" method="post" action="">
          <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
          <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
          <input type="hidden" name="whereClause" value="<%=String.valueOf(whereClause)%>">
	<table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
	  <tr> 
		<td valign="top"> 
		    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
              <tr>
                <td valign="top">&nbsp;&nbsp;<b>Employee List</b> :</td>
              </tr>
                   <%
                    if(listEmp.size()>0)
                    {
                    %>
                    <tr> 
                      <td><%=drawList(listEmp)%></td>
                    </tr>
                    <%
                    }
                        else if(iCommand == Command.LIST)
                    {
                    %>
                    <tr> 
                      <td><%="<div class=\"msginfo\">&nbsp;&nbsp;No day off payment data found ...</div>"%></td>
                    </tr>
                    <%	
                    }
                    //--- end untuk menampilkan list data result, baik summary maupun detail per employee 
                    %>
                    <tr> 
                      <td> 
                        <% 
                            ControlLine ctrLine = new ControlLine();												
                            ctrLine.setLanguage(SESS_LANGUAGE);											
                            ctrLine.setLocationImg(approot+"/images");
                            ctrLine.initDefault();
                            out.println(ctrLine.drawImageListLimit(iCommand, vectSize, start, recordToGet));
                        %>
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
</body>
</html>
