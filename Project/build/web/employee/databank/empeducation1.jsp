
<% 
/* 
 * Page Name  		:  empeducation.jsp
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
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%!

	public String drawList(Vector objectClass ,  long empEducationId)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("60%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("Education","10%");
		ctrlist.addHeader("Education Description","30%");
		ctrlist.addHeader("Start Date","10%");
		ctrlist.addHeader("End Date","10%");
		ctrlist.addHeader("Graduation","20%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;

		for (int i = 0; i < objectClass.size(); i++) {
			EmpEducation empEducation = (EmpEducation)objectClass.get(i);
			 Vector rowx = new Vector();
			 if(empEducationId == empEducation.getOID())
				 index = i;

			Education education = new Education();
			if(empEducation.getEducationId() != 0){
				try{
					education = PstEducation.fetchExc(empEducation.getEducationId());
				}catch(Exception exc){
					education = new Education();
				}
			}
			
			rowx.add(String.valueOf(education.getEducation()));
			rowx.add(empEducation.getEducationDesc());
			rowx.add(String.valueOf(empEducation.getStartDate()));
			rowx.add(String.valueOf(empEducation.getEndDate()));
			rowx.add(empEducation.getGraduation());
			lstData.add(rowx);
			lstLinkData.add(String.valueOf(empEducation.getOID()));
		}

		return ctrlist.draw(index);
	}

%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidEmpEducation = FRMQueryString.requestLong(request, "emp_education_id");
    long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");
    //System.out.println("===> oidEmployee=" + oidEmployee);
    /*variable declaration*/
    int recordToGet = 10;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    String whereClause = PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMPLOYEE_ID]+ " = "+oidEmployee;
    String orderClause = PstEmpEducation.fieldNames[PstEmpEducation.FLD_START_DATE];

    CtrlEmpEducation ctrlEmpEducation = new CtrlEmpEducation(request);
    ControlLine ctrLine = new ControlLine();
    Vector listEmpEducation = new Vector(1,1);

    /*switch statement */
    iErrCode = ctrlEmpEducation.action(iCommand , oidEmpEducation, oidEmployee);
    /* end switch*/
    FrmEmpEducation frmEmpEducation = ctrlEmpEducation.getForm();

    /*count list All EmpEducation*/
    int vectSize = PstEmpEducation.getCount(whereClause);

    EmpEducation empEducation = ctrlEmpEducation.getEmpEducation();
    msgString =  ctrlEmpEducation.getMessage();

    /*switch list EmpEducation*/
    if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)&& (oidEmpEducation == 0))
            start = PstEmpEducation.findLimitStart(empEducation.getOID(),recordToGet, whereClause, orderClause);

    if((iCommand == Command.FIRST || iCommand == Command.PREV )||
      (iCommand == Command.NEXT || iCommand == Command.LAST)){
                    start = ctrlEmpEducation.actionList(iCommand, start, vectSize, recordToGet);
     } 
    /* end switch list*/

    /* get record to display */
    listEmpEducation = PstEmpEducation.list(start,recordToGet, whereClause , orderClause);

    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listEmpEducation.size() < 1 && start > 0)
    {
             if (vectSize - recordToGet > recordToGet)
                            start = start - recordToGet;   //go to Command.PREV
             else{
                     start = 0 ;
                     iCommand = Command.FIRST;
                     prevCommand = Command.FIRST; //go to Command.FIRST
             }
             listEmpEducation = PstEmpEducation.list(start,recordToGet, whereClause , orderClause);
    }
%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Education</title>
<script language="JavaScript">


function cmdAdd(){
	document.frmempeducation.emp_education_id.value="0";
	document.frmempeducation.command.value="<%=Command.ADD%>";
	document.frmempeducation.prev_command.value="<%=prevCommand%>";
	document.frmempeducation.action="empeducation.jsp";
	document.frmempeducation.submit();
}

function cmdAsk(oidEmpEducation){
	document.frmempeducation.emp_education_id.value=oidEmpEducation;
	document.frmempeducation.command.value="<%=Command.ASK%>";
	document.frmempeducation.prev_command.value="<%=prevCommand%>";
	document.frmempeducation.action="empeducation.jsp";
	document.frmempeducation.submit();
}

