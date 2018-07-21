<% 
/* 
 * Page Name  		:  srcemployee.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		: lkarunia 
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

<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>

<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>

<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%@ page import = "com.dimata.harisma.session.employee.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% 
    int appObjCodeGen = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_TRAINING, AppObjInfo.OBJ_GENERAL_TRAINING); 
    int appObjCodeDept = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_TRAINING, AppObjInfo.OBJ_DEPARTMENTAL_TRAINING); 
    int appObjCode = 0; 
    
    // check training privilege (0 = none, 1 = general, 2 = departmental)
    int trainType = checkTrainingType(appObjCodeGen, appObjCodeDept, userSession);
    
    if(trainType == PRIV_GENERAL) {    
        appObjCode = appObjCodeGen;
    }
    else if(trainType == PRIV_DEPT) {  
        appObjCode = appObjCodeDept;
    }

    boolean privView = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
    boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
    boolean privUpdate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
    boolean privDelete = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
    boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>
<%@ include file = "../../main/checktraining.jsp" %>

<!-- Jsp Block -->
<%
    int iCommand = FRMQueryString.requestCommand(request);
    SrcEmployee srcEmployee = new SrcEmployee();
    FrmSrcEmployee frmSrcEmployee = new FrmSrcEmployee();

    if(iCommand==Command.BACK)
    {
        frmSrcEmployee = new FrmSrcEmployee(request, srcEmployee);
        //frmSrcEmployee.requestEntityObject(srcEmployee);
        try{
            srcEmployee = (SrcEmployee)session.getValue(SessEmployee.SESS_SRC_EMPLOYEE+"_1");
            if(srcEmployee == null) {
                srcEmployee = new SrcEmployee();
            }
        }
        catch (Exception e){
            System.out.println("e....."+e.toString());
            srcEmployee = new SrcEmployee();
        }
    }
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Search Employee</title>
<script language="JavaScript">

    function cmdAdd(){
            document.frmsrcemployee.command.value="<%=Command.ADD%>";
            document.frmsrcemployee.action="employee_edit.jsp";
            document.frmsrcemployee.submit();
    }

    function cmdSearch(){
            document.frmsrcemployee.command.value="<%=Command.LIST%>";
            document.frmsrcemployee.action="employee_list.jsp";
            document.frmsrcemployee.submit();
    }

    function cmdSpecialQuery(){
            document.frmsrcemployee.action="specialquery.jsp";
            document.frmsrcemployee.submit();
    }

    function fnTrapKD(){
       if (event.keyCode == 13) {
            document.all.aSearch.focus();
            cmdSearch();
       }
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
<SCRIPT language="javascript">
<!--
function hideObjectForEmployee(){
   //document.frmsrcemployee.<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_START_COMMENC] + "_mn"%>.style.visibility = "hidden";
   //document.frmsrcemployee.<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_START_COMMENC] + "_dy"%>.style.visibility = "hidden";
	/*
    document.frmsrcemployee.<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_START_COMMENC] + "_yr"%>.style.visibility = "hidden";
    document.frmsrcemployee.<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_END_COMMENC] + "_mn"%>.style.visibility = "hidden";
    document.frmsrcemployee.<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_END_COMMENC] + "_dy"%>.style.visibility = "hidden";
    document.frmsrcemployee.<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_END_COMMENC] + "_yr"%>.style.visibility = "hidden";
    document.frmsrcemployee.<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_DEPARTMENT]%>.style.visibility = "hidden";
	*/
} 
	 
function hideObjectForLockers(){ 
}
	
function hideObjectForCanteen(){
}
	
function hideObjectForClinic(){
}

function hideObjectForMasterdata(){
   // document.frmsrcemployee.<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_END_COMMENC] + "_mn"%>.style.visibility = "hidden";
   /*
    document.frmsrcemployee.<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_END_COMMENC] + "_dy"%>.style.visibility = "hidden";
    document.frmsrcemployee.<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_END_COMMENC] + "_yr"%>.style.visibility = "hidden";
	*/
}

