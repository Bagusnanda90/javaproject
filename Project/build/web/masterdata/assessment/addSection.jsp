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
long oidAssessmentFormMain = FRMQueryString.requestLong(request, "hidden_ass_form_main_id");
int orderNumber = FRMQueryString.requestInt(request, FrmAssessmentFormSection.fieldNames[FrmAssessmentFormSection.FRM_FIELD_ORDER_NUMBER]);
int cuurPage = FRMQueryString.requestInt(request, "page");

//System.out.println(">>>>>>>>>>>>>>> "+oidAssessmentFormMain);


/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;

CtrlAssessmentFormSection ctrlAssessmentFormSection = new CtrlAssessmentFormSection(request);
ControlLine ctrLine = new ControlLine();

/*switch statement */
//Membersihkan order number
//System.out.println("::::::::::::::1 "+orderNumber);
if(iCommand == Command.SAVE){
   // System.out.println("::::::::::::::2 "+orderNumber);
    SessAssessmentFormSection.changeOrderNumber(orderNumber,cuurPage);
}
iErrCode = ctrlAssessmentFormSection.action(iCommand , oidAssessmentFormSection);
if(oidAssessmentFormSection>0 && !(iCommand == Command.DELETE || iCommand == Command.SAVE)){
    AssessmentFormSection assSection = new AssessmentFormSection();
    try{
        assSection = PstAssessmentFormSection.fetchExc(oidAssessmentFormSection);
    }catch(Exception ex){}
    oidAssessmentFormMain = assSection.getAssFormMainId();
    cuurPage = assSection.getPage();
}
//System.out.println("::::::::::::::PAGE "+cuurPage);
/* end switch*/
FrmAssessmentFormSection frmAssessmentFormSection = ctrlAssessmentFormSection.getForm();

AssessmentFormSection assessmentFormSection = ctrlAssessmentFormSection.getAssessmentFormSection();
msgString =  ctrlAssessmentFormSection.getMessage();


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
<title>HARISMA - Master Section</title>
<script language="JavaScript">


function cmdAdd(){
	document.frmAssessmentFormSection.hidden_ass_form_section_id.value="0";
	document.frmAssessmentFormSection.command.value="<%=String.valueOf(Command.ADD)%>";
	document.frmAssessmentFormSection.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmAssessmentFormSection.action="addSection.jsp";
	document.frmAssessmentFormSection.submit();
}

function cmdAsk(oid){
	document.frmAssessmentFormSection.hidden_ass_form_section_id.value=oid;
	document.frmAssessmentFormSection.command.value="<%=String.valueOf(Command.ASK)%>";
	document.frmAssessmentFormSection.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmAssessmentFormSection.action="addSection.jsp";
	document.frmAssessmentFormSection.submit();
}

function cmdConfirmDelete(oid){
	document.frmAssessmentFormSection.hidden_ass_form_section_id.value=oid;
	document.frmAssessmentFormSection.command.value="<%=String.valueOf(Command.DELETE)%>";
	document.frmAssessmentFormSection.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmAssessmentFormSection.action="addSection.jsp";
	document.frmAssessmentFormSection.submit();
}
function cmdSave(){   
	document.frmAssessmentFormSection.command.value="<%=String.valueOf(Command.SAVE)%>";
	document.frmAssessmentFormSection.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmAssessmentFormSection.action="addSection.jsp";
	document.frmAssessmentFormSection.submit();
	}

function cmdEdit(oid){
	document.frmAssessmentFormSection.hidden_ass_form_section_id.value=oid;
	document.frmAssessmentFormSection.command.value="<%=String.valueOf(Command.EDIT)%>";
	document.frmAssessmentFormSection.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmAssessmentFormSection.action="addSection.jsp";
	document.frmAssessmentFormSection.submit();
	}

function cmdCancel(oid){
	document.frmAssessmentFormSection.hidden_ass_form_section_id.value=oid;
	document.frmAssessmentFormSection.command.value="<%=String.valueOf(Command.EDIT)%>";
	document.frmAssessmentFormSection.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmAssessmentFormSection.action="addSection.jsp";
	document.frmAssessmentFormSection.submit();
}

