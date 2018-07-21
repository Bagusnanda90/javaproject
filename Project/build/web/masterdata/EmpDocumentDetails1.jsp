<%@page import="com.dimata.harisma.entity.masterdata.PstEmpDocListMutation"%>
<%@page import="com.dimata.harisma.entity.masterdata.DocMasterAction"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDocMasterAction"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstEmpDocFlow"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDocMasterFlow"%>
<%@page import="com.dimata.harisma.entity.masterdata.DocMasterFlow"%>
<%@page import="com.dimata.harisma.entity.masterdata.EmpDocFlow"%>
<%@page import="java.util.Hashtable"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstEmpDocField"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="com.dimata.harisma.entity.employee.PstEmployee"%>
<%@page import="com.dimata.harisma.entity.employee.Employee"%>
<%@page import="com.dimata.harisma.entity.masterdata.EmpDocList"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstEmpDocList"%>
<%@page import="org.omg.CORBA.OBJECT_NOT_EXIST"%>
<%@page import="com.dimata.harisma.entity.masterdata.ObjectDocumentDetail"%>
<%@page import="com.dimata.harisma.entity.masterdata.StringHelper"%>
<%@page import="com.dimata.harisma.entity.masterdata.DocMasterTemplate"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDocMasterTemplate"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmKecamatan"%>
<%@page import="com.dimata.gui.jsp.ControlDate"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDocMaster"%>
<%@page import="com.dimata.harisma.entity.masterdata.DocMaster"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.harisma.entity.masterdata.DocType"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDocType"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstEmpDoc"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlEmpDoc"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.entity.masterdata.EmpDoc"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmEmpDoc"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<% 
/* 
 * Page Name  		:  EmpDoc.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		: priska
 * @version  		: 01 
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
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_PERFORMANCE_APPRAISAL, AppObjInfo.OBJ_GROUP_RANK); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%!    public String drawList(Vector objectClass, long oidEmpDoc) {
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("NAMA", "40%");
        ctrlist.addHeader("FLOW TITLE", "20%");
        ctrlist.addHeader("POSITION INDEX", "20%");
        ctrlist.addHeader("APPROVE", "50%");

        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.reset();
        int index = -1;

        String where1 = " "+PstEmpDocFlow.fieldNames[PstEmpDocFlow.FLD_EMP_DOC_ID] + " = \"" + oidEmpDoc+"\"";
        Hashtable hashtableEmpDocFlow = PstEmpDocFlow.Hlist(0, 0, where1, "FLOW_INDEX");                                       
        
        for (int i = 0; i < objectClass.size(); i++) {
            DocMasterFlow docMasterFlow = (DocMasterFlow) objectClass.get(i);
            EmpDocFlow empDocFlow = new EmpDocFlow();
            if (hashtableEmpDocFlow.get(docMasterFlow.getEmployee_id()) != null){
                empDocFlow = (EmpDocFlow) hashtableEmpDocFlow.get(docMasterFlow.getEmployee_id());
            }
            String statusAp = "Be Approve";
            boolean statusBoolean = false;
            if (empDocFlow.getOID() != 0){
                statusAp = "Approve";
                statusBoolean = true;
            }
            Employee employee = new Employee();
            try{
                employee = PstEmployee.fetchExc(docMasterFlow.getEmployee_id());
            } catch (Exception e){}
            Vector rowx = new Vector();
            //EmpDocumentDetails.jsp
            if (statusBoolean){
                rowx.add(""+employee.getFullName()+"");
            } else {
                rowx.add("<a href=\"javascript:cmdApproval1('"+docMasterFlow.getEmployee_id()+"','"+oidEmpDoc+"','"+docMasterFlow.getFlow_title()+"','"+docMasterFlow.getFlow_index()+"')\">"+employee.getFullName()+"</a>");
            }
            rowx.add(""+docMasterFlow.getFlow_title());
            rowx.add(""+docMasterFlow.getFlow_index());
            rowx.add(""+statusAp);
            
            lstData.add(rowx);
            lstLinkData.add(String.valueOf(docMasterFlow.getOID()));
        }
        return ctrlist.draw(index);
    }

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidEmpDoc = FRMQueryString.requestLong(request, "EmpDocument_oid");
long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");
/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = "";

EmpDoc empDoc1 = new EmpDoc();
DocMaster empDocMaster1 = new DocMaster();
DocMasterTemplate empDocMasterTemplate = new DocMasterTemplate();

CtrlEmpDoc ctrlEmpDoc = new CtrlEmpDoc(request);
ControlLine ctrLine = new ControlLine();

iErrCode = ctrlEmpDoc.action(iCommand , oidEmpDoc);
/* end switch*/
FrmEmpDoc frmEmpDoc = ctrlEmpDoc.getForm();

String empDocMasterTemplateText = "";

try {
    empDoc1 = PstEmpDoc.fetchExc(oidEmpDoc); 
} catch (Exception e){ }
if (empDoc1 != null){
try {
    empDocMaster1 = PstDocMaster.fetchExc(empDoc1.getDoc_master_id());
} catch (Exception e){ }
try {
    empDocMasterTemplateText = PstDocMasterTemplate.getTemplateText(empDoc1.getDoc_master_id());
} catch (Exception e){ }
}



String whereDocMasterFlow = " "+PstDocMasterFlow.fieldNames[PstDocMasterFlow.FLD_DOC_MASTER_ID] + " = \"" + empDoc1.getDoc_master_id() +"\"";
                                                 
