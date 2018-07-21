
<% 
/* 
 * Page Name  		:  srcemployee.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		: lkarunia 
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
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
 long hrdDepOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    boolean isHRDLogin = hrdDepOid == departmentOid ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
%>
<!-- Jsp Block -->
<%
    int iCommand = FRMQueryString.requestCommand(request);
    
    
    SrcSpecialEmployee srcSpecialEmployee = new SrcSpecialEmployee();
    
   /** SearchSpecialQuery searchSpecialQuery = new SearchSpecialQuery();
    FrmSrcSpecialEmployeeQuery frmSrcSpecialEmployeeQuery = new FrmSrcSpecialEmployeeQuery(request, searchSpecialQuery);
     frmSrcSpecialEmployeeQuery.requestEntityObject(searchSpecialQuery);*/
     
     
    FrmSrcSpecialEmployee frmSrcSpecialEmployee = new FrmSrcSpecialEmployee();

    if(iCommand==Command.BACK)
    {
        frmSrcSpecialEmployee = new FrmSrcSpecialEmployee(request, srcSpecialEmployee);
        //frmSrcEmployee.requestEntityObject(srcEmployee);
        try{
            srcSpecialEmployee = (SrcSpecialEmployee) session.getValue(SessSpecialEmployee.SESS_SRC_SPECIAL_EMPLOYEE);
            if(srcSpecialEmployee == null) {
                srcSpecialEmployee = new SrcSpecialEmployee();
            }
            //System.out.println("ecccccc " + srcSpecialEmployee.getSortby());
        }
        catch (Exception e){
            //System.out.println("e....."+e.toString());
            srcSpecialEmployee = new SrcSpecialEmployee();
        }
    }

%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Special Query</title>
<script language="JavaScript">
    function cmdSearch(){
            document.frmsrcemployee.command.value="<%=Command.LIST%>";
            //document.frmsrcemployee.action="specialquery_list.jsp";employee_list.jsp
           // document.frmsrcemployee.action="<%//=approot%>/servlet/com.dimata.harisma.session.employee.SpecialQueryResult";
            document.frmsrcemployee.action="employee_list.jsp";
            document.frmsrcemployee.submit();
    }
    
    function cmdBackSearchEmployee(){
            document.frmsrcemployee.command.value="<%=Command.BACK%>";
            document.frmsrcemployee.action="srcemployee.jsp";
            document.frmsrcemployee.submit();
    }
        function cmdExportToExcel(){
            document.frmsrcemployee.command.value="<%=Command.LIST%>";
            //document.frmsrcemployee.action="specialquery_list.jsp";employee_list.jsp
          document.frmsrcemployee.action="<%=approot%>/servlet/com.dimata.harisma.session.employee.SpecialQueryResult";
            document.frmsrcemployee.submit();
    }

    function fnTrapKD(){
       if (event.keyCode == 13) {
            document.all.aSearch.focus();
            cmdSearch();
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
<SCRIPT language="javascript">
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

function showObjectForMenu(){
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
-->
</SCRIPT>
<!-- #EndEditable -->
<style type="text/css">
    #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3;}
    #menu_title {color:#0099FF; font-size: 14px; font-weight: bold;}
    .content-main {
        color: #575757;
        background-color: #EEE;
        margin: 25px 23px 59px 23px;
        border: 1px solid #CCC;
        border-radius: 5px;
    }
    .content {
        padding: 14px 15px;
    }
    #coloum_title{
        padding: 5px 7px;
        background-color: #999;
        color: #FFF;
    }
    #coloum_caption {
        padding-right: 9px; font-weight: bold; color: #575757;
    }
    #title_part {
        font-size: 13px;
        color: #0099FF;
        background-color: #FFF;
        border-left: 1px solid #373737;
        padding: 3px 5px;
        margin: 5px 0px;
    }
</style>
</head> 

