<%-- 
/* 
 * Page Name  		:  section.jsp
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
--%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_SECTION); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%!
    public String getSectionLink(String sectionId){
        String str = "";
        try{
            Section section = PstSection.fetchExc(Long.valueOf(sectionId));
            str = section.getSection();
            return str;
        } catch(Exception e){
            System.out.println(e);
        }
        return str;
    }
	public String drawList(Vector objectClass ,  long sectionId)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("80%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("Section","20%");
		ctrlist.addHeader("Department","25%");
		ctrlist.addHeader("Description","30%");
                ctrlist.addHeader("Section Link","30%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;

		for (int i = 0; i < objectClass.size(); i++) {
			Section section = (Section)objectClass.get(i);
			 Vector rowx = new Vector();
			 if(sectionId == section.getOID())
				 index = i;

			rowx.add(section.getSection());
			
			//System.out.println("section.getDepartmentId()"+section.getDepartmentId());  
			Vector vector = PstDepartment.list(0,1,PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+" = "+section.getDepartmentId(),"");
			String strDept = "";
			if(vector != null && vector.size()>0){
				Department depart = (Department)vector.get(0);
				strDept = depart.getDepartment();
			}
			rowx.add(strDept);

			rowx.add(section.getDescription());
                        String strLink = "";
                        String strSectionLinkTo = "";
                        String strSectionLink = section.getSectionLinkTo();
                        if ((strSectionLink != null)&&!"".equals(strSectionLink)){

                            for (String retval : strSectionLink.split(",")) {
                                strLink = "<span style='color: #0066CC; background-color: #DDD;padding: 2px; margin-right:3px;'>"+getSectionLink(retval)+"</span>";
                                strSectionLinkTo += strLink;
                            }
                        }
                        
                        rowx.add(strSectionLinkTo);
			lstData.add(rowx);
			lstLinkData.add(String.valueOf(section.getOID()));
		}
		

		return ctrlist.draw(index);
	}

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidSection = FRMQueryString.requestLong(request, "hidden_section_id");
String sectionInput = FRMQueryString.requestString(request, "section_input");
long oidSectionTemp = FRMQueryString.requestLong(request, "section_temp_id");
/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;

CtrlSection ctrlSection = new CtrlSection(request);
ControlLine ctrLine = new ControlLine();
Vector listSection = new Vector(1,1);

/*switch statement */
iErrCode = ctrlSection.action(iCommand , oidSection);
/* end switch*/
FrmSection frmSection = ctrlSection.getForm();


SrcSection srcSection = new SrcSection();
FrmSrcSection frmSrcSection  = new FrmSrcSection(request, srcSection);
if(iCommand==Command.FIRST || iCommand==Command.PREV || iCommand==Command.NEXT || iCommand==Command.LAST || iCommand==Command.BACK || iCommand==Command.ASK
   || iCommand==Command.EDIT || iCommand==Command.ADD || iCommand==Command.DELETE || (iCommand==Command.SAVE && frmSection.errorSize()==0) )
{
	 try
	 { 
		srcSection = (SrcSection)session.getValue(PstSection.SESS_HR_SECTION); 
	 }
	 catch(Exception e)
	 { 
		srcSection = new SrcSection();
	 }
}
else
{
	frmSrcSection.requestEntityObject(srcSection);
	session.putValue(PstSection.SESS_HR_SECTION, srcSection);	
}



String whereClause = "";
if(srcSection.getSecName()!=null && srcSection.getSecName().length()>0)
{
	whereClause = PstSection.fieldNames[PstSection.FLD_SECTION] + 
				  " LIKE \"%" + srcSection.getSecName() + "%\"";
	
	if(srcSection.getSecDepartment() != 0)
	{
		whereClause = whereClause + " AND " + PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + 
			 		  " = " + srcSection.getSecDepartment();
	}
}
else
{
	if(srcSection.getSecDepartment() != 0)
	{
		whereClause = whereClause + PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + 
			 		  " = " + srcSection.getSecDepartment();
	}
}

