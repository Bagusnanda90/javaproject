
<%@page import="com.dimata.harisma.form.masterdata.CtrlEmployeeCompetency"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmEmployeeCompetency"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmLanguage"%>
<% 
/* 
 * Page Name  		:  emplanguage.jsp
 * Created on 		:  [25-9-2002] [3.20] PM 
 * 
 * @author  		: lkarunia 
 * @version  		: 01 org
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
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK_LANG_N_COMPETENCE); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* OBJ_DATABANK_PERSONAL_DATA = 1; */
int appObjCodePer = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK_PERSONAL_DATA);
boolean privViewPer = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodePer, AppObjInfo.COMMAND_VIEW));
/* OBJ_DATABANK_FAMILY_MEMBER = 2; */
int appObjCodeFam = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK_FAMILY_MEMBER);
boolean privViewFam = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeFam, AppObjInfo.COMMAND_VIEW));
/* OBJ_DATABANK_LANG_N_COMPETENCE = 3; */
/* on top */
/* OBJ_DATABANK_EDUCATION = 4; */
int appObjCodeEdu = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK_EDUCATION);
boolean privViewEdu = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeEdu, AppObjInfo.COMMAND_VIEW));
/* OBJ_DATABANK_EXPERIENCE = 5; */
int appObjCodeExp = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK_EXPERIENCE);
boolean privViewExp = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeExp, AppObjInfo.COMMAND_VIEW));
/* OBJ_DATABANK_CAREERPATH = 6; */
int appObjCodeCar = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK_CAREERPATH);
boolean privViewCar = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeCar, AppObjInfo.COMMAND_VIEW));
/* OBJ_DATABANK_TRAINING = 7; */
int appObjCodeTra = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK_TRAINING);
boolean privViewTra = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeTra, AppObjInfo.COMMAND_VIEW));
/* OBJ_DATABANK_WARNING = 8; */
int appObjCodeWar = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK_WARNING);
boolean privViewWar = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeWar, AppObjInfo.COMMAND_VIEW));
/* OBJ_DATABANK_REPRIMAND = 9; */
int appObjCodeRep = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK_REPRIMAND);
boolean privViewRep = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeRep, AppObjInfo.COMMAND_VIEW));
/* OBJ_DATABANK_AWARD = 10; */
int appObjCodeAwr = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK_AWARD);
boolean privViewAwr = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeAwr, AppObjInfo.COMMAND_VIEW));
/* OBJ_DATABANK_PICTURE = 11; */
int appObjCodePic = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK_PICTURE);
boolean privViewPic = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodePic, AppObjInfo.COMMAND_VIEW));
/* OBJ_DATABANK_RELEVANT_DOC = 12; */
int appObjCodeRel = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK_RELEVANT_DOC);
boolean privViewRel = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeRel, AppObjInfo.COMMAND_VIEW));
/////
%>
<!-- Jsp Block -->
<%!
    public String getCompetencyName(long competencyId){
        String str = "";
        try {
            Competency competency = PstCompetency.fetchExc(competencyId);
            str = competency.getCompetencyName();
        } catch(Exception ex){
            System.out.println("getCompetency=>"+ex.toString());
        }
        return str;
    }

	public String drawList(Vector objectClass ,  long empLanguageId, boolean privUpdate, boolean privDelete)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("0");
		ctrlist.setListStyle("tblStyle");
                ctrlist.setTitleStyle("title_tbl");
                ctrlist.setCellStyle("listgensell");
                ctrlist.setHeaderStyle("title_tbl");
                ctrlist.setCellSpacing("0");
		ctrlist.addHeader("Language","");
		ctrlist.addHeader("Oral","");
		ctrlist.addHeader("Written","");
		ctrlist.addHeader("Description","");
                ctrlist.addHeader("&nbsp;","");
		Vector lstData = ctrlist.getData();
		ctrlist.reset();
		int index = -1;
                int no=0; 
		for (int i = 0; i < objectClass.size(); i++) {
                        no++;
                        EmpLanguage empLanguage = (EmpLanguage) objectClass.get(i);
                        Vector rowx = new Vector();
                        if (empLanguageId == empLanguage.getOID()) {
                            index = i;
                        }
                        Language language = new Language();
                        if (empLanguage.getLanguageId() != 0) {
                            try {
                                language = PstLanguage.fetchExc(empLanguage.getLanguageId());
                            } catch (Exception exc) {
                                language = new Language();
                            }
                        }
                            
                        rowx.add(language.getLanguage());
                        rowx.add(PstEmpLanguage.gradeKey[empLanguage.getOral()]);
                        rowx.add(PstEmpLanguage.gradeKey[empLanguage.getWritten()]);
                        rowx.add(empLanguage.getDescription());
                        String btnEdit = "";
                        String btnDel = "";
                        if (privUpdate == true){
                            btnEdit = "<button class=\"btn-small\" onclick=\"cmdEditLanguage('" + empLanguage.getOID() + "')\">e</button>";
                        }
                        if (privDelete == true){
                            btnDel = "<button class=\"btn-small\" onclick=\"cmdAskLanguage('" + empLanguage.getOID() + "')\">&times;</button>";
                        }
                        rowx.add(btnEdit+"&nbsp;"+btnDel);
                        lstData.add(rowx);
                    }

		return ctrlist.draw(index);
	}

        
        public String drawListEmpCompetencies(Vector objectClass ,  long competencyID, boolean privUpdate, boolean privDelete)
	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("0");
		ctrlist.setListStyle("tblStyle");
                ctrlist.setTitleStyle("title_tbl");
                ctrlist.setCellStyle("listgensell");
                ctrlist.setHeaderStyle("title_tbl");
                ctrlist.setCellSpacing("0");
		ctrlist.addHeader("Competencies","");
		ctrlist.addHeader("Level value","");
                ctrlist.addHeader("Date of Achieve","");
                ctrlist.addHeader("Special Achievement","");
                ctrlist.addHeader("&nbsp;");

		Vector lstData = ctrlist.getData();
		ctrlist.reset();
		int index = -1;
                int no=0; 
		for (int i = 0; i < objectClass.size(); i++) {
                    EmployeeCompetency empCompetency = (EmployeeCompetency)objectClass.get(i);
                    Vector rowx = new Vector();
                    rowx.add(getCompetencyName(empCompetency.getCompetencyId()));
                    rowx.add(""+empCompetency.getLevelValue());
                    rowx.add(""+empCompetency.getDateOfAchvmt());
                    rowx.add(empCompetency.getSpecialAchievement());
                    String btnEdit = "";
                    String btnDel = "";
                    if (privUpdate == true){
                        btnEdit = "<button class=\"btn-small\" onclick=\"javascript:cmdEditCompetency('"+empCompetency.getOID()+"')\">e</button>";
                    }
                    if (privDelete == true){
                        btnDel = "<button class=\"btn-small\" onclick=\"javascript:cmdAskCompetency('"+empCompetency.getOID()+"')\">&times;</button>";
                    }
                    rowx.add(btnEdit+"&nbsp;"+btnDel);
                    lstData.add(rowx);
		}

		return ctrlist.draw(index);
	}

