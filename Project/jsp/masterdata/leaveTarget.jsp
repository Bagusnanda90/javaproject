<% 
/* 
 * Page Name  		:  position.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		: gadnyana
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
<%@ page import = "java.text.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.session.leave.SessLeaveApp" %>

<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_DIVISION); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%!

	public String drawList(Vector objectClass, long targetId, int start){
            int installInterval = 10;
            
            ControlList ctrlist = new ControlList();
            ctrlist.setAreaWidth("100%");
            ctrlist.setListStyle("listgen");
            ctrlist.setTitleStyle("listgentitle");
            ctrlist.setCellStyle("listgensell");
            ctrlist.setHeaderStyle("listgentitle");
            ctrlist.addHeader("No","10%","2","0");
            ctrlist.addHeader("Target Date","30%","2","0");
            ctrlist.addHeader("Target","68%","0","3");
            ctrlist.addHeader("<center>DP</center>",""+(68/3)+"%","0","0");
            ctrlist.addHeader("<center>AL</center>",""+(68/3)+"%","0","0");
            ctrlist.addHeader("<center>LL</center>",""+(68/3)+"%","0","0");

            ctrlist.setLinkRow(-1);
            ctrlist.setLinkSufix("");
            Vector lstData = ctrlist.getData();
            Vector lstLinkData = ctrlist.getLinkData();
            ctrlist.setLinkPrefix("javascript:cmdEdit('");
            ctrlist.setLinkSufix("')");
            ctrlist.reset();
            
            DecimalFormat dc2 = new DecimalFormat("0.##");
            int index = -1;
            int noCounter = start+1;
            for (int i = 0; i < objectClass.size(); i++) {
                LeaveTarget leaveTarget = (LeaveTarget)objectClass.get(i);
                
                
                 Vector rowx = new Vector();
                rowx.add(""+noCounter); noCounter++;

                if(targetId == leaveTarget.getOID()){
                    index = i;
                                        
                    String strTargetDate = ControlDate.drawDateMY(FrmLeaveTarget.fieldNames[FrmLeaveTarget.FRM_FIELD_TARGET_DATE],(leaveTarget.getTargetDate()!=null?leaveTarget.getTargetDate():new Date()), "MMM yyyy", "formElemen", -10, installInterval);
                    rowx.add(strTargetDate);
                    String strDetailDp = "<input type='text' name='"+FrmLeaveTarget.fieldNames[FrmLeaveTarget.FRM_FIELD_DP_TARGET]+"'  value='"+dc2.format(leaveTarget.getDpTarget())+"' class='elemenForm' size='20'>%";
                    String strDetailAl = "<input type='text' name='"+FrmLeaveTarget.fieldNames[FrmLeaveTarget.FRM_FIELD_AL_TARGET]+"'  value='"+dc2.format(leaveTarget.getAlTarget())+"' class='elemenForm' size='20'>%";
                    String strDetailLl = "<input type='text' name='"+FrmLeaveTarget.fieldNames[FrmLeaveTarget.FRM_FIELD_LL_TARGET]+"'  value='"+dc2.format(leaveTarget.getLlTarget())+"' class='elemenForm' size='20'>%";
                    rowx.add(strDetailDp);
                    rowx.add(strDetailAl);
                    rowx.add(strDetailLl);
                    lstData.add(rowx);
                    lstLinkData.add(String.valueOf(leaveTarget.getOID()));
                                        
                }else{
                    rowx.add("<a href=javascript:cmdEdit('"+leaveTarget.getOID()+"')>"+leaveTarget.getName());                 
                    rowx.add(""+dc2.format(leaveTarget.getDpTarget())+"%");
                    rowx.add(""+dc2.format(leaveTarget.getAlTarget())+"%");
                    rowx.add(""+dc2.format(leaveTarget.getLlTarget())+"%");
                    lstData.add(rowx);
                    lstLinkData.add(String.valueOf(leaveTarget.getOID()));
                  
                }
                
            }
            String strTable = ctrlist.drawList();
            //System.out.println(strTable);
            return strTable;
	}



%>

<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidLeaveTarget = FRMQueryString.requestLong(request, FrmLeaveTarget.fieldNames[FrmLeaveTarget.FRM_FIELD_LEAVE_TARGET_ID]);
Date datePeriod = FRMQueryString.requestDate(request, FrmLeaveTarget.fieldNames[FrmLeaveTarget.FRM_FIELD_TARGET_DATE]);


/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = PstLeaveTarget.fieldNames[PstLeaveTarget.FLD_TARGET_DATE];

CtrlLeaveTarget ctrlLeaveTarget = new CtrlLeaveTarget(request);
ControlLine ctrLine = new ControlLine();
Vector listData = new Vector(1,1);

/*switch statement */
int vectSize = 0;
String strexist = "";

