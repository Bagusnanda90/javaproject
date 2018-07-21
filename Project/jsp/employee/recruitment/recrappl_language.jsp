
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

<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_RECRUITMENT, AppObjInfo.OBJ_EMPLOYEE_RECRUITMENT_LANGGUAGE); %>
<%@ include file = "../../main/checkuser.jsp" %>

<!-- Jsp Block -->
<%!
	public String drawList(Vector objectClass ,  long recrLanguageId)
	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("60%");
                ctrlist.setListStyle("listgen");
                ctrlist.setTitleStyle("listgentitle");
                ctrlist.setCellStyle("listgensell");
                ctrlist.setHeaderStyle("listgentitle");
		//ctrlist.addHeader("Recr Application Id","20%");
		ctrlist.addHeader("Language","20%");
		ctrlist.addHeader("Spoken","20%");
		ctrlist.addHeader("Written","20%");
		ctrlist.addHeader("Reading","20%");
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;
		for (int i = 0; i < objectClass.size(); i++) {
			RecrLanguage recrLanguage = (RecrLanguage)objectClass.get(i);
			 Vector rowx = new Vector();
			 if(recrLanguageId == recrLanguage.getOID())
				 index = i;
			//rowx.add(String.valueOf(recrLanguage.getRecrApplicationId()));
			Language lang = new Language();
			if(recrLanguage.getLanguageId() != 0){
				try{
					lang = PstLanguage.fetchExc(recrLanguage.getLanguageId());
				}catch(Exception exc){
					lang = new Language();
				}
			}
			rowx.add(String.valueOf(lang.getLanguage()));
			//rowx.add(String.valueOf(recrLanguage.getLanguageId()));
			rowx.add(PstRecrLanguage.gradeKey[recrLanguage.getSpoken()]);
			rowx.add(PstRecrLanguage.gradeKey[recrLanguage.getWritten()]);
			rowx.add(PstRecrLanguage.gradeKey[recrLanguage.getReading()]);
			lstData.add(rowx);
			lstLinkData.add(String.valueOf(recrLanguage.getOID()));
		}
		return ctrlist.draw(index);
	}
%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidRecrLanguage = FRMQueryString.requestLong(request, "hidden_recr_language_id");
    long oidRecrApplication = FRMQueryString.requestLong(request, "hidden_recr_application_id");

    /*variable declaration*/
    int recordToGet = 10;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;

    String whereClause = PstRecrLanguage.fieldNames[PstRecrLanguage.FLD_RECR_APPLICATION_ID] + " = "+oidRecrApplication;
    String orderClause = "";

    CtrlRecrLanguage ctrlRecrLanguage = new CtrlRecrLanguage(request);
    ControlLine ctrLine = new ControlLine();
    Vector listRecrLanguage = new Vector(1,1);

    /*switch statement */
    iErrCode = ctrlRecrLanguage.action(iCommand , oidRecrLanguage, oidRecrApplication);
    /* end switch*/
    FrmRecrLanguage frmRecrLanguage = ctrlRecrLanguage.getForm();

    /*count list All RecrLanguage*/
    int vectSize = PstRecrLanguage.getCount(whereClause);

    RecrLanguage recrLanguage = ctrlRecrLanguage.getRecrLanguage();
    msgString =  ctrlRecrLanguage.getMessage();

    /*switch list RecrLanguage*/
    if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)&& (oidRecrLanguage == 0))
        start = PstRecrLanguage.findLimitStart(recrLanguage.getOID(),recordToGet, whereClause, orderClause);

    if((iCommand == Command.FIRST || iCommand == Command.PREV )||
      (iCommand == Command.NEXT || iCommand == Command.LAST)){
            start = ctrlRecrLanguage.actionList(iCommand, start, vectSize, recordToGet);
     } 
    /* end switch list*/

    /* get record to display */
    listRecrLanguage = PstRecrLanguage.list(start,recordToGet, whereClause , orderClause);

    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listRecrLanguage.size() < 1 && start > 0)
    {
         if (vectSize - recordToGet > recordToGet)
            start = start - recordToGet;   //go to Command.PREV
         else{
             start = 0 ;
             iCommand = Command.FIRST;
             prevCommand = Command.FIRST; //go to Command.FIRST
         }
         listRecrLanguage = PstRecrLanguage.list(start,recordToGet, whereClause , orderClause);
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
	document.frmrecrlanguage.hidden_recr_application_id.value=OID;
	document.frmrecrlanguage.command.value="<%=Command.EDIT%>";	
	document.frmrecrlanguage.action="recrapplication_edit.jsp";
	document.frmrecrlanguage.submit();
	}
