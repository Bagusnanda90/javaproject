<% 
/* 
 * Page Name  		:  reminder.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		:  [authorName] 
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
<%@ page import = "com.dimata.harisma.entity.recruitment.*" %>
<%@ page import = "com.dimata.harisma.form.recruitment.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.session.recruitment.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ include file = "../../main/javainit.jsp" %>

<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_RECRUITMENT, AppObjInfo.OBJ_REMINDER); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
//boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>
<%--
<% int  appObjCode = 1;// AppObjInfo.composeObjCode(AppObjInfo.--, AppObjInfo.--, AppObjInfo.--); %>
<%//@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
boolean privAdd=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
boolean privUpdate=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
boolean privDelete=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
--%>
<!-- Jsp Block -->
<%!
	public String drawList(Vector objectClass ){
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("Department","6%");
		ctrlist.addHeader("Section","6%");
		ctrlist.addHeader("Position","6%");
		ctrlist.addHeader("Status","6%");
		ctrlist.addHeader("Requisition Type","6%");
		ctrlist.addHeader("Male","2%");
		ctrlist.addHeader("Female","2%");
		ctrlist.addHeader("Expected Commencement Date","6%");
		ctrlist.addHeader("Temporary for","6%");
		//ctrlist.addHeader("Approved By","6%");
		//ctrlist.addHeader("Approved Date","6%");
		//ctrlist.addHeader("Acknowledged By","6%");
		//ctrlist.addHeader("Acknowledged Date","6%");
		//ctrlist.addHeader("Requested By","6%");
		//ctrlist.addHeader("Requested Date","6%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();

		for (int i = 0; i < objectClass.size(); i++) {
			//StaffRequisition staffRequisition = (StaffRequisition)objectClass.get(i);
			Vector temp = (Vector)objectClass.get(i);
                        StaffRequisition staffRequisition = (StaffRequisition)temp.get(0);
			Department department = (Department)temp.get(1);
			Section section = (Section)temp.get(2);
			Position position = (Position)temp.get(3);
			EmpCategory empcategory = (EmpCategory)temp.get(4);

			Vector rowx = new Vector();
			rowx.add(String.valueOf(department.getDepartment()));
			rowx.add(String.valueOf(section.getSection()));
			rowx.add(String.valueOf(position.getPosition()));
			rowx.add(String.valueOf(empcategory.getEmpCategory()));
                        rowx.add(PstStaffRequisition.reqtypeKey[staffRequisition.getRequisitionType()]);
			//rowx.add(String.valueOf(staffRequisition.getRequisitionType()));
			rowx.add(String.valueOf(staffRequisition.getNeededMale()));
			rowx.add(String.valueOf(staffRequisition.getNeededFemale()));

			String str_dt_ExpCommDate = ""; 
			try{
				Date dt_ExpCommDate = staffRequisition.getExpCommDate();
				if(dt_ExpCommDate==null){
					dt_ExpCommDate = new Date();
				}
				str_dt_ExpCommDate = Formater.formatDate(dt_ExpCommDate, "dd MMMM yyyy");
			}catch(Exception e){ str_dt_ExpCommDate = ""; }

			rowx.add(str_dt_ExpCommDate);
			rowx.add(String.valueOf(staffRequisition.getTempFor()));
                        /*
			rowx.add(String.valueOf(staffRequisition.getApprovedBy()));
			String str_dt_ApprovedDate = ""; 
			try{
				Date dt_ApprovedDate = staffRequisition.getApprovedDate();
				if(dt_ApprovedDate==null){
					dt_ApprovedDate = new Date();
				}
				str_dt_ApprovedDate = Formater.formatDate(dt_ApprovedDate, "dd MMMM yyyy");
			}catch(Exception e){ str_dt_ApprovedDate = ""; }
			rowx.add(str_dt_ApprovedDate);
			rowx.add(String.valueOf(staffRequisition.getAcknowledgedBy()));
			String str_dt_AcknowledgedDate = ""; 
			try{
				Date dt_AcknowledgedDate = staffRequisition.getAcknowledgedDate();
				if(dt_AcknowledgedDate==null){
					dt_AcknowledgedDate = new Date();
				}
				str_dt_AcknowledgedDate = Formater.formatDate(dt_AcknowledgedDate, "dd MMMM yyyy");
			}catch(Exception e){ str_dt_AcknowledgedDate = ""; }
			rowx.add(str_dt_AcknowledgedDate);
                        Employee emp = new Employee();
                        if (staffRequisition.getRequestedBy() > 0) {
                            try {
                                emp = PstEmployee.fetchExc(staffRequisition.getRequestedBy());
                            }
                            catch (Exception e) {
                            }
                        }
			//rowx.add(String.valueOf(staffRequisition.getRequestedBy()));
                        rowx.add(String.valueOf(emp.getFullName()));
			String str_dt_RequestedDate = ""; 
			try{
				Date dt_RequestedDate = staffRequisition.getRequestedDate();
				if(dt_RequestedDate==null){
					dt_RequestedDate = new Date();
				}
				str_dt_RequestedDate = Formater.formatDate(dt_RequestedDate, "dd MMMM yyyy");
			}catch(Exception e){ str_dt_RequestedDate = ""; }
			rowx.add(str_dt_RequestedDate);*/
			lstData.add(rowx);
			lstLinkData.add(String.valueOf(staffRequisition.getOID()));
		}
		return ctrlist.draw();
	}
