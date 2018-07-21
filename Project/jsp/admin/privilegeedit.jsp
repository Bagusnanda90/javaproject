<%-- 
    Document   : privilegeedit
    Created on : May 9, 2016, 11:23:36 AM
    Author     : Dimata 007
--%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.harisma.form.admin.CtrlAppPrivilegeObj"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.admin.FrmAppPrivilegeObj"%>
<%@ include file = "../main/javainit.jsp" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<% int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_USER_MANAGEMENT, AppObjInfo.OBJ_USER_PRIVILEGE); %>
<%@ include file = "../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%!

public String drawListPrivObj(Vector objectClass)
{
	String temp = "";
	String regdatestr = "";

	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("0");
	ctrlist.setListStyle("tblStyle");
        ctrlist.setTitleStyle("title_tbl");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("title_tbl");
        ctrlist.setCellSpacing("0");
	ctrlist.addHeader("Module","");
	ctrlist.addHeader("Module Group 1","");
	ctrlist.addHeader("Module Group 2","");	
	ctrlist.addHeader("authorization ","");

	ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
	
	Vector lstData = ctrlist.getData();

	Vector lstLinkData 	= ctrlist.getLinkData();					
	
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
	
	for (int i = 0; i < objectClass.size(); i++) {
		 AppPrivilegeObj appPrivObj = (AppPrivilegeObj) objectClass.get(i);

		 Vector rowx = new Vector();
		 rowx.add(AppObjInfo.getTitleObject(appPrivObj.getCode()));
		 rowx.add(AppObjInfo.getTitleGroup1(appPrivObj.getCode()));
		 rowx.add(AppObjInfo.getTitleGroup2(appPrivObj.getCode()));
		 
		 
		 Vector cmdInts = appPrivObj.getCommands();
		 String cmdStr = new String("");
		 for(int ic=0;ic< cmdInts.size() ; ic++){
			cmdStr =cmdStr + AppObjInfo.getStrCommand(((Integer)cmdInts.get(ic)).intValue())+", ";
		 }
		 if(cmdStr.length()>0)
			cmdStr = cmdStr.substring(0, cmdStr.length()-2);
		 
		 rowx.add(cmdStr);
		 
		 lstData.add(rowx);
		 lstLinkData.add(String.valueOf(appPrivObj.getOID()));
	}						

	return ctrlist.draw();
}

%>
<%
 
/* VARIABLE DECLARATION */

int recordToGet = 20;
String order = " " + PstAppPrivilegeObj.fieldNames[PstAppPrivilegeObj.FLD_CODE];

Vector listAppPrivObj = new Vector(1,1);


/* GET REQUEST FROM HIDDEN TEXT */
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start"); 
int listCommand = FRMQueryString.requestInt(request, "list_command");
if(listCommand==Command.NONE)
	listCommand = Command.FIRST;
long appPrivOID = FRMQueryString.requestLong(request,"appriv_oid");
long appPrivObjOID = FRMQueryString.requestLong(request,"apprivobj_oid");

CtrlAppPrivilegeObj ctrlAppPrivObj = new CtrlAppPrivilegeObj(request);
FrmAppPrivilegeObj frmAppPrivObj = ctrlAppPrivObj.getForm();
String cmdIdxString[] = request.getParameterValues("cmd_assigned");

  
/* GET OBJECT */ 
AppPriv appPriv = PstAppPriv.fetch(appPrivOID);
int iErrCode = ctrlAppPrivObj.action(iCommand, appPrivObjOID);
AppPrivilegeObj appPrivObj= ctrlAppPrivObj.getAppPrivObj(); 
int vectSize = PstAppPrivilegeObj.getCountByPrivOID_GroupByObj(appPrivOID); 


/* GET Modules Access */
int appObjG1 = FRMQueryString.requestInt(request,FrmAppPrivilegeObj.fieldNames[FrmAppPrivilegeObj.FRM_G1_IDX]);
int appObjG2 = FRMQueryString.requestInt(request,FrmAppPrivilegeObj.fieldNames[FrmAppPrivilegeObj.FRM_G2_IDX]);
int appObjIdx = FRMQueryString.requestInt(request,FrmAppPrivilegeObj.fieldNames[FrmAppPrivilegeObj.FRM_OBJ_IDX]);