function cmdBackSkill(OID){
                document.frmrecrlanguage.hidden_recr_application_id.value=OID;
                document.frmrecrlanguage.command.value="<%=Command.EDIT%>";	
                document.frmrecrlanguage.action="recrappl_skill.jsp";
                document.frmrecrlanguage.submit();
                }
function cmdAdd(){
	document.frmrecrlanguage.hidden_recr_language_id.value="0";
	document.frmrecrlanguage.command.value="<%=Command.ADD%>";
	document.frmrecrlanguage.prev_command.value="<%=prevCommand%>";
	document.frmrecrlanguage.action="recrappl_language.jsp";
	document.frmrecrlanguage.submit();
}

function cmdAsk(oidRecrLanguage){
	document.frmrecrlanguage.hidden_recr_language_id.value=oidRecrLanguage;
	document.frmrecrlanguage.command.value="<%=Command.ASK%>";
	document.frmrecrlanguage.prev_command.value="<%=prevCommand%>";
	document.frmrecrlanguage.action="recrappl_language.jsp";
	document.frmrecrlanguage.submit();
}

function cmdConfirmDelete(oidRecrLanguage){
	document.frmrecrlanguage.hidden_recr_language_id.value=oidRecrLanguage;
	document.frmrecrlanguage.command.value="<%=Command.DELETE%>";
	document.frmrecrlanguage.prev_command.value="<%=prevCommand%>";
	document.frmrecrlanguage.action="recrappl_language.jsp";
	document.frmrecrlanguage.submit();
}
function cmdSave(){
	document.frmrecrlanguage.command.value="<%=Command.SAVE%>";
	document.frmrecrlanguage.prev_command.value="<%=prevCommand%>";
	document.frmrecrlanguage.action="recrappl_language.jsp";
	document.frmrecrlanguage.submit();
	}

function cmdEdit(oidRecrLanguage){
	document.frmrecrlanguage.hidden_recr_language_id.value=oidRecrLanguage;
	document.frmrecrlanguage.command.value="<%=Command.EDIT%>";
	document.frmrecrlanguage.prev_command.value="<%=prevCommand%>";
	document.frmrecrlanguage.action="recrappl_language.jsp";
	document.frmrecrlanguage.submit();
	}

function cmdCancel(oidRecrLanguage){
	document.frmrecrlanguage.hidden_recr_language_id.value=oidRecrLanguage;
	document.frmrecrlanguage.command.value="<%=Command.EDIT%>";
	document.frmrecrlanguage.prev_command.value="<%=prevCommand%>";
	document.frmrecrlanguage.action="recrappl_language.jsp";
	document.frmrecrlanguage.submit();
}

function cmdBack(){
	document.frmrecrlanguage.command.value="<%=Command.BACK%>";
	document.frmrecrlanguage.action="recrappl_language.jsp";
	document.frmrecrlanguage.submit();
	}

