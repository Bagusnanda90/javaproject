
<%


            /*******************************************************************
             * Page Description 	: [project description ... ]
             * Imput Parameters 	: [input parameter ...]
             * Output 				: [output ...]
             *******************************************************************/
%>
<!-- Ari_20110901_01 { -->
<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>

<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>

<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.util.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>

<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.locker.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK);%>
<%@ include file = "../../main/checkuser.jsp" %>

<%
// Check privilege except VIEW, view is already checked on checkuser.jsp as basic access
//boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
%>

<%--    public String drawList(Vector objectClass, int st, String approot) {
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("No.", "2%");
        ctrlist.addHeader("Picture", "1%");
        ctrlist.addHeader("Name", "15%");
        ctrlist.addHeader("Division", "10%");
        ctrlist.addHeader("Department", "10%");
        ctrlist.addHeader("Position", "10%");
        ctrlist.addHeader("Level", "5%");
        ctrlist.addHeader("Commercing Date", "7%");



        Vector lstData = ctrlist.getData();
        ctrlist.reset();

        for (int i = 0; i < objectClass.size(); i++) {
            Vector temp = (Vector) objectClass.get(i);
            Employee employee = (Employee) temp.get(0);
            Department department = (Department) temp.get(1);
            Position position = (Position) temp.get(2);
            Section section = (Section) temp.get(3);
            EmpCategory empCategory = (EmpCategory) temp.get(4);
            Level level = (Level) temp.get(5);
            Division division = (Division) temp.get(9);


            Vector rowx = new Vector();
            rowx.add(String.valueOf(st + 1 + i));
            rowx.add("" + "<img src=\"" + approot + "/imgcache/" + employee.getEmployeeNum()
                    + ".JPEG\" align=\"top\" height=\"90\" width=\"80\">");
            rowx.add(employee.getFullName());
            rowx.add(division.getDivision());
            rowx.add(department.getDepartment());
            rowx.add(position.getPosition());
            rowx.add(level.getLevel());
            rowx.add(employee.getCommencingDate() == null ? "" : "<nobr>" + Formater.formatDate(employee.getCommencingDate(), "dd MMM yyyy") + "</nobr>");



            lstData.add(rowx);

        }
        return ctrlist.draw();
    }
--%>

