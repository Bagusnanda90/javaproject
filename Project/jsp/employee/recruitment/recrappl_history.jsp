
<% 
/* 
 * Page Name  		:  recrapplication_edit.jsp
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
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_RECRUITMENT, AppObjInfo.OBJ_EMPLOYEE_RECRUITMENT_HISTORY); %>
<%@ include file = "../../main/checkuser.jsp" %>

<!-- Jsp Block -->
<%!
	public String drawList(Vector objectClass ,  long recrWorkHistoryId)
	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
                ctrlist.setListStyle("listgen");
                ctrlist.setTitleStyle("listgentitle");
                ctrlist.setCellStyle("listgensell");
                ctrlist.setHeaderStyle("listgentitle");
		//ctrlist.addHeader("Recr Application Id","7%");
		ctrlist.addHeader("Position","7%");
		ctrlist.addHeader("Start Date","7%");
		ctrlist.addHeader("End Date","7%");
		ctrlist.addHeader("Duties","7%");
		//ctrlist.addHeader("Comm Salary","7%");
		//ctrlist.addHeader("Last Salary","7%");
		ctrlist.addHeader("Company Name","7%");
		//ctrlist.addHeader("Company Address","7%");
		//ctrlist.addHeader("Company Phone","7%");
		ctrlist.addHeader("Company Nature","7%");
		ctrlist.addHeader("Company Spv","7%");
		ctrlist.addHeader("Leave Reason","7%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;

		for (int i = 0; i < objectClass.size(); i++) {
			RecrWorkHistory recrWorkHistory = (RecrWorkHistory)objectClass.get(i);
			 Vector rowx = new Vector();
			 if(recrWorkHistoryId == recrWorkHistory.getOID())
				 index = i;
			//rowx.add(String.valueOf(recrWorkHistory.getRecrApplicationId()));
			rowx.add(recrWorkHistory.getPosition());
			String str_dt_StartDate = ""; 
			try{
				Date dt_StartDate = recrWorkHistory.getStartDate();
				if(dt_StartDate==null){
					dt_StartDate = new Date();
				}
				str_dt_StartDate = Formater.formatDate(dt_StartDate, "dd MMMM yyyy");
			}catch(Exception e){ str_dt_StartDate = ""; }
			rowx.add(str_dt_StartDate);
			String str_dt_EndDate = ""; 
			try{
				Date dt_EndDate = recrWorkHistory.getEndDate();
				if(dt_EndDate==null){
					dt_EndDate = new Date();
				}
				str_dt_EndDate = Formater.formatDate(dt_EndDate, "dd MMMM yyyy");
			}catch(Exception e){ str_dt_EndDate = ""; }
			rowx.add(str_dt_EndDate);
			rowx.add(recrWorkHistory.getDuties());
			//rowx.add(String.valueOf(recrWorkHistory.getCommSalary()));
			//rowx.add(String.valueOf(recrWorkHistory.getLastSalary()));
			rowx.add(recrWorkHistory.getCompanyName());
			//rowx.add(recrWorkHistory.getCompanyAddress());
			//rowx.add(recrWorkHistory.getCompanyPhone());
			rowx.add(recrWorkHistory.getCompanyNature());
			rowx.add(recrWorkHistory.getCompanySpv());
			rowx.add(recrWorkHistory.getLeaveReason());
			lstData.add(rowx);
			lstLinkData.add(String.valueOf(recrWorkHistory.getOID()));
		}
		return ctrlist.draw(index);
	}
%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidRecrWorkHistory = FRMQueryString.requestLong(request, "hidden_recr_work_history_id");
    long oidRecrApplication = FRMQueryString.requestLong(request, "hidden_recr_application_id");

    /*variable declaration*/
    int recordToGet = 10;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    String whereClause = PstRecrWorkHistory.fieldNames[PstRecrWorkHistory.FLD_RECR_APPLICATION_ID]+ " = "+oidRecrApplication;
    String orderClause = PstRecrWorkHistory.fieldNames[PstRecrWorkHistory.FLD_START_DATE];

    CtrlRecrWorkHistory ctrlRecrWorkHistory = new CtrlRecrWorkHistory(request);
    ControlLine ctrLine = new ControlLine();
    Vector listRecrWorkHistory = new Vector(1,1);

    /*switch statement */
    iErrCode = ctrlRecrWorkHistory.action(iCommand, oidRecrWorkHistory, oidRecrApplication);
    /* end switch*/
    FrmRecrWorkHistory frmRecrWorkHistory = ctrlRecrWorkHistory.getForm();

    /*count list All RecrWorkHistory*/
    int vectSize = PstRecrWorkHistory.getCount(whereClause);

    RecrWorkHistory recrWorkHistory = ctrlRecrWorkHistory.getRecrWorkHistory();
    msgString =  ctrlRecrWorkHistory.getMessage();

    /*switch list RecrWorkHistory*/
    if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)&& (oidRecrWorkHistory == 0))
            start = PstRecrWorkHistory.findLimitStart(recrWorkHistory.getOID(),recordToGet, whereClause, orderClause);

    if((iCommand == Command.FIRST || iCommand == Command.PREV )||
      (iCommand == Command.NEXT || iCommand == Command.LAST)){
                    start = ctrlRecrWorkHistory.actionList(iCommand, start, vectSize, recordToGet);
     } 
    /* end switch list*/

    /* get record to display */
    listRecrWorkHistory = PstRecrWorkHistory.list(start,recordToGet, whereClause , orderClause);

    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listRecrWorkHistory.size() < 1 && start > 0)
    {
	 if (vectSize - recordToGet > recordToGet)
            start = start - recordToGet;   //go to Command.PREV
	 else{
             start = 0 ;
             iCommand = Command.FIRST;
             prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listRecrWorkHistory = PstRecrWorkHistory.list(start,recordToGet, whereClause , orderClause);
    }
%>
<!-- End of Jsp Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Recruitment</title>
<script language="JavaScript">

function cmdBackEmp(OID){
	document.frmrecrworkhistory.hidden_recr_application_id.value=OID;
	document.frmrecrworkhistory.command.value="<%=Command.EDIT%>";	
	document.frmrecrworkhistory.action="recrapplication_edit.jsp";
	document.frmrecrworkhistory.submit();
	}
        
function cmdBackSkill(OID){
                document.frmrecrworkhistory.hidden_recr_application_id.value=OID;
                document.frmrecrworkhistory.command.value="<%=Command.EDIT%>";	
                document.frmrecrworkhistory.action="recrappl_skill.jsp";
                document.frmrecrworkhistory.submit();
                }
function cmdBackFamilly(OID){
                document.frmrecrworkhistory.hidden_recr_application_id.value=OID;
                document.frmrecrworkhistory.command.value="<%=Command.EDIT%>";	
                document.frmrecrworkhistory.action="recrappl_familly.jsp";
                document.frmrecrworkhistory.submit();
function cmdAdd(){
	document.frmrecrworkhistory.hidden_recr_work_history_id.value="0";
	document.frmrecrworkhistory.command.value="<%=Command.ADD%>";
	document.frmrecrworkhistory.prev_command.value="<%=prevCommand%>";
	document.frmrecrworkhistory.action="recrappl_history.jsp";
	document.frmrecrworkhistory.submit();
}

function cmdAsk(oidRecrWorkHistory){
	document.frmrecrworkhistory.hidden_recr_work_history_id.value=oidRecrWorkHistory;
	document.frmrecrworkhistory.command.value="<%=Command.ASK%>";
	document.frmrecrworkhistory.prev_command.value="<%=prevCommand%>";
	document.frmrecrworkhistory.action="recrappl_history.jsp";
	document.frmrecrworkhistory.submit();
}

function cmdConfirmDelete(oidRecrWorkHistory){
	document.frmrecrworkhistory.hidden_recr_work_history_id.value=oidRecrWorkHistory;
	document.frmrecrworkhistory.command.value="<%=Command.DELETE%>";
	document.frmrecrworkhistory.prev_command.value="<%=prevCommand%>";
	document.frmrecrworkhistory.action="recrappl_history.jsp";
	document.frmrecrworkhistory.submit();
}
function cmdSave(){
	document.frmrecrworkhistory.command.value="<%=Command.SAVE%>";
	document.frmrecrworkhistory.prev_command.value="<%=prevCommand%>";
	document.frmrecrworkhistory.action="recrappl_history.jsp";
	document.frmrecrworkhistory.submit();
	}

function cmdEdit(oidRecrWorkHistory){
	document.frmrecrworkhistory.hidden_recr_work_history_id.value=oidRecrWorkHistory;
	document.frmrecrworkhistory.command.value="<%=Command.EDIT%>";
	document.frmrecrworkhistory.prev_command.value="<%=prevCommand%>";
	document.frmrecrworkhistory.action="recrappl_history.jsp";
	document.frmrecrworkhistory.submit();
	}

function cmdCancel(oidRecrWorkHistory){
	document.frmrecrworkhistory.hidden_recr_work_history_id.value=oidRecrWorkHistory;
	document.frmrecrworkhistory.command.value="<%=Command.EDIT%>";
	document.frmrecrworkhistory.prev_command.value="<%=prevCommand%>";
	document.frmrecrworkhistory.action="recrappl_history.jsp";
	document.frmrecrworkhistory.submit();
}

function cmdBack(){
	document.frmrecrworkhistory.command.value="<%=Command.BACK%>";
	document.frmrecrworkhistory.action="recrappl_history.jsp";
	document.frmrecrworkhistory.submit();
	}

function cmdListFirst(){
	document.frmrecrworkhistory.command.value="<%=Command.FIRST%>";
	document.frmrecrworkhistory.prev_command.value="<%=Command.FIRST%>";
	document.frmrecrworkhistory.action="recrappl_history.jsp";
	document.frmrecrworkhistory.submit();
}

function cmdListPrev(){
	document.frmrecrworkhistory.command.value="<%=Command.PREV%>";
	document.frmrecrworkhistory.prev_command.value="<%=Command.PREV%>";
	document.frmrecrworkhistory.action="recrappl_history.jsp";
	document.frmrecrworkhistory.submit();
	}

function cmdListNext(){
	document.frmrecrworkhistory.command.value="<%=Command.NEXT%>";
	document.frmrecrworkhistory.prev_command.value="<%=Command.NEXT%>";
	document.frmrecrworkhistory.action="recrappl_history.jsp";
	document.frmrecrworkhistory.submit();
}

function cmdListLast(){
	document.frmrecrworkhistory.command.value="<%=Command.LAST%>";
	document.frmrecrworkhistory.prev_command.value="<%=Command.LAST%>";
	document.frmrecrworkhistory.action="recrappl_history.jsp";
	document.frmrecrworkhistory.submit();
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
                  Employee &gt; Recruitment<!-- #EndEditable --> </strong></font> 
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
                            <form name="frmrecrworkhistory" method="post" action="">
                              <input type="hidden" name="command" value="">
                              <input type="hidden" name="start" value="<%=start%>">
                              <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                              <input type="hidden" name="hidden_recr_application_id" value="<%=oidRecrApplication%>">
                              <input type="hidden" name="vectSize" value="<%=vectSize%>">
                              <input type="hidden" name="hidden_recr_work_history_id" value="<%=oidRecrWorkHistory%>">
                              <input type="hidden" name="<%=frmRecrWorkHistory.fieldNames[FrmRecrWorkHistory.FRM_FIELD_RECR_APPLICATION_ID] %>" value="<%=oidRecrApplication%>">
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                  <% if(oidRecrApplication != 0){%>
                                  <tr> 
                                    <td> 
                                      <table  width="73%" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                          <td  valign="top" height="20" width="10%"> 
                                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                              <tr> 
                                                <td valign="top" align="left" width="1">&nbsp;&nbsp;&nbsp;</td>
                                                <td valign="top" align="left" width="10"><img src="<%=approot%>/images/tab/inactive_left.jpg" width="12" height="29"></td>
                                                <td valign="middle" background="<%=approot%>/images/tab/inactive_bg.jpg" nowrap width="59"> 
                                                  <div align="center" class="tablink"><a href="javascript:cmdBackEmp('<%=oidRecrApplication%>')" class="tablink">Personal</a></div>
                                                </td>
                                                <td width="12" valign="top" align="right"><img src="<%=approot%>/images/tab/inactive_right.jpg" width="12" height="29"></td>
                                              </tr>
                                            </table>
                                          </td>
                                          <td  valign="top" height="20" width="10%"> 
                                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                              <tr> 
                                                <td valign="top" align="left" width="1">&nbsp;&nbsp;&nbsp;</td>
                                                <td valign="top" align="left" width="10"><img src="<%=approot%>/images/tab/inactive_left.jpg" width="12" height="29"></td>
                                                <td valign="middle" background="<%=approot%>/images/tab/inactive_bg.jpg" nowrap width="59"> 
                                                  <div align="center" class="tablink"><a href="javascript:cmdBackFamilly('<%=oidRecrApplication%>')" class="tablink">Familly</a></div>
                                                </td>
                                                <td width="12" valign="top" align="right"><img src="<%=approot%>/images/tab/inactive_right.jpg" width="12" height="29"></td>
                                              </tr>
                                            </table>
                                          </td>
                                          
                                          <td  valign="top" height="20" width="10%"> 
                                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                              <tr> 
                                                <td valign="top" align="left" width="10"><img src="<%=approot%>/images/tab/inactive_left.jpg" width="12" height="29"></td>
                                                <td valign="middle" background="<%=approot%>/images/tab/inactive_bg.jpg" nowrap width="59"> 
                                                  <div align="center" class="tablink"><a href="recrappl_education.jsp?hidden_recr_application_id=<%=oidRecrApplication%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.EDUCATION) %></a></div>
                                                </td>
                                                <td width="12" valign="top" align="right"><img src="<%=approot%>/images/tab/inactive_right.jpg" width="12" height="29"></td>
                                              </tr>
                                            </table>
                                          </td>
                                          <td  valign="top" height="20" width="10%"> 
                                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                              <tr> 
                                                <td valign="top" align="left" width="10"><img src="<%=approot%>/images/tab/inactive_left.jpg" width="12" height="29"></td>
                                                <td valign="middle" background="<%=approot%>/images/tab/inactive_bg.jpg" nowrap width="120"> 
                                                  <div align="center" class="tablink"><a href="recrappl_history.jsp?hidden_recr_application_id=<%=oidRecrApplication%>" class="tablink">Emp 
                                                    Record </a></div>
                                                </td>
                                                <td width="12" valign="top" align="right"><img src="<%=approot%>/images/tab/inactive_right.jpg" width="12" height="29"></td>
                                              </tr>
                                            </table>
                                          </td>
                                          <td  width="10%" valign="top" background="../images/tab/active_bg.jpg" height="20"> 
                                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                              <tr> 
                                                <td height="29" valign="top" align="left" width="10"><img src="<%=approot%>/images/tab/inactive_left.jpg" width="12" height="29"></td>
                                                <td valign="middle" background="<%=approot%>/images/tab/inactive_bg.jpg" nowrap width="59"> 
                                                        <div align="center" class="tablink"><a href="recrappl_language.jsp?hidden_recr_application_id=<%=oidRecrApplication%>" class="tablink"><span class="tablink">Language</span></a></div>
                                                </td>
                                                <td width="12" valign="top" align="right"><img src="<%=approot%>/images/tab/inactive_right.jpg" width="12" height="29"></td>
                                              </tr>
                                            </table>
                                          </td>
                                          <td  width="10%" valign="top" background="../images/tab/inactive_bg.jpg" height="20"> 
                                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                              <tr> 
                                                <td height="29" valign="top" align="left" background="../images/tab/inactive_bg.jpg" width="10"><img src="<%=approot%>/images/tab/inactive_left.jpg" width="12" height="29"></td>
                                                <td width="70"  nowrap valign="middle" background="<%=approot%>/images/tab/inactive_bg.jpg"> 
                                                        <div align="center" class="tablink"><a href="recrappl_references.jsp?hidden_recr_application_id=<%=oidRecrApplication%>" class="tablink"><span class="tablink">References</span></a></div>
                                                </td>
                                                <td width="12" valign="top" align="right" background="../images/tab/active_bg.jpg"><img src="<%=approot%>/images/tab/inactive_right.jpg" width="12" height="29"></td>
                                              </tr>
                                            </table>
                                          </td>
                                          <td  width="10%" valign="top" background="../images/tab/inactive_bg.jpg" height="20"> 
                                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                              <tr> 
                                                <td height="29" valign="top" align="left" background="../images/tab/inactive_bg.jpg" width="10"><img src="<%=approot%>/images/tab/inactive_left.jpg" width="12" height="29"></td>
                                                <td width="70"  nowrap valign="middle" background="<%=approot%>/images/tab/inactive_bg.jpg"> 
                                                        <div align="center" class="tablink"><a href="recrappl_general.jsp?hidden_recr_application_id=<%=oidRecrApplication%>" class="tablink"><span class="tablink">General</span></a></div>
                                                </td>
                                                <td width="12" valign="top" align="right" background="../images/tab/active_bg.jpg"><img src="<%=approot%>/images/tab/inactive_right.jpg" width="12" height="29"></td>
                                              </tr>
                                            </table>
                                          </td>
                                          <td  width="10%" valign="top" background="../images/tab/inactive_bg.jpg" height="20"> 
                                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                              <tr> 
                                                <td height="29" valign="top" align="left" background="../images/tab/inactive_bg.jpg" width="10"><img src="<%=approot%>/images/tab/inactive_left.jpg" width="12" height="29"></td>
                                                <td width="70"  nowrap valign="middle" background="<%=approot%>/images/tab/inactive_bg.jpg"> 
                                                        <div align="center" class="tablink"><a href="recrappl_interview.jsp?hidden_recr_application_id=<%=oidRecrApplication%>&command=<%=Command.EDIT%>" class="tablink"><span class="tablink">Interview</span></a></div>
                                                </td>
                                                <td width="12" valign="top" align="right" background="../images/tab/active_bg.jpg"><img src="<%=approot%>/images/tab/inactive_right.jpg" width="12" height="29"></td>
                                              </tr>
                                            </table>
                                          </td>
                                          <td  valign="top" height="20" width="10%"> 
                                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                              <tr> 
                                                <td valign="top" align="left" width="1">&nbsp;&nbsp;&nbsp;</td>
                                                <td valign="top" align="left" width="10"><img src="<%=approot%>/images/tab/inactive_left.jpg" width="12" height="29"></td>
                                                <td valign="middle" background="<%=approot%>/images/tab/inactive_bg.jpg" nowrap width="59"> 
                                                  <div align="center" class="tablink"><a href="javascript:cmdBackSkill('<%=oidRecrApplication%>')" class="tablink">Skill</a></div>
                                                </td>
                                                <td width="12" valign="top" align="right"><img src="<%=approot%>/images/tab/inactive_right.jpg" width="12" height="29"></td>
                                              </tr>
                                            </table>
                                          </td>
                                          <td width="40%" valign="top" height="20">&nbsp;</td>
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
                                            <table width="100%" height="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                              <tr> 
                                                <td valign="top" width="50%"> 

                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                          <tr align="left" valign="top">
                                                            <td height="14" valign="middle" colspan="3" class="comment">&nbsp;</td>
                                                          </tr>
                                                          <tr align="left" valign="top"> 
                                                            <td height="14" valign="middle" colspan="3" class="comment">Employment 
                                                              Record List </td>
                                                          </tr>
                                                          <%
							try{
								if (listRecrWorkHistory.size()>0){
							%>
                                                          <tr align="left" valign="top"> 
                                                            <td height="22" valign="middle" colspan="3"> 
                                                              <%= drawList(listRecrWorkHistory,oidRecrWorkHistory)%> 
                                                            </td>
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
									  	if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidRecrWorkHistory == 0))
									  		cmd = PstRecrWorkHistory.findLimitCommand(start,recordToGet,vectSize);
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
                                                          </tr><%--
                                                          <tr align="left" valign="top"> 
                                                            <td height="22" valign="middle" colspan="3"><a href="javascript:cmdAdd()" class="command">Add 
                                                              New</a></td>
                                                          </tr>--%>
                                                    <% if(iCommand == Command.NONE || (iCommand == Command.SAVE && frmRecrWorkHistory.errorSize()<1)|| iCommand == Command.DELETE || iCommand == Command.BACK || iCommand == Command.FIRST || iCommand == Command.PREV || iCommand == Command.NEXT || iCommand == Command.LAST ){%>
                                                    <% if(privAdd){%>
                                                    <tr align="left" valign="top"> 
                                                      <td> 
                                                        <table cellpadding="0" cellspacing="0" border="0">
                                                          <tr> 
                                                            <td>&nbsp;</td>
                                                          </tr>
                                                          <tr> 
                                                            <td width="4" height="25"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                            <td width="24" height="25"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                            <td width="6" height="25"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                            <td height="25" valign="middle" colspan="3" width="951"><a href="javascript:cmdAdd()" class="command">Add 
                                                              New Employment Record</a> </td>
                                                          </tr>
                                                        </table>
                                                      </td>
                                                    </tr>
                                                    <% } 
                                                    }%>
                                             </table>
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="8" valign="middle" colspan="3"> 
                                            <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmRecrWorkHistory.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="0">
                                                          <tr align="left" valign="top"> 
                                                            <td height="21" valign="middle" width="17%">&nbsp;</td>
                                                            <td height="21" colspan="2" width="83%" class="comment">*)= 
                                                              required</td>
                                                          </tr>
                                                          <tr align="left" valign="top"> 
                                                            <td height="21" valign="top" colspan="3"> 
                                                              <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                <tr> 
                                                                  <td width="3%">&nbsp;</td>
                                                                  <td width="10%" nowrap>Position</td>
                                                                  <td width="87%"> 
                                                                    <input type="text" name="<%=frmRecrWorkHistory.fieldNames[FrmRecrWorkHistory.FRM_FIELD_POSITION] %>"  value="<%= recrWorkHistory.getPosition() %>" class="formElemen" size="50">
                                                                    * <%= frmRecrWorkHistory.getErrorMsg(FrmRecrWorkHistory.FRM_FIELD_POSITION) %></td>
                                                                </tr>
                                                                <tr> 
                                                                  <td width="3%">&nbsp;</td>
                                                                  <td width="10%" nowrap>From</td>
                                                                  <td width="87%"><%=	ControlDate.drawDateWithStyle(frmRecrWorkHistory.fieldNames[FrmRecrWorkHistory.FRM_FIELD_START_DATE], recrWorkHistory.getStartDate(), 1,-5, "formElemen", "") %> 
                                                                    * <%= frmRecrWorkHistory.getErrorMsg(FrmRecrWorkHistory.FRM_FIELD_START_DATE) %>&nbsp;&nbsp;&nbsp;To&nbsp;&nbsp;&nbsp;<%=	ControlDate.drawDateWithStyle(frmRecrWorkHistory.fieldNames[FrmRecrWorkHistory.FRM_FIELD_END_DATE], recrWorkHistory.getEndDate(), 1,-5, "formElemen", "") %> 
                                                                    * <%= frmRecrWorkHistory.getErrorMsg(FrmRecrWorkHistory.FRM_FIELD_END_DATE) %></td>
                                                                </tr>
                                                                <tr> 
                                                                  <td width="3%">&nbsp;</td>
                                                                  <td width="10%" nowrap>Duties</td>
                                                                  <td width="87%"> 
                                                                    <input type="text" name="<%=frmRecrWorkHistory.fieldNames[FrmRecrWorkHistory.FRM_FIELD_DUTIES] %>"  value="<%= recrWorkHistory.getDuties() %>" class="formElemen" size="50">
                                                                  </td>
                                                                </tr>
                                                                <tr> 
                                                                  <td width="3%">&nbsp;</td>
                                                                  <td width="10%" nowrap>Commencing 
                                                                    Salary</td>
                                                                  <td width="87%"> 
                                                                    <input type="text" name="<%=frmRecrWorkHistory.fieldNames[FrmRecrWorkHistory.FRM_FIELD_COMM_SALARY] %>"  value="<%= recrWorkHistory.getCommSalary() %>" class="formElemen" size="20">
                                                                  </td>
                                                                </tr>
                                                                <tr> 
                                                                  <td width="3%">&nbsp;</td>
                                                                  <td width="10%" nowrap>Last 
                                                                    Drawn Salary</td>
                                                                  <td width="87%"> 
                                                                    <input type="text" name="<%=frmRecrWorkHistory.fieldNames[FrmRecrWorkHistory.FRM_FIELD_LAST_SALARY] %>"  value="<%= recrWorkHistory.getLastSalary() %>" class="formElemen" size="20">
                                                                  </td>
                                                                </tr>
                                                                <tr> 
                                                                  <td width="3%">&nbsp;</td>
                                                                  <td width="10%" nowrap>Company 
                                                                    Name</td>
                                                                  <td width="87%"> 
                                                                    <input type="text" name="<%=frmRecrWorkHistory.fieldNames[FrmRecrWorkHistory.FRM_FIELD_COMPANY_NAME] %>"  value="<%= recrWorkHistory.getCompanyName() %>" class="formElemen" size="50">
                                                                    * <%= frmRecrWorkHistory.getErrorMsg(FrmRecrWorkHistory.FRM_FIELD_COMPANY_NAME) %></td>
                                                                </tr>
                                                                <tr> 
                                                                  <td width="3%">&nbsp;</td>
                                                                  <td width="10%" nowrap>Address</td>
                                                                  <td width="87%"> 
                                                                    <input type="text" name="<%=frmRecrWorkHistory.fieldNames[FrmRecrWorkHistory.FRM_FIELD_COMPANY_ADDRESS] %>"  value="<%= recrWorkHistory.getCompanyAddress() %>" class="formElemen" size="50">
                                                                  </td>
                                                                </tr>
                                                                <tr> 
                                                                  <td width="3%">&nbsp;</td>
                                                                  <td width="10%" nowrap>Telephone</td>
                                                                  <td width="87%"> 
                                                                    <input type="text" name="<%=frmRecrWorkHistory.fieldNames[FrmRecrWorkHistory.FRM_FIELD_COMPANY_PHONE] %>"  value="<%= recrWorkHistory.getCompanyPhone() %>" class="formElemen" size="20">
                                                                  </td>
                                                                </tr>
                                                                <tr> 
                                                                  <td width="3%">&nbsp;</td>
                                                                  <td width="10%" nowrap>Nature 
                                                                    of Business</td>
                                                                  <td width="87%"> 
                                                                    <input type="text" name="<%=frmRecrWorkHistory.fieldNames[FrmRecrWorkHistory.FRM_FIELD_COMPANY_NATURE] %>"  value="<%= recrWorkHistory.getCompanyNature() %>" class="formElemen" size="50">
                                                                  </td>
                                                                </tr>
                                                                <tr> 
                                                                  <td width="3%">&nbsp;</td>
                                                                  <td width="10%" nowrap>Name 
                                                                    of the Superior</td>
                                                                  <td width="87%"> 
                                                                    <input type="text" name="<%=frmRecrWorkHistory.fieldNames[FrmRecrWorkHistory.FRM_FIELD_COMPANY_SPV] %>"  value="<%= recrWorkHistory.getCompanySpv() %>" class="formElemen" size="50">
                                                                  </td>
                                                                </tr>
                                                                <tr> 
                                                                  <td width="3%">&nbsp;</td>
                                                                  <td width="10%" nowrap>Reason 
                                                                    for Leaving</td>
                                                                  <td width="87%"> 
                                                                    <input type="text" name="<%=frmRecrWorkHistory.fieldNames[FrmRecrWorkHistory.FRM_FIELD_LEAVE_REASON] %>"  value="<%= recrWorkHistory.getLeaveReason() %>" class="formElemen" size="50">
                                                                    * <%= frmRecrWorkHistory.getErrorMsg(FrmRecrWorkHistory.FRM_FIELD_LEAVE_REASON) %> 
                                                                  </td>
                                                                </tr>
                                                              </table>
                                                            </td>
                                                          </tr>
                                                          <tr align="left" valign="top" > 
                                                            <td colspan="3" class="command">&nbsp;</td>
                                                          </tr>
                                                          <tr align="left" valign="top" > 
                                                            <td colspan="3" class="command"> 
                                                              <%
                                                    ctrLine.setLocationImg(approot+"/images");
                                                    ctrLine.initDefault();
                                                    ctrLine.setTableWidth("80");
                                                    String scomDel = "javascript:cmdAsk('"+oidRecrWorkHistory+"')";
                                                    String sconDelCom = "javascript:cmdConfirmDelete('"+oidRecrWorkHistory+"')";
                                                    String scancel = "javascript:cmdEdit('"+oidRecrWorkHistory+"')";
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
                                                          <tr align="left" valign="top" >
                                                            <td colspan="3" class="command">&nbsp;</td>
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
                                  </table>
                                </td>
                              </tr>
                            </table>
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
