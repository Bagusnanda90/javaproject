 
<% 
/* 
 * Page Name  		:  empsalary_list.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		: lkarunia 
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
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = 1;//AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_SALARY, AppObjInfo.OBJ_EMP_SALARY); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
    // Check privilege except VIEW, view is already checked on checkuser.jsp as basic access
    boolean privView = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
    boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
    //out.print("privView=" + privView + " | privAdd=" + privAdd);
%>
<!-- Jsp Block -->
<%!
    public String drawList(Vector objectClass ){
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("Name","13%","2","0");
        ctrlist.addHeader("Payroll No.","5%","2","0");
        ctrlist.addHeader("Position","7%","2","0");
        ctrlist.addHeader("Level","3%","2","0");
        ctrlist.addHeader("MS","3%","2","0");
        ctrlist.addHeader("Comm.Date","7%","2","0");
        ctrlist.addHeader("LOS1","4%","2","0");
        ctrlist.addHeader("LOS2","3%","2","0");
        ctrlist.addHeader("Input Date","7%","2","0");
        ctrlist.addHeader("Current Salary","21%","0","3");//48
        ctrlist.addHeader("Basic","7%","0","0");
        ctrlist.addHeader("Transport","7%","0","0");
        ctrlist.addHeader("Total","7%","0","0");
        ctrlist.addHeader("New Salary","21%","0","3");
        ctrlist.addHeader("Basic","7%","0","0");
        ctrlist.addHeader("Transport","7%","0","0");
        ctrlist.addHeader("Total","7%","0","0");
        ctrlist.addHeader("Additional","6%","2","0");

        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();

        for (int i = 0; i < objectClass.size(); i++) {
            Vector temp = (Vector)objectClass.get(i);
			
			if(i==0){
				System.out.println("-------> temp : "+temp);
			}
			
            Employee employee = (Employee)temp.get(0);
            Position position = (Position)temp.get(1);
			Department department = (Department)temp.get(2);
            Level level = (Level)temp.get(3);
            Marital marital = (Marital)temp.get(4);
            EmpSalary empSalary = (EmpSalary)temp.get(5);

            Vector rowx = new Vector();

            rowx.add(employee.getFullName());
            rowx.add(employee.getEmployeeNum());
            rowx.add(position.getPosition());
            rowx.add(level.getLevel());
            rowx.add(marital.getMaritalCode());
            rowx.add(Formater.formatDate(employee.getCommencingDate(),"dd-MMM-yy"));

            rowx.add(""+empSalary.getLos1());
            rowx.add(""+empSalary.getLos2());
            rowx.add(Formater.formatDate(empSalary.getCurrDate(),"dd-MMM-yy"));

            if(empSalary.getCurrBasic()-0.0f>0.0f)
                    rowx.add(Formater.formatNumber(empSalary.getCurrBasic(),"#,###"));
            else
                    rowx.add("-");
            if(empSalary.getCurrTransport()-0.0f>0.0f)
                    rowx.add(Formater.formatNumber(empSalary.getCurrTransport(),"#,###"));
            else
                    rowx.add("-");
            if(empSalary.getCurrTotal()-0.0f>0.0f)
                    rowx.add(Formater.formatNumber(empSalary.getCurrTotal(),"#,###"));
            else
                    rowx.add("-");

            if(empSalary.getNewBasic()-0.0f>0.0f)
                    rowx.add(Formater.formatNumber(empSalary.getNewBasic(),"#,###"));
            else
                    rowx.add("-");
            if(empSalary.getNewTransport()-0.0f>0.0f)
                    rowx.add(Formater.formatNumber(empSalary.getNewTransport(),"#,###"));
            else
                    rowx.add("-");
            if(empSalary.getNewTotal()-0.0f>0.0f)
                    rowx.add(Formater.formatNumber(empSalary.getNewTotal(),"#,###"));
            else
                    rowx.add("-");
            if(empSalary.getAdditional()-0.0f>0.0f)
                    rowx.add(Formater.formatNumber(empSalary.getAdditional(),"#,###"));
            else
                    rowx.add("-");
            lstData.add(rowx);
            lstLinkData.add(String.valueOf(empSalary.getOID()));
        }
        return ctrlist.drawList();
    }
%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidEmpSalary = FRMQueryString.requestLong(request, "emp_salary_oid");

    String msgStr = "";
    int recordToGet = 10;
    int vectSize = 0;
    String whereClause = "";

    ControlLine ctrLine = new ControlLine();
    SrcEmpSalary srcEmpSalary = new SrcEmpSalary();
    CtrlEmpSalary ctrlEmpSalary = new CtrlEmpSalary(request);
    FrmSrcEmpSalary frmSrcEmpSalary = new FrmSrcEmpSalary(request, srcEmpSalary);
    if(iCommand == Command.LIST){
            frmSrcEmpSalary.requestEntityObject(srcEmpSalary);
    }
    if((iCommand==Command.NEXT)||(iCommand==Command.FIRST)||(iCommand==Command.PREV)||(iCommand==Command.LAST)||(iCommand == Command.BACK)){
             try{ 
                    srcEmpSalary = (SrcEmpSalary)session.getValue(SessEmpSalary.SESS_SRC_SALARY); 
             }catch(Exception e){ 
                    srcEmpSalary = new SrcEmpSalary();
             }
     }

    SessEmpSalary sessEmpSalary = new SessEmpSalary();
    session.putValue(SessEmpSalary.SESS_SRC_SALARY, srcEmpSalary);
    vectSize = sessEmpSalary.countEmpSalary(srcEmpSalary);

    if(iCommand == Command.DELETE && start > vectSize){
            start = 0;
            iCommand = Command.LIST;
    }

    if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)||
            (iCommand==Command.LAST)||(iCommand==Command.LIST))
                    start = ctrlEmpSalary.actionList(iCommand, start, vectSize, recordToGet);

    Vector listEmpSalary = new Vector(1,1);
    if(iCommand == Command.SAVE && prevCommand==Command.ADD){
             start = SessEmpSalary.findLimitStart(oidEmpSalary,recordToGet, srcEmpSalary,vectSize);
             iCommand = PstEmpSalary.findLimitCommand(start,recordToGet,vectSize);
    }
    listEmpSalary = sessEmpSalary.searchEmpSalary(srcEmpSalary, start, recordToGet);
%>
<!-- End of Jsp Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Salary</title>
<script language="JavaScript">

	function cmdAdd(){
		document.frm_empsalary.command.value="<%=Command.ADD%>";
		document.frm_empsalary.prev_command.value="<%=Command.ADD%>";
		document.frm_empsalary.action="empsalary_edit.jsp";
		document.frm_empsalary.submit();
	}

	function cmdEdit(oid){
		document.frm_empsalary.emp_salary_oid.value=oid;
		document.frm_empsalary.command.value="<%=Command.EDIT%>";
		document.frm_empsalary.prev_command.value="<%=Command.EDIT%>";
		document.frm_empsalary.action="empsalary_edit.jsp";
		document.frm_empsalary.submit();
	}

	function cmdListFirst(){
		document.frm_empsalary.command.value="<%=Command.FIRST%>";
		document.frm_empsalary.action="empsalary_list.jsp";
		document.frm_empsalary.submit();
	}

	function cmdListPrev(){
		document.frm_empsalary.command.value="<%=Command.PREV%>";
		document.frm_empsalary.action="empsalary_list.jsp";
		document.frm_empsalary.submit();
	}

	function cmdListNext(){
		document.frm_empsalary.command.value="<%=Command.NEXT%>";
		document.frm_empsalary.action="empsalary_list.jsp";
		document.frm_empsalary.submit();
	}

	function cmdListLast(){
		document.frm_empsalary.command.value="<%=Command.LAST%>";
		document.frm_empsalary.action="empsalary_list.jsp";
		document.frm_empsalary.submit();
	}

	function cmdBack(){
		document.frm_empsalary.command.value="<%=Command.BACK%>";
		document.frm_empsalary.action="srcempsalary.jsp";
		document.frm_empsalary.submit();
	}
	
	function cmdImportXLS(){
		document.frm_empsalary.command.value="<%=Command.BACK%>";
		document.frm_empsalary.action="../../system/excel_up/up_salary.jsp";
		document.frm_empsalary.submit();
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
	
    function cmdPrintXLS(){
		window.open("empsalaryXLS_buffer.jsp?start=<%=start%>","listempxls","height=600,width=800,scrollbars=yes, status=no,toolbar=no,menubar=yes,location=no");
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
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnBackOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Employee 
                  &gt; Salary &gt; Employee Salary Search Result<!-- #EndEditable --> 
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
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <% if (privView) { %>
                                    <form name="frm_empsalary" method="post" action="">
                                      <input type="hidden" name="command" value="">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="emp_salary_oid">
                                      <table border="0" width="100%">
                                        <%if((listEmpSalary!=null)&&(listEmpSalary.size()>0)){%>
                                        <tr> 
                                          <td height="8" width="100%" class="listtitle">Employee 
                                            Salary List</td>
                                        </tr>
                                        <tr> 
                                          <td height="8" width="100%"> 
                                            <%try{%>
                                            <%=drawList(listEmpSalary)%> 
                                            <%}catch(Exception e){
										  		out.println("e : "+e.toString());
										  }%>
                                          </td>
                                        </tr>
                                        <%}
                                        else{
                                        %>
                                        <tr> 
                                          <td height="8" width="100%"><span class="comment"><br>
                                            No Employee Salary available</span> 
                                          </td>
                                        </tr>
                                        <%}%>
                                      </table>
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                          <td> 
                                            <table width="100%" cellspacing="0" cellpadding="3">
                                              <tr> 
                                                <td> 
                                                  <% ctrLine.setLocationImg(approot+"/images");
                                                         ctrLine.initDefault();
                                                  %>
                                                  <%=ctrLine.drawImageListLimit(iCommand,vectSize,start,recordToGet)%></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="46%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="46%" nowrap align="left" class="command"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr> 
                                                <td width="24"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image300','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image300" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back To List"></a></td>
                                                <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                <td width="209" nowrap> <a href="javascript:cmdBack()" class="command">Back 
                                                  To Search Employee Salary</a> 
                                                </td>
                                                <%if(privAdd){%>
                                                <td width="12"><img src="<%=approot%>/images/spacer.gif" width="12" height="12"></td>
                                                <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1001" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New"></a></td>
                                                <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                <td width="160"><b><a href="javascript:cmdAdd()" class="command">Add 
                                                  New Employee Salary</a></b></td>
                                                <td width="25"><a href="javascript:cmdPrintXLS()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1001','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1001" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Export To Excel"></a></td>
                                                <td width="157">&nbsp;<a href="javascript:cmdPrintXLS()"><b>Export 
                                                  To Excel</b></a></td>
                                                <td width="19"><a href="javascript:cmdImportXLS()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1001','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1001" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Import To Excel"></a></td>
                                                <td width="329"><a href="javascript:cmdImportXLS()">&nbsp;<b>Import 
                                                  From Excel</b></a></td>
                                                <%}%>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                      </table>
                                    </form>
                                    <% } 
                                   else
                                   {
                                %>
                                    <div align="center">You do not have sufficient 
                                      privilege to access this page.</div>
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

