<%-- 
    Document   : list_lkpbu_803
    Created on : Aug 12, 2015, 11:56:42 AM
    Author     : khirayinnura
--%>
<%@page import="com.dimata.harisma.entity.report.lkpbu.PstLkpbu"%>
<%@page import="com.dimata.harisma.entity.report.lkpbu.Lkpbu"%>
<%@page import="com.dimata.harisma.form.employee.FrmEmployee"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>

<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>

<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>

<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK);%>
<%@ include file = "../../main/checkuser.jsp" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%!
public String drawList(Vector listEmployee, int year) 
{ 
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
                
        ctrlist.addHeader("Status Pegawai","2%", "0", "0");               
        ctrlist.addHeader("Jenis Usia","2%", "0", "0");
        ctrlist.addHeader("Jenis Jabatan","2%", "0", "0");
        ctrlist.addHeader("Jenis Pendidikan","2%", "0", "0");
        ctrlist.addHeader("Jenis Pekerjaan Berdasarkan Jenis Tenaga Kerja","5%", "0", "0");	
        ctrlist.addHeader("Jumlah Laki-laki","2%", "0", "0");
        ctrlist.addHeader("Jumlah Perempuan","2%", "0", "0");
        
        ctrlist.setLinkRow(-1);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();

        Vector rowx = new Vector(1,1);
        
        String codeStatus = "";
        String nameCode = "";
        String lvlCode = "";
        String eduCode = "";        
        String codeUsia = "";
        String codeJabatan = "";
        String codeEdu = "";
        int totalLaki = 0;
        int totalPerempuan = 0;
        int totalL = 0;
        int totalP = 0;
        
        for(int i = 0; i < listEmployee.size(); i++) {
            Lkpbu lkpbu = (Lkpbu)listEmployee.get(i);
            rowx = new Vector(1,1);
                        
            if(i == 0){
                codeStatus = lkpbu.getEmpCategoryNameCode();
                if(codeStatus == null){
                    codeStatus = "";
                }
                codeUsia = Lkpbu.getCodeUsia(lkpbu.getEmpBirthDate(), year);
                if(codeUsia == null){
                    codeUsia = "";
                }
                codeJabatan = lkpbu.getEmpLevelCode();
                if(codeJabatan == null){
                    codeJabatan = "";
                }
                codeEdu = lkpbu.getEmpEduCode();
                if(codeEdu == null){
                    codeEdu = "";
                }
            }
            
            nameCode = lkpbu.getEmpCategoryNameCode();
            lvlCode = lkpbu.getEmpLevelCode();
            eduCode = lkpbu.getEmpEduCode();
            if(nameCode == null){
                nameCode = "";
            }
            if(lvlCode == null){
                lvlCode = "";
            }
            if(eduCode == null){
                eduCode = "";
            }
            
            if( nameCode.equals(codeStatus) && lvlCode.equals(codeJabatan) && 
                    eduCode.equals(codeEdu)){
                 if(lkpbu.getEmpSex() == PstEmployee.MALE ){
                    totalLaki++;
                 }else{
                    totalPerempuan++; 
                 }
                 
                 if(i == ( listEmployee.size()-1) ){
                        rowx.add(""+codeStatus);
                        rowx.add(""+codeUsia);
                        rowx.add(""+codeJabatan);
                        int jml = codeEdu.length();
                        if(jml == 1){
                            rowx.add("0"+codeEdu);
                        } else {
                            rowx.add(""+codeEdu);
                        }
                        
                        rowx.add("");
                        rowx.add(""+totalLaki);
                        rowx.add(""+totalPerempuan);
                        
                        totalL = totalL + totalLaki;
                        totalP = totalP + totalPerempuan;

                        lstData.add(rowx);
                        lstLinkData.add("0");
                    }
                                                 
            } else {
                rowx.add(""+codeStatus);
                rowx.add(""+codeUsia);
                rowx.add(""+codeJabatan);
                int jml = codeEdu.length();
                if(jml == 1){
                    rowx.add("0"+codeEdu);
                } else {
                    rowx.add(""+codeEdu);
                }
                //rowx.add(""+codeEdu);
                rowx.add("");
                rowx.add(""+totalLaki);
                rowx.add(""+totalPerempuan);
               
                totalL = totalL + totalLaki;
                totalP = totalP + totalPerempuan;
                        
                codeStatus = lkpbu.getEmpCategoryNameCode();
                codeUsia = Lkpbu.getCodeUsia(lkpbu.getEmpBirthDate(), year);
                codeJabatan = lkpbu.getEmpLevelCode();
                codeEdu = lkpbu.getEmpEduCode();
                totalLaki = 0;
                totalPerempuan = 0;
                i--;
                                
                lstData.add(rowx);
                lstLinkData.add("0");
            }           
        }
        
        ctrlist.addFooter("Total&nbsp;&nbsp;", "0", "5");
        ctrlist.addFooter(String.valueOf(totalL), "0", "0");
        ctrlist.addFooter(String.valueOf(totalP), "0", "0");
     
        return ctrlist.drawListWithFooter();	
}
%>

<%
    int iCommand = FRMQueryString.requestCommand(request);
    int year = FRMQueryString.requestInt(request,"year");
    Lkpbu lkpbu = new Lkpbu();

    Vector vListResult = new Vector(1, 1);
    
    vListResult = PstLkpbu.listEmployeeJabatan(/* dedy_20150904 year*/);
    
    session.putValue("listresult", vListResult);
    
    Vector listKadiv= new Vector(1,1);
    //get value kadiv HRD
    String kadivPositionOid = PstSystemProperty.getValueByName("HR_DIR_POS_ID");
    String whereClauseOidPosition = "POSITION_ID='"+kadivPositionOid+"'";

    listKadiv = PstLkpbu.listPosition(whereClauseOidPosition);
    session.putValue("listkadiv", listKadiv);

