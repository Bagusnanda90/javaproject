
<% 
/* 
 * Page Name  		:  medicine.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		: lkarunia 
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
<%@ page import = "com.dimata.harisma.entity.clinic.*" %>
<%@ page import = "com.dimata.harisma.form.clinic.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_CLINIC, AppObjInfo.G2_MEDICINE, AppObjInfo.OBJ_LIST_OF_MEDICINE); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
long hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
boolean isHRDLogin = hrdDepartmentOid == departmentOid ? true : false;

if(isHRDLogin){
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%!

	public String drawList(int iCommand,FrmMedicine frmObject, Medicine objEntity, Vector objectClass,  long medicineId)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("80%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("Name","30%");
		ctrlist.addHeader("Unit Price","20%");
		ctrlist.addHeader("Description","50%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		Vector rowx = new Vector(1,1);
		ctrlist.reset();
		int index = -1;

		for (int i = 0; i < objectClass.size(); i++) {
			 Medicine medicine = (Medicine)objectClass.get(i);
			 rowx = new Vector();
			 if(medicineId == medicine.getOID())
				 index = i; 

			 if(index == i && (iCommand == Command.EDIT || iCommand == Command.ASK)){
				index = -1;	
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmMedicine.FRM_FIELD_NAME] +"\" value=\""+medicine.getName()+"\" class=\"elemenForm\" size=\"35\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmMedicine.FRM_FIELD_UNIT_PRICE] +"\" value=\""+Formater.formatNumber(medicine.getUnitPrice(),"#.##")+"\" class=\"elemenForm\" size=\"20\">");
				rowx.add("<textarea name=\""+frmObject.fieldNames[FrmMedicine.FRM_FIELD_DESCRIPTION] +"\"  class=\"elemenForm\" cols=\"35\" rows=\"2\">"+medicine.getDescription()+"</textarea>");
			}else{
				rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(medicine.getOID())+"')\">"+medicine.getName()+"</a>");
				rowx.add(Formater.formatNumber(medicine.getUnitPrice(),"#,###.##"));
				rowx.add(medicine.getDescription());
			} 

			lstData.add(rowx);
		}

		 rowx = new Vector();

		if(iCommand == Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize() > 0)){ 
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmMedicine.FRM_FIELD_NAME] +"\" value=\""+objEntity.getName()+"\" class=\"elemenForm\" size=\"35\">");
				String unitPrc = "<input type=\"text\" name=\""+frmObject.fieldNames[FrmMedicine.FRM_FIELD_UNIT_PRICE] +"\" value=\"";
				if((objEntity.getUnitPrice()-0.0f)>0.0f)
					unitPrc = unitPrc + objEntity.getUnitPrice();
				unitPrc = unitPrc + "\" class=\"elemenForm\" size=\"20\">";
				
				rowx.add(unitPrc);
				rowx.add("<textarea name=\""+frmObject.fieldNames[FrmMedicine.FRM_FIELD_DESCRIPTION] +"\"  class=\"elemenForm\" cols=\"35\" rows=\"2\">"+objEntity.getDescription()+"</textarea>");

		}

		lstData.add(rowx);

		return ctrlist.draw(index);
	}

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidMedicine = FRMQueryString.requestLong(request, "hidden_medicine_id");

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = PstMedicine.fieldNames[PstMedicine.FLD_NAME];

CtrlMedicine ctrlMedicine = new CtrlMedicine(request);
ControlLine ctrLine = new ControlLine();
Vector listMedicine = new Vector(1,1);

/*switch statement */
iErrCode = ctrlMedicine.action(iCommand , oidMedicine);
/* end switch*/
FrmMedicine frmMedicine = ctrlMedicine.getForm();

/*count list All Medicine*/
int vectSize = PstMedicine.getCount(whereClause);

/*switch list Medicine*/
if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlMedicine.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

Medicine medicine = ctrlMedicine.getMedicine();
msgString =  ctrlMedicine.getMessage();

if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)){
	start = PstMedicine.findLimitStart(medicine.getOID(),recordToGet, whereClause,orderClause);
	oidMedicine = medicine.getOID();
}

/* get record to display */
listMedicine = PstMedicine.list(start,recordToGet, whereClause , orderClause);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listMedicine.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listMedicine = PstMedicine.list(start,recordToGet, whereClause , orderClause);
}
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Medicine</title>
<script language="JavaScript">


function cmdAdd(){
	document.frmmedicine.hidden_medicine_id.value="0";
	document.frmmedicine.command.value="<%=Command.ADD%>";
	document.frmmedicine.prev_command.value="<%=prevCommand%>";
	document.frmmedicine.action="medicine.jsp";
	document.frmmedicine.submit();
}

function cmdAsk(oidMedicine){
	document.frmmedicine.hidden_medicine_id.value=oidMedicine;
	document.frmmedicine.command.value="<%=Command.ASK%>";
	document.frmmedicine.prev_command.value="<%=prevCommand%>";
	document.frmmedicine.action="medicine.jsp";
	document.frmmedicine.submit();
}

function cmdConfirmDelete(oidMedicine){
	document.frmmedicine.hidden_medicine_id.value=oidMedicine;
	document.frmmedicine.command.value="<%=Command.DELETE%>";
	document.frmmedicine.prev_command.value="<%=prevCommand%>";
	document.frmmedicine.action="medicine.jsp";
	document.frmmedicine.submit();
}

