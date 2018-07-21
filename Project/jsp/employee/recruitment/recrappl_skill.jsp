
<%@page import="com.dimata.harisma.form.recruitment.CtrlRecrApplication"%>
<% 
/* 
 * Page Name  		:  recrappl_skill.jsp
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
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_RECRUITMENT, AppObjInfo.OBJ_EMPLOYEE_RECRUITMENT ); %>
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
<%
	CtrlRecrApplication ctrlRecrApplication = new CtrlRecrApplication(request);
	long oidRecrApplication = FRMQueryString.requestLong(request, "hidden_recr_application_id");
	int prevCommand = FRMQueryString.requestInt(request, "prev_command");

	int iErrCode = FRMMessage.ERR_NONE;
	String errMsg = "";
	String whereClause = "";
	String orderClause = "";
	int iCommand = FRMQueryString.requestCommand(request);
	int start = FRMQueryString.requestInt(request,"start");
         int from = 2 ;
	//out.println("iCommand : "+iCommand);
	ControlLine ctrLine = new ControlLine();
	iErrCode = ctrlRecrApplication.action(iCommand , oidRecrApplication, from);

	errMsg = ctrlRecrApplication.getMessage();
	FrmRecrApplication frmRecrApplication = ctrlRecrApplication.getForm();
	RecrApplication recrApplication = ctrlRecrApplication.getRecrApplication();
	oidRecrApplication = recrApplication.getOID();
        //recrApplication = PstRecrApplication.fetchExc(oidRecrApplication);
        
        if(iCommand==Command.DELETE){
	%>
        <jsp:forward page="recrapplication_list.jsp"> 
        <jsp:param name="prev_command" value="<%=prevCommand%>" />
        <jsp:param name="start" value="<%=start%>" />
        <jsp:param name="hidden_recr_application_id" value="<%=recrApplication.getOID()%>" />
        </jsp:forward>
        <%
	}

        if((iCommand==Command.SAVE) && (iErrCode == 0)){
            iCommand = Command.EDIT;
        }
%>
<!-- End of Jsp Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Recruitment</title>
<script language="JavaScript">

	function cmdCancel(){
		document.frm_recrapplication.command.value="<%=Command.ADD%>";
		document.frm_recrapplication.action="recrappl_skill.jsp";
		document.frm_recrapplication.submit();
	} 
	function cmdCancel(){
		document.frm_recrapplication.command.value="<%=Command.CANCEL%>";
		document.frm_recrapplication.action="recrappl_skill.jsp";
		document.frm_recrapplication.submit();
	} 

	function cmdEdit(oid){ 
		document.frm_recrapplication.command.value="<%=Command.EDIT%>";
		document.frm_recrapplication.action="recrappl_skill.jsp";
		document.frm_recrapplication.submit(); 
	} 
function cmdBackSkill(OID){
                document.frm_recrapplication.hidden_recr_application_id.value=OID;
                document.frm_recrapplication.command.value="<%=Command.EDIT%>";	
                document.frm_recrapplication.action="recrappl_skill.jsp";
                document.frm_recrapplication.submit();
                }
        function cmdBackEmp(OID){
                document.frm_recrapplication.hidden_recr_application_id.value=OID;
                document.frm_recrapplication.command.value="<%=Command.EDIT%>";	
                document.frm_recrapplication.action="recrapplication_edit.jsp";
                document.frm_recrapplication.submit();
	}

	function cmdSave(){
		document.frm_recrapplication.command.value="<%=Command.SAVE%>"; 
		document.frm_recrapplication.action="recrappl_skill.jsp";
		document.frm_recrapplication.submit();
	}
        function cmdBackFamilly(OID){
                document.frm_recrapplication.hidden_recr_application_id.value=OID;
                document.frm_recrapplication.command.value="<%=Command.EDIT%>";	
                document.frm_recrapplication.action="recrappl_familly.jsp";
                document.frm_recrapplication.submit();
                }
	function cmdAsk(oid){
		document.frm_recrapplication.command.value="<%=Command.ASK%>"; 
		document.frm_recrapplication.action="recrappl_skill.jsp";
		document.frm_recrapplication.submit();
	} 

	function cmdConfirmDelete(oid){
		document.frm_recrapplication.command.value="<%=Command.DELETE%>";
		document.frm_recrapplication.action="recrappl_skill.jsp"; 
		document.frm_recrapplication.submit();
	}  

	function cmdBack(){
		document.frm_recrapplication.command.value="<%=Command.FIRST%>"; 
		document.frm_recrapplication.action="recrapplication_list.jsp";
		document.frm_recrapplication.submit();
	}

//-------------- script form image -------------------

	function cmdDelPic(oid){
		document.frm_recrapplication.command.value="<%=Command.POST%>"; 
		document.frm_recrapplication.action="recrappl_skill.jsp";
		document.frm_recrapplication.submit();
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
                            <form name="frm_recrapplication" method="post" action="">
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
                                    <td class="tablecolor"  style="background-color:<%=bgColorContent%>;"> 
                                      <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                        <tr> 
                                          <td valign="top"> 
                                            <table style="border:1px solid <%=garisContent%>"  width="100%" height="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                              <tr> 
                                                <td valign="top" width="50%"> 

                                                  <table width="100%" cellspacing="2" cellpadding="2" >
                                                    <tr> 
                                                      <td colspan="3"> 
                                                        <div align="center"><b><font size="2">EMPLOYMENT 
                                                          APPLICATION</font></b></div>
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="1%">&nbsp;</td>
                                                      <td colspan="2" class="txtheading1">*) 
                                                        entry required</td>
                                                    </tr>
                                                    <input type="hidden" name="<%=FrmRecrApplication.fieldNames[FrmRecrApplication.FRM_FIELD_FULL_NAME]%>" value="<%=recrApplication.getFullName()%>" class="formElemen" size="60">
                                                    <input type="hidden" name="<%=FrmRecrApplication.fieldNames[FrmRecrApplication.FRM_FIELD_POSITION]%>" value="<%=recrApplication.getPosition()%>" class="formElemen" size="60">
                                                    <input type="hidden" name="<%=FrmRecrApplication.fieldNames[FrmRecrApplication.FRM_FIELD_OTHER_POSITION]%>" value="<%=recrApplication.getOtherPosition()%>" class="formElemen" size="60">
                                                    <input type="hidden" name="<%=FrmRecrApplication.fieldNames[FrmRecrApplication.FRM_FIELD_RELIGION_ID]%>" value="<%=recrApplication.getReligionId()%>" class="formElemen" size="60">
                                                    <input type="hidden" name="<%=FrmRecrApplication.fieldNames[FrmRecrApplication.FRM_FIELD_BIRTH_DATE]%>" value="<%=recrApplication.getBirthDate()%>" class="formElemen" size="60">
                                                    
                                                           
                                                    <tr align="left"> 
                                                      <td width="1%"  valign="top"  >&nbsp;</td>
                                                      <td width="18%"  valign="top"  ><b>SKILL</b> </td>
                                                      <td  width="81%"  valign="top">&nbsp;</td>
                                                    </tr>
                                                    <tr align="left"> 
                                                      <td width="1%"  valign="top"  >&nbsp;</td>
                                                      <td colspan="2"  valign="top"  > 
                                                        <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                          <tr> 
                                                            <td width="12%" nowrap>SKILL</td>
                                                            <td width="20%"> 
                                                              <input type="text" name="<%=FrmRecrApplication.fieldNames[FrmRecrApplication.FRM_FIELD_SKILL]%>" value="<%=recrApplication.getSkill()%>" class="formElemen" size="5">
                                                              <%=frmRecrApplication.getErrorMsg(FrmRecrApplication.FRM_FIELD_SKILL)%></td>
                                                          </tr>
                                                          
                                                          
                                                          
                                                        </table>
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td colspan="3">&nbsp;</td>
                                                    </tr>
                                                    <tr align="left"> 
                                                      <td colspan="3"> 
                                                        <%
                                            ctrLine.setLocationImg(approot+"/images");
                                            ctrLine.initDefault();
                                            ctrLine.setTableWidth("80");
                                            String scomDel = "javascript:cmdAsk('"+oidRecrApplication+"')";
                                            String sconDelCom = "javascript:cmdConfirmDelete('"+oidRecrApplication+"')";
                                            String scancel = "javascript:cmdEdit('"+oidRecrApplication+"')";
                                            ctrLine.setBackCaption("Back to List");
                                            ctrLine.setDeleteCaption("Delete");
                                            ctrLine.setSaveCaption("Save");
                                            ctrLine.setAddCaption("");
                                            ctrLine.setCommandStyle("buttonlink");

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
                                                        <%= ctrLine.drawImage(iCommand, iErrCode, errMsg)%> 
                                                      </td>
                                                    </tr>
                                                    <tr align="left">
                                                      <td colspan="3">&nbsp;</td>
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
