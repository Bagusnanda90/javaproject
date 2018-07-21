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
<%@ page import = "com.dimata.harisma.form.employee.appraisal.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.appraisal.*" %>

<%@ include file = "../../main/javainit.jsp" %>

<%-- YANG INI BELUM DIEDIT --%>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK); %>
<%@ include file = "../../main/checkuser.jsp" %>

<!-- Jsp Block -->
<%
        CtrlAppraisalMain ctrlAppraisalMain = new CtrlAppraisalMain(request);
	long strViewForm = FRMQueryString.requestLong(request, "oidAppraisalMain");
	long oidDepartAppraisor = FRMQueryString.requestLong(request, "depart_appraisor");
	int prevCommand = FRMQueryString.requestInt(request, "prev_command");
        
	
//	out.print("oidAppraisalMain : "+oidAppraisalMain);
	int iErrCode = FRMMessage.ERR_NONE;
	String errMsg = "";
	String whereClause = "";
	String orderClause = "";
	int iCommand = FRMQueryString.requestCommand(request);
	int start = FRMQueryString.requestInt(request,"start");

	Vector listAppraisor = new Vector(1,1);
	if(oidDepartAppraisor == 0){
		Vector tempDepart = PstDepartment.list(0,0,PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+ " LIKE '%HUMAN%'","");
		if(tempDepart.size()<1)
			tempDepart = PstDepartment.list(0,0,"","");
			
		Department dept = (Department)tempDepart.get(0);
		oidDepartAppraisor = dept.getOID();
		listAppraisor = PstEmployee.list(0,0,PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+ " = "+oidDepartAppraisor,""); 
	}else
		listAppraisor = PstEmployee.list(0,0,PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+ " = "+oidDepartAppraisor,""); 
	ControlLine ctrLine = new ControlLine();

	iErrCode = ctrlAppraisalMain.action(iCommand , oidAppraisalMain);

	errMsg = ctrlAppraisalMain.getMessage();
	FrmAppraisalMain frmAppraisalMain = ctrlAppraisalMain.getForm();
	AppraisalMain appraisalMain = ctrlAppraisalMain.getAppraisalMain();
	oidAppraisalMain = appraisalMain.getOID();

	if(iCommand==Command.DELETE){
            %>
            <jsp:forward page="empappraisal_list.jsp"> 
            <jsp:param name="prev_command" value="<%=prevCommand%>" />
            <jsp:param name="start" value="<%=start%>" />
            </jsp:forward>
            <%
        }

    long oidEmployee = appraisalMain.getEmployeeId();

    Employee employee = new Employee();
    Employee assessor = new Employee();
    long oidPosition = 0;
    Position position = new Position();
    Position assessorPosition = new Position();
    long oidDepartment = 0;
    Department department = new Department();
    Department depAppraisor = new Department();
    Vector vDataApp = new Vector(1,1);
    if (appraisalMain.getOID() != 0) {
            try{
                    employee = PstEmployee.fetchExc(oidEmployee);
                    assessor = PstEmployee.fetchExc(appraisalMain.getAssesorId());
                    oidPosition = employee.getPositionId();
                    position = PstPosition.fetchExc(oidPosition);
                    assessorPosition = PstPosition.fetchExc(assessor.getPositionId());
                    oidDepartment = employee.getDepartmentId();
                    department = PstDepartment.fetchExc(oidDepartment);
                    depAppraisor = PstDepartment.fetchExc(oidDepartAppraisor);
            }catch(Exception exc){
                    employee = new Employee();
                    position = new Position();
                    department = new Department();
                    depAppraisor = new Department();
            }
            vDataApp.add(employee.getFullName());
            vDataApp.add(position.getPosition());
            vDataApp.add(department.getDepartment());
            vDataApp.add(appraisalMain.getDateAssumedPosition());
            vDataApp.add(appraisalMain.getDateJoinedHotel());
            vDataApp.add(employee.getCommencingDate());
            vDataApp.add(assessor.getFullName());
            vDataApp.add(assessorPosition.getPosition());
            vDataApp.add(appraisalMain.getDateOfAssessment());
            vDataApp.add(appraisalMain.getDateOfLastAssessment());
            vDataApp.add(appraisalMain.getDateOfNextAssessment());
            
    }
    
    String strViewForm = "";
//System.out.println("CURRENT PAGE : "+currPage);
    try{
            AssessmentFormMain appraisalFormMain = PstAssessmentFormMain.fetchExc(oidAssessmentFormMain);
            strViewForm = ControlForm.createFormMain(appraisalFormMain);
        }else{
          //  strViewForm = ControlForm.createPage(oidAssessmentFormMain,currPage);
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
    document.frmAssessmentFormSection.action="appraisalFormMain.jsp";
    document.frmAssessmentFormSection.submit();
}

function cmdNewPage(){
    document.frmAssessmentFormSection.command.value="<%=String.valueOf(Command.GOTO)%>";
    document.frmAssessmentFormSection.action="appraisalFormMain.jsp";
    document.frmAssessmentFormSection.submit();
}