function cmdSave(){
	document.frmmedicine.command.value="<%=Command.SAVE%>";
	document.frmmedicine.prev_command.value="<%=prevCommand%>";
	document.frmmedicine.action="medicine.jsp";
	document.frmmedicine.submit();
}

function cmdEdit(oidMedicine){
	document.frmmedicine.hidden_medicine_id.value=oidMedicine;
	document.frmmedicine.command.value="<%=Command.EDIT%>";
	document.frmmedicine.prev_command.value="<%=prevCommand%>";
	document.frmmedicine.action="medicine.jsp";
	document.frmmedicine.submit();
}

function cmdCancel(oidMedicine){
	document.frmmedicine.hidden_medicine_id.value=oidMedicine;
	document.frmmedicine.command.value="<%=Command.EDIT%>";
	document.frmmedicine.prev_command.value="<%=prevCommand%>";
	document.frmmedicine.action="medicine.jsp";
	document.frmmedicine.submit();
}

function cmdBack(){
	document.frmmedicine.command.value="<%=Command.BACK%>";
	document.frmmedicine.action="medicine.jsp";
	document.frmmedicine.submit();
}

function cmdListFirst(){
	document.frmmedicine.command.value="<%=Command.FIRST%>";
	document.frmmedicine.prev_command.value="<%=Command.FIRST%>";
	document.frmmedicine.action="medicine.jsp";
	document.frmmedicine.submit();
}

function cmdListPrev(){
	document.frmmedicine.command.value="<%=Command.PREV%>";
	document.frmmedicine.prev_command.value="<%=Command.PREV%>";
	document.frmmedicine.action="medicine.jsp";
	document.frmmedicine.submit();
}

function cmdListNext(){
	document.frmmedicine.command.value="<%=Command.NEXT%>";
	document.frmmedicine.prev_command.value="<%=Command.NEXT%>";
	document.frmmedicine.action="medicine.jsp";
	document.frmmedicine.submit();
}

function cmdListLast(){
	document.frmmedicine.command.value="<%=Command.LAST%>";
	document.frmmedicine.prev_command.value="<%=Command.LAST%>";
	document.frmmedicine.action="medicine.jsp";
	document.frmmedicine.submit();
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

//-------------- script form image -------------------

function cmdDelPict(oidMedicine){
	document.frmimage.hidden_medicine_id.value=oidMedicine;
	document.frmimage.command.value="<%=Command.POST%>";
	document.frmimage.action="medicine.jsp";
	document.frmimage.submit();
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

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
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
			  <!-- #BeginEditable "contenttitle" -->Clinic 
                  &gt; Medicine<!-- #EndEditable --> 
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
                                    <form name="frmmedicine" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="hidden_medicine_id" value="<%=oidMedicine%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">                                              
											  <% if((listMedicine == null || listMedicine.size()<1)&& (iCommand == Command.NONE)){%>
											  <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="comment">&nbsp;</td>
                                              </tr>
											  <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3" class="comment"> 
                                                  &nbsp;&nbsp;No Medicine available </td>
                                              </tr>
                                              <% }else{
												   try{
												%>
											  <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3">&nbsp;<span class="listtitle">Medicine 
                                                  List </span></td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <%= drawList(iCommand,frmMedicine, medicine,listMedicine,oidMedicine)%> 
                                                </td>
                                              </tr>
                                              <% 
											  }catch(Exception exc){ 
											  }
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
													  else{
															if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE))
																cmd = PstMedicine.findLimitCommand(start,recordToGet,vectSize);
															else									 
																cmd =prevCommand;
													  }  
												   } 
							   						 %>
                                                  <%    ctrLine.setLocationImg(approot+"/images");
														ctrLine.initDefault();
														 %>
                                                  <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> 
                                                  </span> </td>
                                              </tr>
                                             <% if((iCommand == Command.NONE) || (iCommand== Command.BACK) || (iCommand == Command.FIRST ) ||
											 		(iCommand == Command.LAST) || (iCommand == Command.PREV) || (iCommand == Command.NEXT)){
											 		if(privAdd){%>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3">
                                                  <table cellpadding="0" cellspacing="0" border="0">
                                                    <tr> 
                                                      <td>&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="4" height="25"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td width="24" height="25"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                      <td width="6" height="25"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td height="25" valign="middle" colspan="3" width="951"><a href="javascript:cmdAdd()" class="command">Add 
                                                        New Medicine</a> </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
											  <%}
											  }%>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="8" valign="middle" width="17%">&nbsp;</td>
                                          <td height="8" colspan="2" width="83%">&nbsp; 
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top" > 
                                          <td colspan="3" class="command"> 
                                            <%
									ctrLine.setLocationImg(approot+"/images");
									ctrLine.initDefault();
									ctrLine.setTableWidth("80%");
									String scomDel = "javascript:cmdAsk('"+oidMedicine+"')";
									String sconDelCom = "javascript:cmdConfirmDelete('"+oidMedicine+"')";
									String scancel = "javascript:cmdEdit('"+oidMedicine+"')";
									ctrLine.setBackCaption("Back to List Medicine");
									ctrLine.setCommandStyle("buttonlink");
									ctrLine.setConfirmDelCaption("Yes Delete Medicine");
									ctrLine.setDeleteCaption("Delete Medicine");
									ctrLine.setSaveCaption("Save Medicine");
									ctrLine.setAddCaption("Add Medicine");
									
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
                                            <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%> 
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
<%
}else{
%>    
    <script language="javascript">
              window.location="<%=approot%>/inform.jsp?ic=<%= I_SystemInfo.HAVE_NOPRIV%>";
    </script>             
<%
}
%>