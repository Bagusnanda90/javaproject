
<%@page import="com.dimata.harisma.entity.configrewardnpunisment.SrcEntriOpnameSales"%>
<%@page import="com.dimata.harisma.form.configrewardnpunisment.FrmSrcEntriOpnameSales"%>
<%@page import="com.dimata.harisma.entity.periodestokopname.PstPeriodeStokOpname"%>
<%@page import="com.dimata.harisma.entity.periodestokopname.PeriodeStokOpname"%>
<%@page import="com.dimata.harisma.entity.configrewardnpunisment.ConfigRewardAndPunishment"%>
<%@page import="com.dimata.harisma.entity.configrewardnpunisment.PstConfigRewardAndPunishment"%>
<%@page import="com.dimata.harisma.entity.jenisSo.JenisSo"%>
<%@page import="com.dimata.harisma.entity.jenisSo.PstJenisSo"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.Location"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.PstLocation"%>
<%@page import="com.dimata.harisma.entity.configrewardnpunisment.PstEntriOpnameSales"%>
<%@page import="com.dimata.harisma.entity.configrewardnpunisment.EntriOpnameSales"%>
<%@page import="com.dimata.harisma.form.configrewardnpunisment.FrmEntriOpnameSales"%>
<%@page import="com.dimata.harisma.form.configrewardnpunisment.CtrlEntriOpnameSales"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@ page language="java" %>  

<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ page language="java" %>

<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->

<%@ include file = "../main/javainit.jsp" %> 
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMPLOYEE, AppObjInfo.OBJ_EMPLOYEE_ENTRI_OPNAME_SALES);%>
<%@ include file = "../main/checkuser.jsp" %>

<%
    //boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
    //boolean privUpdate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));

%>

<%
    int iCommand = FRMQueryString.requestCommand(request);
    SrcEntriOpnameSales srcEntriOpname = new SrcEntriOpnameSales();
     FrmSrcEntriOpnameSales frmSrcEntriOpnameSales = new FrmSrcEntriOpnameSales();
if(iCommand==Command.BACK)
{        
	frmSrcEntriOpnameSales= new FrmSrcEntriOpnameSales(request, srcEntriOpname);  
	try
	{				
		srcEntriOpname = (SrcEntriOpnameSales) session.getValue("SEARCH_ENTRI_OPNAME");			
		if(srcEntriOpname == null)
		{
			srcEntriOpname = new SrcEntriOpnameSales();
		}		
	}
	catch (Exception e)
	{
		srcEntriOpname = new SrcEntriOpnameSales();
	}
}
%>


