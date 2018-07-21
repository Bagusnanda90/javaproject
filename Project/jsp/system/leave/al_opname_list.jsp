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
<%@ page import = "com.dimata.harisma.form.search.FrmSrcAlUpload" %>


<%@ include file = "../../main/javainit.jsp" %>

<%!
private String drawList(Vector vAlUpload, int start){
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
    
    for(int i=0;i<vAlUpload.size();i++){
        Vector rowx = new Vector(1,1);
        rowx.add(String.valueOf(i+1+start));
        rowx.add(vAlUpload.get(i));
        lstData.add(rowx);
        lstLinkData.add(vAlUpload.get(i));
    }
    
    return ctrlist.draw();
}
%>

<%
    final int max_data = 20;
    int iCommand = FRMQueryString.requestCommand(request);
    ControlLine ctrLine = new ControlLine();
    
    
    int vectSize = 0;
    
    CtrlAlUpload ctrlAlUpload = new CtrlAlUpload(request);
    
    //int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    int start = FRMQueryString.requestInt(request, "start");
    String empNumber = FRMQueryString.requestString(request, "FRM_FIELD_EMP_NAME");
    int recordToGet = 10;
    
    Vector vAlOpnameDate = new Vector();
  if(iCommand==Command.LIST){
    vAlOpnameDate = com.dimata.harisma.session.leave.SessAlUpload.getAllAlUpload(0,0,empNumber);
  }else{
        vAlOpnameDate = com.dimata.harisma.session.leave.SessAlUpload.getAllAlUpload(0,0,"");
  }
    vectSize = vAlOpnameDate.size();
    
    if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)
      ||(iCommand==Command.LAST)||(iCommand==Command.LIST))
    {
        start = ctrlAlUpload.actionList(iCommand, start, vectSize, recordToGet);
        
    }
  if(iCommand==Command.LIST){
    vAlOpnameDate = com.dimata.harisma.session.leave.SessAlUpload.getAllAlUpload(start,recordToGet,empNumber);
     }else{
        vAlOpnameDate = com.dimata.harisma.session.leave.SessAlUpload.getAllAlUpload(start,recordToGet,"");
     }
    
%>

<script language="JavaScript">
    function cmdSelect(date) {
        self.opener.document.forms.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.<%=String.valueOf(FrmSrcAlUpload.fieldNames[FrmSrcAlUpload.FRM_FIELD_OPNAME_DATE])%>.value = date;
        self.close();
    }
    
    function cmdSearch() {
    document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.command.value = "<%=String.valueOf(Command.LIST)%>";
    document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.action = "al_opname_list.jsp";
    document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.submit();
    }
        function cmdListFirst(){
		document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.command.value="<%=String.valueOf(Command.FIRST)%>";
		document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.action="al_opname_list.jsp";
		document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.submit();
	}

	function cmdListPrev(){
		document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.command.value="<%=String.valueOf(Command.PREV)%>";
		document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.action="al_opname_list.jsp";
		document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.submit();
	}

	function cmdListNext(){
		document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.command.value="<%=String.valueOf(Command.NEXT)%>";
		document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.action="al_opname_list.jsp";
		document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.submit();
	}

	function cmdListLast(){
		document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.command.value="<%=String.valueOf(Command.LAST)%>";
		document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.action="al_opname_list.jsp";
		document.<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>.submit();
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
        <title>HARISMA - AL Opname List</title>
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
        <form name="<%=FrmSrcAlUpload.FRM_AL_UPLOAD%>" method="post" >
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
            <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
            <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
            <tr> 
                <td width="88%" valign="top" align="left"> 
                
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr> 
                        <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->
                        AL OPNAME DATE LIST
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
                                                    <td>
                                                        <table width="100%">
                                                            <tr>
                                                                <td width="6%"  nowrap="nowrap">Emp.Numb :</td>
                                                                <td width="7%"  nowrap="nowrap">
                                                                     <input type="text" name="<%=FrmSrcAlUpload.fieldNames[FrmSrcAlUpload.FRM_FIELD_EMP_NAME]%>"  value="<%=empNumber%>" class="elemenForm" size="14">
                                                              </td>
                                                                  <td width="87%"  nowrap="nowrap">
                                                                   <a href="javascript:cmdSearch()" class="buttonlink">search</a>                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                    
                                                </tr>
                                                <tr> 
                                                    <td valign="top">
                                                        <!-- #BeginEditable "content" -->
                                                        <table width="100%">
                                                            <tr>
                                                                <td>
                                                        <%
                                                        if(vAlOpnameDate.size()>0){
                                                            out.println(drawList(vAlOpnameDate, start));
                                                            ctrLine.setLocationImg(approot+"/images");
                                                            ctrLine.initDefault();
                                                            out.println(ctrLine.drawImageListLimit(iCommand,vectSize,start,recordToGet));
                                                        }
                                                        %>
                                                                </td>
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
                    </table>
                </td> 
            </tr>
            
        </table>
    </form>
    </body>
  </html>