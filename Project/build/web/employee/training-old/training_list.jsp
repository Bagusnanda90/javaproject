<%@ page language="java" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = 1;//AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE , AppObjInfo.G2_ATTENDANCE   , AppObjInfo.OBJ_DAY_OFF_PAYMENT   	); %>
<!-- JSP Block --> 
<%!
public static int CMD_NONE = 0; 
%>
<!-- End of JSP Block -->
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Training</title>
<script language="JavaScript">
	function cmdLink(){
		document.frmTraining.command.value="<%=CMD_NONE%>";
		document.frmTraining.action="<%=approot%>/employee/training/training_participant.jsp";
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
<link rel="stylesheet" href="../../clinic/medexpense/styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../clinic/medexpense/styles/tab.css" type="text/css">
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

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/first.jpg','<%=approot%>/images/prev.jpg','<%=approot%>/images/next.jpg','<%=approot%>/images/last.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --><%@ include file = "../../main/header.jsp" %><!-- #EndEditable --> 
    </td>
  </tr> 
  <tr> 
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --><%@ include file = "../..//main/mnmain.jsp" %><!-- #EndEditable --> </td> 
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
                              <form name="frmTraining" method="post" action="../../clinic/medexpense/masterdata/training.jsp">
                                <input type="hidden" name="command">
                                <table width="100%" border="0" cellspacing="3" cellpadding="1">
								  <tr> 
                                    <td class="listtitle" valign="top">Employee 
                                      &gt; Training &gt; List</td>
                                  </tr>																
                                  <tr> 
                                    <td class="tableheader" valign="top"> 
                                      <table width="100%" border="0" cellspacing="1" cellpadding="2" class="listbg">
                                        <tr> 
                                          <td class="listheader" rowspan="2" height="37" width="10%">
                                            <div align="center">Training Code 
                                            </div>
                                          </td>
                                          <td class="listheader" rowspan="2" height="37" width="40%">
                                            <div align="center"><b><font color="#FFFFFF">Training 
                                              Name</font></b></div>
                                          </td>
                                          <td class="listheader" rowspan="2" height="37" width="10%">
                                            <div align="center"><b><font color="#FFFFFF">Graduation</font></b></div>
                                          </td>
                                          <td class="listheader" rowspan="2" height="37" width="10%">
                                            <div align="center">Total Hours </div>
                                          </td>
                                          <td class="listheader" colspan="2" height="18">
                                            <div align="center">Period</div>
                                          </td>
                                          <td class="listheader" rowspan="2" height="37">
                                            <div align="center"><b>Directed by 
                                              </b></div>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td class="listheader" width="10%" height="18">
                                            <div align="center"><b><font color="#FFFFFF">From</font></b></div>
                                          </td>
                                          <td class="listheader" width="10%" height="18">
                                            <div align="center"><b><font color="#FFFFFF">To</font></b></div>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td class="listcontent" width="10%"><a href="javascript:cmdLink()">T001</a></td>
                                          <td class="listcontent" width="40%">Computer 
                                            Training</td>
                                          <td class="listcontent" width="10%" nowrap>Sertificate</td>
                                          <td class="listcontent" width="10%" nowrap>40 
                                            hours </td>
                                          <td class="listcontent" width="10%" nowrap>May 
                                            20, 2002</td>
                                          <td class="listcontent" width="10%">May 
                                            30, 2002</td>
                                          <td class="listcontent" width="10%">HR 
                                            Department </td>
                                        </tr>
                                        <tr> 
                                          <td class="listcontent" width="10%">T002</td>
                                          <td class="listcontent" width="40%">Cook 
                                            Training </td>
                                          <td class="listcontent" width="10%" nowrap>Sertificate</td>
                                          <td class="listcontent" width="10%" nowrap>50 
                                            hours </td>
                                          <td class="listcontent" width="10%" nowrap>Jun 
                                            20, 2002</td>
                                          <td class="listcontent" width="10%">Jun 
                                            30, 2002</td>
                                          <td class="listcontent" width="10%">HR 
                                            Department</td>
                                        </tr>
                                        <tr> 
                                          <td class="listcontent" width="10%">...</td>
                                          <td class="listcontent" width="40%">...</td>
                                          <td class="listcontent" width="10%" nowrap>...</td>
                                          <td class="listcontent" width="10%" nowrap>...</td>
                                          <td class="listcontent" width="10%" nowrap>...</td>
                                          <td class="listcontent" width="10%">...</td>
                                          <td class="listcontent" width="10%">...</td>
                                        </tr>
                                      </table>
                                    </td>
                                  </tr>
                                  <tr> 
                                    <td width="20%"> 
                                      <table width="100%" border="0" cellspacing="2" cellpadding="0">
                                        <tr> 
                                          <td>List 1-3, Total 3</td>
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
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --><%@ include file = "../../main/footer.jsp" %><!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->&nbsp;{script}<!-- #EndEditable -->
<!-- #EndTemplate --></html>
