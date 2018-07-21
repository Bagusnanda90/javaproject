
<%
            /* 
             * Page Name  		:  perfevaluation.jsp
             * Created on 		:  [date] [time] AM/PM 
             * 
             * @author  		:  [authorName] 
             * @version  		:  [version] 
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
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_APPRAISAL, AppObjInfo.OBJ_PERFORMANCE_APPRAISAL);%>
<%@ include file = "../../main/checkuser.jsp" %>
<%
            /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//            boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//            boolean privUpdate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//            boolean privDelete = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%
%>
<html><!-- #BeginTemplate "/Templates/maintab.dwt" -->
<head>
    <!-- #BeginEditable "doctitle" --> 
    <title>Performance Evaluation</title>
    <script language="JavaScript">
        <!--
        
        //-------------- script control line -------------------
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
        <!--
        function hideObjectForEmployee(){ 
            <%if ((iCommand == Command.ADD) || (iCommand == Command.SAVE) && (frmPerfEvaluation.errorSize() > 0) || (iCommand == Command.EDIT) || (iCommand == Command.ASK) || (iCommand == Command.LIST)) {%>
            document.frmperfevaluation.category.style.visibility="hidden";
            <%}%>   
        } 
        
        function hideObjectForLockers(){ 
        }
        
        function hideObjectForCanteen(){
        }
        
        function hideObjectForClinic(){
        }
        
        function hideObjectForMasterdata(){
            <%if ((iCommand == Command.ADD) || (iCommand == Command.SAVE) && (frmPerfEvaluation.errorSize() > 0) || (iCommand == Command.EDIT) || (iCommand == Command.ASK) || (iCommand == Command.LIST)) {%>
            document.frmperfevaluation.category.style.visibility="hidden";
            <%}%>
        }
        function showObjectForMenu(){
            <%if ((iCommand == Command.ADD) || (iCommand == Command.SAVE) && (frmPerfEvaluation.errorSize() > 0) || (iCommand == Command.EDIT) || (iCommand == Command.ASK) || (iCommand == Command.LIST)) {%>
            document.frmperfevaluation.category.style.visibility="";
            <%}%>
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

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF">
<tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
        <!-- #BeginEditable "header" --> 
      <%@ include file = "../../main/header.jsp" %> 
        <!-- #EndEditable --> 
    </td>
</tr>  
<tr> 
    <td bgcolor="#9BC1FF"  ID="MAINMENU" valign="middle" height="15"> <!-- #BeginEditable "menumain" --> 
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
                <td>
                    <font color="#FF6600" face="Arial"><strong>
                            <!-- #BeginEditable "contenttitle" -->Employee 
                            &gt; Appraisal &gt; Performance Evaluation <!-- #EndEditable --> 
                    </strong></font>
                </td>
            </tr>
            <tr> 
            <td> 
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                    <td valign="top"> 
                    <table width="100%" border="0" cellspacing="1" cellpadding="1">
                        <tr> 
                        <td valign="top"> <!-- #BeginEditable "content" --> 
                            <form name="frmperfevaluation" method ="post" action="">
                            <input type="hidden" name="command" value="<%=iCommand%>">
                            <table width="100%" border="0" cellspacing="1" cellpadding="1">
                            <tr> 
                                <td valign="top"> 
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <%// if (oidEmpAppraisal != 0) {%>
                                    <tr> 
                                        <td> 
                                            <table  border="0" cellspacing="0" cellpadding="0">
                                                <tr> 
                                                    <td  valign="top" height="20" width="104"> 
                                                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="txtalign">
                                                            <tr> 
                                                                <td   valign="top" align="left" width="12"><img src="<%=approot%>/images/tab/inactive_left.jpg" width="12" height="29"></td>
                                                                <td   valign="middle" background="<%=approot%>/images/tab/inactive_bg.jpg" nowrap> 
                                                                    <div align="center" class="tablink"><a href="empappraisal_edit.jsp?employee_appraisal_oid=<%=oidEmpAppraisal%>&command=<%=Command.EDIT%>" class="tablink">Appraisal</a></div>
                                                                </td>
                                                                <td width="12"   valign="top" align="right"><img src="<%=approot%>/images/tab/inactive_right.jpg" width="12" height="29"></td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                    <td  valign="top" height="20" width="158"> 
                                                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                                            <tr> 
                                                                <td   valign="top" align="left" width="10"><img src="<%=approot%>/images/tab/active_left.jpg" width="12" height="29"></td>
                                                                <td   valign="middle" background="<%=approot%>/images/tab/active_bg.jpg" nowrap > 
                                                                    <div align="center" class="tablink">Performance 
                                                                    Evaluation </div>
                                                                </td>
                                                                <td width="12"   valign="top" align="right"><img src="<%=approot%>/images/tab/active_right.jpg" width="12" height="29"></td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                    <td width="150"  valign="top" height="20">&nbsp;</td>
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
<tr>
    <td >&nbsp;</td>
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
    <td colspan="2" height="20" <%=bgFooterLama%>> 	
        <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
    <!-- #EndEditable --> </td>
</tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> 
<!-- #EndTemplate --></html>