String orderClause = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+", "+PstSection.fieldNames[PstSection.FLD_SECTION];

/*count list All Section*/
int vectSize = PstSection.getCount(whereClause);

Section section = ctrlSection.getSection();
msgString =  ctrlSection.getMessage();

/*switch list Section*/
if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)){
	//start = PstSection.findLimitStart(section.getOID(),recordToGet, whereClause, orderClause);
	oidSection = section.getOID();
}

if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlSection.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

/* get record to display */
listSection = PstSection.list(start,recordToGet, whereClause , orderClause);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listSection.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listSection = PstSection.list(start,recordToGet, whereClause , orderClause);
}
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Master Data Section</title>
<script language="JavaScript">
function cmdBackToSearch(){
	document.frmsection.command.value="<%=Command.BACK%>";
	document.frmsection.action="srcsection.jsp";
	document.frmsection.submit();
}

function cmdAdd(){
	document.frmsection.hidden_section_id.value="0";
	document.frmsection.command.value="<%=Command.ADD%>";
	document.frmsection.prev_command.value="<%=prevCommand%>";
	document.frmsection.action="section_new.jsp";
	document.frmsection.submit();
}

function cmdAsk(oidSection){
	document.frmsection.hidden_section_id.value=oidSection;
	document.frmsection.command.value="<%=Command.ASK%>";
	document.frmsection.prev_command.value="<%=prevCommand%>";
	document.frmsection.action="section_new.jsp";
	document.frmsection.submit();
}

function cmdConfirmDelete(oidSection){
	document.frmsection.hidden_section_id.value=oidSection;
	document.frmsection.command.value="<%=Command.DELETE%>";
	document.frmsection.prev_command.value="<%=prevCommand%>";
	document.frmsection.action="section_new.jsp";
	document.frmsection.submit();
}
function cmdSave(){
	document.frmsection.command.value="<%=Command.SAVE%>";
	document.frmsection.prev_command.value="<%=prevCommand%>";
	document.frmsection.action="section_new.jsp";
	document.frmsection.submit();
	}

function cmdEdit(oidSection){
	document.frmsection.hidden_section_id.value=oidSection;
	document.frmsection.command.value="<%=Command.EDIT%>";
	document.frmsection.prev_command.value="<%=prevCommand%>";
	document.frmsection.action="section_new.jsp";
	document.frmsection.submit();
	}

function cmdCancel(oidSection){
	document.frmsection.hidden_section_id.value=oidSection;
	document.frmsection.command.value="<%=Command.EDIT%>";
	document.frmsection.prev_command.value="<%=prevCommand%>";
	document.frmsection.action="section_new.jsp";
	document.frmsection.submit();
}

function cmdBack(){
	document.frmsection.command.value="<%=Command.BACK%>";
	document.frmsection.action="section_new.jsp";
	document.frmsection.submit();
	}

function cmdListFirst(){
	document.frmsection.command.value="<%=Command.FIRST%>";
	document.frmsection.prev_command.value="<%=Command.FIRST%>";
	document.frmsection.action="section_new.jsp";
	document.frmsection.submit();
}

function cmdListPrev(){
	document.frmsection.command.value="<%=Command.PREV%>";
	document.frmsection.prev_command.value="<%=Command.PREV%>";
	document.frmsection.action="section_new.jsp";
	document.frmsection.submit();
	}

function cmdListNext(){
	document.frmsection.command.value="<%=Command.NEXT%>";
	document.frmsection.prev_command.value="<%=Command.NEXT%>";
	document.frmsection.action="section_new.jsp";
	document.frmsection.submit();
}

function cmdListLast(){
	document.frmsection.command.value="<%=Command.LAST%>";
	document.frmsection.prev_command.value="<%=Command.LAST%>";
	document.frmsection.action="section_new.jsp";
	document.frmsection.submit();
}

