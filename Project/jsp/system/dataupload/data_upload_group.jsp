<%-- 
    Document   : data_upload_group
    Created on : Feb 24, 2016, 11:15:22 AM
    Author     : khirayinnura
--%>

<%@page import="com.dimata.system.session.I_System"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDepartment"%>
<%@page import="com.dimata.harisma.entity.admin.*"%>
<%@page import="com.dimata.util.*"%>
<%@page import="com.dimata.system.entity.dataupload.*"%>
<%@page import="com.dimata.system.form.dataupload.*"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.gui.jsp.*"%>
<%@page import="com.dimata.qdep.form.*"%>

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_DEPARTMENT);%>

<% //int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMPLOYEE, AppObjInfo.OBJ_EMPLOYEE_GRADE_LEVEL); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%!

	public String drawList(Vector objectClass ,  long docId)
	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("50%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
                
                ctrlist.addHeader("System","10%");
                ctrlist.addHeader("Modul","20%");
                ctrlist.addHeader("Type","20%");
		ctrlist.addHeader("Doc Group","20%");
                ctrlist.addHeader("Doc Description","50%");
		
                String type[] = {
                    "Non Icon",
                    "Icon"
                };
                             
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;
                
                String modul = "-";
                
		for (int i = 0; i < objectClass.size(); i++) {
			DataUploadGroup dataUploadGroup = (DataUploadGroup)objectClass.get(i);
			 Vector rowx = new Vector();
			 if(docId == dataUploadGroup.getOID())
				 index = i;
                        try{    
                            modul = I_System.MODULS[dataUploadGroup.getSystemName()][dataUploadGroup.getModul()];
                        } catch(Exception e) {
                            modul = "-";
                        }
                         
                        rowx.add(""+I_System.SYSTEM_NAME[dataUploadGroup.getSystemName()]);
                        rowx.add(""+modul);
                        rowx.add(""+type[dataUploadGroup.getDataGroupTipe()]);
			rowx.add(dataUploadGroup.getDataGroupTitle());
                        rowx.add(dataUploadGroup.getDataGroupDesc());
			lstData.add(rowx);
			lstLinkData.add(String.valueOf(dataUploadGroup.getOID()));
		}

		return ctrlist.draw(index);
		
	}

%>

<%
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidDataGroupId = FRMQueryString.requestLong(request, "hidden_data_group_id");
    String source = FRMQueryString.requestString(request, "source");
    int modul = FRMQueryString.requestInt(request, PstDataUploadGroup.fieldNames[PstDataUploadGroup.FLD_MODUL]);

    String type[] = {
        "Non Icon",
        "Icon"
    };
    
    /*variable declaration*/
    int recordToGet = 10;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    String whereClause = "";
    String orderClause = PstDataUploadGroup.fieldNames[PstDataUploadGroup.FLD_DATA_GROUP_ID];

    CtrlDataUploadGroup ctrlDataUploadGroup = new CtrlDataUploadGroup(request);
    ControlLine ctrLine = new ControlLine();
    Vector listDataGroup = new Vector(1, 1);

    /*switch statement */
    iErrCode = ctrlDataUploadGroup.action(iCommand, oidDataGroupId);
    /* end switch*/
    FrmDataUploadGroup frmDataUploadGroup = ctrlDataUploadGroup.getForm();

    /*count list All GradeLevel*/
    int vectSize = PstDataUploadGroup.getCount(whereClause);

    DataUploadGroup dataUploadGroup = ctrlDataUploadGroup.getDataUploadGroup();
    msgString = ctrlDataUploadGroup.getMessage();

    /*switch list GradeLevel*/
    if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
        //start = PstGradeLevel.findLimitStart(gradeLevel.getOID(),recordToGet, whereClause, orderClause);
        oidDataGroupId = dataUploadGroup.getOID();
    }

    if ((iCommand == Command.FIRST || iCommand == Command.PREV)
            || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
        start = ctrlDataUploadGroup.actionList(iCommand, start, vectSize, recordToGet);
    }
    /* end switch list*/

    /* get record to display */
    listDataGroup = PstDataUploadGroup.list(start, recordToGet, whereClause, orderClause);

    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listDataGroup.size() < 1 && start > 0) {
        if (vectSize - recordToGet > recordToGet) {
            start = start - recordToGet;   //go to Command.PREV
        } else {
            start = 0;
            iCommand = Command.FIRST;
            prevCommand = Command.FIRST; //go to Command.FIRST
        }
        listDataGroup = PstDataUploadGroup.list(start, recordToGet, whereClause, orderClause);
    }