%>
<%
    int start = 0;
    Vector listStaffRequisition = new Vector(1, 1);
    SessStaffRequisition sessStaffRequisition = new SessStaffRequisition();
    listStaffRequisition = sessStaffRequisition.getStaffRequisitionReminder();
%>
<!-- End of Jsp Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Recruitment</title>
<script language="JavaScript">

	function cmdAdd(){
		document.frm_staffrequisition.command.value="<%=Command.ADD%>";
		document.frm_staffrequisition.action="staffrequisition_edit.jsp";
		document.frm_staffrequisition.submit();
	}

	function cmdEdit(oid){
		document.frm_staffrequisition.command.value="<%=Command.EDIT%>";
                document.frm_staffrequisition.hidden_staff_requisition_id.value = oid;
		document.frm_staffrequisition.action="staffrequisition_edit.jsp";
		document.frm_staffrequisition.submit();
	}

	function cmdListFirst(){
		document.frm_staffrequisition.command.value="<%=Command.FIRST%>";
		document.frm_staffrequisition.action="staffrequisition_list.jsp";
		document.frm_staffrequisition.submit();
	}

	function cmdListPrev(){
		document.frm_staffrequisition.command.value="<%=Command.PREV%>";
		document.frm_staffrequisition.action="staffrequisition_list.jsp";
		document.frm_staffrequisition.submit();
	}

	function cmdListNext(){
		document.frm_staffrequisition.command.value="<%=Command.NEXT%>";
		document.frm_staffrequisition.action="staffrequisition_list.jsp";
		document.frm_staffrequisition.submit();
	}

	function cmdListLast(){
		document.frm_staffrequisition.command.value="<%=Command.LAST%>";
		document.frm_staffrequisition.action="staffrequisition_list.jsp";
		document.frm_staffrequisition.submit();
	}

	function cmdBack(){
		document.frm_staffrequisition.command.value="<%=Command.BACK%>";
		document.frm_staffrequisition.action="srcstaffrequisition.jsp";
		document.frm_staffrequisition.submit();
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
</SCRIPT>
<!-- #EndEditable --> 
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> 
                  Employee &gt; Recruitment &gt; Reminder<!-- #EndEditable --> 
                  </strong></font> </td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td   style="background-color:<%=bgColorContent%>;"> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                          <tr> 
                            <td valign="top"> 
                              <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                <tr> 
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="frm_staffrequisition" method="post" action="">
                                      <input type="hidden" name="command" value="">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="hidden_staff_requisition_id" value="<%//=oidStaffRequisition%>">
                                      <table border="0" width="100%">
                                        <tr> 
                                          <td height="8" width="100%" class="comment">Staff 
                                            Requisition List</td>
                                        </tr>
                                      </table>
                                      <%if((listStaffRequisition!=null)&&(listStaffRequisition.size()>0)){%>
                                      <%=drawList(listStaffRequisition)%> 
                                      <%}
					else{
					%>
                                      <span class="comment"><br>
                                      &nbsp;Records is empty ...</span> 
                                      <%}%>
                                      <%--
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
                                            <a href="javascript:cmdAdd()">Add 
                                            New</a> | <a href="javascript:cmdBack()">Back 
                                            to search</a> 
                                          </td>
                                        </tr>
                                      </table>
                                      --%>
                                    </form>
                                    <!-- #EndEditable --> </td>
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
    <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%>
            <tr>
                            <td valign="bottom">
                               <!-- untuk footer -->
                                <%@include file="../../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
