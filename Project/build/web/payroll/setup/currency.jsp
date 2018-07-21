 
<%@ page language="java" %>
<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.form.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_SETUP, AppObjInfo.OBJ_PAYROLL_SETUP_CURRENCY);%>
<%@ include file = "../../main/checkuser.jsp" %>

<%
privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
%>


<%
	CtrlCurrencyType ctrlCurrencyType = new CtrlCurrencyType(request);
	long oidCurrencyType = FRMQueryString.requestLong(request, "currency_type_oid");
	int prevCommand = FRMQueryString.requestInt(request, "prev_command");
	int iCommand = FRMQueryString.requestCommand(request);
	int start = FRMQueryString.requestInt(request,"start");
	
	int iErrCode = FRMMessage.ERR_NONE;
	String msgString = "";
	ControlLine ctrLine = new ControlLine();
	System.out.println("iCommand = "+iCommand);
	iErrCode = ctrlCurrencyType.action(iCommand , oidCurrencyType);
	msgString = ctrlCurrencyType.getMessage();
	FrmCurrencyType frmCurrencyType = ctrlCurrencyType.getForm();
	CurrencyType currencyType = ctrlCurrencyType.getCurrencyType();
	oidCurrencyType = currencyType.getOID();
	
	
	 /*variable declaration*/
	//String frmCurrency = "#,###";
    int recordToGet = 10;
    String whereClause = "";
    String orderClause = " TAB_INDEX ";
	Vector listCurrencyType= new Vector(1,1);
	
	 /*switch statement */
    iErrCode = ctrlCurrencyType.action(iCommand , oidCurrencyType);
    /* end switch*/
   

    /*count list All Language*/
    int vectSize = PstCurrencyType.getCount(whereClause);

    /*switch list Language*/
    if((iCommand == Command.FIRST || iCommand == Command.PREV )||
      (iCommand == Command.NEXT || iCommand == Command.LAST)){
                    start = ctrlCurrencyType.actionList(iCommand, start, vectSize, recordToGet);
     } 
    /* end switch list*/

    //PayExecutive payExecutive = ctrLanguage.getLanguage();
    msgString =  ctrlCurrencyType.getMessage();

    /* get record to display */
    listCurrencyType = PstCurrencyType.list(start,recordToGet, whereClause , orderClause);

    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listCurrencyType.size() < 1 && start > 0)
    {
             if (vectSize - recordToGet > recordToGet)
                            start = start - recordToGet;   //go to Command.PREV
             else{
                     start = 0 ;
                     iCommand = Command.FIRST;
                     prevCommand = Command.FIRST; //go to Command.FIRST
             }
             listCurrencyType = PstCurrencyType.list(start,recordToGet, whereClause , orderClause);
    }

%>

