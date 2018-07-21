<%-- 
    Document   : outsource_plan_list
    Created on : Sep 14, 2015, 3:03:00 PM
    Author     : dimata005
--%>

<%@page import="com.dimata.harisma.entity.payroll.PayGeneral"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayGeneral"%>
<%@page import="com.dimata.harisma.entity.outsource.PstOutSourcePlan"%>
<%@page import="com.dimata.harisma.entity.outsource.OutSourcePlan"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ page language="java" %>
<!-- package java -->
<%@ page import ="java.util.Date"%>
<%@ page import ="java.util.Vector"%>
<!-- package qdep -->
<%@ page import ="com.dimata.gui.jsp.*"%>
<%@ page import ="com.dimata.util.*"%>
<%@ page import ="com.dimata.qdep.form.*"%>
<!-- package harisma -->
<%@ include file = "../main/javainit.jsp" %>
<% //int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_PERFORMANCE_APPRAISAL, AppObjInfo.OBJ_GROUP_RANK); %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_OUTSOURCE, AppObjInfo.G2_OUTSOURCE_DATA_ENTRY, AppObjInfo.OBJ_OUTSOURCE_MASTER_PLAN);%>
<%@ include file = "../main/checkuser.jsp" %>
<%!    
    public String drawList(Vector vOutSourcePlan) {
        Vector result = new Vector(1, 1);
        ControlList ctrlist = new ControlList();
        if (vOutSourcePlan != null && vOutSourcePlan.size() > 0) {
            ctrlist.setAreaWidth("100%");
            ctrlist.setListStyle("listgen");
            ctrlist.setTitleStyle("listgentitle");
            ctrlist.setCellStyle("listgensell");
            ctrlist.setHeaderStyle("listgentitle");
            ctrlist.addHeader("No", "1%", "2", "0");
            ctrlist.addHeader("Tahun", "6%", "2", "0");
            ctrlist.addHeader("Judul", "6%", "0", "3");
            ctrlist.addHeader("Tgl Buat", "2%", "0", "0");
            ctrlist.addHeader("Oleh", "2%", "0", "0");
            ctrlist.addHeader("Status", "2%", "0", "0");
            ctrlist.addHeader("Disetujui Oleh", "2%", "0", "3");
            ctrlist.addHeader("Tanggal Pesetujuan", "2%", "0", "0");
            ctrlist.addHeader("Note", "2%", "0", "0");
            ctrlist.setLinkRow(0);
            ctrlist.setLinkSufix("");

            Vector lstData = ctrlist.getData();
            Vector lstLinkData = ctrlist.getLinkData();
            ctrlist.setLinkPrefix("javascript:cmdEdit('");
            ctrlist.setLinkSufix("')");
            ctrlist.reset();

            // vector of data will used in pdf report
            Vector vectDataToPdf = new Vector(1, 1);
            int count = 0;
            for (int i = 0; i < vOutSourcePlan.size(); i++) {
                OutSourcePlan outSourcePlan = (OutSourcePlan) vOutSourcePlan.get(i);
                Vector rowx = new Vector(1, 1);
                count=count+1;
                String approveName="-";
                if(outSourcePlan.getApproveById()!=0){
                    approveName = outSourcePlan.getApproveName();
                }
                rowx.add("<center>" +count+"</center>");
                rowx.add(""+outSourcePlan.getPlanYear());
                rowx.add(""+outSourcePlan.getTitle());
                rowx.add(""+outSourcePlan.getCreatedDate());
                rowx.add(""+outSourcePlan.getCreateName());
                rowx.add(""+outSourcePlan.getStatus());
                rowx.add(""+approveName);
                rowx.add(""+outSourcePlan.getApprovedDate());
                rowx.add(""+outSourcePlan.getNote());
                lstData.add(rowx);
                lstLinkData.add(String.valueOf(outSourcePlan.getOID()));
            }
        }
        return ctrlist.draw();
    }
%>


<%
    int iCommand = FRMQueryString.requestCommand(request);
    long oidCompanyId = FRMQueryString.requestLong(request, "company_id");
    String whereSearch="";
    if(oidCompanyId!=0){
        whereSearch="ho."+PstOutSourcePlan.fieldNames[PstOutSourcePlan.FLD_COMPANYID]+"='"+oidCompanyId+"'";
    }
    Vector vOutSourcePlan = new Vector();
    if (iCommand == Command.LIST) {
         vOutSourcePlan = PstOutSourcePlan.listJoin(0,0,whereSearch,"");
    }
    
