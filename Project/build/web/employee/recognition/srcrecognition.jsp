
<% 
/* 
 * Page Name  		:  srcrecognition.jsp
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
<%@ page import = "com.dimata.harisma.session.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>

<%//@ include file = "../../main/javainit.jsp" %>
<% //int  appObjCode = 1;//AppObjInfo.composeObjCode(AppObjInfo.G1_SECRETARY , AppObjInfo.G2_SECRETARY_GENERAL   , AppObjInfo.OBJ_SG_RECOGNITION   	); %>
<%//@ include file = "../../main/checkuser.jsp" %>
<%
    /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//	boolean privStart=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
//    boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//    boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//    boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
    /*boolean privAdd = true;
    boolean privUpdate = true;
    boolean privDelete = true;
    boolean privStart = true;*/
%>

<%@ include file = "../../main/javainit.jsp" %>
<%  int  appObjCode = 1; //AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE , AppObjInfo.G2_RECOGNITION, AppObjInfo.OBJ_UPDATE_PER_EMPLOYEE); %>
<%//@ include file = "../../main/checkuser.jsp" %>
<%
    /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//	boolean privStart=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
   /* privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
    privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
    privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
  */
    boolean privAdd = true;
    boolean privUpdate = true;
    boolean privDelete = true;
    boolean privStart = true;
%>
<!-- Jsp Block -->
<%
    int iCommand = FRMQueryString.requestCommand(request);

    SrcRecognition srcRecognition = new SrcRecognition();
    FrmSrcRecognition frmSrcRecognition = new FrmSrcRecognition();

    if(iCommand==Command.BACK)
    {        
        frmSrcRecognition = new FrmSrcRecognition(request, srcRecognition);
        try{
            srcRecognition = (SrcRecognition)session.getValue(SessRecognition.SESS_SRC_RECOGNITION);
            if(srcRecognition == null) {
                srcRecognition = new SrcRecognition();
		//System.out.println("ecccccc "+srcEmpSchedule.getOrderBy());
            }
        }catch (Exception e){
            System.out.println("e....."+e.toString());
            srcRecognition = new SrcRecognition();
        }
    }
%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Recognition</title>
<script language="JavaScript">

function cmdAdd(){
	document.frmsrcrecognition.command.value="<%=Command.ADD%>";
	document.frmsrcrecognition.action="recognition_edit.jsp";
	document.frmsrcrecognition.submit();
}

