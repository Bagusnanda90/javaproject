
<% 
/* 
 * Page Name  		:  commentcardheader_edit.jsp
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
<%@ page import = "com.dimata.harisma.entity.canteen.*" %>
<%@ page import = "com.dimata.harisma.form.canteen.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>

<%@ include file = "../main/javainit.jsp" %>
<%--
<% int  appObjCode = 1;// AppObjInfo.composeObjCode(AppObjInfo.--, AppObjInfo.--, AppObjInfo.--); %>
<%//@ include file = "../main/checkuser.jsp" %>
<% 
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
boolean privAdd=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
boolean privUpdate=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
boolean privDelete=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
--%>
<% int appObjCode = 1;//AppObjInfo.composeObjCode(AppObjInfo.G1_CANTEEN, AppObjInfo.G2_CANTEEN, AppObjInfo.OBJ_COMMENT_CARD); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
    // Check privilege except VIEW, view is already checked on checkuser.jsp as basic access
//    boolean privView = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
//    boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//    boolean privUpdate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//    boolean privDelete = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
    //out.print("privView=" + privView + " | privAdd=" + privAdd);
%>
<!-- Jsp Block -->
<%
	CtrlCommentCardHeader ctrlCommentCardHeader = new CtrlCommentCardHeader(request);
	long oidCommentCardHeader = FRMQueryString.requestLong(request, "hidden_comment_card_header_id");

	int iErrCode = FRMMessage.ERR_NONE;
	String errMsg = "";
	String whereClause = "";
	String orderClause = "";
	int iCommand = FRMQueryString.requestCommand(request);
	int start = FRMQueryString.requestInt(request,"start");

	//out.println("iCommand : "+iCommand);
	ControlLine ctrLine = new ControlLine();
	iErrCode = ctrlCommentCardHeader.action(iCommand, oidCommentCardHeader, request);

	errMsg = ctrlCommentCardHeader.getMessage();
	FrmCommentCardHeader frmCommentCardHeader = ctrlCommentCardHeader.getForm();
	CommentCardHeader commentCardHeader = ctrlCommentCardHeader.getCommentCardHeader();
	oidCommentCardHeader = commentCardHeader.getOID();

        if ( ((iCommand == Command.SAVE) || (iCommand == Command.DELETE)) && (oidCommentCardHeader > 0)) {
            String[] arrQuestionId = null;
            String[] arrMarkId = null;
            String[] arrRemark = null;

            try {
                arrQuestionId = request.getParameterValues(FrmCommentCard.fieldNames[FrmCommentCard.FRM_FIELD_CARD_QUESTION_ID]);
                arrMarkId = request.getParameterValues(FrmCommentCard.fieldNames[FrmCommentCard.FRM_FIELD_CHECKLIST_MARK_ID]);
                arrRemark = request.getParameterValues(FrmCommentCard.fieldNames[FrmCommentCard.FRM_FIELD_REMARK]);
            }
            catch (Exception e) {}

            if ((arrQuestionId != null) && (arrQuestionId.length > 0)) {
                CommentCard commentcard = new CommentCard();
                for (int i = 0; i < arrQuestionId.length; i++) {
                    String where  = PstCommentCard.fieldNames[PstCommentCard.FLD_COMMENT_CARD_HEADER_ID] + "=" + oidCommentCardHeader;
                           where += " AND " + PstCommentCard.fieldNames[PstCommentCard.FLD_CARD_QUESTION_ID] + "=" + arrQuestionId[i];
                    Vector listcc = PstCommentCard.list(0, 0, where, "");

                    if (iCommand == Command.SAVE) {
                        if (listcc.size() > 0) {
                            commentcard = (CommentCard) listcc.get(0);
                            commentcard.setCommentCardHeaderId(oidCommentCardHeader);
                            commentcard.setCardQuestionId(Long.parseLong(arrQuestionId[i]));
                            commentcard.setChecklistMarkId(Long.parseLong(arrMarkId[i]));
                            commentcard.setRemark(arrRemark[i]);
                            PstCommentCard.updateExc(commentcard);
                        }
                        else {
                            commentcard.setCommentCardHeaderId(oidCommentCardHeader);
                            commentcard.setCardQuestionId(Long.parseLong(arrQuestionId[i]));
                            commentcard.setChecklistMarkId(Long.parseLong(arrMarkId[i]));
                            commentcard.setRemark(arrRemark[i]);
                            PstCommentCard.insertExc(commentcard);
                        }
                    }
                    else
                    if (iCommand == Command.DELETE) {
                        if (listcc.size() > 0) {
                            commentcard = (CommentCard) listcc.get(0);
                            PstCommentCard.deleteExc(commentcard.getOID());
                        }
                        else {
                            System.out.println("Cannot delete CommentCard...");
                        }
                    }
                }
            }
        }


	//if(((iCommand==Command.SAVE)||(iCommand==Command.DELETE))&&(frmCommentCardHeader.errorSize()<1)){
	if((iCommand==Command.DELETE)&&(frmCommentCardHeader.errorSize()<1)){
	%>
        <jsp:forward page="commentcardheader_list.jsp"> 
        <jsp:param name="start" value="<%=start%>" />
        <jsp:param name="hidden_comment_card_header_id" value="<%=commentCardHeader.getOID()%>" />
        </jsp:forward>
        <%
	}

        Employee emp = new Employee();
        //Department dep = new Department();
        Position pos = new Position();

        if (commentCardHeader.getEmployeeId() > 0) {
            try {
                emp = PstEmployee.fetchExc(commentCardHeader.getEmployeeId());
            }
            catch (Exception e) {}
            if (emp.getPositionId() > 0) {
                try {
                    pos = PstPosition.fetchExc(emp.getPositionId());
                }
                catch (Exception e) {}
            }
        }
%>
<!-- End of Jsp Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Canteen</title>
<script language="JavaScript">

	function cmdCancel(){
		document.frm_commentcardheader.command.value="<%=Command.ADD%>";
		document.frm_commentcardheader.action="commentcardheader_edit.jsp";
		document.frm_commentcardheader.submit();
	} 
	function cmdCancel(){
		document.frm_commentcardheader.command.value="<%=Command.CANCEL%>";
		document.frm_commentcardheader.action="commentcardheader_edit.jsp";
		document.frm_commentcardheader.submit();
	} 

	function cmdEdit(oid){ 
		document.frm_commentcardheader.command.value="<%=Command.EDIT%>";
		document.frm_commentcardheader.action="commentcardheader_edit.jsp";
		document.frm_commentcardheader.submit(); 
	} 

	function cmdSave(){
		document.frm_commentcardheader.command.value="<%=Command.SAVE%>"; 
		document.frm_commentcardheader.action="commentcardheader_edit.jsp";
		document.frm_commentcardheader.submit();
	}

	function cmdAsk(oid){
		document.frm_commentcardheader.command.value="<%=Command.ASK%>"; 
		document.frm_commentcardheader.action="commentcardheader_edit.jsp";
		document.frm_commentcardheader.submit();
	} 

	function cmdConfirmDelete(oid){
		document.frm_commentcardheader.command.value="<%=Command.DELETE%>";
		document.frm_commentcardheader.action="commentcardheader_edit.jsp"; 
		document.frm_commentcardheader.submit();
	}  

	function cmdBack(){
		document.frm_commentcardheader.command.value="<%=Command.FIRST%>"; 
		document.frm_commentcardheader.action="commentcardheader_list.jsp";
		document.frm_commentcardheader.submit();
	}

	function cmdSearchEmp(){
                window.open("lookupemployee.jsp?department=" + document.frm_commentcardheader.EMPDEPARTMENT.value, null, 
                            "height=600,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}

        function cmdPrint(){
                window.open("<%=approot%>/servlet/com.dimata.harisma.report.canteen.CardPdf?cardId=<%=oidCommentCardHeader%>");
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
<link rel="stylesheet" href="../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../styles/tab.css" type="text/css">
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
      <%@ include file = "../main/header.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
  <tr> 
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
      <%@ include file = "../main/mnmain.jsp" %>
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
                  Canteen &gt; Employee Comment Card<!-- #EndEditable --> </strong></font> 
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
                                    <form name="frm_commentcardheader" method="post" action="">
                                      <input type="hidden" name="command" value="">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="hidden_comment_card_header_id" value="<%=oidCommentCardHeader%>">
                                      <table width="100%" cellspacing="2" cellpadding="2" >
                                        <tr> 
                                          <td> 
                                            <div align="center"><b><font size="3">EMPLOYEE 
                                              COMMENT CARD</font></b></div>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td>&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td> 
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td width="3%">Date</td>
                                                <td width="2%" nowrap><%=ControlDate.drawDate(FrmCommentCardHeader.fieldNames[FrmCommentCardHeader.FRM_FIELD_CARD_DATETIME], commentCardHeader.getCardDatetime() != null ? commentCardHeader.getCardDatetime() : new Date(),"formElemen", 1, -5)%></td>
                                                <td width="3%">Time</td>
                                                <td width="92%" nowrap><%=ControlDate.drawTime(FrmCommentCardHeader.fieldNames[FrmCommentCardHeader.FRM_FIELD_CARD_DATETIME], commentCardHeader.getCardDatetime() != null ? commentCardHeader.getCardDatetime() : new Date(),"formElemen", 24, 1, 0)%> 
                                                  * <%=frmCommentCardHeader.getErrorMsg(FrmCommentCardHeader.FRM_FIELD_CARD_DATETIME)%></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td> 
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td nowrap width="7%">Filled up 
                                                  by</td>
                                                <td width="20%" nowrap> 
                                                  <input type="hidden" name="<%=FrmCommentCardHeader.fieldNames[FrmCommentCardHeader.FRM_FIELD_EMPLOYEE_ID]%>" value="<%=commentCardHeader.getEmployeeId()%>" class="formElemen">
                                                  <%
                                                    String empname = "";
                                                    emp = new Employee();
                                                    if (commentCardHeader.getEmployeeId() > 0) {
                                                        emp = PstEmployee.fetchExc(commentCardHeader.getEmployeeId());
                                                    }
                                                  %>
                                                  <input type="text" name="EMPFULLNAME" value="<%=emp.getFullName()%>" class="formElemen" size="40">
                                                  * <%=frmCommentCardHeader.getErrorMsg(FrmCommentCardHeader.FRM_FIELD_EMPLOYEE_ID)%> 
                                                  <input type="button" name="search2" value="Search..." onClick="javascript:cmdSearchEmp();">
                                                </td>
                                                <td width="3%"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                                <td width="11%"> 
                                                  <% 
                                                        Vector dept_value = new Vector(1,1);
                                                        Vector dept_key = new Vector(1,1);
                                                        //dept_value.add("0");
                                                        //dept_key.add("select ...");
                                                        Vector listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                        for (int i = 0; i < listDept.size(); i++) {
                                                                Department dept = (Department) listDept.get(i);
                                                                dept_key.add(dept.getDepartment());
                                                                dept_value.add(String.valueOf(dept.getOID()));
                                                        }
                                                    %>
                                                  <%= ControlCombo.draw("EMPDEPARTMENT","formElemen",null, ""+emp.getDepartmentId(), dept_value, dept_key) %> 
                                                </td>
                                                <td width="4%">Position</td>
                                                <td width="55%"> 
                                                  <input type="text" name="EMPPOSITION" value="<%=pos.getPosition()%>" class="formElemen" size="40">
                                                </td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td> 
                                            <table width="100%" border="0" cellspacing="1" cellpadding="1" class="listgen">
                                              <tr> 
                                                <td class="listgentitle" width="4%">No.</td>
                                                <td class="listgentitle" width="35%">Questions</td>
                                                <td class="listgentitle" width="18%">Mark</td>
                                                <td class="listgentitle" width="43%">Remark</td>
                                              </tr>
                                              <%
                                                Vector listquestion = PstCardQuestion.list(0, 0, "", "CARD_QUESTION_GROUP_ID");
                                                long currGroupId = 0;
                                                long prevGroupId = 0;
                                                int cnt = 1;
                                                for (int i=0; i<listquestion.size(); i++) {
                                                    cnt++;
                                                    CardQuestion cq = (CardQuestion) listquestion.get(i);
                                                    currGroupId = cq.getCardQuestionGroupId();
                                                    if (currGroupId != prevGroupId) { 
                                                        cnt=1;
                                                        CardQuestionGroup cqg = new CardQuestionGroup();
                                                        try {
                                                            cqg = PstCardQuestionGroup.fetchExc(currGroupId);
                                                        }
                                                        catch(Exception e) {
                                                        }
                                              %>
                                              <tr> 
                                                <td class="listgensell" nowrap width="4%">&nbsp;</td>
                                                <td class="listgensell" nowrap width="35%"><b><%=cqg.getGroupName()%></b></td>
                                                <td class="listgensell" nowrap width="18%">&nbsp;</td>
                                                <td class="listgensell" nowrap width="43%">&nbsp;</td>
                                              </tr>
                                              <% } %>
                                              <tr> 
                                                <td class="listgensell" nowrap width="4%"> 
                                                  <div align="right"><%=cnt%></div>
                                                </td>
                                                <td class="listgensell" nowrap width="35%"> 
                                                  <input type="hidden" name="<%=FrmCommentCard.fieldNames[FrmCommentCard.FRM_FIELD_CARD_QUESTION_ID]%>" value="<%=cq.getOID()%>">
                                                  <%=cq.getQuestion()%> </td>
                                                <td class="listgensell" nowrap width="18%"> 
                                                  <% 
                                                    Vector mark_value = new Vector(1,1);
                                                    Vector mark_key = new Vector(1,1);
                                                    mark_value.add("0");
                                                    mark_key.add("...");
                                                    Vector listmark = PstChecklistMark.list(0, 0, "", "");
                                                    for (int m= 0; m< listmark.size(); m++) {
                                                            ChecklistMark mark = (ChecklistMark) listmark.get(m);
                                                            mark_key.add(mark.getChecklistMark());
                                                            mark_value.add(String.valueOf(mark.getOID()));
                                                    }

                                                    String wherelist = "";
                                                    String sOID = "";
                                                    wherelist += PstCommentCard.fieldNames[PstCommentCard.FLD_COMMENT_CARD_HEADER_ID];
                                                    wherelist += "=" + oidCommentCardHeader;
                                                    wherelist += " AND " + PstCommentCard.fieldNames[PstCommentCard.FLD_CARD_QUESTION_ID];
                                                    wherelist += "=" + cq.getOID();
                                                    System.out.println("wherelist = " + wherelist);
                                                    Vector vchecklist = PstCommentCard.list(0, 0, wherelist, "");
                                                    CommentCard cc = new CommentCard();
                                                    if (vchecklist.size() > 0) {
                                                        cc = (CommentCard) vchecklist.get(0);
                                                        sOID = String.valueOf(cc.getChecklistMarkId());
                                                        System.out.println("sOID = "+sOID);
                                                    }
                                                  %>
                                                  <%= ControlCombo.draw(FrmCommentCard.fieldNames[FrmCommentCard.FRM_FIELD_CHECKLIST_MARK_ID],"formElemen",null, sOID, mark_value, mark_key) %> 
                                                </td>
                                                <td class="listgensell" nowrap width="43%"> 
                                                  <input type="text" name="<%=FrmCommentCard.fieldNames[FrmCommentCard.FRM_FIELD_REMARK]%>" size="50" value="<%=cc.getRemark()%>">
                                                </td>
                                              </tr>
                                              <%
                                                prevGroupId = currGroupId;
                                                }
                                              %>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td> 
                                            <%
                                                ctrLine.setLocationImg(approot+"/images");
                                                ctrLine.initDefault();
                                                ctrLine.setTableWidth("80");
                                                String scomDel = "javascript:cmdAsk('"+oidCommentCardHeader+"')";
                                                String sconDelCom = "javascript:cmdConfirmDelete('"+oidCommentCardHeader+"')";
                                                String scancel = "javascript:cmdEdit('"+oidCommentCardHeader+"')";
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
                                        <tr>
                                          <td>
                                            <table border="0" cellspacing="0" cellpadding="0" align="left">
                                              <tr> 
                                                <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                <td width="24"><a href="javascript:cmdPrint()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td nowrap><a href="javascript:cmdPrint()" class="command">Print Menulist</a></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                      </table>

                                      <%--
                                      <table width="100%" cellspacing="0" cellpadding="0" >
                                        <tr> 
                                          <td colspan="3">&nbsp; </td>
                                        </tr>
                                        <tr> 
                                          <td width="4%">&nbsp;</td>
                                          <td colspan="2" class="txtheading1">*) 
                                            entry required</td>
                                        </tr>
                                        <tr> 
                                          <td width="4%">&nbsp;</td>
                                          <td width="12%">&nbsp;</td>
                                          <td width="84%">&nbsp;</td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="4%"  valign="top"  >&nbsp;</td>
                                          <td width="12%"  valign="top"  >Employee 
                                            Id</td>
                                          <td  width="84%"  valign="top"> 
                                            <% 
                                                Vector obj_employeeid = new Vector(1,1); //vector of object to be listed 
                                                Vector val_employeeid = new Vector(1,1); //hidden values that will be deliver on request (oids) 
                                                Vector key_employeeid = new Vector(1,1); //texts that displayed on combo box
                                                val_employeeid.add("");
                                                key_employeeid.add("---select---");
                                                String select_employeeid = ""+commentCardHeader.getEmployeeId(); //selected on combo box
                                            %>
                                            <%=ControlCombo.draw(FrmCommentCardHeader.fieldNames[FrmCommentCardHeader.FRM_FIELD_EMPLOYEE_ID], null, select_employeeid, val_employeeid, key_employeeid, "", "formElemen")%> 
                                            * <%=frmCommentCardHeader.getErrorMsg(FrmCommentCardHeader.FRM_FIELD_EMPLOYEE_ID)%></td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="4%"  valign="top"  >&nbsp;</td>
                                          <td width="12%"  valign="top"  >Card 
                                            Datetime</td>
                                          <td  width="84%"  valign="top"> 
                                            <input type="text" name="<%=FrmCommentCardHeader.fieldNames[FrmCommentCardHeader.FRM_FIELD_CARD_DATETIME]%>" value="<%=commentCardHeader.getCardDatetime()%>" class="formElemen">
                                          </td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="4%"  valign="top"  >&nbsp;</td>
                                          <td width="12%"  valign="top"  >&nbsp;</td>
                                          <td  width="84%"  valign="top">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3">&nbsp;</td>
                                        </tr>
                                        <tr align="left"> 
                                          <td colspan="3"> 
                                            <%
                                                ctrLine.setLocationImg(approot+"/images/ctr_line");
                                                ctrLine.initDefault();
                                                ctrLine.setTableWidth("80%");
                                                String scomDel = "javascript:cmdAsk('"+oidCommentCardHeader+"')";
                                                String sconDelCom = "javascript:cmdConfirmDelete('"+oidCommentCardHeader+"')";
                                                String scancel = "javascript:cmdEdit('"+oidCommentCardHeader+"')";
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
                                        <tr> 
                                          <td colspan="3">&nbsp;</td>
                                        </tr>
                                        </table>--%>
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
  <tr> 
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
