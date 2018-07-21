 
<%@ page language="java" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_CLINIC, AppObjInfo.G2_EMPLOYEE_VISIT, AppObjInfo.OBJ_EMPLOYEE_VISIT); %>

<!-- JSP Block -->

<%!
public static int CMD_NONE = 0;
public static int CMD_ADD = 1;
public static int CMD_LIST = 2;
public static int CMD_BACK = 3;
public static int CMD_EDIT = 4;
public static int CMD_ASK = 5;
//public static int CMD_DELETE = 6;
%>
<%
 
 int iCommand = Integer.parseInt((request.getParameter("command")== null) ? "0" : request.getParameter("command"));
 String code = request.getParameter("hcode");
 String name = request.getParameter("hname");
 String category = request.getParameter("hcategory");
 String producer = request.getParameter("hproducer");
 String amount = request.getParameter("hamount");
 String remark = request.getParameter("hremark");
 //out.println(iCommand);
 //out.println(code + name + category + producer + amount + remark);
%>
<!-- End of JSP Block -->
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HRIS - Employee Visit</title>
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
		document.frmEmpVisit.command.value="<%=CMD_ADD%>";
		document.frmEmpVisit.action="<%=approot%>/clinic/empvisit/empvisit.jsp";
		document.frmEmpVisit.submit();		
	}

	function cmdList(){
		document.frmEmpVisit.command.value="<%=CMD_LIST%>";
		document.frmEmpVisit.action="<%=approot%>/clinic/empvisit/empvisit.jsp";
		document.frmEmpVisit.submit();			
	}

	function cmdBack(){
		document.frmEmpVisit.command.value="<%=CMD_BACK%>";
		document.frmEmpVisit.action="<%=approot%>/clinic/empvisit/empvisit.jsp";
		document.frmEmpVisit.submit();			
	}
	
	function cmdEdit(code, name, category, amount, remark){
		//alert(code + name + category + producer + amount + remark);
		document.frmEmpVisit.hcode.value = code;
		document.frmEmpVisit.hname.value = name;
		document.frmEmpVisit.hcategory.value = category;
		//document.frmEmpVisit.hproducer.value = producer;
		document.frmEmpVisit.hamount.value = amount;
		document.frmEmpVisit.hremark.value = remark;
		document.frmEmpVisit.command.value="<%=CMD_EDIT%>";
		document.frmEmpVisit.action="<%=approot%>/clinic/empvisit/empvisit.jsp";
		document.frmEmpVisit.submit();			
	}
	
	function cmdAsk(code, name, category, amount, remark){
		document.frmEmpVisit.hcode.value = code;
		document.frmEmpVisit.hname.value = name;
		document.frmEmpVisit.hcategory.value = category;
		//document.frmEmpVisit.hproducer.value = producer;
		document.frmEmpVisit.hamount.value = amount;
		document.frmEmpVisit.hremark.value = remark;
		document.frmEmpVisit.command.value="<%=CMD_ASK%>";
		document.frmEmpVisit.action="<%=approot%>/clinic/empvisit/empvisit.jsp";
		document.frmEmpVisit.submit();		
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
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
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

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/first.jpg','<%=approot%>/images/prev.jpg','<%=approot%>/images/next.jpg','<%=approot%>/images/last.jpg','<%=approot%>/images/add_f2.jpg','<%=approot%>/images/e_home/save_f2.jpg','<%=approot%>/images/e_home/back.jpg','<%=approot%>/images/e_home/delete_f2.jpg')">
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
		  <!-- #EndEditable --> 
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
                            <td valign="top"> <font color="#FF8080" face="Arial"><strong>
		  Clinic > Employee Visit</strong></font>
                              <form name="frmEmpVisit" method="post" action="clinic/empvisit/empvisit.jsp">
							  	<input type="hidden" name="command" value="<%=CMD_NONE%>">
								<input type="hidden" name="hcode" value="">
								<input type="hidden" name="hname" value="">
								<input type="hidden" name="hcategory" value="">
								<input type="hidden" name="hproducer" value="">
								<input type="hidden" name="hamount" value="">
								<input type="hidden" name="hremark" value="">
                                <table width="100%" border="0" cellspacing="3" cellpadding="1">
                                  <tr> 
                                    <td class="tableheader" valign="top"> 
                                      <table width="100%" border="0" cellspacing="1" cellpadding="2" class="listbg">
                                        <tr> 
                                          <td class="listheader"><b><font color="#FFFFFF">Employee 
                                            ID </font></b></td>
                                          <td class="listheader"><b><font color="#FFFFFF">Name</font></b></td>
                                          <td class="listheader"><b><font color="#FFFFFF">Visit 
                                            Date </font></b></td>
                                          <td class="listheader"><b><font color="#FFFFFF">Expenses</font></b></td>
                                          <td class="listheader"><b><font color="#FFFFFF">Remark</font></b></td>
                                        </tr>
                                        <tr> 
                                          <td class="listcontent" id="code1"><span style="color:blue" onclick="javascript:cmdEdit(document.all.code1.innerText, document.all.name1.innerText, document.all.ctg1.innerText, document.all.amt1.innerText, document.all.rem1.innerText);" onmouseover="this.style.cursor='hand';this.style.color='red';" onmouseout="this.style.cursor='default';this.style.color='blue';"><u>19355</u></span></td>
                                          <td class="listcontent" nowrap id="name1">Made 
                                            Wirasuta </td>
                                          <td class="listcontent" id="ctg1">19-Jan-1999</td>
                                          <td class="listcontent" id="amt1" align="right">52.500</td>
                                          <td class="listcontent" id="rem1">-</td>
                                        </tr>
                                        <tr> 
                                          <td class="listcontent" id="code2"><span style="color:blue" onclick="javascript:cmdEdit(document.all.code2.innerText, document.all.name2.innerText, document.all.ctg2.innerText, document.all.amt2.innerText, document.all.rem2.innerText);" onmouseover="this.style.cursor='hand';this.style.color='red';" onmouseout="this.style.cursor='default';this.style.color='blue';"><u>18732</u></span></td>
                                          <td class="listcontent" nowrap id="name2">Soekirman</td>
                                          <td class="listcontent" id="ctg2">2-May-2001</td>
                                          <td class="listcontent" id="amt2" align="right">218.750</td>
                                          <td class="listcontent" id="rem2">Sent 
                                            to RS. Sanglah for thorough checking</td>
                                        </tr>
                                        <tr> 
                                          <td class="listcontent" id="code3"><span style="color:blue" onclick="javascript:cmdEdit(document.all.code3.innerText, document.all.name3.innerText, document.all.ctg3.innerText, document.all.amt3.innerText, document.all.rem3.innerText);" onmouseover="this.style.cursor='hand';this.style.color='red';" onmouseout="this.style.cursor='default';this.style.color='blue';"><u>20112</u></span></td>
                                          <td class="listcontent" nowrap id="name3">Florentina 
                                            W. Supangat</td>
                                          <td class="listcontent" id="ctg3">29-Oct-2000</td>
                                          <td class="listcontent" id="amt3" align="right">25.000</td>
                                          <td class="listcontent" id="rem3">-</td>
                                        </tr>
                                        <tr> 
                                          <td class="listcontent" id="code4"><span style="color:blue" onclick="javascript:cmdEdit(document.all.code4.innerText, document.all.name4.innerText, document.all.ctg4.innerText, document.all.amt4.innerText, document.all.rem4.innerText);" onmouseover="this.style.cursor='hand';this.style.color='red';" onmouseout="this.style.cursor='default';this.style.color='blue';"><u>19872</u></span></td>
                                          <td class="listcontent" nowrap id="name4">Djoko 
                                            Margosoewignyo </td>
                                          <td class="listcontent" id="ctg4">5-Apr-2001</td>
                                          <td class="listcontent" id="amt4" align="right">152.100</td>
                                          <td class="listcontent" id="rem4">-</td>
                                        </tr>
                                        <tr> 
                                          <td class="listcontent" id="code5"><span style="color:blue" onclick="javascript:cmdEdit(document.all.code5.innerText, document.all.name5.innerText, document.all.ctg5.innerText, document.all.amt5.innerText, document.all.rem5.innerText);" onmouseover="this.style.cursor='hand';this.style.color='red';" onmouseout="this.style.cursor='default';this.style.color='blue';"><u>18849</u></span></td>
                                          <td class="listcontent" nowrap id="name5">W. 
                                            Arthanaya </td>
                                          <td class="listcontent" id="ctg5">14-Jul-2000</td>
                                          <td class="listcontent" id="amt5" align="right">32.950</td>
                                          <td class="listcontent" id="rem5">-</td>
                                        </tr>
                                      </table>
                                    </td>
                                  </tr>
                                  <tr>
                                    <td width="20%"> 
                                      <table width="100%" border="0" cellspacing="2" cellpadding="0">
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
									  <%
									    //if(iCommand!=CMD_NONE || iCommand!=CMD_BACK)
									    if((iCommand != CMD_ADD) && (iCommand != CMD_EDIT)){%>
                                      <table width="100%" border="0" cellspacing="2" cellpadding="0">
                                        <tr> 
                                          <td colspan="2" width="77%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td colspan="2" width="77%"> 
                                            <table width="20%" border="0" cellspacing="0" cellpadding="0">
                                              <tr> 
                                                <td width="16%"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/add_f2.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/add.jpg" width="28" height="25" alt="Add new training"></a></td>
                                                <td width="5%"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                <td width="79%" class="buttonlink"><a href="javascript:cmdAdd()" class="command">Add 
                                                  New Employee Visit</a></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                      </table>
									  <%}%>
                                    </td>
                                  </tr>
								  <%if(iCommand==CMD_ADD || iCommand==CMD_LIST){%>
                                  <tr> 
                                    <td width="20%"> 
                                      <table width="100%" border="0" cellspacing="2" cellpadding="0">
                                        <tr> 
                                          <td width="8%">&nbsp;</td>
                                          <td width="92%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td colspan="2"><b>Add</b> </td>
                                        </tr>
                                        <tr> 
                                          <td width="8%">&nbsp;</td>
                                          <td width="92%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="8%">Employee ID</td>
                                          <td width="92%"> 
                                            <input type="text" name="code" size="40" value="">
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="8%">Name</td>
                                          <td width="92%"> 
                                            <input type="text" name="name" size="40" value="">
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="8%">Visit Date</td>
                                          <td width="92%"> 
                                            <%-- <input type="text" name="category" size="40" value=""> --%>
                                            <select name="select2">
                                              <option value="0">Jan</option>
                                              <option value="1">Feb</option>
                                              <option value="2">Mar</option>
                                              <option value="3">Apr</option>
                                              <option value="4">May</option>
                                              <option value="5">Jun</option>
                                              <option value="6" selected>Jul</option>
                                              <option value="7">Aug</option>
                                              <option value="8">Sep</option>
                                              <option value="9">Oct</option>
                                              <option value="10">Nov</option>
                                              <option value="11">Dec</option>
                                            </select>
                                            <select name="select2">
                                              <option value="1">01</option>
                                              <option value="2">02</option>
                                              <option value="3">03</option>
                                              <option value="4">04</option>
                                              <option value="5">05</option>
                                              <option value="6">06</option>
                                              <option value="7">07</option>
                                              <option value="8">08</option>
                                              <option value="9">09</option>
                                              <option value="10">10</option>
                                              <option value="11">11</option>
                                              <option value="12">12</option>
                                              <option value="13">13</option>
                                              <option value="14">14</option>
                                              <option value="15">15</option>
                                              <option value="16">16</option>
                                              <option value="17">17</option>
                                              <option value="18">18</option>
                                              <option value="19">19</option>
                                              <option value="20" selected>20</option>
                                              <option value="21">21</option>
                                              <option value="22">22</option>
                                              <option value="23">23</option>
                                              <option value="24">24</option>
                                              <option value="25">25</option>
                                              <option value="26">26</option>
                                              <option value="27">27</option>
                                              <option value="28">28</option>
                                              <option value="29">29</option>
                                              <option value="30">30</option>
                                              <option value="31">31</option>
                                            </select>
                                            , 
                                            <select name="select2">
                                              <option value="2002" selected>2002</option>
                                              <option value="2003">2003</option>
                                            </select>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="8%">Expenses</td>
                                          <td width="92%"> 
                                            <input type="text" name="amount" size="40" value="">
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="8%">Remark</td>
                                          <td width="92%"> 
                                            <input type="text" name="remark" size="40" value="">
                                          </td>
                                        </tr>
                                      </table>
                                      <table width="100%" border="0" cellspacing="2" cellpadding="0">
                                        <tr> 
                                          <td colspan="2" width="77%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td colspan="2" width="77%"> 
                                            <table width="20%" border="0" cellspacing="0" cellpadding="0">
                                              <tr> 
                                                                        <td width="12%"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image26121','','<%=approot%>/images/e_home/save_f2.jpg',1)"><img name="Image26121" border="0" src="<%=approot%>/images/e_home/save.jpg" width="28" height="25" alt="Save"></a></td>
                                                <td width="3%"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                <td width="31%" class="buttonlink"><a href="javascript:cmdBack()" class="command">Save</a></td>
                                                                        <td width="12%"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image26111','','<%=approot%>/images/e_home/back.jpg',1)"><img name="Image26111" border="0" src="<%=approot%>/images/e_home/back_f2.jpg" width="28" height="27" alt="Back to list"></a></td>
                                                <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                <td width="40%" class="buttonlink"><a href="javascript:cmdBack()" class="command">Back 
                                                  To List</a></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                      </table>
                                    </td>
                                  </tr>
								  <script language="javascript">
								  	document.frmEmpVisit.code.focus();
								  </script>
	  							  <%}%>
								  <%if(iCommand==CMD_EDIT){%>
                                  <tr> 
                                    <td width="20%"> 
                                      <table width="100%" border="0" cellspacing="2" cellpadding="0">
                                        <tr> 
                                          <td width="8%">&nbsp;</td>
                                          <td width="92%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td colspan="2"><b>Edit</b> </td>
                                        </tr>
                                        <tr> 
                                          <td width="8%">&nbsp;</td>
                                          <td width="92%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="8%">Employee ID</td>
                                          <td width="92%"> 
                                            <input type="text" name="code" size="40" value="<%=code%>">
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="8%">Name</td>
                                          <td width="92%"> 
                                            <input type="text" name="name" size="40" value="<%=name%>">
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="8%">Visit Date</td>
                                          <td width="92%"> 
                                            <%-- <input type="text" name="category" size="40" value="%=category%"> --%>
                                            <select name="select1">
                                              <option value="0">Jan</option>
                                              <option value="1">Feb</option>
                                              <option value="2">Mar</option>
                                              <option value="3">Apr</option>
                                              <option value="4">May</option>
                                              <option value="5">Jun</option>
                                              <option value="6" selected>Jul</option>
                                              <option value="7">Aug</option>
                                              <option value="8">Sep</option>
                                              <option value="9">Oct</option>
                                              <option value="10">Nov</option>
                                              <option value="11">Dec</option>
                                            </select>
                                            <select name="select2">
                                              <option value="1">01</option>
                                              <option value="2">02</option>
                                              <option value="3">03</option>
                                              <option value="4">04</option>
                                              <option value="5">05</option>
                                              <option value="6">06</option>
                                              <option value="7">07</option>
                                              <option value="8">08</option>
                                              <option value="9">09</option>
                                              <option value="10">10</option>
                                              <option value="11">11</option>
                                              <option value="12">12</option>
                                              <option value="13">13</option>
                                              <option value="14">14</option>
                                              <option value="15">15</option>
                                              <option value="16">16</option>
                                              <option value="17">17</option>
                                              <option value="18">18</option>
                                              <option value="19">19</option>
                                              <option value="20" selected>20</option>
                                              <option value="21">21</option>
                                              <option value="22">22</option>
                                              <option value="23">23</option>
                                              <option value="24">24</option>
                                              <option value="25">25</option>
                                              <option value="26">26</option>
                                              <option value="27">27</option>
                                              <option value="28">28</option>
                                              <option value="29">29</option>
                                              <option value="30">30</option>
                                              <option value="31">31</option>
                                            </select>
                                            , 
                                            <select name="select2">
                                              <option value="2002" selected>2002</option>
                                              <option value="2003">2003</option>
                                            </select>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="8%">Expenses</td>
                                          <td width="92%"> 
                                            <input type="text" name="amount" size="40" value="<%=amount%>">
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="8%">Remark</td>
                                          <td width="92%"> 
                                            <input type="text" name="remark" size="40" value="<%=remark%>">
                                          </td>
                                        </tr>
                                      </table>
                                      <table width="100%" border="0" cellspacing="2" cellpadding="0">
                                        <tr> 
                                          <td colspan="2" width="77%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td colspan="2" width="77%"> 
                                            <table width="31%" border="0" cellspacing="0" cellpadding="0">
                                              <tr> 

                                                                        <td width="12%"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image26122','','<%=approot%>/images/e_home/save_f2.jpg',1)"><img name="Image26122" border="0" src="<%=approot%>/images/e_home/save.jpg" width="28" height="25" alt="Save"></a></td>
                                                <td width="3%"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                <td width="20%" class="buttonlink"><a href="javascript:cmdBack()" class="command">Save</a></td>
                                                                        <td width="12%"><a href="javascript:cmdAsk(document.all.code.value, document.all.name.value, document.all.select1.value, document.all.select2.value, document.all.amount.value, document.all.remark.value);" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image26131','','<%=approot%>/images/e_home/delete_f2.jpg',1)"><img name="Image26131" border="0" src="<%=approot%>/images/e_home/delete.jpg" width="28" height="25" alt="Delete"></a></td>
                                                <td width="3%"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                <td width="20%" class="buttonlink"><a href="javascript:cmdAsk(document.all.code.value, document.all.name.value, document.all.select1.value, document.all.select2.value, document.all.amount.value, document.all.remark.value)" class="command">Delete</a></td>
                                                                        <td width="12%"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image26112','','<%=approot%>/images/e_home/back.jpg',1)"><img name="Image26112" border="0" src="<%=approot%>/images/e_home/back_f2.jpg" width="28" height="27" alt="Back to list"></a></td>
                                                <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                <td width="40%" class="buttonlink" nowrap><a href="javascript:cmdBack()" class="command">Back To List</a></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                      </table>
                                    </td>
                                  </tr>
								  <script language="javascript">
								  	document.frmEmpVisit.code.focus();
								  </script>
	  							  <%}%>
								  <%if(iCommand==CMD_ASK){%>
                                  <tr> 
                                    <td width="20%"> 
                                      <table width="100%" border="0" cellspacing="2" cellpadding="0">
                                        <tr> 
                                          <td width="8%">&nbsp;</td>
                                          <td width="92%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="8%">Employee ID</td>
                                          <td width="92%"> 
                                            <input type="text" name="code" size="40" value="<%=code%>">
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="8%">Name</td>
                                          <td width="92%"> 
                                            <input type="text" name="name" size="40" value="<%=name%>">
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="8%">Visit Date</td>
                                          <td width="92%"> 
                                            <%-- <input type="text" name="category" size="40" value="%=category%"> --%>
                                            <select name="select2">
                                              <option value="0">Jan</option>
                                              <option value="1">Feb</option>
                                              <option value="2">Mar</option>
                                              <option value="3">Apr</option>
                                              <option value="4">May</option>
                                              <option value="5">Jun</option>
                                              <option value="6" selected>Jul</option>
                                              <option value="7">Aug</option>
                                              <option value="8">Sep</option>
                                              <option value="9">Oct</option>
                                              <option value="10">Nov</option>
                                              <option value="11">Dec</option>
                                            </select>
                                            <select name="select2">
                                              <option value="1">01</option>
                                              <option value="2">02</option>
                                              <option value="3">03</option>
                                              <option value="4">04</option>
                                              <option value="5">05</option>
                                              <option value="6">06</option>
                                              <option value="7">07</option>
                                              <option value="8">08</option>
                                              <option value="9">09</option>
                                              <option value="10">10</option>
                                              <option value="11">11</option>
                                              <option value="12">12</option>
                                              <option value="13">13</option>
                                              <option value="14">14</option>
                                              <option value="15">15</option>
                                              <option value="16">16</option>
                                              <option value="17">17</option>
                                              <option value="18">18</option>
                                              <option value="19">19</option>
                                              <option value="20" selected>20</option>
                                              <option value="21">21</option>
                                              <option value="22">22</option>
                                              <option value="23">23</option>
                                              <option value="24">24</option>
                                              <option value="25">25</option>
                                              <option value="26">26</option>
                                              <option value="27">27</option>
                                              <option value="28">28</option>
                                              <option value="29">29</option>
                                              <option value="30">30</option>
                                              <option value="31">31</option>
                                            </select>
                                            , 
                                            <select name="select2">
                                              <option value="2002" selected>2002</option>
                                              <option value="2003">2003</option>
                                            </select>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="8%">Expenses</td>
                                          <td width="92%"> 
                                            <input type="text" name="amount" size="40" value="<%=amount%>">
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="8%">Remark</td>
                                          <td width="92%"> 
                                            <input type="text" name="remark" size="40" value="<%=remark%>">
                                          </td>
                                        </tr>
                                      </table>
                                      <table width="100%" border="0" cellspacing="2" cellpadding="0">
                                        <tr> 
                                          <td colspan="2" width="77%">&nbsp;</td>
                                        </tr>
									  </table>
                                      <table width="100%" border="0" cellspacing="2" cellpadding="0">
                                        <tr> 
                                          <td colspan="2" width="77%" class="msgquestion">Are you sure to delete this record?</td>
                                        </tr>
                                       <tr> 
                                          <td colspan="2" width="77%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td colspan="2" width="77%"> 
                                            <table width="38%" border="0" cellspacing="0" cellpadding="0">
                                              <tr> 
                                                <td width="8%"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image2612','','<%=approot%>/images/e_home/save_f2.jpg',1)"><img name="Image2612" border="0" src="<%=approot%>/images/e_home/save.jpg" width="28" height="25" alt="Save"></a></td>
                                                <td width="1%"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                <td width="16%" class="buttonlink"><a href="javascript:cmdBack()" class="command">Cancel</a></td>
                                                <td width="4%"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image2613','','<%=approot%>/images/e_home/delete_f2.jpg',1)"><img name="Image2613" border="0" src="<%=approot%>/images/e_home/delete.jpg" width="28" height="25" alt="Delete"></a></td>
                                                <td width="0%"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                <td width="22%" class="buttonlink" nowrap><a href="javascript:cmdBack()" class="command">Yes 
                                                  Delete</a></td>
                                                <td width="10%"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image2611','','<%=approot%>/images/e_home/back.jpg',1)"><img name="Image2611" border="0" src="<%=approot%>/images/e_home/back_f2.jpg" width="28" height="27" alt="Back to list"></a></td>
                                                <td width="0%"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                <td width="39%" class="buttonlink" nowrap><a href="javascript:cmdBack()" class="command">Back 
                                                  To List</a></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                      </table>
                                    </td>
                                  </tr>
	  							  <%}%>
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
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" -->
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #EndTemplate --></html>
