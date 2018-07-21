<% 
/* 
 * Page Name  		:  contactclass.jsp
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
<!--package prochain02 -->
<%@ page import = "com.dimata.common.entity.contact.*" %>
<%@ page import = "com.dimata.common.form.contact.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_TRAINING, AppObjInfo.G2_TRAINING_ORGANIZER , AppObjInfo.OBJ_TRAINING_ORGANIZER_CONTACT ); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%

//if of "hasn't access" condition 
if(!privView && !privAdd && !privUpdate && !privDelete){
%>
<script language="javascript">
	window.location="<%=approot%>/nopriv.html";
</script>
<!-- if of "has access" condition -->
<%
}else{
%>

<!-- Jsp Block -->
<%!
public static String strTitle[][] = {
	{"Nama","Tipe","Keterangan"},	
	{"Name","Type","Description"}
};

public static final String masterTitle[] = {
	"Kontak","Contact"	
};

public static final String classTitle[] = {
	"Kelompok Kontak","Contact Class"	
};

public String getJspTitle(String textJsp[][], int index, int language, String prefiks, boolean addBody){
	String result = "";
	if(addBody){
		if(language==com.dimata.util.lang.I_Language.LANGUAGE_DEFAULT){	
			result = textJsp[language][index] + " " + prefiks;
		}else{
			result = prefiks + " " + textJsp[language][index];		
		}
	}else{
		result = textJsp[language][index];
	} 
	return result;
}

public String drawList(int language,int iCommand,FrmContactClass frmObject,ContactClass objEntity,Vector objectClass,long contactClassId, int iStart){
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("70%");
        ctrlist.setListStyle("tblStyle");
        ctrlist.setTitleStyle("title_tbl");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("title_tbl");
        ctrlist.setCellSpacing("0");
	ctrlist.dataFormat(strTitle[language][0],"20%","left","left");
	ctrlist.dataFormat(strTitle[language][1],"10%","left","left");
	ctrlist.dataFormat(strTitle[language][2],"80%","left","left");

	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	Vector rowx = new Vector(1,1);
	ctrlist.reset();
	int index = -1;
	int series = 0;
	
	Vector val_typeid = new Vector(1,1); 
	Vector key_typeid = new Vector(1,1); 
	for(int i=0;i<PstContactClass.fieldClassType.length;i++){	
	    val_typeid.add(""+i+"");
		key_typeid.add(PstContactClass.fieldClassType[i]);
	}
	String select_typeid = ""+objEntity.getClassType();
	
	for(int i=0; i<objectClass.size(); i++) {
		 ContactClass contactClass = (ContactClass)objectClass.get(i);
		 rowx = new Vector();
		 
		 series = i + 1 + iStart;
		 if(contactClassId == contactClass.getOID())
			 index = i; 
			 
		 select_typeid = ""+contactClass.getClassType();
		 if(index==i && (iCommand==Command.EDIT || iCommand==Command.ASK)){
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmContactClass.FRM_FIELD_CLASS_NAME] +"\" value=\""+contactClass.getClassName()+"\" class=\"elemenForm\">");
			rowx.add(""+ControlCombo.draw(frmObject.fieldNames[frmObject.FRM_FIELD_CLASS_TYPE], null, select_typeid, val_typeid, key_typeid,"onChange=\"javascript:changeContType()\"","")+"");
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmContactClass.FRM_FIELD_CLASS_DESCRIPTION] +"\"  class=\"elemenForm\" value=\""+contactClass.getClassDescription()+"\" size=\"70\">"+
					 "<input type=\"hidden\" name=\""+frmObject.fieldNames[FrmContactClass.FRM_FIELD_SERIES_NUMBER] +"\"  class=\"elemenForm\" value=\""+contactClass.getSeriesNumber()+"\" size=\"70\">"
					);
		}else{
			rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(contactClass.getOID())+"')\">"+contactClass.getClassName()+"</a>");
			rowx.add(PstContactClass.fieldClassType[contactClass.getClassType()]);
			rowx.add(contactClass.getClassDescription());
		} 

		lstData.add(rowx);
	}

	rowx = new Vector();
	if(iCommand==Command.ADD || (iCommand==Command.SAVE && frmObject.errorSize()>0)){ 
	
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmContactClass.FRM_FIELD_CLASS_NAME] +"\" value=\""+objEntity.getClassName()+"\" class=\"elemenForm\">");
			rowx.add(""+ControlCombo.draw(frmObject.fieldNames[frmObject.FRM_FIELD_CLASS_TYPE], null, select_typeid, val_typeid, key_typeid,"onChange=\"javascript:changeContType()\"","")+"");				
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmContactClass.FRM_FIELD_CLASS_DESCRIPTION] +"\"  class=\"elemenForm\" value=\""+objEntity.getClassDescription()+"\" size=\"70\">"+
					"<input type=\"hidden\" name=\""+frmObject.fieldNames[FrmContactClass.FRM_FIELD_SERIES_NUMBER] +"\"  class=\"elemenForm\" value=\""+series+"\" size=\"70\">"
			);
	}
	lstData.add(rowx);
	return ctrlist.drawMe(index);
}
%>

<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidContactClass = FRMQueryString.requestLong(request, "hidden_contact_class_id");
/*if(iCommand == Command.NONE)
	iCommand = Command.ADD;*/
	
