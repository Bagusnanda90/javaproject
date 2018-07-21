<%@page import="com.dimata.harisma.entity.masterdata.searchEmpDoc"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDocMasterTemplate"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmKecamatan"%>
<%@page import="com.dimata.gui.jsp.ControlDate"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDocMaster"%>
<%@page import="com.dimata.harisma.entity.masterdata.DocMaster"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.harisma.entity.masterdata.DocType"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDocType"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstEmpDoc"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlEmpDoc"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.entity.masterdata.EmpDoc"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmEmpDoc"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<% 
/* 
 * Page Name  		:  EmpDoc.jsp
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

	public String drawList(int iCommand,FrmEmpDoc frmObject, EmpDoc objEntity, Vector objectClass,  long empDocId)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("80%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("No","30%");
                ctrlist.addHeader("Doc Master","30%");
                ctrlist.addHeader("Doc Title","30%");
                ctrlist.addHeader("Request Date ","30%");
                ctrlist.addHeader("Doc Number ","30%");
		ctrlist.addHeader("Issue Date","30%");
		ctrlist.addHeader("Plan Process From","50%");
		ctrlist.addHeader("Plan Process To","50%");
		ctrlist.addHeader("Real Process From","50%");
                ctrlist.addHeader("Real Process To","50%");
                ctrlist.addHeader("Objectives","30%");
                ctrlist.addHeader("Details","30%");
		ctrlist.addHeader("Geo Address","50%");
        	ctrlist.addHeader("Detail","50%");

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
			 EmpDoc empDoc = (EmpDoc)objectClass.get(i);
			 rowx = new Vector();
			 if(empDocId == empDoc.getOID())
				 index = i; 
                         String nama = PstDocMasterTemplate.getfilename(empDoc.getDoc_master_id());
			 if(index == i && (iCommand == Command.EDIT || iCommand == Command.ASK)){
                                rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(empDoc.getOID())+"')\">"+(i+1)+"</a>");
				
				rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmEmpDoc.FRM_FIELD_DOC_MASTER_ID], "formElemen", null, String.valueOf(empDoc.getDoc_master_id()), docmaster_value, docmaster_key, ""));
                                rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmEmpDoc.FRM_FIELD_DOC_TITLE] +"\" value=\""+empDoc.getDoc_title()+"\" class=\"elemenForm\"> * ");
				rowx.add(ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmEmpDoc.FRM_FIELD_REQUEST_DATE], empDoc.getRequest_date(), 0, -40, "formElemen") +"*"+ frmObject.getErrorMsg(frmObject.FRM_FIELD_REQUEST_DATE));
                                rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmEmpDoc.FRM_FIELD_DOC_NUMBER] +"\" value=\""+empDoc.getDoc_number()+"\" class=\"elemenForm\"> * ");
				rowx.add(ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmEmpDoc.FRM_FIELD_DATE_OF_ISSUE], empDoc.getDate_of_issue(), 0, -40, "formElemen") +"*"+ frmObject.getErrorMsg(frmObject.FRM_FIELD_DATE_OF_ISSUE));
                                rowx.add(ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmEmpDoc.FRM_FIELD_PLAN_DATE_FROM], empDoc.getPlan_date_from(), 0, -40, "formElemen") +"*"+ frmObject.getErrorMsg(frmObject.FRM_FIELD_PLAN_DATE_FROM));
                                rowx.add(ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmEmpDoc.FRM_FIELD_PLAN_DATE_TO], empDoc.getPlan_date_to(), 0, -40, "formElemen") +"*"+ frmObject.getErrorMsg(frmObject.FRM_FIELD_PLAN_DATE_TO));
                                rowx.add(ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmEmpDoc.FRM_FIELD_REAL_DATE_FROM], empDoc.getReal_date_from(), 0, -40, "formElemen") +"*"+ frmObject.getErrorMsg(frmObject.FRM_FIELD_REAL_DATE_FROM));
                                rowx.add(ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmEmpDoc.FRM_FIELD_REAL_DATE_TO], empDoc.getReal_date_to(), 0, -40, "formElemen") +"*"+ frmObject.getErrorMsg(frmObject.FRM_FIELD_REAL_DATE_TO));
                                rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmEmpDoc.FRM_FIELD_OBJECTIVES] +"\" value=\""+empDoc.getObjectives()+"\" class=\"elemenForm\"> * ");
				//rowx.add("<a href=\"javascript:cmdEditDetail1('"+String.valueOf(empDoc.getOID())+"')\">Edit detail</a><input type=\"text\" name=\""+frmObject.fieldNames[FrmEmpDoc.FRM_FIELD_DETAILS] +"\" value=\""+empDoc.getDetails()+"\" class=\"elemenForm\"> * ");
				rowx.add("<a  href=\"javascript:cmdEditDetail2('"+String.valueOf(empDoc.getOID())+"')\">Edit detail</a><input type=\"text\" name=\""+frmObject.fieldNames[FrmEmpDoc.FRM_FIELD_DETAILS] +"\" value=\""+empDoc.getDetails()+"\" class=\"elemenForm\"> * ");
				rowx.add("<input type=\"text\" name=\"geo_address_pmnt\" value=\"" + objEntity.getGeo_address() + "\" size=\"40\" onClick=\"javascript:updateGeoAddressPmnt()\"><input type=\"hidden\" name=\"" + frmObject.fieldNames[frmObject.FRM_FIELD_COUNTRY_ID] + "\" value=\"" + objEntity.getCountry_id() + "\"> <input type=\"hidden\" name=\"" + frmObject.fieldNames[frmObject.FRM_FIELD_PROVINCE_ID] + "\" value=\"" + objEntity.getProvince_id() + "\"> <input type=\"hidden\" name=\"" + frmObject.fieldNames[frmObject.FRM_FIELD_REGION_ID] + "\" value=\"" + objEntity.getRegion_id() + "\"> <input type=\"hidden\" name=\"" + frmObject.fieldNames[frmObject.FRM_FIELD_SUBREGION_ID] + "\" value=\"" + objEntity.getSubregion_id() + "\"> ");
                                rowx.add("<a href=\"javascript:cmdOpen('"+nama+"')\">download</a> || </a><a href=\"javascript:cmdEmployee('"+String.valueOf(empDoc.getOID())+"')\">Employee</a>");
                         
                                  
                         }else{
                             String geo = PstEmpDoc.GetGeoAddress(empDoc);
                                rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(empDoc.getOID())+"')\">"+(i+1)+"</a>");
				rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmEmpDoc.FRM_FIELD_DOC_MASTER_ID], "formElemen", null, String.valueOf(empDoc.getDoc_master_id()), docmaster_value, docmaster_key, "disabled"));
                                rowx.add(""+ empDoc.getDoc_title());
				rowx.add(""+ empDoc.getRequest_date());
                                rowx.add(""+ empDoc.getDoc_number());
				rowx.add(""+ empDoc.getDate_of_issue());
                                rowx.add(""+ empDoc.getPlan_date_from());
                                rowx.add(""+ empDoc.getPlan_date_to());
                                rowx.add(""+ empDoc.getReal_date_from());
                                rowx.add(""+ empDoc.getReal_date_to());
                                rowx.add(""+ empDoc.getObjectives());
				rowx.add(""+ empDoc.getDetails());
				rowx.add(""+ geo);
                                rowx.add(" - ");
			} 

			lstData.add(rowx);
		}

		 rowx = new Vector();

		if(iCommand == Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize() > 0)){ 
                                rowx.add("-");
				rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmEmpDoc.FRM_FIELD_DOC_MASTER_ID], "formElemen", null, String.valueOf(objEntity.getDoc_master_id()), docmaster_value, docmaster_key, ""));
                                rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmEmpDoc.FRM_FIELD_DOC_TITLE] +"\" value=\""+objEntity.getDoc_title()+"\" class=\"elemenForm\"> * ");
				rowx.add(ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmEmpDoc.FRM_FIELD_REQUEST_DATE], objEntity.getRequest_date(), 0, -40, "formElemen") +"*"+ frmObject.getErrorMsg(frmObject.FRM_FIELD_REQUEST_DATE));
                                rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmEmpDoc.FRM_FIELD_DOC_NUMBER] +"\" value=\""+objEntity.getDoc_number()+"\" class=\"elemenForm\"> * ");
				rowx.add(ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmEmpDoc.FRM_FIELD_DATE_OF_ISSUE], objEntity.getDate_of_issue(), 0, -40, "formElemen") +"*"+ frmObject.getErrorMsg(frmObject.FRM_FIELD_DATE_OF_ISSUE));
                                rowx.add(ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmEmpDoc.FRM_FIELD_PLAN_DATE_FROM], objEntity.getPlan_date_from(), 0, -40, "formElemen") +"*"+ frmObject.getErrorMsg(frmObject.FRM_FIELD_PLAN_DATE_FROM));
                                rowx.add(ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmEmpDoc.FRM_FIELD_PLAN_DATE_TO], objEntity.getPlan_date_to(), 0, -40, "formElemen") +"*"+ frmObject.getErrorMsg(frmObject.FRM_FIELD_PLAN_DATE_TO));
                                rowx.add(ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmEmpDoc.FRM_FIELD_REAL_DATE_FROM], objEntity.getReal_date_from(), 0, -40, "formElemen") +"*"+ frmObject.getErrorMsg(frmObject.FRM_FIELD_REAL_DATE_FROM));
                                rowx.add(ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmEmpDoc.FRM_FIELD_REAL_DATE_TO], objEntity.getReal_date_to(), 0, -40, "formElemen") +"*"+ frmObject.getErrorMsg(frmObject.FRM_FIELD_REAL_DATE_TO));
                                rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmEmpDoc.FRM_FIELD_OBJECTIVES] +"\" value=\""+objEntity.getObjectives()+"\" class=\"elemenForm\"> * ");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmEmpDoc.FRM_FIELD_DETAILS] +"\" value=\""+objEntity.getDetails()+"\" class=\"elemenForm\"> * ");
				rowx.add("<input type=\"text\" name=\"geo_address_pmnt\" value=\"" + objEntity.getGeo_address() + "\" size=\"40\" onClick=\"javascript:updateGeoAddressPmnt()\"><input type=\"hidden\" name=\"" + frmObject.fieldNames[frmObject.FRM_FIELD_COUNTRY_ID] + "\" value=\"" + objEntity.getCountry_id() + "\"> <input type=\"hidden\" name=\"" + frmObject.fieldNames[frmObject.FRM_FIELD_PROVINCE_ID] + "\" value=\"" + objEntity.getProvince_id() + "\"> <input type=\"hidden\" name=\"" + frmObject.fieldNames[frmObject.FRM_FIELD_REGION_ID] + "\" value=\"" + objEntity.getRegion_id() + "\"> <input type=\"hidden\" name=\"" + frmObject.fieldNames[frmObject.FRM_FIELD_SUBREGION_ID] + "\" value=\"" + objEntity.getSubregion_id() + "\"> ");
                                rowx.add("-");
		}

		lstData.add(rowx);

		return ctrlist.draw();
	}

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidEmpDoc = FRMQueryString.requestLong(request, "EmpDocument_oid");
long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");


//untuk searching

long docmaster = FRMQueryString.requestLong(request, "docmaster");
String docTitle = FRMQueryString.requestString(request, "docTitle");
String docNumber = FRMQueryString.requestString(request, "docNumber");

searchEmpDoc sEmpDoc = new searchEmpDoc();
sEmpDoc.setDoc_master_id(docmaster);
sEmpDoc.setDocTitle(docTitle);

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = "";

CtrlEmpDoc ctrlEmpDoc = new CtrlEmpDoc(request);
ControlLine ctrLine = new ControlLine();
Vector listEmpDoc = new Vector(1,1);

iErrCode = ctrlEmpDoc.action(iCommand , oidEmpDoc);
/* end switch*/
FrmEmpDoc frmEmpDoc = ctrlEmpDoc.getForm();

