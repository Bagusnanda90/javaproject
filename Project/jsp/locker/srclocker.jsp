
<% 
/* 
 * Page Name  		:  srclocker.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		: karya 
 * @version  		: 01 
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
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.session.locker.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>

<%@ page import = "com.dimata.harisma.entity.locker.*" %>
<%@ page import = "com.dimata.harisma.form.locker.*" %>
<%@ include file = "../main/javainit.jsp" %>

<%// int  appObjCode = 1;// AppObjInfo.composeObjCode(AppObjInfo.--, AppObjInfo.--, AppObjInfo.--); %>
<%//@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>

<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_LOCKER, AppObjInfo.G2_LOCKER, AppObjInfo.OBJ_LOCKER); %>
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

    SrcLocker srcLocker = new SrcLocker();
    FrmSrcLocker frmSrcLocker = new FrmSrcLocker();
    //FrmSrcLocker frmSrcLocker = new FrmSrcLocker(request, srcLocker);
    //frmSrcLocker.requestEntityObject(srcLocker);
    //if((iCommand==Command.NEXT)||(iCommand==Command.FIRST)||(iCommand==Command.PREV)||(iCommand==Command.LAST))
    if(iCommand==Command.BACK)
    {
        //FrmSrcLocker 
        frmSrcLocker = new FrmSrcLocker(request, srcLocker);
        frmSrcLocker.requestEntityObject(srcLocker);
        try
        {
            srcLocker = (SrcLocker)session.getValue(SessLocker.SESS_SRC_LOCKER); 
            System.out.println("\t session.getValue : done");
        }
        catch (Exception e)
        {
            srcLocker = new SrcLocker();
            System.out.println("\t session.getValue : exeception");
        }
    }

    try{
        session.removeValue(SessLocker.SESS_SRC_LOCKER);
        System.out.println("\t srclocker.jsp - Remove session 'SessLocker.SESS_SRC_LOCKER' : done ---");
    }
    catch(Exception e){
        System.out.println("\t srclocker.jsp - Remove session 'SessLocker.SESS_SRC_LOCKER' : error ---");
    }

/*
int iCommand = FRMQueryString.requestCommand(request);

 SrcLocker srcLocker = new SrcLocker();
 FrmSrcLocker frmSrcLocker = new FrmSrcLocker();
try{
	srcLocker = (SrcLocker)session.getValue(SessLocker.SESS_SRC_LOCKER);
}catch(Exception e){
	srcLocker = new SrcLocker();
}

try{
	session.removeValue(SessLocker.SESS_SRC_LOCKER);
}catch(Exception e){
}
*/
%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Harisma - Locker</title>
<script language="JavaScript">

function cmdAdd(){
	document.frmsrclocker.command.value="<%=Command.ADD%>";
	document.frmsrclocker.action="locker_edit.jsp";
	document.frmsrclocker.submit();
}

