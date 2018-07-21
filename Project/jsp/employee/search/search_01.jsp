<%@page import="com.dimata.harisma.form.attendance.FrmPresence"%>
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
<%@ page import = "com.dimata.harisma.utility.service.tma.*" %>
<%@ page import = "com.dimata.harisma.form.search.FrmSrcEmployee" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.AlUpload" %>
<%@ page import = "com.dimata.harisma.entity.leave.PstAlUpload" %>
<%@ page import = "com.dimata.harisma.form.leave.*" %>
<%@ page import = "com.dimata.harisma.entity.locker.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK); %>
<%//@include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
boolean privStart=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_START));
%>
<%!
public String drawList(Vector objectClass, int st){
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.addHeader("No.","4%");
	ctrlist.addHeader("Payroll","10%");
	ctrlist.addHeader("Name","27%");
	ctrlist.addHeader("Commencing Date","11%");
	ctrlist.addHeader("Division","12%");
	ctrlist.addHeader("Department","12%");
	ctrlist.addHeader("Position","12%");
	//ctrlist.addHeader("Section","12%");
		
        Hashtable hashposition = new Hashtable();
        Vector listPosition = PstPosition.listAll();
        hashposition.put("0", "-");
        for (int ls = 0; ls < listPosition.size(); ls++) {
            Position pos = (Position) listPosition.get(ls);
            hashposition.put(String.valueOf(pos.getOID()), pos.getPosition());
        }
        
	ctrlist.setLinkRow(1);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
	for (int i = 0; i < objectClass.size(); i++) {
		Vector temp = (Vector)objectClass.get(i);
		Employee employee = (Employee)temp.get(0);
		Department department = (Department)temp.get(1);
		Position position = (Position)temp.get(2);
		Section section = (Section)temp.get(3);
		//EmpCategory empCategory = (EmpCategory)temp.get(4);
		//Level level = (Level)temp.get(5);
		//Religion religion = (Religion)temp.get(6);
		//Marital marital = (Marital)temp.get(7);
		Division division = (Division)temp.get(9);

		Vector rowx = new Vector();
		rowx.add(String.valueOf(st + 1 + i));
		rowx.add(employee.getEmployeeNum());
		rowx.add(employee.getFullName());
                if(employee.getCommencingDate()!=null){
                    rowx.add(Formater.formatDate(employee.getCommencingDate(),"dd MMMM yyyy"));
                }else{
                    rowx.add("");
                }
		rowx.add(division.getDivision());
		rowx.add(department.getDepartment());
		rowx.add(position.getPosition());
		//rowx.add(section.getSection());+
		
		lstData.add(rowx);
		lstLinkData.add(String.valueOf(
                        employee.getOID()) 
                        + "','" + employee.getEmployeeNum() 
                        + "','" + employee.getFullName() 
                        + "','" + department.getDepartment()
                        +"','"+hashposition.get(String.valueOf(employee.getPositionId()))
                        +"','"+Formater.formatDate(employee.getCommencingDate(), "dd MMMM yyyy")
                        );
	}
	return ctrlist.draw();
}
%>

<%
    boolean isSecretaryLogin = (positionType >= PstPosition.LEVEL_SECRETARY) ? true : false;
    long hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    boolean isHRDLogin = hrdDepartmentOid == departmentOid ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;


    long oidDepartment = FRMQueryString.requestLong(request,FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_DEPARTMENT]);
    int iCommand = FRMQueryString.requestCommand(request);
    boolean status = false;
    int iErrCode = FRMMessage.ERR_NONE;
    String formName = FRMQueryString.requestString(request,"formName");
    String empPathId = FRMQueryString.requestString(request,"empPathId");
    
    ControlLine ctrLine = new ControlLine();
    SrcEmployee srcEmployee = new SrcEmployee();
    CtrlEmployee ctrlEmployee = new CtrlEmployee(request);
    FrmSrcEmployee frmSrcEmployee = new FrmSrcEmployee(request, srcEmployee);
    frmSrcEmployee.requestEntityObject(srcEmployee);
    
    int start = FRMQueryString.requestInt(request,"start");
    final int recordToGet = 10;
    int vectSize = 0;
    
    String msgStr = "";
    String orderClause = "";
//    String whereClause = "RESIGNED = 0";

    SessEmployee sessEmployee = new SessEmployee();
    vectSize = sessEmployee.countEmployee(srcEmployee);
    if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)
      ||(iCommand==Command.LAST)||(iCommand==Command.LIST))
    {
        start = ctrlEmployee.actionList(iCommand, start, vectSize, recordToGet);
    }else{
        start = 0;
    }
   Vector listEmployee = new Vector(1,1);
   try{
        listEmployee = sessEmployee.searchEmployee(srcEmployee, start, recordToGet);
   }catch(Exception ex){
    System.out.println(""+ex);
   }
%>

<script language=JavaScript src="<%=approot%>/main/calendar.js"></script>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Search Employee</title>
<script language="JavaScript">
    
