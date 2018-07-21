<% 
/* 
 * Page Name  		:  diseasetype.jsp
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
<%@ page import = "com.dimata.harisma.entity.clinic.*" %>
<%@ page import = "com.dimata.harisma.form.clinic.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_CLINIC, AppObjInfo.G2_DISEASE, AppObjInfo.OBJ_DISEASE_TYPE); %>
<%//@ include file = "../../main/checkuser.jsp" %>
<%
long hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
boolean isHRDLogin = hrdDepartmentOid == departmentOid ? true : false;

if(isHRDLogin){
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
boolean privAdd=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
boolean privUpdate=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
boolean privDelete=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%!
public String drawList(int iCommand,FrmDiseaseType frmObject, DiseaseType objEntity, Vector objectClass, long diseaseTipe){
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("40%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.addHeader("Disease Type","100%");

	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	Vector rowx = new Vector(1,1);
	ctrlist.reset();
	int index = -1;

	System.out.println("diseaseTipe : "+diseaseTipe);
	
	for (int i = 0; i < objectClass.size(); i++) {
		 DiseaseType diseaseType = (DiseaseType)objectClass.get(i);
		 rowx = new Vector();
		 if(diseaseTipe == diseaseType.getOID())
			 index = i; 

		 if(index == i && (iCommand == Command.EDIT || iCommand == Command.ASK)){				
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmDiseaseType.FRM_FIELD_DISEASE_TYPE] +"\" value=\""+diseaseType.getDiseaseType()+"\" class=\"formElemen\" size=\"70\">");
		 }else{
			rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(diseaseType.getOID())+"')\">"+diseaseType.getDiseaseType()+"</a>");
		 } 
		 lstData.add(rowx);
	}

	rowx = new Vector();
	if(iCommand == Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize() > 0)){ 
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmDiseaseType.FRM_FIELD_DISEASE_TYPE] +"\" value=\""+objEntity.getDiseaseType()+"\" class=\"formElemen\" size=\"50\">");

	}
	lstData.add(rowx);
	return ctrlist.draw();
}
%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidDiseaseType = FRMQueryString.requestLong(request, "hidden_disease_type");

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = "";

CtrlDiseaseType ctrlDiseaseType = new CtrlDiseaseType(request);
ControlLine ctrLine = new ControlLine();
Vector listDiseaseType = new Vector(1,1);

/*switch statement */
iErrCode = ctrlDiseaseType.action(iCommand , oidDiseaseType);
/* end switch*/
FrmDiseaseType frmDiseaseType = ctrlDiseaseType.getForm();

/*count list All DiseaseType*/
int vectSize = PstDiseaseType.getCount(whereClause);

/*switch list DiseaseType*/
if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlDiseaseType.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

DiseaseType diseaseType = ctrlDiseaseType.getDiseaseType();
msgString =  ctrlDiseaseType.getMessage();

/* get record to display */
listDiseaseType = PstDiseaseType.list(start,recordToGet, whereClause , orderClause);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listDiseaseType.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listDiseaseType = PstDiseaseType.list(start,recordToGet, whereClause , orderClause);
}
%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>harisma--</title>
<script language="JavaScript">


function cmdAdd(){
	document.frmdiseasetype.hidden_disease_type.value="0";
	document.frmdiseasetype.command.value="<%=Command.ADD%>";
	document.frmdiseasetype.prev_command.value="<%=prevCommand%>";
	document.frmdiseasetype.action="diseasetype.jsp";
	document.frmdiseasetype.submit();
}

function cmdAsk(oidDiseaseType){
	document.frmdiseasetype.hidden_disease_type.value=oidDiseaseType;
	document.frmdiseasetype.command.value="<%=Command.ASK%>";
	document.frmdiseasetype.prev_command.value="<%=prevCommand%>";
	document.frmdiseasetype.action="diseasetype.jsp";
	document.frmdiseasetype.submit();
}

function cmdConfirmDelete(oidDiseaseType){
	document.frmdiseasetype.hidden_disease_type.value=oidDiseaseType;
	document.frmdiseasetype.command.value="<%=Command.DELETE%>";
	document.frmdiseasetype.prev_command.value="<%=prevCommand%>";
	document.frmdiseasetype.action="diseasetype.jsp";
	document.frmdiseasetype.submit();
}

function cmdSave(){
	document.frmdiseasetype.command.value="<%=Command.SAVE%>";
	document.frmdiseasetype.prev_command.value="<%=prevCommand%>";
	document.frmdiseasetype.action="diseasetype.jsp";
	document.frmdiseasetype.submit();
}

