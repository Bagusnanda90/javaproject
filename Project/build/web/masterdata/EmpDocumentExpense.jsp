<%@page import="java.text.DecimalFormatSymbols"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstEmpDoc"%>
<%@page import="com.dimata.harisma.entity.masterdata.EmpDoc"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.harisma.entity.masterdata.DocMasterExpense"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDocMasterExpense"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstEmpDocExpenses"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlEmpDocExpenses"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.entity.masterdata.EmpDocExpenses"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmEmpDocExpenses"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<% 
/* 
 * Page Name  		:  EmpDocExpenses.jsp
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

	public String drawList(int iCommand,FrmEmpDocExpenses frmObject, EmpDocExpenses objEntity, Vector objectClass,  long empDocExpensesId, long oidEmpDoc, long docMasterExpenseOid)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("80%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("Action","5%");
                ctrlist.addHeader("Master Expenses","30%");
		ctrlist.addHeader("Budget Value","20%");
		ctrlist.addHeader("Real Value","20%");
		ctrlist.addHeader("Expense Unit","10%");
		ctrlist.addHeader("Total","30%");
		ctrlist.addHeader("Description","40%");
		ctrlist.addHeader("NOTE","40%");
		
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		Vector rowx = new Vector(1,1);
		ctrlist.reset();
		int index = -1;

                EmpDoc empDoc = new EmpDoc();
                try {
                    empDoc = PstEmpDoc.fetchExc(oidEmpDoc);
                } catch (Exception e){

                }
                
                                
                DecimalFormat df = (DecimalFormat) DecimalFormat.getCurrencyInstance();
                    DecimalFormatSymbols dfs = new DecimalFormatSymbols();
                    dfs.setCurrencySymbol("");
                    dfs.setMonetaryDecimalSeparator(',');
                    dfs.setGroupingSeparator('.');
                    df.setDecimalFormatSymbols(dfs);
                
                Vector docMasterExpense_value = new Vector(1, 1);
                Vector docMasterExpense_key = new Vector(1, 1);
                String where1 = PstDocMasterExpense.fieldNames[PstDocMasterExpense.FLD_DOC_MASTER_ID] + " = "+ empDoc.getDoc_master_id();
                Vector listdocMasterExpense = PstDocMasterExpense.list(0, 0, where1, "");
                docMasterExpense_value.add(""+0);
                docMasterExpense_key.add("select");
                for (int i = 0; i < listdocMasterExpense.size(); i++) {
                    DocMasterExpense docMasterExpense = (DocMasterExpense) listdocMasterExpense.get(i);
                    docMasterExpense_key.add(docMasterExpense.getUnit_name());
                    docMasterExpense_value.add(String.valueOf(docMasterExpense.getOID()));
                }
                DocMasterExpense docMasterExpense = new DocMasterExpense();
                if (docMasterExpenseOid > 0){
                    
                }
                    
		for (int i = 0; i < objectClass.size(); i++) {
			 EmpDocExpenses empDocExpenses = (EmpDocExpenses)objectClass.get(i);
			 rowx = new Vector();
			 if(empDocExpensesId == empDocExpenses.getOID())
				 index = i; 

			 if(index == i && (iCommand == Command.EDIT || iCommand == Command.ASK)){
                                rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(empDocExpenses.getOID())+"')\">EDIT</a><input type=\"hidden\" name=\""+frmObject.fieldNames[FrmEmpDocExpenses.FRM_FIELD_DOC_ID] +"\" value=\""+oidEmpDoc+"\" class=\"elemenForm\">");			
				//rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmEmpDocExpenses.FRM_FIELD_DOC_MASTER_EXPENSE_ID] +"\" value=\""+empDocExpenses.getDocMasterExpenseId()+"\" class=\"elemenForm\"> * ");
				rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmEmpDocExpenses.FRM_FIELD_DOC_MASTER_EXPENSE_ID], "formElemen", null, String.valueOf(empDocExpenses.getDocMasterExpenseId()), docMasterExpense_value, docMasterExpense_key, ""));
                                rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmEmpDocExpenses.FRM_FIELD_BUDGET_VALUE] +"\" value=\""+empDocExpenses.getBudgetValue()+"\" class=\"elemenForm\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmEmpDocExpenses.FRM_FIELD_REAL_VALUE] +"\" value=\""+empDocExpenses.getRealvalue()+"\" class=\"elemenForm\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmEmpDocExpenses.FRM_FIELD_EXPENSE_UNIT] +"\" value=\""+empDocExpenses.getExpenseUnit()+"\" class=\"elemenForm\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmEmpDocExpenses.FRM_FIELD_TOTAL] +"\" value=\""+empDocExpenses.getTotal()+"\" class=\"elemenForm\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmEmpDocExpenses.FRM_FIELD_DESCRIPTION] +"\" value=\""+empDocExpenses.getDescription()+"\" class=\"elemenForm\">");
				rowx.add("<textarea name=\""+frmObject.fieldNames[FrmEmpDocExpenses.FRM_FIELD_NOTE] +"\" class=\"elemenForm\" rows=\"3\" cols=\"35\">"+empDocExpenses.getNote()+"</textarea>");
			
                         }else{
				rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(empDocExpenses.getOID())+"')\">EDIT</a><input type=\"hidden\" name=\""+frmObject.fieldNames[FrmEmpDocExpenses.FRM_FIELD_DOC_ID] +"\" value=\""+oidEmpDoc+"\" class=\"elemenForm\">");
                                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmEmpDocExpenses.FRM_FIELD_DOC_MASTER_EXPENSE_ID], "formElemen", null, String.valueOf(empDocExpenses.getDocMasterExpenseId()), docMasterExpense_value, docMasterExpense_key, ""));
                                rowx.add("Rp. " + df.format(empDocExpenses.getBudgetValue()));
				rowx.add("Rp. " + df.format(empDocExpenses.getRealvalue()));
				rowx.add(""+empDocExpenses.getExpenseUnit());
				rowx.add("Rp. " + df.format(empDocExpenses.getTotal()));
                                rowx.add(""+empDocExpenses.getDescription());
				rowx.add(""+empDocExpenses.getNote());
			
			} 

			lstData.add(rowx);
		}

		 rowx = new Vector();

		if(iCommand == Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize() > 0)){ 
		
                                rowx.add("-<input type=\"hidden\" name=\""+frmObject.fieldNames[FrmEmpDocExpenses.FRM_FIELD_DOC_ID] +"\" value=\""+oidEmpDoc+"\" class=\"elemenForm\">");
                               // rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmEmpDocExpenses.FRM_FIELD_DOC_ID] +"\" value=\""+objEntity.getDocMasterExpenseId()+"\" class=\"elemenForm\">");
				rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmEmpDocExpenses.FRM_FIELD_DOC_MASTER_EXPENSE_ID], "formElemen", null, String.valueOf(docMasterExpenseOid), docMasterExpense_value, docMasterExpense_key, "onChange=\"javascript:cmdChange('"+0+"','"+docMasterExpense_value+"')\""));
                                rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmEmpDocExpenses.FRM_FIELD_BUDGET_VALUE] +"\" value=\""+objEntity.getBudgetValue()+"\" class=\"elemenForm\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmEmpDocExpenses.FRM_FIELD_REAL_VALUE] +"\" value=\""+objEntity.getRealvalue()+"\" class=\"elemenForm\"> * ");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmEmpDocExpenses.FRM_FIELD_EXPENSE_UNIT] +"\" value=\""+objEntity.getExpenseUnit()+"\" class=\"elemenForm\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmEmpDocExpenses.FRM_FIELD_TOTAL] +"\" value=\""+objEntity.getTotal()+"\" class=\"elemenForm\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmEmpDocExpenses.FRM_FIELD_DESCRIPTION] +"\" value=\""+objEntity.getDescription()+"\" class=\"elemenForm\">");
				rowx.add("<textarea name=\""+frmObject.fieldNames[FrmEmpDocExpenses.FRM_FIELD_NOTE] +"\" class=\"elemenForm\" rows=\"3\" cols=\"35\">"+objEntity.getNote()+"</textarea>");
			
                                
		}

		lstData.add(rowx);

		return ctrlist.draw();
	}

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidEmpDocExpenses = FRMQueryString.requestLong(request, "doc_expenses_oid");

long oidEmpDoc = FRMQueryString.requestLong(request,"oidEmpDoc");
long docMasterExpenseOid = FRMQueryString.requestLong(request, "docMasterExpenseOid");
/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = PstEmpDocExpenses.fieldNames[PstEmpDocExpenses.FLD_EMP_DOC_ID]+" = "+oidEmpDoc;
String orderClause = "";

CtrlEmpDocExpenses ctrlEmpDocExpenses = new CtrlEmpDocExpenses(request);
ControlLine ctrLine = new ControlLine();
Vector listEmpDocExpenses = new Vector(1,1);

iErrCode = ctrlEmpDocExpenses.action(iCommand , oidEmpDocExpenses);
/* end switch*/
FrmEmpDocExpenses frmEmpDocExpenses = ctrlEmpDocExpenses.getForm();