<body>
    <div class="header">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
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
        </table>
    </div>
    <div id="menu_utama">
        <span id="menu_title"><%=dictionaryD.getWord(I_Dictionary.EMPLOYEE)%> <strong style="color:#999;"> / </strong> Databank <strong style="color:#999;"> / </strong> Special Query</span>
    </div>
    <div class="content-main">
        <form name="frmsrcemployee" method="post" action="" target="blank">
            <input type="hidden" name="command" value="<%=iCommand%>">
            <div class="content">
            <div style="font-style: italic; font-size: 11px; padding: 3px">I would like to search for employee(s) that matched these criterias:</div>
            <table cellspacing="2" cellpadding="2">
                <tr>
                    <td valign="top" id="coloum_title"><%=dictionaryD.getWord(I_Dictionary.COMPANY) %></td>
                    <td valign="top" id="coloum_title"><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></td>
                    <td valign="top" id="coloum_title"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                    <td valign="top" id="coloum_title"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></td>
                </tr>
                <tr>
                    <td>
                        <%
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
                        %>
                        <%= ControlCombo.draw(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_COMPANY_ID],"formElemen",null, ""+srcSpecialEmployee.getCompanyId(), comp_value, comp_key, " size=8 multiple onkeydown=\"javascript:fnTrapKD()\"") %>
                    </td>
                    <td>
                        <%
                            Vector div_value = new Vector(1,1);
                            Vector div_key = new Vector(1,1);
                            div_value.add("0");
                            div_key.add("select ...");
                            Vector listDiv = PstDivision.list(0, 0, "", " DIVISION ");
                          if(listDiv!=null && listDiv.size()>0){ 
                            for (int i = 0; i < listDiv.size(); i++) {
                                    Division division = (Division) listDiv.get(i);
                                    div_key.add(division.getDivision());
                                    div_value.add(String.valueOf(division.getOID()));
                           }
                        }
                        %>
                        <%= ControlCombo.draw(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_EMP_DIVISION_ID],"formElemen",null, ""+srcSpecialEmployee.getDivisionId(), div_value, div_key, " size=8 multiple onkeydown=\"javascript:fnTrapKD()\"") %>
                    </td>
                    <td>
                        <% 
                                    /*
                                      Vector dept_value = new Vector(1,1);
                                      Vector dept_key = new Vector(1,1);
                                      dept_value.add("0");
                                      dept_key.add("...ALL...");
                                      Vector listDept = PstDepartment.list(0, 0, "", " DEPARTMENT ");
                                      for (int i = 0; i < listDept.size(); i++) {
                                              Department dept = (Department) listDept.get(i);
                                              dept_key.add(dept.getDepartment());
                                              dept_value.add(String.valueOf(dept.getOID()));
                                      }*/

                                      Vector dept_value = new Vector(1, 1);
                                      Vector dept_key = new Vector(1, 1);
                                      //Vector listDept = new Vector(1, 1);
                                      DepartmentIDnNameList keyList = new DepartmentIDnNameList();

                                      if (processDependOnUserDept) {
                                          if (emplx.getOID() > 0) {
                                              if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                                                  keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                                                  //listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                              } else {
                                                  Position position = null;
                                                  try {
                                                      position = PstPosition.fetchExc(emplx.getPositionId());
                                                  } catch (Exception exc) {
                                                  }
                                                  if (position != null & position.getDisabedAppDivisionScope() == 0 & position.getPositionLevel() >= PstPosition.LEVEL_MANAGER) {
                                                      String whereDiv = " d." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + emplx.getDivisionId() + "";
                                                      keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, whereDiv, true);
                                                  } else {

                                                      String whereClsDep = "(" + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + " = " + departmentOid
                                                              + ") OR (" + PstDepartment.fieldNames[PstDepartment.FLD_JOIN_TO_DEPARTMENT_ID] + " = " + departmentOid + ") ";
                                                      try {
                                                          String joinDept = PstSystemProperty.getValueByName("JOIN_DEPARMENT");
                                                          Vector depGroup = com.dimata.util.StringParser.parseGroup(joinDept);

                                                          int grpIdx = -1;
                                                          int maxGrp = depGroup == null ? 0 : depGroup.size();
                                                          int countIdx = 0;
                                                          int MAX_LOOP = 10;
                                                          int curr_loop = 0;
                                                          do { // find group department belonging to curretn user base in departmentOid
                                                              curr_loop++;
                                                              String[] grp = (String[]) depGroup.get(countIdx);
                                                              for (int g = 0; g < grp.length; g++) {
                                                                  String comp = grp[g];
                                                                  if (comp.trim().compareToIgnoreCase("" + departmentOid) == 0) {
                                                                      grpIdx = countIdx;   // A ha .. found here 
                                                                  }
                                                              }
                                                              countIdx++;
                                                          } while ((grpIdx < 0) && (countIdx < maxGrp) && (curr_loop < MAX_LOOP)); // if found then exit

                                                          // compose where clause
                                                          if (grpIdx >= 0) {
                                                              String[] grp = (String[]) depGroup.get(grpIdx);
                                                              for (int g = 0; g < grp.length; g++) {
                                                                  String comp = grp[g];
                                                                  whereClsDep = whereClsDep + " OR (DEPARTMENT_ID = " + comp + ")";
                                                              }
                                                          }
                                                                                          } catch (Exception exc) {
                                                                                                  System.out.println(" Parsing Join Dept" + exc);

                                                                                          }
                                                                                          keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, whereClsDep, false);
                                                                                          //listDept = PstDepartment.list(0, 0,whereClsDep, "");
                                                                                      }
                                                                                  }
                                                                              } else {
                                                                                  keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                                                                              }
                                                                          } else {
                                                                              keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                                                                          }
                                                                          dept_value = keyList.getDepIDs();
                                                                          dept_key = keyList.getDepNames();

                                                                          String selectValueDepartment = "" + srcSpecialEmployee.getDepartment();//+objSrcLeaveApp.getDepartmentId(); 
                                  %>
                        <%//= ControlCombo.draw(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_DEPARTMENT],"formElemen",null, ""+srcSpecialEmployee.getDepartment(), dept_value, dept_key, " size=8 multiple onkeydown=\"javascript:fnTrapKD()\"") %>
                        <%=ControlCombo.draw(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_DEPARTMENT], "formElemen", null, selectValueDepartment, dept_value, dept_key, " size=8 multiple onkeydown=\"javascript:fnTrapKD()\"")%>
                    </td>
                    <td>
                        <% 
                            Vector sec_value = new Vector(1,1);
                            Vector sec_key = new Vector(1,1); 
                            sec_value.add("0");
                            sec_key.add("...ALL...");
                            Vector listSec = PstSection.list(0, 0, "", " DEPARTMENT_ID, SECTION ");
                            for (int i = 0; i < listSec.size(); i++) {
                                    Section sec = (Section) listSec.get(i);
                                    sec_key.add(sec.getSection());
                                    sec_value.add(String.valueOf(sec.getOID()));
                            }
                        %>
                        <%= ControlCombo.draw(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_SECTION],"formElemen",null, "" + srcSpecialEmployee.getSection(), sec_value, sec_key, " size=8 multiple onkeydown=\"javascript:fnTrapKD()\"") %>
                    </td>
                </tr>
            </table>
            <table cellspacing="2" cellpadding="2">
                <tr>
                    <td valign="top" id="coloum_title"><%=dictionaryD.getWord(I_Dictionary.POSITION) %></td>
                    <td valign="top" id="coloum_title"><%=dictionaryD.getWord(I_Dictionary.LEVEL) %></td>
                    <td valign="top" id="coloum_title">Grade</td>
                    <td valign="top"></td>
                </tr>
                <tr>
                    <td>
                        <% 
                            Vector pos_value = new Vector(1,1);
                            Vector pos_key = new Vector(1,1); 
                            pos_value.add("0");
                            pos_key.add("...ALL...");
                            //if(keywordPosition ==null) keywordPosition ="";
                            //String whereClause = "" + PstPosition.fieldNames[PstPosition.FLD_POSITION] + " LIKE '%" + keywordPosition + "%'";
                            Vector listPos = PstPosition.list(0, 0, "", " POSITION "); 
                            for (int i = 0; i < listPos.size(); i++) {
                                    Position pos = (Position) listPos.get(i);
                                    pos_key.add(pos.getPosition());
                                    pos_value.add(String.valueOf(pos.getOID()));
                            }
                        %>
                        <%//= ControlCheckBox.draw(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_POSITION],"multiselect",searchSpecialQuery.getArrPositionId(0), pos_value, pos_key, " size=8 multiple onkeydown=\"javascript:fnTrapKD()\"") %>
                        <%= ControlCombo.draw(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_POSITION],"formElemen",null, "" + srcSpecialEmployee.getPosition(), pos_value, pos_key, " size=8 multiple onkeydown=\"javascript:fnTrapKD()\"") %>
                    </td>
                    <td>
                        <% 
                            Vector lvl_value = new Vector(1,1);
                            Vector lvl_key = new Vector(1,1); 
                            lvl_value.add("0");
                            lvl_key.add("...ALL...");
                            Vector listLvl = PstLevel.list(0, 0, "", " LEVEL ");
                            for (int i = 0; i < listLvl.size(); i++) {
                                    Level lvl = (Level) listLvl.get(i);
                                    lvl_key.add(lvl.getLevel());
                                    lvl_value.add(String.valueOf(lvl.getOID()));
                            }
                        %>
                          <%= ControlCombo.draw(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_LEVEL],"formElemen",null, "" + srcSpecialEmployee.getPosition(), lvl_value, lvl_key, " size=8 multiple onkeydown=\"javascript:fnTrapKD()\"") %>
                    </td>
                    <td>
                        <%
                        Vector gd_value = new Vector();
                        Vector gd_key = new Vector();
                        gd_value.add("0");
                        gd_key.add("SELECT");
                        Vector listGradeLevel = PstGradeLevel.listAll(); 
                        for (int i = 0; i < listGradeLevel.size(); i++) {
                            GradeLevel gradeLevel = (GradeLevel) listGradeLevel.get(i);
                            gd_key.add(gradeLevel.getCodeLevel());
                            gd_value.add(String.valueOf(gradeLevel.getOID()));
                        }
                        %>
                        <%= ControlCombo.draw(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_GRADE],"formElemen",null, "" + srcSpecialEmployee.getGradeLevel(), gd_value, gd_key, " size=8 multiple onkeydown=\"javascript:fnTrapKD()\"") %>
                    </td>
                    <td>&nbsp;</td>
                </tr>
            </table>
            </div>
            <div class="content" style="border-top: 1px solid #CCC">
            <table>
                <tr>
                    <td valign="middle" id="coloum_caption">Commencing Date</td>
                    <td>
                        <input type="radio" name="<%=FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_RADIO_COMMERCING_DATE]%>" value="0" checked> All date
                        <input type="radio" name="<%=FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_RADIO_COMMERCING_DATE]%>" value="1">
                        <%=ControlDate.drawDateWithStyle(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_COMMDATEFROM], new Date(), 0, -50,"formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%>&nbsp;to&nbsp; 
                        <%=ControlDate.drawDateWithStyle(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_COMMDATETO], new Date(), 0, -50,"formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%>
                    </td>
                </tr>
                <tr>
                    <td valign="middle" id="coloum_caption">Has been working for</td>
                    <td>
                        <p> 
                        <input type="text" name="<%=frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_WORKYEARFROM]%>" size="2" value="">
                        year, 
                        <input type="text" name="<%=frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_WORKMONTHFROM]%>" size="2" value="">
                        month to 
                        <input type="text" name="<%=frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_WORKYEARTO]%>" size="2" value="">
                        year, 
                        <input type="text" name="<%=frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_WORKMONTHTO]%>" size="2" value="">
                        month</p>
                    </td>
                </tr>
                <tr>
                    <td valign="middle" id="coloum_caption">Age from</td>
                    <td>
                        <input type="text" name="<%=frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_AGEYEARFROM]%>" size="2" value="">
                        year, 
                        <input type="text" name="<%=frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_AGEMONTHFROM]%>" size="2" value="">
                        month to 
                        <input type="text" name="<%=frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_AGEYEARTO]%>" size="2" value="">
                        year, 
                        <input type="text" name="<%=frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_AGEMONTHTO]%>" size="2" value="">
                        month
                    </td>
                </tr>
                <tr>
                    <td valign="middle" id="coloum_caption">Birth Month</td>
                    <td>
                        <select name="<%=frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_EMP_BIRTH_DAY_MONTH]%>">
                        <option value="<%= ""+0%>" <%= (srcSpecialEmployee.getBirthMonth()==(0) ? " selected " : "")  %>>-selected-</option>
                        <%
                          java.util.GregorianCalendar cal = new java.util.GregorianCalendar();
                          cal.set(Calendar.DAY_OF_MONTH, 1);
                          for(int i=0; i<12; i++) {
                        %>
                              <option value="<%= ""+(i+1)%>" <%= (srcSpecialEmployee.getBirthMonth()==(i+1) ? " selected " : "")  %>>
                              <% 
                                  cal.set(Calendar.MONTH, i);
                                  out.print(Formater.formatDate(cal.getTime(), "MMMM"));
                              %>
                              </option>
                        <%
                          }
                        %>
                       </select>
                    </td>
                </tr>
                <tr>
                    <td valign="middle" id="coloum_caption">Birth Year</td>
                    <td>
                        <%=ControlDate.drawDateYear(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_BIRTH_YEAR_FROM], null,"formElemen", 0, -115, " onkeydown=\"javascript:fnTrapKD()\"")%>&nbsp;to&nbsp; 
                              <%=ControlDate.drawDateYear(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_BIRTH_YEAR_TO], null,"formElemen", 0, -115, " onkeydown=\"javascript:fnTrapKD()\"")%> 
                    </td>
                </tr>
                <tr>
                    <td valign="middle" id="coloum_caption">Birth Date</td>
                    <td>
                        <%=ControlDate.drawDateWithStyle(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_BIRTH_DATE_FROM], null, 0, -115,"formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%>&nbsp;to&nbsp; 
                        <%=ControlDate.drawDateWithStyle(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_BIRTH_DATE_TO], null, 0, -115,"formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%>
                    </td>
                </tr>
                <tr>
                    <td valign="middle" id="coloum_caption">Address</td>
                    <td><input type="text" name="<%=frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_EMP_ADDRESS]%>" size="50" value=""></td>
                </tr>
                <tr>
                    <td valign="middle" id="coloum_caption">Address Permanent</td>
                    <td><input type="text" name="<%=frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_ADDRESS_EMP_PERMANET]%>" size="50" value=""></td>
                </tr>
                <tr>
                    <td valign="middle" id="coloum_caption">Resigned Date</td>
                    <td><input type="text" name="<%=frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_ADDRESS_EMP_PERMANET]%>" size="50" value=""></td>
                </tr>
                <tr>
                    <td valign="middle" id="coloum_caption">Resigned Reason</td>
                    <td><input type="text" name="<%=frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_ADDRESS_EMP_PERMANET]%>" size="50" value=""></td>
                </tr>
            </table>
            </div>
            <div class="content" style="border-top: 1px solid #CCC">
                <!-- update by satrya 2013-11-12 -->
                <div id="title_part">Career Path</div>
                <table>
                    
                    <tr>
                        <td valign="middle" id="coloum_caption">Work</td>
                        <td>
                            <%=ControlDate.drawDateWithStyle(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_DATE_CARRIER_WORK_START], null, 0, -50,"formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%>&nbsp;to&nbsp; 
                              <%=ControlDate.drawDateWithStyle(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_DATE_CARRIER_WORK_END], null, 0, -50,"formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%>
                        </td>
                    </tr>
                    <tr>
                        <td valign="middle" id="coloum_caption">Employee Category</td>
                        <td>
                            <% 
                                Vector carrierEmp_value = new Vector(1,1);
                                Vector carrierEmp_key = new Vector(1,1); 
                                carrierEmp_value.add("0");
                                carrierEmp_key.add("...ALL...");
                                //if(keywordPosition ==null) keywordPosition ="";
                                //String whereClause = "" + PstPosition.fieldNames[PstPosition.FLD_POSITION] + " LIKE '%" + keywordPosition + "%'";
                                Vector listEmpCarrierCategory = PstEmpCategory.list(0, 0, "",PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]);  
                                for (int i = 0; i < listEmpCarrierCategory.size(); i++) {
                                        EmpCategory empCategory = (EmpCategory) listEmpCarrierCategory.get(i);
                                        carrierEmp_key.add(empCategory.getEmpCategory());
                                        carrierEmp_value.add(String.valueOf(empCategory.getOID()));
                                }
                            %>
                            <%= ControlCombo.draw(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_CARRIER_EMP_CATEGORY],"formElemen",null, "" + srcSpecialEmployee.getCarrierCategoryEmp(), carrierEmp_value, carrierEmp_key, " onkeydown=\"javascript:fnTrapKD()\"") %>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="content" style="border-top: 1px solid #CCC">
                <table>
                    <tr>
                        <td valign="top" id="coloum_title">Emp.Category</td>
                        <td valign="top" id="coloum_title"><%=dictionaryD.getWord(I_Dictionary.EDUCATION) %></td>
                        <td valign="top" id="coloum_title">Religion</td>
                        <td valign="top" id="coloum_title">Marital Status</td>
                        <td valign="top" id="coloum_title">Blood Type</td>
                        <td valign="top" id="coloum_title">Language</td>
                        <td valign="top" id="coloum_title">Race</td>
                        <td valign="top" id="coloum_title">Country</td>
                        <td valign="top" id="coloum_title">Province</td>
                        <td valign="top" id="coloum_title">Resigned Status</td>
                    </tr>
                    <tr>
                        <td>
                            <%
                                Vector empcat_value = new Vector(1,1);
                                Vector empcat_key = new Vector(1,1); 
                                empcat_value.add("0");
                                empcat_key.add("...ALL..."); 
                                Vector listEmpCat = PstEmpCategory.list(0, 0, "", " EMP_CATEGORY ");
                              if(listEmpCat!=null && listEmpCat.size()>0){
                                for (int i = 0; i < listEmpCat.size(); i++) {
                                        EmpCategory empCategory = (EmpCategory) listEmpCat.get(i);
                                        empcat_key.add(empCategory.getEmpCategory());
                                        empcat_value.add(String.valueOf(empCategory.getOID()));
                                }
                              }

                          %>
                          <%= ControlCombo.draw(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_EMP_CATEGORY],"formElemen",null, "" + srcSpecialEmployee.getEmpCategoryId(), empcat_value, empcat_key, " size= 8 /*+  (listEmpCat!=null?listEmpCat.size()+1:0+1) +8*/ multiple onkeydown=\"javascript:fnTrapKD()\"") %>
                        </td>
                        <td>
                            <%
                                Vector edu_value = new Vector(1,1);
                                Vector edu_key = new Vector(1,1); 
                                edu_value.add("0");
                                edu_key.add("...ALL..."); 
                                Vector listEdu = PstEducation.list(0, 0, "", " EDUCATION ");
                                for (int i = 0; i < listEdu.size(); i++) {
                                        Education edu = (Education) listEdu.get(i);
                                        edu_key.add(edu.getEducation());
                                        edu_value.add(String.valueOf(edu.getOID()));
                                }
                          %>
                          <%= ControlCombo.draw(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_EDUCATION],"formElemen",null, "" + srcSpecialEmployee.getEducation(), edu_value, edu_key, " size= 8 /*+  (listEmpCat!=null?listEmpCat.size()+1:0+1) +8*/ multiple onkeydown=\"javascript:fnTrapKD()\"") %>
                        </td>
                        <td>
                            <% 
                                Vector rel_value = new Vector(1,1);
                                Vector rel_key = new Vector(1,1); 
                                rel_value.add("0");
                                rel_key.add("...ALL...");
                                Vector listRel = PstReligion.list(0, 0, "", " RELIGION ");
                                for (int i = 0; i < listRel.size(); i++) {
                                        Religion rel = (Religion) listRel.get(i);
                                        rel_key.add(rel.getReligion());
                                        rel_value.add(String.valueOf(rel.getOID()));
                                }
                          %>
                          <%= ControlCombo.draw(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_RELIGION],"formElemen",null, "" + srcSpecialEmployee.getReligion(), rel_value, rel_key, " size= 8 /*+  (listEmpCat!=null?listEmpCat.size()+1:0+1) +8*/ multiple onkeydown=\"javascript:fnTrapKD()\"") %>
                        </td>
                        <td>
                            <% 
                                Vector mar_value = new Vector(1,1);
                                Vector mar_key = new Vector(1,1); 
                                mar_value.add("0");
                                mar_key.add("...ALL...");
                                Vector listmar = PstMarital.list(0, 0, "", " MARITAL_CODE ");
                                for (int i = 0; i < listmar.size(); i++) {
                                        Marital mar = (Marital) listmar.get(i);
                                        mar_key.add(mar.getMaritalStatus() + " - " + mar.getNumOfChildren());
                                        mar_value.add(String.valueOf(mar.getOID()));
                                }
                          %>
                          <%= ControlCombo.draw(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_MARITAL],"formElemen",null, "" + srcSpecialEmployee.getMarital(), mar_value, mar_key, " size= 8 /*+  (listEmpCat!=null?listEmpCat.size()+1:0+1) +8*/ multiple onkeydown=\"javascript:fnTrapKD()\"") %>
                        </td>
                        <td>
                            <%
                                Vector bld_value = new Vector(1,1);
                                Vector bld_key = new Vector(1,1); 
                                bld_value.add("0");
                                bld_key.add("...ALL...");
                                Vector blood = PstEmployee.getBlood();
                                for (int i = 0; i < blood.size(); i++) {
                                    bld_key.add(blood.get(i));
                                    bld_value.add(blood.get(i));
                                }
                          %>
                          <%=ControlCombo.draw(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_BLOOD],"formElemen",null,"" + srcSpecialEmployee.getBlood(),bld_value,bld_key, " size= 8 /*+  (listEmpCat!=null?listEmpCat.size()+1:0+1) +8*/ multiple onkeydown=\"javascript:fnTrapKD()\"")%> 
                        </td>
                        <td>
                            <% 
                                Vector lang_value = new Vector(1,1);
                                Vector lang_key = new Vector(1,1); 
                                lang_value.add("0");
                                lang_key.add("...ALL...");
                                Vector listlang = PstLanguage.list(0, 0, "", " LANGUAGE ");
                                for (int i = 0; i < listlang.size(); i++) {
                                        Language lang = (Language) listlang.get(i);
                                        lang_key.add(lang.getLanguage());
                                        lang_value.add(String.valueOf(lang.getOID()));
                                }
                          %>
                          <%= ControlCombo.draw(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_LANGUAGE],"formElemen",null, "" + srcSpecialEmployee.getLanguage(), lang_value, lang_key," size= 8 /*+  (listEmpCat!=null?listEmpCat.size()+1:0+1) +8*/ multiple onkeydown=\"javascript:fnTrapKD()\"") %>
                        </td>
                        <td>
                            <% 
                                Vector race_value = new Vector(1,1);
                                Vector race_key = new Vector(1,1); 
                                race_value.add("0");
                                race_key.add("...ALL...");
                                Vector listrace = PstRace.list(0, 0, "", PstRace.fieldNames[PstRace.FLD_RACE_NAME]);
                                for (int i = 0; i < listrace.size(); i++) {
                                        Race race = (Race) listrace.get(i);
                                        race_key.add(race.getRaceName());
                                        race_value.add(String.valueOf(race.getOID()));
                                }
                          %>
                          <%= ControlCombo.draw(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_RACE],"formElemen",null, "" + srcSpecialEmployee.getRace(), race_value, race_key, " size=8 multiple onkeydown=\"javascript:fnTrapKD()\"") %>
                        </td>
                        <td>
                            <% 
                                Vector country_value = new Vector(1,1);
                                Vector country_key = new Vector(1,1); 
                                country_value.add("0");
                                country_key.add("...ALL...");
                                Vector listCountry = PstNegara.list(0, 0, "", PstNegara.fieldNames[PstNegara.FLD_NM_NEGARA]); 
                                for (int i = 0; i < listCountry.size(); i++) {
                                        Negara negara = (Negara) listCountry.get(i);
                                        country_key.add(negara.getNmNegara());
                                        country_value.add(String.valueOf(negara.getOID()));
                                }
                          %>
                          <%= ControlCombo.draw(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_COUNTRY],"formElemen",null, "" + srcSpecialEmployee.getCountryId(), country_value, country_key, " size=8 multiple onkeydown=\"javascript:fnTrapKD()\"") %>
                          <%//= ControlCombo.draw(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_COUNTRY],"formElemen",null, "" + srcSpecialEmployee.getCountryId(), country_value, country_key, " size=" + (listCountry.size()+1) + " multiple onkeydown=\"javascript:fnTrapKD()\"") %>
                        </td>
                        <td>
                            <% 
                                Vector province_value = new Vector(1,1);
                                Vector province_key = new Vector(1,1); 
                                province_value.add("0");
                                province_key.add("...ALL...");
                                Vector listProvince = PstProvinsi.list(0, 0, "", PstProvinsi.fieldNames[PstProvinsi.FLD_NM_PROVINSI]);
                                for (int i = 0; i < listProvince.size(); i++) {
                                        Provinsi provinsi = (Provinsi) listProvince.get(i);
                                        province_key.add(provinsi.getNmProvinsi()); 
                                        province_value.add(String.valueOf(provinsi.getOID()));
                                }
                          %>
                          <%= ControlCombo.draw(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_PROVINSI_ID],"formElemen",null, "" + srcSpecialEmployee.getProvinsiId(), province_value, province_key, " size=8 multiple onkeydown=\"javascript:fnTrapKD()\"") %>
                        </td>
                        <td style="background-color: #FFF" valign="top">
                            <input type="radio" name="<%=frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_RESIGNED]%>" value="0" checked />No<br>
                            <input type="radio" name="<%=frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_RESIGNED]%>" value="1">Yes<br>
                            <input type="radio" name="<%=frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_RESIGNED]%>" value="2">All 
                        </td>
                    </tr>
                </table>
                <table>
                    <tr>
                        <td valign="top" id="coloum_title">Regency</td>
                        <td valign="top" id="coloum_title">Sub-Regency</td>
                        <td valign="top" id="coloum_title">Country Permanent</td>
                        <td valign="top" id="coloum_title">Province Permanent</td>
                        <td valign="top" id="coloum_title">Regency Permanent</td>
                        <td valign="top" id="coloum_title">Sub-Regency Permanent</td>
                        <td valign="top" id="coloum_title">Sex</td>
                    </tr>
                    <tr>
                        <td>
                            <% 
                                Vector kabupaten_value = new Vector(1,1);
                                Vector kabupaten_key = new Vector(1,1); 
                                kabupaten_value.add("0");
                                kabupaten_key.add("...ALL...");
                                Vector listKabupaten= PstKabupaten.list(0, 0, "", PstKabupaten.fieldNames[PstKabupaten.FLD_NM_KABUPATEN]);
                                for (int i = 0; i < listKabupaten.size(); i++) {
                                        Kabupaten kabupaten = (Kabupaten) listKabupaten.get(i);
                                        kabupaten_key.add(kabupaten.getNmKabupaten());
                                        kabupaten_value.add(String.valueOf(kabupaten.getOID()));
                                }
                          %>
                          <%= ControlCombo.draw(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_KABUPATEN_ID],"formElemen",null, "" + srcSpecialEmployee.getKabupatenId(), kabupaten_value, kabupaten_key, " size=8 multiple onkeydown=\"javascript:fnTrapKD()\"") %>
                        </td>
                        <td>
                            <% 
                                Vector kecamatan_value = new Vector(1,1);
                                Vector kecamatan_key = new Vector(1,1); 
                                kecamatan_value.add("0");
                                kecamatan_key.add("...ALL...");
                                Vector listKecamatan = PstKecamatan.list(0, 0, "", PstKecamatan.fieldNames[PstKecamatan.FLD_NM_KECAMATAN]);
                                for (int i = 0; i < listKecamatan.size(); i++) {
                                        Kecamatan kecamatan = (Kecamatan) listKecamatan.get(i);
                                        kecamatan_key.add(kecamatan.getNmKecamatan());
                                        kecamatan_value.add(String.valueOf(kecamatan.getOID()));
                                }
                          %>
                          <%= ControlCombo.draw(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_KECAMATAN_ID],"formElemen",null, "" + srcSpecialEmployee.getKecamatanId(), kecamatan_value, kecamatan_key, " size=8 multiple onkeydown=\"javascript:fnTrapKD()\"") %>
                        </td>
                        <td>
                            <% 
                                Vector countryPerm_value = new Vector(1,1);
                                Vector countryPerm_key = new Vector(1,1); 
                                countryPerm_value.add("0");
                                countryPerm_key.add("...ALL...");
                                Vector listCountryPerm = PstNegara.list(0, 0, "", PstNegara.fieldNames[PstNegara.FLD_NM_NEGARA]); 
                                for (int i = 0; i < listCountryPerm.size(); i++) {
                                        Negara negaraPerm = (Negara) listCountry.get(i);
                                        countryPerm_key.add(negaraPerm.getNmNegara());
                                        countryPerm_value.add(String.valueOf(negaraPerm.getOID()));
                                }
                          %>
                          <%= ControlCombo.draw(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_COUNTRY_PERMANENT],"formElemen",null, "" + srcSpecialEmployee.getCountryIdPermanent(), countryPerm_value, countryPerm_key, " size=8 multiple onkeydown=\"javascript:fnTrapKD()\"") %>
                        </td>
                        <td>
                            <% 
                                    Vector provincePerm_value = new Vector(1,1);
                                    Vector provincePerm_key = new Vector(1,1); 
                                    provincePerm_value.add("0");
                                    provincePerm_key.add("...ALL...");
                                    Vector listProvincePerm = PstProvinsi.list(0, 0, "", PstProvinsi.fieldNames[PstProvinsi.FLD_NM_PROVINSI]);
                                    for (int i = 0; i < listProvincePerm.size(); i++) {
                                            Provinsi provinsiPerm = (Provinsi) listProvincePerm.get(i);
                                            provincePerm_key.add(provinsiPerm.getNmProvinsi()); 
                                            provincePerm_value.add(String.valueOf(provinsiPerm.getOID()));
                                    }
                              %>
                              <%= ControlCombo.draw(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_PROVINSI_ID_PERMANENT],"formElemen",null, "" + srcSpecialEmployee.getProvinsiIdPermanent(), provincePerm_value, provincePerm_key, " size=8 multiple onkeydown=\"javascript:fnTrapKD()\"") %>
                        </td>
                        <td>
                            <% 
                                    Vector kabupatenPerm_value = new Vector(1,1);
                                    Vector kabupatenPerm_key = new Vector(1,1); 
                                    kabupatenPerm_value.add("0");
                                    kabupatenPerm_key.add("...ALL...");
                                    Vector listKabupatenPerm= PstKabupaten.list(0, 0, "", PstKabupaten.fieldNames[PstKabupaten.FLD_NM_KABUPATEN]);
                                    for (int i = 0; i < listKabupatenPerm.size(); i++) {
                                            Kabupaten kabupatenPerm = (Kabupaten) listKabupaten.get(i);
                                            kabupatenPerm_key.add(kabupatenPerm.getNmKabupaten());
                                            kabupatenPerm_value.add(String.valueOf(kabupatenPerm.getOID()));
                                    }
                              %>
                              <%= ControlCombo.draw(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_KABUPATEN_ID_PERMANENT],"formElemen",null, "" + srcSpecialEmployee.getKabupatenIdPermanent(), kabupatenPerm_value, kabupatenPerm_key, " size=8 multiple onkeydown=\"javascript:fnTrapKD()\"") %>
                        </td>
                        <td>
                            <% 
                                Vector kecamatanPerm_value = new Vector(1,1);
                                Vector kecamatanPerm_key = new Vector(1,1); 
                                kecamatanPerm_value.add("0");
                                kecamatanPerm_key.add("...ALL...");
                                Vector listKecamatanPerm = PstKecamatan.list(0, 0, "", PstKecamatan.fieldNames[PstKecamatan.FLD_NM_KECAMATAN]);
                                for (int i = 0; i < listKecamatanPerm.size(); i++) {
                                        Kecamatan kecamatanPerm = (Kecamatan) listKecamatanPerm.get(i);
                                        kecamatanPerm_key.add(kecamatanPerm.getNmKecamatan());
                                        kecamatanPerm_value.add(String.valueOf(kecamatanPerm.getOID()));
                                }
                          %>
                          <%= ControlCombo.draw(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_KECAMATAN_ID_PERMANENT],"formElemen",null, "" + srcSpecialEmployee.getKecamatanIdPermanent(), kecamatanPerm_value, kecamatanPerm_key, " size=8 multiple onkeydown=\"javascript:fnTrapKD()\"") %>
                        </td>
                        <td style="background-color: #FFF; padding-right: 9px;" valign="top">
                            <input type="radio" name="<%=frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_SEX]%>" value="0"> Male<br>
                            <input type="radio" name="<%=frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_SEX]%>" value="1"> Female<br>
                            <input type="radio" name="<%=frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_SEX]%>" value="2" checked> All
                        </td>
                    </tr>
                </table>
            </div>
            <div class="content" style="border-top: 1px solid #CCC">
                <table>
                    <tr>
                        <td valign="middle" id="coloum_caption">Employee Until This Period</td>
                        <td><%=ControlDate.drawDateWithStyle(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_DATE_PERIOD_END], null, 0, -50,"formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%></td>
                    </tr>
                </table>
            </div>
            <div class="content" style="border-top: 1px solid #CCC">
                <table>
                    <tr>
                        <td valign="middle" id="coloum_caption">Sort By</td>
                        <td>
                            <%//= ControlCombo.draw(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_SORTBY],"formElemen",null, ""+srcSpecialEmployee.getSortby(), SrcSpecialEmployee.getOrderValue(), SrcSpecialEmployee.getOrderKey(), " onkeydown=\"javascript:fnTrapKD()\"") %>
                             <%= ControlCombo.draw(frmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_ORDER],"formElemen",null, ""+srcSpecialEmployee.getSortby(), SrcSpecialEmployee.getOrderValue(), SrcSpecialEmployee.getOrderKey(), " onkeydown=\"javascript:fnTrapKD()\"") %>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="content" style="border-top: 1px solid #CCC">
                <table  border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                      <td><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                      <td class="command" nowrap><div align="left"><a href="javascript:cmdSearch()">Search for Employee</a></div></td>
                      <!-- update by ramayu 2012-11-14 -->
                      <td width="5%" nowrap="nowrap"><a href="javascript:cmdExportToExcel()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnNewOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Export Employee"></a></td>
                      <td nowrap="nowrap"><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                      <td class="command" nowrap="nowrap"><a href="javascript:cmdExportToExcel()">Export To Excel</a></td>


                    </tr>
                    <tr>
                      <td><a href="srcemployee.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnBack.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="BAck Search Employee"></a></td>
                      <td><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                      <td class="command" nowrap><a href="srcemployee.jsp">Back Search Employee</a></td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                </table>
            </div>
          </form>
    </div>
    <div class="footer-page">
        <table>
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
            <tr>
                <td valign="bottom"><%@include file="../../footer.jsp" %></td>
            </tr>
            <%} else {%>
            <tr> 
                <td colspan="2" height="20" ><%@ include file = "../../main/footer.jsp" %></td>
            </tr>
            <%}%>
        </table>
    </div>
</body>
</html>
