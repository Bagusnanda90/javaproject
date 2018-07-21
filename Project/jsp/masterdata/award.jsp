<%@ page language="java" %>
<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode = 1;//AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE , AppObjInfo.G2_ATTENDANCE   , AppObjInfo.OBJ_DAY_OFF_PAYMENT   	); %>
<!-- JSP Block -->
<%!
public static int CMD_NONE = 0;
public static int CMD_ADD  = 1;
public static int CMD_LIST = 2; 
public static int CMD_BACK = 3;
public static int CMD_ASK  = 4;
public static int CMD_DEL  = 5;
%>
<%
 int iCommand = Integer.parseInt((request.getParameter("command")== null) ? "0" : request.getParameter("command"));
%>
<!-- End of JSP Block -->
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Award</title>
<script language="JavaScript">

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

	function cmdAdd(){
		document.frmTraining.command.value="<%=CMD_ADD%>";
		document.frmTraining.action="<%=approot%>/masterdata/award.jsp";
		document.frmTraining.submit();		
	}

	function cmdList(){
		document.frmTraining.command.value="<%=CMD_LIST%>";
		document.frmTraining.action="<%=approot%>/masterdata/award.jsp";
		document.frmTraining.submit();			
	}

	function cmdBack(){
		document.frmTraining.command.value="<%=CMD_BACK%>";
		document.frmTraining.action="<%=approot%>/masterdata/award.jsp";
		document.frmTraining.submit();			
	}
	
	function cmdAsk(){
		document.frmTraining.command.value="<%=CMD_ASK%>";
		document.frmTraining.action="<%=approot%>/masterdata/award.jsp";
		document.frmTraining.submit();			
	}

	function cmdDel(){
		document.frmTraining.command.value="<%=CMD_DEL%>";
		document.frmTraining.action="<%=approot%>/masterdata/award.jsp";
		document.frmTraining.submit();			
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
</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../styles/main.css" type="text/css"> 
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../styles/tab.css" type="text/css"> 
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

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/first.jpg','<%=approot%>/images/prev.jpg','<%=approot%>/images/next.jpg','<%=approot%>/images/last.jpg','<%=approot%>/images/add_f2.jpg','<%=approot%>/images/save_f2.jpg','<%=approot%>/images/delete_f2.jpg','<%=approot%>/images/e_home/back.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
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
                <td class="tablecolor"> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                    <tr> 
                      <td valign="top"> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                          <tr> 
                            <td valign="top">
		    				  <!-- #BeginEditable "content" --> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td class="tablecolor"> 
                  <table width="100%" border="0" cellspacing="2" cellpadding="2" class="tablecolor">
                    <tr> 
                      <td valign="top"> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                          <tr> 
                            <td valign="top"> 
                              <form name="frmTraining" method="post" action="training.jsp">
                                <input type="hidden" name="command" value="<%=CMD_NONE%>">
                                <table width="100%" border="0" cellspacing="3" cellpadding="1">
                                  <tr>
                                    <td valign="top">
                                      <div align="left"><font color="#FF8080" face="Century Gothic"><big><strong class="tabtitle">&nbsp;Masterdata 
                                        &gt; Award List</strong></big></font></div>
                                    </td>
                                  </tr>
                                  <tr> 
                                    <td valign="top"> 
									
									  <table width="77%" border="0" cellspacing="0" cellpadding="1" class="listheader" align="left">
                                        <tr>
										<td><table width="100%" border="0" cellspacing="1" cellpadding="2" class="listbg" align="left">
                                        <tr> 
                                          <td class="listheader" width="28%" height="18">Award 
                                            Name </td>
                                          <td class="listheader" width="43%" height="18"><b><font color="#FFFFFF">Description</font></b></td>
                                        </tr>
                                        <tr> 
                                          <td class="listcontent" width="28%" nowrap><a href="javascript:cmdAdd()">Bagus 
                                            Point </a></td>
                                          <td class="listcontent" width="43%" nowrap>Description 
                                            ...... </td>
                                        </tr>
                                        <tr> 
                                          <td class="listcontent" width="28%" nowrap>Honesty</td>
                                          <td class="listcontent" width="43%" nowrap>....</td>
                                        </tr>
                                        <tr> 
                                          <td class="listcontent" width="28%" nowrap>Up-Selling 
                                            Point </td>
                                          <td class="listcontent" width="43%" nowrap>&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td class="listcontent" width="28%" nowrap>HMWSB 
                                            Point </td>
                                          <td class="listcontent" width="43%" nowrap>&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td class="listcontent" width="28%" nowrap>......</td>
                                          <td class="listcontent" width="43%" nowrap>&nbsp;</td>
                                        </tr>
                                      </table></td>
									  </tr>
									</table>
									
                                      
                                    </td>
                                  </tr>
                                  <tr> 
                                    <td width="20%"> 
                                      <table width="100%" border="0" cellspacing="2" cellpadding="0">
                                        <tr> 
                                          <td>List 1 -3, Total 3</td>
                                        </tr>
                                        <tr> 
                                          <td>&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td> 
                                            <table width="150" border="0" cellspacing="0" cellpadding="0">
                                              <tr align="center"> 
                                                <td width="30"><a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image23','','<%=approot%>/images/first.jpg',1)"><img name="Image23" border="0" src="<%=approot%>/images/first_f2.jpg" width="28" height="27" alt="First"></a></td>
                                                <td width="30"><a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image24','','<%=approot%>/images/prev.jpg',1)"><img name="Image24" border="0" src="<%=approot%>/images/prev_f2.jpg" width="28" height="27" alt="Previous"></a></td>
                                                <td width="30"><a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image25','','<%=approot%>/images/next.jpg',1)"><img name="Image25" border="0" src="<%=approot%>/images/next_f2.jpg" width="28" height="27" alt="Next"></a></td>
                                                <td width="30"><a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image26','','<%=approot%>/images/last.jpg',1)"><img name="Image26" border="0" src="<%=approot%>/images/last_f2.jpg" width="28" height="27" alt="Last"></a></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                      </table>
                                    </td>
                                  </tr>
                                  <%if(!(iCommand==CMD_ADD || iCommand==CMD_LIST || iCommand==CMD_ASK)){%>
                                  <tr> 
                                    <td> 
                                      <table width="100%" border="0" cellspacing="2" cellpadding="0">
                                        <tr> 
                                          <td colspan="2" width="77%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td colspan="2" width="77%"> 
                                            <table width="13%" border="0" cellspacing="0" cellpadding="0">
                                              <tr> 
                                                <td width="17%"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/add_f2.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/add.jpg" width="28" height="25" alt="Add new training"></a></td>
                                                <td width="10%"><img src="../images/spacer.gif" width="1" height="1"></td>
                                                <td width="73%" class="buttonlink"><a href="javascript:cmdAdd()" class="command">Add 
                                                  New </a></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                      </table>
                                    </td>
                                  </tr>
                                  <%}else{%>
                                  <tr> 
                                    <td width="20%"> 
                                      <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                        <tr> 
                                          <td width="12%">&nbsp;</td>
                                          <td width="3%">&nbsp;</td>
                                          <td width="85%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="12%"><%=dictionaryD.getWord(I_Dictionary.AWARD) %></td>
                                          <td width="3%"> 
                                            <div align="center">:</div>
                                          </td>
                                          <td width="85%"> 
                                            <input type="text" name="training" size="30" value="Bagus Point">
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="12%" valign="top">Description</td>
                                          <td width="3%" valign="top"> 
                                            <div align="center">:</div>
                                          </td>
                                          <td width="85%"> 
                                            <textarea name="graduation" cols="40" rows="3">Description ....</textarea>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="12%" height="20">&nbsp;</td>
                                          <td width="3%" height="20">&nbsp;</td>
                                          <td width="85%" height="20">&nbsp;</td>
                                        </tr>
                                        <%if(iCommand==CMD_ASK){%>
                                        <tr> 
                                          <td colspan="4" class="msgquestion">Are 
                                            you sure to delete ?</td>
                                        </tr>
                                        <%}%>
                                        <tr> 
                                          <td width="12%">&nbsp;</td>
                                          <td width="3%">&nbsp;</td>
                                          <td width="85%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3"> 
                                            <table border="0" cellspacing="0" cellpadding="0">
                                              <tr> 
                                                <%if(iCommand!=CMD_ASK){%>
                                                <td><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image2611','','<%=approot%>/images/save_f2.jpg',1)"><img name="Image2611" border="0" src="<%=approot%>/images/save.jpg" width="28" height="25" alt="Save"></a></td>
                                                <td class="command">&nbsp;<a href="javascript:cmdBack()">Save</a></td>
                                                <td>&nbsp;&nbsp;<a href="javascript:cmdAsk()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image2612','','<%=approot%>/images/delete_f2.jpg',1)"><img name="Image2612" border="0" src="<%=approot%>/images/delete.jpg" width="28" height="25" alt="Delete"></a></td>
                                                <td class="buttonlink">&nbsp;<a href="javascript:cmdAsk()" class="command">Delete</a></td>
                                                <td>&nbsp;&nbsp;<a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image2613','','<%=approot%>/images/e_home/back.jpg',1)"><img name="Image2613" border="0" src="<%=approot%>/images/e_home/back_f2.jpg" width="28" height="27" alt="Back to list"></a></td>
                                                <td class="buttonlink">&nbsp;<a href="javascript:cmdBack()" class="command">Back 
                                                  To List</a></td>
                                                <%}if(iCommand==CMD_ASK){%>
                                                <td><a href="javascript:cmdDel()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image2614','','<%=approot%>/images/delete_f2.jpg',1)"><img name="Image2614" border="0" src="<%=approot%>/images/delete.jpg" width="28" height="25" alt="Yes Delete"></a></td>
                                                <td class="buttonlink">&nbsp;<a href="javascript:cmdDel()" class="command">Yes 
                                                  Delete </a></td>
                                                <td >&nbsp;&nbsp;<a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image2615','','<%=approot%>/images/save_f2.jpg',1)"><img name="Image2615" border="0" src="<%=approot%>/images/save.jpg" width="28" height="25" alt="Cancel"></a></td>
                                                <td class="buttonlink">&nbsp;<a href="javascript:cmdBack()" class="command">Cancel</a></td>
                                                <%}%>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                      </table>
                                    </td>
                                    <%}%>
                                  </tr>
                                </table>
                              </form>
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
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --><%@ include file = "../main/footer.jsp" %><!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
