
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
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_RECRUITMENT, AppObjInfo.OBJ_EMPLOYEE_RECRUITMENT_REFERENCES); %>
<%@ include file = "../../main/checkuser.jsp" %>

<!-- Jsp Block -->
<%!
	public String drawList(Vector objectClass ,  long recrReferencesId)
	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
                ctrlist.setListStyle("listgen");
                ctrlist.setTitleStyle("listgentitle");
                ctrlist.setCellStyle("listgensell");
                ctrlist.setHeaderStyle("listgentitle");
		//ctrlist.addHeader("Recr Application Id","25%");
		ctrlist.addHeader("Name","25%");
		ctrlist.addHeader("Company","25%");
		ctrlist.addHeader("Position","25%");
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;
		for (int i = 0; i < objectClass.size(); i++) {
			RecrReferences recrReferences = (RecrReferences)objectClass.get(i);
			 Vector rowx = new Vector();
			 if(recrReferencesId == recrReferences.getOID())
				 index = i;
			//rowx.add(String.valueOf(recrReferences.getRecrApplicationId()));
			rowx.add(recrReferences.getName());
			rowx.add(recrReferences.getCompany());
			rowx.add(recrReferences.getPosition());
			lstData.add(rowx);
			lstLinkData.add(String.valueOf(recrReferences.getOID()));
		}
		return ctrlist.draw(index);
	}
