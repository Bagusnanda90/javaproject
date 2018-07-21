
<%@page import="com.dimata.common.entity.contact.ContactClassAssign"%>
<%@page import="com.dimata.common.entity.contact.PstContactClassAssign"%>
<%@page import="com.dimata.common.entity.contact.ContactClass"%>
<%@page import="com.dimata.common.entity.contact.PstContactClass"%>
<%@page import="com.dimata.common.entity.contact.ContactList"%>
<%@page import="com.dimata.common.entity.contact.PstContactList"%>
<% 
/* 
 * Page Name  		:  empeducation.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		:  [authorName] 
 * @version  		:  [version] 
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
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK_EDUCATION); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* OBJ_DATABANK_PERSONAL_DATA = 1; */
int appObjCodePer = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK_PERSONAL_DATA);
boolean privViewPer = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodePer, AppObjInfo.COMMAND_VIEW));
/* OBJ_DATABANK_FAMILY_MEMBER = 2; */
int appObjCodeFam = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK_FAMILY_MEMBER);
boolean privViewFam = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeFam, AppObjInfo.COMMAND_VIEW));
/* OBJ_DATABANK_LANG_N_COMPETENCE = 3; */
int appObjCodeLang = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK_LANG_N_COMPETENCE);
boolean privViewLang = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeLang, AppObjInfo.COMMAND_VIEW));
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

	public String drawList(Vector objectClass ,  long empEducationId, boolean privUpdate, boolean privDelete)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("tblStyle");
                ctrlist.setTitleStyle("title_tbl");
                ctrlist.setCellStyle("listgensell");
                ctrlist.setHeaderStyle("title_tbl");
                ctrlist.setCellSpacing("0");
		ctrlist.addHeader("Education","");
                ctrlist.addHeader("Nama Sekolah/Instansi","");
                ctrlist.addHeader("Detail","");
		ctrlist.addHeader("Start Year","");
		ctrlist.addHeader("End Year","");
                ctrlist.addHeader("Point");
                ctrlist.addHeader("Description");
                ctrlist.addHeader("&nbsp;");

		Vector lstData = ctrlist.getData();
		ctrlist.reset();
		int index = -1;

		for (int i = 0; i < objectClass.size(); i++) {
			EmpEducation empEducation = (EmpEducation)objectClass.get(i);
			 Vector rowx = new Vector();
			 if(empEducationId == empEducation.getOID())
				 index = i;

			
                        String eduString = "-";
			if(empEducation.getEducationId() != 0){
				try{
                                    Education education = PstEducation.fetchExc(empEducation.getEducationId());
                                    eduString = education.getEducation();
				}catch(Exception exc){
                                    System.out.println(""+exc.toString());
				}
			}
			String contactName = "-";
                        if (empEducation.getInstitutionId()!=0){
                            try {
                                ContactList conList = PstContactList.fetchExc(empEducation.getInstitutionId());
                                contactName = conList.getCompName();
                            } catch(Exception e){
                                System.out.print(""+e.toString());
                            }
                        }
                        
			rowx.add(eduString);
                        rowx.add(contactName);
                        rowx.add(empEducation.getGraduation());
			rowx.add(String.valueOf(empEducation.getStartDate()));
			rowx.add(String.valueOf(empEducation.getEndDate()));
                        rowx.add(""+empEducation.getPoint());
                        rowx.add(empEducation.getEducationDesc());
                        String btnEdit = "";
                        String btnDel = "";
                        if (privUpdate == true){
                            btnEdit = "<button class=\"btn-small\" onclick=\"cmdEdit('"+empEducation.getOID()+"')\">e</button>";
                        }
                        if (privDelete == true){
                            btnDel = "<button class=\"btn-small\" onclick=\"cmdAsk('"+empEducation.getOID()+"')\">&times;</button>";
                        }
                        rowx.add(btnEdit+"&nbsp;"+btnDel);
			lstData.add(rowx);
		}

		return ctrlist.draw(index);
	}

