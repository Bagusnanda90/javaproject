
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
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>

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

	public String drawList(Vector objectClass , long fingerPrintId, int start)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("80%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("No","5%");
		ctrlist.addHeader("Employee Number","50%");
		ctrlist.addHeader("Finger Print Number","45%");

		ctrlist.setLinkRow(1);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;

		for (int i = 0; i < objectClass.size(); i++) {
			FingerPrint fingerPrint = (FingerPrint)objectClass.get(i);
			 Vector rowx = new Vector();
			 if(fingerPrintId == fingerPrint.getOID())
				 index = i;

			rowx.add(""+(i+1+start));
			rowx.add(fingerPrint.getEmployeeNum());
			rowx.add(""+fingerPrint.getFingerPrint());

			lstData.add(rowx);
			lstLinkData.add(String.valueOf(fingerPrint.getOID()));
		}
		return ctrlist.draw(index);
	}

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidFingerPrint = FRMQueryString.requestLong(request, "hidden_fingerprint_id");


if(iCommand == Command.RETRY){
    SessFingerPrint.synchronizeDataFromEployee();
    iCommand = Command.LAST;
}

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
String strMsgError = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = PstFingerPrint.fieldNames[PstFingerPrint.FLD_FINGER_PRINT];

CtrlFingerPrint ctrlFingerPrint = new CtrlFingerPrint(request);
ControlLine ctrLine = new ControlLine();
Vector listFingerPrint = new Vector(1,1);

/*switch statement */
String empNum = FRMQueryString.requestString(request, FrmFingerPrint.fieldNames[FrmFingerPrint.FRM_FIELD_EMPLOYEE_NUM]);
if(PstFingerPrint.checkEmpNum(empNum) && iCommand == Command.SAVE){
    strMsgError = "Employee Number telah dipergunakan sebelumnya!";
    System.out.println(strMsgError);
}else{
    iErrCode = ctrlFingerPrint.action(iCommand , oidFingerPrint);
}    
/* end switch*/
FrmFingerPrint frmFingerPrint = ctrlFingerPrint.getForm();

/*count list All Position*/
int vectSize = PstFingerPrint.getCount(whereClause);

FingerPrint fingerPrint = ctrlFingerPrint.getFingerPrint();
msgString =  ctrlFingerPrint.getMessage();
 
/*switch list FingerPrint*/
if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)){
	//start = PstFingerPrint.findLimitStart(fingerPrint.getOID(),recordToGet, whereClause);
	oidFingerPrint = fingerPrint.getOID();
}

if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlFingerPrint.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

/* get record to display */
listFingerPrint = PstFingerPrint.list(start,recordToGet, whereClause , orderClause);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listFingerPrint.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listFingerPrint = PstFingerPrint.list(start,recordToGet, whereClause , orderClause);
}


%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Master Data Finger Print</title>
<script language="JavaScript">
function cmdSync(){
	document.frmfingerPrint.hidden_fingerprint_id.value="0";
	document.frmfingerPrint.command.value="<%=String.valueOf(Command.RETRY)%>";
	document.frmfingerPrint.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmfingerPrint.action="fingerPrint.jsp";
	document.frmfingerPrint.submit();
}

function cmdAdd(){
	document.frmfingerPrint.hidden_fingerprint_id.value="0";
	document.frmfingerPrint.command.value="<%=String.valueOf(Command.ADD)%>";
	document.frmfingerPrint.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmfingerPrint.action="fingerPrint.jsp";
	document.frmfingerPrint.submit();
}

function cmdAsk(oidFingerPrint){
	document.frmfingerPrint.hidden_fingerprint_id.value=oidFingerPrint;
	document.frmfingerPrint.command.value="<%=String.valueOf(Command.ASK)%>";
	document.frmfingerPrint.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmfingerPrint.action="fingerPrint.jsp";
	document.frmfingerPrint.submit();
}

