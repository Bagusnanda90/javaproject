<%-- 
    Document   : Doc Master Action
    Created on : Sep 12, 2015, 3:56:51 PM
    Author     : Priska
--%>
<%@page import="com.dimata.harisma.entity.payroll.SalaryLevel"%>
<%@page import="com.dimata.harisma.entity.payroll.PstSalaryLevel"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="javax.print.DocFlavor.STRING"%>
<%@page import="org.apache.poi.ss.formula.functions.Hlookup"%>
<%@page import="com.dimata.harisma.entity.attendance.I_Atendance"%>
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


<%
    int iCommand = FRMQueryString.requestCommand(request);
    boolean status = false;
    int iErrCode = FRMMessage.ERR_NONE;
    
    long oidEmpDoc = FRMQueryString.requestLong(request,"oidEmpDoc");
    long oidEmpDocField = FRMQueryString.requestLong(request,"oidEmpDocField");
    String ObjectName = FRMQueryString.requestString(request,"ObjectName");
    String ObjectType = FRMQueryString.requestString(request,"ObjectType");
    String ObjectClass = FRMQueryString.requestString(request,"ObjectClass");
    String ObjectStatusfield = FRMQueryString.requestString(request,"ObjectStatusfield");


    CtrlEmpDocField ctrlEmpDocField = new CtrlEmpDocField(request);
    
    
    FrmEmpDocField frmEmpDocField = ctrlEmpDocField.getForm();
    iErrCode = ctrlEmpDocField.action(iCommand , oidEmpDocField );
        String whereC = " "+PstEmpDocField.fieldNames[PstEmpDocField.FLD_OBJECT_NAME] + " = \"" + ObjectName+"\" AND "+PstEmpDocField.fieldNames[PstEmpDocField.FLD_EMP_DOC_ID] + " = \"" + oidEmpDoc+"\"";
        Vector listEmp = PstEmpDocField.list(0, 0, whereC, ""); 
        EmpDocField empDocField = new EmpDocField();
    try {
        empDocField = (EmpDocField) listEmp.get(0);
        oidEmpDocField = empDocField.getOID();
    }catch (Exception e){
    
    }
  
%>



