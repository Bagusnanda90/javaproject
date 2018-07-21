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

<%@ include file = "../../main/javainit.jsp" %>

<%-- YANG INI BELUM DIEDIT --%>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK); %>
<%@ include file = "../../main/checkuser.jsp" %>

<!-- Jsp Block -->
<%!
    public String drawList(Vector objectList, long assFormSectionId) 
    {
	if(objectList!=null && objectList.size()>0) {
            ControlList ctrlist = new ControlList();
            ctrlist.setAreaWidth("100%");
            ctrlist.setListStyle("listgen");
            ctrlist.setTitleStyle("listgentitle");
            ctrlist.setCellStyle("listgensell");
            ctrlist.setHeaderStyle("listgentitle");
  
            ctrlist.addHeader("Section","30%");    
            ctrlist.addHeader("Description","30%");
            ctrlist.addHeader("Main Form","30%");
            
            ctrlist.setLinkRow(0);
            ctrlist.setLinkSufix("");
            Vector lstData = ctrlist.getData();
            Vector lstLinkData = ctrlist.getLinkData();
            ctrlist.setLinkPrefix("javascript:cmdEdit('");
            ctrlist.setLinkSufix("')");
            ctrlist.reset();
            int index = -1;

            for(int i=0; i<objectList.size(); i++) { 
                AssessmentFormSection assFormSection = (AssessmentFormSection)objectList.get(i);
                AssessmentFormMain assFormMain = new AssessmentFormMain();
                try{
                    assFormMain = PstAssessmentFormMain.fetchExc(assFormSection.getAssFormMainId());
                }catch(Exception ex){}
                Vector rowx = new Vector();
                
                rowx.add(assFormSection.getSection()+"<br><font size=1px>"+assFormSection.getSection_L2()+"</font>");
                rowx.add(assFormSection.getDescription()+"<br><font size=1px>"+assFormSection.getDescription_L2()+"</font>");
                rowx.add(assFormMain.getTitle()+"<br><font size=1px>"+assFormMain.getSubtitle()+"</font>");
              
                if(assFormSection.getOID() == assFormSectionId)
				 index = i;
                
                lstData.add(rowx);
                lstLinkData.add(String.valueOf(assFormSection.getOID()));
                
            }		
							
            return ctrlist.draw(index);						  														
	}
	else {				
            return "<div class=\"msginfo\">&nbsp;&nbsp;No Assessment Form Section data found ...</div>";																				
	}
    }
%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidAssessmentFormSection = FRMQueryString.requestLong(request, "hidden_ass_form_section_id");

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_ASS_FORM_MAIN_ID]
        +","+PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_SECTION];

CtrlAssessmentFormSection ctrlAssessmentFormSection = new CtrlAssessmentFormSection(request);
ControlLine ctrLine = new ControlLine();
Vector listAssessmentFormSection = new Vector(1,1);

/*switch statement */
iErrCode = ctrlAssessmentFormSection.action(iCommand , oidAssessmentFormSection);
/* end switch*/
FrmAssessmentFormSection frmAssessmentFormSection = ctrlAssessmentFormSection.getForm();

/*count list All Position*/
int vectSize = PstAssessmentFormSection.getCount(whereClause);

AssessmentFormSection assessmentFormSection = ctrlAssessmentFormSection.getAssessmentFormSection();
msgString =  ctrlAssessmentFormSection.getMessage();
 
/*switch list Division*/
if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)){
	//start = PstDivision.findLimitStart(AssessmentFormSection.getOID(),recordToGet, whereClause);
	oidAssessmentFormSection = assessmentFormSection.getOID();
}

if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
	start = ctrlAssessmentFormSection.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

/* get record to display */
listAssessmentFormSection = PstAssessmentFormSection.list(start,recordToGet, whereClause , orderClause);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listAssessmentFormSection.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
		start = start - recordToGet;   //go to Command.PREV
	 else{
		start = 0 ;
		iCommand = Command.FIRST;
		prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listAssessmentFormSection = PstAssessmentFormSection.list(start,recordToGet, whereClause , orderClause);
}


%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Master Data Division</title>
<script language="JavaScript">


function cmdAdd(){
	document.frmAssessmentFormSection.hidden_ass_form_section_id.value="0";
	document.frmAssessmentFormSection.command.value="<%=String.valueOf(Command.ADD)%>";
	document.frmAssessmentFormSection.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmAssessmentFormSection.action="assessmentFormSection.jsp";
	document.frmAssessmentFormSection.submit();
}

function cmdAsk(oid){
	document.frmAssessmentFormSection.hidden_ass_form_section_id.value=oid;
	document.frmAssessmentFormSection.command.value="<%=String.valueOf(Command.ASK)%>";
	document.frmAssessmentFormSection.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmAssessmentFormSection.action="assessmentFormSection.jsp";
	document.frmAssessmentFormSection.submit();
}

function cmdConfirmDelete(oid){
	document.frmAssessmentFormSection.hidden_ass_form_section_id.value=oid;
	document.frmAssessmentFormSection.command.value="<%=String.valueOf(Command.DELETE)%>";
	document.frmAssessmentFormSection.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmAssessmentFormSection.action="assessmentFormSection.jsp";
	document.frmAssessmentFormSection.submit();
}
function cmdSave(){   
	document.frmAssessmentFormSection.command.value="<%=String.valueOf(Command.SAVE)%>";
	document.frmAssessmentFormSection.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmAssessmentFormSection.action="assessmentFormSection.jsp";
	document.frmAssessmentFormSection.submit();
	}

function cmdEdit(oid){
	document.frmAssessmentFormSection.hidden_ass_form_section_id.value=oid;
	document.frmAssessmentFormSection.command.value="<%=String.valueOf(Command.EDIT)%>";
	document.frmAssessmentFormSection.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmAssessmentFormSection.action="assessmentFormSection.jsp";
	document.frmAssessmentFormSection.submit();
	}

