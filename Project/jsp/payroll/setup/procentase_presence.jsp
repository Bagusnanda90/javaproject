<%@ page language="java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.form.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_PROCESS, AppObjInfo.OBJ_PAYROLL_PROCESS_INPUT);%>
<%@ include file = "../../main/checkuser.jsp" %>
<%!
	public String drawList(int iCommand,FrmProcenPresence frmObject, ProcenPresence objEntity, Vector objectClass,  long ProcenPresenceId)
	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("30%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("Day","50%");
		ctrlist.addHeader("Procentase","50%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		Vector rowx = new Vector(1,1);
		ctrlist.reset();
		int index = -1;

		for (int i = 0; i < objectClass.size(); i++) {
			 ProcenPresence procenPresence = (ProcenPresence)objectClass.get(i);
			 rowx = new Vector();
			 if(ProcenPresenceId == procenPresence.getOID())
				 index = i; 
			 if(index == i && (iCommand == Command.EDIT || iCommand == Command.ASK)){
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmProcenPresence.FRM_FIELD_ABSENCE_DAY] +"\" value=\""+procenPresence.getAbsenceDay()+"\" size=\"10\" class=\"elemenForm\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmProcenPresence.FRM_FIELD_PROCEN_PRESENCE] +"\" value=\""+objEntity.getProcenPresence()+"\" size=\"10\" class=\"elemenForm\"> %");
			}else{
				//System.out.println("aku cek");
				rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(procenPresence.getOID())+"')\">"+procenPresence.getAbsenceDay()+"</a>");
				//rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayExecutive.FRM_FIELD_EXECUTIVE_NAME] +"\" value=\""+objEntity.getExecutiveName()+"\" size=\"30\" class=\"elemenForm\">");
				rowx.add(""+procenPresence.getProcenPresence());
			} 
		lstData.add(rowx);
		}
		 rowx = new Vector();

		if(iCommand == Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize() > 0) || (objectClass.size()<1)){ 
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmProcenPresence.FRM_FIELD_ABSENCE_DAY] +"\" value=\""+objEntity.getAbsenceDay()+"\" size=\"10\" class=\"elemenForm\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmProcenPresence.FRM_FIELD_PROCEN_PRESENCE] +"\" value=\""+objEntity.getProcenPresence()+"\" size=\"10\" class=\"elemenForm\"> %");
		}
	 lstData.add(rowx);

		return ctrlist.draw();
	}

%>
<!-- End of JSP Block -->
<%
// request data from jsp page
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidProcenPresence = FRMQueryString.requestLong(request, "hidden_id_procen_presence");

// variable declaration
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;

// instantiate TaxType classes
CtrlProcenPresence ctrlProcenPresence = new CtrlProcenPresence(request);
ControlLine ctrLine = new ControlLine();


// action on object agama defend on command entered
iErrCode = ctrlProcenPresence.action(iCommand , oidProcenPresence);
FrmProcenPresence frmProcenPresence = ctrlProcenPresence.getForm();
ProcenPresence procenPresence = ctrlProcenPresence.getProcenPresence();
msgString =  ctrlProcenPresence.getMessage();

// get records to display
String whereClause = "";
String orderClause = PstProcenPresence.fieldNames[PstProcenPresence.FLD_PROCEN_PRESENCE];

int vectSize = PstProcenPresence.getCount(whereClause);
if(iCommand==Command.FIRST || iCommand==Command.PREV || iCommand==Command.NEXT || iCommand==Command.LAST){
	start = ctrlProcenPresence.actionList(iCommand, start, vectSize, recordToGet);
}

Vector listProcenPresence = PstProcenPresence.list(start, recordToGet, whereClause , orderClause);
if(listProcenPresence.size()<1 && start>0){
	 if(vectSize - recordToGet>recordToGet){
		 start = start - recordToGet;
	 }else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST;
	 }
	 listProcenPresence = PstProcenPresence.list(start, recordToGet, whereClause , orderClause);
}
%>
<%
int idx = FRMQueryString.requestInt(request, "idx");
%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - </title>
<script language="JavaScript">
function cmdAdd(){
	document.frmpayrollsetup.hidden_id_procen_presence.value="0";
	document.frmpayrollsetup.command.value="<%=Command.ADD%>";
	document.frmpayrollsetup.prev_command.value="<%=prevCommand%>";
	document.frmpayrollsetup.action="procentase_presence.jsp";
	document.frmpayrollsetup.submit();
}

function cmdAsk(oidProcenPresence){
	document.frmpayrollsetup.hidden_id_procen_presence.value=oidProcenPresence;
	document.frmpayrollsetup.command.value="<%=Command.ASK%>";
	document.frmpayrollsetup.prev_command.value="<%=prevCommand%>";
	document.frmpayrollsetup.action="procentase_presence.jsp";
	document.frmpayrollsetup.submit();
}

function cmdConfirmDelete(oid){
		var x = confirm(" Are You Sure to Delete?");
		if(x){
			document.frmpayrollsetup.command.value="<%=Command.DELETE%>";
			document.frmpayrollsetup.action="procentase_presence.jsp";
			document.frmpayrollsetup.submit();
		}
	}

function cmdSave(){
	document.frmpayrollsetup.command.value="<%=Command.SAVE%>";
	document.frmpayrollsetup.prev_command.value="<%=prevCommand%>";
	document.frmpayrollsetup.action="procentase_presence.jsp";
	document.frmpayrollsetup.submit();
}