long targetid = PstLeaveTarget.getTargetDateId(datePeriod);

if(iCommand==Command.SAVE && targetid!=0 && targetid!=oidLeaveTarget){
    strexist = "Periode is already exists!";
    if(oidLeaveTarget>0){
        iCommand = Command.EDIT;
    }else{
        iCommand = Command.ADD;
    }
}else{
    iErrCode = ctrlLeaveTarget.action(iCommand , oidLeaveTarget);
    msgString =  ctrlLeaveTarget.getMessage();
    LeaveTarget leaveTarget = ctrlLeaveTarget.getLeaveTarget();
    leaveTarget.setName(Formater.formatDate(leaveTarget.getTargetDate(), "MMMM yyyy"));
    try{
        PstLeaveTarget.updateExc(leaveTarget);
    }catch(Exception ex){}
}
    /* end switch*/
    FrmLeaveTarget frmLeaveTarget = ctrlLeaveTarget.getForm();
    Vector vDept = new Vector(1,1);
try{
    vDept = PstDepartment.list(0, 0, null, PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);
}catch(Exception ex){}


/*count list All Position*/
     vectSize = PstLeaveTarget.getCount(whereClause);

if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlLeaveTarget.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

/* get record to display */
listData = PstLeaveTarget.list(start,recordToGet, whereClause , orderClause);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listData.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listData = PstLeaveTarget.list(start,recordToGet, whereClause , orderClause);
}

if(iCommand == Command.ADD){
    LeaveTarget lTarget = new LeaveTarget();
    listData.add(lTarget);
}

//session.putValue("SELECTED_IMAGE_ASSIGN_SESSION", vectPict);


%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Master Data Leave Target</title>
<script language="JavaScript">

function cmdAdd(){
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.<%=FrmLeaveTarget.fieldNames[FrmLeaveTarget.FRM_FIELD_LEAVE_TARGET_ID]%>.value="0";
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.command.value="<%=String.valueOf(Command.ADD)%>";
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.action="leaveTarget.jsp";
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.submit();
}

function cmdAsk(oidLeaveTarget){
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.command.value="<%=String.valueOf(Command.ASK)%>";
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.<%=FrmLeaveTarget.fieldNames[FrmLeaveTarget.FRM_FIELD_LEAVE_TARGET_ID]%>.value=oidLeaveTarget;
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.action="leaveTarget.jsp";
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.submit();
}

function cmdConfirmDelete(oidLeaveTarget){
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.<%=FrmLeaveTarget.fieldNames[FrmLeaveTarget.FRM_FIELD_LEAVE_TARGET_ID]%>.value=oidLeaveTarget;
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.command.value="<%=String.valueOf(Command.DELETE)%>";
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.action="leaveTarget.jsp";
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.submit();
}
function cmdSave(){
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.command.value="<%=String.valueOf(Command.SAVE)%>";
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.prev_command.value="<%=String.valueOf(prevCommand)%>";
        document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.action="leaveTarget.jsp";
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.submit();
	}

function cmdEdit(oidLeaveTarget){
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.<%=FrmLeaveTarget.fieldNames[FrmLeaveTarget.FRM_FIELD_LEAVE_TARGET_ID]%>.value=oidLeaveTarget;
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.command.value="<%=String.valueOf(Command.EDIT)%>";
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.action="leaveTarget.jsp";
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.submit();
	}

function cmdCancel(oidLeaveTarget){
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.<%=FrmLeaveTarget.fieldNames[FrmLeaveTarget.FRM_FIELD_LEAVE_TARGET_ID]%>.value=oidLeaveTarget;
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.command.value="<%=String.valueOf(Command.EDIT)%>";
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.action="leaveTarget.jsp";
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.submit();
}

function cmdBack(){
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.command.value="<%=String.valueOf(Command.LIST)%>";
        document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.<%=FrmLeaveTarget.fieldNames[FrmLeaveTarget.FRM_FIELD_LEAVE_TARGET_ID]%>.value="0";
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.action="leaveTarget.jsp";
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.submit();
	}

function cmdListFirst(){
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.command.value="<%=String.valueOf(Command.FIRST)%>";
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.prev_command.value="<%=String.valueOf(Command.FIRST)%>";
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.action="leaveTarget.jsp";
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.submit();
}

function cmdListPrev(){
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.command.value="<%=String.valueOf(Command.PREV)%>";
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.prev_command.value="<%=String.valueOf(Command.PREV)%>";
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.action="leaveTarget.jsp";
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.submit();
	}

function cmdListNext(){
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.command.value="<%=String.valueOf(Command.NEXT)%>";
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.prev_command.value="<%=String.valueOf(Command.NEXT)%>";
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.action="leaveTarget.jsp";
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.submit();
}