<!-- JSP Block -->
<%!
	public String drawList(int iCommand,FrmCurrencyType frmObject, CurrencyType objEntity, Vector objectClass,  long currencyTypeId, boolean privUpdate)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
                ctrlist.setListStyle("tblStyle");
                ctrlist.setTitleStyle("title_tbl");
                ctrlist.setCellStyle("listgensell");
                ctrlist.setHeaderStyle("title_tbl");
                ctrlist.setCellSpacing("0");
		ctrlist.addHeader("Code","");
		ctrlist.addHeader("Name","");
                ctrlist.addHeader("Format Currency","");
		ctrlist.addHeader("Description","");
		ctrlist.addHeader("Nr. of Series","");
		ctrlist.addHeader("Used","");
		Vector lstData = ctrlist.getData();
		Vector rowx = new Vector(1,1);
		ctrlist.reset();
		int index = -1;
		//untuk status currency dipakai atau tidak
		Vector useKey = new Vector();
        Vector useValue = new Vector();
	    useKey.add(PstCurrencyType.NO_USED+"");
        useKey.add(PstCurrencyType.YES_USED+"");
        useValue.add(PstCurrencyType.used[PstCurrencyType.NO_USED]);
		useValue.add(PstCurrencyType.used[PstCurrencyType.YES_USED]);
		
		for (int i = 0; i < objectClass.size(); i++) {
			 CurrencyType currencyType = (CurrencyType)objectClass.get(i);
			 rowx = new Vector();
			 if(currencyTypeId == currencyType.getOID())
				 index = i; 
			 if(index == i && (iCommand == Command.EDIT || iCommand == Command.ASK)){
			 	rowx.add("<input type=\"text\" align =\"center\" name=\""+frmObject.fieldNames[FrmCurrencyType.FRM_FIELD_CODE] +"\" value=\""+objEntity.getCode()+"\" size=\"20\" class=\"elemenForm\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmCurrencyType.FRM_FIELD_NAME] +"\" value=\""+objEntity.getName()+"\" size=\"30\" class=\"elemenForm\">");
                                rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmCurrencyType.FRM_FIELD_FORMAT_CURRENCY] +"\" value=\""+objEntity.getFormatCurrency()+"\" size=\"30\" class=\"elemenForm\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmCurrencyType.FRM_FIELD_DESCRIPTION] +"\" value=\""+objEntity.getDescription()+"\" size=\"30\" class=\"elemenForm\"> ");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmCurrencyType.FRM_FIELD_TAB_INDEX] +"\" value=\""+objEntity.getTabIndex()+"\" size=\"30\" class=\"elemenForm\">");
				rowx.add(""+ControlCombo.draw(FrmCurrencyType.fieldNames[FrmCurrencyType.FRM_FIELD_INCLUDE_INF_PROCESS],"formElemen",null, ""+objEntity.getIncludeInfProcess(), useKey, useValue)) ;
				}else{
				//System.out.println("aku cek");
                                if (privUpdate == true){
                                    rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(currencyType.getOID())+"')\">"+currencyType.getCode()+"</a>");
                                } else {
                                    rowx.add(""+currencyType.getCode());
                                }
				
				rowx.add(""+currencyType.getName());
                                rowx.add(""+currencyType.getFormatCurrency());
				rowx.add(""+currencyType.getDescription());
				rowx.add(""+currencyType.getTabIndex());
				//rowx.add(""+currencyType.getIncludeInfProcess());
				rowx.add(PstCurrencyType.used[currencyType.getIncludeInfProcess()]);
				
			} 
		lstData.add(rowx);
		}
		 rowx = new Vector();
		if(iCommand == Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize() > 0) || (objectClass.size()<1)){ 
				rowx.add("<input type=\"text\" align =\"center\" name=\""+frmObject.fieldNames[FrmCurrencyType.FRM_FIELD_CODE] +"\" value=\""+objEntity.getCode()+"\" size=\"20\" class=\"elemenForm\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmCurrencyType.FRM_FIELD_NAME] +"\" value=\""+objEntity.getName()+"\" size=\"30\" class=\"elemenForm\">");
                                rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmCurrencyType.FRM_FIELD_FORMAT_CURRENCY] +"\" value=\""+objEntity.getFormatCurrency()+"\" size=\"30\" class=\"elemenForm\">"); 
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmCurrencyType.FRM_FIELD_DESCRIPTION] +"\" value=\""+objEntity.getDescription()+"\" size=\"30\" class=\"elemenForm\"> ");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmCurrencyType.FRM_FIELD_TAB_INDEX] +"\" value=\""+objEntity.getTabIndex()+"\" size=\"30\" class=\"elemenForm\">");
				rowx.add(""+ControlCombo.draw(FrmCurrencyType.fieldNames[FrmCurrencyType.FRM_FIELD_INCLUDE_INF_PROCESS],"formElemen",null, ""+objEntity.getIncludeInfProcess(), useKey, useValue)) ;
		}
	 lstData.add(rowx);

		return ctrlist.draw();
	}
%>
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - </title>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<SCRIPT language=JavaScript>

function cmdSave(){
	document.frm_currency_type.command.value="<%=Command.SAVE%>";
	document.frm_currency_type.prev_command.value="<%=prevCommand%>";
	document.frm_currency_type.action="currency.jsp";
	document.frm_currency_type.submit();
}

