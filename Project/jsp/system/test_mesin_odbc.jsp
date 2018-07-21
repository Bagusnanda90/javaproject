<%-- 
    Document   : test_mesin_odbc
    Created on : Nov 14, 2013, 9:13:35 PM
    Author     : RAMA
--%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.util.Formater"%>
<%@page import="com.dimata.qdep.db.I_DBType"%>
<%@page import="com.dimata.harisma.utility.machine.testmachine.QueryRecordHeader"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.harisma.utility.machine.testmachine.QueryResult"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.utility.machine.testmachine.TestMachine"%>
<%@page import="com.dimata.harisma.utility.machine.testmachine.FrmTestMachine"%>
<%@page import="com.dimata.harisma.utility.machine.testmachine.CtrlTestMachine"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_DIVISION); %>
<%@ include file = "../main/checkuser.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<%!

public String drawList(JspWriter outObj,QueryResult queryResult,int start)
{
   
	ControlList ctrlist = new ControlList();
	
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	
        if(queryResult!=null && queryResult.getSizeHeader()!=0){
                ctrlist.addHeader("No","10%");
            for(int x=0;x<queryResult.getSizeHeader();x++){
                String namaField= (String) queryResult.getvHeader(x).getTitle();
                ctrlist.addHeader(""+namaField,"10%");
            }
            ctrlist.setLinkRow(0);
            ctrlist.setLinkSufix("");
            Vector lstData = ctrlist.getData();
            Vector lstLinkData = ctrlist.getLinkData();
            ctrlist.setLinkPrefix("javascript:cmdEdit('");
            ctrlist.setLinkSufix("')");
            ctrlist.reset();
            
            //for(int idx=0; idx<queryResult.getContentSize();idx++){ 
                  
                  try{
                 Vector vIsiContent = queryResult.getContent(); 
                 //int no=0;
                 if(vIsiContent!=null && vIsiContent.size()>0){
                     ctrlist.drawListHeaderWithJsVer2(outObj);
                        for(int idxcon=0;idxcon<vIsiContent.size();idxcon++){
                            start = start + 1;
                                //rowx.add(""+no);
                                Vector rowx  =queryResult.getContentData(idxcon,start); 
                               lstData.add(rowx);
                                ctrlist.drawListRowJsVer2(outObj, 0, rowx, idxcon);
                        }
                     ctrlist.drawListEndTableJsVer2(outObj);
                 }
                    
                  
                  }catch(Exception exc){
                      System.out.println("Error"+exc);
                  }
                  
            //}
        }
    
	
	return "";
}
%>

<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
int iErrCode = FRMMessage.NONE;
ControlLine ctrLine = new ControlLine();  

CtrlTestMachine ctrlTestMachine= new CtrlTestMachine(request);

iErrCode = ctrlTestMachine.action(iCommand);
/* end switch*/
FrmTestMachine frmTestMachine= ctrlTestMachine.getForm();

TestMachine testMachine= ctrlTestMachine.getMachine();
QueryResult queryResult = ctrlTestMachine.getQueryResult();
int vectSize = testMachine.getTotalRecordQuery();
int recordToGet = testMachine.isUseLimit()?testMachine.getUseLimitValue():0;
if(iCommand==Command.FIRST || iCommand==Command.PREV || iCommand==Command.NEXT || iCommand==Command.LAST)
{
	 try
	 { 
		queryResult = (QueryResult)session.getValue("SESS_QUERY_RESULT");
                testMachine = (TestMachine)session.getValue("SESS_MECHINE_TEST");
                vectSize = testMachine.getTotalRecordQuery();
                recordToGet = testMachine.isUseLimit()?testMachine.getUseLimitValue():0; 
	 }
	 catch(Exception e)
	 { 
		queryResult = new QueryResult();
	 }
}
if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlTestMachine.actionList(iCommand, start, vectSize, recordToGet);
 } 
