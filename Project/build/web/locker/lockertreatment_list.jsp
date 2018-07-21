
<% 
/* 
 * Page Name  		:  lockertreatment_list.jsp
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
<%@ page import = "com.dimata.harisma.entity.locker.*" %>
<%@ page import = "com.dimata.harisma.form.locker.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.session.locker.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ include file = "../main/javainit.jsp" %>

<%// int  appObjCode = 1;// AppObjInfo.composeObjCode(AppObjInfo.--, AppObjInfo.--, AppObjInfo.--); %>
<%//@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>

<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_LOCKER, AppObjInfo.G2_LOCKER, AppObjInfo.OBJ_LOCKER_TREATMENT); %>
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
<%!
    public String drawList(Vector objectClass ){
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("Location","33%");
        ctrlist.addHeader("Treatment Date","33%");
        ctrlist.addHeader("Treatment","33%");

        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();

        for (int i = 0; i < objectClass.size(); i++) {
            Vector vect = (Vector)objectClass.get(i);
            LockerTreatment lockerTreatment = (LockerTreatment) vect.get(0);
            LockerLocation lockerlocation = (LockerLocation) vect.get(1);
            Vector rowx = new Vector();
            rowx.add(lockerlocation.getLocation());
            String str_dt_TreatmentDate = ""; 
            try {
                Date dt_TreatmentDate = lockerTreatment.getTreatmentDate();
                if(dt_TreatmentDate==null){
                    dt_TreatmentDate = new Date();
                }
                str_dt_TreatmentDate = Formater.formatDate(dt_TreatmentDate, "dd MMMM yyyy");
            }
            catch(Exception e) { str_dt_TreatmentDate = ""; }
            rowx.add(str_dt_TreatmentDate);
            rowx.add(lockerTreatment.getTreatment());
            lstData.add(rowx);
            lstLinkData.add(String.valueOf(lockerTreatment.getOID()));
        }
        return ctrlist.draw();
    }
%>
<%
    ControlLine ctrLine = new ControlLine();
    CtrlLockerTreatment ctrlLockerTreatment = new CtrlLockerTreatment(request);
    long oidLockerTreatment = FRMQueryString.requestLong(request, "hidden_treatment_id");

    int iErrCode = FRMMessage.ERR_NONE;
    String msgStr = "";
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int recordToGet = 10;
    int vectSize = 0;
    String whereClause = "";

    //out.println("iCommand : "+iCommand);
    //out.println("<br>start : "+start);
    //out.println("<br>recordToGet : "+recordToGet);

    SrcLockerTreatment srcLockerTreatment = new SrcLockerTreatment();
    FrmSrcLockerTreatment frmSrcLockerTreatment = new FrmSrcLockerTreatment(request, srcLockerTreatment);
    frmSrcLockerTreatment.requestEntityObject(srcLockerTreatment);
    if((iCommand==Command.NEXT)||(iCommand==Command.FIRST)||(iCommand==Command.PREV)||(iCommand==Command.LAST)){
        try{ 
            srcLockerTreatment = (SrcLockerTreatment)session.getValue(SessLockerTreatment.SESS_SRC_LOCKERTREATMENT); 
        }catch(Exception e){ 
            srcLockerTreatment = new SrcLockerTreatment();
        }
     }

    SessLockerTreatment sessLockerTreatment = new SessLockerTreatment();
    session.putValue(SessLockerTreatment.SESS_SRC_LOCKERTREATMENT, srcLockerTreatment);
    vectSize = sessLockerTreatment.getCountSearch(srcLockerTreatment);
    //out.println("vectSize : "+vectSize);
    ctrlLockerTreatment.action(iCommand , oidLockerTreatment);
    if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)||(iCommand==Command.LAST)||(iCommand==Command.LIST))
        start = ctrlLockerTreatment.actionList(iCommand, start, vectSize, recordToGet);
    Vector records = sessLockerTreatment.searchLockerTreatment(srcLockerTreatment, start, recordToGet);
%>
<!-- End of Jsp Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Locker Treatment</title>
<script language="JavaScript">
    function cmdAdd(){
        document.frm_lockertreatment.command.value="<%=Command.ADD%>";
        document.frm_lockertreatment.action="lockertreatment_edit.jsp";
        document.frm_lockertreatment.submit();
    }

    function cmdEdit(oid){
        document.frm_lockertreatment.hidden_treatment_id.value = oid;
        document.frm_lockertreatment.command.value="<%=Command.EDIT%>";
        document.frm_lockertreatment.action="lockertreatment_edit.jsp";
        document.frm_lockertreatment.submit();
    }

    function cmdListFirst(){
        document.frm_lockertreatment.command.value="<%=Command.FIRST%>";
        document.frm_lockertreatment.action="lockertreatment_list.jsp";
        document.frm_lockertreatment.submit();
    }

    function cmdListPrev(){
        document.frm_lockertreatment.command.value="<%=Command.PREV%>";
        document.frm_lockertreatment.action="lockertreatment_list.jsp";
        document.frm_lockertreatment.submit();
    }

    function cmdListNext(){
        document.frm_lockertreatment.command.value="<%=Command.NEXT%>";
        document.frm_lockertreatment.action="lockertreatment_list.jsp";
        document.frm_lockertreatment.submit();
    }

    function cmdListLast(){
        document.frm_lockertreatment.command.value="<%=Command.LAST%>";
        document.frm_lockertreatment.action="lockertreatment_list.jsp";
        document.frm_lockertreatment.submit();
    }

    function cmdBack(){
        document.frm_lockertreatment.command.value="<%=Command.BACK%>";
        document.frm_lockertreatment.action="srclockertreatment.jsp";
        document.frm_lockertreatment.submit();
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
           <%@include file="../../styletemplate/template_header.jsp" %>
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
                  Locker &gt; Locker Treatment<!-- #EndEditable --> </strong></font> 
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
                                    <form name="frm_lockertreatment" method="post" action="">
                                      <input type="hidden" name="command" value="">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="hidden_treatment_id" value="<%=oidLockerTreatment%>">
                                      <table border="0" width="100%">
                                        <tr> 
                                          <td height="8" width="100%" class="comment">Locker 
                                            Treatment List</td>
                                        </tr>
                                      </table>
                                      <%if((records!=null)&&(records.size()>0)){%>
                                      <%=drawList(records)%> 
                                      <%}
					else{
					%>
                                      <span class="comment"><br>
                                      &nbsp;Records is empty ...</span> 
                                      <%}%>
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                          <td> 
                                            <table width="100%" cellspacing="0" cellpadding="3">
                                              <tr> 
                                                <td> 
                                                  <% ctrLine.setLocationImg(approot+"/images");
                                                        ctrLine.initDefault();
                                                %>
                                                  <%=ctrLine.drawImageListLimit(iCommand,vectSize,start,recordToGet)%></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="46%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <!--
                                          <td width="46%" nowrap align="left" class="command">&nbsp; 
                                            <a href="javascript:cmdAdd()">Add 
                                            New</a> | <a href="javascript:cmdBack()">Back 
                                            to search</a> </td>
                                            -->
                                            <td>
                                            <table width="60" border="0" cellspacing="0" cellpadding="0">
                                              <tr> 
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="24"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image300','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image300" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back To List"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="210" nowrap> <a href="javascript:cmdBack()" class="command">Back 
                                                  To Search Locker Treatment</a></td>
												  <%if(privAdd){%>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1001','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1001" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="169" nowrap><b><a href="javascript:cmdAdd()" class="command">Add 
                                                  New Locker Treatment</a></b></td>
												  <%}%>
                                                  <%--
                                                <td width="15"><a href="javascript:cmdPrint()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="230"><b><a href="javascript:cmdPrint()" class="command">Print</a></b></td>
                                                --%>
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