/*variable declaration*/
int recordToGet = 100;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = "";

/**
* Setup controlLine and Commands caption
*/
ControlLine ctrLine = new ControlLine();
ctrLine.setLanguage(SESS_LANGUAGE);
String currPageTitle = classTitle[SESS_LANGUAGE];
String strAddCls = ctrLine.getCommand(SESS_LANGUAGE,currPageTitle,ctrLine.CMD_ADD,true);
String strSaveCls = ctrLine.getCommand(SESS_LANGUAGE,currPageTitle,ctrLine.CMD_SAVE,true);
String strAskCls = ctrLine.getCommand(SESS_LANGUAGE,currPageTitle,ctrLine.CMD_ASK,true);
String strBackCls = ctrLine.getCommand(SESS_LANGUAGE,currPageTitle,ctrLine.CMD_BACK,true)+(SESS_LANGUAGE==com.dimata.util.lang.I_Language.LANGUAGE_FOREIGN ? " List" : "");
String strDeleteCls = ctrLine.getCommand(SESS_LANGUAGE,currPageTitle,ctrLine.CMD_DELETE,true);
String strCancel = ctrLine.getCommand(SESS_LANGUAGE,currPageTitle,ctrLine.CMD_CANCEL,false);
String delConfirm = (SESS_LANGUAGE==com.dimata.util.lang.I_Language.LANGUAGE_FOREIGN ? "Are You Sure to Delete " : "Anda Yakin Menghapus ")+currPageTitle+" ?";

CtrlContactClass ctrlContactClass = new CtrlContactClass(request);
ctrlContactClass.setLanguage(SESS_LANGUAGE);

Vector listContactClass = new Vector(1,1);

iErrCode = ctrlContactClass.action(iCommand , oidContactClass);
FrmContactClass frmContactClass = ctrlContactClass.getForm();

int vectSize = PstContactClass.getCount(whereClause); 

if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlContactClass.actionList(iCommand, start, vectSize, recordToGet);
} 

ContactClass contactClass = ctrlContactClass.getContactClass();

msgString =  ctrlContactClass.getMessage();
listContactClass = PstContactClass.list(start,recordToGet, whereClause , orderClause);

if (listContactClass.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet){
			start = start - recordToGet; 
	 }else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; 
	 }
	 listContactClass = PstContactClass.list(start,recordToGet, whereClause , orderClause);
}
%>
<html><!-- #BeginTemplate "/Templates/main-menu-left-frames.dwt" --><!-- DW6 -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Hairisma - Contact</title>
<script language="JavaScript">
<!--

