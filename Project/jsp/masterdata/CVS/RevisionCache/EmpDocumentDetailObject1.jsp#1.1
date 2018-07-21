<%@page import="com.dimata.harisma.entity.masterdata.PstPosition"%>
<%@page import="com.dimata.system.entity.PstSystemProperty"%>
<%@page import="com.dimata.harisma.entity.masterdata.EmpDocField"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstEmpDocField"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmEmpDocField"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlEmpDocField"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.harisma.entity.masterdata.ObjectSearchDoc"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlEmpDocList"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmEmpDocList"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.utility.service.tma.*" %>
<%@ page import = "com.dimata.harisma.form.search.FrmSrcEmployee" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.AlUpload" %>
<%@ page import = "com.dimata.harisma.entity.leave.PstAlUpload" %>
<%@ page import = "com.dimata.harisma.session.leave.SessAlUpload" %>
<%@ page import = "com.dimata.harisma.form.leave.*" %>
<%@ page import = "com.dimata.harisma.entity.locker.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK); %>
<%//@include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
boolean privStart=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_START));
%>


<%


   int iCommand = FRMQueryString.requestCommand(request);
    boolean status = false;
    int iErrCode = FRMMessage.ERR_NONE;
    String formName = FRMQueryString.requestString(request,"formName");
    String empPathId = FRMQueryString.requestString(request,"empPathId");
    
    long oidEmpDoc = FRMQueryString.requestLong(request,"oidDoc");
    String objectName = FRMQueryString.requestString(request,"ObjectName");
    String objectType = FRMQueryString.requestString(request,"ObjectType");
    String objectStatusfield = FRMQueryString.requestString(request,"ObjectStatusfield");
    long oidEmpDocField = FRMQueryString.requestLong(request,"oidEmpDocField");
    //String empId = FRMQueryString.requestString(request,"empId");
    String empId = "" ;
    
    ObjectSearchDoc objectSearchDoc = new ObjectSearchDoc();
    
    //String whereC = " "+PstEmpDocList.fieldNames[PstEmpDocList.FLD_OBJECT_NAME] + " = \"" + objectName+"\" AND "+PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID] + " = \"" + oidEmpDoc+"\"";
    //Vector listEmp = PstEmpDocList.list(0, 0, whereC, ""); 
    // for(int list = 0 ; list < listEmp.size(); list++ ){
    //     EmpDocList empDocList = (EmpDocList) listEmp.get(list);
    //     Employee employeeFetch = PstEmployee.fetchExc(empDocList.getEmployee_id());
    // }
       
    if (oidEmpDoc>0){
        objectSearchDoc.setEmpPathId(empPathId);
        objectSearchDoc.setObjectName(objectName);
        objectSearchDoc.setFormname(formName);
        objectSearchDoc.setOidDoc(oidEmpDoc);
        objectSearchDoc.setEmpId(empId);
        session.putValue("ObjectSearcDoc", objectSearchDoc);
    }
    if(session.getValue("ObjectSearcDoc")!=null){
    objectSearchDoc = (ObjectSearchDoc) session.getValue("ObjectSearcDoc");
    formName = objectSearchDoc.getFormname();
    empPathId = objectSearchDoc.getEmpPathId();
    
    oidEmpDoc = objectSearchDoc.getOidDoc();
    objectName = objectSearchDoc.getObjectName();
    empId = objectSearchDoc.getEmpId();
    
    }
    CtrlEmpDocField ctrlEmpDocField = new CtrlEmpDocField(request);
    
    
    FrmEmpDocField frmEmpDocField = ctrlEmpDocField.getForm();
    String whereC = " "+PstEmpDocField.fieldNames[PstEmpDocField.FLD_OBJECT_NAME] + " = \"" + objectName+"\" AND "+PstEmpDocField.fieldNames[PstEmpDocField.FLD_EMP_DOC_ID] + " = \"" + oidEmpDoc+"\"";
    Vector listEmp = PstEmpDocField.list(0, 0, whereC, ""); 
    EmpDocField empDocField = new EmpDocField();
    try {
    empDocField = (EmpDocField) listEmp.get(0);
    oidEmpDocField = empDocField.getOID();
    }catch (Exception e){
    
    }
    iErrCode = ctrlEmpDocField.action(iCommand , oidEmpDocField );
    
   
%>

<script language=JavaScript src="<%=approot%>/main/calendar.js"></script>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Search Employee</title>
<script language="JavaScript">
    
    
function cmdSave(){
    document.frmEmpDocField.command.value="<%=String.valueOf(Command.SAVE)%>";
    document.frmEmpDocField.action="EmpDocumentDetailObject.jsp";
    document.frmEmpDocField.submit();
    //alert("tt");
    //document.frmEmpDocField.command.value="<%=String.valueOf(Command.SAVE)%>";
    //alert("t2");
    //document.frmEmpDocField.action="EmpDocumentDetailObject.jsp";
    //alert("t3");
    //document.frmEmpDocField.submit();
    //alert("t4");
}    
    
function cmdAdd() {
        window.open("editEmployee.jsp", "edit_employee", "height=580,width=900, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes,resizable=no,top=50,left=50");
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


<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="<%=approot%>/styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="<%=approot%>/styles/tab.css" type="text/css">
<!-- #EndEditable -->
<link rel="stylesheet" href="<%=approot%>/styles/calendar.css" type="text/css">
    
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<!-- Untuk Calender-->
<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
    <tr><td id="ds_calclass">
    </td></tr>
</table> 
<script language=JavaScript src="<%=approot%>/main/calendar.js"></script>
<!-- End Calender-->

<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >

  
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2"> 
        <tr> 
          <td width="100%">
      <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> ADD </strong></font> </td>
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
                              
                                    <form name="frmEmpDocField" method="post" action="">
                                    <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
                                    <input type="hidden" name="formName" value="<%=String.valueOf(formName)%>">
                                    <input type="hidden" name="empPathId" value="<%=String.valueOf(empPathId)%>">
                                    <input type="hidden" name="oidEmpDocField" value="<%=String.valueOf(oidEmpDocField)%>">
                                    <input type="hidden" name="empPathId" value="<%=String.valueOf(empPathId)%>">
                                    <%=ControlDate.drawDateWithStyle(frmEmpDocField.fieldNames[frmEmpDocField.FRM_FIELD_VALUE], empDocField.getValue() == null ? new Date() : empDocField.getValue(), 0, -150, "formElemen")%>     
                                                        
                                    <input type="text" name="<%=frmEmpDocField.fieldNames[frmEmpDocField.FRM_FIELD_VALUE]%>" value="<%=empDocField.getValue()%>" class="formElemen" >
                                    
                                    <input type="hidden" name="<%=frmEmpDocField.fieldNames[frmEmpDocField.FRM_FIELD_EMP_DOC_ID]%>" value="<%=oidEmpDoc%>" class="formElemen" >
                                    <input type="hidden" name="<%=frmEmpDocField.fieldNames[frmEmpDocField.FRM_FIELD_OBJECT_TYPE]%>" value="<%=objectType%>" class="formElemen" >
                                    <input type="hidden" name="<%=frmEmpDocField.fieldNames[frmEmpDocField.FRM_FIELD_OBJECT_NAME]%>" value="<%=objectName%>" class="formElemen" >
                                    <img src="<%=approot%>/images/spacer.gif" width="1" height="1">
                                    <a href="javascript:cmdSave()" class="command">SAVE</a>
                                                                           
                                    
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

