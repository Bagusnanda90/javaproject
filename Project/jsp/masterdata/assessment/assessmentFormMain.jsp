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
<%// int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK); %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_PERFORMANCE_APPRAISAL, AppObjInfo.OBJ_FORM_CREATOR); %>

<%@ include file = "../../main/checkuser.jsp" %>

<!-- Jsp Block -->
<%!
    public String drawList(Vector objectList, long assFormMainId) 
    {
	if(objectList!=null && objectList.size()>0) {
            
            ControlList ctrlist = new ControlList();
            ctrlist.setAreaWidth("100%");
            ctrlist.setListStyle("listgen");
            ctrlist.setTitleStyle("listgentitle");
            ctrlist.setCellStyle("listgensell");
            ctrlist.setHeaderStyle("listgentitle");
  
            ctrlist.addHeader("Title","20%");
            ctrlist.addHeader("Description","15%");    
            ctrlist.addHeader("Main Data","25%");
            ctrlist.addHeader("Note","20%");
            ctrlist.addHeader("Group Rank","10%");
            ctrlist.addHeader("Create","10%");
            
            ctrlist.setLinkRow(0);
            ctrlist.setLinkSufix("");
            Vector lstData = ctrlist.getData();
            Vector lstLinkData = ctrlist.getLinkData();
            ctrlist.setLinkPrefix("javascript:cmdEdit('");
            ctrlist.setLinkSufix("')");
            ctrlist.reset();
            int index = -1;

            Hashtable hGrouprank = new Hashtable();
            Vector vGroupRank = PstGroupRank.list(0, 0, "", "");
            for(int j=0;j<vGroupRank.size();j++){
                GroupRank gr = (GroupRank)vGroupRank.get(j);
                hGrouprank.put(String.valueOf(gr.getOID()), String.valueOf(gr.getGroupName()));
            }
            //long oidAssMainFormId=0;
            for(int i=0; i<objectList.size(); i++) { 
               AssessmentFormMain assFormMain = (AssessmentFormMain)objectList.get(i);
               // SessAssessmentFormMain sessAssessmentFormMain  = (SessAssessmentFormMain)objectList.get(i);
                String strFormUsed = "";
                if(PstAssessmentFormMain.fieldFormTypes!=null && PstAssessmentFormMain.fieldFormTypes.length>0){
                    for(int ij=0; ij<PstAssessmentFormMain.fieldFormTypes.length; ij++){
                           boolean isUsed = PstAssessmentFormMain.cekFormUsed(assFormMain.getMainData(), ij);
                           String strVal = PstAssessmentFormMain.fieldFormValue[ij][PstAssessmentFormMain.LANGUAGE_FIRST];
                           if(isUsed){
                               if(strFormUsed.length()>0){
                                   strFormUsed += ", "+strVal;
                               }else{
                                   strFormUsed += strVal;
                               }
                           }
                    }			
                }
                
                
                Vector rowx = new Vector();
              String strGroupRank = PstAssessmentFormMainDetail.sGroupNameRank(PstAssessmentFormMain.fieldNames[PstAssessmentFormMain.FLD_ASS_FORM_MAIN_ID]+"="+assFormMain.getOID(), ""); 
               rowx.add(assFormMain.getTitle()+"<br><font size=1px>"+assFormMain.getTitle_L2()+"</font>");
               rowx.add(assFormMain.getSubtitle()+"<br><font size=1px>"+assFormMain.getSubtitle_L2()+"</font>");
               rowx.add("<font size=1px>"+strFormUsed+"</font>");
               rowx.add(assFormMain.getNote().length()>100?assFormMain.getNote().substring(0, 100) + " ..." : assFormMain.getNote()); 
               rowx.add(strGroupRank); 
                rowx.add("<a href=javascript:cmdCreate('"+assFormMain.getOID()+"')>Create</a>");
               
              
                /*rowx.add(assFormMain.getTitle()+"<br><font size=1px>"+assFormMain.getTitle_L2()+"</font>");
                rowx.add(assFormMain.getSubtitle()+"<br><font size=1px>"+assFormMain.getSubtitle_L2()+"</font>");
                rowx.add("<font size=1px>"+strFormUsed+"</font>");
                rowx.add(assFormMain.getNote().length()>100?assFormMain.getNote().substring(0, 100) + " ..." : assFormMain.getNote());
                String strGroupRank = (String)hGrouprank.get(String.valueOf(assFormMain.getGroupRankId()));
                rowx.add(strGroupRank);
                
                rowx.add("<a href=javascript:cmdCreate('"+assFormMain.getOID()+"')>Create</a>");*/
              
                if(assFormMain.getOID() == assFormMainId)
				 index = i;
                
                lstData.add(rowx);
                lstLinkData.add(String.valueOf(assFormMain.getOID()));
                
            }		
							
            return ctrlist.draw(index);						  														
	}
	else {				
            return "<div class=\"msginfo\">&nbsp;&nbsp;No Assessment Form Main data found ...</div>";																				
	}
    }