function cmdConfirmDelete(oidFingerPrint){
	document.frmfingerPrint.hidden_fingerprint_id.value=oidFingerPrint;
	document.frmfingerPrint.command.value="<%=String.valueOf(Command.DELETE)%>";
	document.frmfingerPrint.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmfingerPrint.action="fingerPrint.jsp";
	document.frmfingerPrint.submit();
}
function cmdSave(){
	document.frmfingerPrint.command.value="<%=String.valueOf(Command.SAVE)%>";
	document.frmfingerPrint.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmfingerPrint.action="fingerPrint.jsp";
	document.frmfingerPrint.submit();
	}

function cmdEditOrg(oidFingerPrint){
	document.frmfingerPrint.hidden_fingerprint_id.value=oidFingerPrint;
	document.frmfingerPrint.command.value="<%=String.valueOf(Command.EDIT)%>";
	document.frmfingerPrint.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmfingerPrint.action="fingerPrint.jsp";
	document.frmfingerPrint.submit();
	}

function cmdCancel(oidFingerPrint){
	document.frmfingerPrint.hidden_fingerprint_id.value=oidFingerPrint;
	document.frmfingerPrint.command.value="<%=String.valueOf(Command.EDIT)%>";
	document.frmfingerPrint.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmfingerPrint.action="fingerPrint.jsp";
	document.frmfingerPrint.submit();
}

function cmdBack(){
	document.frmfingerPrint.command.value="<%=String.valueOf(Command.BACK)%>";
	document.frmfingerPrint.action="fingerPrint.jsp";
	document.frmfingerPrint.submit();
	}

function cmdListFirst(){
	document.frmfingerPrint.command.value="<%=String.valueOf(Command.FIRST)%>";
	document.frmfingerPrint.prev_command.value="<%=String.valueOf(Command.FIRST)%>";
	document.frmfingerPrint.action="fingerPrint.jsp";
	document.frmfingerPrint.submit();
}

function cmdListPrev(){
	document.frmfingerPrint.command.value="<%=String.valueOf(Command.PREV)%>";
	document.frmfingerPrint.prev_command.value="<%=String.valueOf(Command.PREV)%>";
	document.frmfingerPrint.action="fingerPrint.jsp";
	document.frmfingerPrint.submit();
	}

function cmdListNext(){
	document.frmfingerPrint.command.value="<%=String.valueOf(Command.NEXT)%>";
	document.frmfingerPrint.prev_command.value="<%=String.valueOf(Command.NEXT)%>";
	document.frmfingerPrint.action="fingerPrint.jsp";
	document.frmfingerPrint.submit();
}

