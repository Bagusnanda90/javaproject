
<% 
/* 
 * Page Name  		:  dayofpayment_list.jsp
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
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.form.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% //int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_SECRETARY , AppObjInfo.G2_SECRETARY_GENERAL   , AppObjInfo.OBJ_SG_LEAVE_DP   	); %>
<% //int  appObjCodeSpec = AppObjInfo.composeObjCode(AppObjInfo.G1_SECRETARY , AppObjInfo.G2_SECRETARY_SPECIFIC   , AppObjInfo.OBJ_SS_LEAVE_DP   	); %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE , AppObjInfo.G2_ATTENDANCE   , AppObjInfo.OBJ_DAY_OFF_PAYMENT   	); %>
<% int  appObjCodeSpec = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE , AppObjInfo.G2_ATTENDANCE   , AppObjInfo.OBJ_LEAVE_MANAGEMENT   	); %>

<%@ include file = "../../main/checkuser.jsp" %>
<%
    /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//	boolean privStart=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
//    boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
    //boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
    //boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
	
	boolean privStartSpec=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeSpec, AppObjInfo.COMMAND_VIEW));
    boolean privAddSpec=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeSpec, AppObjInfo.COMMAND_ADD));

	
	//out.println("privStart : "+privStart+", privAdd : "+privAdd+", privUpdate : "+privUpdate+", privDelete : "+privDelete);
	
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
        ctrlist.addHeader("Employee Name","14%", "2", "0");
        ctrlist.addHeader("Duration","5%", "2", "0");
        ctrlist.addHeader("Day off Payment","14%", "0", "2");
        ctrlist.addHeader("From","10%", "0", "0");
        ctrlist.addHeader("To","10%", "0", "0");
        ctrlist.addHeader("Approved","10%", "2", "0");
        ctrlist.addHeader("Contact Address","20%", "2", "0");
        ctrlist.addHeader("Remarks","20%", "2", "0");

        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();

        for (int i = 0; i < objectClass.size(); i++) {
            //DayOfPayment dayOfPayment = (DayOfPayment)objectClass.get(i);
            Vector temp = (Vector) objectClass.get(i);
            DayOfPayment dayOfPayment = (DayOfPayment) temp.get(0);
            Employee employee = (Employee) temp.get(1);

            Vector rowx = new Vector();
            //rowx.add(String.valueOf(dayOfPayment.getEmployeeId()));
            rowx.add(employee.getFullName());
            rowx.add(String.valueOf(dayOfPayment.getDuration()));

            String str_dt_DpFrom = ""; 
            try{
                Date dt_DpFrom = dayOfPayment.getDpFrom();
                if(dt_DpFrom==null){
                    dt_DpFrom = new Date();
                }
                str_dt_DpFrom = Formater.formatDate(dt_DpFrom, "dd MMMM yyyy");
            }catch(Exception e){ str_dt_DpFrom = ""; }

            rowx.add(str_dt_DpFrom);

            String str_dt_DpTo = ""; 
            try{
                Date dt_DpTo = dayOfPayment.getDpTo();
                if(dt_DpTo==null){
                    dt_DpTo = new Date();
                }
                str_dt_DpTo = Formater.formatDate(dt_DpTo, "dd MMMM yyyy");
            }catch(Exception e){ str_dt_DpTo = ""; }

            rowx.add(str_dt_DpTo);

            String str_dt_AprDeptheadDate = ""; 
            try{
                Date dt_AprDeptheadDate = dayOfPayment.getAprDeptheadDate();
                if(dt_AprDeptheadDate==null){
                    dt_AprDeptheadDate = new Date();
                }
                str_dt_AprDeptheadDate = Formater.formatDate(dt_AprDeptheadDate, "dd MMMM yyyy");
            }catch(Exception e){ str_dt_AprDeptheadDate = ""; }

            rowx.add(str_dt_AprDeptheadDate);
            rowx.add(dayOfPayment.getContactAddress());
            rowx.add(dayOfPayment.getRemarks());
            lstData.add(rowx);
            lstLinkData.add(String.valueOf(dayOfPayment.getOID()));
        }
        return ctrlist.drawList();
    }

%>
<%
    ControlLine ctrLine = new ControlLine();
    CtrlDayOfPayment ctrlDayOfPayment = new CtrlDayOfPayment(request);
    long oidDayOfPayment = FRMQueryString.requestLong(request, "hidden_day_of_payment_id");

    int iErrCode = FRMMessage.ERR_NONE;
    String msgStr = "";
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int recordToGet = 5;
    int vectSize = 0;
    String whereClause = "";

    //out.println("iCommand : "+iCommand);
    //out.println("<br>start : "+start);
    //out.println("<br>recordToGet : "+recordToGet);

    SrcDayOfPayment srcDayOfPayment = new SrcDayOfPayment();
    FrmSrcDayOfPayment frmSrcDayOfPayment = new FrmSrcDayOfPayment(request, srcDayOfPayment);
    frmSrcDayOfPayment.requestEntityObject(srcDayOfPayment);
    if((iCommand==Command.NEXT)||(iCommand==Command.FIRST)||(iCommand==Command.PREV)||(iCommand==Command.LAST)){
        try{ 
            srcDayOfPayment = (SrcDayOfPayment)session.getValue(SessDayOfPayment.SESS_SRC_DAYOFPAYMENT); 
            if (srcDayOfPayment == null) {
                srcDayOfPayment = new SrcDayOfPayment();
            }
        }catch(Exception e){ 
            srcDayOfPayment = new SrcDayOfPayment();
        }
    }

    SessDayOfPayment sessDayOfPayment = new SessDayOfPayment();
    session.putValue(SessDayOfPayment.SESS_SRC_DAYOFPAYMENT, srcDayOfPayment);
    vectSize = sessDayOfPayment.getCountSearch(srcDayOfPayment);

    //out.println("vectSize : "+vectSize);
    ctrlDayOfPayment.action(iCommand , oidDayOfPayment);
    if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)||(iCommand==Command.LAST)||(iCommand==Command.LIST))
            start = ctrlDayOfPayment.actionList(iCommand, start, vectSize, recordToGet);
    Vector records = sessDayOfPayment.searchDayOfPayment(srcDayOfPayment, start, recordToGet);
%>
<!-- End of Jsp Block -->
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Day Off Payment List</title>
<script language="JavaScript">

    function cmdAdd(){
            document.frm_dayofpayment.command.value="<%=Command.ADD%>";
            document.frm_dayofpayment.action="dayofpayment_edit.jsp";
            document.frm_dayofpayment.submit();
    }

    function cmdEdit(oid){
            document.frm_dayofpayment.command.value="<%=Command.EDIT%>";
            document.frm_dayofpayment.hidden_day_of_payment_id.value = oid;
            document.frm_dayofpayment.action="dayofpayment_edit.jsp";
            document.frm_dayofpayment.submit();
    }

    function cmdListFirst(){
            document.frm_dayofpayment.command.value="<%=Command.FIRST%>";
            document.frm_dayofpayment.action="dayofpayment_list.jsp";
            document.frm_dayofpayment.submit();
    }

    function cmdListPrev(){
            document.frm_dayofpayment.command.value="<%=Command.PREV%>";
            document.frm_dayofpayment.action="dayofpayment_list.jsp";
            document.frm_dayofpayment.submit();
    }

    function cmdListNext(){
            document.frm_dayofpayment.command.value="<%=Command.NEXT%>";
            document.frm_dayofpayment.action="dayofpayment_list.jsp";
            document.frm_dayofpayment.submit();
    }

    function cmdListLast(){
            document.frm_dayofpayment.command.value="<%=Command.LAST%>";
            document.frm_dayofpayment.action="dayofpayment_list.jsp";
            document.frm_dayofpayment.submit();
    }

    function cmdBack(){
            document.frm_dayofpayment.command.value="<%=Command.BACK%>";
            document.frm_dayofpayment.action="srcdayofpayment.jsp";
            document.frm_dayofpayment.submit();
    }
	
	function fnTrapKD(){
	//alert(event.keyCode);
	switch(event.keyCode) {
		case <%=LIST_PREV%>:
			cmdListPrev();
			break;
		case <%=LIST_NEXT%>:
			cmdListNext();
			break;
		case <%=LIST_FIRST%>:
			cmdListFirst();
			break;
		case <%=LIST_LAST%>:
			cmdListLast();
			break;
		default:
			break;
	}
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
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "headerscript" --> 
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

</SCRIPT>
<!-- #EndEditable -->
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnBackOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../../main/header.jsp" %>
      <!-- #EndEditable --> 
    </td>
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
          <td height="20">
		    <font color="#FF6600" face="Arial"><strong>
			  <!-- #BeginEditable "contenttitle" --> 
                  Employee &gt; Attendance &gt; Day off Payment<!-- #EndEditable --> 
            </strong></font>
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
                            <td valign="top">
		    				  <!-- #BeginEditable "content" --> 
							  <%if(privStart || privStartSpec){%>
                                    <form name="frm_dayofpayment" method="post" action="">
                                      <input type="hidden" name="command" value="">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="hidden_day_of_payment_id" value="<%=oidDayOfPayment%>">
                                      <table border="0" width="100%">
                                        <tr> 
                                          <td height="8" width="100%" class="listtitle">Day 
                                            off Payment List</td>
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
                                                  <% ctrLine.setLocationImg(approot+"/images/ctr_line");
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
                                          <td width="46%" nowrap align="left" class="command">
										  	<%-- &nbsp; <a href="javascript:cmdAdd()">Add New</a> | <a href="javascript:cmdBack()">Back 
                                            to search</a> </td> --%>
                                            <table width="60" border="0" cellspacing="0" cellpadding="0">
                                              <tr> 
                                                <td width="24"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image300','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image300" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back To List"></a></td>
												<td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="190" nowrap> <a href="javascript:cmdBack()" class="command">Back 
                                                  To Search Day Off Payment</a></td>
												  <%if(privAdd || privAddSpec){%>
												<td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1001','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1001" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New"></a></td>
												<td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="190" nowrap><b><a href="javascript:cmdAdd()" class="command">Add 
                                                  New Day off Payment</a></b></td>
												  <% }
												  if(privStart || privStartSpec){ %><%--
                                                <td width="15"><a href="javascript:cmdPrint()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print"></a></td>
												<td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="230"><b><a href="javascript:cmdPrint()" class="command">Print Day Off Payment</a></b></td>--%>
                                              	<% }%>
											  </tr>
                                            </table>
                                        </tr>
                                      </table>
                                    </form> <%}else{%>
									<div align="center">You do not have sufficient privilege to access this page.</div>
									<%}%>
                                    <!-- #EndEditable -->
                            </td>
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
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<script language="JavaScript">
	var oBody = document.body;
	var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
</script>
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
