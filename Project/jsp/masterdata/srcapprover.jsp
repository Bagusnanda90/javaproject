
<%@page import="com.dimata.harisma.entity.attendance.I_Atendance"%>
<% 
/* 
 * Page Name  		:  position.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		: karya 
 * @version  		: 01 local
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
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_POSITION); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%!    
    public String drawList(Vector objectClass, long positionId, I_Atendance attdConfig) {
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("tblStyle");
        ctrlist.setTitleStyle("title_tbl");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("title_tbl");
            
        ctrlist.setCellSpacing("0");
        ctrlist.addHeader("Position", "");
            
        if (attdConfig != null && attdConfig.getConfigurationShowPositionCode()) {
            ctrlist.addHeader("Position Code", "");
        }
        ctrlist.addHeader("Type", "");
            
        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdChoose('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        int index = -1;
            
        for (int i = 0; i < objectClass.size(); i++) {
            Position position = (Position) objectClass.get(i);
            Vector rowx = new Vector();
            if (positionId == position.getOID()) {
                index = i;
            }
                
            rowx.add(position.getPosition());
            if (attdConfig != null && attdConfig.getConfigurationShowPositionCode()) {
                rowx.add(position.getKodePosition());
            }
            rowx.add("" + PstPosition.strPositionLevelNames[position.getPositionLevel()]);
                
            lstData.add(rowx);
            lstLinkData.add(String.valueOf(position.getOID()));
        }
        return ctrlist.draw(index);
    }

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
/* update variable by Hendra Putu | 2015-08-02 */
int customCommand = FRMQueryString.requestInt(request, "custom_command");
String searchPosition = FRMQueryString.requestString(request, "search_position");
int askCommand = FRMQueryString.requestInt(request, "ask_command");

int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidPosition = FRMQueryString.requestLong(request, "hidden_position_id");

String comm = request.getParameter("comm");

String orderBy = "";

long oidCom = 0;
long oidTrain = 0;
long oidEdu = 0;
long oidKp = 0;
    I_Atendance attdConfig = null;
    try {
        attdConfig = (I_Atendance) (Class.forName(PstSystemProperty.getValueByName("ATTENDANCE_CONFIG")).newInstance());
    } catch (Exception e) {
        System.out.println("Exception : " + e.getMessage());
        System.out.println("Please contact your system administration to setup system property: ATTENDANCE_CONFIG ");
    }
/*variable declaration*/
int recordToGet = 30;
String msgString = "";
int iErrCode = FRMMessage.NONE;
int iErrCodePosKpi = FRMMessage.NONE;

CtrlPosition ctrlPosition = new CtrlPosition(request);
CtrlPositionKPI ctrlPositionKpi = new CtrlPositionKPI(request);
ControlLine ctrLine = new ControlLine();  
Vector listPosition = new Vector(1,1);

/* end switch*/
FrmPosition frmPosition = ctrlPosition.getForm();

SrcPosition srcPosition = new SrcPosition();
FrmSrcPosition frmSrcPosition  = new FrmSrcPosition(request, srcPosition);
if(iCommand==Command.FIRST || iCommand==Command.PREV || iCommand==Command.NEXT || iCommand==Command.LAST || iCommand==Command.BACK || iCommand==Command.ASK
   || iCommand==Command.EDIT || iCommand==Command.ADD || iCommand==Command.DELETE || (iCommand==Command.SAVE && frmPosition.errorSize()==0) )
{
    try
    { 
           srcPosition = (SrcPosition)session.getValue(PstPosition.SESS_HR_POSITION); 
    }
    catch(Exception e)
    { 
           srcPosition = new SrcPosition();
    }
}
else
{
    frmSrcPosition.requestEntityObject(srcPosition);
    session.putValue(PstPosition.SESS_HR_POSITION, srcPosition);	
}
/* Code is modified by Hendra Putu | 2015-08-16 */
String whereClause = "";
if (srcPosition.getStartDate().length() > 0 && srcPosition.getEndDate().length() > 0){
    whereClause = PstPosition.fieldNames[PstPosition.FLD_VALID_START]+" BETWEEN '"+srcPosition.getStartDate()+"' AND '"+srcPosition.getEndDate()+"'";
}
if (srcPosition.getPosName() != null && srcPosition.getPosName().length() > 0){
   if (srcPosition.getStartDate().length() > 0 && srcPosition.getEndDate().length() > 0){
       whereClause += " AND ";
   } 
   whereClause += PstPosition.fieldNames[PstPosition.FLD_POSITION] + " LIKE '%" + srcPosition.getPosName() + "%'";
}
if (srcPosition.getPosLevel() >=0 ){
    if ((srcPosition.getStartDate().length() > 0 && srcPosition.getEndDate().length() > 0)||(srcPosition.getPosName() != null && srcPosition.getPosName().length() > 0)){
        whereClause += " AND ";
    }
    whereClause += PstPosition.fieldNames[PstPosition.FLD_POSITION_LEVEL] + " = " + srcPosition.getPosLevel();
}
if (srcPosition.getLevelRankID() > 0){
    if ((srcPosition.getStartDate().length() > 0 && srcPosition.getEndDate().length() > 0)||(srcPosition.getPosName() != null && srcPosition.getPosName().length() > 0)){
        whereClause += " AND ";
    }
    whereClause += PstPosition.fieldNames[PstPosition.FLD_LEVEL_ID] + " = " + srcPosition.getLevelRankID();
}


