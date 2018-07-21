
<% 
/* 
 * Page Name  		:  med_type.jsp
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
<!--package HRIS -->
<%@ page import = "com.dimata.harisma.entity.clinic.*" %>
<%@ page import = "com.dimata.harisma.form.clinic.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_CLINIC, AppObjInfo.G2_MEDICAL, AppObjInfo.OBJ_MEDICAL_TYPE); %>
<%//@ include file = "::...error, can't generate level, level = 0..::main/checkuser.jsp" %>
<%/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/

boolean privAdd=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
boolean privUpdate=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
boolean privDelete=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
long hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
boolean isHRDLogin = hrdDepartmentOid == departmentOid ? true : false;

if(isHRDLogin){
%>
<!-- Jsp Block -->
<%!

	public String drawList(int iCommand,FrmMedicalType frmObject, MedicalType objEntity, Vector objectClass,  long medicalTypeId, int start)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("90%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader(" ","5%");
		ctrlist.addHeader("Group","25%");
		ctrlist.addHeader("Code","15%");
		ctrlist.addHeader("Name","30%");
		ctrlist.addHeader("Yearly Budget","25%");

		ctrlist.setLinkRow(1);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		Vector rowx = new Vector(1,1);
		ctrlist.reset();
		int index = -1;
		
		Vector groupValue = new Vector(1,1);
		Vector groupKey = new Vector(1,1);
		Vector listMedicalGroup = PstMedExpenseType.listAll();
		for(int g=0;g<listMedicalGroup.size();g++){
			MedExpenseType 	medExpenseType  =  (MedExpenseType)listMedicalGroup.get(g);
			groupValue.add(""+medExpenseType.getOID());
			groupKey.add(medExpenseType.getType());
		}
		
		for (int i = 0; i < objectClass.size(); i++) {
			 MedicalType medicalType = (MedicalType)objectClass.get(i);
			 rowx = new Vector();
			 if(medicalTypeId == medicalType.getOID())
				 index = i; 
				 
			 String whereClause = PstMedExpenseType.fieldNames[PstMedExpenseType.FLD_MEDICINE_EXPENSE_TYPE_ID]+ " = "+medicalType.getMedExpenseTypeId();
			 Vector group =PstMedExpenseType.list(0,1,whereClause,"");
			 String medGroup = "";
			 if(group != null && group.size()>0){
				MedExpenseType 	medExpenseType  =  (MedExpenseType)group.get(0);
				medGroup = medExpenseType.getType();
			 }
			 
			 rowx.add(""+(start+(i+1)));
			 if(index == i && (iCommand == Command.EDIT || iCommand == Command.ASK)){
				
				rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmMedicalType.FRM_FIELD_MED_EXPENSE_TYPE_ID],"formElemen",null,""+medicalType.getMedExpenseTypeId(),groupValue,groupKey));	
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmMedicalType.FRM_FIELD_TYPE_CODE] +"\" value=\""+medicalType.getTypeCode()+"\" class=\"formElemen\" size=\"15\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmMedicalType.FRM_FIELD_TYPE_NAME] +"\" value=\""+medicalType.getTypeName()+"\" class=\"formElemen\" size=\"40\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmMedicalType.FRM_FIELD_YEARLY_AMOUNT] +"\" value=\""+Formater.formatNumber(medicalType.getYearlyAmount(),"###.##")+"\" class=\"formElemen\" size=\"40\">");
			}else{
				rowx.add(medGroup);
				rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(medicalType.getOID())+"')\">"+medicalType.getTypeCode()+"</a>");
				rowx.add(medicalType.getTypeName());
				rowx.add(Formater.formatNumber(medicalType.getYearlyAmount(),"#,###.##"));
			} 

			lstData.add(rowx);
		}

		 rowx = new Vector();

		if(iCommand == Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize() > 0)){ 
				rowx.add(""+(start+objectClass.size()));
				rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmMedicalType.FRM_FIELD_MED_EXPENSE_TYPE_ID],"formElemen",null,""+objEntity.getMedExpenseTypeId(),groupValue,groupKey));	
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmMedicalType.FRM_FIELD_TYPE_CODE] +"\" value=\""+objEntity.getTypeCode()+"\" class=\"formElemen\" size=\"15\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmMedicalType.FRM_FIELD_TYPE_NAME] +"\" value=\""+objEntity.getTypeName()+"\" class=\"formElemen\" size=\"40\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmMedicalType.FRM_FIELD_YEARLY_AMOUNT] +"\" value=\""+objEntity.getYearlyAmount()+"\" class=\"formElemen\" size=\"40\">");

		}

		lstData.add(rowx);

		return ctrlist.draw();
	}

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidMedicalType = FRMQueryString.requestLong(request, "hidden_medical_type_id");

//out.println("oidMedicalType : "+oidMedicalType);

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = PstMedicalType.fieldNames[PstMedicalType.FLD_MED_EXPENSE_TYPE_ID];

CtrlMedicalType ctrlMedicalType = new CtrlMedicalType(request);
ControlLine ctrLine = new ControlLine();
Vector listMedicalType = new Vector(1,1);

/*switch statement */
iErrCode = ctrlMedicalType.action(iCommand , oidMedicalType);
/* end switch*/
FrmMedicalType frmMedicalType = ctrlMedicalType.getForm();

/*count list All MedicalType*/
int vectSize = PstMedicalType.getCount(whereClause);

/*switch list MedicalType*/
if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlMedicalType.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

MedicalType medicalType = ctrlMedicalType.getMedicalType();
msgString =  ctrlMedicalType.getMessage();