function cmdBack(){
	self.opener.document.frmAssessmentFormSection.page.value = document.frmAssessmentFormSection.<%=frmAssessmentFormSection.fieldNames[FrmAssessmentFormSection.FRM_FIELD_PAGE] %>.value;
        self.opener.document.frmAssessmentFormSection.hidden_ass_form_main_id.value = document.frmAssessmentFormSection.<%=frmAssessmentFormSection.fieldNames[FrmAssessmentFormSection.FRM_FIELD_ASS_FORM_MAIN_ID] %>.value;
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
	dml=document.frmAssessmentFormSection;
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
                  Form Section<!-- #EndEditable -->
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
                                    <form name="frmAssessmentFormSection" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
                                      <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
                                      <input type="hidden" name="prev_command" value="<%=String.valueOf(prevCommand)%>">
                                      <input type="hidden" name="page" value="<%=String.valueOf(cuurPage)%>">
                                      <input type="hidden" name="hidden_ass_form_section_id" value="<%=String.valueOf(oidAssessmentFormSection)%>">
                                      <input type="hidden" name="hidden_ass_form_main_id" value="<%=String.valueOf(oidAssessmentFormMain)%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        
                                        <tr align="left" valign="top"> 
                                          <td height="8" valign="middle" colspan="3"> 
                                            <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmAssessmentFormSection.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td class="listtitle"><%=oidAssessmentFormSection == 0?"Add":"Edit"%> Assessment Form Section</td>
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
                                                        Type</td>
                                                      <td width="83%"> 
                                                        <%
                                                        AssessmentFormMain assMain =  new AssessmentFormMain();
                                                            try{
                                                                assMain = PstAssessmentFormMain.fetchExc(oidAssessmentFormMain);
                                                            }catch(Exception ex){}
                                                        out.println("<b>"+assMain.getTitle()+"</b><br><i>"+assMain.getTitle_L2()+"</i>");
                                                        %>
                                                      <input type="hidden" name="<%=frmAssessmentFormSection.fieldNames[FrmAssessmentFormSection.FRM_FIELD_ASS_FORM_MAIN_ID] %>" value="<%=String.valueOf(oidAssessmentFormMain)%>">
                                                      <input type="hidden" name="<%=frmAssessmentFormSection.fieldNames[FrmAssessmentFormSection.FRM_FIELD_PAGE] %>" value="<%=String.valueOf(cuurPage)%>">
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%"> 
                                                        Title</td>
                                                      <td width="83%"> 
                                                       <% 
                                                            Vector orderTypeValue = new Vector(1,1);                                                        
                                                            Vector orderTypeKey = new Vector(1,1);                                                        
                                                            int maxType = PstAssessmentFormSection.fieldTypeNames.length;                                               
                                                            for (int i = 0; i < maxType; i++) {
                                                                orderTypeValue.add(String.valueOf(i));
                                                                orderTypeKey.add(String.valueOf(PstAssessmentFormSection.fieldTypeNames[i]));
                                                            }
                                                        %> <%= ControlCombo.draw(frmAssessmentFormSection.fieldNames[frmAssessmentFormSection.FRM_FIELD_TYPE],"formElemen",null, ""+(assessmentFormSection.getType()>0?assessmentFormSection.getType():(0)), orderTypeValue, orderTypeKey) %>
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%"> 
                                                        Point Weight </td>
                                                       <td width="83%">
                                                           <input type="text" name="<%=frmAssessmentFormSection.fieldNames[frmAssessmentFormSection.FRM_FIELD_WEIGHT_POINT] %>" value="<%=assessmentFormSection.getWeightPoint() %>">
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%"> 
                                                        Section</td>
                                                      <td width="83%"> 
                                                        <input type="text" name="<%=frmAssessmentFormSection.fieldNames[FrmAssessmentFormSection.FRM_FIELD_SECTION] %>"  value="<%= (assessmentFormSection.getSection()!=null?assessmentFormSection.getSection():"") %>" class="elemenForm" size="70">
                                                       * <%=frmAssessmentFormSection.getErrorMsg(FrmAssessmentFormSection.FRM_FIELD_SECTION)%>
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%"> 
                                                        Section 2nd</td>
                                                      <td width="83%"> 
                                                        <input type="text" name="<%=frmAssessmentFormSection.fieldNames[FrmAssessmentFormSection.FRM_FIELD_SECTION_L2] %>"  value="<%= (assessmentFormSection.getSection_L2()!=null?assessmentFormSection.getSection_L2():"") %>" class="elemenForm" size="70">
                                                        <%=frmAssessmentFormSection.getErrorMsg(FrmAssessmentFormSection.FRM_FIELD_SECTION_L2)%>
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%"> 
                                                        Description</td>
                                                      <td width="83%"> 
                                                        <input type="text" name="<%=frmAssessmentFormSection.fieldNames[FrmAssessmentFormSection.FRM_FIELD_DESCRIPTION] %>"  value="<%= (assessmentFormSection.getDescription()!=null?assessmentFormSection.getDescription():"") %>" class="elemenForm" size="50">
                                                        <%=frmAssessmentFormSection.getErrorMsg(FrmAssessmentFormSection.FRM_FIELD_DESCRIPTION)%>
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%"> 
                                                        Description 2nd</td>
                                                      <td width="83%"> 
                                                        <input type="text" name="<%=frmAssessmentFormSection.fieldNames[FrmAssessmentFormSection.FRM_FIELD_DESCRIPTION_L2] %>"  value="<%= (assessmentFormSection.getDescription_L2()!=null?assessmentFormSection.getDescription_L2():"") %>" class="elemenForm" size="50">
                                                        <%=frmAssessmentFormSection.getErrorMsg(FrmAssessmentFormSection.FRM_FIELD_DESCRIPTION_L2)%>
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%"> 
                                                        Order Number</td>
                                                      <td width="83%"> 
                                                        <% 
                                                            Vector orderNumberValue = new Vector(1,1);                                                        
                                                            Vector orderNumberKey = new Vector(1,1);                                                        
                                                            int maxON = SessAssessmentFormSection.getMaxOrderNumber(oidAssessmentFormMain,cuurPage);                                                   
                                                            for (int i = 1; i <= maxON; i++) {
                                                                orderNumberValue.add(String.valueOf(i));
                                                                orderNumberKey.add(String.valueOf(i));
                                                            }
                                                            if(!(assessmentFormSection.getOrderNumber()>0)){
                                                                orderNumberValue.add(String.valueOf((maxON+1)));
                                                                orderNumberKey.add(String.valueOf((maxON+1)));
                                                            }
                                                        %> <%= ControlCombo.draw(frmAssessmentFormSection.fieldNames[frmAssessmentFormSection.FRM_FIELD_ORDER_NUMBER],"formElemen",null, ""+(assessmentFormSection.getOrderNumber()>0?assessmentFormSection.getOrderNumber():(maxON+1)), orderNumberValue, orderNumberKey) %>
                                                      </td>
                                                    </tr>
    <tr align="left" valign="top"> 
      <td valign="top" width="17%"> 
        Point Evaluation Type</td>
      <td width="83%"> 
        <% 
                Vector typeKey = new Vector(1, 1);
                Vector typeValue = new Vector(1, 1);              
                
                for (int i = 0; i < Evaluation.EVAL_TYPE[0].length ; i++) {
                    typeKey.add( Evaluation.EVAL_TYPE[SESS_LANGUAGE][i]);
                    typeValue.add("" + i);
                }
        %> <%= ControlCombo.draw(FrmAssessmentFormSection.fieldNames[FrmAssessmentFormSection.FRM_FIELD_POINT_EVAL_ID], "formElemen", null, "" + assessmentFormSection.getPointEvaluationId() , typeValue, typeKey) %>
      </td>
    </tr>

    <tr align="left" valign="top"> 
      <td valign="top" width="17%"> 
        Predicate Evaluation Type</td>
      <td width="83%"> 
         <%= ControlCombo.draw(FrmAssessmentFormSection.fieldNames[frmAssessmentFormSection.FRM_FIELD_PREDICATE_EVAL_ID], "formElemen", null, "" + assessmentFormSection.getPredicateEvaluationId() , typeValue, typeKey) %>
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
                                                    ctrLine.setSaveCaption("Save Form Section");
                                                    ctrLine.setConfirmDelCaption("Yes Delete Form Section");
                                                    ctrLine.setDeleteCaption("Delete Form Section");

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