function cmdListFirst(){
	document.frmrecrlanguage.command.value="<%=Command.FIRST%>";
	document.frmrecrlanguage.prev_command.value="<%=Command.FIRST%>";
	document.frmrecrlanguage.action="recrappl_language.jsp";
	document.frmrecrlanguage.submit();
}
function cmdBackFamilly(OID){
                document.frmrecrlanguage.hidden_recr_application_id.value=OID;
                document.frmrecrlanguage.command.value="<%=Command.EDIT%>";	
                document.frmrecrlanguage.action="recrappl_familly.jsp";
                document.frmrecrlanguage.submit();
function cmdListPrev(){
	document.frmrecrlanguage.command.value="<%=Command.PREV%>";
	document.frmrecrlanguage.prev_command.value="<%=Command.PREV%>";
	document.frmrecrlanguage.action="recrappl_language.jsp";
	document.frmrecrlanguage.submit();
	}

function cmdListNext(){
	document.frmrecrlanguage.command.value="<%=Command.NEXT%>";
	document.frmrecrlanguage.prev_command.value="<%=Command.NEXT%>";
	document.frmrecrlanguage.action="recrappl_language.jsp";
	document.frmrecrlanguage.submit();
}

function cmdListLast(){
	document.frmrecrlanguage.command.value="<%=Command.LAST%>";
	document.frmrecrlanguage.prev_command.value="<%=Command.LAST%>";
	document.frmrecrlanguage.action="recrappl_language.jsp";
	document.frmrecrlanguage.submit();
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
                            <form name="frmrecrlanguage" method="post" action="">
                              <input type="hidden" name="command" value="">
                              <input type="hidden" name="start" value="<%=start%>">
                              <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                              <input type="hidden" name="hidden_recr_application_id" value="<%=oidRecrApplication%>">
                              <input type="hidden" name="vectSize" value="<%=vectSize%>">
                              <input type="hidden" name="hidden_recr_language_id" value="<%=oidRecrLanguage%>">
                              <input type="hidden" name="<%=frmRecrLanguage.fieldNames[FrmRecrLanguage.FRM_FIELD_RECR_APPLICATION_ID] %>" value="<%=oidRecrApplication%>">
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
                                                            <td height="14" valign="middle" colspan="3" class="comment">Language 
                                                              Proficiency List 
                                                            </td>
                                                          </tr>
                                                          <%
							try{
								if (listRecrLanguage.size()>0){
							%>
                                                          <tr align="left" valign="top"> 
                                                            <td height="22" valign="middle" colspan="3"> 
                                                              <%= drawList(listRecrLanguage,oidRecrLanguage)%> 
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
                                                                if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidRecrLanguage == 0))
                                                                        cmd = PstRecrLanguage.findLimitCommand(start,recordToGet,vectSize);
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
                                                    <% if(iCommand == Command.NONE || (iCommand == Command.SAVE && frmRecrLanguage.errorSize()<1)|| iCommand == Command.DELETE || iCommand == Command.BACK || iCommand == Command.FIRST || iCommand == Command.PREV || iCommand == Command.NEXT || iCommand == Command.LAST ){%>
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
                                                              New Language</a> </td>
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
                                            <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmRecrLanguage.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                                                        <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                          <tr align="left" valign="top">
                                                            <td height="21" valign="middle" width="5%">&nbsp;</td>
                                                            <td height="21" valign="middle" width="7%">&nbsp;</td>
                                                            <td height="21" colspan="2" width="88%" class="comment">*)= 
                                                              required</td>
                                                          </tr>
                                                          <%-- <tr align="left" valign="top"> 
                                                <td height="21" valign="top" width="17%">Recr 
                                                  Application Id</td>
                                                <td height="21" colspan="2" width="83%"> 
                                                  <input type="text" name="<%=frmRecrLanguage.fieldNames[FrmRecrLanguage.FRM_FIELD_RECR_APPLICATION_ID] %>"  value="<%= recrLanguage.getRecrApplicationId() %>" class="formElemen">
                                                  * <%= frmRecrLanguage.getErrorMsg(FrmRecrLanguage.FRM_FIELD_RECR_APPLICATION_ID) %> 
                                                </td>
                                              </tr> --%>
                                                          <tr align="left" valign="top">
                                                            <td height="21" valign="top" width="5%">&nbsp;</td>
                                                            <td height="21" valign="top" width="7%">Language</td>
                                                            <td height="21" colspan="2" width="88%"> 
                                                              <%--
                                                  <input type="text" name="<%=frmRecrLanguage.fieldNames[FrmRecrLanguage.FRM_FIELD_LANGUAGE_ID] %>"  value="<%= recrLanguage.getLanguageId() %>" class="formElemen">
                                                  * <%= frmRecrLanguage.getErrorMsg(FrmRecrLanguage.FRM_FIELD_LANGUAGE_ID) %> 
                                                  --%>
                                                              <%
                                                        Vector language_value = new Vector(1,1);
                                                        Vector language_key = new Vector(1,1);
                                                        Vector listLanguage = PstLanguage.listAll();
                                                        for(int i=0;i<listLanguage.size();i++){
                                                            Language language = (Language)listLanguage.get(i);
                                                            language_value.add(""+language.getOID());
                                                            language_key.add(""+language.getLanguage());
                                                        }
                                                      %>
                                                              <% if((listLanguage != null) && (listLanguage.size()>0)){%>
                                                              <%= ControlCombo.draw(frmRecrLanguage.fieldNames[FrmRecrLanguage.FRM_FIELD_LANGUAGE_ID],"formElemen",null, ""+recrLanguage.getLanguageId(), language_value, language_key) %> 
                                                              <% }else {%>
                                                              <font class="comment">No 
                                                              Language available</font> 
                                                              <% }%>
                                                              * <%= frmRecrLanguage.getErrorMsg(FrmRecrLanguage.FRM_FIELD_LANGUAGE_ID) %> 
                                                            </td>
                                                          </tr>
                                                          <tr align="left" valign="top">
                                                            <td height="21" valign="top" width="5%">&nbsp;</td>
                                                            <td height="21" valign="top" width="7%">Spoken</td>
                                                            <td height="21" colspan="2" width="88%"> 
                                                              <%-- <input type="text" name="<%=frmRecrLanguage.fieldNames[FrmRecrLanguage.FRM_FIELD_SPOKEN] %>"  value="<%= recrLanguage.getSpoken() %>" class="formElemen"> --%>
                                                              <%= ControlCombo.draw(frmRecrLanguage.fieldNames[FrmRecrLanguage.FRM_FIELD_SPOKEN],"formElemen", null, ""+recrLanguage.getSpoken(), PstRecrLanguage.getGradeValue(), PstRecrLanguage.getGradeKey()) %> 
                                                            </td>
                                                          </tr>
                                                          <tr align="left" valign="top">
                                                            <td height="21" valign="top" width="5%">&nbsp;</td>
                                                            <td height="21" valign="top" width="7%">Written</td>
                                                            <td height="21" colspan="2" width="88%"> 
                                                              <%-- <input type="text" name="<%=frmRecrLanguage.fieldNames[FrmRecrLanguage.FRM_FIELD_WRITTEN] %>"  value="<%= recrLanguage.getWritten() %>" class="formElemen"> --%>
                                                              <%= ControlCombo.draw(frmRecrLanguage.fieldNames[FrmRecrLanguage.FRM_FIELD_WRITTEN],"formElemen", null, ""+recrLanguage.getWritten(), PstRecrLanguage.getGradeValue(), PstRecrLanguage.getGradeKey()) %> 
                                                            </td>
                                                          </tr>
                                                          <tr align="left" valign="top">
                                                            <td height="21" valign="top" width="5%">&nbsp;</td>
                                                            <td height="21" valign="top" width="7%">Reading</td>
                                                            <td height="21" colspan="2" width="88%"> 
                                                              <%-- <input type="text" name="<%=frmRecrLanguage.fieldNames[FrmRecrLanguage.FRM_FIELD_READING] %>"  value="<%= recrLanguage.getReading() %>" class="formElemen"> --%>
                                                              <%= ControlCombo.draw(frmRecrLanguage.fieldNames[FrmRecrLanguage.FRM_FIELD_READING],"formElemen", null, ""+recrLanguage.getReading(), PstRecrLanguage.getGradeValue(), PstRecrLanguage.getGradeKey()) %> 
                                                            </td>
                                                          </tr>
                                                          <tr align="left" valign="top">
                                                            <td height="8" valign="middle" width="5%">&nbsp;</td>
                                                            <td height="8" valign="middle" width="7%">&nbsp;</td>
                                                            <td height="8" colspan="2" width="88%">&nbsp; 
                                                            </td>
                                                          </tr>
                                                          <tr align="left" valign="top" > 
                                                            <td colspan="4" class="command"> 
                                                              <%
                                                        ctrLine.setLocationImg(approot+"/images");
                                                        ctrLine.initDefault();
                                                        ctrLine.setTableWidth("80");
                                                        String scomDel = "javascript:cmdAsk('"+oidRecrLanguage+"')";
                                                        String sconDelCom = "javascript:cmdConfirmDelete('"+oidRecrLanguage+"')";
                                                        String scancel = "javascript:cmdEdit('"+oidRecrLanguage+"')";
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
