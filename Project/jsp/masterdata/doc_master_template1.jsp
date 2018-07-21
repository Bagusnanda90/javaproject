<%@page import="com.dimata.harisma.form.masterdata.FrmDocMasterExpense"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDocMaster"%>
<%@page import="com.dimata.harisma.entity.masterdata.DocMaster"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.harisma.entity.masterdata.DocType"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDocType"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDocMasterTemplate"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlDocMasterTemplate"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.entity.masterdata.DocMasterTemplate"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmDocMasterTemplate"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<% 
/* 
 * Page Name  		:  DocMasterTemplate.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		: priska
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
<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_PERFORMANCE_APPRAISAL, AppObjInfo.OBJ_GROUP_RANK); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%!

	public String drawList(int iCommand,FrmDocMasterTemplate frmObject, DocMasterTemplate objEntity, Vector objectClass,  long docMasterTemplateId)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
                ctrlist.addHeader("Title / Master Id","10%");
                //ctrlist.addHeader("Master Id","10%");
		//ctrlist.addHeader("Title","30%");
		ctrlist.addHeader("Text","80%");
		//ctrlist.addHeader("Path","30%");
                ctrlist.addHeader("Path / File","10%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		Vector rowx = new Vector(1,1);
		ctrlist.reset();
		int index = -1;

                Vector docmaster_value = new Vector(1, 1);
                Vector docmaster_key = new Vector(1, 1);
                Vector listdocmaster = PstDocMaster.list(0, 0, "", "");
                docmaster_value.add(""+0);
                docmaster_key.add("select");
                for (int i = 0; i < listdocmaster.size(); i++) {
                    DocMaster docMaster = (DocMaster) listdocmaster.get(i);
                    docmaster_key.add(docMaster.getDoc_title());
                    docmaster_value.add(String.valueOf(docMaster.getOID()));
                }
                
		for (int i = 0; i < objectClass.size(); i++) {
			 DocMasterTemplate docMasterTemplate = (DocMasterTemplate)objectClass.get(i);
			 rowx = new Vector();
			 if(docMasterTemplateId == docMasterTemplate.getOID())
				 index = i; 

			 if(index == i && (iCommand == Command.EDIT || iCommand == Command.ASK)){
                                rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(docMasterTemplate.getOID())+"')\"><input type=\"text\" name=\""+frmObject.fieldNames[FrmDocMasterTemplate.FRM_FIELD_TEMPLATE_TITLE] +"\" value=\""+docMasterTemplate.getTemplate_title()+"\" class=\"elemenForm\"> * <input type=\"hidden\" name=\""+frmObject.fieldNames[FrmDocMasterTemplate.FRM_FIELD_DOC_MASTER_ID] +"\" value=\""+objEntity.getDoc_master_id()+"\" class=\"elemenForm\"></a> /"+ControlCombo.draw(frmObject.fieldNames[FrmDocMasterTemplate.FRM_FIELD_DOC_MASTER_ID], "formElemen", null, String.valueOf(docMasterTemplate.getDoc_master_id()), docmaster_value, docmaster_key, "disabled"));			
				//rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmDocMasterTemplate.FRM_FIELD_DOC_MASTER_ID], "formElemen", null, String.valueOf(docMasterTemplate.getDoc_master_id()), docmaster_value, docmaster_key, "disabled"));
                                //rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmDocMasterTemplate.FRM_FIELD_TEMPLATE_TITLE] +"\" value=\""+docMasterTemplate.getTemplate_title()+"\" class=\"elemenForm\"> * <input type=\"hidden\" name=\""+frmObject.fieldNames[FrmDocMasterTemplate.FRM_FIELD_DOC_MASTER_ID] +"\" value=\""+objEntity.getDoc_master_id()+"\" class=\"elemenForm\">");
				rowx.add("<textarea class=\"ckeditor\" name=\""+frmObject.fieldNames[FrmDocMasterTemplate.FRM_FIELD_TEXT_TEMPLATE] +"\" class=\"elemenForm\" rows=\"8\" cols=\"100\">"+docMasterTemplate.getText_template()+"</textarea>");
                               // rowx.add("<textarea  name=\""+frmObject.fieldNames[FrmDocMasterTemplate.FRM_FIELD_TEMPLATE_FILE_NAME] +"\" class=\"elemenForm\" rows=\"3\" cols=\"35\">"+docMasterTemplate.getTemplate_filename()+"</textarea>");
                                rowx.add("<textarea  name=\""+frmObject.fieldNames[FrmDocMasterTemplate.FRM_FIELD_TEMPLATE_FILE_NAME] +"\" class=\"elemenForm\" rows=\"2\" cols=\"15\">"+docMasterTemplate.getTemplate_filename()+"</textarea><img border=\"0\" src=\"../images/BtnNew.jpg\" width=\"20\" height=\"20\" ><div  valign =\"top\" align=\"center\"><a style=\"text-decoration:none\" href =\"javascript:cmdAttach('"+docMasterTemplate.getOID()+"','"+docMasterTemplate.getDoc_master_id()+"')\"><font color=\"#30009D\">Attach File</font></a></div>");
		
                         
                         }else{
                             
                                rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(docMasterTemplate.getOID())+"')\">"+docMasterTemplate.getTemplate_title()+"</a> / "+ControlCombo.draw(frmObject.fieldNames[FrmDocMasterTemplate.FRM_FIELD_DOC_MASTER_ID], "formElemen", null, String.valueOf(docMasterTemplate.getDoc_master_id()), docmaster_value, docmaster_key, "disabled"));
                                //rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmDocMasterTemplate.FRM_FIELD_DOC_MASTER_ID], "formElemen", null, String.valueOf(docMasterTemplate.getDoc_master_id()), docmaster_value, docmaster_key, "disabled"));
                               // rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmDocMasterTemplate.FRM_FIELD_TEMPLATE_TITLE] +"\" value=\""+docMasterTemplate.getTemplate_title()+"\" class=\"elemenForm\"> * <input type=\"hidden\" name=\""+frmObject.fieldNames[FrmDocMasterTemplate.FRM_FIELD_DOC_MASTER_ID] +"\" value=\""+objEntity.getDoc_master_id()+"\" class=\"elemenForm\">");
				rowx.add("<textarea class=\"ckeditor\" name=\""+frmObject.fieldNames[FrmDocMasterTemplate.FRM_FIELD_TEXT_TEMPLATE] +"\" class=\"elemenForm\" rows=\"3\" disabled cols=\"35\">"+docMasterTemplate.getText_template()+"</textarea>");
                               // rowx.add("<textarea  disabled=\"disabled\" name=\""+frmObject.fieldNames[FrmDocMasterTemplate.FRM_FIELD_TEMPLATE_FILE_NAME] +"\" class=\"elemenForm\" rows=\"3\" cols=\"35\">"+docMasterTemplate.getTemplate_filename()+"</textarea>");
                                rowx.add("<textarea  disabled=\"disabled\" name=\""+frmObject.fieldNames[FrmDocMasterTemplate.FRM_FIELD_TEMPLATE_FILE_NAME] +"\" class=\"elemenForm\" rows=\"3\" cols=\"35\">"+docMasterTemplate.getTemplate_filename()+"</textarea><a href=\"javascript:cmdOpen('"+String.valueOf(docMasterTemplate.getTemplate_filename())+"')\">download</a>");
			} 

			lstData.add(rowx);
		}

		 rowx = new Vector();

		if(iCommand == Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize() > 0)){ 
                                rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmDocMasterTemplate.FRM_FIELD_TEMPLATE_TITLE] +"\" value=\""+objEntity.getTemplate_title()+"\" class=\"elemenForm\"> * <input type=\"hidden\" name=\""+frmObject.fieldNames[FrmDocMasterTemplate.FRM_FIELD_DOC_MASTER_ID] +"\" value=\""+objEntity.getDoc_master_id()+"\" class=\"elemenForm\"> / "+ControlCombo.draw(frmObject.fieldNames[FrmDocMasterTemplate.FRM_FIELD_DOC_MASTER_ID], "formElemen", null, String.valueOf(objEntity.getDoc_master_id()), docmaster_value, docmaster_key, "disabled"));
				//rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmDocMasterTemplate.FRM_FIELD_DOC_MASTER_ID], "formElemen", null, String.valueOf(objEntity.getDoc_master_id()), docmaster_value, docmaster_key, "disabled"));
                                rowx.add("<textarea class=\"ckeditor\" name=\""+frmObject.fieldNames[FrmDocMasterTemplate.FRM_FIELD_TEXT_TEMPLATE] +"\" class=\"elemenForm\" rows=\"3\" cols=\"35\">"+objEntity.getText_template()+"</textarea>");
                                rowx.add("<textarea name=\""+frmObject.fieldNames[FrmDocMasterTemplate.FRM_FIELD_TEMPLATE_FILE_NAME] +"\" class=\"elemenForm\" rows=\"3\" cols=\"35\">"+objEntity.getTemplate_filename()+"</textarea>");
                                //rowx.add("");
		
                }

		lstData.add(rowx);

		return ctrlist.draw();
	}

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidDocMasterTemplate = FRMQueryString.requestLong(request, "doc_master_template_oid");
long oidDocMaster = FRMQueryString.requestLong(request, "DocMasterId");



if ((oidDocMaster !=0) && (oidDocMaster>0)  ) {
    session.putValue("SELECTED_DOC_MASTER_ID", oidDocMaster);
} else {
     try{
      oidDocMaster = ((Long)session.getValue("SELECTED_DOC_MASTER_ID"));
     }catch(Exception e) {
     }
}


/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = "";



CtrlDocMasterTemplate ctrlDocMasterTemplate = new CtrlDocMasterTemplate(request);
ControlLine ctrLine = new ControlLine();
Vector listDocMasterTemplate = new Vector(1,1);


/* end switch */
FrmDocMasterTemplate frmDocMasterTemplate = ctrlDocMasterTemplate.getForm();