function cmdConfirmDelete(oidEmpEducation){
	document.frmempeducation.emp_education_id.value=oidEmpEducation;
	document.frmempeducation.command.value="<%=Command.DELETE%>";
	document.frmempeducation.prev_command.value="<%=prevCommand%>";
	document.frmempeducation.action="empeducation.jsp";
	document.frmempeducation.submit();
}
function cmdSave(){
	document.frmempeducation.command.value="<%=Command.SAVE%>";
	document.frmempeducation.prev_command.value="<%=prevCommand%>";
	document.frmempeducation.action="empeducation.jsp";
	document.frmempeducation.submit();
	}

function cmdEdit(oidEmpEducation){
	document.frmempeducation.emp_education_id.value=oidEmpEducation;
	document.frmempeducation.command.value="<%=Command.EDIT%>";
	document.frmempeducation.prev_command.value="<%=prevCommand%>";
	document.frmempeducation.action="empeducation.jsp";
	document.frmempeducation.submit();
	}

function cmdBackEmp(empOID){
	document.frmempeducation.employee_oid.value=empOID;
	document.frmempeducation.command.value="<%=Command.EDIT%>";	
	document.frmempeducation.action="employee_edit.jsp";
	document.frmempeducation.submit();
	}

function cmdCancel(oidEmpEducation){
	document.frmempeducation.emp_education_id.value=oidEmpEducation;
	document.frmempeducation.command.value="<%=Command.EDIT%>";
	document.frmempeducation.prev_command.value="<%=prevCommand%>";
	document.frmempeducation.action="empeducation.jsp";
	document.frmempeducation.submit();
}

function cmdBack(){
	document.frmempeducation.command.value="<%=Command.BACK%>";
	document.frmempeducation.action="empeducation.jsp";
	document.frmempeducation.submit();
	}

function cmdListFirst(){
	document.frmempeducation.command.value="<%=Command.FIRST%>";
	document.frmempeducation.prev_command.value="<%=Command.FIRST%>";
	document.frmempeducation.action="empeducation.jsp";
	document.frmempeducation.submit();
}

function cmdListPrev(){
	document.frmempeducation.command.value="<%=Command.PREV%>";
	document.frmempeducation.prev_command.value="<%=Command.PREV%>";
	document.frmempeducation.action="empeducation.jsp";
	document.frmempeducation.submit();
	}

function cmdListNext(){
	document.frmempeducation.command.value="<%=Command.NEXT%>";
	document.frmempeducation.prev_command.value="<%=Command.NEXT%>";
	document.frmempeducation.action="empeducation.jsp";
	document.frmempeducation.submit();
}