%>

<html>
    <head>
        <!-- #BeginEditable "doctitle" --> 
        <title>HARISMA - Master Data Upload Group</title>
        <script language="JavaScript">


            function cmdAdd(){
                document.frmDataGroup.hidden_data_group_id.value="0";
                document.frmDataGroup.command.value="<%=Command.ADD%>";
                document.frmDataGroup.prev_command.value="<%=prevCommand%>";
                document.frmDataGroup.action="data_upload_group.jsp";
                document.frmDataGroup.submit();
            }

            function cmdAsk(oidDataGroupId){
                document.frmDataGroup.hidden_data_group_id.value=oidDataGroupId;
                document.frmDataGroup.command.value="<%=Command.ASK%>";
                document.frmDataGroup.prev_command.value="<%=prevCommand%>";
                document.frmDataGroup.action="data_upload_group.jsp";
                document.frmDataGroup.submit();
            }

            function cmdConfirmDelete(oidDataGroupId){
                document.frmDataGroup.hidden_data_group_id.value=oidDataGroupId;
                document.frmDataGroup.command.value="<%=Command.DELETE%>";
                document.frmDataGroup.prev_command.value="<%=prevCommand%>";
                document.frmDataGroup.action="data_upload_group.jsp";
                document.frmDataGroup.submit();
            }
            function cmdSave(){
                document.frmDataGroup.command.value="<%=Command.SAVE%>";
                document.frmDataGroup.prev_command.value="<%=prevCommand%>";
                document.frmDataGroup.action="data_upload_group.jsp";
                document.frmDataGroup.submit();
            }

            function cmdEdit(oidDataGroupId){
                document.frmDataGroup.hidden_data_group_id.value=oidDataGroupId;
                document.frmDataGroup.command.value="<%=Command.EDIT%>";
                document.frmDataGroup.prev_command.value="<%=prevCommand%>";
                document.frmDataGroup.action="data_upload_group.jsp";
                document.frmDataGroup.submit();
            }

            function cmdCancel(oidDataGroupId){
                document.frmDataGroup.hidden_data_group_id.value=oidDataGroupId;
                document.frmDataGroup.command.value="<%=Command.EDIT%>";
                document.frmDataGroup.prev_command.value="<%=prevCommand%>";
                document.frmDataGroup.action="data_upload_group.jsp";
                document.frmDataGroup.submit();
            }

            function cmdBack(){
                document.frmDataGroup.command.value="<%=Command.BACK%>";
                document.frmDataGroup.action="data_upload_group.jsp";
                document.frmDataGroup.submit();
            }

            function cmdListFirst(){
                document.frmDataGroup.command.value="<%=Command.FIRST%>";
                document.frmDataGroup.prev_command.value="<%=Command.FIRST%>";
                document.frmDataGroup.action="data_upload_group.jsp";
                document.frmDataGroup.submit();
            }

            function cmdListPrev(){
                document.frmDataGroup.command.value="<%=Command.PREV%>";
                document.frmDataGroup.prev_command.value="<%=Command.PREV%>";
                document.frmDataGroup.action="data_upload_group.jsp";
                document.frmDataGroup.submit();
            }

            function cmdListNext(){
                document.frmDataGroup.command.value="<%=Command.NEXT%>";
                document.frmDataGroup.prev_command.value="<%=Command.NEXT%>";
                document.frmDataGroup.action="data_upload_group.jsp";
                document.frmDataGroup.submit();
            }

            function cmdListLast(){
                document.frmDataGroup.command.value="<%=Command.LAST%>";
                document.frmDataGroup.prev_command.value="<%=Command.LAST%>";
                document.frmDataGroup.action="data_upload_group.jsp";
                document.frmDataGroup.submit();
            }

            function fnTrapKD(){
                //alert(event.keyCode);
                switch(event.keyCode)         {
                    case <%=LIST_PREV%>:
                            cmdListPrev();
                        break        ;
                    case <%=LIST_NEXT%>:
                            cmdListNext();
                        break        ;
                    case <%=LIST_FIRST%>:
                            cmdListFirst();
                        break        ;
                    case <%=LIST_LAST%>:
                            cmdListLast();
                        break;
                    default:
                        break;
                    }
                }
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
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "stylestab" --> 
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "headerscript" --> 
        <script type="text/javascript">
                    function loadSystem(str) {
                        if (str.length == 0) { 
                            document.getElementById("txtHint").innerHTML = "";
                            return;
                        } else {
                            var xmlhttp = new XMLHttpRequest();
                            xmlhttp.onreadystatechange = function() {
                                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                                    document.getElementById("txtHint").innerHTML = xmlhttp.responseText;
                                }
                            };
                            xmlhttp.open("GET", "data_upload_group_ajax.jsp?hidden_data_group_id=" + str, true);
                            xmlhttp.send();
                        }
                    }

                    function loadModul(str) {
                        if (str.length == 0) { 
                            document.getElementById("txtHint").innerHTML = "";
                            return;
                        } else {
                            var xmlhttp = new XMLHttpRequest();
                            xmlhttp.onreadystatechange = function() {
                                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                                    document.getElementById("txtHint").innerHTML = xmlhttp.responseText;
                                }
                            };
                            xmlhttp.open("GET", "data_upload_group_ajax.jsp?<%=PstDataUploadGroup.fieldNames[PstDataUploadGroup.FLD_SYSTEM_NAME]%>=" + str, true);
                            xmlhttp.send();
                        }
                    }
        </script>
        <script src="<%=approot%>/javascripts/jquery.js"></script>
        <script>
                    function pageLoad(){ 
                        loadSystem('<%=oidDataGroupId%>'); 
                    }  
        </script>
        <!-- #EndEditable -->
    </head>
    <body onload="pageLoad()">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
            <%if (source == null || source.length() == 0) {%> 
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
            <%@include file="../../styletemplate/template_header.jsp" %>
            <%} else {%>
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
            <%}%>
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
                                                Master Data &gt; Data Uplaod Group<!-- #EndEditable --> 
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
                                                                                <form name="frmDataGroup" method ="post" action="">
                                                                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                                                                    <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                                                                    <input type="hidden" name="start" value="<%=start%>">
                                                                                    <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                                                    <input type="hidden" name="hidden_data_group_id" value="<%=oidDataGroupId%>">
                                                                                    <input type="hidden" name="source" value="">
                                                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                        <tr align="left" valign="top"> 
                                                                                            <td height="8"  colspan="3"> 
                                                                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                                    <tr align="left" valign="top"> 
                                                                                                        <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;Data Group List </td>
                                                                                                    </tr>
                                                                                                    <%
                                                                                                        try {
                                                                                                            if (listDataGroup.size() > 0) {
                                                                                                    %>
                                                                                                    <tr align="left" valign="top"> 
                                                                                                        <td height="22" valign="middle" colspan="3"> 
                                                                                                            <%= drawList(listDataGroup, oidDataGroupId)%> 
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                    <%  }
                                                  } catch (Exception exc) {
                                                  }%>
                                                                                                    <tr align="left" valign="top"> 
                                                                                                        <td height="8" align="left" colspan="3" class="command"> 
                                                                                                            <span class="command"> 
                                                                                                                <%
                                                                                                                    int cmd = 0;
                                                                                                                    if ((iCommand == Command.FIRST || iCommand == Command.PREV)
                                                                                                                            || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                                                                                                                        cmd = iCommand;
                                                                                                                    } else {
                                                                                                                        if (iCommand == Command.NONE || prevCommand == Command.NONE) {
                                                                                                                            cmd = Command.FIRST;
                                                                                                                        } else {
                                                                                                                            if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
                                                                                                                                cmd = PstDepartment.findLimitCommand(start, recordToGet, vectSize);
                                                                                                                            } else {
                                                                                                                                cmd = prevCommand;
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }
                                                                                                                %>
                                                                                                                <% ctrLine.setLocationImg(approot + "/images");
                                                                                                                    ctrLine.initDefault();
                                                                                                                %>
                                                                                                                <%=ctrLine.drawImageListLimit(cmd, vectSize, start, recordToGet)%> 
                                                                                                            </span> </td>
                                                                                                    </tr>
                                                                                                    <%//if((iCommand == Command.NONE || iCommand == Command.DELETE || iCommand ==Command.BACK || iCommand ==Command.SAVE)&& (frmDocGroup.errorSize()<1)){
                                                                                                    if ((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT) && (frmDataUploadGroup.errorSize() < 1)) {
                                                                                                        if (privAdd) {%>

                                                                                                    <tr align="left" valign="top"> 
                                                                                                        <td> 
                                                                                                            <table cellpadding="0" cellspacing="0" border="0">
                                                                                                                <tr> 
                                                                                                                    <td>&nbsp;</td>
                                                                                                                </tr>
                                                                                                                <tr> 
                                                                                                                    <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                                    <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                                                                                    <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                                    <td height="22" valign="middle" colspan="3" width="951"><a href="javascript:cmdAdd()" class="command">Add 
                                                                                                                            New</a> </td>
                                                                                                                </tr>
                                                                                                            </table>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                    <%}
                                                                                              }%>
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr> 
                                                                                            <td>&nbsp; </td>
                                                                                        </tr>
                                                                                        <tr align="left" valign="top"> 
                                                                                            <td height="8" valign="middle" colspan="3"> 
                                                                                                <%if ((iCommand == Command.ADD) || (iCommand == Command.SAVE) && (frmDataUploadGroup.errorSize() > 0) || (iCommand == Command.EDIT) || (iCommand == Command.ASK)) {%>
                                                                                                <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                                                    <tr> 
                                                                                                        <td colspan="2" class="listtitle"><%=oidDataGroupId == 0 ? "Add" : "Edit"%> Data Upload Group </td>
                                                                                                    </tr>
                                                                                                    <tr> 
                                                                                                        <td height="100%"> 
                                                                                                            <table border="0" cellspacing="2" cellpadding="2" width="50%">
                                                                                                                <div id="txtHint"></div>
                                                                                                                <tr align="left" valign="top"> 
                                                                                                                    <td valign="top" width="21%">Type
                                                                                                                    </td>
                                                                                                                    <td width="79%"> 
                                                                                                                        <select name="<%=frmDataUploadGroup.fieldNames[FrmDataUploadGroup.FRM_FIELD_DATA_GROUP_TIPE]%>">
                                                                                                                            <%
                                                                                                                            for(int i = 0; i < type.length; i++){
                                                                                                                                if (dataUploadGroup.getDataGroupTipe() == i){
                                                                                                                            %>
                                                                                                                                    <option selected="selected" value="<%=i%>"><%= type[i] %></option>
                                                                                                                            <%
                                                                                                                                } else {
                                                                                                                            %>
                                                                                                                                    <option value="<%=i%>"><%= type[i] %></option>
                                                                                                                            <%
                                                                                                                                }
                                                                                                                            }
                                                                                                                            %>
                                                                                                                        </select>
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top"> 
                                                                                                                    <td valign="top" width="21%">Doc Group
                                                                                                                    </td>
                                                                                                                    <td width="79%"> 
                                                                                                                        <input type="text" name="<%=frmDataUploadGroup.fieldNames[FrmDataUploadGroup.FRM_FIELD_DATA_GROUP_TITLE]%>"  value="<%= dataUploadGroup.getDataGroupTitle()%>" class="elemenForm" size="30">
                                                                                                                        * <%=frmDataUploadGroup.getErrorMsg(FrmDataUploadGroup.FRM_FIELD_DATA_GROUP_TITLE)%></td>
                                                                                                                </tr>

                                                                                                                <tr align="left" valign="top"> 
                                                                                                                    <td valign="top" width="21%">Doc Description
                                                                                                                    </td>
                                                                                                                    <td width="79%"> 
                                                                                                                        <input type="text" name="<%=frmDataUploadGroup.fieldNames[FrmDataUploadGroup.FRM_FIELD_DATA_GROUP_DESC]%>"  value="<%= dataUploadGroup.getDataGroupDesc()%>" class="elemenForm" size="30">
                                                                                                                </tr>

                                                                                                            </table>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                    <tr align="left" valign="top" > 
                                                                                                        <td colspan="2" class="command"> 
                                                                                                            <%
                                                                                                                ctrLine.setLocationImg(approot + "/images");
                                                                                                                ctrLine.initDefault();
                                                                                                                ctrLine.setTableWidth("80%");
                                                                                                                String scomDel = "javascript:cmdAsk('" + oidDataGroupId + "')";
                                                                                                                String sconDelCom = "javascript:cmdConfirmDelete('" + oidDataGroupId + "')";
                                                                                                                String scancel = "javascript:cmdEdit('" + oidDataGroupId + "')";
                                                                                                                ctrLine.setBackCaption("Back to List Data Group");
                                                                                                                ctrLine.setCommandStyle("buttonlink");
                                                                                                                ctrLine.setAddCaption("Add Data Group");
                                                                                                                ctrLine.setSaveCaption("Save Data Group");
                                                                                                                ctrLine.setDeleteCaption("Delete Data Group");
                                                                                                                ctrLine.setConfirmDelCaption("Yes Delete Data Group");

                                                                                                                if (privDelete) {
                                                                                                                    ctrLine.setConfirmDelCommand(sconDelCom);
                                                                                                                    ctrLine.setDeleteCommand(scomDel);
                                                                                                                    ctrLine.setEditCommand(scancel);
                                                                                                                } else {
                                                                                                                    ctrLine.setConfirmDelCaption("");
                                                                                                                    ctrLine.setDeleteCaption("");
                                                                                                                    ctrLine.setEditCaption("");
                                                                                                                }

                                                                                                                if (privAdd == false && privUpdate == false) {
                                                                                                                    ctrLine.setSaveCaption("");
                                                                                                                }

                                                                                                                if (privAdd == false) {
                                                                                                                    ctrLine.setAddCaption("");
                                                                                                                }

                                                                                                                if (iCommand == Command.ASK) {
                                                                                                                    ctrLine.setDeleteQuestion(msgString);
                                                                                                                }
                                                                                                            %>
                                                                                                            <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%> 
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                    <tr align="left" valign="top" > 
                                                                                                        <td colspan="3"> 
                                                                                                            <div align="left"></div>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                </table>
                                                                                                <%}%>
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
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
            <tr>
                <td valign="bottom">
                    <!-- untuk footer -->
                    <%@include file="../../footer.jsp" %>
                </td>

            </tr>
            <%} else {%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
                    <%@ include file = "../../main/footer.jsp" %>
                    <!-- #EndEditable --> </td>
            </tr>
            <%}%>
        </table>
    </body>
</html>