/*count list All GroupRank*/
int vectSize = PstEmpDocExpenses.getCount(whereClause);

/*switch list GroupRank*/
if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlEmpDocExpenses.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

EmpDocExpenses empDocExpenses = ctrlEmpDocExpenses.getdEmpDocExpenses();
msgString =  ctrlEmpDocExpenses.getMessage();

/* get record to display */
listEmpDocExpenses = PstEmpDocExpenses.list(start,recordToGet, whereClause , orderClause);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listEmpDocExpenses.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listEmpDocExpenses = PstEmpDocExpenses.list(start,recordToGet, whereClause , orderClause);
}
if (listEmpDocExpenses.size() == 0 ){
    iCommand = Command.ADD;
}

EmpDoc empDoc = new EmpDoc();
try {
    empDoc = PstEmpDoc.fetchExc(oidEmpDoc);
} catch (Exception e){
    
}

 if (listEmpDocExpenses.size() ==0 ){
         iCommand = Command.ADD;
     } 
     if (iCommand == Command.DELETE || iCommand == Command.SAVE  ){
         iCommand = Command.BACK;
     } 
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Doc Expenses</title>
<script language="JavaScript">

 
function cmdChange1(oidEmpDocExpenses,  docMasterExpenseOid){
	document.frmEmpDocExpenses.doc_expenses_oid.value=oidEmpDocExpenses;
	document.frmEmpDocExpenses.command.value="<%=Command.EDIT%>";
	document.frmEmpDocExpenses.prev_command.value="<%=prevCommand%>";
	document.frmEmpDocExpenses.action="EmpDocumentExpense.jsp?docMasterExpenseOid="+docMasterExpenseOid;
	document.frmEmpDocExpenses.submit();
}

