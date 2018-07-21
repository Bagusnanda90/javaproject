<%-- 
    Document   : grade_level.jsp
    Created on : Juni 3, 2014, 2:40:58 PM
    Author     : Satrya Ramayu
--%>

<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.clinic.*" %>

<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMPLOYEE, AppObjInfo.OBJ_EMPLOYEE_GRADE_LEVEL); %>
<%@ include file = "../main/checkuser.jsp" %>

<!-- Jsp Block -->
<%!

	public String drawList(Vector objectClass ,  long levelId)
	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
                
		ctrlist.addHeader("Grade","");
		
                             
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;

		for (int i = 0; i < objectClass.size(); i++) {
			GradeLevel gradeLevel = (GradeLevel)objectClass.get(i);
			 Vector rowx = new Vector();
			 if(levelId == gradeLevel.getOID())
				 index = i;
			rowx.add(gradeLevel.getCodeLevel());
			lstData.add(rowx);
			lstLinkData.add(String.valueOf(gradeLevel.getOID()));
		}

		return ctrlist.draw(index);
		
	}

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidGradeLevelId = FRMQueryString.requestLong(request, "hidden_grade_level_id");
String source = FRMQueryString.requestString(request, "source"); 
/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = PstGradeLevel.fieldNames[PstGradeLevel.FLD_GRADE_CODE];

CtrlGradeLevel ctrlGradeLevel = new CtrlGradeLevel(request);
ControlLine ctrLine = new ControlLine();
Vector listGradeLevel = new Vector(1,1); 

/*switch statement */
iErrCode = ctrlGradeLevel.action(iCommand , oidGradeLevelId);
/* end switch*/
FrmGradeLevel frmGradeLevel = ctrlGradeLevel.getForm();

/*count list All GradeLevel*/
int vectSize = PstGradeLevel.getCount(whereClause);

GradeLevel gradeLevel = ctrlGradeLevel.getLevel();
msgString =  ctrlGradeLevel.getMessage();

/*switch list GradeLevel*/
if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)){
	//start = PstGradeLevel.findLimitStart(gradeLevel.getOID(),recordToGet, whereClause, orderClause);
	oidGradeLevelId = gradeLevel.getOID();
}

if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlGradeLevel.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

/* get record to display */
listGradeLevel = PstGradeLevel.list(start,recordToGet, whereClause , orderClause);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listGradeLevel.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listGradeLevel = PstGradeLevel.list(start,recordToGet, whereClause , orderClause);
}
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Master Data Grade Level</title>
<script language="JavaScript">


function cmdAdd(){
	document.frmGradeLevel.hidden_grade_level_id.value="0";
	document.frmGradeLevel.command.value="<%=Command.ADD%>";
	document.frmGradeLevel.prev_command.value="<%=prevCommand%>";
	document.frmGradeLevel.action="grade_level.jsp";
	document.frmGradeLevel.submit();
}

function cmdAsk(oidGradeLevelId){
	document.frmGradeLevel.hidden_grade_level_id.value=oidGradeLevelId;
	document.frmGradeLevel.command.value="<%=Command.ASK%>";
	document.frmGradeLevel.prev_command.value="<%=prevCommand%>";
	document.frmGradeLevel.action="grade_level.jsp";
	document.frmGradeLevel.submit();
}

function cmdConfirmDelete(oidGradeLevelId){
	document.frmGradeLevel.hidden_grade_level_id.value=oidGradeLevelId;
	document.frmGradeLevel.command.value="<%=Command.DELETE%>";
	document.frmGradeLevel.prev_command.value="<%=prevCommand%>";
	document.frmGradeLevel.action="grade_level.jsp";
	document.frmGradeLevel.submit();
}
function cmdSave(){
	document.frmGradeLevel.command.value="<%=Command.SAVE%>";
	document.frmGradeLevel.prev_command.value="<%=prevCommand%>";
	document.frmGradeLevel.action="grade_level.jsp";
	document.frmGradeLevel.submit();
	}

function cmdEdit(oidGradeLevelId){
	document.frmGradeLevel.hidden_grade_level_id.value=oidGradeLevelId;
	document.frmGradeLevel.command.value="<%=Command.EDIT%>";
	document.frmGradeLevel.prev_command.value="<%=prevCommand%>";
	document.frmGradeLevel.action="grade_level.jsp";
	document.frmGradeLevel.submit();
	}