long sdocmasterId = FRMQueryString.requestLong(request, FrmDocMasterTemplate.fieldNames[FrmDocMasterTemplate.FRM_FIELD_DOC_MASTER_ID]);

if (oidDocMaster > 0){
    whereClause = PstDocMasterTemplate.fieldNames[PstDocMasterTemplate.FLD_DOC_MASTER_ID] +" = " + oidDocMaster;
} else {
    whereClause = PstDocMasterTemplate.fieldNames[PstDocMasterTemplate.FLD_DOC_MASTER_ID] +" = " + sdocmasterId;
}
listDocMasterTemplate = PstDocMasterTemplate.list(start,recordToGet, whereClause , orderClause);

DocMasterTemplate docMasterTemplateObj = new DocMasterTemplate();
if (listDocMasterTemplate.size() > 0){
 
    try {
        docMasterTemplateObj = (DocMasterTemplate) listDocMasterTemplate.get(0);
    } catch (Exception e){
    }
}
oidDocMasterTemplate = docMasterTemplateObj.getOid();
iErrCode = ctrlDocMasterTemplate.action(iCommand , oidDocMasterTemplate);
/*count list All DocMasterTemplate*/
int vectSize = PstDocMasterTemplate.getCount(whereClause);

/*switch list DocMasterTemplate*/
if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlDocMasterTemplate.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