/*count list All GroupRank*/
int vectSize = 0;
if ((iCommand == Command.LIST) || (iCommand == Command.EDIT)){
    vectSize = PstEmpDoc.getCount(whereClause);
}
/*switch list GroupRank*/
if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlEmpDoc.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

EmpDoc empDoc = ctrlEmpDoc.getEmpDoc();
msgString =  ctrlEmpDoc.getMessage();

/* get record to display */
if ((iCommand == Command.LIST) || (iCommand == Command.EDIT)){
listEmpDoc = PstEmpDoc.listSpecial(start,recordToGet, whereClause , orderClause,sEmpDoc);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listEmpDoc.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listEmpDoc = PstEmpDoc.listSpecial(start,recordToGet, whereClause , orderClause,sEmpDoc);
}
}
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Doc Type</title>
<script language="JavaScript">


function cmdAdd(){
	document.frmEmpDoc.EmpDocument_oid.value="0";
	document.frmEmpDoc.command.value="<%=Command.ADD%>";
	document.frmEmpDoc.prev_command.value="<%=prevCommand%>";
	document.frmEmpDoc.action="EmpDocument.jsp";
	document.frmEmpDoc.submit();
}

function cmdAsk(oidGroupRank){
	document.frmEmpDoc.EmpDocument_oid.value=oidGroupRank;
	document.frmEmpDoc.command.value="<%=Command.ASK%>";
	document.frmEmpDoc.prev_command.value="<%=prevCommand%>";
	document.frmEmpDoc.action="EmpDocument.jsp";
	document.frmEmpDoc.submit();
}