session.putValue("SESS_QUERY_RESULT", queryResult);
session.putValue("SESS_MECHINE_TEST", testMachine);
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Mesin ODBC</title>
 <%@ include file = "../main/konfigurasi_jquery.jsp" %>    
<script type="text/javascript" src="../javascripts/jquery.min-1.6.2.js"></script>
<!-- #BeginEditable "styles" --> 
        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
        <link rel="stylesheet" href="../styles/tab.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
        
            <script type="text/javascript" src="../javascripts/jquery.min.js"></script>
    <script type="text/javascript" src="../javascripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../javascripts/gridviewScroll.min.js"></script>
    <link href="../stylesheets/GridviewScroll.css" rel="stylesheet" />
<script language="JavaScript">


function cmdTest(){
	
	document.<%=FrmTestMachine.FRM_NAME%>.command.value="<%=Command.OK%>";
       
	document.<%=FrmTestMachine.FRM_NAME%>.action="test_mesin_odbc.jsp";
	document.<%=FrmTestMachine.FRM_NAME%>.submit();
}

function cmdExcecute(){
	
	document.<%=FrmTestMachine.FRM_NAME%>.command.value="<%=Command.LIST%>";
         document.<%=FrmTestMachine.FRM_NAME%>.start.value="0";
	document.<%=FrmTestMachine.FRM_NAME%>.action="test_mesin_odbc.jsp";
	document.<%=FrmTestMachine.FRM_NAME%>.submit();
}

 function cmdChangeTypeQuery(){
                document.<%=FrmTestMachine.FRM_NAME%>.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.<%=FrmTestMachine.FRM_NAME%>.action="test_mesin_odbc.jsp";
                document.<%=FrmTestMachine.FRM_NAME%>.submit();
            }
     function cmdListFirst(){
		document.<%=FrmTestMachine.FRM_NAME%>.command.value="<%=Command.FIRST%>";
		document.<%=FrmTestMachine.FRM_NAME%>.action="test_mesin_odbc.jsp";
                document.<%=FrmTestMachine.FRM_NAME%>.target = "";
		document.<%=FrmTestMachine.FRM_NAME%>.submit();
	}

	function cmdListPrev(){
		document.<%=FrmTestMachine.FRM_NAME%>.command.value="<%=Command.PREV%>";
		document.<%=FrmTestMachine.FRM_NAME%>.action="test_mesin_odbc.jsp";
                document.<%=FrmTestMachine.FRM_NAME%>.target = "";
		document.<%=FrmTestMachine.FRM_NAME%>.submit();
	}

	function cmdListNext(){
		document.<%=FrmTestMachine.FRM_NAME%>.command.value="<%=Command.NEXT%>";
		document.<%=FrmTestMachine.FRM_NAME%>.action="test_mesin_odbc.jsp";
                document.<%=FrmTestMachine.FRM_NAME%>.target = "";
		document.<%=FrmTestMachine.FRM_NAME%>.submit();
	}

	function cmdListLast(){
		document.<%=FrmTestMachine.FRM_NAME%>.command.value="<%=Command.LAST%>";
		document.<%=FrmTestMachine.FRM_NAME%>.action="test_mesin_odbc.jsp";
                document.<%=FrmTestMachine.FRM_NAME%>.target = "";
		document.<%=FrmTestMachine.FRM_NAME%>.submit();
	}
window.history.forward();
	function noBack() { window.history.forward(); }
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
<link rel="stylesheet" href="../styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../styles/tab.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "headerscript" --> 
<script type="text/javascript">

function changeHashOnLoad() {
window.location.href += "#";
setTimeout("changeHashAgain()", "50");
}

function changeHashAgain() {
window.location.href +="1";
}

var storedHash = window.location.hash;
window.setInterval(function () {
if (window.location.hash != storedHash) {
window.location.hash = storedHash;
}
}, 50);

</script>
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

