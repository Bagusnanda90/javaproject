
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

<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_RECRUITMENT, AppObjInfo.OBJ_EMPLOYEE_RECRUITMENT_INTERVIEW); %>
<%@ include file = "../../main/checkuser.jsp" %>

<!-- Jsp Block -->
<%
	CtrlRecrApplicationMisc ctrlRecrApplicationMisc = new CtrlRecrApplicationMisc(request);

    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidRecrApplication = FRMQueryString.requestLong(request, "hidden_recr_application_id");

	int iErrCode = FRMMessage.ERR_NONE;
	iErrCode = ctrlRecrApplicationMisc.action(iCommand , oidRecrApplication);

	FrmRecrApplicationMisc frmRecrApplicationMisc = ctrlRecrApplicationMisc.getForm();
	RecrApplication recrApplication = ctrlRecrApplicationMisc.getRecrApplication();

        //RecrApplication recrApplication = PstRecrApplication.fetchExc(oidRecrApplication);

    if (iCommand == Command.SAVE) {
        /* Proses bagian Interview Result */
        RecrInterviewResult recrInterviewResult = new RecrInterviewResult();
        String[] arrInterviewId = null;
        long interviewerFactorId = 0;

        try {
            arrInterviewId = request.getParameterValues("INTERVIEW_ID");
        }
        catch (Exception e) {}

        if ((arrInterviewId != null) && (arrInterviewId.length > 0)) {
            for (int i = 0; i < arrInterviewId.length; i++) {
                if ((arrInterviewId[i].length() > 0)) {
                    try {
                        interviewerFactorId = Long.parseLong(arrInterviewId[i]);
                        long oidPoint = FRMQueryString.requestLong(request, arrInterviewId[i]);
                        System.out.println(i + " - " + interviewerFactorId + " = " + oidPoint);

                        String where = "";
                        where += PstRecrInterviewResult.fieldNames[PstRecrInterviewResult.FLD_RECR_INTERVIEWER_FACTOR_ID] + "=" + interviewerFactorId + " AND ";
                        where += PstRecrInterviewResult.fieldNames[PstRecrInterviewResult.FLD_RECR_APPLICATION_ID] + "=" + oidRecrApplication;
                        Vector listRecrInterviewResult = PstRecrInterviewResult.list(0, 0, where, "");

                        if (listRecrInterviewResult.size() > 0) {
                            recrInterviewResult = (RecrInterviewResult) listRecrInterviewResult.get(0);
                            recrInterviewResult.setRecrInterviewPointId(oidPoint);
                            recrInterviewResult.setRecrInterviewerFactorId(interviewerFactorId);
                            recrInterviewResult.setRecrApplicationId(oidRecrApplication);
                            PstRecrInterviewResult.updateExc(recrInterviewResult);
                        }
                        else {
                            recrInterviewResult.setRecrInterviewPointId(oidPoint);
                            recrInterviewResult.setRecrInterviewerFactorId(interviewerFactorId);
                            recrInterviewResult.setRecrApplicationId(oidRecrApplication);
                            PstRecrInterviewResult.insertExc(recrInterviewResult);
                        }
                    }
                    catch (Exception e) {}
                }
            }
        }

        /* Proses bagian General Comments */
        RecrComment recrComment = new RecrComment();
        String[] arrCommentId = null;
        long commentId = 0;

        try {
            arrCommentId = request.getParameterValues("COMMENT_ID");
        }
        catch (Exception e) {}

        if ((arrCommentId != null) && (arrCommentId.length > 0)) {
            for (int i = 0; i < arrCommentId.length; i++) {
                if ((arrCommentId[i].length() > 0)) {
                    try {
                        commentId = Long.parseLong(arrCommentId[i]);
                        String strComment = FRMQueryString.requestString(request, arrCommentId[i]);
                        System.out.println(i + " - " + commentId + " = " + strComment);

                        String whereComment = "";
                        whereComment += PstRecrComment.fieldNames[PstRecrComment.FLD_RECR_INTERVIEWER_ID] + "=" + commentId + " AND ";
                        whereComment += PstRecrComment.fieldNames[PstRecrComment.FLD_RECR_APPLICATION_ID] + "=" + oidRecrApplication;
                        Vector listComment = PstRecrComment.list(0, 0, whereComment, "");

                        if (listComment.size() > 0) {
                            recrComment = (RecrComment) listComment.get(0);
                            recrComment.setRecrInterviewerId(commentId);
                            recrComment.setComment(strComment);
                            recrComment.setRecrApplicationId(oidRecrApplication);
                            PstRecrComment.updateExc(recrComment);
                        }
                        else {
                            recrComment.setRecrInterviewerId(commentId);
                            recrComment.setComment(strComment);
                            recrComment.setRecrApplicationId(oidRecrApplication);
                            PstRecrComment.insertExc(recrComment);
                        }
                    }
                    catch (Exception e) {}
                }
            }
        }
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
	document.frmrecrinterviewresult.hidden_recr_application_id.value=OID;
	document.frmrecrinterviewresult.command.value="<%=Command.EDIT%>";	
	document.frmrecrinterviewresult.action="recrapplication_edit.jsp";
	document.frmrecrinterviewresult.submit();
	}