function cmdChange(oidEmpDocExpenses,  docMasterExpenseOid){
                document.frm_employee.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frm_employee.action="EmpDocumentExpense.jsp?docMasterExpenseOid="+docMasterExpenseOid;
                document.frm_employee.submit();
}

function cmdAdd(){
	document.frmEmpDocExpenses.doc_expenses_oid.value="0";
	document.frmEmpDocExpenses.command.value="<%=Command.ADD%>";
	document.frmEmpDocExpenses.prev_command.value="<%=prevCommand%>";
	document.frmEmpDocExpenses.action="EmpDocumentExpense.jsp";
	document.frmEmpDocExpenses.submit();
}


function cmdAdd(){
	document.frmEmpDocExpenses.doc_expenses_oid.value="0";
	document.frmEmpDocExpenses.command.value="<%=Command.ADD%>";
	document.frmEmpDocExpenses.prev_command.value="<%=prevCommand%>";
	document.frmEmpDocExpenses.action="EmpDocumentExpense.jsp";
	document.frmEmpDocExpenses.submit();
}




function cmdAdd(){
	document.frmEmpDocExpenses.doc_expenses_oid.value="0";
	document.frmEmpDocExpenses.command.value="<%=Command.ADD%>";
	document.frmEmpDocExpenses.prev_command.value="<%=prevCommand%>";
	document.frmEmpDocExpenses.action="EmpDocumentExpense.jsp";
	document.frmEmpDocExpenses.submit();
}

function cmdAsk(oidGroupRank){
	document.frmEmpDocExpenses.doc_expenses_oid.value=oidGroupRank;
	document.frmEmpDocExpenses.command.value="<%=Command.ASK%>";
	document.frmEmpDocExpenses.prev_command.value="<%=prevCommand%>";
	document.frmEmpDocExpenses.action="EmpDocumentExpense.jsp";
	document.frmEmpDocExpenses.submit();
}

function cmdConfirmDelete(oidEmpDocExpenses){
	document.frmEmpDocExpenses.doc_expenses_oid.value=oidEmpDocExpenses;
	document.frmEmpDocExpenses.command.value="<%=Command.DELETE%>";
	document.frmEmpDocExpenses.prev_command.value="<%=prevCommand%>";
	document.frmEmpDocExpenses.action="EmpDocumentExpense.jsp";
	document.frmEmpDocExpenses.submit();
}

function cmdSave(){
	document.frmEmpDocExpenses.command.value="<%=Command.SAVE%>";
	document.frmEmpDocExpenses.prev_command.value="<%=prevCommand%>";
	document.frmEmpDocExpenses.action="EmpDocumentExpense.jsp";
	document.frmEmpDocExpenses.submit();
}

function cmdEdit(oidEmpDocExpenses){
	document.frmEmpDocExpenses.doc_expenses_oid.value=oidEmpDocExpenses;
	document.frmEmpDocExpenses.command.value="<%=Command.EDIT%>";
	document.frmEmpDocExpenses.prev_command.value="<%=prevCommand%>";
	document.frmEmpDocExpenses.action="EmpDocumentExpense.jsp";
	document.frmEmpDocExpenses.submit();
}

function cmdCancel(oidEmpDocExpenses){
	document.frmEmpDocExpenses.doc_expenses_oid.value=oidEmpDocExpenses;
	document.frmEmpDocExpenses.command.value="<%=Command.EDIT%>";
	document.frmEmpDocExpenses.prev_command.value="<%=prevCommand%>";
	document.frmEmpDocExpenses.action="EmpDocumentExpense.jsp";
	document.frmEmpDocExpenses.submit();
}