function cmdSearch(){
	document.frmsrclocker.command.value="<%=Command.LIST%>";
	document.frmsrclocker.action="locker_list.jsp";
	document.frmsrclocker.submit();
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
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../styletemplate/template_header.jsp" %>
            <%}else{%>
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
  <%}%>
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr> 
          <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> 
                  Locker &gt; Locker Search<!-- #EndEditable --> </strong></font> 
                </td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td  style="background-color:<%=bgColorContent%>; "> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr> 
                            <td valign="top"> 
                              <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                <tr> 
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="frmsrclocker" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <table width="100%" border="0" cellspacing="1" cellpadding="1">
                                        <tr align="left" valign="top">
                                          <td height="21" valign="top" width="3%">&nbsp;</td>
                                          <td height="21" valign="top" width="10%">&nbsp;</td>
                                          <td height="21" width="87%">&nbsp;</td>
										 </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="21" valign="top" width="3%">&nbsp;</td>
                                          <td height="21" valign="top" width="10%">Location</td>
                                          <td height="21" width="87%"> 
                                            <%
                                                Vector locationid_value = new Vector(1,1); 
                                                Vector locationid_key = new Vector(1,1); 
                                                locationid_key.add("All Location");
                                                locationid_value.add("0");
                                                    String selectVal = String.valueOf(srcLocker.getLocation()); 
                                                    Vector listLockerLocation = new Vector(1,1);
                                                    listLockerLocation = PstLockerLocation.listAll();
                                                    for (int i = 0; i < listLockerLocation.size(); i++) {
                                                        LockerLocation lockerLocation = (LockerLocation) listLockerLocation.get(i);
                                                        locationid_key.add(lockerLocation.getLocation());
                                                        locationid_value.add(String.valueOf(lockerLocation.getOID()));
                                                    }
                                          %>
                                            <%= ControlCombo.draw(frmSrcLocker.fieldNames[FrmSrcLocker.FRM_FIELD_LOCATION], null, selectVal, locationid_value, locationid_key) %> 
                                                </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="21" valign="top" width="3%">&nbsp;</td>
                                          <td height="21" valign="top" width="10%">Locker 
                                            Number</td>
                                          <td height="21" width="87%"> 
                                            <input type="text" name="<%=frmSrcLocker.fieldNames[FrmSrcLocker.FRM_FIELD_LOCKERNUMBER] %>"  value="<%= srcLocker.getLockernumber() %>" class="elemenForm">
                                                </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="21" valign="top" width="3%">&nbsp;</td>
                                          <td height="21" valign="top" width="10%">Locker 
                                            Key</td>
                                          <td height="21" width="87%"> 
                                            <input type="text" name="<%=frmSrcLocker.fieldNames[FrmSrcLocker.FRM_FIELD_LOCKERKEY] %>"  value="<%= srcLocker.getLockerkey() %>" class="elemenForm">
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="21" valign="top" width="3%">&nbsp;</td>
                                          <td height="21" valign="top" width="10%">Spare 
                                            Key</td>
                                          <td height="21" width="87%"> 
                                            <input type="text" name="<%=frmSrcLocker.fieldNames[FrmSrcLocker.FRM_FIELD_SPAREKEY] %>"  value="<%= (srcLocker.getSparekey() == null) ? "" : srcLocker.getSparekey() %>" class="elemenForm">
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="21" valign="top" width="3%">&nbsp;</td>
                                          <td height="21" valign="top" width="10%">Condition</td>
                                          <td height="21" width="87%"> 
                                            <%-- <input type="text" name="<%=frmSrcLocker.fieldNames[FrmSrcLocker.FRM_FIELD_CONDITION] %>"  value="<%= srcLocker.getCondition() %>" class="elemenForm">
                                            --%>
                                            <%
                                                    Vector conditionid_value = new Vector(1,1); 
                                                    Vector conditionid_key = new Vector(1,1); 
                                                    conditionid_key.add("All Condition");
                                                    conditionid_value.add("-1");
                                                    String selectVal2 = "-1";//String.valueOf(srcLocker.getCondition()); 
                                                    Vector listLockerCondition = new Vector(1,1);
                                                    listLockerCondition = PstLockerCondition.listAll();
                                                    for (int i = 0; i < listLockerCondition.size(); i++) {
                                                        LockerCondition lockerCondition = (LockerCondition) listLockerCondition.get(i);
                                                        conditionid_key.add(lockerCondition.getCondition());
                                                        conditionid_value.add(String.valueOf(lockerCondition.getOID()));
                                                    }
                                              %>
                                            <%= ControlCombo.draw(frmSrcLocker.fieldNames[FrmSrcLocker.FRM_FIELD_CONDITION], null, selectVal2, conditionid_value, conditionid_key) %> 
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td height="21" valign="top" width="3%">&nbsp;</td>
                                          <td height="21" valign="top" width="10%">&nbsp;</td>
                                          <td width="87%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td height="21" valign="top" width="3%">&nbsp;</td>
                                          <td height="21" valign="top" width="10%">&nbsp;</td>
                                          <td width="87%"> 
                                            <table border="0" cellpadding="0" cellspacing="0">
                                              <tr> 
                                                <td width="8"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Locker"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="2" height="8"></td>
                                                <td width="110" class="command" nowrap><a href="javascript:cmdSearch()">Search 
                                                  for Locker</a></td>
                                                <% if(privAdd){%>
                                                <td width="12"><img src="<%=approot%>/images/spacer.gif" width="6" height="8"></td>
                                                <td width="8"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New Locker"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="6" height="8"></td>
                                                <td width="100" class="command" nowrap><a href="javascript:cmdAdd()">Add 
                                                  New Locker</a></td>
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
                                <%@include file="../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