<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../styletemplate/template_header.jsp" %>
            <%}else{%>
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
                  System &gt; Query Setup<!-- #EndEditable -->
            </strong></font>
	      </td>
        </tr>
        <tr> 
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td  style="background-color:<%=bgColorContent%>; "> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                    <tr> 
                      <td valign="top"> 
                        <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                          <tr> 
                            <td valign="top">
		    				  <!-- #BeginEditable "content" --> 
                                    <form name="<%=FrmTestMachine.FRM_NAME%>" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                       <input type="hidden" name="start" value="<%=start%>">
                                       <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                             
                                             
                                        <tr align="left" valign="top"> 
                                          <td height="8" valign="middle" colspan="3"> 
                                          
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td class="listtitle">Test Koneksi</td>
                                              </tr>
                                              <tr> 
                                                <td height="100%"> 
                                                  <table border="0" cellspacing="2" cellpadding="2" width="50%">
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="39%">&nbsp;</td>
                                                      <td width="47%" class="comment">*)entry 
                                                        required </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="39%">Nama Odbc</td>
                                                      <td width="47%"> 
                                                        <input type="text" name="<%=frmTestMachine.fieldNames[FrmTestMachine.FRM_FIELD_NAME_ODBC]%>"  value="<%=testMachine.getNamaOdbc()%>" class="elemenForm" size="30">
                                                        *<%=frmTestMachine.getErrorMsg(FrmTestMachine.FRM_FIELD_NAME_ODBC)%>
                                                      </td>
                                                    </tr>
						   <tr align="left" valign="top"> 
                                                      <td valign="top" width="40%"> DNS</td>
                                                      <td width="50%"> 
                                                        <input type="text" name="<%=frmTestMachine.fieldNames[FrmTestMachine.FRM_FIELD_DNS]%>"  value="<%=testMachine.getDns()%>" class="elemenForm" size="95">
                                                        *<%=frmTestMachine.getErrorMsg(FrmTestMachine.FRM_FIELD_DNS)%>
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="39%">User Name</td>
                                                      <td width="47%"> 
							<input type="text" name="<%=frmTestMachine.fieldNames[FrmTestMachine.FRM_FIELD_USER]%>"  value="<%=testMachine.getUser()%>" class="elemenForm" size="30">
                                                        <%=frmTestMachine.getErrorMsg(FrmTestMachine.FRM_FIELD_USER)%>
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="39%">Password</td>
                                                      <td width="47%"> 
                                                        <input type="text" name="<%=frmTestMachine.fieldNames[FrmTestMachine.FRM_FIELD_PASSWORD]%>"  value="<%=testMachine.getPassword()%>" class="elemenForm" size="30">
                                                        <%=frmTestMachine.getErrorMsg(FrmTestMachine.FRM_FIELD_PASSWORD)%>
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="39%">Type Odbc</td>
                                                      <td width="47%"> 
                                                         <%= ControlCombo.draw(frmTestMachine.fieldNames[FrmTestMachine.FRM_FIELD_TYPE_ODBC], "formElemen", null, "" + (testMachine.getTypeOdbc()), FrmTestMachine.getValueTypeOdbc(), FrmTestMachine.getKeyTypeOdbc(),"")%></td>
                                                                                                                                
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="39%">Type Code</td>
                                                      <td width="47%"> 
                                                         <%= ControlCombo.draw(frmTestMachine.fieldNames[FrmTestMachine.FRM_FIELD_EXSAMPLE_FUNGSI], "formElemen", null, "" + (testMachine.getCodeExsample()), FrmTestMachine.getValueTypeQuery(), FrmTestMachine.getKeyTypeQuery(),"onChange=\"javascript:cmdChangeTypeQuery()\"")%></td>
                                                                                                                                
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="39%">Use Limit</td>
                                                      <td width="47%"> 
                                                         <input name=<%=frmTestMachine.fieldNames[FrmTestMachine.FRM_FIELD_USE_LIMIT]%> type="checkbox" checked value=1 ><input name=<%=frmTestMachine.fieldNames[FrmTestMachine.FRM_FIELD_USE_LIMIT_VALUE]%> type="text" value=<%=testMachine.getUseLimitValue()%> >                                                                       
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="39%">Query </td>
                                                      <td width="47%"> 
                                                        <textarea name="<%=frmTestMachine.fieldNames[FrmTestMachine.FRM_FIELD_QUERY_EXSAMPLE] %>" class="elemenForm" cols="150" rows="10"><%= testMachine.getSampleQuery() %></textarea>
                                                      </td>
                                                    </tr>
											
                                                  </table>
                                                </td>
                                              </tr>
                                              
                                              
                                              
                                              
                                              
                                              
                                              <%//if((iCommand == Command.NONE || iCommand == Command.DELETE || iCommand == Command.BACK || iCommand ==Command.SAVE)&& (frmDivision.errorSize()<1)){
											  
											   if(privAdd){%>
                                              
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top">MESSAGE: <%=iCommand==Command.NONE?"":CtrlTestMachine.resultText[0][iErrCode]%></td>
                                                    </tr>
                                              <tr align="left" valign="top"> 
											  	<td>
                                                  <table cellpadding="0" cellspacing="0" border="0">
                                                    <tr> 
                                                      <td></td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                      <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td height="22" valign="middle" colspan="3" width="951"> 
                                                        <a href="javascript:cmdExcecute()" class="command">Execute</a> ||  <a href="javascript:cmdTest()" class="command">Test Connection</a> </td>
                                                      
                                                    </tr>
                                                  </table>
												</td>
                                              </tr>
											  <%}%>
                                             <%if(iCommand==Command.LIST || (iCommand == Command.FIRST || iCommand == Command.PREV )||  
										(iCommand == Command.NEXT || iCommand == Command.LAST)){%>
                                             <tr>
                                             <td>
                                                
                                                 <%=drawList(out,queryResult,start)%>
                                             </td>
                                             </tr>
                                             <tr align="left" valign="top"> 
                                                <td height="8" align="left" colspan="3" class="command" valign="top"> 
                                                  <span class="command"> 
                                                  <% 
                                                  
								   int cmd = 0;
									   if ((iCommand == Command.FIRST || iCommand == Command.PREV )|| 
										(iCommand == Command.NEXT || iCommand == Command.LAST))
											cmd =iCommand; 
								   else{
									  if(iCommand == Command.NONE || prevCommand == Command.NONE)
										cmd = Command.FIRST;
									  else 
									  	cmd =prevCommand; 
								   } 
							    %>
                                                  <% ctrLine.setLocationImg(approot+"/images");
							   	ctrLine.initDefault();
								 %>
                                                  <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> 
                                                  </span> </td>
                                              </tr>
                                              
                                            <%}%>
                                            
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
                                <%@include file="../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
      <%@ include file = "../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
    <script type="text/javascript">
	    $(document).ready(function () {
	        gridviewScroll();
	    });
            <%
                int freesize=1;
                
            %>
	    function gridviewScroll() {
	        gridView1 = $('#GridView1').gridviewScroll({
                width: 1310,
                height: 500,
                railcolor: "##33AAFF",
                barcolor: "#CDCDCD",
                barhovercolor: "#606060",
                bgcolor: "##33AAFF",
                freezesize: <%=freesize%>,
                arrowsize: 30,
                varrowtopimg: "<%=approot%>/images/arrowvt.png",
                varrowbottomimg: "<%=approot%>/images/arrowvb.png",
                harrowleftimg: "<%=approot%>/images/arrowhl.png",
                harrowrightimg: "<%=approot%>/images/arrowhr.png",
                headerrowcount: 1,
                railsize: 16,
                barsize: 15
            });
	    }
	</script>
</body>
<!-- #BeginEditable "script" -->
<script language="JavaScript">
	//var oBody = document.body;
	//var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
</script>
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
