
<% 
/* 
 * Page Name  		:  srccommentcardheader.jsp
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
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%@ page import = "com.dimata.harisma.session.canteen.*" %>
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
    int iCommand = FRMQueryString.requestCommand(request);

    SrcCommentCardHeader srcCommentCardHeader = new SrcCommentCardHeader();
    FrmSrcCommentCardHeader frmSrcCommentCardHeader = new FrmSrcCommentCardHeader();

    if(iCommand==Command.BACK)
    {
        frmSrcCommentCardHeader = new FrmSrcCommentCardHeader(request, srcCommentCardHeader);

        try{
            srcCommentCardHeader = (SrcCommentCardHeader) session.getValue(SessCommentCardHeader.SESS_SRC_COMMENTCARDHEADER);
            if(srcCommentCardHeader == null)
                srcCommentCardHeader = new SrcCommentCardHeader();
        }catch (Exception e){
            System.out.println("e....."+e.toString());
            srcCommentCardHeader = new SrcCommentCardHeader();
        }
    }	

/*
 SrcCommentCardHeader srcCommentCardHeader = new SrcCommentCardHeader();
 FrmSrcCommentCardHeader frmSrcCommentCardHeader = new FrmSrcCommentCardHeader();
try{
	srcCommentCardHeader = (SrcCommentCardHeader)session.getValue(SessCommentCardHeader.SESS_SRC_COMMENTCARDHEADER);
}catch(Exception e){
	srcCommentCardHeader = new SrcCommentCardHeader();
}

try{
	session.removeValue(SessCommentCardHeader.SESS_SRC_COMMENTCARDHEADER);
}catch(Exception e){
}
*/
%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Canteen</title>
<script language="JavaScript">

function cmdAdd(){
	document.frmsrccommentcardheader.command.value="<%=Command.ADD%>";
	document.frmsrccommentcardheader.action="commentcardheader_edit.jsp";
	document.frmsrccommentcardheader.submit();
}

function cmdSearch(){
	document.frmsrccommentcardheader.command.value="<%=Command.LIST%>";
	document.frmsrccommentcardheader.action="commentcardheader_list.jsp";
	document.frmsrccommentcardheader.submit();
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
                                    <form name="frmsrccommentcardheader" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                        <tr align="left" valign="top"> 
                                          <td height="21" valign="middle" width="3%">&nbsp;</td>
                                          <td height="21" valign="middle" width="9%">&nbsp;</td>
                                          <td height="21" width="88%" class="comment">&nbsp;</td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="21" valign="top" width="3%">&nbsp;</td>
                                          <td height="21" valign="top" width="9%">Name</td>
                                          <td height="21" width="88%"> 
                                            <input type="text" name="<%=frmSrcCommentCardHeader.fieldNames[FrmSrcCommentCardHeader.FRM_FIELD_NAME] %>"  value="<%= srcCommentCardHeader.getName() %>" class="elemenForm" size="50">
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="21" valign="top" width="3%">&nbsp;</td>
                                          <td height="21" valign="top" width="9%" nowrap>Comment 
                                            Card Date from </td>
                                          <td height="21" width="88%"> <%=	ControlDate.drawDate(frmSrcCommentCardHeader.fieldNames[FrmSrcCommentCardHeader.FRM_FIELD_CARDDATEFROM], srcCommentCardHeader.getCarddatefrom(), 1,-5) %> 
                                            &nbsp;&nbsp;&nbsp; to &nbsp;&nbsp;&nbsp; 
                                            <%=	ControlDate.drawDate(frmSrcCommentCardHeader.fieldNames[FrmSrcCommentCardHeader.FRM_FIELD_CARDDATETO], srcCommentCardHeader.getCarddateto(), 1,-5) %> 
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="21" valign="top" width="3%">&nbsp;</td>
                                          <td height="21" valign="top" width="9%"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                          <td height="21" width="88%"> 
                                            <%/*Vector department_value = new Vector(1,1);
						Vector department_key = new Vector(1,1);
					 	String selectValue = (String)srcCommentCardHeader.getDepartment();*/
					  %>
                                            <%//= ControlCombo.draw(frmSrcCommentCardHeader.fieldNames[FrmSrcCommentCardHeader.FRM_FIELD_DEPARTMENT],"elementForm", "", selectVal, objKey, objValue) %> 
                                            <% 
                                                Vector dept_value = new Vector(1,1);
                                                Vector dept_key = new Vector(1,1);
                                                dept_value.add("0");
                                                dept_key.add("select ...");
                                                Vector listDept = PstDepartment.list(0, 0, "", " DEPARTMENT ");
                                                for (int i = 0; i < listDept.size(); i++) {
                                                        Department dept = (Department) listDept.get(i);
                                                        dept_key.add(dept.getDepartment());
                                                        dept_value.add(String.valueOf(dept.getOID()));
                                                }
                                            %>
                                            <%= ControlCombo.draw(frmSrcCommentCardHeader.fieldNames[FrmSrcCommentCardHeader.FRM_FIELD_DEPARTMENT],"formElemen",null, ""+srcCommentCardHeader.getDepartment(), dept_value, dept_key)%> 
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="21" valign="top" width="3%">&nbsp;</td>
                                          <td height="21" valign="top" width="9%">&nbsp;</td>
                                          <td height="21" width="88%"> &nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="3%">&nbsp;</td>
                                          <td width="9%">&nbsp;</td>
                                          <td width="88%" class="command"> <%-- 
                                            <% if(privAdd){%>
                                            <a href="javascript:cmdAdd()">Add 
                                            New</a> 
                                            <%}%> --%>
                                            <table width="30%" border="0" cellspacing="0" cellpadding="0">
                                              <tr> 
                                                <td width="20%"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                                <td width="28%" class="command" nowrap><a href="javascript:cmdSearch()">Search 
                                                  for Comment Card</a></td>
                                                <% if(privAdd){%>
                                                <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="10" height="8"></td>
                                                <td width="20%"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New Employee"></a></td>
                                                <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                                <td width="26%" class="command" nowrap><a href="javascript:cmdAdd()">Add 
                                                  New Comment Card</a></td>
                                                <%}else{%>
                                                <td width="50%">&nbsp;</td>
                                                <%}%>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td height="21" valign="top" width="3%">&nbsp;</td>
                                          <td height="21" valign="top" width="9%">&nbsp;</td>
                                          <td width="88%">&nbsp;</td>
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
      <%@ include file = "../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