<html><!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" -->
        <title>HARISMA - Master Data DocMasterAction</title>
        <script language="JavaScript">

           function cmdSave(){
                document.frmEmpDocField.command.value="<%=String.valueOf(Command.SAVE)%>";
                document.frmEmpDocField.action="EmpDocumentDetailObject.jsp";
                document.frmEmpDocField.submit();
   
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
           <%//@include file="../styletemplate/template_header.jsp" %>
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
                                                    SET FIELD
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
                                                                                 <form name="frmEmpDocField" method="post" action="">
                                                                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                                                                    <input type="hidden" name="oidEmpDoc" value="<%=oidEmpDoc %>">
                                                                                    <input type="hidden" name="oidEmpDocField" value="<%=oidEmpDocField%>">
                                                                                    <input type="hidden" name="ObjectName" value="<%=ObjectName%>">
                                                                                    <input type="hidden" name="ObjectType" value="<%=ObjectType%>">
                                                                                    <input type="hidden" name="ObjectClass" value="<%=ObjectClass%>">
                                                                                    <input type="hidden" name="ObjectStatusfield" value="<%=ObjectStatusfield%>">
                                                                                    
                                                                                    <input type="hidden" name="<%=frmEmpDocField.fieldNames[frmEmpDocField.FRM_FIELD_EMP_DOC_ID]%>" value="<%=oidEmpDoc%>" class="formElemen" >
                                                                                    <input type="hidden" name="<%=frmEmpDocField.fieldNames[frmEmpDocField.FRM_FIELD_OBJECT_NAME]%>" value="<%=ObjectName%>" class="formElemen" >
                                                                                    
                                                                                    <% if (ObjectClass.equals("CLASSFIELD")) { %>
                                                                                    <input type="hidden" name="<%=frmEmpDocField.fieldNames[frmEmpDocField.FRM_FIELD_CLASS_NAME]%>" value="<%=ObjectStatusfield%>" class="formElemen" >
                                                                                    <% } else { %>
                                                                                    <input type="hidden" name="<%=frmEmpDocField.fieldNames[frmEmpDocField.FRM_FIELD_CLASS_NAME]%>" value="<%=ObjectClass%>" class="formElemen" >
                                                                                    <% } %>
                                                                                    
                                                                                    
                                                                                    
                                                                                    <% if (ObjectClass.equals("ALLFIELD")){ %>
                                                                                            
                                                                                            <% if (ObjectStatusfield.equals("TEXT")){ %>
                                                                                                <input type="hidden" name="<%=frmEmpDocField.fieldNames[frmEmpDocField.FRM_FIELD_OBJECT_TYPE]%>" value="0" class="formElemen" >
                                                                                                <input type="text" name="<%=frmEmpDocField.fieldNames[frmEmpDocField.FRM_FIELD_VALUE]%>" value="<%=empDocField.getValue()%>" class="formElemen" >
                                                                                            <% } %>
                                                                                            
                                                                                            <% if (ObjectStatusfield.equals("DATE")){ %>
                                                                                                <% 
                                                                                                    Date  dateF = new Date(); 
                                                                                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                                                                                try{
                                                                                                    dateF = formatter.parse(empDocField.getValue() );
                                                                                                } catch (Exception e){

                                                                                                } %>
                                                                                                
                                                                                                <input type="hidden" name="<%=frmEmpDocField.fieldNames[frmEmpDocField.FRM_FIELD_OBJECT_TYPE]%>" value="1" class="formElemen" >
                                                                                                <%=ControlDate.drawDateWithStyle(frmEmpDocField.fieldNames[frmEmpDocField.FRM_FIELD_VALUE_DATE], dateF == null ? new Date() : dateF, 0, -150, "formElemen")%>     
                                                                                            <% } %>
                                                                                            
                                                                                    <% } %>
                                                                              
                                                                                    
                                                                                    <% if (ObjectClass.equals("CLASSFIELD")){ %>
                                                                                            
                                                                                    
                                                                                            <% if (ObjectStatusfield.equals("COMPANY")) { %>
                                                                                            <%
                                                                                                Vector company_value = new Vector(1, 1);
                                                                                                Vector company_key = new Vector(1, 1);
                                                                                                Vector listComp = PstCompany.list(0, 0, "", "");
                                                                                                company_value.add(""+0);
                                                                                                company_key.add("select");
                                                                                                for (int i = 0; i < listComp.size(); i++) {
                                                                                                    Company comp = (Company) listComp.get(i);
                                                                                                    company_key.add(comp.getCompany());
                                                                                                    company_value.add(String.valueOf(comp.getOID()));
                                                                                                }
                                                                                                long compId = 0;
                                                                                                try {
                                                                                                   compId = Long.parseLong(empDocField.getValue());
                                                                                                }catch (Exception e){
                                                                                                }    
                                                                                              %>
                                                                                              
                                                                                                <input type="hidden" name="<%=frmEmpDocField.fieldNames[frmEmpDocField.FRM_FIELD_OBJECT_TYPE]%>" value="2" class="formElemen" >
                                                                                                <%= ControlCombo.draw(frmEmpDocField.fieldNames[frmEmpDocField.FRM_FIELD_VALUE_LONG], "formElemen", null, "" + (compId !=0 ? compId :0), company_value, company_key,"")%>
                                                                                             <% } %>
                                                                                    
                                                                                             
                                                                                             <% if (ObjectStatusfield.equals("DIVISION")) { %>
                                                                                            <%
                                                                                                Vector div_value = new Vector(1, 1);
                                                                                                Vector div_key = new Vector(1, 1);
                                                                                                Vector listDiv = PstDivision.list(0, 0, "", "");
                                                                                                div_value.add(""+0);
                                                                                                div_key.add("select");
                                                                                                for (int i = 0; i < listDiv.size(); i++) {
                                                                                                    Division div = (Division) listDiv.get(i);
                                                                                                    div_key.add(div.getDivision());
                                                                                                    div_value.add(String.valueOf(div.getOID()));
                                                                                                }
                                                                                                long divId = 0;
                                                                                                try {
                                                                                                   divId = Long.parseLong(empDocField.getValue());
                                                                                                }catch (Exception e){
                                                                                                }    
                                                                                              %>
                                                                                              
                                                                                                <input type="hidden" name="<%=frmEmpDocField.fieldNames[frmEmpDocField.FRM_FIELD_OBJECT_TYPE]%>" value="2" class="formElemen" >
                                                                                                <%= ControlCombo.draw(frmEmpDocField.fieldNames[frmEmpDocField.FRM_FIELD_VALUE_LONG], "formElemen", null, "" + (divId !=0 ? divId :0), div_value, div_key,"")%>
                                                                                             <% } %>
                                                                                    
                                                                                             
                                                                                             
                                                                                             
                                                                                             <% if (ObjectStatusfield.equals("DEPARTMENT")) { %>
                                                                                            <%
                                                                                                Vector dep_value = new Vector(1, 1);
                                                                                                Vector dep_key = new Vector(1, 1);
                                                                                                Vector listDep = PstDepartment.list(0, 0, "", "");
                                                                                                dep_value.add(""+0);
                                                                                                dep_key.add("select");
                                                                                                for (int i = 0; i < listDep.size(); i++) {
                                                                                                    Department dep = (Department) listDep.get(i);
                                                                                                    dep_key.add(dep.getDepartment());
                                                                                                    dep_value.add(String.valueOf(dep.getOID()));
                                                                                                }
                                                                                                long depId = 0;
                                                                                                try {
                                                                                                   depId = Long.parseLong(empDocField.getValue());
                                                                                                }catch (Exception e){
                                                                                                }    
                                                                                              %>
                                                                                              
                                                                                                <input type="hidden" name="<%=frmEmpDocField.fieldNames[frmEmpDocField.FRM_FIELD_OBJECT_TYPE]%>" value="2" class="formElemen" >
                                                                                                <%= ControlCombo.draw(frmEmpDocField.fieldNames[frmEmpDocField.FRM_FIELD_VALUE_LONG], "formElemen", null, "" + (depId !=0 ? depId :0), dep_value, dep_key,"")%>
                                                                                             <% } %>
                                                                                    
                                                                                             
                                                                                             <% if (ObjectStatusfield.equals("EMPCAT")) { %>
                                                                                            <%
                                                                                                Vector empcat_value = new Vector(1, 1);
                                                                                                Vector empcat_key = new Vector(1, 1);
                                                                                                Vector listempcat = PstEmpCategory.list(0, 0, "", "");
                                                                                                empcat_value.add(""+0);
                                                                                                empcat_key.add("select");
                                                                                                for (int i = 0; i < listempcat.size(); i++) {
                                                                                                    EmpCategory empcat = (EmpCategory) listempcat.get(i);
                                                                                                    empcat_key.add(empcat.getEmpCategory());
                                                                                                    empcat_value.add(String.valueOf(empcat.getOID()));
                                                                                                }
                                                                                                long empcatId = 0;
                                                                                                try {
                                                                                                   empcatId = Long.parseLong(empDocField.getValue());
                                                                                                }catch (Exception e){
                                                                                                }    
                                                                                              %>
                                                                                              
                                                                                                <input type="hidden" name="<%=frmEmpDocField.fieldNames[frmEmpDocField.FRM_FIELD_OBJECT_TYPE]%>" value="2" class="formElemen" >
                                                                                                <%= ControlCombo.draw(frmEmpDocField.fieldNames[frmEmpDocField.FRM_FIELD_VALUE_LONG], "formElemen", null, "" + (empcatId !=0 ? empcatId :0), empcat_value, empcat_key,"")%>
                                                                                             <% } %>
                                                                                             
                                                                                             
                                                                                              <% if (ObjectStatusfield.equals("LEVEL")) { %>
                                                                                            <%
                                                                                                Vector level_value = new Vector(1, 1);
                                                                                                Vector level_key = new Vector(1, 1);
                                                                                                Vector listlevel = PstLevel.list(0, 0, "", "");
                                                                                                level_value.add(""+0);
                                                                                                level_key.add("select");
                                                                                                for (int i = 0; i < listlevel.size(); i++) {
                                                                                                    Level level = (Level) listlevel.get(i);
                                                                                                    level_key.add(level.getLevel());
                                                                                                    level_value.add(String.valueOf(level.getOID()));
                                                                                                }
                                                                                                long levelId = 0;
                                                                                                try {
                                                                                                   levelId = Long.parseLong(empDocField.getValue());
                                                                                                }catch (Exception e){
                                                                                                }    
                                                                                              %>
                                                                                              
                                                                                                <input type="hidden" name="<%=frmEmpDocField.fieldNames[frmEmpDocField.FRM_FIELD_OBJECT_TYPE]%>" value="2" class="formElemen" >
                                                                                                <%= ControlCombo.draw(frmEmpDocField.fieldNames[frmEmpDocField.FRM_FIELD_VALUE_LONG], "formElemen", null, "" + (levelId !=0 ? levelId :0), level_value, level_key,"")%>
                                                                                             <% } %>
                                                                                             
                                                                                             
                                                                                             
                                                                                            <% if (ObjectStatusfield.equals("POSITION")) { %>
                                                                                            <%
                                                                                                Vector pos_value = new Vector(1, 1);
                                                                                                Vector pos_key = new Vector(1, 1);
                                                                                                Vector listPos = PstPosition.list(0, 0, "", "");
                                                                                                pos_value.add(""+0);
                                                                                                pos_key.add("select");
                                                                                                for (int i = 0; i < listPos.size(); i++) {
                                                                                                    Position pos = (Position) listPos.get(i);
                                                                                                    pos_key.add(pos.getPosition());
                                                                                                    pos_value.add(String.valueOf(pos.getOID()));
                                                                                                }
                                                                                                long posId = 0;
                                                                                                try {
                                                                                                   posId = Long.parseLong(empDocField.getValue());
                                                                                                }catch (Exception e){
                                                                                                }    
                                                                                              %>
                                                                                              
                                                                                                <input type="hidden" name="<%=frmEmpDocField.fieldNames[frmEmpDocField.FRM_FIELD_OBJECT_TYPE]%>" value="2" class="formElemen" >
                                                                                                <%= ControlCombo.draw(frmEmpDocField.fieldNames[frmEmpDocField.FRM_FIELD_VALUE_LONG], "formElemen", null, "" + (posId !=0?posId:0), pos_value, pos_key,"")%>
                                                                                            <% } %>
                                                                                              
                                                                                            <% if (ObjectStatusfield.equals("SECTION")) { %>
                                                                                            <%
                                                                                                Vector sec_value = new Vector(1, 1);
                                                                                                Vector sec_key = new Vector(1, 1);
                                                                                                Vector listsec = PstSection.list(0, 0, "", "");
                                                                                                sec_value.add(""+0);
                                                                                                sec_key.add("select");
                                                                                                for (int i = 0; i < listsec.size(); i++) {
                                                                                                    Section sec = (Section) listsec.get(i);
                                                                                                    sec_key.add(sec.getSection());
                                                                                                    sec_value.add(String.valueOf(sec.getOID()));
                                                                                                }
                                                                                                long secId = 0;
                                                                                                try {
                                                                                                   secId = Long.parseLong(empDocField.getValue());
                                                                                                }catch (Exception e){
                                                                                                }    
                                                                                              %>
                                                                                              
                                                                                                <input type="hidden" name="<%=frmEmpDocField.fieldNames[frmEmpDocField.FRM_FIELD_OBJECT_TYPE]%>" value="2" class="formElemen" >
                                                                                                <%= ControlCombo.draw(frmEmpDocField.fieldNames[frmEmpDocField.FRM_FIELD_VALUE_LONG], "formElemen", null, "" + (secId !=0?secId:0), sec_value, sec_key,"")%>
                                                                                            <% } %>
                                                                                            
                                                                                            <% if (ObjectStatusfield.equals("SALARYLEVEL")) { %>
                                                                                            <%
                                                                                                Vector sLevel_value = new Vector(1, 1);
                                                                                                Vector sLevel_key = new Vector(1, 1);
                                                                                                Vector listsLevel = PstSalaryLevel.list(0, 0, "", "");
                                                                                                sLevel_value.add(""+0);
                                                                                                sLevel_key.add("select");
                                                                                                for (int i = 0; i < listsLevel.size(); i++) {
                                                                                                    SalaryLevel sLevel = (SalaryLevel) listsLevel.get(i);
                                                                                                    sLevel_key.add(sLevel.getLevelName());
                                                                                                    sLevel_value.add(String.valueOf(sLevel.getOID()));
                                                                                                }
                                                                                                long sLevelId = 0;
                                                                                                try {
                                                                                                   sLevelId = Long.parseLong(empDocField.getValue());
                                                                                                }catch (Exception e){
                                                                                                }    
                                                                                              %>
                                                                                              
                                                                                                <input type="hidden" name="<%=frmEmpDocField.fieldNames[frmEmpDocField.FRM_FIELD_OBJECT_TYPE]%>" value="2" class="formElemen" >
                                                                                                <%= ControlCombo.draw(frmEmpDocField.fieldNames[frmEmpDocField.FRM_FIELD_VALUE_LONG], "formElemen", null, "" + (sLevelId !=0?sLevelId:0), sLevel_value, sLevel_key,"")%>
                                                                                            <% } %>
                                                                                           
                                                                                    <% } %>
                                                                                    
                                                                                    <a href="javascript:cmdSave()" class="command">SAVE</a>
                                                                                </form>
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
                <td colspan="2" height="20" > 
      <%@ include file = "../main/footer.jsp" %>
                </td>
            </tr>
            <%}%>
        </table>
    </body>
    <script language="JavaScript">
    </script>
</html>

