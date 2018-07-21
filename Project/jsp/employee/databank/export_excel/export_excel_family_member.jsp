<%-- 
    Document   : familymember_template
    Created on : Aug 7, 2015, 5:54:25 PM
    Author     : khirayinnura
--%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
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
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../../../main/javainit.jsp" %>

<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK);%>
<%@ include file = "../../../main/checkuser.jsp" %>
<%
            /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>

<!DOCTYPE html>

<%

            int iCommand = FRMQueryString.requestCommand(request);
            int start = FRMQueryString.requestInt(request, "start");
            int prevCommand = FRMQueryString.requestInt(request, "prev_command");
            long oidFamilyMember = FRMQueryString.requestLong(request, "family_member_oid");
            long oidEmployee=0;

//variable declaration
            int recordToGet = 30;
            String msgString = "";
            int iErrCode = FRMMessage.NONE;
           
            CtrlFamilyMember ctrlFamilyMember = new CtrlFamilyMember(request);
            ControlLine ctrLine = new ControlLine();
            Vector listFamilyMember = new Vector(1, 1);

            FamilyMember familyMember = ctrlFamilyMember.getFamilyMember();
            FrmFamilyMember frmFamilyMember = ctrlFamilyMember.getForm();

            Vector vctMember = new Vector(1, 1);
           
            
            if(session.getValue("familymember")!=null){
                vctMember = (Vector)session.getValue("familymember"); 
                //rekapitulasiAbsensi.setWhereClauseEMployee("");
            }
            if(session.getValue("oidemployee")!=null){
                oidEmployee = (Long)session.getValue("oidemployee"); 
                //rekapitulasiAbsensi.setWhereClauseEMployee("");
            }

//switch statement 
            //iErrCode = ctrlFamilyMember.action(iCommand, oidEmployee, vctMember);//oidFamilyMember, oidEmployee);
// end switch
            FrmFamilyMember FrmFamilyMember = ctrlFamilyMember.getForm();

            msgString = ctrlFamilyMember.getMessage();

            //vctMember = PstFamilyMember.list(0, 0, whereClause, orderClause);
%>

<%@page contentType="application/x-msexcel" pageEncoding="UTF-8"%>

