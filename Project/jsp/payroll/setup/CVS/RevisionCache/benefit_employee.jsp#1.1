<%-- 
    Document   : benefit_employee
    Created on : Mar 9, 2015, 9:40:00 AM
    Author     : Hendra Putu
--%>
<%@page import="com.dimata.util.Formater"%>
<%@page import="com.dimata.harisma.entity.payroll.SrcBenefitEmp"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.qdep.form.Control"%>
<%@page import="com.dimata.harisma.entity.payroll.PstBenefitPeriodEmp"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.harisma.entity.payroll.PstBenefitConfig"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayPeriod"%>
<%@page import="com.dimata.harisma.entity.payroll.BenefitConfig"%>
<%@page import="com.dimata.harisma.entity.payroll.PayPeriod"%>
<%@page import="com.dimata.harisma.entity.payroll.BenefitPeriod"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@page import="com.dimata.harisma.session.payroll.SessBenefitLevel"%>
<%@page import="javax.swing.text.Style"%>
<%@ page language="java" %>

<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.form.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_SETUP, AppObjInfo.OBJ_PAYROLL_SETUP_COMPONENT);%>
<%@ include file = "../../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%!
    public String drawList(Vector objectClass) {

        ControlList ctrlist = new ControlList();

        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("tblStyle");
        ctrlist.setTitleStyle("title_tbl");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("title_tbl");
        
        ctrlist.setCellSpacing("0");
        ctrlist.addHeader("No", "");
        ctrlist.addHeader("Payroll Period", "");
        ctrlist.addHeader("Payroll | Name","");
        ctrlist.addHeader("Level Code", "");
        ctrlist.addHeader("Level Point", "");
        ctrlist.addHeader("Flat Service","");
        ctrlist.addHeader("Service by Point","");
        ctrlist.addHeader("Total Service Charge","");

        ctrlist.setLinkRow(1); // untuk menge-sett link di kolom pertama atau dikolom yg lain

        Vector lstData = ctrlist.getData();

        ctrlist.reset(); //berfungsi untuk menginisialisasi list menjadi kosong

        int no = 0;
        for (int i = 0; i < objectClass.size(); i++) {     
            SrcBenefitEmp benefitEmp = (SrcBenefitEmp) objectClass.get(i);
            // rowx will be created secara berkesinambungan base on i
            Vector rowx = new Vector();
            no = no + 1;
            rowx.add("" + no);
            rowx.add(benefitEmp.getPeriod());
            rowx.add(benefitEmp.getEmployeeNum()+" | "+benefitEmp.getEmployeeName());
            rowx.add(benefitEmp.getLevelCode());
            rowx.add(""+benefitEmp.getLevelPoint());
            rowx.add(""+Formater.formatNumberMataUang(benefitEmp.getFlatService(), "Rp"));
            rowx.add(""+Formater.formatNumberMataUang(benefitEmp.getServicePoint(), "Rp"));
            rowx.add(""+Formater.formatNumberMataUang(benefitEmp.getFlatService() + benefitEmp.getServicePoint(), "Rp"));
            lstData.add(rowx);

        }

        return ctrlist.draw(); // mengembalikan data-data control list

    }
%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long periodId = FRMQueryString.requestLong(request, "period_id");
    String empNum = FRMQueryString.requestString(request, "emp_num");
    String empName = FRMQueryString.requestString(request, "emp_name");
    

    /*variable declaration*/
    int recordToGet = 10;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    String whereClause = "";
    String orderClause = "";//
    Vector listBenefitEmp = new Vector();
    ControlLine ctrLine = new ControlLine();

    /*count list All Position*/
    int vectSize = PstBenefitPeriodEmp.getCount(periodId, empNum, empName);     
    Control control = new Control();
    if ((iCommand == Command.FIRST || iCommand == Command.PREV)
            || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
        start = control.actionList(iCommand, start, vectSize, recordToGet);
    }
    /* end switch list*/

    /* get record to display */
    //if (iCommand == Command.LIST){
        listBenefitEmp = PstBenefitPeriodEmp.list(start, recordToGet, periodId, empNum, empName);
    //}

    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listBenefitEmp.size() < 1 && start > 0) {
        if (vectSize - recordToGet > recordToGet) {
            start = start - recordToGet;   //go to Command.PREV
        } else {
            start = 0;
            iCommand = Command.FIRST;
            prevCommand = Command.FIRST; //go to Command.FIRST
        }
        listBenefitEmp = PstBenefitPeriodEmp.list(start, recordToGet, periodId, empNum, empName);
    }
