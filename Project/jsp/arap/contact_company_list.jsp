<% 
/* 
 * Page Name  		:  contactlist_list.jsp
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
<%@ page import = "java.util.*,
                   com.dimata.aiso.form.arap.FrmArApMain" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package prochain02 -->
<%@ page import = "com.dimata.common.entity.contact.*" %>
<%@ page import = "com.dimata.common.entity.search.*" %>
<%@ page import = "com.dimata.common.form.contact.*" %>
<%@ page import = "com.dimata.common.form.search.*" %>
<%@ page import = "com.dimata.common.session.contact.*" %>


<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode = 1;//AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MASTERDATA_CONTACT, AppObjInfo.OBJ_MASTERDATA_CONTACT_COMPANY); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
boolean privView=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
boolean privAdd=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
boolean privUpdate=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
boolean privDelete=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));

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
	{"No","Kode","Nama Perusahaan","Nama Kontak","Telepon","Alamat"},	
	{"No","Code","Company Name","Contact Name","Phone","Address"}
};

public static final String masterTitle[] = {
	"Daftar","List"
};

public static final String compTitle[] = {
	"Kontak","Contact"
};

public String drawList(int language, Vector objectClass, long oid, int start){
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setCellStyleOdd("listgensellOdd");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.dataFormat(strTitle[language][0],"2%","left","left");
	ctrlist.dataFormat(strTitle[language][1],"11%","left","left");
	ctrlist.dataFormat(strTitle[language][2],"22%","left","left");
	ctrlist.dataFormat(strTitle[language][3],"22%","left","left");
	ctrlist.dataFormat(strTitle[language][4],"11%","left","left");
	ctrlist.dataFormat(strTitle[language][5],"33%","left","left");

	ctrlist.setLinkRow(1);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
	int index = -1;
	
	if(start<0)
		start =0;
		
	for (int i = 0; i < objectClass.size(); i++) {
		ContactList contactList = (ContactList)objectClass.get(i);
		Vector rowx = new Vector();
		
	    if(oid==contactList.getOID()){index =i;} 		
		
		start = start + 1;
		rowx.add(String.valueOf(start)); 
		
		rowx.add(cekNull(contactList.getContactCode())); 
		rowx.add(cekNull(contactList.getCompName()));
		rowx.add(cekNull(contactList.getPersonName())+" "+cekNull(contactList.getPersonLastname()));
		rowx.add(cekNull(contactList.getTelpNr()));
		rowx.add(cekNull(contactList.getBussAddress()));
		
		lstData.add(rowx);
		lstLinkData.add(String.valueOf(contactList.getOID())+"','"+mergeString(contactList.getCompName(),contactList.getPersonName()));
	}
	return ctrlist.drawMe(index);
}

public String cekNull(String val){
	if(val==null)
		val = "";
	return val;	
}

public String mergeString(String name1, String name2){
	if(name1==null || name1.length()==0){
        if(name2==null || name2.length()==0){
            return "";
        }
        else{
            return name2;
        }
    }
    else{
        if(name2==null || name2.length()==0){
            return name1;
        }
        else{
            return name1 + " / " + name2;
        }
    }
}
%>

<%
int iCommand = FRMQueryString.requestCommand(request);
long oidContactList = FRMQueryString.requestLong(request, "hidden_contact_id");
int start = FRMQueryString.requestInt(request, "start");
long arap_main_id = FRMQueryString.requestLong(request, "arap_main_id");
long journal_id = FRMQueryString.requestLong(request, "journal_id");

/**
* Setup controlLine and Commands caption
*/
ControlLine ctrLine = new ControlLine();
ctrLine.setLanguage(SESS_LANGUAGE);
String currPageTitle = compTitle[SESS_LANGUAGE];
String strAddComp = ctrLine.getCommand(SESS_LANGUAGE,currPageTitle,ctrLine.CMD_ADD,true);
String strBackComp = ctrLine.getCommand(SESS_LANGUAGE,currPageTitle,ctrLine.CMD_BACK_SEARCH,true);
String strNoCompany = SESS_LANGUAGE==com.dimata.util.lang.I_Language.LANGUAGE_FOREIGN ? "No "+currPageTitle+" Available" : "Tidak Ada "+currPageTitle; 