%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidRecrReferences = FRMQueryString.requestLong(request, "hidden_recr_references_id");
    long oidRecrApplication = FRMQueryString.requestLong(request, "hidden_recr_application_id");

    /*variable declaration*/
    int recordToGet = 10;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;

    String whereClause = PstRecrReferences.fieldNames[PstRecrReferences.FLD_RECR_APPLICATION_ID] + " = "+oidRecrApplication;
    String orderClause = "";

    CtrlRecrReferences ctrlRecrReferences = new CtrlRecrReferences(request);
    ControlLine ctrLine = new ControlLine();
    Vector listRecrReferences = new Vector(1,1);

    /*switch statement */
    iErrCode = ctrlRecrReferences.action(iCommand , oidRecrReferences, oidRecrApplication);
    /* end switch*/
    FrmRecrReferences frmRecrReferences = ctrlRecrReferences.getForm();

    /*count list All RecrReferences*/
    int vectSize = PstRecrReferences.getCount(whereClause);

    RecrReferences recrReferences = ctrlRecrReferences.getRecrReferences();
    msgString =  ctrlRecrReferences.getMessage();

    /*switch list RecrReferences*/
    if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)&& (oidRecrReferences == 0))
            start = PstRecrReferences.findLimitStart(recrReferences.getOID(),recordToGet, whereClause, orderClause);

    if((iCommand == Command.FIRST || iCommand == Command.PREV )||
      (iCommand == Command.NEXT || iCommand == Command.LAST)){
            start = ctrlRecrReferences.actionList(iCommand, start, vectSize, recordToGet);
     } 
    /* end switch list*/

    /* get record to display */
    listRecrReferences = PstRecrReferences.list(start,recordToGet, whereClause , orderClause);

    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listRecrReferences.size() < 1 && start > 0)
    {
         if (vectSize - recordToGet > recordToGet)
            start = start - recordToGet;   //go to Command.PREV
         else{
             start = 0 ;
             iCommand = Command.FIRST;
             prevCommand = Command.FIRST; //go to Command.FIRST
         }
         listRecrReferences = PstRecrReferences.list(start,recordToGet, whereClause , orderClause);
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

function cmdAdd(){
	document.frmrecrreferences.hidden_recr_references_id.value="0";
	document.frmrecrreferences.command.value="<%=Command.ADD%>";
	document.frmrecrreferences.prev_command.value="<%=prevCommand%>";
	document.frmrecrreferences.action="recrappl_references.jsp";
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
	document.frmrecrreferences.action="recrappl_references.jsp";
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

function cmdListFirst(){
	document.frmrecrreferences.command.value="<%=Command.FIRST%>";
	document.frmrecrreferences.prev_command.value="<%=Command.FIRST%>";
	document.frmrecrreferences.action="recrappl_references.jsp";
	document.frmrecrreferences.submit();
}

function cmdListPrev(){
	document.frmrecrreferences.command.value="<%=Command.PREV%>";
	document.frmrecrreferences.prev_command.value="<%=Command.PREV%>";
	document.frmrecrreferences.action="recrappl_references.jsp";
	document.frmrecrreferences.submit();
	}

function cmdListNext(){
	document.frmrecrreferences.command.value="<%=Command.NEXT%>";
	document.frmrecrreferences.prev_command.value="<%=Command.NEXT%>";
	document.frmrecrreferences.action="recrappl_references.jsp";
	document.frmrecrreferences.submit();
}

function cmdListLast(){
	document.frmrecrreferences.command.value="<%=Command.LAST%>";
	document.frmrecrreferences.prev_command.value="<%=Command.LAST%>";
	document.frmrecrreferences.action="recrappl_references.jsp";
	document.frmrecrreferences.submit();
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
                            <form name="frmrecrreferences" method="post" action="">
                              <input type="hidden" name="command" value="">
                              <input type="hidden" name="start" value="<%=start%>">
                              <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                              <input type="hidden" name="hidden_recr_application_id" value="<%=oidRecrApplication%>">
                              <input type="hidden" name="vectSize" value="<%=vectSize%>">
                              <input type="hidden" name="hidden_recr_references_id" value="<%=oidRecrReferences%>">
                              <input type="hidden" name="<%=frmRecrReferences.fieldNames[FrmRecrReferences.FRM_FIELD_RECR_APPLICATION_ID] %>" value="<%=oidRecrApplication%>">
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                  <% if(oidRecrApplication != 0){%>
                                  <tr> 
                                    <td> 
                                      <table  width="69%" border="0" cellspacing="0" cellpadding="0">
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
                                                <td valign="top" align="left" width="10"><img src="<%=approot%>/images/tab/inactive_left.jpg" width="12" height="29"></td>
                                                <td valign="middle" background="<%=approot%>/images/tab/inactive_bg.jpg" nowrap width="120"> 
                                                  <div align="center" class="tablink"><a href="recrappl_history.jsp?hidden_recr_application_id=<%=oidRecrApplication%>" class="tablink">Employment 
                                                    Record </a></div>
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
                                                        <div align="center" class="tablink"><a href="recrappl_language.jsp?hidden_recr_application_id=<%=oidRecrApplication%>" class="tablink"><span class="tablink">Language</span></a></div>
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
                                                    <div align="center" class="tablink">References</div>
                                                </td>
                                                <td width="13" valign="top" align="right"><img src="<%=approot%>/images/tab/active_right.jpg" width="12" height="29"></td>
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
                                                            <td height="14" valign="middle" colspan="3" class="comment">&nbsp;References 
                                                              List </td>
                                                          </tr>
                                                          <%
							try{
								if (listRecrReferences.size()>0){
							%>
                                                          <tr align="left" valign="top"> 
                                                            <td height="22" valign="middle" colspan="3"> 
                                                              <%= drawList(listRecrReferences,oidRecrReferences)%> 
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
                                                                    if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidRecrReferences == 0))
                                                                            cmd = PstRecrReferences.findLimitCommand(start,recordToGet,vectSize);
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
                                                    <% if(iCommand == Command.NONE || (iCommand == Command.SAVE && frmRecrReferences.errorSize()<1)|| iCommand == Command.DELETE || iCommand == Command.BACK || iCommand == Command.FIRST || iCommand == Command.PREV || iCommand == Command.NEXT || iCommand == Command.LAST ){%>
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
                                                              New References</a> </td>
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
                                            <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmRecrReferences.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
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
                                                  <input type="text" name="<%=frmRecrReferences.fieldNames[FrmRecrReferences.FRM_FIELD_RECR_APPLICATION_ID] %>"  value="<%= recrReferences.getRecrApplicationId() %>" class="formElemen">
                                                  * <%= frmRecrReferences.getErrorMsg(FrmRecrReferences.FRM_FIELD_RECR_APPLICATION_ID) %> 
                                                </td>
                                              </tr> --%>
                                                          <tr align="left" valign="top">
                                                            <td height="21" valign="top" width="5%">&nbsp;</td>
                                                            <td height="21" valign="top" width="7%">Name</td>
                                                            <td height="21" colspan="2" width="88%"> 
                                                              <input type="text" name="<%=frmRecrReferences.fieldNames[FrmRecrReferences.FRM_FIELD_NAME] %>"  value="<%= recrReferences.getName() %>" class="formElemen">
                                                              * <%= frmRecrReferences.getErrorMsg(FrmRecrReferences.FRM_FIELD_NAME) %> 
                                                            </td>
                                                          </tr>
                                                          <tr align="left" valign="top">
                                                            <td height="21" valign="top" width="5%">&nbsp;</td>
                                                            <td height="21" valign="top" width="7%">Company</td>
                                                            <td height="21" colspan="2" width="88%"> 
                                                              <input type="text" name="<%=frmRecrReferences.fieldNames[FrmRecrReferences.FRM_FIELD_COMPANY] %>"  value="<%= recrReferences.getCompany() %>" class="formElemen">
                                                            </td>
                                                          </tr>
                                                          <tr align="left" valign="top">
                                                            <td height="21" valign="top" width="5%">&nbsp;</td>
                                                            <td height="21" valign="top" width="7%">Position</td>
                                                            <td height="21" colspan="2" width="88%"> 
                                                              <input type="text" name="<%=frmRecrReferences.fieldNames[FrmRecrReferences.FRM_FIELD_POSITION] %>"  value="<%= recrReferences.getPosition() %>" class="formElemen">
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
                                                    String scomDel = "javascript:cmdAsk('"+oidRecrReferences+"')";
                                                    String sconDelCom = "javascript:cmdConfirmDelete('"+oidRecrReferences+"')";
                                                    String scancel = "javascript:cmdEdit('"+oidRecrReferences+"')";
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