DocMasterTemplate docMasterTemplate = ctrlDocMasterTemplate.getdDocMasterTemplate();
msgString =  ctrlDocMasterTemplate.getMessage();

/* get record to display */

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listDocMasterTemplate.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listDocMasterTemplate = PstDocMasterTemplate.list(start,recordToGet, whereClause , orderClause);
}
     if (oidDocMaster > 0){
                             docMasterTemplate.setDoc_master_id(oidDocMaster);
                         } else {
                             docMasterTemplate.setDoc_master_id(sdocmasterId);
                         }

%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Doc Type</title>
<script language="JavaScript">
function cmdOpen(fileName){
		window.open("<%=approot%>/imgdoc/"+fileName , null);
		
}


function cmdAttach(doc_master_template_Id, doc_master_Id){
	window.open("upload.jsp?command="+<%=Command.EDIT%>+"&doc_master_template_id=" + doc_master_template_Id + "&doc_master_id=" + doc_master_Id , null, "height=400,width=600,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes");
}
function cmdAsk(oidDocMasterTemplate){
	document.frmDocMasterTemplate.doc_master_template_oid.value=oidDocMasterTemplate;
	document.frmDocMasterTemplate.command.value="<%=Command.ASK%>";
	document.frmDocMasterTemplate.prev_command.value="<%=prevCommand%>";
	document.frmDocMasterTemplate.action="doc_master_template.jsp";
	document.frmDocMasterTemplate.submit();
}