%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidAssessmentFormMain = FRMQueryString.requestLong(request, "hidden_ass_form_main_id");

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = PstAssessmentFormMain.fieldNames[PstAssessmentFormMain.FLD_ASS_FORM_MAIN_ID];

CtrlAssessmentFormMain ctrlAssessmentFormMain = new CtrlAssessmentFormMain(request);
ControlLine ctrLine = new ControlLine();
Vector listAssessmentFormMain = new Vector(1,1);

/*switch statement */
iErrCode = ctrlAssessmentFormMain.action (iCommand , oidAssessmentFormMain); 
/* end switch*/
FrmAssessmentFormMain frmAssessmentFormMain = ctrlAssessmentFormMain.getForm();

/*count list All Position*/
int vectSize = PstAssessmentFormMain.getCount(whereClause);
//int vectSize = PstAssessmentFormMain.getCountDetail(start,recordToGet, whereClause , orderClause);
AssessmentFormMain assessmentFormMain = ctrlAssessmentFormMain.getAssessmentFormMain();
msgString =  ctrlAssessmentFormMain.getMessage();
 
/*switch list Division*/
if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)){
	//start = PstDivision.findLimitStart(AssessmentFormMain.getOID(),recordToGet, whereClause);
	oidAssessmentFormMain = assessmentFormMain.getOID();
}

if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlAssessmentFormMain.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

/* get record to display */
listAssessmentFormMain = PstAssessmentFormMain.list(start,recordToGet, whereClause , orderClause);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listAssessmentFormMain.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
        // vectSize = PstAssessmentFormMain.getCountDetail(start,recordToGet, whereClause , orderClause);
	 listAssessmentFormMain = PstAssessmentFormMain.list(start,recordToGet, whereClause , orderClause);
}


%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Master Data Division</title>
<script language="JavaScript">

function cmdCreate(oidAssessmentFormMain){
	document.frmAssessmentFormMain.hidden_ass_form_main_id.value=oidAssessmentFormMain;
	document.frmAssessmentFormMain.command.value="<%=String.valueOf(Command.VIEW)%>";
	document.frmAssessmentFormMain.action="assessmentFormCreator.jsp";
	document.frmAssessmentFormMain.submit();
}

function cmdAdd(){
	document.frmAssessmentFormMain.hidden_ass_form_main_id.value="";
	document.frmAssessmentFormMain.command.value="<%=String.valueOf(Command.ADD)%>";
	document.frmAssessmentFormMain.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmAssessmentFormMain.action="assessmentFormMain.jsp";
	document.frmAssessmentFormMain.submit();
}

function cmdAsk(oidAssessmentFormMain){
	document.frmAssessmentFormMain.hidden_ass_form_main_id.value=oidAssessmentFormMain;
	document.frmAssessmentFormMain.command.value="<%=String.valueOf(Command.ASK)%>";
	document.frmAssessmentFormMain.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmAssessmentFormMain.action="assessmentFormMain.jsp";
	document.frmAssessmentFormMain.submit();
}