function showObjectForMenu(){
    //document.frmsrcemployee.<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_START_COMMENC] + "_mn"%>.style.visibility = "";
   // document.frmsrcemployee.<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_START_COMMENC] + "_dy"%>.style.visibility = "";
   /*
    document.frmsrcemployee.<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_START_COMMENC] + "_yr"%>.style.visibility = "";
    document.frmsrcemployee.<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_END_COMMENC] + "_mn"%>.style.visibility = "";
    document.frmsrcemployee.<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_END_COMMENC] + "_dy"%>.style.visibility = "";
    document.frmsrcemployee.<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_END_COMMENC] + "_yr"%>.style.visibility = "";
    document.frmsrcemployee.<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_DEPARTMENT]%>.style.visibility = "";
	*/
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
-->
</SCRIPT>
<!-- #EndEditable -->
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg')">
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> 
                  Employee &gt; Training History &gt; Employee Search<!-- #EndEditable --> 
                  </strong></font> </td>
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
                                    <form name="frmsrcemployee" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
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
                                                          Name</div>
                                                      </td>
                                                      <td width="83%"> 
                                                        <input type="text" name="<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_NAME] %>"  value="<%= srcEmployee.getName() %>" class="elemenForm" size="40" onkeydown="javascript:fnTrapKD()">
                                                      </td>
                                                    </tr>
                                                    <script language="javascript">
                                                        document.frmsrcemployee.<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_NAME]%>.focus();
                                                    </script>
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" nowrap> 
                                                        <div align="left">Payroll 
                                                          Number</div>
                                                      </td>
                                                      <td width="83%"> 
                                                        <input type="text" name="<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_EMPNUMBER] %>"  value="<%= srcEmployee.getEmpnumber() %>" class="elemenForm" onkeydown="javascript:fnTrapKD()">
                                                      </td>
                                                    </tr>                                                   
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" nowrap> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>
                                                      </td>
                                                      <td width="83%"> 
                                                        <% 
                                                            Vector dept_value = new Vector(1,1);
                                                            Vector dept_key = new Vector(1,1);        
                                                            
                                                            String whereClause = "";
                                                            
                                                            if(trainType == PRIV_DEPT) {
                                                                whereClause = PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + "=" + departmentOid;
                                                            }
                                                            else {
                                                                dept_value.add("0");
                                                                dept_key.add("select ...");    
                                                            }
                                                            
                                                            Vector listDept = PstDepartment.list(0, 0, whereClause, " DEPARTMENT ");                                                        
                                                            for (int i = 0; i < listDept.size(); i++) {
                                                                Department dept = (Department) listDept.get(i);
                                                                dept_key.add(dept.getDepartment());
                                                                dept_value.add(String.valueOf(dept.getOID()));
                                                            }
                                                        %>
                                                        <%= ControlCombo.draw(frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_DEPARTMENT],"formElemen",null, ""+srcEmployee.getDepartment(), dept_value, dept_key, " onkeydown=\"javascript:fnTrapKD()\"") %> 
                                                      </td>
                                                    </tr>						
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" height="21" nowrap> 
                                                        <div align="left">Sort By</div>
                                                      </td>
                                                      <td width="83%"> <%= ControlCombo.draw(frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_ORDER],"formElemen",null, ""+srcEmployee.getOrderBy(), FrmSrcEmployee.getOrderValue(), FrmSrcEmployee.getOrderKey(), " onkeydown=\"javascript:fnTrapKD()\"") %></td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%"> 
                                                        <div align="left"></div>
                                                      </td>
                                                      <td width="83%">&nbsp;</td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%"> 
                                                        <div align="left"></div>
                                                      </td>
                                                      <td width="83%"> 
                                                        <table width="30%" border="0" cellspacing="0" cellpadding="0">
                                                          <tr> 
                                                            <td width="20%"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                            <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                                            <td width="28%" class="command" nowrap><a href="javascript:cmdSearch()">Search 
                                                              for Employee</a></td>
                                                            <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="10" height="8"></td>                                                           
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
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
