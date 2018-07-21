<%-- 
    Document   : overtime_report
    Created on : Apr 1, 2012, 10:06:42 PM
    Author     : Gede115
--%>


<%@page import="com.dimata.harisma.entity.leave.I_Leave"%>
<%@page import="com.dimata.harisma.entity.attendance.PstDpStockManagement"%>
<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "java.sql.Time" %>

<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>

<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<%@ page import = "com.dimata.qdep.entity.*" %>

<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.overtime.*" %>
<%@ page import = "com.dimata.harisma.form.overtime.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.session.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>

<%@ include file = "../../main/javainit.jsp" %>

<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_OVERTIME, AppObjInfo.OBJ_PAYROLL_OVERTIME_REPORT);%>
<%@ include file = "../../main/checkuser.jsp" %>
<%
            /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>

<!-- Jsp Block -->
<%!
 long hr_department = 0;
 int finalApprovalMinLevel = PstPosition.LEVEL_DIRECTOR;
 int finalApprovalMaxLevel = PstPosition.LEVEL_GENERAL_MANAGER;
 
 public void init(){
            try{ hr_department = Long.parseLong(PstSystemProperty.getValueByName("OID_HRD_DEPARTMENT")); } catch(Exception exc){}                        
}
  
%>

<%
        I_Leave leaveConfig = null;
        try {
            leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }


float minOvertimeHour = 0.0F;
try{
    String minOv = PstSystemProperty.getValueByName("MIN_OVERTM_DURATION");
    if(minOv!=null && minOv.length()>0){
        minOvertimeHour =  Float.parseFloat(minOv)/60f;
    }
 } 
catch(Exception exc){
     
 }
        
//Untuk religion
int countReligion = 0;

            try{
                countReligion = PstReligion.getCount(null);
            }catch(Exception E){
                System.out.println("excption "+E.toString());
            }

            Vector vReligionList = new Vector();
            String orderCnt = PstReligion.fieldNames[PstReligion.FLD_RELIGION]+" ASC ";

            try{
                vReligionList = PstReligion.list(0, 0, "", orderCnt);
            }catch(Exception E){
                System.out.println("excption "+E.toString());
            }

            String[] religionId = null;

            religionId = new String[countReligion];

            int max1 = 0;
            for(int j = 0 ; j < countReligion ; j++){

                Religion objReligion = new Religion();
                objReligion = (Religion)vReligionList.get(j);

                String name = "RELIG_"+objReligion.getOID();
                religionId[j] = FRMQueryString.requestString(request,name);
                max1++;
            }

//} 

//}



//long oidCompany = FRMQueryString.requestLong(request, FrmSrcOvertimeReport.fieldNames[FrmSrcOvertimeReport.FRM_FIELD_COMPANY_ID]);
//long oidDivision = FRMQueryString.requestLong(request, FrmSrcOvertimeReport.fieldNames[FrmSrcOvertimeReport.FRM_FIELD_DIVISION_ID]);
//long oidDepartment = FRMQueryString.requestLong(request, FrmSrcOvertimeReport.fieldNames[FrmSrcOvertimeReport.FRM_FIELD_DEPARTMENT_ID]);
//Date dateFrom = FRMQueryString.requestDate(request, FrmSrcOvertimeReport.fieldNames[FrmSrcOvertimeReport.FRM_FIELD_REQ_DATE]);
//Date dateTo = FRMQueryString.requestDate(request, FrmSrcOvertimeReport.fieldNames[FrmSrcOvertimeReport.FRM_FIELD_REQ_DATE_TO]);
long oidOvertime = FRMQueryString.requestLong(request, "overtime_oid");
long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");
int iCommand = FRMQueryString.requestCommand(request);
int iSearchAndCalculate = FRMQueryString.requestInt(request, "search_calc");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
int start = FRMQueryString.requestInt(request, "start");
int iErrCode = FRMMessage.ERR_NONE;

int gettime = FRMQueryString.requestInt(request, "gettime");


String msgStr = "";
//update by satrya 22012-12-20
String Errmsg ="";
int recordToGet = 10;
int vectSize = 0;
String orderClause = "";
String whereClause = "";
Overtime overtime = new Overtime();
ControlLine ctrLine = new ControlLine();
SrcOvertimeReport srcOvertimeReport = new SrcOvertimeReport();
CtrlOvertime ctrlOvertime = new CtrlOvertime(request);
FrmSrcOvertimeReport frmSrcOvertimeReport = new FrmSrcOvertimeReport(request, srcOvertimeReport);



if(iCommand == Command.LIST)
{
     frmSrcOvertimeReport.requestEntityObject(srcOvertimeReport);
      session.putValue(SessOvertime.SESS_SRC_OVERTIME, srcOvertimeReport);
}
//update by satrya 2012-12-06
else if(iCommand==Command.REFRESH){
        try{
                    srcOvertimeReport =(SrcOvertimeReport) session.getValue(SessOvertime.SESS_SRC_OVERTIME);
                    if(srcOvertimeReport==null){
                        srcOvertimeReport = new SrcOvertimeReport();
                    }
                  } catch(Exception exc){
                      System.out.println("Exception SrcOvertimeReport"+exc);
                  }
}

 boolean loginByHRD= false;
            if(departmentOid ==hr_department){
                loginByHRD = true;
            }
SessOvertime sessOvertime = new SessOvertime();
//if(iCommand == Command.UPDATE ){
//    srcOvertimeReport = (SrcOvertimeReport) session.getValue(SessOvertime.SESS_SRC_OVERTIME);
//}else{
if(iCommand == Command.SAVE){
    srcOvertimeReport = (SrcOvertimeReport) session.getValue(SessOvertime.SESS_SRC_OVERTIME + "REPORT"); 
     session.putValue(SessOvertime.SESS_SRC_OVERTIME, srcOvertimeReport);
}else {
    session.putValue(SessOvertime.SESS_SRC_OVERTIME+ "REPORT", srcOvertimeReport);
     session.putValue(SessOvertime.SESS_SRC_OVERTIME, srcOvertimeReport);
}

  if(srcOvertimeReport==null){
        FrmSrcOvertimeReport frmScr = new FrmSrcOvertimeReport(request, srcOvertimeReport);
        srcOvertimeReport= frmScr.getEntityObject(); 
        if(srcOvertimeReport==null){
            srcOvertimeReport = new SrcOvertimeReport();
        }        
  }