function cmdListLast(){
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.command.value="<%=String.valueOf(Command.LAST)%>";
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.prev_command.value="<%=String.valueOf(Command.LAST)%>";
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.action="leaveTarget.jsp";
	document.<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>.submit();
}

function fnTrapKD(){
	//alert(event.keyCode);
	switch(event.keyCode) {
		case <%=String.valueOf(LIST_PREV)%>:
			cmdListPrev();
			break;
		case <%=String.valueOf(LIST_NEXT)%>:
			cmdListNext();
			break;
		case <%=String.valueOf(LIST_FIRST)%>:
			cmdListFirst();
			break;
		case <%=String.valueOf(LIST_LAST)%>:
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

<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../styletemplate/template_header.jsp" %>
            <%}else{%>
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../main/header.jsp" %>
      <!-- #EndEditable --> 
    </td>
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
          <td height="20">
		    <font color="#FF6600" face="Arial"><strong>
			  <!-- #BeginEditable "contenttitle" --> 
                  Master Data &gt; Image Assign<!-- #EndEditable -->
            </strong></font>
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
                            <td valign="top">
		    				  <!-- #BeginEditable "content" -->
                                    <!--- FORM PERTAMA-->
                                    
                                    <!--- FORM KEDUA-->
                                    <form name="<%=FrmLeaveTarget.FRM_NAME_LEAVE_TARGET%>"><!--   method ="post"  enctype="multipart/form-data" action="upload_img_assign_process.jsp" -->
                                      <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
                                      <input type="hidden" name="vectSize" value="<%=String.valueOf(vectSize)%>">
                                      <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
                                      <input type="hidden" name="prev_command" value="<%=String.valueOf(prevCommand)%>">
                                      <input type="hidden" name="<%=FrmLeaveTarget.fieldNames[FrmLeaveTarget.FRM_FIELD_LEAVE_TARGET_ID]%>" value="<%=String.valueOf(oidLeaveTarget)%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="listtitle">
                                                    &nbsp;Leave Target List </td>
                                              </tr>
                                              <%
                                            try{
                                            if (listData.size()>0){
                                            %>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <%=drawList(listData,oidLeaveTarget,start)%>
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
                                                                cmd =prevCommand; 
                                                   } 
                                                    %>
                                                  <% ctrLine.setLocationImg(approot+"/images");
							   	ctrLine.initDefault();
								 %>
                                                  <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> 
                                                  </span> </td>
                                              </tr>
                                                <%//if((iCommand == Command.NONE || iCommand == Command.DELETE || iCommand == Command.BACK || iCommand ==Command.SAVE)&& (frmLeaveTarget.errorSize()<1)){
                                                   if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmLeaveTarget.errorSize()<1)){
                                                   if(privAdd){%>
                                              <tr align="left" valign="top"> 
                                                <td>
                                                  <table cellpadding="0" cellspacing="0" border="0">
                                                    <tr> 
                                                      <td colspan='4'>&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                      <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td height="22" valign="middle" colspan="3" width="951"> 
                                                        <a href="javascript:cmdAdd()" class="command">Add New Leave Target</a> </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
											  <%}
											  }%>
                                            </table>
                                          </td>
                                        </>
                                        <tr align="left" valign="top"> 
                                          <td height="8" valign="middle" colspan="3"> 
                                            <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmLeaveTarget.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>

                                              <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <%
                                                if(strexist.length()>0){
                                              %>
                                              <tr>
                                                  <td><font color="red"><%=strexist%></font></td>
                                              </tr>
                                              <%}%>
                                              <tr> 
                                                <td colspan="2"> 
                                                  <%
									ctrLine.setLocationImg(approot+"/images");
									ctrLine.initDefault();
									ctrLine.setTableWidth("80%");
									String scomDel = "javascript:cmdAsk('"+oidLeaveTarget+"')";
									String sconDelCom = "javascript:cmdConfirmDelete('"+oidLeaveTarget+"')";
									String scancel = "javascript:cmdEdit('"+oidLeaveTarget+"')";
									ctrLine.setBackCaption("Back to List");
									ctrLine.setCommandStyle("buttonlink");
									ctrLine.setBackCaption("Back to List Leave Target");
									ctrLine.setSaveCaption("Save Leave Target");
									ctrLine.setConfirmDelCaption("Yes Delete Leave Target");
									ctrLine.setDeleteCaption("Delete Leave Target");

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
									
									if(iCommand == Command.ASK)
										ctrLine.setDeleteQuestion(msgString);
									%>
                                                  <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%> 
                                                </td>
                                              </tr>
                                            </table>
                                            <%}%>
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