function cmdEdit(oidProcenPresence){
	document.frmpayrollsetup.hidden_id_procen_presence.value=oidProcenPresence;
	document.frmpayrollsetup.command.value="<%=Command.EDIT%>";
	document.frmpayrollsetup.prev_command.value="<%=prevCommand%>";
	document.frmpayrollsetup.action="procentase_presence.jsp";
	document.frmpayrollsetup.submit();
}

function cmdCancel(oidProcenPresence){
	document.frmpayrollsetup.hidden_id_procen_presence.value=oidProcenPresence;
	document.frmpayrollsetup.command.value="<%=Command.EDIT%>";
	document.frmpayrollsetup.prev_command.value="<%=prevCommand%>";
	document.frmpayrollsetup.action="procentase_presence.jsp";
	document.frmpayrollsetup.submit();
}

function cmdBack(){
	document.frmpayrollsetup.command.value="<%=Command.BACK%>";
	document.frmpayrollsetup.action="procentase_presence.jsp";
	document.frmpayrollsetup.submit();
}

function cmdListFirst(){
	document.frmpayrollsetup.command.value="<%=Command.FIRST%>";
	document.frmpayrollsetup.prev_command.value="<%=Command.FIRST%>";
	document.frmpayrollsetup.action="procentase_presence.jsp";
	document.frmpayrollsetup.submit();
}

function cmdListPrev(){
	document.frmpayrollsetup.command.value="<%=Command.PREV%>";
	document.frmpayrollsetup.prev_command.value="<%=Command.PREV%>";
	document.frmpayrollsetup.action="procentase_presence.jsp";
	document.frmpayrollsetup.submit();
	}

function cmdListNext(){
	document.frmpayrollsetup.command.value="<%=Command.NEXT%>";
	document.frmpayrollsetup.prev_command.value="<%=Command.NEXT%>";
	document.frmpayrollsetup.action="procentase_presence.jsp";
	document.frmpayrollsetup.submit();
}

function cmdListLast(){
	document.frmpayrollsetup.command.value="<%=Command.LAST%>";
	document.frmpayrollsetup.prev_command.value="<%=Command.LAST%>";
	document.frmpayrollsetup.action="procentase_presence.jsp";
	document.frmpayrollsetup.submit();
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
</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 


<!-- #EndEditable --> 
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
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
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr> 
          <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Jenis 
                  Pajak <!-- #EndEditable --> </strong></font> </td>
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
                                    <form name="frmpayrollsetup" method="post" action="">
									  <input type="hidden" name="command" value="<%=iCommand%>">
									  <input type="hidden" name="vectSize" value="<%=vectSize%>">
									  <input type="hidden" name="start" value="<%=start%>">
									  <input type="hidden" name="prev_command" value="<%=prevCommand%>">
									  <input type="hidden" name="hidden_id_procen_presence" value="<%=oidProcenPresence%>">
                                      <table width="963" border="0" cellspacing="0" cellpadding="0" class="listgensell">
                                        <tr> 
                                          <td width="494">&nbsp;</td>
                                        </tr>
                                        <%
										try{
										%>
                                        <tr> 
                                          <td> <%=drawList(iCommand, frmProcenPresence, procenPresence, listProcenPresence, oidProcenPresence)%> </td>
                                        </tr>
                                        <% 
										  }catch(Exception exc){ 
										  System.out.println("Err::::::"+exc.toString());
										  }%>
										<%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmProcenPresence.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>  
                                        <tr> 
                                          <td> <%
										  		ctrLine.setLocationImg(approot+"/images");
												ctrLine.initDefault();
												ctrLine.setTableWidth("80");
												String scomDel = "javascript:cmdAsk('"+oidProcenPresence+"')";
												String sconDelCom = "javascript:cmdConfirmDelete('"+oidProcenPresence+"')";
												String scancel = "javascript:cmdEdit('"+oidProcenPresence+"')";
												ctrLine.setBackCaption("Back to List");
												ctrLine.setCommandStyle("buttonlink");
													ctrLine.setDeleteCaption("Delete");
													ctrLine.setSaveCaption("Save ProcenPresence");
													ctrLine.setAddCaption("");
			
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
                                                  <%//= ctrLine.drawImage(iCommand, iErrCode, msgString)%> 
										   </td>
                                        </tr>
										<%}%>
                                      </table>
									  <table width="100%" border="0">
									  <tr> 
                                          <td> 
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
														cmd = PstTaxType.findLimitCommand(start,recordToGet,vectSize);
													else									 
														cmd =prevCommand;
											  }  
										   } 
											%> 
                                            <% ctrLine.setLocationImg(approot+"/images");
											ctrLine.initDefault();
											 %> 
                                            <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> </td>
                                        </tr>
										<tr> 
											  <%if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmProcenPresence.errorSize()<1)){%>
											  <td  valign="middle"> <a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a> 
												&nbsp;&nbsp;<a href="javascript:cmdAdd()" class="command">Add New ProcenPresence</a></td>
											  <%}%>
											</tr>
									  	<%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmProcenPresence.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>  
									  <tr>
										<td width="12%"><a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnSave.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save Data"></a>
                                         <a href="javascript:cmdSave()" class="command">Save ProcenPresence</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</td>
										<td width="16%"><img name="Image261" border="0" src="<%=approot%>/images/BtnDel.jpg" width="24" height="24" alt="Delete">
										 <a href="javascript:cmdConfirmDelete()" class="command">Delete ProcenPresence</a>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp; </td>
										<td width="72%"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnBack.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back to List"></a>
                                         <a href="javascript:cmdBack()" class="command">Back to List ProcenPresence</a> </td>
										 <%
										 }
										 %>
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
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" --> {script} 
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
