
<% 
/* 
 * Page Name  		:  employee_list.jsp
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
<%//@ page import = "java.sql.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.locker.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = 1;//AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DB_EMPLOYEE); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privPrint=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));

%>
<!-- Jsp Block -->
<%
    int iCommand = FRMQueryString.requestCommand(request);

    String sex      = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_SEX]);
    String resigned = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_RESIGNED]);
   // System.out.println("resigned = " + resigned + " --- " + Integer.valueOf(resigned));
    String sort     = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_SORTBY]);

    String workyearfrom    = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_WORKYEARFROM]);
    //System.out.println("workyearfrom = " + workyearfrom);
    String workmonthfrom   = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_WORKMONTHFROM]);
    String workyearto      = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_WORKYEARTO]);
    String workmonthto     = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_WORKMONTHTO]);

    String ageyearfrom    = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_AGEYEARFROM]);
    String agemonthfrom   = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_AGEMONTHFROM]);
    String ageyearto      = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_AGEYEARTO]);
    String agemonthto     = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_AGEMONTHTO]);

    String radiocommdate = request.getParameter("radiocommdate");
    java.util.Date commdatefrom    = FRMQueryString.requestDate(request, FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_COMMDATEFROM]);
    java.util.Date commdateto      = FRMQueryString.requestDate(request, FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_COMMDATETO]);

    Vector vparam = new Vector(1,1);
    String[] arrDepartmentId = null;
    String[] arrPositionId = null;
    String[] arrSectionId = null;
    String[] arrLevelId = null;
    String[] arrEducationId = null;
    String[] arrReligionId = null;
    String[] arrMaritalId = null;
    String[] arrBlood = null;
    String[] arrLanguageId = null;

    System.out.println("\r=== specialquery_list.jsp ===");
    try {
        arrDepartmentId = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_DEPARTMENT]);
        arrPositionId   = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_POSITION]);
        arrSectionId    = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_SECTION]);
        arrLevelId      = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_LEVEL]);
        arrEducationId  = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_EDUCATION]);
        arrReligionId   = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_RELIGION]);
        arrMaritalId    = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_MARITAL]);
        arrBlood        = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_BLOOD]);
        arrLanguageId   = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_LANGUAGE]);

        System.out.println(" arrDepartmentId.length = " + arrDepartmentId.length);
        for (int i=0; i<arrDepartmentId.length; i++) {
            System.out.println(" arrDepartmentId #" + i + " = " + arrDepartmentId[i]);
        }
    }
    catch (Exception e) {}
    System.out.println("\r=============================");

    vparam.add(arrDepartmentId);    //0
    vparam.add(arrPositionId);      //1
    vparam.add(arrSectionId);       //2
    vparam.add(arrLevelId);         //3
    vparam.add(arrEducationId);     //4
    vparam.add(arrReligionId);      //5
    vparam.add(arrMaritalId);       //6
    vparam.add(arrBlood);           //7
    vparam.add(arrLanguageId);      //8
    vparam.add(Integer.valueOf(sex));        //9
    vparam.add(Integer.valueOf(resigned));   //10
    vparam.add(radiocommdate);      //11
    vparam.add(commdatefrom);       //12
    vparam.add(commdateto);         //13

    vparam.add(workyearfrom);   //14
    vparam.add(workmonthfrom);  //15
    vparam.add(workyearto);     //16
    vparam.add(workmonthto);    //17
    vparam.add(ageyearfrom);    //18
    vparam.add(agemonthfrom);   //19
    vparam.add(ageyearto);      //20
    vparam.add(agemonthto);     //21

    vparam.add(sort);     //22

    Vector listEmployee = new Vector(1,1);
   // listEmployee = SessSpecialEmployee.searchSpecialEmployee(vparam);

%>
<!-- End of Jsp Block -->
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Employee List</title>
<script language="JavaScript">
	function cmdBack(){
		document.frm_employee.command.value="<%=Command.BACK%>";
		document.frm_employee.action="specialquery.jsp";
		document.frm_employee.submit();
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
<SCRIPT language=JavaScript>
<!--
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
//-->
</SCRIPT>
<!-- #EndEditable -->
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnBackOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">
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
              <!-- #BeginEditable "contenttitle" -->Employee &gt; Employee Search Result<!-- #EndEditable --> 
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
                                    <form name="frm_employee" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <table border="0" width="100%">
                                        <tr> 
                                          <td height="8" width="100%" class="listtitle"><span class="listtitle">Employee List</span> 
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td height="8" width="100%" class="comment"><span class="comment"><br>
                                            &nbsp;No Employee available</span> 
                                          </td>
                                        </tr>
                                      </table>                                                                                                                                                                                              
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                          <td nowrap align="left" class="command">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td nowrap align="left" class="command"> 
                                            <table border="0" cellspacing="0" cellpadding="0" align="left">
                                              <tr> <%--
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="24"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image300','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image300" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back To List"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td nowrap> <a href="javascript:cmdBack()" class="command">Back 
                                                  To Search Employee</a></td>--%>
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
