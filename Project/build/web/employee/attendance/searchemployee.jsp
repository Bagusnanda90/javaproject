
<% 
/* 
 * Page Name  		:  srcemployeevisit.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		:  [lkarunia] 
 * @version  		:  [version] 
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
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%@ page import = "com.dimata.harisma.session.employee.*" %>

<%@ page import = "com.dimata.harisma.entity.locker.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>

<%@ page import = "com.dimata.harisma.form.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = 1;//AppObjInfo.composeObjCode(AppObjInfo.G1_CLINIC, AppObjInfo.G2_CLINIC_MEDICAL, AppObjInfo.OBJ_CLINIC_EMP_VISIT); %>
<%//@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd = true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privPrint = true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>
<%!
	public String drawList(Vector objectClass ){
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("Payroll","6%");
		ctrlist.addHeader("Name","15%");
		//ctrlist.addHeader("Address","18%");
		//ctrlist.addHeader("Phone","7%");
		ctrlist.addHeader("Sex","5%");
		ctrlist.addHeader("Religion","5%");
		ctrlist.addHeader("Department","10%");
		ctrlist.addHeader("Position","10%");
		//ctrlist.addHeader("Section","5%");
		//ctrlist.addHeader("Emp Category","10%");
		ctrlist.addHeader("Level","7%");
			
		ctrlist.setLinkRow(0);
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
			EmpCategory empCategory = (EmpCategory)temp.get(4);
			Level level = (Level)temp.get(5);
			Religion religion = (Religion)temp.get(6);
			Marital marital = (Marital)temp.get(7);
			//Locker locker = (Locker)temp.get(8);
			
			Vector rowx = new Vector();
			rowx.add(employee.getEmployeeNum());
			rowx.add(employee.getFullName());
			//rowx.add(employee.getAddress());
			//rowx.add(employee.getPhone());
			rowx.add(PstEmployee.sexKey[employee.getSex()]);
			rowx.add(religion.getReligion());
			rowx.add(department.getDepartment());
			rowx.add(position.getPosition());
			//rowx.add(section.getSection());
			//rowx.add(empCategory.getEmpCategory());
			rowx.add(level.getLevel());
			
			lstData.add(rowx);
			//lstLinkData.add(String.valueOf(employee.getOID()));
                        lstLinkData.add(String.valueOf(employee.getOID()) + "','" + employee.getEmployeeNum() + "','" + employee.getFullName() + "','" + employee.getDepartmentId());
		}
		return ctrlist.draw();
	}

%>

<%
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    //long empVisitOID = FRMQueryString.requestLong(request, "employee_visit_oid");
    long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");

    int recordToGet = 10;
    int vectSize = 0;

    ControlLine ctrLine = new ControlLine();
    SrcEmployee srcEmployee = new SrcEmployee();
    SessEmployee sessEmployee = new SessEmployee();
    CtrlEmployee ctrlEmployee = new CtrlEmployee(request);

    //FrmSrcEmployeeVisit frmSrcEmployeeVisit = new FrmSrcEmployeeVisit(request, srcEmployeeVisit);
    FrmSrcEmployee frmSrcEmployee = new FrmSrcEmployee(request, srcEmployee);

    //frmSrcEmployeeVisit.requestEntityObject(srcEmployeeVisit);
    frmSrcEmployee.requestEntityObject(srcEmployee);

    if(iCommand != Command.LIST && iCommand != Command.NONE){
        //try{
        //    srcEmployeeVisit = (SrcEmployeeVisit)session.getValue(SessEmployeeVisit.SESS_SRC_EMPVISIT);
        //    if(srcEmployeeVisit == null) {
        //        srcEmployeeVisit = new SrcEmployeeVisit();
        //    }
        //} 
        //catch(Exception e){
        //    srcEmployeeVisit = new SrcEmployeeVisit();
        //}

        try{
            srcEmployee = (SrcEmployee)session.getValue(SessEmployee.SESS_SRC_EMPLOYEE);
            if(srcEmployee == null) {
                srcEmployee = new SrcEmployee();
            }
            //System.out.println("ecccccc "+srcEmployee.getOrderBy());
        }
        catch (Exception e){
            //System.out.println("e....."+e.toString());
            srcEmployee = new SrcEmployee();
        }
        iCommand = prevCommand; 
    }

    ////if add new employee visit, system will be find start and iCommand where is new data...
    //vectSize = sessEmployeeVisit.countEmployeeVisit(srcEmployeeVisit);
    vectSize = sessEmployee.countEmployee(srcEmployee);

    //if(iCommand == Command.ADD){
    //        start = PstEmployeeVisit.findLimitStart(empVisitOID, recordToGet,vectSize,srcEmployeeVisit);	
    //        iCommand = PstEmployeeVisit.findLimitCommand(start, recordToGet, vectSize);
    //}

    //if start = vectSize when delete employee visit 
    if(start == vectSize){
        if(vectSize > recordToGet)
            start = vectSize - recordToGet;
        else
            start = 0;
    }

    //Vector listEmployeeVisit = new Vector(1,1);
    Vector listEmployee = new Vector(1,1);
    if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)||
        (iCommand==Command.LAST)||(iCommand==Command.LIST))
    {
        //start = ctrlEmployeeVisit.actionList(iCommand, start, vectSize, recordToGet);		
        start = ctrlEmployee.actionList(iCommand, start, vectSize, recordToGet);
        //listEmployeeVisit = sessEmployeeVisit.searchEmployeeVisit(srcEmployeeVisit, start, recordToGet);
	listEmployee = sessEmployee.searchEmployee(srcEmployee, start, recordToGet);
    }

    //session.putValue(SessEmployeeVisit.SESS_SRC_EMPVISIT,srcEmployeeVisit);
    session.putValue(SessEmployee.SESS_SRC_EMPLOYEE, srcEmployee);
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Employee Search</title>
<script language="JavaScript">
<!--
    function cmdEdit(oid, number, fullname, department) {
        //alert(oid + " " + number + " " + fullname);
        self.opener.document.frm_empschedule.<%=FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_EMPLOYEE_ID]%>.value = oid;
        self.opener.document.frm_empschedule.EMP_NUMBER.value = number;
        self.opener.document.frm_empschedule.EMP_FULLNAME.value = fullname;
        self.opener.document.frm_empschedule.EMP_DEPARTMENT.value = department;
        self.close();
    }

    function cmdSearch(){
            document.frmsrcemployee.command.value="<%=Command.LIST%>";
            document.frmsrcemployee.prev_command.value="<%=Command.LIST%>";
            document.frmsrcemployee.action="searchemployee.jsp";
            document.frmsrcemployee.submit();
    }

    function cmdListFirst(){
            document.frm_employee.command.value="<%=Command.FIRST%>";
            document.frm_employee.prev_command.value="<%=Command.FIRST%>";
            document.frm_employee.action="searchemployee.jsp";
            document.frm_employee.submit();
    }

    function cmdListPrev(){
            document.frm_employee.command.value="<%=Command.PREV%>";
            document.frm_employee.prev_command.value="<%=Command.PREV%>";
            document.frm_employee.action="searchemployee.jsp";
            document.frm_employee.submit();
    }

    function cmdListNext(){
            document.frm_employee.command.value="<%=Command.NEXT%>";
            document.frm_employee.prev_command.value="<%=Command.NEXT%>";
            document.frm_employee.action="searchemployee.jsp";
            document.frm_employee.submit();
    }

    function cmdListLast(){
            document.frm_employee.command.value="<%=Command.LAST%>";
            document.frm_employee.prev_command.value="<%=Command.LAST%>";
            document.frm_employee.action="searchemployee.jsp";
            document.frm_employee.submit();
    }

    function fnTrapKD(){
            //alert(event.keyCode);
            switch(event.keyCode) {
                    case <%=LIST_PREV%>:
                            cmdListPrev();
                            break;
                    case <%=LIST_NEXT%>:
                            cmdListNext();
                            break;
                    case <%=LIST_FIRST%>:
                            cmdListFirst();
                            break;
                    case <%=LIST_LAST%>:
                            cmdListLast();
                            break;
                    default:
                            break;
            }
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
</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> <!-- #EndEditable --> 
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg','<%=approot%>/images/BtnSearchOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
<%--
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../../main/header.jsp" %>
      <!-- #EndEditable --> 
    </td>
  </tr> 
  <tr> 
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
      <%//@ include file = "../../main/mnmain.jsp" %>
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
--%>
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2"> 
        <tr> 
          <td width="100%">
      <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td height="20">
		    <font color="#FF6600" face="Arial"><strong>
			  <!-- #BeginEditable "contenttitle" -->Search for Employee<!-- #EndEditable --> 
            </strong></font>
	      </td>
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
                                    <form name="frmsrcemployee" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">									  
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td valign="middle" colspan="2"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                                              <tr>
                                                <td width="3%">&nbsp;</td>
                                                <td width="97%">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td width="3%">&nbsp;</td>
                                                <td width="97%"> 
                                                  <table border="0" cellspacing="2" cellpadding="2" width="52%">
                                                    <tr align="left" valign="top"> 
                                                      <td width="20%" nowrap> 
                                                        <div align="left">Employee 
                                                          Name</div>
                                                      </td>
                                                      <td width="37%"> 
                                                        <input type="text" name="<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_NAME] %>"  value="<%= srcEmployee.getName() %>" class="elemenForm" size="20" onkeydown="javascript:fnTrapKD()">
                                                      </td>
                                                      <td width="3%">&nbsp;</td>
                                                      <td width="16%"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                                      <td width="24%"> 
                                                        <% 
                                                            Vector dept_value = new Vector(1,1);
                                                            Vector dept_key = new Vector(1,1);        
															dept_value.add("0");
                                                            dept_key.add("select ...");                                                          
                                                            Vector listDept = PstDepartment.list(0, 0, "", " DEPARTMENT ");                                                        
                                                            for (int i = 0; i < listDept.size(); i++) {
                                                                    Department dept = (Department) listDept.get(i);
                                                                    dept_key.add(dept.getDepartment());
                                                                    dept_value.add(String.valueOf(dept.getOID()));
                                                            }
                                                        %>
                                                        <%= ControlCombo.draw(frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_DEPARTMENT],"formElemen",null, ""+srcEmployee.getDepartment(), dept_value, dept_key, " onkeydown=\"javascript:fnTrapKD()\"") %> 
                                                      </td>
                                                    </tr>
                                                    <script language="javascript">
                                                        document.frmsrcemployee.<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_NAME]%>.focus();
                                                    </script>
                                                    <tr align="left" valign="top"> 
                                                      <td width="20%" nowrap> 
                                                        <div align="left">Payroll 
                                                          Number</div>
                                                      </td>
                                                      <td width="37%"> 
                                                        <input type="text" name="<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_EMPNUMBER] %>"  value="<%= srcEmployee.getEmpnumber() %>" class="elemenForm" onkeydown="javascript:fnTrapKD()" size="8">
                                                      </td>
                                                      <td width="3%">&nbsp;</td>
                                                      <td width="16%">Position</td>
                                                      <td width="24%"> 
                                                        <% 
                                                            Vector pos_value = new Vector(1,1);
                                                            Vector pos_key = new Vector(1,1); 
															pos_value.add("0");
                                                            pos_key.add("select ...");                                                       
                                                            Vector listPos = PstPosition.list(0, 0, "", " POSITION ");                                                            
                                                            for (int i = 0; i < listPos.size(); i++) {
                                                                    Position pos = (Position) listPos.get(i);
                                                                    pos_key.add(pos.getPosition());
                                                                    pos_value.add(String.valueOf(pos.getOID()));
                                                            }
                                                        %>
                                                        <%= ControlCombo.draw(frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_POSITION],"formElemen",null, "" + srcEmployee.getPosition(), pos_value, pos_key, " onkeydown=\"javascript:fnTrapKD()\"") %>	
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td width="20%" nowrap> 
                                                        <div align="left">Sort 
                                                          By</div>
                                                      </td>
                                                      <td width="37%"><%= ControlCombo.draw(frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_ORDER],"formElemen",null, ""+srcEmployee.getOrderBy(), FrmSrcEmployee.getOrderValue(), FrmSrcEmployee.getOrderKey(), " onkeydown=\"javascript:fnTrapKD()\"") %> 
                                                      </td>
                                                      <td width="3%">&nbsp;</td>
                                                      <td width="16%"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></td>
                                                      <td width="24%"> 
                                                        <% 
                                                            Vector sec_value = new Vector(1,1);
                                                            Vector sec_key = new Vector(1,1); 
							sec_value.add("0");
                                                            sec_key.add("select ...");
                                                            Vector listSec = PstSection.list(0, 0, "", " DEPARTMENT_ID, SECTION ");
                                                            for (int i = 0; i < listSec.size(); i++) {
                                                                    Section sec = (Section) listSec.get(i);
                                                                    sec_key.add(sec.getSection());
                                                                    sec_value.add(String.valueOf(sec.getOID()));
                                                            }
                                                        %>
                                                        <%= ControlCombo.draw(frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_SECTION],"formElemen",null, "" + srcEmployee.getSection(), sec_value, sec_key, " onkeydown=\"javascript:fnTrapKD()\"") %></td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td width="20%" nowrap> 
                                                        <div align="left"></div>
                                                      </td>
                                                      <td width="37%"> 
                                                        <table width="30%" border="0" cellspacing="0" cellpadding="0">
                                                          <tr> 
                                                            <td width="20%"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                            <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                                            <td width="28%" class="command" nowrap><a href="javascript:cmdSearch()">Search 
                                                              for Employee</a></td>
                                                            <td width="50%">&nbsp;</td>
                                                          </tr>
                                                        </table>
                                                      </td>
                                                      <td width="3%">&nbsp;</td>
                                                      <td width="16%">&nbsp;</td>
                                                      <td width="24%">&nbsp;</td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                      </table>
                                    </form>

                                    <form name="frm_employee" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">									  
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <%-- <input type="hidden" name="employee_oid" value="<%=oidEmployee%>"> --%>

                                      <table border="0" width="100%">
                                        <tr>
                                          <td height="8" width="100%" class="listtitle">
                                            <hr>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td height="8" width="100%" class="listtitle"><span class="listtitle">Employee 
                                            List</span> </td>
                                        </tr>
                                        <%if((listEmployee!=null)&&(listEmployee.size()>0)){%>
                                        <tr> 
                                          <td height="8" width="100%"><%=drawList(listEmployee)%></td>
                                        </tr>
                                        <%}else{%>
                                        <tr> 
                                          <td height="8" width="100%" class="comment"><span class="comment"><br>
                                            &nbsp;No Employee available</span> 
                                          </td>
                                        </tr>
                                        <%}%>
                                      </table>                                                                                                                                                                                              
                                        <%if((listEmployee!=null)&&(listEmployee.size()>0)){%>
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                          <td> 
                                            <% ctrLine.setLocationImg(approot+"/images");
						ctrLine.initDefault();						
						%>
                                            <%=ctrLine.drawImageListLimit(iCommand,vectSize,start,recordToGet)%> 
                                          </td>
                                        </tr>
                                        <tr>
                                          <td nowrap align="left" class="command">&nbsp;</td>
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
<!-- #EndTemplate -->
</html>