function cmdListLast(){
	document.frmempeducation.command.value="<%=Command.LAST%>";
	document.frmempeducation.prev_command.value="<%=Command.LAST%>";
	document.frmempeducation.action="empeducation.jsp";
	document.frmempeducation.submit();
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
				  <td>
					<font color="#FF6600" face="Arial"><strong>
					  <!-- #BeginEditable "contenttitle" -->Employee 
                  &gt; Education<!-- #EndEditable --> 
					</strong></font>
				  </td>
				</tr>
				<tr> 
				 <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td valign="top"> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1">
                          <tr> 
                            <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="frmempeducation" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="emp_education_id" value="<%=oidEmpEducation%>">
                                    <input type="hidden" name="employee_oid" value="<%=oidEmployee%>">
                                    <input type="hidden" name="<%=frmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_EMPLOYEE_ID] %>" value="<%=oidEmployee%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                  <% if(oidEmployee != 0){%>
                                  <tr> 
                                    <td> 
									<br>
                                      <table width="100%" border="0" cellspacing="2" cellpadding="2" height="26">
                                        <tr> 
                                          <td width="2%" bgcolor="#FFFFFF">&nbsp;</td>
                                          <td width="11%" nowrap bgcolor="#0066CC"> 
                                            <div align="center" class="tablink"><a href="javascript:cmdBackEmp('<%=oidEmployee%>')" class="tablink">Personal 
                                              Data</a></div>
                                          </td>
                                          <td width="12%" nowrap bgcolor="#0066CC"> 
                                            <div align="center"  class="tablink"><a href="familymember.jsp?employee_oid=<%=oidEmployee%>" class="tablink">Family 
                                              Member</a></div>
                                          </td>
                                          <td width="9%" nowrap bgcolor="#0066CC"> 
                                            <div align="center"  class="tablink"><a href="emplanguage.jsp?employee_oid=<%=oidEmployee%>" class="tablink">Language</a></div>
                                          </td>
                                          <td width="10%" nowrap bgcolor="#66CCFF"> 
                                            <div align="center"  class="tablink"><%=dictionaryD.getWord(I_Dictionary.EDUCATION) %></div>
                                          </td>
                                          <td width="9%" nowrap bgcolor="#0066CC"> 
                                            <div align="center"  class="tablink"><a href="experience.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><span class="tablink"><%=dictionaryD.getWord(I_Dictionary.EXPERIENCE) %></span></a></div>
                                          </td>
                                          <td width="10%" nowrap bgcolor="#0066CC"> 
                                            <div align="center"><font class="tablink" ><span class="tablink"><a href="careerpath.jsp?employee_oid=<%=oidEmployee%>" class="tablink">Career 
                                              Path</a></span></font></div>
                                          </td>
                                          <td width="37%">&nbsp;</td>
                                        </tr>
                                      </table>
                                    </td>
                                  </tr>
                                  <%}%>
                                  <tr> 
                                    <td class="tablecolor"> 
                                      <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                        <tr> 
                                          <td valign="top"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tabbg">
                                              <tr align="left" valign="top"> 
                                                <td height="8"  colspan="3"> 
                                                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                      <tr align="left" valign="top"> 
                                                        <td height="8" valign="middle" colspan="3">&nbsp;</td>
                                                      </tr>
                                                        <% if(oidEmployee != 0){
                                                                Employee employee = new Employee();
                                                                try{
                                                                         employee = PstEmployee.fetchExc(oidEmployee);
                                                                }catch(Exception exc){
                                                                         employee = new 	Employee();
                                                                }
                                                  %>
                                                    <tr align="left" valign="top"> 
                                                      <td height="14" valign="middle" colspan="3" class="comment"> 
                                                        <table width="100%" border="0" cellspacing="2" cellpadding="1">
                                                          <tr> 
                                                            <td width="17%">Payroll 
                                                              Number </td>
                                                            <td width="3%">:</td>
                                                            <td width="80%"><%=employee.getEmployeeNum()%></td>
                                                          </tr>
                                                          <tr> 
                                                            <td width="17%">Name</td>
                                                            <td width="3%">:</td>
                                                            <td width="80%"><%=employee.getFullName()%></td>
                                                          </tr>
                                                          <% Department department = new Department();
                                                               try{
                                                                            department = PstDepartment.fetchExc(employee.getDepartmentId());
                                                               }catch(Exception exc){
                                                                            department = new Department();
                                                               }
                                                            %>
                                                          <tr> 
                                                            <td width="17%"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                                            <td width="3%">:</td>
                                                            <td width="80%"><%=department.getDepartment()%></td>
                                                          </tr>
                                                          <tr> 
                                                            <td width="17%">Address</td>
                                                            <td width="3%">:</td>
                                                            <td width="80%"><%=employee.getAddress()%></td>
                                                          </tr>
                                                        </table>
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td height="14" valign="middle" colspan="3" class="comment">&nbsp;</td>
                                                    </tr>
                                                    <%}%>
                                              <%
							try{
								if (listEmpEducation.size()>0){
							%>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <%= drawList(listEmpEducation,oidEmpEducation)%> 
                                                </td>
                                              </tr>
                                                    <%  } else{%>
                                                    <tr align="left" valign="top"> 
                                                      <td height="22" valign="middle" colspan="3" class="comment"> 
                                                        No Employee Education 
                                                        available </td>
                                                    </tr>
                                              <%  } 
						  }catch(Exception exc){
						  }%>
                                              <tr align="left" valign="top"> 
                                                <td height="8" align="left" colspan="3" class="command"> 
                                                  <span class="command"> 
                                                  <% 
								   int cmd = 0;
									   if ((iCommand == Command.FIRST || iCommand == Command.PREV )|| 
										(iCommand == Command.NEXT || iCommand == Command.LAST))
											cmd =iCommand; 
								   else{
									  if(iCommand == Command.NONE || prevCommand == Command.NONE)
										cmd = Command.FIRST;
									  else
									  { 
									  	if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidEmpEducation == 0))
									  		cmd = PstEmpEducation.findLimitCommand(start,recordToGet,vectSize);
									  	else
									  		cmd = prevCommand;
									  } 
								   } 
							    %>
                                                  <% ctrLine.setLocationImg(approot+"/images");
							   	ctrLine.initDefault();
								 %>
                                                  <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> 
                                                  </span> </td>
                                              </tr>
                                                <%if(iCommand == Command.NONE || (iCommand == Command.SAVE && frmEmpEducation.errorSize()<1) || iCommand == Command.DELETE || iCommand==Command.BACK ||
                                                        iCommand == Command.FIRST || iCommand == Command.PREV ||iCommand == Command.NEXT || iCommand == Command.LAST){%>
                                                    <% if(privAdd){%>
													<tr align="left" valign="top"> 
                                                      <td> 
                                                        <table cellpadding="0" cellspacing="0" border="0">
                                                          <tr> 
                                                            <td>&nbsp;</td>
                                                          </tr>
                                                          <tr> 
                                                            <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                            <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                            <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                            <td height="22" valign="middle" colspan="3" width="951"><a href="javascript:cmdAdd()" class="command">Add 
                                                              New Education </a> </td>
                                                          </tr>
                                                        </table>
                                                      </td>
                                                    </tr>
                                                <%}
                                                }%>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="8" valign="middle" colspan="3"> 
                                            <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmEmpEducation.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                                                  <table width="100%" border="0" cellspacing="1" cellpadding="0">
                                                    <tr align="left" valign="top"> 
                                                      <td height="21" valign="middle" width="2%">&nbsp;</td>
                                                      <td height="21" valign="middle" width="13%">&nbsp;</td>
                                                      <td height="21" colspan="2" width="85%" class="comment">*)=
                                                        required</td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td height="21" valign="top" width="2%">&nbsp;</td>
                                                      <td height="21" valign="top" width="13%"><%=dictionaryD.getWord(I_Dictionary.EDUCATION) %></td>
                                                      <td height="21" colspan="2" width="85%"> 
                                                        <%--
                                                  <input type="text" name="<%=frmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_EDUCATION_ID] %>"  value="<%= empEducation.getEducationId() %>" class="formElemen">
                                                  * <%= frmEmpEducation.getErrorMsg(FrmEmpEducation.FRM_FIELD_EDUCATION_ID) %> 
                                                  --%>
                                                        <%    Vector education_value = new Vector(1,1);
                                                        Vector education_key = new Vector(1,1);																	
                                                        Vector listEducation = PstEducation.listAll();
                                                        for(int i=0;i<listEducation.size();i++){
                                                            Education education = (Education) listEducation.get(i);
                                                            education_value.add(""+education.getOID());
                                                            education_key.add(""+education.getEducation());
                                                        }
                                                    %>
                                                        <% if((listEducation != null) && (listEducation.size() > 0)){%>
                                                        <%= ControlCombo.draw(frmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_EDUCATION_ID],"formElemen",null, ""+empEducation.getEducationId(), education_value, education_key) %> 
                                                        <% }else {%>
                                                        <font class="comment">No 
                                                        Education available</font> 
                                                        <% }%>
                                                        * <%= frmEmpEducation.getErrorMsg(FrmEmpEducation.FRM_FIELD_EDUCATION_ID) %> 
                                                    </tr>
                                                    <tr align="left" valign="top">
                                                      <td valign="top" width="2%">&nbsp;</td>
                                                      <td valign="top" width="13%" nowrap>Education 
                                                        Description </td>
                                                      <td width="85%">
                                                        <textarea name="<%=frmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_EDUCATION_DESC]%>" class="formElemen" rows="2" cols="40"><%=empEducation.getEducationDesc()%></textarea>
                                                      </td>
                                                    </tr>
                                                    <%-- <tr align="left" valign="top"> 
                                                <td height="21" valign="top" width="17%">Employee 
                                                  Id</td>
                                                <td height="21" colspan="2" width="83%"> 
                                                  <input type="text" name="<%=frmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_EMPLOYEE_ID] %>"  value="<%= empEducation.getEmployeeId() %>" class="formElemen">
                                                  * <%= frmEmpEducation.getErrorMsg(FrmEmpEducation.FRM_FIELD_EMPLOYEE_ID) %> 
                                              </tr> --%>
                                                    <%--
                                              <tr align="left" valign="top"> 
                                                <td height="21" valign="top" width="17%">Start 
                                                  Date</td>
                                                <td height="21" colspan="2" width="83%"> 
                                                  <%=	ControlDate.drawDateWithStyle(frmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_START_DATE], empEducation.getStartDate(), 1,-5, "formElemen", "") %> 
                                                  * <%= frmEmpEducation.getErrorMsg(FrmEmpEducation.FRM_FIELD_START_DATE) %> 
                                              <tr align="left" valign="top"> 
                                                <td height="21" valign="top" width="17%">End 
                                                  Date</td>
                                                <td height="21" colspan="2" width="83%"> 
                                                  <%=	ControlDate.drawDateWithStyle(frmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_END_DATE], empEducation.getEndDate(), 1,-5, "formElemen", "") %> 
                                                  * <%= frmEmpEducation.getErrorMsg(FrmEmpEducation.FRM_FIELD_END_DATE) %> 
                                              --%>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="2%">&nbsp;</td>
                                                      <td valign="top" width="13%" nowrap>Start 
                                                        Date</td>
                                                      <td width="85%"> <%=ControlDate.drawDateYear(frmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_START_DATE], empEducation.getStartDate(),"formElemen",-45,0) %> 
                                                        to <%=	ControlDate.drawDateYear(frmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_END_DATE], empEducation.getEndDate(),"formElemen",-45,0) %> 
                                                        * 
                                                        <% String strStart = frmEmpEducation.getErrorMsg(frmEmpEducation.FRM_FIELD_START_DATE);
                                                         String strEnd = frmEmpEducation.getErrorMsg(frmEmpEducation.FRM_FIELD_END_DATE);
                                                         System.out.println("strStart "+strStart);
                                                         System.out.println("strEnd "+strEnd);
                                                         if((strStart.length()>0)&&(strEnd.length()>0)){
                                                                %>
                                                        <%= strStart %> 
                                                        <%}else{
                                                                if((strStart.length()>0)||(strEnd.length()>0)){%>
                                                        <%= strStart.length()>0?strStart:strEnd %> 
                                                        <%}
                                                        }%>
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td height="21" valign="top" width="2%">&nbsp;</td>
                                                      <td height="21" valign="top" width="13%">Graduation</td>
                                                      <td height="21" colspan="2" width="85%"> 
                                                        <input type="text" name="<%=frmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_GRADUATION] %>"  value="<%= empEducation.getGraduation() %>" class="formElemen">
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td height="8" valign="middle" width="2%">&nbsp;</td>
                                                      <td height="8" valign="middle" width="13%">&nbsp;</td>
                                                      <td height="8" colspan="2" width="85%">&nbsp; 
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top" > 
                                                      <td colspan="4" class="command"> 
                                                        <%
                                                    ctrLine.setLocationImg(approot+"/images");
                                                    ctrLine.initDefault();
                                                    ctrLine.setTableWidth("80");
                                                    String scomDel = "javascript:cmdAsk('"+oidEmpEducation+"')";
                                                    String sconDelCom = "javascript:cmdConfirmDelete('"+oidEmpEducation+"')";
                                                    String scancel = "javascript:cmdEdit('"+oidEmpEducation+"')";
                                                    ctrLine.setBackCaption("Back to List");
                                                    ctrLine.setCommandStyle("buttonlink");
                                                    ctrLine.setDeleteCaption("Delete");
                                                    ctrLine.setSaveCaption("Save");
                                                    ctrLine.setAddCaption("");

                                                    if (privDelete){
                                                            ctrLine.setConfirmDelCommand(sconDelCom);
                                                            ctrLine.setDeleteCommand(scomDel);
                                                            ctrLine.setEditCommand(scancel);
                                                    }else{ 
                                                            ctrLine.setConfirmDelCaption("");
                                                            ctrLine.setDeleteCaption("");
                                                            ctrLine.setEditCaption("");
                                                    }

                                                    if(privAdd == false  && privUpdate == false){
                                                            ctrLine.setSaveCaption("");
                                                    }

                                                    if (privAdd == false){
                                                            ctrLine.setAddCaption("");
                                                    }
                                                    %>
                                                        <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%> 
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="2%">&nbsp;</td>
                                                      <td width="13%">&nbsp;</td>
                                                      <td width="85%">&nbsp;</td>
                                                    </tr>
                                                    <tr align="left" valign="top" > 
                                                      <td colspan="4"> 
                                                        <div align="left"></div>
                                                      </td>
                                                    </tr>
                                                  </table>
                                            <%}%>
                                                </td>
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
                              </form>                              
                              <!-- #EndEditable --> 
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr>
                      <td >&nbsp;</td>
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
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