function cmdOpen(fileName){
		window.open("<%=approot%>/imgdoc/"+fileName , null);
		
}

function updateGeoAddressPmnt(){
                oidNegara    = document.frmEmpDoc.<%=frmEmpDoc.fieldNames[frmEmpDoc.FRM_FIELD_COUNTRY_ID]%>.value;
                oidProvinsi  = document.frmEmpDoc.<%=frmEmpDoc.fieldNames[frmEmpDoc.FRM_FIELD_PROVINCE_ID]%>.value ;
                oidKabupaten = document.frmEmpDoc.<%=frmEmpDoc.fieldNames[frmEmpDoc.FRM_FIELD_REGION_ID]%>.value ;
                oidKecamatan = document.frmEmpDoc.<%=frmEmpDoc.fieldNames[frmEmpDoc.FRM_FIELD_SUBREGION_ID]%>.value;                    
                window.open("<%=approot%>/masterdata/geo_address.jsp?formName=frmEmpDoc&employee_oid=<%=String.valueOf(oidEmployee)%>&addresstype=2&"+
                    "<%=FrmKecamatan.fieldNames[FrmKecamatan.FRM_FIELD_ID_NEGARA]%>="+oidNegara+"&"+
                    "<%=FrmKecamatan.fieldNames[FrmKecamatan.FRM_FIELD_ID_PROPINSI]%>="+oidProvinsi+"&"+
                    "<%=FrmKecamatan.fieldNames[FrmKecamatan.FRM_FIELD_ID_KABUPATEN]%>="+oidKabupaten+"&"+
                    "<%=FrmKecamatan.fieldNames[FrmKecamatan.FRM_FIELD_ID_KECAMATAN]%>="+oidKecamatan+"",                                                
                null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");
            }
function cmdEmployee(EmpDocId){
    
        document.frmEmpDoc.command.value="<%=Command.ADD%>";
	document.frmEmpDoc.prev_command.value="<%=prevCommand%>";
	document.frmEmpDoc.action="EmpDocList.jsp?EmpDocId="+EmpDocId;
	document.frmEmpDoc.submit();

 }
function cmdTemplate(EmpDocId){
	window.open("<%=approot%>/masterdata/EmpDocTemplate.jsp?EmpDocId="+EmpDocId+"", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
}
function cmdExpense(EmpDocId){
	window.open("<%=approot%>/masterdata/EmpDocExpense.jsp?EmpDocId="+EmpDocId+"", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
}

function cmdConfirmDelete(oidEmpDoc){
	document.frmEmpDoc.EmpDocument_oid.value=oidEmpDoc;
	document.frmEmpDoc.command.value="<%=Command.DELETE%>";
	document.frmEmpDoc.prev_command.value="<%=prevCommand%>";
	document.frmEmpDoc.action="EmpDocument.jsp";
	document.frmEmpDoc.submit();
}

 function cmdSearch(){
                document.frmEmpDoc.command.value="<%=String.valueOf(Command.LIST)%>";
                document.frmEmpDoc.action="EmpDocument.jsp";
                document.frmEmpDoc.submit();
                
            }

function cmdSave(){
	document.frmEmpDoc.command.value="<%=Command.SAVE%>";
	document.frmEmpDoc.prev_command.value="<%=prevCommand%>";
	document.frmEmpDoc.action="EmpDocument.jsp";
	document.frmEmpDoc.submit();
}

function cmdEdit(oidEmpDoc){
	document.frmEmpDoc.EmpDocument_oid.value=oidEmpDoc;
	document.frmEmpDoc.command.value="<%=Command.EDIT%>";
	document.frmEmpDoc.prev_command.value="<%=prevCommand%>";
	document.frmEmpDoc.action="EmpDocument.jsp";
	document.frmEmpDoc.submit();
}
function cmdEditDetail(oidEmpDoc){
	document.frmEmpDoc.EmpDocument_oid.value=oidEmpDoc;
	document.frmEmpDoc.command.value="<%=Command.EDIT%>";
	document.frmEmpDoc.prev_command.value="<%=prevCommand%>";
	document.frmEmpDoc.action="EmpDocumentDetails.jsp";
	document.frmEmpDoc.submit();
}
function cmdEditDetail1(EmpDocument_oid){
	window.open("<%=approot%>/masterdata/EmpDocumentDetails.jsp?EmpDocument_oid="+EmpDocument_oid+"", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
}
function cmdEditDetail2(EmpDocument_oid){
	window.open("<%=approot%>/masterdata/EmpDocumentDetails.jsp?EmpDocument_oid="+EmpDocument_oid);       
}
function cmdCancel(oidEmpDoc){
	document.frmEmpDoc.EmpDocument_oid.value=oidEmpDoc;
	document.frmEmpDoc.command.value="<%=Command.EDIT%>";
	document.frmEmpDoc.prev_command.value="<%=prevCommand%>";
	document.frmEmpDoc.action="EmpDocument.jsp";
	document.frmEmpDoc.submit();
}

function cmdBack(){
	document.frmEmpDoc.command.value="<%=Command.BACK%>";
	document.frmEmpDoc.action="EmpDocument.jsp";
	document.frmEmpDoc.submit();
}

function cmdListFirst(){
	document.frmEmpDoc.command.value="<%=Command.FIRST%>";
	document.frmEmpDoc.prev_command.value="<%=Command.FIRST%>";
	document.frmEmpDoc.action="EmpDocument.jsp";
	document.frmEmpDoc.submit();
}

function cmdListPrev(){
	document.frmEmpDoc.command.value="<%=Command.PREV%>";
	document.frmEmpDoc.prev_command.value="<%=Command.PREV%>";
	document.frmEmpDoc.action="EmpDocument.jsp";
	document.frmEmpDoc.submit();
}

function cmdListNext(){
	document.frmEmpDoc.command.value="<%=Command.NEXT%>";
	document.frmEmpDoc.prev_command.value="<%=Command.NEXT%>";
	document.frmEmpDoc.action="EmpDocument.jsp";
	document.frmEmpDoc.submit();
}

function cmdListLast(){
	document.frmEmpDoc.command.value="<%=Command.LAST%>";
	document.frmEmpDoc.prev_command.value="<%=Command.LAST%>";
	document.frmEmpDoc.action="EmpDocument.jsp";
	document.frmEmpDoc.submit();
}

function cmdListCateg(oidEmpDoc){
	document.frmEmpDoc.EmpDocument_oid.value=oidEmpDoc;
	document.frmEmpDoc.command.value="<%=Command.LIST%>";
	document.frmEmpDoc.action="EmpDocument.jsp";
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
                  &gt; EmpDoc<!-- #EndEditable --> 
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
                                    <form name="frmEmpDoc" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
				      <input type="hidden" name="EmpDocument_oid" value="<%=oidEmpDoc%>">
                                      
                                      
                                      <tr> 
                                          
                                          <td  nowrap="nowrap">Doc Master    :    
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
                                            <%=ControlCombo.draw("docmaster", null, "" + docmaster, docmaster_value, docmaster_key)%> 
                                        </td>

                                        </tr>
                                        <tr> 
                                          
                                        <td  nowrap="nowrap">Title    :    
                                            <input type="text" name="docTitle" value="<%=docTitle%>">
                                                                                    
                                        </td>

                                        </tr>
                                        
                                        <tr> 
                                          
                                        <td  nowrap="nowrap">Doc Number    :    
                                            <input type="text" name="docNumber" value="<%=docNumber%>">
                                                                                    
                                        </td>

                                        </tr>
                                      <tr>
                                                        <td width="39%"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search "></a>
                                                        <img src="<%=approot%>/images/spacer.gif" width="6" height="1">
                                                        <a href="javascript:cmdSearch(0)">Search </a></td>  
                                                     
                                      </tr>
                                      
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
                                                   
                                                  </table>
                                                </td>
                                              </tr>
                                              <%
												try{
												%>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                <% if (((iCommand == Command.LIST) || (iCommand == Command.EDIT)) && listEmpDoc.size()>0){%>
                                                <%= drawList(iCommand,frmEmpDoc, empDoc, listEmpDoc,oidEmpDoc)%>
                                                <%}%>
                                                </td>
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
                                              <%if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmEmpDoc.errorSize()<1)){
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
                                                        New Doc Type</a> </td>
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
                                        <% if((iCommand == Command.LIST) || (iCommand == Command.EDIT)){%>
                                        <tr align="left" valign="top" > 
                                          <td colspan="3" class="command" height="26"> 
                                            <%
									ctrLine.setLocationImg(approot+"/images");
									ctrLine.initDefault();
									ctrLine.setTableWidth("80%");
									String scomDel = "javascript:cmdAsk('"+oidEmpDoc+"')";
									String sconDelCom = "javascript:cmdConfirmDelete('"+oidEmpDoc+"')";
									String scancel = "javascript:cmdEdit('"+oidEmpDoc+"')";
									ctrLine.setBackCaption("Back to List Emp Doc");
									ctrLine.setCommandStyle("buttonlink");
									ctrLine.setAddCaption("Add Emp Doc");
									ctrLine.setSaveCaption("Save Emp Doc");
									ctrLine.setDeleteCaption("Delete Emp Doc");
									ctrLine.setConfirmDelCaption("Yes Delete Emp Doc");

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
									
									if((listEmpDoc.size()<1)&&(iCommand == Command.NONE))
										 iCommand = Command.ADD;  
										 
									if(iCommand == Command.ASK)
										ctrLine.setDeleteQuestion(msgString);										 
									%>
                                            <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%> 
                                          </td>
                                        </tr>
                                        <%}%>
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
