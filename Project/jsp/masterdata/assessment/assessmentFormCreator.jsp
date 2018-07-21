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
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_PERFORMANCE_APPRAISAL, AppObjInfo.OBJ_FORM_CREATOR); %>
<%@ include file = "../../main/checkuser.jsp" %>

<!-- Jsp Block -->
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
long oidAssessmentFormMain = FRMQueryString.requestLong(request, "hidden_ass_form_main_id");
int currPage = FRMQueryString.requestInt(request, "page");

CtrlAssessmentFormSection ctrlAssessmentFormSection = new CtrlAssessmentFormSection(request);
String strViewForm="";

int recordToGet = 10;


/*count list All Page*/
int vectSize = SessAssessmentFormItem.getMaxPage(oidAssessmentFormMain);
//System.out.println("MAX PAGE :::: "+vectSize);
if(iCommand==Command.GOTO){
        start = ctrlAssessmentFormSection.actionList(Command.LAST, start, vectSize, recordToGet);
        currPage = SessAssessmentFormItem.getMaxPage(oidAssessmentFormMain)+1;
}
if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
    //System.out.println("AWAL START : "+start);
		start = ctrlAssessmentFormSection.actionList(iCommand, start, vectSize, recordToGet);
                iCommand = Command.VIEW;
                currPage = start;
    //System.out.println("AKHIR START : "+start);
 }

if(start<0){
    start = 0;
}
//System.out.println("CURRENT PAGE : "+currPage);
if(iCommand==Command.VIEW){
    try{
        if(!(currPage>0)){
            AssessmentFormMain assessmentFormCreator = PstAssessmentFormMain.fetchExc(oidAssessmentFormMain);
            strViewForm = ControlForm.createFormMain(assessmentFormCreator);
        }else{
            strViewForm = ControlForm.createPage(oidAssessmentFormMain,currPage);
        }
    }catch(Exception ex){}
}



%>

<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Master Data Division</title>
<script language="JavaScript">

function cmdGoToPage(page){
    //alert(page);
    document.frmAssessmentFormSection.page.value=page;
    document.frmAssessmentFormSection.command.value="<%=String.valueOf(Command.VIEW)%>";
    document.frmAssessmentFormSection.action="assessmentFormCreator.jsp";
    document.frmAssessmentFormSection.submit();
}

function cmdNewPage(){
    document.frmAssessmentFormSection.command.value="<%=String.valueOf(Command.GOTO)%>";
    document.frmAssessmentFormSection.action="assessmentFormCreator.jsp";
    document.frmAssessmentFormSection.submit();
}