function cmdCancel(oidGradeLevelId){
	document.frmGradeLevel.hidden_grade_level_id.value=oidGradeLevelId;
	document.frmGradeLevel.command.value="<%=Command.EDIT%>";
	document.frmGradeLevel.prev_command.value="<%=prevCommand%>";
	document.frmGradeLevel.action="grade_level.jsp";
	document.frmGradeLevel.submit();
}

function cmdBack(){
	document.frmGradeLevel.command.value="<%=Command.BACK%>";
	document.frmGradeLevel.action="grade_level.jsp";
	document.frmGradeLevel.submit();
	}

function cmdListFirst(){
	document.frmGradeLevel.command.value="<%=Command.FIRST%>";
	document.frmGradeLevel.prev_command.value="<%=Command.FIRST%>";
	document.frmGradeLevel.action="grade_level.jsp";
	document.frmGradeLevel.submit();
}

function cmdListPrev(){
	document.frmGradeLevel.command.value="<%=Command.PREV%>";
	document.frmGradeLevel.prev_command.value="<%=Command.PREV%>";
	document.frmGradeLevel.action="grade_level.jsp";
	document.frmGradeLevel.submit();
	}

function cmdListNext(){
	document.frmGradeLevel.command.value="<%=Command.NEXT%>";
	document.frmGradeLevel.prev_command.value="<%=Command.NEXT%>";
	document.frmGradeLevel.action="grade_level.jsp";
	document.frmGradeLevel.submit();
}

function cmdListLast(){
	document.frmGradeLevel.command.value="<%=Command.LAST%>";
	document.frmGradeLevel.prev_command.value="<%=Command.LAST%>";
	document.frmGradeLevel.action="grade_level.jsp";
	document.frmGradeLevel.submit();
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
           function loadList(grade_name) {
                if (grade_name.length == 0) { 
                    grade_name = "0";
                } 
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                        document.getElementById("div_respon").innerHTML = xmlhttp.responseText;
                    }
                };
                xmlhttp.open("GET", "grade_level_ajax.jsp?grade_name=" + grade_name, true);
                xmlhttp.send();
            }
            
            function prepare(){
                loadList("0");
            }
            
            function cmdListFirst(start){                
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                        document.getElementById("div_respon").innerHTML = xmlhttp.responseText;
                    }
                };
                xmlhttp.open("GET", "grade_level_ajax.jsp?grade_name=0&command=" + <%=Command.FIRST%> + "&start="+start, true);
                xmlhttp.send();
            }

            function cmdListPrev(start){
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                        document.getElementById("div_respon").innerHTML = xmlhttp.responseText;
                    }
                };
                xmlhttp.open("GET", "grade_level_ajax.jsp?grade_name=0&command=" + <%=Command.PREV%> + "&start="+start, true);
                xmlhttp.send();
            }

            function cmdListNext(start){
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                        document.getElementById("div_respon").innerHTML = xmlhttp.responseText;
                    }
                };
                xmlhttp.open("GET", "grade_level_ajax.jsp?grade_name=0&command=" + <%=Command.NEXT%> + "&start="+start, true);
                xmlhttp.send();
            }

            function cmdListLast(start){
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                        document.getElementById("div_respon").innerHTML = xmlhttp.responseText;
                    }
                };
                xmlhttp.open("GET", "grade_level_ajax.jsp?grade_name=0&command=" + <%=Command.LAST%> + "&start="+start, true);
                xmlhttp.send();
            }
        </script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 

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
        <style type="text/css">
            .tblStyle {border-collapse: collapse; font-size: 12px;}
            .tblStyle td {padding: 7px 9px; border: 1px solid #CCC; font-size: 12px; background-color: #FFF;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            .title_page {color:#0db2e1; font-weight: bold; font-size: 14px; background-color: #EEE; border-left: 1px solid #0099FF; padding: 12px 18px;}

            body {color:#373737;}
            #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3;}
            #menu_title {color:#0099FF; font-size: 14px;}
            #menu_teks {color:#CCC;}
            
            #btn {
              background: #3498db;
              border: 1px solid #0066CC;
              border-radius: 3px;
              font-family: Arial;
              color: #ffffff;
              font-size: 12px;
              padding: 3px 9px 3px 9px;
            }

            #btn:hover {
              background: #3cb0fd;
              border: 1px solid #3498db;
            }
            .breadcrumb {
                background-color: #EEE;
                color:#0099FF;
                padding: 7px 9px;
            }
            .navbar {
                font-family: sans-serif;
                font-size: 12px;
                background-color: #0084ff;
                padding: 7px 9px;
                color : #FFF;
                border-top:1px solid #0084ff;
                border-bottom: 1px solid #0084ff;
            }
            .navbar ul {
                list-style-type: none;
                margin: 0;
                padding: 0;
            }

            .navbar li {
                padding: 7px 9px;
                display: inline;
                cursor: pointer;
            }
            
            .navbar li a {
                color : #F5F5F5;
                text-decoration: none;
            }
            
            .navbar li a:hover {
                color:#FFF;
            }
            
            .navbar li a:active {
                color:#FFF;
            }

            .navbar li:hover {
                background-color: #0b71d0;
                border-bottom: 1px solid #033a6d;
            }

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
            
        </style>