if(iCommand == Command.EDIT){  
  appObjG1 =(AppObjInfo.getIdxGroup1(appPrivObj.getCode()));
  appObjG2 =(AppObjInfo.getIdxGroup2(appPrivObj.getCode()));
  appObjIdx =(AppObjInfo.getIdxObject(appPrivObj.getCode())); 
   System.out.println(" IDX "+ appObjG1+ " "+ appObjG2+ " "+ appObjIdx +" " + appPrivObj.getCommands());
  appObjG1 = appObjG1<0 ? 0 : appObjG1;
  appObjG2 = appObjG2<0 ? 0 : appObjG2;
  appObjIdx = appObjIdx<0 ? 0 : appObjIdx;
}

String msgString = ctrlAppPrivObj.getMessage();
start=ctrlAppPrivObj.actionList(listCommand,start,vectSize,recordToGet);
order=	PstAppPrivilegeObj.fieldNames[PstAppPrivilegeObj.FLD_CODE];	
listAppPrivObj = PstAppPrivilegeObj.listWithCmd_ByPrivOID_GroupByObj(start,recordToGet, appPrivOID , order);


%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HAIRISMA - Privilege Edit</title>
        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <style type="text/css">
            .tblStyle {border-collapse: collapse; }
            .tblStyle td {padding: 5px 7px; border: 1px solid #CCC; font-size: 12px; }
            .tr1 {background-color: #FFF;}
            .tr2 {background-color: #EEE;}
            .tblForm { }
            .tblForm td {padding: 5px 7px; font-weight: bold; font-size: 12px; }
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            .title_page {color:#0db2e1; font-weight: bold; font-size: 14px; background-color: #EEE; border-left: 1px solid #0099FF; padding: 12px 18px;}

            body {color:#373737;}
            #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3;}
            #menu_title {color:#0099FF; font-size: 14px;}
            #menu_teks {color:#CCC;}
            #style_add {
                color: #FFF;
                font-size: 12px;
                background-color: #9bdf3b;
                padding: 5px 9px;
                border-radius: 3px;
            }
            #style_edit {
                color: #FFF;
                font-size: 12px;
                background-color: #67c3cc;
                padding: 5px 9px;
                border-radius: 3px;
            }
            #style_delete {
                color: #FFF;
                font-size: 12px;
                background-color: #ea4e6f;
                padding: 5px 9px;
                border-radius: 3px;
            }
            #style_login {
                color: #FFF;
                font-size: 12px;
                background-color: #FF7E00;
                padding: 5px 9px;
                border-radius: 3px;
            }
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
            .title_part {color:#FF7E00; background-color: #F7F7F7; border-left: 1px solid #0099FF; padding: 9px 11px;}
            
            body {background-color: #EEE; font-size: 12px;}
            .header {
                
            }
            .content-main {
                padding: 5px 25px 25px 25px;
                margin: 0px 23px 59px 23px;
            }
            .content-info {
                padding: 21px;
                border-bottom: 1px solid #E5E5E5;
            }
            .content-title {
                padding: 21px;
                border-bottom: 1px solid #E5E5E5;
                margin-bottom: 5px;
            }
            #title-large {
                color: #575757;
                font-size: 16px;
                font-weight: bold;
            }
            #title-small {
                color:#797979;
                font-size: 11px;
            }
            .content {
                padding: 21px;
            }
            .box {
                margin: 17px 7px;
                background-color: #FFF;
                color:#575757;
            }
            #box_title {
                padding:21px; 
                font-size: 14px; 
                color: #007fba;
                border-top: 1px solid #28A7D1;
                border-bottom: 1px solid #EEE;
            }
            #box_content {
                padding:21px; 
                font-size: 12px;
                color: #575757;
            }
            #info {
                background-color: #DDD;
                color:#474747;
                margin-top: 21px;
                padding: 12px 17px;
                border-radius: 3px;
            }
            .box-info {
                padding:21px 43px; 
                background-color: #F7F7F7;
                border-bottom: 1px solid #CCC;
                -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, 0.065);
                 -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, 0.065);
                      box-shadow: 0 1px 4px rgba(0, 0, 0, 0.065);
            }
            #title-info-name {
                padding: 11px 15px;
                font-size: 35px;
                color: #535353;
            }
            #title-info-desc {
                padding: 7px 15px;
                font-size: 21px;
                color: #676767;
            }
            
            #photo {
                padding: 7px; 
                background-color: #FFF; 
                border: 1px solid #DDD;
            }
            .btn {
                background-color: #00a1ec;
                border-radius: 3px;
                font-family: Arial;
                border-radius: 3px;
                color: #EEE;
                font-size: 12px;
                padding: 7px 11px 7px 11px;
                border: solid #00a1ec 1px;
                text-decoration: none;
            }

            .btn:hover {
                color: #FFF;
                background-color: #007fba;
                text-decoration: none;
                border: 1px solid #007fba;
            }
            
            .btn-small {
                text-decoration: none;
                padding: 3px;
                background-color: #00a1ec; color: #EEE; 
                font-size: 11px; cursor: pointer;
                border-radius: 3px;
                border:1px solid #00a1ec;
            }
            .btn-small:hover {background-color: #007fba; color: #FFF; border:1px solid #007fba;}
            
            .tbl-main {border-collapse: collapse; font-size: 11px; background-color: #FFF; margin: 0px;}
            .tbl-main td {padding: 4px 7px; border: 1px solid #DDD; }
            #tbl-title {font-weight: bold; background-color: #F5F5F5; color: #575757;}
            
            .tbl-small {border-collapse: collapse; font-size: 11px; background-color: #FFF;}
            .tbl-small td {padding: 2px 3px; border: 1px solid #DDD; }
            
            #caption {
                font-size: 12px;
                color: #474747;
                font-weight: bold;
                padding: 2px 0px 3px 0px;
            }
            #divinput {
                margin-bottom: 5px;
                padding-bottom: 5px;
            }
            
            #div_item_sch {
                background-color: #EEE;
                color: #575757;
                padding: 5px 7px;
            }
            
            .form-style {
                font-size: 12px;
                color: #575757;
                border: 1px solid #DDD;
                border-radius: 5px;
            }
            .form-title {
                padding: 11px 21px;
                margin-bottom: 2px;
                border-bottom: 1px solid #DDD;
                background-color: #EEE;
                border-top-left-radius: 5px;
                border-top-right-radius: 5px;
                font-weight: bold;
            }
            .form-content {
                padding: 21px;
            }
            .form-footer {
                border-top: 1px solid #DDD;
                padding: 11px 21px;
                margin-top: 2px;
                background-color: #EEE;
                border-bottom-left-radius: 5px;
                border-bottom-right-radius: 5px;
            }
            #confirm {
                padding: 18px 21px;
                background-color: #FF6666;
                color: #FFF;
                border: 1px solid #CF5353;
            }
            #btn-confirm {
                padding: 3px; border: 1px solid #CF5353; 
                background-color: #CF5353; color: #FFF; 
                font-size: 11px; cursor: pointer;
            }
            .footer-page {
                font-size: 12px;
            }
            td { font-size: 12px; }
            
        </style>
        <script language="JavaScript">
        function cmdAdd(){
                document.frmList.command.value="<%=Command.ADD%>"; 
                document.frmList.list_command.value="<%=Command.LIST%>";
                document.frmList.submit();	
        }

        function cmdEdit(oid){
                document.frmList.command.value="<%=Command.EDIT%>"; 
                document.frmList.apprivobj_oid.value=oid;
                document.frmList.list_command.value="<%=Command.LIST%>";
                document.frmList.submit();	
        }

        function cmdSave(){
                document.frmEdit.command.value="<%=Command.SAVE%>"; 
                document.frmEdit.list_command.value="<%=Command.LIST%>";
                document.frmEdit.submit();	
        }

        function gotoManagementMenu(){
                document.frmList.action="<%=approot%>/management/main_man.jsp";
                document.frmList.submit();
        }


        <% if(privDelete) {%>
        function cmdCancel(){
                document.frmEdit.command.value="<%=Command.EDIT%>"; 
                document.frmEdit.list_command.value="<%=Command.LIST%>";
                document.frmEdit.submit();	
        }

        function cmdAsk(){
                document.frmEdit.command.value="<%=Command.ASK%>"; 
                document.frmEdit.list_command.value="<%=Command.LIST%>";
                document.frmEdit.submit();	
        }

        function cmdDelete(){
                document.frmEdit.command.value="<%=Command.DELETE%>"; 
                document.frmEdit.list_command.value="<%=Command.LIST%>";
                document.frmEdit.submit();	
        }
        <%}%>
        function changeG1(){
                document.frmEdit.command.value="<%=iCommand%>"; 
                document.frmEdit.list_command.value="<%=Command.LIST%>";
                document.frmEdit.submit();	
        }

        function changeG2(){
                document.frmEdit.command.value="<%=iCommand%>"; 
                document.frmEdit.list_command.value="<%=Command.LIST%>";
                document.frmEdit.submit();	
        }

        function changeModule(){
                document.frmEdit.command.value="<%=iCommand%>"; 
                document.frmEdit.list_command.value="<%=Command.LIST%>";
                document.frmEdit.submit();	
        }


        function cmdListFirst(){
                document.frmList.list_command.value="<%=Command.FIRST%>";
                document.frmList.command.value="<%=Command.NONE%>";
                document.frmList.action="privilegeedit.jsp";
                document.frmList.submit();
        }

        function cmdListPrev(){
                document.frmList.list_command.value="<%=Command.PREV%>";
                document.frmList.command.value="<%=Command.NONE%>";
                document.frmList.action="privilegeedit.jsp";
                document.frmList.submit();
        }

        function cmdListNext(){
                document.frmList.list_command.value="<%=Command.NEXT%>";
                document.frmList.command.value="<%=Command.NONE%>";
                document.frmList.action="privilegeedit.jsp";
                document.frmList.submit();
        }
        function cmdListLast(){
                document.frmList.list_command.value="<%=Command.LAST%>";
                document.frmList.command.value="<%=Command.NONE%>";
                document.frmList.action="privilegeedit.jsp";
                document.frmList.submit();
        }

        function goPrivilege(){
                document.frmList.command.value="<%=Command.BACK%>";
                document.frmList.action="privilegelist.jsp";
                document.frmList.submit();
        }

        function cmdBack(){
                document.frmEdit.command.value="<%=Command.BACK%>";
                document.frmEdit.action="privilegeedit.jsp";
                document.frmEdit.submit();
        }
        </script>
    </head>
    <body>
        <div class="header">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../styletemplate/template_header.jsp" %>
            <%} else {%>
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
            </table>
        </div>
        <div id="menu_utama">
            <span id="menu_title" style="font-weight: bold;">User Management <strong style="color:#333;"> / </strong>Privilege Edit</span>
        </div>
        <div class="content-main">
            <form name="frmList" method="post" action="privilegeedit.jsp">
                <table width="100%">
                  <tr> 
                    <td width="167" nowrap> Privilege 
                      Name </td>
                    <td width="679" nowrap>&nbsp;<%=appPriv.getPrivName()%> 
                    </td>
                  </tr>
                  <tr> 
                    <td width="167">Description </td>
                    <td width="679" nowrap>&nbsp;<%=appPriv.getDescr()%></td>
                  </tr>
                  <% if(listAppPrivObj != null && listAppPrivObj.size()>0){%>
                  <tr> 
                    <td colspan="2" class="listtitle">Module 
                      Access List</td>
                  </tr>
                  <tr> 
                    <td colspan="2"><%=drawListPrivObj(listAppPrivObj )%> 
                    </td>
                  </tr>
                  <% }else{%>
                  <tr> 
                    <td colspan="2" class="comment">No 
                      Access available </td>
                  </tr>
                  <%}%>
                  <tr> 
                    <td colspan="2" class="command"> 
                      <% ControlLine ctrLine = new ControlLine(); %>
                      <%
                       ctrLine.setLocationImg(approot+"/images");
                                                                       ctrLine.initDefault();						   					   
                                                            %>
                      <%=ctrLine.drawImageListLimit(listCommand,vectSize,start,recordToGet)%> 
                    </td>
                  </tr>											  
                  <tr> 
                    <td colspan="2" class="command"> 
                      <% if(iCommand == Command.LIST || iCommand == Command.SAVE || iCommand == Command.DELETE || iCommand == Command.BACK ||
                             iCommand == Command.FIRST || iCommand == Command.PREV || iCommand == Command.NEXT || iCommand == Command.LAST ){%>
                      <table width="38%" border="0" cellspacing="1" cellpadding="2">
                        <% if(privAdd && privUpdate){%>
                        <tr> 
                          <td width="9%"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100111','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100111" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New Privilege"></a></td>
                          <td nowrap width="41%"><a href="javascript:cmdAdd()" class="command">Add 
                            New Module Access</a></td>
                          <td width="7%"><a href="javascript:goPrivilege()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('G2','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="G2" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back To Privilege List"></a></td>
                          <td class="command" width="43%"><a href="javascript:goPrivilege()">Back 
                            To Privilege List</a></td>
                        </tr>
                        <% }%>
                      </table>
                      <%}%>
                      <input type="hidden" name="appriv_oid" value="<%=appPrivOID%>">
                      <input type="hidden" name="apprivobj_oid" value="<%=appPrivObjOID%>">
                      <input type="hidden" name="command" value="<%=iCommand%>">
                      <input type="hidden" name="list_command" value="<%=listCommand%>">
                      <input type="hidden" name="<%=FrmAppPrivilegeObj.fieldNames[FrmAppPrivilegeObj.FRM_G1_IDX]%>" value="<%=appObjG1%>">
                      <input type="hidden" name="<%=FrmAppPrivilegeObj.fieldNames[FrmAppPrivilegeObj.FRM_G2_IDX]%>" value="<%=appObjG2%>">
                      <input type="hidden" name="<%=FrmAppPrivilegeObj.fieldNames[FrmAppPrivilegeObj.FRM_OBJ_IDX]%>" value="<%=appObjIdx%>">
                      <input type="hidden" name="start" value="<%=start%>">
                    </td>
                  </tr>
                </table>
              </form>
              <!------------------->
              <%if(((iCommand==Command.SAVE)&&(frmAppPrivObj.errorSize()>0))||(iCommand==Command.ADD)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                                          <form name="frmEdit" method="post" action="">
                                            <table width="100%" border="0" cellspacing="1" cellpadding="1">
                                              <tr> 
                                                <td width="167">Group 1 
                                                  <input type="hidden" name="appriv_oid" value="<%=appPrivOID%>">
                                                  <input type="hidden" name="<%=FrmAppPrivilegeObj.fieldNames[FrmAppPrivilegeObj.FRM_PRIV_ID]%>" value="<%=appPrivOID%>">
                                                  <input type="hidden" name="apprivobj_oid" value="<%=appPrivObjOID%>">
                                                  <input type="hidden" name="command" value="<%=iCommand%>">
                                                  <input type="hidden" name="list_command" value="<%=listCommand%>">
                                                  <input type="hidden" name="start" value="<%=start%>">
                                                </td>
                                                <td width="679" nowrap> 
                                                  <% if (iCommand==Command.ADD) {%>
                                                  <select name="<%=FrmAppPrivilegeObj.fieldNames[FrmAppPrivilegeObj.FRM_G1_IDX]%>" class="elemenForm" onChange="javascript:changeG1()">
                                                    <% for(int ig1=0;ig1< AppObjInfo.titleG1.length; ig1++){
                        String select = (appObjG1 == ig1) ? "selected" : "";
						  try{
                        %>
                                                    <option value="<%=ig1%>" <%=select%>><%=AppObjInfo.titleG1[ig1]%></option>
                                                    <% 
							  } catch(Exception exc){
							     System.out.println(" CREATE LIST ==> privilegeedit.jsp. G1 exc"+exc);
							  }
							
							}%>
                                                  </select>
                                                  <% } else { %>
                                                  <%=AppObjInfo.titleG1[appObjG1]%> 
                                                  <input type="hidden" name="<%=FrmAppPrivilegeObj.fieldNames[FrmAppPrivilegeObj.FRM_G1_IDX]%>" value="<%=appObjG1%>">
                                                  <%}%>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="167">Group 2</td>
                                                <td width="679" nowrap> 
                                                  <% if (iCommand==Command.ADD) {%>
                                                  <select name="<%=FrmAppPrivilegeObj.fieldNames[FrmAppPrivilegeObj.FRM_G2_IDX]%>" onChange="javascript:changeG2()">
                                                    <% for(int ig2=0;ig2< AppObjInfo.titleG2[appObjG1].length; ig2++){
                        String select = (appObjG2 == ig2) ? "selected" : "";
						  try{
                        %>
                                                    <option value="<%=ig2%>" <%=select%>><%=AppObjInfo.titleG2[appObjG1][ig2]%></option>
                                                    <% 
							  } catch(Exception exc){
							     System.out.println(" CREATE LIST ==> privilegeedit.jsp. G2 exc"+exc);
							  }
							
							}%>
                                                  </select>
                                                  <% } else { %>
                                                  <%=AppObjInfo.titleG2[appObjG1][appObjG2]%> 
                                                  <input type="hidden" name="<%=FrmAppPrivilegeObj.fieldNames[FrmAppPrivilegeObj.FRM_G2_IDX]%>" value="<%=appObjG2%>">
                                                  <%}%>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="167"> Module</td>
                                                <td width="679" nowrap> 
                                                  <% if (iCommand==Command.ADD) {%>
                                                  <select name="<%=FrmAppPrivilegeObj.fieldNames[FrmAppPrivilegeObj.FRM_OBJ_IDX]%>" onChange="javascript:changeModule()">
                                                    <% for(int iobj=0;iobj< AppObjInfo.objectTitles[appObjG1][appObjG2].length; iobj++){
                        String select = (appObjIdx == iobj) ? "selected" : "";
						  try{
                        %>
                                                    <option value="<%=iobj%>" <%=select%>><%=AppObjInfo.objectTitles[appObjG1][appObjG2][iobj]%></option>
                                                    <%
							  } catch(Exception exc){
							     System.out.println(" CREATE LIST ==> privilegeedit.jsp. Object exc"+exc);
							  }
							 }%>
                                                  </select>
                                                  <% } else { %>
                                                  <%=AppObjInfo.objectTitles[appObjG1][appObjG2][appObjIdx]%> 
                                                  <input type="hidden" name="<%=FrmAppPrivilegeObj.fieldNames[FrmAppPrivilegeObj.FRM_OBJ_IDX]%>" value="<%=appObjIdx%>">
                                                  <%}%>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="167">Authorization</td>
                                                <td width="679" nowrap> 
                                                  <% for(int id=0;id< AppObjInfo.objectCommands[appObjG1][appObjG2][appObjIdx].length; id++){
                            int iCmd= AppObjInfo.objectCommands[appObjG1][appObjG2][appObjIdx][id];
                            String checked = appPrivObj.existCommand(iCmd) ? "checked" : "";
                        %>
                                                  <input type="checkbox" name="<%=FrmAppPrivilegeObj.fieldNames[FrmAppPrivilegeObj.FRM_COMMANDS]%>" value="<%=iCmd%>" <%=checked%>>
                                                  <%=AppObjInfo.strCommand[iCmd]%> 
                                                  &nbsp;&nbsp;&nbsp;&nbsp; 
                                                  <% }%>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="167">&nbsp;</td>
                                                <td width="679" nowrap>&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td colspan="2"> 
                                                  <%
							ctrLine.setLocationImg(approot+"/images");
							ctrLine.initDefault();
							ctrLine.setTableWidth("80%");
							String scomDel = "javascript:cmdAsk('"+appPrivObjOID+"')";
							String sconDelCom = "javascript:cmdDelete('"+appPrivObjOID+"')";
							String scancel = "javascript:cmdCancel('"+appPrivObjOID+"')";
							ctrLine.setBackCaption("Back to Module Access List");
							ctrLine.setCommandStyle("buttonlink");
							ctrLine.setSaveCaption("Save Module Access");
							ctrLine.setDeleteCaption("Delete Module Access");
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
							
							%>
                                                  <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%> 
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td colspan="2"> </td>
                                              </tr>
                                              <tr> 
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                              </tr>
                                            </table>
                                          </form>

                                      <% } %>
        </div>
        <div class="footer-page">
            <table>
                <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
                <tr>
                    <td valign="bottom"><%@include file="../footer.jsp" %></td>
                </tr>
                <%} else {%>
                <tr> 
                    <td colspan="2" height="20" ><%@ include file = "../main/footer.jsp" %></td>
                </tr>
                <%}%>
            </table>
        </div>
    </body>
</html>