function cmdAdd(){
	document.frmrecrinterviewresult.hidden_recr_references_id.value="0";
	document.frmrecrinterviewresult.command.value="<%=Command.ADD%>";
	document.frmrecrinterviewresult.prev_command.value="<%=prevCommand%>";
	document.frmrecrinterviewresult.action="recrappl_interview.jsp";
	document.frmrecrinterviewresult.submit();
}

function cmdAsk(oidRecrReferences){
	document.frmrecrinterviewresult.hidden_recr_references_id.value=oidRecrReferences;
	document.frmrecrinterviewresult.command.value="<%=Command.ASK%>";
	document.frmrecrinterviewresult.prev_command.value="<%=prevCommand%>";
	document.frmrecrinterviewresult.action="recrappl_interview.jsp";
	document.frmrecrinterviewresult.submit();
}

function cmdConfirmDelete(oidRecrReferences){
	document.frmrecrinterviewresult.hidden_recr_references_id.value=oidRecrReferences;
	document.frmrecrinterviewresult.command.value="<%=Command.DELETE%>";
	document.frmrecrinterviewresult.prev_command.value="<%=prevCommand%>";
	document.frmrecrinterviewresult.action="recrappl_interview.jsp";
	document.frmrecrinterviewresult.submit();
}
function cmdSave(){
	document.frmrecrinterviewresult.command.value="<%=Command.SAVE%>";
	document.frmrecrinterviewresult.prev_command.value="<%=prevCommand%>";
	document.frmrecrinterviewresult.action="recrappl_interview.jsp";
	document.frmrecrinterviewresult.submit();
	}

function cmdEdit(oidRecrReferences){
	document.frmrecrinterviewresult.hidden_recr_references_id.value=oidRecrReferences;
	document.frmrecrinterviewresult.command.value="<%=Command.EDIT%>";
	document.frmrecrinterviewresult.prev_command.value="<%=prevCommand%>";
	document.frmrecrinterviewresult.action="recrappl_interview.jsp";
	document.frmrecrinterviewresult.submit();
	}

function cmdCancel(oidRecrReferences){
	document.frmrecrinterviewresult.hidden_recr_references_id.value=oidRecrReferences;
	document.frmrecrinterviewresult.command.value="<%=Command.EDIT%>";
	document.frmrecrinterviewresult.prev_command.value="<%=prevCommand%>";
	document.frmrecrinterviewresult.action="recrappl_interview.jsp";
	document.frmrecrinterviewresult.submit();
}

function cmdBack(){
	document.frmrecrinterviewresult.command.value="<%=Command.BACK%>";
	document.frmrecrinterviewresult.action="recrappl_interview.jsp";
	document.frmrecrinterviewresult.submit();
	}