function cmdNewSection(mainOid){
    //alert(mainOid);
    currPageX = document.frmAssessmentFormSection.page.value;
    window.open("<%=approot%>/masterdata/assessment/addSection.jsp?command=<%=String.valueOf(Command.ADD)%>&hidden_ass_form_main_id="+mainOid+"&page="+currPageX, null, "height=350,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
                
}
function cmdEditSection(sectionOid){
    //alert(sectionOid);
    window.open("<%=approot%>/masterdata/assessment/addSection.jsp?command=<%=String.valueOf(Command.EDIT)%>&hidden_ass_form_section_id="+sectionOid, null, "height=350,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       

}
function cmdNewItem(sectionOid){
    //alert(mainOid);
    currPageX = document.frmAssessmentFormSection.page.value;
    window.open("<%=approot%>/masterdata/assessment/addItem.jsp?command=<%=String.valueOf(Command.ADD)%>&hidden_ass_form_section_id="+sectionOid+"&page="+currPageX, null, "height=490,width=700, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
                
}
function cmdEditItem(itemOid){
    //alert(sectionOid);
    window.open("<%=approot%>/masterdata/assessment/addItem.jsp?command=<%=String.valueOf(Command.EDIT)%>&hidden_ass_form_item_id="+itemOid, null, "height=350,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
                
}


function cmdBack(){
	document.frmAssessmentFormSection.command.value="<%=String.valueOf(Command.BACK)%>";
	document.frmAssessmentFormSection.action="appraisalFormMain.jsp";
	document.frmAssessmentFormSection.submit();
	}

function cmdListFirst(){
	document.frmAssessmentFormSection.command.value="<%=String.valueOf(Command.FIRST)%>";
	document.frmAssessmentFormSection.action="appraisalFormMain.jsp";
	document.frmAssessmentFormSection.submit();
}

function cmdListPrev(){
	document.frmAssessmentFormSection.command.value="<%=String.valueOf(Command.PREV)%>";
	document.frmAssessmentFormSection.action="appraisalFormMain.jsp";
	document.frmAssessmentFormSection.submit();
	}

function cmdListNext(){
        //alert("NEXT");
	document.frmAssessmentFormSection.command.value="<%=String.valueOf(Command.NEXT)%>";
	document.frmAssessmentFormSection.action="appraisalFormMain.jsp";
	document.frmAssessmentFormSection.submit();
}

function cmdListLast(){
	document.frmAssessmentFormSection.command.value="<%=String.valueOf(Command.LAST)%>";
	document.frmAssessmentFormSection.action="appraisalFormMain.jsp";
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
                  Master Data &gt; Assessment &gt; Form Creator<!-- #EndEditable -->
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
                                    <input type="hidden" name="command" value="<%=String.valueOf((iCommand==Command.GOTO?Command.VIEW:iCommand))%>">
                                    <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
                                    <input type="hidden" name="hidden_ass_form_main_id" value="<%=String.valueOf(oidAssessmentFormMain)%>">  
                                    <input type="hidden" name="page" value="<%=String.valueOf(currPage)%>">  
                                    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                        <tr>
                                            <td>
                                                <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
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
                                                                    out.println("<span class='pagelink'><a href='javascript:cmdListFirst()'>&#124;&lt;</a></span>");
                                                                    out.println("<span class='pagelink'><a href='javascript:cmdListPrev()'>&lt;&lt;</a></span>");
                                                                }
                                                                    
                                                                for(int i=start;i<(vectSize>(recordToGet+start)?recordToGet+start:vectSize+1);i++){
                                                                    int iPage = i;
                                                                    
                                                                    if(currPage==iPage){
                                                                        out.println("<span class='pageSelected'>["+(iPage+1)+"]</span>");
                                                                    }else{
                                                                        out.println("<span class='pagelink'><a href='javascript:cmdGoToPage("+iPage+")'>["+(iPage+1)+"]</a></span>");
                                                                    }
                                                                }
                                                                if(iCommand==Command.GOTO){
                                                                    out.println("<span class='pageSelected'>["+(vectSize+2)+"]</span>");
                                                                }
                                                                if(start+recordToGet<vectSize){
                                                                   // out.println("<a href='javascript:cmdListNext()'>[&gt;&gt;]</a>");
                                                                   // out.println("<a href='javascript:cmdListLast()'>[&gt;&#124;]</a>");
                                                                    out.println("<span class='pagelink'><a href='javascript:cmdListNext()'>&gt;&gt;</a></span>");
                                                                    out.println("<span class='pagelink'><a href='javascript:cmdListLast()'>&gt;&#124;</a></span>");
                                                                } 
                                                            %>
                                                        <!--</div>-->
                                                        </td>
                                                        <td width="20%" align="right"><!--Tempat menu-->
                                                            <a href="javascript:cmdNewPage()">[New Page]</a>
                                                            <%if(currPage>0){%>
                                                                <a href="javascript:cmdNewSection('<%=String.valueOf(oidAssessmentFormMain)%>')">[New Section]</a>
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
