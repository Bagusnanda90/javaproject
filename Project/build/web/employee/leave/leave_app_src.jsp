<%-- 
    Class Name   : template.jsp
    Author       : Dian Putra
    Created      : Apr 17, 2016 10:59:14 PM
    Function     : This is template
--%>

<%@page import="com.dimata.harisma.form.leave.FrmLeaveApplication"%>
<%@page import="com.dimata.harisma.entity.masterdata.leaveconfiguration.PstLeaveConfigurationMainRequestOnly"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayGeneral"%>
<%@page import="com.dimata.harisma.entity.masterdata.leaveconfiguration.PstLeaveConfigurationMain"%>
<%-- 
    Document   : Leave_App
    Created on : Dec 26, 2009, 9:42:49 AM
    Author     : Tu Roy
--%>

<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.session.leave.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_LEAVE_MANAGEMENT, AppObjInfo.OBJ_LEAVE_APP_SRC);%>
<%@ include file = "../../main/checkuser.jsp" %>
<%@page import="com.dimata.harisma.entity.masterdata.PstPosition"%>
<%@page import="com.dimata.system.entity.PstSystemProperty"%>
<%@page import="com.dimata.harisma.session.leave.SessLeaveApplication"%>
<%@page import="com.dimata.harisma.entity.leave.PstLeaveApplication"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.entity.search.SrcLeaveApp"%>
<%@page import="com.dimata.harisma.form.search.FrmSrcLeaveApp"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstPublicLeaveDetail"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="java.util.Vector"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    
    
    
    long hrdDepOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    long sdmDivisionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_SDM_DIVISION)));
    boolean isHRDLogin = hrdDepOid == departmentOid ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
    long oidLeave = FRMQueryString.requestLong(request, "hidden_leave_application_id");

    int iCommand = FRMQueryString.requestCommand(request);
    
    long oidCompany = FRMQueryString.requestLong(request,FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_COMPANY_ID]);
    long oidDivision = FRMQueryString.requestLong(request,FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_DIVISION_ID]);
    long oidDepartment = FRMQueryString.requestLong(request,FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_DEPARTMENT]);
    long oidSection = FRMQueryString.requestLong(request,FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_SECTION]);
    
    String whereClause = "";
    String order = "";
    Vector listCompany = new Vector();
    Vector listDivision = new Vector();
    Vector listDepartment = new Vector();
    Vector listSection = new Vector();
    
    SrcLeaveApp objSrcLeaveApp = new SrcLeaveApp();
    FrmSrcLeaveApp objFrmSrcLeaveApp = new FrmSrcLeaveApp();

//SessLeaveClosing.getPeriode(new Date());
//update by satrya 2013-09-19
    if (iCommand == Command.GOTO) {
        objFrmSrcLeaveApp = new FrmSrcLeaveApp(request, objSrcLeaveApp);
        objFrmSrcLeaveApp.requestEntityObject(objSrcLeaveApp);
    }
    if (iCommand == Command.BACK) {
        objFrmSrcLeaveApp = new FrmSrcLeaveApp(request, objSrcLeaveApp);
        try {
            objSrcLeaveApp = (SrcLeaveApp) session.getValue(SessLeaveApplication.SESS_SRC_LEAVE_APPLICATION);
            if (objSrcLeaveApp == null) {
                objSrcLeaveApp = new SrcLeaveApp();
            }
        } catch (Exception e) {
            objSrcLeaveApp = new SrcLeaveApp();
        }
    }

// pengecekan status user yang login utk menentukan status approval mana yang mestinya default buatnya\
    boolean isManager = false;
    boolean isHrManager = false;
    if (positionType == PstPosition.LEVEL_MANAGER) {
        isManager = true;

        long hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
        if (departmentOid == hrdDepartmentOid) {
            isHrManager = true;
        }
    }
    //update by satrya 2012-11-1
    /**
     * untuk pengaturan apakah departement itu dpt exsekusi leave
                *
     */
    int isAdminExecuteLeave = 0;//hanya cuti full day jika fullDayLeave = 0
    try {
        isAdminExecuteLeave = Integer.parseInt(PstSystemProperty.getValueByName("LEAVE_EXCECUTE_BY_ADMIN"));
    } catch (Exception ex) {
        System.out.println("Execption LEAVE_EXCECUTE_BY_ADMIN " + ex);
        isAdminExecuteLeave = 0;

    }