String orderClause = " POSITION ";

/*count list All Position*/
int vectSize = PstPosition.getCount(whereClause);

Position position = ctrlPosition.getPosition();
msgString =  ctrlPosition.getMessage();

/*switch list Position*/
if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)){
	//start = PstPosition.findLimitStart(position.getOID(),recordToGet, whereClause);
	oidPosition = position.getOID();
}

if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlPosition.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

if (customCommand == 7){
    whereClause = PstPosition.fieldNames[PstPosition.FLD_POSITION]+" LIKE '%"+searchPosition+"%'";
    orderClause = PstPosition.fieldNames[PstPosition.FLD_POSITION]+" ASC";
}
/* get record to display */
listPosition = PstPosition.list(start, recordToGet, whereClause , orderClause);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listPosition.size() < 1 && start > 0)
{
    if (vectSize - recordToGet > recordToGet)
                   start = start - recordToGet;   //go to Command.PREV
    else{
            start = 0 ;
            iCommand = Command.FIRST;
            prevCommand = Command.FIRST; //go to Command.FIRST
    }
    listPosition = PstPosition.list(start,recordToGet, whereClause , orderClause);
}

%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Master Data Position</title>
<script language="JavaScript">
function cmdChoose(posId) {
    self.opener.document.frmjobdesc.approve_pos_id.value = posId;
    self.opener.document.frmjobdesc.command.value = "<%=comm%>";                 
    //self.close();
    self.opener.document.frmjobdesc.submit();
    windows.close();
}
function cmdSearchPosition(){
    document.frmposition.custom_command.value="7";
    document.frmposition.action="srcapprover.jsp";
    document.frmposition.submit();
}

function cmdGoToForm(){
    document.frmposition.action="position_form.jsp";
    document.frmposition.submit(); 
}

function cmdAsk(oidPosition){
    document.frmposition.hidden_position_id.value=oidPosition;
    document.frmposition.command.value="<%=Command.ASK%>";
    document.frmposition.prev_command.value="<%=prevCommand%>";
    document.frmposition.action="srcapprover.jsp";
    document.frmposition.submit();
}

function cmdBackConfirm(){
    document.frmposition.delete_command.value="0";
    document.frmposition.ask_command.value="0";
    document.frmposition.action="srcapprover.jsp";
    document.frmposition.submit();
}

function cmdBack(){
    document.frmposition.command.value="<%=Command.BACK%>";
    document.frmposition.action="srcapprover.jsp";
    document.frmposition.submit();
}

function cmdListFirst(){
    document.frmposition.command.value="<%=Command.FIRST%>";
    document.frmposition.prev_command.value="<%=Command.FIRST%>";
    document.frmposition.action="srcapprover.jsp";
    document.frmposition.submit();
}

function cmdListPrev(){
    document.frmposition.command.value="<%=Command.PREV%>";
    document.frmposition.prev_command.value="<%=Command.PREV%>";
    document.frmposition.action="srcapprover.jsp";
    document.frmposition.submit();
}

function cmdListNext(){
    document.frmposition.command.value="<%=Command.NEXT%>";
    document.frmposition.prev_command.value="<%=Command.NEXT%>";
    document.frmposition.action="srcapprover.jsp";
    document.frmposition.submit();
}

function cmdListLast(){
    document.frmposition.command.value="<%=Command.LAST%>";
    document.frmposition.prev_command.value="<%=Command.LAST%>";
    document.frmposition.action="srcapprover.jsp";
    document.frmposition.submit();
}