function cmdSearch(){
	document.frmsrcrecognition.command.value="<%=Command.LIST%>";
	document.frmsrcrecognition.action="recognition_list.jsp";
	document.frmsrcrecognition.submit();
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
<!--
    function hideObjectForEmployee(){
        //document.frmsrcemployee.<%//=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_START_COMMENC] + "_mn"%>.style.visibility = 'hidden';
        document.frmsrcrecognition.FRM_FIELD_RECOG_DATE_FROM_yr.style.visibility = 'hidden';
        document.frmsrcrecognition.FRM_FIELD_RECOG_DATE_TO_mn.style.visibility = 'hidden';
        document.frmsrcrecognition.FRM_FIELD_RECOG_DATE_TO_dy.style.visibility = 'hidden';
        document.frmsrcrecognition.FRM_FIELD_RECOG_DATE_TO_yr.style.visibility = 'hidden';
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
        document.frmsrcrecognition.FRM_FIELD_RECOG_DATE_FROM_yr.style.visibility = '';
        document.frmsrcrecognition.FRM_FIELD_RECOG_DATE_TO_mn.style.visibility = '';
        document.frmsrcrecognition.FRM_FIELD_RECOG_DATE_TO_dy.style.visibility = '';
        document.frmsrcrecognition.FRM_FIELD_RECOG_DATE_TO_yr.style.visibility = '';
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
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">
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
                  Employee &gt; Recognition Search<!-- #EndEditable --> </strong></font> 
                </td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td   style="background-color:<%=bgColorContent%>; "> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                          <tr> 
                            <td valign="top"> 
                              <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                <tr> 
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="frmsrcrecognition" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
									  
									  <%if(privStart){%>
									  
                                      <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                        <tr align="left" valign="top"> 
                                          <td valign="middle" width="1%">&nbsp;</td>
                                          <td valign="middle" width="10%" nowrap>&nbsp;</td>
                                          <td width="89%" class="comment">*)= 
                                            required</td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td valign="top" width="1%">&nbsp;</td>
                                          <td valign="top" width="10%" nowrap>Full 
                                            Name</td>
                                          <td width="89%"> 
                                            <input type="text" name="<%=frmSrcRecognition.fieldNames[FrmSrcRecognition.FRM_FIELD_FULL_NAME] %>"  value="<%= srcRecognition.getFullName() %>" class="elemenForm">
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td valign="top" width="1%">&nbsp;</td>
                                          <td valign="top" width="10%" nowrap>Payroll</td>
                                          <td width="89%"> 
                                            <input type="text" name="<%=frmSrcRecognition.fieldNames[FrmSrcRecognition.FRM_FIELD_EMP_NUMBER] %>"  value="<%= srcRecognition.getEmpNumber() %>" class="elemenForm">
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td valign="top" width="1%">&nbsp;</td>
                                          <td valign="top" width="10%" nowrap>Recognition 
                                            Date</td>
                                          <td width="89%"> <%=	ControlDate.drawDate(frmSrcRecognition.fieldNames[FrmSrcRecognition.FRM_FIELD_RECOG_DATE_FROM], srcRecognition.getRecogDateFrom(), 1,-5) %> to 
                                                           <%=	ControlDate.drawDate(frmSrcRecognition.fieldNames[FrmSrcRecognition.FRM_FIELD_RECOG_DATE_TO], srcRecognition.getRecogDateTo(), 1,-5) %>
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td valign="top" width="1%">&nbsp;</td>
                                          <td valign="top" width="10%" nowrap><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                          <td width="89%"> 
                                            <%
                                                Vector department_value = new Vector(1,1);
                                                Vector department_key = new Vector(1,1);
                                                String where;
                                                
                                                long oidHRD = 0;
                                                try{
                                                    oidHRD = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName("OID_HRD_DEPARTMENT"))); 
                                                }catch(Exception E){
                                                    System.out.println("[exception] Sys Prop OID_HRD_DEPARTMENT [not set] "+E.toString());
                                                }
                                                
                                                if(departmentOid==oidHRD){
                                                    where="";
                                                    department_value.add("0");
                                                    department_key.add("select ...");
                                                }else{
                                                    where = PstDepartment.TBL_HR_DEPARTMENT+".DEPARTMENT_ID = "+departmentOid;
                                                }
                                               
                                                Vector listDept = PstDepartment.list(0, 0, where, " DEPARTMENT ");
                                                String selectValueDepartment = String.valueOf(srcRecognition.getDepartment());
                                                for (int i = 0; i < listDept.size(); i++) {
                                                    Department dept = (Department) listDept.get(i);
                                                    department_key.add(dept.getDepartment());
                                                    department_value.add(String.valueOf(dept.getOID()));
                                                }
                                            %>
                                            <%=ControlCombo.draw(frmSrcRecognition.fieldNames[FrmSrcRecognition.FRM_FIELD_DEPARTMENT],"elementForm", null, selectValueDepartment, department_value, department_key," onkeydown=\"javascript:fnTrapKD()\"") %> 
                                            <%-- <input type="text" name="<%=frmSrcRecognition.fieldNames[FrmSrcRecognition.FRM_FIELD_DEPARTMENT] %>"  value="<%= srcRecognition.getDepartment() %>" class="elemenForm"> --%>
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td valign="top" width="1%">&nbsp;</td>
                                          <td valign="top" width="10%" nowrap>Position</td>
                                          <td width="89%"> 
                                            <%
						Vector position_value = new Vector(1,1);
						Vector position_key = new Vector(1,1);
                                                position_value.add("0");
                                                position_key.add("select ...");
                                                Vector listPos = PstPosition.list(0, 0, "", " POSITION ");
					 	String selectValuePosition = String.valueOf(srcRecognition.getPosition());
                                                for (int i = 0; i < listPos.size(); i++) {
                                                    Position pos = (Position) listPos.get(i);
                                                    position_key.add(pos.getPosition());
                                                    position_value.add(String.valueOf(pos.getOID()));
                                                }
                                            %>
                                            <%= ControlCombo.draw(frmSrcRecognition.fieldNames[FrmSrcRecognition.FRM_FIELD_POSITION],"elementForm", null, selectValuePosition, position_value, position_key," onkeydown=\"javascript:fnTrapKD()\"") %> 
                                            <%-- <input type="text" name="<%=frmSrcRecognition.fieldNames[FrmSrcRecognition.FRM_FIELD_POSITION] %>"  value="<%= srcRecognition.getPosition() %>" class="elemenForm"> --%>
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td valign="top" width="1%">&nbsp;</td>
                                          <td valign="top" width="10%" nowrap>&nbsp;</td>
                                          <td width="89%">&nbsp;</td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td valign="top" width="1%">&nbsp;</td>
                                          <td valign="top" width="10%" nowrap>&nbsp;</td>
                                          <td width="89%"> 
                                            <table border="0" cellpadding="0" cellspacing="0" width="333">
                                              <tr> 
                                                <td width="4%"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                <td width="11%"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Schedule" ></a></td>
                                                <td width="4%"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                <td width="11%" class="command" nowrap><a href="javascript:cmdSearch()">Search 
                                                  for Recognition</a></td>
                                                <% if(privAdd){%>
                                                <td width="4%"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                <td width="11%"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New Schedule"></a></td>
                                                <td width="4%"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                <td width="11%" class="command" nowrap><a href="javascript:cmdAdd()">Add 
                                                  New Recognition</a></td>
                                                <%}%>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <%--
                                        <tr align="left" valign="top"> 
                                          <td valign="top" width="1%">&nbsp;</td>
                                          <td valign="top" width="10%">&nbsp;</td>
                                          <td width="89%"> 
                                            <input type="button" name="Submit" value="Search" onClick="javascript:cmdSearch()">
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td width="10%">&nbsp;</td>
                                          <td width="89%" class="command"> 
                                            <% if(privAdd){%>
                                            <a href="javascript:cmdAdd()">Add 
                                            New</a> 
                                            <%}%>
                                          </td>
                                        </tr>
										--%>
                                      </table>
									  <%}else{%>
									  <div align="center">You do not have sufficient privilege to access this page.</div>
									  <%}%>
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
