<%@ page language = "java" %>

<!-- package java -->
<%@ page import = "java.util.*" %>
<%@ page import="java.util.Date"%>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.leave.*" %>
<%@ page import = "com.dimata.harisma.form.search.FrmSrcLLUpload" %>


<%@ include file = "../../main/javainit.jsp" %>

<%!
private String drawList(Vector vLLUpload, int start){
    ControlList ctrlist = new ControlList();
    ctrlist.setAreaWidth("100%");
    ctrlist.setListStyle("listgen");
    ctrlist.setTitleStyle("listgentitle");
    ctrlist.setCellStyle("listgensell");
    ctrlist.setHeaderStyle("listgentitle");
    ctrlist.addHeader("No","25%");
    ctrlist.addHeader("Date Opname","75%");
    
    ctrlist.setLinkRow(1);
    ctrlist.setLinkSufix("");
    Vector lstData = ctrlist.getData();
    Vector lstLinkData = ctrlist.getLinkData();
    ctrlist.setLinkPrefix("javascript:cmdSelect('");
    ctrlist.setLinkSufix("')");
    ctrlist.reset();
    
    for(int i=0;i<vLLUpload.size();i++){
        Vector rowx = new Vector(1,1);
        rowx.add(String.valueOf(i+1+start));
        rowx.add(vLLUpload.get(i));
        lstData.add(rowx);
        lstLinkData.add(vLLUpload.get(i));
    }
    
    return ctrlist.draw();
}
%>

<%
    //final int max_data = 20;
    int iCommand = FRMQueryString.requestCommand(request);
    ControlLine ctrLine = new ControlLine();
    
    
    int vectSize = 0;
    
    CtrlAlUpload ctrlAlUpload = new CtrlAlUpload(request);
    
    //int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    int start = FRMQueryString.requestInt(request, "start");
    int recordToGet = 10;
    
    Vector vAlOpnameDate = new Vector();
    vAlOpnameDate = com.dimata.harisma.session.leave.SessLLUpload.getAllLLUpload(0,0);
    vectSize = vAlOpnameDate.size();
    
    if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)
      ||(iCommand==Command.LAST)||(iCommand==Command.LIST))
    {
        start = ctrlAlUpload.actionList(iCommand, start, vectSize, recordToGet);
    }else{
        start = 0;
    }
    vAlOpnameDate = com.dimata.harisma.session.leave.SessLLUpload.getAllLLUpload(start,recordToGet);
    
%>

<script language="JavaScript">
    function cmdSelect(date) {
        self.opener.document.forms.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.<%=String.valueOf(FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_OPNAME_DATE])%>.value = date;
        self.close();
    }
    
    
        function cmdListFirst(){
		document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.command.value="<%=String.valueOf(Command.FIRST)%>";
		document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.action="opnameLL.jsp";
		document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.submit();
	}

	function cmdListPrev(){
		document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.command.value="<%=String.valueOf(Command.PREV)%>";
		document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.action="opnameLL.jsp";
		document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.submit();
	}

	function cmdListNext(){
		document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.command.value="<%=String.valueOf(Command.NEXT)%>";
		document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.action="opnameLL.jsp";
		document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.submit();
	}

	function cmdListLast(){
		document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.command.value="<%=String.valueOf(Command.LAST)%>";
		document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.action="opnameLL.jsp";
		document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.submit();
	}


        function MM_swapImgRestore() 
        { //v3.0
                var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
        }

        function MM_preloadImages() 
        { //v3.0
                var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
                var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
                if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
        }

        function MM_findObj(n, d) 
        { //v4.0
                var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
                d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
                if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
                for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
                if(!x && document.getElementById) x=document.getElementById(n); return x;
        }

        function MM_swapImage() 
        { //v3.0
                var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
                if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
        }
</script>

<html><!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" --> 
        <title>HARISMA - LL Opname List</title>
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
            <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
            <tr> 
                <td width="88%" valign="top" align="left"> 
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr> 
                        <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->
                        LL OPNAME DATE LIST
                          <!-- #EndEditable --> 
                          </strong></font> </td>
                    </tr>
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
                                                        if(vAlOpnameDate.size()>0){
                                                            out.println(drawList(vAlOpnameDate,start));
                                                            ctrLine.setLocationImg(approot+"/images");
                                                            ctrLine.initDefault();
                                                            out.println(ctrLine.drawImageListLimit(iCommand,vectSize,start,recordToGet));
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
                    </table>
                </td> 
            </tr>
            
        </table>
    </body>
    <!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>