function cmdAdd(){
	document.frmcontactclass.hidden_contact_class_id.value="0";
	document.frmcontactclass.command.value="<%=Command.ADD%>";
	document.frmcontactclass.prev_command.value="<%=prevCommand%>";
	document.frmcontactclass.action="contact_class.jsp";
	document.frmcontactclass.submit();
}

function cmdAsk(oidContactClass){
	document.frmcontactclass.hidden_contact_class_id.value=oidContactClass;
	document.frmcontactclass.command.value="<%=Command.ASK%>";
	document.frmcontactclass.prev_command.value="<%=prevCommand%>";
	document.frmcontactclass.action="contact_class.jsp";
	document.frmcontactclass.submit();
}

function cmdConfirmDelete(oidContactClass){
	document.frmcontactclass.hidden_contact_class_id.value=oidContactClass;
	document.frmcontactclass.command.value="<%=Command.DELETE%>";
	document.frmcontactclass.prev_command.value="<%=prevCommand%>";
	document.frmcontactclass.action="contact_class.jsp";
	document.frmcontactclass.submit();
}

function cmdSave(){
	document.frmcontactclass.command.value="<%=Command.SAVE%>";
	document.frmcontactclass.prev_command.value="<%=prevCommand%>";
	document.frmcontactclass.action="contact_class.jsp";
	document.frmcontactclass.submit();
}

function cmdEdit(oidContactClass){
	document.frmcontactclass.hidden_contact_class_id.value=oidContactClass;
	document.frmcontactclass.command.value="<%=Command.EDIT%>";
	document.frmcontactclass.prev_command.value="<%=prevCommand%>";
	document.frmcontactclass.action="contact_class.jsp";
	document.frmcontactclass.submit();
}

function cmdCancel(oidContactClass){
	document.frmcontactclass.hidden_contact_class_id.value=oidContactClass;
	document.frmcontactclass.command.value="<%=Command.EDIT%>";
	document.frmcontactclass.prev_command.value="<%=prevCommand%>";
	document.frmcontactclass.action="contact_class.jsp";
	document.frmcontactclass.submit();
}

function cmdBack(){
	document.frmcontactclass.command.value="<%=Command.BACK%>";
	document.frmcontactclass.action="contact_class.jsp";
	document.frmcontactclass.submit();
}

function first(){
	document.frmcontactclass.command.value="<%=Command.FIRST%>";
	document.frmcontactclass.prev_command.value="<%=Command.FIRST%>";
	document.frmcontactclass.action="contact_class.jsp";
	document.frmcontactclass.submit();
}

function prev(){
	document.frmcontactclass.command.value="<%=Command.PREV%>";
	document.frmcontactclass.prev_command.value="<%=Command.PREV%>";
	document.frmcontactclass.action="contact_class.jsp";
	document.frmcontactclass.submit();
}

function next(){
	document.frmcontactclass.command.value="<%=Command.NEXT%>";
	document.frmcontactclass.prev_command.value="<%=Command.NEXT%>";
	document.frmcontactclass.action="contact_class.jsp";
	document.frmcontactclass.submit();
}

function last(){
	document.frmcontactclass.command.value="<%=Command.LAST%>";
	document.frmcontactclass.prev_command.value="<%=Command.LAST%>";
	document.frmcontactclass.action="contact_class.jsp";
	document.frmcontactclass.submit();
}