function cmdNewSection(mainOid){
    //alert(mainOid);
    currPageX = document.frmAssessmentFormSection.page.value;
     var newWindow= window.open("<%=approot%>/masterdata/assessment/addSection.jsp?command=<%=String.valueOf(Command.ADD)%>&hidden_ass_form_main_id="+mainOid+"&page="+currPageX, "AssesmentEditSection", "height=600,width=700, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
      newWindow.focus();          
}
function cmdEditSection(sectionOid){
    //alert(sectionOid);
    var newWindow= window.open("<%=approot%>/masterdata/assessment/addSection.jsp?command=<%=String.valueOf(Command.EDIT)%>&hidden_ass_form_section_id="+sectionOid, "AssesmentEditSection", "height=600,width=700, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
    newWindow.focus();
}
function cmdNewItem(sectionOid){
    //alert(mainOid);
    currPageX = document.frmAssessmentFormSection.page.value;
     var newWindow= window.open("<%=approot%>/masterdata/assessment/addItem.jsp?command=<%=String.valueOf(Command.ADD)%>&hidden_ass_form_section_id="+sectionOid+"&page="+currPageX, "AssesmentEditItem", "height=600,width=700, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
    newWindow.focus();            
}
function cmdEditItem(itemOid, sectionOid){
    //alert(sectionOid);
    var newWindow=window.open("<%=approot%>/masterdata/assessment/addItem.jsp?command=<%=String.valueOf(Command.EDIT)%>&hidden_ass_form_item_id="+itemOid,  "AssesmentEditItem", "height=600,width=700, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
     newWindow.focus();           
}


function cmdBack(){
	document.frmAssessmentFormSection.command.value="<%=String.valueOf(Command.BACK)%>";
	document.frmAssessmentFormSection.action="assessmentFormCreator.jsp";
	document.frmAssessmentFormSection.submit();
	}

function cmdListFirst(){
	document.frmAssessmentFormSection.command.value="<%=String.valueOf(Command.FIRST)%>";
	document.frmAssessmentFormSection.action="assessmentFormCreator.jsp";
	document.frmAssessmentFormSection.submit();
}

function cmdListPrev(){
	document.frmAssessmentFormSection.command.value="<%=String.valueOf(Command.PREV)%>";
	document.frmAssessmentFormSection.action="assessmentFormCreator.jsp";
	document.frmAssessmentFormSection.submit();
	}

function cmdListNext(){
        //alert("NEXT");
	document.frmAssessmentFormSection.command.value="<%=String.valueOf(Command.NEXT)%>";
	document.frmAssessmentFormSection.action="assessmentFormCreator.jsp";
	document.frmAssessmentFormSection.submit();
}

function cmdListLast(){
	document.frmAssessmentFormSection.command.value="<%=String.valueOf(Command.LAST)%>";
	document.frmAssessmentFormSection.action="assessmentFormCreator.jsp";
	document.frmAssessmentFormSection.submit();
}

function createSection(){
    
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
<link rel="stylesheet" href="<%=approot%>/styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="<%=approot%>/styles/tab.css" type="text/css">
<link rel="stylesheet" href="<%=approot%>/styles/form.css" type="text/css">
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
                  Master Data &gt; Assessment &gt; Form Creator<!-- #EndEditable -->
            </strong></font>
	      </td>
        </tr>
        <tr> 
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td style="background-color:<%=bgColorContent%>; "> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                    <tr> 
                      <td valign="top"> 
                        <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr> 
                            <td valign="top">
                                <!-- #BeginEditable "content" -->
                                <form name="frmAssessmentFormSection" method ="post" action="">
                                    <input type="hidden" name="command" value="<%=String.valueOf((iCommand==Command.GOTO?Command.VIEW:iCommand))%>">
                                    <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
                                    <input type="hidden" name="hidden_ass_form_main_id" value="<%=String.valueOf(oidAssessmentFormMain)%>">  
                                    <input type="hidden" name="page" value="<%=String.valueOf(currPage)%>">  
                                    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                        <tr>
                                            <td>
                                                <table width="100%" border="0" cellspacing="1" cellpadding="1">
                                                    <tr>
                                                        <td width="80%" align="left"><!--Tempat menu-->
                                                            <!--<div class="page">-->
                                                            PAGE : 
                                                            <%
                                                       /*         if(currPage==0){
                                                                    out.println("<span class='pageSelected'>[1]</span>");
                                                                }else{
                                                                    out.println("<span class='pagelink'><a href='javascript:cmdGoToPage(0)'>[1]</a></span>");
                                                                } */
                                                            
                                                            
                                                                if(start>=recordToGet){
                                                                    //out.println("<a href='javascript:cmdListFirst()'>[&#124;&lt;]</a>");
                                                                    //out.println("<a href='javascript:cmdListPrev()'>[&lt;&lt;]</a>");
                                                                    out.println("<span class='pagelink2'><a href='javascript:cmdListFirst()'>&#124;&lt;</a></span>");
                                                                    out.println("<span class='pagelink2'><a href='javascript:cmdListPrev()'>&lt;&lt;</a></span>");
                                                                }
                                                                    
                                                                for(int i=start;i<(vectSize>(recordToGet+start)?recordToGet+start:vectSize+1);i++){
                                                                    int iPage = i;
                                                                    
                                                                    if(currPage==iPage){
                                                                        out.println("<span class='pageSelected2'>["+(iPage+1)+"]</span>");
                                                                    }else{
                                                                        out.println("<span class='pagelink2'><a href='javascript:cmdGoToPage("+iPage+")'>["+(iPage+1)+"]</a></span>");
                                                                    }
                                                                }
                                                                if(iCommand==Command.GOTO){
                                                                    out.println("<span class='pageSelected2'>["+(vectSize+2)+"]</span>");
                                                                }
                                                                if(start+recordToGet<vectSize){
                                                                   // out.println("<a href='javascript:cmdListNext()'>[&gt;&gt;]</a>");
                                                                   // out.println("<a href='javascript:cmdListLast()'>[&gt;&#124;]</a>");
                                                                    out.println("<span class='pagelink2'><a href='javascript:cmdListNext()'>&gt;&gt;</a></span>");
                                                                    out.println("<span class='pagelink2'><a href='javascript:cmdListLast()'>&gt;&#124;</a></span>");
                                                                } 
                                                            %>
                                                        <!--</div>-->
                                                        </td>
                                                        <td width="20%" align="right"><!--Tempat menu-->
                                                            <span class='pagelink2'><a href="javascript:cmdNewPage()">[New Page]</a></span>
                                                            <%if(currPage>0){%>
                                                                <span class='pagelink2'><a href="javascript:cmdNewSection('<%=String.valueOf(oidAssessmentFormMain)%>')">[New Section]</a></span>
                                                            <%}%>
                                                        </td>
                                                    <tr>
                                                </table>
                                            </td>
                                        <tr>
                                    </table>
                                <%
                                if(iCommand==Command.VIEW && strViewForm.length()>0){
                                %>    
                                    <%=strViewForm%>
                                <%}%>
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
