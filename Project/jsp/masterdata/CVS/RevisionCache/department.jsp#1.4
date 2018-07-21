
<%@page import="com.dimata.harisma.entity.payroll.PstPayGeneral"%>
<% 
/* 
 * Page Name  		:  department.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		: karya 
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
<!--package HRIS -->
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../main/javainit.jsp" %> 
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_DEPARTMENT); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%
boolean isSecretaryLogin = (positionType >= PstPosition.LEVEL_SECRETARY) ? true : false;
            long hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
            boolean isHRDLogin = hrdDepartmentOid == departmentOid ? true : false;
            long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
            boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
            boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
            //update by satrya 2013-10-07
            //boolean isDirector = positionType == PstPosition.LEVEL_DIRECTOR ? true:false;
%>
<%!

	public String drawList(Vector objectClass ,  long departmentId, I_Dictionary dictionaryD)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
                ctrlist.setListStyle("tblStyle");
                ctrlist.setTitleStyle("title_tbl");
                ctrlist.setCellStyle("listgensell");
                ctrlist.setHeaderStyle("title_tbl");
                ctrlist.setCellSpacing("0");
                ctrlist.addHeader(dictionaryD.getWord(I_Dictionary.DEPARTMENT),"");
                ctrlist.addHeader(dictionaryD.getWord(I_Dictionary.DIVISION),"");
		ctrlist.addHeader(dictionaryD.getWord("DESCRIPTION"),"");
                ctrlist.addHeader("Link To Department","");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;

		for (int i = 0; i < objectClass.size(); i++) {
			Department department = (Department)objectClass.get(i);
			 Vector rowx = new Vector();
			 if(departmentId == department.getOID())
				 index = i;

			Division division = new Division();
                try{
                    division = PstDivision.fetchExc(department.getDivisionId());
                }catch(Exception e){}

                    rowx.add(department.getDepartment());                    
                    rowx.add(division.getDivision());
                    rowx.add(department.getDescription());
                    rowx.add(department.getJoinToDepartment()!=null?department.getJoinToDepartment():"");
                    lstData.add(rowx);
                    lstLinkData.add(String.valueOf(department.getOID()));
		}

		//return ctrlist.drawList(index);

		return ctrlist.draw(index);
	}

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidDepartment = FRMQueryString.requestLong(request, "hidden_department_id");
//String departmentName = FRMQueryString.requestString(request,FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_DEPARTMENT]);
/*variable declaration*/
int recordToGet = 50;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID];

CtrlDepartment ctrlDepartment = new CtrlDepartment(request);
ControlLine ctrLine = new ControlLine();
Vector listDepartment = new Vector(1,1);

/*switch statement */
//update by satrya 2013-08-23
int iComanTmp=0;
if(iCommand==Command.GET){
    iCommand = Command.EDIT;
    iComanTmp =Command.GET;
}
iErrCode = ctrlDepartment.action(iCommand , oidDepartment);
/* end switch*/
FrmDepartment frmDepartment = ctrlDepartment.getForm();

/*count list All Department*/
long company_id = FRMQueryString.requestLong(request, "company_id");
long companyIdHidden=FRMQueryString.requestLong(request, "hidden_companyId");
String joinSQL = "";
int vectSize = 0;

if(company_id!=0){        
   joinSQL  = " inner join " + PstDivision.TBL_HR_DIVISION + " on " +
           PstDivision.TBL_HR_DIVISION +"."+PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID] + " = " +
           PstDepartment.TBL_HR_DEPARTMENT +"."+PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] ;
   whereClause = PstDivision.TBL_HR_DIVISION +"."+PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID] + " = " + company_id;    
   vectSize = PstDepartment.getCount(joinSQL, whereClause);
} else {
   vectSize= PstDepartment.getCount(whereClause);
}




Department department = ctrlDepartment.getDepartment();
msgString =  ctrlDepartment.getMessage();

/*switch list Department*/
if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)){
	start = PstDepartment.findLimitStart(department.getOID(),recordToGet, whereClause, orderClause);
	oidDepartment = department.getOID();
}