function changeContType(){
	var contType = 0;
	<% if((iCommand == Command.ADD || iCommand == Command.EDIT) && iErrCode == 0){ %>
		contType = document.frmcontactclass.<%=frmContactClass.fieldNames[frmContactClass.FRM_FIELD_CLASS_TYPE]%>.value;
	<%}%>	
	
	switch(contType){
		<%			
			String type = "";
			int index = 0;
		if(PstContactClass.fieldClassType.length > 0){	 
			for(int i=0;i<PstContactClass.fieldClassType.length;i++){	
				index = i;	    		
				type = PstContactClass.fieldClassType[i];
				
		%>
		
					case '<%=index%>':										
						document.frmcontactclass.<%=frmContactClass.fieldNames[frmContactClass.FRM_FIELD_CLASS_DESCRIPTION]%>.value = "<%=type%>";
						document.frmcontactclass.<%=frmContactClass.fieldNames[frmContactClass.FRM_FIELD_CLASS_NAME]%>.value = "<%=type.toUpperCase()%>";
					break;					
			<%}
			}
			%>
	}
		
}

//-------------- script form image -------------------

function cmdDelPict(oidContactClass){
	document.frmimage.hidden_contact_class_id.value=oidContactClass;
	document.frmimage.command.value="<%=Command.POST%>";
	document.frmimage.action="contact_class.jsp";
	document.frmimage.submit();
}

//-------------- script control line -------------------
//-->
</script>
<!-- #EndEditable -->
<!-- #BeginEditable "headerscript" --> 
<SCRIPT language=JavaScript>
/*function hideObjectForMarketing(){    
} 
	 
function hideObjectForWarehouse(){ 
}
	
function hideObjectForProduction(){
}
	
function hideObjectForPurchasing(){
}

function hideObjectForAccounting(){
}

function hideObjectForHRD(){
}

function hideObjectForGallery(){
}

function hideObjectForMasterData(){
}*/

function hideObjectForMenuJournal(){
}
	
function hideObjectForMenuReport(){	 
}
	
function hideObjectForMenuPeriod(){
}
	 
function hideObjectForMenuMasterData(){
}

function hideObjectForMenuSystem(){
}

function showObjectForMenu(){ 	
}