int recordToGet = 20;
int vectSize = 0;
String whereClause = "";
int iErrCode = FRMMessage.ERR_NONE;
String msgStr = "";

//if using deduplicate contact, set this variable TRUE otherwise FALSE
boolean useDeduplicate = true;


/**
* instantiate some object used in this page
*/
CtrlContactList ctrlContactList = new CtrlContactList(request);
SrcContactList srcContactList = new SrcContactList();
SessContactList sessContactList = new SessContactList();
FrmSrcContactList frmSrcContactList = new FrmSrcContactList(request, srcContactList);
		
/**
* handle current search data session 
*/
if(iCommand==Command.BACK || iCommand==Command.FIRST || iCommand==Command.PREV || iCommand==Command.NEXT || iCommand==Command.LAST){
	 try{ 
		srcContactList = (SrcContactList)session.getValue(SessContactList.SESS_SRC_CONTACT_LIST); 
	 }catch(Exception e){ 
		srcContactList = new SrcContactList();
	 }
}else{
	 frmSrcContactList.requestEntityObject(srcContactList);
	 Vector vectSt = new Vector(1,1);
	 String[] strStatus = request.getParameterValues(frmSrcContactList.fieldNames[frmSrcContactList.FRM_FIELD_TYPE]);
	 if(strStatus!=null && strStatus.length>0){
		 for(int i=0; i<strStatus.length; i++){        
			try{
				vectSt.add(strStatus[i]);
			}catch(Exception exc){
				System.out.println("err");
			}
		 }
	 }
	 srcContactList.setType(vectSt);
	 session.putValue(SessContactList.SESS_SRC_CONTACT_LIST, srcContactList);
}

/**
* get vectSize, start and data to be display in this page
*/
vectSize = sessContactList.countCompany(srcContactList);
if(iCommand!=Command.BACK){  
	start = ctrlContactList.actionList(iCommand, start, vectSize, recordToGet);
}else{
	iCommand = Command.LIST;
}

Vector records = sessContactList.searchCompany(srcContactList, start, recordToGet);

if(useDeduplicate){
	if(session.getValue("DEDUPLICATE_COMPANY")!=null){
		session.removeValue("DEDUPLICATE_COMPANY");
	}
	session.putValue("DEDUPLICATE_COMPANY",records);
}
%>
<!-- End of Jsp Block -->
<html>
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Accounting Information System Online</title>
<script language="JavaScript">
<!--
function cmdAdd(){
	document.frm_contactlist.command.value="<%=Command.ADD%>";
	document.frm_contactlist.action="contact_company_edit.jsp";
	document.frm_contactlist.submit();
}

function cmdEdit(oid, name){
    document.frm_contactlist.hidden_contact_id.value=""+oid;
    updateContact(oid);
    self.opener.document.forms.frmarapentry.<%=FrmArApMain.fieldNames[FrmArApMain.FRM_CONTACT_ID]%>.value = oid;
    self.opener.document.forms.frmarapentry.<%=FrmArApMain.fieldNames[FrmArApMain.FRM_CONTACT_ID]%>_TEXT.value = name;
    self.close();
}

function first(){
	document.frm_contactlist.command.value="<%=Command.FIRST%>";
	document.frm_contactlist.action="contact_company_list.jsp";
	document.frm_contactlist.submit();
}

function prev(){
	document.frm_contactlist.command.value="<%=Command.PREV%>";
	document.frm_contactlist.action="contact_company_list.jsp";
	document.frm_contactlist.submit();
}

function next(){
	document.frm_contactlist.command.value="<%=Command.NEXT%>";
	document.frm_contactlist.action="contact_company_list.jsp";
	document.frm_contactlist.submit();
}

function last(){
	document.frm_contactlist.command.value="<%=Command.LAST%>";
	document.frm_contactlist.action="contact_company_list.jsp";
	document.frm_contactlist.submit();
}