%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    int proses = FRMQueryString.requestInt(request, "proses");
    long oidEmpLanguage = FRMQueryString.requestLong(request, "oid_emp_language");
    long oidEmpCompetency = FRMQueryString.requestLong(request, "oid_emp_competency");
    long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");
    long oidCompetency = FRMQueryString.requestLong(request, "competency_id");

    int iErrCode = FRMMessage.NONE;
    String whereClause = "";
    String orderClause = "";

    CtrlEmpLanguage ctrlEmpLanguage = new CtrlEmpLanguage(request);
    Vector listEmpLanguage = new Vector(1,1);
    
    whereClause = PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_EMPLOYEE_ID]+ " = "+oidEmployee;
    listEmpLanguage = PstEmpLanguage.list(0,0, whereClause, "");
    
    CtrlEmployeeCompetency ctrlEmpCompetency = new CtrlEmployeeCompetency(request);
    Vector listEmpCompetency = new Vector();
    
    whereClause = PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_EMPLOYEE_ID]+"="+oidEmployee;
    listEmpCompetency = PstEmployeeCompetency.list(0, 0, whereClause, "");
    
    if (proses > 0){
        if (proses == 1){
            iErrCode = ctrlEmpCompetency.action(iCommand, oidEmpCompetency, request, emplx.getFullName(), appUserIdSess);
            if (iCommand == Command.SAVE || iCommand == Command.DELETE){
                whereClause = PstEmployeeCompetency.fieldNames[PstEmployeeCompetency.FLD_EMPLOYEE_ID]+"="+oidEmployee;
                listEmpCompetency = PstEmployeeCompetency.list(0, 0, whereClause, "");
                iCommand = Command.NONE;
            }
        }
        if (proses == 2){
            iErrCode = ctrlEmpLanguage.action(iCommand , oidEmpLanguage, oidEmployee, request, emplx.getFullName(), appUserIdSess);
            if (iCommand == Command.SAVE || iCommand == Command.DELETE){
                whereClause = PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_EMPLOYEE_ID]+ " = "+oidEmployee;
                listEmpLanguage = PstEmpLanguage.list(0,0, whereClause , "");
                iCommand = Command.NONE;
            }
        }
    }
    FrmEmployeeCompetency frmEmpComp = ctrlEmpCompetency.getForm();
    EmployeeCompetency empCompetency = ctrlEmpCompetency.getEmployeeCompetency();
    
    FrmEmpLanguage frmEmpLanguage = ctrlEmpLanguage.getForm();
    EmpLanguage empLanguage = ctrlEmpLanguage.getEmpLanguage();