Vector listMasterDocFlow = PstDocMasterFlow.list(0,0, whereDocMasterFlow, "");
String whereList = " "+PstEmpDocFlow.fieldNames[PstEmpDocFlow.FLD_EMP_DOC_ID] + " = \"" + oidEmpDoc+"\"";
Hashtable hashtableEmpDocFlow = PstEmpDocFlow.Hlist(0, 0, whereList, "FLOW_INDEX");  

// cek komplit untuk approval
boolean nstatus = true;
for (int x = 1; x < listMasterDocFlow.size(); x++){
     DocMasterFlow docMasterFlow = (DocMasterFlow) listMasterDocFlow.get(x);
     EmpDocFlow empDocFlow = new EmpDocFlow();
            if (hashtableEmpDocFlow.get(docMasterFlow.getEmployee_id()) != null){
                empDocFlow = (EmpDocFlow) hashtableEmpDocFlow.get(docMasterFlow.getEmployee_id());
            }
     if (empDocFlow.getOID() == 0){
         nstatus = false;
     }
}
int show = 1;
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Emp Document</title>
<style type="text/css">
            #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3;}
            #menu_title {color:#0099FF; font-size: 14px; font-weight: bold;}
            #menu_teks {color:#CCC;}
            #mn_utama {color: #FF6600; padding: 5px 14px; border-left: 1px solid #999; font-size: 14px; background-color: #F5F5F5;}
            #btn {
              background: #C7C7C7;
              border: 1px solid #BBBBBB;
              border-radius: 3px;
              font-family: Arial;
              color: #474747;
              font-size: 11px;
              padding: 3px 7px;
              cursor: pointer;
            }

            #btn:hover {
              color: #FFF;
              background: #B3B3B3;
              border: 1px solid #979797;
            }
            #btn1 {
              background: #f27979;
              border: 1px solid #d74e4e;
              border-radius: 3px;
              font-family: Arial;
              color: #ffffff;
              font-size: 12px;
              padding: 3px 9px 3px 9px;
            }

            #btn1:hover {
              background: #d22a2a;
              border: 1px solid #c31b1b;
            }
            #tdForm {
                padding: 5px;
            }
            .tblStyle {border-collapse: collapse;font-size: 11px;}
            .tblStyle td {padding: 3px 5px; border: 1px solid #CCC;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            #confirm {background-color: #fad9d9;border: 1px solid #da8383; color: #bf3c3c; padding: 14px 21px;border-radius: 5px;}
            #td1 {border: 1px solid #CCC; background-color: #DDD;}
            #td2 {border: 1px solid #CCC;}
            #tdTotal {background-color: #fad9d9;}
            #query {
                padding: 7px 9px; color: #f7f7f7; background-color: #797979; 
                border:1px solid #575757; border-radius: 5px; 
                margin-bottom: 5px; font-size: 12px;
                font-family: Courier New,Courier,Lucida Sans Typewriter,Lucida Typewriter,monospace;
            }
            #info {
                width: 500px;
                padding: 21px; color: #797979; background-color: #F7F7F7;
                border:1px solid #DDD;
                font-size: 12px;
            }
            
            .LockOff {
                display: none;
                visibility: hidden;
            }

            .LockOn {
                display: block;
                visibility: visible;
                position: absolute;
                z-index: 999;
                top: 0px;
                left: 0px;
                width: 105%;
                height: 105%;
                background-color: #ccc;
                text-align: center;
                padding-top: 20%;
                filter: alpha(opacity=75);
                opacity: 0.75;
                font-size: 250%;
            }
        </style>
<script language="JavaScript">

function hide()
{
    document.getElementById("formula").style.display="none";
    document.getElementById("showB").style.display="block"; 
    document.getElementById("hideB").style.display="none";  
}
function show()
{
    document.getElementById("formula").style.display="block";
    document.getElementById("showB").style.display="none";
    document.getElementById("hideB").style.display="block";  
}