function cmdFlow(DocMasterTemplateId){
	window.open("<%=approot%>/masterdata/DocMasterTemplateFlow.jsp?DocMasterTemplateId="+DocMasterTemplateId+"", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
}
function cmdTemplate(DocMasterTemplateId){
	var newWin =window.open("<%=approot%>/masterdata/DocMasterTemplateTemplate.jsp?DocMasterTemplateId="+DocMasterTemplateId+"", "DocTemplate", "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
        newWin.focus();
}
function cmdExpense(DocMasterTemplateId){
	window.open("<%=approot%>/masterdata/DocMasterTemplateExpense.jsp?DocMasterTemplateId="+DocMasterTemplateId+"", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
}

function cmdConfirmDelete(oidDocMasterTemplate){
	document.frmDocMasterTemplate.doc_master_template_oid.value=oidDocMasterTemplate;
	document.frmDocMasterTemplate.command.value="<%=Command.DELETE%>";
	document.frmDocMasterTemplate.prev_command.value="<%=prevCommand%>";
	document.frmDocMasterTemplate.action="doc_master_template.jsp";
	document.frmDocMasterTemplate.submit();
}

function cmdSave(){
	document.frmDocMasterTemplate.command.value="<%=Command.SAVE%>";
	document.frmDocMasterTemplate.prev_command.value="<%=prevCommand%>";
	document.frmDocMasterTemplate.action="doc_master_template.jsp";
	document.frmDocMasterTemplate.submit();
}

function cmdEdit(oidDocMasterTemplate){
	document.frmDocMasterTemplate.doc_master_template_oid.value=oidDocMasterTemplate;
	document.frmDocMasterTemplate.command.value="<%=Command.EDIT%>";
	document.frmDocMasterTemplate.prev_command.value="<%=prevCommand%>";
	document.frmDocMasterTemplate.action="doc_master_template.jsp";
	document.frmDocMasterTemplate.submit();
}

function cmdCancel(oidDocMasterTemplate){
	document.frmDocMasterTemplate.doc_master_template_oid.value=oidDocMasterTemplate;
	document.frmDocMasterTemplate.command.value="<%=Command.EDIT%>";
	document.frmDocMasterTemplate.prev_command.value="<%=prevCommand%>";
	document.frmDocMasterTemplate.action="doc_master_template.jsp";
	document.frmDocMasterTemplate.submit();
}

function cmdBack(){
	document.frmDocMasterTemplate.command.value="<%=Command.BACK%>";
	document.frmDocMasterTemplate.action="doc_master_template.jsp";
	document.frmDocMasterTemplate.submit();
}

function cmdListFirst(){
	document.frmDocMasterTemplate.command.value="<%=Command.FIRST%>";
	document.frmDocMasterTemplate.prev_command.value="<%=Command.FIRST%>";
	document.frmDocMasterTemplate.action="doc_master_template.jsp";
	document.frmDocMasterTemplate.submit();
}

function cmdListPrev(){
	document.frmDocMasterTemplate.command.value="<%=Command.PREV%>";
	document.frmDocMasterTemplate.prev_command.value="<%=Command.PREV%>";
	document.frmDocMasterTemplate.action="doc_master_template.jsp";
	document.frmDocMasterTemplate.submit();
}

function cmdListNext(){
	document.frmDocMasterTemplate.command.value="<%=Command.NEXT%>";
	document.frmDocMasterTemplate.prev_command.value="<%=Command.NEXT%>";
	document.frmDocMasterTemplate.action="doc_master_template.jsp";
	document.frmDocMasterTemplate.submit();
}

function cmdListLast(){
	document.frmDocMasterTemplate.command.value="<%=Command.LAST%>";
	document.frmDocMasterTemplate.prev_command.value="<%=Command.LAST%>";
	document.frmDocMasterTemplate.action="doc_master_template.jsp";
	document.frmDocMasterTemplate.submit();
}

function cmdListCateg(oidDocMasterTemplate){
	document.frmDocMasterTemplate.doc_master_template_oid.value=oidDocMasterTemplate;
	document.frmDocMasterTemplate.command.value="<%=Command.LIST%>";
	document.frmDocMasterTemplate.action="doc_master_template.jsp";
	document.frmdoctype.submit();
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

<script src="../styles/ckeditor/ckeditor.js"></script>
</head> 

<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%//@include file="../styletemplate/template_header.jsp" %>
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
			  <!-- #BeginEditable "contenttitle" -->Masterdata 
                  &gt; DocMasterTemplate<!-- #EndEditable --> 
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
                                    <form name="frmDocMasterTemplate" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="doc_master_template_oid" value="<%=oidDocMasterTemplate%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">                                              
                                              <tr align="left" valign="top"> 
                                                <td colspan="3" >
                                                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    
                                                    <tr> 
                                                      <td class="listtitle" width="37%">Doc Master Template *)entry required </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="8" align="left" colspan="3" class="command"> 
                                                  <span class="command"> 
                                                  
                                                  </span> </td>
                                              </tr>
                                              <tr align="left" valign="top">
                                                    <td valign="top" width="17%"> Doc Master Template Title</td>
                                                    <td width="83%">
                                                       <input type="text" name="<%=frmDocMasterTemplate.fieldNames[frmDocMasterTemplate.FRM_FIELD_TEMPLATE_TITLE]%>"  value="<%= docMasterTemplateObj.getTemplate_title()%>" class="elemenForm" size="30">
                                                       *<%=frmDocMasterTemplate.getErrorMsg(frmDocMasterTemplate.FRM_FIELD_TEMPLATE_TITLE)%>
                                                    </td>
                                              </tr>
                                              
                                              <tr align="left" valign="top">
                                                    <td valign="top" width="17%"> Doc Master </td>
                                                    <td width="83%">
                                                                 <%
                                                    Vector docmaster_value = new Vector(1, 1);
                                                    Vector docmaster_key = new Vector(1, 1);
                                                    Vector listdocmaster = PstDocMaster.list(0, 0, "", "");
                                                    docmaster_value.add(""+0);
                                                    docmaster_key.add("select");
                                                    for (int i = 0; i < listdocmaster.size(); i++) {
                                                        DocMaster docMaster = (DocMaster) listdocmaster.get(i);
                                                        docmaster_key.add(docMaster.getDoc_title());
                                                        docmaster_value.add(String.valueOf(docMaster.getOID()));
                                                    }
                                                                                                                                    
                                                                                                                                    %>
                                                       <!-- Priska_2015 08 28 -->
                                                       <%= ControlCombo.draw(frmDocMasterTemplate.fieldNames[frmDocMasterTemplate.FRM_FIELD_DOC_MASTER_ID], "formElemen", null, "" + (docMasterTemplate.getDoc_master_id()!=0?docMasterTemplate.getDoc_master_id():"-"), docmaster_value, docmaster_key, "")%>
                                                    </td>
                                              </tr>
                                             
                                             <tr align="left" valign="top">
                                                    <td valign="top" width="17%"> Doc Master Template Detail</td>
                                                    <td width="83%">
                                                        <textarea class="ckeditor" name="<%=frmDocMasterTemplate.fieldNames[frmDocMasterTemplate.FRM_FIELD_TEXT_TEMPLATE]%>" class="elemenForm" cols="70" rows="40"><%= docMasterTemplateObj.getText_template()%></textarea>
                                                    </td>
                                             </tr>
                                             <tr align="left" valign="top"> 
                                              <td height="8" valign="middle" width="17%">&nbsp;</td>
                                              <td height="8" colspan="2" width="83%">&nbsp; 
                                              </td>
                                            </tr>
                                             <tr align="left" valign="top">
                                                    <td valign="top" width="17%"></td>
                                                    <td width="83%">
                                                        <a href="javascript:cmdSave()" class="command">SAVE</a>
                                                    </td>
                                             </tr> 
                                             
                                            </table>
                                          </td>										  
                                        </tr>
                                        
                                        <tr align="left" valign="top"> 
                                          <td height="8" valign="middle" width="17%">&nbsp;</td>
                                          <td height="8" colspan="2" width="83%">&nbsp; 
                                          </td>
                                        </tr>
                                        
                                        <tr align="left" valign="top" > 
                                          <td colspan="3" class="command" height="26"> 
                                            
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
                                <%@include file="../../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
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