<html>
    <head>
        <title></title>
        <!-- #EndEditable -->
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <!-- #BeginEditable "styles" -->
        <link rel="stylesheet" href="../../../styles/main.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "stylestab" -->
        <link rel="stylesheet" href="../../../styles/tab.css" type="text/css">
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
        <style type="text/css">
            
            .tblStyle {border-collapse: collapse;font-size: 11px;}
            .tblStyle td {padding: 3px 5px; border: 1px solid #CCC; background-color: #F7F7F7;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757; }
            body {color:#373737;}
            #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3;}
            #menu_title {color:#0099FF; font-size: 14px; font-weight: bold;}
            #menu_teks {color:#CCC;}
            #box_title {padding:9px; background-color: #D5D5D5; font-weight: bold; color:#575757; margin-bottom: 7px; }
            #btn {
              background: #3498db;
              border: 1px solid #0066CC;
              border-radius: 3px;
              font-family: Arial;
              color: #ffffff;
              font-size: 12px;
              padding: 3px 9px 3px 9px;
            }

            #btn:hover {
              background: #3cb0fd;
              border: 1px solid #3498db;
            }
            .breadcrumb {
                background-color: #EEE;
                color:#0099FF;
                padding: 7px 9px;
            }
            .navbar {
                background-color: #0084ff;
                padding: 7px 9px;
                color : #FFF;
                border-top:1px solid #0084ff;
                border-bottom: 1px solid #0084ff;
            }
            .navbar ul {
                list-style-type: none;
                margin: 0;
                padding: 0;
            }

            .navbar li {
                padding: 7px 9px;
                display: inline;
                cursor: pointer;
            }

            .navbar li:hover {
                background-color: #0b71d0;
                border-bottom: 1px solid #033a6d;
            }

            .active {
                background-color: #0b71d0;
                border-bottom: 1px solid #033a6d;
            }
            .title_part {color:#FF7E00; background-color: #F7F7F7; border-left: 1px solid #0099FF; padding: 9px 11px;}
        </style>
    </head>
    <body>
        <br>
        <center><h5>DAFTAR RINCIAN PIHAK TERKAIT<br>Posisi : Juli 2015</h5></center>
        <br>
        <table border="1" width="100%">
            <tr valign="top" style="text-align: center; font-size: 12px"> <!--font 9 on excel-->
                <td rowspan="3">
                    No.
                </td>
                <td rowspan="3">
                    NAMA PIHAK TERKAIT
                </td>
                <td colspan="4">
                    HUBUNGAN KEPEMILIKAN SAHAM
                </td>
                <td colspan="5">
                    HUBUNGAN KEPENGURUSAN
                </td>
                <td colspan="2">
                    HUBUNGAN KELUARGA
                </td>
                <td width="100">
                    HUBUNGAN KEUANGAN
                </td>
            </tr>
            <tr valign="top" style=" text-align: center;font-size: 12px">
                <td rowspan="2" width="50">
                    Pada Bank BPD Bali %
                </td>
                <td colspan="3">
                    Pada Perusahaan Lainnya
                </td>
                <td colspan="2">
                    Jabatan Pada Bank BPD Bali
                </td>
                <td colspan="3">
                    Jabatan Pada Perusahaan Lainnya
                </td>
                <td rowspan="2" width="200">
                    Nama Keluarga
                </td>
                <td rowspan="2" width="200">
                    Status*)
                </td>
                <td rowspan="2">
                    Pada Pihak Lain & Pihak Penjamin
                </td>
            </tr>
            <tr valign="top" style=" text-align: center;font-size: 12px">
                <td>
                    Nama Perusahaan
                </td>
                <td>
                    Sektor Usaha
                </td>
                <td>
                    %
                </td>
                <td>
                    Jabatan
                </td>
                <td>
                    Sejak
                </td>
                <td>
                    Jabatan
                </td>
                <td>
                    Nama Perusahaan
                </td>
                <td>
                    Sektor Usaha
                </td>
            </tr>
            <%if (oidEmployee != 0) {
                Employee employee = new Employee();
                try {
                    employee = PstEmployee.fetchExc(oidEmployee);
                } catch (Exception exc) {
                    employee = new Employee();
                }
                Vector listPos = PstPosition.list(0, 0, "POSITION_ID="+employee.getPositionId(), "");
                Position pos = (Position) listPos.get(0);
                
                try{
                    Vector listExperience = PstExperience.list(0, 0, "EMPLOYEE_ID="+employee.getOID(), "");
                    Experience exper = (Experience) listExperience.get(0);
                } catch(Exception exc) {
                    
                }

            %>
            <tr style=" font-size: 13px">
                <td>1</td>
                <td><%=employee.getFullName()%></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td><%=pos.getPosition()%></td>
                <td><%=Formater.formatDate(employee.getCommencingDate(),"dd MMMM yyyy")%></td>
                <%
                    try{
                        Vector listExperience = PstExperience.list(0, 0, "EMPLOYEE_ID="+employee.getOID(), "");
                        Experience exper = (Experience) listExperience.get(0);
                    
                %>
                <td><%=exper.getPosition()%></td>
                <td><%=exper.getCompanyName()%></td>
                <%
                    } catch(Exception exc) {%>
                        <td></td>
                        <td></td>
                    <%}
                %>
                <td></td>
                <td colspan="2">
                    <table border="1" width="100%">
                        <% for (int idx=0; idx < vctMember.size(); idx++ ){ 
                            FamilyMember famX = new FamilyMember();
                            if (vctMember != null && vctMember.size() > 0 && vctMember.size() > idx) {
                                famX = (FamilyMember) vctMember.get(idx);
                            }
                            
                            Vector listRelationX =PstFamRelation.listRelationName(0,0,famX.getRelationship(),""); 
                            FamRelation famRelation =(FamRelation) listRelationX.get(0);
                            String relation = famRelation.getFamRelation();
                            char huruf = relation.charAt(0);
                            
                            if(huruf == 'a' && famX.getSex() == 0){
                                relation = "Ayah Kandung";
                            } else if(huruf == 'a' && famX.getSex() == 1){
                                relation = "Ibu Kandung";
                            } else if(huruf == 'b'){
                                relation = "Saudara Kandung";
                            } else if(huruf == 'c' && famX.getSex() == 0){
                                relation = "Suami";
                            } else if(huruf == 'c' && famX.getSex() == 1){
                                relation = "Istri";
                            } else if(huruf == 'd' && famX.getSex() == 0){
                                relation = "Mertua Laki-Laki";
                            } else if(huruf == 'd' && famX.getSex() == 1){
                                relation = "Mertua Perempuan";
                            } else if(huruf == 'e'){
                                relation = "Anak Kandung";
                            } else if(huruf == 'f' && famX.getSex() == 0){
                                relation = "Kakek Kandung";
                            } else if(huruf == 'f' && famX.getSex() == 1){
                                relation = "Nenek Kandung";
                            } else if(huruf == 'g'){
                                relation = "Cucu Kandung";
                            } else if(huruf == 'h'){
                                relation = "Saudara Kandung dari Orang Tua";
                            } else if(huruf == 'i' && famX.getSex() == 0){
                                relation = "Suami Dari Anak";
                            } else if(huruf == 'i' && famX.getSex() == 1){
                                relation = "Istri  Dari Anak";
                            } else if(huruf == 'k' && famX.getSex() == 0){
                                relation = "Suami Dari cucu";
                            } else if(huruf == 'k' && famX.getSex() == 1){
                                relation = "Istri  Dari cucu";
                            } else if(huruf == 'l' && famX.getSex() == 0){
                                relation = "Ipar Laki-Laki";
                            } else if(huruf == 'l' && famX.getSex() == 1){
                                relation = "Ipar Perempuan";
                            } else{

                            }
                        %>
                        <tr style=" font-size: 13px">
                            <td width="200"><%=huruf%>. <%= famX.getFullName()%></td>
                            <td width="200"><%= relation%></td>
                        </tr>
                        <%}%>
                    </table>
                </td>
                <td></td>
            </tr>
            <%}%>
        </table> 
        <p style=" font-size: 13px">
        Keterangan *) :	<br>
        a   = Orang Tua Kandung/ Tiri/ Angkat<br>
        b   = Saudara Kandung/ Tiri/ Angkat<br>
        c   = Suami atau istri<br>
        d   = Mertua atau Besan<br>
        e   = Anak Kandung/ Tiri/ Angkat<br>
        f   = Kakek atau Nenek Kandung/ Tiri/ Angkat<br>
        g   = Cucu Kandung/ Tiri/ Angkat<br>
        h   = Saudara Kandung/ Tiri/ Angkat dari Orang Tua<br>
        i   = Suami atau Istri dari Anak Kandung/ Tiri/ Angkat<br>
        j   = Kakek atau Nenek dari Suami atau Istri<br>
        k   = Suami atau Istri dari Cucu Kandung/ Tiri/ Angkat<br>
        l   = Saudara Kandung/ Tiri/ Angkat dari Suami atau Istri Beserta Suami atau Istrinya dari Saudara yang Bersangkutan
        </p>
    </body>
</html>
