<%@page import="com.dimata.harisma.entity.attendance.I_Atendance"%>
<%
            /*
             * Page Name  		:  kpi achievment.jsp
             * Created on 		:  [date] [time] AM/PM
             *
             * @author  		: Priska_20150917
             * @version  		: -
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
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%@ include file = "../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_DIVISION);%>
<%@ include file = "../main/checkuser.jsp" %>

<%!    public String drawList(Vector objectClass, long empId) {
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("80%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("No ","5%");
        ctrlist.addHeader("Company","20%");
        ctrlist.addHeader("KPI Title","20%");
        ctrlist.addHeader("Description","30%");
        ctrlist.addHeader("Valid from","10%");
        ctrlist.addHeader("Valid to","10%");
        ctrlist.addHeader("Value Type","5%");
        ctrlist.addHeader("TARGET","5%");
        ctrlist.addHeader("ACHIEVMENT","5%");
        ctrlist.addHeader("PERBEDAAN","5%");
        ctrlist.addHeader("Add Achievment","5%");
        // ctrlist.addHeader("List Group","30%");

        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.reset();
        int index = -1;

        //kpi_list
                    Vector listComp = PstCompany.list(0, 0, "", " COMPANY ");
                   
                    Hashtable<String, String> compNames = new Hashtable();
                    for (int c = 0; c < listComp.size(); c++) {
                        Company comp = (Company) listComp.get(c);
                        compNames.put(""+comp.getOID(), comp.getCompany());                       
                    }
        
        for (int i = 0; i < objectClass.size(); i++) {
            KPI_List kpi_list = (KPI_List) objectClass.get(i);
            Vector rowx = new Vector();
            rowx.add(""+(i+1));
            rowx.add(""+compNames.get(""+kpi_list.getCompany_id()));
            rowx.add(""+kpi_list.getKpi_title());
            rowx.add(""+kpi_list.getDescription());
            rowx.add(""+kpi_list.getValid_from());
            rowx.add(""+kpi_list.getValid_to());
            rowx.add(""+kpi_list.getValue_type());
            
            double totalTarget = PstKPI_Employee_Target.getTotalTargetEmployee(empId, kpi_list.getOID());//mencari total target
            double totalAchievment = PstKPI_Employee_Achiev.getTotalAchievEmployee(empId, kpi_list.getOID());//mencari total achievment
            
            rowx.add(""+totalTarget);
            rowx.add(""+totalAchievment);
            rowx.add(""+(Math.abs(totalTarget-totalAchievment)));
            rowx.add("<a href=\"javascript:cmdAddAchievment('"+empId+"','"+kpi_list.getOID()+"')\">Add Achievment</a>");
            lstData.add(rowx);
            lstLinkData.add(String.valueOf(kpi_list.getOID()));
        }
        return ctrlist.draw(index);
    }

%>

<%
            int iCommand = FRMQueryString.requestCommand(request);
            int start = FRMQueryString.requestInt(request, "start");
            int prevCommand = FRMQueryString.requestInt(request, "prev_command");
            long oidKPI_Employee_Achiev = FRMQueryString.requestLong(request, "hidden_kPI_Employee_Achiev_id");

      
            /*variable declaration*/
            int recordToGet = 0;
            String msgString = "";
            int iErrCode = FRMMessage.NONE;
            String whereClause = "";
            String orderClause = "";

            CtrlKPI_Employee_Achiev ctrlKPI_Employee_Achiev = new CtrlKPI_Employee_Achiev(request);
            ControlLine ctrLine = new ControlLine();
            Vector listKPI_Employee_Achiev = new Vector(1, 1);

            /*switch statement */
            iErrCode = ctrlKPI_Employee_Achiev.action(iCommand, oidKPI_Employee_Achiev);
            /* end switch*/
            FrmKPI_Employee_Achiev frmKPI_Employee_Achiev = ctrlKPI_Employee_Achiev.getForm();

            
            KPI_Employee_Achiev kPI_Employee_Achiev = ctrlKPI_Employee_Achiev.getdKPI_Employee_Achiev();
            msgString = ctrlKPI_Employee_Achiev.getMessage();
            //long empId = 504404524286105253L;
            long empId = emplx.getOID();
            String whereEmployeeID = " hket."+ PstKPI_Employee_Target.fieldNames[PstKPI_Employee_Target.FLD_EMPLOYEE_ID] + " = " + empId ;
            Vector listKpiTarget = PstKPI_List.listInnerJoinKPIEmpTarget(0, 0, whereEmployeeID, "") ; 

%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" -->
        <title>HARISMA - Master Data KPI_Employee_Achiev</title>
        <script language="JavaScript">