//}
/*
if(iCommand == Command.SAVE && prevCommand == Command.ADD)
{
	start = PstOvertime.findLimitStart(oidOvertime,recordToGet, whereClause,orderClause);
	vectSize = PstOvertime.getCount(whereClause);
}
else
{
	vectSize = sessOvertime.countOvertime(srcOvertime);
}

if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)||
(iCommand==Command.LAST)||(iCommand==Command.LIST))
		start = ctrlOvertime.actionList(iCommand, start, vectSize, recordToGet);

Vector listOvertime = new Vector(1,1);
if(iCommand == Command.SAVE && prevCommand==Command.ADD)
{
	listOvertime = sessOvertime.searchOvertime(new SrcOvertime(), start, recordToGet);

}
else
{
    try{
	listOvertime = sessOvertime.searchOvertime(srcOvertime, start, recordToGet);
        }catch(Exception ex){

        }
}*/
Hashtable hashSection = PstSection.hashlistSection();
%>
<html><!-- #BeginTemplate "/Templates/maintab.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" -->
        <title>Overtime Report</title>
        <script language="JavaScript">
            
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
                                
            function cmdSave(){
                document.frmovertime.command.value="<%=Command.SAVE%>";
                document.frmovertime.prev_command.value="<%=prevCommand%>";
                document.frmovertime.action="overtime_report.jsp";
                document.frmovertime.submit();
            }
            function cmdRel(){
                 document.frmovertime.command.value="<%=Command.REFRESH%>";
                document.frmovertime.action="overtime_report.jsp";
                document.frmovertime.submit();
                   
               } 
           function cmdBack(){
                 document.frmovertime.command.value="<%=Command.BACK%>";
                document.frmovertime.action="src_overtime_report.jsp";
                document.frmovertime.submit();
                   
               } 
             function cmdEditPresenceOT(otDetailId,employeeOid,lDateFrom){
               
               var oidDetailOT="<%=(FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_OVERTIME_DETAIL_ID]+"=")%>"+otDetailId;
               var employeeId="<%=(FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_EMPLOYEE_ID]+"=")%>"+employeeOid;
               
               
               var linkPage = "overtime_set_manually.jsp?"+oidDetailOT+"&"+employeeId+"&lDateFrom="+lDateFrom+"&command=<%=(Command.EDIT)%>"; 
               var newWin = window.open(linkPage,"Overtime","height=700,width=990,status=yes,toolbar=yes,menubar=no,resizable=yes,scrollbars=yes,location=yes");  			
               //window.open("<%//=approot%>/report/presence/overtime_set_manually.jsp?command=<%//=(Command.EDIT)%>&"+employeeId+"&"+oidDetailOT+"&lDateFrom="+lDateFrom, null, "height=600,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");
               newWin.focus();
               //alert(otDetailid);  
             }  
             function cmdAddAttd(otDetailId,employeeOid,lDateFrom){
                var oidDetailOT="<%=(FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_OVERTIME_DETAIL_ID]+"=")%>"+otDetailId;
               var employeeId="<%=(FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_EMPLOYEE_ID]+"=")%>"+employeeOid;
               var linkPage = "<%=approot%>/employee/presence/presence_edit.jsp?"+"source=overtime"+"&"+oidDetailOT+"&"+employeeId+"&lDateFrom="+lDateFrom+"&privAdd="+true+"&privUpdate="+true+"&command=<%=(Command.ADD)%>";  
               var newWin = window.open(linkPage,"Overtime","height=700,width=990,status=yes,toolbar=yes,menubar=no,resizable=yes,scrollbars=yes,location=yes"); 			
               //window.open("<%//=approot%>/report/presence/overtime_set_manually.jsp?command=<%//=(Command.EDIT)%>&"+employeeId+"&"+oidDetailOT+"&lDateFrom="+lDateFrom, null, "height=600,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");
               newWin.focus();
               //alert(otDetailid);  
                 
             }
                               
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
        <!-- #EndEditable -->
    </head>

    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">

        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF">
            <tr>
                <td width="88%" valign="top" align="left">
                    <table width="100%" border="0" cellspacing="3" cellpadding="2">
                        <tr>
                            <td width="100%">
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td valign="top">
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1">
                                                            <tr>
                                                                <td valign="top"> <!-- #BeginEditable "content" -->
                                                                    <form name="frmovertime" method ="post" action="">
                                                                        <input type="hidden" name="command" value="<%=iCommand%>">
                                                                        <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                                                        <input type="hidden" name="start" value="<%=start%>">
                                                                        <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                                        <input type="hidden" name="overtime_oid" value="<%=oidOvertime%>">
                                                                        <input type="hidden" name="department_oid" value="<%=(srcOvertimeReport!=null?srcOvertimeReport.getDepartmentId():0 )%>">
                                                                        <input type="hidden" name="employee_oid" value="<%=oidEmployee%>">
                                                                        <input type="hidden" name="hidden_overtime_detail_id" value="0">


                                                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                            <tr>
                                                                                <td  style="background-color:<%=bgColorContent%>; ">
                                                                                    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                                                        <tr>
                                                                                            <td valign="top">
		<table  style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="0" cellpadding="0" class="tabbg">
			<tr align="left" valign="top">
				<td height="8" valign="middle" colspan="3">

                                    <table width="100%" border="0" cellspacing="2" cellpadding="2">
						<tr>
							<td colspan="2"><h4><b class="listtitle">Overtime Report</b><h4></td>                                                        
						</tr>
						<tr>
							<td ><b class="listtitle">
                                                                <%
        Vector allw_value = new Vector(1, 1);
        Vector allw_key = new Vector(1, 1);
        for (int il = 0; il < Overtime.allowanceType.length ; il++){
                allw_value.add(""+ Overtime.allowanceValue[il]);
                allw_key.add(Overtime.allowanceType[il] );
        }
                                                                
                                                                
                                                                 Company company = null;
                                                                 try{
                                                                    company  = PstCompany.fetchExc(srcOvertimeReport.getCompanyId());
                                                                 } catch(Exception exc){
                                                                     System.out.println(exc);
                                                                 }
                                                                 Division division = null;                                                                                                                                 
                                                                 try{
                                                                    if(srcOvertimeReport.getDivisionId()!=0){
                                                                    division = PstDivision.fetchExc(srcOvertimeReport.getDivisionId());
                                                                    }
                                                                 } 
                                                                 catch(Exception exc){
                                                                     System.out.println("Exception Overtime Report  [Table: hr_division] Record not found"+exc);
                                                                 }                                                  
                                                                 Department department = null;
                                                                 try{
                                                                     if(srcOvertimeReport.getDepartmentId()!=0){
                                                                     department = PstDepartment.fetchExc(srcOvertimeReport.getDepartmentId());
                                                                     }
                                                                 }catch(Exception exc){
                                                                     System.out.println("Exception  Overtime Report [Table: hr_department] Record not found"+exc);
                                                                 } 
                                                                 
                                                                %>
                                                                Company : <%=(company!=null? company.getCompany() : "- ") %>&nbsp;&nbsp;
                                                                Division: <%=(division!=null? division.getDivision() : "- ") %>&nbsp;&nbsp;
                                                                Department: <%=(department!=null? department.getDepartment() :"- ") %>&nbsp;&nbsp;
                                                                Date from : <%=Formater.formatDate(srcOvertimeReport.getRequestDate(), "dd MMMM yyyy")%> to 
                                                                <%=Formater.formatDate(srcOvertimeReport.getRequestDateTo(), "dd MMMM yyyy")%>
                                                            </b></td> <td nowrap ><a href="javascript:cmdBack()" ><< Back</a> </td>                                                       
						</tr>
						
						<tr>
							<td width="100%" colspan="2">
                                                <!-- Start Tabel Report -->
                                                <%//untuk search berdasarkan religion
                                                Vector vReligion = new Vector();
                                                String order = PstReligion.fieldNames[PstReligion.FLD_RELIGION]+" ASC ";
                                                try{
                                                    vReligion = PstReligion.list(0, 0, "", order);
                                                }catch(Exception E){
                                                    System.out.println("Exception "+E.toString());
                                                }

                                                String whereReligion = "";

                                                for(int idRelig = 0 ; idRelig < vReligion.size() ; idRelig++){

                                                    Religion religion = new Religion();
                                                    religion = (Religion)vReligion.get(idRelig);

                                                    if(religionId[idRelig].equals("1")){

                                                        if(whereReligion.length()<=0){
                                                            whereReligion = whereReligion + " emp."+PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]+" = "+religion.getOID();
                                                        }else{
                                                            whereReligion = whereReligion + " OR emp."+PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]+" = "+religion.getOID();

                                                        }

                                                    }

                                                }

                                                %>
                                                <%
                                                    //wherecluse Status
                                                    String whereClauseStat=""; 
                                                    if(srcOvertimeReport.getStatusDoc().size()>0){
                                                        
                                                       for(int stat=0 ; stat<srcOvertimeReport.getStatusDoc().size() ; stat++){
                                                           if(stat==0){
                                                               whereClauseStat=whereClauseStat+" odt."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_STATUS] +"="+srcOvertimeReport.getStatusDoc().get(stat);
                                                               }else{
                                                                whereClauseStat=whereClauseStat+" OR odt."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_STATUS] +"="+srcOvertimeReport.getStatusDoc().get(stat);
                                                           }
                                                       }
                                                    }
                                                    
                                                    whereClauseStat= (whereClauseStat.length()>0? whereClauseStat+ " AND " : "" ) +  " (o."+PstOvertime.fieldNames[PstOvertime.FLD_STATUS_DOC]+"<>"+ I_DocStatus.DOCUMENT_STATUS_CANCELLED + " AND " +
                                                            " odt."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_STATUS]+"<>"+ I_DocStatus.DOCUMENT_STATUS_CANCELLED + " )"; 
                                                                                                        
                                                    

                                                    //whereclause overtime date time
                                                    String whereClauseReq="";
                                                    if ((srcOvertimeReport.getRequestDate() != null) && (srcOvertimeReport.getRequestDateTo() != null)) {
                                                        String strDateStart = Formater.formatDate(srcOvertimeReport.getRequestDate(), "yyyy-MM-dd 00:00:00");
                                                        String strDateEnd = Formater.formatDate(srcOvertimeReport.getRequestDateTo(), "yyyy-MM-dd 23:59:59");
                                                        whereClauseReq = whereClauseReq + " odt." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM] + " BETWEEN '"
                                                                + strDateStart + "' AND '" + strDateEnd + "'";
                                                    }
                                                %>
                                                <table width="100%" class="listgen" cellspacing="1">
                                                <!--Start loop division-->
                                                <%//where clause countDiv
                                                String whereClauseDiv;
                                                if(srcOvertimeReport.getDivisionId() ==0){
                                                    whereClauseDiv=" d."+ PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID] +"="+srcOvertimeReport.getCompanyId() ;
                                                    if(whereReligion.length() > 0){
                                                        whereClauseDiv = whereClauseDiv + " AND ("+ whereReligion +") ";
                                                    }
                                                    if(whereClauseStat.length()>0){
                                                        whereClauseDiv = whereClauseDiv + " AND ("+ whereClauseStat +") ";
                                                    }
                                                    if((srcOvertimeReport.getRequestDate() != null) && (srcOvertimeReport.getRequestDateTo() != null)) {
                                                        whereClauseDiv = whereClauseDiv + " AND "+whereClauseReq;
                                                    }
                                                }
                                                else{
                                                    whereClauseDiv=" d."+ PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID] +"="+srcOvertimeReport.getCompanyId()+ " AND d."+ PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID] +"="+srcOvertimeReport.getDivisionId();
                                                    if(whereReligion.length() > 0){
                                                        whereClauseDiv = whereClauseDiv + " AND ("+ whereReligion +") ";
                                                    }
                                                    if(whereClauseStat.length()>0){
                                                        whereClauseDiv = whereClauseDiv + " AND ("+ whereClauseStat +") ";
                                                    }
                                                    if((srcOvertimeReport.getRequestDate() != null) && (srcOvertimeReport.getRequestDateTo() != null)) {
                                                        whereClauseDiv = whereClauseDiv + " AND "+whereClauseReq;
                                                    }
                                                }
                                                 Vector listDiv = PstDivision.list3(0, 0, whereClauseDiv, " DIVISION ");
                                                 int totalPerson = 0;
                                                 double GrandTotal = 0;
                                                 double realGrandTotal = 0;
                                                 boolean reloadOvertimeIndexMap = true;                                                  
                                                %>
                                                <%for(int a=0;a<listDiv.size();a++){
                                                    int totalPersonDiv =0;
                                                    %>
                                                <%Division div = (Division) listDiv.get(a);%>
                                                  <!--Start loop Department-->
                                                  <%//where clause countDep
                                                  String whereClauseDep= "";/*" o."+PstOvertime.fieldNames[PstOvertime.FLD_STATUS_DOC]+"<>"+ I_DocStatus.DOCUMENT_STATUS_CANCELLED + " AND " +
                                                            " odt."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_STATUS]+"<>"+ I_DocStatus.DOCUMENT_STATUS_CANCELLED + " AND " ;
 *                                                          */
                                                  
                                                if(srcOvertimeReport.getDepartmentId()==0){
                                                    whereClauseDep=" dep."+ PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] +"="+div.getOID();
                                                    if(whereReligion.length() > 0){
                                                        whereClauseDep = whereClauseDep + " AND ("+ whereReligion +") ";
                                                    }
                                                    if(whereClauseStat.length()>0){
                                                        whereClauseDep = whereClauseDep + " AND ("+ whereClauseStat +") ";
                                                    }
                                                    if((srcOvertimeReport.getRequestDate() != null) && (srcOvertimeReport.getRequestDateTo() != null)) {
                                                        whereClauseDep = whereClauseDep + " AND "+whereClauseReq;
                                                    }
                                                }
                                                else{
                                                  //update by satrya 2013-08-13
                                                      //penambahan section
                                                    if(srcOvertimeReport!=null && srcOvertimeReport.getSectionId()!=0){
                                                    
                                                        whereClauseDep=" dep."+ PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] +"="+div.getOID() 
                                                                +" AND dep."+ PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] 
                                                                +"="+srcOvertimeReport.getDepartmentId()
                                                                +" AND sec."+ PstSection.fieldNames[PstSection.FLD_SECTION_ID] 
                                                                +"="+srcOvertimeReport.getSectionId();
                                                        if(whereReligion.length() > 0){
                                                            whereClauseDep = whereClauseDep + " AND ("+ whereReligion +") ";
                                                        }
                                                        if(whereClauseStat.length()>0){
                                                            whereClauseDep = whereClauseDep + " AND ("+ whereClauseStat +") ";
                                                        }
                                                        if((srcOvertimeReport.getRequestDate() != null) && (srcOvertimeReport.getRequestDateTo() != null)) {
                                                            whereClauseDep = whereClauseDep + " AND "+whereClauseReq;
                                                        }
                                                    }else{
                                                    whereClauseDep=" dep."+ PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] +"="+div.getOID() +" AND dep."+ PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] +"="+srcOvertimeReport.getDepartmentId();
                                                    if(whereReligion.length() > 0){
                                                        whereClauseDep = whereClauseDep + " AND ("+ whereReligion +") ";
                                                    }
                                                    if(whereClauseStat.length()>0){
                                                        whereClauseDep = whereClauseDep + " AND ("+ whereClauseStat +") ";
                                                    }
                                                    if((srcOvertimeReport.getRequestDate() != null) && (srcOvertimeReport.getRequestDateTo() != null)) {
                                                        whereClauseDep = whereClauseDep + " AND "+whereClauseReq;
                                                    }
                                                   }
                                                }
                                                Vector listDep = PstDepartment.list3(0, 0, whereClauseDep, " DEPARTMENT ");
                                                if(listDep==null || listDep.size()<1){
                                                    continue;// next loop for division
                                                }
                                                %>
                                                <%
                                                 double totalDiv=0;
                                                 double realTotalDiv=0;
                                                 boolean showDivision=false;
 %>
                                                <%for(int b=0;b<listDep.size();b++){%>
                                                <%Department dep = (Department) listDep.get(b);%>
                                                <%//where clause countEmp
                                                String whereClauseEmp=
                                                        /*" o."+PstOvertime.fieldNames[PstOvertime.FLD_STATUS_DOC]+"<>"+ I_DocStatus.DOCUMENT_STATUS_CANCELLED + " AND " +
                                                        " odt."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_STATUS]+"<>"+ I_DocStatus.DOCUMENT_STATUS_CANCELLED + " AND " + */
                                                        " emp."+ PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +"="+dep.getOID();
                                                if(whereReligion.length() > 0){
                                                    whereClauseEmp = whereClauseEmp + " AND ("+ whereReligion +") ";
                                                }
                                                if(whereClauseStat.length()>0){
                                                        whereClauseEmp = whereClauseEmp + " AND ("+ whereClauseStat +") ";
                                                }
                                                if((srcOvertimeReport.getRequestDate() != null) && (srcOvertimeReport.getRequestDateTo() != null)) {
                                                        whereClauseEmp = whereClauseEmp + " AND "+whereClauseReq;
                                                }
                                                if((srcOvertimeReport.getRequestDate() != null) && (srcOvertimeReport.getRequestDateTo() != null)) {
                                                        whereClauseEmp = whereClauseEmp + " AND "+whereClauseReq;
                                                }
                                                if(srcOvertimeReport.getPayroll() != null && srcOvertimeReport.getPayroll().length()>0 ) {
                                                   whereClauseEmp = whereClauseEmp + " AND  emp."+ PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                                                           "='"+srcOvertimeReport.getPayroll()+"'" ;
                                                }
                                                if(srcOvertimeReport.getFullname() != null && srcOvertimeReport.getFullname().length()>0 ) {
                                                   whereClauseEmp = whereClauseEmp + " AND  emp."+ PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                                                           " LIKE '%"+srcOvertimeReport.getFullname()+"%'" ;
                                                }
                                                //update by satrya 2013-08-13
                                                //penambahan section
                                                if(srcOvertimeReport != null && srcOvertimeReport.getSectionId()!=0 ) {
                                                   whereClauseEmp = whereClauseEmp + " AND  sec."+ PstSection.fieldNames[PstSection.FLD_SECTION_ID] +
                                                          "="+srcOvertimeReport.getSectionId() ;
                                                }
                                                Vector listEmp = PstEmployee.list3(0, 0, whereClauseEmp, "");
                                                int  totalPersonInDep =0;
                                                double totalDep=0;
                                                double realTotalDep=0;
                                                //update by devin 2014-02-20
                                                  double realTotalIdx=0;
                                                //where untuk count list emptime @department
                                                Vector listTime = null;
                                                String whereClauseEmpTime=""; 
                                                if(listEmp!=null && listEmp.size()>0){
                                                    whereClauseEmpTime= /* " o."+PstOvertime.fieldNames[PstOvertime.FLD_STATUS_DOC]+"<>"+ I_DocStatus.DOCUMENT_STATUS_CANCELLED + " AND " +
                                                            " odt."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_STATUS]+"<>"+ I_DocStatus.DOCUMENT_STATUS_CANCELLED + " AND " + 
                                                            */
                                                            " emp."+ PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +"="+dep.getOID();
                                                    if(whereReligion.length() > 0){
                                                        whereClauseEmpTime = whereClauseEmpTime + " AND ("+ whereReligion +") ";
                                                    }
                                                    if(whereClauseStat.length()>0){
                                                        whereClauseEmpTime = whereClauseEmpTime + " AND ("+ whereClauseStat +") ";
                                                    }
                                                    if((srcOvertimeReport.getRequestDate() != null) && (srcOvertimeReport.getRequestDateTo() != null)) {
                                                            whereClauseEmpTime = whereClauseEmpTime + " AND "+whereClauseReq;
                                                    }
                                                    //update by satrya 2013-08-13
                                                //penambahan section
                                                if(srcOvertimeReport != null && srcOvertimeReport.getSectionId()!=0 ) {
                                                   whereClauseEmpTime = whereClauseEmpTime + " AND  sec."+ PstSection.fieldNames[PstSection.FLD_SECTION_ID] +
                                                          "="+srcOvertimeReport.getSectionId() ;
                                                }
                                                    listTime = PstOvertimeDetail.list3(0, 0, whereClauseEmpTime, "");
                                                }
                                                if(listEmp!=null && listTime!=null && listEmp.size()>0 && listTime.size()>0){ 
                                                   if(!showDivision){ // jika division belum di printout
                                                      showDivision = true; %>
                                                  <tr bgcolor="#00CCFF">
                                                       <td width="2%"  bgcolor="#666666">&nbsp;</td>
                                                      <td class="listgentitle" colspan="16" align="left"><%=div.getDivision()%></td>
                                                  </tr>
                                                <%}%>
                                                  <tr >
                                                    <td width="2%"  bgcolor="#666666">&nbsp;</td>
                                                      <td width="2%"  bgcolor="#666666">&nbsp;</td>
                                                    <td class="listgentitle" colspan="15" align="left"><%=dep.getDepartment()%></td>
                                                  </tr>
                                                  <!-- update by satrya 2013-08-13 penambahan section-->
                                                  <%
                                                 /* String sectionName="";
                                                    if(hashSection!=null && dep!=null && hashSection.get(dep.getOID())!=null){
                                                       try{
                                                        Section section = (Section)hashSection.get(dep.get);
                                                        sectionName = section.getSection();
                                                       }catch(Exception exc){
                                                           System.out.println("exc sec ot report"+exc);
                                                       }
                                                    }*/
                                                   %>
                                                   
                                                  <tr >
                                                    <td width="2%"  bgcolor="#666666">&nbsp;</td>
                                                    <td class="listgentitle" width="2%" align="center">No</td>
                                                    <td class="listgentitle" width="14%" align="center">Payroll Number</td>
                                                    <td class="listgentitle" width="16%" align="center">Name</td>
                                                    <td class="listgentitle" width="6%" align="center">Religion</td>
                                                    <td class="listgentitle" align="center" width="6%">Plan Start date/time</td>
                                                    <td class="listgentitle" align="center" width="6%">Plan End date/time</td>
                                                    <td class="listgentitle" align="center" width="6%">Plan Duration (h)</td>
                                                    <td class="listgentitle" align="center" width="6%">Manual Registration</td>
                                                    <td class="listgentitle" align="center" width="3%">Real Start</td>
                                                    <td class="listgentitle" align="center" width="3%">Real End </td>
                                                    <td class="listgentitle" align="center" width="4%">Rest (hour) </td>
                                                    <td class="listgentitle" align="center" width="6%">Real(Round) Duration </td>
                                                    <td class="listgentitle" align="center" width="5%">Ov. Idx / DP </td>
                                                    <td class="listgentitle" align="center" width="6%">Paid by</td>
                                                    <td class="listgentitle" align="center" width="6%">Allowance</td>
                                                    <td class="listgentitle" align="center" width="11%">Status</td>
                                                  </tr>
                                                  <!--Start loop Employee-->
                                                  <% 
                                                    totalPersonInDep = listEmp.size();
                                                    totalPersonDiv = totalPersonDiv + totalPersonInDep ;
                                                    totalPerson = totalPerson  + totalPersonInDep ;
                                                    
                                                     %>
                                                  <%for(int c=0;c<listEmp.size();c++){%> <!--count tiap department-->
                                                  <%Employee emp = (Employee) listEmp.get(c);
                                                    String religi = String.valueOf(emp.getReligionId());
                                                  %>
                                                  <%//where untuk list emptime @employee
                                                    whereClauseEmpTime=
                                                       /*" o."+PstOvertime.fieldNames[PstOvertime.FLD_STATUS_DOC]+"<>"+ I_DocStatus.DOCUMENT_STATUS_CANCELLED + " AND " +
                                                            " odt."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_STATUS]+"<>"+ I_DocStatus.DOCUMENT_STATUS_CANCELLED + " AND " +                                                                                                                       
 *                                                      */
                                                            " odt."+ PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID] +"="+emp.getOID();
                                                    if(whereReligion.length() > 0){
       ;                                             whereClauseEmpTime = whereClauseEmpTime + " AND ("+ whereReligion +") ";
                                                    }
                                             ;       if(whereClauseStat.length()>0){
                                                    whereClauseEmpTime = whereClauseEmpTime + " AND ("+ whereClauseStat +") ";
                                                    }
                                                    if((srcOvertimeReport.getRequestDate() != null) && (srcOvertimeReport.getRequestDateTo() != null)) {
                                                        whereClauseEmpTime = whereClauseEmpTime + " AND "+whereClauseReq;
                                                    }
                                             
                                                    Vector listEmpTime = PstOvertimeDetail.list3(0, 0, whereClauseEmpTime, "");
                                                  %>
                                                  <!--Start loop Employee Time-->
                                                  <%double totalEmp=0;
                                                    double realTotalEmp=0;
                                                    double totalEmpOvIdex =0;
                                                    Hashtable hashCekAccDate= new Hashtable();//cek apakah dp accusiion date sudah ada atau jika ada 2 accussionDate tpi nilainya berbeda maka akan di cek dlu
                                                    //Hashtable hashCekOvertimeDetail = new Hashtable();//cek berdasarkan jika tanggal sama tapi jam berbeda maka akan di insert ke DPnya;
                                                    %>
                                                  <%for(int d=0;d<listEmpTime.size();d++){%> <!--count tiap employee-->
                                                  <%OvertimeDetail empTime = (OvertimeDetail) listEmpTime.get(d);
                                                    //update by satrya 2012-12-20
                                                      
                                                      if(empTime.getOvertimeId()!=0){
                                                          overtime = PstOvertime.fetchExc(empTime.getOvertimeId());
                                                      }
                                                    if(iCommand==Command.SAVE || iSearchAndCalculate == Command.SAVE ){
                                                        
                                                        if(iSearchAndCalculate != Command.SAVE ){
                                                            double restTime = FRMQueryString.requestDouble(request, FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_REST_TIME_HR]+"_"+empTime.getOID());
                                                            //update by satrya 2014-01-24
                                                            int manualSetRestTimeHr = FRMQueryString.requestInt(request, FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_MANUAL_SET_REST_TIME]+"_"+empTime.getOID());    
                                                            int iPaidBy = FRMQueryString.requestInt(request, FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_PAID_BY]+"_"+empTime.getOID());    
                                                            int iAllowance = FRMQueryString.requestInt(request, FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_ALLOWANCE]+"_"+empTime.getOID());                                                            
                                                            empTime.setAllowance(iAllowance);
                                                            empTime.setRestTimeinHr(restTime);
                                                            empTime.setPaidBy(iPaidBy);
                                                            //update by satrya 2014-01-24
                                                            empTime.setManualSetRestTime(manualSetRestTimeHr); 
                                                          
                                                        }
                                                        
                                                        if ( gettime==1 || iSearchAndCalculate == Command.SAVE ){
                                                            //update by devin 2014-02-24;
                                                            //PstOvertimeDetail.setRealTime   (empTime, 1000*60*30, 1000*60*30, reloadOvertimeIndexMap, minOvertimeHour,overtime.getStatusDoc());
                                                            PstOvertimeDetail.setRealTime   (empTime, 1000*60*30, 1000*60*30, reloadOvertimeIndexMap, minOvertimeHour,overtime.getStatusDoc()); // normal early and late  = 30 menit
                                                        } 
                                                        
                                                            try{ if(empTime.getPaidBy()==OvertimeDetail.PAID_BY_SALARY){  
                                                                    SessOvertime.calcOvTmIndex(empTime, reloadOvertimeIndexMap, minOvertimeHour); 
                                                                    DpStockManagement dpStock = new DpStockManagement(); 
                                                                    dpStock.setEmployeeId(empTime.getEmployeeId());
                                                                    // kenapa di pakai empTime.getOID() supaya  spesific masing" overtime detail dp_stoc yg di generate,di karenakan jika memakai empTime.GetPeriodId , jika karyawan tersebut OT di period yg sama maka nantinya bisa terhapus
                                                                    dpStock.setLeavePeriodeId(empTime.getOID());
                                                                    dpStock.setDtOwningDate(empTime.getRealDateFrom());
                                                                    long oid =PstDpStockManagement.deleteByPeriodId(dpStock);
                                                                    //update by satrya 2012-12-20
                                                                    if(oid==-1){
                                                                        Errmsg = Errmsg +"<br>"+  "can't update paid by to salary, because the DP have been used "+empTime.getEmployee_num();
                                                                        empTime.setPaidBy(OvertimeDetail.PAID_BY_DAY_OFF);
                                                                    }
                                                                 }else{
                                                                     //empTime.setTot_Idx(0);// dibayar dengan day off
                                                                     SessOvertime.calcOvTmDayOff(empTime, reloadOvertimeIndexMap, minOvertimeHour); 
                                                                     //insert DP
                                                                     if((empTime.getStatus()==I_DocStatus.DOCUMENT_STATUS_PROCEED) 
                                                                             //if((empTime.getStatus()==I_DocStatus.DOCUMENT_STATUS_FINAL || empTime.getStatus()==I_DocStatus.DOCUMENT_STATUS_PROCEED) 
                                                                             && empTime.getRealDateFrom()!=null && empTime.getRealDateTo()!=null ){                                                                             
                                                                         DpStockManagement dpStock = new DpStockManagement(); 
                                                                         dpStock.setDtOwningDate(empTime.getRealDateFrom());
                                                                         dpStock.setDtExpiredDate(new Date(empTime.getRealDateFrom().getTime()+ ((30L*24L*60L*60L*1000L)*(long)leaveConfig.getDpValidity(leaveConfig.getStrLevels()[0]) )));
                                                                         dpStock.setDtStartDate(empTime.getRealDateFrom());
                                                                         dpStock.setEmployeeId(empTime.getEmployeeId());
                                                                         // kenapa di pakai empTime.getOID() supaya  spesific masing" overtime detail dp_stoc yg di generate,di karenakan jika memakai empTime.GetPeriodId , jika karyawan tersebut OT di period yg sama maka nantinya bisa terhapus
                                                                         dpStock.setLeavePeriodeId(empTime.getOID());                                                                       
                                                                         dpStock.setQtyResidue((float)(empTime.getTot_Idx()/8f )); //empTime.getNetDuration()/8f));
                                                                         dpStock.setiDpQty((float)(empTime.getTot_Idx()/8f)); // empTime.getNetDuration()/8f));
                                                                         dpStock.setQtyUsed(0f); 
                                                                         dpStock.setStNote("Dp generated from overtime by "+ userIsLogin.toLowerCase());
                                                                         dpStock.setToBeTaken(0f);
                                                                         //update by satrya 2013-02-24
                                                                         dpStock.setFlagStock(PstDpStockManagement.DP_FLAG_EDIT_NO); //artinya ini generate by OT
                                                                         if(empTime.getTot_Idx()>0){
                                                                             //update by satrya 2012-12-20
                                                                               if(empTime.getStatus()== I_DocStatus.DOCUMENT_STATUS_PROCEED){
                                                                                    PstDpStockManagement.insertOrUpdateByPeriodIdVer2(dpStock,hashCekAccDate);
                                                                                    //PstDpStockManagement.insertOrUpdateByPeriodId(dpStock);
                                                                               }
                                                                         }else{
                                                                           long oid = PstDpStockManagement.deleteByPeriodId(dpStock); 
                                                                            //update by satrya 2012-12-20
                                                                            /*if(oid==-1){
                                                                                msgStr = msgStr +"<br>"+ " can't update paid by to salary, because the DP have been used "+empTime.getEmployee_num();
                                                                                 empTime.setPaidBy(OvertimeDetail.PAID_BY_DAY_OFF);
                                                                            }*/
                                                                         }
                                                                     }
                                                                 }
                                                                    PstOvertimeDetail.updateExc(empTime);
                                                                    reloadOvertimeIndexMap = false;                                                                  
                                                                }catch(Exception exc){ 
                                                                    System.out.println(exc);                                                                       
                                                              }                                                            
                                                    }
                                                
                                                  long OvertimeDetailId = empTime.getOID();
                                                  
                                                  %>
                                                  <tr>
                                                    <%if(d==0){
                                                        Religion empReligion = null;
                                                        try{
                                                            if(emp.getReligionId()!=0){
                                                            empReligion = PstReligion.fetchExc(emp.getReligionId());
                                                            }
                                                        }
                                                        catch(Exception exc){
                                                        }
                                                        %>
                                                    <td width="2%"  bgcolor="#666666">&nbsp;</td>
                                                    <td class="listgensell" align="center"  rowspan="1<% //listEmpTime.size()%>"><%=c+1%></td><!--colspan sejumlah employee time-->
                                                    <td class="listgensell" rowspan="1<% //listEmpTime.size()%>"><%=emp.getEmployeeNum()%></td><!--colspan sejumlah employee time-->
                                                    <td class="listgensell" rowspan="1<% //listEmpTime.size()%>"><%=emp.getFullName()%></td><!--colspan sejumlah employee time-->
                                                    <td class="listgensell" rowspan="1<% //listEmpTime.size()%>"><%=(empReligion!=null? empReligion.getReligion():"")  %></td><!--colspan sejumlah employee time-->
                                                    <%} else {%>
                                                        <td width="2%"  bgcolor="#666666">&nbsp;</td>
                                                        <td class="listgensell">&nbsp;</td><!--colspan sejumlah employee time-->
                                                        <td class="listgensell">&nbsp;</td><!--colspan sejumlah employee time-->
                                                        <td class="listgensell">&nbsp;</td><!--colspan sejumlah employee time-->
                                                        <td class="listgensell">&nbsp;</td><!--colspan sejumlah employee time-->
                                                        
                                                    <%}
                                                    double durasi=Formater.formatDurationTime2(empTime.getDateFrom(), empTime.getDateTo(), true);%>
                                                    <!-- update by devin 2014-02-20  -->
                                                    <%double realDurasi=/*empTime.getStatus()==4 ?*/empTime.getNetDuration()/*:0*/;//(empTime.getRealDateFrom()!=null && empTime.getRealDateTo()!=null )? Formater.formatDurationTime2(empTime.getRealDateFrom(), empTime.getRealDateTo(), true):0.0;%>
                                                    <td class="listgensell" align="center" ><%=Formater.formatDate(empTime.getDateFrom(), "dd-MM-yyyy / HH:mm")%></td>
                                                    <td class="listgensell" align="center" ><%=Formater.formatDate(empTime.getDateTo(), "dd-MM-yyyy / HH:mm")%></td>
                                                    <td class="listgensell" align="center" ><%=Formater.formatNumber(durasi,"#,###.##")%><br><a href ="javascript:cmdEditPresenceOT('<%=empTime.getOID()+"','"+empTime.getEmployeeId()+"','"+empTime.getDateFrom().getTime()%>')">Edit</a></td>  
                                                    <td class="listgensell" align="center" ><a href ="javascript:cmdAddAttd('<%=empTime.getOID()+"','"+empTime.getEmployeeId()+"','"+empTime.getDateFrom().getTime()%>')">Add</a></td>  
                                                    <%if(empTime.getFlagStatus()==1){%>
                                                    <td width="6%" class="listgensell" align="center" ><blink><i><font color="#FF0000" > !! </font></i></blink> <%=empTime.getRealDateFrom()!=null? Formater.formatDate(empTime.getRealDateFrom(), "dd-MM-yyyy / HH:mm"):"" %></td>  
                                                    <td width="6%" class="listgensell" align="center" ><blink><i><font color="#FF0000"> !! </font></i></blink> <%=empTime.getRealDateTo()!=null ? Formater.formatDate(empTime.getRealDateTo(), "dd-MM-yyyy / HH:mm"):"" %></td>
                                                    <%}else if(empTime.getFlagStatus()==2){%>
                                                     <td width="6%" class="listgensell" align="center" ><blink><i><font color="#FF0000" > !!! </font></i></blink> <%=empTime.getRealDateFrom()!=null? Formater.formatDate(empTime.getRealDateFrom(), "dd-MM-yyyy / HH:mm"):"" %></td>  
                                                    <td width="6%" class="listgensell" align="center" ><blink><i><font color="#FF0000"> !!! </font></i></blink> <%=empTime.getRealDateTo()!=null ? Formater.formatDate(empTime.getRealDateTo(), "dd-MM-yyyy / HH:mm"):"" %></td>
                                                    <%}else{%> 
                                                  <!-- update by devin 2014-02-20  -->
                                                    <td class="listgensell" align="center" ><%=empTime.getRealDateFrom()!=null /*&& empTime.getStatus()==4*/ ? Formater.formatDate(empTime.getRealDateFrom(), "dd-MM-yyyy / HH:mm"):"" %></td>  
                                                    <td class="listgensell" align="center" ><%=empTime.getRealDateTo()!=null /*&& empTime.getStatus()==4 */ ? Formater.formatDate(empTime.getRealDateTo(), "dd-MM-yyyy / HH:mm"):"" %></td>
                                                    <%}%>
                                                    <td class="listgensell" align="left" >&nbsp;<input  name="<%=(FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_REST_TIME_HR]+"_"+empTime.getOID()) %>" type="text" maxlength="4" size="5" value="<%=empTime.getRestTimeinHr() %>">
                                                        <!-- update by satrya 2014-01-24-->
                                                        <input type="checkbox" name="<%=(FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_MANUAL_SET_REST_TIME]+"_"+empTime.getOID()) %>"   value="1" /> <a href="#"><b title="check if you want input manually  without calculation presence IN PERSONAL or OUT PERSONAL ">?</b></a>
                                                    </td>
                                                      <!-- update by devin 2014-02-20  -->
                                                    <td class="listgensell" align="center" ><%=/*empTime.getStatus()==4 ?*/    Formater.formatNumber(realDurasi,"#,###.##")%> <% if( SessOvertime.getOvertimeRoundTo()>0 && (realDurasi!=empTime.getRoundDuration()) && empTime.getStatus()>0 ){%>(<%=Formater.formatNumber(empTime.getRoundDuration() ,"#,###.##")%>) <%}%></td> 
                                                    <td class="listgensell" align="center" ><%= /*empTime.getStatus()==4 ?*/  Formater.formatNumber(empTime.getTot_Idx() ,"#,###.##") %></td>  
                                                   <td class="listgensell" align="center" >
                                                <%   if(loginByHRD){
                                                        out.println(ControlCombo.draw(FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_PAID_BY]+"_"+empTime.getOID(), "formElemen", null, "" + empTime.getPaidBy(), OvertimeDetail.getPaidByVal(), OvertimeDetail.getPaidByKey(), ""));                                                                                    
                                                       } else {
                                                         out.println(OvertimeDetail.paidByKey[empTime.getPaidBy()]); 
                                                         out.println("<input type=\"hidden\" name=\""+FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_PAID_BY]+"_"+empTime.getOID()+"\" value=\""+empTime.getPaidBy()+"\" />");
                                                       }
                                                    %>
                                                    </td>                                                    
                                                   <td class="listgensell" align="center" >
                                                <%    if(loginByHRD){                                                        
                                                        out.println(ControlCombo.draw(FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_ALLOWANCE]+"_"+empTime.getOID(), "formElemen", null, "" + empTime.getAllowance(), allw_value, allw_key, ""));                                                                                        
                                                       } else {
                                                         out.println(Overtime.allowanceType[empTime.getAllowance()]); 
                                                         out.println("<input type=\"hidden\" name=\""+FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_ALLOWANCE]+"_"+empTime.getOID()+"\" value=\""+empTime.getAllowance()+"\" />");
                                                       }
                                                    %>

                                                    </td>
                                                    <td class="listgensell" align="center" ><%=I_DocStatus.fieldDocumentStatus[empTime.getStatus()]%></td>
                                                  </tr>
                                                  <%
                                                  totalEmp=(totalEmp)+(durasi);
                                                  realTotalEmp=(realTotalEmp)+(realDurasi);                                                 
                                                  totalEmpOvIdex =  totalEmpOvIdex + empTime.getTot_Idx();                                                   
                                                  totalDep=(totalDep)+(durasi);
                                                  realTotalDep=(realTotalDep)+(realDurasi);
                                                  //update by devin 2014-02-20
                                                  realTotalIdx=(realTotalIdx)+( empTime.getTot_Idx());
                                                  
                                                  totalDiv=(totalDiv)+(durasi);
                                                  realTotalDiv=(realTotalDiv)+(realDurasi);
                                                  
                                                  GrandTotal = GrandTotal + durasi;
                                                  realGrandTotal = realGrandTotal + durasi; 
                                                  %>
                                                  <%}%>
                                                  <!--End loop Employee Time-->  
                                                  <tr >
                                                    <td width="2%" colspan="1" bgcolor="#666666">&nbsp;</td>                                                    
                                                    <td class="listgensell" colspan="4">&nbsp;</td>
                                                    <td class="tabtitle" colspan="2" align="center" >Total</td>
                                                    <td class="listgensell" align="center" 
                                                        style="font-weight: bold" ><%=Formater.formatNumber(totalEmp,"#,###.##")%></td>
                                                    <td  class="listgensell" >&nbsp;</td>
                                                    <td  class="listgensell">&nbsp;</td>
                                                    <td  class="listgensell" align="center" 
                                                        style="font-weight: bold">&nbsp;</td>
                                                     <td  class="listgensell">&nbsp;</td>
													 <td  class="listgensell" align="center" 
                                                        style="font-weight: bold"><%=Formater.formatNumber(realTotalEmp,"#,###.##")%></td>
                                                    <td  class="listgensell" align="center" 
                                                        style="font-weight: bold"><%=Formater.formatNumber(totalEmpOvIdex,"#,###.##")%> </td>
                                                    
                                                    <td  class="listgensell">&nbsp;</td>
                                                    <td  class="listgensell">&nbsp;</td>
                                                    <td  class="listgensell">&nbsp;</td>
                                                  </tr>
                                                  <%}
                                                   }
                                                   %>
                                                  <!--End loop Employee-->
                                                  <%if(totalPersonInDep>0){%>
                                                  <tr >
                                                    <td width="2%" bgcolor="#666666">&nbsp;</td>                                                    
                                                    <td class="tabtitle" colspan="3" align="center" >Total  Department</td>
                                                    <td class="tabtitle" >Employee</td>
                                                    <td class="tabtitle" ><%=totalPersonInDep %> Person</td>
                                                    <td class="tabtitle">Duration</td>
                                                    <td class="listgensell" align="center" style="font-weight: bold" >
							<%=Formater.formatNumber(totalDep,"#,###.##")%></td>
                                                    <td class="listgensell">&nbsp;</td>
                                                    <td class="listgensell">&nbsp;</td>
                                                    <td class="listgensell" align="center" style="font-weight: bold" >&nbsp;</td>
													  <td class="listgensell">&nbsp;</td>
                                                    <td class="listgensell" align="center" style="font-weight: bold" >
							<%=Formater.formatNumber(realTotalDep,"#,###.##")%></td>
                                                  <!-- update by devin 2014-02-20 -->
                                                  <td class="listgensell" align="center" style="font-weight: bold" >
							<%=Formater.formatNumber(realTotalIdx,"#,###.##")%></td>
                                                    <td class="listgensell">&nbsp;</td>
                                                    <td class="listgensell">&nbsp;</td>
                                                    <td class="listgensell">&nbsp;</td>
                                                     
                                                  </tr>
                                                  <%}
                                                  }%>                                                                                                    
                                                  <!--End Loop department-->
                                                  <% if(totalPersonDiv>0){ %>
                                                  <tr >                                                    
                                                    <td class="tabtitle" colspan="4" align="center" >
                                                    Total  Division</td>

                                                    <td class="tabtitle" >Employee</td>
                                                    <td class="tabtitle" ><%=totalPersonDiv %> Person</td>
                                                    <td class="tabtitle" >Duration</td>
                                                    <td class="listgensell" align="center" style="font-weight: bold" >&nbsp;<%=Formater.formatNumber(totalDiv,"#,###.##")%></td>
                                                    <td class="listgensell" >&nbsp;</td>
                                                    <td class="listgensell" >&nbsp;</td>
                                                    <td class="listgensell" >&nbsp;</td>
                                                    <td class="listgensell" >&nbsp;</td>
                                                    <td class="listgensell" >&nbsp;</td>
                                                    <td class="listgensell" >&nbsp;</td>
                                                    <td class="listgensell" >&nbsp;</td>
                                                    <td class="listgensell" >&nbsp;</td>
                                                    <td class="listgensell" >&nbsp;</td>
                                                  </tr> 
                                                  <%} 
                                                  }
                                                 %>
                                                  
                                                  <!--end loop division-->
                                                  <% if(totalPerson>0){ %>
                                                  <tr >
                                                    <td class="tabtitle" colspan="4" align="center" bgcolor="#66FF99">Grand Total </td>
                                                    <td class="tabtitle" >Employee</td>
                                                    <td class="tabtitle" ><%=totalPerson %> Person</td>
                                                    <td class="tabtitle" >Duration</td>
                                                    <td class="listgensell" align="center" style="font-weight: bold" >&nbsp;<%=Formater.formatNumber(GrandTotal,"#,###.##")%></td>
                                                    <td class="listgensell" >&nbsp;</td>
                                                    <td class="listgensell" >&nbsp;</td>
                                                    <td class="listgensell" >&nbsp;</td>
                                                    <td class="listgensell" >&nbsp;</td>
                                                    <td class="listgensell" >&nbsp;</td>
                                                    <td class="listgensell" >&nbsp;</td>
                                                    <td class="listgensell" >&nbsp;</td>
                                                    <td class="listgensell" >&nbsp;</td>
                                                     <td class="listgensell" >&nbsp;</td>
                                                  </tr> 
                                                  <%} %>
                                                </table>
                                                <!--End Tabel Report -->
							</td>
						</tr>                                                                                                    
						<tr>
					           <td width="100%" height="20">
							 <table width="100%" cellspacing="1" cellpadding="1">									
                                                            <tr>
                                                            <td><b class="listtitle">
                                                              &nbsp;
                                                                </b>                                                            </td>
                                                            <td><b class="listtitle">                                                          
                                                               <input name="gettime" type="checkbox" value="1" checked="true" > Get attendance time </b>                                                            </td>                                                        
						           </tr>
								   <tr>
								   	<td  colspan="2">
                                                                            <div align="center">
                                                                                <%=Errmsg + " , " +msgStr%>
                                                                             </div></td>   
									</tr>  
                                                        <tr>
							<td><b class="listtitle">  
                                                           &nbsp;<< <a href="javascript:cmdBack()" > Back to Search Overtime </a>   
                                                            </b>  </td>
							<td><b class="listtitle">                                                            
                                                           <a href="javascript:cmdSave();" >Save & Process </a>  </b>                                                        </td>
						           </tr>
							 </table>
						  </td>
						</tr>


					</table>
				</td>
			</tr
		></table>
                                                                                            </td>
                                                                                        </tr>
                                                                                    </table>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>&nbsp; </td>  
                                                                            </tr>  
                                                                        </table>  
                                                                    </form>  
                                                                    <!-- #EndEditable --> </td>  
                                                            </tr>  
                                                        </table>  
                                                    </td> 
                                                </tr> 
                                                <tr> 
                                                    <td >&nbsp;</td> 
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
                var oBody = document.body;
                var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
    </script>
    <!-- #EndEditable -->
    <!-- #EndTemplate --></html>