function fnTrapKD(){	
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
<link rel="stylesheet" href="../styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../styles/tab.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "headerscript" --> 


                            
<script type="text/javascript">

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
<style type="text/css">
    #listPos {background-color: #FFF; border: 1px solid #CCC; padding: 3px 9px; cursor: pointer; margin: 1px 0px;}  
    #btn {
        padding: 3px; border: 1px solid #CCC; 
        background-color: #EEE; color: #777777; 
        font-size: 11px; cursor: pointer;
    }
    #btn:hover {border: 1px solid #999; background-color: #CCC; color: #FFF;}
    .tblStyle {border-collapse: collapse;font-size: 11px;}
    .tblStyle td {padding: 3px 5px; border: 1px solid #CCC;}
    .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
    .title_content {
        padding: 9px 14px; 
        border-left: 1px solid #0099FF; 
        font-size: 14px; 
        background-color: #F3F3F3; 
        color:#0099FF;
        font-weight: bold;
    }
    .title_part {
        padding: 9px 14px; 
        border-left: 1px solid #0099FF; 
        font-size: 12px; 
        background-color: #F3F3F3; 
        color:#333; 
        font-weight: bold;
    }
    .part_content {
        border:1px solid #0099FF;
        border-radius: 5px;
        background-color: #F5F5F5;
    }
    .part_name {
        padding: 12px 19px; border-bottom: 1px solid #0099FF;
        border-top-left-radius: 5px;
        border-top-right-radius: 5px;
        background-color: #a9d5f2;
        color:#04619e;
        font-weight: bold;
        font-size: 12px;
    }
    #confirm {background-color: #fad9d9;border: 1px solid #da8383; color: #bf3c3c; padding: 14px 21px;border-radius: 5px;}
    #btn1 {
        background: #f27979;
        border: 1px solid #d74e4e;
        border-radius: 3px;
        font-family: Arial;
        color: #ffffff;
        font-size: 12px;
        padding: 3px 9px 3px 9px;
    }

    #btn1:hover {
        background: #d22a2a;
        border: 1px solid #c31b1b;
    }
</style>
<!-- #EndEditable -->
<link rel="stylesheet" href="<%=approot%>/javascripts/datepicker/themes/jquery.ui.all.css">
<script src="<%=approot%>/javascripts/jquery.js"></script>
<script src="<%=approot%>/javascripts/datepicker/jquery.ui.core.js"></script>
<script src="<%=approot%>/javascripts/datepicker/jquery.ui.widget.js"></script>
<script src="<%=approot%>/javascripts/datepicker/jquery.ui.datepicker.js"></script>
<script>
$(function() {
    $( "#datepicker1" ).datepicker({ dateFormat: "yy-mm-dd" });
    $( "#datepicker2" ).datepicker({ dateFormat: "yy-mm-dd" });
});
</script>
</head> 

<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2"> 
        <tr> 
          <td width="100%">
      <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td height="20">
            <div class="title_content">Master Data / Position</div>
	  </td>
        </tr>
        <tr> 
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td  style="background-color:<%=bgColorContent%>; "> 
                  <table width="100%" border="0"  >
                    <tr> 
                      <td valign="top"> 
                        <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr> 
                            <td valign="top">
		    				  <!-- #BeginEditable "content" --> 
                                    <form name="frmposition" method ="post" action="">
                                      <input type="hidden" name="custom_command" value="<%=customCommand%>">
                                      <input type="hidden" name="ask_command" value="<%=askCommand%>">
                                      <input type="hidden" name="position_map_id" value="<%=customCommand%>">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="hidden_position_id" value="<%=oidPosition%>">
                                      
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="listtitle">
                                                    <div class="title_part">
                                                        Position List&nbsp;
                                                        <input type="text" name="search_position" value="<%=searchPosition%>" placeholder="cari posisi..." size="50" />
                                                        &nbsp;<button id="btn" onclick="cmdSearchPosition()">cari</button>
                                                    </div>
                                                </td>
                                              </tr>
                                              <%
											  if (listPosition.size()>0)
											  {
											  %>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <%= drawList(listPosition,oidPosition,attdConfig)%> 
                                                </td>
                                              </tr>
                                              <%  
											  }
											  else
											  { 
											  %>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3" class="errfont">There 
                                                  is no position data found ...</td>
                                              </tr>											  
											  <%
											  }
											  %>
                                              <tr align="left" valign="top"> 
                                                <td height="8" align="left" colspan="3" class="command" valign="top"> 
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
									  	cmd =prevCommand; 
								   } 
							    %>
                                                  <% ctrLine.setLocationImg(approot+"/images");
							   	ctrLine.initDefault();
								 %>
                                                  <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> 
                                                  </span> </td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                      </table>
                                    </form>
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
<script language="JavaScript">
	//var oBody = document.body;
	//var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
</script>
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
