
<% 
/* 
 * Page Name  		:  srcorichecklist.jsp
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
<%@ page import = "com.dimata.harisma.session.recruitment.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ include file = "../../main/javainit.jsp" %>

<% int  appObjCode = 1;//AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_RECRUITMENT, AppObjInfo.OBJ_ORIENTATION_CHECKLIST); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
//boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));

%>

<%! 
boolean privAdd = true;
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
    int iCommand = FRMQueryString.requestCommand(request);

    SrcOriChecklist srcOriChecklist = new SrcOriChecklist();
    FrmSrcOriChecklist frmSrcOriChecklist = new FrmSrcOriChecklist();

    if(iCommand==Command.BACK)
    {
        frmSrcOriChecklist = new FrmSrcOriChecklist(request, srcOriChecklist);
        try{
            srcOriChecklist = (SrcOriChecklist) session.getValue(SessOriChecklist.SESS_SRC_ORICHECKLIST);
                if(srcOriChecklist == null)
            srcOriChecklist = new SrcOriChecklist();
        }catch (Exception e){
            System.out.println("e....."+e.toString());
            srcOriChecklist = new SrcOriChecklist();
        }
    }
%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Recruitment</title>
<script language="JavaScript">

function cmdAdd(){
	document.frmsrcorichecklist.command.value="<%=Command.ADD%>";
	document.frmsrcorichecklist.action="orichecklist_edit.jsp";
	document.frmsrcorichecklist.submit();
}

function cmdSearch(){
	document.frmsrcorichecklist.command.value="<%=Command.LIST%>";
	document.frmsrcorichecklist.action="orichecklist_list.jsp";
	document.frmsrcorichecklist.submit();
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
                  Employee &gt; Recruitment &gt; Orientation Checklist<!-- #EndEditable --> 
                  </strong></font> </td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td  style="background-color:<%=bgColorContent%>;"> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                          <tr> 
                            <td valign="top"> 
                              <table  style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                <tr> 
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="frmsrcorichecklist" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                        <tr align="left" valign="top">
                                          <td height="21" valign="middle" width="3%">&nbsp;</td>
                                          <td height="21" valign="middle" width="15%" nowrap>&nbsp;</td>
                                          <td height="21" width="82%" class="comment">*)= 
                                            required</td>
                                        </tr>
                                        <tr align="left" valign="top">
                                          <td height="21" valign="top" width="3%">&nbsp;</td>
                                          <td height="21" valign="top" width="15%" nowrap>Name</td>
                                          <td height="21" width="82%"> 
                                            <input type="text" name="<%=frmSrcOriChecklist.fieldNames[FrmSrcOriChecklist.FRM_FIELD_NAME] %>"  value="<%= srcOriChecklist.getName() %>" class="elemenForm" size="50">
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top">
                                          <td height="21" valign="top" width="3%">&nbsp;</td>
                                          <td height="21" valign="top" width="15%" nowrap><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                          <td height="21" width="82%"> 
                                            <%/*Vector department_value = new Vector(1,1);
						Vector department_key = new Vector(1,1);
					 	String selectValue = (String)srcOriChecklist.getDepartment();*/
					  %>
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
                                            <%= ControlCombo.draw(frmSrcOriChecklist.fieldNames[FrmSrcOriChecklist.FRM_FIELD_DEPARTMENT],"formElemen",null, ""+srcOriChecklist.getDepartment(), dept_value, dept_key) %> 
                                            <%//= ControlCombo.draw(frmSrcOriChecklist.fieldNames[FrmSrcOriChecklist.FRM_FIELD_DEPARTMENT],"elementForm", "", selectVal, objKey, objValue) %> 
                                          </td>
                                        </tr>
                                        <%--
                                        <tr align="left" valign="top">
                                          <td height="21" valign="top" width="3%">&nbsp;</td>
                                          <td height="21" valign="top" width="15%" nowrap><%=dictionaryD.getWord(I_Dictionary.SECTION) %></td>
                                          <td height="21" width="82%"> 
                                            <%/*Vector section_value = new Vector(1,1);
						Vector section_key = new Vector(1,1);
					 	String selectValue = (String)srcOriChecklist.getSection();*/
					  %>
                                            <%//= ControlCombo.draw(frmSrcOriChecklist.fieldNames[FrmSrcOriChecklist.FRM_FIELD_SECTION],"elementForm", "", selectVal, objKey, objValue) %> 
                                            <% 
                                                Vector sec_value = new Vector(1,1);
                                                Vector sec_key = new Vector(1,1); 
                                                sec_value.add("0");
                                                sec_key.add("select ...");
                                                Vector listSec = PstSection.list(0, 0, "", " DEPARTMENT_ID, SECTION ");
                                                for (int i = 0; i < listSec.size(); i++) {
                                                        Section sec = (Section) listSec.get(i);
                                                        sec_key.add(sec.getSection());
                                                        sec_value.add(String.valueOf(sec.getOID()));
                                                }
                                            %>
                                            <%= ControlCombo.draw(frmSrcOriChecklist.fieldNames[FrmSrcOriChecklist.FRM_FIELD_SECTION],"formElemen",null, "" + srcOriChecklist.getSection(), sec_value, sec_key) %> 
                                          </td>
                                        </tr>
                                        --%>
                                        <tr align="left" valign="top">
                                          <td height="21" valign="top" width="3%">&nbsp;</td>
                                          <td height="21" valign="top" width="15%" nowrap>Position</td>
                                          <td height="21" width="82%"> 
                                            <%/*Vector position_value = new Vector(1,1);
						Vector position_key = new Vector(1,1);
					 	String selectValue = (String)srcOriChecklist.getPosition();*/
					  %>
                                            <%//= ControlCombo.draw(frmSrcOriChecklist.fieldNames[FrmSrcOriChecklist.FRM_FIELD_POSITION],"elementForm", "", selectVal, objKey, objValue) %> 
                                            <% 
                                                Vector pos_value = new Vector(1,1);
                                                Vector pos_key = new Vector(1,1); 
                                                pos_value.add("0");
                                                pos_key.add("select ...");
                                                Vector listPos = PstPosition.list(0, 0, "", " POSITION ");
                                                for (int i = 0; i < listPos.size(); i++) {
                                                        Position pos = (Position) listPos.get(i);
                                                        pos_key.add(pos.getPosition());
                                                        pos_value.add(String.valueOf(pos.getOID()));
                                                }
                                            %>
                                            <%= ControlCombo.draw(frmSrcOriChecklist.fieldNames[FrmSrcOriChecklist.FRM_FIELD_POSITION],"formElemen",null, "" + srcOriChecklist.getPosition(), pos_value, pos_key, " onkeydown=\"javascript:fnTrapKD()\"") %>	
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top">
                                          <td height="21" valign="top" width="3%">&nbsp;</td>
                                          <td height="21" valign="top" width="15%" nowrap>Commencement 
                                            Date from</td>
                                          <td height="21" width="82%"> <%=	ControlDate.drawDate(frmSrcOriChecklist.fieldNames[FrmSrcOriChecklist.FRM_FIELD_COMMDATESTART], srcOriChecklist.getCommdatestart(), 1,-5) %> 
                                            &nbsp;&nbsp;&nbsp; to &nbsp;&nbsp;&nbsp;<%=	ControlDate.drawDate(frmSrcOriChecklist.fieldNames[FrmSrcOriChecklist.FRM_FIELD_COMMDATEEND], srcOriChecklist.getCommdateend(), 1,-5) %></td>
                                        </tr>
                                        <tr align="left" valign="top">
                                          <td height="21" valign="top" width="3%">&nbsp;</td>
                                          <td height="21" valign="top" width="15%" nowrap>&nbsp;</td>
                                          <td height="21" width="82%">&nbsp; </td>
                                        </tr>
                                        <tr>
                                          <td width="3%">&nbsp;</td>
                                          <td width="15%" nowrap>&nbsp;</td>
                                          <td width="82%" class="command"> 
                                          <%--
                                            <% if(privAdd){%>
                                            <a href="javascript:cmdAdd()">Add 
                                            New</a> 
                                            <%}%>
                                            --%>
                                            <table width="30%" border="0" cellspacing="0" cellpadding="0">
                                              <tr> 
                                                <td width="20%"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                                <td width="28%" class="command" nowrap><a href="javascript:cmdSearch()">Search 
                                                  for Orientation Checklist</a></td>
                                                <% if(true){%>
                                                <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="10" height="8"></td>
                                                <td width="20%"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New Employee"></a></td>
                                                <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                                <td width="26%" class="command" nowrap><a href="javascript:cmdAdd()">Add 
                                                  New Orientation Checklist</a></td>
                                                <%}else{%>
                                                <td width="50%">&nbsp;</td>
                                                <%}%>
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
