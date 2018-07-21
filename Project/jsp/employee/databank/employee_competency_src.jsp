<%-- 
    Document   : employee_competency_src
    Created on : Feb 5, 2015, 7:08:04 PM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.harisma.entity.masterdata.CompetencyGroup"%>
<%@page import="com.dimata.harisma.entity.masterdata.CompetencyType"%>
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
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_POSITION);%>
<%@ include file = "../../main/checkuser.jsp" %>

<%!
	public String drawList(Vector objectClass, long oidCompetency) {

        ControlList ctrlist = new ControlList(); //membuat new class ControlList
        // membuat tampilan dengan controllist
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.setAreaStyle("customTable");
        ctrlist.addHeader("No", "7%");
        ctrlist.addHeader("Competency Name", "20%");

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

    public String drawData(Vector objectClass, long employeeID) {
        if(objectClass==null || objectClass.size()<1){
            return "No competency master";
        }
        String str = "";
        long typ = 0;
        long grp = 0;
        
         String where = "";
        where = "" + employeeID;
        Hashtable hListEmpCompetency = PstEmployeeCompetency.listInnerJoinHashtable(where, PstCompetency.fieldNames[PstCompetency.FLD_COMPETENCY_ID]);
        
        str= "<table>";
            for (int i = 0; i < objectClass.size(); i++) {
                //
                Vector arrEnt = (Vector) objectClass.get(i);
                Competency competency = (Competency) arrEnt.get(0);
                CompetencyType compType = (CompetencyType) arrEnt.get(1);
                CompetencyGroup compGroup = (CompetencyGroup) arrEnt.get(2);
                if (competency.getCompetencyTypeId() != typ){
                    // tampilkan tipe
                    str += "<tr><td colspan='4' ><div id='divType'>"+compType.getTypeName()+"</div></td></tr>";
                   
                    typ = competency.getCompetencyTypeId();
                }
                if (competency.getCompetencyGroupId() != grp){
                    // tampilkan group
                    str += "<tr><td colspan='4' ><div id='divGroup'>&nbsp;&nbsp;&nbsp;"+compGroup.getGroupName()+"</div></td></tr>";
                     str += "<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;Competency</td><td>Score</td><td>Note/Achievement</td><td>&nbsp;</td></tr>";
                    grp = competency.getCompetencyGroupId();
                }
                
                EmployeeCompetency employeeCompetency = (EmployeeCompetency)(hListEmpCompetency!=null ? hListEmpCompetency.get(""+competency.getOID()):null); 
                if(employeeCompetency==null) { 
                    employeeCompetency = new EmployeeCompetency(); 
                }        
                Vector compLevels = PstCompetencyLevel.list(0, 100, ""+PstCompetencyLevel.fieldNames[PstCompetencyLevel.FLD_COMPETENCY_ID]+"="+competency.getOID() ,
                       PstCompetencyLevel.fieldNames[PstCompetencyLevel.FLD_SCORE_VALUE] );
                String inputLevel ="";
                if(compLevels!=null && compLevels.size()>0){
                    Vector valScores = new Vector();
                    Vector valSelect = new Vector();
                    valScores.add("0");
                     valSelect.add("-no score-");
                    for(int ic=0; ic < compLevels.size() ; ic++){
                        CompetencyLevel compLevel = (CompetencyLevel) compLevels.get(ic);
                        valScores.add(""+compLevel.getOID()+"_"+compLevel.getScoreValue());
                        valSelect.add(""+compLevel.getScoreValue() + " | "+compLevel.getDescription() + " | " +compLevel.getLevelMin()+" ~ "+compLevel.getLevelMax() +" " + compLevel.getLevelUnit());                     
                    }
                    inputLevel = ControlCombo.draw(""+competency.getOID()+"_compscore","formElemen", null,
                        ""+employeeCompetency.getCompetencyLevelId()+"_"+employeeCompetency.getScoreValue(), valScores, valSelect);
                    
                }else{
                    inputLevel ="<input name=\""+competency.getOID()+"_compscore\" value="+ (employeeCompetency!=null? employeeCompetency.getScoreValue():0) +" >";
                }
                String specialAchv= "<input name=\""+competency.getOID()+"_specialachv\" value="+ (employeeCompetency!=null? employeeCompetency.getSpecialAchievement():"") +" >";
                str += "<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<div id='divComp'>"+ /*"<a href=\"javascript:cmdChoose('"+competency.getOID()+"')\">"+*/
                        competency.getCompetencyName()+/*"</a>"+*/"</div></td><td>"+ inputLevel+ "</td><td> " +specialAchv+"</td><td>&nbsp</td></tr>";
                
            }
        str= str+"<table>";
        return str;
    }
%>


<%
    String comm = request.getParameter("comm");
    int iCommand= FRMQueryString.requestCommand(request);
    
    Vector listCompetency = new Vector();
    listCompetency = PstCompetency.listInnerJoin(0, 100);
    long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");
    
    // add by kar
    if(Command.SAVE==iCommand && listCompetency!=null && listCompetency.size()>0){
            for (int i = 0; i < listCompetency.size(); i++) {
                //
                Vector arrEnt = (Vector) listCompetency.get(i);
                Competency competency = (Competency) arrEnt.get(0);
                String scoreLevel = FRMQueryString.requestString(request, ""+competency.getOID()+"_compscore");
                if(scoreLevel!=null && !scoreLevel.equals("0")){
                      String[] scoreValues =  scoreLevel.split("_");
                      try{
                          long levelOid = Long.parseLong(scoreValues[0]);                          
                          float score = Float.parseFloat(scoreValues[1]);
                          String specArch = FRMQueryString.requestString(request, ""+competency.getOID()+"_specialachv");
                          EmployeeCompetency employeeCompetency = new EmployeeCompetency();
                          employeeCompetency.setCompetencyId(competency.getOID());
                          employeeCompetency.setCompetencyLevelId(levelOid);
                          employeeCompetency.setEmployeeId(oidEmployee);
                          employeeCompetency.setScoreValue(score);
                          employeeCompetency.setSpecialAchievement(specArch);
                          
                          Vector vC= PstEmployeeCompetency.listByEmployeeCompetencies(1,oidEmployee, competency.getOID() );
                          if(vC==null || vC.size()==0){
                               PstEmployeeCompetency.insertExc(employeeCompetency);
                           }else{
                               EmployeeCompetency empCompPrev = (EmployeeCompetency) vC.get(0);
                               employeeCompetency.setOID(empCompPrev.getOID());
                               PstEmployeeCompetency.updateExc(employeeCompetency);
                          }
                          
                       }catch(Exception exc){
                           System.out.println(exc);
                       }
                      
                  }
           }
    }
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
        td {border: 1px solid #CCC;}
        #cbType {padding: 3px 5px; color: #0066FF; border: 1px solid #CCC;}
        #divType {border-left: 1px solid #333; padding: 3px 7px; background-color: #DDD; font-weight: bold;}
        #divGroup {padding: 3px 6px; background-color: #EEE; margin-left: 9px; border-left:1px solid #0099FF; }
        #divComp {padding-left: 27px;}
        #listbtn {background-color: #DDD; padding: 5px; margin-top: 14px;}
    </style>
    <script language="javascript">
        function cmdChoose(competencyId) {
            self.opener.document.frmemplanguage.competency_id.value = competencyId;
            self.opener.document.frmemplanguage.command.value = "<%=Command.SUBMIT%>";                 
            //self.close();
            self.opener.document.frmemplanguage.submit();
        }
        
        function cmdSave(){
	document.frmcompetencies.command.value="<%=Command.SAVE%>";
	document.frmcompetencies.action="employee_competency_src.jsp";
	document.frmcompetencies.submit();
	}
    </script>
</head>
<body>
    <div id="title">Competency Search</div>
    <div id="content">
        <form name="frmcompetencies" >
            <input type="hidden" name="command" value="<%=iCommand%>" />
            <input type="hidden" name="employee_oid" value="<%=oidEmployee%>" />
        <%if (listCompetency != null && listCompetency.size() > 0) {%>
        <%=drawData(listCompetency, oidEmployee)%>
        <%}else{%>
        <div>no record</div>
        <%}%>
        </form>
    </div>
     <div id="content">
       
        <%if (listCompetency != null && listCompetency.size() > 0) {%>
        <a href="javascript:cmdSave()">Save Employee Competencies Map</a>
        <%}%>
        
    </div>  
        <% if(Command.SAVE==iCommand) { %>
        <script language="javascript">
             cmdChoose(0); 
            </script>
            <% } %>
</body>
</html>