</SCRIPT>
<!-- #EndEditable -->
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <!-- #BeginEditable "styles" -->
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "stylestab" -->
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <!-- #EndEditable -->       
        <style type="text/css">
            body {background-color: #EEE;}
            .header {
                
            }
            .content-main {
                background-color: #FFF;
                margin: 25px 23px 59px 23px;
                border: 1px solid #DDD;
                border-radius: 5px;
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
            .tblStyle {border-collapse: collapse;font-size: 11px;}
            .tblStyle td {padding: 3px 5px; border: 1px solid #CCC;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
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
                background: #ebebeb;
                background-image: -webkit-linear-gradient(top, #ebebeb, #dddddd);
                background-image: -moz-linear-gradient(top, #ebebeb, #dddddd);
                background-image: -ms-linear-gradient(top, #ebebeb, #dddddd);
                background-image: -o-linear-gradient(top, #ebebeb, #dddddd);
                background-image: linear-gradient(to bottom, #ebebeb, #dddddd);
                -webkit-border-radius: 5;
                -moz-border-radius: 5;
                border-radius: 3px;
                font-family: Arial;
                color: #7a7a7a;
                font-size: 12px;
                padding: 5px 11px 5px 11px;
                border: solid #d9d9d9 1px;
                text-decoration: none;
            }

            .btn:hover {
                color: #474747;
                background: #ddd;
                background-image: -webkit-linear-gradient(top, #ddd, #CCC);
                background-image: -moz-linear-gradient(top, #ddd, #CCC);
                background-image: -ms-linear-gradient(top, #ddd, #CCC);
                background-image: -o-linear-gradient(top, #ddd, #CCC);
                background-image: linear-gradient(to bottom, #ddd, #CCC);
                text-decoration: none;
                border: 1px solid #C5C5C5;
            }
            
            .btn-small {
                padding: 3px; border: 1px solid #CCC; 
                background-color: #EEE; color: #777777; 
                font-size: 11px; cursor: pointer;
            }
            .btn-small:hover {border: 1px solid #999; background-color: #CCC; color: #FFF;}
            
            .tbl-main {border-collapse: collapse; font-size: 11px; background-color: #FFF; margin: 0px;}
            .tbl-main td {padding: 4px 7px; border: 1px solid #DDD; }
            #tbl-title {font-weight: bold; background-color: #F5F5F5; color: #575757;}
            
            .tbl-small {border-collapse: collapse; font-size: 11px; background-color: #FFF;}
            .tbl-small td {padding: 2px 3px; border: 1px solid #DDD; }
            
            #caption {padding: 7px 0px 2px 0px; font-size: 12px; font-weight: bold; color: #575757;}
            #div_input {}
            
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
            <div class="content-main">
                <div class="content-title">
                    <div id="title-large">Kelompok Kontak</div>
                    <div id="title-small">Daftar kontak penyedia.</div>
                </div>

            <div class="content">
            <form name="frmcontactclass" method ="post" action="">
              <input type="hidden" name="command" value="<%=iCommand%>">
              <input type="hidden" name="vectSize" value="<%=vectSize%>">
              <input type="hidden" name="start" value="<%=start%>">
              <input type="hidden" name="prev_command" value="<%=prevCommand%>">
              <input type="hidden" name="hidden_contact_class_id" value="<%=oidContactClass%>">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr align="left" valign="top"> 
                  <td height="8"  colspan="3"> 
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr align="left" valign="top"> 
                        <td height="8" valign="middle" colspan="3">&nbsp; 
                          
                        </td>
                      </tr>
                      <%
							try{
							%>
                      <tr align="left" valign="top"> 
                        <td height="22" valign="middle" colspan="3"> <%= drawList(SESS_LANGUAGE,iCommand,frmContactClass, contactClass,listContactClass,oidContactClass,start)%> </td>
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
								cmd = Command.LAST;
							  else 
								cmd =prevCommand; 
						   } 
						   ctrLine.initDefault();
						 %>
                         <%=ctrLine.drawMeListLimit(cmd,vectSize,start,recordToGet,"first","prev","next","last","left")%></span> </td>
                      </tr>					  
                      <%//if(privAdd && (iErrCode==CtrlContactClass.RSLT_OK)&&(iCommand!=Command.ADD)&&(iCommand!=Command.ASK)&&(iCommand!=Command.EDIT)&&(frmContactClass.errorSize()==0)){ %>
					  					  
                      <!-- <tr align="left" valign="top"> 
                        <td height="22" valign="middle" colspan="3"> 
						<a href="javascript:cmdAdd()" class="command"><%//=strAddCls%></a>
                        </td>
                      </tr> -->
                      <%//}%>					  
                    </table>
                  </td>
                </tr>
                <tr align="left" valign="top" > 
                  <td colspan="3" class="command"> 
                    <%                  iCommand = iCommand == Command.NONE ? Command.LIST : iCommand;
					ctrLine.setLocationImg(approot+"/images");						  					
					ctrLine.initDefault();
					ctrLine.setTableWidth("80%");
					String scomDel = "javascript:cmdAsk('"+oidContactClass+"')";
					String sconDelCom = "javascript:cmdConfirmDelete('"+oidContactClass+"')";
					String scancel = "javascript:cmdEdit('"+oidContactClass+"')";
					ctrLine.setCommandStyle("command");
					ctrLine.setColCommStyle("command");
					ctrLine.setCancelCaption(strCancel);
					ctrLine.setBackCaption("");
					ctrLine.setSaveCaption(strSaveCls);
					ctrLine.setDeleteCaption(strAskCls);
					ctrLine.setAddCaption(strAddCls);
					ctrLine.setConfirmDelCaption(strDeleteCls);

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
					}%>
					<%=ctrLine.draw(iCommand, iErrCode, msgString)%> 
					<% if(iCommand==Command.ASK)
						ctrLine.setDeleteQuestion(delConfirm);%>				
					
                    
                  </td>
                </tr>
              </table>
            </form>
            </div>
            <script javascript>
                    changeContType();
            </script>
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
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
<% } %>