function cmdAddAchievment(oidEmp,kpiListId){
        window.open("kpi_achievment_add.jsp?oidEmp="+oidEmp+"&kpiListId="+kpiListId, null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
}

            function cmdAdd(){
                document.frmkPI_Employee_Achiev.hidden_kPI_Employee_Achiev_id.value="0";
                document.frmkPI_Employee_Achiev.command.value="<%=Command.ADD%>";
                document.frmkPI_Employee_Achiev.prev_command.value="<%=prevCommand%>";
                document.frmkPI_Employee_Achiev.action="kPI_Employee_Achiev.jsp";
                document.frmkPI_Employee_Achiev.submit();
            }

            function cmdAsk(oidKPI_Employee_Achiev){
                document.frmkPI_Employee_Achiev.hidden_kPI_Employee_Achiev_id.value=oidKPI_Employee_Achiev;
                document.frmkPI_Employee_Achiev.command.value="<%=Command.ASK%>";
                document.frmkPI_Employee_Achiev.prev_command.value="<%=prevCommand%>";
                document.frmkPI_Employee_Achiev.action="kPI_Employee_Achiev.jsp";
                document.frmkPI_Employee_Achiev.submit();
            }

            function cmdConfirmDelete(oidKPI_Employee_Achiev){
                document.frmkPI_Employee_Achiev.hidden_kPI_Employee_Achiev_id.value=oidKPI_Employee_Achiev;
                document.frmkPI_Employee_Achiev.command.value="<%=Command.DELETE%>";
                document.frmkPI_Employee_Achiev.prev_command.value="<%=prevCommand%>";
                document.frmkPI_Employee_Achiev.action="kPI_Employee_Achiev.jsp";
                document.frmkPI_Employee_Achiev.submit();
            }
            function cmdSave(){
                document.frmkPI_Employee_Achiev.command.value="<%=Command.SAVE%>";
                document.frmkPI_Employee_Achiev.prev_command.value="<%=prevCommand%>";
                document.frmkPI_Employee_Achiev.action="kPI_Employee_Achiev.jsp";
                document.frmkPI_Employee_Achiev.submit();
            }

            function cmdEdit(oidKPI_Employee_Achiev){
                document.frmkPI_Employee_Achiev.hidden_kPI_Employee_Achiev_id.value=oidKPI_Employee_Achiev;
                document.frmkPI_Employee_Achiev.command.value="<%=Command.EDIT%>";
                document.frmkPI_Employee_Achiev.prev_command.value="<%=prevCommand%>";
                document.frmkPI_Employee_Achiev.action="kPI_Employee_Achiev.jsp";
                document.frmkPI_Employee_Achiev.submit();
            }

            function cmdCancel(oidKPI_Employee_Achiev){
                document.frmkPI_Employee_Achiev.hidden_kPI_Employee_Achiev_id.value=oidKPI_Employee_Achiev;
                document.frmkPI_Employee_Achiev.command.value="<%=Command.EDIT%>";
                document.frmkPI_Employee_Achiev.prev_command.value="<%=prevCommand%>";
                document.frmkPI_Employee_Achiev.action="kPI_Employee_Achiev.jsp";
                document.frmkPI_Employee_Achiev.submit();
            }

            function cmdBack(){
                document.frmkPI_Employee_Achiev.command.value="<%=Command.BACK%>";
                document.frmkPI_Employee_Achiev.action="kPI_Employee_Achiev.jsp";
                document.frmkPI_Employee_Achiev.submit();
            }

            function cmdListFirst(){
                document.frmkPI_Employee_Achiev.command.value="<%=Command.FIRST%>";
                document.frmkPI_Employee_Achiev.prev_command.value="<%=Command.FIRST%>";
                document.frmkPI_Employee_Achiev.action="kPI_Employee_Achiev.jsp";
                document.frmkPI_Employee_Achiev.submit();
            }

            function cmdListPrev(){
                document.frmkPI_Employee_Achiev.command.value="<%=Command.PREV%>";
                document.frmkPI_Employee_Achiev.prev_command.value="<%=Command.PREV%>";
                document.frmkPI_Employee_Achiev.action="kPI_Employee_Achiev.jsp";
                document.frmkPI_Employee_Achiev.submit();
            }

            function cmdListNext(){
                document.frmkPI_Employee_Achiev.command.value="<%=Command.NEXT%>";
                document.frmkPI_Employee_Achiev.prev_command.value="<%=Command.NEXT%>";
                document.frmkPI_Employee_Achiev.action="kPI_Employee_Achiev.jsp";
                document.frmkPI_Employee_Achiev.submit();
            }

            function cmdListLast(){
                document.frmkPI_Employee_Achiev.command.value="<%=Command.LAST%>";
                document.frmkPI_Employee_Achiev.prev_command.value="<%=Command.LAST%>";
                document.frmkPI_Employee_Achiev.action="kPI_Employee_Achiev.jsp";
                document.frmkPI_Employee_Achiev.submit();
            }

            function fnTrapKD(){
                //alert(event.keyCode);
                switch(event.keyCode) {
                    case <%=LIST_PREV%>:
                            cmdListPrev();
                        break;
                    case <%=LIST_NEXT%>:
                            cmdListNext();
                        break;
                    case <%=LIST_FIRST%>:
                            cmdListFirst();
                        break;
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
        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "stylestab" -->
        <link rel="stylesheet" href="../styles/tab.css" type="text/css">
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
                                                    Master Data &gt; <%=dictionaryD.getWord(I_Dictionary.COMPANY)%><!-- #EndEditable -->
                                                </strong></font>
                                        </td>
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
                                                                        <tr>
                                                                            <td valign="top">
                                                                                <!-- #BeginEditable "content" -->
                                                                                <form name="frmkPI_Employee_Achiev" method ="post" action="">
                                                                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                                                                    <input type="hidden" name="start" value="<%=start%>">
                                                                                    <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                                                    <input type="hidden" name="hidden_kPI_Employee_Achiev_id" value="<%=oidKPI_Employee_Achiev%>">
                                                                                    <input type="text" name="test" value="<%= emplx.getOID()  %>">
                                                                                    <%=drawList(listKpiTarget, empId)%>
                                                                                     
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
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
        </table>
    </body>
    <!-- #BeginEditable "script" -->
    <script language="JavaScript">
                //var oBody = document.body;
                //var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
    </script>
    <!-- #EndEditable -->
    <!-- #EndTemplate --></html>