//-------------- script control line -------------------
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
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSaveOn.jpg')">
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
                            <form name="frmrecrinterviewresult" method="post" action="">
                              <input type="hidden" name="command" value="">
                              <input type="hidden" name="start" value="<%=start%>">
                              <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                              <input type="hidden" name="hidden_recr_application_id" value="<%=oidRecrApplication%>">
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
                                                <td valign="top" align="left" width="12"><img src="<%=approot%>/images/tab/inactive_left.jpg" width="12" height="29"></td>
                                                <td valign="middle" background="<%=approot%>/images/tab/inactive_bg.jpg" nowrap width="120"> 
                                                  <div align="center" class="tablink"><a href="recrappl_history.jsp?hidden_recr_application_id=<%=oidRecrApplication%>" class="tablink">Employment 
                                                    Record </a></div>
                                                </td>
                                                <td width="10" valign="top" align="right"><img src="<%=approot%>/images/tab/inactive_right.jpg" width="12" height="29"></td>
                                              </tr>
                                            </table>
                                          </td>
                                          <td  width="10%" valign="top" background="../images/tab/inactive_bg.jpg" height="20"> 
                                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                              <tr> 
                                                <td height="29" valign="top" align="left" background="../images/tab/inactive_bg.jpg" width="10"><img src="<%=approot%>/images/tab/inactive_left.jpg" width="12" height="29"></td>
                                                <td width="70"  nowrap valign="middle" background="<%=approot%>/images/tab/inactive_bg.jpg"> 
                                                  <div align="center" class="tablink"><a href="recrappl_language.jsp?hidden_recr_application_id=<%=oidRecrApplication%>" class="tablink"><span class="tablink">Language</span></a></div>
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
                                          <td  valign="top" height="20" width="10%"> 
                                            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="txtalign">
                                              <tr> 
                                                <td valign="top" align="left" width="12"><img src="<%=approot%>/images/tab/active_left.jpg" width="12" height="29"></td>
                                                <td valign="middle" background="<%=approot%>/images/tab/active_bg.jpg" nowrap> 
                                                  <div align="center" class="tablink">Interview</div>
                                                </td>
                                                <td width="13" valign="top" align="right"><img src="<%=approot%>/images/tab/active_right.jpg" width="12" height="29"></td>
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
                                                    <tr> 
                                                      <td>&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td> 
                                                        <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                          <tr> 
                                                            <td width="0%">&nbsp;</td>
                                                            <td width="96%"><b>INTERVIEW 
                                                              RESULT</b></td>
                                                            <td width="4%">&nbsp;</td>
                                                          </tr>
                                                          <tr> 
                                                            <td width="0%">&nbsp;</td>
                                                            <td width="96%"> 
                                                              <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                <tr> 
                                                                  <td width="12%" nowrap>Write 
                                                                    the Point</td>
                                                                  <%
                                                                      Vector listpoint = PstRecrInterviewPoint.listAll();
                                                                      for (int i=0; i<listpoint.size(); i++) {
                                                                        RecrInterviewPoint rip = (RecrInterviewPoint) listpoint.get(i);
                                                                  %>
                                                                  <td nowrap>&nbsp;<%=rip.getInterviewPoint()%>&nbsp;-&nbsp;<%=rip.getInterviewMark()%></td>
                                                                  <%
                                                                      }
                                                                  %>
                                                                  <td width="100%">&nbsp;</td>
                                                                </tr>
                                                              </table>
                                                            </td>
                                                            <td width="4%">&nbsp;</td>
                                                          </tr>
                                                          <tr> 
                                                            <td width="0%">&nbsp;</td>
                                                            <td width="96%"> 
                                                              <table width="100%" border="1" cellspacing="2" cellpadding="2">
                                                                <tr> 
                                                                  <td width="17%"> 
                                                                    <div align="center"><b>Factors</b></div>
                                                                  </td>
                                                                  <%
                                                                    Vector listinterviewer = PstRecrInterviewer.listAll();
                                                                    for (int i=0; i<listinterviewer.size(); i++) {
                                                                        RecrInterviewer rint = (RecrInterviewer) listinterviewer.get(i);
                                                                  %>
                                                                  <td width="10%"> 
                                                                    <div align="center"><b><%=rint.getInterviewer()%></b></div>
                                                                  </td>
                                                                  <%
                                                                    }
                                                                  %>
                                                                </tr>
                                                                <%
                                                                  Vector listfactor = PstRecrInterviewFactor.listAll();
                                                                  for (int j=0; j<listfactor.size(); j++) {
                                                                      RecrInterviewFactor rif = (RecrInterviewFactor) listfactor.get(j);
                                                                %>
                                                                <tr> 
                                                                  <td width="10%">&nbsp;<%=rif.getInterviewFactor()%></td>
                                                                  <%
                                                                    for (int i=0; i<listinterviewer.size(); i++) {
                                                                        String where = "";
                                                                        RecrInterviewer ri = (RecrInterviewer) listinterviewer.get(i);

                                                                        /* dipakai untuk penamaan combo box - see rierf.getOID() */
                                                                        RecrInterviewerFactor rierf = new RecrInterviewerFactor();

                                                                        long factorId = rif.getOID();
                                                                        long interviewerId = ri.getOID();
                                                                        where += PstRecrInterviewerFactor.fieldNames[PstRecrInterviewerFactor.FLD_RECR_INTERVIEW_FACTOR_ID] + "=" + factorId + " AND ";
                                                                        where += PstRecrInterviewerFactor.fieldNames[PstRecrInterviewerFactor.FLD_RECR_INTERVIEWER_ID] + "=" + interviewerId;
                                                                        //System.out.println(where);
                                                                        Vector listRIERF = PstRecrInterviewerFactor.list(0, 0, where, "");
                                                                        if (listRIERF.size() > 0) {
                                                                            rierf = (RecrInterviewerFactor) listRIERF.get(0);
                                                                        }
                                                                        //System.out.println(rierf.getOID());
                                                                  %>
                                                                  <td> 
                                                                    <div align="center"> 
                                                                      <%
                                                                        /***********************************************
                                                                         * get record dari hr_recr_interview_result
                                                                         * parameter : recr_interviewer_factor_id = rierf.getOID()
                                                                         *             recr_application_id        = oidRecrApplication
                                                                         * result    : recr_interview_point_id
                                                                         ***********************************************/
                                                                        String whereResult = "";
                                                                        whereResult += PstRecrInterviewResult.fieldNames[PstRecrInterviewResult.FLD_RECR_INTERVIEWER_FACTOR_ID] + "=" + rierf.getOID() + " AND ";
                                                                        whereResult += PstRecrInterviewResult.fieldNames[PstRecrInterviewResult.FLD_RECR_APPLICATION_ID] + "=" + oidRecrApplication;
                                                                        Vector listInterviewResult = PstRecrInterviewResult.list(0, 0, whereResult, "");

                                                                        RecrInterviewResult rir = new RecrInterviewResult();
                                                                        if ((listInterviewResult != null) && (listInterviewResult.size() > 0)) {
                                                                            rir = (RecrInterviewResult) listInterviewResult.get(0);
                                                                        }

                                                                        //--- membuat combo box yg isinya point & mark ---
                                                                        Vector point_value = new Vector(1,1);
                                                                        Vector point_key = new Vector(1,1);
                                                                        Vector listpointres = PstRecrInterviewPoint.listAll();
                                                                        point_value.add("0");
                                                                        point_key.add("...");
                                                                        for(int p=0; p<listpointres.size(); p++){
                                                                            RecrInterviewPoint rip = (RecrInterviewPoint) listpointres.get(p);
                                                                            point_value.add(""+rip.getOID());
                                                                            point_key.add(""+rip.getInterviewPoint() + " - " + rip.getInterviewMark());
                                                                        }
                                                                        //-------------------------------------------------
                                                                      %>
                                                                      <% if((listpointres != null) && (listpointres.size()>0)){%>
                                                                      <input type="hidden" name="INTERVIEW_ID" value="<%=rierf.getOID()%>">
                                                                      <%= ControlCombo.draw(""+rierf.getOID(),"formElemen",null, ""+rir.getRecrInterviewPointId(), point_value, point_key) %> 
                                                                      <% }else {%>
                                                                      <font class="comment">No 
                                                                      Point available</font> 
                                                                      <% }%>
                                                                    </div>
                                                                  </td>
                                                                  <%
                                                                    }
                                                                  %>
                                                                </tr>
                                                                <%
                                                                  }
                                                                %>
                                                              </table>
                                                            </td>
                                                            <td width="4%">&nbsp;</td>
                                                          </tr>
                                                          <tr> 
                                                            <td width="0%">&nbsp;</td>
                                                            <td width="96%">&nbsp;</td>
                                                            <td width="4%">&nbsp;</td>
                                                          </tr>
                                                          <tr> 
                                                            <td width="0%">&nbsp;</td>
                                                            <td width="96%"><b>GENERAL 
                                                              COMMENTS</b> </td>
                                                            <td width="4%">&nbsp;</td>
                                                          </tr>
                                                          <tr> 
                                                            <td width="0%">&nbsp;</td>
                                                            <td width="96%"> 
                                                              <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                <%
                                                                    for (int i=0; i<listinterviewer.size(); i++) {
                                                                        RecrInterviewer rint = (RecrInterviewer) listinterviewer.get(i);
                                                                        String whereComment = "";
                                                                        whereComment += PstRecrComment.fieldNames[PstRecrComment.FLD_RECR_INTERVIEWER_ID] + "=" + rint.getOID() + " AND ";
                                                                        whereComment += PstRecrComment.fieldNames[PstRecrComment.FLD_RECR_APPLICATION_ID] + "=" + oidRecrApplication;
                                                                        Vector listComment = PstRecrComment.list(0, 0, whereComment, "");
                                                                        RecrComment recrComment = new RecrComment();
                                                                        if ((listComment != null) && (listComment.size() > 0)) {
                                                                            recrComment = (RecrComment) listComment.get(0);
                                                                        }
                                                                  %>
                                                                <tr> 
                                                                  <td nowrap valign="top" width="3%"><%=rint.getInterviewer()%></td>
                                                                  <td width="97%"> 
                                                                    <input type="hidden" name="COMMENT_ID" value="<%=rint.getOID()%>">
                                                                    <textarea cols="60" name="<%=rint.getOID()%>" class="formElemen"><%= recrComment.getComment() %></textarea>
                                                                  </td>
                                                                </tr>
                                                                <%
                                                                    }
                                                                %>
                                                              </table>
                                                            </td>
                                                            <td width="4%">&nbsp;</td>
                                                          </tr>
                                                          <tr> 
                                                            <td width="0%">&nbsp;</td>
                                                            <td width="96%">&nbsp;</td>
                                                            <td width="4%">&nbsp;</td>
                                                          </tr>
                                                          <tr>
                                                            <td width="0%">&nbsp;</td>
                                                            <td width="96%"><b>TO 
                                                              BE COMPLETED BY 
                                                              PERSONNEL DEPARTMENT</b></td>
                                                            <td width="4%">&nbsp;</td>
                                                          </tr>
                                                          <tr> 
                                                            <td width="0%">&nbsp;</td>
                                                            <td width="96%">
                                                              <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                <tr>
                                                                  <td>Position</td>
                                                                  <td nowrap>
                                                                    <% 
                                                                        Vector pos_value = new Vector(1,1);
                                                                        Vector pos_key = new Vector(1,1);
                                                                        Vector listPos = PstPosition.list(0, 0, "", " POSITION ");
                                                                        pos_key.add("...");
                                                                        pos_value.add("0");
                                                                        for (int i = 0; i < listPos.size(); i++) {
                                                                                Position pos = (Position) listPos.get(i);
                                                                                pos_key.add(pos.getPosition());
                                                                                pos_value.add(String.valueOf(pos.getOID()));
                                                                        }
                                                                    %>
                                                                    <%=ControlCombo.draw(FrmRecrApplicationMisc.fieldNames[FrmRecrApplicationMisc.FRM_FIELD_FNL_POSITION_ID],"formElemen",null, "" +recrApplication.getFnlPositionId(), pos_value, pos_key) %> 
                                                                    *
                                                                    <%=frmRecrApplicationMisc.getErrorMsg(FrmRecrApplicationMisc.FRM_FIELD_FNL_POSITION_ID)%>
                                                                  </td>
                                                                  <td><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                                                  <td nowrap>
                                                                    <% 
                                                                        Vector dept_value = new Vector(1,1);
                                                                        Vector dept_key = new Vector(1,1);
                                                                        Vector listDept = PstDepartment.list(0, 0, "", " DEPARTMENT ");
                                                                        dept_key.add("...");
                                                                        dept_value.add("0");
                                                                        for (int i = 0; i < listDept.size(); i++) {
                                                                                Department dept = (Department) listDept.get(i);
                                                                                dept_key.add(dept.getDepartment());
                                                                                dept_value.add(String.valueOf(dept.getOID()));
                                                                        }
                                                                    %>
                                                                    <%=ControlCombo.draw(FrmRecrApplicationMisc.fieldNames[FrmRecrApplicationMisc.FRM_FIELD_FNL_DEPARTMENT_ID],"formElemen",null, ""+recrApplication.getFnlDepartmentId(), dept_value, dept_key) %>
                                                                    *
                                                                    <%=frmRecrApplicationMisc.getErrorMsg(FrmRecrApplicationMisc.FRM_FIELD_FNL_DEPARTMENT_ID)%>
                                                                  </td>
                                                                  <td>Grade</td>
                                                                  <td nowrap>
                                                                    <% 
                                                                        Vector lvl_value = new Vector(1,1);
                                                                        Vector lvl_key = new Vector(1,1);
                                                                        lvl_key.add("...");
                                                                        lvl_value.add("0");
                                                                        Vector listlvl = PstLevel.list(0, 0, "", " LEVEL ");
                                                                        for (int i = 0; i < listlvl.size(); i++) {
                                                                                Level lvl = (Level) listlvl.get(i);
                                                                                lvl_key.add(lvl.getLevel());
                                                                                lvl_value.add(String.valueOf(lvl.getOID()));
                                                                        }
                                                                    %>
                                                                    <%=ControlCombo.draw(FrmRecrApplicationMisc.fieldNames[FrmRecrApplicationMisc.FRM_FIELD_FNL_LEVEL_ID],"formElemen",null, "" + recrApplication.getFnlLevelId(), lvl_value, lvl_key) %> 
                                                                    *
                                                                    <%=frmRecrApplicationMisc.getErrorMsg(FrmRecrApplicationMisc.FRM_FIELD_FNL_LEVEL_ID)%>
                                                                  </td>
                                                                </tr>
                                                                <tr>
                                                                  <td>Medical 
                                                                    Scheme </td>
                                                                  <td><input type="text" name="<%=FrmRecrApplicationMisc.fieldNames[FrmRecrApplicationMisc.FRM_FIELD_FNL_MEDICAL_SCHEME]%>" value="<%=recrApplication.getFnlMedicalScheme()%>" class="formElemen"></td>
                                                                  <td>Hospitalization 
                                                                    Benefits</td>
                                                                  <td><input type="text" name="<%=FrmRecrApplicationMisc.fieldNames[FrmRecrApplicationMisc.FRM_FIELD_FNL_HOSPITALIZATION]%>" value="<%=recrApplication.getFnlHospitalization()%>" class="formElemen"></td>
                                                                  <td>ASTEK Deduction</td>
                                                                  <td><input type="text" name="<%=FrmRecrApplicationMisc.fieldNames[FrmRecrApplicationMisc.FRM_FIELD_FNL_ASTEK_DEDUCTION]%>" value="<%=recrApplication.getFnlAstekDeduction()%>" class="formElemen"></td>
                                                                </tr>
                                                                <tr>
                                                                  <td>Basic Salary</td>
                                                                  <td><input type="text" name="<%=FrmRecrApplicationMisc.fieldNames[FrmRecrApplicationMisc.FRM_FIELD_FNL_BASIC_SALARY]%>" value="<%=recrApplication.getFnlBasicSalary()%>" class="formElemen"></td>
                                                                  <td>Service 
                                                                    Charge </td>
                                                                  <td><input type="text" name="<%=FrmRecrApplicationMisc.fieldNames[FrmRecrApplicationMisc.FRM_FIELD_FNL_SERVICE_CHARGE]%>" value="<%=recrApplication.getFnlServiceCharge()%>" class="formElemen"></td>
                                                                  <td>Allowance</td>
                                                                  <td><input type="text" name="<%=FrmRecrApplicationMisc.fieldNames[FrmRecrApplicationMisc.FRM_FIELD_FNL_ALLOWANCE]%>" value="<%=recrApplication.getFnlAllowance()%>" class="formElemen"></td>
                                                                </tr>
                                                                <tr>
                                                                  <td>Annual Leave</td>
                                                                  <td><input type="text" name="<%=FrmRecrApplicationMisc.fieldNames[FrmRecrApplicationMisc.FRM_FIELD_FNL_ANNUAL_LEAVE]%>" value="<%=recrApplication.getFnlAnnualLeave()%>" class="formElemen"></td>
                                                                  <td>Other Benefits</td>
                                                                  <td><input type="text" name="<%=FrmRecrApplicationMisc.fieldNames[FrmRecrApplicationMisc.FRM_FIELD_FNL_OTHER_BENEFIT]%>" value="<%=recrApplication.getFnlOtherBenefit()%>" class="formElemen"></td>
                                                                  <td>Privilege</td>
                                                                  <td><input type="text" name="<%=FrmRecrApplicationMisc.fieldNames[FrmRecrApplicationMisc.FRM_FIELD_FNL_PRIVILEGE]%>" value="<%=recrApplication.getFnlPrivilege()%>" class="formElemen"></td>
                                                                </tr>
                                                                <tr>
                                                                  <td>Commencement 
                                                                    Date </td>
                                                                  <td nowrap>
                                                                    <%//=ControlDate.drawDateWithStyle(FrmRecrApplicationMisc.fieldNames[FrmRecrApplicationMisc.FRM_FIELD_FNL_COMM_DATE], (recrApplication.getFnlCommDate()==null) ? new Date() : recrApplication.getFnlCommDate(), 1, -5, "formElemen", "")%>
                                                                    <%=ControlDate.drawDateWithStyle(FrmRecrApplicationMisc.fieldNames[FrmRecrApplicationMisc.FRM_FIELD_FNL_COMM_DATE], recrApplication.getFnlCommDate(), 1, -5, "formElemen", "")%>
                                                                    *
                                                                    <%=frmRecrApplicationMisc.getErrorMsg(FrmRecrApplicationMisc.FRM_FIELD_FNL_COMM_DATE)%>
                                                                  </td>
                                                                  <td>Probation 
                                                                    Period </td>
                                                                  <td><input type="text" name="<%=FrmRecrApplicationMisc.fieldNames[FrmRecrApplicationMisc.FRM_FIELD_FNL_PROBATION]%>" value="<%=recrApplication.getFnlProbation()%>" class="formElemen"></td>
                                                                  <td>&nbsp;</td>
                                                                  <td>&nbsp;</td>
                                                                </tr>
                                                              </table>
                                                            </td>
                                                            <td width="4%">&nbsp;</td>
                                                          </tr>
                                                        </table>
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td>&nbsp;</td>
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