if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlDepartment.actionList(iCommand, start, vectSize, recordToGet);		
 } 
/* end switch list*/

/* get record to display */


if(company_id!=0){        
   listDepartment = PstDepartment.listWithJointToDep(start,recordToGet, joinSQL, whereClause , orderClause);
} else {
   listDepartment = PstDepartment.listWithJointToDep(start,recordToGet, whereClause , orderClause);
}

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listDepartment.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listDepartment = PstDepartment.list(start,recordToGet, whereClause , orderClause);
}

%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Master Data Department</title>
<script language="JavaScript">

function checkValueJoinDepartment(){
    var valcost = document.frmovertime.<%=FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_JOIN_TO_DEP_ID ] %>.value;
    if(valcost==-1){
        alert("Please select department, you have selected company name");
    }
    if(valcost==-2){
        alert("Please select department, you have selected division name");
    }
}

function cmdUpdateDiv(){
                document.frmdepartment.command.value="<%=String.valueOf(Command.GET)%>";
                document.frmdepartment.action="department.jsp";
                document.frmdepartment.submit();
}
function cmdAdd(){
	document.frmdepartment.hidden_department_id.value="0";
	document.frmdepartment.command.value="<%=Command.ADD%>";
	document.frmdepartment.prev_command.value="<%=prevCommand%>";
	document.frmdepartment.action="department.jsp";
	document.frmdepartment.submit();
}

function cmdAsk(oidDepartment){
	document.frmdepartment.hidden_department_id.value=oidDepartment;
	document.frmdepartment.command.value="<%=Command.ASK%>";
	document.frmdepartment.prev_command.value="<%=prevCommand%>";
	document.frmdepartment.action="department.jsp";
	document.frmdepartment.submit();
}

function cmdConfirmDelete(oidDepartment){
	document.frmdepartment.hidden_department_id.value=oidDepartment;
	document.frmdepartment.command.value="<%=Command.DELETE%>";
	document.frmdepartment.prev_command.value="<%=prevCommand%>";
	document.frmdepartment.action="department.jsp";
	document.frmdepartment.submit();
}
function cmdSave(){
	document.frmdepartment.command.value="<%=Command.SAVE%>";
	document.frmdepartment.prev_command.value="<%=prevCommand%>";
	document.frmdepartment.action="department.jsp";
	document.frmdepartment.submit();
	}

function cmdEdit(oidDepartment){
	document.frmdepartment.hidden_department_id.value=oidDepartment;
	document.frmdepartment.command.value="<%=Command.EDIT%>";
	document.frmdepartment.prev_command.value="<%=prevCommand%>";
	document.frmdepartment.action="department.jsp";
	document.frmdepartment.submit();
	}

function cmdCancel(oidDepartment){
	document.frmdepartment.hidden_department_id.value=oidDepartment;
	document.frmdepartment.command.value="<%=Command.EDIT%>";
	document.frmdepartment.prev_command.value="<%=prevCommand%>";
	document.frmdepartment.action="department.jsp";
	document.frmdepartment.submit();
}

function cmdBack(){
	document.frmdepartment.command.value="<%=Command.BACK%>";
	document.frmdepartment.action="department.jsp";
	document.frmdepartment.submit();
	}

function cmdListFirst(){
	document.frmdepartment.command.value="<%=Command.FIRST%>";
	document.frmdepartment.prev_command.value="<%=Command.FIRST%>";
	document.frmdepartment.action="department.jsp";
	document.frmdepartment.submit();
}

function cmdListPrev(){
	document.frmdepartment.command.value="<%=Command.PREV%>";
	document.frmdepartment.prev_command.value="<%=Command.PREV%>";
	document.frmdepartment.action="department.jsp";
	document.frmdepartment.submit();
	}

function cmdListNext(){
	document.frmdepartment.command.value="<%=Command.NEXT%>";
	document.frmdepartment.prev_command.value="<%=Command.NEXT%>";
	document.frmdepartment.action="department.jsp";
	document.frmdepartment.submit();
}