%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidEmpEducation = FRMQueryString.requestLong(request, "emp_education_id");
    long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");
    //System.out.println("===> oidEmployee=" + oidEmployee);
    /*variable declaration*/
    int recordToGet = 10;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    String whereClause = PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMPLOYEE_ID]+ " = "+oidEmployee;
    String orderClause = PstEmpEducation.fieldNames[PstEmpEducation.FLD_START_DATE];

    CtrlEmpEducation ctrlEmpEducation = new CtrlEmpEducation(request);
    ControlLine ctrLine = new ControlLine();
    Vector listEmpEducation = new Vector(1,1);

    /*switch statement */
    iErrCode = ctrlEmpEducation.action(iCommand , oidEmpEducation, oidEmployee, request, emplx.getFullName(), appUserIdSess);
    /* end switch*/
    FrmEmpEducation frmEmpEducation = ctrlEmpEducation.getForm();

    /*count list All EmpEducation*/
    int vectSize = PstEmpEducation.getCount(whereClause);

    EmpEducation empEducation = ctrlEmpEducation.getEmpEducation();
    msgString =  ctrlEmpEducation.getMessage();

    /*switch list EmpEducation*/
    if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)&& (oidEmpEducation == 0))
            start = PstEmpEducation.findLimitStart(empEducation.getOID(),recordToGet, whereClause, orderClause);

    if((iCommand == Command.FIRST || iCommand == Command.PREV )||
      (iCommand == Command.NEXT || iCommand == Command.LAST)){
                    start = ctrlEmpEducation.actionList(iCommand, start, vectSize, recordToGet);
     } 
    /* end switch list*/

    /* get record to display */
    listEmpEducation = PstEmpEducation.list(start,recordToGet, whereClause , orderClause);

    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listEmpEducation.size() < 1 && start > 0)
    {
             if (vectSize - recordToGet > recordToGet)
                            start = start - recordToGet;   //go to Command.PREV
             else{
                     start = 0 ;
                     iCommand = Command.FIRST;
                     prevCommand = Command.FIRST; //go to Command.FIRST
             }
             listEmpEducation = PstEmpEducation.list(start,recordToGet, whereClause , orderClause);
    }
%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Education</title>
<script language="JavaScript">


function cmdAdd(){
	document.frmempeducation.emp_education_id.value="0";
	document.frmempeducation.command.value="<%=Command.ADD%>";
	document.frmempeducation.prev_command.value="<%=prevCommand%>";
	document.frmempeducation.action="empeducation.jsp";
	document.frmempeducation.submit();
}

function cmdAsk(oidEmpEducation){
	document.frmempeducation.emp_education_id.value=oidEmpEducation;
	document.frmempeducation.command.value="<%=Command.ASK%>";
	document.frmempeducation.prev_command.value="<%=prevCommand%>";
	document.frmempeducation.action="empeducation.jsp";
	document.frmempeducation.submit();
}

function cmdConfirmDelete(oidEmpEducation){
	document.frmempeducation.emp_education_id.value=oidEmpEducation;
	document.frmempeducation.command.value="<%=Command.DELETE%>";
	document.frmempeducation.prev_command.value="<%=prevCommand%>";
	document.frmempeducation.action="empeducation.jsp";
	document.frmempeducation.submit();
}
function cmdSave(){
	document.frmempeducation.command.value="<%=Command.SAVE%>";
	document.frmempeducation.prev_command.value="<%=prevCommand%>";
	document.frmempeducation.action="empeducation.jsp";
	document.frmempeducation.submit();
	}

function cmdEdit(oidEmpEducation){
	document.frmempeducation.emp_education_id.value=oidEmpEducation;
	document.frmempeducation.command.value="<%=Command.EDIT%>";
	document.frmempeducation.prev_command.value="<%=prevCommand%>";
	document.frmempeducation.action="empeducation.jsp";
	document.frmempeducation.submit();
	}

