<%@page import="com.dimata.harisma.form.masterdata.FrmKecamatan"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstCompetitor"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlCompetitor"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmCompetitor"%>
<%@page import="com.dimata.harisma.entity.masterdata.Competitor"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<% 
/* 
 * Page Name  		:  competitor.jsp
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

	public String drawList(int iCommand,FrmCompetitor frmObject, Competitor objEntity, Vector objectClass,  long competitorId)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("80%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
                ctrlist.addHeader("No ","30%");
                ctrlist.addHeader("COMPANY NAME","30%");
                ctrlist.addHeader("ADDRESS","30%");
		ctrlist.addHeader("WEBSITE","30%");
                ctrlist.addHeader("EMAIL","30%");
                ctrlist.addHeader("TELEPHONE","30%");
                ctrlist.addHeader("FAX","30%");
		ctrlist.addHeader("CONTACT PERSON","30%");
		ctrlist.addHeader("DESCRIPTION","30%");
                ctrlist.addHeader("GEO ADDRES","30%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		Vector rowx = new Vector(1,1);
		ctrlist.reset();
		int index = -1;

		for (int i = 0; i < objectClass.size(); i++) {
			 Competitor competitor = (Competitor)objectClass.get(i);
			 rowx = new Vector();
			 if(competitorId == competitor.getOID())
				 index = i; 

			 if(index == i && (iCommand == Command.EDIT || iCommand == Command.ASK)){				
			        rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(competitor.getOID())+"')\">"+(i+1)+"</a>");
                                rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmCompetitor.FRM_FIELD_COMPANY_NAME] +"\" value=\""+competitor.getCompanyName()+"\" class=\"elemenForm\"> * ");
                                rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmCompetitor.FRM_FIELD_ADDRESS] +"\" value=\""+competitor.getAddress()+"\" class=\"elemenForm\"> * ");
                                rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmCompetitor.FRM_FIELD_WEBSITE] +"\" value=\""+competitor.getWebsite()+"\" class=\"elemenForm\"> * ");
                                rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmCompetitor.FRM_FIELD_EMAIL] +"\" value=\""+competitor.getEmail()+"\" class=\"elemenForm\"> * ");
                                rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmCompetitor.FRM_FIELD_TELEPHONE] +"\" value=\""+competitor.getTelephone()+"\" class=\"elemenForm\"> * ");
                                rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmCompetitor.FRM_FIELD_FAX] +"\" value=\""+competitor.getFax()+"\" class=\"elemenForm\"> * ");
                                rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmCompetitor.FRM_FIELD_CONTACT_PERSON] +"\" value=\""+competitor.getContact_person()+"\" class=\"elemenForm\"> * ");
				rowx.add("<textarea name=\""+frmObject.fieldNames[FrmCompetitor.FRM_FIELD_DESCRIPTION] +"\" class=\"elemenForm\" rows=\"3\" cols=\"35\">"+competitor.getDescription()+"</textarea>");
                                rowx.add("<input type=\"text\" name=\"geo_address_pmnt\" value=\"" + objEntity.getGeoAddress() + "\" size=\"40\" onClick=\"javascript:updateGeoAddressPmnt()\"><input type=\"hidden\" name=\"" + frmObject.fieldNames[frmObject.FRM_FIELD_COUNTRY_ID] + "\" value=\"" + objEntity.getCountryId() + "\"> <input type=\"hidden\" name=\"" + frmObject.fieldNames[frmObject.FRM_FIELD_PROVINCE_ID] + "\" value=\"" + objEntity.getProvinceId() + "\"> <input type=\"hidden\" name=\"" + frmObject.fieldNames[frmObject.FRM_FIELD_REGION_ID] + "\" value=\"" + objEntity.getRegionId() + "\"> <input type=\"hidden\" name=\"" + frmObject.fieldNames[frmObject.FRM_FIELD_SUBREGION_ID] + "\" value=\"" + objEntity.getSubregionId() + "\"> ");
                               	
                         
                         }else{
				rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(competitor.getOID())+"')\">"+(i+1)+"</a>");
				rowx.add(""+competitor.getCompanyName());
                                rowx.add(""+competitor.getAddress());
                                rowx.add(""+competitor.getWebsite());
                                rowx.add(""+competitor.getEmail());
                                rowx.add(""+competitor.getTelephone());
                                rowx.add(""+competitor.getFax());
                                rowx.add(""+competitor.getContact_person());
				rowx.add(""+competitor.getDescription());
                                rowx.add(""+objEntity.getGeoAddress());
			} 

			lstData.add(rowx);
		}

		 rowx = new Vector();

		if(iCommand == Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize() > 0)){ 
                                rowx.add("-");
                                rowx.add("<input type=\"text\" name=\""+FrmCompetitor.fieldNames[FrmCompetitor.FRM_FIELD_COMPANY_NAME] +"\" value=\""+objEntity.getCompanyName()+"\" class=\"elemenForm\"> * ");
                                rowx.add("<input type=\"text\" name=\""+FrmCompetitor.fieldNames[FrmCompetitor.FRM_FIELD_ADDRESS] +"\" value=\""+objEntity.getAddress()+"\" class=\"elemenForm\"> * ");
                                rowx.add("<input type=\"text\" name=\""+FrmCompetitor.fieldNames[FrmCompetitor.FRM_FIELD_WEBSITE] +"\" value=\""+objEntity.getWebsite()+"\" class=\"elemenForm\"> * ");
                                rowx.add("<input type=\"text\" name=\""+FrmCompetitor.fieldNames[FrmCompetitor.FRM_FIELD_EMAIL] +"\" value=\""+objEntity.getEmail()+"\" class=\"elemenForm\"> * ");
                                rowx.add("<input type=\"text\" name=\""+FrmCompetitor.fieldNames[FrmCompetitor.FRM_FIELD_TELEPHONE] +"\" value=\""+objEntity.getTelephone()+"\" class=\"elemenForm\"> * ");
                                rowx.add("<input type=\"text\" name=\""+FrmCompetitor.fieldNames[FrmCompetitor.FRM_FIELD_FAX] +"\" value=\""+objEntity.getFax()+"\" class=\"elemenForm\"> * ");
                                rowx.add("<input type=\"text\" name=\""+FrmCompetitor.fieldNames[FrmCompetitor.FRM_FIELD_CONTACT_PERSON] +"\" value=\""+objEntity.getContact_person()+"\" class=\"elemenForm\"> * ");
				rowx.add("<textarea name=\""+FrmCompetitor.fieldNames[FrmCompetitor.FRM_FIELD_DESCRIPTION] +"\" class=\"elemenForm\" rows=\"3\" cols=\"35\">"+objEntity.getDescription()+"</textarea>");
                                rowx.add("<input type=\"text\" name=\"geo_address_pmnt\" value=\"" + objEntity.getGeoAddress() + "\" size=\"40\" onClick=\"javascript:updateGeoAddressPmnt()\"><input type=\"hidden\" name=\"" + FrmCompetitor.fieldNames[FrmCompetitor.FRM_FIELD_COUNTRY_ID] + "\" value=\"" + objEntity.getCountryId() + "\"> <input type=\"hidden\" name=\"" + FrmCompetitor.fieldNames[FrmCompetitor.FRM_FIELD_PROVINCE_ID] + "\" value=\"" + objEntity.getProvinceId() + "\"> <input type=\"hidden\" name=\"" + FrmCompetitor.fieldNames[FrmCompetitor.FRM_FIELD_REGION_ID] + "\" value=\"" + objEntity.getRegionId() + "\"> <input type=\"hidden\" name=\"" + FrmCompetitor.fieldNames[FrmCompetitor.FRM_FIELD_SUBREGION_ID] + "\" value=\"" + objEntity.getSubregionId() + "\"> ");
                               
		}

		lstData.add(rowx);

		return ctrlist.draw();
	}

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidCompetitor = FRMQueryString.requestLong(request, "Competitor_oid");
long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = "";

CtrlCompetitor ctrlCompetitor = new CtrlCompetitor(request);
ControlLine ctrLine = new ControlLine();
Vector listCompetitor = new Vector(1,1);

iErrCode = ctrlCompetitor.action(iCommand , oidCompetitor);
/* end switch*/
FrmCompetitor frmCompetitor = ctrlCompetitor.getForm();

