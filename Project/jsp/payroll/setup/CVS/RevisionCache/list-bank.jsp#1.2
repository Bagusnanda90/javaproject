 <% 
/* 
 * Page Name  		:  list-bank.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		: autami
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
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.form.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_SETUP, AppObjInfo.OBJ_PAYROLL_SETUP_BANK);%>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
privDelete=false;
%>
<%!
	public String drawList(int iCommand,FrmPayBanks frmObject, PayBanks objEntity, Vector objectClass,  long bankId, boolean privUpdate)
	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
                ctrlist.setListStyle("tblStyle");
                ctrlist.setTitleStyle("title_tbl");
                ctrlist.setCellStyle("listgensell");
                ctrlist.setHeaderStyle("title_tbl");
                ctrlist.setCellSpacing("0");
		ctrlist.addHeader("<strong>Bank Name</strong>","20%");
		ctrlist.addHeader("<strong>Bank Branch</strong>","20%");
		ctrlist.addHeader("<strong>Swift Code</strong>","20%");
		ctrlist.addHeader("<strong>Address</strong>","20%");
		ctrlist.addHeader("<strong>Tel./Fax</strong>","20%");
		
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		Vector rowx = new Vector(1,1);
		ctrlist.reset();
		int index = -1;
		
		for (int i = 0; i < objectClass.size(); i++) {
			 PayBanks payBanks = (PayBanks)objectClass.get(i);
			 rowx = new Vector();
			 if(bankId == payBanks.getOID())
				 index = i;
				 
			 if(index == i && (iCommand == Command.EDIT || iCommand == Command.ASK)){					
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayBanks.FRM_FIELD_BANK_NAME] +"\" value=\""+payBanks.getBankName()+"\" class=\"formElement\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayBanks.FRM_FIELD_BANK_BRANCH] +"\" value=\""+payBanks.getBankBranch()+"\" class=\"formElement\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayBanks.FRM_FIELD_BANK_SWIFTCODE] +"\" value=\""+payBanks.getSwiftCode()+"\" class=\"formElement\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayBanks.FRM_FIELD_BANK_ADDRESS] +"\" value=\""+payBanks.getBankAddress()+"\" class=\"formElement\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayBanks.FRM_FIELD_BANK_TELP] +"\" value=\""+payBanks.getBankTelp()+"\" class=\"formElement\">");			
			}else{
                             if (privUpdate == true){
                                 rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(payBanks.getOID())+"')\">"+payBanks.getBankName()+"</a>");
                             } else {
                                 rowx.add(payBanks.getBankName());
                             }
				
				rowx.add(payBanks.getBankBranch());
				rowx.add(payBanks.getSwiftCode());
				rowx.add(payBanks.getBankAddress());
				rowx.add(payBanks.getBankTelp());				
			} 

			lstData.add(rowx);	 
		} 
			rowx = new Vector();
	
			if(iCommand == Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize() > 0)){ 
					rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayBanks.FRM_FIELD_BANK_NAME] +"\" value=\""+objEntity.getBankName()+"\" class=\"formElement\">");
					rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayBanks.FRM_FIELD_BANK_BRANCH] +"\" value=\""+objEntity.getBankBranch()+"\" class=\"formElement\">");
					rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayBanks.FRM_FIELD_BANK_SWIFTCODE] +"\" value=\""+objEntity.getSwiftCode()+"\" class=\"formElement\">");
					rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayBanks.FRM_FIELD_BANK_ADDRESS] +"\" value=\""+objEntity.getBankAddress()+"\" class=\"formElement\">");
					rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmPayBanks.FRM_FIELD_BANK_TELP] +"\" value=\""+objEntity.getBankTelp()+"\" class=\"formElement\">");
			}
	
			lstData.add(rowx);
	
			return ctrlist.draw();
	}


%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidBank = FRMQueryString.requestLong(request, "hidden_bank_id");

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = "";

CtrlPayBanks ctrlPayBanks = new CtrlPayBanks(request);
ControlLine ctrLine = new ControlLine();
Vector listPayBanks = new Vector(1,1);

/*switch statement */
iErrCode = ctrlPayBanks.action(iCommand , oidBank);
/* end switch*/
FrmPayBanks frmPayBanks = ctrlPayBanks.getForm();

/*count list All kategori*/
int vectSize = PstPayBanks.getCount(whereClause);

/*switch list kategori*/
if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlPayBanks.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

PayBanks payBanks = ctrlPayBanks.getPayBanks();
msgString =  ctrlPayBanks.getMessage();

/* get record to display */
listPayBanks = PstPayBanks.list(start,recordToGet, whereClause , orderClause);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listPayBanks.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listPayBanks = PstPayBanks.list(start,recordToGet, whereClause , orderClause);
}

