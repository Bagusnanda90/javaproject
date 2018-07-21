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
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ include file = "../../main/javainit.jsp" %>

<%-- YANG INI BELUM DIEDIT --%>
<% int  appObjCode = 0;//AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_TRAINING, AppObjInfo.OBJ_TRAINING_HISTORY); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
  privAdd = true;
  privDelete = true;
  privUpdate = true;
  privPrint = true;
%>

<!-- Jsp Block -->
<%!

	public String drawList(Vector objectClass ,  long empWarningId)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("60%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("NO.","10%");
		ctrlist.addHeader("BREAK DATE","30%");
		ctrlist.addHeader("BREAK FACT","10%");
		ctrlist.addHeader("WARNING DATE","10%");
		ctrlist.addHeader("WARNING BY","20%");
                ctrlist.addHeader("VALID UNTIL","20%");
                ctrlist.addHeader("WARNING lEVEL","20%");
                ctrlist.addHeader("WARNING POINT","20%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;
                int recordNo = 1;

		for (int i = 0; i < objectClass.size(); i++) {
			EmpWarning empWarning = (EmpWarning)objectClass.get(i);
			 Vector rowx = new Vector();
			 if(empWarningId == empWarning.getOID())
				 index = i;

			Warning warning = new Warning();
			if(empWarning.getWarnLevel() != -1){
				try{
					warning = PstWarning.fetchExc(empWarning.getWarnLevel());
				}catch(Exception exc){
					warning = new Warning();
				}
			}

			rowx.add(String.valueOf(recordNo++));
                        rowx.add(Formater.formatDate(empWarning.getBreakDate(), "MMM d, yyyy"));
                        rowx.add(empWarning.getBreakFact());
                        rowx.add(Formater.formatDate(empWarning.getWarningDate(), "MMM d, yyyy"));
                        rowx.add(empWarning.getWarningBy());
                        rowx.add(Formater.formatDate(empWarning.getValidityDate(), "MMM d, yyyy"));
                        rowx.add(warning.getWarnDesc());
                        rowx.add(warning.getWarnPoint());
                        lstData.add(rowx);
			lstLinkData.add(String.valueOf(empWarning.getOID()));

		}

		return ctrlist.draw(index);
	}

%>


<%	
    int iCommand = FRMQueryString.requestCommand(request);
    int prevCommand = FRMQueryString.requestInt(request,"prev_command");
    long oidEmployee = FRMQueryString.requestLong(request,"employee_oid");
    long oidWarning = FRMQueryString.requestLong(request, "warning_id");
    int start = FRMQueryString.requestInt(request, "start");
        
    String[] listTitles =
    {
        "NO",
        "BREAK DATE",
        "BREAK FACT",
        "WARNING DATE",
        "WARNING BY",
        "VALID UNTIL",
        "WARNING_LEVEL"
    };
    
    int recordToGet = 10;
    int iErrCode = FRMMessage.ERR_NONE;
    String errMsg = "";
    String whereClause = PstEmpWarning.fieldNames[PstEmpWarning.FLD_EMPLOYEE_ID] + "=" + oidEmployee;
    String orderClause = PstEmpWarning.fieldNames[PstEmpWarning.FLD_BREAK_DATE];
    
    int defaultWarnLevel = 0;
    
    Employee employee = new Employee();
    Department department = new Department();
    Section section = new Section();

    // GET EMPLOYEE DATA TO DISPLAY
    try {
        employee = PstEmployee.fetchExc(oidEmployee);
        
        long oidDepartment = employee.getDepartmentId();
        department = PstDepartment.fetchExc(oidDepartment);
        
        long oidSection = employee.getSectionId();
        section = PstSection.fetchExc(oidSection);
    }
    catch(Exception e) {
        employee = new Employee();
        department = new Department();
        section = new Section();
    }
        
    CtrlEmpWarning ctrlWarning = new CtrlEmpWarning(request);
    ControlLine ctrLine = new ControlLine();
    Vector listWarning = new Vector(1,1);
    
    /* EXECUTE ACTION COMMAND */
    iErrCode = ctrlWarning.action(iCommand, oidWarning);    
    errMsg = ctrlWarning.getMessage();
    
    EmpWarning empWarning = ctrlWarning.getEmpWarning();
    FrmEmpWarning frmEmpWarning = ctrlWarning.getForm();
    EmpWarning warning = ctrlWarning.getEmpWarning();
    
    int vectSize = PstEmpWarning.getCount(whereClause);
    
   
    /* CASE NAVIGATION COMMAND */
    if((iCommand == Command.FIRST || iCommand == Command.PREV )||
       (iCommand == Command.NEXT || iCommand == Command.LAST)){
        
        start = ctrlWarning.actionList(iCommand, start, vectSize, recordToGet);
    } 
    
    
    /* GET WARNING DATA TO DISPLAY */
    listWarning = PstEmpWarning.list(start, recordToGet, whereClause, orderClause);
    
    
    // design vector that handle data to store in session
    Vector warningReport = new Vector();

    warningReport.add(warning);
    warningReport.add(employee);

    session.putValue("LETTER_OF_WARNING", warningReport);	


%>
<!-- End of Jsp Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Warning</title>
<script language="JavaScript">
    
    function cmdBackToList(){
        document.fredit.start.value="0";
        document.fredit.command.value="<%=Command.BACK%>"; 
        document.fredit.action="../warning/warning_list.jsp";		
        document.fredit.submit();
    }
    
    function cmdBackEmp(oid){
	document.fredit.employee_oid.value=oid;
	document.fredit.command.value="<%=Command.EDIT%>";	
	document.fredit.action="employee_edit.jsp";
	document.fredit.submit();
    }
    
    function cmdBack(){
	document.fredit.command.value="<%=Command.BACK%>";	
	document.fredit.action="warning.jsp";
	document.fredit.submit();
    }

    function cmdAdd(){	
        document.fredit.warning_id.value="0";
        document.fredit.command.value="<%=Command.ADD%>";
        document.fredit.action="warning.jsp";
        document.fredit.submit(); 
    } 
    
    function cmdEdit(oid){
        document.fredit.warning_id.value=oid;
        document.fredit.command.value="<%=Command.EDIT%>";
        document.fredit.action="warning.jsp";
        document.fredit.submit(); 
    } 
    
    function cmdSave(){
        document.fredit.warning_id.value="<%= oidWarning %>";
	document.fredit.command.value="<%=Command.SAVE%>";
	document.fredit.prev_command.value="<%=iCommand%>";    /* edit atau add */
	document.fredit.action="warning.jsp";
	document.fredit.submit();
    }
    
    function cmdCancel(){
        document.fredit.command.value="<%=Command.CANCEL%>";
        document.fredit.action="warning.jsp";
        document.fredit.submit();
    } 

    function cmdAsk(oid){
        document.fredit.command.value="<%=Command.ASK%>"; 
        document.fredit.action="warning.jsp";
        document.fredit.submit();
    } 

    function cmdDelete(oidWarning){
        document.fredit.command.value="<%=Command.DELETE%>";
        document.fredit.action="warning.jsp"; 
        document.fredit.submit();
    }  
    
    function cmdPrint(){
	var rptSource = "<%=printroot%>.report.employee.EmployeeWarningPdf";
	window.open(rptSource);
    }


    function cmdListFirst(){
        document.fredit.command.value="<%=Command.FIRST%>";
        document.fredit.action="warning.jsp";
        document.fredit.submit();
    }

    function cmdListPrev(){
        document.fredit.command.value="<%=Command.PREV%>";
        document.fredit.action="warning.jsp";
        document.fredit.submit();
    }

    function cmdListNext(){
        document.fredit.command.value="<%=Command.NEXT%>";
        document.fredit.action="warning.jsp";
        document.fredit.submit();
    }

    function cmdListLast(){
        document.fredit.command.value="<%=Command.LAST%>";
        document.fredit.action="warning.jsp";
        document.fredit.submit();
    }
    
    function cmdListEmployee(){
        document.fredit.command.value="<%=iCommand%>";
        win = window.open("<%=approot%>/employee/warning/src_emp.jsp?formName=fredit&empPathId=<%= FrmEmpWarning.fieldNames[FrmEmpWarning.FRM_FIELD_EMPLOYEE_ID]%>", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");
      
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
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<!-- #EndEditable --> 
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg','<%=approot%>/images/BtnCancelOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../../main/header.jsp" %>
      <!-- #EndEditable --> </td>
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Employee 
                  &gt; Warning &gt; Warning History<!-- #EndEditable --> </strong></font> 
                </td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr ><td  height="26">
                          <br/>
                          <table width="98%" align="center" height="26" border="0" bgcolor="#FFFFFF"  cellspacing="2" cellpadding="2" >
                              <tr>
                              
                              <%-- TAB MENU --%>
                                  <td width="11%" nowrap bgcolor="#0066CC"> 
                                      <div align="center" class="tablink">
                                      <a href="javascript:cmdBackEmp('<%=oidEmployee%>')" class="tablink">Personal Data</a>
                                      </div>
                                  </td>
                                  <td width="11%" nowrap bgcolor="#0066CC"> 
                                      <div align="center"  class="tablink">
                                      <a href="familymember.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.FAMILY_MEMBER) %></a>
                                      </div>
                                  </td>
                                  <td width="7%" nowrap bgcolor="#0066CC"> 
                                      <div align="center"  class="tablink">
                                      <a href="emplanguage.jsp?employee_oid=<%=oidEmployee%>" class="tablink">Language</a>
                                      </div>
                                  </td>
                                  <td width="8%" nowrap bgcolor="#0066CC"> 
                                      <div align="center"  class="tablink">
                                      <a href="empeducation.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.WARNING) %></a>
                                      </div>
                                  </td>                                          
                                  <td width="8%" nowrap bgcolor="#0066CC"> 
                                      <div align="center"  class="tablink">
                                      <a href="experience.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.EXPERIENCE) %></a>
                                      </div>
                                  </td>                                  
                                  <td width="9%" nowrap bgcolor="#0066CC"> 
                                      <div align="center"  class="tablink">
                                      <a href="careerpath.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.CAREER_PATH) %></a>
                                      </div>
                                  </td> 
                                  <td width="7%" nowrap bgcolor="#0066CC"> 
                                      <div align="center"  class="tablink">
                                      <a href="training.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.TRAINING) %></a>
                                      </div>
                                  </td> 
                                  <%-- active tab --%>          
                                  <td width="7%" nowrap bgcolor="#66CCFF"> 
                                      <div align="center"  class="tablink">
                                      <span class="tablink"><%=dictionaryD.getWord(I_Dictionary.WARNING) %></span>
                                      </div>
                                  </td>
                                  <td width="8%" nowrap bgcolor="#0066CC"> 
                                      <div align="center">
                                      <a href="reprimand.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.REPRIMAND) %></a>
                                      </div>
                                  </td>
                                  <td width="6%" nowrap bgcolor="#0066CC"> 
                                      <div align="center"  class="tablink">
                                      <a href="award.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.AWARD) %></a>
                                      </div>
                                  </td>     
                                  <td width="7%" nowrap bgcolor="#0066CC"> 
                                      <div align="center"  class="tablink">
                                      <a href="picture.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.PICTURE) %></a>
                                      </div>
                                  </td>                               										  
                                  <td width="11%" nowrap bgcolor="#0066CC">
                                      <div align="center">
                                      <a href="doc_relevant.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.RELEVANT_DOCS) %></a>
                                      </div>
                                  </td>
                                  <%-- END TAB MENU --%>
                                  
                              </tr>
                        </table>                          
                      </td></tr>
                    <tr> 
                      <td class="tablecolor"> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr> 
                            <td valign="top"> 
                              <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                <tr> 
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                  
                                    <%-- MAIN FORM --%>
                                    <form name="fredit" method="post" action="">
                                      <input type="hidden" name="command" value="">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="employee_oid" value="<%=oidEmployee%>"> 
                                      <input type="hidden" name="<%= FrmEmpWarning.fieldNames[FrmEmpWarning.FRM_FIELD_EMPLOYEE_ID]%>" value="<%=oidEmployee%>">
                                      <input type="hidden" name="warning_id" value="<%=oidWarning%>">
                                                                             
                                      <table width="100%" cellspacing="2" cellpadding="1" >
                                        <tr> 
											
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td colspan="2"  valign="top" align="left"  > 
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td colspan="5" nowrap>&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td colspan="5" nowrap> 
                                                  <div align="center"><font size="3"><b>WARNING 
                                                    HISTORY</b></font></div>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="1%" nowrap>&nbsp;</td>
                                                <td width="18%" nowrap>&nbsp;</td>
                                                <td width="4%" nowrap>&nbsp;</td>
                                                <td width="46%" nowrap>&nbsp;</td>
                                                <td width="13%">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                              
                                                <%-- EMPLOYEE MAIN DATA --%>
                                                <td width="1%" nowrap>&nbsp;</td>
                                                <td width="18%" nowrap> 
                                                  <div align="left">Employee 
                                                              Number</div>
                                                </td>
                                                <td width="4%">:</td>
                                                <td width="46%" nowrap> <%= employee.getEmployeeNum() %> </td>                                                
                                              </tr>
                                              <tr> 
                                                <td width="1%" nowrap>&nbsp;</td>
                                                <td width="18%" nowrap> 
                                                  <div align="left">Employee Name</div>
                                                </td>
                                                <td width="4%">:</td>
                                                <td width="46%" nowrap> <%= employee.getFullName() %> </td>                                                
                                              </tr>
                                              <tr> 
                                                <td width="1%" nowrap>&nbsp;</td>
                                                <td width="18%" nowrap> 
                                                  <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>
                                                </td>
                                                <td width="4%">:</td>
                                                <td width="46%" nowrap>  <%=department.getDepartment()%> </td>
                                                <td width="13%" nowrap> 
                                                  <div align="left"></div>
                                                </td>
                                                <td width="18%">&nbsp; </td>
                                              </tr>
                                              <tr> 
                                                 <td width="1%" nowrap>&nbsp;</td>
                                                 <td width="18%"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></td>
                                                 <td width="4%">:</td>
                                                 <td width="46%"><%=section.getSection()%></td>
                                              </tr>                                              
                                            </table>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3"  valign="top" align="left"  > 
                                            <hr>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td colspan="2" align="left" nowrap valign="top"  > 
                                            
                                            <%-- EMPLOYEE WARNING TABLE DATA --%>                                         
                                            <%=drawList(listWarning, oidWarning)%>
                                          </td>
                                        </tr>
                                        <tr>
                                        
                                          <%-- DRAW RECORDS INFORMATION --%>
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td colspan="2" align="left" nowrap valign="top"  >
                                          <%
                                              ctrLine.setLocationImg(approot+"/images");
                                              ctrLine.initDefault();
                                          %>
                                          <%= ctrLine.drawImageListLimit(iCommand, vectSize, start, recordToGet) %>
                                          </td>
                                        </tr>
                                        <tr> 
                                        
                                          <%-- DRAW BUTTON --%>
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td colspan="2" align="left" nowrap valign="top"  > 										 
                                            <table cellpadding="0" cellspacing="0" border="0" width="25%">
                                              <tr> 
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="10"><a href="javascript:cmdBackToList()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image101','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image101" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back to List"></a></td>
                                                <td width="6"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td class="command" nowrap width="180"> 
                                                    <div align="left"><a href="javascript:cmdBackToList()">Back to
                                                    Warning List</a></div>
                                                </td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="10"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image102','','<%=approot%>/images/BtnSearchOn.jpg',1)"><img name="Image102" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Schedule"></a></td>
                                                <td width="6"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td class="command" nowrap width="180"> 
                                                    <div align="left"><a href="javascript:cmdAdd()">Add New 
                                                    Warning</a></div>
                                                </td>                                               
                                              </tr>
                                            </table>				
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td width="11%" align="left" nowrap valign="top"  >&nbsp;</td>
                                          <td  width="88%"  valign="top" align="left" class="comment">&nbsp;</td>
                                        </tr>  
                                        <%-- DISPLAY MESSAGE --%>
                                        <% 
                                           if(iCommand==Command.ADD) {
                                              int warnLevel = SessEmpWarning.chekcActiveWarning(new Date(), oidEmployee);
                                            
                                              if(warnLevel != -1) {
                                                  defaultWarnLevel = warnLevel + 1;
                                        %>
                                            <tr>
                                              <td width="1%"  valign="top" align="left">&nbsp;</td>
                                              <td width="11%" align="left" nowrap valign="top" colspan="2">
                                                  <span class="warningmsg">
                                                      <% if(warnLevel < PstEmpWarning.levelNames.length - 1) {%>
                                                        <p class="warningmsg">Note: This band member is still in effective warning period (<%=PstEmpWarning.levelNames[warnLevel]%>) !</p>
                                                      <% } else {%>
                                                        <p class="warningmsg">Note: This band member has received "Warning Level 3", use reprimand instead !</p>
                                                      <% } %>
                                                  </span>
                                              </td>                                              
                                            </tr> 
                                            <tr> 
                                              <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                              <td width="11%" align="left" nowrap valign="top"  >&nbsp;</td>
                                              <td  width="88%"  valign="top" align="left" class="comment">&nbsp;</td>
                                            </tr>
                                            <% } %>
                                        <% } %>
                                                                         
                                        <%-- FOR EDITING STATE --%>
                                        <%if(iCommand==Command.ADD || iCommand==Command.EDIT || iCommand==Command.ASK || 
                                            (iCommand==Command.SAVE && iErrCode!=FRMMessage.NONE)){ %>
                                        <tr> 
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td colspan="2" align="left" nowrap valign="top"  ><b>WARNING RECORD EDITOR</b></td>
                                        </tr>
                                        <tr> 
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td width="11%" align="left" nowrap valign="top"  >&nbsp;</td>
                                          <td  width="88%"  valign="top" align="left" class="comment">*) 
                                            entry required</td>
                                        </tr>
                                        <tr> 
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td width="11%" align="left" nowrap valign="top"  >Break Date</td>
                                          <td  width="88%"  valign="top" align="left"> 
                                            <%=ControlDate.drawDateWithStyle(FrmEmpWarning.fieldNames[FrmEmpWarning.FRM_FIELD_BREAK_DATE], warning.getBreakDate(), 2, -30, "formElemen", "")%> 
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td width="11%" align="left" nowrap valign="top"  >Break Fact </td>
                                          <td  width="88%"  valign="top" align="left"> 
                                            <textarea cols="22" rows="5" name="<%= FrmEmpWarning.fieldNames[FrmEmpWarning.FRM_FIELD_BREAK_FACT] %>"><%= warning.getBreakFact() %></textarea> * <%=frmEmpWarning.getErrorMsg(FrmEmpWarning.FRM_FIELD_BREAK_FACT)%> 
                                        </tr>
                                        <tr> 
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td width="11%" align="left" nowrap valign="top"  >Warning Date</td>
                                          <td  width="88%"  valign="top" align="left"> 
                                            <%=ControlDate.drawDateWithStyle(FrmEmpWarning.fieldNames[FrmEmpWarning.FRM_FIELD_WARN_DATE], warning.getWarningDate(), 2, -30, "formElemen", "")%> 
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td width="11%" align="left" nowrap valign="top"  >Warning Level</td>
                                          <td  width="88%"  valign="top" align="left"> 
                                              <%    Vector warning_value = new Vector(1,1);
                                                        Vector warning_key = new Vector(1,1);
                                                        Vector listEmpWarning = PstWarning.listAll();
                                                        for(int i=0;i<listEmpWarning.size();i++){
                                                            Warning empwarning = (Warning) listEmpWarning.get(i);
                                                            warning_value.add(""+empwarning.getWarnLevel());
                                                            warning_key.add(""+empwarning.getWarnDesc());
                                                        }
                                                    %>
                                                        <% if((listEmpWarning != null) && (listEmpWarning.size() > 0)){%>
                                                        <%= ControlCombo.draw(frmEmpWarning.fieldNames[FrmEmpWarning.FRM_FIELD_WARN_LEVEL],"formElemen",null, ""+empWarning.getWarnLevel(), warning_value, warning_key) %>
                                                        <% }else {%>
                                                        <font class="comment">No
                                                        Warning available</font>
                                                        <% }%>
                                                        * <%= frmEmpWarning.getErrorMsg(FrmEmpWarning.FRM_FIELD_WARN_LEVEL) %>
                                          </td>
                                          </td>
                                        </tr>
                                        <tr>
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td width="11%" align="left" nowrap valign="top"  >Warning Point</td>
                                          <td  width="88%"  valign="top" align="left">
                                            <%=ControlDate.drawDateWithStyle(FrmEmpWarning.fieldNames[FrmEmpWarning.FRM_FIELD_WARN_DATE], warning.getWarningDate(), 2, -30, "formElemen", "")%>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td width="11%" align="left" nowrap valign="top"  >Warning By </td>
                                          <td  width="88%"  valign="top" align="left"> 
                                            <table>
                                            <tr>
                                                <td>
                                                    <input type="text" readonly size="33" name="<%=FrmEmpWarning.fieldNames[FrmEmpWarning.FRM_FIELD_WARN_BY]%>" value="<%=warning.getWarningBy()%>" class="formElemen" maxlength="50">
                                                </td>
                                                <td>
                                                <td>    
                                                    <a href="javascript:cmdListEmployee()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10x','','<%=approot%>/images/icon/folderopen.gif',1)"><img name="Image10x" border="0" src="<%=approot%>/images/icon/folder.gif" width="24" height="24" alt="Search Employee"></a>
                                                </td>                                                
                                                <td>
                                                    <a href="javascript:cmdListEmployee()">Search Employee</a>
                                                </td>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td width="11%" align="left" nowrap valign="top"  >Valid Until</td>
                                          <td  width="88%"  valign="top" align="left"> 
                                            <%                                                
                                                if(iCommand == Command.ADD) {
                                                    Calendar cal = new GregorianCalendar();                                                    
                                                    cal.add(Calendar.MONTH, 1);
                                                    
                                                    Date date = new Date(cal.get(Calendar.YEAR) - 1900, cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                                                    out.println(ControlDate.drawDateWithStyle(FrmEmpWarning.fieldNames[FrmEmpWarning.FRM_FIELD_VALID_DATE], date, 2, -30, "formElemen", ""));
                                                }
                                                else {
                                                    out.println(ControlDate.drawDateWithStyle(FrmEmpWarning.fieldNames[FrmEmpWarning.FRM_FIELD_VALID_DATE], warning.getValidityDate(), 2, -30, "formElemen", ""));
                                                }
                                            %>                                            
                                          </td>
                                        </tr>                                        
                                        <tr> 
                                          <td width="1%"  valign="top" align="left"  >&nbsp;</td>
                                          <td width="11%"  valign="top" align="left"  >&nbsp;</td>
                                          <td  width="88%"  valign="top" align="left">&nbsp;</td>
                                        </tr>
                                        <tr align="left"> 
                                          <td colspan="4"> 
                                            <%
                                                ctrLine.setLocationImg(approot+"/images");
                                                ctrLine.initDefault();
                                                ctrLine.setTableWidth("90");
                                                ctrLine.setCommandStyle("buttonlink");
                                
                                                String scomDel = "javascript:cmdDelete('"+oidWarning+"')";
                                                String scomAsk = "javascript:cmdAsk('"+oidWarning+"')";
                                                String scomEdit = "javascript:cmdEdit('"+oidWarning+"')";
                                                
                                                ctrLine.setBackCaption("Back To List");
                                                ctrLine.setDeleteCaption("Delete Warning Record");
                                                ctrLine.setSaveCaption("Save Warning Record");
                                                ctrLine.setConfirmDelCaption("Yes, Delete Warning Record");
                                                ctrLine.setAddCaption("Add New Data");                                                

                                                if(privDelete) {
                                                    ctrLine.setConfirmDelCommand(scomDel);
                                                    ctrLine.setDeleteCommand(scomAsk);
                                                    ctrLine.setEditCommand(scomEdit);
                                                }
                                                else{ 
                                                    ctrLine.setConfirmDelCaption("");
                                                    ctrLine.setDeleteCaption("");
                                                    ctrLine.setEditCaption("");
                                                }

                                                if(!privAdd){
                                                    ctrLine.setAddCaption("");
                                                }
                                                
                                                if(!privAdd  && !privUpdate){
                                                    ctrLine.setSaveCaption("");
                                                }

                                            %>
                                            <%= ctrLine.drawImage(iCommand, iErrCode, errMsg)%>
                                
                                        </td>                                 
                                      </tr>
                                          <% if((iCommand != Command.ASK) && (iCommand == Command.EDIT) && privPrint) { %>
                                          <tr>
                                             <td>
                                                 <a href="javascript:cmdPrint()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image110','','<%=approot%>/images/BtnNewOn.jpg',1)" id="aSearch">
                                                 <img name="Image110" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print Warning Report"></a>
                                             </td>

                                             <td>
                                                <a href="javascript:cmdPrint()">Print Report</a>
                                             </td>
                                          </tr>
                                          <% } %>
                                      <% } %>
                                      <%-- END EDITING STATE --%>
                                        
                                     </table>
                                   </form>
                                   <!-- #EndEditable --> </td>
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
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>