function cmdBack(){
	document.frmEmpDocExpenses.command.value="<%=Command.BACK%>";
	document.frmEmpDocExpenses.action="EmpDocumentExpense.jsp";
	document.frmEmpDocExpenses.submit();
}

function cmdListFirst(){
	document.frmEmpDocExpenses.command.value="<%=Command.FIRST%>";
	document.frmEmpDocExpenses.prev_command.value="<%=Command.FIRST%>";
	document.frmEmpDocExpenses.action="EmpDocumentExpense.jsp";
	document.frmEmpDocExpenses.submit();
}

function cmdListPrev(){
	document.frmEmpDocExpenses.command.value="<%=Command.PREV%>";
	document.frmEmpDocExpenses.prev_command.value="<%=Command.PREV%>";
	document.frmEmpDocExpenses.action="EmpDocumentExpense.jsp";
	document.frmEmpDocExpenses.submit();
}

function cmdListNext(){
	document.frmEmpDocExpenses.command.value="<%=Command.NEXT%>";
	document.frmEmpDocExpenses.prev_command.value="<%=Command.NEXT%>";
	document.frmEmpDocExpenses.action="EmpDocumentExpense.jsp";
	document.frmEmpDocExpenses.submit();
}

function cmdListLast(){
	document.frmEmpDocExpenses.command.value="<%=Command.LAST%>";
	document.frmEmpDocExpenses.prev_command.value="<%=Command.LAST%>";
	document.frmEmpDocExpenses.action="EmpDocumentExpense.jsp";
	document.frmEmpDocExpenses.submit();
}

function cmdListCateg(oidEmpDocExpenses){
	document.frmEmpDocExpenses.doc_expenses_oid.value=oidEmpDocExpenses;
	document.frmEmpDocExpenses.command.value="<%=Command.LIST%>";
	document.frmEmpDocExpenses.action="EmpDocumentExpense.jsp";
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
			  <!-- #BeginEditable "contenttitle" -->Masterdata 
                  &gt; EmpDocExpenses<!-- #EndEditable --> 
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
                                    <form name="frmEmpDocExpenses" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="doc_expenses_oid" value="<%=oidEmpDocExpenses%>">
                                      <input type="hidden" name="oidEmpDoc" value="<%=oidEmpDoc%>">
                                        
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">                                              
                                              <tr align="left" valign="top"> 
                                                <td colspan="3" >
                                                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                      <td class="listtitle" width="37%">&nbsp;</td>
                                                      <td width="63%" class="comment">&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td class="listtitle" width="37%"><%=empDoc.getDoc_title()%></td>
                                                      <td width="63%" class="comment">*)entry 
                                                        required </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              <%
												try{
												%>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <%= drawList(iCommand,frmEmpDocExpenses, empDocExpenses, listEmpDocExpenses,oidEmpDocExpenses,oidEmpDoc, docMasterExpenseOid )%></td>
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
                                                  <% ctrLine.setLocationImg(approot+"/images");
							   	ctrLine.initDefault();
								 %>
                                                  <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> 
                                                  </span> </td>
                                              </tr>
                                              <%if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmEmpDocExpenses.errorSize()<1)){
											  	if(privAdd){%>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <table cellpadding="0" cellspacing="0" border="0">
                                                    <tr> 
                                                      <td>&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                      <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td height="22" valign="middle" colspan="3" width="951"><a href="javascript:cmdAdd()" class="command">Add 
                                                        New </a> </td>
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
                                          <td colspan="3" class="command" height="26"> 
                                            <%
									ctrLine.setLocationImg(approot+"/images");
									ctrLine.initDefault();
									ctrLine.setTableWidth("80%");
									String scomDel = "javascript:cmdAsk('"+oidEmpDocExpenses+"')";
									String sconDelCom = "javascript:cmdConfirmDelete('"+oidEmpDocExpenses+"')";
									String scancel = "javascript:cmdEdit('"+oidEmpDocExpenses+"')";
									ctrLine.setBackCaption("Back to List Doc Type");
									ctrLine.setCommandStyle("buttonlink");
									ctrLine.setAddCaption("Add Doc Type");
									ctrLine.setSaveCaption("Save Doc Type");
									ctrLine.setDeleteCaption("Delete Doc Type");
									ctrLine.setConfirmDelCaption("Yes Delete Doc Type");

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
									
									if((listEmpDocExpenses.size()<1)&&(iCommand == Command.NONE))
										 iCommand = Command.ADD;  
										 
									if(iCommand == Command.ASK)
										ctrLine.setDeleteQuestion(msgString);										 
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