function cmdEdit_old(oid, number, fullname, department) {
        self.opener.document.<%=formName%>.<%=empPathId%>.value = oid;
        self.opener.document.<%=formName%>.EMP_NUMBER.value = number;
        self.opener.document.<%=formName%>.EMP_FULLNAME.value = fullname;
        self.opener.document.<%=formName%>.EMP_DEPARTMENT.value = department;
        self.close();
    }
function cmdEdit(oid, number, fullname, department, position, commencing_date) {
        //alert(oid + " " + number + " " + fullname);
        self.opener.document.<%=formName%>.<%=empPathId%>.value = oid;
         self.opener.document.<%=formName%>.<%=FrmPresence.fieldNames[FrmPresence.FRM_FIELD_EMPLOYEE_ID]%>.value = oid;
        self.opener.document.<%=formName%>.EMP_NUMBER.value = number;
        self.opener.document.<%=formName%>.EMP_FULLNAME.value = fullname;
        self.opener.document.<%=formName%>.EMP_DEPARTMENT.value = department;
        self.opener.document.<%=formName%>.EMP_POSITION.value = position;
        self.opener.document.<%=formName%>.EMP_COMMENCING_DATE.value = commencing_date;
        self.close();
    }
function cmdAdd() {
        window.open("editEmployee.jsp", "edit_employee", "height=580,width=900, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes,resizable=no,top=50,left=50");
    }
    
function cmdUpdateDep(){
	document.searchEmp.command.value="<%=String.valueOf(Command.ADD)%>";
	document.searchEmp.action="search_01.jsp"; 
	document.searchEmp.submit();
}

function deptChange() {
    document.searchEmp.command.value = "<%=String.valueOf(Command.GOTO)%>";
    document.searchEmp.hidden_goto_dept.value = document.searchEmp.<%=FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_DEPARTMENT]%>.value;
    document.searchEmp.action = "search_01.jsp";
    document.searchEmp.submit();
}

function cmdSearch() {
    document.searchEmp.command.value = "<%=String.valueOf(Command.LIST)%>";									
    document.searchEmp.action = "search_01.jsp";
    document.searchEmp.submit();
}

//-------------- script control line -------------------
        function cmdListFirst(){
		document.searchEmp.command.value="<%=String.valueOf(Command.FIRST)%>";
		document.searchEmp.action="search_01.jsp";
		document.searchEmp.submit();
	}

	function cmdListPrev(){
		document.searchEmp.command.value="<%=String.valueOf(Command.PREV)%>";
		document.searchEmp.action="search_01.jsp";
		document.searchEmp.submit();
	}

	function cmdListNext(){
		document.searchEmp.command.value="<%=String.valueOf(Command.NEXT)%>";
		document.searchEmp.action="search_01.jsp";
		document.searchEmp.submit();
	}

	function cmdListLast(){
		document.searchEmp.command.value="<%=String.valueOf(Command.LAST)%>";
		document.searchEmp.action="search_01.jsp";
		document.searchEmp.submit();
	}


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
<link rel="stylesheet" href="<%=approot%>/styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="<%=approot%>/styles/tab.css" type="text/css">
<!-- #EndEditable -->
<link rel="stylesheet" href="<%=approot%>/styles/calendar.css" type="text/css">
    
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<!-- Untuk Calender-->
<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
    <tr><td id="ds_calclass">
    </td></tr>
</table> 
<script language=JavaScript src="<%=approot%>/main/calendar.js"></script>
<!-- End Calender-->

<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >

  
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2"> 
        <tr> 
          <td width="100%">
      <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->
                Search Employee
                <!-- #EndEditable --> 
                  </strong></font> </td>
        </tr>
        <tr> 
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td class="tablecolor" style="background-color:<%=bgColorContent%>; "> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                    <tr> 
                      <td valign="top"> 
                        <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                          <tr> 
                            <td valign="top">
		    				  <!-- #BeginEditable "content" -->
                                <% if (privStart) { %>
                                    <form name="searchEmp" method="post" action="">
                                    <%if(iCommand == Command.SAVE || iCommand == Command.ACTIVATE){ %>
                                        <input type="hidden" name="command" value="<%=String.valueOf(Command.LIST)%>">
                                    <%}else{%>
                                        <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
                                    <%}%>
                                    <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
                                    <input type="hidden" name="formName" value="<%=String.valueOf(formName)%>">
                                    <input type="hidden" name="empPathId" value="<%=String.valueOf(empPathId)%>">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                            <td>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td width="19%">Name</td>
                                                <td width="1%">:</td>
                                                <td width="80%"> 
                                                  <input type="text" name="<%=FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_NAME]%>"  value="<%=String.valueOf(srcEmployee.getName())%>" class="elemenForm" size="40">
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="19%">Payroll Number</td>
                                                <td width="1%">:</td>
                                                <td width="80%"> 
                                                  <input type="text" name="<%=FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_EMPNUMBER]%>"  value="<%=String.valueOf(srcEmployee.getEmpnumber())%>" class="elemenForm">
                                                </td>
                                              </tr>
                                             
                                              <tr> 
                                                <td width="19%">Category</td>
                                                <td width="1%">:</td>
                                                <td width="80%"> 
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
                                                <%= ControlCombo.draw(FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_EMP_CATEGORY],"formElemen",null,String.valueOf(srcEmployee.getEmpCategory()), cat_value, cat_key, "") %> </td>
                                              </tr>
                                              <tr> 
                                                <td width="19%"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                                <td width="1%">:</td>
                                                <td width="80%"> 
                                                <%
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
                                      grpIdx = countIdx;   // A ha .. found here 
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

            for (int i = 0; i < listDept.size(); i++) {
                Department dept = (Department) listDept.get(i);
                dept_key.add(dept.getDepartment());
                dept_value.add(String.valueOf(dept.getOID()));
            }

            //String sSelectedDepartment = iCommand==Command.GOTO ? ""+lDepartmentOid : srcEmpSchedule.getDepartment();