<%
            long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");
            int iCommand = FRMQueryString.requestCommand(request);
            int prevCommand = FRMQueryString.requestInt(request, "prev_command");
            int start = FRMQueryString.requestInt(request, "start");
            long oidEmpPicture = FRMQueryString.requestLong(request, "emp_picture_oid");
            String pictName = FRMQueryString.requestString(request, "pict");
            int iErrCode = FRMMessage.ERR_NONE;


            String msgStr = "";
            int recordToGet = 10;
            int vectSize = 0;
            String orderClause = "";
            String whereClause = "RESIGNED = 0";
            Hashtable listJournalReceive = new Hashtable();

            ControlLine ctrLine = new ControlLine();
            SrcEmployee srcEmployee = new SrcEmployee();
            CtrlEmployee ctrlEmployee = new CtrlEmployee(request);
            FrmSrcEmployee frmSrcEmployee = new FrmSrcEmployee(request, srcEmployee);

            Vector listEmpPicture = new Vector(1, 1);
            CtrlEmpPicture ctrlEmpPicture = new CtrlEmpPicture(request);
            SessEmployeePicture sessEmployeePicture = new SessEmployeePicture();
            String pictPath = "";
            try {
                pictPath = sessEmployeePicture.fetchImagePeserta(oidEmployee);

            } catch (Exception e) {
                System.out.println("err." + e.toString());
            }
            System.out.println("pictPath sebelum..............." + pictPath);

            iErrCode = ctrlEmpPicture.action(iCommand, oidEmpPicture, oidEmployee);
            FrmEmpPicture frmEmpPicture = ctrlEmpPicture.getForm();
            EmpPicture empPicture = ctrlEmpPicture.getEmpPicture();

            if (iCommand == Command.GOTO) {
                frmSrcEmployee = new FrmSrcEmployee(request, srcEmployee);
                frmSrcEmployee.requestEntityObject(srcEmployee);
            }

            if (iCommand == Command.LIST) {
                frmSrcEmployee.requestEntityObject(srcEmployee);
            }

            if ((iCommand == Command.NEXT) || (iCommand == Command.FIRST) || (iCommand == Command.PREV) || (iCommand == Command.LAST) || (iCommand == Command.BACK)) {
                try {
                    srcEmployee = (SrcEmployee) session.getValue(SessEmployee.SESS_SRC_EMPLOYEE);
                    if (srcEmployee == null) {
                        srcEmployee = new SrcEmployee();
                    }
                } catch (Exception e) {
                    srcEmployee = new SrcEmployee();
                }
            }

            SessEmployee sessEmployee = new SessEmployee();
            session.putValue(SessEmployee.SESS_SRC_EMPLOYEE, srcEmployee);

            if (iCommand == Command.SAVE && prevCommand == Command.ADD) {
                start = PstEmployee.findLimitStart(oidEmployee, recordToGet, whereClause, orderClause);
                vectSize = PstEmployee.getCount(whereClause);
            } else {
                vectSize = sessEmployee.countEmployee(srcEmployee);
            }

            if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                    || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
                start = ctrlEmployee.actionList(iCommand, start, vectSize, recordToGet);
            }

            Vector listEmployee = new Vector(1, 1);
            if (iCommand == Command.SAVE && prevCommand == Command.ADD) {
                listEmployee = sessEmployee.searchEmployee(new SrcEmployee(), start, recordToGet);
            } else {
                try {
                    listEmployee = sessEmployee.searchEmployee(srcEmployee, start, recordToGet);
                } catch (Exception ex) {
                }
            }
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" -->
        <title>HARISMA - Search Employee</title>
        <script language="JavaScript">
            function cmdSearch()        {
                document.frmsrcemployee.command.value="<%=String.valueOf(Command.LIST)%>";
                document.frmsrcemployee.action="new_employee_list2.jsp";
                document.frmsrcemployee.submit();
            }

            function cmdListFirst(){
                document.frmsrcemployee.command.value="<%=Command.FIRST%>";
                document.frmsrcemployee.action="new_employee_list2.jsp";
                document.frmsrcemployee.submit();
            }

            function cmdListPrev(){
                document.frmsrcemployee.command.value="<%=Command.PREV%>";
                document.frmsrcemployee.action="new_employee_list2.jsp";
                document.frmsrcemployee.submit();
            }

            function cmdListNext(){
                document.frmsrcemployee.command.value="<%=Command.NEXT%>";
                document.frmsrcemployee.action="new_employee_list2.jsp";
                document.frmsrcemployee.submit();
            }

            function cmdListLast(){
                document.frmsrcemployee.command.value="<%=Command.LAST%>";
                document.frmsrcemployee.action="new_employee_list2.jsp";
                document.frmsrcemployee.submit();
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
                }

                function hideObjectForLockers(){
                }

                function hideObjectForCanteen(){
                }

                function hideObjectForClinic(){
                }

                function hideObjectForMasterdata(){
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
    </head>

    <body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">
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
                                                    Employee &gt; Data Bank &gt; New Employee List<!-- #EndEditable -->
                                                </strong></font>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td class="tablecolor">
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                                            <tr>
                                                                <td valign="top">
                                                                    <!-- #BeginEditable "content" -->
                                                                    <form name="frmsrcemployee" method="post" action="">
                                                                        <input type="hidden" name="command" value="<%=iCommand%>">
                                                                        <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                                        <input type="hidden" name="start" value="<%=start%>">
                                                                        <input type="hidden" name="employee_oid" value="<%=oidEmployee%>">
                                                                        <input type="hidden" name="emp_picture_oid" value="<%=oidEmpPicture%>">

                                                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                            <tr align="left" valign="top">
                                                                                <td valign="middle" colspan="2">
                                                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                                                                                        <tr>
                                                                                            <td width="3%">&nbsp;</td>
                                                                                            <td width="97%">&nbsp;</td>
                                                                                            <td width="0%">&nbsp;</td>
                                                                                        </tr>
                                                                                        <tr>

                                                                                            <td width="100%">
                                                                                                <table border="0" cellspacing="2" cellpadding="2" width="72%">
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td width="17%" height="24" nowrap>Commencing
                                                                                                            Date</td>
                                                                                                        <td width="83%"><%--=ControlDate.drawDateWithStyle(frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_START_COMMENC], srcEmployee.getStartCommenc()==null?new Date():srcEmployee.getStartCommenc(), 0, -50, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"")--%>
                                                                                                            <%Calendar cal = new GregorianCalendar();
                                                                                                                        cal.add(Calendar.MONTH, -1);
                                                                                                                        Date date = new Date(cal.get(Calendar.YEAR) - 1900, cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                                                                                                                        out.println(ControlDate.drawDateWithStyle(frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_START_COMMENC], date, 2, -30, "formElemen", ""));%>
                                                                                                            to <%=ControlDate.drawDateWithStyle(frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_END_COMMENC], srcEmployee.getEndCommenc() == null ? new Date() : srcEmployee.getEndCommenc(), 0, -50, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%></td>
                                                                                                    </tr>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td width="17%"> <div align="left"></div></td>
                                                                                                        <td width="83%">&nbsp;</td>
                                                                                                    </tr>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td width="17%"> <div align="left"></div></td>
                                                                                                        <td width="83%">
                                                                                                            <table width="30%" border="0" cellspacing="0" cellpadding="0">
                                                                                                                <tr>
                                                                                                                    <td width="1%"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                                                                                    <td width="1%"><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                                                                                                    <td width="28%" class="command" nowrap><a href="javascript:cmdSearch()">View New Employee List</a></td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td width="1%">&nbsp;</td>
                                                                                                                    <td width="1%">&nbsp;</td>
                                                                                                                    <td width="28%">&nbsp;</td>

                                                                                                                </tr>
                                                                                                            </table></td>
                                                                                                    </tr>
                                                                                                    <%
                                                                                                                Enumeration e = listJournalReceive.keys();

                                                                                                                while (e.hasMoreElements()) {
                                                                                                                    String key = (String) e.nextElement();
                                                                                                                    Employee employee = (Employee) listJournalReceive.get(key);
                                                                                                    %>
                                                                                                  <table>
                                                                                                        <tr>
                                                                                                            <td>
                                                                                                                
                                                                                                                <table width="100%" border="0" cellspacing="3" cellpadding="0">
                                                                                                                    <tr>
                                                                                                                        <td colspan="4">&nbsp;</td>
                                                                                                                    </tr>

                                                                                                                    <tr>
                                                                                                                        <td width="8%" rowspan="4"><%
                                                                                                                             if (pictPath != null && pictPath.length() > 0) {
                                                                                                                                 out.println("<img width=\"100\" height=\"110\" src=\"" + approot + "/" + pictPath + "\">");
                                                                                                                             } else {
                                                                                                                            %>
                                                                                                                            <img width="100" height="110" src="<%=approot%>/imgcache/no_photo.JPEG">
                                                                                                                            <%

                                                                                                                                 }
                                                                                                                            %></td>
                                                                                                                        <td width="15%" height="20" nowrap>Full Name</td>
                                                                                                                        <td width="1%" height="20" nowrap>:</td>
                                                                                                                        <td width="39% height="20"><%=employee.getFullName()%> </td>
                                                                                                                        <td width="5%" height="20" nowrap></td>
                                                                                                                    </tr>
                                                                                                                    <tr>
                                                                                                                        <td width="15%" height="20" nowrap><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                                                                                                        <td width="1%" height="20" nowrap>:</td>
                                                                                                                        <td width="29%" height="20"><% Department department = new Department();
                                                                                                                             try {
                                                                                                                                 department = PstDepartment.fetchExc(employee.getDepartmentId());
                                                                                                                             } catch (Exception exc) {
                                                                                                                                 department = new Department();
                                                                                                                             }
                                                                                                                        %>
                                                                                                                        <%=department.getDepartment()%></td>
                                                                                                                        <td width="5%" height="20" nowrap></td>
                                                                                                                    </tr>
                                                                                                                    <tr>
                                                                                                                        <td width="15%" height="20" nowrap>Position</td>
                                                                                                                        <td width="1%" height="20" nowrap>:</td>
                                                                                                                        <td width="29%" height="20"><% Position position = new Position();
                                                                                                                             try {
                                                                                                                                 position = PstPosition.fetchExc(employee.getPositionId());
                                                                                                                             } catch (Exception exc) {
                                                                                                                                 position = new Position();
                                                                                                                             }
                                                                                                                        %>
                                                                                                                        <%=position.getPosition()%></td>
                                                                                                                        <td width="5%" height="20" nowrap></td>
                                                                                                                    </tr>
                                                                                                                    <tr>
                                                                                                                        <td width="15%" height="20" nowrap><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></td>
                                                                                                                        <td width="1%" height="20" nowrap>:</td>
                                                                                                                        <td width="39%" height="20"> <% Division division = new Division();
                                                                                                                             try {
                                                                                                                                 division = PstDivision.fetchExc(employee.getDivisionId());
                                                                                                                             } catch (Exception exc) {
                                                                                                                                 division = new Division();
                                                                                                                             }
                                                                                                                        %>
                                                                                                                        <%=division.getDivision()%></td>
                                                                                                                        <td width="5%" height="20" nowrap></td>
                                                                                                                    </tr>
                                                                                                                    <tr>
                                                                                                                        <td width="15%" height="20" nowrap>Level</td>
                                                                                                                        <td width="1%" height="20" nowrap>:</td>
                                                                                                                        <td width="39%" height="20"><% Level level = new Level();
                                                                                                                             try {
                                                                                                                                 level = PstLevel.fetchExc(employee.getLevelId());
                                                                                                                             } catch (Exception exc) {
                                                                                                                                 level = new Level();
                                                                                                                             }
                                                                                                                        %>
                                                                                                                        <%=level.getLevel()%> </td>
                                                                                                                        <td width="5%" height="20" nowrap></td>
                                                                                                                    </tr>
                                                                                                                    <tr>
                                                                                                                        <td width="15%" height="20" nowrap>Commencing Date</td>
                                                                                                                        <td width="1%" height="20" nowrap>:</td>
                                                                                                                        <td width="29%" height="20"><%=employee.getCommencingDate()%></td>
                                                                                                                        <td width="5%" height="20" nowrap></td>
                                                                                                                    </tr>

                                                                                                                    <tr>
                                                                                                                        <td colspan="4">&nbsp;</td>
                                                                                                                    </tr>
                                                                                                                </table>
                                                                                                                        
                                                                                                            </td>
                                                                                                        </tr>
                                                                                                    </table>
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
    <!-- #BeginEditable "script" -->
    <!-- #EndEditable -->
    <!-- #EndTemplate --></html>
<!-- } -->