function cmdConfirmDelete(oidAssessmentFormMain){
	document.frmAssessmentFormMain.hidden_ass_form_main_id.value=oidAssessmentFormMain;
	document.frmAssessmentFormMain.command.value="<%=String.valueOf(Command.DELETE)%>";
	document.frmAssessmentFormMain.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmAssessmentFormMain.action="assessmentFormMain.jsp";
	document.frmAssessmentFormMain.submit();
}
function cmdSave(){
        <%
        if(PstAssessmentFormMain.fieldFormTypes!=null && PstAssessmentFormMain.fieldFormTypes.length>0){
                for(int i=0; i<PstAssessmentFormMain.fieldFormTypes.length; i++){
                    %>
                    if (document.frmAssessmentFormMain.<%="chx_"+i%>.checked){
                        document.frmAssessmentFormMain.<%=frmAssessmentFormMain.fieldNames[FrmAssessmentFormMain.FRM_FIELD_MAIN_DATA]%>.value+="1";
                    }else{
                        document.frmAssessmentFormMain.<%=frmAssessmentFormMain.fieldNames[FrmAssessmentFormMain.FRM_FIELD_MAIN_DATA]%>.value+="0";
                    }
                    <%
                }			
          }
        %>    
	document.frmAssessmentFormMain.command.value="<%=String.valueOf(Command.SAVE)%>";
	document.frmAssessmentFormMain.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmAssessmentFormMain.action="assessmentFormMain.jsp";
	document.frmAssessmentFormMain.submit();
	}

function cmdEdit(oidAssessmentFormMain){
	document.frmAssessmentFormMain.hidden_ass_form_main_id.value=oidAssessmentFormMain;
	document.frmAssessmentFormMain.command.value="<%=String.valueOf(Command.EDIT)%>";
	document.frmAssessmentFormMain.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmAssessmentFormMain.action="assessmentFormMain.jsp";
	document.frmAssessmentFormMain.submit();
	}

function cmdCancel(oidAssessmentFormMain){
	document.frmAssessmentFormMain.hidden_ass_form_main_id.value=oidAssessmentFormMain;
	document.frmAssessmentFormMain.command.value="<%=String.valueOf(Command.EDIT)%>";
	document.frmAssessmentFormMain.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmAssessmentFormMain.action="assessmentFormMain.jsp";
	document.frmAssessmentFormMain.submit();
}

function cmdBack(){
	document.frmAssessmentFormMain.command.value="<%=String.valueOf(Command.BACK)%>";
	document.frmAssessmentFormMain.action="assessmentFormMain.jsp";
	document.frmAssessmentFormMain.submit();
	}

function cmdListFirst(){
	document.frmAssessmentFormMain.command.value="<%=String.valueOf(Command.FIRST)%>";
	document.frmAssessmentFormMain.prev_command.value="<%=String.valueOf(Command.FIRST)%>";
	document.frmAssessmentFormMain.action="assessmentFormMain.jsp";
	document.frmAssessmentFormMain.submit();
}

function cmdListPrev(){
	document.frmAssessmentFormMain.command.value="<%=String.valueOf(Command.PREV)%>";
	document.frmAssessmentFormMain.prev_command.value="<%=String.valueOf(Command.PREV)%>";
	document.frmAssessmentFormMain.action="assessmentFormMain.jsp";
	document.frmAssessmentFormMain.submit();
	}

function cmdListNext(){
	document.frmAssessmentFormMain.command.value="<%=String.valueOf(Command.NEXT)%>";
	document.frmAssessmentFormMain.prev_command.value="<%=String.valueOf(Command.NEXT)%>";
	document.frmAssessmentFormMain.action="assessmentFormMain.jsp";
	document.frmAssessmentFormMain.submit();
}