%>
<html><!-- #BeginTemplate "/Templates/maintab.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Competencies & Language</title>
<script language="JavaScript">
function getCmd(){
    document.frm.action = "emplanguage.jsp";
    document.frm.submit();
}

function cmdAddCompetency(){
    document.frm.proses.value="1";
    document.frm.oid_emp_competency.value="0";
    document.frm.command.value="<%=Command.ADD%>";
    getCmd();
}
function cmdAddLanguage(){
    document.frm.proses.value="2";
    document.frm.oid_emp_language.value="0";
    document.frm.command.value="<%=Command.ADD%>";
    getCmd();
}

function cmdAskCompetency(oidEmpCompetency){
    document.frm.proses.value="1";
    document.frm.oid_emp_competency.value=oidEmpCompetency;
    document.frm.command.value="<%=Command.ASK%>";
    getCmd();
}

function cmdAskLanguage(oidEmpLanguage){
    document.frm.proses.value="2";
    document.frm.oid_emp_language.value=oidEmpLanguage;
    document.frm.command.value="<%=Command.ASK%>";
    getCmd();
}

function cmdConfirmDeleteCompetency(oidEmpCompetency){
    document.frm.proses.value="1";
    document.frm.oid_emp_competency.value=oidEmpCompetency;
    document.frm.command.value="<%=Command.DELETE%>";
    getCmd();
}

function cmdConfirmDeleteLanguage(oidEmpLanguage){
    document.frm.proses.value="2";
    document.frm.oid_emp_language.value=oidEmpLanguage;
    document.frm.command.value="<%=Command.DELETE%>";
    getCmd();
}

function cmdSaveCompetency(){
    document.frm.proses.value="1";
    document.frm.command.value="<%=Command.SAVE%>";
    getCmd();
}

function cmdSaveLanguage(){
    document.frm.proses.value="2";
    document.frm.command.value="<%=Command.SAVE%>";
    getCmd();
}

function cmdEditCompetency(oidEmpCompetency){
    document.frm.proses.value="1";
    document.frm.oid_emp_competency.value=oidEmpCompetency;
    document.frm.command.value="<%=Command.EDIT%>";
    getCmd();
}

function cmdEditLanguage(oidEmpLanguage, oidEmployee){
    document.frm.proses.value="2";
    document.frm.oid_emp_language.value=oidEmpLanguage;
    document.frm.employee_oid.value=oidEmployee;
    document.frm.command.value="<%=Command.EDIT%>";
    getCmd();
}

function cmdCancelLanguage(oidEmpLanguage){
    document.frm.proses.value="2";
    document.frm.oid_emp_language.value=oidEmpLanguage;
    document.frm.command.value="<%=Command.EDIT%>";
    getCmd();
}

function cmdBack(){
    document.frm.command.value="<%=Command.BACK%>";
    getCmd();
}
	