function cmdBackEmp(empOID){
	document.frmempeducation.employee_oid.value=empOID;
	document.frmempeducation.command.value="<%=Command.EDIT%>";	
	document.frmempeducation.action="employee_edit.jsp";
	document.frmempeducation.submit();
	}

function cmdCancel(oidEmpEducation){
	document.frmempeducation.emp_education_id.value=oidEmpEducation;
	document.frmempeducation.command.value="<%=Command.EDIT%>";
	document.frmempeducation.prev_command.value="<%=prevCommand%>";
	document.frmempeducation.action="empeducation.jsp";
	document.frmempeducation.submit();
}

function cmdBack(){
	document.frmempeducation.command.value="<%=Command.BACK%>";
	document.frmempeducation.action="empeducation.jsp";
	document.frmempeducation.submit();
	}

function cmdListFirst(){
	document.frmempeducation.command.value="<%=Command.FIRST%>";
	document.frmempeducation.prev_command.value="<%=Command.FIRST%>";
	document.frmempeducation.action="empeducation.jsp";
	document.frmempeducation.submit();
}

function cmdListPrev(){
	document.frmempeducation.command.value="<%=Command.PREV%>";
	document.frmempeducation.prev_command.value="<%=Command.PREV%>";
	document.frmempeducation.action="empeducation.jsp";
	document.frmempeducation.submit();
	}

function cmdListNext(){
	document.frmempeducation.command.value="<%=Command.NEXT%>";
	document.frmempeducation.prev_command.value="<%=Command.NEXT%>";
	document.frmempeducation.action="empeducation.jsp";
	document.frmempeducation.submit();
}

function cmdListLast(){
	document.frmempeducation.command.value="<%=Command.LAST%>";
	document.frmempeducation.prev_command.value="<%=Command.LAST%>";
	document.frmempeducation.action="empeducation.jsp";
	document.frmempeducation.submit();
}

//-------------- script control line -------------------
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

</script>

<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<SCRIPT language=JavaScript>
    function hideObjectForEmployee(){
        //document.frmsrcemployee.<%//=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_START_COMMENC] + "_mn"%>.style.visibility = 'hidden';
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
        //document.all.<%//=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_START_COMMENC] + "_mn"%>.style.visibility = "";
    }