%>
<!-- End of JSP Block -->
<html>
    <!-- #BeginTemplate "/Templates/main.dwt" --> 
    <head>
        <!-- #BeginEditable "doctitle" --> 
        <title>HARISMA - Outsource Master Plan</title>
        <script language="JavaScript">
            function cmdView() {
                document.frpresence.command.value = "<%=Command.LIST%>";
                document.frpresence.action = "outsource_plan_list.jsp";
                document.frpresence.submit();
            }
            
             function cmdAddNew() {
                document.frpresence.command.value = "<%=Command.ADD%>";
                document.frpresence.action = "outsource_plan_edit.jsp";
                document.frpresence.submit();
            }
            
            function cmdEdit(oid) {
                document.frpresence.command.value = "<%=Command.EDIT%>";
                document.frpresence.hidden_outsource_id.value = oid;
                document.frpresence.action = "outsource_plan_edit.jsp";
                document.frpresence.submit();
            }
            
            //update by devin 2014-01-29
            function cmdUpdateDiv() {
                document.frpresence.command.value = "<%=String.valueOf(Command.GOTO)%>";
                document.frpresence.action = "outsource_plan_list.jsp";
                document.frpresence.target = "";
                document.frpresence.submit();
            }
            function cmdUpdatePos() {
                document.frpresence.command.value = "<%=String.valueOf(Command.GOTO)%>";
                document.frpresence.action = "outsource_plan_list.jsp";
                document.frpresence.target = "";
                document.frpresence.submit();
            }
            function cmdUpdateSec() {
                document.frpresence.command.value = "<%=String.valueOf(Command.GOTO)%>";
                document.frpresence.action = "outsource_plan_list.jsp";
                document.frpresence.target = "";
                document.frpresence.submit();
            }

            function cmdUpdateDep() {
                document.frpresence.command.value = "<%=String.valueOf(Command.GOTO)%>";
                document.frpresence.action = "outsource_plan_list.jsp";
                document.frpresence.target = "";
                document.frpresence.submit();
            }

            //-------------- script control line -------------------
            function MM_swapImgRestore() { //v3.0
                var i, x, a = document.MM_sr;
                for (i = 0; a && i < a.length && (x = a[i]) && x.oSrc; i++)
                    x.src = x.oSrc;
            }

            function MM_preloadImages() { //v3.0
                var d = document;
                if (d.images) {
                    if (!d.MM_p)
                        d.MM_p = new Array();
                    var i, j = d.MM_p.length, a = MM_preloadImages.arguments;
                    for (i = 0; i < a.length; i++)
                        if (a[i].indexOf("#") != 0) {
                            d.MM_p[j] = new Image;
                            d.MM_p[j++].src = a[i];
                        }
                }
            }

            function MM_findObj(n, d) { //v4.0
                var p, i, x;
                if (!d)
                    d = document;
                if ((p = n.indexOf("?")) > 0 && parent.frames.length) {
                    d = parent.frames[n.substring(p + 1)].document;
                    n = n.substring(0, p);
                }
                if (!(x = d[n]) && d.all)
                    x = d.all[n];
                for (i = 0; !x && i < d.forms.length; i++)
                    x = d.forms[i][n];
                for (i = 0; !x && d.layers && i < d.layers.length; i++)
                    x = MM_findObj(n, d.layers[i].document);
                if (!x && document.getElementById)
                    x = document.getElementById(n);
                return x;
            }

            function MM_swapImage() { //v3.0
                var i, j = 0, x, a = MM_swapImage.arguments;
                document.MM_sr = new Array;
                for (i = 0; i < (a.length - 2); i += 3)
                    if ((x = MM_findObj(a[i])) != null) {
                        document.MM_sr[j++] = x;
                        if (!x.oSrc)
                            x.oSrc = x.src;
                        x.src = a[i + 2];
                    }
            }
        </script>
        <!-- update by devin 2014-01-29 -->
        <style type="text/css">
            .tooltip {
                display:none;
                position:absolute;
                border:1px solid #333;
                background-color:#161616;
                border-radius:5px;
                padding:10px;
                color:#fff;
                font-size:12px Arial;
            }
        </style>
        <!-- update by devin 2014-01-29 -->
        <style type="text/css">

            .bdr{border-bottom:2px dotted #0099FF;}

            .highlight {
                color: #090;
            }

            .example {
                color: #08C;
                cursor: pointer;
                padding: 4px;
                border-radius: 4px;
            }

            .example:after {
                font-family: Consolas, Courier New, Arial, sans-serif;
                content: '?';
                margin-left: 6px;
                color: #08C;
            }

            .example:hover {
                background: #F2F2F2;
            }

            .example.dropdown-open {
                background: #888;
                color: #FFF;
            }

            .example.dropdown-open:after {
                color: #FFF;
            }

        </style>
        <!-- update by devin 2014-01-29 -->
        <script type="text/javascript">
            $(document).ready(function() {
                // Tooltip only Text
                $('.masterTooltip').hover(function() {
                    // Hover over code
                    var title = $(this).attr('title');
                    $(this).data('tipText', title).removeAttr('title');
                    $('<p class="tooltip"></p>')
                            .text(title)
                            .appendTo('body')
                            .fadeIn('fast');
                }, function() {
                    // Hover out code
                    $(this).attr('title', $(this).data('tipText'));
                    $('.tooltip').remove();
                }).mousemove(function(e) {
                    var mousex = e.pageX + 20; //Get X coordinates
                    var mousey = e.pageY + 10; //Get Y coordinates
                    $('.tooltip')
                            .css({top: mousey, left: mousex})
                });
            });
        </script>

        <!-- #EndEditable --> 
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <!-- #BeginEditable "styles" --> 
        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
        <link rel="stylesheet" href="../styles/tab.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
        <!-- #EndEditable --> 
    </head>
    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg')">
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
                                        <td height="20"> <font color="#FF6600" face="Arial"><h2> <!-- #BeginEditable "contenttitle" -->Laporan Alih Daya &gt; Form Master Alih Daya<!-- #EndEditable --> </h2></font> 
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
                                                                            <td valign="top"> <!-- #BeginEditable "content" -->
                                                                                <form name="frpresence" method="post" action="">
                                                                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                                                                     <input type="hidden" name="hidden_outsource_id" value="">
                                                                                
                                                                                    <table width="60%" border="0" cellspacing="2" cellpadding="2">
                                                                                       <tr> 
                                                                                            <td width="6%" nowrap="nowrap"><div align="left">Perusahaan</div></td>
                                                                                            <td width="30%" nowrap="nowrap">:
                                                                                                <%
                                                                                                    Date startDate = new Date();	    
                                                                                                    Vector val_company = new Vector(1,1);
                                                                                                    Vector key_company = new Vector(1,1);
                                                                                                    Vector vCompany= PstPayGeneral.listAll();
                                                                                                     val_company.add("0");
                                                                                                     key_company.add("-Pilih-");
                                                                                                     for (int k = 0; k < vCompany.size(); k++) {
                                                                                                        PayGeneral payGeneral = (PayGeneral) vCompany.get(k);
                                                                                                        val_company.add(""+payGeneral.getOID());
                                                                                                        key_company.add(""+payGeneral.getCompanyName());
                                                                                                     }
                                                                                                %>
                                                                                                <%=ControlCombo.draw("company_id", null,""+oidCompanyId, val_company, key_company,"", "formElemen")%>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr> 
                                                                                            <td width="3%">&nbsp;</td>
                                                                                            <td width="9%" nowrap> 
                                                                                                <div align="left"></div>
                                                                                            </td>
                                                                                            <td width="88%"> 
                                                                                                <table border="0" cellspacing="0" cellpadding="0" width="137">
                                                                                                    <tr> 
                                                                                                        <td width="16"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10', '', '<%=approot%>/images/BtnSearchOn.jpg', 1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="View Night Shift"></a></td>
                                                                                                        <td width="2"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                                                                        <td width="94" class="command" nowrap><a href="javascript:cmdView()">View Plan</a></td>
                                                                                                    </tr>
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>
                                                                                    </table>
                                                                                    <% if (iCommand == Command.LIST) {%>
                                                                                    <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                                        <tr>
                                                                                            <td><hr></td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <%=drawList(vOutSourcePlan)%>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <%if (privAdd) {%>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <table width="18%" border="0" cellspacing="1" cellpadding="1">
                                                                                                    <tr>
                                                                                                        <td width="17%"><a href="javascript:cmdAddNew()"><img src="../images/BtnNew.jpg" width="24" height="24" border="0" alt="Add New"></a></td>												  
                                                                                                        <td width="83%"><b><a href="javascript:cmdAddNew()" class="command">Add New</a></b> </td>
                                                                                                    </tr>
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <%}%>										  
                                                                                    </table>
                                                                                    <%}%>
                                                                                </form>
                                                                                <!-- #EndEditable --> </td>
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
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
            <tr>
                <td valign="bottom">
                    <!-- untuk footer -->
                    <%@include file="../footer.jsp" %>
                </td>

            </tr>
            <%} else {%>
            <tr> 
                <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
                    <%@ include file = "../main/footer.jsp" %>
                    <!-- #EndEditable --> </td>
            </tr>
            <%}%>
        </table>
    </body>
    <!-- #BeginEditable "script" --> 
    <!-- #EndEditable --> <!-- #EndTemplate -->
</html>

