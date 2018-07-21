
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

<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_RECRUITMENT, AppObjInfo.OBJ_EMPLOYEE_RECRUITMENT_GENERAL); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidRecrApplication = FRMQueryString.requestLong(request, "hidden_recr_application_id");

    CtrlRecrAnswerGeneral ctrlRecrAnswerGeneral = new CtrlRecrAnswerGeneral(request);
    FrmRecrAnswerGeneral frmRecrAnswerGeneral = ctrlRecrAnswerGeneral.getForm();

    CtrlRecrAnswerIllness ctrlRecrAnswerIllness = new CtrlRecrAnswerIllness(request);
    FrmRecrAnswerIllness frmRecrAnswerIllness = ctrlRecrAnswerIllness.getForm();

    Vector listGeneralQuestions = PstRecrGeneral.listAll();
    Vector listIllnessQuestions = PstRecrIllness.listAll();

    if (iCommand == Command.SAVE) {
        //--- bagian pemroses General Question ---
        RecrAnswerGeneral recrAnswerGeneral = new RecrAnswerGeneral();

        String[] arrGeneralQuestionId = null;
        String[] arrGeneralAnswer = null;
        long generalQuestionId = 0;
        String generalAnswer = "";

        try {
            arrGeneralQuestionId = request.getParameterValues(FrmRecrAnswerGeneral.fieldNames[FrmRecrAnswerGeneral.FRM_FIELD_RECR_GENERAL_ID]);
            arrGeneralAnswer = request.getParameterValues(FrmRecrAnswerGeneral.fieldNames[FrmRecrAnswerGeneral.FRM_FIELD_ANSWER]);
        }
        catch (Exception e) {}

        if ((arrGeneralQuestionId != null) && (arrGeneralQuestionId.length > 0)) {
            for (int i = 0; i < arrGeneralQuestionId.length; i++) {
                if ((arrGeneralQuestionId[i].length() > 0)) {
                    try {
                       generalQuestionId = Long.parseLong(arrGeneralQuestionId[i]);
                    } catch (Exception e) {}
                }
                else { generalQuestionId = 0; }
                if ((arrGeneralAnswer[i].length() > 0)) {
                    try {
                       generalAnswer = arrGeneralAnswer[i];
                    } catch (Exception e) {}
                }
                else { generalAnswer = ""; }
                //System.out.println("generalQuestionId : "+generalQuestionId+" - generalAnswer : "+generalAnswer);

                String where  = PstRecrAnswerGeneral.fieldNames[PstRecrAnswerGeneral.FLD_RECR_GENERAL_ID] + "=" + generalQuestionId;
                       where += " AND " + PstRecrAnswerGeneral.fieldNames[PstRecrAnswerGeneral.FLD_RECR_APPLICATION_ID] + "=" + oidRecrApplication;
                Vector vansgen = PstRecrAnswerGeneral.list(0, 0, where, "");

                if (vansgen.size() > 0) {
                    recrAnswerGeneral = (RecrAnswerGeneral) vansgen.get(0);
                    recrAnswerGeneral.setRecrGeneralId(generalQuestionId);
                    recrAnswerGeneral.setRecrApplicationId(oidRecrApplication);
                    recrAnswerGeneral.setAnswer(generalAnswer);
                    PstRecrAnswerGeneral.updateExc(recrAnswerGeneral);
                }
                else {
                    recrAnswerGeneral.setRecrGeneralId(generalQuestionId);
                    recrAnswerGeneral.setRecrApplicationId(oidRecrApplication);
                    recrAnswerGeneral.setAnswer(generalAnswer);
                    PstRecrAnswerGeneral.insertExc(recrAnswerGeneral);
                }
            }
        }

        //--- bagian pemroses Illness Question ---
        RecrAnswerIllness recrAnswerIllness = new RecrAnswerIllness();
        if (listIllnessQuestions.size() > 0) {
            for (int i=0; i<listIllnessQuestions.size(); i++) {
                RecrIllness ri = (RecrIllness) listIllnessQuestions.get(i);
                int valueIllness = FRMQueryString.requestInt(request, String.valueOf(ri.getOID()));
                System.out.println("ri.getOID = "+ri.getOID()+" - valueIllness = "+valueIllness);
                String where  = PstRecrAnswerIllness.fieldNames[PstRecrAnswerIllness.FLD_RECR_ILLNESS_ID] + "=" + ri.getOID();
                       where += " AND " + PstRecrAnswerIllness.fieldNames[PstRecrAnswerIllness.FLD_RECR_APPLICATION_ID] + "=" + oidRecrApplication;
                Vector vansill = PstRecrAnswerIllness.list(0, 0, where, "");
                if (vansill.size() > 0) {
                    recrAnswerIllness = (RecrAnswerIllness) vansill.get(0);
                    recrAnswerIllness.setRecrIllnessId(ri.getOID());
                    recrAnswerIllness.setRecrApplicationId(oidRecrApplication);
                    recrAnswerIllness.setAnswer(valueIllness);
                    PstRecrAnswerIllness.updateExc(recrAnswerIllness);
                }
                else {
                    recrAnswerIllness.setRecrIllnessId(ri.getOID());
                    recrAnswerIllness.setRecrApplicationId(oidRecrApplication);
                    recrAnswerIllness.setAnswer(valueIllness);
                    PstRecrAnswerIllness.insertExc(recrAnswerIllness);
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
	document.frmrecrreferences.hidden_recr_application_id.value=OID;
	document.frmrecrreferences.command.value="<%=Command.EDIT%>";	
	document.frmrecrreferences.action="recrapplication_edit.jsp";
	document.frmrecrreferences.submit();
	}
function cmdBackFamilly(OID){
                document.frmrecrreferences.hidden_recr_application_id.value=OID;
                document.frmrecrreferences.command.value="<%=Command.EDIT%>";	
                document.frmrecrreferences.action="recrappl_familly.jsp";
                document.frmrecrreferences.submit();
                }
function cmdAdd(){
	document.frmrecrreferences.hidden_recr_references_id.value="0";
	document.frmrecrreferences.command.value="<%=Command.ADD%>";
	document.frmrecrreferences.prev_command.value="<%=prevCommand%>";
	document.frmrecrreferences.action="recrappl_references.jsp";
	document.frmrecrreferences.submit();
}
function cmdBackSkill(OID){
                document.frmrecrreferences.hidden_recr_application_id.value=OID;
                document.frmrecrreferences.command.value="<%=Command.EDIT%>";	
                document.frmrecrreferences.action="recrappl_skill.jsp";
                document.frmrecrreferences.submit();
                }
function cmdAsk(oidRecrReferences){
	document.frmrecrreferences.hidden_recr_references_id.value=oidRecrReferences;
	document.frmrecrreferences.command.value="<%=Command.ASK%>";
	document.frmrecrreferences.prev_command.value="<%=prevCommand%>";
	document.frmrecrreferences.action="recrappl_references.jsp";
	document.frmrecrreferences.submit();
}

function cmdConfirmDelete(oidRecrReferences){
	document.frmrecrreferences.hidden_recr_references_id.value=oidRecrReferences;
	document.frmrecrreferences.command.value="<%=Command.DELETE%>";
	document.frmrecrreferences.prev_command.value="<%=prevCommand%>";
	document.frmrecrreferences.action="recrappl_references.jsp";
	document.frmrecrreferences.submit();
}
function cmdSave(){
	document.frmrecrreferences.command.value="<%=Command.SAVE%>";
	document.frmrecrreferences.prev_command.value="<%=prevCommand%>";
	document.frmrecrreferences.action="recrappl_general.jsp";
	document.frmrecrreferences.submit();
	}

function cmdEdit(oidRecrReferences){
	document.frmrecrreferences.hidden_recr_references_id.value=oidRecrReferences;
	document.frmrecrreferences.command.value="<%=Command.EDIT%>";
	document.frmrecrreferences.prev_command.value="<%=prevCommand%>";
	document.frmrecrreferences.action="recrappl_references.jsp";
	document.frmrecrreferences.submit();
	}

function cmdCancel(oidRecrReferences){
	document.frmrecrreferences.hidden_recr_references_id.value=oidRecrReferences;
	document.frmrecrreferences.command.value="<%=Command.EDIT%>";
	document.frmrecrreferences.prev_command.value="<%=prevCommand%>";
	document.frmrecrreferences.action="recrappl_references.jsp";
	document.frmrecrreferences.submit();
}

function cmdBack(){
	document.frmrecrreferences.command.value="<%=Command.BACK%>";
	document.frmrecrreferences.action="recrappl_references.jsp";
	document.frmrecrreferences.submit();
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
                            <form name="frmrecrreferences" method="post" action="">
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
                                                    <tr> 
                                                      <td>&nbsp; </td>
                                                    </tr>
                                                    <tr>
                                                      <td>
                                                        <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                          <tr> 
                                                            <td width="1%">&nbsp;</td>
                                                            <td width="99%"><b>GENERAL</b></td>
                                                          </tr>
                                                          <tr> 
                                                            <td width="1%">&nbsp;</td>
                                                            <td width="99%"> 
                                                              <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                <%
                                                                for (int i=0; i<listGeneralQuestions.size(); i++) {
                                                                    RecrAnswerGeneral rag = new RecrAnswerGeneral();
                                                                    RecrGeneral rg = (RecrGeneral) listGeneralQuestions.get(i);
                                                                    String where  = PstRecrAnswerGeneral.fieldNames[PstRecrAnswerGeneral.FLD_RECR_GENERAL_ID] + "=" + rg.getOID();
                                                                           where += " AND " + PstRecrAnswerGeneral.fieldNames[PstRecrAnswerGeneral.FLD_RECR_APPLICATION_ID] + "=" + oidRecrApplication;
                                                                    Vector vansgen2 = PstRecrAnswerGeneral.list(0, 0, where, "");
                                                                    if (vansgen2.size() > 0) {
                                                                        rag = (RecrAnswerGeneral) vansgen2.get(0);
                                                                    }
                                                                    //System.out.println(rag.getAnswer());
                                                                %>
                                                                <tr> 
                                                                  <td> 
                                                                    <%=rg.getQuestion()%><br>
                                                                    <input type="hidden" name="<%=frmRecrAnswerGeneral.fieldNames[FrmRecrAnswerGeneral.FRM_FIELD_RECR_GENERAL_ID]%>" value="<%=rg.getOID()%>">
                                                                    <input type="text" name="<%=frmRecrAnswerGeneral.fieldNames[FrmRecrAnswerGeneral.FRM_FIELD_ANSWER]%>" value="<%=rag.getAnswer()%>" size="80">
                                                                  </td>
                                                                </tr>
                                                                <%
                                                                }
                                                                %>
                                                              </table>
                                                            </td>
                                                          </tr>
                                                          <tr> 
                                                            <td width="1%">&nbsp;</td>
                                                            <td width="99%">
                                                              <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                <tr>
                                                                  <td>Do you or 
                                                                    did you suffer 
                                                                    from the following 
                                                                    illness ?</td>
                                                                </tr>
                                                                <tr>
                                                                  <td>
                                                                    <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                    <%
                                                                    for (int j=0; j<listIllnessQuestions.size(); j++) {
                                                                        RecrAnswerIllness rai = new RecrAnswerIllness();
                                                                        RecrIllness ri = (RecrIllness) listIllnessQuestions.get(j);
                                                                        String where  = PstRecrAnswerIllness.fieldNames[PstRecrAnswerIllness.FLD_RECR_ILLNESS_ID] + "=" + ri.getOID();
                                                                               where += " AND " + PstRecrAnswerIllness.fieldNames[PstRecrAnswerIllness.FLD_RECR_APPLICATION_ID] + "=" + oidRecrApplication;
                                                                        System.out.println(where);
                                                                        Vector vansill2 = PstRecrAnswerIllness.list(0, 0, where, "");
                                                                        if (vansill2.size() > 0) {
                                                                            rai = (RecrAnswerIllness) vansill2.get(0);
                                                                        }
                                                                        System.out.println(rai.getAnswer());
                                                                    %>
                                                                      <tr>
                                                                        <td width="1%">&nbsp;&nbsp;</td>
                                                                        <td width="12%" nowrap><%=ri.getIllness()%></td>
                                                                        <td width="87%">
                                                                          <input type="hidden" name="<%=frmRecrAnswerIllness.fieldNames[FrmRecrAnswerIllness.FRM_FIELD_RECR_ILLNESS_ID]%>" value="<%=ri.getOID()%>">
                                                                          <% 
                                                                            for(int k=0;k<PstRecrAnswerIllness.illnessValue.length;k++){
                                                                            String str = "";
                                                                            if(rai.getAnswer()==PstRecrAnswerIllness.illnessValue[k]){str = "checked";}
                                                                          %>
                                                                          <input type="radio" name="<%=ri.getOID()%>" value="<%=PstRecrAnswerIllness.illnessValue[k]%>" <%=str%> style="border:'none'">
                                                                          <%=PstRecrAnswerIllness.illnessKey[k]%> 
                                                                          <% }%>
                                                                          <%=frmRecrAnswerIllness.getErrorMsg(FrmRecrAnswerIllness.FRM_FIELD_ANSWER)%>
                                                                        </td>
                                                                      </tr>
                                                                    <%
                                                                    }
                                                                    %>
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