%>
                                                  <%/* 
                                                        Vector dept_value = new Vector(1,1);
                                                        Vector dept_key = new Vector(1,1);
                                                        Vector listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                        dept_key.add("all department...");
                                                        dept_value.add("0");
                                                        //String selectDept = String.valueOf(gotoDept);
                                                        for (int i = 0; i < listDept.size(); i++) {
                                                            Department dept = (Department) listDept.get(i);
                                                            dept_key.add(dept.getDepartment());
                                                            dept_value.add(String.valueOf(dept.getOID()));
                                                        }
                                                   */ %>
                                                  <%//= ControlCombo.draw("DEPARTMENT_ID","formElemen",null, selectDept, dept_value, dept_key, "onchange=\"javascript:deptChange();\"") %>
                                                  <%= ControlCombo.draw(FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_DEPARTMENT],"formElemen",null,String.valueOf(srcEmployee.getDepartment()), dept_value, dept_key, "onChange=\"javascript:cmdUpdateDep()\"") %> </td>
                                              </tr>
                                              <tr> 
                                                <td width="19%"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></td>
                                                <td width="1%">:</td>
                                                <td width="80%"> 
                                                <% 
							Vector sec_value = new Vector(1,1);
							Vector sec_key = new Vector(1,1); 
							sec_value.add("0");
							sec_key.add("all section ...");
                                                        String strWhere = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+"="+oidDepartment;
							Vector listSec = PstSection.list(0, 0, strWhere, " DEPARTMENT_ID, SECTION ");
							for (int i = 0; i < listSec.size(); i++) {
								Section sec = (Section) listSec.get(i);
								sec_key.add(sec.getSection());
								sec_value.add(String.valueOf(sec.getOID()));
							}
						%>
                                                <%= ControlCombo.draw(FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_SECTION],"formElemen",null,String.valueOf(srcEmployee.getSection()), sec_value, sec_key, "") %> </td>
                                              </tr>
                                              <tr> 
                                                <td width="19%">Position</td>
                                                <td width="1%">:</td>
                                                <td width="80%"> 
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
                                                <%= ControlCombo.draw(FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_POSITION],"formElemen",null,String.valueOf(srcEmployee.getPosition()), pos_value, pos_key, "") %> </td>
                                              </tr>
					      
                                              <input type="hidden" name="<%=FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_RESIGNED]%>" value="0">
                                              <input type="hidden" name="<%=FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_SEX]%>" value="3">
                                              
                                              <tr> 
                                                <td width="19%">&nbsp;</td>
                                                <td width="1%">&nbsp;</td>
                                                <td width="80%"> 
                                                  <input type="submit" name="Submit" value="Search Employee" onClick="javascript:cmdSearch()">
                                                <!--  <input type="submit" name="Submit" value="Add Employee" onClick="javascript:cmdAdd()">
                                                -->
                                                </td>
                                              </tr>
                                              
                                            </table>
                                        </td>
                                      </tr>
                                      <tr>
                                        <td>
                                            
                                          <% if (listEmployee.size() > 0 && ((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)
      ||(iCommand==Command.LAST)||(iCommand==Command.LIST))) { %>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <%if((listEmployee!=null)&&(listEmployee.size()>0)){%>
                                                <tr> 
                                                  <td height="8" width="100%"><%=drawList(listEmployee, start)%></td>
                                                </tr>
                                                <%}else{%>
                                                <tr> 
                                                  <td height="8" width="100%" class="comment"><span class="comment"><br>
                                                    &nbsp;No Employee available</span> 
                                                  </td>
                                                </tr>
                                                <%}%>
                                              <tr>
                                                <td><%
						ctrLine.setLocationImg(approot+"/images");
                                                ctrLine.initDefault();
                                                %><%=ctrLine.drawImageListLimit(iCommand,vectSize,start,recordToGet)%>
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
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>

