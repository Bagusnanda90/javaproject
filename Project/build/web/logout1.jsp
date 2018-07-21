<%@ page language="java" %>

<%@ page import="java.util.*" %>
<%@ page import="com.dimata.qdep.form.*" %>
<%@ page import="com.dimata.harisma.session.admin.*" %>

<%@ include file="main/javainit.jsp"%>

<%


try
{
    
    int cekOut = FRMQueryString.requestInt(request, "cekOut");
    if (cekOut == 1){
        
    }
	if(userSession.isLoggedIn()==true)
	{
		//System.out.println("doLogout"); 
		//userSession.printAppUser();
		userSession.doLogout(); 
		session.removeValue(SessUserSession.HTTP_SESSION_NAME);
                
	}
} 
catch (Exception exc)
{
	System.out.println(" >>> Exception during logout user");
}

int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_LOGIN, AppObjInfo.G2_LOGIN, AppObjInfo.OBJ_LOGIN_LOGOUT);
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Harisma - Logout</title>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="styles/tab.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "headerscript" --> 
<script language="JavaScript">
function cmdClose(){
	window.close();
}

function cmdLogin(){
	document.frmlogout.action="login.jsp";
	document.frmlogout.submit();
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
</head> 

<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnDelOn.jpg','<%=approot%>/images/BtnSaveOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="template_header_logout.jsp" %>
            <%}else{%>
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "main/header.jsp" %>
      <!-- #EndEditable --> 
    </td>
  </tr> 
  <tr> 
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
      <%//@ include file = "main/mnmain.jsp" %>
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
			  <!-- #BeginEditable "contenttitle" --><!-- #EndEditable --> 
            </strong></font>
	      </td>
        </tr>
        <tr> 
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td class="tablecolor" style="background-color:<%=bgColorContent%>; "> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                    <tr> 
                      <td valign="top"> 
                        <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                          <tr> 
                            <td valign="top">
		    				  <!-- #BeginEditable "content" -->
								  	<form name="frmlogout" method="post" action="login.jsp">
	                                  <table width="100%" cellspacing="2" cellpadding="2" align="center">
                                        <tr> 
                                          <td> 
                                            <div align="center"><font face="Arial, Helvetica, sans-serif" color="#3300CC">Thank 
                                              you for using</font></div>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td> 
                                            <div align="center"><img src="images/harismaFlat.gif" width="364" height="177"></div>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td> 
                                            <div align="center"><font face="Arial, Helvetica, sans-serif" color="#3300CC"><br>
                                              Now you can close this application 
                                              safely, or click the Login button 
                                              below to log in.</font></div>
                                          </td>
                                        </tr>
                                        <tr>
                                          <td>&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td> 
                                            <div align="center"> 
                                              <table border="0" cellpadding="0" cellspacing="0" width="100">
                                                <tr> 
                                                  <td width="7%"><a href="javascript:cmdClose()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnDelOn.jpg',1)"><img name="Image10" border="0" src="<%=approot%>/images/BtnDel.jpg" width="24" height="24" alt="Close"></a></td>
                                                  <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                  <td width="39%" class="command" nowrap><a href="javascript:cmdClose()">Close</a></td>
                                                  <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="30" height="8"></td>
                                                  <td width="7%"><a href="javascript:cmdLogin()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%=approot%>/images/BtnSaveOn.jpg',1)"><img name="Image100" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Login"></a></td>
                                                  <td width="3%"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                  <td width="38%" class="command" nowrap><a href="javascript:cmdLogin()">Login</a></td>
                                                </tr>
                                              </table>
                                            </div>
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