<!-- JSP Block -->
<!-- End of JSP Block -->
<html>
    <!-- #BeginTemplate "/Templates/main.dwt" --> 
    <head>
        <!-- #BeginEditable "doctitle" --> 
        <title>HARISMA - Entri Opname Sales</title>
        <!-- #EndEditable --> 
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <!-- #BeginEditable "styles" --> 
        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
        <link rel="stylesheet" href="../styles/tab.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
        <SCRIPT language=JavaScript>
           
            function cmdChangeLoc(){
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.action="search_entriopnamesales.jsp";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.submit();
            }
            
            function cmdSearch(){
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.command.value="<%=Command.VIEW%>";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.action="entriopnamesales.jsp";
                document.<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>.submit();
            }
            
            
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
            
            function showObjectForMenu(){
                
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
        </SCRIPT>
        <!-- #EndEditable --> 
    </head>
    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">     
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>    
            <%@include file="../styletemplate/template_header.jsp" %>
            <%} else {%>
            <tr>  
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
                    <!-- #BeginEditable "header" --> 
                    <%@ include file = "../main/header.jsp" %>
                    <!-- #EndEditable --> </td>
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
                                        <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Search Reward And Punishment Setting<!-- #EndEditable --> </strong></font> </td>
                                    </tr>
                                    <tr> 
                                        <td height="20"> </td>
                                    </tr>
                                    <tr> 
                                        <td> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr> 
                                                    <td  style="background-color:<%=bgColorContent%>; "> 
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                                                            <tr> 
                                                                <td valign="top">  
                                                                    <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                                        <!-- #BeginEditable "content" --> 
                                                                        <tr> 
                                                                            <td valign="top"> 
                                                                                <form name="<%=FrmEntriOpnameSales.FRM_ENTRI_OPNAME_SALES%>" method="post" action="">
                                                                                    <input type="hidden" name="command" value="">
                                                                                    <table width="100%" border="0">
                                                                                        <%
                                                                                            Vector vListOutlet = PstLocation.list(0, 0, "", "");
                                                                                            Vector valueOutlet = new Vector();
                                                                                            Vector keyOutlet = new Vector();
                                                                                            valueOutlet.add("" + 0);
                                                                                            keyOutlet.add("select");
                                                                                            if (vListOutlet != null && vListOutlet.size() > 0) {
                                                                                                for (int x = 0; x < vListOutlet.size(); x++) {
                                                                                                    Location location = (Location) vListOutlet.get(x);
                                                                                                    valueOutlet.add("" + location.getOID());
                                                                                                    keyOutlet.add("" + location.getName());
                                                                                                }
                                                                                            }
                                                                                            Vector valuePeriod = new Vector();
                                                                                            Vector keyPeriod = new Vector();
                                                                                            valuePeriod.add("" + 0);
                                                                                            keyPeriod.add("select");
                                                                                            Vector vPeriodOpname = PstPeriodeStokOpname.list(0, 0, "", "");
                                                                                            if (vPeriodOpname != null && vPeriodOpname.size() > 0) {
                                                                                                for (int x = 0; x < vPeriodOpname.size(); x++) {
                                                                                                    PeriodeStokOpname periodeStokOpname = (PeriodeStokOpname) vPeriodOpname.get(x);
                                                                                                    valuePeriod.add("" + periodeStokOpname.getOID());
                                                                                                    keyPeriod.add("" + periodeStokOpname.getNamePeriod()); 
                                                                                                }
                                                                                            }
                                                                                            Vector valueJenisSo = new Vector();
                                                                                            Vector keyJenisSo = new Vector();
                                                                                            valueJenisSo.add("" + 0);
                                                                                            keyJenisSo.add("select");
                                                                                            Vector vJenisSo = PstJenisSo.list(0, 0, "", "");
                                                                                            if (vJenisSo != null && vJenisSo.size() > 0) {
                                                                                                for (int x = 0; x < vJenisSo.size(); x++) {
                                                                                                    JenisSo jenisSo = (JenisSo) vJenisSo.get(x);
                                                                                                    valueJenisSo.add("" + jenisSo.getOID());
                                                                                                    keyJenisSo.add("" + jenisSo.getNamaSo());
                                                                                                }
                                                                                            }
                                                                                            Vector valueTypeOfTolerance = new Vector();
                                                                                            Vector keyTypeOfTolerance = new Vector();
                                                                                            valueTypeOfTolerance.add("-1");
                                                                                            keyTypeOfTolerance.add("select");
                                                                                            valueTypeOfTolerance.add("" + ConfigRewardAndPunishment.DC);
                                                                                            keyTypeOfTolerance.add("DC");
                                                                                            valueTypeOfTolerance.add("" + ConfigRewardAndPunishment.NetOffSales);
                                                                                            keyTypeOfTolerance.add("Net Sales");
                                                                                        %>
                                                                                        <tr>
                                                                                            <td width="14%">Nama Outlet</td>
                                                                                            <td width="1%">:</td>
                                                                                            <td width="85%"><%=ControlCombo.draw(FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_LOCATION_ID], "formElemen", null, String.valueOf(srcEntriOpname.getLocationId()), valueOutlet, keyOutlet, "")%></td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td width="14%">Period</td>
                                                                                            <td width="1%">:</td>
                                                                                            <td width="85%"><%=ControlDate.drawDateWithStyle(FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_DATE_START_PERIOD], srcEntriOpname.getDtFromPeriod(), 0, 30,"formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%> &nbsp;to&nbsp; 
                                                                                                            <%=ControlDate.drawDateWithStyle(FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_DATE_END_PERIOD], srcEntriOpname.getDtToPeriod(), 0, 50,"formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%></td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td width="14%">Jenis So</td>
                                                                                            <td width="1%">:</td>
                                                                                            <td width="85%"><%=ControlCombo.draw(FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_JENIS_SO_ID], "formElemen", null, String.valueOf(srcEntriOpname.getJenisSoId()), valueJenisSo, keyJenisSo, "")%></td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td width="14%">Type Of Tolerance</td>
                                                                                            <td width="1%">:</td>
                                                                                            <td width="85%"><%=ControlCombo.draw(FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_TYPE_OF_TOLERANCE], "formElemen", null, String.valueOf(srcEntriOpname.getTypeTolerance()), valueTypeOfTolerance, keyTypeOfTolerance, "")%></td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td width="14%">Create Form Location Name</td>
                                                                                            <td width="1%">:</td>
                                                                                            <td width="85%"><input type="text" name="<%=FrmEntriOpnameSales.fieldNames[FrmEntriOpnameSales.FRM_FLD_CREATE_FORM_LOCATION_OPNAME]%>" value="<%=srcEntriOpname.getCreateLocationName()%>" /></td>
                                                                                        </tr>
                                                                                    </table>
                                                                                    <table width="100%">
                                                                                        <tr>
                                                                                            <td width="150"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnSearchOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search"></a>
                                                                                            <a href="javascript:cmdSearch()" class="command">Search</a> 
                                                                                            </td>
                                                                                        </tr>
                                                                                    </table>
                                                                                </form> 
                                                                                    
                                                                            </td> 
                                                                        </tr>
                                                                        <tr> 
                                                                            <td>&nbsp; </td>
                                                                        </tr>
                                                                    <!-- #EndEditable -->      </table>
                                                                              
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
                                    <tr>
                                        <td valign="bottom">
                                            <!-- untuk footer -->
                                            <%@include file="../footer.jsp" %>
                                        </td>

                                    </tr>
                                    <%} else {%>
                                    <tr> 
                                        <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
                                            <%@ include file = "../main/footer.jsp" %>
                                            <!-- #EndEditable --> </td>
                                    </tr>
                                    <%}%>
                                </table>
                                </body>
                                <!-- #BeginEditable "script" --> <script language="JavaScript">
                                        var oBody = document.body;
                                        var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
                                </script>

                                <!-- #EndEditable --> <!-- #EndTemplate -->
                                </html>
