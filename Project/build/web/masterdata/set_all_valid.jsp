<%-- 
    Document   : dialog_struct
    Created on : Apr 25, 2016, 10:39:10 PM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDivision"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ include file = "../main/javainit.jsp" %>
<!DOCTYPE html>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    String master = FRMQueryString.requestString(request, "master");
    String dateFrom = FRMQueryString.requestString(request, "datefrom");
    String dateTo = FRMQueryString.requestString(request, "dateto");
    String valid = FRMQueryString.requestString(request, "valid");
    long oid = FRMQueryString.requestLong(request, "oid");
    //String strValidStart = FRMQueryString.requestString(request, "datefrom");
    String[] statusChx = FRMQueryString.requestStringValues(request, "status_chx");
    
    String strChx = "";
    
    String DATE_FORMAT_NOW = "yyyy-MM-dd";
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
    
     Vector listDivision = PstDivision.list(0, 0, "", PstDivision.fieldNames[PstDivision.FLD_DIVISION]);
     Vector listDepartment = PstDepartment.list(0, 0, "", PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);
     Vector listSection = PstSection.list(0, 0, "", PstSection.fieldNames[PstSection.FLD_SECTION]);
    
    if (master.equals("division") && iCommand == Command.SAVE ){
            Date dateNow = new Date();
            if(statusChx != null){
                for (int i=0; i<statusChx.length; i++){
                    Date validStart = sdf.parse(dateFrom);
                    Date validEnd = sdf.parse(dateTo);
                    strChx += "["+ statusChx[i] +"]";
                    Division division = new Division();
                    try {
                        division = PstDivision.fetchExc(Long.valueOf(statusChx[i]));
                        division.setValidStart(validStart);
                        division.setValidEnd(validEnd);
                        division.setValidStatus(Integer.valueOf(valid));
                        PstDivision.updateExc(division);
                    } catch(Exception e){
                        System.out.println(e.toString());
                    }
                }
            }
    }
    
    if (master.equals("department") && iCommand == Command.SAVE){
            Date dateNow = new Date();
            if(statusChx != null){
                for (int i=0; i<statusChx.length; i++){
                    Date validStart = sdf.parse(dateFrom);
                    Date validEnd = sdf.parse(dateTo);
                    strChx += "["+ statusChx[i] +"]";
                    Department department = new Department();
                    try {
                        department = PstDepartment.fetchExc(Long.valueOf(statusChx[i]));
                        department.setValidStart(validStart);
                        department.setValidEnd(validEnd);
                        department.setValidStatus(Integer.valueOf(valid));
                        PstDepartment.updateExc(department);
                    } catch(Exception e){
                        System.out.println(e.toString());
                    }
                }
            }
        }
    
    
    if (master.equals("section") && iCommand == Command.SAVE ){
            Date dateNow = new Date();
            if(statusChx != null){
                for (int i=0; i<statusChx.length; i++){
                    Date validStart = sdf.parse(dateFrom);
                    Date validEnd = sdf.parse(dateTo);
                    strChx += "["+ statusChx[i] +"]";
                    Section section = new Section();
                    try {
                        section = PstSection.fetchExc(Long.valueOf(statusChx[i]));
                        section.setValidStart(validStart);
                        section.setValidEnd(validEnd);
                        section.setValidStatus(Integer.valueOf(valid));
                        PstSection.updateExc(section);
                    } catch(Exception e){
                        System.out.println(e.toString());
                    }
                }
            }
        }
    
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <title>Set Valid Status</title>
        <style type="text/css">
            body {
                color: #FFF;
                font-size: 12px;
                font-family: sans-serif;
                background-color: #5ACADB;
                padding: 0;
                margin: 0;
            }
            .header {
                color: #377387;
                padding: 18px;
                position: fixed;
                background-color: #FFF;
                border-bottom: 1px solid #377387;
                width: 100%;
            }
            .content {
                padding: 21px;
            }
            .btn {
                background-color: #00a1ec;
                border-radius: 3px;
                font-family: Arial;
                color: #EEE;
                font-size: 12px;
                padding: 7px 11px 7px 11px;
                text-decoration: none;
            }

            .btn:hover {
                color: #FFF;
                background-color: #007fba;
                text-decoration: none;
            }
            .footer {
                display: inline;
                position: fixed;
            }
        </style>
        <script type="text/javascript">
            function cmdSave(){
                document.frm.command.value="<%=Command.SAVE%>";
                document.frm.action="set_all_valid.jsp?master=<%=master%>";
                document.frm.submit();
            }
            
            function cmdRefresh() {
                self.opener.document.frm.command.value = "<%= Command.NONE %>";                 
                self.opener.document.frm.submit();
            }
        </script>
        <link rel="stylesheet" href="<%=approot%>/javascripts/datepicker/themes/jquery.ui.all.css">
        <script src="<%=approot%>/javascripts/jquery.js"></script>
        <script src="<%=approot%>/javascripts/datepicker/jquery.ui.core.js"></script>
	<script src="<%=approot%>/javascripts/datepicker/jquery.ui.widget.js"></script>
	<script src="<%=approot%>/javascripts/datepicker/jquery.ui.datepicker.js"></script>
        <script>
        $(function() {
    $( "#datepicker1" ).datepicker({ dateFormat: "yy-mm-dd" });
    $( "#datepicker2" ).datepicker({ dateFormat: "yy-mm-dd" });
});
	</script>
    </head>
    <body>
        <form name="frm" method="post" action="">
        <input type="hidden" name="command" value="<%=iCommand%>">
        <input type="hidden" name="oid" value="<%= oid %>">
        
        <div class="header">
            <table border="0" cellspacing="2" cellpadding="2">
                <tr>
                    <% if (master.equals("division")){ %>
                    <h1>Division</h1>
                    <% } 
                     if (master.equals("department")) { %>
                    <h1>Department</h1>
                    <% } 
                    if (master.equals("section")) { %>
                    <h1>Section</h1>
                    <% } %>
                </tr>
                <tr>
                    <td>Masa Berlaku</td>
                        <%
                            
                            Date dateStart = new Date();
                            Date dateEnd = new Date();
                            String strValidStart = sdf.format(dateStart);
                            String strValidEnd = sdf.format(dateEnd);
                        %>
                        <td><input type="text" name="datefrom" id="datepicker1" value="<%=strValidStart%>" /> to <input type="text" name="dateto" id="datepicker2" value="<%=strValidEnd%>" /></td>
            
                </tr>
                <tr>
                    <td>Valid Status</td>
                    <td><select name="valid">
                            <option value="1" selected="selected">Active</option>
                            <option value="0" selected="selected">History</option>
                        </select>
                    </td>
            
                </tr>
                <tr>
                    <td>&nbsp</td>
                </tr>
                <tr>
                    <td><a class="btn" style="color:#FFF;" href="javascript:cmdSave()">Submit</a></td>
                </tr>
            </table>
        </div>
            
        <div class="content">
            <div style="padding-top: 180px; ">&nbsp;</div>
            <table>
            <%
            if (master.equals("division")){
                if (listDivision != null && listDivision.size()>0){
                for (int i=0; i<listDivision.size(); i++){
                    Division divisi = (Division)listDivision.get(i);
                        %>
                        <tr>
                            <td><input type="checkbox" name="status_chx" value="<%= divisi.getOID() %>" /></td>
                            <td><strong><%= divisi.getDivision() %></strong></td>
                        </tr>
                        <%
                    }
                }
            }

            if (master.equals("department")){
                if (listDepartment != null && listDepartment.size()>0){
                for (int i=0; i<listDepartment.size(); i++){
                    Department dep = (Department)listDepartment.get(i);
                        %>
                        <tr>
                            <td><input type="checkbox" name="status_chx" value="<%= dep.getOID() %>" /></td>
                            <td><strong><%= dep.getDepartment()%></strong></td>
                        </tr>
                        <%
                    }
                }
            }

            if (master.equals("section")){
                if (listSection != null && listSection.size()>0){
                for (int i=0; i<listSection.size(); i++){
                    Section section = (Section)listSection.get(i);
                      %>
                        <tr>
                            <td><input type="checkbox" name="status_chx" value="<%= section.getOID() %>" /></td>
                            <td><strong><%= section.getSection()%></strong></td>
                        </tr>
                        <%
                    }
                }
                }
            %>
            </table>
        </div>
        </form>
    </body>
</html>