/*count list All GroupRank*/
int vectSize = PstCompetitor.getCount(whereClause);

/*switch list GroupRank*/
if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlCompetitor.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

Competitor competitor = ctrlCompetitor.getdCompetitor();
msgString =  ctrlCompetitor.getMessage();

/* get record to display */
listCompetitor = PstCompetitor.list(start,recordToGet, whereClause , orderClause);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listCompetitor.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listCompetitor = PstCompetitor.list(start,recordToGet, whereClause , orderClause);
}
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Doc Type</title>
<script language="JavaScript">

function updateGeoAddressPmnt(){
                oidNegara    = document.frmCompetitor.<%=frmCompetitor.fieldNames[frmCompetitor.FRM_FIELD_COUNTRY_ID]%>.value;
                oidProvinsi  = document.frmCompetitor.<%=frmCompetitor.fieldNames[frmCompetitor.FRM_FIELD_PROVINCE_ID]%>.value ;
                oidKabupaten = document.frmCompetitor.<%=frmCompetitor.fieldNames[frmCompetitor.FRM_FIELD_REGION_ID]%>.value ;
                oidKecamatan = document.frmCompetitor.<%=frmCompetitor.fieldNames[frmCompetitor.FRM_FIELD_SUBREGION_ID]%>.value;                    
                window.open("<%=approot%>/masterdata/geo_addressComp.jsp?formName=frmCompetitor&employee_oid=<%=String.valueOf(oidEmployee)%>&addresstype=2&"+
                    "<%=FrmKecamatan.fieldNames[FrmKecamatan.FRM_FIELD_ID_NEGARA]%>="+oidNegara+"&"+
                    "<%=FrmKecamatan.fieldNames[FrmKecamatan.FRM_FIELD_ID_PROPINSI]%>="+oidProvinsi+"&"+
                    "<%=FrmKecamatan.fieldNames[FrmKecamatan.FRM_FIELD_ID_KABUPATEN]%>="+oidKabupaten+"&"+
                    "<%=FrmKecamatan.fieldNames[FrmKecamatan.FRM_FIELD_ID_KECAMATAN]%>="+oidKecamatan+"",                                                
                null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");
            }
function cmdAdd(){
	document.frmCompetitor.Competitor_oid.value="0";
	document.frmCompetitor.command.value="<%=Command.ADD%>";
	document.frmCompetitor.prev_command.value="<%=prevCommand%>";
	document.frmCompetitor.action="competitor.jsp";
	document.frmCompetitor.submit();
}

function cmdAsk(oidCompetitor){
	document.frmCompetitor.Competitor_oid.value=oidCompetitor;
	document.frmCompetitor.command.value="<%=Command.ASK%>";
	document.frmCompetitor.prev_command.value="<%=prevCommand%>";
	document.frmCompetitor.action="competitor.jsp";
	document.frmCompetitor.submit();
}

function cmdConfirmDelete(oidCompetitor){
	document.frmCompetitor.Competitor_oid.value=oidCompetitor;
	document.frmCompetitor.command.value="<%=Command.DELETE%>";
	document.frmCompetitor.prev_command.value="<%=prevCommand%>";
	document.frmCompetitor.action="competitor.jsp";
	document.frmCompetitor.submit();
}

function cmdSave(){
	document.frmCompetitor.command.value="<%=Command.SAVE%>";
	document.frmCompetitor.prev_command.value="<%=prevCommand%>";
	document.frmCompetitor.action="competitor.jsp";
	document.frmCompetitor.submit();
}

function cmdEdit(oidCompetitor){
	document.frmCompetitor.Competitor_oid.value=oidCompetitor;
	document.frmCompetitor.command.value="<%=Command.EDIT%>";
	document.frmCompetitor.prev_command.value="<%=prevCommand%>";
	document.frmCompetitor.action="competitor.jsp";
	document.frmCompetitor.submit();
}

function cmdCancel(oidCompetitor){
	document.frmCompetitor.Competitor_oid.value=oidCompetitor;
	document.frmCompetitor.command.value="<%=Command.EDIT%>";
	document.frmCompetitor.prev_command.value="<%=prevCommand%>";
	document.frmCompetitor.action="competitor.jsp";
	document.frmCompetitor.submit();
}

function cmdBack(){
	document.frmCompetitor.command.value="<%=Command.BACK%>";
	document.frmCompetitor.action="competitor.jsp";
	document.frmCompetitor.submit();
}

function cmdListFirst(){
	document.frmCompetitor.command.value="<%=Command.FIRST%>";
	document.frmCompetitor.prev_command.value="<%=Command.FIRST%>";
	document.frmCompetitor.action="competitor.jsp";
	document.frmCompetitor.submit();
}

function cmdListPrev(){
	document.frmCompetitor.command.value="<%=Command.PREV%>";
	document.frmCompetitor.prev_command.value="<%=Command.PREV%>";
	document.frmCompetitor.action="competitor.jsp";
	document.frmCompetitor.submit();
}

function cmdListNext(){
	document.frmCompetitor.command.value="<%=Command.NEXT%>";
	document.frmCompetitor.prev_command.value="<%=Command.NEXT%>";
	document.frmCompetitor.action="competitor.jsp";
	document.frmCompetitor.submit();
}

function cmdListLast(){
	document.frmCompetitor.command.value="<%=Command.LAST%>";
	document.frmCompetitor.prev_command.value="<%=Command.LAST%>";
	document.frmCompetitor.action="competitor.jsp";
	document.frmCompetitor.submit();
}

function cmdListCateg(oidCompetitor){
	document.frmCompetitor.Competitor_oid.value=oidCompetitor;
	document.frmCompetitor.command.value="<%=Command.LIST%>";
	document.frmCompetitor.action="competitor.jsp";
	document.frmCompetitor.submit();
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
                  &gt; Competitor<!-- #EndEditable --> 
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
                                    <form name="frmCompetitor" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
									  <input type="hidden" name="Competitor_oid" value="<%=oidCompetitor%>">
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
                                                      <td class="listtitle" width="37%">Doc Type List</td>
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
                                                  <%= drawList(iCommand,frmCompetitor, competitor, listCompetitor,oidCompetitor)%></td>
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
                                              <%if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmCompetitor.errorSize()<1)){
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
                                        <tr align="left" valign="top" > 
                                          <td colspan="3" class="command" height="26"> 
                                            <%
									ctrLine.setLocationImg(approot+"/images");
									ctrLine.initDefault();
									ctrLine.setTableWidth("80%");
									String scomDel = "javascript:cmdAsk('"+oidCompetitor+"')";
									String sconDelCom = "javascript:cmdConfirmDelete('"+oidCompetitor+"')";
									String scancel = "javascript:cmdEdit('"+oidCompetitor+"')";
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
									
									if((listCompetitor.size()<1)&&(iCommand == Command.NONE))
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
