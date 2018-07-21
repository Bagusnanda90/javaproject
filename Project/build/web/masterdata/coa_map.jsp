<%-- 
    Document   : coa_map
    Created on : Jun 19, 2015, 9:16:47 AM
    Author     : Hendra Putu McHen
--%>

<%@page import="com.dimata.harisma.entity.payroll.PayComponent"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayComponent"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.system.entity.system.PstSystemProperty"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstPosition"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.harisma.entity.masterdata.Company"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstCompany"%>
<%@page import="java.util.Vector"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file = "../main/javainit.jsp" %>
<!DOCTYPE html>

<%
    int iCommand = FRMQueryString.requestCommand(request);
    long oidCoa = FRMQueryString.requestLong(request, "oid_coa");
    long oidDepart = FRMQueryString.requestLong(request,"oid_department");
    long idPerkiraan = FRMQueryString.requestLong(request, "id_perkiraan");
    long idCompany = FRMQueryString.requestLong(request, "company_id");
    long idDivision = FRMQueryString.requestLong(request, "division_id");
    long idDepartment = FRMQueryString.requestLong(request, "department_id");
    long idSection = FRMQueryString.requestLong(request, "section_id");
    String formula = FRMQueryString.requestString(request, "formula_code");
    String formulaInput = FRMQueryString.requestString(request, "formula_input");
    /* find company and division */
    Department department = new Department();
    try{
        department = PstDepartment.fetchExc(oidDepart);
    } catch(Exception ex){
        System.out.println("Department fetchExc=>"+ex.toString());
    }
    Division divisi = new Division();
    try {
        divisi = PstDivision.fetchExc(department.getDivisionId());
    } catch(Exception ex){
        System.out.println("Division fetchExc=>"+ex.toString());
    }
    Company company = new Company();
    try {
        company = PstCompany.fetchExc(divisi.getCompanyId());
    } catch(Exception ex){
        System.out.println("Company fetchExc=>"+ex.toString());
    }
    String whereSection = " DEPARTMENT_ID="+oidDepart+" ";
    Vector listSection = PstSection.list(0, 0, whereSection, "");
    Vector section_value = new Vector(1,1);
    Vector section_key = new Vector(1,1);
    section_key.add("-select-");
    section_value.add("0");
    if (listSection != null && listSection.size()>0){
        for (int i=0; i<listSection.size(); i++){
            Section section = (Section)listSection.get(i);
            section_value.add(""+section.getOID());
            section_key.add(section.getSection());
        }
    }
    
    
    if (idPerkiraan == 0){
        idPerkiraan = oidCoa;
    }
    if (iCommand == Command.SAVE){
        ComponentCoaMap coaMap = new ComponentCoaMap();
        coaMap.setGenId(idCompany);
        coaMap.setDivisionId(idDivision);
        coaMap.setDepartmentId(idDepartment);
        coaMap.setSectionId(idSection);
        if (formula.length()==0){
            formula = "-";
        }
        coaMap.setFormula(formula);
        coaMap.setIdPerkiraan(idPerkiraan);
        PstComponentCoaMap.insertExc(coaMap);
        iCommand = 0;
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chart of Account Mapping</title>
        <style type="text/css">
            body {color :#373737;margin: 0; padding: 0;}
            .header {background-color: #EEE; padding:21px 32px;}
            h1 {color:#575757;}
            .content {padding:21px 32px;}
            td {padding: 9px 14px; text-align: left; }
            select {padding:5px 7px; border: 1px solid #CCC; color: #575757; }
            input {padding:5px 7px; border: 1px solid #CCC; color: #575757; }
            .btn {padding:5px 7px; background-color: #D3D3D3; color: #575757; border: 1px solid #C3C3C3; border-radius: 3px; }
            .btn:hover {background-color: #C5C5C5; color: #FFF; border: 1px solid #B5B5B5;}
        </style>
        <script language="JavaScript">
            
            function cmdSave()
            {
                document.frm_mapping.command.value = "<%=Command.SAVE%>";
                document.frm_mapping.action = "coa_map.jsp";
                document.frm_mapping.submit();
            }
            function cmdRefresh(){
                self.opener.document.frmaccountchart.command.value = "0";                 
                //self.close();
                self.opener.document.frmaccountchart.submit();
                self.close();
            }
            
            function cmdAddFormula(){
                var data = document.getElementById("select_formula").value;
                var result = document.getElementById("formula_input").value;
                if(result!=""){
                    result = result +","+ data;
                } else {
                    result = result + data;
                }
                document.getElementById("formula_input").value = result;
            }
        </script>
    </head>
    <body>
        <div class="header">
            <h1>Chart of Account Mapping</h1>
        </div>
        <div class="content">
            <form name="frm_mapping" method="post" action="">
                <input type="hidden" name="command" value="<%=iCommand%>">
                <input type="hidden" name="id_perkiraan" value="<%=idPerkiraan%>">
                <%
                    
                %>
            <table>
                <tr>
                    <th valign="middle" align="left">Company</th>
                    <td valign="middle" align="left">
                        <input type="hidden" name="company_id" value="<%=company.getOID()%>" />
                        <input type="text" disabled="disabled" name="company_name" value="<%=company.getCompany()%>" />
                    </td>
                </tr>
                <tr>
                    <th valign="middle" align="left">Division</th>
                    <td valign="middle" align="left">
                        <input type="hidden" name="division_id" value="<%=divisi.getOID()%>" />
                        <input type="text" disabled="disabled" name="division_name" value="<%=divisi.getDivision()%>" />
                    </td>
                </tr>
                <tr>
                    <th valign="middle" align="left">Department</th>
                    <td valign="middle" align="left">
                        <input type="hidden" name="department_id" value="<%=department.getOID()%>" />	
                        <input type="text" disabled="disabled" name="department_name" value="<%=department.getDepartment()%>" />	
                    </td>
                </tr>
                <tr>
                    <th valign="middle" align="left">Section</th>
                    <td valign="middle" align="left">
                        <%= ControlCombo.draw("section_id", "frm_mapping", null, "", section_value, section_key, "") %>
                    </td>
                </tr>
                <tr>
                    <th valign="middle" align="left">Formula</th>
                    <td valign="middle" align="left">
                        <button class="btn" onclick="javascript:cmdAddFormula()">Add Salary Component</button>
                        <select id="select_formula">
                            <option value="0">-select-</option>
                        <%
                        /* List of Salary Component */
                        Vector listComponent = PstPayComponent.list(0, 0, "", "");
                        if (listComponent != null & listComponent.size()>0){
                            for(int i=0; i<listComponent.size(); i++){
                                PayComponent payComp = (PayComponent)listComponent.get(i);
                                %>
                                <option value="<%=""+payComp.getCompCode()%>"><%=payComp.getCompCode()+" | "+payComp.getCompName()%></option>
                                <%
                            }
                        }

                        String formulaData = "";
                        formulaData = formulaInput;
                        
                        %>
                        </select><br />
                        <input type="text" id="formula_input" name="formula_input" value="<%=formulaData%>" size="70" />
                        <input type="hidden" size="49"  name="formula_code" value="<%=formulaData%>" />
                    </td>
                </tr>
                <tr>
                    <th colspan="2" valign="middle" align="left">
                        <button class="btn" onclick="cmdSave()">Save</button>
                        <button class="btn" onclick="cmdRefresh()">Close</button>
                    </th>
                </tr>
            </table>
            </form>
        </div>
    </body>
</html>
