
<% 
/* 
 * Page Name  		:  masterlanguage.jsp
 * Created on 		:  [25-9-2002] [11.50] AM/PM 
 * 
 * @author  		:  [lkarunia] 
 * @version  		:  [01] 
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
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMPLOYEE, AppObjInfo.OBJ_EMPLOYEE_LANGUAGE); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//kondisi privDelete diberikan nilai false agar command Delete tidak tampak dan user tidak bisa menghapus data master
//Edited By yunny
//privDelete=false;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%!

	public String drawList(int iCommand,FrmLanguage frmObject, Language objEntity, Vector objectClass,  long languageId)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("35%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("Language","100%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		Vector rowx = new Vector(1,1);
		ctrlist.reset();
		int index = -1;

		for (int i = 0; i < objectClass.size(); i++) {
			 Language language = (Language)objectClass.get(i);
			 rowx = new Vector();
			 if(languageId == language.getOID())
				 index = i; 

			 if(index == i && (iCommand == Command.EDIT || iCommand == Command.ASK)){
					
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmLanguage.FRM_FIELD_LANGUAGE] +"\" value=\""+language.getLanguage()+"\" size=\"30\" class=\"elemenForm\">");
			}else{
				rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(language.getOID())+"')\">"+language.getLanguage()+"</a>");
			} 

			lstData.add(rowx);
		}

		 rowx = new Vector();

		if(iCommand == Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize() > 0) || (objectClass.size()<1)){ 
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmLanguage.FRM_FIELD_LANGUAGE] +"\" value=\""+objEntity.getLanguage()+"\" size=\"30\" class=\"elemenForm\">");

		}

		lstData.add(rowx);

		return ctrlist.draw();
	}

%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidLanguage = FRMQueryString.requestLong(request, "hidden_language_id");

    /*variable declaration*/
    int recordToGet = 10;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    String whereClause = "";
    String orderClause = "";

    CtrlLanguage ctrLanguage = new CtrlLanguage(request);
    ControlLine ctrLine = new ControlLine();
    Vector listLanguage = new Vector(1,1);

    /*switch statement */
    iErrCode = ctrLanguage.action(iCommand , oidLanguage);
    /* end switch*/
    FrmLanguage frmLanguage = ctrLanguage.getForm();

    /*count list All Language*/
    int vectSize = PstLanguage.getCount(whereClause);

    /*switch list Language*/
    if((iCommand == Command.FIRST || iCommand == Command.PREV )||
      (iCommand == Command.NEXT || iCommand == Command.LAST)){
                    start = ctrLanguage.actionList(iCommand, start, vectSize, recordToGet);
     } 
    /* end switch list*/

    Language language = ctrLanguage.getLanguage();
    msgString =  ctrLanguage.getMessage();

    /* get record to display */
    listLanguage = PstLanguage.list(start,recordToGet, whereClause , orderClause);

    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listLanguage.size() < 1 && start > 0)
    {
             if (vectSize - recordToGet > recordToGet)
                            start = start - recordToGet;   //go to Command.PREV
             else{
                     start = 0 ;
                     iCommand = Command.FIRST;
                     prevCommand = Command.FIRST; //go to Command.FIRST
             }
             listLanguage = PstLanguage.list(start,recordToGet, whereClause , orderClause);
    }
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Master Data Language</title>
<script language="JavaScript">


function cmdAdd(){
	document.frmmasterlanguage.hidden_language_id.value="0";
	document.frmmasterlanguage.command.value="<%=Command.ADD%>";
	document.frmmasterlanguage.prev_command.value="<%=prevCommand%>";
	document.frmmasterlanguage.action="masterlanguage.jsp";
	document.frmmasterlanguage.submit();
}

function cmdAsk(oidLanguage){
	document.frmmasterlanguage.hidden_language_id.value=oidLanguage;
	document.frmmasterlanguage.command.value="<%=Command.ASK%>";
	document.frmmasterlanguage.prev_command.value="<%=prevCommand%>";
	document.frmmasterlanguage.action="masterlanguage.jsp";
	document.frmmasterlanguage.submit();
}

function cmdConfirmDelete(oidLanguage){
	document.frmmasterlanguage.hidden_language_id.value=oidLanguage;
	document.frmmasterlanguage.command.value="<%=Command.DELETE%>";
	document.frmmasterlanguage.prev_command.value="<%=prevCommand%>";
	document.frmmasterlanguage.action="masterlanguage.jsp";
	document.frmmasterlanguage.submit();
}