</SCRIPT>
<style type="text/css">
    .tblStyle {border-collapse: collapse;font-size: 11px;}
    .tblStyle td {padding: 3px 5px; border: 1px solid #CCC;}
    .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
    .title_page {color:#0db2e1; font-weight: bold; font-size: 14px; background-color: #EEE; border-left: 1px solid #0099FF; padding: 12px 18px;}

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
    
    .form-style {
        color: #575757;
        border: 1px solid #CCC;
        border-radius: 5px;
        background-color: #F7F7F7;
        margin: 21px;
    }
    .form-title {
        padding: 11px 21px;
        margin-bottom: 2px;
        border-bottom: 1px solid #CCC;
        background-color: #EEE;
        border-top-left-radius: 5px;
        border-top-right-radius: 5px;
        font-weight: bold;
    }
    .form-content {
        padding: 21px;
    }
    .form-footer {
        border-top: 1px solid #CCC;
        padding: 11px 21px;
        margin-top: 2px;
        background-color: #EEE;
        border-bottom-left-radius: 5px;
        border-bottom-right-radius: 5px;
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
    .btn-small {
        padding: 3px; border: 1px solid #CCC; 
        background-color: #EEE; color: #777777; 
        font-size: 11px; cursor: pointer;
    }
    .btn-small:hover {border: 1px solid #999; background-color: #CCC; color: #FFF;}
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
            <span id="menu_title"><%=dictionaryD.getWord(I_Dictionary.EMPLOYEE)%> <strong style="color:#333;"> / </strong> <%=dictionaryD.getWord(I_Dictionary.EDUCATION) %></span>
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
                    <% if (privViewLang == true){ %>
                    <li class=""> <a href="emplanguage.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.COMPETENCIES) %></a> </li>
                    <% } %>
                    <li class="active"><%=dictionaryD.getWord(I_Dictionary.EDUCATION) %></li>
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
            <form name="frmempeducation" method ="post" action="">
              <input type="hidden" name="command" value="<%=iCommand%>">
              <input type="hidden" name="vectSize" value="<%=vectSize%>">
              <input type="hidden" name="start" value="<%=start%>">
              <input type="hidden" name="prev_command" value="<%=prevCommand%>">
              <input type="hidden" name="emp_education_id" value="<%=oidEmpEducation%>">
              <input type="hidden" name="employee_oid" value="<%=oidEmployee%>">
              <input type="hidden" name="<%=frmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_EMPLOYEE_ID] %>" value="<%=oidEmployee%>">
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
                                <td valign="top" style="padding-right: 11px;"><strong>Payroll Number</strong></td>
                              <td valign="top"><%=employee.getEmployeeNum()%></td>
                        </tr>
                        <tr> 
                              <td valign="top" style="padding-right: 11px;"><strong>Name</strong></td>
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
                                <td valign="top" style="padding-right: 11px;"><strong>Address</strong></td>
                              <td valign="top"><%=employee.getAddress()%></td>
                        </tr>
                        </table>
                    <% } %>
                </div>
                <div class="content-title">
                    <div id="title-large">Employee Education List</div>
                    <div id="title-small">Daftar pendidikan karyawan.</div>
                </div>
                <div class="content">
                    <p style="margin-top: 2px">
                        <%
                        if (privAdd == true){
                            %>
                            <button class="btn" onclick="cmdAdd()">Tambah Data</button>
                            <%
                        }
                        %>
                    </p>
                    <%
                    if (iCommand == Command.ASK){
                    %>
                    <table>
                        <tr>
                            <td valign="top">
                                <div id="confirm">
                                    <strong>Are you sure to delete item ?</strong> &nbsp;
                                    <button id="btn-confirm" onclick="javascript:cmdConfirmDelete('<%=oidEmpEducation%>')">Yes</button>
                                    &nbsp;<button id="btn-confirm" onclick="javascript:cmdBack()">No</button>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <%
                    }
                    %>
                    <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmEmpEducation.errorSize()>0)||(iCommand==Command.EDIT)){%>
                    
                        <div class="form-style" style="width: 35%">
                            <div class="form-title">Form Pendidikan</div>
                            <div class="form-content">
                                <div id="caption"><%=dictionaryD.getWord(I_Dictionary.EDUCATION) %></div>
                                <div id="div_input">
                                    <%  Vector education_value = new Vector(1,1);
                                        Vector education_key = new Vector(1,1);																	
                                        Vector listEducation = PstEducation.listAll();
                                        for(int i=0;i<listEducation.size();i++){
                                            Education education = (Education) listEducation.get(i);
                                            education_value.add(""+education.getOID());
                                            education_key.add(""+education.getEducation());
                                        }
                                    %>
                                    <% if((listEducation != null) && (listEducation.size() > 0)){%>
                                    <%= ControlCombo.draw(frmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_EDUCATION_ID],"formElemen",null, ""+empEducation.getEducationId(), education_value, education_key) %> 
                                    <% }else {%>
                                    <font class="comment">No 
                                    Education available</font> 
                                    <% }%>
                                    * <%= frmEmpEducation.getErrorMsg(FrmEmpEducation.FRM_FIELD_EDUCATION_ID) %> 
                                </div>
                                <div id="caption">Start Year</div>
                                <div id="div_input">
                                    <%=	ControlDate.drawDateYear(frmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_START_DATE], empEducation.getStartDate(),"formElemen",-65,0) %> 
                                    to <%=	ControlDate.drawDateYear(frmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_END_DATE], empEducation.getEndDate(),"formElemen",-65,0) %> 
                                    * 
                                    <% String strStart = frmEmpEducation.getErrorMsg(frmEmpEducation.FRM_FIELD_START_DATE);
                                     String strEnd = frmEmpEducation.getErrorMsg(frmEmpEducation.FRM_FIELD_END_DATE);
                                     System.out.println("strStart "+strStart);
                                     System.out.println("strEnd "+strEnd);
                                     if((strStart.length()>0)&&(strEnd.length()>0)){
                                            %>
                                    <%= strStart %> 
                                    <%}else{
                                            if((strStart.length()>0)||(strEnd.length()>0)){%>
                                    <%= strStart.length()>0?strStart:strEnd %> 
                                    <% }
                                    }%>
                                </div>
                                <div id="caption">Universitas / Institusi</div>
                                <div id="div_input">
                                    <select name="<%=frmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_INSTITUTION_ID] %>">
                                        <option value="0">-SELECT-</option>
                                        <%
                                        /**
                                        * Update by Hendra Putu | 2015-11-18
                                        */
                                        // select contact_class where contact type = 13 [Institution] . result get contact class id
                                        String whereCClass = PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE]+"=13";
                                        Vector listCClass = PstContactClass.list(0, 0, whereCClass, "");
                                        long cClassId = 0;
                                        if (listCClass != null && listCClass.size()>0){
                                            ContactClass cclass = (ContactClass)listCClass.get(0);
                                            cClassId = cclass.getOID();
                                        }
                                        String inContactId = "";
                                        if (cClassId != 0){
                                            String whereCAssign = PstContactClassAssign.fieldNames[PstContactClassAssign.FLD_CNT_CLS_ID] +"="+cClassId;
                                            Vector listCAssign = PstContactClassAssign.list(0, 0, whereCAssign, "");
                                            if (listCAssign != null && listCAssign.size()>0){
                                                for(int a=0; a<listCAssign.size(); a++){
                                                    ContactClassAssign cA = (ContactClassAssign)listCAssign.get(a);
                                                    inContactId += cA.getContactId()+",";
                                                }
                                                inContactId += "0";
                                            }
                                        }
                                        
                                        
                                        String whereContact = PstContactList.fieldNames[PstContactList.FLD_CONTACT_ID]+" IN("+inContactId+")";
                                        Vector listContact = PstContactList.list(0, 0, whereContact, "");
                                        if (listContact != null && listContact.size()>0){
                                            for(int i=0; i<listContact.size(); i++){
                                                ContactList contact = (ContactList)listContact.get(i);
                                                if (empEducation.getInstitutionId()==contact.getOID()){
                                                    %>
                                                    <option selected="selected" value="<%=contact.getOID()%>"><%=contact.getCompName()%></option>
                                                    <%
                                                } else {
                                                    %>
                                                    <option value="<%=contact.getOID()%>"><%=contact.getCompName()%></option>
                                                    <%
                                                }
                                                %>
                                                
                                                <%
                                            }
                                        }
                                        %>
                                    </select>
                                </div>
                                <div id="caption">Detail</div>
                                <div id="div_input"><input type="text" name="<%=frmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_GRADUATION] %>" size="45"  value="<%= empEducation.getGraduation() %>" class="formElemen"></div>
                                <div id="caption">Point</div>
                                <div id="div_input"><input type="text" name="<%=frmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_POINT] %>" value="<%=empEducation.getPoint()%>" /></div>
                                <div id="caption">Description</div>
                                <div id="div_input"><textarea name="<%=frmEmpEducation.fieldNames[FrmEmpEducation.FRM_FIELD_EDUCATION_DESC]%>" class="formElemen" rows="2" cols="40"><%=empEducation.getEducationDesc()%></textarea></div>
                            </div>
                            <div class="form-footer">
                                <button class="btn" onclick="cmdSave()">Save</button>
                                <button class="btn" onclick="cmdBack()">Close</button>
                            </div>
                        </div>
                           
                    <% 
                        }
                        if (listEmpEducation != null && listEmpEducation.size()>0){
                            %>
                            <%= drawList(listEmpEducation, oidEmpEducation, privUpdate, privDelete)%>
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
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