function cmdBack(){
	document.frm_contactlist.command.value="<%=Command.BACK%>";
	document.frm_contactlist.action="srccontact_list.jsp";
	document.frm_contactlist.submit();
}

function cmdDeduplicate(){
	var strUrl = "deduplicate_company.jsp"; 
	window.open(strUrl,"personnel","height=600,width=800,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes");	
}
//-------------- script control line -------------------
//-->
</script>
<script type="text/javascript">
	var req;
	function updateContact(oid) {
           var newContactOid= document.frm_contactlist.hidden_contact_id.value;
           //alert(newContactOid);
	   //var poststr ="arap_main_id="+encodeURI(<%=("'"+arap_main_id+"'")%>)+"&journal_id="+encodeURI(<%=("'"+journal_id+"'")%>)+"&oid=" +   encodeURI("'"+oid+"'");
           var poststr ="arap_main_id="+(<%=("'"+arap_main_id+"'")%>)+"&journal_id="+(<%=("'"+journal_id+"'")%>)+"&oid="+newContactOid+"";
	   var url = "update_contact.jsp";
if (window.XMLHttpRequest) {
	       req = new XMLHttpRequest();
	   } else if (window.ActiveXObject) {
	       req = new ActiveXObject("Microsoft.XMLHTTP");
	   }
	   
	   req.open("POST", url, true);
	   req.onreadystatechange = callback;
	   req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	   req.setRequestHeader("Content-length", poststr.length);
	   req.setRequestHeader("Connection", "close");
	   req.send(poststr);
	}
function callback() {
	    if (req.readyState == 4) {
	        if (req.status == 200) {
	        // update the HTML DOM based on whether or not message is valid
	            parseMessage();
	        }
	    }
	}
function parseMessage() {
	    var message = req.responseText;
	    setMessage(message);
	}
	function setMessage(message) {
	    mdiv = document.getElementById("Message");
	    mdiv.innerHTML = "<div style=\"color:green\">"+message+"</ div>";
	}
	</script>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../style/main.css" type="text/css">
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table bgcolor="#FFFFFF" width="100%" border="0" cellspacing="3" cellpadding="2" height="100%">

  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td height="20" class="contenttitle"> 
           <%=masterTitle[SESS_LANGUAGE]%> : <font color="#CC3300"><%=currPageTitle.toUpperCase()%></font></td>
        </tr>
        <tr> 
          <td>            <form name="frm_contactlist" method="post" action="">
                  <div id="Message"></div>
              <input type="hidden" name="command" value="">
              <input type="hidden" name="start" value="<%=start%>">
              <input type="hidden" name="hidden_contact_id" value="<%=oidContactList%>">
              <input type="hidden" name="arap_main_id" value="<%=arap_main_id%>">
              <input type="hidden" name="journal_id" value="<%=journal_id%>">
              <table border="0" width="100%">
                <tr> 
                  <td height="8">&nbsp; 
                  </td>
                </tr>
              </table>                       
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
	            <%if((records!=null)&&(records.size()>0)){%>			  
                <tr> 
                  <td><%=drawList(SESS_LANGUAGE,records,oidContactList,start)%></td>
                </tr>			  
                <%}else{%>
                <tr> 
                  <td><span class="comment"><%=strNoCompany%></span></td>
                </tr>			  				
				<%}%>				
                <tr> 
                  <td>
				  <%=ctrLine.drawMeListLimit(iCommand,vectSize,start,recordToGet,"first","prev","next","last","left")%> 
                  </td>
                </tr>				
                <tr> 
                  <td width="46%" nowrap align="left" class="command">
				  <%if(privAdd){%><a href="javascript:cmdAdd()" class="command"><%=strAddComp%></a><%}%> | <a href="javascript:cmdBack()" class="command"><%=strBackComp%></a>
                  </td>
                </tr>								
              </table>
            </form></td>
        </tr>
      </table>
    </td>
  </tr>

</table>
</body>

</html>
<!-- endif of "has access" condition -->
<%}%>