function cmdListLast(){
	document.frmdepartment.command.value="<%=Command.LAST%>";
	document.frmdepartment.prev_command.value="<%=Command.LAST%>";
	document.frmdepartment.action="department.jsp";
	document.frmdepartment.submit();
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
 function cmdUpdateList(){
	document.frmdepartment.command.value="<%=String.valueOf(Command.GOTO)%>";
	document.frmdepartment.action="department.jsp";
	document.frmdepartment.submit();
}
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
        <style type="text/css">
            #mn_utama {color: #FF6600; padding: 5px 14px; border-left: 1px solid #999; font-size: 12px; font-weight: bold; background-color: #F5F5F5;}
            #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3; color:#0099FF; font-size: 14px; font-weight: bold;}
            #btn {
              background: #3498db;
              border: 1px solid #0066CC;
              border-radius: 3px;
              font-family: Arial;
              color: #ffffff;
              font-size: 12px;
              padding: 3px 9px 3px 9px;
            }

            #btn:hover {
              background: #3cb0fd;
              border: 1px solid #3498db;
            }

            #pos_list {
                border:1px solid #CCC; padding:3px 5px;
                background-color: #ddd; color: #333; margin:2px 0px; 
            }
            .tblStyle {border-collapse: collapse;font-size: 11px;}
            .tblStyle td {padding: 3px 5px; border: 1px solid #CCC;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            #confirm {background-color: #fad9d9;border: 1px solid #da8383; color: #bf3c3c; padding: 14px 21px;border-radius: 5px;}
            .note {
                padding: 5px 7px;
                background-color: #d1e8ef;
                border:1px solid #86bacb;
                color: #329abc;
                border-radius: 3px;
            }
        </style>
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
          <td height="20"><div id="menu_utama">Master Data / Department</div></td>
        </tr>
        <tr> 
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td  style="background-color:#EEE;"> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                    <tr> 
                      <td valign="top"> 
                        <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                          <tr> 
                            <td valign="top">
		    				  <!-- #BeginEditable "content" --> 
                                    <form name="frmdepartment" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="hidden_department_id" value="<%=oidDepartment%>">
                                     <!-- <input type="text" name="<//%=frmDepartment.fieldNames[FrmDepartment.FRM_FIELD_DEPARTMENT]%>" value="<//%=%>">-->
                                      
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <%
                                                            Vector comp_value = new Vector(1,1);
                                                            Vector comp_key = new Vector(1,1);
                                                            comp_value.add("0");
                                                            comp_key.add("select ...");
                                                            Vector listComp = PstCompany.list(0, 0, "", " COMPANY ");
                                                            for (int i = 0; i < listComp.size(); i++) {
                                                                    Company comp = (Company) listComp.get(i);
                                                                    comp_key.add(comp.getCompany());
                                                                    comp_value.add(String.valueOf(comp.getOID()));
                                                            }
                                                        %> <%= ControlCombo.draw("company_id","formElemen",null, ""+company_id, comp_value, comp_key, "onChange=\"javascript:cmdUpdateList()\"") %>
                                              <tr align="left" valign="top"> 
                                                <td valign="middle" colspan="3">&nbsp;<div id="mn_utama"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT)%> List </div></td>
                                              </tr>
                                              <%
							try{
								if (listDepartment.size()>0){
							%>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <%= drawList(listDepartment,oidDepartment, dictionaryD)%></td>
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
										(iCommand == Command.NEXT || iCommand == Command.LAST)){												
												cmd =iCommand; 
								   	}else{
									  if(iCommand == Command.NONE || prevCommand == Command.NONE)
											cmd = Command.FIRST;
									  else{
									  		if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE))
												cmd = PstDepartment.findLimitCommand(start,recordToGet,vectSize);
											else									 
									  			cmd =prevCommand;
									  } 
								    }
									
							    %>
                                                  <% ctrLine.setLocationImg(approot+"/images");
							   	ctrLine.initDefault();
								 %>
                                                  <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> 
                                                  </span> </td>
                                              </tr>
											  <%//if((iCommand == Command.NONE || iCommand == Command.DELETE || iCommand ==Command.BACK || iCommand ==Command.SAVE)&& (frmDepartment.errorSize()<1)){
                                               if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmDepartment.errorSize()<1)){
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
										<tr>
											<td>&nbsp;
											</td>
										</tr>
                                        <tr align="left" valign="top"> 
                                          <td height="8" valign="middle" colspan="3"> 
                                            <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmDepartment.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
											  <tr>
                                                                                              <td><div id="mn_utama"><%=oidDepartment==0?"Add":"Edit"%><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div></td>
											  </tr>
                                              <tr>
											    <td height="100%">
                                                  <table border="0" cellspacing="2" cellpadding="2" width="50%">
                                                    <tr align="left" valign="top">
                                                      <td valign="top" width="18%">&nbsp;</td>
                                                      <td width="82%" class="comment">*)entry
                                                        required </td>
                                                    </tr>
                                                   
                                                    <tr align="left" valign="top">
                                                      <td valign="top" width="18%">
                                                        <%=dictionaryD.getWord(I_Dictionary.DIVISION)%></td>
                                                      <td width="82%">
                                                          <!-- update by satrya 2013-08-23-->
