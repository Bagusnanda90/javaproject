<%@ page language = "java" %>
<%@ page import="com.dimata.util.Formater"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.dimata.qdep.form.*" %>
<%@ page import="com.dimata.harisma.entity.employee.*" %>
<%@ page import="com.dimata.harisma.entity.attendance.*" %>
<%@ page import="com.dimata.harisma.entity.masterdata.LeavePeriod" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMP_LEAVE_OPNAME, AppObjInfo.OBJ_EMP_LEAVE_AL_OPNAME);%>
<%@ include file = "../../main/checkuser.jsp" %>
<%
            /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
            //boolean privSubmit = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_SUBMIT));
%>

<html><!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" --> 
        <title>HARISMA - Upload AL Stock</title>
        <!-- #EndEditable --> 
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
        <!-- #BeginEditable "styles" --> 
<!-- #EndEditable -->
        <!-- #BeginEditable "stylestab" --> 
        <link rel="stylesheet" href="../../../styles/tab.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "headerscript" --> 
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <!-- #EndEditable -->
    </head> 
    
    <body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">    
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
                                        <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Uploader 
                                            &gt; Leave Management &gt; AL Stock<!-- #EndEditable --> </strong></font> 
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
                                    <%
            boolean transferSuccess = false;
            String[] empnumber = request.getParameterValues("empnumber");
            String[] empid = request.getParameterValues("empid");
            String[] lastyearQty = request.getParameterValues("lastyearqty");
            String[] entitledQty = request.getParameterValues("entitledqty");
            String[] earnedQty = request.getParameterValues("earnedytd");
            String[] takenQty = request.getParameterValues("takenytd");
            Date OpnameDate = FRMQueryString.requestDate(request, "opnamedate");
            
            String[] strIsProcess = new String[empid.length];
            String[] strStatus = new String[empid.length];
            String[] alUpload_id = new String[empid.length];
            for(int i=0;i<strIsProcess.length;i++){
                alUpload_id[i] = "0";
                strIsProcess[i] = "true";
                strStatus[i] = String.valueOf(com.dimata.harisma.entity.leave.PstAlUpload.FLD_DOC_STATUS_NOT_PROCESS);
            }
            Vector vAlUpload = new Vector();
            
            vAlUpload.add(empid);//0 employee id
            //out.println("<br/>Data Size empid :::: "+empid.length);
            vAlUpload.add(alUpload_id);//1 al upload id
            //out.println("<br/>Data Size alUpload_id :::: "+alUpload_id.length);
            vAlUpload.add(lastyearQty);//2 last year to clear
            //out.println("<br/>Data Size lastyearQty :::: "+lastyearQty.length);
            vAlUpload.add(takenQty);//3 taken curr period
            //out.println("<br/>Data Size takenQty :::: "+takenQty.length);
            vAlUpload.add(strStatus);//4 data status
            //out.println("<br/>Data Size strStatus :::: "+strStatus.length);
            vAlUpload.add(strIsProcess);//5 status proses
            //out.println("<br/>Data Size strIsProcess :::: "+strIsProcess.length);
            vAlUpload.add(OpnameDate);//6 tanggal opname
            //out.println("<br/>Data Size OpnameDate :::: "+OpnameDate);
        
            System.out.println("Process data..........................................");
            Vector vAlUploadId = new Vector(1,1);
            vAlUploadId = com.dimata.harisma.session.leave.SessAlUpload.saveAlUpload(vAlUpload);
            if(vAlUploadId.size()>0){
                //update by satrya 2013-01-06
            //status = com.dimata.harisma.session.leave.SessAlUpload.opnameALAllData(vAlUploadId);
                transferSuccess = com.dimata.harisma.session.leave.SessAlUpload.opnameALAllData(vAlUploadId,null);
            }
            if(transferSuccess){
            %>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="fffff9">
                <tr>
                    <td>
                        <center>PROCESS DATA SUCCESS</center>
                    </td>
                </tr>
            </table>
            <%
            }else{
            %>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="red">
                <tr>
                    <td>
                        <center>PROCESS DATA FAILED</center>
                    </td>
                </tr>
            </table>
            <%
            }
            %>
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
                <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
        </table>
    </body>
    <!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