function cmdCancel(oid){
	document.frmAssessmentFormSection.hidden_ass_form_section_id.value=oid;
	document.frmAssessmentFormSection.command.value="<%=String.valueOf(Command.EDIT)%>";
	document.frmAssessmentFormSection.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmAssessmentFormSection.action="assessmentFormSection.jsp";
	document.frmAssessmentFormSection.submit();
}

function cmdBack(){
	document.frmAssessmentFormSection.command.value="<%=String.valueOf(Command.BACK)%>";
	document.frmAssessmentFormSection.action="assessmentFormSection.jsp";
	document.frmAssessmentFormSection.submit();
	}

function cmdListFirst(){
	document.frmAssessmentFormSection.command.value="<%=String.valueOf(Command.FIRST)%>";
	document.frmAssessmentFormSection.prev_command.value="<%=String.valueOf(Command.FIRST)%>";
	document.frmAssessmentFormSection.action="assessmentFormSection.jsp";
	document.frmAssessmentFormSection.submit();
}

function cmdListPrev(){
	document.frmAssessmentFormSection.command.value="<%=String.valueOf(Command.PREV)%>";
	document.frmAssessmentFormSection.prev_command.value="<%=String.valueOf(Command.PREV)%>";
	document.frmAssessmentFormSection.action="assessmentFormSection.jsp";
	document.frmAssessmentFormSection.submit();
	}

function cmdListNext(){
	document.frmAssessmentFormSection.command.value="<%=String.valueOf(Command.NEXT)%>";
	document.frmAssessmentFormSection.prev_command.value="<%=String.valueOf(Command.NEXT)%>";
	document.frmAssessmentFormSection.action="assessmentFormSection.jsp";
	document.frmAssessmentFormSection.submit();
}

function cmdListLast(){
	document.frmAssessmentFormSection.command.value="<%=String.valueOf(Command.LAST)%>";
	document.frmAssessmentFormSection.prev_command.value="<%=String.valueOf(Command.LAST)%>";
	document.frmAssessmentFormSection.action="assessmentFormSection.jsp";
	document.frmAssessmentFormSection.submit();
}

function cmdSearch(){
	document.frmAssessmentFormSection.command.value="<%=String.valueOf(Command.GOTO)%>";
	document.frmAssessmentFormSection.prev_command.value="<%=String.valueOf(Command.GOTO)%>";
	document.frmAssessmentFormSection.action="assessmentFormSection.jsp";
	document.frmAssessmentFormSection.submit();
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
                  Master Data &gt; Assessment &gt; Form Section<!-- #EndEditable -->
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
                                      <input type="hidden" name="vectSize" value="<%=String.valueOf(vectSize)%>">
                                      <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
                                      <input type="hidden" name="prev_command" value="<%=String.valueOf(prevCommand)%>">
                                      <input type="hidden" name="hidden_ass_form_section_id" value="<%=String.valueOf(oidAssessmentFormSection)%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;Assessment Form Section List </td>
                                              </tr>
                                              <%
                                                try{
                                                    if (listAssessmentFormSection.size()>0){
                                                %>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <%=drawList(listAssessmentFormSection,oidAssessmentFormSection)%>
                                                </td>
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
                                                    <%//if((iCommand == Command.NONE || iCommand == Command.DELETE || iCommand == Command.BACK || iCommand ==Command.SAVE)&& (frmAssessmentFormSection.errorSize()<1)){
                                                       if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmAssessmentFormSection.errorSize()<1)){
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
                                                      <td height="22" valign="middle" colspan="3" width="300"> 
                                                        <a href="javascript:cmdAdd()" class="command">Add New Assessment Form Section</a> </td>
                                                        
                                                      <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td width="24"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnSearchOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search data"></a></td>
                                                      <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td height="22" valign="middle" colspan="3" width="951"> 
                                                        <a href="javascript:cmdSearch()" class="command">Search Assessment Form Section</a> </td>
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
                                            <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmAssessmentFormSection.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td class="listtitle"><%=oidAssessmentFormSection == 0?"Add":"Edit"%> Assessment Form Section</td>
                                              </tr>
                                              <tr> 
                                                <td height="100%"> 
                                                  <table border="0" cellspacing="2" cellpadding="2" width="50%">
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%">&nbsp;</td>
                                                      <td width="83%" class="comment">*)entry 
                                                        required </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%"> 
                                                        Title</td>
                                                      <td width="83%"> 
                                                        <%
                                                            Vector divKey = new Vector(1,1);
                                                            Vector divValue = new Vector(1,1);
                                                            Vector listMain = PstAssessmentFormMain.list(0, 0, "", PstAssessmentFormMain.fieldNames[PstAssessmentFormMain.FLD_TITLE]);
                                                            for(int i =0;i < listMain.size();i++){
                                                                  AssessmentFormMain assFormMain = (AssessmentFormMain)listMain.get(i);
                                                                  divKey.add(assFormMain.getTitle());
                                                                  divValue.add(""+assFormMain.getOID());
                                                            }
                                                        %>
                                                        <%=ControlCombo.draw(FrmAssessmentFormSection.fieldNames[FrmAssessmentFormSection.FRM_FIELD_ASS_FORM_MAIN_ID],"formElemen",null,""+assessmentFormSection.getAssFormMainId(),divValue,divKey )%>
                                                        * <%=frmAssessmentFormSection.getErrorMsg(FrmAssessmentFormSection.FRM_FIELD_ASS_FORM_MAIN_ID)%>
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
                                                    ctrLine.setBackCaption("Back to List Form Section");
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
  <tr> 
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
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