function cmdAdd(){
	document.frm_currency_type.currency_type_oid.value="0";
	document.frm_currency_type.command.value="<%=Command.ADD%>";
	document.frm_currency_type.prev_command.value="<%=prevCommand%>";
	document.frm_currency_type.action="currency.jsp";
	document.frm_currency_type.submit();
}

function cmdBack(){
	document.frm_currency_type.command.value="<%=Command.BACK%>";
	document.frm_currency_type.action="currency.jsp";
	document.frm_currency_type.submit();
}
function cmdEdit(oidCurrencyType){
	document.frm_currency_type.currency_type_oid.value=oidCurrencyType;
	document.frm_currency_type.command.value="<%=Command.EDIT%>";
	document.frm_currency_type.prev_command.value="<%=prevCommand%>";
	document.frm_currency_type.action="currency.jsp";
	document.frm_currency_type.submit();
}

function cmdListFirst(){
	document.frm_currency_type.command.value="<%=Command.FIRST%>";
	document.frm_currency_type.prev_command.value="<%=Command.FIRST%>";
	document.frm_currency_type.action="currency.jsp";
	document.frm_currency_type.submit();
}

function cmdListPrev(){
	document.frm_currency_type.command.value="<%=Command.PREV%>";
	document.frm_currency_type.prev_command.value="<%=Command.PREV%>";
	document.frm_currency_type.action="currency.jsp";
	document.frm_currency_type.submit();
}

function cmdListNext(){
	document.frm_currency_type.command.value="<%=Command.NEXT%>";
	document.frm_currency_type.prev_command.value="<%=Command.NEXT%>";
	document.frm_currency_type.action="currency.jsp";
	document.frm_currency_type.submit();
}



function cmdListLast(){
	document.frm_currency_type.command.value="<%=Command.LAST%>";
	document.frm_currency_type.prev_command.value="<%=Command.LAST%>";
	document.frm_currency_type.action="currency.jsp";
	document.frm_currency_type.submit();
}

function cmdConfirmDelete(oid){
		var x = confirm(" Are you sure to delete?");
		if(x){
			document.frm_currency_type.command.value="<%=Command.DELETE%>";
			document.frm_currency_type.action="currency.jsp";
			document.frm_currency_type.submit();
		}
}

function cmdAsk(oid){
		document.frm_currency_type.command.value="<%=Command.ASK%>";
		document.frm_currency_type.action="currency.jsp";
		document.frm_currency_type.submit();
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

    body {background-color: #EEE; margin: 0;}
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
        <span id="menu_title">Payroll <strong style="color:#333;"> / </strong>Currency Type</span>
    </div>
    <div class="content-main">
        <form name="frm_currency_type" method="post" action="">
            <input type="hidden" name="command" value="">
            <input type="hidden" name="start" value="<%=start%>">
            <input type="hidden" name="currency_type_oid" value="<%=oidCurrencyType%>">
            <input type="hidden" name="prev_command" value="<%=prevCommand%>">
            <%
            if (listCurrencyType != null && listCurrencyType.size()>0){
                %><%= drawList(iCommand,frmCurrencyType, currencyType,listCurrencyType,oidCurrencyType, privUpdate)%><%
            } else {
                %>No Data<%
            } %>
            <div>&nbsp;</div>                                    
            <span class="command"> 
            <% ctrLine.setLocationImg(approot+"/images");
                ctrLine.initDefault();
            %>
            <%=ctrLine.drawImageListLimit(iCommand,vectSize,start,recordToGet)%> 
            </span>
            <div>&nbsp;</div>
            <%
           if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmCurrencyType.errorSize()<1)){
                   if(privAdd){%>
                   <a href="javascript:cmdAdd()" style="color:#FFF" class="btn">Add New Currency </a> 
            <%
                   }
            }
            %>
            <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmCurrencyType.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>

            <a href="javascript:cmdSave()" style="color:#FFF" class="btn">Save Currency</a> 
            <a href="javascript:cmdConfirmDelete()" style="color:#FFF" class="btn">Delete Currency</a> 
            <a href="javascript:cmdBack()" style="color:#FFF" class="btn">Back to List Currency</a> 
            <% } %>
        </form>
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