function cmdApproval(empId,oidEmpDoc,title,flowIndex){
        window.open("documentApproval.jsp?oidApprover="+empId+"&oidEmpDoc="+oidEmpDoc+"&flowTitle="+title+"&flowIndex="+flowIndex, null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
}
function cmdApproval1(empId,oidEmpDoc,title,flowIndex){
	document.frmEmpDoc.action="documentApproval.jsp?oidApprover="+empId+"&oidEmpDoc="+oidEmpDoc+"&flowTitle="+title+"&flowIndex="+flowIndex;
	document.frmEmpDoc.submit(); 
}
function cmdopen(){
	window.open("EmpDocTesting.jsp", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
} 
 
function cmdSearchEmp(value){
        window.open("<%=approot%>/employee/search/SearchDocumentDetails.jsp?value="+value+"&formName=frmEmpDoc&empPathId=<%=frmEmpDoc.fieldNames[frmEmpDoc.FRM_FIELD_EMPLOYEE_ID]%>", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
}
function cmdAddEmp(ObjectName,oidDoc){
        window.open("<%=approot%>/employee/search/SearchDocumentDetails.jsp?ObjectName="+ObjectName+"&oidDoc="+oidDoc+"&formName=frmEmpDoc&empPathId=<%=frmEmpDoc.fieldNames[frmEmpDoc.FRM_FIELD_EMPLOYEE_ID]%>", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       


}
function cmdAddText(oidEmpDoc,ObjectName,ObjectType,ObjectClass,ObjectStatusfield){
        window.open("EmpDocumentDetailObject.jsp?oidEmpDoc="+oidEmpDoc+"&ObjectName="+ObjectName+"&ObjectType="+ObjectType+"&ObjectClass="+ObjectClass+"&ObjectStatusfield="+ObjectStatusfield+"&formName=frmEmpDoc&empPathId=<%=frmEmpDoc.fieldNames[frmEmpDoc.FRM_FIELD_EMPLOYEE_ID]%>", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
}
function cmdSelectPos(empDocId,ObjectName,ObjectType,ObjectClass,ObjectStatusfield,empId){
        window.open("EmpDocListMutation.jsp?empDocId="+empDocId+"&ObjectName="+ObjectName+"&ObjectType="+ObjectType+"&ObjectClass="+ObjectClass+"&ObjectStatusfield="+ObjectStatusfield+"&formName=frmEmpDoc&empPathId=<%=frmEmpDoc.fieldNames[frmEmpDoc.FRM_FIELD_EMPLOYEE_ID]%>"+"&employeeId="+empId, null, "height=300,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
}

function cmdPrintPDF(oidEmpDoc){
                window.open("<%=approot%>/servlet/com.dimata.harisma.report.EmpDocumentPDF?oid="+oidEmpDoc);
            }
 function cmdExpense(oidEmpDoc){
                window.open("EmpDocumentExpense.jsp?oidEmpDoc="+oidEmpDoc);      
            }           
 function cmdAction(oidEmpDoc,oidDocAction){
                window.open("EmpDocumentDetailAction.jsp?oidEmpDoc="+oidEmpDoc+"&oidDocAction="+oidDocAction); 
            }
 function cmdPrint(oidEmpDoc){
                window.open("EmpDocumentDetailsEditor.jsp?EmpDocument_oid="+oidEmpDoc, null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes"); 
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
<script src="../styles/ckeditor/ckeditor.js"></script>
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
			  DOCUMENT
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
                                    <form name="frmEmpDoc" method ="post" action="" >
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
				      <input type="hidden" name="EmpDocument_oid" value="<%=oidEmpDoc%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">                                              
                                              <tr align="left" valign="top"> 
                                                <td colspan="3" >
                                                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                      <td class="listtitle" width="37%">&nbsp;</td>
                                                      <td width="63%" class="comment">&nbsp;</td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              
                                             
                                              <%//=ControlDate.drawDateWithStyle(frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_BIRTH_DATE], employee.getBirthDate() == null ? new Date() : employee.getBirthDate(), 0, -150, "formElemen")%>    
                                                        
                                             <% 
                                                empDocMasterTemplateText = empDocMasterTemplateText.replace("&lt", "<");
                                                
                                                empDocMasterTemplateText = empDocMasterTemplateText.replace("<;", "<");
                                                empDocMasterTemplateText = empDocMasterTemplateText.replace("&gt", ">");
                                                String tanpaeditor = empDocMasterTemplateText;
                                                String subString = "";
                                                String stringResidual = empDocMasterTemplateText;
                                                Vector vNewString = new Vector();

                                                Hashtable empDOcFecthH = new Hashtable();
                                                
                                                try {
                                                    empDOcFecthH = PstEmpDoc.fetchExcHashtable(oidEmpDoc); 
                                                } catch (Exception e){ }
                                                
                                                String where1 = " "+PstEmpDocField.fieldNames[PstEmpDocField.FLD_EMP_DOC_ID] + " = \"" + oidEmpDoc+"\"";
                                                Hashtable hlistEmpDocField = PstEmpDocField.Hlist(0, 0, where1, ""); 
                                                
                                                int startPosition = 0 ;
                                                int endPosition = 0; 
                                                try {
                                                do {
                                                    
                                                        ObjectDocumentDetail objectDocumentDetail = new ObjectDocumentDetail();
                                                        startPosition = stringResidual.indexOf("${") + "${".length();
                                                        endPosition = stringResidual.indexOf("}", startPosition);
                                                        subString = stringResidual.substring(startPosition, endPosition);
                                                        
                                                        
                                                        //cek substring
                                                        
                                                        
                                                            String []parts = subString.split("-");
                                                            String objectName = "";
                                                            String objectType = "";
                                                            String objectClass = "";
                                                            String objectStatusField = "";
                                                            try{
                                                            objectName = parts[0]; 
                                                            objectType = parts[1];
                                                            objectClass = parts[2];
                                                            objectStatusField = parts[3];
                                                            } catch (Exception e){
                                                                System.out.printf("pastikan 4 parameter");
                                                            }
                                                             
                                                            
                                                        //cek dulu apakah hanya object name atau tidak
                                                        if  (!objectName.equals("") && !objectType.equals("") && !objectClass.equals("") && !objectStatusField.equals("")){
                                                        
                                                            
                                                            //jika list maka akan mencari penutupnya..
                                                        if  (objectType.equals("LIST") && objectStatusField.equals("START")){
                                                            String add = "<a href=\"javascript:cmdAddEmp('"+objectName+"','"+oidEmpDoc+"')\">add employee</a></br>";
                                                            empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+subString+"}", add); 
                                                            tanpaeditor = tanpaeditor.replace("${"+subString+"}", " ");    
                                                            int endPnutup = stringResidual.indexOf("${"+objectName+"-"+objectType+"-"+objectClass+"-END"+"}", endPosition);
                                                                //ambil stringnya //end position adalah penutup formula dan end penutup adalah penutup isi dari end formula nya
                                                                String textString = stringResidual.substring(endPosition, endPnutup);//berisi dialam string yang ada di dalam formulanya
                                                               //menghapus tutup formula 
                                                              
                                                          
                                                                //mencari jumlah table table
                                                                  int startPositionOfTable = 0;
                                                                  int endPositionOfTable = 0;
                                                                  String subStringOfTable = "";
                                                                  String residueOfTextString = textString;
                                                                  do{
                                                                      //cari tag table pembuka
                                                                      startPositionOfTable = residueOfTextString.indexOf("<table") + "<table".length();
                                                                      //int tt = textString.indexOf("&lttable") + ((textString.indexOf("&gt") + 3)-textString.indexOf("&lttable"));
                                                                      endPositionOfTable = residueOfTextString.indexOf("</table>", startPositionOfTable);
                                                                      subStringOfTable = residueOfTextString.substring(startPositionOfTable, endPositionOfTable);//isi table 
                                                                      
                                                                      //mencari body 
                                                                      int startPositionOfBody = subStringOfTable.indexOf("<tbody>") + "<tbody>".length();
                                                                      int endPositionOfBody = subStringOfTable.indexOf("</tbody>", startPositionOfBody);
                                                                      String subStringOfBody = subStringOfTable.substring(startPositionOfBody, endPositionOfBody);//isi body
                                                                      
                                                                      //mencari tr pertama pada table
                                                                      int startPositionOfTr1 = subStringOfBody.indexOf("<tr>") + "<tr>".length();
                                                                      int endPositionOfTr1 = subStringOfBody.indexOf("</tr>", startPositionOfTr1);
                                                                      String subStringOfTr1 = subStringOfBody.substring(startPositionOfTr1, endPositionOfTr1);
                                                                      
                                                                      String subStringOfBody2 = subStringOfBody.substring(endPositionOfTr1, subStringOfBody.length());//isi body setelah dipotong tr pertama
                                                                      int startPositionOfTr2 = subStringOfBody2.indexOf("<tr>") + "<tr>".length();
                                                                      int endPositionOfTr2 = subStringOfBody2.indexOf("</tr>", startPositionOfTr2);
                                                                      String subStringOfTr2 = subStringOfBody2.substring(startPositionOfTr2, endPositionOfTr2);
                                                                      
                                                                      //disini diisi perulanganya
                                                                      
                                                                     String whereC = " "+PstEmpDocList.fieldNames[PstEmpDocList.FLD_OBJECT_NAME] + " = \"" + objectName+"\" AND "+PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID] + " = \"" + oidEmpDoc+"\"";
                                                                     Vector listEmp = PstEmpDocList.list(0, 0, whereC, ""); 

                                                                     //baca table dibawahnya

                                                                         String stringTrReplace = ""; 
                                                                    if (listEmp.size() > 0 ){
                                                                     for(int list = 0 ; list < listEmp.size(); list++ ){
                                                                         
                                                                         EmpDocList empDocList = (EmpDocList) listEmp.get(list);
                                                                         Employee employeeFetch = PstEmployee.fetchExc(empDocList.getEmployee_id());
                                                                         Hashtable HashtableEmp = PstEmployee.fetchExcHashtable(empDocList.getEmployee_id());
                                                                         
                                                                         
                                                                      
                                                                     stringTrReplace = stringTrReplace+"<tr>"; 
                                                                      
                                                                      //menghitung jumlah td html
                                                                      int startPositionOfTd = 0;
                                                                      int endPositionOfTd = 0;
                                                                      String subStringOfTd = "";
                                                                      String residuOfsubStringOfTr2 = subStringOfTr2 ;
                                                                      int jumlahtd = 0;
                                                                      
                                                                      
                                                                      
                                                                      do{
                                                                      
                                                                      stringTrReplace = stringTrReplace+"<td>";  
                                                                      
                                                                      startPositionOfTd = residuOfsubStringOfTr2.indexOf("<td>") + "<td>".length();
                                                                      endPositionOfTd = residuOfsubStringOfTr2.indexOf("</td>", startPositionOfTd);
                                                                      subStringOfTd = residuOfsubStringOfTr2.substring(startPositionOfTd, endPositionOfTd);//isi table 
                                                                      
                                                                      int startPositionOfFormula = 0;
                                                                      int endPositionOfFormula = 0;
                                                                      String subStringOfFormula = "";
                                                                      String residuOfsubStringOfTd = subStringOfTd;
                                                                              do{
                                                                                  
                                                                                startPositionOfFormula = residuOfsubStringOfTd.indexOf("${") + "${".length();
                                                                                endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);
                                                                                subStringOfFormula = residuOfsubStringOfTd.substring(startPositionOfFormula, endPositionOfFormula);//isi table 
                                                                      
                                                                                   
                                                                                String []partsOfFormula = subStringOfFormula.split("-");
                                                                                String objectNameFormula = partsOfFormula[0]; 
                                                                                String objectTypeFormula = partsOfFormula[1];
                                                                                String objectTableFormula = partsOfFormula[2];
                                                                                String objectStatusFormula = partsOfFormula[3];
                                                                                String value = "";
                                                                                if (objectTableFormula.equals("EMPLOYEE")){
                                                                                         value = (String) HashtableEmp.get(objectStatusFormula);
                                                                                      
                                                                                } else {
                                                                                    System.out.print("Selain Object Employee belum bisa dipanggil");
                                                                                }
                                                                                
                                                                                stringTrReplace = stringTrReplace+value;  
                                                                                
                                                                                residuOfsubStringOfTd = residuOfsubStringOfTd.substring(endPositionOfFormula, residuOfsubStringOfTd.length());
                                                                                endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);
                                                                              }while(endPositionOfFormula > 0);
                                                                      
                                                                      
                                                                             
                                                                        residuOfsubStringOfTr2 = residuOfsubStringOfTr2.substring(endPositionOfTd, residuOfsubStringOfTr2.length());
                                                                        startPositionOfTd = residuOfsubStringOfTr2.indexOf("<td>") + "<td>".length();
                                                                        endPositionOfTd = residuOfsubStringOfTr2.indexOf("</td>", startPositionOfTd);
                                                                      jumlahtd = jumlahtd + 1 ;
                                                                      
                                                                      stringTrReplace = stringTrReplace+"</td>"; 
                                                                      }while(endPositionOfTd > 0);
                                                                      
                                                                      }
                                                                                                                                              }
                                                                         
                                                                        empDocMasterTemplateText = empDocMasterTemplateText.replace("<tr>"+subStringOfTr2+"</tr>", stringTrReplace); 
                                                                        tanpaeditor = tanpaeditor.replace("<tr>"+subStringOfTr2+"</tr>", stringTrReplace); 
                                                                     //tutup perulanganya
                                                                      
                                                                      //setelah baca td maka akan membuat td baru... disini
                                                                      
                                                                       residueOfTextString = residueOfTextString.substring(endPositionOfTable, residueOfTextString.length());
                                                                        
                                                                      startPositionOfTable = residueOfTextString.indexOf("<table") + "<table".length();
                                                                      endPositionOfTable = residueOfTextString.indexOf("</table>", startPositionOfTable);
                                                                      
                                                                  }  while ( endPositionOfTable > 0);
                                                                
                                                                empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+objectName+"-"+objectType+"-"+objectClass+"-END"+"}", " ");                                                                     
                                                                tanpaeditor = tanpaeditor.replace("${"+objectName+"-"+objectType+"-"+objectClass+"-END"+"}", " ");       
                                                            
                                                        } else if  (objectType.equals("LISTMUTATION") && objectStatusField.equals("START")){
                                                            String add = "<a href=\"javascript:cmdAddEmp('"+objectName+"','"+oidEmpDoc+"')\">add employee</a></br>";
                                                            empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+subString+"}", add); 
                                                            tanpaeditor = tanpaeditor.replace("${"+subString+"}", " ");    
                                                            int endPnutup = stringResidual.indexOf("${"+objectName+"-"+objectType+"-"+objectClass+"-END"+"}", endPosition);
                                                                //ambil stringnya //end position adalah penutup formula dan end penutup adalah penutup isi dari end formula nya
                                                                String textString = stringResidual.substring(endPosition, endPnutup);//berisi dialam string yang ada di dalam formulanya
                                                               //menghapus tutup formula 
                                                              
                                                          
                                                                //mencari jumlah table table
                                                                  int startPositionOfTable = 0;
                                                                  int endPositionOfTable = 0;
                                                                  String subStringOfTable = "";
                                                                  String residueOfTextString = textString;
                                                                  do{
                                                                      //cari tag table pembuka
                                                                      startPositionOfTable = residueOfTextString.indexOf("<table") + "<table".length();
                                                                      //int tt = textString.indexOf("&lttable") + ((textString.indexOf("&gt") + 3)-textString.indexOf("&lttable"));
                                                                      endPositionOfTable = residueOfTextString.indexOf("</table>", startPositionOfTable);
                                                                      subStringOfTable = residueOfTextString.substring(startPositionOfTable, endPositionOfTable);//isi table 
                                                                      
                                                                      //mencari body 
                                                                      int startPositionOfBody = subStringOfTable.indexOf("<tbody>") + "<tbody>".length();
                                                                      int endPositionOfBody = subStringOfTable.indexOf("</tbody>", startPositionOfBody);
                                                                      String subStringOfBody = subStringOfTable.substring(startPositionOfBody, endPositionOfBody);//isi body
                                                                      
                                                                      //mencari tr pertama pada table
                                                                      int startPositionOfTr1 = subStringOfBody.indexOf("<tr>") + "<tr>".length();
                                                                      int endPositionOfTr1 = subStringOfBody.indexOf("</tr>", startPositionOfTr1);
                                                                      String subStringOfTr1 = subStringOfBody.substring(startPositionOfTr1, endPositionOfTr1);
                                                                      
                                                                      String subStringOfBody2 = subStringOfBody.substring(endPositionOfTr1, subStringOfBody.length());//isi body setelah dipotong tr pertama
                                                                      int startPositionOfTr2 = subStringOfBody2.indexOf("<tr>") + "<tr>".length();
                                                                      int endPositionOfTr2 = subStringOfBody2.indexOf("</tr>", startPositionOfTr2);
                                                                      String subStringOfTr2 = subStringOfBody2.substring(startPositionOfTr2, endPositionOfTr2);
                                                                      
                                                                      String subStringOfBody3 = subStringOfBody2.substring(endPositionOfTr2, subStringOfBody2.length());//isi body setelah dipotong tr pertama
                                                                      int startPositionOfTr3 = subStringOfBody3.indexOf("<tr>") + "<tr>".length();
                                                                      int endPositionOfTr3 = subStringOfBody3.indexOf("</tr>", startPositionOfTr3);
                                                                      String subStringOfTr3 = subStringOfBody3.substring(startPositionOfTr3, endPositionOfTr3);
                                                                      
                                                                      String subStringOfBody4 = subStringOfBody3.substring(endPositionOfTr3, subStringOfBody3.length());//isi body setelah dipotong tr pertama
                                                                      int startPositionOfTr4 = subStringOfBody4.indexOf("<tr>") + "<tr>".length();
                                                                      int endPositionOfTr4 = subStringOfBody4.indexOf("</tr>", startPositionOfTr4);
                                                                      String subStringOfTr4 = subStringOfBody4.substring(startPositionOfTr4, endPositionOfTr4);
                                                                      
                                                                      String subStringOfBody5 = subStringOfBody4.substring(endPositionOfTr4, subStringOfBody4.length());//isi body setelah dipotong tr pertama
                                                                      int startPositionOfTr5 = subStringOfBody5.indexOf("<tr>") + "<tr>".length();
                                                                      int endPositionOfTr5 = subStringOfBody5.indexOf("</tr>", startPositionOfTr5);
                                                                      String subStringOfTr5 = subStringOfBody5.substring(startPositionOfTr5, endPositionOfTr5);
                                                                      
                                                                      //disini diisi perulanganya
                                                                      
                                                                     String whereC = " "+PstEmpDocList.fieldNames[PstEmpDocList.FLD_OBJECT_NAME] + " = \"" + objectName+"\" AND "+PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID] + " = \"" + oidEmpDoc+"\"";
                                                                     Vector listEmp = PstEmpDocList.list(0, 0, whereC, ""); 

                                                                     //baca table dibawahnya

                                                                         String stringTrReplace = ""; 
                                                                    if (listEmp.size() > 0 ){
                                                                     for(int list = 0 ; list < listEmp.size(); list++ ){
                                                                         
                                                                         EmpDocList empDocList = (EmpDocList) listEmp.get(list);
                                                                         Employee employeeFetch = PstEmployee.fetchExc(empDocList.getEmployee_id());
                                                                         Hashtable HashtableEmp = PstEmployee.fetchExcHashtable(empDocList.getEmployee_id());
                                                                         
                                                                         //cek nilai ada object atau tidak
                                                                         
                                                                         
                                                                      
                                                                     stringTrReplace = stringTrReplace+"<tr>"; 
                                                                      
                                                                      //menghitung jumlah td html
                                                                      int startPositionOfTd = 0;
                                                                      int endPositionOfTd = 0;
                                                                      String subStringOfTd = "";
                                                                      String residuOfsubStringOfTr2 = subStringOfTr5 ;
                                                                      int jumlahtd = 0;
                                                                      
                                                                      
                                                                      
                                                                      do{
                                                                      
                                                                      stringTrReplace = stringTrReplace+"<td>";  
                                                                      
                                                                      startPositionOfTd = residuOfsubStringOfTr2.indexOf("<td>") + "<td>".length();
                                                                      endPositionOfTd = residuOfsubStringOfTr2.indexOf("</td>", startPositionOfTd);
                                                                      subStringOfTd = residuOfsubStringOfTr2.substring(startPositionOfTd, endPositionOfTd);//isi table 
                                                                      
                                                                      int startPositionOfFormula = 0;
                                                                      int endPositionOfFormula = 0;
                                                                      String subStringOfFormula = "";
                                                                      String residuOfsubStringOfTd = subStringOfTd;
                                                                              do{
                                                                                try {
                                                                                startPositionOfFormula = residuOfsubStringOfTd.indexOf("${") + "${".length();
                                                                                endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);
                                                                                subStringOfFormula = residuOfsubStringOfTd.substring(startPositionOfFormula, endPositionOfFormula);//isi table 
                                                                      
                                                                                   
                                                                                String []partsOfFormula = subStringOfFormula.split("-");
                                                                                String objectNameFormula = partsOfFormula[0]; 
                                                                                String objectTypeFormula = partsOfFormula[1];
                                                                                String objectTableFormula = partsOfFormula[2];
                                                                                String objectStatusFormula = partsOfFormula[3];
                                                                                String value = "";
                                                                                if (objectTableFormula.equals("EMPLOYEE")){
                                                                                    if (objectStatusFormula.equals("NEW_POSITION")){
                                                                                        String select = "<a href=\"javascript:cmdSelectPos('"+oidEmpDoc+"','"+objectName+"','"+objectType+"','"+objectClass+"','"+objectStatusField+"','"+employeeFetch.getOID()+"')\">SELECT</a></br>";
                                                                
                                                                                        String newposition = PstEmpDocListMutation.getNewPosition(oidEmpDoc, employeeFetch.getOID(), objectNameFormula) + "-"+select;
                                                                                        value = newposition;
                                                                                    } else { 
                                                                                         value = (String) HashtableEmp.get(objectStatusFormula);
                                                                                    }  
                                                                                } else {
                                                                                    System.out.print("Selain Object Employee belum bisa dipanggil");
                                                                                }
                                                                                
                                                                                stringTrReplace = stringTrReplace+value;  
                                                                                
                                                                                residuOfsubStringOfTd = residuOfsubStringOfTd.substring(endPositionOfFormula, residuOfsubStringOfTd.length());
                                                                                endPositionOfFormula = residuOfsubStringOfTd.indexOf("}", startPositionOfFormula);
                                                                              } catch (Exception e){
                                                                              System.out.printf(e+"");
                                                                              }
                                                                             }  while(endPositionOfFormula > 0);
                                                                      
                                                                      
                                                                             
                                                                        residuOfsubStringOfTr2 = residuOfsubStringOfTr2.substring(endPositionOfTd, residuOfsubStringOfTr2.length());
                                                                        startPositionOfTd = residuOfsubStringOfTr2.indexOf("<td>") + "<td>".length();
                                                                        endPositionOfTd = residuOfsubStringOfTr2.indexOf("</td>", startPositionOfTd);
                                                                      jumlahtd = jumlahtd + 1 ;
                                                                      
                                                                      stringTrReplace = stringTrReplace+"</td>"; 
                                                                      }while(endPositionOfTd > 0);
                                                                      
                                                                      }
                                                                                                                                              }
                                                                         
                                                                        empDocMasterTemplateText = empDocMasterTemplateText.replace("<tr>"+subStringOfTr5+"</tr>", stringTrReplace); 
                                                                        tanpaeditor = tanpaeditor.replace("<tr>"+subStringOfTr5+"</tr>", stringTrReplace); 
                                                                     //tutup perulanganya
                                                                      
                                                                      //setelah baca td maka akan membuat td baru... disini
                                                                      
                                                                       residueOfTextString = residueOfTextString.substring(endPositionOfTable, residueOfTextString.length());
                                                                        
                                                                      startPositionOfTable = residueOfTextString.indexOf("<table") + "<table".length();
                                                                      endPositionOfTable = residueOfTextString.indexOf("</table>", startPositionOfTable);
                                                                      
                                                                  }  while ( endPositionOfTable > 0);
                                                                
                                                                empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+objectName+"-"+objectType+"-"+objectClass+"-END"+"}", " ");                                                                     
                                                                tanpaeditor = tanpaeditor.replace("${"+objectName+"-"+objectType+"-"+objectClass+"-END"+"}", " ");       
                                                            
                                                        } else if  (objectType.equals("FIELD") && objectStatusField.equals("AUTO")){
                                                                //String field = "<input type=\"text\" name=\""+ subString +"\" value=\"\">";
                                                            Date newd = new Date();
                                                                String field = "04/KEP/BPD-PMT/"+newd.getMonth()+"/"+newd.getYear();
                                                                empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+subString+"}", field); 
                                                                tanpaeditor = tanpaeditor.replace("${"+subString+"}", field); 
                                                              
                                                        } else if  (objectType.equals("FIELD")){
                                                            
                                                            if ((objectClass.equals("ALLFIELD")) && (objectStatusField.equals("TEXT"))){
                                                               //String add = "<a href=\"javascript:cmdAddText('"+objectName+"','"+oidEmpDoc+"','"+objectStatusField+"')\">"+(hlistEmpDocField.get(objectName) != null?(String)hlistEmpDocField.get(objectName):"add")+"</a></br>";
                                                                String add = "<a href=\"javascript:cmdAddText('"+oidEmpDoc+"','"+objectName+"','"+objectType+"','"+objectClass+"','"+objectStatusField+"')\">"+(hlistEmpDocField.get(objectName) != null?(String)hlistEmpDocField.get(objectName):"NEW TEXT")+"</a></br>";
                                                                empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+subString+"}", add); 
                                                                tanpaeditor = tanpaeditor.replace("${"+subString+"}", (hlistEmpDocField.get(objectName) != null?(String)hlistEmpDocField.get(objectName):" ")); 
                                                            } else if ((objectClass.equals("ALLFIELD")) && (objectStatusField.equals("DATE"))){
                                                                String add = "<a href=\"javascript:cmdAddText('"+oidEmpDoc+"','"+objectName+"','"+objectType+"','"+objectClass+"','"+objectStatusField+"')\">"+(hlistEmpDocField.get(objectName) != null?(String)hlistEmpDocField.get(objectName):"NEW DATE")+"</a></br>";
                                                                empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+subString+"}", add); 
                                                                tanpaeditor = tanpaeditor.replace("${"+subString+"}", (hlistEmpDocField.get(objectName) != null?(String)hlistEmpDocField.get(objectName):" ")); 
                                                            } else if ((objectClass.equals("CLASSFIELD"))){
                                                                //cari tahu ini untuk perubahan apa Divisi
                                                                 String getNama ="";
                                                                if (hlistEmpDocField.get(objectName) != null){
                                                                    getNama = PstDocMasterTemplate.getNama((String)hlistEmpDocField.get(objectName), objectStatusField);
                                                                }
            
                                                                String add = "<a href=\"javascript:cmdAddText('"+oidEmpDoc+"','"+objectName+"','"+objectType+"','"+objectClass+"','"+objectStatusField+"')\">"+(hlistEmpDocField.get(objectName) != null?getNama:"ADD")+"</a></br>";
                                                                empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+subString+"}", add); 
                                                                tanpaeditor = tanpaeditor.replace("${"+subString+"}", (hlistEmpDocField.get(objectName) != null?(String)hlistEmpDocField.get(objectName):" ")); 
                                                            
                                                            } else if ((objectClass.equals("EMPDOCFIELD"))){
                                                                //String add = "<a href=\"javascript:cmdAddText('"+objectName+"','"+oidEmpDoc+"','"+objectStatusField+"')\">"+(empDOcFecthH.get(objectName) != null?(String)empDOcFecthH.get(objectName):"add")+"</a></br>";
                                                                empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+subString+"}", ""+empDOcFecthH.get(objectStatusField) ); 
                                                                tanpaeditor = tanpaeditor.replace("${"+subString+"}", ""+empDOcFecthH.get(objectStatusField) ); 
                                                            }
                                                                
                                                        }
                                                        
                                                        } else if (!objectName.equals("") && objectType.equals("") && objectClass.equals("") && objectStatusField.equals("")) {
                                                              String obj = ""+(hlistEmpDocField.get(objectName) != null?(String)hlistEmpDocField.get(objectName):"-");
                                                                 empDocMasterTemplateText = empDocMasterTemplateText.replace("${"+objectName+"}", obj);
                                                                 tanpaeditor = tanpaeditor.replace("${"+objectName+"}", obj);
                                                        }
                                                        stringResidual = stringResidual.substring(endPosition, stringResidual.length());
                                                        objectDocumentDetail.setStartPosition(startPosition);
                                                        objectDocumentDetail.setEndPosition(endPosition);
                                                        objectDocumentDetail.setText(subString);
                                                        vNewString.add(objectDocumentDetail);
                                                        
                                                        
                                                        //mengecek apakah masih ada sisa
                                                        startPosition = stringResidual.indexOf("${") + "${".length();
                                                        endPosition = stringResidual.indexOf("}", startPosition);
                                                 } while ( endPosition > 0);
                                                 } catch (Exception e){}
                                              
                                             %>
                                             <%                                               
                                                
                                                empDocMasterTemplateText = empDocMasterTemplateText.replace("&lt", "<");
                                                empDocMasterTemplateText = empDocMasterTemplateText.replace("&gt", ">");
   
                                             %>
                                              
                                              
                                              <tr align="left" valign="top" > 
                                                <td colspan="3"cen >
                                                  <table width="80%" align="center"  cellspacing="0" cellpadding="0" >
                                                    <tr>
                                                      <td width="37%">&nbsp;</td>
                                                    </tr>
                                                    <tr>
                                                      <td width="37%">&nbsp;</td>
                                                    </tr>
                                                  </table> 
                                                </td>
                                              </tr>
                                              
                                             <tr align="left" valign="top" > 
                                                <td colspan="3"cen >
                                                  <table width="80%" align="center" border="1"  cellspacing="0" cellpadding="0" style="background-color: white; ">
                                                    <tr>
                                                      <td width="37%"><%=empDocMasterTemplateText%></td>
                                                    </tr>
                                                  </table> 
                                                </td>
                                              </tr>
                                           
                                              
                                               <% if(nstatus){%>
                                              <tr align="left" valign="top"> 
                                                <td colspan="3"cen >
                                                  <table width="80%" align="center" border="1"  cellspacing="0" cellpadding="0">
                                                    <tr>
                                                      <td width="37%"><a href="javascript:cmdPrint('<%=oidEmpDoc%>')" class="command">EDITOR & PRINT</a> || <a href="javascript:cmdExpense('<%=oidEmpDoc%>')" class="command">EXPENSE</a> </td>
                                                    </tr>
                                                  </table> 
                                                </td>
                                              </tr>
                                              
                                              <tr align="left" valign="top"> 
                                                <td colspan="3"cen >
                                                  <table width="80%" align="center" border="1"  cellspacing="0" cellpadding="0">
                                                    <tr>
                                                      <td width="37%">
                                                          
                                                       <% 
                                                       String whereClause1 = PstDocMasterAction.fieldNames[PstDocMasterAction.FLD_DOC_MASTER_ID]+" = "+ empDoc1.getDoc_master_id();
                                                       Vector listDocMasterAction = PstDocMasterAction.list(0, 0, whereClause1, "");
                                                        if (listDocMasterAction.size() > 0 ) { 
                                                        for (int i = 0; i < listDocMasterAction.size(); i++){
                                                           DocMasterAction docMasterAction1 =  (DocMasterAction) listDocMasterAction.get(i);
                                                        %>  
                                                          
                                                        <a href="javascript:cmdAction('<%=oidEmpDoc%>','<%=docMasterAction1.getOID() %>')" class="command"> || ACTION <%=docMasterAction1.getActionTitle() %> || </a> 
                                                      
                                                        <%
                                                                 }
                                                        }
                                                        %>
                                                      </td>
                                                    </tr>
                                                  </table> 
                                                </td>
                                              </tr>
                                              
                                              <%}%>
                                               
                                             <tr align="left" valign="top"> 
                                                <td colspan="3"cen >
                                                  <table width="80%" align="center" border="1"  cellspacing="0" cellpadding="0">
                                                    <tr>
                                                      <td width="37%"><%= drawList(listMasterDocFlow, oidEmpDoc)%></td>
                                                    </tr>
                                                  </table> 
                                                </td>
                                              </tr>
                                     
                                          <!-- <textarea class="ckeditor"  class="elemenForm" cols="70" rows="40"></textarea> -->
                                       
                                        
                                            </table>
                                          </td>										  
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="8" valign="middle" width="17%">&nbsp;</td>
                                          <td height="8" colspan="2" width="83%">&nbsp; 
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
   <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%>
            <tr>
                            <td valign="bottom">
                               <!-- untuk footer -->
                                <%@include file="../../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
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
	//var oBody = document.body;
	//var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
</script>
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