%>
<!-- JSP Block -->
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - BANK LIST</title>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<SCRIPT language=JavaScript>
<!--
function cmdAdd(){
		document.frmPayBanks.hidden_bank_id.value="0";
		document.frmPayBanks.command.value="<%=Command.ADD%>";
		document.frmPayBanks.prev_command.value="<%=prevCommand%>";
		document.frmPayBanks.action="list-bank.jsp";
		document.frmPayBanks.submit();
	}
	function cmdState(oid){
		document.frmPayBanks.hidden_bank_id.value=oid;
		document.frmPayBanks.command.value="<%=Command.NONE%>";
		document.frmPayBanks.action="state.jsp";
		document.frmPayBanks.submit();
	}

	function cmdAsk(oidBank){
		document.frmPayBanks.hidden_bank_id.value=oidBank;
		document.frmPayBanks.command.value="<%=Command.ASK%>";
		document.frmPayBanks.prev_command.value="<%=prevCommand%>";
		document.frmPayBanks.action="list-bank.jsp";
		document.frmPayBanks.submit();
	}

	function cmdConfirmDelete(oid){
		var x = confirm(" Are You Sure to Delete?");
		if(x){
			document.frmPayBanks.command.value="<%=Command.DELETE%>";
			document.frmPayBanks.action="list-bank.jsp";
			document.frmPayBanks.submit();
		}
	}
	
	function cmdSave(){
		document.frmPayBanks.command.value="<%=Command.SAVE%>";
		document.frmPayBanks.prev_command.value="<%=prevCommand%>";
		document.frmPayBanks.action="list-bank.jsp";
		document.frmPayBanks.submit();
	}
	
	function cmdEdit(oidBank){
		document.frmPayBanks.hidden_bank_id.value=oidBank;
		document.frmPayBanks.command.value="<%=Command.EDIT%>";
		document.frmPayBanks.prev_command.value="<%=prevCommand%>";
		document.frmPayBanks.action="list-bank.jsp";
		document.frmPayBanks.submit();
	}
	
	function cmdCancel(oidBank){
		document.frmPayBanks.hidden_bank_id.value=oidBank;
		document.frmPayBanks.command.value="<%=Command.EDIT%>";
		document.frmPayBanks.prev_command.value="<%=prevCommand%>";
		document.frmPayBanks.action="list-bank.jsp";
		document.frmPayBanks.submit();
	}
	
	function cmdBack(){
		document.frmPayBanks.command.value="<%=Command.BACK%>";
		document.frmPayBanks.action="list-bank.jsp";
		document.frmPayBanks.submit();
	}
	
	function cmdListFirst(){
		document.frmPayBanks.command.value="<%=Command.FIRST%>";
		document.frmPayBanks.prev_command.value="<%=Command.FIRST%>";
		document.frmPayBanks.action="list-bank.jsp";
		document.frmPayBanks.submit();
	}
	
	function cmdListPrev(){
		document.frmPayBanks.command.value="<%=Command.PREV%>";
		document.frmPayBanks.prev_command.value="<%=Command.PREV%>";
		document.frmPayBanks.action="list-bank.jsp";
		document.frmPayBanks.submit();
	}
	
	function cmdListNext(){
		document.frmPayBanks.command.value="<%=Command.NEXT%>";
		document.frmPayBanks.prev_command.value="<%=Command.NEXT%>";
		document.frmPayBanks.action="list-bank.jsp";
		document.frmPayBanks.submit();
	}
	
	function cmdListLast(){
		document.frmPayBanks.command.value="<%=Command.LAST%>";
		document.frmPayBanks.prev_command.value="<%=Command.LAST%>";
		document.frmPayBanks.action="list-bank.jsp";
		document.frmPayBanks.submit();
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
	
	function showObjectForMenu(){
        
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
//-->
</SCRIPT>
<style type="text/css">
    .tblStyle {border-collapse: collapse;}
    .tblStyle td {padding: 7px 9px; border: 1px solid #CCC; font-size: 12px;}
    .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
    .title_page {color:#0db2e1; font-weight: bold; font-size: 14px; background-color: #EEE; border-left: 1px solid #0099FF; padding: 12px 18px;}
    body {color:#373737;}
    #menu_utama {padding: 9px 14px; border-bottom: 1px solid #CCC; font-size: 14px; background-color: #F3F3F3;}
    #menu_title {color:#0099FF; font-size: 14px;}

    .title_part {
        font-size: 12px;
        color:#0099FF; 
        background-color: #F7F7F7; 
        border-left: 1px solid #0099FF; 
        padding: 7px 9px;
    }

    body {background-color: #EEE;}
    .header {

    }
    .content-main {
        padding: 21px 32px;
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
    
    .btn {
        background-color: #00a1ec;
        border-radius: 3px;
        font-family: Arial;
        color: #EEE;
        font-size: 12px;
        padding: 7px 11px 7px 11px;
        text-decoration: none;
    }

    .btn:hover {
        color: #FFF;
        background-color: #007fba;
        text-decoration: none;
    }

    .btn-small {
        font-family: sans-serif;
        text-decoration: none;
        padding: 5px 7px; 
        background-color: #676767; color: #F5F5F5; 
        font-size: 11px; cursor: pointer;
        border-radius: 3px;
    }
    .btn-small:hover { background-color: #474747; color: #FFF;}

    .btn-small-1 {
        font-family: sans-serif;
        text-decoration: none;
        padding: 5px 7px; 
        background-color: #EEE; color: #575757; 
        font-size: 11px; cursor: pointer;
        border-radius: 3px;
    }
    .btn-small-1:hover { background-color: #DDD; color: #474747;}

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

    #record_count{
        font-size: 12px;
        font-weight: bold;
        padding-bottom: 9px;
    }
    #box-form {
        background-color: #EEE; 
        border-radius: 5px;
    }
    .form-style {
        font-size: 12px;
        color: #575757;
        border: 1px solid #DDD;
        border-radius: 5px;
    }
    .form-title {
        padding: 11px 21px;
        text-align: left;
        border-bottom: 1px solid #DDD;
        background-color: #FFF;
        border-top-left-radius: 5px;
        border-top-right-radius: 5px;
        font-weight: bold;
    }
    .form-content {
        text-align: left;
        padding: 21px;
        background-color: #DDD;
    }
    .form-footer {
        border-top: 1px solid #DDD;
        padding: 11px 21px;
        background-color: #FFF;
        border-bottom-left-radius: 5px;
        border-bottom-right-radius: 5px;
    }
    #confirm {
        padding: 13px 17px;
        background-color: #FF6666;
        color: #FFF;
        border-radius: 3px;
        font-size: 12px;
        font-weight: bold;
        visibility: hidden;
    }
    #btn-confirm {
        padding: 4px 9px; border-radius: 3px;
        background-color: #CF5353; color: #FFF; 
        font-size: 11px; cursor: pointer;
    }
    .footer-page {
        font-size: 12px;
    }
</style>
<!-- #EndEditable --> 
</head>
<body>
    <div class="header">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../../styletemplate/template_header.jsp" %>
            <%} else {%>
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
            <%}%>
            </table>
        </div>
        <div id="menu_utama">
            <span id="menu_title">Payroll <strong style="color:#333;"> / </strong>Bank List</span>
        </div>
        <div class="content-main">
            <div class="tablecolor">
                <form name="frmPayBanks" method ="post" action="">
                <input type="hidden" name="command" value="<%=iCommand%>">
                <input type="hidden" name="vectSize" value="<%=vectSize%>">
                <input type="hidden" name="start" value="<%=start%>">
                <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                <input type="hidden" name="hidden_bank_id" value="<%=oidBank%>">
                <%
                if (listPayBanks != null && listPayBanks.size()>0){
                    %>
                    <%= drawList(iCommand, frmPayBanks, payBanks,listPayBanks,oidBank, privUpdate)%> 
                    <%
                } else {
                    %>
                    No Data
                    <%
                }
                %>
                 
									  
		<%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmPayBanks.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%> 
                <div>
                    
                <%
                ctrLine.setLocationImg(approot+"/images");
                ctrLine.initDefault();
                ctrLine.setTableWidth("80%");
                String scomDel = "javascript:cmdAsk('"+oidBank+"')";
                String sconDelCom = "javascript:cmdConfirmDelete('"+oidBank+"')";
                String scancel = "javascript:cmdEdit('"+oidBank+"')";
                ctrLine.setBackCaption("Back to List");
                ctrLine.setCommandStyle("buttonlink");
                //ctrLine.setBackCaption("Back to List Department");
                ctrLine.setSaveCaption("Save Bank");
                ctrLine.setConfirmDelCaption("Yes Delete Bank");
                ctrLine.setDeleteCaption("Delete Bank");									

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
                </div>
		<% } %>

                <div>
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
                                      cmd = PstPayBanks.findLimitCommand(start,recordToGet,vectSize);
                              else									 
                                      cmd =prevCommand;
                            } 
                          }

                    %>
                  <% ctrLine.setLocationImg(approot+"/images");
                         ctrLine.initDefault();
                   %>
                  <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%>
                </div>
                <div>&nbsp;</div>
                    <%//if((iCommand == Command.NONE || iCommand == Command.DELETE || iCommand ==Command.BACK || iCommand ==Command.SAVE)&& (frmDepartment.errorSize()<1)){
                         if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmPayBanks.errorSize()<1)){

                        if (privAdd == true){
                            %>
                            <a href="javascript:cmdAdd()" style="color:#FFF;" class="btn">Add New Bank</a>
                            <%
                        }
                        %>


                     <% } %>

                        <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmPayBanks.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%> 
                          <a href="javascript:cmdSave()" style="color:#FFF;" class="btn">Save Bank</a>
                          <a href="javascript:cmdConfirmDelete()" style="color:#FFF;" class="btn">Delete Bank</a>
                         <a href="javascript:cmdBack()" style="color:#FFF;" class="btn">Back to List Banks</a> 
                         <%
                        }
                        %>

                 </form>
            </div>                       
        </div>  

        <div class="footer-page">
            <table>
                <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
                <tr>
                    <td valign="bottom"><%@include file="../../footer.jsp" %></td>
                </tr>
                <%} else {%>
                <tr> 
                    <td colspan="2" height="20" ><%@ include file = "../../main/footer.jsp" %></td>
                </tr>
                <%}%>
            </table>
        </div> 
</body>
</html>
