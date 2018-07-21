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
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>

<%@ include file = "../../main/javainit.jsp" %>

<%-- YANG INI BELUM DIEDIT --%>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK); %>
<%@ include file = "../../main/checkuser.jsp" %>


<%
int iCommand = FRMQueryString.requestCommand(request);

SrcEmpReprimand srcReprimand = new SrcEmpReprimand();
FrmSrcEmpReprimand frmSrcReprimand = new FrmSrcEmpReprimand();

// refresh combo box
if(iCommand == Command.GOTO){
    frmSrcReprimand = new FrmSrcEmpReprimand(request, srcReprimand);
    frmSrcReprimand.requestEntityObject(srcReprimand);
}

// back from reprimand list
if(iCommand == Command.BACK) {  
    try {
        srcReprimand = (SrcEmpReprimand)session.getValue("SRC_EMP_REPRIMAND");
        
        if(srcReprimand == null){
            srcReprimand = new SrcEmpReprimand();
        }
    }
    catch(Exception e) {
        srcReprimand = new SrcEmpReprimand();
    }
}


%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Search Employee Reprimand</title>
<script language="JavaScript">

    function cmdUpdateDept(){
        document.frmsrcreprimand.command.value="<%=String.valueOf(Command.GOTO)%>";
        document.frmsrcreprimand.action="src_reprimand.jsp"; 
        document.frmsrcreprimand.submit();
    }

    function cmdUpdateSec(){
        document.frmsrcreprimand.command.value="<%=String.valueOf(Command.GOTO)%>";
        document.frmsrcreprimand.action="src_reprimand.jsp"; 
        document.frmsrcreprimand.submit();
    }
    
    function cmdSearch(){
        document.frmsrcreprimand.command.value="<%=String.valueOf(Command.LIST)%>";
        document.frmsrcreprimand.action="reprimand_list.jsp";
        document.frmsrcreprimand.submit();
    }

    function MM_swapImgRestore() { //v3.0
      var i,x,a=document.MM_sr; for(i=0;a && i < a.length && (x=a[i]) && x.oSrc;i++) x.src=x.oSrc;
    }

    function MM_preloadImages() { //v3.0
      var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
        var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i < a.length; i++)
        if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
    }

    function MM_findObj(n, d) { //v4.0
      var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0 && parent.frames.length) {
        d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
      if(!(x=d[n]) && d.all) x=d.all[n]; for (i=0;!x && i < d.forms.length;i++) x=d.forms[i][n];
      for(i=0;!x && d.layers && i < d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
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
<!-- #EndEditable -->
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">
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
                  Employee &gt; Reprimand &gt; Search Reprimand <!-- #EndEditable --> 
            </strong></font>
	      </td>
        </tr>
        <tr> 
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td  style="background-color:<%=bgColorContent%>;"> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                    <tr> 
                      <td valign="top"> 
                        <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr> 
                            <td valign="top">
		    				  <!-- #BeginEditable "content" --> 
                                    <form name="frmsrcreprimand" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="<%= PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_EMPLOYEE_ID] %>" value="<%= srcReprimand.getEmployeeId() %>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td valign="middle" colspan="2"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                                              <tr> 
                                                <td width="3%">&nbsp;</td>
                                                <td width="97%">&nbsp;</td>
                                                <td width="0%">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td width="3%">&nbsp;</td>
                                                <td width="97%"> 
                                                  <table border="0" cellspacing="2" cellpadding="2" width="72%">
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" nowrap> 
                                                        <div align="left">Employee 
                                                          Name</div></td>
                                                      <td width="83%"> <input type="text" name="<%=frmSrcReprimand.fieldNames[FrmSrcEmpReprimand.FRM_FIELD_NAME] %>"  value="<%= srcReprimand.getName()%>" class="elemenForm" size="40"> 
                                                      </td>
                                                    </tr>                                       
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" nowrap> 
                                                        <div align="left">Payroll 
                                                          Number</div></td>
                                                      <td width="83%"> <input type="text" name="<%=frmSrcReprimand.fieldNames[FrmSrcEmpReprimand.FRM_FIELD_PAYROLL] %>"  value="<%= srcReprimand.getPayroll()%>" class="elemenForm"> 
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" nowrap> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></div></td>
                                                      <td width="83%"> 
                                                        <% 
                                                            Vector div_value = new Vector(1,1);
                                                            Vector div_key = new Vector(1,1);  
                                                            
                                                            div_value.add("0");
                                                            div_key.add("ALL");
                                         
                                                            Vector listDiv = PstDivision.list(0, 0, "", PstDivision.fieldNames[PstDivision.FLD_DIVISION]);                                                        
                                                            
                                                            for(int i=0; i<listDiv.size(); i++) {
                                                                Division div = (Division) listDiv.get(i);
                                                                div_key.add(div.getDivision());
                                                                div_value.add(String.valueOf(div.getOID()));
                                                            }
                                                          %> 
                                                          <%= ControlCombo.draw(frmSrcReprimand.fieldNames[FrmSrcEmpReprimand.FRM_FIELD_DIV_ID],"formElemen",null, ""+srcReprimand.getDivisionId(), div_value, div_key, "onChange=\"javascript:cmdUpdateDept()\"") %> </td>
                                                    </tr> 
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" nowrap> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div></td>
                                                      <td width="83%"> 
                                                        <% 
                                                            Vector dept_value = new Vector(1,1);
                                                            Vector dept_key = new Vector(1,1);  
                                                            
                                                            dept_value.add("0");
                                                            dept_key.add("ALL");
                                                            
                                                            String strWhereDep = "hr_department.DIVISION_ID="+srcReprimand.getDivisionId();
                                                            Vector listDept = PstDepartment.list(0, 0, strWhereDep, PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);                                                        
                                                            
                                                            for(int i=0; i<listDept.size(); i++) {
                                                                Department dept = (Department) listDept.get(i);
                                                                dept_key.add(dept.getDepartment());
                                                                dept_value.add(String.valueOf(dept.getOID()));
                                                            }
                                                          %> 
                                                          <%= ControlCombo.draw(frmSrcReprimand.fieldNames[FrmSrcEmpReprimand.FRM_FIELD_DEPT_ID],"formElemen",null, ""+srcReprimand.getDepartmentId(), dept_value, dept_key, "onChange=\"javascript:cmdUpdateSec()\"") %> </td>
                                                    </tr>                                                    
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" nowrap> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></div></td>
                                                      <td width="83%"> 
                                                        <% 
                                                            Vector sec_value = new Vector(1,1);
                                                            Vector sec_key = new Vector(1,1);
                                                            
                                                            sec_value.add("0");
                                                            sec_key.add("ALL");
                                                       
                                                            String strWhereSec = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+"="+srcReprimand.getDepartmentId();
                                                            Vector listSec = PstSection.list(0, 0, strWhereSec, PstSection.fieldNames[PstSection.FLD_SECTION]);
                                                            
                                                            for(int i=0; i<listSec.size(); i++) {
                                                                Section sec = (Section) listSec.get(i);
                                                                sec_key.add(sec.getSection());
                                                                sec_value.add(String.valueOf(sec.getOID()));
                                                            }
                                                        %> 
                                                        <%= ControlCombo.draw(frmSrcReprimand.fieldNames[FrmSrcEmpReprimand.FRM_FIELD_SEC_ID],"formElemen",null, "" + srcReprimand.getSectionId(), sec_value, sec_key) %></td>
													
                                                    </tr>	
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" nowrap> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.POSITION) %></div></td>
                                                      <td width="83%"> 
                                                        <% 
                                                            Vector pos_value = new Vector(1,1);
                                                            Vector pos_key = new Vector(1,1);
                                                            
                                                            pos_value.add("0");
                                                            pos_key.add("ALL");
                                                            
                                                            Vector listPos = PstPosition.list(0, 0, "", PstPosition.fieldNames[PstPosition.FLD_POSITION]);
                                                            
                                                            for(int i=0; i<listPos.size(); i++) {
                                                                Position pos = (Position) listPos.get(i);
                                                                pos_key.add(pos.getPosition());
                                                                pos_value.add(String.valueOf(pos.getOID()));
                                                            }
                                                        %> 
                                                        <%= ControlCombo.draw(frmSrcReprimand.fieldNames[FrmSrcEmpReprimand.FRM_FIELD_POS_ID],"formElemen",null, "" + srcReprimand.getPositionId(), pos_value, pos_key) %></td>
													
                                                    </tr>                            
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" height="24" nowrap>Reprimand Date From</td>
                                                      <td width="83%">
                                                        <%=ControlDate.drawDateWithStyle(frmSrcReprimand.fieldNames[FrmSrcEmpReprimand.FRM_FIELD_REPRIMAND_START_DATE], srcReprimand.getStartingReprimandDate(), 0, -50,"formElemen")%> &nbsp;To&nbsp; 
                                                        <%=ControlDate.drawDateWithStyle(frmSrcReprimand.fieldNames[FrmSrcEmpReprimand.FRM_FIELD_REPRIMAND_END_DATE], srcReprimand.getEndingReprimandDate(), 0, -50,"formElemen")%></td>
                                                    </tr> 
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%"> <div align="left"></div></td>
                                                      <td width="83%">&nbsp;</td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%"> <div align="left"></div></td>
                                                      <td width="83%"> <table width="80%" border="0" cellspacing="0" cellpadding="0">
                                                          <tr> 
                                                            <td width="5%"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Reprimand"></a></td>
                                                            <td width="70%" class="command" nowrap><a href="javascript:cmdSearch()">Search Reprimand</a></td>                                                          
                                                          </tr>
                                                        </table></td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                            </table>
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
<!-- #EndEditable -->
<!-- #EndTemplate --></html>