%>
<html>
    <head>
        <!-- #BeginEditable "doctitle" --> 
        <title>HARISMA - Benefit Employee</title>
        <!-- #EndEditable --> 
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <!-- #BeginEditable "styles" --> 
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
        
        <style type="text/css">
            #menu_utama {color: #0066CC; font-weight: bold; padding: 5px 14px; border-left: 1px solid #0066CC; font-size: 14px; background-color: #F7F7F7;}
            #mn_utama {color: #FF6600; padding: 5px 14px; border-left: 1px solid #999; font-size: 14px; background-color: #F5F5F5;}            
            #btn {
              background: #3498db;
              border: 1px solid #0066CC;
              border-radius: 3px;
              font-family: Arial;
              color: #ffffff;
              font-size: 12px;
              padding: 3px 9px 3px 9px;
            }

            .tblStyle {border-collapse: collapse;font-size: 11px;}
            .tblStyle td {padding: 3px 5px; border: 1px solid #CCC;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            
            #btn:hover {
              background: #3cb0fd;
              border: 1px solid #3498db;
            }
            #titleTd {background-color: #3cb0fd; color: #FFF; padding: 3px 5px; border-left: 1px solid #0066CC;}
            #subtitle {padding: 2px 7px; font-weight: bold; background-color: #FFF; border-left: 1px solid #3498db;}
            #td1{ padding: 3px;}
            #td2{ padding: 3px 7px 3px 5px;}
            #tdrs {padding: 3px; border-top: 1px solid #333;text-align: right;}
            #tdrs1 { padding: 3px; border-top: 1px solid #373737;text-align: right;background-color: #ebffd2; color: #3d6a02; font-weight: bold;}
            #tdrs2 { padding: 3px; text-align: right;background-color: #dff6ff; color: #197a9e; font-weight: bold;}
            #tbl {border-collapse: collapse;}
            #td3 {padding: 3px; border: 1px solid #999;}
            #td3Header{padding: 3px; border: 1px solid #999; background-color: #DDD; color:#333; font-weight: bold;}
            #td4 {padding:3px 5px 3px 7px;background-color:#F5F5F5;}
            #td5 {padding:3px 5px 3px 7px;background-color:#F7F7F7;}
            #td4L {padding:3px 5px 3px 7px;background-color:#F5F5F5;border-left: 1px solid #0066CC;}
            #td5L {padding:3px 5px 3px 7px;background-color:#F7F7F7;border-left: 1px solid #0066CC;}
            #tdsave {padding: 3px;background-color: #FFF;}
        </style>
        <script type="text/javascript">
            function cmdSearch(){ 
                document.frm_src.command.value="<%=Command.LIST%>";
                document.frm_src.action="benefit_employee.jsp";
                document.frm_src.submit();
            }
            
            function cmdListFirst(){
                document.frm_src.command.value="<%=Command.FIRST%>";
                document.frm_src.prev_command.value="<%=Command.FIRST%>";
                document.frm_src.action="benefit_employee.jsp";
                document.frm_src.submit();
            }

            function cmdListPrev(){
                document.frm_src.command.value="<%=Command.PREV%>";
                document.frm_src.prev_command.value="<%=Command.PREV%>";
                document.frm_src.action="benefit_employee.jsp";
                document.frm_src.submit();
            }

            function cmdListNext(){
                document.frm_src.command.value="<%=Command.NEXT%>";
                document.frm_src.prev_command.value="<%=Command.NEXT%>";
                document.frm_src.action="benefit_employee.jsp";
                document.frm_src.submit();
            }

            function cmdListLast(){
                document.frm_src.command.value="<%=Command.LAST%>";
                document.frm_src.prev_command.value="<%=Command.LAST%>";
                document.frm_src.action="benefit_employee.jsp";
                document.frm_src.submit();
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
    </head>
    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../../styletemplate/template_header.jsp" %>
            <%} else {%>
            <tr> 
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
                    <!-- #BeginEditable "header" --> 
                    <%@ include file = "../../main/header.jsp" %>
                    <!-- #EndEditable --> </td>
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
            <tr> 
                <td width="88%" valign="top" align="left"> 
                    <table width="100%" border="0" cellspacing="3" cellpadding="2" id="tbl0">
                        <tr> 
                            <td width="100%" colspan="3" valign="top"> 
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr> 
                                        <td height="20"> <div id="menu_utama"> <!-- #BeginEditable "contenttitle" -->Benefit Employee<!-- #EndEditable --> </div> </td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td style="background-color:#EEE;">
                                        
                                            <table style="padding:9px; border:1px solid #00CCFF;" <%=garisContent%> width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                <tr>
                                                    <td>
                                                        <div id="mn_utama">Search Form</div>
                                                        <form name="frm_src" method="POST" action="">
                                                        <input type="hidden" name="start" value="<%=start%>">
                                                        <input type="hidden" name="command" value="<%=iCommand%>">
                                                        <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                                        <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                        <table>
                                                            <tr>
                                                                <td valign="top">Payroll Period</td>
                                                                <td valign="top">
                                                                    <select name="period_id">
                                                                        <option value="0">-select-</option>
                                                                        <%
                                                                        String selectedPeriod = "";
                                                                        Vector listPeriod = PstPayPeriod.list(0, 0, "", "START_DATE DESC"); 
                                                                        if (listPeriod != null && listPeriod.size() > 0){
                                                                            for(int i=0; i<listPeriod.size(); i++){
                                                                                PayPeriod periods = (PayPeriod)listPeriod.get(i);
                                                                                
                                                                                if (periodId == periods.getOID()){
                                                                                    selectedPeriod = " selected=\"selected\"";
                                                                                } else {
                                                                                    selectedPeriod = " ";
                                                                                }
                                                                                %>
                                                                                <option value="<%=periods.getOID()%>" <%=selectedPeriod%>><%=periods.getPeriod()%></option>
                                                                                <%
                                                                            }
                                                                        }
                                                                        %>
                                                                    </select>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td valign="top">Payroll Number</td>
                                                                <td valign="top"><input type="text" name="emp_num" value="<%=empNum%>" /></td>
                                                            </tr>
                                                            <tr>
                                                                <td valign="top">Employee Name</td>
                                                                <td valign="top"><input type="text" name="emp_name" value="<%=empName%>" /></td>
                                                            </tr>
                                                            <tr>
                                                                <td colspan="2"><button id="btn" onclick="javascript:cmdSearch()">Search</button></td>
                                                            </tr>
                                                        </table>
                                                        </form>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <div id="mn_utama"> List Benefit Employee </div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <%if (listBenefitEmp != null && listBenefitEmp.size() > 0) {%>
                                                    <td>
                                                        <%=drawList(listBenefitEmp)%>
                                                    </td>

                                                    <div> 
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
                                                                    cmd = PstBenefitPeriodEmp.findLimitCommand(start, recordToGet, vectSize);
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
                                                </div>
                                                    
                                                    <%} else {%>
                                                    <td>
                                                        record not found
                                                    </td>
                                                    <%}%>
                                                </tr>
                                                <tr>
                                                    <td>&nbsp;</td>
                                                </tr>
                                                <tr>
                                                    <td>&nbsp;</td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table><!---End Tble--->
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <table>
            <tr>
                <td>&nbsp;</td>
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
    <!-- #BeginEditable "script" --> 
    <script language="JavaScript">
                var oBody = document.body;
                var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
    </script>
                
    <!-- #EndEditable --> <!-- #EndTemplate -->
</html>