function cmdSave(){
	document.frmmasterlanguage.command.value="<%=Command.SAVE%>";
	document.frmmasterlanguage.prev_command.value="<%=prevCommand%>";
	document.frmmasterlanguage.action="masterlanguage.jsp";
	document.frmmasterlanguage.submit();
}

function cmdEdit(oidLanguage){
	document.frmmasterlanguage.hidden_language_id.value=oidLanguage;
	document.frmmasterlanguage.command.value="<%=Command.EDIT%>";
	document.frmmasterlanguage.prev_command.value="<%=prevCommand%>";
	document.frmmasterlanguage.action="masterlanguage.jsp";
	document.frmmasterlanguage.submit();
}

function cmdCancel(oidLanguage){
	document.frmmasterlanguage.hidden_language_id.value=oidLanguage;
	document.frmmasterlanguage.command.value="<%=Command.EDIT%>";
	document.frmmasterlanguage.prev_command.value="<%=prevCommand%>";
	document.frmmasterlanguage.action="masterlanguage.jsp";
	document.frmmasterlanguage.submit();
}

function cmdBack(){
	document.frmmasterlanguage.command.value="<%=Command.BACK%>";
	document.frmmasterlanguage.action="masterlanguage.jsp";
	document.frmmasterlanguage.submit();
}

function cmdListFirst(){
	document.frmmasterlanguage.command.value="<%=Command.FIRST%>";
	document.frmmasterlanguage.prev_command.value="<%=Command.FIRST%>";
	document.frmmasterlanguage.action="masterlanguage.jsp";
	document.frmmasterlanguage.submit();
}

function cmdListPrev(){
	document.frmmasterlanguage.command.value="<%=Command.PREV%>";
	document.frmmasterlanguage.prev_command.value="<%=Command.PREV%>";
	document.frmmasterlanguage.action="masterlanguage.jsp";
	document.frmmasterlanguage.submit();
}

function cmdListNext(){
	document.frmmasterlanguage.command.value="<%=Command.NEXT%>";
	document.frmmasterlanguage.prev_command.value="<%=Command.NEXT%>";
	document.frmmasterlanguage.action="masterlanguage.jsp";
	document.frmmasterlanguage.submit();
}

function cmdListLast(){
	document.frmmasterlanguage.command.value="<%=Command.LAST%>";
	document.frmmasterlanguage.prev_command.value="<%=Command.LAST%>";
	document.frmmasterlanguage.action="masterlanguage.jsp";
	document.frmmasterlanguage.submit();
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
//-------------- script form image -------------------

function cmdDelPict(oidLanguage){
	document.frmimage.hidden_language_id.value=oidLanguage;
	document.frmimage.command.value="<%=Command.POST%>";
	document.frmimage.action="masterlanguage.jsp";
	document.frmimage.submit();
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
			  <!-- #BeginEditable "contenttitle" -->Master Data > Language<!-- #EndEditable --> 
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
                                    <form name="frmmasterlanguage" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="hidden_language_id" value="<%=oidLanguage%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">                                              
                                              <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;Language 
                                                  List </td>
                                              </tr>
                                              <%
                                                try{
                                                if((listLanguage == null || listLanguage.size()<1)&&(iCommand == Command.NONE))
                                                        iCommand = Command.ADD;  
                                                %>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3">												
                                                  <%= drawList(iCommand,frmLanguage, language,listLanguage,oidLanguage)%> 
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
                                              <%
                                              if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmLanguage.errorSize()<1)){

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
                                                        New Language</a> </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              <%}
                                              }%>
                                            </table>
                                          </td>
                                        </tr>
										<%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmLanguage.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                                        <tr align="left" valign="top"> 
                                          <td height="8" valign="middle" width="17%">&nbsp;</td>
                                          <td height="8" colspan="2" width="83%">&nbsp; 
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top" > 
                                          <td colspan="3" class="command"> 
                                            <%
                                            ctrLine.setLocationImg(approot+"/images");
                                            ctrLine.initDefault();
                                            ctrLine.setTableWidth("80");
                                            String scomDel = "javascript:cmdAsk('"+oidLanguage+"')";
                                            String sconDelCom = "javascript:cmdConfirmDelete('"+oidLanguage+"')";
                                            String scancel = "javascript:cmdEdit('"+oidLanguage+"')";
                                            ctrLine.setBackCaption("Back to List language");
                                            ctrLine.setCommandStyle("buttonlink");
                                            ctrLine.setAddCaption("Add Language");
                                            ctrLine.setSaveCaption("Save Language");
                                            ctrLine.setDeleteCaption("Delete Language");
                                            ctrLine.setConfirmDelCaption("Delete Language");

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

                                            if((listLanguage.size()<1)&&(iCommand == Command.NONE))
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
