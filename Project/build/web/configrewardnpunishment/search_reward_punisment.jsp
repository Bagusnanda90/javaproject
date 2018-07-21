
<%@page import="com.dimata.harisma.entity.jenisSo.JenisSo"%>
<%@page import="com.dimata.harisma.entity.jenisSo.JenisSo"%>
<%@page import="com.dimata.harisma.entity.jenisSo.PstJenisSo"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.Location"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.Location"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.PstLocation"%>
<%@page import="com.dimata.harisma.form.search.FrmSrcSpecialEmployeeQuery"%>
<%@page import="com.dimata.harisma.entity.configrewardnpunisment.SrcRewardPunishment"%>
<%@page import="com.dimata.harisma.form.configrewardnpunisment.FrmSrcRewardPunishment"%>
<%@page import="com.dimata.harisma.form.configrewardnpunisment.FrmRewardPunismentMain"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@ page language="java" %>  

<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ page language="java" %>

<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->

<%@ include file = "../main/javainit.jsp" %> 
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMPLOYEE, AppObjInfo.OBJ_EMPLOYEE_ENTRI_OPNAME_SALES);%>
<%@ include file = "../main/checkuser.jsp" %>

<%
    long hrdDepOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    boolean isHRDLogin = hrdDepOid == departmentOid ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
%>

<%
    int iCommand = FRMQueryString.requestCommand(request);

//update by priska 2014-12-1
SrcRewardPunishment srcRewardPunishment = new SrcRewardPunishment();
FrmSrcRewardPunishment frmSrcRewardPunishment= new FrmSrcRewardPunishment();

if(iCommand==Command.GOTO){
    //update by priska 2014-12-1
    frmSrcRewardPunishment = new FrmSrcRewardPunishment(request, srcRewardPunishment);
    frmSrcRewardPunishment.requestEntityObject(srcRewardPunishment);
}

if(iCommand==Command.BACK)
{
        //update by priska 2014-12-1
         frmSrcRewardPunishment = new FrmSrcRewardPunishment(request, srcRewardPunishment); 
	try
	{
		if(srcRewardPunishment == null) 
		{
			srcRewardPunishment = new SrcRewardPunishment();
		}
		
	}
	catch (Exception e)
	{
            ///update by satrya 2013-08-05
            srcRewardPunishment = new SrcRewardPunishment();
	}
}
%>