function cmdAddSectionLink(){
    var data = document.getElementById("select_section").value;
    var result = document.getElementById("section_input").value;
    if(result!=""){
        result = result +","+ data;
    } else {
        result = result + data;
    }
    document.getElementById("section_input").value = result;
}
function cmdValid(){
        window.open("set_all_valid.jsp?master=section", null, "height=550,width=500, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");  
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
<script type="text/javascript">

            function loadList() {
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                        document.getElementById("div_respon").innerHTML = xmlhttp.responseText;
                    }
                };
                xmlhttp.open("GET", "section_ajax.jsp?section_name=0&department_id=0", true);
                xmlhttp.send();
            }
            
            function loadListBySection(section_name) {
                if (section_name.length == 0) { 
                    section_name = "0";
                } 
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                        document.getElementById("div_respon").innerHTML = xmlhttp.responseText;
                    }
                };
                xmlhttp.open("GET", "section_ajax.jsp?section_name="+section_name+"&department_id=0", true);
                xmlhttp.send();
            }
           
            function loadListByDepartment(department_id) {
                document.getElementById("section_name").value="";
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                        document.getElementById("div_respon").innerHTML = xmlhttp.responseText;
                    }
                };
                xmlhttp.open("GET", "section_ajax.jsp?section_name=0&department_id="+department_id, true);
                xmlhttp.send();
            }
            
            function cmdListFirst(start){                
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                        document.getElementById("div_respon").innerHTML = xmlhttp.responseText;
                    }
                };
                xmlhttp.open("GET", "section_ajax.jsp?section_name=0&command=" + <%=Command.FIRST%> + "&start="+start, true);
                xmlhttp.send();
            }

            function cmdListPrev(start){
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                        document.getElementById("div_respon").innerHTML = xmlhttp.responseText;
                    }
                };
                xmlhttp.open("GET", "section_ajax.jsp?section_name=0&command=" + <%=Command.PREV%> + "&start="+start, true);
                xmlhttp.send();
            }

            function cmdListNext(start){
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                        document.getElementById("div_respon").innerHTML = xmlhttp.responseText;
                    }
                };
                xmlhttp.open("GET", "section_ajax.jsp?section_name=0&command=" + <%=Command.NEXT%> + "&start="+start, true);
                xmlhttp.send();
            }

            function cmdListLast(start){
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                        document.getElementById("div_respon").innerHTML = xmlhttp.responseText;
                    }
                };
                xmlhttp.open("GET", "section_ajax.jsp?section_name=0&command=" + <%=Command.LAST%> + "&start="+start, true);
                xmlhttp.send();
            }
        </script>
        <style type="text/css">
            .tblStyle {border-collapse: collapse; font-size: 12px;}
            .tblStyle td {padding: 5px 7px; border: 1px solid #CCC; font-size: 12px; background-color: #FFF;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            .title_page {color:#0db2e1; font-weight: bold; font-size: 14px; background-color: #EEE; border-left: 1px solid #0099FF; padding: 12px 18px;}
            #tbl_form {
                
            }
            #tbl_form td {
                font-size: 12px;
            }
            body {color:#373737;}
            #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3;}
            #menu_title {color:#0099FF; font-size: 14px;}
            #menu_teks {color:#CCC;}
            
            .active {
                background-color: #0b71d0;
                border-bottom: 1px solid #033a6d;
            }
            .title_part {color:#FF7E00; background-color: #F7F7F7; border-left: 1px solid #0099FF; padding: 9px 11px;}
            
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
            
            .btn-small-e {
                font-family: sans-serif;
                text-decoration: none;
                padding: 3px 5px; 
                background-color: #92C8E8; color: #F5F5F5; 
                font-size: 11px; cursor: pointer;
                border-radius: 3px;
            }
            .btn-small-e:hover { background-color: #659FC2; color: #FFF;}
            
            .btn-small-x {
                font-family: sans-serif;
                text-decoration: none;
                padding: 3px 5px; 
                background-color: #EB9898; color: #F5F5F5; 
                font-size: 11px; cursor: pointer;
                border-radius: 3px;
            }
            .btn-small-x:hover { background-color: #D14D4D; color: #FFF;}
            
        </style>
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
        <link rel="stylesheet" href="<%=approot%>/javascripts/datepicker/themes/jquery.ui.all.css">
        <script src="<%=approot%>/javascripts/jquery.js"></script>
        <script src="<%=approot%>/javascripts/datepicker/jquery.ui.core.js"></script>
        <script src="<%=approot%>/javascripts/datepicker/jquery.ui.widget.js"></script>
        <script src="<%=approot%>/javascripts/datepicker/jquery.ui.datepicker.js"></script>
        <script>
        $(function() {
            $( "#datepicker1" ).datepicker({ dateFormat: "yy-mm-dd" });
            $( "#datepicker2" ).datepicker({ dateFormat: "yy-mm-dd" });
        });
        </script>
<!-- #EndEditable -->
</head> 

<body onload="loadList()">
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
            <span id="menu_title">Masterdata <strong style="color:#333;"> / </strong><%=dictionaryD.getWord(I_Dictionary.SECTION)%></span>
        </div>
        <div class="content-main">
            <form name="frmsection" method ="post" action="">
                <input type="hidden" name="command" value="<%=iCommand%>">
                <input type="hidden" name="vectSize" value="<%=vectSize%>">
                <input type="hidden" name="start" value="<%=start%>">
                <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                <input type="hidden" name="hidden_section_id" value="<%=oidSection%>">
                
                <table>
                    
                    <tr>
                        <td style="font-size: 12px; font-weight: bold; padding-top: 7px;">Section Name</td>
                        <td style="font-size: 12px; font-weight: bold; padding-top: 7px;">Department</td>
                    </tr>    
                    <tr>
                        <td><input type="text" style="padding:5px 7px" id="section_name" name="section_name" onkeyup="loadListBySection(this.value)" placeholder="Input Section Name..." size="70" /> </td>
                        <td>
                            <select id="department_select" name="department_select" style="padding:4px 6px" onchange="loadListByDepartment(this.value)">
                                <option value="0">-SELECT-</option>
                                <%
                                Vector listDept = PstDepartment.list(0, 0, "", PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);
                                if (listDept != null && listDept.size()>0){
                                    for(int i=0; i<listDept.size(); i++){
                                        Department deptSelect = (Department)listDept.get(i);
                                        %>
                                        <option value="<%=deptSelect.getOID()%>"><%=deptSelect.getDepartment()%></option>
                                        <%
                                    }
                                }
                                %>
                            </select>
                        </td>
                    </tr>
                </table>
                <div>&nbsp;</div>
                <div style="margin-bottom: 13px;">
                    <a class="btn" style="color:#FFF" href="javascript:cmdValid()">Set Valid Status</a>
                    <% if(privAdd){ %>
                    <a class="btn" style="color:#FFF" href="javascript:cmdAdd()">Tambah Data</a>
                    <% } %>
                </div>
                <div id="div_respon"></div>
                <div>&nbsp;</div>
                <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&( iErrCode>0 ||frmSection.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                <table border="0" cellspacing="2" cellpadding="2">
                    <tr> 
                        <td colspan="2"><div class="title_part"><%=oidSection == 0?"Add ":"Edit "%><%=dictionaryD.getWord(I_Dictionary.SECTION) %></div></td>
                    </tr>
                    <tr>
                        <td colspan="2" class="comment" >*)entry required </td>     
                    </tr>
                    <tr>
                        <td> <%=dictionaryD.getWord(I_Dictionary.SECTION)%> </td>    
                        <td> <input type="text" name="<%=frmSection.fieldNames[FrmSection.FRM_FIELD_SECTION] %>"  value="<%= section.getSection()%>" class="elemenForm" size="30">
                            * <%=frmSection.getErrorMsg(FrmSection.FRM_FIELD_SECTION)%> </td>
                    </tr>
                    <tr>
                       <td><%= dictionaryD.getWord(I_Dictionary.DEPARTMENT) %> </td>
                       <td> 
                           <%
                               Vector deptKey = new Vector(1, 1);
                               Vector deptValue = new Vector(1, 1);
                               /*Vector listDepartment = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                 for(int i =0;i < listDepartment.size();i++){
                                       Department department = (Department)listDepartment.get(i);
                                       deptKey.add(department.getDepartment());
                                       deptValue.add(""+department.getOID());														
                                 }*/
                               Vector listCostDept = PstDepartment.listWithCompanyDiv(0, 0, "");
                               //Vector listDept = PstDepartment.list(0, 0, "", " DEPARTMENT ");
                               String prevCompany = "";
                               String prevDivision = "";
                               for (int i = 0; i < listCostDept.size(); i++) {
                                   Department dept = (Department) listCostDept.get(i);
                                   if (prevCompany.equals(dept.getCompany())) {
                                       if (prevDivision.equals(dept.getDivision())) {
                                           deptKey.add("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + dept.getDepartment());
                                           deptValue.add(String.valueOf(dept.getOID()));
                                       } else {
                                           deptKey.add("&nbsp;-" + dept.getDivision() + "-");
                                           deptValue.add("-2");
                                           deptKey.add("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + dept.getDepartment());
                                           deptValue.add(String.valueOf(dept.getOID()));
                                           prevDivision = dept.getDivision();
                                       }
                                   } else {
                                       deptKey.add("-" + dept.getCompany() + "-");
                                       deptValue.add("-1");
                                       deptKey.add("&nbsp;-" + dept.getDivision() + "-");
                                       deptValue.add("-2");
                                       deptKey.add("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + dept.getDepartment());
                                       deptValue.add(String.valueOf(dept.getOID()));
                                       prevCompany = dept.getCompany();
                                       prevDivision = dept.getDivision();
                                   }
                               }

                           %>
                           <%=ControlCombo.draw(frmSection.fieldNames[FrmSection.FRM_FIELD_DEPARTMENT_ID], "formElemen", null, "" + section.getDepartmentId(), deptValue, deptKey)%> * <%=frmSection.getErrorMsg(FrmSection.FRM_FIELD_DEPARTMENT_ID)%> 
                       </td>
                    </tr>
                    <tr>
                        <td> <%=dictionaryD.getWord(I_Dictionary.DESCRIPTION)%> </td>    
                        <td> <textarea name="<%=frmSection.fieldNames[FrmSection.FRM_FIELD_DESCRIPTION] %>" class="elemenForm" cols="30" rows="3"><%= section.getDescription() %></textarea> </td>
                    </tr>
                    <tr>
                        <td> Add Section Link To </td>
                        <td>
                            <button id="btn" onclick="javascript:cmdAddSectionLink()">Add Section Link</button>
                            <select id="select_section">
                                <option value="0">-select-</option>
                                <%
                                    Vector listSectionLink = new Vector();
                                    listSectionLink = PstSection.list(0, 0, "", "");
                                    if (listSectionLink != null && listSectionLink.size() > 0) {
                                        for (int i = 0; i < listSectionLink.size(); i++) {
                                            Section sec = (Section) listSectionLink.get(i);
                                %>
                                <option value="<%="" + sec.getOID()%>"><%="[" + sec.getOID() + "] " + sec.getSection()%></option>
                                <%
                                        }
                                    }
                                    String sectionData = "";
                                    if (section.getSectionLinkTo() != null && !section.getSectionLinkTo().equals("")) {
                                        //if (sectionInput.equals("")){
                                        sectionData = section.getSectionLinkTo();
                                        //} else {
                                        //sectionData = sectionInput;
                                        //}
                                    } else if (oidSectionTemp == section.getOID()) {
                                        sectionData = sectionInput;
                                    } else {
                                        sectionData = "";
                                    }
                                    sectionInput = "";
                                %>
                            </select><br />
                            <input type="hidden" name="section_temp_id" value="<%=section.getOID()%>" />
                            <input type="hidden" id="section_input" name="section_input" value="<%=sectionData%>" size="70" />
                            <input type="text" size="49"  name="<%=frmSection.fieldNames[FrmSection.FRM_FIELD_SECTION_LINK_TO]%>" value="<%=sectionData%>" size="70" />
                        </td> 
                    </tr>
                    <tr>
                        <td valign="middle">Valid Status</td>
                        <td valign="middle">
                            <select name="<%=frmSection.fieldNames[FrmSection.FRM_FIELD_VALID_STATUS]%>">
                                <%
                                    if (section.getValidStatus() == PstSection.VALID_ACTIVE) {
                                %>
                                <option value="<%=PstSection.VALID_ACTIVE%>" selected="selected">Active</option>
                                <option value="<%=PstSection.VALID_HISTORY%>">History</option>
                                <%
                                } else {
                                %>
                                <option value="<%=PstSection.VALID_ACTIVE%>">Active</option>
                                <option value="<%=PstSection.VALID_HISTORY%>" selected="selected">History</option>
                                <%
                                    }
                                %>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td valign="top">Masa berlaku</td>
                        <td valign="top">
                            <%
                                String DATE_FORMAT_NOW = "yyyy-MM-dd";
                                Date dateStart = section.getValidStart() == null ? new Date() : section.getValidStart();
                                Date dateEnd = section.getValidEnd() == null ? new Date() : section.getValidEnd();
                                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
                                String strValidStart = sdf.format(dateStart);
                                String strValidEnd = sdf.format(dateEnd);
                            %>
                            <input type="text" name="<%=frmSection.fieldNames[frmSection.FRM_FIELD_VALID_START]%>" id="datepicker1" value="<%=strValidStart%>" />&nbsp;to
                            &nbsp;<input type="text" name="<%=frmSection.fieldNames[frmSection.FRM_FIELD_VALID_END]%>" id="datepicker2" value="<%=strValidEnd%>" />
                        </td>
                    </tr>
                    
                    <tr> 
                        <td colspan="2"> 
                            <%
                                ctrLine.setLocationImg(approot + "/images");
                                ctrLine.initDefault();
                                ctrLine.setTableWidth("80%");
                                String scomDel = "javascript:cmdAsk('" + oidSection + "')";
                                String sconDelCom = "javascript:cmdConfirmDelete('" + oidSection + "')";
                                String scancel = "javascript:cmdEdit('" + oidSection + "')";
                                ctrLine.setCommandStyle("buttonlink");
                                ctrLine.setBackCaption("Back to List Section");
                                ctrLine.setSaveCaption("Save Section");
                                ctrLine.setConfirmDelCaption("Yes Delete Section");
                                ctrLine.setDeleteCaption("Delete Section");

                                if (privDelete) {
                                    ctrLine.setConfirmDelCommand(sconDelCom);
                                    ctrLine.setDeleteCommand(scomDel);
                                    ctrLine.setEditCommand(scancel);
                                } else {
                                    ctrLine.setConfirmDelCaption("");
                                    ctrLine.setDeleteCaption("");
                                    ctrLine.setEditCaption("");
                                }

                                if (privAdd == false && privUpdate == false) {
                                    ctrLine.setSaveCaption("");
                                }

                                if (privAdd == false) {
                                    ctrLine.setAddCaption("");
                                }

                                if (iCommand == Command.ASK) {
                                    ctrLine.setDeleteQuestion(msgString);
                                }
                            %>
                            <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%> </td>
                    </tr>
                    
                </table>
                 <% } %>
            </form>
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
<!-- #BeginEditable "script" --> 
<script language="JavaScript">
	//var oBody = document.body;
	//var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
</script>
<!-- #EndEditable --> 
<!-- #EndTemplate --></html>
