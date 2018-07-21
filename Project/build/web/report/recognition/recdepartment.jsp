
<% 
/* 
 * Page Name  		:  recognition.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		:  [authorName] 
 * @version  		:  [version] 
 */

/*******************************************************************
 * Page Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
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
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = 1;// AppObjInfo.composeObjCode(AppObjInfo.--, AppObjInfo.--, AppObjInfo.--); %>
<%//@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
    privStart=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_START));
%>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
privAdd=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
privUpdate=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
privDelete=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%!
    public String drawList(Vector objectClass, int gotoPeriodmn, int gotoPeriodyr){
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("80%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("Payroll","5%");
        ctrlist.addHeader("Full Name","15%");
        ctrlist.addHeader("Position","15%");
        ctrlist.addHeader("Points","5 %");

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

        Vector vPoint = new Vector(1, 1);
        for (int i = 0; i < objectClass.size(); i++) {
            Employee employee = (Employee) objectClass.get(i);
            Vector rowx = new Vector();
            String where = "";

            rowx.add(employee.getEmployeeNum());
            rowx.add(employee.getFullName());
            rowx.add(position.get(String.valueOf(employee.getPositionId())));

            where = "EMPLOYEE_ID = " + String.valueOf(employee.getOID()) + 
                " AND MONTH(PERIOD_DATE) = " + String.valueOf(gotoPeriodmn) + 
                " AND YEAR(PERIOD_DATE) = " + String.valueOf(gotoPeriodyr);
            //System.out.println(where);
            vPoint = PstRecognition.list(0, 0, where, "");
            if (vPoint.size() > 0) {
                Recognition rec = (Recognition) vPoint.get(0);
                rowx.add(String.valueOf(rec.getPoint()));
            }
            else {
                rowx.add("0");
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
    int gotoPeriodmn = FRMQueryString.requestInt(request, "hidden_goto_period_mn");
    int gotoPeriodyr = FRMQueryString.requestInt(request, "hidden_goto_period_yr");

    //System.out.println(gotoPeriodmn + " - " + gotoPeriodyr);
    Date period = new Date();
    String[] point = null;
    String[] employee_id = null;
    Vector vList = new Vector(1, 1);
    String whereClauseRecognition = "";

    if (gotoPeriodmn > 0) {
        period = new Date(gotoPeriodyr - 1900, gotoPeriodmn - 1, 1);
    }

    if (iCommand == Command.SAVE) {
        try {
            employee_id = request.getParameterValues("employee_id");
            point = request.getParameterValues("point");
        }
        catch (Exception e) {}

        System.out.println("\t" + "Period : " + period.toString());
    }

    int start = 0;
    int recordToGet = 0;
    String whereClause = PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + String.valueOf(gotoDept);
        whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 0 ";
    String orderClause = "";
    Vector listEmp = new Vector(1,1);
    listEmp = PstEmployee.list(start, recordToGet, whereClause, orderClause);
%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Report</title>
<script language="JavaScript">

    function cmdGetList() {
        document.frmbarcode.command.value = "<%=Command.GOTO%>";
        document.frmbarcode.hidden_goto_dept.value = document.frmbarcode.DEPARTMENT_ID.value;
        document.frmbarcode.hidden_goto_period_mn.value = document.frmbarcode.PERIOD_mn.value;
        document.frmbarcode.hidden_goto_period_yr.value = document.frmbarcode.PERIOD_yr.value;
        document.frmbarcode.action = "recdepartment.jsp";
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
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<SCRIPT language=JavaScript>
    function hideObjectForEmployee(){
        //document.frmsrcemployee.<%//=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_START_COMMENC] + "_mn"%>.style.visibility = 'hidden';
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
        //document.all.<%//=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_START_COMMENC] + "_mn"%>.style.visibility = "";
    }
</SCRIPT>
<!-- #EndEditable --> 
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
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
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr> 
          <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> 
                  Report &gt; Recognition &gt; Department Report<!-- #EndEditable --> </strong></font> 
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
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                <% if (privStart) { %>
                                    <form name="frmbarcode" method="post" action="">
                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                    <input type="hidden" name="hidden_goto_dept" value="<%=gotoDept%>">
                                    <input type="hidden" name="hidden_goto_period_mn" value="<%=gotoPeriodmn%>">
                                    <input type="hidden" name="hidden_goto_period_yr" value="<%=gotoPeriodyr%>">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                            <td>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td width="1%">&nbsp;</td>
                                                <td width="8%">Period</td>
                                                <td width="91%"><%=ControlDate.drawDateMY("PERIOD", (period == null) ? new Date() : period,"MMMM","elemenForm",0,-1)%></td>
                                              </tr>
                                              <tr> 
                                                <td width="1%">&nbsp;</td>
                                                <td width="8%"> 
                                                  <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>
                                                </td>
                                                <td width="91%"> 
                                                  <% 
                                                        Vector dept_value = new Vector(1,1);
                                                        Vector dept_key = new Vector(1,1);
                                                        Vector listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                        //dept_key.add("select...");
                                                        //dept_value.add("0");
                                                        String selectDept = String.valueOf(gotoDept);
                                                        for (int i = 0; i < listDept.size(); i++) {
                                                            Department dept = (Department) listDept.get(i);
                                                            dept_key.add(dept.getDepartment());
                                                            dept_value.add(String.valueOf(dept.getOID()));
                                                        }
                                                    %>
                                                  <%= ControlCombo.draw("DEPARTMENT_ID","formElemen",null, selectDept, dept_value, dept_key) %> 
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="1%">&nbsp;</td>
                                                <td></td>
                                                <td> 
                                                  <table cellpadding="0" cellspacing="0" border="0">
                                                    <tr> 
                                                      <td width="8"><a href="javascript:cmdGetList()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Locker"></a></td>
                                                      <td width="4"><img src="<%=approot%>/images/spacer.gif" width="2" height="8"></td>
                                                      <td width="110" class="command" nowrap><a href="javascript:cmdGetList()">Get 
                                                        List </a> </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td colspan="3"><hr></td>
                                              </tr>
                                            </table>
                                        </td>
                                      </tr>
                                      <tr>
                                        <td>
                                          <% if (listEmp.size() > 0) { 
                                                Department dep = new Department();
                                                if (gotoDept > 0) {
                                                    dep = PstDepartment.fetchExc(gotoDept);
                                                }
                                          %>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <% if (gotoDept > 0) { %>
                                              <tr>
                                                <td>&nbsp;List of Points accumulated by <b><%=dep.getDepartment()%></b> Department on <b><%=Formater.formatDate(period, "MMMM yyyy")%></b>
                                                    </td>
                                              </tr>
                                              <% } %>
                                              <tr>
                                                <td>
                                                    <%=drawList(listEmp, gotoPeriodmn, gotoPeriodyr)%>
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
  <tr> 
    <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