%>

<script language="JavaScript">
            function cmdChangeComp(oid){
                document.frmsrcleaveapp.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmsrcleaveapp.<%=FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_COMPANY_ID]%>.value=oid;
                document.frmsrcleaveapp.action="leave_app_src.jsp";
                document.frmsrcleaveapp.submit();
            }
            function cmdChangeDivi(oid){
                document.frmsrcleaveapp.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmsrcleaveapp.<%=FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_DIVISION_ID]%>.value=oid;
                document.frmsrcleaveapp.action="leave_app_src.jsp";
                document.frmsrcleaveapp.submit();
            }
            function cmdChangeDept(oid){
                document.frmsrcleaveapp.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmsrcleaveapp.<%=FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_DEPARTMENT]%>.value=oid;
                document.frmsrcleaveapp.action="leave_app_src.jsp";
                document.frmsrcleaveapp.submit();
            }
            function cmdAdd(){
                document.frmsrcleaveapp.command.value="<%=String.valueOf(Command.ADD)%>";
                document.frmsrcleaveapp.action="leave_app_edit.jsp";
                document.frmsrcleaveapp.submit();
            }

    function cmdUpdateDiv        (){
                document.frmsrcleaveapp.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmsrcleaveapp.action="leave_app_src.jsp";
                document.frmsrcleaveapp.submit();
            }
            function cmdUpdateDep(){
                document.frmsrcleaveapp.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmsrcleaveapp.action="leave_app_src.jsp";
                document.frmsrcleaveapp.submit();
            }
            function cmdUpdatePos(){
                document.frmsrcleaveapp.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmsrcleaveapp.action="leave_app_src.jsp";
                document.frmsrcleaveapp.submit();
            }

            

            function cmdSearch(){
                document.frmsrcleaveapp.command.value="<%=String.valueOf(Command.LIST)%>";
                document.frmsrcleaveapp.action="leave_app_list.jsp";
                document.frmsrcleaveapp.submit();
            }
            function cmdSearchExecute(){
                document.frmsrcleaveapp.command.value="<%=String.valueOf(Command.LIST)%>";
                document.frmsrcleaveapp.action="leave_app_execute.jsp";
                document.frmsrcleaveapp.submit();
            }
            function cmdSearchStatusToBeApp(){
                document.frmsrcleaveapp.command.value="<%=String.valueOf(Command.LIST)%>";
                document.frmsrcleaveapp.action="leave_app_to_be_app.jsp";
                document.frmsrcleaveapp.submit();
            }
            function cmdSearchStatusApprove(){
                document.frmsrcleaveapp.command.value="<%=String.valueOf(Command.LIST)%>";
                document.frmsrcleaveapp.action="leave_application_approve.jsp";
                document.frmsrcleaveapp.submit();
            }

            //-------------------------- for Calendar -------------------------
            function getThn(){
            <%
            out.println(ControlDatePopup.writeDateCaller("frmsrcleaveapp", FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_SUBMISSION_DATE]));
            %>
            }


            function hideObjectForDate(){
            <%
                    /*
                     out.println(ControlDatePopup.writeDateHideObj("frmsrcleaveapp", FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_APPROVAL_STATUS]));
                     out.println(ControlDatePopup.writeDateHideObj("frmsrcleaveapp", FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_DOC_STATUS]));
                     out.println(ControlDatePopup.writeDateHideObj("frmsrcleaveapp", FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_APPROVAL_HR_MAN]));
                     *      */
            %>
                    }

                    function showObjectForDate(){
            <%
                    /*
                     out.println(ControlDatePopup.writeDateShowObj("frmsrcleaveapp", FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_APPROVAL_STATUS]));
                     out.println(ControlDatePopup.writeDateShowObj("frmsrcleaveapp", FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_DOC_STATUS]));
                     out.println(ControlDatePopup.writeDateHideObj("frmsrcleaveapp", FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_APPROVAL_HR_MAN]));
                     *      */
            %>
                    } 


                    //-------------------------------------------

                    function fnTrapKD(){
                        if (event.keyCode == 13) {
                            document.all.aSearch.focus();
                            cmdSearch();
                        }
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
        </script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Leave Application List | Dimata Hairisma</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <!-- Bootstrap 3.3.5 --> 
        <link rel="stylesheet" href="<%= approot%>/assets/bootstrap/css/bootstrap.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="<%= approot%>/assets/font-awesome/4.6.1/css/font-awesome.css">
        <!-- Ionicons -->
        <link rel="stylesheet" href="<%= approot%>/assets/ionicons/2.0.1/css/ionicons.css">
        <!-- Select2 -->
        <link rel="stylesheet" href="<%= approot%>/assets/plugins/select2/select2.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="<%= approot%>/assets/dist/css/AdminLTE.css">
        <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
        <link rel="stylesheet" href="<%= approot%>/assets/dist/css/skins/skin-blue.css">
        <link rel="stylesheet" href="<%= approot %>/assets/plugins/datatables/dataTables.bootstrap.css">
        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="<%= approot%>/assets/html5shiv/3.7.3/html5shiv.min.js"></script>
            <script src="<%= approot%>/assets/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
        <style>
            #masterdata_icon {
                color: black;
            }
            #masterdata_icon:hover {
                color: #0088cc;
            }
        </style>
    </head>
    
    
    <body class="hold-transition skin-blue sidebar-mini sidebar-collapse">
        <div class="wrapper">
            <%@ include file="../../template/header.jsp" %>
            <%@ include file="../../template/sidebar.jsp" %>
            <div class="content-wrapper">
                <section class="content-header">
                    <h1>
                        Leave Application List
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="../../home.jsp"><i class="fa fa-home"></i> Home</a></li>
                        <li class="active">Leave App</li>
                    </ol>
                </section>
                <section class="content">
                    <div class="row">
                        <div class="col-md-12">
                    <form name="frmsrcleaveapp" method="post" action="">
                        <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
                        <input type="hidden" name="<%=objFrmSrcLeaveApp.fieldNames[objFrmSrcLeaveApp.FRM_FIELD_TYPE_FORM]%>" value="<%=PstLeaveApplication.LEAVE_APPLICATION%>">
                            <div class="box">
                                <div class="box-header">
                                    <div class="row pull-left">
                                        <div class="col-md-12">
                                            <button type="button" onclick= "location.href='javascript:cmdAdd()'" class="command btn btn-success"><i class="fa fa-plus"></i> Add Leave Application</button>
                                            &nbsp;
                                            &nbsp;
                                            &nbsp;
                                       </div>
                                    </div>
                                </div>
                     </form>

                                <div class="box-body">                                    
                                    <div class="box box-primary collapsed-box">  
                                        <div class="box-header with-border">
                                            <h3 class="box-title">Advance Search</h3>
                                            <div class="box-tools pull-right">
                                                <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-plus"></i></button>
                                            </div><!-- /.box-tools -->
                                        </div><!-- /.box-header -->
                                        <div class="box-body">
                                            <!--<form method="get" action="">-->
                                                <div class="row"> 
                                                    
                                                    
                                                    
                                                    
                                                    
                                                    
                                                    
                                                    
                                                    
                                                    
                                                    
                                                    
                                                    
                                                    <div class="col-md-3" style="border-right: 1px solid #f4f4f4">
                                                        <div class="form-group">
                                                            <label>Employee Number</label>
                                                            <input type="text" name="employee_num" class="form-control" placeholder="E.g : 13102009">
                                                        </div>
                                                    </div>
                                                    <div class="col-md-3" style="border-right: 1px solid #f4f4f4">
                                                        <div class="form-group">
                                                            <label>Full Name</label>
                                                            <input type="text" name="full_name" class="form-control" placeholder="E.g : I Putu Dian Pramana Putra">
                                                        </div>
                                                    </div>
                                                    <div class="col-md-4" style="border-right: 1px solid #f4f4f4">
                                                                    <div class="form-group">
                                                                        <label>Document Status</label><br>
                                                                        <input type="checkbox" name="resign" value="0" checked>  Draft
                                                                        &nbsp;
                                                                        <input type="checkbox" name="resign" value="1"> To Be Approve
                                                                        &nbsp;
                                                                        <input type="checkbox" name="resign" value="1"> Approved
                                                                        &nbsp;
                                                                        <input type="checkbox" name="resign" value="1"> Executed
                                                                    </div>
                                                    </div>
                                                    <div class="col-md-2" style="border-right: 1px solid #f4f4f4">
                                                        <div class="form-group">
                                                            <label>Type Leave</label>
                                                            <%
                                                            Vector vTypeLeave_value = new Vector(1, 1);
                                                            Vector vTypeLeave_key = new Vector(1, 1);
                                                            vTypeLeave_value.add("0");
                                                            vTypeLeave_key.add("Regular");
                                                            vTypeLeave_value.add("" + PstPublicLeaveDetail.TYPE_LEAVE);
                                                            vTypeLeave_key.add("Public Leave");
                                                            String selectTypeValue = "" + objSrcLeaveApp.getPositionId();
                                                            %>
                                                            
                                                        <%=ControlCombo.draw(objFrmSrcLeaveApp.fieldNames[objFrmSrcLeaveApp.FRM_FIELD_PUBLIC_LEAVE_TYPE], "elementForm", null, selectTypeValue, vTypeLeave_value, vTypeLeave_key, " onkeydown=\"javascript:fnTrapKD()\"")%>
                                                        </div>
                                                    </div>
                                                        
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-2" style="border-right: 1px solid #f4f4f4">
                                                        <div class="form-group">
                                                            <label>Company</label>
                                                            <select class="form-control comboCompany" style="width: 100%;" name="comboCompany" multiple="multiple">
                                                                <option>Queen Tandoor</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-2" style="border-right: 1px solid #f4f4f4">
                                                        <div class="form-group">
                                                            <label>Division</label>
                                                            <select class="form-control comboDivision" style="width: 100%;" name="comboDivision" multiple="multiple">
                                                                <optgroup label="Queen Tandoor">
                                                                    <option>Finance</option>
                                                                    <option>HRD</option>
                                                                    <option>F&B</option>
                                                                </optgroup>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-2" style="border-right: 1px solid #f4f4f4">
                                                        <div class="form-group">
                                                            <label>Department</label>
                                                            <select class="form-control comboDepartment" style="width: 100%;" name="comboDepartment" multiple="multiple">
                                                                <optgroup label="HRD">
                                                                    <option>Operational</option>
                                                                    <option>Payroll</option>
                                                                    <option>Training</option>
                                                                </optgroup>
                                                                <optgroup label="Finance">
                                                                    <option>AR</option>
                                                                    <option>AP</option>
                                                                </optgroup>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-3" style="border-right: 1px solid #f4f4f4">
                                                        <div class="form-group">
                                                            <label>Section</label>
                                                            <select class="form-control comboSection" style="width: 100%;" name="comboSection" multiple="multiple">
                                                                <optgroup label="HRD">
                                                                <optgroup label="-- Operational">
                                                                    <option>Operational</option>
                                                                    <option>Payroll</option>
                                                                    <option>Training</option>
                                                                </optgroup>
                                                                <optgroup label="-- Payroll">
                                                                    <option>Operational</option>
                                                                    <option>Payroll</option>
                                                                    <option>Training</option>
                                                                </optgroup>
                                                                <optgroup label="-- Training">
                                                                    <option>Operational</option>
                                                                    <option>Payroll</option>
                                                                    <option>Training</option>
                                                                </optgroup>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-3" style="border-right: 1px solid #f4f4f4">
                                                        <div class="form-group">
                                                            <label>Position</label>
                                                            <select class="form-control comboPosition" style="width: 100%;" name="comboPosition" multiple="multiple">
                                                                <option>HR Staff</option>
                                                                <option>Payroll Staff</option>
                                                                <option>Training Staff</option>
                                                                <option>AR Staff</option>
                                                                <option>Chief Accounting</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-2">
                                                        <div class="form-group">
                                                            <label>&nbsp;</label>
                                                            <button id="submit1" class="btn btn-block btn-primary btn-flat">Search</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            <!--</formf>-->
                                        </div><!-- /.box-body -->
                                    </div><!-- /.box -->
                                    <div class="row">                                    
                                        <div class="col-md-12">
                                            <div class="table-responsive">
                                            <div id="listdata">
                                                <form id="frm_leave_application" method="post" action="" class="form-horizontal">
                                                <input type="hidden" name="hidden_leave_application_id" value="<%=oidLeave%>">
                                                <input type="hidden" name="<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_LEAVE_APPLICATION_ID]%>" value="">                           
                                                <input type="hidden" name="oid_employee" value="">     
                                                <input type="hidden" name="oid_period" value="">
                                            </div>  
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>   
                </section>
            </div><!-- /.content-wrapper -->
            <%@ include file="../../template/footer.jsp" %>
        </div>
        <!-- jQuery 2.1.4 -->
        <script src="<%= approot%>/assets/plugins/jQuery/jQuery-2.1.4.min.js"></script>
        <!-- Bootstrap 3.3.5 -->
        <script src="<%= approot%>/assets/bootstrap/js/bootstrap.js"></script>
        <!-- Select2 -->
        <script src="<%= approot%>/assets/plugins/select2/select2.full.min.js"></script>
        <!-- FastClick -->
        <script src="<%= approot%>/assets/plugins/fastclick/fastclick.js"></script>
        <!-- AdminLTE App -->
        <script src="<%= approot%>/assets/dist/js/app.js"></script>
        <!-- SlimScroll 1.3.0 -->
        <script src="<%= approot%>/assets/plugins/slimScroll/jquery.slimscroll.js"></script>
        <script src="<%= approot%>/assets/plugins/notify/notify.jsp" ></script>
        <script src="<%= approot %>/assets/plugins/datatables/jquery.dataTables.js" type="text/javascript"></script>
        <script src="<%= approot %>/assets/plugins/datatables/dataTables.bootstrap.js" type="text/javascript"></script>
        <script>
            $(function () {
                $(".comboDivision").select2({
                    placeholder: "Division"
                });
                $(".comboCompany").select2({
                    placeholder: "Company"
                });
                $(".comboDepartment").select2({
                    placeholder: "Department"
                });
                $(".comboSection").select2({
                    placeholder: "Section"
                });
                $(".comboPosition").select2({
                    placeholder: "Position"
                });
            });
        </script>
        <script type="text/javascript">
            $(document).ready(function(){
                function modalSetting(selector, keyboard, show, backdrop){
                    $(selector).modal({
                        show : keyboard,
                        backdrop : backdrop,
                        keyboard : keyboard
                    });
                }
                
                var datePicker = function(contentId, formatDate){
                    $(contentId).datepicker({
                 format : formatDate
                    });
                    $(contentId).on('changeDate', function(ev){
                 $(this).datepicker('hide');
                    });
                };
                
                
                var getUrlParameter = function getUrlParameter(sParam) {
                var sPageURL = decodeURIComponent(window.location.search.substring(1)),
                    sURLVariables = sPageURL.split('&'),
                    sParameterName,
                    i;

                for (i = 0; i < sURLVariables.length; i++) {
                    sParameterName = sURLVariables[i].split('=');

                    if (sParameterName[0] === sParam) {
                        return sParameterName[1] === undefined ? true : sParameterName[1];
                    }
                }
            };

                      
                function sendData(url, datasend, onDone, onSuccess){
                   //alert("WORK");
                    $.ajax({
                        type    : "GET",
                        data    : datasend,
                        url     : url,
                        success : function(data){
                            onSuccess(data);
                        },
                        error: function(xhr,err){
                            alert("readyState: "+xhr.readyState+"\nstatus: "+xhr.status);
                            alert("responseText: "+xhr.responseText);
                        }
                    }).done(function(data){
                        onDone(data);
                    });
                    
                }
                
                function paging(selector){
                    $(selector).click(function(){
                        var start = $(this).data("start");
                        var commandlist = $(this).data("command");
                        $("#start").val(start);
                        $("#commandlist").val(commandlist);
                        loadData("#listdata");
                    })
                }
                
                function loadData(selector){
                       var oidLeave = $(this).data("hidden_leave_application_id");
                       var dataFor = "listEmployee";
                       var commandlist = $("#commandlist").val();
                       var start = $("#start").val();
                       var dataSend = {
                           "hidden_leave_application_id" : oidLeave,
                           "datafor" : dataFor,
                           "command" : commandlist,
                           "start" : start
                       }
                       var onDone = function(data){
                           $(selector).html(data).fadeIn("medium");
                           addEdit(".addeditdata");
                           deleteData(".deletedata");
                           $('#listEmployee').DataTable( {
                           "bScrollCollapse": true,
                            "bPaginate": true,
                            "bJQueryUI": true,
                            "iDisplayLength": 10,
                            "aLengthMenu": [[ 10, 25, 50, -1], [ 10, 25, 50, "All"]]    
                           
                       } );
                       }
                       
                       var onSuccess = function(data){
                           
                       };
                       $(selector).html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>").fadeIn("medium");
                       sendData("<%= approot %>/employee/leave/ajax/leave_app_list_ajax.jsp", dataSend, onDone, onSuccess);
                   
                }
                
                function deleteData(selector){
                    $(selector).click(function(){
                       var oidLeave = $(this).data("hidden_leave_application_id");
                       var dataFor = $(this).data("for");
                       var command = $(this).data("command");
                       var confirmText = "Are you sure to delete these data?"
                       var dataSend = {
                           "hidden_leave_application_id" : oidLeave,
                           "datafor" : dataFor,
                           "command" : command
                       }
                       var onDone = function(data){
                           //$(selector).html(data).fadeIn("medium");
                           loadData("#listdata")
                       }
                       
                       var onSuccess = function(data){
                           
                       };
                       //$(selector).html("Please wait..").fadeIn("medium");
                       
                       if(confirm(confirmText)){
                           sendData("<%= approot %>/employee/leave/ajax/leave_app_list_ajax.jsp", dataSend, onDone, onSuccess);
                       }
                        
                   });
                }
                
                
                function addEdit(selector){
                    $(selector).click(function(){
                       var oidLeave = $(this).data("hidden_leave_application_id");
                       var dataFor = $(this).data("for");
                       var command = $(this).data("command");
                       
                       $("#hidden_leave_application_id").val(oidLeave);
                       $("#datafor").val(dataFor);
                       $("#command").val("4");
                       
                       if(oidLeave != 0){
			if(dataFor == 'showposform'){
			    $(".addeditgeneral-title").html("Edit Position");
			}

                        }else{
                            if(dataFor == 'showposform'){
                                $(".addeditgeneral-title").html("Add Position");
                            }
                        }
                       
                       var dataSend1 = {
                           "hidden_leave_application_id" : oidLeave,
                           "datafor" : dataFor,
                           "command" : command
                       }
                       var onDone1 = function(data){
                           $("#modalbody").html(data).fadeIn("medium");
                           datePicker(".datepicker","yyyy-mm-dd");
                       }
                       
                       var onSuccess1 = function(data){
                           
                       };
                       
                       $("#myModal").modal("show");
                       $("#modalbody").html("Please wait...");
                       
                       sendData("<%= approot %>/employee/leave/ajax/leave_app_list_ajax.jsp", dataSend1, onDone1, onSuccess1);
                    });
                }
                modalSetting("#myModal", false, false, 'static');
                loadData("#listdata");
                
                $("form#form-modal").submit(function(){
                    var dataSend = $(this).serialize();
                    var onDone = function(data){
                        $("#myModal").modal("hide");
                        loadData("#listdata");
                        
                    }
                    var onSuccess = function(data){
                    }
                    sendData("<%= approot %>/employee/leave/ajax/levae_app_list_ajax.jsp", dataSend, onDone, onSuccess);
                    return false;
                });
            });
        </script>
    </body>
</html>