%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HARISMA - LKPBU 803 Report</title>
        <script language="JavaScript">
            <%if (iCommand == Command.PRINT) {%>
                //com.dimata.harisma.report.listRequest	
                window.open("<%=printroot%>.report.listRequest.ListEmpEducationPdf");
            <%}%>

                function cmdAdd(){
                    document.frmemplkpbu.command.value="<%=Command.ADD%>";
                    document.frmemplkpbu.action="list_lkpbu_803.jsp";
                    document.frmemplkpbu.submit();
                }

                function reportPdf(){
                    document.frmemplkpbu.command.value="<%=Command.PRINT%>";
                    document.frmemplkpbu.action="list_lkpbu_803.jsp";
                    document.frmemplkpbu.submit();
                }

                function cmdSearch(){
                    document.frmemplkpbu.command.value="<%=Command.LIST%>";
                    document.frmemplkpbu.action="list_lkpbu_803.jsp";
                    document.frmemplkpbu.submit();
                }

                function cmdSpecialQuery(){
                    document.frmemplkpbu.action="specialquery.jsp";
                    document.frmemplkpbu.submit();
                }

                function fnTrapKD(){
                    if (event.keyCode == 13) {
                        document.all.aSearch.focus();
                        cmdSearch();
                    }
                }
                function cmdExportExcel(){
                 
                    var linkPage = "<%=approot%>/report/employee/export_excel/export_excel_list_lkpbu_803.jsp";    
                    var newWin = window.open(linkPage,"attdReportDaily","height=700,width=990,status=yes,toolbar=yes,menubar=no,resizable=yes,scrollbars=yes,location=yes"); 			
                     newWin.focus();
                }
                
                function cmdView()
                {
                        document.frmemplkpbu.command.value="<%=String.valueOf(Command.LIST)%>";
                        document.frmemplkpbu.action="list_lkpbu_803.jsp";
                        document.frmemplkpbu.submit();
                }

                function MM_swapImgRestore() { //v3.0
                    var i,x,a=document.MM_sr; for(i=0;a && i < a.length && (x=a[i]) && x.oSrc;i++) x.src=x.oSrc;
                }

                function MM_preloadImages() { //v3.0
                    var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
                        var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i < a.length; i++)
                            if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
                    }

                    function MM_findObj(n, d) { //v4.0
                        var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0 && parent.frames.length) {
                            d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
                        if(!(x=d[n]) && d.all) x=d.all[n]; for (i=0;!x && i < d.forms.length;i++) x=d.forms[i][n];
                        for(i=0;!x && d.layers && i < d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
                        if(!x && document.getElementById) x=document.getElementById(n); return x;
                    }

                    function MM_swapImage() { //v3.0
                        var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
                            if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
                    }

                    function cmdUpdateLevp(){
                        document.frmemplkpbu.command.value="<%=Command.ADD%>";
                        document.frmemplkpbu.action="list_lkpbu_803.jsp"; 
                        document.frmemplkpbu.submit();
                    }
        </script>
        <!-- #BeginEditable "styles" --> 
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "stylestab" --> 
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "headerscript" --> 
        <!-- #EndEditable -->
    </head>
    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
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
                                                Report &gt;Employee &gt; LKPBU Form 803 <!-- #EndEditable --> 
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
                                                                                <form name="frmemplkpbu" method="post" action="">
                                                                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                                                                    <table width="60%" border="0" cellspacing="2" cellpadding="2">
                                        <tr> 
                                          <td width="18%" nowrap> 
                                            <div align="left"></div>
                                          </td>
                                          <!-- dedy_20150904 -->
                                          <!--<td width="1%"><!--%=ControlDate.drawDateYear("year", year==0? Validator.getIntYear(new Date()) : year ,"formElemen", -40)%></td>-->
                                          <td width="3%">&nbsp;</td>
                                          <td width="78%"> 
                                            <table border="0" cellspacing="0" cellpadding="0" width="197">
                                              <tr> 
                                                <td width="24"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="View Report LKPBU 803"></a></td>
                                                <td width="10"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                <td width="163" class="command" nowrap><a href="javascript:cmdView()">View LKPBU 803 Report</a></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                      </table>									  
									  
									  
                                      <% 
                                      if(iCommand == Command.LIST && vListResult.size()>0)
                                      {
                                      %>
                                      <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                        <tr>
                                          <td><hr></td>
                                        </tr>
                                        <tr>
                                          <td><%=drawList(vListResult, year)%></td>
                                        </tr>
                                        <%
                                            if(vListResult.size()>0 && privPrint)
                                            {
                                        %>
                                        <tr>
                                          <td class="command">
                                            <table border="0" cellspacing="0" cellpadding="0" width="197">
                                              <tr>
                                                <td width="24"><a href="javascript:cmdExportExcel()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image110','','<%=approot%>/images/BtnNewOn.jpg',1)" id="aSearch"><img name="Image110" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print Report"></a></td>
                                                <td width="10"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                <td width="163" class="command" nowrap><a href="javascript:cmdExportExcel()">Export to Excel</a></td>
                                              </tr>
                                            </table></td>
                                        </tr>
                                        <%
                                            }
                                        %>
                                      </table>
                                      <%
                                          }                                         
                                      %>
                                                                                </form>
                                                                                <!-- #EndEditable --> </td>
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