function cmdEdit(oidDiseaseType){
	document.frmdiseasetype.hidden_disease_type.value=oidDiseaseType;
	document.frmdiseasetype.command.value="<%=Command.EDIT%>";
	document.frmdiseasetype.prev_command.value="<%=prevCommand%>";
	document.frmdiseasetype.action="diseasetype.jsp";
	document.frmdiseasetype.submit();
}

function cmdCancel(oidDiseaseType){
	document.frmdiseasetype.hidden_disease_type.value=oidDiseaseType;
	document.frmdiseasetype.command.value="<%=Command.EDIT%>";
	document.frmdiseasetype.prev_command.value="<%=prevCommand%>";
	document.frmdiseasetype.action="diseasetype.jsp";
	document.frmdiseasetype.submit();
}

function cmdBack(){
	document.frmdiseasetype.command.value="<%=Command.BACK%>";
	document.frmdiseasetype.action="diseasetype.jsp";
	document.frmdiseasetype.submit();
}

function cmdListFirst(){
	document.frmdiseasetype.command.value="<%=Command.FIRST%>";
	document.frmdiseasetype.prev_command.value="<%=Command.FIRST%>";
	document.frmdiseasetype.action="diseasetype.jsp";
	document.frmdiseasetype.submit();
}

function cmdListPrev(){
	document.frmdiseasetype.command.value="<%=Command.PREV%>";
	document.frmdiseasetype.prev_command.value="<%=Command.PREV%>";
	document.frmdiseasetype.action="diseasetype.jsp";
	document.frmdiseasetype.submit();
}

function cmdListNext(){
	document.frmdiseasetype.command.value="<%=Command.NEXT%>";
	document.frmdiseasetype.prev_command.value="<%=Command.NEXT%>";
	document.frmdiseasetype.action="diseasetype.jsp";
	document.frmdiseasetype.submit();
}

function cmdListLast(){
	document.frmdiseasetype.command.value="<%=Command.LAST%>";
	document.frmdiseasetype.prev_command.value="<%=Command.LAST%>";
	document.frmdiseasetype.action="diseasetype.jsp";
	document.frmdiseasetype.submit();
}

//-------------- script form image -------------------

function cmdDelPict(oidDiseaseType){
	document.frmimage.hidden_disease_type.value=oidDiseaseType;
	document.frmimage.command.value="<%=Command.POST%>";
	document.frmimage.action="diseasetype.jsp";
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
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
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
</SCRIPT>
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> 
                  Clinic &gt; Disease &gt; Type<!-- #EndEditable --> </strong></font> 
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
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="frmdiseasetype" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="hidden_disease_type" value="<%=oidDiseaseType%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <%
											try{
											%>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <%= drawList(iCommand,frmDiseaseType, diseaseType,listDiseaseType,oidDiseaseType)%> </td>
                                              </tr>
                                              <% 
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
                                                  <% ctrLine.setLocationImg(approot+"/images/ctr_line");
													ctrLine.initDefault();
													 %>
                                                  <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> </span> </td>
                                              </tr>
                                              <%if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT && iCommand != Command.SAVE)&& (frmDiseaseType.errorSize()==0)){%>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <table width="17%" border="0" cellspacing="1" cellpadding="1">
                                                    <tr> 
                                                      <td width="13%"><img src="../../images/BtnNew.jpg" width="24" height="24"></td>
                                                      <td width="87%"><b><a href="javascript:cmdAdd()" class="buttonlink">Add 
                                                        New Disease Type</a></b></td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              <%}%>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top" > 
                                          <td colspan="3" class="command"> 
                                            <%
									ctrLine.setLocationImg(approot+"/images/");
									ctrLine.initDefault();
									ctrLine.setTableWidth("80%");
									String scomDel = "javascript:cmdAsk('"+oidDiseaseType+"')";
									String sconDelCom = "javascript:cmdConfirmDelete('"+oidDiseaseType+"')";
									String scancel = "javascript:cmdEdit('"+oidDiseaseType+"')";
									ctrLine.setCommandStyle("buttonlink");
									ctrLine.setAddCaption("Add New Disease Type");
									ctrLine.setSaveCaption("Save Disease Type");
									ctrLine.setConfirmDelCaption("Yes Delete Disease Type");
									ctrLine.setDeleteCaption("Delete Disease Type");
									ctrLine.setBackCaption("Back to List Disease Type");
									
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
                                            <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%> </td>
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
<%
}else{
%>    
    <script language="javascript">
              window.location="<%=approot%>/inform.jsp?ic=<%= I_SystemInfo.HAVE_NOPRIV%>";
    </script>             
<%
}
%>
