
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


<!-- JSP Block -->
<!-- End of JSP Block -->
<html>
    <!-- #BeginTemplate "/Templates/main.dwt" --> 
    <head>
        <!-- #BeginEditable "doctitle" --> 
        <title>HARISMA - Service Config</title>
        <!-- #EndEditable --> 
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <!-- #BeginEditable "styles" --> 
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
        
        <style type="text/css">
            #mn_utama {color: #333; padding: 3px 21px 3px 7px; border-left: 1px solid #00008B; font-size: 18px; font-weight: bold;}
            #sub {border-left: 1px solid #333; background-color: #EEE; color: #333; padding: 3px 5px; margin: 5px 0px 3px 0px;}
            #sub1 {padding-left: 14px;}
            #sub_part {padding-left: 14px; display: none;}
            #sub2 {padding-left: 21px; margin-top: 3px;}
            #exception1, #exception2, #exception3, #exception4 {padding-left: 21px; margin-top: 3px; display: none;}
            #tbl0 {padding: 7px;}
            #tbl1 {border-collapse: collapse;}
            #td1 {
                border-collapse: collapse;
                border: 1px solid #CCC;
                background-color: #DDD;
                font-weight: bold;
                padding: 3px;
            }
            #td2 {
                border-collapse: collapse;
                border: 1px solid #CCC;
                padding: 3px;
            }
            #btn {border:1px solid #999; color: #333; background-color: #DDD; padding: 5px; cursor: pointer;}
            #btn:hover {
              color: #FFF; background-color: #797979; border:1px solid #333;
            }
            #btn1 {font-size: 11px; border:1px solid #999; color: #333; background-color: #DDD; padding: 2px; cursor: pointer;}
            #btn1:hover {
              color: #FFF; background-color: #797979; border:1px solid #333;
            }
            #input {border: 1px solid #ccc; padding: 3px;}
            #divTbl {padding: 3px; background-color: #F7F7F7;}
            #msg_save {padding: 5px; background-color: #EDFFC2; color: #7C9A35; border-left: 1px solid #688426; font-size: 14px;}
            #activeBtn {padding: 2px; color: #FFF; background-color: #00CCFF; border: 1px solid #08C;}
            #offBtn {padding: 2px; color: #FFF; background-color: #333; border: 1px solid #000;} 
        </style>
        <script type="text/javascript">
            function showInput(val){
                switch (val){
                    case "1":
                        document.getElementById("exception1").style.display="table";
                        document.getElementById("exception2").style.display="none";
                        document.getElementById("exception3").style.display="none";
                        document.getElementById("exception4").style.display="none";
                        break;
                    case "2":
                        document.getElementById("exception1").style.display="none";
                        document.getElementById("exception2").style.display="table";
                        document.getElementById("exception3").style.display="none";
                        document.getElementById("exception4").style.display="none";
                        break;
                    case "3":
                        document.getElementById("exception1").style.display="none";
                        document.getElementById("exception2").style.display="none";
                        document.getElementById("exception3").style.display="table";
                        document.getElementById("exception4").style.display="none";
                        break;
                    case "4":
                        document.getElementById("exception1").style.display="none";
                        document.getElementById("exception2").style.display="none";
                        document.getElementById("exception3").style.display="none";
                        document.getElementById("exception4").style.display="table";
                        break;
                }
 
            }
            
            function showPart(){
                var chk = document.getElementById("serviceDistribution").value;
                if (chk == "on"){
                    document.getElementById("sub_part").style.display = "table";
                    document.getElementById("test").innerHTML=chk;
                } else {
                    document.getElementById("sub_part").style.display = "none";
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
                                        <td height="20"> <span id="mn_utama"> <!-- #BeginEditable "contenttitle" -->Service Charge<!-- #EndEditable --> </span> </td>
                                    </tr>
                                                
                                                
                                </table>
                            </td>
                        </tr>

                        <tr>
                            <td valign="top"> 
                                <div id="sub"><input type="checkbox" name="define" value="" /> By Company Structure</div>
                                &nbsp;<input type="checkbox" name="" /> Company
                                &nbsp;<input type="checkbox" name="" /> Division
                                &nbsp;<input type="checkbox" name="" /> Department
                            </td>
                        
                            <td valign="top">
                                <div id="sub"><input type="checkbox" name="prorate" /> Prorate per employee presence </div>
                                &nbsp;<input type="radio" name="define" value="" />Working day
                                &nbsp;<input type="radio" name="define" value="" />Total Presence Company
                            </td>
                        
                            <td valign="top">
                                <div id="sub"><input type="checkbox" name="emp_level" />Employee Level weight (Point)</div>
                            </td>
                        </tr>
                        <tr>
                            <td valign="top">
                                <div id="sub"><input type="checkbox" name="" />Deduction</div>
                                <div id="sub1">
                                    <table cellspacing="0" cellpadding="0" id="tbl1">
                                        <tr>
                                            <td id="td1">No</td>
                                            <td id="td1">%</td>
                                            <td id="td1">Deduction Description</td>
                                            <td id="td1">Reference</td>
                                        </tr>
                                        <tr>
                                            <td id="td2">1</td>
                                            <td id="td2"><input type="text" name="" id="input" /></td>
                                            <td id="td2"><input type="text" name="" id="input" /></td>
                                            <td id="td2"><input type="text" name="" id="input" /></td>
                                        </tr>
                                    </table>
                                </div>
                            </td>
                        
                            <td valign="top" colspan="2">
                                <div id="sub">
                                    <input type="checkbox" name="service_distribution" id="serviceDistribution"  />Service Charge Distribution
                                </div>
                                
                                    <table>
                                        <tr>
                                            <td>
                                    
                                    <table cellspacing="0" cellpadding="0" id="tbl1">       
                                        <tr>
                                            <td id="td2">Part 1</td>
                                            <td id="td2"><input type="text" name="" id="input" /></td>
                                        </tr>
                                        <tr>
                                            <td id="td2">Code</td>
                                            <td id="td2"><input type="text" name="" id="input" /></td>
                                        </tr>
                                        <tr>
                                            <td id="td2">Description</td>
                                            <td id="td2"><input type="text" name="" id="input" /></td>
                                        </tr>
                                        <tr>
                                            <td id="td2" colspan="2">
                                                <select>
                                                    <option>Total Employee Entitle</option>
                                                    <option>Total Presence</option>
                                                    <option>Total Level Point</option>
                                                </select>
                                            </td>
                                        </tr>
                                    </table>
                                            </td>
                                            <td>
                                    <table cellspacing="0" cellpadding="0" id="tbl1">       
                                        <tr>
                                            <td id="td2">Part 2</td>
                                            <td id="td2"><input type="text" name="" id="input" /></td>
                                        </tr>
                                        <tr>
                                            <td id="td2">Code</td>
                                            <td id="td2"><input type="text" name="" id="input" /></td>
                                        </tr>
                                        <tr>
                                            <td id="td2">Description</td>
                                            <td id="td2"><input type="text" name="" id="input" /></td>
                                        </tr>
                                        <tr>
                                            <td id="td2" colspan="2">
                                                <select>
                                                    <option>Total Employee Entitle</option>
                                                    <option>Total Presence</option>
                                                    <option>Total Level Point</option>
                                                </select>
                                            </td>
                                        </tr>
                                    </table>
                                    
                                            </td>
                                            <td>
                                    
                                    <table cellspacing="0" cellpadding="0" id="tbl1">       
                                        <tr>
                                            <td id="td2">Part 3</td>
                                            <td id="td2"><input type="text" name="" id="input" /></td>
                                        </tr>
                                        <tr>
                                            <td id="td2">Code</td>
                                            <td id="td2"><input type="text" name="" id="input" /></td>
                                        </tr>
                                        <tr>
                                            <td id="td2">Description</td>
                                            <td id="td2"><input type="text" name="" id="input" /></td>
                                        </tr>
                                        <tr>
                                            <td id="td2" colspan="2">
                                                <select>
                                                    <option>Total Employee Entitle</option>
                                                    <option>Total Presence</option>
                                                    <option>Total Level Point</option>
                                                </select>
                                            </td>
                                        </tr>
                                    </table>
                                            </td>
                                        </tr>
                                    </table>
                                
                            </td>
                        
                            
                        </tr>
                        
                        <tr>
                            
                            
                            <td valign="top" colspan="2">
                                <div id="sub"><input type="checkbox" name="" />Exception No Service Charge</div>
                                <table>
                                    <tr>
                                        <td>
                                    
                                
                                <span id="sub1">
                                    <input type="checkbox" name="exception_no_sc" id="byEmpCategory" value="1" onclick="showInput('1')" />By Employee Category
                                    <div id="sub2">
                                        <div><button id="btn1">Select</button></div>
                                        <div><textarea id="input" rows="7"></textarea></div>
                                    </div>
                                </span>
                                
                                        </td>
                                        <td>
                                
                                <span id="sub1">
                                    <input type="checkbox" name="exception_no_sc" id="byEmpPosition" value="2" onclick="showInput('2')" />By Employee Position
                                    <div id="sub2">
                                        <div><button id="btn1">Select</button></div>
                                        <div><textarea id="input" rows="7"></textarea></div>
                                    </div>
                                </span>
                                
                                        </td>
                                        <td>
                                            
                                <span id="sub1">
                                    <input type="checkbox" name="exception_no_sc" id="byEmpPayroll" value="3" onclick="showInput('3')" />By Employee Payroll Number
                                    <div id="sub2">
                                        <div><button id="btn1">Select</button></div>
                                        <div><textarea id="input" rows="7"></textarea></div>
                                    </div>
                                </span>
                                        </td>
                                        <td>
                                <span id="sub1">
                                    <input type="checkbox" name="exception_no_sc" id="bySpecialLeave" value="4" onclick="showInput('4')" />By Special Leave Taken
                                    <div id="sub2">
                                        <div><button id="btn1">Select</button></div>
                                        <div><textarea id="input" rows="7"></textarea></div>
                                    </div>
                                </span>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                            <td valign="top">
                                <div id="sub">
                                    <input type="checkbox" name="" />Employee Service Charge Entitle
                                </div>
                                <div id="sub1">
                                    By Employee Payroll Number
                                    <div id="sub2">
                                        <div><input type="text" name="t" id="input" /><button id="btn1">search</button></div>
                                        <div><textarea id="input" rows="7"></textarea></div>
                                    </div>
                                </div>
                            </td>
                        
                            
                        </tr>
                        <tr>
                            <td colspan="3"><button id="btn">Save Configuration</button></td>
                        </tr>
                        <tr>
                            <td colspan="3">&nbsp;</td>
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
    <!-- #BeginEditable "script" --> <script language="JavaScript">
                var oBody = document.body;
                var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
    </script>
                
    <!-- #EndEditable --> <!-- #EndTemplate -->
</html>