function cmdBackEmp(empOID){
    document.frmemplanguage.employee_oid.value=empOID;
    document.frmemplanguage.command.value="<%=Command.EDIT%>";	
    getCmd();
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

    <style type="text/css">
        .tblStyle {border-collapse: collapse;font-size: 11px;}
        .tblStyle td {padding: 3px 5px; border: 1px solid #CCC;}
        .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
        .title_page {color:#0db2e1; font-weight: bold; font-size: 14px; background-color: #EEE; border-left: 1px solid #0099FF; padding: 12px 18px;}

        #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3;}
        #menu_title {color:#0099FF; font-size: 14px; font-weight: bold; font-family: sans-serif;}
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
            font-family: sans-serif;
            font-size: 12px;
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
        .info {
            padding: 21px;
            margin: 15px;
            background-color: #F5F5F5;
            color: #575757;
            border-radius: 3px;
        }
    </style>
    <style type="text/css">
            body {background-color: #EEE;}
            .header {
                
            }
            .content-main {
                background-color: #FFF;
                margin: 25px 23px 59px 23px;
                border: 1px solid #DDD;
                border-radius: 5px;
            }
            .content-info {
                padding: 21px;
                border-bottom: 1px solid #E5E5E5;
            }
            .content-title {
                padding: 21px;
                border-bottom: 1px solid #E5E5E5;
                margin-bottom: 5px;
                background-color: #EEE;
            }
            #title-large {
                color: #575757;
                font-size: 16px;
                font-weight: bold;
            }
            #title-small {
                color:#797979;
                font-size: 11px;
            }
            .content {
                padding: 21px;
            }
            .btn {
                background: #ebebeb;
                background-image: -webkit-linear-gradient(top, #ebebeb, #dddddd);
                background-image: -moz-linear-gradient(top, #ebebeb, #dddddd);
                background-image: -ms-linear-gradient(top, #ebebeb, #dddddd);
                background-image: -o-linear-gradient(top, #ebebeb, #dddddd);
                background-image: linear-gradient(to bottom, #ebebeb, #dddddd);
                -webkit-border-radius: 5;
                -moz-border-radius: 5;
                border-radius: 3px;
                font-family: Arial;
                color: #7a7a7a;
                font-size: 12px;
                padding: 5px 11px 5px 11px;
                border: solid #d9d9d9 1px;
                text-decoration: none;
            }

            .btn:hover {
                color: #474747;
                background: #ddd;
                background-image: -webkit-linear-gradient(top, #ddd, #CCC);
                background-image: -moz-linear-gradient(top, #ddd, #CCC);
                background-image: -ms-linear-gradient(top, #ddd, #CCC);
                background-image: -o-linear-gradient(top, #ddd, #CCC);
                background-image: linear-gradient(to bottom, #ddd, #CCC);
                text-decoration: none;
                border: 1px solid #C5C5C5;
            }
            
            .btn-small {
                padding: 3px; border: 1px solid #CCC; 
                background-color: #EEE; color: #777777; 
                font-size: 11px; cursor: pointer;
            }
            .btn-small:hover {border: 1px solid #999; background-color: #CCC; color: #FFF;}
            
            .tbl-main {border-collapse: collapse; font-size: 11px; background-color: #FFF; margin: 0px;}
            .tbl-main td {padding: 4px 7px; border: 1px solid #DDD; }
            #tbl-title {font-weight: bold; background-color: #F5F5F5; color: #575757;}
            
            .tbl-small {border-collapse: collapse; font-size: 11px; background-color: #FFF;}
            .tbl-small td {padding: 2px 3px; border: 1px solid #DDD; }
            
            #caption {padding: 7px 0px 2px 0px; font-size: 12px; font-weight: bold; color: #575757;}
            #div_input {}
            
            .form-style {
                font-size: 12px;
                color: #575757;
                border: 1px solid #DDD;
                border-radius: 5px;
            }
            .form-title {
                padding: 11px 21px;
                margin-bottom: 2px;
                border-bottom: 1px solid #DDD;
                background-color: #EEE;
                border-top-left-radius: 5px;
                border-top-right-radius: 5px;
                font-weight: bold;
            }
            .form-content {
                padding: 21px;
            }
            .form-footer {
                border-top: 1px solid #DDD;
                padding: 11px 21px;
                margin-top: 2px;
                background-color: #EEE;
                border-bottom-left-radius: 5px;
                border-bottom-right-radius: 5px;
            }
            #confirm {
                padding: 18px 21px;
                background-color: #FF6666;
                color: #FFF;
                border: 1px solid #CF5353;
            }
            #btn-confirm {
                padding: 3px; border: 1px solid #CF5353; 
                background-color: #CF5353; color: #FFF; 
                font-size: 11px; cursor: pointer;
            }
            .footer-page {
                
            }
            
        </style>
<link rel="stylesheet" href="<%=approot%>/javascripts/datepicker/themes/jquery.ui.all.css">
<script src="<%=approot%>/javascripts/jquery.js"></script>
<script src="<%=approot%>/javascripts/datepicker/jquery.ui.core.js"></script>
<script src="<%=approot%>/javascripts/datepicker/jquery.ui.widget.js"></script>
<script src="<%=approot%>/javascripts/datepicker/jquery.ui.datepicker.js"></script>
<script>
$(function() {
    $( "#datepicker1" ).datepicker({ dateFormat: "yy-mm-dd" });
});
</script>
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
            <span id="menu_title"><%=dictionaryD.getWord(I_Dictionary.EMPLOYEE)%> <strong style="color:#333;"> / </strong> <%=dictionaryD.getWord(I_Dictionary.COMPETENCIES) %> & <%=dictionaryD.getWord(I_Dictionary.LANGUAGE) %></span>
        </div>
        <% if (oidEmployee != 0) {%>
            <div class="navbar">
                <ul style="margin-left: 97px">
                    <% if (privViewPer == true){ %>
                    <li class=""> <a href="employee_edit.jsp?employee_oid=<%=oidEmployee%>&prev_command=<%=Command.EDIT%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.PERSONAL_DATA)%></a> </li>
                    <% } %>
                    <% if (privViewFam == true){ %>
                    <li class=""> <a href="familymember.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.FAMILY_MEMBER) %></a> </li>
                    <% } %>
                    <li class="active"><%=dictionaryD.getWord(I_Dictionary.COMPETENCIES) %></li>
                    <% if (privViewEdu == true){ %>
                    <li class=""> <a href="empeducation.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.EDUCATION) %></a> </li>
                    <% } %>
                    <% if (privViewExp == true){ %>
                    <li class=""> <a href="experience.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.EXPERIENCE) %></a> </li>
                    <% } %>
                    <% if (privViewCar == true){ %>
                    <li class=""> <a href="careerpath.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.CAREER_PATH) %></a> </li>
                    <% } %>
                    <% if (privViewTra == true){ %>
                    <li class=""> <a href="training.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.TRAINING_ON_DATABANK)%></a></li>
                    <% } %>
                    <% if (privViewWar == true){ %>
                    <li class=""> <a href="warning.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.WARNING) %></a> </li>
                    <% } %>
                    <% if (privViewRep == true){ %>
                    <li class=""> <a href="reprimand.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.REPRIMAND) %></a> </li>
                    <% } %>
                    <% if (privViewAwr == true){ %>
                    <li class=""> <a href="award.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.AWARD) %></a> </li>
                    <% } %>
                    <% if (privViewPic == true){ %>
                    <li class=""> <a href="picture.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.PICTURE) %></a> </li>
                    <% } %>
                    <% if (privViewRel == true){ %>
                    <li class=""> <a href="doc_relevant.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.RELEVANT_DOCS) %></a> </li>
                    <% } %>
                </ul>
            </div>
        <%}%>
        <div class="content-main">
            <form name="frm" method ="post" action="">
		<input type="hidden" name="command" value="<%=iCommand%>">
                <input type="hidden" name="proses" value="">
		<input type="hidden" name="employee_oid" value="<%=oidEmployee%>">
                <input type="hidden" name="oid_emp_competency" value="<%=oidEmpCompetency%>" />
		<input type="hidden" name="oid_emp_language" value="<%=oidEmpLanguage%>">
		<input type="hidden" name="competency_id" value="<%=oidCompetency%>">
                <div class="content-info">
                    <% 
                        if(oidEmployee != 0){
                        Employee employee = new Employee();
                        try{
                                employee = PstEmployee.fetchExc(oidEmployee);
                        }catch(Exception exc){
                                employee = new Employee();
                        }
                    %>
                        <table border="0" cellspacing="0" cellpadding="0" style="color: #575757">
                        <tr> 
                                <td valign="top" style="padding-right: 11px;"><strong><%=dictionaryD.getWord(I_Dictionary.PAYROLL)%> </strong></td>
                              <td valign="top"><%=employee.getEmployeeNum()%></td>
                        </tr>
                        <tr> 
                              <td valign="top" style="padding-right: 11px;"><strong><%=dictionaryD.getWord(I_Dictionary.NAME)%></strong></td>
                              <td valign="top"><%=employee.getFullName()%></td>
                        </tr>
                        <% Department department = new Department();
                              try{
                                department = PstDepartment.fetchExc(employee.getDepartmentId());
                              }catch(Exception exc){
                                department = new Department();
                              }
                        %>
                        <tr> 
                              <td valign="top" style="padding-right: 11px;"><strong><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></strong></td>
                              <td valign="top"><%=department.getDepartment()%></td>
                        </tr>
                        <tr> 
                                <td valign="top" style="padding-right: 11px;"><strong><%=dictionaryD.getWord(I_Dictionary.ADDRESS)%></strong></td>
                              <td valign="top"><%=employee.getAddress()%></td>
                        </tr>
                        </table>
                    <% } %>
                </div>
                
                <div class="content-title">
                    <div id="title-large">Employee Competency List</div>
                    <div id="title-small">Daftar kompetensi yang telah dimiliki.</div>
                </div>
                <div class="content">
                    <p style="margin-top: 2px">
                        <%
                        if (privAdd == true){
                            %>
                            <button class="btn" onclick="cmdAddCompetency()">Tambah Data</button>
                            <%
                        }
                        %>
                    </p>
                    <%
                    if (iCommand == Command.ASK && proses == 1){
                    %>
                    <table>
                        <tr>
                            <td valign="top">
                                <div id="confirm">
                                    <strong>Are you sure to delete item ?</strong> &nbsp;
                                    <button id="btn-confirm" onclick="javascript:cmdConfirmDeleteCompetency('<%=oidEmpCompetency%>')">Yes</button>
                                    &nbsp;<button id="btn-confirm" onclick="javascript:cmdBack()">No</button>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <%
                    }
                    %>
                    <% if ((iCommand == Command.ADD || iCommand == Command.EDIT) && proses == 1){ %>
                    <table cellpadding="0" cellspacing="0">
                        <tr>
                            <td valign="top">
                                <div class="form-style">
                                    <div class="form-title">Form Competency</div>
                                    <div class="form-content">
                                        <input type="hidden" name="<%= FrmEmployeeCompetency.fieldNames[FrmEmployeeCompetency.FRM_FIELD_EMPLOYEE_ID] %>" value="<%=oidEmployee%>">
                                        <input type="hidden" name="<%= FrmEmployeeCompetency.fieldNames[FrmEmployeeCompetency.FRM_FIELD_HISTORY] %>" value="0">
                                        <input type="hidden" name="<%= FrmEmployeeCompetency.fieldNames[FrmEmployeeCompetency.FRM_FIELD_PROVIDER_ID] %>" value="0">
                                        <div id="caption">Choose Competency</div>
                                        <div id="div_input">
                                            <select name="<%= FrmEmployeeCompetency.fieldNames[FrmEmployeeCompetency.FRM_FIELD_COMPETENCY_ID] %>">
                                                <option value="0">-SELECT-</option>
                                                <%
                                                Vector listCompetency = PstCompetency.list(0, 0, "", "");
                                                if (listCompetency != null && listCompetency.size()>0){
                                                    for(int c=0; c<listCompetency.size(); c++){
                                                        Competency competency = (Competency)listCompetency.get(c);
                                                        if (empCompetency.getCompetencyId()==competency.getOID()){
                                                            %>
                                                            <option value="<%=competency.getOID()%>" selected="selected"><%=competency.getCompetencyName()%></option>
                                                            <%
                                                        } else {
                                                        %>
                                                            <option value="<%=competency.getOID()%>"><%=competency.getCompetencyName()%></option>
                                                        <%
                                                        }
                                                    }                                               
                                                } 
                                                %>
                                            </select>
                                        </div>
                                        <div id="caption">Level Value</div>
                                        <div id="div_input">
                                            <input type="text" name="<%= FrmEmployeeCompetency.fieldNames[FrmEmployeeCompetency.FRM_FIELD_LEVEL_VALUE] %>" value="<%= empCompetency.getLevelValue() %>" />
                                        </div>
                                        <div id="caption">Date of Achievement</div>
                                        <div id="div_input">
                                            <input type="text" id="datepicker1" name="<%= FrmEmployeeCompetency.fieldNames[FrmEmployeeCompetency.FRM_FIELD_DATE_OF_ACHVMT] %>" value="<%= empCompetency.getDateOfAchvmt() %>" />
                                        </div>
                                        <div id="caption">Special Achievement</div>
                                        <div id="div_input">
                                            <textarea name="<%= FrmEmployeeCompetency.fieldNames[FrmEmployeeCompetency.FRM_FIELD_SPECIAL_ACHIEVEMENT] %>"><%= empCompetency.getSpecialAchievement() %></textarea>
                                        </div>
                                    </div>
                                    <div class="form-footer">
                                        <button class="btn" onclick="cmdSaveCompetency()">Save</button>
                                        <button class="btn" onclick="cmdBack()">Close</button>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <% 
                        }
                        if (listEmpCompetency != null && listEmpCompetency.size()>0){
                            %>
                            <%=drawListEmpCompetencies(listEmpCompetency, oidEmpCompetency, privUpdate, privDelete)%>
                            <%
                        } else {
                            %>
                            <p>No record available</p>
                            <%
                        }
                    %>
                </div>
                
                <div class="content-title">
                    <div id="title-large">Employee Language List</div>
                    <div id="title-small">Daftar kemampuan berbahasa yang telah dimiliki.</div>
                </div>
                <div class="content">
                    <p style="margin-top: 2px">
                        <%
                        if (privAdd == true){
                            %>
                            <button class="btn" onclick="cmdAddLanguage()">Tambah Data</button>
                            <%
                        }
                        %>
                    </p>
                    <%
                    if (iCommand == Command.ASK && proses == 2){
                    %>
                    <table>
                        <tr>
                            <td valign="top">
                                <div id="confirm">
                                    <strong>Are you sure to delete item ?</strong> &nbsp;
                                    <button id="btn-confirm" onclick="javascript:cmdConfirmDeleteLanguage('<%=oidEmpLanguage%>')">Yes</button>
                                    &nbsp;<button id="btn-confirm" onclick="javascript:cmdBack()">No</button>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <%
                    }
                    %>
                    <% if ((iCommand == Command.ADD || iCommand == Command.EDIT)&& proses == 2){ %>
                    <table cellpadding="0" cellspacing="0">
                        <tr>
                            <td valign="top">
                                <div class="form-style">
                                    <div class="form-title">Form Language</div>
                                    <div class="form-content">
                                        <div id="caption">Choose Language</div>
                                        <div id="div_input">
                                            <% Vector listLanguage = PstLanguage.listAll();
                                                Vector language_value = new Vector(1, 1);
                                                Vector language_key = new Vector(1, 1);
                                                language_value.add("0");
                                                language_key.add("-SELECT-");
                                                for (int i = 0; i < listLanguage.size(); i++) {
                                                    Language language = (Language) listLanguage.get(i);
                                                    language_value.add("" + language.getOID());
                                                    language_key.add("" + language.getLanguage());
                                                }
                                            %>
                                            <%= ControlCombo.draw(FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_LANGUAGE_ID],"formElemen",null, ""+empLanguage.getLanguageId(), language_value, language_key) %>
                                        </div>
                                        <div id="caption">Oral</div>
                                        <div id="div_input">
                                            <%= ControlCombo.draw(FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_ORAL],"formElemen", null, ""+empLanguage.getOral(), PstEmpLanguage.getGradeValue(), PstEmpLanguage.getGradeKey()) %>
                                        </div>
                                        <div id="caption">Written</div>
                                        <div id="div_input">
                                            <%= ControlCombo.draw(FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_WRITTEN],null, ""+empLanguage.getWritten(), PstEmpLanguage.getGradeValue(), PstEmpLanguage.getGradeKey()) %>
                                        </div>
                                        <div id="caption">Description</div>
                                        <div id="div_input">
                                            <textarea name="<%=FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_DESCRIPTION]%>" cols="60" rows="6"><%= empLanguage.getDescription() %></textarea>
                                        </div>
                                    </div>
                                    <div class="form-footer">
                                        <button class="btn" onclick="cmdSaveLanguage()">Save</button>
                                        <button class="btn" onclick="cmdBack()">Close</button>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <% 
                        }
                        if (listEmpLanguage != null && listEmpLanguage.size()>0){
                            %>
                            <%= drawList(listEmpLanguage, oidEmpLanguage, privUpdate, privDelete)%>
                            <%
                        } else {
                            %>
                            <p>No record available</p>
                            <%
                        }
                    %>
                    
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
