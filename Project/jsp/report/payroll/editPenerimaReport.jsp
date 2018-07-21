
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
<%@ page import = "com.dimata.harisma.form.locker.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.location.Location" %>
<%@ page import = "com.dimata.harisma.entity.locker.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.session.employee.SessEmployeePicture" %>
<%@page import = "com.dimata.harisma.form.masterdata.FrmKecamatan" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK);%>
<% boolean privGenerate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_GENERATE_SALARY_LEVEL));%>
<%@ include file = "../../main/checkuser.jsp" %>
<%
    /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
%>
<%    
    CtrlEmployee ctrlEmployee = new CtrlEmployee(request);
    //long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");
    String email = FRMQueryString.requestString(request, "email");
    int iErrCode = FRMMessage.ERR_NONE;
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");

    if (email.length()>0 && iCommand == Command.GOTO){
    session.putValue("emailAddress", email);
    }
%>
<html>
<head> 
<title>Harisma - Login</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<link rel="stylesheet" href="<%=approot%>/styles/calendar.css" type="text/css">
<script language="JavaScript">
window.status="";

function fnTrapKD(){
   if (event.keyCode == 13) 
   {
        document.all.aSearch.focus();
        cmdLogin();
   }
}

<% if ( iCommand == Command.GOTO )  { %>
    window.open("<%=approot%>/servlet/com.dimata.harisma.report.EmployeeListEmail");
<% } %>    


function cmdSendEmail1(){
       // window.open("<%=approot%>/servlet/com.dimata.harisma.report.EmployeeDetailXLS?oid=");
       }
function cmdSendEmail(){
                document.frmEmpEmail.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmEmpEmail.action="editPenerima.jsp";
                document.frmEmpEmail.submit();
		//window.open("<%=approot%>/servlet/com.dimata.harisma.report.EmployeeListEmail");
                //document.frmEmpEmail.email.value=0;
	}
function MM_swapImgRestore() 
{ //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() 
{ //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
	var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
	if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) 
{ //v4.0
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
</head>
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('../../images/login_images/button_f2.jpg');window.status=''">
    <!-- Untuk Calender-->
<%=(ControlDatePopup.writeTable(approot))%>
<!-- End Calender-->
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#FFFFFF">
  <tr>
    <td  bgcolor="#9BC1FF" height="20" ID="MAINMENU" valign="middle">
	<table width="100%" border="0">
  <tr>
    <td  bgcolor="#BBDDFF">
      <div align="center"><font color="#0000FF">&nbsp;</font></div>
	</td>
  </tr>
</table>
	</td>
  </tr>
  <tr>
    <td valign="middle" align="left">
      <form name="frmEmpEmail" action="" onsubmit="window.status=''">
        <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">  
        
        <input type="hidden" name="approvedId1" value="0">
        <input type="hidden" name="isApprove1" value="false">
             
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          
          <tr>
            <td width="100%">&nbsp;</td>
          </tr>
          <tr> 
            <td width="100%" align="center"><font color="#FF0000" size="4"><b><font face="Verdana, Arial, Helvetica, sans-serif">.:: 
              SET EMAIL ADDRESS ::.</font></b></font></td>
          </tr>
          <tr> 
            <td width="100%">&nbsp;</td>
          </tr>

          <tr> 
            <td width="100%" valign="middle" align="center"> 
              <table width="339" border="0" cellpadding="0" cellspacing="0" align="center">
                <tr> 
                  <td colspan="3" height="28" valign="top"> 
                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                      <tr> 
                        <td width="43" valign="top" align="right" background="../../images/login_images/uppmidd.jpg"><img src="../../images/login_images/upcorner.jpg" width="12" height="28"></td>
                      </tr>
                    </table>
                  </td>
                </tr>
                <tr> 
                  <td width="12" valign="top" background="../../images/login_images/left.jpg"><img src="../../images/login_images/left.jpg" width="12" height="17"> 
                  </td>
                  <td width="315" valign="top"> 
                    <table width="100%" border="0" cellpadding="1" cellspacing="0" bgcolor="#52BAFF">
                      <tr valign="middle"> 
                        <td width="89" height="15">&nbsp;</td>
                        <td width="222"></td>
                      </tr>
                   
                      <tr valign="middle"> 
                        <td height="15" nowrap width="89">Email</td>
                        <td width="222"> 
                          <input type="text" name="email" size="25" >
                          <a href="javascript:cmdSendEmail()" onMouseOut="MM_swapImgRestore()" >SEND</a>
                        </td>
                      </tr>
                    </table>
                  </td>
                  <td width="12" valign="top" background="../../images/login_images/right.jpg"><img src="../../images/login_images/right.jpg" width="12" height="17"></td>
                </tr>
                <tr> 
                  <td colspan="3" height="42" valign="top"> 
                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                      <tr> 
                        <td width="27" valign="top" background="../../images/login_images/bottom_middle.jpg">&nbsp;</td>
                        <td width="12" valign="top" align="right" background="../../images/login_images/bottom_middle.jpg"><img src="../../images/login_images/bottom_right_corner.jpg" width="12" height="42"></td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
		 
        </table>
		  </form>
    </td>
  </tr>
  <tr>
    <td colspan="2" height="20" <%=bgFooterLama%>>
      <%@ include file = "../../main/footer.jsp" %>
      </td>
  </tr>
</table>
<script language="JavaScript">
 document.frmEmpEmail.pass_wd.focus();
</script>
</body>
</html>