function cmdListLast(){
	document.frmfingerPrint.command.value="<%=String.valueOf(Command.LAST)%>";
	document.frmfingerPrint.prev_command.value="<%=String.valueOf(Command.LAST)%>";
	document.frmfingerPrint.action="fingerPrint.jsp";
	document.frmfingerPrint.submit();
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

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
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
                  Master Data &gt; Finger Print<!-- #EndEditable -->
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
                                    <form name="frmfingerPrint" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=""+iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=""+vectSize%>">
                                      <input type="hidden" name="start" value="<%=""+start%>">
                                      <input type="hidden" name="prev_command" value="<%=String.valueOf(prevCommand)%>">
                                      <input type="hidden" name="hidden_fingerprint_id" value="<%=""+oidFingerPrint%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;Finger Print
                                                  List </td>
                                              </tr>
                                              <tr>
                                                  <td>
                                                      [<a href="javascript:cmdSync()">Synchronize Data</a>]
                                                  </td>
                                              </tr>
                                              <%
							try{
								if (listFingerPrint.size()>0){
							%>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <%= drawList(listFingerPrint,oidFingerPrint,start)%>
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
                                              <%
                                          if(strMsgError.length()>0){
                                      %>
                                      <tr>
                                          <td>
                                              <font color="red"><%=strMsgError%></font>
                                          </td>
                                      </tr>
                                      <%}%>
											<%//if((iCommand == Command.NONE || iCommand == Command.DELETE || iCommand == Command.BACK || iCommand ==Command.SAVE)&& (frmFingerPrint.errorSize()<1)){
											   if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmFingerPrint.errorSize()<1)){
											   if(privAdd){%>
                                              
                                                    
<tr align="left" valign="top"> 
											  	<td>
                                                  <table cellpadding="0" cellspacing="0" border="0">
                                                    <tr> 
                                                      <td>&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                      <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td height="22" valign="middle" colspan="3" width="951"> 
                                                        <a href="javascript:cmdAdd()" class="command">Add 
                                                        New Finger Print</a> </td>
                                                    </tr>
                                                  </table>
												</td>
                                              </tr>
											  <%}
											  }%>
                                            </table>
                                          </td>
                                        </tr>
										<tr>
											<td>&nbsp;
											</td>
										</tr>
                                     
                                        <tr align="left" valign="top"> 
                                          <td height="8" valign="middle" colspan="3"> 
                                            <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmFingerPrint.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td class="listtitle"><%=oidFingerPrint == 0?"Add":"Edit"%> Finger Print</td>
                                              </tr>
                                              <tr> 
                                                <td height="100%"> 
                                                  <table border="0" cellspacing="2" cellpadding="2" width="50%">
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%">&nbsp;</td>
                                                      <td width="83%" class="comment">*)entry 
                                                        required </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%"> 
                                                        Employee Number</td>
                                                      <td width="83%"> 
                                                        <input type="text" name="<%=frmFingerPrint.fieldNames[FrmFingerPrint.FRM_FIELD_EMPLOYEE_NUM] %>"  value="<%=(fingerPrint.getEmployeeNum()!=null?fingerPrint.getEmployeeNum():"")%>" class="elemenForm" size="30">
                                                        *<%=frmFingerPrint.getErrorMsg(FrmFingerPrint.FRM_FIELD_EMPLOYEE_NUM)%>
                                                      </td>
                                                    </tr>
                                                    <%
                                                    int nextFingerPrint = 0;
                                                    try{
                                                         nextFingerPrint  =SessFingerPrint.getNextFingerPrint();
                                                    }catch(Exception ex){}
                                                    %>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%"> 
                                                        Finger Print Number </td>
                                                      <td width="83%"> 
                                                        <input type="hidden" name="<%=frmFingerPrint.fieldNames[FrmFingerPrint.FRM_FIELD_FINGER_PRINT] %>"  value="<%=""+nextFingerPrint%>" class="elemenForm" size="30">
                                                        <input type="text" readonly="true" name="<%=frmFingerPrint.fieldNames[FrmFingerPrint.FRM_FIELD_FINGER_PRINT] %>_TEMP"  value="<%=""+nextFingerPrint%>" class="elemenForm" size="30">
                                                        *<%=frmFingerPrint.getErrorMsg(FrmFingerPrint.FRM_FIELD_FINGER_PRINT)%>
                                                      </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td colspan="2"> 
                                                  <%
									ctrLine.setLocationImg(approot+"/images");
									ctrLine.initDefault();
									ctrLine.setTableWidth("80%");
									String scomDel = "";//"javascript:cmdAsk('"+oidFingerPrint+"')";
									String sconDelCom = "";//"javascript:cmdConfirmDelete('"+oidFingerPrint+"')";
									String scancel = "";//"javascript:cmdEdit('"+oidFingerPrint+"')";
									ctrLine.setBackCaption("Back to List");
									ctrLine.setCommandStyle("buttonlink");
									ctrLine.setBackCaption("Back to List Finger Print");
									ctrLine.setSaveCaption("Save FingerPrint");
									ctrLine.setConfirmDelCaption("Yes Delete Finger Print");
									ctrLine.setDeleteCaption("Delete Finger Print");

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
                                                                                ctrLine.setDeleteCaption("");
										ctrLine.setEditCaption("");
									}

									if (privAdd == false){
										ctrLine.setAddCaption("");
                                                                                ctrLine.setDeleteCaption("");
										ctrLine.setEditCaption("");
									}
									
									if(iCommand == Command.ASK){
										ctrLine.setDeleteQuestion(msgString);
                                                                        }
                                                                        ctrLine.setDeleteCaption("");
                                                                        ctrLine.setEditCaption("");
                                                                                        
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
<script language="JavaScript">
	//var oBody = document.body;
	//var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
</script>
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
