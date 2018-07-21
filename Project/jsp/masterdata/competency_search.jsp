<%-- 
    Document   : competency_search
    Created on : Feb 3, 2015, 4:42:13 PM
    Author     : Hendra Putu
--%>

<%@page import="com.dimata.harisma.entity.masterdata.Competency"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstCompetency"%>
<%@page import="java.util.Vector"%>
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
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_POSITION);%>
<%@ include file = "../main/checkuser.jsp" %>

<%!
	public String drawList(Vector objectClass, long oidCompetency) {

        ControlList ctrlist = new ControlList(); //membuat new class ControlList
        // membuat tampilan dengan controllist
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("tblStyle");
        ctrlist.setTitleStyle("title_tbl");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("title_tbl");
        ctrlist.setCellSpacing("0");
        ctrlist.addHeader("No", "");
        ctrlist.addHeader("Competency Name", "");

        /////////
        ctrlist.setLinkRow(1); // untuk menge-sett link di kolom pertama atau dikolom yg lain

        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ////////


        ctrlist.reset(); //berfungsi untuk menginisialisasi list menjadi kosong

        int no = 0;

        // objectClass mempunyai tipe data Vector
        // objectClass.size(); mendapatkan banyak record
        for (int i = 0; i < objectClass.size(); i++) {
            // membuat object WarningReprimandAyat berdasarkan objectClass ke-i
            
            Competency competency = (Competency) objectClass.get(i);
            // rowx will be created secara berkesinambungan base on i
            Vector rowx = new Vector();

            no = no + 1;
            rowx.add("" + no);
            
            
            rowx.add(competency.getCompetencyName());
           

            lstData.add(rowx);
            // menambah ID ke list LinkData
            lstLinkData.add(String.valueOf(competency.getOID()));

        }

        return ctrlist.draw(); // mengembalikan data-data control list

    }

    public String drawData(Vector objectClass) {
        String str = "";
        long typ = 0;
        long grp = 0;
            for (int i = 0; i < objectClass.size(); i++) {
                //
                Vector arrEnt = (Vector) objectClass.get(i);
                Competency competency = (Competency) arrEnt.get(0);
                CompetencyType compType = (CompetencyType) arrEnt.get(1);
                CompetencyGroup compGroup = (CompetencyGroup) arrEnt.get(2);
                if (competency.getCompetencyTypeId() != typ){
                    // tampilkan tipe
                    str += "<div id='divType'>"+compType.getTypeName()+"</div>";
                    typ = competency.getCompetencyTypeId();
                }
                if (competency.getCompetencyGroupId() != grp){
                    // tampilkan group
                    str += "<div id='divGroup'>"+compGroup.getGroupName()+"</div>";
                    grp = competency.getCompetencyGroupId();
                }
                str += "<div id='divComp'><a href=\"javascript:cmdChoose('"+competency.getOID()+"')\">"+competency.getCompetencyName()+"</a></div>";
            }
        
        return str;
    }
%>


<%
    String comm = request.getParameter("comm");
    Vector listCompetency = new Vector();
    listCompetency = PstCompetency.listInnerJoin(0, 100);
%>
<html>
<head>
    <title>Competency Form</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <style type="text/css">
        body {
            margin: 0;
            padding: 0;
        }
        #title {
            padding: 5px 7px;
            border-bottom: 1px solid #0099FF;
            background-color: #EEE;
            font-size: 24px;
            color: #333;
        }
        #content {
            background-color: #F7F7F7;
            padding: 5px 7px;
            margin-top: 7px;
        }
        #btn-sc {
            padding: 3px 7px;
            border: 1px solid #CCC;
            background-color: #EEE;
            color: #333;
        }
        #btn-sc:hover {
            background-color: #999;
            color: #FFF;
        }
        
        .tblStyle {border-collapse: collapse;font-size: 11px;}
        .tblStyle td {padding: 3px 5px; border: 1px solid #CCC;}
        .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            
        td {border: 1px solid #CCC;}
        #cbType {padding: 3px 5px; color: #0066FF; border: 1px solid #CCC;}
        #divType {border-left: 1px solid #333; padding: 3px 7px; background-color: #DDD; font-weight: bold;}
        #divGroup {padding: 3px 6px; background-color: #EEE; margin-left: 9px; border-left:1px solid #0099FF; }
        #divComp {padding-left: 27px;}
        #listbtn {background-color: #DDD; padding: 5px; margin-top: 14px;}
    </style>
    <script language="javascript">
        function cmdChoose(competencyId) {
            self.opener.document.frmposition.competency_id.value = competencyId;
            self.opener.document.frmposition.command.value = "<%=comm%>";                 
            //self.close();
            self.opener.document.frmposition.submit();
        }
    </script>
</head>
<body>
    <div id="title">Competency Search</div>
    <div id="content">
       
        <%if (listCompetency != null && listCompetency.size() > 0) {%>
        <%=drawData(listCompetency)%>
        <%}else{%>
        <div>no record</div>
        <%}%>

       
    </div>
</body>
</html>