<%
                    comp_value = new Vector(1, 1);
                    comp_key = new Vector(1, 1);
                    String whereCompany = "";
                    if (!(isHRDLogin || isEdpLogin || isGeneralManager || isDirector)) {
                        whereCompany = PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID] + "='" + emplx.getCompanyId() + "'";
                    } 
                    else {
                        comp_value.add("0");
                        comp_key.add("select ...");
                    }
                   //long companyId=0;
                   if(!(iComanTmp==Command.GET) && department.getDivisionId()!=0){
                       companyIdHidden = PstDivision.getOidCompany(PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+"="+department.getDivisionId(), "");  
                   }
                   listComp = PstCompany.list(0, 0, whereCompany, " COMPANY ");
                    for (int i = 0; i < listComp.size(); i++) {
                        Company comp = (Company) listComp.get(i);
                        if(companyIdHidden==0){
                            companyIdHidden=comp.getOID();
                        }
                        comp_key.add(comp.getCompany());
                        comp_value.add(String.valueOf(comp.getOID()));
                    }
                    %> <%= ControlCombo.draw("hidden_companyId", "formElemen", null, "" + companyIdHidden, comp_value, comp_key, "onChange=\"javascript:cmdUpdateDiv()\"")%>

                    <%
                    String  whereClauseComp=PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID]+"="+companyIdHidden;
                    /*if(companyIdHidden!=0){
                        whereClauseComp= PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID]+"="+companyIdHidden;
                    }*/
                                Vector divKey = new Vector(1,1);
                                Vector divValue = new Vector(1,1);
                                Vector listDiv = PstDivision.list(0, 0, whereClauseComp, "DIVISION");
                                for(int i =0;i < listDiv.size();i++){
                                      Division division = (Division)listDiv.get(i);
                                      divKey.add(division.getDivision());
                                      divValue.add(""+division.getOID());
                                }
                          %>
                                                       <%=ControlCombo.draw(frmDepartment.fieldNames[FrmDepartment.FRM_FIELD_DIVISION_ID],"formElemen",null,""+department.getDivisionId(),divValue,divKey )%>
                                                        * <%=frmDepartment.getErrorMsg(frmDepartment.FRM_FIELD_DIVISION_ID)%>
                                                        </td>
                                                    </tr>
                                                     <tr align="left" valign="top">
                                                      <td valign="top" width="18%">
                                                        <%=dictionaryD.getWord(I_Dictionary.DEPARTMENT)%></td>
                                                      <td width="82%">
                                                        <input type="text" name="<%=frmDepartment.fieldNames[FrmDepartment.FRM_FIELD_DEPARTMENT] %>"  value="<%= department.getDepartment() %>" class="elemenForm" size="35">
                                                        * <%=frmDepartment.getErrorMsg(FrmDepartment.FRM_FIELD_DEPARTMENT)%></td>
                                                    </tr>
                                                    <tr align="left" valign="top">
                                                      <td valign="top" width="18%">
                                                        <%=dictionaryD.getWord("DESCRIPTION")%></td>
                                                      <td width="82%">
                                                        <textarea name="<%=frmDepartment.fieldNames[FrmDepartment.FRM_FIELD_DESCRIPTION] %>" class="elemenForm" cols="30" rows="3"><%= department.getDescription() %></textarea>
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top">
                                                      <td valign="top" width="18%">
                                                       HOD Join to <%=dictionaryD.getWord(I_Dictionary.DEPARTMENT)%></td>
                                                      <td width="82%">
                                                        <%
                                                    Vector joinDep_value = new Vector(1, 1);
                                                    Vector joinDep_key = new Vector(1, 1);
                                                    joinDep_value.add("0");
                                                    joinDep_key.add("select ...");
                                                    String strWhereDept = "";
                                                    Vector listCostDept = PstDepartment.listWithCompanyDiv(0, 0, strWhereDept);
                                                    String prevCompany="";
                                                    String prevDivision="";
                                                    for (int i = 0; i < listCostDept.size(); i++) {
                                                        Department dept = (Department) listCostDept.get(i);
                                                        if(prevCompany.equals(dept.getCompany())){
                                                           if(prevDivision.equals(dept.getDivision())){
                                                               joinDep_key.add("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+dept.getDepartment());                                                               
                                                               joinDep_value.add(String.valueOf(dept.getOID()));
                                                           } else{
                                                               joinDep_key.add("&nbsp;-"+dept.getDivision()+"-");                                                               
                                                               joinDep_value.add("-2");
                                                               joinDep_key.add("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+dept.getDepartment());                                                               
                                                               joinDep_value.add(String.valueOf(dept.getOID()));
                                                               prevDivision=dept.getDivision();
                                                           }
                                                        } else {
                                                               joinDep_key.add("-"+dept.getCompany()+"-") ;                                                               
                                                               joinDep_value.add("-1");                                                                                                                                                                                     
                                                               joinDep_key.add("&nbsp;-"+dept.getDivision()+"-");                                                                                                                              joinDep_value.add("-2");                                                                                                                                                                                     
                                                               joinDep_key.add("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+dept.getDepartment());                                                               
                                                               joinDep_value.add(String.valueOf(dept.getOID()));
                                                               prevCompany =dept.getCompany();
                                                               prevDivision=dept.getDivision();                                                            
                                                        }
                                                   }                                                  
                                
                            
                                            %> <%= ControlCombo.draw(FrmDepartment.fieldNames[FrmDepartment.FRM_FIELD_JOIN_TO_DEP_ID], "formElemen", null, "" +
                                                department.getJoinToDepartmentId(), joinDep_value, joinDep_key, 
                                                "onChange=\"javascript:checkValueJoinDepartment()\"")%>
                                                      </td>
                                                    </tr>
                                                    
                                                    <tr>
                                                        <td valign="middle">
                                                            Department Type Id
                                                        </td>
                                                        <td valign="middle">
                                                            <select name="<%=frmDepartment.fieldNames[FrmDepartment.FRM_FIELD_DEPARTMENT_TYPE_ID]%>">
                                                                <option value="0">-select-</option>
                                                                <%
                                                                Vector listDepartmentType = PstDepartmentType.list(0, 0, "", "");
                                                                if (listDepartmentType != null && listDepartmentType.size()>0){
                                                                    for(int ldt=0; ldt<listDepartmentType.size(); ldt++){
                                                                        DepartmentType depT = (DepartmentType)listDepartmentType.get(ldt);
                                                                        if (department.getDepartmentTypeId()== depT.getOID()){
                                                                            %>
                                                                            <option selected="selected" value="<%=depT.getOID()%>"><%=depT.getTypeName()%></option>
                                                                            <%
                                                                        } else {
                                                                            %>
                                                                            <option value="<%=depT.getOID()%>"><%=depT.getTypeName()%></option>
                                                                            <%
                                                                        }
                                                                    }
                                                                }
                                                                %>

                                                            </select>
                                                        </td>
                                                    </tr>
                                                    
                                                    <tr>
                                                        <td colspan="2">
                                                            <div class="note">
                                                                <strong>note:</strong> fill some of field below, if you choose Branch of Company
                                                            </div>
                                                        </td>
                                                    </tr>

                                                    <tr>
                                                        <td valign="middle">
                                                            Address
                                                        </td>
                                                        <td valign="middle">
                                                            <input type="text" name="<%=frmDepartment.fieldNames[FrmDepartment.FRM_FIELD_ADDRESS]%>" size="50" value="<%=department.getAddress()%>" />
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td valign="middle">
                                                            City
                                                        </td>
                                                        <td valign="middle">
                                                           <input type="text" name="<%=frmDepartment.fieldNames[FrmDepartment.FRM_FIELD_CITY]%>" size="50" value="<%=department.getCity()%>" />
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td valign="middle">
                                                            NPWP
                                                        </td>
                                                        <td valign="middle">
                                                           <input type="text" name="<%=frmDepartment.fieldNames[FrmDepartment.FRM_FIELD_NPWP]%>" size="50" value="<%=department.getNpwp()%>" />
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td valign="middle">
                                                            Province
                                                        </td>
                                                        <td valign="middle">
                                                           <input type="text" name="<%=frmDepartment.fieldNames[FrmDepartment.FRM_FIELD_PROVINCE]%>" size="50" value="<%=department.getProvince()%>" />
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td valign="middle">
                                                            Region
                                                        </td>
                                                        <td valign="middle">
                                                           <input type="text" name="<%=frmDepartment.fieldNames[FrmDepartment.FRM_FIELD_REGION]%>" size="50" value="<%=department.getRegion()%>" />
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td valign="middle">
                                                            Sub Region
                                                        </td>
                                                        <td valign="middle">
                                                           <input type="text" name="<%=frmDepartment.fieldNames[FrmDepartment.FRM_FIELD_SUB_REGION]%>" size="50" value="<%=department.getSubRegion()%>" />
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td valign="middle">
                                                            Village
                                                        </td>
                                                        <td valign="middle">
                                                           <input type="text" name="<%=frmDepartment.fieldNames[FrmDepartment.FRM_FIELD_VILLAGE]%>" size="50" value="<%=department.getVillage()%>" />
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td valign="middle">
                                                            Area
                                                        </td>
                                                        <td valign="middle">
                                                           <input type="text" name="<%=frmDepartment.fieldNames[FrmDepartment.FRM_FIELD_AREA]%>" size="50" value="<%=department.getArea()%>" />
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td valign="middle">
                                                            Telephone
                                                        </td>
                                                        <td valign="middle">
                                                           <input type="text" name="<%=frmDepartment.fieldNames[FrmDepartment.FRM_FIELD_TELEPHONE]%>" size="50" value="<%=department.getTelphone()%>" />
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td valign="middle">
                                                            Fax Number
                                                        </td>
                                                        <td valign="middle">
                                                           <input type="text" name="<%=frmDepartment.fieldNames[FrmDepartment.FRM_FIELD_FAX_NUMBER]%>" size="50" value="<%=department.getFaxNumber()%>" />
                                                        </td>
                                                    </tr>
                                                    
                                                    
                                                    
                                                  </table>
						</td>
                                              </tr> 
                                              <tr align="left" valign="top" > 
                                                <td class="command"> 
                                                  <%
									ctrLine.setLocationImg(approot+"/images");
									ctrLine.initDefault();
									ctrLine.setTableWidth("80%");
									String scomDel = "javascript:cmdAsk('"+oidDepartment+"')";
									String sconDelCom = "javascript:cmdConfirmDelete('"+oidDepartment+"')";
									String scancel = "javascript:cmdEdit('"+oidDepartment+"')";
									ctrLine.setBackCaption("Back to List");
									ctrLine.setCommandStyle("buttonlink");
									ctrLine.setBackCaption("Back to List");
									ctrLine.setSaveCaption("Save ");
									ctrLine.setConfirmDelCaption("Yes Delete");
									ctrLine.setDeleteCaption("Delete");									

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