<!-- JSP Block -->
<!-- End of JSP Block -->
<html>
    <!-- #BeginTemplate "/Templates/main.dwt" --> 
    <head>
        <!-- #BeginEditable "doctitle" --> 
        <title>HARISMA - Reward & Punisment Search</title>
        <!-- #EndEditable --> 
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <!-- #BeginEditable "styles" --> 
        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
        <link rel="stylesheet" href="../styles/tab.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
        <SCRIPT language=JavaScript>
           
            function cmdChangeLoc(){
                document.<%=FrmRewardPunismentMain.FRM_REWARD_PUNISMENT_MAIN%>.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.<%=FrmRewardPunismentMain.FRM_REWARD_PUNISMENT_MAIN%>.action="search_entriopnamesales.jsp";
                document.<%=FrmRewardPunismentMain.FRM_REWARD_PUNISMENT_MAIN%>.submit();
            }
            
            function cmdSearch(){
                document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.command.value="<%=Command.LIST%>";
                document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.action="search_reward_punisment_list.jsp";
                document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.submit();
            }
          
        function cmdUpdateDiv(){
                document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.action="search_reward_punisment.jsp";
                document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.submit();
            }

        function cmdUpdateDep(){
                document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.action="search_reward_punisment.jsp"; 
                document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.submit();
            }
        function cmdUpdatePos(){
                document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.action="search_reward_punisment.jsp"; 
                document.<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>.submit();
            }
            function fnTrapKD(){
                if (event.keyCode == 13) {
		document.all.aSearch.focus();
		cmdSearch();
            }
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
            
            function showObjectForMenu(){
                
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
        </SCRIPT>
        <!-- #EndEditable --> 
    </head>
    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">     
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>    
            <%@include file="../styletemplate/template_header.jsp" %>
            <%} else {%>
            <tr>  
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
                    <!-- #BeginEditable "header" --> 
                    <%@ include file = "../main/header.jsp" %>
                    <!-- #EndEditable --> </td>
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
                                        <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Reward & Punisment Search<!-- #EndEditable --> </strong></font> </td>
                                    </tr>
                                    <tr> 
                                        <td height="20"> </td>
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
                                                                        <!-- #BeginEditable "content" --> 
                                                                        <tr> 
                                                                            <td valign="top"> 
                                                                                <form name="<%=FrmSrcRewardPunishment.FRM_SRC_REWARD_PUNISHMENT%>" method="post" action="">
                                                                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                                                                    <table width="100%" border="0">
                                                                                        <tr>
                                                                                            <td>
                                                                                                <table width="100%">
                                                                                                    <tr>
                                                                                                        <td width="8%" nowrap="nowrap">Full Name</td>
                                                                                                        <td width="1%">:</td>
                                                                                                        <td width="96%"> <input type="text" name="<%=frmSrcRewardPunishment.fieldNames[FrmSrcRewardPunishment.FRM_FLD_FULL_NAME_EMPLOYEE] %>"  value="<%= srcRewardPunishment.getFullNameEmp()%>" class="elemenForm" size="40" onkeydown="javascript:fnTrapKD()"> </td>
                                                                                                        <tr>
                                                                                                        <td width="8%" nowrap="nowrap">Employee Number</td>
                                                                                                        <td width="1%">:</td>
                                                                                                        <td width="96%"> <input type="text" name="<%=frmSrcRewardPunishment.fieldNames[FrmSrcRewardPunishment.FRM_FLD_EMPLOYEE_NUMBER] %>"  value="<%= srcRewardPunishment.getEmpnumber()%>" class="elemenForm" size="40" onkeydown="javascript:fnTrapKD()"> </td>
                                                                                                        </tr>
                                                                                                     </tr>
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <table width="100%">
                                                                                                    <tr>
                                                                                                        <td width="8%" nowrap="nowrap">Company</td>
                                                                                                        <td width="1%">:</td>
                                                                                                        <td width="96%"><%
                                                                                                        Vector comp_value = new Vector(1,1);
                                                                                                        Vector comp_key = new Vector(1,1);
                                                                                                        comp_value.add("0");
                                                                                                        comp_key.add("select ...");
                                                                                                        Vector listComp = PstCompany.list(0, 0, "", " COMPANY ");
                                                                                                        for (int i = 0; i < listComp.size(); i++) {
                                                                                                            Company comp = (Company) listComp.get(i);
                                                                                                            comp_key.add(comp.getCompany());
                                                                                                            comp_value.add(String.valueOf(comp.getOID()));
                                                                                                        }
                                                                                                        String companyId[] = srcRewardPunishment.getArrCompany(0);
                                                                                                        long lCompanyId = companyId!=null && companyId[0]!=null && companyId[0].length()>0 ? Long.parseLong(companyId[0]):0; 
                                                                                                        %> <%= ControlCombo.draw(frmSrcRewardPunishment.fieldNames[FrmSrcRewardPunishment.FRM_FLD_COMPANY],"formElemen",null, ""+(lCompanyId) , comp_value, comp_key, "onChange=\"javascript:cmdUpdateDiv()\"") %> 
                                                            
                                                            
                                                                                                        </td>
                                                                                                        <tr>
                                                                                                        <td width="8%" nowrap="nowrap"><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></td>
                                                                                                        <td width="1%">:</td>
                                                                                                          <td width="96%"> <%
                                                                                                        Vector div_value = new Vector(1,1);
                                                                                                        Vector div_key = new Vector(1,1);
                                                                                                        div_value.add("0");
                                                                                                        div_key.add("select ...");
                                                                                                        Vector listDiv = PstDivision.list(0, 0, "", " DIVISION ");
                                                                                                        for (int i = 0; i < listDiv.size(); i++) {
                                                                                                                Division div = (Division) listDiv.get(i);
                                                                                                                div_key.add(div.getDivision());
                                                                                                                div_value.add(String.valueOf(div.getOID()));
                                                                                                        }

                                                                                                        String divisionId[] = srcRewardPunishment.getArrDivision(0);
                                                                                                        long lDivisionId = divisionId!=null && divisionId[0]!=null && divisionId[0].length()>0 ? Long.parseLong(divisionId[0]):0; 
                                                                                                        %> <%= ControlCombo.draw(frmSrcRewardPunishment.fieldNames[FrmSrcRewardPunishment.FRM_FLD_DIVISION],"formElemen",null, ""+(lDivisionId), div_value, div_key, "onChange=\"javascript:cmdUpdateDep()\"") %> </td>
                                                                                                        </tr>
                                                                                                    </tr>
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <table width="100%">
                                                                                                    <tr>
                                                                                                        <td width="8%" nowrap="nowrap"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                                                                                        <td width="1%">:</td>
                                                                                                        <td width="96%"> <% 
                                                                                                        Vector dept_value = new Vector(1,1);
                                                                                                        Vector dept_key = new Vector(1,1);        
                                                                                                        dept_value.add("0");
                                                                                                        dept_key.add("select ...");
                                                                                                        String strWhere = PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID]+"="+lDivisionId;//oidDivision
                                                                                                        Vector listDept = PstDepartment.list(0, 0, strWhere,PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);                                                        
                                                                                                        for (int i = 0; i < listDept.size(); i++) {
                                                                                                            Department dept = (Department) listDept.get(i);
                                                                                                            dept_key.add(dept.getDepartment());
                                                                                                            dept_value.add(String.valueOf(dept.getOID()));
                                                                                                        }
                                                                                                        String departementId[] = srcRewardPunishment.getArrDepartmentId(0);
                                                                                                        long lDepartment = departementId!=null && departementId[0]!=null && departementId[0].length()>0 ? Long.parseLong(departementId[0]):0;
                                                                                                        %> <%= ControlCombo.draw(frmSrcRewardPunishment.fieldNames[FrmSrcRewardPunishment.FRM_FLD_DEPARTMENT],"formElemen",null, ""+lDepartment, dept_value, dept_key, "onChange=\"javascript:cmdUpdatePos()\"") %>  
                                                                                                        </td>
                                                                                                        <tr>
                                                                                                         <td width="8%" nowrap="nowrap"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></td>
                                                                                                        <td width="1%">:</td>
                                                                                                        <td width="96%"><% 
                                                                                                        Vector sec_value = new Vector(1,1);
                                                                                                        Vector sec_key = new Vector(1,1);
                                                                                                        sec_value.add("0");
                                                                                                        sec_key.add("select ...");
                                                                                                        String strWhereSec = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+"="+lDepartment;//oidDepartment
                                                                                                        Vector listSec = PstSection.list(0, 0, strWhereSec, " SECTION ");
                                                                                                        for (int i = 0; i < listSec.size(); i++) {
                                                                                                        Section sec = (Section) listSec.get(i);
                                                                                                        sec_key.add(sec.getSection());
                                                                                                        sec_value.add(String.valueOf(sec.getOID()));
                                                                                                        }
                                                                                                        String sectionId[] = srcRewardPunishment.getArrSectionId(0);
                                                                                                        long lSectionId = sectionId!=null && sectionId[0]!=null && sectionId[0].length()>0 ? Long.parseLong(sectionId[0]):0;
                                                                                                        %> <%= ControlCombo.draw(frmSrcRewardPunishment.fieldNames[FrmSrcRewardPunishment.FRM_FLD_SECTION],"formElemen",null, "" +lSectionId , sec_value, sec_key, " onkeydown=\"javascript:fnTrapKD()\"") %>
                                                                                                        </td>
									
                                                                                                        </tr>
                                                                                                     </tr>
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <table width="100%">
                                                                                                    <tr>
                                                                                                        <td width="8%" nowrap="nowrap">Location</td>
                                                                                                        <td width="1%">:</td>
                                                                                                        <td width="96%"><% 
                                                                                                        Vector loc_value = new Vector(1,1);
                                                                                                        Vector loc_key = new Vector(1,1);
                                                                                                        loc_value.add("0");
                                                                                                        loc_key.add("select ");                                                            
                                                                                                        Vector listLoc = PstLocation.list(0, 0, "", " NAME ");                                                            
                                                                                                        for (int i = 0; i < listLoc.size(); i++) {
                                                                                                                Location loc = (Location) listLoc.get(i);
                                                                                                                loc_key.add(loc.getName());
                                                                                                                loc_value.add(String.valueOf(loc.getOID()));
                                                                                                                }
                                                                                                        String locationId[] = srcRewardPunishment.getArrLocationId(0);
                                                                                                        long lLocationId = locationId!=null && locationId[0]!=null && locationId[0].length()>0 ? Long.parseLong(locationId[0]):0;
                                                                                                        %> <%= ControlCombo.draw(frmSrcRewardPunishment.fieldNames[FrmSrcRewardPunishment.FRM_FLD_LOCATION],"formElemen",null, "" +lLocationId , loc_value, loc_key, " onkeydown=\"javascript:fnTrapKD()\"") %> 
                                                                                                    </td>
                                                                                                    </tr>
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <table width="100%">
                                                                                                    <tr>
                                                                                                        <td width="8%" nowrap="nowrap">Jenis SO</td>
                                                                                                        <td width="1%">:</td>
                                                                                                        <td width="96%"><%
                                                                                                            Vector valueJenisSo = new Vector();
                                                                                                            Vector keyJenisSo = new Vector();
                                                                                                            valueJenisSo.add("" + 0);
                                                                                                            keyJenisSo.add("select");
                                                                                                            Vector vJenisSo = PstJenisSo.list(0, 0, "", "");
                                                                                                            if (vJenisSo != null && vJenisSo.size() > 0) {
                                                                                                            for (int x = 0; x < vJenisSo.size(); x++) {
                                                                                                            JenisSo jenisSo = (JenisSo) vJenisSo.get(x);
                                                                                                            valueJenisSo.add("" + jenisSo.getOID());
                                                                                                            keyJenisSo.add("" + jenisSo.getNamaSo());
                                                                                                            }
                                                                                                            }
                                                                                                            String soId[] = srcRewardPunishment.getArrJenisSo(0);
                                                                                                            long lSoId = soId!=null && soId[0]!=null && soId[0].length()>0 ? Long.parseLong(soId[0]):0;
                                                                                                            %>
                                                                                                            <%=ControlCombo.draw(FrmSrcRewardPunishment.fieldNames[FrmSrcRewardPunishment.FRM_FLD_JENIS_SO], "formElemen", null, String.valueOf(srcRewardPunishment.getArrJenisSo(0)), valueJenisSo, keyJenisSo, "")%>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <table width="100%">
                                                                                                    <tr>
                                                                                                        <%
                                                                                                        String checkedEd="";
                                                                                                        String dflChekedEd="checked";
                                                                                                         if(srcRewardPunishment.getRadioperiode()!=0){
                                                                                                        checkedEd="checked";
                                                                                                        dflChekedEd="";
                                                                                                        }
                                                                                                        %>
                                                                                                        <td width="8%" nowrap="nowrap">Periode</td>
                                                                                                        <td width="1%">:</td>
                                                                                                        <td width="96%"><input type="radio" name="<%=FrmSrcRewardPunishment.fieldNames[FrmSrcRewardPunishment.FRM_FLD_RADIO_PERIODE]%>" value="0" <%=checkedEd.length()>0?checkedEd:dflChekedEd%>>
                                                                                                        All date</td>
                                                                                                    </tr>
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>
                                                                                        
                                                                                        <tr>
                                                                                            <td>
                                                                                                <table width="100%">
                                                                                                    <tr>
                                                                                                        <td width="8%" nowrap="nowrap"></td>
                                                                                                        <td width="1%">:</td>
                                                                                                        <td width="96%"><input type="radio" name="<%=FrmSrcRewardPunishment.fieldNames[FrmSrcRewardPunishment.FRM_FLD_RADIO_PERIODE]%>" value="1" <%=checkedEd%>>
                                                                                                        <%=ControlDate.drawDateWithStyle(frmSrcRewardPunishment.fieldNames[FrmSrcRewardPunishment.FRM_FLD_PERIODEFROM], srcRewardPunishment.getDtPeriodFrom(0), 0, 30,"formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%> &nbsp;to&nbsp; 
                                                                                                        <%=ControlDate.drawDateWithStyle(frmSrcRewardPunishment.fieldNames[FrmSrcRewardPunishment.FRM_FLD_PERIODETO], srcRewardPunishment.getDtPeriodTo(0), 0, 30,"formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%>;
                                                                                                       
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>
                                                                                        
                                                                                        <tr>
                                                                                            <td>
                                                                                                <table width="100%">
                                                                                                    <tr>
                                                                                                        <%
                                                                                                        String checkedSo="";
                                                                                                        String dflChekedSo="checkedso";
                                                                                                         if(srcRewardPunishment.getRadiostatusso()!=0){
                                                                                                        checkedSo="checkedso";
                                                                                                        dflChekedSo="";
                                                                                                        }
                                                                                                        %>
                                                                                                        <td width="8%" nowrap="nowrap">Status SO</td>
                                                                                                        <td width="1%">:</td>
                                                                                                        <td width="96%">
                                                                                                            <input type="radio" name="<%=frmSrcRewardPunishment.fieldNames[FrmSrcRewardPunishment.FRM_FLD_STATUS_SO]%>" value="0" <%=checkedSo.length()>0?checkedSo:dflChekedSo%>> all
                                                                                                            <input type="radio" name="<%=frmSrcRewardPunishment.fieldNames[FrmSrcRewardPunishment.FRM_FLD_STATUS_SO]%>" value="1" <%=checkedSo%>> Reward
                                                                                                            <input type="radio" name="<%=frmSrcRewardPunishment.fieldNames[FrmSrcRewardPunishment.FRM_FLD_STATUS_SO]%>" value="2" <%=checkedSo%>> Punishment
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <table width="100%">
                                                                                                    <tr>
                                                                                                        <td width="8%" nowrap="nowrap">Order By</td>
                                                                                                        <td width="83%"> <%= ControlCombo.draw(frmSrcRewardPunishment.fieldNames[FrmSrcRewardPunishment.FRM_FLD_ORDER_BY],"formElemen",null, ""+srcRewardPunishment.getSort(0), FrmSrcRewardPunishment.getOrderValue(), FrmSrcRewardPunishment.getOrderKey(), " onkeydown=\"javascript:fnTrapKD()\"") %></td> 
                                                    
                                                                                                        <td></td>
                                                                                                    </tr>
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td>
                                                                                                <table width="100%">
                                                                                                    <tr>
                                                                                                        <td width="8%" nowrap="nowrap"></td>
                                                                                                        <td width="1%"></td>
                                                                                                        <td width="95%"></td>
                                                                                                    </tr>
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>
                                                                                    </table>
                                                                                    <table width="100%">
                                                                                        <tr>
                                                                                            <td width="150"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnSearchOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search"></a>
                                                                                            <a href="javascript:cmdSearch()" class="command">Search</a> 
                                                                                            </td>
                                                                                        </tr>
                                                                                    </table>
                                                                                </form> 
                                                                                    
                                                                            </td> 
                                                                        </tr>
                                                                        <tr> 
                                                                            <td>&nbsp; </td>
                                                                        </tr>
                                                                    <!-- #EndEditable -->      </table>
                                                                              
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
                                            <%@include file="../footer.jsp" %>
                                        </td>

                                    </tr>
                                    <%} else {%>
                                    <tr> 
                                        <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
                                            <%@ include file = "../main/footer.jsp" %>
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