/* get record to display */
listMedicalType = PstMedicalType.list(start,recordToGet, whereClause , orderClause);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listMedicalType.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listMedicalType = PstMedicalType.list(start,recordToGet, whereClause , orderClause);
}
%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Medical Type</title>
<script language="JavaScript">


function cmdAdd(){
	document.frmmedicaltype.hidden_medical_type_id.value="0";
	document.frmmedicaltype.command.value="<%=Command.ADD%>";
	document.frmmedicaltype.prev_command.value="<%=prevCommand%>";
	document.frmmedicaltype.action="med_type.jsp";
	document.frmmedicaltype.submit();
}

function cmdAsk(oidMedicalType){
	document.frmmedicaltype.hidden_medical_type_id.value=oidMedicalType;
	document.frmmedicaltype.command.value="<%=Command.ASK%>";
	document.frmmedicaltype.prev_command.value="<%=prevCommand%>";
	document.frmmedicaltype.action="med_type.jsp";
	document.frmmedicaltype.submit();
}

function cmdConfirmDelete(oidMedicalType){
	document.frmmedicaltype.hidden_medical_type_id.value=oidMedicalType;
	document.frmmedicaltype.command.value="<%=Command.DELETE%>";
	document.frmmedicaltype.prev_command.value="<%=prevCommand%>";
	document.frmmedicaltype.action="med_type.jsp";
	document.frmmedicaltype.submit();
}

function cmdSave(){
	document.frmmedicaltype.command.value="<%=Command.SAVE%>";
	document.frmmedicaltype.prev_command.value="<%=prevCommand%>";
	document.frmmedicaltype.action="med_type.jsp";
	document.frmmedicaltype.submit();
}

function cmdEdit(oidMedicalType){
	document.frmmedicaltype.hidden_medical_type_id.value=oidMedicalType;
	document.frmmedicaltype.command.value="<%=Command.EDIT%>";
	document.frmmedicaltype.prev_command.value="<%=prevCommand%>";
	document.frmmedicaltype.action="med_type.jsp";
	document.frmmedicaltype.submit();
}

function cmdCancel(oidMedicalType){
	document.frmmedicaltype.hidden_medical_type_id.value=oidMedicalType;
	document.frmmedicaltype.command.value="<%=Command.EDIT%>";
	document.frmmedicaltype.prev_command.value="<%=prevCommand%>";
	document.frmmedicaltype.action="med_type.jsp";
	document.frmmedicaltype.submit();
}

function cmdBack(){
	document.frmmedicaltype.command.value="<%=Command.BACK%>";
	document.frmmedicaltype.action="med_type.jsp";
	document.frmmedicaltype.submit();
}

function cmdListFirst(){
	document.frmmedicaltype.command.value="<%=Command.FIRST%>";
	document.frmmedicaltype.prev_command.value="<%=Command.FIRST%>";
	document.frmmedicaltype.action="med_type.jsp";
	document.frmmedicaltype.submit();
}

function cmdListPrev(){
	document.frmmedicaltype.command.value="<%=Command.PREV%>";
	document.frmmedicaltype.prev_command.value="<%=Command.PREV%>";
	document.frmmedicaltype.action="med_type.jsp";
	document.frmmedicaltype.submit();
}

function cmdListNext(){
	document.frmmedicaltype.command.value="<%=Command.NEXT%>";
	document.frmmedicaltype.prev_command.value="<%=Command.NEXT%>";
	document.frmmedicaltype.action="med_type.jsp";
	document.frmmedicaltype.submit();
}

function cmdListLast(){
	document.frmmedicaltype.command.value="<%=Command.LAST%>";
	document.frmmedicaltype.prev_command.value="<%=Command.LAST%>";
	document.frmmedicaltype.action="med_type.jsp";
	document.frmmedicaltype.submit();
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
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Medical > Type
				<!-- #EndEditable --> </strong></font> 
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
                                    <form name="frmmedicaltype" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="hidden_medical_type_id" value="<%=oidMedicalType%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td height="8" valign="middle" colspan="3">&nbsp; 
                                                </td>
                                              </tr>
											  <% if((listMedicalType == null || listMedicalType.size()<1)&& (iCommand == Command.NONE)){%>
                                              <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="comment">&nbsp; No Medical 
                                                  Type available </td>
                                              </tr>
											  <%}else{%>
											  <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;Medical 
                                                  Type List </td>
                                              </tr>
                                              <%
												try{ 
												%>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <%= drawList(iCommand,frmMedicalType, medicalType,listMedicalType,oidMedicalType,start)%> 
                                                </td>
                                              </tr>
                                              <% 
												  }catch(Exception exc){ 
												  }
											 } %>
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
                                                      <td width="24" height="23"><a href="javascript:cmdList()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Get List"></a></td>
                                                      <td width="6" height="23"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td height="23" valign="middle" colspan="3" width="951"><a href="javascript:cmdAdd()" class="command">Add 
                                                        New Medical Type</a> 
                                                      </td>
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
									String scomDel = "javascript:cmdAsk('"+oidMedicalType+"')";
									String sconDelCom = "javascript:cmdConfirmDelete('"+oidMedicalType+"')";
									String scancel = "javascript:cmdEdit('"+oidMedicalType+"')";
									ctrLine.setBackCaption("Back to List Medical Type");
									ctrLine.setCommandStyle("buttonlink");
									ctrLine.setConfirmDelCaption("Yes Delete Medical Type");
									ctrLine.setDeleteCaption("Delete Medical Type");
									ctrLine.setSaveCaption("Save Medical Type");
									ctrLine.setAddCaption("Add Medical Type");

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
