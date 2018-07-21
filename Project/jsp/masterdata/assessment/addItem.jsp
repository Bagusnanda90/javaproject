<%@ page language = "java" %>

<!-- package java -->
<%@ page import = "java.util.*" %>

<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>

<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>

<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.assessment.*" %>
<%@ page import = "com.dimata.harisma.form.employee.assessment.*" %>
<%@ page import = "com.dimata.harisma.session.employee.assessment.*" %>

<%@ include file = "../../main/javainit.jsp" %>

<%-- YANG INI BELUM DIEDIT --%>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK); %>
<%@ include file = "../../main/checkuser.jsp" %>

<!-- Jsp Block -->

<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidAssessmentFormSection = FRMQueryString.requestLong(request, "hidden_ass_form_section_id");
long oidAssessmentFormItem = FRMQueryString.requestLong(request, "hidden_ass_form_item_id");
int currPage = FRMQueryString.requestInt(request, "page");
int currPageSelect = FRMQueryString.requestInt(request, FrmAssessmentFormItem.fieldNames[FrmAssessmentFormItem.FRM_FIELD_PAGE]);

//System.out.println(">>>>>>>>>>>>>>> "+currPageSelect);
int orderNumber = -1;
int currType = -1;
orderNumber = FRMQueryString.requestInt(request, FrmAssessmentFormItem.fieldNames[FrmAssessmentFormItem.FRM_FIELD_ORDER_NUMBER]);
currType = FRMQueryString.requestInt(request, FrmAssessmentFormItem.fieldNames[FrmAssessmentFormItem.FRM_FIELD_TYPE]);

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;


CtrlAssessmentFormItem ctrlAssessmentFormItem = new CtrlAssessmentFormItem(request);
ControlLine ctrLine = new ControlLine();

if(iCommand==Command.REFRESH){
    iCommand = prevCommand;
    prevCommand = Command.REFRESH;
}
/*switch statement */
//Membersihkan order number
if(iCommand == Command.SAVE){
    SessAssessmentFormItem.changeOrderNumber(orderNumber,currPageSelect);
}
iErrCode = ctrlAssessmentFormItem.action(iCommand , oidAssessmentFormItem);
if(oidAssessmentFormItem>0 && !(iCommand == Command.DELETE || iCommand == Command.SAVE)){
    AssessmentFormItem assItem = new AssessmentFormItem();
    try{
        assItem = PstAssessmentFormItem.fetchExc(oidAssessmentFormItem);
    }catch(Exception ex){}
    oidAssessmentFormSection = assItem.getAssFormSection();
    currPage = assItem.getPage();
    if(prevCommand != Command.REFRESH){
        if(orderNumber<=0){
            orderNumber = assItem.getOrderNumber();
        }

        if(currType<=0){
            currType = assItem.getType();
        }
    }
}


/* end switch*/
FrmAssessmentFormItem frmAssessmentFormItem = ctrlAssessmentFormItem.getForm();

AssessmentFormItem assessmentFormItem = ctrlAssessmentFormItem.getAssessmentFormItem();
msgString =  ctrlAssessmentFormItem.getMessage();

if(((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE))||iCommand==Command.DELETE){
    %>
<script language="JavaScript">
    self.opener.document.frmAssessmentFormSection.command.value="<%=String.valueOf(Command.VIEW)%>";
    self.close();
    opener.location.reload();
    
</script>
    <%
}
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" -->
<link rel="icon" href="<%=approot%>/image/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=approot%>/image/favicon.ico" type="image/x-icon" />
<title>HARISMA - Master Item</title>
<script language="JavaScript">


function cmdUpdateType(){
   // alert("BBBBBBBBBBBbb");
   // xselect = document.frmAssessmentFormItem.<%//=PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_TYPE]%>.//value;
   // alert(xselect);
	document.frmAssessmentFormItem.command.value="<%=String.valueOf(Command.REFRESH)%>";
	document.frmAssessmentFormItem.action="addItem.jsp";
	document.frmAssessmentFormItem.submit();
}

