<%@ page language="java" %>
<%@ include file="main/javainit.jsp"%>
<%@ page import="java.util.*,
                 com.dimata.util.Command" %>
<%@ page import="com.dimata.harisma.session.admin.*" %>
<%@ page import="com.dimata.qdep.form.*" %>
<%!
 final static int CMD_NONE =0;
 final static int CMD_LOGIN=1;
 final static int MAX_SESSION_IDLE=100000;
%>
<%
    response.setHeader("Expires", "Mon, 06 Jan 1990 00:00:01 GMT");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "nocache");
%>
<%
   int iCommand = Integer.parseInt((request.getParameter("command")==null) ? "0" : request.getParameter("command"));
    int dologin = SessUserSession.DO_LOGIN_OK ;
     if(iCommand==CMD_LOGIN){
        String loginID = FRMQueryString.requestString(request,"login_id");
        String passwd  = FRMQueryString.requestString(request,"pass_wd");
        String remoteIP = request.getRemoteAddr();
		boolean viewMin = FRMQueryString.requestBoolean(request,"viewform");
		boolean WOBirth	= FRMQueryString.requestBoolean(request,"viewBirth");
		session.putValue("VIEW_MINIMUM", new Boolean(viewMin));
		session.putValue("VIEW_BIRTHDAY", new Boolean(WOBirth));


        SessUserSession userSess = new SessUserSession(remoteIP );		
        dologin=userSess.doLogin(loginID, passwd);		
        if(dologin==SessUserSession.DO_LOGIN_OK)
		{
            session.setMaxInactiveInterval(MAX_SESSION_IDLE);
			userSess.setUserHrdData();
            session.putValue(SessUserSession.HTTP_SESSION_NAME, userSess);
            userSess = (SessUserSession)session.getValue(SessUserSession.HTTP_SESSION_NAME);
        }
    }

    int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_LOGIN, AppObjInfo.G2_LOGIN, AppObjInfo.OBJ_LOGIN_LOGIN);
%>
<html>
<head>
<title>Harisma - Login</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="styles/main.css" type="text/css">
<link rel="stylesheet" href="styles/tab.css" type="text/css">
<script language="JavaScript">

	window.status="";
	function fnTrapKD(){
	   //if (event.keyCode == 13) {
	   //     document.all.aSearch.focus();
	   //     cmdLogin();
	   //}
	}

	function cmdLogin()	{
	  document.frmLogin.action = "login.jsp";
	  document.frmLogin.submit();
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
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('images/login_images/button_f2.jpg');window.status=''">
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
      <form name="frmLogin" action="" onsubmit="window.status=''">
		<input type="hidden" name="command" value="<%=CMD_LOGIN%>">

        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <%
		  if((iCommand==CMD_LOGIN) && (dologin == SessUserSession.DO_LOGIN_OK))
		  {
		  %>
          <script language="JavaScript">
				 window.location = "home.jsp"
				</script>
          <%
		  }
		  %>
          <tr> 
            <td width="100%"> 
              <div align="center"><img src="images/harismaFlatWhite.gif"  width="364" height="177"></div>
            </td>
          </tr>
          <tr> 
            <td width="100%">&nbsp;</td>
          </tr>
          <tr> 
            <td width="100%">&nbsp;</td>
          </tr>
          <tr> 
            <td width="100%" align="center"><b><strong><font face="Arial" color="#FF6600" size="4">- 
              CANTEEN MODULE -</font></strong></b> </td>
          </tr>
          <tr>
            <td width="100%">&nbsp;</td>
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
                        <td width="296" height="28" valign="top" background="images/login_images/uppmidd.jpg"><img src="images/login_images/uper_login.jpg" width="253" height="28"></td>
                        <td width="43" valign="top" align="right" background="images/login_images/uppmidd.jpg"><img src="images/login_images/upcorner.jpg" width="12" height="28"></td>
                      </tr>
                    </table>
                  </td>
                </tr>
                <tr> 
                  <td width="12" valign="top" background="images/login_images/left.jpg"><img src="images/login_images/left.jpg" width="12" height="17"> 
                  </td>
                  <td width="315" valign="top"> 
                    <table width="100%" border="0" cellpadding="1" cellspacing="0" bgcolor="#52BAFF">
                      <tr valign="middle"> 
                        <td width="89" height="15">&nbsp;</td>
                        <td width="222">&nbsp;</td>
                      </tr>
                      <tr valign="middle"> 
                        <td nowrap height="15" width="89">Login Name</td>
                        <td width="222"> 
                          <input type="text" name="login_id" size="20">
                        </td>
                      </tr>
                      <tr valign="middle"> 
                        <td height="15" nowrap width="89">Password</td>
                        <td width="222"> 
                          <input type="password" name="pass_wd" size="15" onkeydown="javascript:fnTrapKD()">
                          <input type="submit" value="Submit" style="width: 0; height: 0">
                        </td>
                      </tr>
                      <tr valign="middle"> 
                        <td width="89" height="15">&nbsp;</td>
                        <td width="222">&nbsp; </td>
                      </tr>
                    </table>
                  </td>
                  <td width="12" valign="top" background="images/login_images/right.jpg"><img src="images/login_images/right.jpg" width="12" height="17"></td>
                </tr>
                <tr> 
                  <td colspan="3" height="42" valign="top"> 
                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                      <tr> 
                        <td width="105" height="42" valign="top" background="images/login_images/bottom_middle.jpg"><img src="images/login_images/bottom_left_corner.jpg" width="9" height="42"></td>
                        <td width="195" valign="top" background="images/login_images/bottom_middle.jpg" align="right"><a href="javascript:cmdLogin()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image7','','images/login_images/button_f2.jpg',1)"><img name="Image7" border="0" src="images/login_images/button.jpg" width="102" height="42" alt="Click to login"  id="aSearch"></a></td>
                        <td width="27" valign="top" background="images/login_images/bottom_middle.jpg">&nbsp;</td>
                        <td width="12" valign="top" align="right" background="images/login_images/bottom_middle.jpg"><img src="images/login_images/bottom_right_corner.jpg" width="12" height="42"></td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
          <tr> 
            <td height="26" valign="bottom"> 
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <%
					if((iCommand==CMD_LOGIN) && (dologin != SessUserSession.DO_LOGIN_OK)) 
					{
					%>
                <tr> 
                  <td valign="middle" height="38" align="right" class="text" colspan="2"> 
                    <div align="center"> <font size="+1" color="#FF0000" ><%=SessUserSession.soLoginTxt[dologin]%></font> </div>
                  </td>
                </tr>
                <%}%>
              </table>
            </td>
          </tr>
        </table>

		  </form>
    </td>
  </tr>
  <tr>
    <td colspan="2" height="20" <%=bgFooterLama%>>
      <%@ include file = "main/footer.jsp" %>
      </td>
  </tr>
</table>
<script language="JavaScript">
 document.frmLogin.login_id.focus();
</script>
</body>
</html>