<!-- #EndEditable -->
</head> 

<body onload="prepare()">
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
            <span id="menu_title">Masterdata <strong style="color:#333;"> / </strong>Grade Level</span>
        </div>
        <div class="content-main">
            <form name="frmGradeLevel" method ="post" action="">
                <input type="hidden" name="command" value="<%=iCommand%>">
                <input type="hidden" name="vectSize" value="<%=vectSize%>">
                <input type="hidden" name="start" value="<%=start%>">
                <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                <input type="hidden" name="hidden_grade_level_id" value="<%=oidGradeLevelId%>">
                <input type="hidden" name="source" value="">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr align="left" valign="top"> 
                    <td height="8"  colspan="3"> 
                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr align="left" valign="top"> 
                          <td height="14" valign="middle" colspan="3" class="listtitle">
                            <input type="text" style="padding:6px 7px" name="grade_name" onkeyup="loadList(this.value)" placeholder="Searching..." size="70" /> 
                            <div>&nbsp;</div>
                            <div id="div_respon"></div>
                            <div>&nbsp;</div>
                          </td>
                        </tr>

                        
                        <%
                        if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmGradeLevel.errorSize()<1)){
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
                                <td height="22" valign="middle" colspan="3" width="951"><a href="javascript:cmdAdd()" class="command">Add 
                                  New GradeLevel</a> </td>
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
                    <td>&nbsp; </td>
                  </tr>
                  <tr align="left" valign="top"> 
                    <td height="8" valign="middle" colspan="3"> 
                      <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmGradeLevel.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                      <table width="100%" border="0" cellspacing="2" cellpadding="2">
                        <tr> 
                          <td colspan="2" class="listtitle"><%=oidGradeLevelId == 0 ?"Add":"Edit"%> Employee 
                            GradeLevel </td>
                        </tr>
                        <tr> 
                          <td height="100%"> 
                            <table border="0" cellspacing="2" cellpadding="2" width="50%">
                              <tr align="left" valign="top"> 
                                <td valign="top" width="21%">Grade Level 
                                </td>
                                <td width="79%"> 
                                  <input type="text" name="<%=frmGradeLevel.fieldNames[FrmGradeLevel.FRM_FIELD_GRADE_CODE] %>"  value="<%= gradeLevel.getCodeLevel() %>" class="elemenForm" size="30">
                                  * <%=frmGradeLevel.getErrorMsg(FrmGradeLevel.FRM_FIELD_GRADE_CODE)%></td>
                              </tr>

                            </table>
                          </td>
                        </tr>
                        <tr align="left" valign="top" > 
                          <td colspan="2" class="command"> 
                            <%
                                                  ctrLine.setLocationImg(approot+"/images");
                                                  ctrLine.initDefault();
                                                  ctrLine.setTableWidth("80%");
                                                  String scomDel = "javascript:cmdAsk('"+oidGradeLevelId+"')";
                                                  String sconDelCom = "javascript:cmdConfirmDelete('"+oidGradeLevelId+"')";
                                                  String scancel = "javascript:cmdEdit('"+oidGradeLevelId+"')";
                                                  ctrLine.setBackCaption("Back to List GradeLevel");
                                                  ctrLine.setCommandStyle("buttonlink");
                                                  ctrLine.setAddCaption("Add GradeLevel");
                                                  ctrLine.setSaveCaption("Save GradeLevel");
                                                  ctrLine.setDeleteCaption("Delete GradeLevel");
                                                  ctrLine.setConfirmDelCaption("Yes Delete GradeLevel");

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
                        <tr align="left" valign="top" > 
                          <td colspan="3"> 
                            <div align="left"></div>
                          </td>
                        </tr>
                      </table>
                      <%}%>
                    </td>
                  </tr>
                </table>
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