function cmdListLast(){
	document.frmAssessmentFormMain.command.value="<%=String.valueOf(Command.LAST)%>";
	document.frmAssessmentFormMain.prev_command.value="<%=String.valueOf(Command.LAST)%>";
	document.frmAssessmentFormMain.action="assessmentFormMain.jsp";
	document.frmAssessmentFormMain.submit();
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
	dml=document.frmAssessmentFormMain;
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
 <link rel="stylesheet" href="../../stylesheets/chosen.css" >
 
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
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
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
                  Master Data &gt; Assessment &gt; Form Main<!-- #EndEditable -->
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
                                                  
                                    <form name="frmAssessmentFormMain" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
                                      <input type="hidden" name="vectSize" value="<%=String.valueOf(vectSize)%>">
                                      <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
                                      <input type="hidden" name="prev_command" value="<%=String.valueOf(prevCommand)%>">
                                      <input type="hidden" name="hidden_ass_form_main_id" value="<%=String.valueOf(oidAssessmentFormMain)%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;Assessment Form Main List </td>
                                              </tr>
                                              <%
                                                try{
                                                    if (listAssessmentFormMain.size()>0){
                                                %>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <%= drawList(listAssessmentFormMain,oidAssessmentFormMain)%>
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
                                                    <%//if((iCommand == Command.NONE || iCommand == Command.DELETE || iCommand == Command.BACK || iCommand ==Command.SAVE)&& (frmAssessmentFormMain.errorSize()<1)){
                                                       if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmAssessmentFormMain.errorSize()<1)){
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
                                                        <a href="javascript:cmdAdd()" class="command">Add New Assessment Form Main</a> </td>
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
                                            <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmAssessmentFormMain.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td class="listtitle"><%=oidAssessmentFormMain == 0?"Add":"Edit"%> Assessment Form Main</td>
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
                                                      <td valign="top" width="17%" nowrap> 
                                                        Group Rank</td>
                                                      <td width="83%" nowrap> 
                                                      <% 
							Vector gr_value = new Vector(1,1);
							Vector gr_key = new Vector(1,1);                                              
							Vector vGroupRank = PstGroupRank.list(0, 0, "", "");
                                                        for (int i = 0; i < vGroupRank.size(); i++) {
								GroupRank gr = (GroupRank) vGroupRank.get(i);
								gr_value.add(String.valueOf(gr.getOID()));
								gr_key.add(gr.getGroupName());
							}
                                                        
                                                         String attTag =  "data-placeholder=\"\" style=\"width:350px;\" tabindex=\"4\" multiple";

						%>
                                                <%=ControlCombo.drawStringArraySelected(frmAssessmentFormMain.fieldNames[frmAssessmentFormMain.FRM_FIELD_GROUP_RANK_ID], "chosen-select", null, assessmentFormMain.getsGroupRankId(), gr_key,gr_value,null, attTag)%>
                  
                                                        *<%=frmAssessmentFormMain.getErrorMsg(FrmAssessmentFormMain.FRM_FIELD_GROUP_RANK_ID)%>
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%"> 
                                                        Title</td>
                                                      <td width="83%"> 
                                                        <input type="text" name="<%=frmAssessmentFormMain.fieldNames[FrmAssessmentFormMain.FRM_FIELD_TITLE] %>"  value="<%= (assessmentFormMain.getTitle()!=null?assessmentFormMain.getTitle():"") %>" class="elemenForm" size="70">
                                                        *<%=frmAssessmentFormMain.getErrorMsg(FrmAssessmentFormMain.FRM_FIELD_TITLE)%>
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%"> 
                                                        Title 2nd</td>
                                                      <td width="83%"> 
                                                        <input type="text" name="<%=frmAssessmentFormMain.fieldNames[FrmAssessmentFormMain.FRM_FIELD_TITLE_L2] %>"  value="<%= (assessmentFormMain.getTitle_L2()!=null?assessmentFormMain.getTitle_L2():"") %>" class="elemenForm" size="70">
                                                        <%=frmAssessmentFormMain.getErrorMsg(FrmAssessmentFormMain.FRM_FIELD_TITLE_L2)%>
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%"> 
                                                        Subtitle</td>
                                                      <td width="83%"> 
                                                        <input type="text" name="<%=frmAssessmentFormMain.fieldNames[FrmAssessmentFormMain.FRM_FIELD_SUBTITLE] %>"  value="<%= (assessmentFormMain.getSubtitle()!=null?assessmentFormMain.getSubtitle():"") %>" class="elemenForm" size="50">
                                                        <%=frmAssessmentFormMain.getErrorMsg(FrmAssessmentFormMain.FRM_FIELD_SUBTITLE)%>
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%"> 
                                                        Subtitle 2nd</td>
                                                      <td width="83%"> 
                                                        <input type="text" name="<%=frmAssessmentFormMain.fieldNames[FrmAssessmentFormMain.FRM_FIELD_SUBTITLE_L2] %>"  value="<%= (assessmentFormMain.getSubtitle_L2()!=null?assessmentFormMain.getSubtitle_L2():"") %>" class="elemenForm" size="50">
                                                        <%=frmAssessmentFormMain.getErrorMsg(FrmAssessmentFormMain.FRM_FIELD_SUBTITLE_L2)%>
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%"> 
                                                        Main Data </td>
                                                      <td width="83%"> 
                                                      <input type="hidden" name="<%=frmAssessmentFormMain.fieldNames[FrmAssessmentFormMain.FRM_FIELD_MAIN_DATA] %>" value="">
                                                        <%
                                                          String strCode = assessmentFormMain.getMainData()!=null?assessmentFormMain.getMainData():"";
                                                          if(PstAssessmentFormMain.fieldFormTypes!=null && PstAssessmentFormMain.fieldFormTypes.length>0){
                                                                for(int i=0; i<PstAssessmentFormMain.fieldFormTypes.length; i++){
                                                                        
                                                                       boolean isUsed = PstAssessmentFormMain.cekFormUsed(strCode, i);
                                                                      // System.out.println(i+"=="+isUsed);
                                                                       String strVal = PstAssessmentFormMain.fieldFormValue[i][PstAssessmentFormMain.LANGUAGE_FIRST];
                                                                                                                                             
                                                                       %>
                                                                        <input type="checkbox" name="chx_<%=String.valueOf(i)%>" value="1" <%=(isUsed?"checked":"")%>><%=strVal%>
                                                                <%}			
                                                          }
                                                        %>
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                        <td valign="top" width="17%">&nbsp;</td>
                                                        <td width="83%"> <a href="javascript:setChecked(1)">General 
                                                          Data (Select All Data)&nbsp;
                                                        </a> &nbsp;&nbsp;| &nbsp;&nbsp;<a href="javascript:setChecked(0)">Release 
                                                          All</a></td>

                                                   </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="17%"> 
                                                        Note </td>
                                                      <td width="83%"> 
                                                        <textarea name="<%=frmAssessmentFormMain.fieldNames[FrmAssessmentFormMain.FRM_FIELD_NOTE] %>" class="elemenForm" cols="70" rows="3"><%= (assessmentFormMain.getNote()!=null?assessmentFormMain.getNote():"") %></textarea>
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
                                                    String scomDel = "javascript:cmdAsk('"+oidAssessmentFormMain+"')";
                                                    String sconDelCom = "javascript:cmdConfirmDelete('"+oidAssessmentFormMain+"')";
                                                    String scancel = "javascript:cmdEdit('"+oidAssessmentFormMain+"')";
                                                    ctrLine.setBackCaption("Back to List");
                                                    ctrLine.setCommandStyle("buttonlink");
                                                    ctrLine.setBackCaption("Back to List Form Main");
                                                    ctrLine.setSaveCaption("Save Form Main");
                                                    ctrLine.setConfirmDelCaption("Yes Delete Form Main");
                                                    ctrLine.setDeleteCaption("Delete Form Main");

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
<script src="../../javascripts/jquery.min-1.6.2.js" type="text/javascript"></script>
<script src="../../javascripts/chosen.jquery.js" type="text/javascript"></script>

    <script type="text/javascript">
        var config = {
            '.chosen-select'           : {},
            '.chosen-select-deselect'  : {allow_single_deselect:true},
            '.chosen-select-no-single' : {disable_search_threshold:10},
            '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
            '.chosen-select-width'     : {width:"95%"}
        }
        for (var selector in config) {
            $(selector).chosen(config[selector]);
        }
</script>
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
<!-- #EndTemplate -->
</html>