function cmdAdd(){
	document.frmAssessmentFormItem.hidden_ass_form_section_id.value="0";
	document.frmAssessmentFormItem.command.value="<%=String.valueOf(Command.ADD)%>";
	document.frmAssessmentFormItem.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmAssessmentFormItem.action="addItem.jsp";
	document.frmAssessmentFormItem.submit();
}

function cmdAsk(oid){
	document.frmAssessmentFormItem.hidden_ass_form_section_id.value=oid;
	document.frmAssessmentFormItem.command.value="<%=String.valueOf(Command.ASK)%>";
	document.frmAssessmentFormItem.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmAssessmentFormItem.action="addItem.jsp";
	document.frmAssessmentFormItem.submit();
}

function cmdConfirmDelete(oid){
	document.frmAssessmentFormItem.hidden_ass_form_section_id.value=oid;
	document.frmAssessmentFormItem.command.value="<%=String.valueOf(Command.DELETE)%>";
	document.frmAssessmentFormItem.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmAssessmentFormItem.action="addItem.jsp";
	document.frmAssessmentFormItem.submit();
}
function cmdSave(){   
	document.frmAssessmentFormItem.command.value="<%=String.valueOf(Command.SAVE)%>";
	document.frmAssessmentFormItem.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmAssessmentFormItem.action="addItem.jsp";
	document.frmAssessmentFormItem.submit();
	}

function cmdEdit(oid){
	document.frmAssessmentFormItem.hidden_ass_form_section_id.value=oid;
	document.frmAssessmentFormItem.command.value="<%=String.valueOf(Command.EDIT)%>";
	document.frmAssessmentFormItem.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmAssessmentFormItem.action="addItem.jsp";
	document.frmAssessmentFormItem.submit();
	}

function cmdCancel(oid){
	document.frmAssessmentFormItem.hidden_ass_form_section_id.value=oid;
	document.frmAssessmentFormItem.command.value="<%=String.valueOf(Command.EDIT)%>";
	document.frmAssessmentFormItem.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmAssessmentFormItem.action="addItem.jsp";
	document.frmAssessmentFormItem.submit();
}

function cmdBack(){
	self.opener.document.frmAssessmentFormSection.command.value="<%=String.valueOf(Command.VIEW)%>";
        self.close();
        opener.location.reload();
}


function fnTrapKD(){
	//alert(event.keyCode);
	switch(event.keyCode) {
		case <%=String.valueOf(LIST_PREV)%>:
			cmdListPrev();
			break;
		case <%=String.valueOf(LIST_NEXT)%>:
			cmdListNext();
			break;
		case <%=String.valueOf(LIST_FIRST)%>:
			cmdListFirst();
			break;
		case <%=String.valueOf(LIST_LAST)%>:
                        cmdListLast();
			break;
		default:
			break;
	}
}

function setChecked(val) {
	dml=document.frmAssessmentFormItem;
	len = dml.elements.length;
	var i=0;
	for( i=0 ; i<len ; i++) {						
		dml.elements[i].checked = val;
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
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "headerscript" --> 
<SCRIPT language=JavaScript>
                            
function openKPIList(){
 var newWindow=window.open("kpi_list.jsp?command=<%=String.valueOf(Command.LIST)%>",  "AssesmentSelectKPI", "height=600,width=700, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
     newWindow.focus();        
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
<!-- #EndEditable -->
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
  
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2"> 
        <tr> 
          <td width="100%">
      <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td height="20">
		    <font color="#FF6600" face="Arial"><strong>
			  <!-- #BeginEditable "contenttitle" --> 
                  Form Item<!-- #EndEditable -->
            </strong></font>
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
                            <td valign="top">
		    				  <!-- #BeginEditable "content" --> 
                                    <form name="frmAssessmentFormItem" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
                                      <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
                                      <input type="hidden" name="prev_command" value="<%=String.valueOf(iCommand)%>">
                                      <input type="hidden" name="hidden_ass_form_section_id" value="<%=String.valueOf(oidAssessmentFormSection)%>">
                                      <input type="hidden" name="hidden_ass_form_item_id" value="<%=String.valueOf(oidAssessmentFormItem)%>">
                                      <input type="hidden" name="page" value="<%=String.valueOf(currPage)%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        
                                        <tr align="left" valign="top"> 
                                          <td height="8" valign="middle" colspan="3"> 
                                            <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmAssessmentFormItem.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td class="listtitle"><%=oidAssessmentFormSection == 0?"Add":"Edit"%> Assessment Form Item</td>
                                              </tr>
                                              <tr> 
                                                <td height="100%"> 
                                                  <table border="0" cellspacing="2" cellpadding="2" width="100%">
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" height="27" valign="top">&nbsp;</td>
                                                      <td width="83%" class="comment">*)entry 
                                                        required </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%"> 
                                                        Section</td>
                                                      <td width="83%">
                                                        <%
                                                        AssessmentFormSection assSection =  new AssessmentFormSection();
                                                            try{
                                                                assSection = PstAssessmentFormSection.fetchExc(oidAssessmentFormSection);
                                                            }catch(Exception ex){}
                                                        out.println("<b>"+assSection.getSection()+"</b><br><i>"+assSection.getSection_L2()+"</i>");
                                                        %>
                                                      <input type="hidden" name="<%=frmAssessmentFormItem.fieldNames[FrmAssessmentFormItem.FRM_FIELD_ASS_FORM_SECTION_ID] %>" value="<%=String.valueOf(oidAssessmentFormSection)%>">
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%"> 
                                                        Page</td>
                                                      <td width="83%"> 
                                                        <% 
                                                            Vector orderPageValue = new Vector(1,1);                                                        
                                                            Vector orderPageKey = new Vector(1,1);
                                                            int maxPage = SessAssessmentFormItem.getMaxPage(assSection.getAssFormMainId());
                                                            
                                                            for (int  i= currPage; i <= maxPage+1; i++) {
                                                                orderPageValue.add(String.valueOf(i));
                                                                orderPageKey.add(String.valueOf(i+1));
                                                            }
                                                            int pageSelected = 0;
                                                            pageSelected = assessmentFormItem.getPage();
                                                            //System.out.println(">>>>>>>>>>>>>>>>>> Curr Page Select "+currPageSelect);
                                                            if(currPageSelect>0){
                                                                pageSelected = currPageSelect;
                                                            }
                                                        %> 
                                                        <%=ControlCombo.draw(frmAssessmentFormItem.fieldNames[frmAssessmentFormItem.FRM_FIELD_PAGE], null,""+pageSelected,orderPageValue,orderPageKey, "onChange=\"javascript:cmdUpdateType()\"")%>
                                                        <%//= ControlCombo.draw(frmAssessmentFormItem.fieldNames[frmAssessmentFormItem.FRM_FIELD_ORDER_NUMBER],"formElemen",null, ""+(assessmentFormSection.getOrderNumber()>0?assessmentFormSection.getOrderNumber():(maxON+1)), orderNumberValue, orderNumberKey) %>
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%"> 
                                                        Order Number</td>
                                                      <td width="83%"> 
                                                        <% 
                                                            Vector orderNumberValue = new Vector(1,1);                                                        
                                                            Vector orderNumberKey = new Vector(1,1);                                                        
                                                            int maxON = SessAssessmentFormItem.getMaxOrderNumber(assSection.getOID(),pageSelected>0?pageSelected:currPage);                                                   
                                                            if(maxON<=0){maxON=1;}
                                                            for (int i = 1; i <= maxON; i++) {
                                                                orderNumberValue.add(String.valueOf(i));
                                                                orderNumberKey.add(String.valueOf(i));
                                                            }
                                                            if(!(assessmentFormItem.getOrderNumber()>0)){
                                                                orderNumberValue.add(String.valueOf((maxON+1)));
                                                                orderNumberKey.add(String.valueOf((maxON+1)));
                                                            }
                                                        %> <%= ControlCombo.draw(frmAssessmentFormItem.fieldNames[frmAssessmentFormItem.FRM_FIELD_ORDER_NUMBER],"formElemen",null, ""+(assessmentFormItem.getOrderNumber()>0?assessmentFormItem.getOrderNumber():(maxON+1)), orderNumberValue, orderNumberKey) %>
                                                      </td>
                                                    </tr>                                        
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%"> 
                                                        Type</td>
                                                      <td width="83%"> 
                                                        <% 
                                                            Vector orderTypeValue = new Vector(1,1);                                                        
                                                            Vector orderTypeKey = new Vector(1,1);                                                        
                                                            int maxType = PstAssessmentFormItem.fieldTypesName.length;
                                                            for (int i = 0; i < maxType; i++) {
                                                                orderTypeValue.add(String.valueOf(i));
                                                                orderTypeKey.add(PstAssessmentFormItem.fieldTypesName[i]);
                                                            }
                                                            //(assessmentFormItem.getType()>=0?assessmentFormItem.getType():currType)
                                                            //System.out.println("=====> "+currType);
                                                            int selectedType = assessmentFormItem.getType();
                                                            if(currType>0){
                                                                selectedType = currType;
                                                            }
                                                        %> 
                                                        <%//= ControlCombo.draw(frmAssessmentFormItem.fieldNames[frmAssessmentFormItem.FRM_FIELD_TYPE],"formElemen",null, ""+(assessmentFormItem.getType()>0?assessmentFormItem.getType():currType), orderTypeValue, orderTypeKey) %>
                                                        <%=ControlCombo.draw(frmAssessmentFormItem.fieldNames[frmAssessmentFormItem.FRM_FIELD_TYPE], null,""+currType,orderTypeValue,orderTypeKey, "onChange=\"javascript:cmdUpdateType()\"")%>
                                                        * <%=frmAssessmentFormItem.getErrorMsg(FrmAssessmentFormItem.FRM_FIELD_TYPE)%>
                                                        </td>
                                                    </tr>
                                                    <%if(
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_COL_1_WITH_TEXT ||
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_COL_2_WITHOUT_TEXT                                                                                                  
                                                      ){
                                                    %>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%"> 
                                                        Number</td>
                                                      <td width="83%"> 
                                                        <% 
                                                            Vector numberValue = new Vector(1,1);                                                        
                                                            Vector numberKey = new Vector(1,1);
                                                            int maxNumber = SessAssessmentFormItem.getMaxNumber(assSection.getOID());
                                                           // System.out.println("XXXXXXXXXXXXXXXXXXXX "+maxNumber);
                                                            for (int  i= 1; i <= maxNumber+1; i++) {
                                                                numberValue.add(String.valueOf(i));
                                                                numberKey.add(String.valueOf(i));
                                                            }
                                                            int numberSelected = maxNumber+1;
                                                            int currNumberSelected = assessmentFormItem.getNumber();
                                                            //System.out.println(">>>>>>>>>>>>>>>>>> Curr Page Select "+currPageSelect);
                                                            if(currNumberSelected>0){
                                                                numberSelected = currNumberSelected;
                                                            }
                                                        %> 
                                                        <%=ControlCombo.draw(frmAssessmentFormItem.fieldNames[frmAssessmentFormItem.FRM_FIELD_NUMBER], null,""+numberSelected,numberValue,numberKey, "")%>
                                                        <%//= ControlCombo.draw(frmAssessmentFormItem.fieldNames[frmAssessmentFormItem.FRM_FIELD_ORDER_NUMBER],"formElemen",null, ""+(assessmentFormSection.getOrderNumber()>0?assessmentFormSection.getOrderNumber():(maxON+1)), orderNumberValue, orderNumberKey) %>
                                                      </td>
                                                    </tr>
                                                    <%}%>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%"> 
                                                        KPI Link</td>
                                                       <td width="83%"> <input type="hidden" name="<%=FrmAssessmentFormItem.fieldNames[FrmAssessmentFormItem.FRM_FIELD_KPI_LIST_ID] %>" id="<%=FrmAssessmentFormItem.fieldNames[FrmAssessmentFormItem.FRM_FIELD_KPI_LIST_ID] %>" value="<%=assessmentFormItem.getKpiListId() %>">
                                                           <%
                                                             KPI_List kpiList =  new KPI_List();
                                                             if(assessmentFormItem.getKpiListId()!=0){
                                                                try{
                                                                    kpiList = PstKPI_List.fetchExc(assessmentFormItem.getKpiListId());
                                                                }catch(Exception excKPIList){
                                                                    System.out.println(excKPIList);
                                                                    kpiList =  new KPI_List();
                                                                }
                                                                 
                                                             }
                                                            %>
                                                            <input readonly onclick="javascript:openKPIList()" type="text" name="<%=FrmAssessmentFormItem.fieldNames[FrmAssessmentFormItem.FRM_FIELD_KPI_LIST_ID] %>text" value="<%=kpiList.getKpi_title() %>">
                                                      </td>
                                                    </tr> 
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%"> 
                                                        KPI Target | Unit | Note </td>
                                                       <td width="83%">
                                                           <input type="text" size="10" maxlength="10" name="<%=FrmAssessmentFormItem.fieldNames[FrmAssessmentFormItem.FRM_FIELD_KPI_TARGET] %>" value="<%=assessmentFormItem.getKpiTarget() %>">
                                                           <input type="text" size="15" maxlength="15" name="<%=FrmAssessmentFormItem.fieldNames[FrmAssessmentFormItem.FRM_FIELD_KPI_UNIT ] %>" value="<%=assessmentFormItem.getKpiUnit() %>">
                                                           <input type="text" size="45" maxlength="128" name="<%=FrmAssessmentFormItem.fieldNames[FrmAssessmentFormItem.FRM_FIELD_KPI_NOTE] %>" value="<%=assessmentFormItem.getKpiNote() %>">
                                                      </td>
                                                    </tr> 
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%"> 
                                                        Weight </td>
                                                       <td width="83%">
                                                           <input type="text" name="<%=FrmAssessmentFormItem.fieldNames[FrmAssessmentFormItem.FRM_FIELD_WEIGHT_POINT] %>" value="<%=assessmentFormItem.getWeightPoint() %>">
                                                      </td>
                                                    </tr> 
                                                    <%if(currType==PstAssessmentFormItem.ITEM_TYPE_COL_2_HEADER ||
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_COL_1_WITH_TEXT ||
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_COL_2_WITHOUT_TEXT ||
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_INPUT_WITH_DOT ||
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_SELECT_1_WITH_RANGE ||
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_SELECT_2_WITHOUT_RANGE ||                                                                                                           
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_SELECT_HEADER ||                                                                                                           
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_TEXT ||                                                                                                          
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_TEXT_BOLD ||                                                                                                           
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_2_COL_ASS_COMM ||                                                                                                          
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_2_COL_OVERALL_COMM ||                                                                                                          
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_INPUT_ASS_COMM||                                                                                                          
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_INPUT_EMP_COMM ||                                                                                                          
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_INPUT_CHECK ||                                                                                                          
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_INPUT_CHECK_HEADER                                                                                                         
                                                      ){
                                                    %>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%"> 
                                                        <% if(currType==PstAssessmentFormItem.ITEM_TYPE_COL_2_HEADER || currType==PstAssessmentFormItem.ITEM_TYPE_SELECT_HEADER){ %>
                                                            Member Title
                                                        <%}else{%>
                                                            Title
                                                        <%}%>
                                                        </td>
                                                      <td width="83%">
                                                        <textarea name="<%= frmAssessmentFormItem.fieldNames[frmAssessmentFormItem.FRM_FIELD_TITLE] %>" cols="60" rows="2" class="formElemen"><%= (assessmentFormItem.getTitle()!=null?assessmentFormItem.getTitle():"") %></textarea>
                                                       * <%=frmAssessmentFormItem.getErrorMsg(FrmAssessmentFormItem.FRM_FIELD_TITLE)%>
                                                      </td>
                                                    </tr>
                                                    <%}%>
                                                    <%if(currType==PstAssessmentFormItem.ITEM_TYPE_COL_2_HEADER ||
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_COL_1_WITH_TEXT ||
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_COL_2_WITHOUT_TEXT ||
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_INPUT_WITH_DOT ||
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_SELECT_1_WITH_RANGE ||
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_SELECT_2_WITHOUT_RANGE ||                                                                                                           
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_SELECT_HEADER ||                                                                                                           
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_TEXT || 
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_TEXT_BOLD ||                                                                                                           
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_2_COL_ASS_COMM ||                                                                                                          
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_2_COL_OVERALL_COMM ||                                                                                                          
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_INPUT_ASS_COMM||                                                                                                          
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_INPUT_EMP_COMM ||                                                                                                          
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_INPUT_CHECK ||                                                                                                          
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_INPUT_CHECK_HEADER                                                                                                         
                                                      ){
                                                    %>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%">
                                                          <% if(currType==PstAssessmentFormItem.ITEM_TYPE_COL_2_HEADER || currType==PstAssessmentFormItem.ITEM_TYPE_SELECT_HEADER){ %>
                                                            Member Title 2nd
                                                        <%}else{%>
                                                            Title 2nd
                                                        <%}%>
                                                        </td>
                                                      <td width="83%"> 
                                                        <textarea name="<%= frmAssessmentFormItem.fieldNames[frmAssessmentFormItem.FRM_FIELD_TITLE_L2] %>" cols="60" rows="2" class="formElemen"><%= (assessmentFormItem.getTitle_L2()!=null?assessmentFormItem.getTitle_L2():"") %></textarea>
                                                       <%=frmAssessmentFormItem.getErrorMsg(FrmAssessmentFormItem.FRM_FIELD_TITLE_L2)%>
                                                      </td>
                                                    </tr>
                                                    <%}%>
                                                    <%if(currType==PstAssessmentFormItem.ITEM_TYPE_COL_2_HEADER ||
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_COL_1_WITH_TEXT ||
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_COL_2_WITHOUT_TEXT ||
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_SELECT_1_WITH_RANGE ||                                                                                                    
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_SELECT_HEADER  ||                                                                                                        
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_2_COL_ASS_COMM ||                                                                                                          
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_2_COL_OVERALL_COMM ||                                                                                                      
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_INPUT_CHECK_HEADER                                                                                                     
                                                      ){
                                                    %>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%">
                                                          <% if(currType==PstAssessmentFormItem.ITEM_TYPE_COL_2_HEADER 
                                                                  || currType==PstAssessmentFormItem.ITEM_TYPE_SELECT_HEADER){ %>
                                                                Assessor Title
                                                          <%}else if(currType==PstAssessmentFormItem.ITEM_TYPE_SELECT_1_WITH_RANGE){ %>
                                                                Range
                                                          <%//}else if(currType==PstAssessmentFormItem.ITEM_TYPE_INPUT_WITH_DOT){ %>
                                                            <!--    Type Data -->
                                                          <%}else{%>
                                                                Poin 1
                                                          <%}%>
                                                      </td>
                                                      <td width="83%">
                                                        <%//if(currType==PstAssessmentFormItem.ITEM_TYPE_INPUT_WITH_DOT){ %>
                                                        <%/* 
                                                            Vector orderTypeDataVal = new Vector(1,1);                                                        
                                                            Vector orderTypeDataKey = new Vector(1,1);                                                        
                                                            int maxTypeData = PstAssessmentFormItem.fieldTypeDataName.length;
                                                            for (int i = 0; i < maxTypeData; i++) {
                                                                orderTypeValue.add(String.valueOf(i));
                                                                orderTypeKey.add(PstAssessmentFormItem.fieldTypeDataName[i]);
                                                            }
                                                            //(assessmentFormItem.getType()>=0?assessmentFormItem.getType():currType)
                                                            //System.out.println("=====> "+currType);
                                                            String selectedTypeData = assessmentFormItem.getItemPoin1();
                                                        */%> 
                                                        <%//= ControlCombo.draw(frmAssessmentFormItem.fieldNames[frmAssessmentFormItem.FRM_FIELD_TYPE],"formElemen",null, ""+(assessmentFormItem.getType()>0?assessmentFormItem.getType():currType), orderTypeValue, orderTypeKey) %>
                                                        
                                                        <%//=ControlCombo.draw(frmAssessmentFormItem.fieldNames[frmAssessmentFormItem.FRM_FIELD_ITEM_POIN_1], null,selectedTypeData,orderTypeDataVal,orderTypeDataKey, "")%>
                                                        
                                                          <%//}else{%>
                                                        <textarea name="<%= frmAssessmentFormItem.fieldNames[frmAssessmentFormItem.FRM_FIELD_ITEM_POIN_1] %>" cols="60" rows="2" class="formElemen"><%= (assessmentFormItem.getItemPoin1()!=null?assessmentFormItem.getItemPoin1():"") %></textarea>
                                                          <%//}%>
                                                      </td>
                                                    </tr>
                                                    <%}%>
                                                    <%if(currType==PstAssessmentFormItem.ITEM_TYPE_COL_2_HEADER ||
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_COL_1_WITH_TEXT ||
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_COL_2_WITHOUT_TEXT ||
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_SELECT_HEADER  ||                                                                                                   
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_2_COL_ASS_COMM ||                                                                                                          
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_2_COL_OVERALL_COMM ||                                                                                                        
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_INPUT_CHECK_HEADER  
                                                      ){
                                                    %>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%">
                                                      <% if(currType==PstAssessmentFormItem.ITEM_TYPE_COL_2_HEADER 
                                                              || currType==PstAssessmentFormItem.ITEM_TYPE_SELECT_HEADER){ %>
                                                            Assessor Title 2nd
                                                      <%}else{%>
                                                            Poin 2
                                                      <%}%>
                                                      </td>
                                                      <td width="83%"> 
                                                     <!--   <input type="text" name="<%//=frmAssessmentFormItem.fieldNames[FrmAssessmentFormItem.FRM_FIELD_ITEM_POIN_2] %>"  value="<%//= (assessmentFormItem.getItemPoin2()!=null?assessmentFormItem.getItemPoin2():"") %>" class="elemenForm" size="70">-->
                                                        <textarea name="<%= frmAssessmentFormItem.fieldNames[frmAssessmentFormItem.FRM_FIELD_ITEM_POIN_2] %>" cols="60" rows="2" class="formElemen"><%= (assessmentFormItem.getItemPoin2()!=null?assessmentFormItem.getItemPoin2():"") %></textarea>
                                                      </td>
                                                    </tr>
                                                    <%}%>
                                                    <%if(currType==PstAssessmentFormItem.ITEM_TYPE_COL_1_WITH_TEXT ||
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_COL_2_WITHOUT_TEXT                                                                                          
                                                      ){
                                                    %>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%">Poin 3</td>
                                                      <td width="83%"> 
                                                        <textarea name="<%= frmAssessmentFormItem.fieldNames[frmAssessmentFormItem.FRM_FIELD_ITEM_POIN_3] %>" cols="60" rows="2" class="formElemen"><%= (assessmentFormItem.getItemPoin3()!=null?assessmentFormItem.getItemPoin3():"") %></textarea></td>
                                                    </tr>
                                                    <%}%>
                                                    <%if(currType==PstAssessmentFormItem.ITEM_TYPE_COL_1_WITH_TEXT ||
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_COL_2_WITHOUT_TEXT                                                                                         
                                                      ){
                                                    %>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%">Poin 4</td>
                                                      <td width="83%"> 
                                                        <textarea name="<%= frmAssessmentFormItem.fieldNames[frmAssessmentFormItem.FRM_FIELD_ITEM_POIN_4] %>" cols="60" rows="2" class="formElemen"><%= (assessmentFormItem.getItemPoin4()!=null?assessmentFormItem.getItemPoin4():"") %></textarea></td>
                                                    </tr>
                                                    <%}%>
                                                    <%if(currType==PstAssessmentFormItem.ITEM_TYPE_COL_1_WITH_TEXT ||
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_COL_2_WITHOUT_TEXT                                                                                               
                                                      ){
                                                    %>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%">Poin 5</td>
                                                      <td width="83%"> 
                                                        <textarea name="<%= frmAssessmentFormItem.fieldNames[frmAssessmentFormItem.FRM_FIELD_ITEM_POIN_5] %>" cols="60" rows="2" class="formElemen"><%= (assessmentFormItem.getItemPoin5()!=null?assessmentFormItem.getItemPoin5():"") %></textarea></td>
                                                    </tr>
                                                    <%}%>
                                                    <%if(currType==PstAssessmentFormItem.ITEM_TYPE_COL_1_WITH_TEXT ||
                                                      currType==PstAssessmentFormItem.ITEM_TYPE_COL_2_WITHOUT_TEXT                                                                                                     
                                                      ){
                                                    %>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%">Poin 6</td>
                                                      <td width="83%"> 
                                                        <textarea name="<%= frmAssessmentFormItem.fieldNames[frmAssessmentFormItem.FRM_FIELD_ITEM_POIN_6] %>" cols="60" rows="2" class="formElemen"><%= (assessmentFormItem.getItemPoin6()!=null?assessmentFormItem.getItemPoin6():"") %></textarea></td>
                                                    </tr>
                                                    <%}%>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%"> 
                                                        Height</td>
                                                      <td width="83%"> 
                                                        <% 
                                                            Vector orderHeightValue = new Vector(1,1);                                                        
                                                            Vector orderHeightKey = new Vector(1,1);                                                        
                                                            int maxHeight = 20;
                                                        /*    if(iCommand == Command.ADD && prevCommand==0){
                                                                orderTypeValue.add(String.valueOf(-1));
                                                                orderTypeKey.add("Select...");
                                                                currType = -1;
                                                            } */
                                                            for (int i = 1; i <= maxHeight; i++) {
                                                                orderHeightValue.add(String.valueOf(i));
                                                                //System.out.println(">>> "+i);
                                                                orderHeightKey.add(String.valueOf(i));
                                                            }
                                                            int selectedHeight = assessmentFormItem.getHeight();
                                                            if(selectedHeight==0){
                                                                selectedHeight = 1;
                                                            }
                                                        %> 
                                                        <%= ControlCombo.draw(frmAssessmentFormItem.fieldNames[frmAssessmentFormItem.FRM_FIELD_HEIGHT],"formElemen",null, ""+(selectedHeight), orderHeightValue, orderHeightKey) %>
                                                        <%//=ControlCombo.draw(frmAssessmentFormItem.fieldNames[frmAssessmentFormItem.FRM_FIELD_HEIGHT], null,""+selectedHeight,orderHeightValue,orderHeightKey, "onChange=\"javascript:cmdUpdateType()\"")%>
                                                         <%//=frmAssessmentFormItem.getErrorMsg(FrmAssessmentFormItem.FRM_FIELD_TYPE)%>
                                                      </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td colspan="2"> 
                                                  <%
                                                    ctrLine.setLocationImg(approot+"/images");
                                                    ctrLine.initDefault();
                                                    ctrLine.setTableWidth("80%");
                                                    String scomDel = "javascript:cmdAsk('"+oidAssessmentFormSection+"')";
                                                    String sconDelCom = "javascript:cmdConfirmDelete('"+oidAssessmentFormSection+"')";
                                                    String scancel = "javascript:cmdEdit('"+oidAssessmentFormSection+"')";
                                                    ctrLine.setBackCaption("Back to List");
                                                    ctrLine.setCommandStyle("buttonlink");
                                                    ctrLine.setBackCaption("Back to Form Creator");
                                                    ctrLine.setSaveCaption("Save Form Item");
                                                    ctrLine.setConfirmDelCaption("Yes Delete Form Item");
                                                    ctrLine.setDeleteCaption("Delete Form Item");

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
  
</table>
</body>
<!-- #BeginEditable "script" -->
<script language="JavaScript">
	//var oBody = document.body;
	//var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
</script>
<!-- #EndEditable -->
<!-- #EndTemplate -->
</html>
